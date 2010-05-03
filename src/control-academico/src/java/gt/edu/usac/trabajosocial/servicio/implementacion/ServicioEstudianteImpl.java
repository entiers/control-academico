/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.servicio.implementacion;

import gt.edu.usac.trabajosocial.dao.DaoGeneral;
import gt.edu.usac.trabajosocial.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.trabajosocial.dominio.Carrera;
import gt.edu.usac.trabajosocial.dominio.Estudiante;
import gt.edu.usac.trabajosocial.dominio.Usuario;
import gt.edu.usac.trabajosocial.servicio.ServicioEstudiante;
import java.util.Date;
import javax.annotation.Resource;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Daniel Castillo
 * @version 2.0
 */
@Service("servicioEstudianteImpl")
public class ServicioEstudianteImpl implements ServicioEstudiante {

    /** Permite el acceso y manipulacion a la base de datos */
    @Resource
    private DaoGeneral daoGeneralImpl;


    /**
     * <p>Constructor predeterminado de la clase.</p>
     */
    public ServicioEstudianteImpl(){}
//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de agregar los datos del estudiante a la base
     * de datos. Para agregar la informacion del estudiante el metodo realiza
     * los siguientes pasos:
     * <ul>
     * <li>Crea un {@link Usuario} para el estudiante</li>
     * <li>Almacena la informacion del {@link Estudiante}</li>
     * <li>Y por ultimo
     * realiza la asignacion de la {@link Carrera} al estudiante</li>
     * </ul>
     * Los pasos anteriores se realizan dentro de una transaccion por lo tanto,
     * o se ejecutan todos o no se ejecuta ninguno.</p>
     *
     * @param estudiante Pojo del tipo {@link Estudiante}
     * @param carrera Pojo del tipo {@link Carrera}
     * @throws DataIntegrityViolationException Se dio una violacion de integridad
     *         de los datos
     * @throws DataAccessException Ocurrio un error con el acceso a la base de
     *         datos
     */
    @Transactional(rollbackFor = Exception.class)
    public void agregarEstudiante(Estudiante estudiante, Carrera carrera)
            throws DataIntegrityViolationException, DataAccessException {

        // se genera el usuario para el estudiante
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(estudiante.getCarne());
        usuario.setPassword(estudiante.getCarne());
        usuario.setHabilitado(true);

        // se guarda el usuario en la BD
        this.daoGeneralImpl.save(usuario);

        // se le asigna el usuario al estudiante
        estudiante.setUsuario(usuario);
        estudiante.setRequisitos(false);

        // se guarda el estudiante en la BD
        this.daoGeneralImpl.save(estudiante);

        // se asigna la carrera al estudiante
        this.asignarCarrera(estudiante, carrera);
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo permite actualizar los datos de un estudiante.</p>
     *
     * @param estudiante Pojo del tipo {@link Estudiante}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    @Transactional(rollbackFor = Exception.class)
    public void actualizarEstudiante(Estudiante estudiante)
            throws DataAccessException {

        // se obtiene el usuario del estudiante
//        Usuario usuario = this.getUsuarioEstudiante(estudiante);
//        estudiante.setUsuario(usuario);

        this.daoGeneralImpl.update(estudiante);
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo asigna una {@link Carrera} al {@link Estudiante}. El
     * metodo se encarga de crear el objeto {@link AsignacionEstudianteCarrera}
     * el cual representa la asignacion de carrera y estudiante.</p>
     *
     * @param estudiante Pojo del tipo {@link Estudiante}
     * @param carrera Pojo del tipo {@link Carrera}
     * @throws DataAccessException Ocurrio un error con el acceso a la base de
     *         datos
     */
    @Transactional(rollbackFor = Exception.class)
    public void asignarCarrera(Estudiante estudiante, Carrera carrera)
            throws DataAccessException {

        // se crea la nueva asignacion de carrera del estudiante
        AsignacionEstudianteCarrera asignacionCarrera = new AsignacionEstudianteCarrera();
        asignacionCarrera.setCarrera(carrera);
        asignacionCarrera.setEstudiante(estudiante);
        asignacionCarrera.setFechaInicio(new Date());

        // se guarda la asignacion en la base de datos
        this.daoGeneralImpl.save(asignacionCarrera);
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo permite la busqueda de estudiantes por su numero de
     * carne.</p>
     * @param carne Numero de carne del estudiante
     * @return Estudiante
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    public Estudiante buscarEstudiantePorCarne(String carne)
            throws DataAccessException {

        // se busca el estudiante por el numero de carne
        DetachedCriteria criteria = DetachedCriteria.forClass(Estudiante.class);
        criteria.add(Restrictions.eq("carne", carne));

        // se retorna el estudiante o null sino se encontro
        return this.daoGeneralImpl.uniqueResult(criteria);
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo obtiene el {@link Usuario} del {@link Estudiante}.</p>
     * @param estudiante Pojo del tipo {@link Estudiante}
     * @return Usuario
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    public Usuario getUsuarioEstudiante(Estudiante estudiante)
            throws DataAccessException {

        DetachedCriteria criteria = DetachedCriteria.forClass(Usuario.class);
        DetachedCriteria criteria2 = criteria.createCriteria("estudiantes");
        criteria2.add(Restrictions.eq("idEstudiante", estudiante.getIdEstudiante()));

        return this.daoGeneralImpl.uniqueResult(criteria);
    }
}
