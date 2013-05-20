/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.controlador.asignacionCursoPensum;

import gt.edu.usac.cats.dominio.Pensum;
import gt.edu.usac.cats.dominio.wrapper.WrapperCursoPensumEquivalencia;
import gt.edu.usac.cats.servicio.ServicioAsignacionCursoPensum;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.ui.Model;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
public class ControladorAbstractoCursoPensumEquivalencia {
//______________________________________________________________________________
    @Resource
    protected ServicioAsignacionCursoPensum servicioAsignacionCursoPensum;

//______________________________________________________________________________
    protected void agregarAtributosDefault(Model modelo,
            WrapperCursoPensumEquivalencia wrapperCursoPensumEquivalencia,
            boolean buscar ){
        List <Pensum> listadoPensums =
                    this.servicioAsignacionCursoPensum.listarEntidad(Pensum.class, true, "codigo");
        

        modelo.addAttribute("listadoPensums", listadoPensums);
        modelo.addAttribute("wrapperCursoPensumEquivalencia", wrapperCursoPensumEquivalencia);

    }

}
