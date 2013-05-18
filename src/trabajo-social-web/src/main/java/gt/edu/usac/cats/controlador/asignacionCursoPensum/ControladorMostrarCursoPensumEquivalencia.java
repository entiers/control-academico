/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cats.controlador.asignacionCursoPensum;

import gt.edu.usac.cats.dominio.AsignacionCursoPensum;
import gt.edu.usac.cats.dominio.Pensum;
import gt.edu.usac.cats.dominio.wrapper.WrapperCursoPensumEquivalencia;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
@Controller
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class ControladorMostrarCursoPensumEquivalencia extends ControladorAbstractoCursoPensumEquivalencia implements Serializable{

//______________________________________________________________________________
    /**
     * <p>Matiene una bitaacutecora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorMostrarCursoPensumEquivalencia.class);

//______________________________________________________________________________
    @RequestMapping(value = "mostrarCursoPensumEquivalencia.htm", method = RequestMethod.GET)
    public String crearFormulario(Model modelo) {
        this.agregarAtributosDefault(modelo, new WrapperCursoPensumEquivalencia(), true);
        return "cursoPensum/mostrarCursoPensumEquivalencia";
    }

//______________________________________________________________________________
    @RequestMapping(value = "mostrarCursoPensumEquivalenciaPag.htm", method = RequestMethod.GET)
    public String crearFormularioPag(Model modelo, WrapperCursoPensumEquivalencia wrapperCursoPensumEquivalencia,
            HttpServletRequest request) {
        this.realizarBusqueda(modelo, wrapperCursoPensumEquivalencia, request);
        this.agregarAtributosDefault(modelo, wrapperCursoPensumEquivalencia, false);
        return "cursoPensum/mostrarCursoPensumEquivalencia";
    }

//______________________________________________________________________________
    private void realizarBusqueda(Model modelo, WrapperCursoPensumEquivalencia wrapperCursoPensumEquivalencia,
            HttpServletRequest request) {
        try {
            Pensum pensumOriginal = wrapperCursoPensumEquivalencia.getPensumOriginal();
            Pensum pensumEquivalente = wrapperCursoPensumEquivalencia.getPensumEquivalente();
            List<AsignacionCursoPensum> listadoAsignacionCursoPensums =
                    this.servicioAsignacionCursoPensum.getListadoCursosOrignalesPorPensumOriginalYEquivalente(
                    pensumOriginal, pensumEquivalente);

            modelo.addAttribute("listadoAsignacionCursoPensums", listadoAsignacionCursoPensums);

        } catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, "agregarCursoAPensum.titulo", "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }
    }

//______________________________________________________________________________
    @RequestMapping(value = "mostrarCursoPensumEquivalencia.htm", method = RequestMethod.POST)
    public String mostrar(@Valid WrapperCursoPensumEquivalencia wrapperCursoPensumEquivalencia,
            BindingResult bindingResult, Model modelo, HttpServletRequest request) {

        if (!bindingResult.hasErrors()) {
            this.realizarBusqueda(modelo, wrapperCursoPensumEquivalencia, request);
        }

        this.agregarAtributosDefault(modelo, wrapperCursoPensumEquivalencia, false);

        return "cursoPensum/mostrarCursoPensumEquivalencia";
    }

//______________________________________________________________________________
    @RequestMapping(value = "eliminarCursoPensumEquivalencia.htm", method = RequestMethod.GET)
    public String eliminar(Short idAsignacionCursoPensumOriginal, Short idAsignacionCursoPensumEquivalente,
            HttpServletRequest request) {
        if (idAsignacionCursoPensumOriginal != null && idAsignacionCursoPensumEquivalente != null) {
            RequestUtil.agregarRedirect(request, "mostrarCursoPensumEquivalencia.htm");

            try {
                AsignacionCursoPensum acpOriginal = this.servicioAsignacionCursoPensum.cargarEntidadPorID(AsignacionCursoPensum.class, idAsignacionCursoPensumOriginal);

                AsignacionCursoPensum acpEquivalente = this.servicioAsignacionCursoPensum.cargarEntidadPorID(AsignacionCursoPensum.class, idAsignacionCursoPensumEquivalente);

                Set<AsignacionCursoPensum> equivalencias =
                        acpOriginal.getAsignacionCursoPensumsForIdCursoPensumEquivalencia();
                equivalencias.remove(acpEquivalente);

                this.servicioAsignacionCursoPensum.actualizar(acpOriginal);


                RequestUtil.crearMensajeRespuesta(request, "cursoPensum.eliminarCursoPensumEquivalencia.titulo",
                        "cursoPensum.eliminarCursoPensumEquivalencia.exito", true);

                log.info("Equivalencia " + acpEquivalente.toString() + " eliminada de  " + acpOriginal.toString());
            } catch (DataAccessException e) {
                // error de acceso a datos
                RequestUtil.crearMensajeRespuesta(request, "agregarCursoAPensum.titulo", "dataAccessException", false);
                log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
            }
            return "cursoPensum/mostrarCursoPensumEquivalencia";
        }
        return "redirect:mostrarCursoPensumEquivalencia.htm";
    }
}
