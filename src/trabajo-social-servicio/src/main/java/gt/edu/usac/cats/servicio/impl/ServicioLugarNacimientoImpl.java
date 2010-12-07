/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.servicio.impl;

import gt.edu.usac.cats.dominio.LugarNacimiento;
import gt.edu.usac.cats.servicio.ServicioLugarNacimiento;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */

@Service("servicioLugarNacimientoImpl")
public class ServicioLugarNacimientoImpl extends ServicioGeneralImpl implements ServicioLugarNacimiento{

    @Override
    public List<LugarNacimiento> listar() throws DataAccessException {
        return this.listarEntidad(LugarNacimiento.class, true, "codigo");
    }
}
