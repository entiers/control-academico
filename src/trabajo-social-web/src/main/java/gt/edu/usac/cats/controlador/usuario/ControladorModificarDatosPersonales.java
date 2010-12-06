/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */


package gt.edu.usac.cats.controlador.usuario;

import org.springframework.stereotype.Controller;
import gt.edu.usac.cats.dominio.wrapper.WrapperDatosPersonales;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.Usuario;
import gt.edu.usac.cats.servicio.ServicioEstudiante;
import gt.edu.usac.cats.servicio.ServicioUsuario;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 *
 * @author Carlos Solórzano
 *
 * @version 1.0
 */
@Controller
public class ControladorModificarDatosPersonales {
    private static Logger log = Logger.getLogger(ControladorAsignarPerfilUsuario.class);
//_____________________________________________________________________________
    private static final String TITULO_MENSAJE = "modificarDatosPersonales.titulo";
//______________________________________________________________________________
    private Estudiante estudiante;
//_____________________________________________________________________________
    private Usuario usuario;
//_____________________________________________________________________________
    @Resource
    private ServicioEstudiante servicioEstudianteImpl;
//_____________________________________________________________________________
    @Resource
    private ServicioUsuario servicioUsuarioImpl;
 //______________________________________________________________________________
    /**
     * @param modelo
     * @param idUsuario
     * @param request
     *
     * @return
     */
    @RequestMapping(value="modificarDatosPersonales.htm", method = RequestMethod.GET)
    public String crearFormulario(Model modelo,HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        WrapperDatosPersonales wrapperDatosPersonales;
        
        //Buscando usuario logueado por nombre
        this.usuario = this.servicioUsuarioImpl.cargarUsuarioPorNombre(auth.getName().toString());

        //Validando que el usuario se haya encontrado
        if(this.usuario==null){
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "buscarUsuario.sinResultados", false);
            return "redirect:index.htm";
        }

        //Validando que el usuario sea un estudiante
        if(this.usuario.getEstudiantes().toArray().length>0){
            this.estudiante = (Estudiante) this.usuario.getEstudiantes().toArray()[0];
            wrapperDatosPersonales = new WrapperDatosPersonales();
            wrapperDatosPersonales.setDireccion(this.estudiante.getDireccion());
            wrapperDatosPersonales.setTelefono(this.estudiante.getTelefono());
            wrapperDatosPersonales.setCelular(this.estudiante.getCelular());
            wrapperDatosPersonales.setEmail(this.estudiante.getEmail());
        }
        else
            return "redirect:index.htm";

        // se agregan los objetos que se usaran en la pagina
        modelo.addAttribute("estudiante", this.estudiante);
        modelo.addAttribute("wrapperDatosPersonales", wrapperDatosPersonales);
        return "usuario/modificarDatosPersonales";
    }
//_____________________________________________________________________________
    @RequestMapping(value="modificarDatosPersonales.htm",method = RequestMethod.POST)
    public String modificarDatos(@Valid WrapperDatosPersonales wrapperDatosPersonales, BindingResult bindingResult,
                        Model modelo, HttpServletRequest request) {
        
        modelo.addAttribute("estudiante", this.estudiante);

        if(bindingResult.hasErrors())            
            return "usuario/modificarDatosPersonales";
        
        try {
            // se actualizan los datos del estudiante
            this.estudiante.setDireccion(wrapperDatosPersonales.getDireccion());
            this.estudiante.setTelefono(wrapperDatosPersonales.getTelefono());
            this.estudiante.setCelular(wrapperDatosPersonales.getCelular());
            this.estudiante.setEmail(wrapperDatosPersonales.getEmail());

            this.servicioEstudianteImpl.actualizar(this.estudiante);

            // se registra el evento
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "editarDatosPersonales.exito", true);
            String msg = Mensajes.EXITO_ACTUALIZACION + "Usuario, idUsuario " + this.usuario.getIdUsuario();
            log.info(msg);

        } catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, null, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        return "usuario/modificarDatosPersonales";
    }


}