/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.controlador.perfil;

import gt.edu.usac.cats.dominio.Perfil;
import gt.edu.usac.cats.servicio.ServicioGeneral;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;

/**
 * Esta clase se encarga de almacenar los salones en la BD.
 * La informacion se pide en la pagina de <code>buscarPerfil.htm</code>.
 *
 * @author Mario Batres
 * @version 1.0
 */

@Controller
@RequestMapping(value="buscarPerfil.htm")
@Scope(value = WebApplicationContext.SCOPE_SESSION)
@SessionAttributes(value = {"listadoPerfil"})
public class ControladorBuscarPerfil implements Serializable{
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
    protected ServicioGeneral servicioGeneralImpl;
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

        this.listadoPerfil = this.servicioGeneralImpl.listarEntidad(Perfil.class, true, "nombre");
        modelo.addAttribute("listadoPerfil", this.listadoPerfil);

        return "perfil/buscarPerfil";
    }
}
