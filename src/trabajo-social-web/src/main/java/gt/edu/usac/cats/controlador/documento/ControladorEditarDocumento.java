/*
 * Universidad de San Carlos de Guatemala
 * Escuela de Trabajo Social
 * Control Academico
 */

package gt.edu.usac.cats.controlador.documento;

import gt.edu.usac.cats.dominio.Documento;
import gt.edu.usac.cats.dominio.wrapper.WrapperDocumento;
import gt.edu.usac.cats.servicio.ServicioGeneral;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import javax.annotation.Resource;
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
 * @author Carlos Solorzano
 * @version 1.0
 */
@Controller("controladorEditarDocumento")
@RequestMapping(value="editarDocumento.htm")
public class ControladorEditarDocumento {
//______________________________________________________________________________
    private static String TITULO_MENSAJE = "editarDocumento.titulo";
//______________________________________________________________________________
    private static Logger log = Logger.getLogger(ControladorEditarDocumento.class);
//______________________________________________________________________________
    private Documento documento;
//______________________________________________________________________________
    @Resource
    private ServicioGeneral servicioGeneralImpl;
//______________________________________________________________________________
    @RequestMapping(method=RequestMethod.GET)
    public String getAgregarHorario(Short idDoc,Model modelo, HttpServletRequest request){
        // se agregan los objetos que se usaran en la pagina
        if(idDoc!=null)
            this.documento = this.servicioGeneralImpl.cargarEntidadPorID(Documento.class, idDoc);

        WrapperDocumento wrapperDocumento = new WrapperDocumento();
        wrapperDocumento.agregarWrapper(this.documento);
        modelo.addAttribute("wrapperDocumento", wrapperDocumento);

        return "documento/editarDocumento";
    }
//______________________________________________________________________________
    @RequestMapping(method = RequestMethod.POST)
    public String submit(@Valid WrapperDocumento wrapperDocumento, BindingResult bindingResult,
            Model modelo, HttpServletRequest request) {
        modelo.addAttribute("wrapperDocumento", wrapperDocumento);

        // se validan los campos ingresados en el formulario, si existen errores
        // se regresa al formulario para que se muestren los mensajes correspondientes
        if(bindingResult.hasErrors())
            return "documento/editarDocumento";

        try {

            wrapperDocumento.quitarWrapper(documento);
            this.servicioGeneralImpl.actualizar(documento);

            // se registra el evento
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "editarDocumento.exito", true);
            RequestUtil.agregarRedirect(request, "buscarDocumento.htm");

            String msg = Mensajes.EXITO_ACTUALIZACION + "Documento, codigo " + documento.getIdDocumento();
            log.info(msg);

        } catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, null, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }
        return "documento/editarDocumento";
    }

}

