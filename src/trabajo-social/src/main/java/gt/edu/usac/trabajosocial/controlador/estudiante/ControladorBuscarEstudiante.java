/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.controlador.estudiante;

import gt.edu.usac.trabajosocial.dominio.Estudiante;
import gt.edu.usac.trabajosocial.servicio.ServicioEstudiante;
import gt.edu.usac.trabajosocial.util.BotonesPaginacion;
import gt.edu.usac.trabajosocial.util.MensajePopup;
import gt.edu.usac.trabajosocial.util.Mensajes;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Daniel Castillo
 * @version 2.0
 */
@Controller("controladorBuscarEstudiante")
public class ControladorBuscarEstudiante {

    /**
     * <p>Lleva el nombre del titulo para el mensaje en la página.</p>
     */
    private static String TITULO_MENSAJE = "buscarEstudiante.titulo";

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

    /**
     * <p>Mantiene una lista con los estudiantes devueltos en una busqueda.</p>
     */
    private List<Estudiante> listadoEstudiantes;

    /**
     * <p>Mantiene los parametros de busqueda ingresados por el usuario.</p>
     */
    private DatosBusquedaEstudiante datosBusquedaEstudiante;

    /**
     * <p>Mantiene el total de registros que retorna la busqueda.</p>
     */
    private int rowCount;


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
        this.datosBusquedaEstudiante = new DatosBusquedaEstudiante();
        modelo.addAttribute("listadoEstudiantes", this.listadoEstudiantes);
        modelo.addAttribute("datosBusquedaEstudiante", this.datosBusquedaEstudiante);

        return "estudiante/buscarEstudiante";
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cuando se solicita una busqueda desde la pagina
     * de buscar estudiante. Las busquedas solo se realizan por el numero de
     * carne, nombre o apellido del estudiante. El metodo realiza los siguientes
     * pasos:
     * <ul>
     * <li>Se valida que al menos se envio un parametro de busqueda</li>
     * <li>Valida los datos ingresados</li>
     * <li>Se delega la busqueda al metodo <code>buscarEstudiantes</code></li>
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

        // se almacenan los parametros de busqueda ingresados en la pagina
        this.datosBusquedaEstudiante = datosBusquedaEstudiante;

        // no se enviaron parametros de busqueda
        if(this.datosBusquedaEstudiante.isEmpty())
            return "estudiante/buscarEstudiante";

        // los parametros de busqueda no cumplen las reglas de validacion
        if(bindingResult.hasErrors())
            return "estudiante/buscarEstudiante";

        this.datosBusquedaEstudiante.inicializarPrimerRegistro();
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
    @RequestMapping(value = "paginarAdelanteBuscarEstudiante.htm", method = RequestMethod.POST)
    public String paginarAdelante(Model modelo, HttpServletRequest request) {

        // se aumenta la paginacion
        this.datosBusquedaEstudiante.aumentarPrimerRegistro();

        // se mantienen los parametros de busqueda
        modelo.addAttribute("datosBusquedaEstudiante", this.datosBusquedaEstudiante);

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
    @RequestMapping(value = "paginarAtrasBuscarEstudiante.htm", method = RequestMethod.POST)
    public String paginarAtras(Model modelo, HttpServletRequest request) {

        // se disminuye la paginacion
        this.datosBusquedaEstudiante.disminuirPrimerRegistro();

        // se mantienen los parametros de busqueda
        modelo.addAttribute("datosBusquedaEstudiante", this.datosBusquedaEstudiante);

        return this.realizarBusqueda(modelo, request, false);
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de realizar la busqueda de estudiantes segun
     * los parametros de busqueda enviados. El procedimiento para realizar la
     * busqueda es el siguiente:
     * <ul>
     * <li>La busqueda se delegaga a {@link ServicioEstudiante}, se le envia
     * el objeto {@link DatosBusquedaEstudiante} el cual contiene los parametros
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
        this.listadoEstudiantes.clear();
        modelo.addAttribute("listadoEstudiantes", this.listadoEstudiantes);

        try {
            // se trata de obtener el total de registros de la consulta
            if(obtenerRowCount)
                this.rowCount = this.servicioEstudianteImpl.rowCount(datosBusquedaEstudiante);

            // se trata de hacer la busqueda
            List<Estudiante> listado = this.servicioEstudianteImpl.getListadoEstudiantes(this.datosBusquedaEstudiante);
            if(listado.isEmpty())
                MensajePopup.configurar(request, true, true, TITULO_MENSAJE, "buscarEstudiante.sinResultados");
            else
                this.listadoEstudiantes.addAll(listado);

            // se configuran los botones de la paginacion
            BotonesPaginacion.configurar(request, this.datosBusquedaEstudiante.getPrimerRegistro(), this.rowCount);

        } catch (HibernateException e) {
            // error de acceso a datos
            MensajePopup.configurar(request, false, false, TITULO_MENSAJE, "dataAccessException");
            this.datosBusquedaEstudiante.inicializarPrimerRegistro();

            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        // siempre se retorna la pagina de busquedas
        return "estudiante/buscarEstudiante";
    }
}
