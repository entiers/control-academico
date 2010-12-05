/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.controlador.usuario;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;


/**
 *
 * @author cats
 */
@Controller
public class ControladorRecordarUsuario {
    private static Logger log = Logger.getLogger(ControladorAsignarPerfilUsuario.class);
//______________________________________________________________________________
    private static final String TITULO_MENSAJE = "modificarDatosPersonales.titulo";
//______________________________________________________________________________
    private String email;
    //______________________________________________________________________________
    /**
     * @param modelo
     * @param request
     *
     * @return
     */
    @RequestMapping(value="recordarUsuario.htm", method = RequestMethod.GET)
    public String crearFormulario(Model modelo,HttpServletRequest request) {
        this.email = "correo@gmail.com";
        modelo.addAttribute("email",this.email);
        return "login/recordarUsuario.htm";
    }

}
