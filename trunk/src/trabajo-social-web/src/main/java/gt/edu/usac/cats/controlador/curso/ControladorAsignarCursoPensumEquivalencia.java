/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.controlador.curso;

import gt.edu.usac.cats.dominio.wrapper.WrapperCursoPensumEquivalencia;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
@Controller
@RequestMapping(value="equivalenciaCurso.htm")
public class ControladorAsignarCursoPensumEquivalencia {


    @RequestMapping(method=RequestMethod.GET)
    public String crearFormulario(Model modelo){
        return "";
    }


    @RequestMapping(method=RequestMethod.POST)
    public String asignarEquivalenciaCurso(
            @Valid WrapperCursoPensumEquivalencia wrapperCursoPensumEquivalencia
            , BindingResult bindingResult
            , Model modelo
            , HttpServletRequest request){

        return "";
    }

}
