/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
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
 * <p>Controlador que se encarga de eliminar un historial de asignaci&oacute;n de carrera
 * con el estudiante y su carrera. Objeto de tipo {@link HistorialAsignacionEstudianteCarrera}</p>
 * 
 * <p>Se utiliza en en la p&aacute;gina 
 * <code>eliminarHistorialAsignacionEstudianteCarrera.htm</code> </p>
 *
 * @author Mario Batres
 * @version 1.0
 */
@Controller
public class ControladorEliminarHistorialAsignacionEstudianteCarrera extends ControladorAbstractoAsignacionEstudianteCarrera {

//______________________________________________________________________________
    /**
     * <p>Lleva el nombre del titulo para el mensaje en la pagina</p>
     */
    private static String TITULO_MENSAJE = "eliminarHistorialAsignacionEstudianteCarrera.titulo";
//______________________________________________________________________________
    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorEliminarHistorialAsignacionEstudianteCarrera.class);

//______________________________________________________________________________
    /**
     * Este m&eacute;todo elimina un historial de asignaci&oacute;n de una carrera con el estudiante
     * en la base de datos.
     *
     * @param idHistorialAsignacionEstudianteCarrera Objeto de tipo {@link Integer} que recibe
     * el id del objeto de tipo {@link HistorialAsignacionEstudianteCarrera}
     * @param idAsignacionEstudianteCarrera Objeto de tipo {@link Integer} que recibe
     * el id del objeto de tipo {@link AsignacionEstudianteCarrera} que se utiliza para hacer
     * el redirect a la vista <code>mostrarHistorialAsignacionEstudianteCarrera.htm</code>
     * @param request Objeto de tipo {@link HttpServletRequest}
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
