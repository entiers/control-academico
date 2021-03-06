/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */
package gt.edu.usac.cats.controlador.estudiante;

import gt.edu.usac.cats.dominio.Carrera;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.busqueda.DatosBusquedaEstudiante;
import gt.edu.usac.cats.enums.ControlReporte;
import gt.edu.usac.cats.servicio.ServicioEstudiante;
import gt.edu.usac.cats.util.RequestUtil;
import gt.edu.usac.cats.util.Mensajes;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;

/**
 * <p>Esta clase se encuentra registrada en Spring como un controlador. Este
 * controlador esta asociado a la pagina
 * <code>buscarEstudiante</code> y a todas las peticiones que esta pagina
 * realiza.</p>
 *
 * <p>El controlador responde a tres eventos distintos de la pagina antes
 * mencionada, los eventos son: <ul> <li>Creacion de la pagina, este evento se
 * genera cada vez que se solicita la pagina desde algun link u otro
 * controlador. El controlador responde a este evento por medio del metodo
 * {@link crearFormulario(Model modelo)} el cual es el encargado de crear la
 * pagina.</li> <li>Buscar estudiante, este evento se genera desde la pagina
 * asociada a este controlador cuando se solicita buscar un estudiante por
 * cualquiera de los criterios de busqueda de la pagina. El metodo que responde
 * a este evento es null {@link buscarEstudiantes(DatosBusquedaEstudiante datosBusquedaEstudiante,
 * BindingResult bindingResult, Model modelo, HttpServletRequest request)}</li>
 * <li>Los eventos de paginacion, estos eventos se generan cada vez que se
 * solicitan los resultados anteriores o posteriores en la tabla que muestra los
 * resultados de la busqueda, los metodos que responden a estos eventos son
 * {@link paginarAdelante(Model modelo, HttpServletRequest request)} y
 * {@link paginarAtras(Model modelo, HttpServletRequest request)}</li> </ul>
 * </p>
 *
 * @author Daniel Castillo
 * @version 1.0
 */
@Controller
@Scope(value = WebApplicationContext.SCOPE_SESSION)
@SessionAttributes(value = {"listadoEstudiantes", "listadoCarreras"})
public class ControladorBuscarEstudiante implements Serializable {

    /**
     * <p>Lleva el nombre del titulo para el mensaje en la pagina.</p>
     */
    private static String TITULO_MENSAJE = "buscarEstudiante.titulo";
//______________________________________________________________________________
    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorBuscarEstudiante.class);
//______________________________________________________________________________
    /**
     * <p>Contiene metodos que permiten el manejo de la informacion relacionada
     * con el estudiante en la base de datos. Este objeto se encuentra
     * registrado como un bean de servicio en Spring, por lo que este es el
     * encargado de inyectar la dependencia.</p>
     */
    @Resource
    private ServicioEstudiante servicioEstudianteImpl;
//______________________________________________________________________________
    /**
     * <p>Mantiene una lista con los estudiantes devueltos en una busqueda.</p>
     */
    private List<Estudiante> listadoEstudiantes;
//______________________________________________________________________________
    private List<Carrera> listadoCarreras;
//______________________________________________________________________________

    /**
     * <p>Constructor de la clase, no realiza ninguna accion.</p>
     */
    public ControladorBuscarEstudiante() {
    }
//______________________________________________________________________________

    private void agregarAtributosReportes(Model modelo, boolean realizarBusqueda) {
        modelo.addAttribute("reporteListadoCarreraSimultaneas", ControlReporte.LISTADO_CARRERAS_SIMULTANEAS);
        modelo.addAttribute("reporteAsignacionPorCarrera", ControlReporte.LISTADO_ASIGNACION_POR_CARRERA);

        if (realizarBusqueda) {
            this.listadoCarreras = this.servicioEstudianteImpl.listarEntidad(Carrera.class, true, "codigo");
        }

        if (listadoCarreras != null) {
            modelo.addAttribute("listadoCarreras", this.listadoCarreras);
        }
    }
//______________________________________________________________________________

    public void agregarAtributosDefault(Model modelo, DatosBusquedaEstudiante datosBusquedaEstudiante, boolean realizarBusqueda) {
        if (listadoEstudiantes != null) {
            modelo.addAttribute("listadoEstudiantes", this.listadoEstudiantes);
        }

        if (datosBusquedaEstudiante != null) {
            modelo.addAttribute("datosBusquedaEstudiante", datosBusquedaEstudiante);
        }
        this.agregarAtributosReportes(modelo, realizarBusqueda);
    }
//______________________________________________________________________________

    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina
     * <code>buscarEstudiante.htm</code>. El metodo se encarga de iniciar los
     * objetos que se usaran en la pagina.</p>
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     * seran usados en la pagina
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(value = "buscarEstudiante.htm", method = RequestMethod.GET)
    public String crearFormulario(Model modelo) {
        this.listadoEstudiantes = null;
        this.agregarAtributosDefault(modelo, new DatosBusquedaEstudiante(), true);
        return "estudiante/buscarEstudiante";
    }

    @RequestMapping(value = "buscarEstudiantePag.htm", method = RequestMethod.GET)
    public String buscarEstudiantePag(Model modelo, DatosBusquedaEstudiante datosBusquedaEstudiante) {
        this.agregarAtributosDefault(modelo, datosBusquedaEstudiante, false);
        return "estudiante/buscarEstudiante";
    }
//______________________________________________________________________________

    /**
     * <p>Este metodo se ejecuta cuando se solicita una busqueda desde la pagina
     * de buscar estudiante. Las busquedas solo se realizan por el numero de
     * carne, nombre o apellido del estudiante. El metodo realiza los siguientes
     * pasos: <ul> <li>Se valida que al menos se envio un parametro de
     * busqueda</li> <li>Valida los datos ingresados</li> <li>Se delega la
     * busqueda al metodo
     * <code>buscarEstudiantes</code></li> </ul> </p>
     *
     * @param datosBusquedaEstudiante Contiene los parametros de la busqueda
     * @param bindingResult Objeto {@link BindingResult} que valida los datos
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     * seran usados en la pagina
     * @param request Peticion HTTP
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(value = "buscarEstudiante.htm", method = RequestMethod.POST)
    public String buscarEstudiantes(@Valid DatosBusquedaEstudiante datosBusquedaEstudiante,
            BindingResult bindingResult, Model modelo, HttpServletRequest request) {
        // los parametros de busqueda no cumplen las reglas de validacion
        if (!datosBusquedaEstudiante.isEmpty() && !bindingResult.hasErrors()) {
            try {
                this.listadoEstudiantes = this.servicioEstudianteImpl.getListadoEstudiantes(datosBusquedaEstudiante);
                if (this.listadoEstudiantes.isEmpty()) {
                    RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "buscarEstudiante.sinResultados", true);
                }
            } catch (DataAccessException e) {
                RequestUtil.crearMensajeRespuesta(request, null, "dataAccessException", false);
                log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
            }
        }

        this.agregarAtributosDefault(modelo, datosBusquedaEstudiante, false);
        return "estudiante/buscarEstudiante";
    }
}
