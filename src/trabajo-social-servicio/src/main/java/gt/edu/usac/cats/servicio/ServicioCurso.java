/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.servicio;

import gt.edu.usac.cats.dominio.AsignacionCursoPensum;
import gt.edu.usac.cats.dominio.Carrera;
import gt.edu.usac.cats.dominio.Curso;
import gt.edu.usac.cats.dominio.Pensum;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.enums.TipoHorario;
import java.util.List;
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

    /**
     * <p>Este metodo permite la busqueda de los cursos de primer semestre de una
     * carrera en especifico.</p>
     *
     * @param carrera pojo del tipo {@link Carrrera}
     * @return Curso
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    List<AsignacionCursoPensum> getCursoPrimerSemestreXCarrera(Carrera carrera)
            throws DataAccessException;

    /**
     * <p>Este metodo permite la busqueda de los cursos de una
     * carrera en especifico.</p>
     *
     * @param carrera pojo del tipo {@link Carrrera}
     * @return Curso
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    List<Curso> getCurso(Carrera carrera)
            throws DataAccessException;
    
    /**
     * <p>Este metodo permite la busqueda de los cursos de una
     * carrera en especifico, que tenga horario para el tipoHorario 
     * especificado.</p>
     *
     * @param carrera pojo del tipo {@link Carrrera}
     * @param tipoHorario pojo del tipo {@link TipoHorario}
     * @return Curso
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    List<AsignacionCursoPensum> getCursoAsignacion(Pensum pensum,Semestre semestre, TipoHorario tipoHorario)
            throws DataAccessException;


}
