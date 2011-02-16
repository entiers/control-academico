/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.servicio.impl;

import gt.edu.usac.cats.dominio.Asignacion;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.enums.TipoAsignacion;
import gt.edu.usac.cats.servicio.ServicioAsignacion;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * <p></p>
 *
 * @author Mario Batres
 * @version 1.0
 */
@Service("servicioAsignacionImpl")
public class ServicioAsignacionImpl extends ServicioGeneralImpl implements ServicioAsignacion {

    /**
     * <p>Constructor predeterminado de la clase.</p>
     */
    public ServicioAsignacionImpl() {}
//______________________________________________________________________________
    /**
     * <p>Este metodo obtiene todos los tipo de asignacion habilitados
     * en la base de datos.</p>
     *
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    @Override
    public List<TipoAsignacion> buscarTipoAsignacionHabilitado()
            throws DataAccessException {

        DetachedCriteria criteria = DetachedCriteria.forClass(TipoAsignacion.class);
        criteria.add(Restrictions.eq("habilitado", true));

        return this.daoGeneralImpl.find(criteria);
    }

    @Override
    public List<Asignacion> buscarAsignacionPorEstudiante(Estudiante estudiante, 
                                                          TipoAsignacion tipoAsignacion,
                                                          Integer anio) throws DataAccessException {
        StringBuilder sql = new StringBuilder();
        sql.append("select asig from Asignacion as asig ")
           .append("where asig.asignacionEstudianteCarrera.estudiante = :estudiante ")
           .append("and asig.tipoAsignacion = :tipoAsignacion ")
           .append("and year(asig.fecha)=:anio");

        Query query = this.daoGeneralImpl.getSesion().createQuery(sql.toString());
        query.setParameter("estudiante", estudiante);
        query.setParameter("tipoAsignacion",tipoAsignacion);
        query.setParameter("anio", anio);

        return query.list();
    }
}
