/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.controlador.pensumEstudianteCarrera;

import gt.edu.usac.cats.dominio.PensumEstudianteCarrera;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <p>Controlador que se encarga de eliminar una asignaci&oacute;n de un pensum
 * con el estudiante y su carrera. Objeto de tipo {@link PensumEstudianteCarrera}</p>
 * 
 * <p>Se utiliza en en la p&aacute;gina 
 * <code>eliminarPensumEstudianteCarrera.htm</code> </p>
 *
 * @author Mario Batres
 * @version 1.0
 */
@Controller
public class ControladorEliminarPensumEstudianteCarrera extends ControladorAbstractoPensumEstudianteCarrera {

//______________________________________________________________________________
    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorMostrarPensumEstudianteCarrera.class);

//______________________________________________________________________________
    /**
     * Este m&eacute;todo elimina la asignaci&oacute;n de un pensum con el estudiante
     * y su carrera en la base de datos.
     *
     * @param modelo Objeto de tipo {@link Model}
     * @param idPensumEstudianteCarrera Objeto de tipo {@link Integer} que obtiene el id de
     * la asignaci&oacute;n del pensum con el estudinate y la carrera.
     * @param request Objeto de tipo {@link HttpServletRequest}
     *
     * @return String de la vista a mostrar
     */
    @RequestMapping(value = "eliminarPensumEstudianteCarrera.htm", method = RequestMethod.GET)
    public String eliminarPensumEstudianteCarrera(Model modelo, Integer idPensumEstudianteCarrera, HttpServletRequest request) {

        if (idPensumEstudianteCarrera == null) {
            return "redirect:buscarEstudiante.htm";
        }

        PensumEstudianteCarrera pensumEstudianteCarrera = this.servicioPensumEstudianteCarreraImpl.cargarEntidadPorID(PensumEstudianteCarrera.class,
                idPensumEstudianteCarrera);


        if (pensumEstudianteCarrera == null) {
            return "redirect:buscarEstudiante.htm";
        }

        //Esto es para hacer realiar el redirect.
        int idAsignacionEstudianteCarrera = pensumEstudianteCarrera.getAsignacionEstudianteCarrera().getIdAsignacionEstudianteCarrera();

        try {

            this.servicioPensumEstudianteCarreraImpl.borrar(pensumEstudianteCarrera);

            RequestUtil.agregarRedirect(request, "mostrarPensumEstudianteCarrera.htm?idAsignacionEstudianteCarrera="
                    + idAsignacionEstudianteCarrera);

            RequestUtil.crearMensajeRespuesta(request, "eliminarPensumEstudianteCarrera.titulo", "eliminarPensumEstudianteCarrera.exito", true);
            log.info(Mensajes.EXITO_BORRAR + pensumEstudianteCarrera.toString());

        } catch (DataAccessException e) {
            RequestUtil.crearMensajeRespuesta(request, "asignarPensumEstudianteCarrera.titulo", "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        modelo.addAttribute("pensumEstudianteCarrera", null);
        return "pensumEstudianteCarrera/mostrarPensumEstudianteCarrera";
    }
}