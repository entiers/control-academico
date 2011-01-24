/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 *
 * @author Daniel Castillo
 * @version 1.0
 */
@Service("emailSender")
public class EmailSender {

    /**
     * <p>Sesion de JavaMail con el servidor de correo electronico.</p>
     */
    @Resource
    private JavaMailSenderImpl javaMailSender;
//______________________________________________________________________________
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
    public void enviarCorreo(String[] datosCorreo) throws MessagingException {

        // se crea el mensaje de correo electronico
        MimeMessage loMimeMsg = this.javaMailSender.createMimeMessage();

        // realiza varias validaciones del mensaje de correo electronico
        MimeMessageHelper loMsgHlpr = new MimeMessageHelper(loMimeMsg);
        loMsgHlpr.setFrom(this.javaMailSender.getUsername());
        loMsgHlpr.setSubject(datosCorreo[0]);
        loMsgHlpr.addTo(datosCorreo[1]);
        loMsgHlpr.setText(datosCorreo[2]);

        // se trata de enviar el mensaje de correo electronico
        this.javaMailSender.send(loMimeMsg);
    }
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
    public void enviarCorreo2()
            throws IOException, MailException, MessagingException {

        // se carga la configuracion
        Properties properties = this.cargarProperties();

        // se crea el objeto que permite el envio de los correos
        JavaMailSenderImpl javaMailSender2 = new JavaMailSenderImpl();
        javaMailSender2.setPassword(properties.getProperty("mail.smtp.password"));
        javaMailSender2.setUsername(properties.getProperty("mail.smtp.user"));
        javaMailSender2.setJavaMailProperties(properties);

        // se crea el mensaje de correo electronico
        MimeMessage loMimeMsg = javaMailSender2.createMimeMessage();

        // realiza varias validaciones del mensaje de correo electronico
        MimeMessageHelper loMsgHlpr = new MimeMessageHelper(loMimeMsg);
        loMsgHlpr.setFrom(javaMailSender2.getUsername());
        loMsgHlpr.setSubject(this.subject);
        loMsgHlpr.addTo(this.destinatario);
        loMsgHlpr.setText(this.mensaje);

        // se trata de enviar el mensaje de correo electronico
        javaMailSender2.send(loMimeMsg);
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
    private Properties cargarProperties() throws IOException {
        // se crea el desencriptador
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("APP_ENCRYPTION_PASSWORD");

        // se crea el archivo de propiedades
        Properties properties = new EncryptableProperties(encryptor);
        properties.load(this.getClass().getResourceAsStream("/gt/edu/usac/cats/util/smtp.properties"));

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
