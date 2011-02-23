/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */
package gt.edu.usac.cats.controlador.usuario;

import gt.edu.usac.cats.dominio.Usuario;
import gt.edu.usac.cats.dominio.wrapper.WrapperRecordarContrasenia;
import gt.edu.usac.cats.servicio.ServicioUsuario;
import gt.edu.usac.cats.util.EmailSender;
import gt.edu.usac.cats.util.ManejadorSitioPropiedades;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import nl.captcha.Captcha;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;
import org.springframework.dao.DataAccessException;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author Carlos Solorzano
 */
@Controller
@RequestMapping(value = "recordarContrasenia.htm")
public class ControladorRecordarContrasenia {
    private static Logger log = Logger.getLogger(ControladorRecordarContrasenia.class);
//______________________________________________________________________________
    private static final String TITULO_MENSAJE = "recordarContrasenia.titulo";
//______________________________________________________________________________
    private Usuario usuario;
//______________________________________________________________________________
    @Resource
    private ServicioUsuario servicioUsuarioImpl;
//______________________________________________________________________________
    @Resource
    private EmailSender emailSender;
//______________________________________________________________________________
    /**
     * @param modelo
     * @param request
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String crearFormulario(Model modelo,HttpServletRequest request) {
        modelo.addAttribute("wrapperRecordarContrasenia",new WrapperRecordarContrasenia());
        return "usuario/recordarContrasenia";
    }

//______________________________________________________________________________

    /**
     * @param modelo
     * @param request
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public String recordarContrasenia(@Valid WrapperRecordarContrasenia wrapperRecordarContrasenia, BindingResult bindingResult,
                        Model modelo, HttpServletRequest request) throws MessagingException, URISyntaxException {

        //Obtener captcha generado
        Captcha captcha = (Captcha) request.getSession().getAttribute(Captcha.NAME);

        //Validar que el formulario no tenga errores
        if(bindingResult.hasErrors())
            return "usuario/recordarContrasenia";

        //Validar captcha
        if(!captcha.isCorrect(wrapperRecordarContrasenia.getCaptcha())){
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "recordarContrasenia.errorCaptcha",false);
            return "usuario/recordarContrasenia";
        }

        try{
            //Buscar usuario por email
            this.usuario = this.servicioUsuarioImpl.getUsuarioPorEmail(wrapperRecordarContrasenia.getEmail());

            //Si el correo no esta registrado en el sistema
            if (this.usuario==null){
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "recordarUsuario.emailNoExiste",false);
                return "usuario/recordarUsuario";
            }
            try {
                //Se crea el codigo de validacion para el reinicio de contrasenia
                this.servicioUsuarioImpl.setCodigoVerficadorReinicioContrasenia(this.usuario);
                
                //Se envia el correo con el nombre de usuario hacia el correo especificado
                this.enviarEmail(this.usuario, wrapperRecordarContrasenia.getEmail());
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "recordarContrasenia.exito", true);
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
    private void enviarEmail(Usuario usuario, String email) throws IOException, URISyntaxException {
        //Se carga la configuracion para el url del sitio
        Properties properties = ManejadorSitioPropiedades.leer();
        
        String subject = "Reinicio de contraseña (Escuela de Trabajo Social)";
        String mensaje = "Estimado usuario, \n\n"+
                "Se ha solicitado generar una nueva contraseña para el usuario registrado en el sistema de la Escuela de Trabajo Social. " +
                "Para esto se ha generado un código de validación para que pueda modificar su contraseña. Utilice la siguiente información:\n\n"+
                "Ingrese a este enlace: " + properties.getProperty("urlHTTPS") + "reiCont.htm?idUsuario=" + this.usuario.getIdUsuario() + "\n" +
                "Código de validación: " + usuario.getCodigoValidacion();
        try {
            
            // se trata de enviar el correo
            this.emailSender.enviarCorreo(subject, email, mensaje);

        } catch (MessagingException ex) {
            log.error(Mensajes.MESSAGING_EXCEPTION, ex);

        }
    }



}


