/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */
package gt.edu.usac.cats.controlador.asignacionCursoPensum;

import gt.edu.usac.cats.dominio.AsignacionCursoPensum;
import gt.edu.usac.cats.dominio.wrapper.WrapperAsignacionEquivalencia;
import gt.edu.usac.cats.dominio.wrapper.WrapperEquivalenciaPorPensum;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

        List<AsignacionCursoPensum> listadoEquivalencias = this.servicioAsignacionCursoPensumImpl.getEquivalenciasPorPensums(
                wrapperEquivalenciaPorPensum.getAsignacionEstudianteCarrera(),
                wrapperEquivalenciaPorPensum.getPensumOriginal(),
                wrapperEquivalenciaPorPensum.getPensumEquivalencia());

        WrapperAsignacionEquivalencia wrapperAsignacionEquivalencia =
                new WrapperAsignacionEquivalencia(
                wrapperEquivalenciaPorPensum.getAsignacionEstudianteCarrera());

        String nombreAction = "realizarEquivalenciaPorPensums.htm";
        String linkRegresar = "mostrarPensumEstudianteCarrera.htm?idAsignacionEstudianteCarrera="
                + wrapperAsignacionEquivalencia.getAsignacionEstudianteCarrera().getIdAsignacionEstudianteCarrera();
        this.agregarAtributosDefault(modelo, wrapperAsignacionEquivalencia, listadoEquivalencias, nombreAction, linkRegresar);

        return "cursoPensum/mostrarParaRealizarEquivalencia";
    }

    @RequestMapping(value = "realizarEquivalenciaPorPensums.htm", method = RequestMethod.POST)
    public String realizarEquivalenciaPorPensums(@Valid WrapperAsignacionEquivalencia wrapperAsignacionEquivalencia,
            BindingResult bindingResult, Model modelo, HttpServletRequest request) {
        return this.realizarEquivalencia(wrapperAsignacionEquivalencia, bindingResult, modelo, request);
    }
}
