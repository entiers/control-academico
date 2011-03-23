/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cats.controlador.asignacionEstudianteCarrera;

import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.Estudiante;
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
import gt.edu.usac.cats.dominio.wrapper.WrapperModificarAsignacionEstudianteCarrera;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
@Controller
public class ControladorMostrarAsignacionEstudianteCarrera extends ControladorAbstractoAsignacionEstudianteCarrera {

    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorMostrarAsignacionEstudianteCarrera.class);

    private static String TITULO_MENSAJE_AGREGAR = "agregarAsignacionEstudianteCarrera.titulo";
    private static String TITULO_MENSAJE_MODIFICAR = "modificarAsignacionEstudianteCarrera.titulo";


    @RequestMapping(value = "mostrarAsignacionEstudianteCarrera.htm", method = RequestMethod.GET)
    public String mostrar(Model modelo, Integer idEstudiante) {
        if (idEstudiante == null) {
            return "redirect:buscarEstudiante.htm";
        }

        this.estudiante = this.servicioAsignacionEstudianteCarreraImpl.cargarEntidadPorID(Estudiante.class, idEstudiante);

        if (estudiante == null) {
            return "redirect:buscarEstudiante.htm";
        }

        this.agregarAtributosDefault(modelo, new WrapperAgregarAsignacionEstudianteCarrera(), false, 
                new WrapperModificarAsignacionEstudianteCarrera(), false, true);



        return "asignacionEstudianteCarrera/mostrarAsignacionEstudianteCarrera";
    }

    @RequestMapping(value = "agregarAsignacionEstudianteCarrera.htm", method = RequestMethod.POST)
    public String agregar(
            @Valid WrapperAgregarAsignacionEstudianteCarrera wrapperAgregarAsignacionEstudianteCarrera,
            BindingResult bindingResult, Model modelo, HttpServletRequest request) {

        if (!bindingResult.hasErrors()) {
            try {
                this.asignacionEstudianteCarrera = new AsignacionEstudianteCarrera();
                HistorialAsignacionEstudianteCarrera historialAsignacionEstudianteCarrera = new HistorialAsignacionEstudianteCarrera();

                wrapperAgregarAsignacionEstudianteCarrera.quitarWrapper(asignacionEstudianteCarrera, historialAsignacionEstudianteCarrera);

                asignacionEstudianteCarrera.setEstudiante(this.estudiante);


                this.servicioAsignacionEstudianteCarreraImpl.agregarAsignacionEstudianteCarrera(asignacionEstudianteCarrera,
                        historialAsignacionEstudianteCarrera);


                RequestUtil.agregarRedirect(request, "mostrarAsignacionEstudianteCarrera.htm?idEstudiante="
                        + this.estudiante.getIdEstudiante());


                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE_AGREGAR, "agregarAsignacionEstudianteCarrera.exito", true);
                log.info(Mensajes.EXITO_AGREGAR + asignacionEstudianteCarrera.toString());


            } catch (DataIntegrityViolationException e) {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE_AGREGAR, "dataIntegrityViolationException", false);
                log.error(Mensajes.DATA_INTEGRITY_VIOLATION_EXCEPTION, e);

                this.agregarAtributosDefault(modelo, wrapperAgregarAsignacionEstudianteCarrera,
                        true, new WrapperModificarAsignacionEstudianteCarrera(),  false, false);
            } catch (DataAccessException e) {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE_AGREGAR, "dataAccessException", false);
                log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);

                this.agregarAtributosDefault(modelo, wrapperAgregarAsignacionEstudianteCarrera,
                        true, new WrapperModificarAsignacionEstudianteCarrera(),  false, false);
            }
        } else {
            this.agregarAtributosDefault(modelo, wrapperAgregarAsignacionEstudianteCarrera,
                        true, new WrapperModificarAsignacionEstudianteCarrera(),  false, false);
        }
        return "asignacionEstudianteCarrera/mostrarAsignacionEstudianteCarrera";
    }

    @RequestMapping(value = "modificarAsignacionEstudianteCarrera.htm", method = RequestMethod.POST)
    public String modificar(Model modelo, @Valid WrapperModificarAsignacionEstudianteCarrera wrapperModificarAsignacionEstudianteCarrera
            , BindingResult bindingResult, HttpServletRequest request){

            if (!bindingResult.hasErrors()) {
            try {
                this.asignacionEstudianteCarrera = this.servicioAsignacionEstudianteCarreraImpl
                        .cargarEntidadPorID(AsignacionEstudianteCarrera.class, wrapperModificarAsignacionEstudianteCarrera.getIdAsignacionEstudianteCarrera());

                wrapperModificarAsignacionEstudianteCarrera.quitarWrapper(asignacionEstudianteCarrera);

                this.servicioAsignacionEstudianteCarreraImpl.actualizar(asignacionEstudianteCarrera);


                RequestUtil.agregarRedirect(request, "mostrarAsignacionEstudianteCarrera.htm?idEstudiante="
                        + this.estudiante.getIdEstudiante());


                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE_MODIFICAR, "modificarAsignacionEstudianteCarrera.exito", true);
                log.info(Mensajes.EXITO_ACTUALIZACION + asignacionEstudianteCarrera.toString());


            } catch (DataIntegrityViolationException e) {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE_MODIFICAR, "dataIntegrityViolationException", false);
                log.error(Mensajes.DATA_INTEGRITY_VIOLATION_EXCEPTION, e);

                this.agregarAtributosDefault(modelo, new WrapperAgregarAsignacionEstudianteCarrera(),
                        false, wrapperModificarAsignacionEstudianteCarrera,  true, false);
            } catch (DataAccessException e) {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE_MODIFICAR, "dataAccessException", false);
                log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);

                this.agregarAtributosDefault(modelo, new WrapperAgregarAsignacionEstudianteCarrera(),
                        false, wrapperModificarAsignacionEstudianteCarrera,  true, false);
            }
        } else {
            this.agregarAtributosDefault(modelo, new WrapperAgregarAsignacionEstudianteCarrera(),
                        false, wrapperModificarAsignacionEstudianteCarrera,  true, false);
        }
        return "asignacionEstudianteCarrera/mostrarAsignacionEstudianteCarrera";

    }
}
