/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.servicio.impl;

import gt.edu.usac.cats.dominio.AsignacionCatedraticoHorario;
import gt.edu.usac.cats.dominio.Catedratico;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.servicio.ServicioAsignacionCatedraticoHorario;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
@Service("servicioAsignacionCatedraticoHorarioImpl")
public class ServicioAsignacionCatedraticoHorarioImpl extends ServicioGeneralImpl implements ServicioAsignacionCatedraticoHorario {

    @Override
    public List<AsignacionCatedraticoHorario> getAsignacionCatedraticoHorario(Semestre semestre, Catedratico catedratico) throws DataAccessException {

        StringBuilder builder = new StringBuilder();
        builder.append("select aCH from AsignacionCatedraticoHorario aCH ")
               .append("where aCH.horario.semestre =:semestre ")
               .append("and aCH.catedratico =:catedratico");

        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("semestre", semestre);
        query.setParameter("catedratico", catedratico);

        return query.list();

    }

}
