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
import gt.edu.usac.cats.servicio.ServicioHorario;
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
 * Esta clase se encarga de almacenar horarios en la BD.
 * La informacion se pide en la pagina de <code>agregarHorario.htm</code>.
 *
 * @author Mario Batres
 * @version 1.0
 */
@Controller("controladorAgregarHorario")
@RequestMapping(value = "agregarHorario.htm")
@Scope(value = WebApplicationContext.SCOPE_SESSION)
@SessionAttributes(value={"listadoSemestres", "listadoSalones", "listadoCursos"}) 
public class ControladorAgregarHorario extends ControladorAbstractoHorario {
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
//______________________________________________________________________________

    /**
     * <p>Lleva el nombre del titulo para el mensaje en la pagina.<p>
     */
    private static String TITULO_MENSAJE = "agregarHorario.titulo";
//______________________________________________________________________________
    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorAgregarHorario.class);
//______________________________________________________________________________

    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina <code>agregarHorario.htm</code>. El metodo se encarga
     * de iniciar los objetos que se usaran en la pagina.</p>
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(method = RequestMethod.GET)
    public String crearFormulario(Model modelo) {
        this.agregarAtributosDefault(modelo, listadoCursos, listadoSalones, listadoSemestres, new WrapperHorario(), true);
        return "horario/agregarHorario";
    }
//______________________________________________________________________________

    /**
     * <p>Este metodo es llamado cuando se realiza un SUBMIT desde la pagina de
     * agregar horario <code>agregarHorario.htm</code>. El metodo se encarga de
     * agregar la informacion ingresada en el formulario de la pagina en la
     * base de datos, el procedimiento
     * que sigue el metodo es el siguiente:
     * <ul>
     * <li>Se realiza la validacion de datos ingresados, si algun dato no cumple
     * con las reglas de validacion se retorna a la pagina para que se muestren
     * los mensajes de error</li>
     * <li>Si la validacion tuvo exito se trata de agregar la informacion a la
     * base de datos por medio de {@link ServicioHorario}</li>
     * <li>Se procesa el resultado de la operacion y se le indica a la pagina el
     * mensaje de respuesta que debe de mostrar, el mensaje puede ser de exito o
     * de error</li>
     * </ul></p>
     *
     * @param horario Pojo del tipo {@link Horario}
     * @param resultado Objeto {@link BindingResult}, contiene el resultado de
     *        las validaciones
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @param request Objeto {@link HttpServletRequest}
     * @return String Con la url de la pagina a mostrar
     */
    @RequestMapping(method = RequestMethod.POST)
    public String submit(Model modelo, @Valid WrapperHorario wrapperHorario, BindingResult bindingResult,
            HttpServletRequest request) {

        // se validan los campos ingresados en el formulario, si existen errores
        // se regresa al formulario para que se muestren los mensajes correspondientes
        if (!bindingResult.hasErrors()) {
            try {
                Horario horario = new Horario();
                wrapperHorario.quitarWrapper(horario);
                
                this.servicioHorarioImpl.agregarHorario(horario, wrapperHorario.getHorarioDiasWrapper());

                
                wrapperHorario = new WrapperHorario();
                // se registra el evento
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "agregarHorario.exito", true);                
                log.info(Mensajes.EXITO_AGREGAR + "Horario, id " + horario.getIdHorario());

            } catch (DataAccessException e) {
                // error de acceso a datos
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
                log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
            } catch (NullPointerException e) {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "agregarHorario.noDias", false);
                log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
            }
        }
        this.agregarAtributosDefault(modelo, listadoCursos, listadoSalones, listadoSemestres,  wrapperHorario, false);
        return "horario/agregarHorario";
    }
}
