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
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
@RequestMapping(value="asignarRolPerfil.htm")
public class ControladorAsignarRolPerfil {

    private static final String TITULO_MENSAJE = "asiganarRolPerfil.titulo";
//______________________________________________________________________________
    private Perfil perfil;
//______________________________________________________________________________
    @Resource
    private ServicioPerfil servicioPerfilImpl;
//______________________________________________________________________________
    @Resource
    private ServicioAsignacionRolPerfil servicioAsignacionRolPerfilImpl;

//______________________________________________________________________________
    @RequestMapping(method = RequestMethod.GET)
    public String crearFormulario(Model modelo, short idPerfil, HttpServletRequest request) {

        this.perfil = this.servicioPerfilImpl.getPerfilPorID(idPerfil);

        if(this.perfil == null){
            MensajePopup.crearMensajeRespuesta(request, TITULO_MENSAJE, "buscarPerfil.sinResultados", false);
            return "perfil/buscarPerfil";
        }        

        List <Rol> listadoRolAsignado = this.servicioAsignacionRolPerfilImpl.getRolesAsignadosPorPerfil(perfil);
        List <Rol> listadoRolNoAsignado = this.servicioAsignacionRolPerfilImpl.getRolesNoAsignadosPorPerfil(perfil);
        // se agregan los objetos que se usaran en la pagina
        modelo.addAttribute("perfil", this.perfil);
        AsignacionRolPerfil asignacionRolPerfil = new AsignacionRolPerfil();
        asignacionRolPerfil.setPerfil(perfil);

        modelo.addAttribute("asignacionRolPerfil", asignacionRolPerfil);
        modelo.addAttribute("listadoRolAsignado", listadoRolAsignado);
        modelo.addAttribute("listadoRolNoAsignado", listadoRolNoAsignado);

        return "perfil/asignarRolPerfil";
    }

    public String submit(Model modelo, String [] asignacionRolPerfils, HttpServletRequest request){
        modelo.addAttribute("perfil", this.perfil);
        

        return "perfil/asignarRolPerfil";
    }
}
