/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.controlador.asignacionEstudianteCarrera;

import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.HistorialAsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.wrapper.WrapperHistorialAsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.wrapper.WrapperModificarHistorialAsignacionEstudianteCarrera;
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
 * <p>Este controlador lleva el manejo de las vistas <code>mostrarHistorialAsignacionEstudianteCarrera.htm</code>
 * , <code>agregarHistorialAsignacionEstudianteCarrera.htm</code> y <code>modificarHistorialAsignacionEstudianteCarrera.htm</code>
 * , las dos &uacute;ltimas no tiene una vista fisica
 * solamente es una llamada l&oacute;gica y redirecciona a la primera vista.</p>
 *
 * <p>Se manejan todas estas vistas en este controlador porque hay datos que se almacenan
 * en los atributos que estan declarados en la clase padre.  Si se hacen en diferentes controladores
 * se pierden dichos datos.</p>
 *
 * @author Mario Batres
 * @version 1.0
 */
@Controller
public class ControladorMostrarHistorialAsignacionEstudianteCarrera
        extends ControladorAbstractoAsignacionEstudianteCarrera {
//______________________________________________________________________________
    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorMostrarHistorialAsignacionEstudianteCarrera.class);

//______________________________________________________________________________
    /**
     * <p>Lleva el nombre del titulo para el mensaje en la pagina <code>agregarHistorialAsignacionEstudianteCarrera.htm</code></p>
     */
    private static String TITULO_MENSAJE_AGREGAR = "agregarHistorialAsignacionEstudianteCarrera.titulo";

//______________________________________________________________________________
    /**
     * <p>Lleva el nombre del titulo para el mensaje en la pagina <code>modificarHistorialAsignacionEstudianteCarrera.htm</code></p>
     */
    private static String TITULO_MENSAJE_MODIFICAR = "modificarHistorialAsignacionEstudianteCarrera.titulo";

//______________________________________________________________________________
    /**
     * Se ejecuta cuando se hace llamada de tipo <b>GET</b> a la
     * p&aacute;gina <code>mostrarHistorialAsignacionEstudianteCarrera.htm</code>.
     * Crea y busca los atributos para mostrar en dicha p&aacute;gina de acuerdo
     * con el id del objeto de tipo {@link AsignacionEstudianteCarrera} en la base de datos.
     *
     * @param modelo Objeto de tipo {@link model}
     * @param idAsignacionEstudianteCarrera Objeto de tipo {@link Integer} que contiene el id del
     * objeto de tipo {@link AsignacionEstudianteCarrera} para buscar su historial deasignaciones.
     */
    @RequestMapping(value = "mostrarHistorialAsignacionEstudianteCarrera.htm", method = RequestMethod.GET)
    public String mostrar(Model modelo, Integer idAsignacionEstudianteCarrera) {
        if (idAsignacionEstudianteCarrera == null) {
            return "redirect:buscarEstudiante.htm";
        }

        this.asignacionEstudianteCarrera = this.servicioAsignacionEstudianteCarreraImpl.cargarEntidadPorID(AsignacionEstudianteCarrera.class,
                idAsignacionEstudianteCarrera);

        if (this.asignacionEstudianteCarrera == null) {
            return "redirect:buscarEstudiante.htm";
        }

        this.estudiante = asignacionEstudianteCarrera.getEstudiante();

        this.agregarAtributosDefaultHistorial(modelo,
                null,
                new WrapperHistorialAsignacionEstudianteCarrera(),
                false,
                new WrapperModificarHistorialAsignacionEstudianteCarrera(), false, true);

        return "asignacionEstudianteCarrera/mostrarHistorialAsignacionEstudianteCarrera";
    }

