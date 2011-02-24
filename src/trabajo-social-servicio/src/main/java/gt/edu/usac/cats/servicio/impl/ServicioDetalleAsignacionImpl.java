/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.servicio.impl;

import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.Curso;
import gt.edu.usac.cats.dominio.Horario;
import gt.edu.usac.cats.dominio.DetalleAsignacion;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.servicio.ServicioDetalleAsignacion;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

/**
 * <p></p>
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
@Service("servicioDetalleAsignacionImpl")
public class ServicioDetalleAsignacionImpl extends ServicioGeneralImpl implements ServicioDetalleAsignacion{

    @Override
    public Integer getCountDetalleAsignacionXHorario(Horario horario) throws HibernateException {
        StringBuilder builder = new StringBuilder();
        builder.append("select count(dta) from DetalleAsignacion as dta")
               .append(" where dta.horario = :horario");

        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("horario", horario);
        
        return Integer.valueOf(query.list().get(0).toString());
    }

    @Override
    public List<DetalleAsignacion> getListadoDetalleAsignacion(Integer idHorario) throws HibernateException {
        StringBuilder builder = new StringBuilder();

        builder.append(" select deta from DetalleAsignacion deta ")
               .append(" where deta.horario.idHorario = :idHorario");

        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("idHorario", idHorario);

        return query.list();
    }
    
    @Override
    public void cambioCierreSeccion(Horario horario,
                                    List lstIdDetalleAsignacion) throws HibernateException{
        DetalleAsignacion detAsign;
        for(Object idDetAsign: lstIdDetalleAsignacion){
            detAsign = this.cargarEntidadPorID(DetalleAsignacion.class, Integer.valueOf(idDetAsign.toString()));
            detAsign.setHorario(horario);
            this.actualizar(detAsign);
        }
    }

    @Override
    public List<DetalleAsignacion> getListadoDetalleAsignacion(Curso curso, Semestre semestre, AsignacionEstudianteCarrera asignacionEstudianteCarrera) throws HibernateException {
        StringBuilder builder = new StringBuilder();
        builder.append("select det from DetalleAsignacion det ")
               .append("where det.horario.curso = :curso ")
               .append("and det.horario.semestre= :semestre ")
               .append("and det.asignacion.asignacionEstudianteCarrera = :asignacionEstudianteCarrera");

        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("curso", curso);
        query.setParameter("semestre", semestre);
        query.setParameter("asignacionEstudianteCarrera", asignacionEstudianteCarrera);

        return query.list();
    }


}
