/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.controlador.pensum;

import gt.edu.usac.cats.dominio.Carrera;
import gt.edu.usac.cats.dominio.Curso;
import gt.edu.usac.cats.dominio.Pensum;
import gt.edu.usac.cats.dominio.wrapper.WrapperCursoPensum;
import gt.edu.usac.cats.dominio.wrapper.WrapperPensum;
import gt.edu.usac.cats.servicio.ServicioCarrera;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
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
 *
 * @author Daniel Castillo
 * @version 1.0
 */
@Controller
@Scope(value = WebApplicationContext.SCOPE_SESSION)
@SessionAttributes(value = {"listadoPensum", "listadoCarreras", "listadoCursos",
"pensumSeleccionado"})
public class ControladorControlPensum implements Serializable{

    /**
     * <p>Lleva el nombre del titulo para el mensaje en la página.</p>
     */
    private static String TITULO_MENSAJE = "controlPensum.titulo";
//______________________________________________________________________________
    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorControlPensum.class);
//______________________________________________________________________________
    /**
     * <p>Contiene metodos que permiten el manejo de la informacion relacionada
     * con el pensum en la base de datos. Este objeto se encuentra registrado
     * como un bean de servicio en Spring, por lo que este es el encargado de
     * inyectar la dependencia.</p>
     */
    @Resource
    protected ServicioCarrera servicioCarreraImpl;
//______________________________________________________________________________
    /**
     * <p>Listado de todos los pensum disponibles.</p>
     */
    private List<Pensum> listadoPensum;
//______________________________________________________________________________
    /**
     * <p>Listado de todas las carreras disponibles.</p>
     */
    private List<Carrera> listadoCarreras;
//______________________________________________________________________________
    /**
     * <p>Listado de todas las carreras disponibles.</p>
     */
    private List<Curso> listadoCursos;
//______________________________________________________________________________
    /**
     * <p>Contiene temporalmente el pensum al que se le van a agregar cursos.</p>
     */
    private Pensum pensumSeleccionado;
//______________________________________________________________________________
    /**
     * <p>Constructor de la clase, no realiza ninguna accion.</p>
     */
    public ControladorControlPensum() {}
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
    @RequestMapping(value = "controlPensum.htm", method = RequestMethod.GET)
    public String crearFormulario(Model modelo) {

        // se cargan los listado de pensum y carreras
        this.cargarListados();

        // se agregan los objetos usados por la pagina
        modelo.addAttribute("wrapperPensum", new WrapperPensum());
        modelo.addAttribute("wrapperCursoPensum", new WrapperCursoPensum());
        modelo.addAttribute("listadoPensum", this.listadoPensum);
        modelo.addAttribute("listadoCarreras", this.listadoCarreras);
        modelo.addAttribute("listadoCursos", this.listadoCursos);

        return "pensum/controlPensum";
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo es llamado cuando se realiza un SUBMIT desde el formulario
     * de agregar nuevo pensum. El metodo se encarga de agregar la informacion
     * ingresada en el formulario en la base de datos, el procedimiento que
     * sigue el metodo es el siguiente:
     * <ul>
     * <li>Se realiza la validacion de datos ingresados, si algun dato no cumple
     * con las reglas de validacion se retorna a la pagina para que se muestren
     * los mensajes de error</li>
     * <li>Si la validacion tuvo exito se trata de agregar la informacion a la
     * base de datos por medio de {@link ServicioPensum}</li>
     * <li>Se procesa el resultado de la operacion y se le indica a la pagina el
     * mensaje de respuesta que debe de mostrar, el mensaje puede ser de exito o
     * de error</li>
     * </ul></p>
     *
     * @param wrapperPensum Contiene los datos ingresados en el formulario
     * @param bindingResult Objeto {@link BindingResult}, contiene el resultado de
     *        las validaciones
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @param request Objeto {@link HttpServletRequest}
     * @return String Con la url de la pagina a mostrar
     */
    @RequestMapping(value = "agregarControlPensum.htm", method = RequestMethod.POST)
    public String agregarPensum(@Valid WrapperPensum wrapperPensum, BindingResult bindingResult,
            Model modelo, HttpServletRequest request) {

        modelo.addAttribute("wrapperCursoPensum", new WrapperCursoPensum());
        modelo.addAttribute("listadoPensum", this.listadoPensum);
        modelo.addAttribute("listadoCarreras", this.listadoCarreras);
        modelo.addAttribute("listadoCursos", this.listadoCursos);

        // se validan los campos ingresados en el formulario, si existen errores
        // se regresa al formulario para que se muestren los mensajes correspondientes
        if(bindingResult.hasErrors()) {
            request.setAttribute("mostrarPopupAgregar", true);
            return "pensum/controlPensum";
        }

        // se obtiene la carrera seleccionada
        Carrera carrera = null;//this.getCarreraSeleccionada(wrapperPensum.getIdCarrera());

        // se quita el envoltorio y se trata de agregar el pensum
        Pensum pensum = new Pensum();
        wrapperPensum.quitarWrapper(pensum);

        try {
            this.servicioCarreraImpl.agregarPensum(pensum, carrera);

            // se agrega el pensum nuevo al listado
            this.listadoPensum.add(0, pensum);

            // se registra el evento
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "controlPensum.agregarExito", true);
            String msg = Mensajes.EXITO_AGREGAR + "Pensum, codigo " + pensum.getCodigo();
            log.info(msg);

        } catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, null, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        return "pensum/controlPensum";
    }
