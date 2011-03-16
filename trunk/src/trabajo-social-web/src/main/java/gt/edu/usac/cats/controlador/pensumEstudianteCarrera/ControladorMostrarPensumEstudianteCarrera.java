/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.controlador.pensumEstudianteCarrera;

import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.Pensum;
import gt.edu.usac.cats.dominio.PensumEstudianteCarrera;
import gt.edu.usac.cats.servicio.ServicioPensumEstudianteCarrera;
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
@RequestMapping(value="mostrarPensumEstudianteCarrera.htm")
public class ControladorMostrarPensumEstudianteCarrera {

    @Resource
    private ServicioPensumEstudianteCarrera servicioPensumEstudianteCarreraImpl;

    @RequestMapping(method=RequestMethod.GET)
    public String mostrarPensumEstudianteCarrera(Model model
            , Integer idAsignacionEstudianteCarrera){

        if(idAsignacionEstudianteCarrera == null){
            return "redirect:buscarEstudiante.htm";
        }

        //Se obtiene la asignación del estudiante con la carrera
        AsignacionEstudianteCarrera asignacionEstudianteCarrera
                = this.servicioPensumEstudianteCarreraImpl.cargarEntidadPorID(AsignacionEstudianteCarrera.class, idAsignacionEstudianteCarrera);

        if(asignacionEstudianteCarrera == null){
            return "redirect:buscarEstudiante.htm";
        }

        //Se obtiene las relaciones con el pensum válido
        PensumEstudianteCarrera pensumEstudianteCarrera = this.servicioPensumEstudianteCarreraImpl.getPensumEstudianteCarreraValido(
                asignacionEstudianteCarrera);


        Pensum pensum = pensumEstudianteCarrera.getPensum();

        //Se obtiene los no válidos
        List <PensumEstudianteCarrera> listadoPensumEstudianteCarreraNoValidos
                = this.servicioPensumEstudianteCarreraImpl.getListadoPensumEstudianteCarreraNoValidos(asignacionEstudianteCarrera);


        model.addAttribute("estudiante", asignacionEstudianteCarrera.getEstudiante());
        model.addAttribute("carrera", asignacionEstudianteCarrera.getCarrera());
        model.addAttribute("pensum", pensum);
        model.addAttribute("pensumEstudianteCarrera", pensumEstudianteCarrera);
        model.addAttribute("listadoPensumEstudianteCarreraNoValidos", listadoPensumEstudianteCarreraNoValidos);


        return "pensumEstudianteCarrera/mostrarPensumEstudianteCarrera";
    }
}
