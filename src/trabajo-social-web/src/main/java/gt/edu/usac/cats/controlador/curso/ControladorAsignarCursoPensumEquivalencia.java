/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cats.controlador.curso;

import gt.edu.usac.cats.dominio.Curso;
import gt.edu.usac.cats.dominio.Pensum;
import gt.edu.usac.cats.dominio.wrapper.WrapperCursoPensumEquivalencia;
import gt.edu.usac.cats.servicio.ServicioPensum;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
@Controller
public class ControladorAsignarCursoPensumEquivalencia extends ControladorAbstractoCursoPensumEquivalencia {

//______________________________________________________________________________
    /**
     * <p>Matiene una bit&aacute;cora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorAsignarCursoPensumEquivalencia.class);

//______________________________________________________________________________
    @Resource
    private ServicioPensum servicioPensumImpl;

//______________________________________________________________________________
    @RequestMapping(value = "asignarCursoPensumEquivalencia.htm", method = RequestMethod.GET)
    public String crearFormulario(Model modelo) {
        this.agregarAtributosDefault(modelo, new WrapperCursoPensumEquivalencia(), true);
        return "cursoPensum/asignarCursoPensumEquivalencia";
    }

//______________________________________________________________________________
    @RequestMapping(value = "asignarCursoPensumEquivalencia.htm", method = RequestMethod.POST)
    public String asignarEquivalenciaCurso(
            @Valid WrapperCursoPensumEquivalencia wrapperCursoPensumEquivalencia, BindingResult bindingResult, Model modelo, HttpServletRequest request) {

        return "";
    }

//______________________________________________________________________________
    @RequestMapping(value = "getListadoCursosPorIdPensum.htm", method = RequestMethod.GET)
    public @ResponseBody @JsonIgnore List<Curso> getListadoCursosPorIdPensum(@RequestParam Short idPensum) {
        return this.servicioAsignacionCursoPensum.getListadoCursosPorIdPensum(idPensum);
    }

    @RequestMapping(value = "getListadoOtrosPensums.htm", method = RequestMethod.GET)
    public @ResponseBody @JsonIgnore List<Pensum> getListadoOtrosPensums(@RequestParam Short idPensum){
        return this.servicioPensumImpl.getListadoOtrosPensums(idPensum);
    }
}
