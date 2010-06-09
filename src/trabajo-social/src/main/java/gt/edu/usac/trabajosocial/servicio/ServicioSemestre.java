/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.servicio;

import gt.edu.usac.trabajosocial.dominio.Semestre;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
public interface ServicioSemestre {

//______________________________________________________________________________
    /**
     * <p>Este metodo permite agregar la informacion de un semestre a la base
     * de datos.
     *
     * @param semestre Pojo del tipo {@link semestre}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     * @throws DataIntegrityViolationException Si ocurrio una violacion de
     *         de integridad de datos
     */
    void agregarSemestre(Semestre semestre)
        throws DataIntegrityViolationException, DataAccessException;

//______________________________________________________________________________
    /**
     * <p>Obtiene todo los semestres v�lido en la DB</p>
     */
    List <Semestre> getSemestres() throws DataAccessException;


}
