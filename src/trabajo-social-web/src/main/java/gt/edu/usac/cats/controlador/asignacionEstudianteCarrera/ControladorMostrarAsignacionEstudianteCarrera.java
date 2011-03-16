/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.controlador.asignacionEstudianteCarrera;

import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.servicio.ServicioAsignacionEstudianteCarrera;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */

@Controller
@RequestMapping("mostrarAsignacionEstudianteCarrera.htm")
public class ControladorMostrarAsignacionEstudianteCarrera {


    
    @Resource
    private ServicioAsignacionEstudianteCarrera servicioAsignacionEstudianteCarreraImpl;

    @RequestMapping(method=RequestMethod.GET)
    public String mostrarAsignacionEstudianteCarrera(Model modelo, Integer idEstudiante){
        if(idEstudiante == null){
            return "redirect:buscarEstudiante.htm";
        }

        Estudiante estudiante = this.servicioAsignacionEstudianteCarreraImpl.cargarEntidadPorID(Estudiante.class, idEstudiante);

        if(estudiante == null){
            return "redirect:buscarEstudiante.htm";
        }

        List <AsignacionEstudianteCarrera> listadoAsignacionEstudianteCarrera =
                this.servicioAsignacionEstudianteCarreraImpl.getAsignacionEstudianteCarrera(estudiante);

        modelo.addAttribute("estudiante", estudiante);
        modelo.addAttribute("listadoAsignacionEstudianteCarrera", listadoAsignacionEstudianteCarrera);
        return "asignacionEstudianteCarrera/mostrarAsignacionEstudianteCarrera";

    }
}
