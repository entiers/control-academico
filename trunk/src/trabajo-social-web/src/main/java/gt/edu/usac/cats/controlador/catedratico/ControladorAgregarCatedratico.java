/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */
package gt.edu.usac.cats.controlador.catedratico;

import gt.edu.usac.cats.dominio.Catedratico;
import gt.edu.usac.cats.dominio.Escuela;
import gt.edu.usac.cats.dominio.Usuario;
import gt.edu.usac.cats.dominio.wrapper.WrapperCatedratico;
import gt.edu.usac.cats.servicio.ServicioCatedratico;
import gt.edu.usac.cats.servicio.ServicioUsuario;
import gt.edu.usac.cats.util.EmailSenderVelocity;
import gt.edu.usac.cats.util.RequestUtil;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.velocity.FabricaTemplateVelocity;
import gt.edu.usac.cats.velocity.contexto.NuevoUsuario;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;

/**
 * <p>Esta clase se encuentra registrada en Spring como un controlador. Este
 * controlador esta asociado a la pagina <code>agregarCatedratico</code> y a todas
 * las peticiones que esta pagina realiza.</p>
 *
 * <p>El controlador responde a dos eventos distintos de la pagina antes
 * mencionada, los eventos son:
 * <ul>
 * <li>Creacion de la pagina, este evento se genera cada vez que se solicita
 * la pagina desde algun link u otro controlador. El controlador responde a este
 * evento por medio del metodo {@link crearFormulario(Model modelo)} el cual es
 * el encargado de crear la pagina.</li>
 * <li>Agregar catedratico, este evento se genera desde la pagina asociada a este
 * controlador cuando se solicita agregar un nuevo catedratico. El metodo que
 * responde a este evento es {@link submit(WrapperCatedratico wrapperCatedratico,
 * BindingResult bindingResult, Model modelo, HttpServletRequest request)}</li>
 * </ul>
 * </p>
 *
 * @author Daniel Castillo
 * @version 1.0
 */
@Controller
@RequestMapping(value = "agregarCatedratico.htm")
@Scope(value = WebApplicationContext.SCOPE_SESSION)
@SessionAttributes(value={"listadoEscuelas"})
public class ControladorAgregarCatedratico implements Serializable{

    /**
     * <p>Lleva el nombre del titulo para el mensaje en la pagina.</p>
     */
    private static String TITULO_MENSAJE = "agregarCatedratico.titulo";
//______________________________________________________________________________
    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorAgregarCatedratico.class);
//______________________________________________________________________________
    /**
     * <p>Contiene metodos que permiten el manejo de la informacion relacionada
     * con el catedratico en la base de datos. Este objeto se encuentra registrado
     * como un bean de servicio en Spring, por lo que este es el encargado de
     * inyectar la dependencia.</p>
     */
    @Resource
    protected ServicioCatedratico servicioCatedraticoImpl;
//______________________________________________________________________________
    /**
     * <p>Bean de servicio que permite enviar correos electronicos</p>
     */
    @Resource
    private EmailSenderVelocity emailSenderVelocity;
//______________________________________________________________________________
    /**
     * <p>Bean de servicio para validad email unico</p>
     */
    @Resource
    private ServicioUsuario servicioUsuarioImpl;
//______________________________________________________________________________
    /**
     * <p>Listado de todas las escuelas disponibles.</p>
     */
    private List<Escuela> listadoEscuelas;
//______________________________________________________________________________

    /**
     * <p>Constructor de la clase, no realiza ninguna accion.</p>
     */
    public ControladorAgregarCatedratico() {
    }
//______________________________________________________________________________

    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina <code>agregarCatedratico.htm</code>. El metodo se encarga
     * de iniciar los objetos que se usaran en la pagina.</p>
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(method = RequestMethod.GET)
    public String crearFormulario(Model modelo) {

        // se agregan los objetos que se usaran en la pagina
        modelo.addAttribute("wrapperCatedratico", new WrapperCatedratico());

        this.listadoEscuelas = this.servicioCatedraticoImpl.listarEntidad(Escuela.class);
        modelo.addAttribute("escuelas", this.listadoEscuelas);

        return "catedratico/agregarCatedratico";
    }
