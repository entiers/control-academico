/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cats.servicio.impl;

import gt.edu.usac.cats.dominio.Persona;
import gt.edu.usac.cats.dominio.Usuario;
import gt.edu.usac.cats.dominio.busqueda.DatosBusquedaPersona;
import gt.edu.usac.cats.servicio.ServicioPersona;
import gt.edu.usac.cats.util.EmailSenderVelocity;
import gt.edu.usac.cats.util.GeneradorPassword;
import gt.edu.usac.cats.util.ManejadorSitioPropiedades;
import gt.edu.usac.cats.velocity.FabricaTemplateVelocity;
import gt.edu.usac.cats.velocity.contexto.NuevoUsuario;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Properties;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
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
public class ServicioPersonaImpl extends ServicioGeneralImpl implements ServicioPersona {

    @Resource
    private EmailSenderVelocity emailSenderVelocity;

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
    private void enviarCorreo(Persona persona) throws IOException, MailException, MessagingException, URISyntaxException {


        Usuario usuario = persona.getUsuario();

        NuevoUsuario nuevoUsuario = new NuevoUsuario();
        nuevoUsuario.setNombreUsuario(usuario.getNombreUsuario());
        nuevoUsuario.setNombre(persona.getNombre());
        nuevoUsuario.setPassword(usuario.getPassword());

        emailSenderVelocity.enviarCorreo("Nuevo usuario", persona.getEmail(),
                FabricaTemplateVelocity.NUEVO_USUARIO,
                nuevoUsuario);
        
    }

//______________________________________________________________________________
    /**
     * <p>Metodo que se encarga de buscar a una persona por la expresion regular de su registro de personal</p>
     *
     * @param datosBusquedaPersona Objeto de tipo {@link DatosBusquedaPersona}
     *
     * @throws DataAccessException Se efectua si se puede acceder a la base de datos.
     *
     * @return Listado de elementso de tipo {@link Persona}
     */
    @Override
    public List<Persona> getListadoPersonas(DatosBusquedaPersona datosBusquedaPersona) throws DataAccessException {

        if (datosBusquedaPersona.esValido()) {
            DetachedCriteria criteria = DetachedCriteria.forClass(Persona.class);

            String registroPersonal = datosBusquedaPersona.getRegistroPersonal();
            String nombre = datosBusquedaPersona.getNombre();

            if (!registroPersonal.isEmpty() && nombre.isEmpty()) {
                criteria.add(Restrictions.ilike("registroPersonal", registroPersonal, MatchMode.ANYWHERE));
            } else {
                if (registroPersonal.isEmpty() && !nombre.isEmpty()) {
                    criteria.add(Restrictions.ilike("nombre", nombre, MatchMode.ANYWHERE));
                } else {
                    if (!registroPersonal.isEmpty() && !nombre.isEmpty()) {
                        criteria.add(Restrictions.ilike("registroPersonal", registroPersonal, MatchMode.ANYWHERE));
                        criteria.add(Restrictions.ilike("nombre", nombre, MatchMode.ANYWHERE));
                    }
                }
            }

            criteria.add(Restrictions.eq("habilitado", datosBusquedaPersona.isHabilitado()));
            criteria.addOrder(Order.asc("nombre"));

            return this.daoGeneralImpl.find(criteria);
        }
        return null;
    }
//______________________________________________________________________________

    /**
     * <p>Este metodo se encarga de actualizar la informacion de la persona como su nombre de usuario</>
     *
     * @param persona Objeto de tipo {@link Persona}
     *
     * @throws DataIntegrityViolationException Se efectua la excepcion si hay un nombre de usuario igual en la base de datos.
     * @throws DataAccessException Se efectua si se puede acceder a la base de datos.
     *
     */
    @Override
    public void modificarPersona(Persona persona) throws DataIntegrityViolationException, DataAccessException {


        this.daoGeneralImpl.update(persona.getUsuario());

        this.daoGeneralImpl.update(persona);


    }
}
