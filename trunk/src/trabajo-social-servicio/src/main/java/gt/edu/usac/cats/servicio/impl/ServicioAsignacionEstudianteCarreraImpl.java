/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */
package gt.edu.usac.cats.servicio.impl;

import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.Carrera;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.HistorialAsignacionEstudianteCarrera;
import gt.edu.usac.cats.servicio.ServicioAsignacionEstudianteCarrera;
import java.util.Calendar;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
@Service("servicioAsignacionEstudianteCarreraImpl")
public class ServicioAsignacionEstudianteCarreraImpl extends ServicioGeneralImpl implements ServicioAsignacionEstudianteCarrera{
//______________________________________________________________________________
    @Override
    public List<AsignacionEstudianteCarrera> getAsignacionEstudianteCarreraPrimerIngreso(Carrera carrera) throws HibernateException {
        Calendar fecha = Calendar.getInstance();
        StringBuilder builder = new StringBuilder();
        builder.append(" select aec from AsignacionEstudianteCarrera as aec ")
               .append(" left join aec.asignacions as asign")
               .append(" where aec.carrera = :carrera")
               .append(" and aec.estudiante.carne like '")
               .append(String.valueOf(fecha.get(Calendar.YEAR)))
               .append("%' and asign.idAsignacion is null");

        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("carrera", carrera);

        return query.list();
    }
//______________________________________________________________________________
    @Override
    public List<AsignacionEstudianteCarrera> getAsignacionEstudianteCarreraPorEstudiante(Estudiante estudiante){
        StringBuilder builder = new StringBuilder();
        builder.append(" select aec from AsignacionEstudianteCarrera aec ")
               .append(" where aec.estudiante =:estudiante ")
               .append(" and exists elements(")
               .append("    aec.carrera.pensums.asignacionCursoPensums )");
        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("estudiante", estudiante);

        return query.list();
    }

    @Override
    public List<AsignacionEstudianteCarrera> getAsignacionEstudianteCarrera(Estudiante estudiante) throws DataAccessException {
        DetachedCriteria criteria = DetachedCriteria.forClass(AsignacionEstudianteCarrera.class);
        criteria.add(Restrictions.eq("estudiante", estudiante));
        return this.daoGeneralImpl.find(criteria);
    }

    @Override
    public List<Carrera> getListadoCarrerasNoAsignadasPorEstudiante(Estudiante estudiante) {
        StringBuilder builder = new StringBuilder();
        builder.append(" select c from Carrera c ")
                .append(" where c.idCarrera not in ( ")
                .append(" select acp.carrera.idCarrera from AsignacionEstudianteCarrera acp ")
                .append(" where acp.estudiante = :estudiante) ");

        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("estudiante", estudiante);

        return query.list();
    }

    @Override
    public void agregarAsignacionEstudianteCarrera(AsignacionEstudianteCarrera asignacionEstudianteCarrera
            , HistorialAsignacionEstudianteCarrera historialAsignacionEstudianteCarrera)
        throws DataIntegrityViolationException, DataAccessException {
      
      
        this.daoGeneralImpl.save(asignacionEstudianteCarrera);

        historialAsignacionEstudianteCarrera.setAsignacionEstudianteCarrera(asignacionEstudianteCarrera);
        this.daoGeneralImpl.save(historialAsignacionEstudianteCarrera);
    }
}
