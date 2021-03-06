/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */


package gt.edu.usac.cats.servicio;

import gt.edu.usac.cats.dominio.Asignacion;
import gt.edu.usac.cats.dominio.AsignacionCursoPensum;
import java.util.List;

import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.Curso;
import gt.edu.usac.cats.dominio.CursoAprobado;
import gt.edu.usac.cats.dominio.Pensum;
import gt.edu.usac.cats.dominio.wrapper.WrapperCursoAprobado;
import org.springframework.dao.DataAccessException;

/**
 * <p>Contiene los metodos que permiten el manejo de la informacion relacionada
 * con el curso_aprobado en la base de datos.</p>
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
public interface ServicioCursoAprobado extends ServicioGeneral{
//______________________________________________________________________________
     /**
     * <p>Este metodo valida que existe un curso aprobado.</p>
     *
     * @param curso pojo del tipo {@link Curso}
     * @return boolean
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    boolean esCursoAprobado(AsignacionEstudianteCarrera asignacionEstudianteCarrera, Curso curso, Pensum pensum)
            throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Este determina si un curso tiene o no cursos de prerrequisito pendientes.</p>
     *
     * @param curso pojo del tipo {@link Curso}
     * @param curso asignacionEstudianteCarrera del tipo {@link AsignacionEstudianteCarrera}
     * @return boolean
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    List<Curso> getCursoPrerrequisitoPendiente(AsignacionEstudianteCarrera asignacionEstudianteCarrera, AsignacionCursoPensum asignacionCursoPensum)
            throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Devuelve el total de creditos aprobados por un estudiante en una carrera determinada.</p>
     *
     * @param curso asignacionEstudianteCarrera del tipo {@link AsignacionEstudianteCarrera}
     * @return boolean
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    int getCreditosAprobados(AsignacionEstudianteCarrera asignacionEstudianteCarrera)
            throws DataAccessException;    
//______________________________________________________________________________
    /**
     * <p>Obtener un {@link CursoAprobado} en base a los parametros enviados</p>
     * 
     * @param asignacion
     * @param asignacionCursoPensum
     * @return
     * @throws DataAccessException 
     */
    CursoAprobado getCursoAprobado(Asignacion asignacion, AsignacionCursoPensum asignacionCursoPensum)
        throws DataAccessException;    
//______________________________________________________________________________
    /**
     * 
     * @param asignacionEstudianteCarrera
     * @param asignacionCursoPensum
     * @param wrapperCursoAprobado
     * @throws DataAccessException 
     */
    void agregarCursoAprobado(AsignacionEstudianteCarrera asignacionEstudianteCarrera, 
            AsignacionCursoPensum asignacionCursoPensum,
            WrapperCursoAprobado wrapperCursoAprobado) throws DataAccessException;
//______________________________________________________________________________
    /**
     * 
     * @param asignacionEstudianteCarrera
     * @return
     * @throws DataAccessException 
     */
    List<CursoAprobado> listaCursoAprobadoModificable(AsignacionEstudianteCarrera asignacionEstudianteCarrera)
            throws DataAccessException;
    
    //CursoAprobado obtenerCursoAprobado(Asignacion asignacion);
}
