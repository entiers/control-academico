/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.controlador.perfil;

import gt.edu.usac.cats.dominio.AsignacionRolPerfil;
import gt.edu.usac.cats.dominio.Perfil;
import gt.edu.usac.cats.dominio.Rol;
import gt.edu.usac.cats.servicio.ServicioAsignacionRolPerfil;
import gt.edu.usac.cats.servicio.ServicioGeneral;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
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
    private ServicioGeneral servicioGeneralImpl;

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
    public String crearFormulario(Model modelo, Short idPerfil, 
                        String accion,HttpServletRequest request) {
        try
        {
            if(idPerfil == null){
                return "perfil/buscarPerfil";
            }

            this.perfil = this.servicioGeneralImpl.cargarEntidadPorID(Perfil.class, idPerfil);

            if(this.perfil == null){
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "buscarPerfil.sinResultados", false);
                return "perfil/buscarPerfil";
            }        

            if(accion != null){
                if(accion.equals("ADDOK"))
                    RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "asignarRolPerfil.exito", true);                
                else if(accion.equals("DELOK"))
                    RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "desasignarRolPerfil.exito", true);
            }
        }catch (Exception e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
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
            this.servicioGeneralImpl.agregar(asignacionRolPerfil);
            
            String msg = Mensajes.EXITO_AGREGAR + "Asignar Rol Perfil, id = "
                    + asignacionRolPerfil.getIdAsignacionRolPerfil();
            log.info(msg);

        }catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        this.setModel(modelo);
        return "redirect:asignarRolPerfil.htm?accion=ADDOK&idPerfil=" + this.perfil.getIdPerfil();
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
                AsignacionRolPerfil asignacionRolPerfil = this.servicioGeneralImpl
                        .cargarEntidadPorID(AsignacionRolPerfil.class, idAsignacionRolPerfil);

                this.servicioGeneralImpl.borrar(asignacionRolPerfil);                
                String msg = Mensajes.EXITO_AGREGAR + "Desasignar Rol Perfil, id = "+ idAsignacionRolPerfil;
                log.info(msg);
            }catch(DataAccessException e){
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
                log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
            }
        }

        this.setModel(modelo);
        return "redirect:asignarRolPerfil.htm?accion=DELOK&idPerfil=" + this.perfil.getIdPerfil();
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
