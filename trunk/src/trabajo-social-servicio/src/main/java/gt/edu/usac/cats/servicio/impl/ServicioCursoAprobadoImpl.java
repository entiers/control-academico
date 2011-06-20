/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.servicio.impl;

import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.Curso;
import gt.edu.usac.cats.dominio.CursoAprobado;
import gt.edu.usac.cats.dominio.Pensum;
import gt.edu.usac.cats.servicio.ServicioCursoAprobado;
import gt.edu.usac.cats.servicio.ServicioPensumEstudianteCarrera;
import java.util.List;
import javax.annotation.Resource;
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
@Service("servicioCursoAprobadoImpl")
public class ServicioCursoAprobadoImpl extends ServicioGeneralImpl implements ServicioCursoAprobado{

    @Resource
    private ServicioPensumEstudianteCarrera servicioPensumEstudianteCarreraImpl;

//______________________________________________________________________________
    @Override
    public boolean esCursoAprobado(AsignacionEstudianteCarrera asignacionEstudianteCarrera, Curso curso, Pensum pensum) throws DataAccessException {
        DetachedCriteria criteria = DetachedCriteria.forClass(CursoAprobado.class);
        criteria.createAlias("asignacion", "a");
        criteria.createAlias("asignacionCursoPensum", "acp");
        criteria.add(Restrictions.eq("acp.curso", curso));
        criteria.add(Restrictions.eq("acp.pensum", pensum));
        criteria.add(Restrictions.eq("a.asignacionEstudianteCarrera", asignacionEstudianteCarrera));

        return !this.daoGeneralImpl.find(criteria).isEmpty();
    }
//______________________________________________________________________________
    @Override
    public List<Curso> getCursoPrerrequisitoPendiente(AsignacionEstudianteCarrera asignacionEstudianteCarrera, Curso curso) throws DataAccessException {

        StringBuilder builder = new StringBuilder();
        builder.append("select pre.curso from AsignacionCursoPensum acp ")
               .append("inner join acp.asignacionCursoPensumsForIdCursoPensumPrerequisito pre ")
               .append("left join pre.curso.cursoAprobados curAp ")
               .append("where acp.curso = :curso " )
               .append("and acp.pensum = :pensum ")
               .append("and curAp is null");

        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("curso", curso);
        query.setParameter("pensum", servicioPensumEstudianteCarreraImpl.
                getPensumEstudianteCarreraValido(asignacionEstudianteCarrera)
                .getPensum());

        return query.list();
    }
//______________________________________________________________________________
    @Override
    public int getCreditosAprobados(AsignacionEstudianteCarrera asignacionEstudianteCarrera){
        StringBuilder builder = new StringBuilder();
        builder.append("select (sum(acp.creditosPracticos) + sum(acp.creditosTeoricos)) as total ")
               .append("from AsignacionCursoPensum acp ")
               .append("inner join acp.curso.cursoAprobados curAp ")
               .append("where curAp.asignacion.asignacionEstudianteCarrera = :aec");

        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("aec", asignacionEstudianteCarrera);

        List result = query.list();
        if(result.isEmpty())
            return 0;
        else if(result.get(0) == null)
            return 0;
        else
            return Integer.parseInt(result.get(0).toString());
    }


}
