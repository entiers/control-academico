/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author Daniel Castillo
 * @version 1.0
 */
@Service("emailSender")
public class EmailSender {

    /**
     * <p>Direccion de correo electronico del destinatario.</p>
     */
    private String destinatario;
//______________________________________________________________________________
    /**
     * <p>Titulo del correo electronico (subject).</p>
     */
    private String subject;
//______________________________________________________________________________
    /**
     * <p>Cuerpo del correo eletronico.</p>
     */
    private String mensaje;
//______________________________________________________________________________
    /**
     * <p>Constructor de la clase.</p>
     */
    public EmailSender() {}
//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de enviar correos electronicos. El metodo
     * sigue los siguientes pasos:
     * <ul>
     * <li>Se carga la configuracion de JavaMail desde el archivo de
     * propiedades</li>
     * <li>Se crea un objeto {@link JavaMailSenderImpl} que es el encargado
     * de hacer el envio de correos</li>
     * <li>Se crea un objeto {@link MimeMessage} que es el correo en si</li>
     * <li>Se envia el correo</li>
     * </ul>
     * </p>
     *
     * @throws IOException Si no se pudo leer el archivo de propiedades
     * @throws MailException Si no se pudo enviar el correo
     * @throws MessagingException Si no se pudo crear el correo
     */
    public void enviarCorreo()
            throws IOException, MailException, MessagingException {

        // se carga la configuracion
        Properties properties = this.leerArchivoProperties();

        // se crea el objeto que permite el envio de los correos
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setPassword(properties.getProperty("mail.smtp.password"));
        javaMailSender.setUsername(properties.getProperty("mail.smtp.user"));
        javaMailSender.setJavaMailProperties(properties);

        // se crea el mensaje de correo electronico
        MimeMessage loMimeMsg = javaMailSender.createMimeMessage();

        // realiza varias validaciones del mensaje de correo electronico
        MimeMessageHelper loMsgHlpr = new MimeMessageHelper(loMimeMsg);
        loMsgHlpr.setFrom(javaMailSender.getUsername());
        loMsgHlpr.setSubject(this.subject);
        loMsgHlpr.addTo(this.destinatario);
        loMsgHlpr.setText(this.mensaje);

        // se trata de enviar el mensaje de correo electronico
        javaMailSender.send(loMimeMsg);
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de leer el archivo de propiedades que contiene
     * la configuracion de JavaMail para el envio de correos electronicos.</p>
     *
     * @param request Obejto HttpServletRequest
     * @return Properties Contiene la configuracion para JavaMail
     * @throws IOException Si no se pudo leer el archivo de propiedades
     */
    private Properties leerArchivoProperties() throws IOException {
        // se obtiene el contexto de Spring
        WebApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();

        // se obtiene la path absoluto del archivo de propiedades
        String nombreArchivo = applicationContext.getServletContext().getRealPath("/WEB-INF/smtp.properties");

        // se crea el desencriptador
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("APP_ENCRYPTION_PASSWORD");

        // se crea el archivo de propiedades
        Properties properties = new EncryptableProperties(encryptor);
        properties.load(new FileInputStream(nombreArchivo));

        return properties;
    }
//______________________________________________________________________________
    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }
//______________________________________________________________________________
    public void setSubject(String subject) {
        this.subject = subject;
    }
//______________________________________________________________________________
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
