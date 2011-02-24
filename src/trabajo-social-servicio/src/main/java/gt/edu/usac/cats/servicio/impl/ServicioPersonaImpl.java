/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.servicio.impl;

import gt.edu.usac.cats.dominio.Persona;
import gt.edu.usac.cats.dominio.Usuario;
import gt.edu.usac.cats.servicio.ServicioPersona;
import gt.edu.usac.cats.util.EmailSender;
import gt.edu.usac.cats.util.GeneradorPassword;
import gt.edu.usac.cats.util.ManejadorSitioPropiedades;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

/**
 * <p>Contiene la implementacion de los metodos que permiten el manejo de la
 * informacion relacionada con la perosona en la base de datos.</p>
 *
 * @see ServicioPersona
 * @author Mario Batres
 * @version 1.0
 */
@Service
public class ServicioPersonaImpl extends ServicioGeneralImpl implements ServicioPersona{

    @Resource
    private EmailSender emailSender;

    /**
     * <p>Agrega una persona al sistema, tiene una funcionalidad diferente por eso no
     * se utiliza el metodo del {link @ServicioGeneral}.</p>
     *
     * <p>La funcionalidad es la siguiente:
     * <ul>
     *  <li>Se agrega el usuario, la restricciones de unicidad en la base de datos puede
     * causar una restriccion de nombre de usuario duplicado.</li>
     *  <li>Se agrega la persona</li>
     *  <li>Se envia el correo al usuario indicandole que ha sido registrado en el sistema
     * indicandole su usuario y password para poder acceder al sistema.</li>     * 
     * </ul>
     * </p>
     *
     * @param persona Objeto de tipo {@link Persona}
     *
     * @throws DataIntegrityViolationException Se efectua la excepcion si hay un nombre de usuario igual en la base de datos.
     * @throws DataAccessException Se efectua si se puede acceder a la base de datos.
     * @throws IOException Se efectua si hay un problema en acceder al archivo de configuracion del STMP para envia el correo.
     * @throws MailException Se efectua si no se ha podido enviar el email.
     * @throws MessagingException Se efectua si hay algun problema en el mensaje del email.
     * @throws URISyntaxException Se efectua si existe un problema en la lectura del path del archivo de configuracion en el objeto {@link ManejadorSitioPropiedades}
     */
    @Override
    public void agregarPersona(Persona persona) throws DataIntegrityViolationException, DataAccessException, IOException, MailException, MessagingException, URISyntaxException {
        Usuario usuario = persona.getUsuario();
        usuario.setHabilitado(true);
        usuario.setPassword(GeneradorPassword.generarPassword());

        this.agregar(usuario);
        this.agregar(persona);

        this.enviarCorreo(persona);
    }


    /**
     * Env�a el correo al usuario indicandole su nombre de usuario y password a
     * donde puede acceder.
     *
     * @param persona Objeto de tipo {@link Persona}
     *
     * @throws IOException Se efectua si hay un problema en acceder al archivo de configuraci�n del STMP para env�a el correo.
     * @throws MailException Se efectua si no se ha podido enviar el email.
     * @throws MessagingException Se efectua si hay alg�n problema en el mensaje del email.
     * @throws URISyntaxException Se efectua si existe un problema en la lectura del path del archivo de configuraci�n en el objeto {@link ManejadorSitioPropiedades}
     *
     */
    private void enviarCorreo(Persona persona) throws IOException, MailException, MessagingException, URISyntaxException{


        String asunto = "ESCUELA DE TRABAJO SOCIAL - NUEVO USUARIO";
        StringBuilder mensaje = new StringBuilder();

        mensaje.append("<p>Se ha a�adido al sistema de la Escuela de Trabajo Social.  Las credenciales son las siguientes:</p> ");

        mensaje.append("<p><ul><li>Nombre de usuario: <b>").append(persona.getUsuario().getNombreUsuario());
        mensaje.append("</b></li><li>Password: <b>").append(persona.getUsuario().getPassword()).append("</b></li></ul></p>");

        Properties properties = ManejadorSitioPropiedades.leer();

        mensaje.append("<p>Para ir al acceder al sistema ");
        mensaje.append("<a href=\"").append(properties.getProperty("url")).append("\">");
        mensaje.append("Pulse aqu�</a></p>");

        this.emailSender.enviarCorreo(asunto, persona.getEmail(), mensaje.toString());
    }

}
