/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */
package gt.edu.usac.cats.controlador.usuario;

import org.springframework.stereotype.Controller;
import gt.edu.usac.cats.dominio.Usuario;
import gt.edu.usac.cats.dominio.wrapper.WrapperContrasenia;
import gt.edu.usac.cats.servicio.ServicioGeneral;
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
 * @author Carlos Solorzano
 */
@Controller
@RequestMapping(value = "reiCont.htm")
public class ControladorReiniciarContrasenia {
        private static Logger log = Logger.getLogger(ControladorAsignarPerfilUsuario.class);
//______________________________________________________________________________
    private static final String TITULO_MENSAJE = "modificarContrasenia.titulo";
//______________________________________________________________________________
    private Usuario usuario;
//______________________________________________________________________________
    @Resource
    private ServicioGeneral servicioGeneralImpl;
//_____________________________________________________________________________
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
    public String crearFormulario(Model modelo, Integer idUsuario, HttpServletRequest request) {
        //Validando que el parametro para la busqueda sea enviado
        if(idUsuario == null){
            return "welcome";
        }
        //Buscando usuario por idUsuario
        this.usuario = this.servicioGeneralImpl.cargarEntidadPorID(Usuario.class, idUsuario);
        //Validando que el usuario exista
        if(this.usuario == null){
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "buscarUsuario.sinResultados", false);
            return "welcome";
        }
        //Validar que el codigo de validacion exista
        if(this.usuario.getCodigoValidacion()==null){
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "reiniciarContrasenia.SinCodigoValidacion", false);
            return "welcome";
        }
        //Validar que el codigo de validacion no esta vacio
        if(this.usuario.getCodigoValidacion().isEmpty()){
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "reiniciarContrasenia.SinCodigoValidacion", false);
            return "welcome";
        }
        //Agragando atributos al modelo a usarse en la pagina
        modelo.addAttribute("wrapperContrasenia",new WrapperContrasenia());
        return "usuario/reiCont";
    }
//______________________________________________________________________________
    /**
     * @param modelo
     * @param request
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public String cambiarContrasenia(@Valid WrapperContrasenia wrapperContrasenia, BindingResult bindingResult,
                        Model modelo, HttpServletRequest request) {
        //Validando que no existan errores en el formulario
        if(bindingResult.hasErrors()){
            return "usuario/reiCont";
        }
        //Codigo de validacion
        if(!this.usuario.getCodigoValidacion().equals(wrapperContrasenia.getContraseniaAnterior())){
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "reiniciarPassword.codValidacionInvalido", false);
            return "usuario/reiCont";
        }
        //Validando que las contrase;as coincidan
        if(!wrapperContrasenia.getContrasenia1().equals(wrapperContrasenia.getContrasenia2())){
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "modificarContrasenia.inconsistentes", false);
            return "usuario/reiCont";
        }

        try{
            //Haciendo el cambio de contrase;a y reiniciando el codigo de validacion
            this.usuario.setPassword(wrapperContrasenia.getContrasenia1());
            this.usuario.setCodigoValidacion("");
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

        return "welcome";
    }
}
