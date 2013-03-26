/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.controlador.asignacionCursoPensum;

import gt.edu.usac.cats.dominio.wrapper.WrapperAsignacionEquivalencia;
import gt.edu.usac.cats.dominio.wrapper.WrapperEquivalenciaPorPensum;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import gt.edu.usac.cats.dominio.wrapper.WrapperAsignacionEquivalencia;
import javax.servlet.http.HttpServletRequest;

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

        WrapperAsignacionEquivalencia wrapperAsignacionEquivalencia =
                new WrapperAsignacionEquivalencia(
                wrapperEquivalenciaPorPensum.getAsignacionEstudianteCarrera()   );

		  this.nombreAction = "realizarEquivalenciaPorPensums.htm";
		  this.linkRegresar = "mostrarPensumEstudianteCarrera.htm?idAsignacionEstudianteCarrera=" +
		  		wrapperAsignacionEquivalencia.getAsignacionEstudianteCarrera().getIdAsignacionEstudianteCarrera();
        this.agregarAtributosDefault(modelo, wrapperAsignacionEquivalencia);
		  
        return "cursoPensum/mostrarParaRealizarEquivalencia";
    }

    @RequestMapping(value = "realizarEquivalenciaPorPensums.htm", method = RequestMethod.POST)
    public String realizarEquivalenciaPorPensums(@Valid WrapperAsignacionEquivalencia wrapperAsignacionEquivalencia,
						 BindingResult bindingResult, Model modelo, HttpServletRequest request){
	return this.realizarEquivalencia(wrapperAsignacionEquivalencia, bindingResult, modelo, request);
    }
}