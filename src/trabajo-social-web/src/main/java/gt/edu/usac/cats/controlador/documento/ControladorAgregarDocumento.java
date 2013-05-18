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
import java.io.Serializable;
import javax.annotation.Resource;
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
 * Esta clase se encarga de almacenar los documentos en la BD.
 * La informacion se pide en la pagina de <code>agregarDocumento.htm</code>.
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
@Controller
@RequestMapping(value="agregarDocumento.htm")
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class ControladorAgregarDocumento implements Serializable{
//______________________________________________________________________________
    private static String TITULO_MENSAJE = "agregarDocumento.titulo";
//______________________________________________________________________________
    private static Logger log = Logger.getLogger(ControladorAgregarDocumento.class);
//______________________________________________________________________________
    @Resource
    private ServicioGeneral servicioGeneralImpl;
//______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina <code>agregarDocumento.htm</code>. El metodo se encarga
     * de iniciar los objetos que se usaran en la pagina.</p>
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(method=RequestMethod.GET)
    public String getAgregarHorario(Model modelo){
        // se agregan los objetos que se usaran en la pagina
        modelo.addAttribute("wrapperDocumento", new WrapperDocumento());

        return "documento/agregarDocumento";
    }
//______________________________________________________________________________
    @RequestMapping(method = RequestMethod.POST)
    public String submit(@Valid WrapperDocumento wrapperDocumento, BindingResult bindingResult,
            Model modelo, HttpServletRequest request) {
        // se validan los campos ingresados en el formulario, si existen errores
        // se regresa al formulario para que se muestren los mensajes correspondientes
        if(bindingResult.hasErrors()){
            return "documento/agregarDocumento";
        }
        try {

            // se quita el envoltorio y se trata de agregar al curso
            Documento documento  = new Documento();
            wrapperDocumento.quitarWrapper(documento);
            this.servicioGeneralImpl.agregar(documento);

            // se registra el evento
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "agregarDocumento.exito", true);
            RequestUtil.agregarRedirect(request, "agregarDocumento.htm");

            String msg = Mensajes.EXITO_AGREGAR + "Documento, codigo " + documento.getIdDocumento();
            log.info(msg);

        } catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, null, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }
        return "documento/agregarDocumento";
    }

}
