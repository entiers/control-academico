/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cats.controlador.asignacionCursoPensum;

import gt.edu.usac.cats.controlador.pensum.ControladorAbstractoPensum;
import gt.edu.usac.cats.dominio.AsignacionCursoPensum;
import gt.edu.usac.cats.dominio.Pensum;
import gt.edu.usac.cats.dominio.wrapper.WrapperAsignacionCursoPensum;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import gt.edu.usac.cats.enums.ControlReporte;
import java.io.Serializable;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;

/**
 * <p> Este controlador lleva el manejo del todo lo relacionado con la
 * asignaci&oacute;n de cursos a un pensum, agregaci&oacute;n,
 * actualizaci&oacute;n y eliminaci&oacute;n de cursos al pensum; as&iacute;
 * tambi&eacute;n el manejo de prerrequisitos. </p> <p> Se hizo todo en este
 * controlador ya que todo tiene relacionado un pensum que se selecciona en la
 * p&aacute;gina
 * <code>buscarPensum.htm</code>. Si se hubiera hecho en controladores
 * diferentes se tendr&iacute;a que buscar el pensum por cada llamada a esos
 * controladores. </p>
 *
 *
 * @author Mario Batres
 * @version 1.0
 */
@Controller
@Scope(value = WebApplicationContext.SCOPE_SESSION)
@SessionAttributes(value={"pensum", "asignacionCursoPensum"})
public class ControladorAsignacionCursoPensum extends ControladorAbstractoPensum implements Serializable {
//______________________________________________________________________________

