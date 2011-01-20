/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.servicio.impl;

import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.Carrera;
import gt.edu.usac.cats.servicio.ServicioAsignacionEstudianteCarrera;
import java.util.Calendar;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

/**
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
@Service("servicioAsignacionEstudianteCarreraImpl")
public class ServicioAsignacionEstudianteCarreraImpl extends ServicioGeneralImpl implements ServicioAsignacionEstudianteCarrera{

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

}
