/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.servicio;

import gt.edu.usac.trabajosocial.dominio.Perfil;
import java.util.List;
import org.springframework.dao.DataAccessException;

/**
 * <p>Contiene los metodos que permiten el manejo de la informacion relacionada
 * con el perfil en la base de datos.</p>
 *
 * @author Mario Batres
 * @version 1.0
 */

public interface ServicioPerfil {
//______________________________________________________________________________
    /**
     * <p>Este metodo permite agregar la informacion de un perfil a la base
     * de datos.</p>
     *
     * @param salon Pojo del tipo {@link Perfil}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */

    void agregarPerfil(Perfil perfil) throws DataAccessException;

//______________________________________________________________________________
    /**
     * <p>Este metodo permite actualizar la informacion de un perfil  a la base
     * de datos</p>
     *
     * @param salon Pojo del tipo {@link Perfil}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    void actualizarPerfil(Perfil perfil) throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Este metodo permite obtener todos los perfiles disponibles</p>
     *
     * @return List de tipo {@link Perfil}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    List <Perfil> getPerfiles() throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Este metodo obtiene el perfil por su ID</p>
     *
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    Perfil getPerfilPorID(short idPerfil);
}
