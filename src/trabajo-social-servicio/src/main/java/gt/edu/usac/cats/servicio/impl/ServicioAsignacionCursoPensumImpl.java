/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */
package gt.edu.usac.cats.servicio.impl;

import gt.edu.usac.cats.dominio.Asignacion;
import gt.edu.usac.cats.dominio.AsignacionCursoPensum;
import gt.edu.usac.cats.dominio.AsignacionEquivalencia;
import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.Curso;
import gt.edu.usac.cats.dominio.CursoAprobado;
import gt.edu.usac.cats.dominio.Pensum;
import gt.edu.usac.cats.enums.TipoAsignacion;
import gt.edu.usac.cats.servicio.ServicioAsignacionCursoPensum;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
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
    public List<Curso> getListadoCursosPorIdPensum(Short idPensum) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Curso.class);
        criteria.createCriteria("asignacionCursoPensums", "acp");
        criteria.add(Restrictions.eq("acp.pensum.idPensum", idPensum));
        criteria.addOrder(Order.asc("codigo"));

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

    @Override
    public AsignacionCursoPensum getAsignacionPorCursoYPensum(Curso curso, Pensum pensum) {
        DetachedCriteria criteria = DetachedCriteria.forClass(AsignacionCursoPensum.class);
        criteria.add(Restrictions.eq("curso", curso));
        criteria.add(Restrictions.eq("pensum", pensum));
        return this.daoGeneralImpl.uniqueResult(criteria);
    }

//______________________________________________________________________________
    /**
     * Este m&eacute;todo se encarga de obtener los cursos de un estudiante que se pueden
     * hacer equivalencias entre dos pensums de una misma carrera.
     *
     * @param asignacionEstudianteCarrera Objeto de tipo {@link AsignacionEstudianteCarrera}
     * @param pensumOriginal Objeto de tipo {@link Pensum}
     * @param pensumEquivalencia Objeto de tipo {@link Pensum}
     *
     * @return Listado de objetos de tipo {@link AsignacionCursoPensum} con los cursos
     * con los cuales se pueden hacer equivalencias.
     *
     * @throws DataAccessException Si ocurri&oacute; un error de acceso a datos
     */

    @Override
    public List<AsignacionCursoPensum> getEquivalenciasPorPensums(
            AsignacionEstudianteCarrera asignacionEstudianteCarrera,
            Pensum pensumOriginal,
            Pensum pensumEquivalencia) throws DataAccessException {


        StringBuilder builder = new StringBuilder();

        builder.append("\n select distinct cpe from Asignacion as a").
                append("\n inner join a.cursoAprobados as ca").
                append("\n inner join ca.asignacionCursoPensum as acp").
                append("\n inner join acp.asignacionCursoPensumsForIdCursoPensumEquivalencia cpe").
                append("\n where acp.pensum = :pensumOriginal and cpe.pensum = :pensumEquivalencia and").
                append("\n a.asignacionEstudianteCarrera = :asignacionEstudianteCarrera");

        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString()).
                setParameter("asignacionEstudianteCarrera", asignacionEstudianteCarrera).
                setParameter("pensumOriginal", pensumOriginal).
                setParameter("pensumEquivalencia", pensumEquivalencia);


        return query.list();
    }
//______________________________________________________________________________
    /**
     * Realiza el proceso de asignaci&oacute; de cursos por equivalencias.
     *
     * @param asignacionEstudianteCarrera Objeto de tipo {@link AsignacionEstudianteCarrera}
     * @param asignacionEquivalencia Objeto de tipo {@link AsignacionEquivalencia}
     * @param listadoEquivalencias Listado de objetos de tipo {@link AsignacionCursoPensum}
     *
     * @throws DataAccessException Si ocurri&oacute; un error de acceso a datos
     */

    @Override
    public void realizarEquivalencias(AsignacionEstudianteCarrera asignacionEstudianteCarrera,
            AsignacionEquivalencia asignacionEquivalencia,
            List<AsignacionCursoPensum> listadoEquivalencias) throws DataAccessException {

        List entidades = new ArrayList();
        entidades.add(asignacionEquivalencia);

        Asignacion asignacion = new Asignacion();
        asignacion.setTipoAsignacion(TipoAsignacion.ASIGNACION_EQUIVALENCIA);
        asignacion.setFecha(new Date());
        asignacion.setAsignacionEquivalencia(asignacionEquivalencia);
        asignacion.setAsignacionEstudianteCarrera(asignacionEstudianteCarrera);

        entidades.add(asignacion);

        for(Iterator <AsignacionCursoPensum> it = listadoEquivalencias.iterator(); it.hasNext();){
            AsignacionCursoPensum asignacionCursoPensum = it.next();

            CursoAprobado cursoAprobado = new CursoAprobado();
            cursoAprobado.setAsignacion(asignacion);
            cursoAprobado.setAsignacionCursoPensum(asignacionCursoPensum);

            entidades.add(cursoAprobado);
        }

        CursoAprobado cursoAprobado = new CursoAprobado();
        cursoAprobado.setAsignacion(asignacion);

        this.daoGeneralImpl.saveOrUpdateAll(entidades);

        
    }
}
