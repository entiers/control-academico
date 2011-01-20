/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.servicio;

import gt.edu.usac.cats.dominio.Curso;
import org.springframework.dao.DataAccessException;

/**
 * <p>Contiene los metodos que permiten el manejo de la informacion relacionada
 * con el curso en la base de datos.</p>
 *
 * @author Mario Batres
 * @version 1.0
 */
public interface ServicioCurso extends ServicioGeneral {

    /**
     * <p>Este metodo permite la busqueda de un curso por su codigo .</p>
     *
     * @param codigo Codigo del curso
     * @return Curso
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    Curso buscarCursoPorCodigo(String codigo)
            throws DataAccessException;
}
