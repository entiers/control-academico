/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.trabajosocial.controlador.curso;

import gt.edu.usac.trabajosocial.dominio.Curso;
import gt.edu.usac.trabajosocial.dominio.wrapper.WrapperCurso;
import gt.edu.usac.trabajosocial.servicio.ServicioCurso;
import gt.edu.usac.trabajosocial.servicio.ServicioGeneral;
import gt.edu.usac.trabajosocial.util.MensajePopup;
import gt.edu.usac.trabajosocial.util.Mensajes;
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
 * @author Mario Batres
 * @version 1.0
 */

@Controller("controladorAgregarCurso")
@RequestMapping(value = "agregarCurso.htm")
public class ControladorAgregarCurso {
//______________________________________________________________________________
    /**
     * <p>
     * Lleva el nombre del titulo para el mensaje en la página
     * <p>
     */
    private static String TITULO_MENSAJE = "agregarCurso.titulo";
//______________________________________________________________________________
    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorAgregarCurso.class);

//______________________________________________________________________________
    /**
     * <p>Contiene metodos que permiten el manejo de la informacion relacionada
     * con el curso en la base de datos. Este objeto se encuentra registrado
     * como un bean de servicio en Spring, por lo que este es el encargado de
     * inyectar la dependencia.</p>
     */
    @Resource
    protected ServicioCurso servicioCursoImpl;

//______________________________________________________________________________
    /**
     * <p>Contiene metodos basicos de acceso a la base de datos, estos metodos
     * permiten realizar operaciones basicas sobre cualquier tabla de la base
     * de datos.</p>
     */
    @Resource
    protected ServicioGeneral servicioGeneralImpl;


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
        modelo.addAttribute("wrapperCurso", new WrapperCurso());

        return "curso/agregarCurso";
    }

    //______________________________________________________________________________
    /**
     * <p>Este metodo es llamado cuando se realiza un SUBMIT desde la pagina de
     * agregar curso. El metodo se encarga de agregar la informacion
     * ingresada en el formulario de la pagina en la base de datos, el procedimiento
     * que sigue el metodo es el siguiente:
     * <ul>
     * <li>Se realiza la validacion de datos ingresados, si algun dato no cumple
     * con las reglas de validacion se retorna a la pagina para que se muestren
     * los mensajes de error</li>
     * <li>Si la validacion tuvo exito se trata de agregar la informacion a la
     * base de datos por medio de {@link ServicioCurso}</li>
     * <li>Se procesa el resultado de la operacion y se le indica a la pagina el
     * mensaje de respuesta que debe de mostrar, el mensaje puede ser de exito o
     * de error</li>
     * </ul></p>
     *
     * @param wrapperCurso Pojo del tipo {@link WrapperCurso}
     * @param bindingResult Objeto {@link BindingResult}, contiene el resultado de
     *        las validaciones
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @param request Objeto {@link HttpServletRequest}
     * @return String Con la url de la pagina a mostrar
     */
    @RequestMapping(method = RequestMethod.POST)
    public String submit(@Valid WrapperCurso wrapperCurso, BindingResult bindingResult,
            Model modelo, HttpServletRequest request) {
        // se validan los campos ingresados en el formulario, si existen errores
        // se regresa al formulario para que se muestren los mensajes correspondientes
        if(bindingResult.hasErrors())
            return "curso/agregarCurso";

        try {

            // se quita el envoltorio y se trata de agregar al estudiante
            Curso curso = new Curso();
            wrapperCurso.quitarWrapper(curso);
            this.servicioCursoImpl.agregarCurso(curso);

            MensajePopup.configurar(request, true, true, TITULO_MENSAJE, "agregarCurso.exito");

            // se registra el evento
            log.info("Agregar codigo, codigo: " + curso.getCodigo());

        } catch (DataIntegrityViolationException e) {
            // el carne ingresado ya existe
            MensajePopup.configurar(request, false, false, TITULO_MENSAJE, "agregarEstudiante.dataIntegrityViolationException");
            log.warn(Mensajes.DATA_INTEGRITY_VIOLATION_EXCEPTION, e);

        } catch (DataAccessException e) {
            // error de acceso a datos
            MensajePopup.configurar(request, false, false, TITULO_MENSAJE, "dataAccessException");
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        return "curso/agregarCurso";
    }
}
