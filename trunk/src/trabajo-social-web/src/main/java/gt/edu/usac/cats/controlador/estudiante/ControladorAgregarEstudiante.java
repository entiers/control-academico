/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.controlador.estudiante;

import gt.edu.usac.cats.dominio.Carrera;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.dominio.wrapper.WrapperEstudiante;
import gt.edu.usac.cats.servicio.ServicioEstudiante;
import gt.edu.usac.cats.servicio.ServicioGeneral;
import gt.edu.usac.cats.util.EmailSender;
import gt.edu.usac.cats.util.RequestUtil;
import gt.edu.usac.cats.util.Mensajes;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.mail.MessagingException;
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
 * <p>Esta clase se encuentra registrada en Spring como un controlador. Este
 * controlador esta asociado a la pagina <code>agregarEstudiante</code> y a todas
 * las peticiones que esta pagina realiza.</p>
 *
 * <p>El controlador responde a dos eventos distintos de la pagina antes
 * mencionada, los eventos son:
 * <ul>
 * <li>Creacion de la pagina, este evento se genera cada vez que se solicita
 * la pagina desde algun link u otro controlador. El controlador responde a este
 * evento por medio del metodo {@link crearFormulario(Model modelo)} el cual es
 * el encargado de crear la pagina.</li>
 * <li>Agregar estudiante, este evento se genera desde la pagina asociada a este
 * controlador cuando se solicita agregar un nuevo estudiante. El metodo que
 * responde a este evento es {@link submit(WrapperEstudiante wrapperEstudiante,
 * BindingResult bindingResult, Model modelo, HttpServletRequest request)}</li>
 * </ul>
 * </p>
 *
 * @author Daniel Castillo y Mario Batres
 * @version 1.5
 */
@Controller("controladorAgregarEstudiante")
@RequestMapping(value = "agregarEstudiante.htm")
public class ControladorAgregarEstudiante {

    /**
     * <p>Lleva el nombre del titulo para el mensaje en la pagina.</p>
     */
    private static String TITULO_MENSAJE = "agregarEstudiante.titulo";
//______________________________________________________________________________
    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorAgregarEstudiante.class);
//______________________________________________________________________________
    /**
     * <p>Contiene metodos que permiten el manejo de la informacion relacionada
     * con el estudiante en la base de datos. Este objeto se encuentra registrado
     * como un bean de servicio en Spring, por lo que este es el encargado de
     * inyectar la dependencia.</p>
     */
    @Resource
    private ServicioEstudiante servicioEstudianteImpl;
//______________________________________________________________________________
    /**
     * <p>Contiene metodos basicos de acceso a la base de datos, estos metodos
     * permiten realizar operaciones basicas sobre cualquier tabla de la base
     * de datos.</p>
     */
    @Resource
    private ServicioGeneral servicioGeneralImpl;
//______________________________________________________________________________
    /**
     * <p>Bean de servicio que permite enviar correos electronicos</p>
     */
    @Resource
    private EmailSender emailSender;
//______________________________________________________________________________
    /**
     * <p>Listado de todas las carreras disponibles.</p>
     */
    private List<Carrera> listadoCarreras;
//______________________________________________________________________________
    private List<Semestre> listadoSemestres;
//______________________________________________________________________________
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

        // se trata de cargar el listado de carreras
        try {
            this.listadoCarreras = this.servicioGeneralImpl.listarEntidad(Carrera.class);
        } catch (DataAccessException e) {
            this.listadoCarreras = new ArrayList<Carrera>();
            log.error("ERROR: no se puede cargar el listado de carreras", e);
        }

        this.listadoSemestres = this.servicioGeneralImpl.listarEntidad(Semestre.class, true, "anio");

        // se agregan los objetos que se usaran en la pagina
        modelo.addAttribute("wrapperEstudiante", new WrapperEstudiante());
        //modelo.addAttribute("carreras", this.listadoCarreras);
//        modelo.addAttribute("listadoSemestres", this.listadoSemestres);
//        modelo.addAttribute("situaciones", Situacion.values());

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
     * @param wrapperEstudiante Contiene los datos ingresados en el formulario
     * @param bindingResult Objeto {@link BindingResult}, contiene el resultado de
     *        las validaciones
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @param request Objeto {@link HttpServletRequest}
     * @return String Con la url de la pagina a mostrar
     */
    @RequestMapping(method = RequestMethod.POST)
    public String agregarEstudiante(@Valid WrapperEstudiante wrapperEstudiante, BindingResult bindingResult,
            Model modelo, HttpServletRequest request) {

        modelo.addAttribute("carreras", this.listadoCarreras);

        // se validan los campos ingresados en el formulario, si existen errores
        // se regresa al formulario para que se muestren los mensajes correspondientes
        if(bindingResult.hasErrors())
            return "estudiante/agregarEstudiante";

        try {
            // se obtiene la carrera seleccionada
            //Carrera carrera = this.getCarreraSeleccionada(wrapperEstudiante.getIdCarrera());
            //Semestre semestre = this.servicioGeneralImpl.cargarEntidadPorID(Semestre.class, idSemestre);

            //Situacion situacion = Situacion.valueOf(situacionName);
            // se quita el envoltorio y se trata de agregar al estudiante
            Estudiante estudiante = new Estudiante();
            wrapperEstudiante.quitarWrapper(estudiante);
            //this.servicioEstudianteImpl.agregarEstudiante(estudiante, carrera, semestre, situacion);

            // se envia correo electronico de confirmacion
            this.enviarEmail(estudiante);

            // se registra el evento
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "agregarEstudiante.exito", true);
            String msg = Mensajes.EXITO_AGREGAR + "Estudiante, carne " + estudiante.getCarne();
            log.info(msg);

        } catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, null, "dataAccessException", false);
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
//______________________________________________________________________________
    /**
     * <p>Este metodo permite enviar un correo electronico al estudiante que se
     * agrego al sistema. El correo notifica al estudiante que su registro se
     * realizo con exito, ademas el correo incluye las credenciales de acceso
     * al sistema que fueron asignadas al estudiante.</p>
     *
     * @param destinatario Correo electronico al que se debe de enviar el correo
     */
    private void enviarEmail(Estudiante estudiante) {
        String subject = "Informe de registro (Escuela de Trabajo Social)";
        String mensaje = estudiante.getNombre() +
                "\n\nTu registro en el sitio de la Escuela de Trabajo Social se realizo con exito. " +
                "Para acceder al sitio utiliza los siguientes datos:" +
                "\n\nUSUARIO:  " + estudiante.getCarne() +
                "\nPASSWORD: " + estudiante.getPassword();

        String[] datosCorreo = {subject, estudiante.getEmail(), mensaje};

        try {
            // se trata de enviar el correo
            this.emailSender.enviarCorreo(datosCorreo);

        } catch (MessagingException ex) {
            log.error(Mensajes.MESSAGING_EXCEPTION, ex);

        }
    }
}