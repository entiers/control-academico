/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.servicio.impl;

import gt.edu.usac.cats.dominio.AsignacionCursoPensum;
import gt.edu.usac.cats.dominio.Carrera;
import gt.edu.usac.cats.dominio.Curso;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.enums.TipoHorario;
import gt.edu.usac.cats.servicio.ServicioCurso;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * <p>Contiene la implementacion de los metodos que permiten el manejo de la
 * informacion relacionada con el curso en la base de datos.</p>
 *
 * @see ServicioCurso
 * @author Mario Batres
 * @version 1.0
 */
@Service("servicioCursoImpl")
public class ServicioCursoImpl extends ServicioGeneralImpl implements ServicioCurso {

     /**
     * <p>Constructor predeterminado de la clase.</p>
     */
    public ServicioCursoImpl() {}
//______________________________________________________________________________
    /**
     * <p>Este metodo permite la busqueda de curso por su código.</p>
     *
     * @param codigo Código del curso
     * @return Curso
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    @Override
    public Curso buscarCursoPorCodigo(String codigo) 
            throws DataAccessException {

        // se busca el estudiante por el numero de carne
        DetachedCriteria criteria = DetachedCriteria.forClass(Curso.class);
        criteria.add(Restrictions.eq("codigo", codigo));

        // se retorna el estudiante o null sino se encontro
        return this.daoGeneralImpl.uniqueResult(criteria);
    }

    @Override
    public List<AsignacionCursoPensum> getCursoPrimerSemestreXCarrera(Carrera carrera) throws DataAccessException {
        StringBuilder builder = new StringBuilder();

        builder.append(" select acp from AsignacionCursoPensum as acp ")
               .append(" where acp.pensum.carrera = :carrera ")
               .append("   and acp.numeroSemestre = 1")
               .append("   and acp.pensum.estado = 1 ");

        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("carrera", carrera);

        return query.list();
    }

    @Override
    public List<Curso> getCurso(Carrera carrera) throws DataAccessException {
        StringBuilder builder = new StringBuilder();

        builder.append("select acp.curso from AsignacionCursoPensum acp ")
               .append("where acp.pensum.carrera = :carrera ");

        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("carrera", carrera);

        return query.list();
    }

    @Override
    public List<AsignacionCursoPensum> getCursoAsignacion(Carrera carrera, Semestre semestre, TipoHorario tipoHorario) throws DataAccessException {
         StringBuilder builder = new StringBuilder();

        builder.append("select distinct horario.asignacionCursoPensum from Horario as horario ")
               .append("inner join horario.curso.asignacionCursoPensums aCP ")
               .append("where horario.semestre = :semestre ")
               .append("and aCP.pensum.carrera = :carrera ")
               .append("and horario.tipo = :tipoHorario ")
               .append("and horario.salon.capacidad > ( ")
               .append(" select count(*) from DetalleAsignacion det")
               .append(" where det.horario = horario")
               .append(")");

        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("carrera", carrera);
        query.setParameter("tipoHorario", tipoHorario);
        query.setParameter("semestre", semestre);


        return query.list();
    }

}
