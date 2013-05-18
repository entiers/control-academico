/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */
package gt.edu.usac.cats.controlador.asignacionCursoPensum;

import gt.edu.usac.cats.dominio.AsignacionCursoPensum;
import gt.edu.usac.cats.dominio.PensumEstudianteCarrera;
import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
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
import java.io.Serializable;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
@Controller
@Scope(value = WebApplicationContext.SCOPE_SESSION)
@SessionAttributes(value = {"catedratico"})
public class ControladorRealizarEquivalenciaPorCarreras extends ControladorAbstractoRealizarEquivalencia implements Serializable{

    @Resource
    private ServicioPensumEstudianteCarrera servicioPensumEstudianteCarreraImpl;

    @RequestMapping(value = "mostrarParaRealizarEquivalenciaPorCarreras.htm", method = RequestMethod.POST)
    public String mostrarParaRealizarEquivalencia(WrapperEquivalenciaPorCarrera wrapperEquivalenciaPorCarrera,
            Model modelo) {

        //Cargando todo los datos para poder obtener la carrera en el metodo del servicio que busca la
        //asignacion a pensum valido
        AsignacionEstudianteCarrera asignacionEstudianteCarreraOriginal =
                this.servicioPensumEstudianteCarreraImpl.cargarEntidadPorID(AsignacionEstudianteCarrera.class,
                wrapperEquivalenciaPorCarrera.getAsignacionEstudianteCarreraOriginal().getIdAsignacionEstudianteCarrera());
        AsignacionEstudianteCarrera asignacionEstudianteCarreraEquivalencia =
                this.servicioPensumEstudianteCarreraImpl.cargarEntidadPorID(AsignacionEstudianteCarrera.class,
                wrapperEquivalenciaPorCarrera.getAsignacionEstudianteCarreraEquivalencia().getIdAsignacionEstudianteCarrera());
        //------------------------------------------------------
        PensumEstudianteCarrera pecOriginal = this.servicioPensumEstudianteCarreraImpl.getPensumEstudianteCarreraValido(
                asignacionEstudianteCarreraOriginal);
        PensumEstudianteCarrera pecEquivalencia = this.servicioPensumEstudianteCarreraImpl.getPensumEstudianteCarreraValido(
                asignacionEstudianteCarreraEquivalencia);
        
        List<AsignacionCursoPensum> listadoEquivalencias = this.servicioAsignacionCursoPensumImpl.getEquivalenciasPorCarreras(
                pecOriginal, pecEquivalencia);
        WrapperAsignacionEquivalencia wrapperAsignacionEquivalencia =
                new WrapperAsignacionEquivalencia(asignacionEstudianteCarreraEquivalencia);
        String nombreAction = "realizarEquivalenciaPorCarreras.htm";
        String linkRegresar = "mostrarAsignacionEstudianteCarrera.htm?idEstudiante="
                + asignacionEstudianteCarreraEquivalencia.getEstudiante().getIdEstudiante();

        this.agregarAtributosDefault(modelo, wrapperAsignacionEquivalencia, listadoEquivalencias, nombreAction, linkRegresar);

        return "cursoPensum/mostrarParaRealizarEquivalencia";
    }

    @RequestMapping(value = "realizarEquivalenciaPorCarreras.htm", method = RequestMethod.POST)
    public String realizarEquivalenciaPorCarreras(@Valid WrapperAsignacionEquivalencia wrapperAsignacionEquivalencia,
            BindingResult bindingResult, Model modelo, HttpServletRequest request) {
        return this.realizarEquivalencia(wrapperAsignacionEquivalencia,  bindingResult, modelo, request);
    }
}
