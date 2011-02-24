/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cats.controlador.persona;

import gt.edu.usac.cats.dominio.Persona;
import gt.edu.usac.cats.dominio.busqueda.DatosBusquedaPersona;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
@Controller
@RequestMapping(value = "buscarPersona.htm")
public class ControladorBuscarPersona extends ControladorAbstractoPersona {
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
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String crearFormulario(Model modelo) {
        this.listadoPersonas = this.servicioPersonaImpl.listarEntidad(Persona.class, true, "nombre");
        this.agregarAtributosDefault(modelo, new DatosBusquedaPersona());
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
    @RequestMapping(method = RequestMethod.POST)
    public String submit(Model modelo, @Valid DatosBusquedaPersona datosBusquedaPersona, HttpServletRequest request) {
        modelo.addAttribute("listadoPersonas");        
        try {
            if(datosBusquedaPersona.esValido()){

                this.listadoPersonas = this.servicioPersonaImpl.getListadoPersonas(datosBusquedaPersona);

                this.agregarAtributosDefault(modelo, datosBusquedaPersona);

                if(this.listadoPersonas.isEmpty()){
                    RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "buscarPersona.sinResultados", false);
                }
            }else{
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "buscarPersona.formularioNoLLeno", false);
            }

        } catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        return "persona/buscarPersona";
    }
    
    
    private void agregarAtributosDefault(Model modelo, DatosBusquedaPersona datosBusquedaPersona) {
        modelo.addAttribute("listadoPersonas", listadoPersonas);
        modelo.addAttribute("datosBusquedaPersona", datosBusquedaPersona);
    }
}
