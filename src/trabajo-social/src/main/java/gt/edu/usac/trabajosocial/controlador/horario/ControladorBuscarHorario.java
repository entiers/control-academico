/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.controlador.horario;

import gt.edu.usac.trabajosocial.dominio.Horario;
import gt.edu.usac.trabajosocial.dominio.Salon;
import gt.edu.usac.trabajosocial.dominio.Semestre;
import gt.edu.usac.trabajosocial.dominio.busqueda.DatosBusquedaHorario;
import gt.edu.usac.trabajosocial.servicio.ServicioHorario;
import gt.edu.usac.trabajosocial.servicio.ServicioSalon;
import gt.edu.usac.trabajosocial.servicio.ServicioSemestre;
import gt.edu.usac.trabajosocial.util.MensajePopup;
import gt.edu.usac.trabajosocial.util.Mensajes;
import java.util.List;
import javax.annotation.Resource;
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
 * Esta clase se encarga de la busqueda de Horarios en la BD
 * para mostrarlos en la pagina de <code>buscarHorario.htm</code>.
 *
 * @author Mario Batres
 * @version 1.0
 */

@Controller("controladorBuscarHorario")
@RequestMapping(value="buscarHorario.htm")
public class ControladorBuscarHorario {

//______________________________________________________________________________
    /**
     * <p>
     * Lleva el nombre del titulo para el mensaje en la pagina
     * </p>
     */
    private static final String TITULO_MENSAJE = "buscarHorario.titulo";

//______________________________________________________________________________
    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorBuscarHorario.class);
//______________________________________________________________________________
    /**
     * <p>Listado de todas las cursos disponibles.</p>
     */
    protected List <Semestre> listadoSemestres;
//______________________________________________________________________________
    /**
     * <p>Listado de todas las salones disponibles.</p>
     */
    protected List <Salon> listadoSalones;
//______________________________________________________________________________
    /**
     * <p>Listado de todas las horarios disponibles.</p>
     */
    protected List <Horario> listadoHorarios;

//______________________________________________________________________________
    /**
     * <p>Contiene metodos que permiten el manejo de la informacion relacionada
     * con el semestre en la base de datos. Este objeto se encuentra registrado
     * como un bean de servicio en Spring, por lo que este es el encargado de
     * inyectar la dependencia.</p>
     */
    @Resource
    protected ServicioSemestre servicioSemestreImpl;

//______________________________________________________________________________
    /**
     * <p>Contiene metodos que permiten el manejo de la informacion relacionada
     * con el horario en la base de datos. Este objeto se encuentra registrado
     * como un bean de servicio en Spring, por lo que este es el encargado de
     * inyectar la dependencia.</p>
     */
    @Resource
    protected ServicioSalon servicioSalonImpl;

//______________________________________________________________________________
    /**
     * <p>Contiene metodos que permiten el manejo de la informacion relacionada
     * con el horario en la base de datos. Este objeto se encuentra registrado
     * como un bean de servicio en Spring, por lo que este es el encargado de
     * inyectar la dependencia.</p>
     */
    @Resource
    protected ServicioHorario servicioHorarioImpl;

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
    @RequestMapping(method = RequestMethod.GET)
    public String crearFormulario(Model modelo) {        
        this.listadoSalones = this.servicioSalonImpl.getSalones();
        this.listadoSemestres = this.servicioSemestreImpl.getSemestres();

        modelo.addAttribute("salones", this.listadoSalones);
        modelo.addAttribute("semestres", this.listadoSemestres);

        modelo.addAttribute("datosBusquedaHorario", new DatosBusquedaHorario());
        return "horario/buscarHorario";
    }

//______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cuando se solicita una busqueda desde la pagina
     * de buscar horario. Las busquedas solo se realizan por el
     * salon y semestre asigando al horari. El metodo realiza los siguientes
     * pasos:
     * <ul>
     * <li>Se valida que al menos se envio un parametro de busqueda</li>
     * <li>Valida los datos ingresados</li>
     * <li>Se delega la busqueda al metodo <code>buscar</code></li>
     * </ul>
     * </p>
     *
     * @param datosBusquedaHorario Contiene los parametros de la busqueda
     * @param bindingResult Objeto {@link BindingResult} que valida los datos
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @param request Peticion HTTP
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(method = RequestMethod.POST)
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
                MensajePopup.crearMensajeRespuesta(request, TITULO_MENSAJE, "buscarHorario.sinResultados", true);
            }
            modelo.addAttribute("horarios", listadoHorarios);
        } catch (DataAccessException e) {
            // error de acceso a datos
            MensajePopup.crearMensajeRespuesta(request, null, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }
        return "horario/buscarHorario";
    }

}
