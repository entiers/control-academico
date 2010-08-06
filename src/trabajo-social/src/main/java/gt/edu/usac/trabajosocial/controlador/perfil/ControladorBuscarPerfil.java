/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.controlador.perfil;

import gt.edu.usac.trabajosocial.dominio.Perfil;
import gt.edu.usac.trabajosocial.servicio.ServicioPerfil;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Esta clase se encarga de almacenar los salones en la BD.
 * La informacion se pide en la pagina de <code>buscarPerfil.htm</code>.
 *
 * @author Mario Batres
 * @version 1.0
 */

@Controller
@RequestMapping(value="buscarPerfil.htm")
public class ControladorBuscarPerfil {
    /**
     * <p>Listado de todas las perfiles disponibles.</p>
     */
    protected List <Perfil> listadoPerfil;
//______________________________________________________________________________
    /**
     * <p>Contiene metodos que permiten el manejo de la informacion relacionada
     * con el perfil en la base de datos. Este objeto se encuentra registrado
     * como un bean de servicio en Spring, por lo que este es el encargado de
     * inyectar la dependencia.</p>
     */
    @Resource
    protected ServicioPerfil servicioPerfilImpl;
//______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina <code>agregarPerfil.htm</code>. El metodo se encarga
     * de iniciar los objetos que se usaran en la pagina.</p>
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(method = RequestMethod.GET)
    public String crearFormulario(Model modelo) {

        this.listadoPerfil = this.servicioPerfilImpl.getPerfiles();
        modelo.addAttribute("listadoPerfil", this.listadoPerfil);

        return "perfil/buscarPerfil";
    }
}
