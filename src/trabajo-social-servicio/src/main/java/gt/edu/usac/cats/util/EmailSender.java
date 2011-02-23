/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.util;


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

/**
 *
 * @author Mario Batres
 * @version 2.0
 */
@Service("emailSender")
public class EmailSender {

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
    public void enviarCorreo(String asunto, String destinatario, String mensaje)
            throws IOException, MailException, MessagingException {

        // se carga la configuracion
        Properties properties = this.cargarProperties();

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
        loMsgHlpr.setSubject(asunto);
        loMsgHlpr.addTo(destinatario);
        loMsgHlpr.setText(mensaje, true);

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
    private Properties cargarProperties() throws IOException {
        // se crea el desencriptador
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("APP_ENCRYPTION_PASSWORD");

        // se crea el archivo de propiedades
        Properties properties = new EncryptableProperties(encryptor);
        properties.load(this.getClass().getResourceAsStream("/gt/edu/usac/cats/util/smtp.properties"));

        return properties;
    }
}
