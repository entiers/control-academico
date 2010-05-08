/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.controlador.estudiante;

import gt.edu.usac.trabajosocial.dominio.Estudiante;
import gt.edu.usac.trabajosocial.servicio.ServicioEstudiante;
import gt.edu.usac.trabajosocial.util.MensajePopup;
import gt.edu.usac.trabajosocial.util.Mensajes;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Daniel Castillo
 * @version 1.0
 */
@Controller("controladorBuscarEstudiante")
public class ControladorBuscarEstudiante {

    //______________________________________________________________________________
    /**
     * <p>
     * Lleva el nombre del titulo para el mensaje en la página
     * <p>
     */
    private static String TITULO_MENSAJE = "editarEstudiante.titulo";

    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorBuscarEstudiante.class);

    /**
     * <p>Contiene metodos que permiten el manejo de la informacion relacionada
     * con el estudiante en la base de datos. Este objeto se encuentra registrado
     * como un bean de servicio en Spring, por lo que este es el encargado de
     * inyectar la dependencia.</p>
     */
    @Resource
    protected ServicioEstudiante servicioEstudianteImpl;

    private List<Estudiante> listadoEstudiantes;


    /**
     * <p>Constructor de la clase, no realiza ninguna accion.</p>
     */
    public ControladorBuscarEstudiante() {}
//______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina <code>buscarEstudiante.htm</code>. El metodo se encarga
     * de iniciar los objetos que se usaran en la pagina.</p>
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(value = "buscarEstudiante.htm", method = RequestMethod.GET)
    public String crearFormulario(Model modelo) {

        // se agregan los objetos que se usaran en la pagina
        this.listadoEstudiantes = new ArrayList<Estudiante>();
        modelo.addAttribute("listadoEstudiantes", this.listadoEstudiantes);
        modelo.addAttribute("datosBusquedaEstudiante", new DatosBusquedaEstudiante());

        return "estudiante/buscarEstudiante";
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cuando se solicita una busqueda desde la pagina
     * de buscar estudiante. Las busquedas solo se realizan por el numero de
     * carne, nombre o apellido del estudiante. El metodo realiza los siguientes
     * pasos:
     * <ul>
     * <li>Valida los datos ingresados</li>
     * <li>Si los datos son validos se realiza la busqueda y se muestra un listado
     * de estudiantes que cumplan con parametros de busqueda ingresados, si la
     * busqueda no genera resultados se muestra un mensaje popup</li>
     * <li>Si ocurre un error de acceso a la base de datos se muestra un mensaje
     * popup indicando del error</li>
     * </ul>
     * </p>
     *
     * @param datosBusquedaEstudiante Contiene los parametros de la busqueda
     * @param bindingResult Objeto {@link BindingResult} que valida los datos
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @param request Peticion HTTP
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(value = "buscarBuscarEstudiante.htm", method = RequestMethod.POST)
    public String buscarEstudiantes(@Valid DatosBusquedaEstudiante datosBusquedaEstudiante,
            BindingResult bindingResult, Model modelo, HttpServletRequest request) {

        this.listadoEstudiantes.clear();
        modelo.addAttribute("listadoEstudiantes", this.listadoEstudiantes);

        if(bindingResult.hasErrors())
            return "estudiante/buscarEstudiante";

        if(datosBusquedaEstudiante.isEmpty())
            return "estudiante/buscarEstudiante";
        
        try {
            // se trata de hacer la busqueda
            List<Estudiante> listado = this.servicioEstudianteImpl.getListadoEstudiantes(datosBusquedaEstudiante, true, "carne");
            if(listado.isEmpty())
                MensajePopup.configurar(request, true, true, TITULO_MENSAJE,  "buscarEstudiante.sinResultados");
            else
                this.listadoEstudiantes.addAll(listado);

        } catch (DataAccessException e) {
            // error de acceso a datos
            MensajePopup.configurar(request, false, false, TITULO_MENSAJE, "dataAccessException");

            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        return "estudiante/buscarEstudiante";
    }

}
