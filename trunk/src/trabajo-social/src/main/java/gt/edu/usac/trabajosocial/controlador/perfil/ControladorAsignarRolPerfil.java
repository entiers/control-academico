/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.trabajosocial.controlador.perfil;

import gt.edu.usac.trabajosocial.dominio.Perfil;
import gt.edu.usac.trabajosocial.dominio.Rol;
import gt.edu.usac.trabajosocial.servicio.ServicioPerfil;
import gt.edu.usac.trabajosocial.servicio.ServicioRol;
import gt.edu.usac.trabajosocial.servicio.impl.ServicioAsignacionRolPerfilImpl;
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
    private ServicioRol servicioRolImpl;
//______________________________________________________________________________
    private List <Rol> listadoRol;

    @Resource
    private ServicioAsignacionRolPerfilImpl servicioAsignacionRolPerfilImpl;

//______________________________________________________________________________
    @RequestMapping(method = RequestMethod.GET)
    public String crearFormulario(Model modelo, short idPerfil, HttpServletRequest request) {

        this.perfil = this.servicioPerfilImpl.getPerfilPorID(idPerfil);

        if(this.perfil == null){
            MensajePopup.crearMensajeRespuesta(request, TITULO_MENSAJE, "buscarPerfil.sinResultados", false);
            return "perfil/buscarPerfil";
        }

        this.listadoRol = this.servicioRolImpl.getRoles();

        this.servicioAsignacionRolPerfilImpl.getAsignacionRolPerfilPorPerfil(perfil);

        // se agregan los objetos que se usaran en la pagina
        modelo.addAttribute("perfil", this.perfil);
        modelo.addAttribute("listadoRol", this.listadoRol);
        return "perfil/asignarRolPerfil";
    }

    public String submit(Model modelo, String [] asignacionRolPerfils, HttpServletRequest request){
        modelo.addAttribute("perfil", this.perfil);
        modelo.addAttribute("listadoRol", this.listadoRol);

        return "perfil/asignarRolPerfil";
    }
}
