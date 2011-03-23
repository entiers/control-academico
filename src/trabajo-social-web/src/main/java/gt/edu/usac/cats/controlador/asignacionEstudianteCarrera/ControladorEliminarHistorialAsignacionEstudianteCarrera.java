/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cats.controlador.asignacionEstudianteCarrera;

import gt.edu.usac.cats.dominio.HistorialAsignacionEstudianteCarrera;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
@Controller
public class ControladorEliminarHistorialAsignacionEstudianteCarrera extends ControladorAbstractoAsignacionEstudianteCarrera {

//______________________________________________________________________________
    
    private static String TITULO_MENSAJE = "eliminarHistorialAsignacionEstudianteCarrera.titulo";

//______________________________________________________________________________
    
    private static Logger log = Logger.getLogger(ControladorEliminarHistorialAsignacionEstudianteCarrera.class);

//______________________________________________________________________________
    /**
     * @param idHistorialAsignacionEstudianteCarrera
     * @param idHistorialAsignacionEstudianteCarrera
     * @param request 
     */
    @RequestMapping(value = "eliminarHistorialAsignacionEstudianteCarrera.htm", method = RequestMethod.GET)
    public String eliminar(Integer idHistorialAsignacionEstudianteCarrera,
            Integer idAsignacionEstudianteCarrera, HttpServletRequest request) {
        RequestUtil.agregarRedirect(request,
                    "mostrarHistorialAsignacionEstudianteCarrera.htm?idAsignacionEstudianteCarrera=" + idAsignacionEstudianteCarrera);

        try {
            if (idHistorialAsignacionEstudianteCarrera == null) {
                return "redirect:buscarEstudiante.htm";
            }

            HistorialAsignacionEstudianteCarrera historialAsignacionEstudianteCarrera = this.servicioAsignacionEstudianteCarreraImpl.cargarEntidadPorID(HistorialAsignacionEstudianteCarrera.class,
                    idHistorialAsignacionEstudianteCarrera);

            if (historialAsignacionEstudianteCarrera == null) {
                return "redirect:buscarEstudiante.htm";
            }

            this.servicioAsignacionEstudianteCarreraImpl.borrar(historialAsignacionEstudianteCarrera);

            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE,
                    "eliminarHistorialAsignacionEstudianteCarrera.exito", true);

            log.info(Mensajes.EXITO_BORRAR + historialAsignacionEstudianteCarrera.toString());
        } catch (DataIntegrityViolationException e) {
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataIntegrityViolatioException", false);
            log.error(Mensajes.DATA_INTEGRITY_VIOLATION_EXCEPTION, e);
        } catch (DataAccessException e) {
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }
        return "asignacionEstudianteCarrera/mostrarHistorialAsignacionEstudianteCarrera";
    }
}