//______________________________________________________________________________
    /**
     * Este m&eacute;todo se ejecuta cuando se hace una llamada de tipo <b>POST</b>
     * a la p&aacute;gina <code>agregarHistorialAsignacionEstudianteCarrera.htm</code>.
     * Se encarga de agregar un nuevo historial de asignaci&oacute;n de carrera a
     * a la asignaci&oacute;n del estudiante que ha sido seleccionado previamente.
     *
     * @param modelo Objeto de tipo {@link Model}
     * @param wrapperHistorialAsignacionEstudianteCarrera Objeto de tipo {@link WrapperHistorialAsignacionEstudianteCarrera}
     * @param bindingResult Objeto de tipo {@link BindingResult}
     * @param request
     *
     * @retrun
     */
    @RequestMapping(value = "agregarHistorialAsignacionEstudianteCarrera.htm", method = RequestMethod.POST)
    public String agregar(
            Model modelo,
            @Valid WrapperHistorialAsignacionEstudianteCarrera wrapperHistorialAsignacionEstudianteCarrera,
            BindingResult bindingResult,
            HttpServletRequest request) {

        if (!bindingResult.hasErrors()) {
            try {
                HistorialAsignacionEstudianteCarrera historialAsignacionEstudianteCarrera = new HistorialAsignacionEstudianteCarrera();

                wrapperHistorialAsignacionEstudianteCarrera.quitarWrapper(historialAsignacionEstudianteCarrera);
                historialAsignacionEstudianteCarrera.setAsignacionEstudianteCarrera(this.asignacionEstudianteCarrera);

                this.servicioAsignacionEstudianteCarreraImpl.agregar(historialAsignacionEstudianteCarrera);

                RequestUtil.agregarRedirect(request, "mostrarHistorialAsignacionEstudianteCarrera.htm?idAsignacionEstudianteCarrera="
                        + this.asignacionEstudianteCarrera.getIdAsignacionEstudianteCarrera());

                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE_AGREGAR,
                        "agregarHistorialAsignacionEstudianteCarrera.exito", true);

                log.info(Mensajes.EXITO_AGREGAR + asignacionEstudianteCarrera.toString());
            } catch (DataIntegrityViolationException e) {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE_AGREGAR, "dataIntegrityViolatioException", false);
                log.error(Mensajes.DATA_INTEGRITY_VIOLATION_EXCEPTION, e);

                this.agregarAtributosDefaultHistorial(modelo, null, wrapperHistorialAsignacionEstudianteCarrera, true,
                        new WrapperModificarHistorialAsignacionEstudianteCarrera(), false, false);
            } catch (DataAccessException e) {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE_AGREGAR, "dataAccessException", false);
                log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);

                this.agregarAtributosDefaultHistorial(modelo, null, wrapperHistorialAsignacionEstudianteCarrera, true,
                        new WrapperModificarHistorialAsignacionEstudianteCarrera(), false, false);
            }
        } else {
            this.agregarAtributosDefaultHistorial(modelo, null, wrapperHistorialAsignacionEstudianteCarrera, true,
                    new WrapperModificarHistorialAsignacionEstudianteCarrera(), false, false);
        }

        return "asignacionEstudianteCarrera/mostrarHistorialAsignacionEstudianteCarrera";
    }

    /**
     * Este m&eacute;todo se ejecuta cuando se hace una llamada de tipo <b>POST</b>
     * a la p&aacute;gina <code>modificarHistorialAsignacionEstudianteCarrera.htm</code>.
     * Se encarga de modificar un historial de asignaci&oacute;n de carrera a
     * a la asignaci&oacute;n del estudiante que ha sido seleccionado previamente.
     *
     * @param modelo Objeto de tipo {@link model}
     * @param wrapperModificarHistorialAsignacionEstudianteCarrera Objeto de tipo {@link WrapperModificarHistorialAsignacionEstudianteCarrera}
     * @param bindingResult Objeto de tipo {@link BindingResult}
     * @param idHistorialAsignacionEstudianteCarrera
     * @param request Objeto de tipo {@link HttpServletRequest}
     *
     * @return
     */
    @RequestMapping(value = "modificarHistorialAsignacionEstudianteCarrera.htm", method = RequestMethod.POST)
    public String modificar(Model modelo,
            @Valid WrapperModificarHistorialAsignacionEstudianteCarrera wrapperModificarHistorialAsignacionEstudianteCarrera,
            BindingResult bindingResult,
            Integer idHistorialAsignacionEstudianteCarrera,
            HttpServletRequest request) {

        if (!bindingResult.hasErrors() && idHistorialAsignacionEstudianteCarrera != null) {
            try {
                HistorialAsignacionEstudianteCarrera historialAsignacionEstudianteCarrera =
                        this.servicioAsignacionEstudianteCarreraImpl.cargarEntidadPorID(HistorialAsignacionEstudianteCarrera.class,
                        idHistorialAsignacionEstudianteCarrera);

                wrapperModificarHistorialAsignacionEstudianteCarrera.quitarWrapper(historialAsignacionEstudianteCarrera);

                this.servicioAsignacionEstudianteCarreraImpl.actualizar(historialAsignacionEstudianteCarrera);

                RequestUtil.agregarRedirect(request, "mostrarHistorialAsignacionEstudianteCarrera.htm?idAsignacionEstudianteCarrera="
                        + historialAsignacionEstudianteCarrera.getAsignacionEstudianteCarrera().getIdAsignacionEstudianteCarrera());

                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE_MODIFICAR,
                        "modificarHistorialAsignacionEstudianteCarrera.exito", true);

                log.info(Mensajes.EXITO_ACTUALIZACION + historialAsignacionEstudianteCarrera.toString());

            } catch (DataIntegrityViolationException e) {

                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE_MODIFICAR, "dataIntegrityViolatioException", false);
                log.error(Mensajes.DATA_INTEGRITY_VIOLATION_EXCEPTION, e);

                this.agregarAtributosDefaultHistorial(modelo, idHistorialAsignacionEstudianteCarrera, new WrapperHistorialAsignacionEstudianteCarrera(), false,
                        wrapperModificarHistorialAsignacionEstudianteCarrera, true, false);
            } catch (DataAccessException e) {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE_MODIFICAR, "dataAccessException", false);
                log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);

                this.agregarAtributosDefaultHistorial(modelo, idHistorialAsignacionEstudianteCarrera,
                        new WrapperHistorialAsignacionEstudianteCarrera(), false, wrapperModificarHistorialAsignacionEstudianteCarrera, true, false);
            }
        } else {
            this.agregarAtributosDefaultHistorial(modelo, idHistorialAsignacionEstudianteCarrera, new WrapperHistorialAsignacionEstudianteCarrera(),
                    false, wrapperModificarHistorialAsignacionEstudianteCarrera, true, false);
        }

        return "asignacionEstudianteCarrera/mostrarHistorialAsignacionEstudianteCarrera";
    }
}
