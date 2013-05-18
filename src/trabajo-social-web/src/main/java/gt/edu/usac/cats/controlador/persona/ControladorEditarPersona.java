/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cats.controlador.persona;

import gt.edu.usac.cats.dominio.Persona;
import gt.edu.usac.cats.dominio.wrapper.WrapperPersona;
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
 *
 * @author Mario Batres
 * @version 1.0
 */
@Controller
@RequestMapping(value = "editarPersona.htm")
@Scope(value = WebApplicationContext.SCOPE_SESSION)
@SessionAttributes(value = {"persona"})
public class ControladorEditarPersona extends ControladorAbstractoPersona implements Serializable{
//______________________________________________________________________________
    /**
     * <p>
     * Lleva el nombre del titulo para el mensaje en la pagina
     * <p>
     */
    private static final String TITULO_MENSAJE = "agregarPersona.titulo";
//______________________________________________________________________________
    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorEditarPersona.class);
//______________________________________________________________________________
    /**
     * <p>Se utiliza para mantener todos los datos de la persona que se
     * encontro en la busqueda.</p>
     */
    private Persona persona;

//______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina <code>editarPersona.htm</code>. El metodo se encarga
     * de iniciar los objetos que se usaran en la pagina.</p>
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @param idPersona Id de la persona a mostrar.
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(method = RequestMethod.GET)
    public String crearFormulario(Model modelo, Short idPersona) {

        if (idPersona == null) {
            return "redirect:editarPersona.htm";
        }

        this.persona = this.servicioPersonaImpl.cargarEntidadPorID(Persona.class, idPersona);

        if (this.persona == null) {
            return "redirect:editarPersona.htm";
        }

        WrapperPersona wrapperPersona = new WrapperPersona();
        wrapperPersona.agregarWrapper(persona);

        modelo.addAttribute("wrapperPersona", wrapperPersona);

        return "persona/editarPersona";
    }

//______________________________________________________________________________    
    /**
     * <p>Este metodo se ejecuta cuando se presiona el boton de editar de la
     * pagina <code>editarPersona</code>. El metodo se encarga de
     * actualizar la informacion de la persona que se obtuvo en
     * la busqueda. El metodo realiza las siguiente acciones:
     * <ul>
     * <li>Realiza las validaciones de los datos del formulario</li>
     * <li>Delega la funcion de actualizacion a {@link ServicioCalendarioActividades}</li>
     * <li>Muestra un mensaje popup con el resultado de la operacion</li>
     * </ul></p>
     *
     * @param modelo Objeto {@link Model} que contiene los objeto de la pagina
     * @param wrapperPersona Pojo del tipo {@link WrapperPersona}
     * @param bindingResult Ojeto {@link BindingResult} que realiza las validaciones
     * @param request Objeto {@link HttpServletRequest}
     * @return String
     */
    @RequestMapping(method = RequestMethod.POST)
    public String submit(Model modelo, @Valid WrapperPersona wrapperPersona, BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return "persona/editarPersona";
        }

        try {

            //Validando correo unico por usuario
            if(!this.persona.getEmail().equals(wrapperPersona.getEmail())){
                if(this.servicioUsuarioImpl.getUsuarioPorEmail(wrapperPersona.getEmail())!=null){
                    RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "usuario.correoYaExiste", false);
                    return "persona/editarPersona";
                }
            }

            wrapperPersona.quitarWrapper(this.persona);

            this.servicioPersonaImpl.modificarPersona(persona);

            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "editarPersona.exito", true);
            RequestUtil.agregarRedirect(request, "buscarPersona.htm");

            log.info(Mensajes.EXITO_ACTUALIZACION + "Persona, Id: " + this.persona.getIdPersona());


        } catch (DataIntegrityViolationException e) {
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "agregarPersona.dataIntegrityViolationException", false);
            log.warn(Mensajes.DATA_INTEGRITY_VIOLATION_EXCEPTION, e);

        } catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        return "persona/editarPersona";

    }
}
