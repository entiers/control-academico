/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.servicio;

import gt.edu.usac.cats.dominio.Persona;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.mail.MessagingException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.MailException;

/**
 * <p>Contiene los metodos que permiten el manejo de la informacion relacionada
 * con la persona en la base de datos.</p>
 * 
 * @author Mario Batres
 * @version 1.0
 */
public interface ServicioPersona extends ServicioGeneral {

    /**
     * <p>Agrega una persona al sistema, tiene una funcionalidad diferente por eso no
     * se utiliza el m�todo del {link @ServicioGeneral}.</p>
     *
     * <p>La funcionalidad es la siguiente:
     * <ul>
     *  <li>Se agrega el usuario, la restricciones de unicidad en la base de datos puede
     * causar una restricci�n de nombre de usuario duplicado.</li>
     *  <li>Se agrega la persona</li>
     *  <li>Se env�a el correo al usuario indicandole que ha sido registrado en el sistema
     * indicandole su usuario y password para poder acceder al sistema.</li>     *
     * </ul>
     * </p>
     *
     * @param persona Objeto de tipo {@link Persona}
     *
     * @throws DataIntegrityViolationException Se efectua la excepci�n si hay un nombre de usuario igual en la base de datos.
     * @throws DataAccessException Se efectua si se puede acceder a la base de datos.
     * @throws IOException Se efectua si hay un problema en acceder al archivo de configuraci�n del STMP para env�a el correo.
     * @throws MailException Se efectua si no se ha podido enviar el email.
     * @throws MessagingException Se efectua si hay alg�n problema en el mensaje del email.
     * @throws URISyntaxException Se efectua si existe un problema en la lectura del path del archivo de configuraci�n en el objeto {@link ManejadorSitioPropiedades}
     */

    void agregarPersona(Persona persona) throws DataIntegrityViolationException, DataAccessException,
            IOException, MailException, MessagingException, URISyntaxException;
}
