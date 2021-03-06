/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */
package gt.edu.usac.cats.controlador.usuario;

import java.io.IOException;
import javax.mail.MessagingException;
import org.springframework.stereotype.Controller;
import gt.edu.usac.cats.dominio.wrapper.WrapperDatosPersonales;
import gt.edu.usac.cats.dominio.Usuario;
import gt.edu.usac.cats.servicio.ServicioUsuario;
import gt.edu.usac.cats.util.EmailSenderVelocity;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import gt.edu.usac.cats.velocity.FabricaTemplateVelocity;
import java.io.Serializable;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author Carlos Solórzano
 */
@Controller
@RequestMapping(value = "recordarUsuario.htm")
@Scope(value = WebApplicationContext.SCOPE_SESSION)
@SessionAttributes(value = {"usuario"})

public class ControladorRecordarUsuario implements Serializable{

    private static Logger log = Logger.getLogger(ControladorAsignarPerfilUsuario.class);
//______________________________________________________________________________
    private static final String TITULO_MENSAJE = "recordarUsuario.titulo";
//______________________________________________________________________________
    private Usuario usuario;
//______________________________________________________________________________
    @Resource
    private ServicioUsuario servicioUsuarioImpl;
//______________________________________________________________________________
    @Resource
    private EmailSenderVelocity emailSenderVelocity;
//______________________________________________________________________________

    /**
     * @param modelo
     * @param request
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String crearFormulario(Model modelo, HttpServletRequest request) {
        modelo.addAttribute("wrapperDatosPersonales", new WrapperDatosPersonales());
        return "usuario/recordarUsuario";
    }
//______________________________________________________________________________

    /**
     * @param modelo
     * @param request
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public String recordarUsuario(@Valid WrapperDatosPersonales wrapperDatosPersonales, BindingResult bindingResult,
            Model modelo, HttpServletRequest request) throws MessagingException {

        //Validar que no existan errores en el formulario        
        if (bindingResult.hasErrors()) {
            return "usuario/recordarUsuario";
        }

        try {
            //Buscar usuario por email
            this.usuario = this.servicioUsuarioImpl.getUsuarioPorEmail(wrapperDatosPersonales.getEmail());

            //Si el correo no esta registrado en el sistema
            if (this.usuario == null) {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "recordarUsuario.emailNoExiste", false);
                return "usuario/recordarUsuario";
            }
            try {
                //Se envia el correo con el nombre de usuario hacia el correo especificado
                this.enviarEmail(this.usuario, wrapperDatosPersonales.getEmail());
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "recordarUsuario.exito", true);
            } catch (IOException ex) {
                // error en envio de correo electronico
                RequestUtil.crearMensajeRespuesta(request, null, "emailExcepcion", false);
                log.error(Mensajes.MAIL_EXCEPTION, ex);
            }
        } catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, null, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        return "welcome";
    }

    //______________________________________________________________________________
    /**
     * <p>Este metodo permite enviar un correo electronico al usuario que lo solicita
     * desde el formulario de Login del sistema.</p>
     *
     * @param usuario   Usuario que solicita el recordatorio de nombre de usuario
     * @param email     Direccion de correo hacia donde se enviara el correo
     */
    private void enviarEmail(Usuario usuario, String email) throws IOException {
        try {

            // se trata de enviar el correo
            this.emailSenderVelocity.enviarCorreo("Usuario registrado", email,
                    FabricaTemplateVelocity.RECORDATORIO_USUARIO, usuario.getNombreUsuario());

        } catch (MessagingException ex) {
            log.error(Mensajes.MESSAGING_EXCEPTION, ex);

        }
    }
}
