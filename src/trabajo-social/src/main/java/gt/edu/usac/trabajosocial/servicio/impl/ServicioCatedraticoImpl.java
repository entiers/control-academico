/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.servicio.impl;

import gt.edu.usac.trabajosocial.dominio.busqueda.DatosBusquedaCatedratico;
import gt.edu.usac.trabajosocial.dao.DaoGeneral;
import gt.edu.usac.trabajosocial.dominio.AsignacionCatedraticoEscuela;
import gt.edu.usac.trabajosocial.dominio.Catedratico;
import gt.edu.usac.trabajosocial.dominio.Escuela;
import gt.edu.usac.trabajosocial.dominio.Usuario;
import gt.edu.usac.trabajosocial.servicio.ServicioCatedratico;
import gt.edu.usac.trabajosocial.util.BotonesPaginacion;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
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
 *
 * @author Daniel Castillo
 * @version 1.0
 */
@Service("servicioCatedraticoImpl")
public class ServicioCatedraticoImpl implements ServicioCatedratico {

    /** Permite el acceso y manipulacion a la base de datos */
    @Resource
    private DaoGeneral daoGeneralImpl;
//______________________________________________________________________________
    /**
     * <p>Constructor predeterminado de la clase.</p>
     */
    public ServicioCatedraticoImpl(){}
//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de agregar los datos del catedratico a la base
     * de datos. Para agregar la informacion del catedratico el metodo realiza
     * los siguientes pasos:
     * <ul>
     * <li>Crea un {@link Usuario} para el catedratico</li>
     * <li>Almacena la informacion del {@link Catedratico}</li>
     * <li>Y por ultimo realiza la asignacion de la {@link Escuela} al
     * catedratico</li>
     * </ul>
     * Los pasos anteriores se realizan dentro de una transaccion por lo tanto,
     * o se ejecutan todos o no se ejecuta ninguno.</p>
     *
     * @param catedratico Pojo del tipo {@link Catedratico}
     * @param escuela Pojo del tipo {@link Escuela}
     * @throws DataIntegrityViolationException Se dio una violacion de integridad
     *         de los datos
     * @throws DataAccessException Ocurrio un error con el acceso a la base de
     *         datos
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void agregarCatedratico(Catedratico catedratico, Escuela escuela)
            throws DataIntegrityViolationException, DataAccessException {

        // se genera el usuario para el estudiante
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(catedratico.getCodigo());
        usuario.setPassword(catedratico.getCodigo());
        usuario.setHabilitado(true);

        // se guarda el usuario en la BD
        this.daoGeneralImpl.save(usuario);

        // se le asigna el usuario al catedratico
        catedratico.setUsuario(usuario);

        // se guarda el estudiante en la BD
        this.daoGeneralImpl.save(catedratico);

        // se asigna la carrera al estudiante
        this.asignarEscuela(catedratico, escuela);
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo permite actualizar los datos de un catedratico.</p>
     *
     * @param catedratico Pojo del tipo {@link Catedratico}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void actualizarCatedratico(Catedratico catedratico)
            throws DataAccessException {

        this.daoGeneralImpl.update(catedratico);
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo asigna una {@link Escuela} al {@link Catedratico}. El
     * metodo se encarga de crear el objeto {@link AsignacionCatedraticoEscuela}
     * el cual representa la asignacion de escuela y catedratico.</p>
     *
     * @param catedratico Pojo del tipo {@link Catedratico}
     * @param escuela Pojo del tipo {@link Escuela}
     * @throws DataAccessException Ocurrio un error con el acceso a la base de
     *         datos
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void asignarEscuela(Catedratico catedratico, Escuela escuela)
            throws DataAccessException {

        // se crea la nueva asignacion de carrera del estudiante
        AsignacionCatedraticoEscuela asignacionEscuela = new AsignacionCatedraticoEscuela();
        asignacionEscuela.setEscuela(escuela);
        asignacionEscuela.setCatedratico(catedratico);
        asignacionEscuela.setFechaInicio(new Date());

        // se guarda la asignacion en la base de datos
        this.daoGeneralImpl.save(asignacionEscuela);
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo permite la busqueda de catedraticos por su codigo de
     * personal.</p>
     *
     * @param codigo Numero de personal del catedratico
     * @return Catedratico
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    @Override
    public Catedratico buscarCatedraticoPorCodigo(String codigo)
            throws DataAccessException {

        // se busca el catedratico por el numero de carne
        DetachedCriteria criteria = DetachedCriteria.forClass(Catedratico.class);
        criteria.add(Restrictions.eq("codigo", codigo));

        // se retorna el catedratico o null sino se encontro
        return this.daoGeneralImpl.uniqueResult(criteria);
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de crear un listado de catedraticos, el listado
     * se filtra en base a los datos de busqueda y se ordena en base al tipo
     * de orden y columna indicados.</p>
     *
     * @param datos Contiene los filtros para el listado
     * @return List Listado de estudiantes
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    @Override
    public List<Catedratico> getListadoCatedraticos(DatosBusquedaCatedratico datos)
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
        criteria.setMaxResults(BotonesPaginacion.REGISTROS_MAXIMOS);

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
    public Integer rowCount(DatosBusquedaCatedratico datos)
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
     * @param datos Objeto {@link DatosBusquedaCatedratico} que contiene los
     *        parametros de busqueda ingresados por el usuario
     * @return Criteria
     */
    private Criteria crearCriteriaBusqueda(DatosBusquedaCatedratico datos) {
        // se crea la consulta
        Criteria criteria = this.daoGeneralImpl.getSesion().createCriteria(Catedratico.class);

        // se crean los filtro de la busqueda
        Criterion eqCodigo = Restrictions.eq("codigo", datos.getCodigoBusqueda());
        Criterion eqNombre = Restrictions.ilike("nombre", datos.getNombreBusqueda(), MatchMode.ANYWHERE);
        Criterion eqApellido = Restrictions.ilike("apellido", datos.getApellidoBusqueda(), MatchMode.ANYWHERE);

        // si no se envia el codigo se crea un filtro or con nombre y apellido
        if(datos.getCodigoBusqueda().isEmpty()) {
            if(!datos.getNombreBusqueda().isEmpty() && !datos.getApellidoBusqueda().isEmpty()) {
                LogicalExpression orExp = Restrictions.or(eqNombre, eqApellido);
                criteria.add(orExp);

            } else if(!datos.getNombreBusqueda().isEmpty() && datos.getApellidoBusqueda().isEmpty())
                criteria.add(eqNombre);

            else if(!datos.getApellidoBusqueda().isEmpty() && datos.getNombreBusqueda().isEmpty())
                criteria.add(eqApellido);

        } else
            criteria.add(eqCodigo);

        return criteria;
    }
}
