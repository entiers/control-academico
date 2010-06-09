/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.servicio;

import gt.edu.usac.trabajosocial.dominio.Curso;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
public interface ServicioCurso {

    /**
     * <p>Este metodo permite agregar la informacion de un curso a la base
     * de datos.
     *
     * @param curso Pojo del tipo {@link curso}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     * @throws DataIntegrityViolationException Si ocurrio una violacion de
     *         de integridad de datos
     */
    void agregarCurso(Curso curso)
        throws DataIntegrityViolationException, DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Este metodo permite actualizar los datos del curso.</p>
     *
     * @param curso Pojo del tipo {@link Curso}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    void actualizarCurso(Curso curso)
            throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Este metodo permite la busqueda de un curso por su codigo .</p>
     *
     * @param codigo Codigo del curso
     * @return Curso
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    Curso buscarCursoPorCodigo(String codigo)
            throws DataAccessException;

//______________________________________________________________________________
    /**
     * <p>Este metodo permite obtener todos los cursos disponibles</p>
     *
     * @return List de tipo {@link Curso}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    List <Curso> getCursos() throws DataAccessException;

}
