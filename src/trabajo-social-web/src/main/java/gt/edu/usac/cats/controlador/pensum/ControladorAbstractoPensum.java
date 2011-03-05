/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.controlador.pensum;

import gt.edu.usac.cats.dominio.Carrera;
import gt.edu.usac.cats.dominio.wrapper.WrapperPensum;
import gt.edu.usac.cats.servicio.ServicioPensum;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.ui.Model;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
public abstract class ControladorAbstractoPensum {


    @Resource
    protected ServicioPensum servicioPensumImpl;


    protected void agregarAtributosDefault(Model modelo, WrapperPensum wrapperPensum){
        List <Carrera> listadoCarreras =  this.servicioPensumImpl
                .listarEntidad(Carrera.class, true, "codigo");

        modelo.addAttribute("wrapperPensum", wrapperPensum);
        modelo.addAttribute("listadoCarreras", listadoCarreras);
    }
}
