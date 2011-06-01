/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.controlador.curso;

import gt.edu.usac.cats.dominio.AsignacionCursoPensum;
import gt.edu.usac.cats.dominio.Pensum;
import gt.edu.usac.cats.dominio.wrapper.WrapperCursoPensumEquivalencia;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
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
public class ControladorMostrarCursoPensumEquivalencia extends ControladorAbstractoCursoPensumEquivalencia{

//______________________________________________________________________________
    /**
     * <p>Matiene una bitaacutecora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorMostrarCursoPensumEquivalencia.class);

//______________________________________________________________________________
    @RequestMapping(value="mostrarCursoPensumEquivalencia.htm", method=RequestMethod.GET)
    public String crearFormulario(Model modelo){
        this.agregarAtributosDefault(modelo, new WrapperCursoPensumEquivalencia(), true);
        return "cursoPensum/mostrarCursoPensumEquivalencia";
    }

//______________________________________________________________________________
    @RequestMapping(value="mostrarCursoPensumEquivalencia.htm", method=RequestMethod.POST)
    public String mostrar(@Valid WrapperCursoPensumEquivalencia wrapperCursoPensumEquivalencia,
            BindingResult bindingResult, Model modelo, HttpServletRequest request){

        this.agregarAtributosDefault(modelo, wrapperCursoPensumEquivalencia, false);

        try{
            Pensum pensumOriginal = wrapperCursoPensumEquivalencia.getPensumOriginal();
            Pensum pensumEquivalente = wrapperCursoPensumEquivalencia.getPensumEquivalente();
            List <AsignacionCursoPensum> listadoAsignacionCursoPensums= 
                    this.servicioAsignacionCursoPensum.
                    getListadoCursosOrignalesPorPensumOriginalYEquivalente(pensumOriginal,
                    pensumEquivalente);

            modelo.addAttribute("listadoAsignacionCursoPensums", listadoAsignacionCursoPensums);
            
        } catch (DataAccessException e) {
                // error de acceso a datos
                RequestUtil.crearMensajeRespuesta(request, "agregarCursoAPensum.titulo", "dataAccessException", false);
                log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
            }

        return "cursoPensum/mostrarCursoPensumEquivalencia";
    }
}