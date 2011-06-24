/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cats.controlador.asignacionCursoPensum;

import gt.edu.usac.cats.dominio.AsignacionCursoPensum;
import gt.edu.usac.cats.dominio.AsignacionEquivalencia;
import gt.edu.usac.cats.dominio.wrapper.WrapperAsignacionEquivalencia;
import gt.edu.usac.cats.servicio.ServicioAsignacionCursoPensum;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Mairo Batres
 * @version 1.0
 */
public class ControladorAbstractoRealizarEquivalencia {

    @Resource
    protected ServicioAsignacionCursoPensum servicioAsignacionCursoPensumImpl;
    protected List<AsignacionCursoPensum> listadoEquivalencias;

    protected void agregarAtributosDefault(Model modelo,
            WrapperAsignacionEquivalencia wrapperAsignacionEquivalencia) {

        modelo.addAttribute("listadoEquivalencias", this.listadoEquivalencias);
        modelo.addAttribute("wrapperAsignacionEquivalencia", wrapperAsignacionEquivalencia);
    }

    @RequestMapping(value = "realizarEquivalencias.htm", method = RequestMethod.POST)
    public String realizarEquivalencias(@Valid WrapperAsignacionEquivalencia wrapperAsignacionEquivalencia,
            BindingResult bindingResult, Model modelo, HttpServletRequest request) {

        if (!bindingResult.hasErrors()) {
            AsignacionEquivalencia asignacionEquivalencia = new AsignacionEquivalencia();
            wrapperAsignacionEquivalencia.quitarWrapper(asignacionEquivalencia);

            System.out.println(asignacionEquivalencia);
            System.out.println(wrapperAsignacionEquivalencia.getAsignacionEstudianteCarrera());
            System.out.println(this.listadoEquivalencias);
//
//            this.servicioAsignacionCursoPensumImpl.realizarEquivalencias(
//                    wrapperAsignacionEquivalencia.getAsignacionEstudianteCarrera(),
//                    asignacionEquivalencia, this.listadoEquivalencias);
        }
        this.agregarAtributosDefault(modelo, wrapperAsignacionEquivalencia);
        return "cursoPensum/mostrarParaRealizarEquivalencia";
    }
}
