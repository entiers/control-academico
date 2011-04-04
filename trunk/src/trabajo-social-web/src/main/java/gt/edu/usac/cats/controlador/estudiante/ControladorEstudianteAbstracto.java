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
    
//______________________________________________________________________________

    protected List <Nacionalidad> listadoNacionalidades;

//______________________________________________________________________________

    protected List <LugarNacimiento> listadoLugaresNacimiento;

//______________________________________________________________________________

    protected void listarEntidades(Model modelo){
        this.listadoLugaresNacimiento = this.servicioLugarNacimientoImpl.listar();
        this.listadoNacionalidades = this.servicioNacionalidadImpl.listar();

        this.agregarAlModeloListadoEntidades(modelo);
    }
    
    protected void agregarAlModeloListadoEntidades(Model modelo){
        modelo.addAttribute("listadoNacionalidades", this.listadoNacionalidades);
        modelo.addAttribute("listadoLugaresNacimiento", this.listadoLugaresNacimiento);
    }
    
}
