/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.servicio.impl;

import gt.edu.usac.trabajosocial.dao.DaoGeneral;
import gt.edu.usac.trabajosocial.dominio.CalendarioActividades;
import gt.edu.usac.trabajosocial.dominio.Semestre;
import gt.edu.usac.trabajosocial.servicio.ServicioCalendarioActividades;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>Contiene la implementación de los metodos que permiten el manejo de la
 * informacion relacionada con el calendario de actividades en la base de datos.</p>
 *
 * @see ServicioCalendarioActividades
 * @author Mario Batres
 * @version 1.0
 */

@Service("servicioCalendarioActividadesImpl")
public class ServicioCalendarioActividadesImpl implements ServicioCalendarioActividades {
//______________________________________________________________________________
    /** Permite el acceso y manipulacion a la base de datos */
    @Resource
    private DaoGeneral daoGeneralImpl;

//______________________________________________________________________________
    /**
     * <p>Este metodo permite agregar la informacion de un calendario de
     * actividades a la base de datos.
     *
     * @param calendarioActividades Pojo del tipo {@link CalendarioActividades}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     * @throws DataIntegrityViolationException Si ocurrio una violacion de
     *         de integridad de datos
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void agregarCalendarioActividades(CalendarioActividades calendarioActividades)
            throws DataIntegrityViolationException, DataAccessException {
        this.daoGeneralImpl.save(calendarioActividades);
    }
//______________________________________________________________________________
    /**
     * <p>Obtiene todos los calendarios de actividades por el semestre</p>
     *
     * @param semestre Pojo del tipo {@link semestre}
     *
     * @throws DataAccessException Si ocurrió un error de acceso a datos
     **/
    @Override
    public List<CalendarioActividades> getCalendarioActividadesPorSemestre(Semestre semestre)
            throws DataAccessException {

        DetachedCriteria criteria = DetachedCriteria.forClass(CalendarioActividades.class);
        criteria.add(Restrictions.eq("semestre", semestre));
        criteria.addOrder(Order.asc("fechaInicio"));
        criteria.addOrder(Order.asc("fechaFin"));

        return this.daoGeneralImpl.find(criteria);
    }
//______________________________________________________________________________
    /**
     * <p>Obtiene el calendario de actividades según el id que poose</p>
     *
     * @param id Identificador del Calendario de Actividades
     *
     * @throws DataAccessException Si oucrrió un error de acesso a datos
     */
    @Override
    public CalendarioActividades getCalendarioActividadesPorID(short id)
        throws DataAccessException{
        DetachedCriteria criteria = DetachedCriteria.forClass(CalendarioActividades.class);
        criteria.add(Restrictions.eq("idCalendarioActividades", id));
        
        return this.daoGeneralImpl.uniqueResult(criteria);
    }

//______________________________________________________________________________
     /**
     * <p>Este metodo permite actualizar la informacion de un calendario de
     * actividades a la base de datos.
     *
     * @param calendarioActividades Pojo del tipo {@link CalendarioActividades}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     * @throws DataIntegrityViolationException Si ocurrio una violacion de
     *         de integridad de datos
     */
    @Override
    public void actualizarCalendarioActividades(CalendarioActividades calendarioActividades)
            throws DataAccessException {
        this.daoGeneralImpl.update(calendarioActividades);
    }



}
