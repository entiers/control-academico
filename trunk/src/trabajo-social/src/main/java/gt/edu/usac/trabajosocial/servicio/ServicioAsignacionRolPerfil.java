/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.trabajosocial.servicio;

import gt.edu.usac.trabajosocial.dominio.Perfil;
import gt.edu.usac.trabajosocial.dominio.Rol;
import java.util.List;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author shakamca
 */
public interface ServicioAsignacionRolPerfil {

    List <Rol> getRolesAsignadosPorPerfil(Perfil perfil) throws DataAccessException;
    
    List <Rol> getRolesNoAsignadosPorPerfil(Perfil perfil) throws DataAccessException;
}
