/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */
package gt.edu.usac.cats.controlador.asignacionEstudianteCarrera;

import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.HistorialAsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.wrapper.WrapperAgregarAsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.wrapper.WrapperEquivalenciaPorCarrera;
import gt.edu.usac.cats.dominio.wrapper.WrapperModificarAsignacionEstudianteCarrera;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import java.io.Serializable;
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
 * <p>Este controlador lleva el manejo de las vistas
 * <code>mostrarAsignacionEstudianteCarrera.htm</code> ,
 * <code>agregarAsignacionEstudianteCarrera.htm</code> y
 * <code>modificarAsignacionEstudianteCarrera.htm</code> , las dos
 * &uacute;ltimas no tiene una vista fisica solamente es una llamada
 * l&oacute;gica y redirecciona a la primera vista.</p>
 *
 * <p>Se manejan todas estas vistas en este controlador porque hay datos que se
 * almacenan en los atributos que estan declarados en la clase padre. Si se
 * hacen en diferentes controladores se pierden dichos datos.</p>
 *
 * @author Mario Batres
 * @version 1.0
 */
@Controller
@Scope(value = WebApplicationContext.SCOPE_SESSION)
@SessionAttributes(value={"estudiante", "asignacionEstudianteCarrera"})
public class ControladorMostrarAsignacionEstudianteCarrera extends ControladorAbstractoAsignacionEstudianteCarrera implements Serializable{
//______________________________________________________________________________

    /**
     * Objeto de tipo {@link Estudiante} que ha sido seleccionado.
     */
    private Estudiante estudiante;
//______________________________________________________________________________
    /**
     * Objeto de tipo {@link AsignacionEstudianteEstudiante} que ha sido
     * seleccionado.
     */
    private AsignacionEstudianteCarrera asignacionEstudianteCarrera;
//______________________________________________________________________________
    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorMostrarAsignacionEstudianteCarrera.class);
//______________________________________________________________________________
    /**
     * <p>Lleva el nombre del titulo para el mensaje en la pagina
     * <code>agregarAsignacionEstudianteCarrera.htm</code></p>
     */
    private static String TITULO_MENSAJE_AGREGAR = "agregarAsignacionEstudianteCarrera.titulo";
//______________________________________________________________________________
    /**
     * <p>Lleva el nombre del titulo para el mensaje en la pagina
     * <code>modificarAsignacionEstudianteCarrera.htm</code></p>
     */
    private static String TITULO_MENSAJE_MODIFICAR = "modificarAsignacionEstudianteCarrera.titulo";
//______________________________________________________________________________

    /**
     * Se ejecuta cuando se hace llamada de tipo <b>GET</b> a la p&aacute;gina
     * <code>mostrarAsignacionEstudianteCarrera.htm</code>. Crea y busca los
     * atributos para mostrar en dicha p&aacute;gina de acuerdo con el id del
     * estudiante en la base de datos.
     *
     * @param modelo Objeto de tipo {@link model}
     * @param idEstudiante Objeto de tipo {@link Integer} que contiene el id del
     * estudiante para buscar sus asignaciones.
     *
     * @return Nombre de la vista a mostrar
     */
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
                new WrapperModificarAsignacionEstudianteCarrera(), false, true,
                new WrapperEquivalenciaPorCarrera(), this.estudiante);



        return "asignacionEstudianteCarrera/mostrarAsignacionEstudianteCarrera";
    }
