/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cats.controlador.persona;

import gt.edu.usac.cats.dominio.Persona;
import gt.edu.usac.cats.dominio.busqueda.DatosBusquedaPersona;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import java.io.Serializable;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
@Controller
@Scope(value = WebApplicationContext.SCOPE_SESSION)
@SessionAttributes(value = {"listadoPersonas"})
public class ControladorBuscarPersona extends ControladorAbstractoPersona implements Serializable{
//______________________________________________________________________________

    /**
     * <p>
     * Lleva el nombre del titulo para el mensaje en la pagina
     * <p>
     */
    private static final String TITULO_MENSAJE = "buscarPersona.titulo";
//______________________________________________________________________________
    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorAgregarPersona.class);
//______________________________________________________________________________
    /**
     *
     */
    private List<Persona> listadoPersonas;

//______________________________________________________________________________
    /**
     * @param modelo
     * @param request
     * @return
     */
    @RequestMapping(value = "buscarPersona.htm", method = RequestMethod.GET)
    public String crearFormulario(Model modelo, HttpServletRequest request) {
        this.realizarBusqueda(modelo, new DatosBusquedaPersona(), request);
        return "persona/buscarPersona";
    }
//______________________________________________________________________________
    /**
     * @param model
     * @param datosBusquedaPersona
     * @param request
     */
    @RequestMapping(value = "buscarPersonaPag.htm", method = RequestMethod.GET)
    public String buscarPersonaPag(Model model, DatosBusquedaPersona datosBusquedaPersona,
            HttpServletRequest request) {
        this.realizarBusqueda(model, datosBusquedaPersona, request);
        return "persona/buscarPersona";
    }

//______________________________________________________________________________
    /**
     * @param modelo
     * @param datosBusquedaPersona
     * @param request
     *
     * @return
     */
    @RequestMapping(value = "buscarPersona.htm", method = RequestMethod.POST)
    public String submit(Model modelo, @Valid DatosBusquedaPersona datosBusquedaPersona, HttpServletRequest request) {
        modelo.addAttribute("listadoPersonas");

        if (datosBusquedaPersona.esValido()) {
            this.realizarBusqueda(modelo, datosBusquedaPersona, request);
        } else {
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "buscarPersona.formularioNoLLeno", false);
        }

        return "persona/buscarPersona";
    }
//______________________________________________________________________________
    /**
     * @param modelo
     * @param datosBusquedaPersona
     */
    private void agregarAtributosDefault(Model modelo, DatosBusquedaPersona datosBusquedaPersona) {
        modelo.addAttribute("listadoPersonas", listadoPersonas);
        modelo.addAttribute("datosBusquedaPersona", datosBusquedaPersona);
    }

//______________________________________________________________________________
    /**
     * @param modelo
     * @param datosBusquedaPersona
     * @param request 
     */
    private void realizarBusqueda(Model modelo, DatosBusquedaPersona datosBusquedaPersona,
            HttpServletRequest request) {
        try {

            this.listadoPersonas = this.servicioPersonaImpl.getListadoPersonas(datosBusquedaPersona);

            this.agregarAtributosDefault(modelo, datosBusquedaPersona);

            if (this.listadoPersonas.isEmpty()) {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "buscarPersona.sinResultados", false);
            }
        } catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }
    }
}