//______________________________________________________________________________

    /**
     * <p>Este metodo es llamado cuando se realiza un SUBMIT desde la pagina de
     * agregar catedratico. El metodo se encarga de agregar la informacion
     * ingresada en el formulario de la pagina en la base de datos, el procedimiento
     * que sigue el metodo es el siguiente:
     * <ul>
     * <li>Se realiza la validacion de datos ingresados, si algun dato no cumple
     * con las reglas de validacion se retorna a la pagina para que se muestren
     * los mensajes de error</li>
     * <li>Si la validacion tuvo exito se trata de agregar la informacion a la
     * base de datos por medio de {@link ServicioCatedratico}</li>
     * <li>Se procesa el resultado de la operacion y se le indica a la pagina el
     * mensaje de respuesta que debe de mostrar, el mensaje puede ser de exito o
     * de error</li>
     * </ul></p>
     *
     * @param wrapperCatedratico Pojo del tipo {@link Catedratico}
     * @param resultado Objeto {@link BindingResult}, contiene el resultado de
     *        las validaciones
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @param request Objeto {@link HttpServletRequest}
     * @return String Con la url de la pagina a mostrar
     */
    @RequestMapping(method = RequestMethod.POST)
    public String submit(@Valid WrapperCatedratico wrapperCatedratico, BindingResult bindingResult,
            Model modelo, HttpServletRequest request) {

        modelo.addAttribute("escuelas", this.listadoEscuelas);

        // se validan los campos ingresados en el formulario, si existen errores
        // se regresa al formulario para que se muestren los mensajes correspondientes
        if (bindingResult.hasErrors()) {
            return "catedratico/agregarCatedratico";
        }

        try {
            //Validar email unico
            if (this.servicioUsuarioImpl.getUsuarioPorEmail(wrapperCatedratico.getEmail()) != null) {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "usuario.correoYaExiste", false);
                return "catedratico/editarCatedratico";
            }

            // se obtiene la carrera seleccionada
            Escuela escuela = this.getEscuelaSeleccionada(wrapperCatedratico.getIdEscuela());

            // se quita el envoltorio y se trata de agregar al catedratico
            Catedratico catedratico = new Catedratico();
            wrapperCatedratico.quitarWrapper(catedratico);
            this.servicioCatedraticoImpl.agregarCatedratico(catedratico, escuela);

            // se envia correo electronico de confirmacion
            this.enviarEmail(catedratico);

            // se registra el evento
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "agregarCatedratico.exito", true);
            String msg = Mensajes.EXITO_AGREGAR + "Catedratico, codigo " + catedratico.getCodigo();
            log.info(msg);

        } catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, null, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        return "catedratico/agregarCatedratico";
    }
//______________________________________________________________________________

    /**
     * <p>Este metodo se encarga de obtener el objeto {@link Carrera} que se
     * selecciono en el combo box de la pagina. El metodo itera la lista de
     * carreras hasta que encuentra la carrera seleccionada, y despues de
     * encontrarla la retorna.</p>
     *
     * @param idEscuela Identificador de la Escuela
     * @return Escuela
     */
    private Escuela getEscuelaSeleccionada(short idEscuela) {
        for (Escuela escuela : this.listadoEscuelas) {
            if (escuela.getIdEscuela() == idEscuela) {
                return escuela;
            }
        }
        return null;
    }
//______________________________________________________________________________

    /**
     * <p>Este metodo permite enviar un correo electronico al catedratico que se
     * agrego al sistema. El correo notifica al catedratico que su registro se
     * realizo con exito, ademas el correo incluye las credenciales de acceso
     * al sistema que fueron asignadas al estudiante.</p>
     *
     * @param destinatario Correo electronico al que se debe de enviar el correo
     */
    private void enviarEmail(Catedratico catedratico) {
        Usuario usuario = catedratico.getUsuario();

        NuevoUsuario nuevoUsuario = new NuevoUsuario();
        nuevoUsuario.setNombreUsuario(usuario.getNombreUsuario());
        nuevoUsuario.setPassword(usuario.getPassword());
        nuevoUsuario.setNombre(catedratico.getNombre() + " " + catedratico.getApellido());


        try {
            this.emailSenderVelocity.enviarCorreo("Nuevo catedratico",
                    catedratico.getEmail(),
                    FabricaTemplateVelocity.NUEVO_USUARIO,
                    nuevoUsuario);

        } catch (MailException e) {
            log.error(Mensajes.MAIL_EXCEPTION, e);
        } catch (IOException e) {
            log.error(Mensajes.IO_EXCEPTION, e);
        } catch (MessagingException e) {
            log.error(Mensajes.MESSAGING_EXCEPTION, e);
        }

    }
}
