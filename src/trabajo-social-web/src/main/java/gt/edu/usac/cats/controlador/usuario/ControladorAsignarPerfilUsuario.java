/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.controlador.usuario;

import org.springframework.stereotype.Controller;
import gt.edu.usac.cats.dominio.AsignacionUsuarioPerfil;
import gt.edu.usac.cats.dominio.Perfil;
import gt.edu.usac.cats.dominio.Usuario;
import gt.edu.usac.cats.servicio.ServicioUsuario;
import gt.edu.usac.cats.servicio.ServicioGeneral;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 *
 * @author Carlos Sol�rzano
 * @version 1.0
 */
@Controller
public class ControladorAsignarPerfilUsuario {

    private static Logger log = Logger.getLogger(ControladorAsignarPerfilUsuario.class);
//______________________________________________________________________________
    private static final String TITULO_MENSAJE = "asignarPerfilUsuario.titulo";
//______________________________________________________________________________
    private Usuario usuario;
//______________________________________________________________________________
    @Resource
    private ServicioGeneral servicioGeneralImpl;

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
    @RequestMapping(value="asignarPerfilUsuario.htm", method = RequestMethod.GET)
    public String crearFormulario(Model modelo, Short idUsuario, HttpServletRequest request) {

        if(idUsuario == null){
            return "redirect:buscarUsuario.htm";
        }

        this.usuario = this.servicioGeneralImpl.cargarEntidadPorID(Usuario.class, idUsuario);

        if(this.usuario == null){
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "buscarUsuario.sinResultados", false);
            return "redirect:buscarUsuario.htm";
        }

        this.setModel(modelo);
        return "usuario/asignarPerfilUsuario";
    }

    //______________________________________________________________________________
    /**
     * @param modelo
     * @param asignacionUsuarioPerfil
     * @param request
     *
     * @return
     */
    @RequestMapping(value="asignarPerfilUsuario.htm", method = RequestMethod.POST)
    public String asignarUsuarioPerfil(Model modelo, AsignacionUsuarioPerfil
            asignacionUsuarioPerfil,  HttpServletRequest request){

        try
        {
            asignacionUsuarioPerfil.setUsuario(this.usuario);
            this.servicioGeneralImpl.agregar(asignacionUsuarioPerfil);

            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "asignarPerfilUsuario.exito", true);
            String msg = Mensajes.EXITO_AGREGAR + "Asignar Perfil Usuario, id = "
                    + asignacionUsuarioPerfil.getIdAsignacionUsuarioPerfil();
            log.info(msg);

        }catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        this.setModel(modelo);
        return "redirect:asignarPerfilUsuario.htm?idUsuario=" + this.usuario.getIdUsuario() ;
    }
    /**
     * @param modelo
     * @param idAsignacionRolPerfil
     * @param request
     *
     * @return
     */
//______________________________________________________________________________
    @RequestMapping(value="desasignarUsuarioPerfil", method = RequestMethod.GET)
    public String desasignarRolPerfil(Model modelo, Integer idAsignacionUsuarioPerfil
            , HttpServletRequest request){

        if(idAsignacionUsuarioPerfil != null){
            try{
                AsignacionUsuarioPerfil asignacionUsuarioPerfil = this.servicioGeneralImpl
                        .cargarEntidadPorID(AsignacionUsuarioPerfil.class, idAsignacionUsuarioPerfil);

                this.servicioGeneralImpl.borrar(asignacionUsuarioPerfil);
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "desasignarUsuarioPerfil.exito", true);
                String msg = Mensajes.EXITO_AGREGAR + "Desasignar Rol Perfil, id = "+ idAsignacionUsuarioPerfil;
                log.info(msg);
            }catch(DataAccessException e){
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
                log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
            }
        }

        this.setModel(modelo);
        return "redirect:asignarPerfilUsuario.htm?idUsuario=" + this.usuario.getIdUsuario() ;
    }


    //______________________________________________________________________________
    /**
     * @param modelo
     **/
    private void setModel(Model modelo){
        List <AsignacionUsuarioPerfil> listadoAsignacionPerfilUsuario = this.servicioUsuarioImpl
                .getAsignacionUsuarioPerfilPorUsuario(usuario);
                

        List <Perfil> listadoPerfilNoAsignado = this.servicioUsuarioImpl
                .getPerfilesNoAsignadosPorUsuario(usuario);

        // se agregan los objetos que se usaran en la pagina
        modelo.addAttribute("usuario", this.usuario);

        modelo.addAttribute("asignacionUsuarioPerfil", new AsignacionUsuarioPerfil());
        modelo.addAttribute("listadoAsignacionPerfilUsuario", listadoAsignacionPerfilUsuario);
        modelo.addAttribute("listadoPerfilNoAsignado", listadoPerfilNoAsignado);

    }


}
