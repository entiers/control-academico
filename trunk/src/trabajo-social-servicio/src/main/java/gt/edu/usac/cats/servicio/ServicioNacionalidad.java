/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.servicio;

import gt.edu.usac.cats.dominio.Nacionalidad;
import java.util.List;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
public interface ServicioNacionalidad {

    /**
     * @return 
     */
    public List <Nacionalidad> listar() throws DataAccessException;

}
