/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author Mario Batres
 * @version 1.0
 */
@Controller
public class ControladorEliminarPensumEstudianteCarrera extends ControladorAbstractoPensumEstudianteCarrera {

    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorMostrarPensumEstudianteCarrera.class);

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

        int idAsignacionEstudianteCarrera = pensumEstudianteCarrera.getAsignacionEstudianteCarrera().getIdAsignacionEstudianteCarrera();

        try {

            this.servicioPensumEstudianteCarreraImpl.borrar(pensumEstudianteCarrera);

            RequestUtil.agregarRedirect(request, "mostrarPensumEstudianteCarrera.htm?idAsignacionEstudianteCarrera="
                    + idAsignacionEstudianteCarrera);

            RequestUtil.crearMensajeRespuesta(request, "eliminarPensumEstudianteCarrera.titulo", "eliminarPensumEstudianteCarrera.exito", true);
            log.info(Mensajes.EXITO_BORRAR + "PensumEstudianteCarrera, id " + pensumEstudianteCarrera.getIdPensumEstudianteCarrera());

        } catch (DataAccessException e) {
            RequestUtil.crearMensajeRespuesta(request, "asignarPensumEstudianteCarrera.titulo", "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        modelo.addAttribute("pensumEstudianteCarrera", null);
        return "pensumEstudianteCarrera/mostrarPensumEstudianteCarrera";
    }
}
