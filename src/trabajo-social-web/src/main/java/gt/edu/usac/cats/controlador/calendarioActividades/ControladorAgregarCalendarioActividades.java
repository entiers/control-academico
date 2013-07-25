/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */
package gt.edu.usac.cats.controlador.calendarioActividades;

import gt.edu.usac.cats.dominio.CalendarioActividades;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.dominio.wrapper.WrapperCalendarioActividades;
import gt.edu.usac.cats.enums.TipoActividad;
import gt.edu.usac.cats.servicio.ServicioCalendarioActividades;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;

/**
 * Esta clase se encarga de almacenar los calendarios de actividades en la BD.
 * La informacion se pide en la pagina de
 * <code>agregarCalendarioActividades.htm</code>.
 *
 * @author Mario Batres
 * @version 1.0
 */
@Controller("controladorAgregarCalendarioActividades")
@RequestMapping("agregarCalendarioActividades.htm")
@Scope(value = WebApplicationContext.SCOPE_SESSION)
@SessionAttributes(value = {"listadoSemestres"})
public class ControladorAgregarCalendarioActividades extends ControladorAbstractoCalendarioActividades {
    //______________________________________________________________________________

    /**
     * <p>Listado de todas las semestres disponibles.</p>
     */
    private List<Semestre> listadoSemestres;
//______________________________________________________________________________
    /**
     * <p> Lleva el nombre del titulo para el mensaje en la pagina <p>
     */
    private static final String TITULO_MENSAJE = "agregarCalendarioActividades.titulo";
//______________________________________________________________________________
    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorAgregarCalendarioActividades.class);

//______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina
     * <code>agregarSemestre.htm</code>. El metodo se encarga de iniciar los
     * objetos que se usaran en la pagina.</p>
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     * seran usados en la pagina
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(method = RequestMethod.GET)
    public String crearFormulario(Model modelo, HttpServletRequest request) {
        // se agregan los objetos que se usaran en la pagina

        modelo.addAttribute("wrapperCalendarioActividades",
                new WrapperCalendarioActividades());

        this.listadoSemestres = this.servicioSemestreImpl.listarSemestresNoVencidos();

        modelo.addAttribute("listadoSemestres", this.listadoSemestres);
        modelo.addAttribute("listadoTipoActividades", TipoActividad.values());

        if (this.listadoSemestres == null || this.listadoSemestres.isEmpty()) {
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE,
                    "agregarCalendarioActividades.noExisteSemestresValidos", false);
        }

        return "calendarioActividades/agregarCalendarioActividades";
    }

//______________________________________________________________________________
    /**
     * <p>Este metodo es llamado cuando se realiza un SUBMIT desde la pagina de
     * agregar calendario de actividades. El metodo se encarga de agregar la
     * informacion ingresada en el formulario de la pagina en la base de datos,
     * el procedimiento que sigue el metodo es el siguiente: <ul> <li>Se realiza
     * la validacion de datos ingresados, si algun dato no cumple con las reglas
     * de validacion se retorna a la pagina para que se muestren los mensajes de
     * error</li> <li>Si la validacion tuvo exito se trata de agregar la
     * informacion a la base de datos por medio de
     * {@link ServicioCalendarioActividades}</li> <li>Se procesa el resultado de
     * la operacion y se le indica a la pagina el mensaje de respuesta que debe
     * de mostrar, el mensaje puede ser de exito o de error</li> </ul></p>
     *
     * @param wrapperCalendarioActividades Pojo del tipo
     * {@link WrapperCalendarioActividades}
     * @param bindingResult Objeto {@link BindingResult}, contiene el resultado
     * de las validaciones
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     * seran usados en la pagina
     * @param request Objeto {@link HttpServletRequest}
     * @return String Con la url de la pagina a mostrar
     */
    @RequestMapping(method = RequestMethod.POST)
    public String submit(@Valid WrapperCalendarioActividades wrapperCalendarioActividades,
            BindingResult bindingResult, Model modelo, HttpServletRequest request) {
        // se validan los campos ingresados en el formulario, si existen errores
        // se regresa al formulario para que se muestren los mensajes correspondientes

        modelo.addAttribute("listadoSemestres", this.listadoSemestres);
        modelo.addAttribute("listadoTipoActividades", TipoActividad.values());

        if (bindingResult.hasErrors()) {
            return "calendarioActividades/agregarCalendarioActividades";
        }
        try {

            // se quita el envoltorio y se trata de agregar al calendario de actividades
            CalendarioActividades calendarioActividades = new CalendarioActividades();
            wrapperCalendarioActividades.quitarWrapper(calendarioActividades);


            boolean existe = false;

            if (calendarioActividades.getTipoActividad() != null) {
                existe = this.servicioCalendarioActividadesImpl.existeTipoActividadPorSemestre(
                        calendarioActividades.getTipoActividad(), calendarioActividades.getSemestre());
            }


            if (!existe) {
                this.servicioCalendarioActividadesImpl.agregar(calendarioActividades);

                modelo.addAttribute("wrapperCalendarioActividades", new WrapperCalendarioActividades());

                RequestUtil.crearMensajeRespuesta(request,
                        TITULO_MENSAJE, "agregarCalendarioActividades.exito", true);

                // se registra el evento
                log.info("Agregar calendario de actividades, id: "
                        + calendarioActividades.getIdCalendarioActividades());
            } else {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE,
                        "agregarCalendarioActividades.existeTipoActividad", false);

                // se registra el evento, no hay problema que se null porque se valida en el if de arriba
                log.error("No se puede agregar una actividad para un tipo de actividad ya existen"
                        + calendarioActividades.getTipoActividad().toString());
            }

        } catch (DataIntegrityViolationException e) {
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE,
                    "agregarCalendarioActividades.dataIntegrityViolationException", false);
            log.warn(Mensajes.DATA_INTEGRITY_VIOLATION_EXCEPTION, e);

        } catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        return "calendarioActividades/agregarCalendarioActividades";
    }
}
