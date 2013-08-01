/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */
package gt.edu.usac.cats.controlador.horario;


import gt.edu.usac.cats.dominio.AsignacionCursoPensum;
import gt.edu.usac.cats.dominio.Horario;
import gt.edu.usac.cats.dominio.Salon;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.dominio.wrapper.WrapperHorario;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;

/**
 *Esta clase se encarga de modficar un horario existente en la BD.
 * La informacion se pide en la pagina de <code>editarHorario.htm</code>.
 *
 * @author Mario Batres
 * @version 1.0
 */
@Controller("controladorEditarHorario")
@RequestMapping(value = "editarHorario.htm")
@Scope(value = WebApplicationContext.SCOPE_SESSION)
@SessionAttributes(value={"listadoSemestres", "listadoSalones", "listadoCursos"}) 
public class ControladorEditarHorario extends ControladorAbstractoHorario {
//______________________________________________________________________________

    /**
     * <p>Listado de todas las cursos disponibles.</p>
     */
    private List<Semestre> listadoSemestres;
//______________________________________________________________________________
    /**
     * <p>Listado de todas las salones disponibles.</p>
     */
    private List<Salon> listadoSalones;
//______________________________________________________________________________
    /**
     * <p>Listado de todas las cursos disponibles.</p>
     */
    private List<AsignacionCursoPensum> listadoCursos;     
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
     * <p>Listado de todas las cursos disponibles.</p>    */
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
    public String crearFormularioEditar(Model modelo, Integer idHorario, HttpServletRequest request) {

        if (idHorario == null) {
            return "redirect:buscarHorario.htm";
        }

        this.horario = this.servicioHorarioImpl.cargarEntidadPorID(Horario.class, idHorario);

        if (this.horario == null) {
            return "redirect:buscarHorario.htm";
        }

        WrapperHorario wrapperHorario = new WrapperHorario();
        wrapperHorario.agregarWrapper(this.horario);
        modelo.addAttribute("wrapperHorario", wrapperHorario);

        this.agregarAtributosDefault(modelo, listadoCursos, listadoSalones, listadoSemestres, wrapperHorario);
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
    public String editar(Model modelo, @Valid WrapperHorario wrapperHorario, BindingResult bindingResult,
            HttpServletRequest request) {
// se validan los campos ingresados en el formulario, si existen errores
        // se regresa al formulario para que se muestren los mensajes correspondientes
        if (!bindingResult.hasErrors()) {
            try {
                wrapperHorario.quitarWrapper(this.horario);

                this.servicioHorarioImpl.actualizarHorario(horario, wrapperHorario.getHorarioDiasWrapper());

                /*this.servicioHorarioImpl.actualizar(this.horario);
                this.servicioHorarioImpl.borrar(this);

                for (String numeroDia : wrapperHorario.getHorarioDiasWrapper()) {
                    this.servicioHorarioImpl.agregar(new HorarioDia(horario, Integer.parseInt(numeroDia)));
                }*/
                
                RequestUtil.agregarRedirect(request, "buscarHorario.htm");
                // se registra el evento
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "editarHorario.exito", true);
                log.info(Mensajes.EXITO_ACTUALIZACION + "Horario, id " + horario.getIdHorario());

            } catch (DataAccessException e) {
                // error de acceso a datos
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
                log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
            } catch (NullPointerException e) {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "agregarHorario.noDias", false);
                log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
            }
        }

        this.agregarAtributosDefault(modelo, listadoCursos, listadoSalones, listadoSemestres, wrapperHorario);
        return "horario/editarHorario";
    }
}