//______________________________________________________________________________

    /**
     * Este m&eacute;todo se ejecuta cuando se hace una llamada de tipo
     * <b>POST</b> a la p&aacute;gina
     * <code>agregarAsignacionEstudianteCarrera.htm</code>. Se encarga de
     * agregar una nueva asignaci&oacute;n de carrera al estudiante que ha sido
     * seleccionado previamente.
     *
     * @param wrapperAgregarAsignacionEstudianteCarrera Objeto de tipo
     * {@link wrapperAgregarAsignacionEstudianteCarrera}
     * @param bindingResult Objeto de tipo {@link BindingResult}
     * @param modelo Objeto de tipo {@link Model}
     * @param request Objeto de tipo {@link HttpServletRequest}
     *
     * @return Nombre de la vista a mostrar
     */
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
                
                this.agregarAtributosDefault(modelo, wrapperAgregarAsignacionEstudianteCarrera,
                        true, new WrapperModificarAsignacionEstudianteCarrera(), false, true,
                        new WrapperEquivalenciaPorCarrera(), this.estudiante);


            } catch (DataIntegrityViolationException e) {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE_AGREGAR, "dataIntegrityViolationException", false);
                log.error(Mensajes.DATA_INTEGRITY_VIOLATION_EXCEPTION, e);

                this.agregarAtributosDefault(modelo, wrapperAgregarAsignacionEstudianteCarrera,
                        true, new WrapperModificarAsignacionEstudianteCarrera(), false, false,
                        new WrapperEquivalenciaPorCarrera(), this.estudiante);
            } catch (DataAccessException e) {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE_AGREGAR, "dataAccessException", false);
                log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);

                this.agregarAtributosDefault(modelo, wrapperAgregarAsignacionEstudianteCarrera,
                        true, new WrapperModificarAsignacionEstudianteCarrera(), false, false,
                        new WrapperEquivalenciaPorCarrera(), this.estudiante);
            }
        } else {
            this.agregarAtributosDefault(modelo, wrapperAgregarAsignacionEstudianteCarrera,
                    true, new WrapperModificarAsignacionEstudianteCarrera(), false, false,
                    new WrapperEquivalenciaPorCarrera(), this.estudiante);
        }
        return "asignacionEstudianteCarrera/mostrarAsignacionEstudianteCarrera";
    }

    /**
     * Este m&eacute;todo se ejecuta cuando se hace una llamada de tipo
     * <b>POST</b> a la p&aacute;gina
     * <code>modificarAsignacionEstudianteCarrera.htm</code>. Se encarga de
     * modificar una asignaci&oacute;n de carrera al estudiante que ha sido
     * seleccionado previamente.
     *
     * @param modelo Objeto de tipo {@link Model}
     * @param wrapperModificarAsignacionEstudianteCarrera Objeto de tipo
     * {@link WrapperModificarAsignacionEstudianteCarrera}
     * @param bindingResult Objeto de tipo {@link BindingResult}
     * @param request Objeto de tipo {@link HttpServletRequest}
     */
    @RequestMapping(value = "modificarAsignacionEstudianteCarrera.htm", method = RequestMethod.POST)
    public String modificar(Model modelo, @Valid WrapperModificarAsignacionEstudianteCarrera wrapperModificarAsignacionEstudianteCarrera, BindingResult bindingResult, HttpServletRequest request) {

        if (!bindingResult.hasErrors()) {
            try {
                this.asignacionEstudianteCarrera = this.servicioAsignacionEstudianteCarreraImpl
                        .cargarEntidadPorID(AsignacionEstudianteCarrera.class, wrapperModificarAsignacionEstudianteCarrera.getIdAsignacionEstudianteCarrera());

                wrapperModificarAsignacionEstudianteCarrera.quitarWrapper(asignacionEstudianteCarrera);

                this.servicioAsignacionEstudianteCarreraImpl.actualizar(asignacionEstudianteCarrera);

                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE_MODIFICAR, "modificarAsignacionEstudianteCarrera.exito", true);
                log.info(Mensajes.EXITO_ACTUALIZACION + asignacionEstudianteCarrera.toString());


            } catch (DataIntegrityViolationException e) {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE_MODIFICAR, "dataIntegrityViolationException", false);
                log.error(Mensajes.DATA_INTEGRITY_VIOLATION_EXCEPTION, e);

            } catch (DataAccessException e) {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE_MODIFICAR, "dataAccessException", false);
                log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
            } finally {
                this.agregarAtributosDefault(modelo, new WrapperAgregarAsignacionEstudianteCarrera(),
                        false, wrapperModificarAsignacionEstudianteCarrera, true, false,
                        new WrapperEquivalenciaPorCarrera(), this.estudiante);
            }
        } else {
            this.agregarAtributosDefault(modelo, new WrapperAgregarAsignacionEstudianteCarrera(),
                    false, wrapperModificarAsignacionEstudianteCarrera, true, false,
                    new WrapperEquivalenciaPorCarrera(), this.estudiante);
        }

        return "redirect:mostrarAsignacionEstudianteCarrera.htm?idEstudiante="
                + this.estudiante.getIdEstudiante();

    }
}
