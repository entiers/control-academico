/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.controlador.semestre;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Esta clase se encarga de buscar los semestres en la BD.
 * 
 * @author Mario Batres
 * @version 1.0
 *
 */
@Controller
@RequestMapping(value = "buscarSemestre.htm")
public class ControladorBuscarSemestre extends ControladorAbstractoSemestre{


    @RequestMapping(method = RequestMethod.GET)
    public String crearFormulario(Model modelo){
        return this.crearFormularioBuscarSemestre(modelo);
    }
}
