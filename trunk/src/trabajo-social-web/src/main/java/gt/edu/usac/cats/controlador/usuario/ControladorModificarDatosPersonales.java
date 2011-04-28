/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */


package gt.edu.usac.cats.controlador.usuario;

import gt.edu.usac.cats.dominio.Catedratico;
import org.springframework.stereotype.Controller;
import gt.edu.usac.cats.dominio.wrapper.WrapperDatosPersonales;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.Persona;
import gt.edu.usac.cats.dominio.Usuario;
import gt.edu.usac.cats.servicio.ServicioGeneral;
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
//______________________________________________________________________________
    private Catedratico catedratico;
//______________________________________________________________________________
    private Persona persona;
//_____________________________________________________________________________
    private Usuario usuario;
//_____________________________________________________________________________
    private String tipoEntidad;
//_____________________________________________________________________________
    @Resource
    private ServicioGeneral servicioGeneralImpl;
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
        WrapperDatosPersonales wrapperDatosPersonales = new WrapperDatosPersonales();
        boolean error = false;
        
        //Buscando usuario logueado por nombre
        this.usuario = this.servicioUsuarioImpl.cargarUsuarioPorNombre(auth.getName().toString());

        //Validando que el usuario se haya encontrado
        if(this.usuario==null){
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "buscarUsuario.sinResultados", false);
            error = true;
        }

        //Validando que el usuario sea un estudiante
        if(this.usuario.getEstudiantes().toArray().length>0){
            tipoEntidad = "estudiante";
            this.estudiante = (Estudiante) this.usuario.getEstudiantes().toArray()[0];
            wrapperDatosPersonales.agregarWrapper(estudiante);
        }
        //Validando que el usuario sea un catedratico
        else if(this.usuario.getCatedraticos().toArray().length>0){
            tipoEntidad = "catedratico";
            this.catedratico = (Catedratico) this.usuario.getCatedraticos().toArray()[0];
            wrapperDatosPersonales.agregarWrapper(catedratico);
        }
        //Validando que el usuario sea una persona
        else if(this.usuario.getPersona()!=null){
            tipoEntidad = "persona";
            this.persona = this.usuario.getPersona();
            wrapperDatosPersonales.agregarWrapper(persona);
        }
        else{
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "editarDatosPersonales.usuarioSinDatos", true);
            error = true;
        }

        // se agregan los objetos que se usaran en la pagina
        modelo.addAttribute("error", error);
        if(!error){
            modelo.addAttribute("tipoEntidad", this.tipoEntidad);
            modelo.addAttribute("wrapperDatosPersonales", wrapperDatosPersonales);
            if(this.tipoEntidad.equals("estudiante"))
                modelo.addAttribute("estudiante", this.estudiante);
            else if(this.tipoEntidad.equals("persona"))
                modelo.addAttribute("persona", this.persona);
            else
                modelo.addAttribute("catedratico", this.catedratico);
        }
        
        return "usuario/modificarDatosPersonales";
    }
//_____________________________________________________________________________
    @RequestMapping(value="modificarDatosPersonales.htm",method = RequestMethod.POST)
    public String modificarDatos(@Valid WrapperDatosPersonales wrapperDatosPersonales, BindingResult bindingResult,
                        Model modelo, HttpServletRequest request) {
        
        modelo.addAttribute("tipoEntidad", this.tipoEntidad);
        if(this.tipoEntidad.equals("estudiante"))
            modelo.addAttribute("estudiante", this.estudiante);
        else if(this.tipoEntidad.equals("persona"))
            modelo.addAttribute("persona", this.persona);
        else
            modelo.addAttribute("catedratico", this.catedratico);

        if(bindingResult.hasErrors())            
            return "usuario/modificarDatosPersonales";

        /*if(this.servicioUsuarioImpl.getUsuarioPorEmail(wrapperDatosPersonales.getEmail()).getIdUsuario()!=
                this.usuario.getIdUsuario()){
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "usuario.correoYaExiste", false);
            return "usuario/modificarDatosPersonales";
        }*/
        
        try {
            if(this.tipoEntidad.equals("estudiante")){
                // se actualizan los datos del estudiante
                wrapperDatosPersonales.quitarWrapper(estudiante);
                this.servicioGeneralImpl.actualizar(this.estudiante);
            }
            else if(this.tipoEntidad.equals("persona")) {
                // se actualizan los datos de la persona
                wrapperDatosPersonales.quitarWrapper(persona);
                this.servicioGeneralImpl.actualizar(this.persona);
            }
            else{
                // se actualizan los datos del catedratico
                wrapperDatosPersonales.quitarWrapper(catedratico);            
                this.servicioGeneralImpl.actualizar(this.catedratico);
            }

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