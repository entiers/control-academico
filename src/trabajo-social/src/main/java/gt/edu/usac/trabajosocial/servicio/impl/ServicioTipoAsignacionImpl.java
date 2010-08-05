/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.trabajosocial.servicio.impl;

import gt.edu.usac.trabajosocial.dao.DaoGeneral;
import gt.edu.usac.trabajosocial.dominio.TipoAsignacion;
import gt.edu.usac.trabajosocial.servicio.ServicioTipoAsignacion;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>Contiene la implementacion de los metodos que permiten el manejo de la
 * informacion relacionada con el Tipo Asignacion en la base de datos.</p>
 *
 * @author Mario Batres
 * @version 1.0
 */
@Service
public class ServicioTipoAsignacionImpl implements ServicioTipoAsignacion {
    /** <p>Permite el acceso y manipulacion a la base de datos.</p> */
    @Resource
    private DaoGeneral daoGeneralImpl;

//______________________________________________________________________________

    /**
     * <p>Este metodo permite agregar la informacion de un tipo de asignacion
     * a la base de datos.</p>
     *
     * @param tipoAsignacion Pojo del tipo {@link TipoAsignacion}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     * @throws DataIntegrityViolationException Si ocurrio una violacion de
     *         de integridad de datos
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void agregarTipoAsignacion(TipoAsignacion tipoAsignacion) throws DataIntegrityViolationException, DataAccessException {
        this.daoGeneralImpl.save(tipoAsignacion);
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo permite actualizar la informacion de un tipo de asignacion
     * en la base de datos.</p>
     *
     * @param tipoAsignacion Pojo del tipo {@link TipoAsignacion}
     * @throws DataAccessException Si ocurrio un error de acceso a datos

     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void actualizarTipoAsignacion(TipoAsignacion tipoAsignacion) throws DataAccessException {
        this.daoGeneralImpl.update(tipoAsignacion);
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo permite eliminar fisicamente la informacion de un tipo de asignacion
     * en la base de datos.</p>
     *
     * @param tipoAsignacion Pojo del tipo {@link TipoAsignacion}
     * @throws DataAccessException Si ocurrio un error de acceso a datos

     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void elimiarTipoAsignacion(TipoAsignacion tipoAsignacion) 
            throws DataAccessException, DataIntegrityViolationException, ConstraintViolationException{
        this.daoGeneralImpl.delete(tipoAsignacion);
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo obtiene todos los tipo de asignacion habilitados
     * en la base de datos.</p>
     *
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    @Override
    public List<TipoAsignacion> buscarTipoAsignacion() throws DataAccessException {
        DetachedCriteria criteria = DetachedCriteria.forClass(TipoAsignacion.class);
        criteria.add(Restrictions.eq("habilitado", true));

        return this.daoGeneralImpl.find(criteria);
    }
    
//______________________________________________________________________________
    /**
     * <p>Este metodo obtiene el tipo de asignacion por su ID</p>
     *
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    @Override
    public TipoAsignacion getTipoAsignacionPorID(short idTipoAsignacion) throws DataAccessException {
        DetachedCriteria criteria = DetachedCriteria.forClass(TipoAsignacion.class);
        criteria.add(Restrictions.eq("idTipoAsignacion", idTipoAsignacion));

        return this.daoGeneralImpl.uniqueResult(criteria);
    }

}
