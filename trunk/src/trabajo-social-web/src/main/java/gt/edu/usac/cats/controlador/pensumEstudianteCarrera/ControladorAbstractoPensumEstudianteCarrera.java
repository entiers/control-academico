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
import org.springframework.ui.Model;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
public abstract class ControladorAbstractoPensumEstudianteCarrera {
    @Resource
    protected ServicioPensumEstudianteCarrera servicioPensumEstudianteCarreraImpl;

    protected AsignacionEstudianteCarrera asignacionEstudianteCarrera;

    protected Pensum pensum;

    protected void agregarAtributosDefault(Model modelo, PensumEstudianteCarrera pensumEstudianteCarrera
            , boolean autoOpenDialogAsignar){

        //Se obtiene los no validos
        List <PensumEstudianteCarrera> listadoPensumEstudianteCarreraNoValidos
                = this.servicioPensumEstudianteCarreraImpl.getListadoPensumEstudianteCarreraNoValidos(asignacionEstudianteCarrera);



        List <Pensum> listadoPensumsNoAsignadosAEsutudianteCarrera =
                this.servicioPensumEstudianteCarreraImpl.getPensumsNoAsignadosAEstudianteCarrera(asignacionEstudianteCarrera);

        modelo.addAttribute("estudiante", asignacionEstudianteCarrera.getEstudiante());
        modelo.addAttribute("carrera", asignacionEstudianteCarrera.getCarrera());
        modelo.addAttribute("pensum", pensum);
        modelo.addAttribute("pensumEstudianteCarrera", pensumEstudianteCarrera);
        modelo.addAttribute("listadoPensumEstudianteCarreraNoValidos", listadoPensumEstudianteCarreraNoValidos);
        modelo.addAttribute("pensumEstudianteCarrera", new PensumEstudianteCarrera());
        modelo.addAttribute("listadoPensumsNoAsignadosAEsutudianteCarrera", listadoPensumsNoAsignadosAEsutudianteCarrera);
        modelo.addAttribute("autoOpenDialogAsignar", autoOpenDialogAsignar);
    }

}
