/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.servicio;

import gt.edu.usac.trabajosocial.dominio.Rol;
import java.util.List;
import org.springframework.dao.DataAccessException;

/**
 * <p>Contiene los metodos que permiten el manejo de la informacion relacionada
 * con el rol en la base de datos.</p>
 *
 * @author Mario Batres
 * @version 1.0
 */
public interface ServicioRol {

//______________________________________________________________________________
    /**
     * <p>Este metodo permite actualizar la informacion de un rol  a la base
     * de datos</p>
     *
     * @param salon Pojo del tipo {@link Rol}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    void actualizarRol(Rol rol) throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Este metodo permite obtener todos los roles disponibles</p>
     *
     * @return List de tipo {@link Rol}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    List <Rol> getRoles() throws DataAccessException;

//______________________________________________________________________________
    /**
     * <p>Este metodo obtiene el rol por su ID</p>
     *
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    Rol getRolPorID(short idRol);
}