    /**
     * <p>Se utiliza para mantener todos los datos del pensum que se encontro en
     * la busqueda.</p>
     */
    private Pensum pensum;
//______________________________________________________________________________
    /**
     * <p>Se utiliza para mantener todos los datos de la asignacion curso pensum
     * que se encontro en la busqueda.</p>
     */
    private AsignacionCursoPensum asignacionCursoPensum;
//______________________________________________________________________________
    /**
     * <p>Matiene una bitaacutecora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorAsignacionCursoPensum.class);

//______________________________________________________________________________
    /**
     * Este m&eacute;todo se activa con la petici&oacute;n GET desde la
     * p&aacute;gina
     * <code>asignarCursoPensum.htm<code>. Que tiene como fin mostrar los cursos
     * asignados al pensum que se est&aacute; seleccionando. Se v&aacute;lida que el id y la existencia del
     * pensum para poder mostrar la p&aacute;gina de lo contrario regresa a la p&aacute;gina
     * <code>buscarPensum.htm</code>
     *
     * @param modelo Objeto de tipo {@link Model}
     * @param idPensum Id del pensum para asignarle un curso.
     *
     * @return El nombre de la vista a mostrar.
     */
    @RequestMapping(value = "asignarCursoPensum.htm", method = RequestMethod.GET)
    public String asignarCursoPensum(Model modelo, Short idPensum) {
        if (!this.validarPensum(idPensum, this.pensum)) {
            return "redirect:buscarPensum.htm";
        }

        this.agregarAtributosDefault(modelo);
        return "pensum/asignarCursoPensum";
    }

//______________________________________________________________________________
    @RequestMapping(value = "asignarCursoPensumPag.htm", method = RequestMethod.GET)
    public String asignarCursoPensumPag(Model modelo) {
        this.validarPensum(this.pensum.getIdPensum(), this.pensum);
        this.agregarAtributosDefault(modelo);
        return "pensum/asignarCursoPensum";
    }

//______________________________________________________________________________
    public void agregarAtributosDefault(Model modelo) {
        //El pensum es asigando el metodo anterior
        modelo.addAttribute("pensum", this.pensum);
        modelo.addAttribute("nombreControlReporte", ControlReporte.PENSUM_ESTUDIO);
    }

//______________________________________________________________________________
    /**
     * Este m&eacute;todo se activa con la petici&oacute;n GET a la
     * p&aacute;gina
     * <code>agregarCursoAPensum.htm</code>. Tienen como objetivo agregar los
     * atributos necesarios para mostrar en la p&aacute;gina para poder agregar
     * un curso al pensum que ha sido seleccionando en la p&aacute;gina
     * <code>buscarPensum.htm</code> y que ha pasado por
     * <code>asignarCursoPensum.htm</code>.
     *
     * @param modelo Objeto de tipo {@link Model}
     *
     * @return El nombre de la vista a mostrar.
     */
    @RequestMapping(value = "agregarCursoAPensum.htm", method = RequestMethod.GET)
    public String agregarCursoAPensum(Model modelo) {

        if (this.pensum == null) {
            return "redirect:buscarPensum.htm";
        }

        this.agregarAtributosDefaultAsignacionCursoPensum(modelo,
                pensum,
                new WrapperAsignacionCursoPensum());
        return "pensum/agregarCursoAPensum";
    }

//______________________________________________________________________________
    /**
     * Este m&eacute;todo se activa con la petici&oacute;n POST a la
     * p&aacute;gina
     * <code>agregarCursoAPensum.htm</code>. Tiene como finalidad agregar un
     * curso al pensum que ha sido seleccionado previamente.
     *
     * @param modelo Objeto de tipo {@link Model}
     * @param wrapperAsignacionCursoPensum Objeto de tipo
     * {@link WrapperAsignacionCursoPensum}
     * @param bindingResult Objeto de tipo {@link BindingResult}
     * @param request Objeto de tipo {@link HttpServletRequest}
     *
     * @return El nombre de la vista a mostrar.
     */
    @RequestMapping(value = "agregarCursoAPensum.htm", method = RequestMethod.POST)
    public String agregarCursoAPensum(Model modelo, @Valid WrapperAsignacionCursoPensum wrapperAsignacionCursoPensum, BindingResult bindingResult, HttpServletRequest request) {

        if (!bindingResult.hasErrors()) {
            try {
                this.asignacionCursoPensum = new AsignacionCursoPensum();
                wrapperAsignacionCursoPensum.setPensum(this.pensum);

                wrapperAsignacionCursoPensum.quitarWrapper(asignacionCursoPensum, true);

                this.servicioPensumImpl.agregar(asignacionCursoPensum);


                wrapperAsignacionCursoPensum = new WrapperAsignacionCursoPensum();
                // se registra el evento
                RequestUtil.crearMensajeRespuesta(request, "agregarCursoAPensum.titulo", "asignarCursoPensum.exito", true);
                log.info(Mensajes.EXITO_AGREGAR + asignacionCursoPensum.toString());


            } catch (DataIntegrityViolationException e) {
                RequestUtil.crearMensajeRespuesta(request, "agregarCursoAPensum.titulo", "dataIntegrityException", false);
                log.error(Mensajes.DATA_INTEGRITY_VIOLATION_EXCEPTION, e);
            } catch (DataAccessException e) {
                // error de acceso a datos
                RequestUtil.crearMensajeRespuesta(request, "agregarCursoAPensum.titulo", "dataAccessException", false);
                log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
            }
        }
        this.agregarAtributosDefaultAsignacionCursoPensum(modelo,
                this.pensum, wrapperAsignacionCursoPensum);
        return "pensum/agregarCursoAPensum";
    }

//______________________________________________________________________________
    /**
     * Este m&eacute;todo se activa con la petici&oacute;n GET a la
     * p&aacute;gina
     * <code>eliminarCursoDePensum.htm</code>. Tiene como fin eliminar una
     * asignaci&oacute;n de un curso al pensum previamente seleccionado. Se
     * valida que la asignaci&oacute;n exista y pertenezca al pensum. .
     *
     * @param modelo Objeto de tipo {@link Model}
     * @param idAsignacionCursoPensum Id de la asignaci&oacute;n del curso con
     * el pensum.
     * @param request Objeto de tipo {@link HttpServletRequest}
     *
     * @return El nombre de la vista a mostrar.
     */
    @RequestMapping(value = "eliminarCursoDePensum.htm", method = RequestMethod.GET)
    public String eliminarCursoDePensum(Model modelo, Short idAsignacionCursoPensum,
            HttpServletRequest request) {
        if (!this.validarAsignacionCursoPensum(idAsignacionCursoPensum, this.asignacionCursoPensum)) {
            return "redirect:buscarPensum.htm";
        }

        try {
            //Evita que al borrar tambien borre los postrrequisitos del curso asignado al pensum
            this.asignacionCursoPensum.getAsignacionCursoPensumsForIdCursoPensum().clear();


            this.servicioPensumImpl.borrar(this.asignacionCursoPensum);

            //Para que muestre la informaci&oacute;n correcta
            RequestUtil.agregarRedirect(request, "asignarCursoPensum.htm?idPensum=" + this.pensum.getIdPensum());
            RequestUtil.crearMensajeRespuesta(request, "eliminarCursoDePensum.titulo", "asignarCursoPensum.exito", true);

            log.info(Mensajes.EXITO_BORRAR + this.asignacionCursoPensum.toString());
        } catch (DataIntegrityViolationException e) {
            RequestUtil.crearMensajeRespuesta(request, "eliminarCursoDePensum.titulo", "dataIntegrityException", false);
            log.error(Mensajes.DATA_INTEGRITY_VIOLATION_EXCEPTION, e);
        } catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, "eliminarCursoDePensum.titulo", "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }
        return "pensum/asignarCursoPensum";
    }

//______________________________________________________________________________
    /**
     * Este m&eacute;todo se activa con la petici&oacute;n GET a la
     * p&aacute;gina
     * <code>eliminarCursoDePensum.htm</code>. Tiene como fin agregar los
     * atributos para mostrar en la p&aacute;gina para que se pueda editar un
     * curso asignado al pensum previsamente seleccionado. Se valida que la
     * asignaci&oacute;n del curso exista y qu&eacute; peterneza cal pensum.
     *
     * @param modelo Objeto de tipo {@link Model}
     * @param idAsignacionCursoPensum Id de la asignaci&oacute;n del curso con
     * el pensum.
     *
     * @return El nombre de la vista a mostrar.
     */
    @RequestMapping(value = "editarCursoDePensum.htm", method = RequestMethod.GET)
    public String editarCursoDePensum(Model modelo, Short idAsignacionCursoPensum) {
        if (!this.validarAsignacionCursoPensum(idAsignacionCursoPensum, this.asignacionCursoPensum)) {
            return "redirect:buscarPensum.htm";
        }

        WrapperAsignacionCursoPensum wrapperAsignacionCursoPensum = new WrapperAsignacionCursoPensum();
        wrapperAsignacionCursoPensum.agregarWrapper(this.asignacionCursoPensum);

        this.agregarAtributosDefaultAsignacionCursoPensum(modelo, this.pensum, wrapperAsignacionCursoPensum);
        return "pensum/editarCursoDePensum";
    }

//______________________________________________________________________________
    /**
     * Este m&eacute;todo se activa con la petici&oacute;n POST a la
     * p&aacute;gina
     * <code>eliminarCursoDePensum.htm</code>. Tiene como fin actualizar un la
     * informaci&oacute;n de la asignaci&oacute;n del curso al pensum que fue
     * seleccionado anteriormente.
     *
     * @param modelo Objeto de tipo {@link Model}
     * @param wrapperAsignacionCursoPensum Objeto de tipo
     * {@link WrapperAsignacionCursoPensum}
     * @param bindingResult Objeto de tipo {@link BindingResult}
     * @param request Objeto de tipo {@link HttpServletRequest}
     *
     * @return El nombre de la vista a mostrar.
     */
    @RequestMapping(value = "editarCursoDePensum.htm", method = RequestMethod.POST)
    public String editarCursoDePensum(Model modelo, @Valid WrapperAsignacionCursoPensum wrapperAsignacionCursoPensum, BindingResult bindingResult, HttpServletRequest request) {

        if (!bindingResult.hasErrors()) {
            try {
                wrapperAsignacionCursoPensum.quitarWrapper(this.asignacionCursoPensum, false);

                this.servicioPensumImpl.actualizar(this.asignacionCursoPensum);

                wrapperAsignacionCursoPensum.agregarWrapper(this.asignacionCursoPensum);
                // se registra el evento
                RequestUtil.crearMensajeRespuesta(request, "editarCursoDePensum.titulo", "editarCursoDePensum.exito", true);
                log.info(Mensajes.EXITO_ACTUALIZACION + this.asignacionCursoPensum.toString());


            } catch (DataIntegrityViolationException e) {
                RequestUtil.crearMensajeRespuesta(request, "editarCursoDePensum.titulo", "dataIntegrityException", false);
                log.error(Mensajes.DATA_INTEGRITY_VIOLATION_EXCEPTION, e);
            } catch (DataAccessException e) {
                // error de acceso a datos
                RequestUtil.crearMensajeRespuesta(request, "editarCursoDePensum.titulo", "dataAccessException", false);
                log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
            }
        }
        this.agregarAtributosDefaultAsignacionCursoPensum(modelo, this.pensum, wrapperAsignacionCursoPensum);
        return "pensum/editarCursoDePensum";
    }

//______________________________________________________________________________
    /**
     * Este m&eacute;todo se activa con la petici&oacute;n GET a la
     * p&aacute;gina
     * <code>administrarPrerrequisitos.htm</code>. Tiene como fin agregar los
     * atributos para poder actualizar los prerrequisitos de un curso en el
     * pensum previamente seleccionado.
     *
     * @param modelo Objeto de tipo {@link Model}
     * @param idAsignacionCursoPensum Id de la asignaci&oacute;n del curso con
     * el pensum.
     *
     * @return El nombre de la vista a mostrar.
     */
    @RequestMapping(value = "administrarPrerrequisitos.htm", method = RequestMethod.GET)
    public String administrarPrerrequisitos(Model modelo, Short idAsignacionCursoPensum) {
        if (!this.validarAsignacionCursoPensum(idAsignacionCursoPensum, this.asignacionCursoPensum)) {
            return "redirect:buscarPensum.htm";
        }

        this.agregarAtributosDefaultAdministracionPrerrequisitos(modelo, this.pensum, this.asignacionCursoPensum);
        return "pensum/administrarPrerrequisitos";
    }

//______________________________________________________________________________
    /**
     * Este m&eacute;todo se activa con la petici&oacute;n POST a la
     * p&aacute;gina
     * <code>administrarPrerrequisitos.htm</code>. Actualiza los prerrequisitos
     * de un curso de un pensum que han sido seleccionado previamente. Se
     * env&iacute;a un arreglo con los ids de los prerrequisitos para luego
     * buscarlos y asignarlos al curso.
     *
     * @param modelo Objeto de tipo {@link Model}
     * @param asignacionCursoPensumsForIdCursoPensumPrerequisito Arreglo de
     * string que contienen los id de los prerrequisitos del curso.
     *
     * @return El nombre de la vista a mostrar.
     */
    @RequestMapping(value = "administrarPrerrequisitos.htm", method = RequestMethod.POST)
    public String administrarPrerrequisitos(Model modelo, String[] asignacionCursoPensumsForIdCursoPensumPrerequisito,
            HttpServletRequest request) {
        try {
            Set<AsignacionCursoPensum> prerrequisitos = this.asignacionCursoPensum.getAsignacionCursoPensumsForIdCursoPensumPrerequisito();
            prerrequisitos.clear();

            if (asignacionCursoPensumsForIdCursoPensumPrerequisito != null) {
                for (String id : asignacionCursoPensumsForIdCursoPensumPrerequisito) {
                    AsignacionCursoPensum acp = this.servicioPensumImpl.cargarEntidadPorID(AsignacionCursoPensum.class, Short.parseShort(id));
                    prerrequisitos.add(acp);
                }
            }

            this.asignacionCursoPensum.setAsignacionCursoPensumsForIdCursoPensumPrerequisito(prerrequisitos);

            this.servicioPensumImpl.actualizar(this.asignacionCursoPensum);

            RequestUtil.crearMensajeRespuesta(request, "administrarPrerrequisitos.titulo", "administrarPrerrequisitos.exito", true);
            log.info(Mensajes.EXITO_ACTUALIZACION + " Administrar Prerrequisitos: " + asignacionCursoPensum.toString());

        } catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, "administrarPrerrequisitos.titulo", "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }


        this.agregarAtributosDefaultAdministracionPrerrequisitos(modelo, this.pensum, this.asignacionCursoPensum);
        return "pensum/administrarPrerrequisitos";
    }

//______________________________________________________________________________
    /**
     * Este metodo se activa al rendereizar la p&aacute;gina
     * <code>administrarPrerrequisitos.htm</code>. Se encarga de convertir los
     * el set de prerrequisitos a cadena de caracteras para poder ser utilizado
     * en el tag
     * <code>form:checkboxex</code>. Sin este m&eacute;todo se ejecuta una
     * excepci&oacute;n que no permite el correcto funcionamiento.
     *
     * @param binde Objeto de tipo {@link WebDataBinder}
     */
    @InitBinder
    public void registerConverters(WebDataBinder binder) {

        if (binder.getConversionService() instanceof GenericConversionService) {
            GenericConversionService conversionService = (GenericConversionService) binder.getConversionService();
            conversionService.addConverter(new Converter<AsignacionCursoPensum, String>() {
                @Override
                public String convert(AsignacionCursoPensum acp) {
                    return String.valueOf(acp.getIdAsignacionCursoPensum());
                }
            });
        }
    }
}
