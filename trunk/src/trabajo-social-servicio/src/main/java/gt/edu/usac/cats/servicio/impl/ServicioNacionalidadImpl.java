/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.servicio.impl;

import gt.edu.usac.cats.dominio.Nacionalidad;
import gt.edu.usac.cats.servicio.ServicioNacionalidad;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */

@Service("servicioNacionalidadImpl")
public class ServicioNacionalidadImpl extends ServicioGeneralImpl implements ServicioNacionalidad{

    @Override
    public List<Nacionalidad> listar() throws DataAccessException{
        return this.listarEntidad(Nacionalidad.class, true, "codigo");
    }

}
