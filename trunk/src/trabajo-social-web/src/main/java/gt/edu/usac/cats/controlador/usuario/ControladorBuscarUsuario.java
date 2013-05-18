/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.controlador.usuario;

import gt.edu.usac.cats.dominio.Usuario;
import gt.edu.usac.cats.dominio.busqueda.DatosBusquedaUsuario;
import gt.edu.usac.cats.servicio.ServicioUsuario;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;

/**
 * <p>Esta clase se encuentra registrada en Spring como un controlador. Este
 * controlador esta asociado a la página <code>buscarUsuario</code> y a todas
 * las peticiones que esta página realiza.</p>
 *
 * <p>El controlador responde a tres eventos distintos de la pagina antes
 * mencionada, los eventos son:
 * <ul>
 * <li>Creacion de la pagina, este evento se genera cada vez que se solicita
 * la pagina desde algun link u otro controlador. El controlador responde a este
 * evento por medio del metodo {@link crearFormulario(Model modelo)} el cual es
 * el encargado de crear la pagina.</li>
 * <li>Buscar usuario, este evento se genera desde la pagina asociada a este
 * controlador cuando se solicita buscar un usuario por cualquiera de los
 * criterios de busqueda de la pagina. El metodo que responde a este evento es
 * {@link buscarUsuario(DatosBusquedaUsuario datosBusquedaUsuario,
 * BindingResult bindingResult, Model modelo, HttpServletRequest request)}</li>
 * <li>Los eventos de paginacion, estos eventos se generan cada vez que se
 * solicitan los resultados anteriores o posteriores en la tabla que muestra los
 * resultados de la busqueda, los metodos que responden a estos eventos son
 * {@link paginarAdelante(Model modelo, HttpServletRequest request)} y
 * {@link paginarAtras(Model modelo, HttpServletRequest request)}</li>
 * </ul>
 * </p>
 *
 * @author Carlos Solórzano
 * @version 1.0
 */
@Controller
@Scope(value = WebApplicationContext.SCOPE_SESSION)
@SessionAttributes(value = {"listadoUsuarios", "datosBusquedaUsuario", "rowCount"})
public class ControladorBuscarUsuario implements Serializable {
/**
     * <p>Lleva el nombre del titulo para el mensaje en la pagina.</p>
     */
    private static String TITULO_MENSAJE = "buscarUsuario.titulo";
//______________________________________________________________________________
    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorBuscarUsuario.class);
//______________________________________________________________________________
    /**
     * <p>Contiene metodos que permiten el manejo de la informacion relacionada
     * con el Usuario en la base de datos. Este objeto se encuentra registrado
     * como un bean de servicio en Spring, por lo que este es el encargado de
     * inyectar la dependencia.</p>
     */
    @Resource
    private ServicioUsuario servicioUsuarioImpl;
//______________________________________________________________________________
    /**
     * <p>Mantiene una lista con los Usuarios devueltos en una busqueda.</p>
     */
    private List<Usuario> listadoUsuarios;
//______________________________________________________________________________
    /**
     * <p>Mantiene los parametros de busqueda ingresados por el usuario.</p>
     */
    private DatosBusquedaUsuario datosBusquedaUsuario;
//______________________________________________________________________________
    /**
     * <p>Mantiene el total de registros que retorna la busqueda.</p>
     */
    private int rowCount;
//______________________________________________________________________________
    /**
     * <p>Constructor de la clase, no realiza ninguna accion.</p>
     */
    public ControladorBuscarUsuario() {}
