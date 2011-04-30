/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cats.controlador.persona;

import gt.edu.usac.cats.dominio.Persona;
import gt.edu.usac.cats.dominio.Usuario;
import gt.edu.usac.cats.dominio.wrapper.WrapperPersona;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.MailException;
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
@RequestMapping(value = "agregarPersona.htm")
public class ControladorAgregarPersona extends ControladorAbstractoPersona {
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
    private static Logger log = Logger.getLogger(ControladorAgregarPersona.class);

//______________________________________________________________________________
    @RequestMapping(method = RequestMethod.GET)
    public String crearFormulario(Model modelo) {
        this.agregarAtributosDefault(modelo);
        return "persona/agregarPersona";
    }

//______________________________________________________________________________


    @RequestMapping(method = RequestMethod.POST)
    public String submit(@Valid WrapperPersona wrapperPersona,
            BindingResult bindingResult, Model modelo, HttpServletRequest request) {

        try {
            if (bindingResult.hasErrors()) {
                return "persona/agregarPersona";
            }
            
            //Validar email unico
            if(super.servicioUsuarioImpl.getUsuarioPorEmail(wrapperPersona.getEmail())!=null){
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "usuario.correoYaExiste", false);
                return "persona/agregarPersona";
            }

            Persona persona = new Persona();
            persona.setUsuario(new Usuario());
            wrapperPersona.quitarWrapper(persona);

            this.servicioPersonaImpl.agregarPersona(persona);

            log.info(Mensajes.EXITO_AGREGAR+ "Persona, Id: " + persona.getIdPersona());

            RequestUtil.crearMensajeRespuesta(request,
                        TITULO_MENSAJE, "agregarPersona.exito", true);

            this.agregarAtributosDefault(modelo);
        } catch (URISyntaxException e) {
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "uriSyntaxException", true);
            log.warn(e.getMessage(), e);

        } catch (IOException e) {            
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "ioException", true);
            log.warn(Mensajes.IO_EXCEPTION, e);

        } catch (MailException e) {            
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "mailException", true);
            log.warn(Mensajes.MAIL_EXCEPTION, e);

        } catch (MessagingException e) {            
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "messaginException", true);
            log.warn(Mensajes.MESSAGING_EXCEPTION, e);

        } catch (DataIntegrityViolationException e) {
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "agregarPersona.dataIntegrityViolationException", false);
            log.warn(Mensajes.DATA_INTEGRITY_VIOLATION_EXCEPTION, e);

        } catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        return "persona/agregarPersona";
    }
//______________________________________________________________________________

    private void agregarAtributosDefault(Model modelo) {
        modelo.addAttribute("wrapperPersona", new WrapperPersona());
    }


}
