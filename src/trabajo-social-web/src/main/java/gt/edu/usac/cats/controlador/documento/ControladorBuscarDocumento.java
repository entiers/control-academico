/*
 * Universidad de San Carlos de Guatemala
 * Escuela de Trabajo Social
 * Control Academico
 */
package gt.edu.usac.cats.controlador.documento;

import gt.edu.usac.cats.dominio.Documento;
import gt.edu.usac.cats.dominio.busqueda.DatosBusquedaDocumento;
import gt.edu.usac.cats.servicio.ServicioDocumento;
import gt.edu.usac.cats.servicio.ServicioGeneral;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
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
 * @author Carlos Solorzano
 * @version 1.0
 */
@Controller
@Scope(value = WebApplicationContext.SCOPE_SESSION)
@SessionAttributes(value = {"listaDocumentos"})
public class ControladorBuscarDocumento implements Serializable {
//______________________________________________________________________________

    private static String TITULO_MENSAJE = "agregarDocumento.titulo";
//______________________________________________________________________________
    private static Logger log = Logger.getLogger(ControladorAgregarDocumento.class);
//______________________________________________________________________________
    private List<Documento> listaDocumentos;
//______________________________________________________________________________
    @Resource
    private ServicioGeneral servicioGeneralImpl;
//______________________________________________________________________________
    @Resource
    private ServicioDocumento servicioDocumentoImpl;
//______________________________________________________________________________

    @RequestMapping(value = "buscarDocumento.htm", method = RequestMethod.GET)
    public String getBuscarDocumento(Model model) {
        this.listaDocumentos = this.servicioGeneralImpl.listarEntidad(Documento.class, true, "nombre");
        this.agregarAtributosDefault(model, new DatosBusquedaDocumento());
        return "documento/buscarDocumento";
    }
//______________________________________________________________________________

    @RequestMapping(value = "buscarDocumentoPag.htm", method = RequestMethod.GET)
    public String buscarDocumentoPag(Model model, DatosBusquedaDocumento datosBusquedaDocumento){
        this.agregarAtributosDefault(model, datosBusquedaDocumento);
        return "documento/buscarDocumento";
    }

    @RequestMapping(value = "buscarDocumento.htm", method = RequestMethod.POST)
    public String submit(Model modelo,
            @Valid DatosBusquedaDocumento datosBusquedaDocumento,
            HttpServletRequest request) {

        try {
            if (datosBusquedaDocumento.esValido()) {

                this.listaDocumentos = this.servicioDocumentoImpl.getDocumento(datosBusquedaDocumento.getNombre());

                this.agregarAtributosDefault(modelo, datosBusquedaDocumento);

                if (this.listaDocumentos.isEmpty()) {
                    RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "buscarDocumento.sinResultados", false);
                }
            } else {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "buscarDocumento.formularioNoLLeno", false);
            }

        } catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        return "documento/buscarDocumento";
    }

    private void agregarAtributosDefault(Model modelo, DatosBusquedaDocumento datosBusquedaDocumento) {
        modelo.addAttribute("listadoDocumentos", this.listaDocumentos);
        modelo.addAttribute("datosBusquedaDocumento", datosBusquedaDocumento);
    }
}