//______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina <code>buscarUsuario.htm</code>. El metodo se encarga
     * de iniciar los objetos que se usaran en la pagina.</p>
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(value = "buscarUsuario.htm", method = RequestMethod.GET)
    public String crearFormulario(Model modelo) {

        // se agregan los objetos que se usaran en la pagina
        this.listadoUsuarios = new ArrayList<Usuario>();
        this.datosBusquedaUsuario = new DatosBusquedaUsuario();
        modelo.addAttribute("listadoUsuarios", this.listadoUsuarios);
        modelo.addAttribute("datosBusquedaUsuario", this.datosBusquedaUsuario);

        return "usuario/buscarUsuario";
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cuando se solicita una busqueda desde la pagina
     * de buscar Usuario. Las busquedas solo se realizan por el numero de
     * carne, nombre o apellido del Usuario. El metodo realiza los siguientes
     * pasos:
     * <ul>
     * <li>Se valida que al menos se envio un parametro de busqueda</li>
     * <li>Valida los datos ingresados</li>
     * <li>Se delega la busqueda al metodo <code>buscarUsuarios</code></li>
     * </ul>
     * </p>
     *
     * @param datosBusquedaUsuario Contiene los parametros de la busqueda
     * @param bindingResult Objeto {@link BindingResult} que valida los datos
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @param request Peticion HTTP
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(value = "buscarBuscarUsuario.htm", method = RequestMethod.POST)
    public String buscarUsuarios(@Valid DatosBusquedaUsuario datosBusquedaUsuario,
            BindingResult bindingResult, Model modelo, HttpServletRequest request) {

        // se almacenan los parametros de busqueda ingresados en la pagina
        this.datosBusquedaUsuario = datosBusquedaUsuario;

        // los parametros de busqueda no cumplen las reglas de validacion
        if(bindingResult.hasErrors()){
            return "usuario/buscarUsuario";
        }
        this.datosBusquedaUsuario.inicializarPrimerRegistro();
        return this.realizarBusqueda(modelo, request, true);
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo se llama cada vez que se quiere paginar los resultados
     * de la busqueda hacia adelante. Primero se incrementa el primer resultado
     * a mostrar (numero de pagina a mostrar) y luego se llama al metodo
     * <code>realizarBusqueda</code> para que realice la busqueda.</p>
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @param request Peticion HTTP
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(value = "paginarAdelanteBuscarUsuario.htm", method = RequestMethod.POST)
    public String paginarAdelante(Model modelo, HttpServletRequest request) {

        // se aumenta la paginacion
        this.datosBusquedaUsuario.aumentarPrimerRegistro();

        // se mantienen los parametros de busqueda
        modelo.addAttribute("datosBusquedaUsuario", this.datosBusquedaUsuario);

        return this.realizarBusqueda(modelo, request, false);
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo se llama cada vez que se quiere paginar los resultados
     * de la busqueda hacia atras. Primero se disminuye el primer resultado
     * a mostrar (numero de pagina a mostrar) y luego se llama al metodo
     * <code>realizarBusqueda</code> para que realice la busqueda.</p>
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @param request Peticion HTTP
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(value = "paginarAtrasBuscarUsuario.htm", method = RequestMethod.POST)
    public String paginarAtras(Model modelo, HttpServletRequest request) {

        // se disminuye la paginacion
        this.datosBusquedaUsuario.disminuirPrimerRegistro();

        // se mantienen los parametros de busqueda
        modelo.addAttribute("datosBusquedaUsuario", this.datosBusquedaUsuario);

        return this.realizarBusqueda(modelo, request, false);
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de realizar la busqueda de Usuarios segun
     * los parametros de busqueda enviados. El procedimiento para realizar la
     * busqueda es el siguiente:
     * <ul>
     * <li>La busqueda se delegaga a {@link ServicioUsuario}, se le envia
     * el objeto {@link DatosBusquedaUsuario} el cual contiene los parametros
     * de busqueda ingresados por el usuario</li>
     * <li>Si la busqueda no produce resultados se muestra un mensaje popup
     * indicando que no hay resultados que mostrar</li>
     * <li>Si ocurre un error de acceso a la base de datos se muestra un mensaje
     * popup indicando del error</li>
     * </ul>
     * </p>
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @param request Peticion HTTP
     * @param obtenerRowCount Indica si se debe de realizar un rowCount
     * @return String Contiene el nombre de la vista a mostrar
     */
    private String realizarBusqueda(Model modelo, HttpServletRequest request, boolean obtenerRowCount) {

        // se limpia el resultado anterior
        this.listadoUsuarios.clear();
        modelo.addAttribute("listadoUsuarios", this.listadoUsuarios);

        try {
            // se trata de obtener el total de registros de la consulta
            if(obtenerRowCount) {
                this.rowCount = this.servicioUsuarioImpl.rowCount(datosBusquedaUsuario);
            }
            
            // se trata de hacer la busqueda
            List<Usuario> listado = this.servicioUsuarioImpl.getListadoUsuarios(this.datosBusquedaUsuario);
            if(listado.isEmpty()) {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "buscarUsuario.sinResultados", true);
            } else {
                this.listadoUsuarios.addAll(listado);
            }
            // se configuran los botones de la paginacion
            RequestUtil.configurarPaginacion(request, this.datosBusquedaUsuario.getPrimerRegistro(), this.rowCount);

        } catch (HibernateException e) {
            // error de acceso a datos
            this.datosBusquedaUsuario.inicializarPrimerRegistro();

            RequestUtil.crearMensajeRespuesta(request, null, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        // siempre se retorna la pagina de busquedas
        return "usuario/buscarUsuario";
    }
}
