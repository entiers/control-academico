/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.servicio.implementacion;

import gt.edu.usac.trabajosocial.dao.DaoGeneral;
import gt.edu.usac.trabajosocial.servicio.ServicioGeneral;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Daniel Castillo
 * @version 1.0
 */
@Service("servicioGeneralImpl")
public class ServicioGeneralImpl implements ServicioGeneral {

    @Resource
    private DaoGeneral daoGeneralImpl;

    /**
     * <p>Constructor vacio, crea una nueva instancia de la clase.</p>
     */
    public ServicioGeneralImpl() {}
//______________________________________________________________________________
    @Transactional(rollbackFor = Exception.class)
    public void agregar(Object entidad) throws DataIntegrityViolationException, DataAccessException {
        this.daoGeneralImpl.save(entidad);
    }
//______________________________________________________________________________
    @Transactional(rollbackFor = Exception.class)
    public void persistir(Object entidad) throws DataIntegrityViolationException, DataAccessException {
        this.daoGeneralImpl.persist(entidad);
    }
//______________________________________________________________________________
    @Transactional(rollbackFor = Exception.class)
    public void actualizar(Object entidad) throws DataIntegrityViolationException, DataAccessException {
        this.daoGeneralImpl.update(entidad);
    }
//______________________________________________________________________________
    @Transactional(rollbackFor = Exception.class)
    public void agregarActualizar(Object entidad) throws DataIntegrityViolationException, DataAccessException {
        this.daoGeneralImpl.saveOrUpdate(entidad);
    }
//______________________________________________________________________________
    @Transactional(rollbackFor = Exception.class)
    public void agregarActualizarTodo(Collection entidades) throws DataIntegrityViolationException, DataAccessException {
        this.daoGeneralImpl.saveOrUpdateAll(entidades);
    }
//______________________________________________________________________________
    @Transactional(rollbackFor = Exception.class)
    public void borrar(Object entidad) throws DataIntegrityViolationException, DataAccessException {
        this.daoGeneralImpl.delete(entidad);
    }
//______________________________________________________________________________
    public <T> T cargarEntidadPorID(Class<T> claseEntidad, Serializable id) throws DataAccessException {
        return this.daoGeneralImpl.load(claseEntidad, id);
    }
//______________________________________________________________________________
    public <T> List<T> cargarEntidades(Class<T> claseEntidad) throws DataAccessException {
        return this.daoGeneralImpl.loadAll(claseEntidad);
    }
//______________________________________________________________________________
    public <T> List<T> listarEntidad(Class<T> claseEntidad) throws DataAccessException {
        return this.daoGeneralImpl.find(claseEntidad);
    }
//______________________________________________________________________________
    public <T> List<T> listarEntidad(Class<T> claseEntidad, boolean ordenAscendente, String columna) throws DataAccessException {
        DetachedCriteria criteria = DetachedCriteria.forClass(claseEntidad);
        if(ordenAscendente)
            criteria.addOrder(Order.asc(columna));
        else
            criteria.addOrder(Order.desc(columna));
        return this.daoGeneralImpl.find(criteria);
    }
}
