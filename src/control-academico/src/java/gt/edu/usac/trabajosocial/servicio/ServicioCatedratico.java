/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.servicio;

import gt.edu.usac.trabajosocial.dominio.Catedratico;
import gt.edu.usac.trabajosocial.dominio.Escuela;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

/**
 *
 * @author Daniel Castillo
 * @version 1.0
 */
public interface ServicioCatedratico {

    /**
     * <p>Este metodo permite agregar la informacion de un catedratico a la base
     * de datos, el metodo ademas realiza la asignacion de escuela al
     * catedratico.</p>
     *
     * @param catedratico Pojo del tipo {@link Catedratico}
     * @param escuela Pojo del tipo {@link Escuela}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     * @throws DataIntegrityViolationException Si ocurrio una violacion de
     *         de integridad de datos
     */
    void agregarCatedratico(Catedratico catedratico, Escuela escuela)
            throws DataIntegrityViolationException, DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Este metodo permite actualizar los datos de un catedratico.</p>
     *
     * @param catedratico Pojo del tipo {@link Catedratico}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    void actualizarCatedratico(Catedratico catedratico)
            throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Este metodo realiza la asignacion de escuela a un catedratico.</p>
     *
     * @param catedratico Pojo del tipo {@link Catedratico}
     * @param escuela Pojo del tipo {@link Escuela}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    void asignarEscuela(Catedratico catedratico, Escuela escuela)
            throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Este metodo permite la busqueda de catedraticos por su codigo de
     * personal.</p>
     *
     * @param codigo Numero de personal del catedratico
     * @return Catedratico
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    Catedratico buscarCatedraticoPorCodigo(String codigo)
            throws DataAccessException;
}
