/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.util;

import gt.edu.usac.cats.velocity.FabricaTemplateVelocity;
import gt.edu.usac.cats.velocity.TemplateVelocity;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */

@Service
public class EmailSenderVelocity {

    @Resource
    private EmailSender emailSender;

   
    public void enviarCorreo(String asunto, String destinatario, FabricaTemplateVelocity tipo, Serializable contexto)
            throws IOException, MailException, MessagingException{

        asunto = asunto + " - Escuela de Trabajo Social";
        TemplateVelocity tv = tipo.crear(contexto);
        Writer writer = tv.transformarTemplate();

        emailSender.enviarCorreo(asunto, destinatario, writer.toString());

    }
}
