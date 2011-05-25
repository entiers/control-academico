/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */
package gt.edu.usac.cats.servicio.impl;

import gt.edu.usac.cats.dominio.AsignacionCursoPensum;
import gt.edu.usac.cats.dominio.Curso;
import gt.edu.usac.cats.dominio.Pensum;
import gt.edu.usac.cats.servicio.ServicioAsignacionCursoPensum;
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
@Service("ServicioAsignacionCursoPensumImpl")
public class ServicioAsignacionCursoPensumImpl extends ServicioGeneralImpl implements ServicioAsignacionCursoPensum {

    @Override
    public List<AsignacionCursoPensum> getListadoAsignacionCursoPensum(Curso curso, Pensum pensum) throws DataAccessException {
        DetachedCriteria criteria = DetachedCriteria.forClass(AsignacionCursoPensum.class);
        criteria.add(Restrictions.and(
                Restrictions.eq("curso", curso),
                Restrictions.eq("pensum", pensum)));

        return this.daoGeneralImpl.find(criteria);
    }

    @Override
    public List<Curso> getListadoCursosPorPensum(Pensum pensum) {

        DetachedCriteria criteria = DetachedCriteria.forClass(Curso.class);
        criteria.add(Restrictions.eq("pensum", pensum));

        return this.daoGeneralImpl.find(criteria);
    }

    @Override
    public List<AsignacionCursoPensum> getListadoCursosOrignalesPorPensumOriginalYEquivalente(
            Pensum pensumOriginal, Pensum pensumEquivalente) {

        StringBuilder builder = new StringBuilder();
        builder.append(" select distinct acp1 from AsignacionCursoPensum as acp ");
        builder.append(" inner join acp.asignacionCursoPensumsForIdCursoPensumOriginal as acp1 ");
        builder.append(" inner join acp1.asignacionCursoPensumsForIdCursoPensumEquivalencia as acp2");
        builder.append(" where acp1.pensum = :pensumOriginal");
        builder.append(" and acp2.pensum = :pensumEquivalente ");

        Query query = this.daoGeneralImpl.getSesion().
                createQuery(builder.toString()).
                setParameter("pensumOriginal", pensumOriginal).
                setParameter("pensumEquivalente", pensumEquivalente);

        return query.list();
    }
}
