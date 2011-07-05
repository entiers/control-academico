/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cats.controlador.asignacionCursoPensum;

import gt.edu.usac.cats.dominio.AsignacionCursoPensum;
import gt.edu.usac.cats.dominio.AsignacionEquivalencia;
import gt.edu.usac.cats.dominio.wrapper.WrapperAsignacionEquivalencia;
import gt.edu.usac.cats.servicio.ServicioAsignacionCursoPensum;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Mairo Batres
 * @version 1.0
 */
public abstract class ControladorAbstractoRealizarEquivalencia {

    @Resource
    protected ServicioAsignacionCursoPensum servicioAsignacionCursoPensumImpl;
    protected List<AsignacionCursoPensum> listadoEquivalencias;
    private Logger log = Logger.getLogger(ControladorAbstractoRealizarEquivalencia.class);
    protected String nombreAction;
    protected String linkRegresar;

    /**
     * @param modelo
     * @param wrapperAsignacionEquivalencia
     */
    protected void agregarAtributosDefault(Model modelo,
            WrapperAsignacionEquivalencia wrapperAsignacionEquivalencia) {

        modelo.addAttribute("listadoEquivalencias", this.listadoEquivalencias);
        modelo.addAttribute("wrapperAsignacionEquivalencia", wrapperAsignacionEquivalencia);
		  modelo.addAttribute("nombreAction", this.nombreAction);
		  modelo.addAttribute("linkRegresar", this.linkRegresar);
    }

    /**
     * @param wrapperAsignacionEquivalencia
     * @param bindingResult
     * @param modelo
     * @param request
     */
    protected  String realizarEquivalencia(WrapperAsignacionEquivalencia wrapperAsignacionEquivalencia,
            BindingResult bindingResult, Model modelo, HttpServletRequest request) {

        if (!bindingResult.hasErrors()) {
            AsignacionEquivalencia asignacionEquivalencia = new AsignacionEquivalencia();
            wrapperAsignacionEquivalencia.quitarWrapper(asignacionEquivalencia);

            try {
                this.servicioAsignacionCursoPensumImpl.realizarEquivalencias(
                        wrapperAsignacionEquivalencia.getAsignacionEstudianteCarrera(),
                        asignacionEquivalencia, this.listadoEquivalencias);

                RequestUtil.crearMensajeRespuesta(request, "cursoPensum.titulo.realizarEquivalencia",
                        "cursoPensum.realizarEquivalencias.exito", true);

                this.listadoEquivalencias = null;

            } catch (DataAccessException e) {
                // error de acceso a datos
                RequestUtil.crearMensajeRespuesta(request, "cursoPensum.titulo.realizarEquivalencia",
                        "dataAccessException", false);
                log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
            }

        }
        this.agregarAtributosDefault(modelo, wrapperAsignacionEquivalencia);
        return "cursoPensum/mostrarParaRealizarEquivalencia";
    }
}
