/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cats.controlador.asignacionCursoPensum;

import gt.edu.usac.cats.dominio.AsignacionCursoPensum;
import gt.edu.usac.cats.dominio.wrapper.WrapperAsignacionEquivalencia;
import gt.edu.usac.cats.servicio.ServicioAsignacionCursoPensum;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.ui.Model;

/**
 *
 * @author cats
 */
public class ControladorAbstractoRealizarEquivalencia {

    @Resource
    protected ServicioAsignacionCursoPensum servicioAsignacionCursoPensumImpl;

    protected List<AsignacionCursoPensum> listadoEquivalencias;

    protected void agregarAtributosDefault(Model modelo,
            WrapperAsignacionEquivalencia wrapperAsignacionEquivalencia) {

        modelo.addAttribute("listadoEquivalencias", this.listadoEquivalencias);
        modelo.addAttribute("wrapperAsignacionEquivalencia", wrapperAsignacionEquivalencia);
       // modelo.addAttribute("estudiante", this.estudiante);
    }
}
