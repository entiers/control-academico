/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.servicio;

import gt.edu.usac.cats.dominio.AsignacionRolPerfil;
import gt.edu.usac.cats.dominio.Perfil;
import gt.edu.usac.cats.dominio.Rol;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
public interface ServicioAsignacionRolPerfil extends ServicioGeneral {

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
