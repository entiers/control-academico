/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.controlador.pensum;

import gt.edu.usac.cats.dominio.Pensum;
import gt.edu.usac.cats.enums.ControlReporte;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Esta clase se encarga de la busqueda de Pensums en la BD
 * para mostrarlos en la pagina de <code>buscarPensum.htm</code>.
 *
 * @author Mario Batres
 * @version 1.0
 */
@Controller
@RequestMapping(value = "buscarPensum.htm")
public class ControladorBuscarPensum extends ControladorAbstractoPensum {
    
//______________________________________________________________________________

    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina <code>buscarPensum.htm</code>. El metodo se encarga
     * de iniciar los objetos que se usaran en la pagina.</p>
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(method = RequestMethod.GET)
    public String crearFormulario(Model modelo) {
        List<Pensum> listadoPensums = this.servicioPensumImpl.listarEntidad(Pensum.class, true, "codigo");
        modelo.addAttribute("listadoPensums", listadoPensums);
        modelo.addAttribute("nombreControlReporte", ControlReporte.PENSUM_ESTUDIO);
        return "pensum/buscarPensum";
    }

}
