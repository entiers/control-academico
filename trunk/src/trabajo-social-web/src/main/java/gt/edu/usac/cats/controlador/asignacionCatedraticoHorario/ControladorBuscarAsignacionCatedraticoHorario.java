/*
 * Universidad de San Carlos de Guatemala
 * Escuela de Trabajo Social
 * Control Academico
 */

package gt.edu.usac.cats.controlador.asignacionCatedraticoHorario;


import gt.edu.usac.cats.dominio.Catedratico;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controlador encargado de manejar las peticiones GET y POST de la p�gina 
 * buscarAsignacionCatedraticoHorario.htm. La clase se encarga de manejar
 * la logica para la consulta de las asignaciones de horarios de un catedratico 
 * en especifico
 * 
 * @author Carlos Solorzano
 * @version 1.0
 */
@Controller("controladorBuscarAsignacionCatedraticoHorario")
@RequestMapping(value="buscarAsignacionCatedraticoHorario.htm")
public class ControladorBuscarAsignacionCatedraticoHorario extends ControladorAbstractoAsignacionCatedraticoHorario{
    
    @RequestMapping(method= RequestMethod.GET)
    public String getBuscarCatHorario(Model modelo, @RequestParam int idCatedratico){
        super.catedratico = super.servicioGeneralImpl.cargarEntidadPorID(Catedratico.class, idCatedratico);
        
        if(super.catedratico != null){
            
        }        
        return "asignacionCatedraticoHorario/buscarAsignacionCatedraticoHorario";
    }            
    
}
