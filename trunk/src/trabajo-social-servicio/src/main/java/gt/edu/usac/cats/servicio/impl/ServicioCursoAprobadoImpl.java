/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */
package gt.edu.usac.cats.servicio.impl;

import gt.edu.usac.cats.dominio.Asignacion;
import gt.edu.usac.cats.dominio.AsignacionCursoPensum;
import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.Curso;
import gt.edu.usac.cats.dominio.CursoAprobado;
import gt.edu.usac.cats.dominio.Pensum;
import gt.edu.usac.cats.dominio.wrapper.WrapperCursoAprobado;
import gt.edu.usac.cats.servicio.ServicioCursoAprobado;
import gt.edu.usac.cats.servicio.ServicioPensumEstudianteCarrera;
import java.util.List;
import java.util.UUID;
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
public class ServicioCursoAprobadoImpl extends ServicioGeneralImpl implements ServicioCursoAprobado {

    @Resource
    private ServicioPensumEstudianteCarrera servicioPensumEstudianteCarreraImpl;

//______________________________________________________________________________
    @Override
    public boolean esCursoAprobado(AsignacionEstudianteCarrera asignacionEstudianteCarrera, Curso curso, Pensum pensum) throws DataAccessException {
        DetachedCriteria criteria = DetachedCriteria.forClass(AsignacionCursoPensum.class, "acp");
        criteria.createAlias("acp.cursoAprobados", "ca");
        criteria.createAlias("ca.asignacion", "a");        
        criteria.add(Restrictions.eq("acp.curso", curso));
        criteria.add(Restrictions.eq("acp.pensum", pensum));
        criteria.add(Restrictions.eq("a.asignacionEstudianteCarrera", asignacionEstudianteCarrera));

        return !this.daoGeneralImpl.find(criteria).isEmpty();
    }
//______________________________________________________________________________

    @Override
    public List<Curso> getCursoPrerrequisitoPendiente(AsignacionEstudianteCarrera asignacionEstudianteCarrera, AsignacionCursoPensum asignacionCursoPensum) throws DataAccessException {


        Pensum pensumValido = servicioPensumEstudianteCarreraImpl.
                getPensumEstudianteCarreraValido(asignacionEstudianteCarrera).
                getPensum();

        StringBuilder builder = new StringBuilder();
        builder.append("\n select p2.curso from AsignacionCursoPensum as acp2").
                append("\n inner join acp2.asignacionCursoPensumsForIdCursoPensumPrerequisito as p2").
                append("\n where ").
                append("\n p2 not in (").
                append("\n select p from AsignacionCursoPensum as acp ").
                append("\n inner join acp.asignacionCursoPensumsForIdCursoPensumPrerequisito as p").
                append("\n inner join p.cursoAprobados as ca").
                append("\n inner join ca.asignacion as a").   
                append("\n where a.asignacionEstudianteCarrera = :asignacionEstudianteCarrera) and ").
                append("\n acp2 = :asignacionCursoPensum and acp2.pensum = :pensum");
        
        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString()).
                setParameter("asignacionEstudianteCarrera", asignacionEstudianteCarrera).
                setParameter("asignacionCursoPensum", asignacionCursoPensum).
                setParameter("pensum", pensumValido);
        
        return query.list();
    }
//______________________________________________________________________________

    @Override
    public int getCreditosAprobados(AsignacionEstudianteCarrera asignacionEstudianteCarrera) {
        StringBuilder builder = new StringBuilder();
        
        Pensum pensumValido = servicioPensumEstudianteCarreraImpl.
                getPensumEstudianteCarreraValido(asignacionEstudianteCarrera).
                getPensum();


        builder.append("\n select coalesce((sum(acp.creditosPracticos) + sum(acp.creditosTeoricos)), 0) as total ").
                append("\n from Asignacion a").
                append("\n inner join a.cursoAprobados ca ").
                append("\n inner join ca.asignacionCursoPensum as acp ").                
                append("\n where acp.pensum = :pensum and ").
                append("\n a.asignacionEstudianteCarrera = :asignacionEstudianteCarrera");


        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString()).
                setParameter("pensum", pensumValido).
                setParameter("asignacionEstudianteCarrera", asignacionEstudianteCarrera);


        List result = query.list();
        if (result.isEmpty()) {
            return 0;
        } else if (result.get(0) == null) {
            return 0;
        } else {
            return Integer.parseInt(result.get(0).toString());
        }
    }
//______________________________________________________________________________
    @Override
    public CursoAprobado getCursoAprobado(Asignacion asignacion, AsignacionCursoPensum asignacionCursoPensum) throws DataAccessException {
        DetachedCriteria criteria = DetachedCriteria.forClass(CursoAprobado.class);
        criteria.add(
                Restrictions.and(
                    Restrictions.eq("asignacion", asignacion), 
                    Restrictions.eq("asignacionCursoPensum", asignacionCursoPensum)
                )
        );        
        return this.daoGeneralImpl.uniqueResult(criteria);
    }
//______________________________________________________________________________
    @Override
    public void agregarCursoAprobado(AsignacionEstudianteCarrera asignacionEstudianteCarrera, 
            AsignacionCursoPensum asignacionCursoPensum,
            WrapperCursoAprobado wrapperCursoAprobado) throws DataAccessException {        
        Asignacion asignacion = new Asignacion();
        asignacion.setTransaccion(UUID.randomUUID().toString());
        asignacion.setAsignacionEstudianteCarrera(asignacionEstudianteCarrera);
        asignacion.setTipoAsignacion(wrapperCursoAprobado.getTipoAsignacion());
        this.agregar(asignacion);
        
        CursoAprobado cursoAprobado = new CursoAprobado();
        wrapperCursoAprobado.quitarWrapper(cursoAprobado);
        cursoAprobado.setAsignacionCursoPensum(asignacionCursoPensum);
        cursoAprobado.setAsignacion(asignacion);  
        cursoAprobado.setIngresoManual(true);
        this.agregar(cursoAprobado);
    }
//______________________________________________________________________________
    @Override
    public List<CursoAprobado> listaCursoAprobadoModificable(AsignacionEstudianteCarrera asignacionEstudianteCarrera) throws DataAccessException {
        DetachedCriteria cursoAprobado = DetachedCriteria.forClass(CursoAprobado.class);
        DetachedCriteria asignacion = cursoAprobado.createCriteria("asignacion");
        
        asignacion.add(Restrictions.eq("asignacionEstudianteCarrera", asignacionEstudianteCarrera));        
        cursoAprobado.add(Restrictions.eq("ingresoManual", true));
        
        return this.daoGeneralImpl.find(cursoAprobado);
    }
}
