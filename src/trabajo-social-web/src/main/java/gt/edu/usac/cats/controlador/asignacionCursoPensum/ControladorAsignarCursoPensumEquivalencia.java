/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cats.controlador.asignacionCursoPensum;

import gt.edu.usac.cats.dominio.AsignacionCursoPensum;
import gt.edu.usac.cats.dominio.Curso;
import gt.edu.usac.cats.dominio.Pensum;
import gt.edu.usac.cats.dominio.wrapper.WrapperCursoPensumEquivalencia;
import gt.edu.usac.cats.servicio.ServicioPensum;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
@Controller
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class ControladorAsignarCursoPensumEquivalencia extends ControladorAbstractoCursoPensumEquivalencia implements Serializable{

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
    public String asignarCursoPensumEquivalencia(
            @Valid WrapperCursoPensumEquivalencia wrapperCursoPensumEquivalencia,
            BindingResult bindingResult, Model modelo, HttpServletRequest request) {

        if (!bindingResult.hasErrors()) {
            try {
                AsignacionCursoPensum acpOriginal = this.servicioAsignacionCursoPensum.getAsignacionPorCursoYPensum(
                        wrapperCursoPensumEquivalencia.getCursoOriginal(),
                        wrapperCursoPensumEquivalencia.getPensumOriginal());


                AsignacionCursoPensum acpEquivalente = this.servicioAsignacionCursoPensum.getAsignacionPorCursoYPensum(
                        wrapperCursoPensumEquivalencia.getCursoEquivalente(),
                        wrapperCursoPensumEquivalencia.getPensumEquivalente());

                Set<AsignacionCursoPensum> equivalencias =
                        acpOriginal.getAsignacionCursoPensumsForIdCursoPensumEquivalencia();
                equivalencias.add(acpEquivalente);

                this.servicioAsignacionCursoPensum.actualizar(acpOriginal);

                RequestUtil.crearMensajeRespuesta(request, "cursoPensum.asignarCursoPensumEquivalencia.titulo", 
                        "cursoPensum.asignarCursoPensumEquivalencia.exito", true);
                log.info("Equivalencia de " + acpOriginal.toString() + " a " + acpEquivalente.toString());

            } catch (DataIntegrityViolationException e) {
                RequestUtil.crearMensajeRespuesta(request, "agregarCursoAPensum.titulo", "dataIntegrityException", false);
                log.error(Mensajes.DATA_INTEGRITY_VIOLATION_EXCEPTION, e);
            } catch (DataAccessException e) {
                // error de acceso a datos
                RequestUtil.crearMensajeRespuesta(request, "agregarCursoAPensum.titulo", "dataAccessException", false);
                log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
            }
        }
        this.agregarAtributosDefault(modelo, wrapperCursoPensumEquivalencia, false);
        return "cursoPensum/asignarCursoPensumEquivalencia";
    }

//______________________________________________________________________________
    @RequestMapping(value = "getListadoCursosPorIdPensum.htm", method = RequestMethod.GET)
    public
    @ResponseBody
    @JsonIgnore
    List<Curso> getListadoCursosPorIdPensum(@RequestParam Short idPensum) {
        return this.servicioAsignacionCursoPensum.getListadoCursosPorIdPensum(idPensum);
    }

    @RequestMapping(value = "getListadoOtrosPensums.htm", method = RequestMethod.GET)
    public
    @ResponseBody
    @JsonIgnore
    List<Pensum> getListadoOtrosPensums(@RequestParam Short idPensum) {
        return this.servicioPensumImpl.getListadoOtrosPensums(idPensum);
    }
}
