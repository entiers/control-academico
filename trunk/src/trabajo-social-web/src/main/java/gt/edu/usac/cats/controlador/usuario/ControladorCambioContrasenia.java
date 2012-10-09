/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.controlador.usuario;

import org.springframework.stereotype.Controller;
import gt.edu.usac.cats.dominio.Usuario;
import gt.edu.usac.cats.dominio.wrapper.WrapperCambioContrasenia;
import gt.edu.usac.cats.servicio.ServicioUsuario;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
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
 * @author Ing. Carlos Solorzano
 */
@Controller
@RequestMapping(value="cambioContrasenia.htm")
public class ControladorCambioContrasenia {
    private static Logger log = Logger.getLogger(ControladorAsignarPerfilUsuario.class);
//______________________________________________________________________________
    private static final String TITULO_MENSAJE = "cambioContrasenia.titulo";
//______________________________________________________________________________
    private Usuario usuario;
//______________________________________________________________________________
    @Resource
    private ServicioUsuario servicioUsuarioImpl;
//______________________________________________________________________________
    /**
     * @param modelo
     * @param request
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String crearFormulario(Model modelo,HttpServletRequest request) {
        // se agregan los objetos que se usaran en la pagina
        modelo.addAttribute("wrapperCambioContrasenia", new WrapperCambioContrasenia());        
        return "usuario/cambioContrasenia";
    }

    //______________________________________________________________________________
    /**
     * @param modelo
     * @param request
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public String cambiarContrasenia(@Valid WrapperCambioContrasenia wrapperContrasenia, BindingResult bindingResult,
                        Model modelo, HttpServletRequest request) {

        //Buscando usuario logueado por nombre
        this.usuario = this.servicioUsuarioImpl.cargarUsuarioPorNombre(wrapperContrasenia.getNombreUsuario());

        //Validando que el usuario se haya encontrado
        if(this.usuario==null){
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "cambioContrasenia.usuarioNoExiste", false);
            return "usuario/cambioContrasenia";
        }
        if(bindingResult.hasErrors()){
            return "usuario/cambioContrasenia";
        }
        
        if(!wrapperContrasenia.getContrasenia1().equals(wrapperContrasenia.getContrasenia2())){
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "modificarContrasenia.inconsistentes", false);
            return "usuario/cambioContrasenia";
        }

        try{
            this.usuario.setPassword(wrapperContrasenia.getContrasenia1());
            this.servicioUsuarioImpl.actualizar(this.usuario);

            // se registra el evento
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "modificarContrasenia.exito", true);
            String msg = Mensajes.EXITO_ACTUALIZACION + "Usuario, idUsuario " + this.usuario.getIdUsuario();
            log.info(msg);
        }
        catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, null, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        return "usuario/cambioContrasenia";
    }

}
