/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.servicio;

import gt.edu.usac.trabajosocial.dominio.Horario;
import gt.edu.usac.trabajosocial.dominio.Salon;
import gt.edu.usac.trabajosocial.dominio.Semestre;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
public interface ServicioHorario {

    /**
     * <p>Este metodo permite agregar la informacion de un horario a la base
     * de datos.
     *
     * @param horario Pojo del tipo {@link Horario}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     * @throws DataIntegrityViolationException Si ocurrio una violacion de
     *         de integridad de datos
     */
    void agregarHorario(Horario horario)
            throws DataIntegrityViolationException, DataAccessException;
//______________________________________________________________________________
     /**
     * <p>Este metodo permite agregar la informacion de un horario a la base
     * de datos.
     *
     * @param salon Pojo del tipo {@link Salon}
     * @param semestre Pojo del tipo {@link Semestre}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     * @return List
     */
    List <Horario> buscarHorarioPorSalonYSemestre(Salon salon, Semestre semestre)
            throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p></p>
     * @param horario
     * @throws DataAccessException
     */
    void actualizarHorario(Horario horario)
            throws DataAccessException;
}
