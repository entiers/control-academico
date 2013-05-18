/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cats.controlador.estudiante;

import gt.edu.usac.cats.dominio.LugarNacimiento;
import gt.edu.usac.cats.dominio.Nacionalidad;
import gt.edu.usac.cats.servicio.ServicioEstudiante;
import gt.edu.usac.cats.servicio.ServicioLugarNacimiento;
import gt.edu.usac.cats.servicio.ServicioNacionalidad;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.ui.Model;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
public abstract class ControladorEstudianteAbstracto {

//______________________________________________________________________________
    @Resource
    private ServicioNacionalidad servicioNacionalidadImpl;
//______________________________________________________________________________
    @Resource
    private ServicioLugarNacimiento servicioLugarNacimientoImpl;
//______________________________________________________________________________
    @Resource
    protected ServicioEstudiante servicioEstudianteImpl;

    protected void agregarAlModeloListadoEntidades(Model modelo) {
        List <LugarNacimiento> listadoLugaresNacimiento = this.servicioLugarNacimientoImpl.listar();
        List <Nacionalidad> listadoNacionalidades = this.servicioNacionalidadImpl.listar();


        modelo.addAttribute("listadoNacionalidades", listadoNacionalidades);
        modelo.addAttribute("listadoLugaresNacimiento", listadoLugaresNacimiento);
    }
}
