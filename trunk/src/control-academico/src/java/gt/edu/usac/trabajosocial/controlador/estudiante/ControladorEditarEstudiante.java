/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.controlador.estudiante;

import gt.edu.usac.trabajosocial.dominio.Estudiante;
import gt.edu.usac.trabajosocial.dominio.wrapper.WrapperEstudiante;
import gt.edu.usac.trabajosocial.servicio.ServicioEstudiante;
import gt.edu.usac.trabajosocial.util.MensajePopup;
import gt.edu.usac.trabajosocial.util.Mensajes;
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
@Controller("controladorEditarEstudiante")
public class ControladorEditarEstudiante {

/**
     * <p>Lleva el nombre del titulo para el mensaje en la página</p>
     */
    private static String TITULO_MENSAJE = "editarEstudiante.titulo";

    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorEditarEstudiante.class);

    /**
     * <p>Contiene metodos que permiten el manejo de la informacion relacionada
     * con el estudiante en la base de datos. Este objeto se encuentra registrado
     * como un bean de servicio en Spring, por lo que este es el encargado de
     * inyectar la dependencia.</p>
     */
    @Resource
    protected ServicioEstudiante servicioEstudianteImpl;

    /**
     * <p>Se utiliza para mantener todos los datos del estudiante que se
     * encontro en la busqueda.</p>
     */
    private Estudiante estudiante;


    /**
     * <p>Constructor de la clase, no realiza ninguna accion.</p>
     */
    public ControladorEditarEstudiante() {}
//______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina <code>agregarEstudiante.htm</code>. El metodo se encarga
     * de iniciar los objetos que se usaran en la pagina.</p>
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(value = "editarEstudiante.htm", method = RequestMethod.GET)
    public String crearFormulario(Model modelo) {

        // se agregan los objetos que se usaran en la pagina
        modelo.addAttribute("wrapperEstudiante", new WrapperEstudiante());
        modelo.addAttribute("datosBusquedaEstudiante", new DatosBusquedaEstudiante());

        return "estudiante/editarEstudiante";
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cuando se solicita una busqueda desde la pagina
     * de editar estudiante. Las busquedas solo se realizan por el numero de
     * carne del estudiante. El metodo realiza los siguientes pasos:
     * <ul>
     * <li>Valida que el numero de carne ingresado sea valido</li>
     * <li>Si el carne es valido se realiza la busqueda y se muestra la
     * informacion del estudiante en la pagina, si la busqueda no genera
     * resultados se muestra un mensaje popup</li>
     * <li>Si ocurre un error de acceso a la base de datos se muestra un mensaje
     * popup indicando del error</li>
     * </ul>
     * </p>
     *
     * @param datosBusquedaEstudiante Contiene los parametros de la busqueda, en
     *        este caso solo el numero de carne
     * @param bindingResult Objeto {@link BindingResult} que valida los datos
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @param request Peticion HTTP
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(value = "buscarEditarEstudiante.htm", method = RequestMethod.POST)
    public String buscarEstudiante(@Valid DatosBusquedaEstudiante datosBusquedaEstudiante,
            BindingResult bindingResult, Model modelo, HttpServletRequest request) {

        // se crea el envoltorio para el estudiante
        WrapperEstudiante wrapperEstudiante = new WrapperEstudiante();
        modelo.addAttribute("wrapperEstudiante", wrapperEstudiante);

        // se obtiene el carne ingresado para realizar la busqueda
        String carne = datosBusquedaEstudiante.getCarneBusqueda();

        if(carne.isEmpty() || bindingResult.hasErrors())
            return "estudiante/editarEstudiante";

        try {
            // se realiza la busqueda del estudiante
            this.estudiante = this.servicioEstudianteImpl.buscarEstudiantePorCarne(carne);

            if(this.estudiante == null)
                MensajePopup.configurar(request, true, true, TITULO_MENSAJE, "editarEstudiante.sinResultados");
            else
                wrapperEstudiante.agregarWrapper(this.estudiante);

        } catch (DataAccessException e) {
            // error de acceso a datos
            MensajePopup.configurar(request, false, false, TITULO_MENSAJE, "dataAccessException");
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        return "estudiante/editarEstudiante";
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cuando se presiona el boton de editar de la
     * pagina. El metodo se encarga de actualizar la informacion del estudiante
     * que se obtuvo en la busqueda. El metodo realiza las siguiente acciones:
     * <ul>
     * <li>Realiza las validaciones de los datos del formulario</li>
     * <li>Delega la funcion de actualizacion a {@link ServicioEstudiante}</li>
     * <li>Muestra un mensaje popup con el resultado de la operacion</li>
     * </ul></p>
     * 
     * @param estudiante Pojo del tipo {@link Estudiante}
     * @param bindingResult Ojeto {@link BindingResult} que realiza las validaciones
     * @param modelo Objeto {@link Model} que contiene los objeto de la pagina
     * @param request Objeto {@link HttpServletRequest}
     * @return String
     */
    @RequestMapping(value = "editarEstudiante.htm", method = RequestMethod.POST)
    public String submit(@Valid WrapperEstudiante wrapperEstudiante, BindingResult bindingResult,
            Model modelo, HttpServletRequest request) {

        modelo.addAttribute("datosBusquedaEstudiante", new DatosBusquedaEstudiante());

        // se validan los campos ingresados en el formulario, si existen errores
        // se regresa al formulario para que se muestren los mensajes correspondientes
        // en este caso se pregunta si la cantidad de errores es mayor a 1
        // debido a que no se esta realizando actualizacion de la carrera, por
        // lo tanto no se utiliza el campo idCarrera y como este tiene asociado
        // un validador entonces siempre dara error
        if(bindingResult.hasErrors() && bindingResult.getErrorCount() > 1)
            return "estudiante/editarEstudiante";

        try {
            // se quita el envoltorio y se trata de actualizar al estudiante
            wrapperEstudiante.quitarWrapper(this.estudiante);
            this.servicioEstudianteImpl.actualizarEstudiante(this.estudiante);

            MensajePopup.configurar(request, true, true, TITULO_MENSAJE ,"editarEstudiante.exito");
            log.info("Actualizar estudiante, carne: " + this.estudiante.getCarne());

        } catch (DataAccessException e) {
            // error de acceso a datos
            MensajePopup.configurar(request, false, false, TITULO_MENSAJE, "dataAccessException");
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        return "estudiante/editarEstudiante";
    }
}
