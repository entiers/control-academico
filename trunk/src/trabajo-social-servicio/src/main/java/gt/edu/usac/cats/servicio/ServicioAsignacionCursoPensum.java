/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */
package gt.edu.usac.cats.servicio;

import gt.edu.usac.cats.dominio.AsignacionCursoPensum;
import gt.edu.usac.cats.dominio.AsignacionEquivalencia;
import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.Curso;
import gt.edu.usac.cats.dominio.Pensum;
import gt.edu.usac.cats.dominio.PensumEstudianteCarrera;
import java.util.List;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
public interface ServicioAsignacionCursoPensum extends ServicioGeneral {

    /**
     * <p>Devuelve un listado del tipo {@link AsignacionCursoPensum} en base
     * al curso</p>
     * 
     * @param curso pojo del tipo {@link Curso}
     * @throws DataAccessException
     */
    List<AsignacionCursoPensum> getListadoAsignacionCursoPensum(AsignacionCursoPensum asignacionCursoPensum, Pensum pensum)
            throws DataAccessException;

    /***/
    List<Curso> getListadoCursosPorIdPensum(Short idPensum);

    /***/
    List<AsignacionCursoPensum> getListadoCursosOrignalesPorPensumOriginalYEquivalente(
            Pensum pensumOriginal, Pensum pensumEquivalente);

    /***/
    AsignacionCursoPensum getAsignacionPorCursoYPensum(Curso curso, Pensum pensum);

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
    List <AsignacionCursoPensum> getEquivalenciasPorPensums(
            AsignacionEstudianteCarrera asignacionEstudianteCarrera,
            Pensum pensumOriginal,
            Pensum pensumEquivalencia) throws DataAccessException;
    
//______________________________________________________________________________
    /**
     * Este m&eacute;todo se encarga de obtener los cursos de un estudiante que son 
     * equivalente entre dos carreras
     * 
     * @param pecOriginal Objeto de tipo {@link PensumEstudianteCarrera}
     * @param pecEquivalente Objeto de tipo {@link PensumEstudianteCarrera}
     * 
     * @return Listado de objetos de tipo {@link AsignacionCursoPensum} con los cursos
     * con los cuales se pueden hacer equivalencias.
     * 
     * @throws DataAccessException Si ocurri&oacute; un error de acceso a datos
     */
    
    List <AsignacionCursoPensum> getEquivalenciasPorCarreras(
            PensumEstudianteCarrera pecOriginal,
            PensumEstudianteCarrera pecEquivalente) throws DataAccessException;
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
    void realizarEquivalencias(AsignacionEstudianteCarrera asignacionEstudianteCarrera,
            AsignacionEquivalencia asignacionEquivalencia,
            List <AsignacionCursoPensum> listadoEquivalencias) throws DataAccessException;
//______________________________________________________________________________
    /**
     * Metodo que retorna el listado de cursos sin aprobar que se pueden agregar al estudiante
     * con las restricciones correspondientes para que lo tenga aprobado
     * @param asignacionEstudianteCarrera
     * @return
     * @throws DataAccessException 
     */
    List<AsignacionCursoPensum> cursosSinAprobarValidos(AsignacionEstudianteCarrera asignacionEstudianteCarrera)
            throws DataAccessException;
}