//______________________________________________________________________________
    /**
     * 
     * @param modelo
     * @param request
     * @return
     */
    @RequestMapping(value = "borrarControlPensum.htm", method = RequestMethod.POST)
    public String borrarPensum(Model modelo, HttpServletRequest request) {

        // se agregan los objetos usados por la pagina
        modelo.addAttribute("wrapperPensum", new WrapperPensum());
        modelo.addAttribute("wrapperCursoPensum", new WrapperCursoPensum());
        modelo.addAttribute("listadoPensum", this.listadoPensum);
        modelo.addAttribute("listadoCarreras", this.listadoCarreras);
        modelo.addAttribute("listadoCursos", this.listadoCursos);

        // se obtiene el id del pensum seleccionado
        Pensum pensum = this.getPensumSeleccionado(request.getParameter("idPensumBorrar"));
        try {
            boolean b = this.servicioCarreraImpl.borrarPensum(pensum);
            if(b) {
                // se registra el evento
                this.listadoPensum.remove(pensum);
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "controlPensum.borrarExito", true);
                String msg = Mensajes.EXITO_BORRAR + "Pensum, codigo " + pensum.getCodigo();
                log.info(msg);
            } else {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "controlPensum.borrarError", true);
            }
        } catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, null, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        return "pensum/controlPensum";
    }
//______________________________________________________________________________
    /**
     * 
     * @param modelo
     * @param request
     * @return
     */
    @RequestMapping(value = "activarControlPensum.htm", method = RequestMethod.POST)
    public String activarPensum(Model modelo, HttpServletRequest request) {

        // se agregan los objetos usados por la pagina
        modelo.addAttribute("wrapperPensum", new WrapperPensum());
        modelo.addAttribute("wrapperCursoPensum", new WrapperCursoPensum());
        modelo.addAttribute("listadoPensum", this.listadoPensum);
        modelo.addAttribute("listadoCarreras", this.listadoCarreras);
        modelo.addAttribute("listadoCursos", this.listadoCursos);

        // se obtiene el id del pensum seleccionado
        Pensum pensum = this.getPensumSeleccionado(request.getParameter("idPensumActivar"));
        try {
            boolean b = this.servicioCarreraImpl.activarPensum(pensum);
            if(b) {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "controlPensum.activarExito", true);
                String msg = Mensajes.EXITO_ACTIVAR + "Pensum, codigo " + pensum.getCodigo();
                log.info(msg);
            } else {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "controlPensum.activarError", true);
            }
        } catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, null, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        return "pensum/controlPensum";
    }
//______________________________________________________________________________
    /**
     *
     * @param modelo
     * @param request
     * @return
     */
    @RequestMapping(value = "caducarControlPensum.htm", method = RequestMethod.POST)
    public String caducarPensum(Model modelo, HttpServletRequest request) {

        // se agregan los objetos usados por la pagina
        modelo.addAttribute("wrapperPensum", new WrapperPensum());
        modelo.addAttribute("wrapperCursoPensum", new WrapperCursoPensum());
        modelo.addAttribute("listadoPensum", this.listadoPensum);
        modelo.addAttribute("listadoCarreras", this.listadoCarreras);
        modelo.addAttribute("listadoCursos", this.listadoCursos);

        // se obtiene el id del pensum seleccionado
        Pensum pensum = this.getPensumSeleccionado(request.getParameter("idPensumCaducar"));
        try {
            boolean b = this.servicioCarreraImpl.caducarPensum(pensum);
            if(b) {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "controlPensum.caducarExito", true);
                String msg = Mensajes.EXITO_CADUCAR + "Pensum, codigo " + pensum.getCodigo();
                log.info(msg);
            } else {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "controlPensum.caducarError", true);
            }
        } catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, null, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        return "pensum/controlPensum";
    }
