/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */
package gt.edu.usac.cats.servicio;

import gt.edu.usac.cats.dominio.AsignacionCursoPensum;
import gt.edu.usac.cats.dominio.Curso;
import gt.edu.usac.cats.dominio.Pensum;
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
    List<AsignacionCursoPensum> getListadoAsignacionCursoPensum(Curso curso, Pensum pensum)
            throws DataAccessException;

    /***/
    List<Curso> getListadoCursosPorPensum(Pensum pensum);

    /***/
    List<AsignacionCursoPensum> getListadoCursosOrignalesPorPensumOriginalYEquivalente(
            Pensum pensumOriginal, Pensum pensumEquivalente);
}
