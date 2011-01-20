/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.servicio.impl;

import gt.edu.usac.cats.dominio.Horario;
import gt.edu.usac.cats.servicio.ServicioDetalleAsignacion;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

/**
 *
 * @author prdacesa17
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

}
