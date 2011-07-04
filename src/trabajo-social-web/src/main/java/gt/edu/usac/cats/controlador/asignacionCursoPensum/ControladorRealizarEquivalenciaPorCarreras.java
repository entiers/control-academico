/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */
package gt.edu.usac.cats.controlador.asignacionCursoPensum;

import gt.edu.usac.cats.dominio.PensumEstudianteCarrera;
import gt.edu.usac.cats.dominio.wrapper.WrapperAsignacionEquivalencia;
import gt.edu.usac.cats.dominio.wrapper.WrapperEquivalenciaPorCarrera;
import gt.edu.usac.cats.servicio.ServicioPensumEstudianteCarrera;
import javax.annotation.Resource;
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
public class ControladorRealizarEquivalenciaPorCarreras extends ControladorAbstractoRealizarEquivalencia {

    @Resource
    private ServicioPensumEstudianteCarrera servicioPensumEstudianteCarreraImpl;

    @RequestMapping(value = "mostrarParaRealizarEquivalenciaPorCarreras.htm", method = RequestMethod.POST)
    public String mostrarParaRealizarEquivalencia(WrapperEquivalenciaPorCarrera wrapperEquivalenciaPorCarrera,
            Model modelo) {

        PensumEstudianteCarrera pecOriginal = this.servicioPensumEstudianteCarreraImpl.getPensumEstudianteCarreraValido(
                wrapperEquivalenciaPorCarrera.getAsignacionEstudianteCarreraOriginal());

        PensumEstudianteCarrera pecEquivalencia = this.servicioPensumEstudianteCarreraImpl.getPensumEstudianteCarreraValido(
                wrapperEquivalenciaPorCarrera.getAsignacionEstudianteCarreraEquivalencia());


        this.listadoEquivalencias = this.servicioAsignacionCursoPensumImpl.getEquivalenciasPorCarreras(
                pecOriginal, pecEquivalencia);


        WrapperAsignacionEquivalencia wrapperAsignacionEquivalencia =
                new WrapperAsignacionEquivalencia(
                wrapperEquivalenciaPorCarrera.getAsignacionEstudianteCarreraEquivalencia());

        this.agregarAtributosDefault(modelo, wrapperAsignacionEquivalencia);

	this.nombreAction = "realizarEquivalenciaPorCarreras.htm";
        return "cursoPensum/mostrarParaRealizarEquivalencia";
    }

    @RequestMapping(value = "realizarEquivalenciaPorCarreras.htm", method = RequestMethod.POST)
	public String realizarEquivalenciaPorCarreras(@Valid WrapperAsignacionEquivalencia wrapperAsignacionEquivalencia,
						     BindingResult bindingResult, Model modelo, HttpServletRequest request){
	return this.realizarEquivalencia(wrapperAsignacionEquivalencia, bindingResult,modelo,request);
    }
}
