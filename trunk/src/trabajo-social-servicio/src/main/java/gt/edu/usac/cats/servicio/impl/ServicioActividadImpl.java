/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.servicio.impl;

import gt.edu.usac.cats.dominio.CalendarioActividades;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.servicio.ServicioActividad;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * <p>Implementacion de los metodos de la interfaz {@link ServicioActividad}, los
 * cuales permiten el manejo de la informacion relacionada con las actividades.
 * Entre las actividades se encuentran:
 * <ul>
 * <li>Creacion de semestres</li>
 * <li>Manipulacion de calendarios de actividades</li>
 * </ul>
 * </p>
 *
 * @author Mario Batres
 * @version 1.0
 */
@Service("servicioActividadImpl")
public class ServicioActividadImpl extends ServicioGeneralImpl implements ServicioActividad {

    /**
     * <p>Constructor predeterminado de la clase.</p>
     */
    public ServicioActividadImpl() {}
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
}
