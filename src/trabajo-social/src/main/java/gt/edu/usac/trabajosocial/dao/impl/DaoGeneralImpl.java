/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.dao.impl;

import gt.edu.usac.trabajosocial.dao.DaoGeneral;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>Implementacion de la interface {@link DaoGeneral} que define los metodos
 * basicos de acceso a la base de datos. Todas las implementaciones de los
 * metodos son operaciones basicas: select, insert, update y delete. Para
 * simplificar mas el codigo de esta clase se hereda de la clase de spring
 * {@link HibernateDaoSupport}, la cual es la que contiene la logica para
 * el manejo de la sesion y la conexion a la base de datos.</p>
 *
 * <p>Debido a que esta clase funciana como un objeto de acceso a datos, se
 * debe de indicarle a spring de esto, esto se realiza por medio de la anotacion
 * {@link Repository}.</p>
 *
 * <p>Como esta es una para el acceso a datos, todas sus operaciones se deben de
 * manejar en transacciones, salvo las operaciones de solo lectura. La
 * configuracion de las transacciones se realiza con la anotacion
 * {@link Transactional}. La configuracion predeterminada de la anotacion es:
 * <ul>
 * <li>El modo de propagación es REQUIRED</li>
 * <li>El flag de sólo-lectura es false</li>
 * <li>El nivel de aislamiento de la transacción es READ_COMMITTED</li>
 * <li>La transacción realiza un rollback sobre cualquier excepcion lanzada</li>
 * </ul>
 * Para los metodos de solo lectura no se agrega la anotacion de transaccion, ya
 * que no es necsario, y levantar una transaccion solo seria consumir recursos
 * innecesariamente.
 * </p>
 *
 * @author Daniel Castillo
 * @version 1.0
 */
@Repository("daoGeneralImpl")
public class DaoGeneralImpl extends HibernateDaoSupport implements DaoGeneral {


    /**
     * <p>Constructor, inicializa el objeto con los parametros de la sesion.
     * La anotacion Autowired le indica a Spring que al momento de instanciar
     * la clase le debe de inyectar el bean de SessionFactory.</p>
     * @param sessionFactory
     */
    @Autowired(required = true)
    public DaoGeneralImpl(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }
//______________________________________________________________________________
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(Object entidad) throws DataAccessException {
        this.getHibernateTemplate().save(entidad);
    }
//______________________________________________________________________________
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void persist(Object entidad) throws DataAccessException {
        this.getHibernateTemplate().persist(entidad);
    }
//______________________________________________________________________________
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(Object entidad) throws DataAccessException {
        this.getHibernateTemplate().update(entidad);
    }
//______________________________________________________________________________
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdate(Object entidad) throws DataAccessException {
        this.getHibernateTemplate().saveOrUpdate(entidad);
    }
//______________________________________________________________________________
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateAll(Collection entidades) throws DataAccessException {
        this.getHibernateTemplate().saveOrUpdateAll(entidades);
    }
//______________________________________________________________________________
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Object entidad) throws DataAccessException {
        this.getHibernateTemplate().delete(entidad);
    }
//______________________________________________________________________________
    @Transactional
    @Override
    public void bulkUpdate(String queryString) throws DataAccessException {
        this.getHibernateTemplate().bulkUpdate(queryString);
    }
//______________________________________________________________________________
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void bulkUpdate(String queryString, Object value) throws DataAccessException {
        this.getHibernateTemplate().bulkUpdate(queryString, value);
    }
//______________________________________________________________________________
    @Override
    public <T> T load(Class<T> claseEntidad, Serializable id) throws DataAccessException {
        final T entidad = (T)getHibernateTemplate().load(claseEntidad, id);
        return entidad;
    }
//______________________________________________________________________________
    @Override
    public <T> List<T> loadAll(Class<T> claseEntidad) throws DataAccessException {
        List<T> entidades = getHibernateTemplate().loadAll(claseEntidad);
        return entidades;
    }
//______________________________________________________________________________
    @Override
    public <T> List<T> find(Class<T> claseEntidad) throws DataAccessException {
        String hql = "from " + claseEntidad.getName();
        final List<T> entidades = getHibernateTemplate().find(hql);
        return entidades;
    }
//______________________________________________________________________________
    @Override
    public <T> List<T> find(String hql) throws DataAccessException {
        final List<T> entidades = getHibernateTemplate().find(hql);
        return entidades;
    }
//______________________________________________________________________________
    @Override
    public <T> List<T> find(String hql, Object valor) throws DataAccessException {
        final List<T> entidades = getHibernateTemplate().find(hql, valor);
        return entidades;
    }
//______________________________________________________________________________
    @Override
    public <T> List<T> find(String hql, Object... valores) throws DataAccessException {
        final List<T> entidades = getHibernateTemplate().find(hql, valores);
        return entidades;
    }
//______________________________________________________________________________
    @Override
    public <T> List<T> find(DetachedCriteria criteria) throws DataAccessException {
        final List<T> entidades = getHibernateTemplate().findByCriteria(criteria);
        return entidades;
    }
//______________________________________________________________________________
    @Override
    public <T> T uniqueResult(String hql, Object valor) throws DataAccessException {
        final T entidad = (T)DataAccessUtils.uniqueResult(getHibernateTemplate().find(hql, valor));
        return entidad;
    }
//______________________________________________________________________________
    @Override
    public <T> T uniqueResult(DetachedCriteria criteria) throws DataAccessException {
        final T entidad = (T)DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
        return entidad;
    }
//______________________________________________________________________________
    @Override
    public Session getSesion() {
        return this.getSession(true);
    }
}
