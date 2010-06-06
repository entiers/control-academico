/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.controlador.horario;

import gt.edu.usac.trabajosocial.dominio.Curso;
import gt.edu.usac.trabajosocial.dominio.Horario;
import gt.edu.usac.trabajosocial.dominio.Salon;
import gt.edu.usac.trabajosocial.dominio.Semestre;
import gt.edu.usac.trabajosocial.dominio.busqueda.DatosBusquedaHorario;
import gt.edu.usac.trabajosocial.dominio.wrapper.WrapperHorario;
import gt.edu.usac.trabajosocial.util.MensajePopup;
import gt.edu.usac.trabajosocial.util.Mensajes;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */

@Controller("controladorBuscarEditarHorario")
public class ControladorBuscarEditarHorario extends ControladorHorarioAbstracto {

    /**
     * <p>Lleva el nombre del titulo para el mensaje en la página de buscar.</p>
     */
    private static final String TITULO_MENSAJE_BUSCAR = "buscarHorario.titulo";
    private static final String TITULO_MENSAJE_EDITAR = "editarHorario.titulo";
//______________________________________________________________________________
    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorBuscarEditarHorario.class);
//______________________________________________________________________________
    /**
     * <p></p>
     */
    private List <Horario> listadoHorarios;
//______________________________________________________________________________
    /**
     * <p></p>
     */
    private Horario horario;
//______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina <code>buscarHorario.htm</code>. El metodo se encarga
     * de iniciar los objetos que se usaran en la pagina.</p>
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(value="buscarHorario.htm", method = RequestMethod.GET)
    public String crearFormularioBuscar(Model modelo) {        
        this.listadoSalones = this.servicioGeneralImpl.listarEntidad(Salon.class);        
        this.listadoSemestres = this.servicioGeneralImpl.listarEntidad(Semestre.class);        

        modelo.addAttribute("salones", this.listadoSalones);
        modelo.addAttribute("semestres", this.listadoSemestres);

        modelo.addAttribute("datosBusquedaHorario", new DatosBusquedaHorario());
        return "horario/buscarHorario";
    }
//______________________________________________________________________________
    /**
     *
     * @param datosBusquedaHorario
     * @param bindingResult
     * @param modelo
     * @param request
     * 
     * @return String
     */
    @RequestMapping(value="buscarHorario.htm", method = RequestMethod.POST)
    public String buscar(@Valid DatosBusquedaHorario datosBusquedaHorario, BindingResult bindingResult,
            Model modelo, HttpServletRequest request) {

        modelo.addAttribute("salones", this.listadoSalones);
        modelo.addAttribute("semestres", this.listadoSemestres);

        if(bindingResult.hasErrors())
            return "horario/buscarHorario";

        try{
            Salon salon = datosBusquedaHorario.getSalon();
            Semestre semestre = datosBusquedaHorario.getSemestre();
            
            this.listadoHorarios = this.servicioHorarioImpl.buscarHorarioPorSalonYSemestre(salon, semestre);

            if(listadoHorarios == null || listadoHorarios.size() == 0) {
                MensajePopup.crearMensajeRespuesta(request, TITULO_MENSAJE_BUSCAR, "buscarHorario.sinResultados", true);
            }
            modelo.addAttribute("horarios", listadoHorarios);
        } catch (DataAccessException e) {
            // error de acceso a datos
            MensajePopup.crearMensajeRespuesta(request, null, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        return "horario/buscarHorario";
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina <code>editarHorario.htm</code>. El metodo se encarga
     * de iniciar los objetos que se usaran en la pagina.</p>
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     * seran usados en la pagina
     * @param idHorario Id del Horario para realizar la busqueda y mostrar los datos
     * en la vista
     *
     * @return String
     */
    @RequestMapping(value="editarHorario.htm", method = RequestMethod.GET)
    public String crearFormularioEditar(Model modelo, int idHorario){
        /**Estos no se cargan porque ya han cargados desde la página de búsqueda*/
        modelo.addAttribute("salones", this.listadoSalones);
        modelo.addAttribute("semestres", this.listadoSemestres);


        this.listadoCursos = this.servicioGeneralImpl.listarEntidad(Curso.class);
        modelo.addAttribute("cursos", listadoCursos);
        modelo.addAttribute("dias", LISTADO_DIAS);

        WrapperHorario wrapperHorario = new WrapperHorario();

        this.horario = null;
        Iterator it = this.listadoHorarios.iterator();

        boolean encontrado = false;

        while(it.hasNext() && !encontrado){
            this.horario = (Horario) it.next();
            if(this.horario.getIdHorario() == idHorario){
                encontrado = true;
            }
        }

        if(encontrado){
            wrapperHorario.agregarWrapper(this.horario);
        }

        modelo.addAttribute("wrapperHorario", wrapperHorario);
        return "horario/editarHorario";
    }
//______________________________________________________________________________
    /**
     * @param wrapperHorario
     * @param bindingResult
     * @param modelo
     * @param request
     *
     * @return String
     **/
    @RequestMapping(value="editarHorario.htm", method = RequestMethod.POST)
    public String editar(@Valid WrapperHorario wrapperHorario, BindingResult bindingResult,
            Model modelo, HttpServletRequest request){

        modelo.addAttribute("salones", this.listadoSalones);
        modelo.addAttribute("semestres", this.listadoSemestres);
        modelo.addAttribute("cursos", this.listadoCursos);
        modelo.addAttribute("dias", LISTADO_DIAS);


        if(bindingResult.hasErrors()){

            return "horario/editarHorario";
        }

        try {

            // se quita el envoltorio y se trata de actualizar al horario
            wrapperHorario.quitarWrapper(this.horario);
            this.servicioHorarioImpl.actualizarHorario(this.horario);

            // se registra el evento
            MensajePopup.crearMensajeRespuesta(request, TITULO_MENSAJE_EDITAR, "editarHorario.exito", true);
            String msg = Mensajes.EXITO_ACTUALIZACION + "Catedratico, codigo " + this.horario.getIdHorario();
            log.info(msg);

        } catch (DataAccessException e) {
            // error de acceso a datos
            MensajePopup.crearMensajeRespuesta(request, null, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        return "horario/editarHorario";
    }
}
