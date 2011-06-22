/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cats.controlador.asignacionCursoPensum;

import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.wrapper.WrapperAsignacionEquivalencia;
import gt.edu.usac.cats.dominio.wrapper.WrapperEquivalenciaPorPensum;
import javax.servlet.http.HttpServletRequest;
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
public class ControladorRealizarEquivalenciaPorPensums extends ControladorAbstractoRealizarEquivalencia {

    @RequestMapping(value = "mostrarParaRealizarEquivalenciaPorPensum.htm", method = RequestMethod.POST)
    public String mostrarParaRealizarEquivalencia(WrapperEquivalenciaPorPensum wrapperEquivalenciaPorPensum,
            Model modelo) {

        this.listadoEquivalencias = this.servicioAsignacionCursoPensumImpl.getEquivalenciasPorPensums(
                wrapperEquivalenciaPorPensum.getAsignacionEstudianteCarrera(),
                wrapperEquivalenciaPorPensum.getPensumOriginal(),
                wrapperEquivalenciaPorPensum.getPensumEquivalencia());

        WrapperAsignacionEquivalencia wrapperAsignacionEquivalencia = new WrapperAsignacionEquivalencia();
        this.agregarAtributosDefault(modelo, wrapperAsignacionEquivalencia);
        return "cursoPensum/mostrarParaRealizarEquivalencia";
    }
}
