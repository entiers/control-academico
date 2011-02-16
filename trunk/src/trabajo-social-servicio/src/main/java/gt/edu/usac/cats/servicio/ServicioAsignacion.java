/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.servicio;

import gt.edu.usac.cats.dominio.Asignacion;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.enums.TipoAsignacion;
import java.util.List;
import org.springframework.dao.DataAccessException;

/**
 * <p></p>
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
    List <Asignacion> buscarAsignacionPorEstudiante(Estudiante estudiante,
                                                    TipoAsignacion tipoAsignacion,
                                                    Integer anio)
            throws DataAccessException;
}
