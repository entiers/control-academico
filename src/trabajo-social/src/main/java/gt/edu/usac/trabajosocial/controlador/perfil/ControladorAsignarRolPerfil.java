/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.trabajosocial.controlador.perfil;

import gt.edu.usac.trabajosocial.dominio.AsignacionRolPerfil;
import gt.edu.usac.trabajosocial.dominio.Perfil;
import gt.edu.usac.trabajosocial.dominio.Rol;
import gt.edu.usac.trabajosocial.servicio.ServicioAsignacionRolPerfil;
import gt.edu.usac.trabajosocial.servicio.ServicioPerfil;
import gt.edu.usac.trabajosocial.util.MensajePopup;
import gt.edu.usac.trabajosocial.util.Mensajes;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
public class ControladorAsignarRolPerfil {

    private static Logger log = Logger.getLogger(ControladorAsignarRolPerfil.class);
//______________________________________________________________________________
    private static final String TITULO_MENSAJE = "asignarRolPerfil.titulo";
//______________________________________________________________________________
    private Perfil perfil;
//______________________________________________________________________________
    @Resource
    private ServicioPerfil servicioPerfilImpl;
//______________________________________________________________________________
    @Resource
    private ServicioAsignacionRolPerfil servicioAsignacionRolPerfilImpl;

//______________________________________________________________________________
    /**
     * @param modelo
     * @param idPerfil
     * @param request
     *
     * @return
     */
    @RequestMapping(value="asignarRolPerfil.htm", method = RequestMethod.GET)
    public String crearFormulario(Model modelo, Short idPerfil, HttpServletRequest request) {

        if(idPerfil == null){
            return "perfil/buscarPerfil";
        }

        this.perfil = this.servicioPerfilImpl.getPerfilPorID(idPerfil);

        if(this.perfil == null){
            MensajePopup.crearMensajeRespuesta(request, TITULO_MENSAJE, "buscarPerfil.sinResultados", false);
            return "perfil/buscarPerfil";
        }        

        this.setModel(modelo);
        return "perfil/asignarRolPerfil";
    }
//______________________________________________________________________________
    /**
     * @param modelo
     * @param asignacionRolPerfil
     * @param request
     *
     * @return
     */
    @RequestMapping(value="asignarRolPerfil.htm", method = RequestMethod.POST)
    public String asignarRolPerfil(Model modelo, AsignacionRolPerfil
            asignacionRolPerfil,  HttpServletRequest request){        

        try
        {
            asignacionRolPerfil.setPerfil(this.perfil);
            this.servicioAsignacionRolPerfilImpl.agregarAsignacionRolPerfil(asignacionRolPerfil);

            MensajePopup.crearMensajeRespuesta(request, TITULO_MENSAJE, "asignarRolPerfil.exito", true);
            String msg = Mensajes.EXITO_AGREGAR + "Asignar Rol Perfil, id = "
                    + asignacionRolPerfil.getIdAsignacionRolPerfil();
            log.info(msg);

        }catch (DataAccessException e) {
            // error de acceso a datos
            MensajePopup.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        this.setModel(modelo);
        return "perfil/asignarRolPerfil";
    }
    /**
     * @param modelo
     * @param idAsignacionRolPerfil
     * @param request
     *
     * @return
     */
//______________________________________________________________________________
    @RequestMapping(value="desasignarRolPerfil.htm", method = RequestMethod.GET)
    public String desasignarRolPerfil(Model modelo, Integer idAsignacionRolPerfil
            , HttpServletRequest request){

        if(idAsignacionRolPerfil != null){
            try{
                this.servicioAsignacionRolPerfilImpl.eliminarAsingacionRolPerfil(idAsignacionRolPerfil);
                MensajePopup.crearMensajeRespuesta(request, TITULO_MENSAJE, "desasignarRolPerfil.exito", true);
                String msg = Mensajes.EXITO_AGREGAR + "Desasignar Rol Perfil, id = "+ idAsignacionRolPerfil;
                log.info(msg);
            }catch(DataAccessException e){
                MensajePopup.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
                log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
            }
        }

        this.setModel(modelo);
        return "perfil/asignarRolPerfil";
    }

//______________________________________________________________________________
    /**
     * @param modelo 
     **/
    private void setModel(Model modelo){
        List <AsignacionRolPerfil> listadoAsignacionRolPerfil = this.servicioAsignacionRolPerfilImpl
        .getAsignacionRolPerfilPorPerfil(perfil);

        List <Rol> listadoRolNoAsignado = this.servicioAsignacionRolPerfilImpl
                .getRolesNoAsignadosPorPerfil(perfil);

        // se agregan los objetos que se usaran en la pagina
        modelo.addAttribute("perfil", this.perfil);

        modelo.addAttribute("asignacionRolPerfil", new AsignacionRolPerfil());
        modelo.addAttribute("listadoAsignacionRolPerfil", listadoAsignacionRolPerfil);
        modelo.addAttribute("listadoRolNoAsignado", listadoRolNoAsignado);

    }
}
