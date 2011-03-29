/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author Mario Batres
 * @version 1.0
 */
@Controller
@RequestMapping(value = "cambiarAsignacionEstudianteCarrera.htm")
public class ControladorCambiarAsignacionEstudianteCarrera extends ControladorAbstractoAsignacionEstudianteCarrera {
    /***/
    public static String TITULO_MENSAJE = "cambiarAsignacionEstudianteCarrera.titulo";

    /***/
    private static Logger log = Logger.getLogger(ControladorCambiarAsignacionEstudianteCarrera.class);

    /**
     * @param modelo
     * @param idAsignacionEstudianteCarrera 
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

    /**
     * @param modelo
     * @param wrapperAgregarAsignacionEstudianteCarrera
     * @param bindingResult
     * @param request
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
