/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.trabajosocial.servicio;

import gt.edu.usac.trabajosocial.dominio.TipoAsignacion;
import java.util.List;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * <p>Contiene los metodos que permiten el manejo de la informacion relacionada
 * con el Tipo de Asignación en la base de datos.</p>
 *
 * @author Mario Batres
 * @version 1.0
 */
public interface ServicioTipoAsignacion {
    /**
     * <p>Este metodo permite agregar la informacion de un tipo de asignación
     * a la base de datos.</p>
     *
     * @param tipoAsignacion Pojo del tipo {@link TipoAsignacion}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     * @throws DataIntegrityViolationException Si ocurrio una violacion de
     *         de integridad de datos
     */
    void agregarTipoAsignacion(TipoAsignacion tipoAsignacion)
            throws DataIntegrityViolationException, DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Este metodo permite actualizar la informacion de un tipo de asignacion
     * en la base de datos.</p>
     *
     * @param tipoAsignacion Pojo del tipo {@link TipoAsignacion}
     * @throws DataAccessException Si ocurrio un error de acceso a datos

     */
    void actualizarTipoAsignacion(TipoAsignacion tipoAsignacion)
            throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Este metodo permite eliminar fisicamente la informacion de un tipo de asignacion
     * en la base de datos.</p>
     *
     * @param tipoAsignacion Pojo del tipo {@link TipoAsignacion}
     * @throws DataAccessException Si ocurrio un error de acceso a datos

     */
    void elimiarTipoAsignacion(TipoAsignacion tipoAsignacion)
            throws DataAccessException, DataIntegrityViolationException, ConstraintViolationException;
//______________________________________________________________________________
    /**
     * <p>Este metodo obtiene todos los tipo de asignacion habilitados
     * en la base de datos.</p>
     *
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    List <TipoAsignacion> buscarTipoAsignacion()
            throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Este metodo obtiene el tipo de asignacion por su ID</p>
     *
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    TipoAsignacion getTipoAsignacionPorID(short idTipoAsignacion)
            throws DataAccessException;
}
