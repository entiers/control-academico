/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.servicio.impl;

import gt.edu.usac.cats.dao.impl.DaoGeneralImpl;
import gt.edu.usac.cats.dominio.Curso;
import gt.edu.usac.cats.dominio.busqueda.DatosBusquedaEstudiante;
import gt.edu.usac.cats.dominio.AsignacionUsuarioPerfil;
import gt.edu.usac.cats.dominio.Carrera;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.Perfil;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.dominio.Usuario;
import gt.edu.usac.cats.servicio.ServicioEstudiante;
import gt.edu.usac.cats.util.GeneradorPassword;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p></p>
 *
 * @author Daniel Castillo
 * @version 2.0
 */
@Service("servicioEstudianteImpl")
public class ServicioEstudianteImpl extends ServicioGeneralImpl implements ServicioEstudiante {

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
     * @param semestre Pojo del tipo {@link Semestre}
     * @param situacion Enum del tipo {@link situacion}
     * 
     * @throws DataIntegrityViolationException Se dio una violacion de integridad
     *         de los datos
     * @throws DataAccessException Ocurrio un error con el acceso a la base de
     *         datos
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void agregarEstudiante(Estudiante estudiante)
            throws DataIntegrityViolationException, DataAccessException {

        // se genera el password para el usuario del estudiante
        String password = GeneradorPassword.generarPassword();
        estudiante.setPassword(password);

        // se genera el usuario para el estudiante
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(estudiante.getCarne());
        usuario.setPassword(password);
        usuario.setHabilitado(true);

        // se guarda el usuario en la BD
        this.daoGeneralImpl.save(usuario);

        // se asignan los perfiles al usuario
        this.asignarPerfil(usuario);

        // se le asigna el usuario al estudiante
        estudiante.setUsuario(usuario);
        estudiante.setRequisitos(false);

        // se guarda el estudiante en la BD
        this.daoGeneralImpl.save(estudiante);

        // se asigna la carrera al estudiante
        //this.asignarCarrera(estudiante, carrera, semestre, situacion);
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo asigna una {@link Carrera} al {@link Estudiante}. El
     * metodo se encarga de crear el objeto {@link AsignacionEstudianteCarrera}
     * el cual representa la asignacion de carrera y estudiante.</p>
     *
     * @param estudiante Pojo del tipo {@link Estudiante}
     * @param carrera Pojo del tipo {@link Carrera}
     * @param semestre Pojo del tipo {@link Semestre}
     * @param situacion Enum del tipo {@link situacion}
     *
     * @throws DataAccessException Ocurrio un error con el acceso a la base de
     *         datos
     */
    /*@Transactional(rollbackFor = Exception.class)
    @Override
    public void asignarCarrera(Estudiante estudiante, Carrera carrera,
            Semestre semestre, Situacion situacion)
            throws DataAccessException {

        // se crea la nueva asignacion de carrera del estudiante
        AsignacionEstudianteCarrera asignacionCarrera = new AsignacionEstudianteCarrera();
        asignacionCarrera.setCarrera(carrera);
        asignacionCarrera.setEstudiante(estudiante);
        asignacionCarrera.setSemestre(semestre);
        asignacionCarrera.setFechaInscripcion(new Date());
        asignacionCarrera.setSituacion(situacion.getId());
        // se guarda la asignacion en la base de datos
        this.daoGeneralImpl.save(asignacionCarrera);
    }*/
//______________________________________________________________________________
    /**
     * <p>Este metodo realiza la asignacion del perfil ESTUDIANTE a un
     * estudiante. Este perfil contiene todos los permisos/roles para las
     * operaciones un estudiante puede realizar en el sistema. Para realizar la
     * asignacion se debe de enviar el usuario asignado al estudiante.</p>
     *
     * @param estudiante Pojo del tipo {@link Estudiante}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void asignarPerfil(Usuario usuario)
            throws DataAccessException {

        // se obtiene el perfil de estudiante
        DetachedCriteria criteria = DetachedCriteria.forClass(Perfil.class);
        criteria.add(Restrictions.eq("nombre", "ESTUDIANTE"));
        Perfil perfil = this.daoGeneralImpl.uniqueResult(criteria);

        // se crea la asignacion del perfil
        AsignacionUsuarioPerfil asignacion = new AsignacionUsuarioPerfil();
        asignacion.setPerfil(perfil);
        asignacion.setUsuario(usuario);

        // se guarda la asignacion en la base de datos
        this.daoGeneralImpl.save(asignacion);
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de habilitar o deshabilitar el acceso de un
     * {@link Estudiante} al sistema.</p>
     *
     * @param estudiante Pojo del tipo {@link Estudiante}
     * @param habilitar <code>true</code> para habilitar y <code>false</code>
     *        para deshabilitar
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void habilitarEstudiante(Estudiante estudiante, boolean habilitar)
            throws DataAccessException {

        Usuario usuario = this.getUsuarioEstudiante(estudiante);
        usuario.setHabilitado(habilitar);
        this.daoGeneralImpl.update(usuario);
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo permite la busqueda de estudiantes por su numero de
     * carne.</p>
     *
     * @param carne Numero de carne del estudiante
     * @return Estudiante
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    @Override
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
     *
     * @param estudiante Pojo del tipo {@link Estudiante}
     * @return Usuario
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    @Override
    public Usuario getUsuarioEstudiante(Estudiante estudiante)
            throws DataAccessException {

        DetachedCriteria criteria = DetachedCriteria.forClass(Usuario.class);
        DetachedCriteria criteria2 = criteria.createCriteria("estudiantes");
        criteria2.add(Restrictions.eq("idEstudiante", estudiante.getIdEstudiante()));

        return this.daoGeneralImpl.uniqueResult(criteria);
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de crear un listado de estudiantes, el listado
     * se filtra en base a los datos de busqueda y se ordena en base al tipo
     * de orden y columna indicados.</p>
     *
     * @param datos Contiene los filtros para el listado
     * @return List Listado de estudiantes
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    @Override
    public List<Estudiante> getListadoEstudiantes(DatosBusquedaEstudiante datos)
                throws HibernateException {

        // se crea la consulta
        Criteria criteria = this.crearCriteriaBusqueda(datos);

        // se ordena el resultado
        if(datos.isOrdenAscendente())
            criteria.addOrder(Order.asc(datos.getColumnaOrden()));
        else
            criteria.addOrder(Order.desc(datos.getColumnaOrden()));

        // se pagina el resultado
        criteria.setFirstResult(datos.getPrimerRegistro());
        criteria.setMaxResults(DaoGeneralImpl.REGISTROS_MAXIMOS);

        return criteria.list();
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo obtiene la cantidad de registros que retornaria una
     * busqueda hecha en base a los parametros de busqueda enviados por el
     * usuario.</p>
     *
     * @param datos Contiene los filtros para el listado
     * @return Integer Cantidad de registros
     * @throws HibernateException Si ocurrio un error de acceso a datos
     */
    @Override
    public Integer rowCount(DatosBusquedaEstudiante datos)
            throws HibernateException {

        // se crea la consulta
        Criteria criteria = this.crearCriteriaBusqueda(datos);

        criteria.setProjection(Projections.rowCount());

        return ((Long) criteria.list().get(0)).intValue();
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de crear el objeto {@link Criteria} que se usa
     * para realizar las busquedas en base a los parametros de busqueda ingresados
     * por el usuario.</p>
     * @param datos Objeto {@link DatosBusquedaEstudiante} que contiene los
     *        parametros de busqueda ingresados por el usuario
     * @return Criteria
     */
    private Criteria crearCriteriaBusqueda(DatosBusquedaEstudiante datos) {
        // se crea la consulta
        Criteria criteria = this.daoGeneralImpl.getSesion().createCriteria(Estudiante.class);

        // se crean los filtro de la busqueda
        Criterion eqCarne = Restrictions.eq("carne", datos.getCarneBusqueda());
        Criterion eqNombre = Restrictions.ilike("nombre", datos.getNombreBusqueda(), MatchMode.ANYWHERE);
        Criterion eqApellido = Restrictions.ilike("apellido", datos.getApellidoBusqueda(), MatchMode.ANYWHERE);

        // si no se envia el carne se crea un filtro or con nombre y apellido
        if(datos.getCarneBusqueda().isEmpty()) {
            if(!datos.getNombreBusqueda().isEmpty() && !datos.getApellidoBusqueda().isEmpty()) {
                LogicalExpression orExp = Restrictions.or(eqNombre, eqApellido);
                criteria.add(orExp);

            } else if(!datos.getNombreBusqueda().isEmpty() && datos.getApellidoBusqueda().isEmpty())
                criteria.add(eqNombre);

            else if(!datos.getApellidoBusqueda().isEmpty() && datos.getNombreBusqueda().isEmpty())
                criteria.add(eqApellido);

        } else
            criteria.add(eqCarne);

        return criteria;
    }

    @Override
    public boolean tieneCursoAsignadoPrimerIngreso(Estudiante estudiante, Curso curso) throws HibernateException {
        StringBuilder builder = new StringBuilder();

        builder.append(" select asign from Asignacion as asign")
               .append(" inner join asign.detalleAsignacions deta ")
               .append(" where asign.asignacionEstudianteCarrera.estudiante= :estudiante ")
               .append(" and deta.horario.curso = :curso");

        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("estudiante", estudiante);
        query.setParameter("curso", curso);
        
        if(query.list().isEmpty())
            return false;
        else
            return true;
    }
}
