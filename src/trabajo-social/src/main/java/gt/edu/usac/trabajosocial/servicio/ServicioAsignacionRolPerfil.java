/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.trabajosocial.servicio;

import gt.edu.usac.trabajosocial.dominio.Perfil;
import java.util.List;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author shakamca
 */
public interface ServicioAsignacionRolPerfil {

    List getAsignacionRolPerfilPorPerfil(Perfil perfil) throws DataAccessException;
}
