/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.controlador.semestre;

import java.io.Serializable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;

/**
 * Esta clase se encarga de buscar los semestres en la BD.
 * 
 * @author Mario Batres
 * @version 1.0
 *
 */
@Controller
@RequestMapping(value = "buscarSemestre.htm")
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class ControladorBuscarSemestre extends ControladorAbstractoSemestre implements Serializable{


    @RequestMapping(method = RequestMethod.GET)
    public String crearFormulario(Model modelo){
        return this.crearFormularioBuscarSemestre(modelo);
    }
}
