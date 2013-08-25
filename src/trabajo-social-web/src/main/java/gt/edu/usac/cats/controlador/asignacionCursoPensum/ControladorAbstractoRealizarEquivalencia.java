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
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

/**
 *
 * @author Mairo Batres
 * @version 1.0
 */
public abstract class ControladorAbstractoRealizarEquivalencia {

    @Autowired
    protected ServicioAsignacionCursoPensum servicioAsignacionCursoPensumImpl;
    private Logger log = Logger.getLogger(ControladorAbstractoRealizarEquivalencia.class);

    /**
     * @param modelo
     * @param wrapperAsignacionEquivalencia
     */
    protected void agregarAtributosDefault(Model modelo,
            WrapperAsignacionEquivalencia wrapperAsignacionEquivalencia,
            List<AsignacionCursoPensum> listadoEquivalencias,
            String nombreAction,
            String linkRegresar) {

        if (listadoEquivalencias != null) {
            modelo.addAttribute("listadoEquivalencias", listadoEquivalencias);
        }
        modelo.addAttribute("wrapperAsignacionEquivalencia", wrapperAsignacionEquivalencia);
        modelo.addAttribute("nombreAction", nombreAction);
        modelo.addAttribute("linkRegresar", linkRegresar);
    }

    /**
     * @param wrapperAsignacionEquivalencia
     * @param bindingResult
     * @param modelo
     * @param request
     */
    protected String realizarEquivalencia(WrapperAsignacionEquivalencia wrapperAsignacionEquivalencia,
            BindingResult bindingResult, Model modelo, HttpServletRequest request) {

        List<AsignacionCursoPensum> listadoEquivalencias = null;

        if (!bindingResult.hasErrors()) {
            AsignacionEquivalencia asignacionEquivalencia = new AsignacionEquivalencia();
            wrapperAsignacionEquivalencia.quitarWrapper(asignacionEquivalencia);



            try {
                this.servicioAsignacionCursoPensumImpl.realizarEquivalencias(
                        wrapperAsignacionEquivalencia.getAsignacionEstudianteCarrera(),
                        asignacionEquivalencia, listadoEquivalencias);

                RequestUtil.crearMensajeRespuesta(request, "cursoPensum.titulo.realizarEquivalencia",
                        "cursoPensum.realizarEquivalencias.exito", true);

                listadoEquivalencias = null;

            } catch (DataAccessException e) {
                // error de acceso a datos
                RequestUtil.crearMensajeRespuesta(request, "cursoPensum.titulo.realizarEquivalencia",
                        "dataAccessException", false);
                log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
            }

        }
        agregarAtributosDefault(modelo, wrapperAsignacionEquivalencia, listadoEquivalencias, "", "");
        return "cursoPensum/mostrarParaRealizarEquivalencia";
    }
}
