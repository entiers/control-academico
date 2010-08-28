/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.trabajosocial.servicio;

import gt.edu.usac.trabajosocial.dominio.AsignacionRolPerfil;
import gt.edu.usac.trabajosocial.dominio.Perfil;
import gt.edu.usac.trabajosocial.dominio.Rol;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
public interface ServicioAsignacionRolPerfil {
//______________________________________________________________________________
    /**
     * @param asignacionRolPerfil
     *
     * @throws DataIntegrityViolationException
     * @throws DataAccessException
     */
    void agregarAsignacionRolPerfil(AsignacionRolPerfil asignacionRolPerfil) throws
            DataIntegrityViolationException, DataAccessException;
//______________________________________________________________________________
    /**
     * @param idAsignacionRolPerfil
     *
     * @throws DataAccessException     *
     */
    void eliminarAsingacionRolPerfil(int idAsignacionRolPerfil)
            throws DataAccessException;
//______________________________________________________________________________
    /**
     * @param perfil
     *
     * @throws DataAccessException
     *
     * @return
     **/
    List <Rol> getRolesAsignadosPorPerfil(Perfil perfil) throws DataAccessException;
//______________________________________________________________________________
    /**
     * @param perfil
     *
     * @throws DataAccessException
     *
     * @return
     **/
    List <Rol> getRolesNoAsignadosPorPerfil(Perfil perfil) throws DataAccessException;
//______________________________________________________________________________
    /**
     * @param perfil
     *
     * @throws DataAccessException
     * 
     * @return
     **/
    List <AsignacionRolPerfil> getAsignacionRolPerfilPorPerfil(Perfil perfil)
            throws DataAccessException;
}
