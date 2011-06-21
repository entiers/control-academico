/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cats.controlador.pensumEstudianteCarrera;

import gt.edu.usac.cats.dominio.AsignacionCursoPensum;
import gt.edu.usac.cats.dominio.wrapper.WrapperEquivalenciaPorPensum;
import gt.edu.usac.cats.servicio.ServicioAsignacionCursoPensum;
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
public class ControladorEquivalenciaPorPensums {

    @Resource
    private ServicioAsignacionCursoPensum servicioAsignacionCursoPensumImpl;


    private List <AsignacionCursoPensum> listadoEquivalencias;

    @RequestMapping(value = "mostrarParaRealizarEquivalencia.htm", method = RequestMethod.POST)
    public String mostrarParaRealizarEquivalencia(Model modelo,
            WrapperEquivalenciaPorPensum wrapperEquivalenciaPorPensum) {



        this.listadoEquivalencias = this.servicioAsignacionCursoPensumImpl.
                getEquivalenciasPorPensums(
                    wrapperEquivalenciaPorPensum.getAsignacionEstudianteCarrera(),
                    wrapperEquivalenciaPorPensum.getPensumOriginal(),
                    wrapperEquivalenciaPorPensum.getPensumEquivalencia());

        modelo.addAttribute("listadoEquivalencias", this.listadoEquivalencias);
        return "pensumEstudianteCarrera/mostrarParaRealizarEquivalencia";
    }
}
