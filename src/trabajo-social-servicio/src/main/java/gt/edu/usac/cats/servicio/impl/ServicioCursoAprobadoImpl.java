/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.servicio.impl;

import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.Curso;
import gt.edu.usac.cats.dominio.CursoAprobado;
import gt.edu.usac.cats.servicio.ServicioCursoAprobado;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Carlos Sol�rzano
 */
@Service("servicioCursoAprobadoImpl")
public class ServicioCursoAprobadoImpl extends ServicioGeneralImpl implements ServicioCursoAprobado{
//______________________________________________________________________________
    @Override
    public boolean esCursoAprobado(AsignacionEstudianteCarrera asignacionEstudianteCarrera, Curso curso) throws DataAccessException {
        StringBuilder builder = new StringBuilder();
        builder.append("select curAp from CursoAprobado curAp ")
               .append("where curAp.curso = :curso")
               .append("and curAp.asignacion.asignacionEstudianteCarrera = :asignacionEstudianteCarrera");

        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("curso", curso);
        query.setParameter("asignacionEstudianteCarrera", asignacionEstudianteCarrera);

        return !query.list().isEmpty();
    }
//______________________________________________________________________________
    @Override
    public List<Curso> getCursoPrerrequisitoPendiente(AsignacionEstudianteCarrera asignacionEstudianteCarrera, Curso curso) throws DataAccessException {

        StringBuilder builder = new StringBuilder();
        builder.append("select pre.curso from AsignacionCursoPensum acp ")
               .append("inner join acp.asignacionCursoPensumsForIdCursoPensumPrerequisito pre ")
               .append("left join pre.curso.cursoAprobados curAp ")
               .append("where acp.curso = :curso " )
               .append("and acp.pensum.carrera = :carrera ")
               .append("and curAp is null");

        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("curso", curso);
        query.setParameter("carrera", asignacionEstudianteCarrera.getCarrera());

        return query.list();
    }
//______________________________________________________________________________
    @Override
    public int getCreditosAprobados(AsignacionEstudianteCarrera asignacionEstudianteCarrera){
        StringBuilder builder = new StringBuilder();
        builder.append("select sum(acp.creditosPracticos) + sum(acp.creditosTeoricos) total")
               .append("from AsignacionCursoPensum acp ")
               .append("inner join acp.curso.cursoAprobados curAp ")
               .append("where curAp.asignacion.asignacionEstudianteCarrera = :aec");

        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("aec", asignacionEstudianteCarrera);

        List result = query.list();
        if(result.isEmpty())
            return 0;
        else
            return Integer.parseInt(result.get(0).toString());
    }


}