//______________________________________________________________________________
    /**
     *
     * @param modelo
     * @param request
     * @return
     */
    @RequestMapping(value = "popupAgregarCursoControlPensum.htm", method = RequestMethod.POST)
    public String mostrarPopupAgregarCurso(Model modelo, HttpServletRequest request) {

        // se agregan los objetos usados por la pagina
        modelo.addAttribute("wrapperPensum", new WrapperPensum());
        modelo.addAttribute("wrapperCursoPensum", new WrapperCursoPensum());
        modelo.addAttribute("listadoPensum", this.listadoPensum);
        modelo.addAttribute("listadoCarreras", this.listadoCarreras);
        modelo.addAttribute("listadoCursos", this.listadoCursos);

        // se obtiene el id del pensum seleccionado
        this.pensumSeleccionado = this.getPensumSeleccionado(request.getParameter("idPensumAgregarCurso"));
        try {
            this.listadoCursos.clear();
            this.listadoCursos.addAll(this.servicioCarreraImpl.buscarCursosNoAsignados(this.pensumSeleccionado));
            request.setAttribute("mostrarPopupAgregarCurso", true);
        } catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, null, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        return "pensum/controlPensum";
    }
//______________________________________________________________________________
    /**
     *
     * @param modelo
     * @param request
     * @return
     */
    @RequestMapping(value = "agregarCursoControlPensum.htm", method = RequestMethod.POST)
    public String agregarCursoPensum(@Valid WrapperCursoPensum wrapperCursoPensum, BindingResult bindingResult,
            Model modelo, HttpServletRequest request) {

        // se agregan los objetos usados por la pagina
        modelo.addAttribute("wrapperPensum", new WrapperPensum());
        modelo.addAttribute("listadoPensum", this.listadoPensum);
        modelo.addAttribute("listadoCarreras", this.listadoCarreras);
        modelo.addAttribute("listadoCursos", this.listadoCursos);

        // se validan los campos ingresados en el formulario, si existen errores
        // se regresa al formulario para que se muestren los mensajes correspondientes
        if(bindingResult.hasErrors()) {
            request.setAttribute("mostrarPopupAgregarCurso", true);
            return "pensum/controlPensum";
        }

        // se obtiene el id del curso seleccionado
        Curso curso = this.getCursoSeleccionado(wrapperCursoPensum.getIdCurso());
        try {
            // se trata de crear la asignacion del curso al pensum
//            this.servicioCarreraImpl.agregarCursoPensum(this.pensumSeleccionado, curso, wrapperCursoPensum.isObligatorio());

            // se registra el evento
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "controlPensum.agregarCursoExito", true);
            String msg = Mensajes.EXITO_AGREGAR + "AsignacionCursoPensum, curso " +
                    curso.getCodigo() + ", pensum " + this.pensumSeleccionado.getCodigo();
            log.info(msg);

        } catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, null, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        return "pensum/controlPensum";
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de crear un listado de {@link Carrera} y un
     * listado de {@link Pensum}. En el caso de que alguno de los dos listados
     * no se pueda crear, ambos listados se dejan en vacios.</p>
     */
    private void cargarListados() {

        this.listadoCursos = new ArrayList<Curso>();

        try {
            // se trata de obtener el listado de pensums
            this.listadoPensum = this.servicioCarreraImpl.listarEntidad(Pensum.class);
            
            // se trata de obtener el listado de carreras
            this.listadoCarreras = this.servicioCarreraImpl.listarEntidad(Carrera.class);

        } catch (DataAccessException e) {
            // si alguno de los listados no se puede obtener, se crean ambos
            // listados en blanco
            this.listadoPensum = new ArrayList<Pensum>();
            this.listadoCarreras = new ArrayList<Carrera>();

            // se registra el error en el log
            String msg = Mensajes.ERROR_CARGAR_LISTADO + "Pensum y Carreras";
            log.error(msg, e);
        }
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
            if(carrera.getIdCarrera() == idCarrera) {
                return carrera;
            }
        }
        return null;
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de obtener el pensum que fue seleccionado en
     * el listado de pensum.</p>
     *
     * @param idPensum Identificador del pensum
     * @return Pensum
     */
    private Pensum getPensumSeleccionado(String idPensum) {
        short id = Short.parseShort(idPensum);
        for(Pensum p : this.listadoPensum) {
            if(p.getIdPensum() == id) {
                return p;
            }
        }
        return null;
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de obtener el curso que fue seleccionado en
     * el listado de cursos.</p>
     *
     * @param idCurso Identificador del curso
     * @return Curso
     */
    private Curso getCursoSeleccionado(short idCurso) {
        for(Curso c : this.listadoCursos) {
            if(c.getIdCurso() == idCurso) {
                return c;
            }
        }
        return null;
    }
}
