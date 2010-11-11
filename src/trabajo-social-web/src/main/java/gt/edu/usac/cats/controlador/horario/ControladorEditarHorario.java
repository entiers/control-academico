/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.controlador.horario;

import gt.edu.usac.cats.dominio.Curso;
import gt.edu.usac.cats.dominio.Horario;
import gt.edu.usac.cats.dominio.Salon;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.dominio.wrapper.WrapperHorario;
import gt.edu.usac.cats.servicio.ServicioHorario;
import gt.edu.usac.cats.util.RequestUtil;
import gt.edu.usac.cats.util.Mensajes;
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
 *Esta clase se encarga de modficar un horario existente en la BD.
 * La informacion se pide en la pagina de <code>editarHorario.htm</code>.
 *
 * @author Mario Batres
 * @version 1.0
 */

@Controller("controladorEditarHorario")
@RequestMapping(value="editarHorario.htm")
public class ControladorEditarHorario {

    protected static final String [] LISTADO_DIAS =
    {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};
//_____________________________________________________________________________
    /**
     * <p>Lleva el nombre del titulo para el mensaje en la pagina.</p>
     */
    private static final String TITULO_MENSAJE = "editarHorario.titulo";
//______________________________________________________________________________
    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorEditarHorario.class);
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
     * <p>Listado de todas las cursos disponibles.</p>
     */
    protected List <Curso> listadoCursos;
//______________________________________________________________________________    
    /**
     * <p>Contiene metodos que permiten el manejo de la informacion relacionada
     * con el salon en la base de datos. Este objeto se encuentra registrado
     * como un bean de servicio en Spring, por lo que este es el encargado de
     * inyectar la dependencia.</p>
     */
    @Resource
    protected ServicioHorario servicioHorarioImpl;
//______________________________________________________________________________
    /**
     * <p>Se utiliza para mantener todos los datos del horario que se
     * encontro en la busqueda.</p>
     */
    private Horario horario;
//______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina <code>editarHorario.htm</code>. El metodo se encarga
     * de iniciar los objetos que se usaran en la pagina.</p>
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(method = RequestMethod.GET)
    public String crearFormularioEditar(Model modelo, int idHorario, HttpServletRequest request){

        this.horario = this.servicioHorarioImpl.cargarEntidadPorID(Horario.class, idHorario);

        if(this.horario == null){
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "buscarCalendarioActividades.sinResultados", false);
            return "horario/buscarHorario";
        }

        WrapperHorario wrapperHorario = new WrapperHorario();
        wrapperHorario.agregarWrapper(this.horario);

        this.listadoSalones = this.servicioHorarioImpl.listarEntidad(Salon.class);
        this.listadoSemestres = this.servicioHorarioImpl.listarEntidad(Semestre.class);
        this.listadoCursos = this.servicioHorarioImpl.listarEntidad(Curso.class);

        modelo.addAttribute("salones", this.listadoSalones);
        modelo.addAttribute("semestres", this.listadoSemestres);
        modelo.addAttribute("cursos", listadoCursos);
        modelo.addAttribute("dias", LISTADO_DIAS);
        modelo.addAttribute("wrapperHorario", wrapperHorario);
        return "horario/editarHorario";
    }

    /**
     * <p>Este metodo se ejecuta cuando se presiona el boton de editar de la
     * pagina. El metodo se encarga de actualizar la informacion del horario
     * que se obtuvo en la busqueda. El metodo realiza las siguiente acciones:
     * <ul>
     * <li>Realiza las validaciones de los datos del formulario</li>
     * <li>Delega la funcion de actualizacion a {@link ServicioCalendarioActividades}</li>
     * <li>Muestra un mensaje popup con el resultado de la operacion</li>
     * </ul></p>
     *
     * @param wrapperCalendarioActividades Pojo del tipo {@link WrapperCalendarioActividades}
     * @param bindingResult Ojeto {@link BindingResult} que realiza las validaciones
     * @param modelo Objeto {@link Model} que contiene los objeto de la pagina
     * @param request Objeto {@link HttpServletRequest}
     * @return String
     */

    @RequestMapping(method = RequestMethod.POST)
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
            this.servicioHorarioImpl.actualizar(this.horario);

            // se registra el evento
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "editarHorario.exito", true);
            String msg = Mensajes.EXITO_ACTUALIZACION + "Catedratico, codigo " + this.horario.getIdHorario();
            log.info(msg);

            modelo.addAttribute("wrapperHorario", wrapperHorario);

        } catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, null, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        return "horario/editarHorario";
    }
}
