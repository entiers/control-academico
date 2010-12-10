/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.controlador.estudiante;

import gt.edu.usac.trabajosocial.dominio.Carrera;
import gt.edu.usac.trabajosocial.dominio.Estudiante;
import gt.edu.usac.trabajosocial.dominio.wrapper.WrapperEstudiante;
import gt.edu.usac.trabajosocial.servicio.ServicioEstudiante;
import gt.edu.usac.trabajosocial.servicio.ServicioGeneral;
import gt.edu.usac.trabajosocial.util.MensajePopup;
import gt.edu.usac.trabajosocial.util.Mensajes;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
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
@Controller("controladorAgregarEstudiante")
@RequestMapping(value = "agregarEstudiante.htm")
public class ControladorAgregarEstudiante {

    /**
     * <p>Lleva el nombre del titulo para el mensaje en la página.</p>
     */
    private static String TITULO_MENSAJE = "agregarEstudiante.titulo";

    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorAgregarEstudiante.class);

    /**
     * <p>Contiene metodos que permiten el manejo de la informacion relacionada
     * con el estudiante en la base de datos. Este objeto se encuentra registrado
     * como un bean de servicio en Spring, por lo que este es el encargado de
     * inyectar la dependencia.</p>
     */
    @Resource
    protected ServicioEstudiante servicioEstudianteImpl;

    /**
     * <p>Contiene metodos basicos de acceso a la base de datos, estos metodos
     * permiten realizar operaciones basicas sobre cualquier tabla de la base
     * de datos.</p>
     */
    @Resource
    protected ServicioGeneral servicioGeneralImpl;

    /**
     * <p>Listado de todas las carreras disponibles.</p>
     */
    private List<Carrera> listadoCarreras;


    /**
     * <p>Constructor de la clase, no realiza ninguna accion.</p>
     */
    public ControladorAgregarEstudiante() {}
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
    @RequestMapping(method = RequestMethod.GET)
    public String crearFormulario(Model modelo) {

        // se agregan los objetos que se usaran en la pagina
        modelo.addAttribute("wrapperEstudiante", new WrapperEstudiante());

        this.listadoCarreras = this.servicioGeneralImpl.listarEntidad(Carrera.class);
        modelo.addAttribute("carreras", this.listadoCarreras);

        return "estudiante/agregarEstudiante";
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo es llamado cuando se realiza un SUBMIT desde la pagina de
     * agregar estudiante. El metodo se encarga de agregar la informacion
     * ingresada en el formulario de la pagina en la base de datos, el procedimiento
     * que sigue el metodo es el siguiente:
     * <ul>
     * <li>Se realiza la validacion de datos ingresados, si algun dato no cumple
     * con las reglas de validacion se retorna a la pagina para que se muestren
     * los mensajes de error</li>
     * <li>Si la validacion tuvo exito se trata de agregar la informacion a la
     * base de datos por medio de {@link ServicioEstudiante}</li>
     * <li>Se procesa el resultado de la operacion y se le indica a la pagina el
     * mensaje de respuesta que debe de mostrar, el mensaje puede ser de exito o
     * de error</li>
     * </ul></p>
     *
     * @param estudiante Pojo del tipo {@link Estudiante}
     * @param resultado Objeto {@link BindingResult}, contiene el resultado de
     *        las validaciones
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @param request Objeto {@link HttpServletRequest}
     * @return String Con la url de la pagina a mostrar
     */
    @RequestMapping(method = RequestMethod.POST)
    public String submit(@Valid WrapperEstudiante wrapperEstudiante, BindingResult bindingResult,
            Model modelo, HttpServletRequest request) {

        modelo.addAttribute("carreras", this.listadoCarreras);
        
        // se validan los campos ingresados en el formulario, si existen errores
        // se regresa al formulario para que se muestren los mensajes correspondientes
        if(bindingResult.hasErrors())
            return "estudiante/agregarEstudiante";

        try {
            // se obtiene la carrera seleccionada
            Carrera carrera = this.getCarreraSeleccionada(wrapperEstudiante.getIdCarrera());

            // se quita el envoltorio y se trata de agregar al estudiante
            Estudiante estudiante = new Estudiante();
            wrapperEstudiante.quitarWrapper(estudiante);
            this.servicioEstudianteImpl.agregarEstudiante(estudiante, carrera);

            MensajePopup.configurar(request, true, true, TITULO_MENSAJE, "agregarEstudiante.exito");

            // se registra el evento
            log.info("Agregar estudiante, carne: " + estudiante.getCarne());

        } catch (DataIntegrityViolationException e) {
            // el carne ingresado ya existe
            MensajePopup.configurar(request, false, false, TITULO_MENSAJE, "agregarEstudiante.dataIntegrityViolationException");
            log.warn(Mensajes.DATA_INTEGRITY_VIOLATION_EXCEPTION, e);

        } catch (DataAccessException e) {
            // error de acceso a datos
            MensajePopup.configurar(request, false, false, TITULO_MENSAJE, "dataAccessException");
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        return "estudiante/agregarEstudiante";
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de obtener el objeto {@link Carrera} que se
     * selecciono en el combo box de la pagina. El metodo itera la lista de
     * carreras hasta que encuentra la carrera seleccionada, y despues de
     * encontrarla la retorna.</p>
     *
     * @param idCarrera Identificador de la Carrera
     * @return Carrera
     */
    private Carrera getCarreraSeleccionada(short idCarrera) {
        for(Carrera carrera : this.listadoCarreras) {
            if(carrera.getIdCarrera() == idCarrera)
                return carrera;
        }
        return null;
    }

}