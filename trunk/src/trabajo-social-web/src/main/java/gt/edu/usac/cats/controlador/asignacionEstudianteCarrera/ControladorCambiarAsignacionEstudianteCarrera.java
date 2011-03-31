/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.controlador.asignacionEstudianteCarrera;

import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.HistorialAsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.wrapper.WrapperAgregarAsignacionEstudianteCarrera;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <p>Este controlador lleva el manejo de la vista<code>cambiarAsignacionEstudianteCarrera.htm</code>
 * que se encarga de realizar el cambio de carrera de un estudiante.
 * </p>
 *
 * @author Mario Batres
 * @version 1.0
 */
@Controller
@RequestMapping(value = "cambiarAsignacionEstudianteCarrera.htm")
public class ControladorCambiarAsignacionEstudianteCarrera extends ControladorAbstractoAsignacionEstudianteCarrera {
//______________________________________________________________________________
    /**
     * <p>Lleva el nombre del titulo para el mensaje en la pagina </p>
     */
    public static String TITULO_MENSAJE = "cambiarAsignacionEstudianteCarrera.titulo";
//______________________________________________________________________________
    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorCambiarAsignacionEstudianteCarrera.class);
//______________________________________________________________________________
    /**
    * Se ejecuta cuando se hace llamada de tipo <b>GET</b> a la
     * p&aacute;gina <code>cambiarAsignacionEstudianteCarrera.htm</code>.
     * Crea y busca los atributos para mostrar en dicha p&aacute;gina de acuerdo
     * con el id del objeto de tipo {@link AsignacionEstudianteCarrera} en la base de datos.
     *
     * @param modelo Objeto de tipo {@link Model}
     * @param idAsignacionEstudianteCarrera Objeto de tipo {@link Integer} que recibe
     * el id del objeto de tipo {@link AsignacionEstudianteCarrera} que est&aacute; en la base
     * de datos.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String crearFormulario(Model modelo, Integer idAsignacionEstudianteCarrera) {
        if (idAsignacionEstudianteCarrera == null) {
            return "redirect:buscarEstudiante.htm";
        }

        this.asignacionEstudianteCarrera =
                this.servicioAsignacionEstudianteCarreraImpl.cargarEntidadPorID(AsignacionEstudianteCarrera.class, idAsignacionEstudianteCarrera);

        if (this.asignacionEstudianteCarrera == null) {
            return "redirect:buscarEstudiante.htm";
        }

        this.agregarAtributosDefaultCambiarAsignacion(modelo, new WrapperAgregarAsignacionEstudianteCarrera(), true);
        return "asignacionEstudianteCarrera/cambiarAsignacionEstudianteCarrera";
    }
//______________________________________________________________________________
    /**
     * Este m&eacute;todo se ejecuta cuando se hace una llamada de tipo <b>POST</b>
     * a la p&aacute;gina <code>cambiarAsignacionEstudianteCarrera.htm</code>.
     * Se encarga de ejecutar el proceso para el cambio de carrera de un estudiante que 
     * ha sido seleccionado previamente.
     *
     * @param modelo Objeto de tipo {@link Model}
     * @param wrapperAgregarAsignacionEstudianteCarrera Objeto de tipo {@link WrapperAgregarAsignacionEstudianteCarrera}
     * @param bindingResult Objeto de tipo {@link BindingResult}
     * @param request Objeto de tipo {@link HttpServletRequest}
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public String realizarCambio(Model modelo,
            @Valid WrapperAgregarAsignacionEstudianteCarrera wrapperAgregarAsignacionEstudianteCarrera,
            BindingResult bindingResult,
            HttpServletRequest request) {

        if (!bindingResult.hasErrors()) {
            try {
                AsignacionEstudianteCarrera asignacionEstudianteCarreraNueva =
                        new AsignacionEstudianteCarrera();

                HistorialAsignacionEstudianteCarrera historialAsignacionEstudianteCarrera =
                        new HistorialAsignacionEstudianteCarrera();

                wrapperAgregarAsignacionEstudianteCarrera.quitarWrapper(
                        asignacionEstudianteCarreraNueva,
                        historialAsignacionEstudianteCarrera);

                this.servicioAsignacionEstudianteCarreraImpl.realizarCambioAsignacionEstudianteCarrera(
                        this.asignacionEstudianteCarrera,
                        asignacionEstudianteCarreraNueva,
                        historialAsignacionEstudianteCarrera);

                RequestUtil.agregarRedirect(request, "mostrarAsignacionEstudianteCarrera.htm?idEstudiante="
                        + this.estudiante.getIdEstudiante());


                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "cambiarAsignacionEstudianteCarrera.exito", true);
                log.info("Cambio de carrera de " + asignacionEstudianteCarrera.toString()
                        + " a " + asignacionEstudianteCarreraNueva.toString());

            } catch (DataIntegrityViolationException e) {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataIntegrityViolationException", false);
                log.error(Mensajes.DATA_INTEGRITY_VIOLATION_EXCEPTION, e);

                this.agregarAtributosDefaultCambiarAsignacion(modelo,
                        new WrapperAgregarAsignacionEstudianteCarrera(),
                        false);
            } catch (DataAccessException e) {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
                log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);

                this.agregarAtributosDefaultCambiarAsignacion(modelo,
                        new WrapperAgregarAsignacionEstudianteCarrera(),
                        false);
            }
        } else {
            this.agregarAtributosDefaultCambiarAsignacion(modelo,
                    wrapperAgregarAsignacionEstudianteCarrera,
                    false);
        }
        return "asignacionEstudianteCarrera/cambiarAsignacionEstudianteCarrera";
    }
}
