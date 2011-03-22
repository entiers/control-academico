/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cats.controlador.asignacionEstudianteCarrera;

import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.HistorialAsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.wrapper.WrapperHistorialAsignacionEstudianteCarrera;
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
public class ControladorMostrarHistorialAsignacionEstudianteCarrera
        extends ControladorAbstractoAsignacionEstudianteCarrera {

    private static Logger log = Logger.getLogger(ControladorMostrarHistorialAsignacionEstudianteCarrera.class);

    private static String TITULO_MENSAJE = "agregarHistorialAsignacionEstudianteCarrera.titulo";

    @RequestMapping(value = "mostrarHistorialAsignacionEstudianteCarrera.htm", method = RequestMethod.GET)
    public String mostrar(Model modelo, Integer idAsignacionEstudianteCarrera) {


        this.asignacionEstudianteCarrera = this.servicioAsignacionEstudianteCarreraImpl.cargarEntidadPorID(AsignacionEstudianteCarrera.class, idAsignacionEstudianteCarrera);

        this.estudiante = asignacionEstudianteCarrera.getEstudiante();


        this.agregarAtributosDefaultHistorial(modelo, new WrapperHistorialAsignacionEstudianteCarrera(), false, true);
        return "asignacionEstudianteCarrera/mostrarHistorialAsignacionEstudianteCarrera";
    }

    @RequestMapping(value = "agregarHistorialAsignacionEstudianteCarrera.htm", method = RequestMethod.POST)
    public String agregar(Model modelo, @Valid WrapperHistorialAsignacionEstudianteCarrera wrapperHistorialAsignacionEstudianteCarrera, BindingResult bindingResult, HttpServletRequest request) {

        if (!bindingResult.hasErrors()) {
            try {
                HistorialAsignacionEstudianteCarrera historialAsignacionEstudianteCarrera = new HistorialAsignacionEstudianteCarrera();

                wrapperHistorialAsignacionEstudianteCarrera.quitarWrapper(historialAsignacionEstudianteCarrera);
                historialAsignacionEstudianteCarrera.setAsignacionEstudianteCarrera(this.asignacionEstudianteCarrera);

                this.servicioAsignacionEstudianteCarreraImpl.agregar(historialAsignacionEstudianteCarrera);

                RequestUtil.agregarRedirect(request, "mostrarHistorialAsignacionEstudianteCarrera.htm?idAsignacionEstudianteCarrera="
                        + this.asignacionEstudianteCarrera.getIdAsignacionEstudianteCarrera());

                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE,
                        "agregarHistorialAsignacionEstudianteCarrera.exito", true);

                log.info(Mensajes.EXITO_AGREGAR + asignacionEstudianteCarrera.toString());
            } catch (DataIntegrityViolationException e) {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataIntegrityViolatioException", false);
                log.error(Mensajes.DATA_INTEGRITY_VIOLATION_EXCEPTION, e);

                this.agregarAtributosDefaultHistorial(modelo, wrapperHistorialAsignacionEstudianteCarrera, true, false);
            } catch (DataAccessException e) {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
                log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);

                this.agregarAtributosDefaultHistorial(modelo, wrapperHistorialAsignacionEstudianteCarrera, true, false);
            }
        } else {
            this.agregarAtributosDefaultHistorial(modelo, wrapperHistorialAsignacionEstudianteCarrera, true, false);
        }

        return "asignacionEstudianteCarrera/mostrarHistorialAsignacionEstudianteCarrera";
    }
}
