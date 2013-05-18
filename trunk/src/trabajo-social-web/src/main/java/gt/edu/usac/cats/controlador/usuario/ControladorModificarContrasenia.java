/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.controlador.usuario;

import gt.edu.usac.cats.dominio.Catedratico;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.Usuario;
import gt.edu.usac.cats.dominio.wrapper.WrapperContrasenia;
import gt.edu.usac.cats.servicio.ServicioUsuario;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import java.io.Serializable;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author Carlos Solorzano
 */
@Controller
@Scope(value = WebApplicationContext.SCOPE_SESSION)
@SessionAttributes(value = {"usuario", "estudiante", "catedratico"})
public class ControladorModificarContrasenia implements Serializable{
    private static Logger log = Logger.getLogger(ControladorAsignarPerfilUsuario.class);
//______________________________________________________________________________
    private static final String TITULO_MENSAJE = "modificarContrasenia.titulo";
//______________________________________________________________________________
    private Usuario usuario;
//______________________________________________________________________________
    private Estudiante estudiante;
//______________________________________________________________________________
    private Catedratico catedratico;
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
    @RequestMapping(value="modificarContrasenia.htm", method = RequestMethod.GET)
    public String crearFormulario(Model modelo,HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String tipoEntidad = "";

        //Buscando usuario logueado por nombre
        this.usuario = this.servicioUsuarioImpl.cargarUsuarioPorNombre(auth.getName().toString());

        //Validando que el usuario se haya encontrado
        if(this.usuario==null){
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "buscarUsuario.sinResultados", false);
            return "redirect:index.htm";
        }

        //Validando que el usuario sea un estudiante para mostrar datos en pagina
        if(this.usuario.getEstudiantes().toArray().length>0){
            this.estudiante = (Estudiante) this.usuario.getEstudiantes().toArray()[0];            
            tipoEntidad = "estudiante";
        }
        //Validando que el usuario sea un catedratico para mostrar datos en pagina
        else if(this.usuario.getCatedraticos().toArray().length>0){
            this.catedratico = (Catedratico) this.usuario.getCatedraticos().toArray()[0];
            tipoEntidad = "catedratico";
        }

        // se agregan los objetos que se usaran en la pagina
        modelo.addAttribute("tipoEntidad", tipoEntidad);
        if (tipoEntidad.equals("estudiante")) {
            modelo.addAttribute("estudiante", this.estudiante);
        } else if (tipoEntidad.equals("catedratico")) {
            modelo.addAttribute("catedratico", this.catedratico);
        }
        modelo.addAttribute("wrapperContrasenia", new WrapperContrasenia());
        return "usuario/modificarContrasenia";
    }

    //______________________________________________________________________________
    /**
     * @param modelo
     * @param request
     *
     * @return
     */
    @RequestMapping(value="modificarContrasenia.htm", method = RequestMethod.POST)
    public String cambiarContrasenia(@Valid WrapperContrasenia wrapperContrasenia, BindingResult bindingResult,
                        Model modelo, HttpServletRequest request) {

        modelo.addAttribute("estudiante", this.estudiante);

        if(bindingResult.hasErrors()){
            return "usuario/modificarContrasenia";
        }
        if(!this.usuario.getPassword().equals(wrapperContrasenia.getContraseniaAnterior())){
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "modificarContrasenia.anteriorIncorrecta", false);
            return "usuario/modificarContrasenia";
        }

        if(!wrapperContrasenia.getContrasenia1().equals(wrapperContrasenia.getContrasenia2())){
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "modificarContrasenia.inconsistentes", false);
            return "usuario/modificarContrasenia";
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

        return "redirect:index.htm";
    }

}
