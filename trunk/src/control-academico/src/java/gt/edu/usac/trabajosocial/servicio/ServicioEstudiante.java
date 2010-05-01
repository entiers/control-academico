/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.servicio;

import gt.edu.usac.trabajosocial.dominio.Carrera;
import gt.edu.usac.trabajosocial.dominio.Estudiante;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

/**
 *
 * @author Daniel Castillo
 * @version 1.0
 */
public interface ServicioEstudiante {


    /**
     * <p>Este metodo permite agregar la informacion de un estudiante a la base
     * de datos, el metodo ademas realiza la asignacion de carrera al
     * estudiante.</p>
     *
     * @param estudiante Pojo del tipo {@link Estudiante}
     * @param estudiante Pojo del tipo {@link Carrera}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     * @throws DataIntegrityViolationException Si ocurrio una violacion de
     *         de integridad de datos
     */
    void agregarEstudiante(Estudiante estudiante, Carrera carrera)
            throws DataIntegrityViolationException, DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Este metodo realiza la asignacion de carrera a un estudiante.</p>
     * @param estudiante Pojo del tipo {@link Estudiante}
     * @param carrera Pojo del tipo {@link Carrera}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    void asignarCarrera(Estudiante estudiante, Carrera carrera)
            throws DataAccessException;
}
