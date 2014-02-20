/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.servicio;

import gt.edu.usac.cats.dominio.Asignacion;
import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.DetalleAsignacion;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.Horario;
import gt.edu.usac.cats.enums.TipoAsignacion;
import java.util.List;
import org.springframework.dao.DataAccessException;

/**
 * <p>Contiene los metodos que permiten el manejo de la informacion relacionada
 * con la asignacion en la base de datos.</p>
 *
 * @author Mario Batres
 * @version 1.0
 */
public interface ServicioAsignacion extends ServicioGeneral {

    /**
     * <p>Este metodo obtiene todos los tipo de asignacion habilitados
     * en la base de datos.</p>
     *
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    List <TipoAsignacion> buscarTipoAsignacionHabilitado()
            throws DataAccessException;

    /**
     * <p>Este metodo obtiene todos los tipo de asignacion habilitados
     * en la base de datos.</p>
     *
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    List <DetalleAsignacion> buscarAsignacionPorEstudiante(Estudiante estudiante,
                                                    TipoAsignacion tipoAsignacion,
                                                    Integer anio)
            throws DataAccessException;
    
        /**


    /**
     * <p>Este metodo realiza la asignación de cursos a un estudiante.</p>
     *
     * @param asignacionEstudianteCarrera pojo del tipo {@link AsignacionEstudianteCarrera}
     * @param listaHorario lista de pojos del tipo {@link Horario}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    List<DetalleAsignacion> realizarAsignacionCursos(AsignacionEstudianteCarrera asignacionEstudianteCarrera,
                                                        List<Horario> listaHorario,
                                                        TipoAsignacion tipoAsignacion)
            throws DataAccessException;
}