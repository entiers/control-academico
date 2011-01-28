/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.servicio.impl;

import gt.edu.usac.cats.dominio.CalendarioActividades;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.enums.TipoActividad;
import gt.edu.usac.cats.servicio.ServicioCalendarioActividades;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * <p>Implementacion de los metodos de la interfaz {@link ServicioCalendarioActividades}, los
 * cuales permiten el manejo de la informacion relacionada con las actividades. 
 * </p>
 *
 * @author Mario Batres
 * @version 1.0
 */
@Service("servicioActividadImpl")
public class ServicioCalendarioActividadesImpl extends ServicioGeneralImpl implements ServicioCalendarioActividades {

    /**
     * <p>Constructor predeterminado de la clase.</p>
     */
    public ServicioCalendarioActividadesImpl() {}
//______________________________________________________________________________
    /**
     * <p>Obtiene todos los calendarios de actividades por el semestre.</p>
     *
     * @param semestre Pojo del tipo {@link semestre}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
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
     *
     * <p>Verifica si existe un tipo de actividad en un semestre.  Segun la reglas del negocio
     * se utiliza este metodo si el calendario de actividades tiene un tipo de actividad,
     * de lo contrario no se debe utilizar.</p>
     *
     * @param tipoActividad Objeto enum {@link TipoActividad}
     * @param semestre Objeto {@link Semestre}
     *
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     *
     * @return
     *  <p>
     *      <ul>
     *          <li><b>True</b> - Si existe un tipo de actividad por el semestre enviado</li>
     *          <li><b>False</b> - Si no existe un tipo de actividad por el semestre enviado</li>
     *      </ul>
     * </p>
     */
    @Override
    public boolean existeTipoActividadPorSemestre(TipoActividad tipoActividad, Semestre semestre) throws DataAccessException {
        DetachedCriteria criteria = DetachedCriteria.forClass(CalendarioActividades.class);

        criteria.add(Restrictions.eq("tipoActividad", tipoActividad));
        criteria.add(Restrictions.eq("semestre", semestre));
        criteria.setProjection(Projections.rowCount());

        Long count = (Long) this.daoGeneralImpl.uniqueResult(criteria);

        if(count.longValue() > 0){
            return true;
        }
        return false;
    }



}
