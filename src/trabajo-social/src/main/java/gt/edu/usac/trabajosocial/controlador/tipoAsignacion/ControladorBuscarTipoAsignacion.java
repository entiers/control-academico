/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.controlador.tipoAsignacion;

import gt.edu.usac.trabajosocial.dominio.TipoAsignacion;
import gt.edu.usac.trabajosocial.servicio.ServicioTipoAsignacion;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Esta clase se encarga de la búsqueda de Tipo de Asignacion en la BD
 * para mostrarlos en la página de <code>buscarTipoAsignacion.htm</code>.
 *
 * @author Mario Batres
 * @version 1.0
 */
@Controller("controladorBuscarTipoAsignacion")
@RequestMapping(value = "buscarTipoAsignacion.htm")
public class ControladorBuscarTipoAsignacion {

//______________________________________________________________________________
    /**
     * <p>
     * Lleva el nombre del titulo para el mensaje en la página
     * </p>
     */
    private static final String TITULO_MENSAJE = "buscarHorario.titulo";

//______________________________________________________________________________
    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorBuscarTipoAsignacion.class);
//______________________________________________________________________________

    /**
     * <p>Listado de todas las cursos disponibles.</p>
     */
    protected List <TipoAsignacion> listadoTipoAsignacion;
//______________________________________________________________________________
    /**
     * <p>Contiene metodos que permiten el manejo de la informacion relacionada
     * con el semestre en la base de datos. Este objeto se encuentra registrado
     * como un bean de servicio en Spring, por lo que este es el encargado de
     * inyectar la dependencia.</p>
     */
    @Resource
    protected ServicioTipoAsignacion servicioTipoAsignacionImpl;
//______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina <code>agregarTipoAsignacion.htm</code>. El metodo se encarga
     * de iniciar los objetos que se usaran en la pagina.</p>
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(method = RequestMethod.GET)
    public String crearFormulario(Model modelo) {

        this.listadoTipoAsignacion = this.servicioTipoAsignacionImpl.buscarTipoAsignacion();
        modelo.addAttribute("listadoTipoAsignacion", this.listadoTipoAsignacion);

        return "tipoAsignacion/buscarTipoAsignacion";
    }
}
