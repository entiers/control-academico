/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */
package gt.edu.usac.cats.controlador.asignacion;

import gt.edu.usac.cats.dominio.Asignacion;
import gt.edu.usac.cats.dominio.AsignacionCursoPensum;
import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.DetalleAsignacion;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.Horario;
import gt.edu.usac.cats.dominio.PensumEstudianteCarrera;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.dominio.Usuario;
import gt.edu.usac.cats.dominio.busqueda.DatosAsignacion;
import gt.edu.usac.cats.enums.TipoActividad;
import gt.edu.usac.cats.enums.TipoAsignacion;
import gt.edu.usac.cats.enums.TipoHorario;
import gt.edu.usac.cats.enums.TipoRubro;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import java.io.Serializable;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
@Controller
@Scope(value = WebApplicationContext.SCOPE_SESSION)
@SessionAttributes(value = {
    "semestre", "usuario", "estudiante", "listaAEC",
    "asignacionEstudianteCarrera", "pensumEstudianteCarrera", "listaAsignacionCursoPensum", 
    "listaHorario"
})
public class ControladorAsignacionCursos extends ControladorAbstractoAsignacion implements Serializable {
//______________________________________________________________________________    

    @Autowired
    private Semestre semestre;
//______________________________________________________________________________    
    @Autowired
    private Usuario usuario;
//______________________________________________________________________________    
    @Autowired
    private Estudiante estudiante;
//______________________________________________________________________________    
    @Autowired
    private List<AsignacionEstudianteCarrera> listaAEC;
//______________________________________________________________________________    
    @Autowired
    private AsignacionEstudianteCarrera asignacionEstudianteCarrera;
//______________________________________________________________________________    
    @Autowired
    private PensumEstudianteCarrera pensumEstudianteCarrera;
//______________________________________________________________________________    
    @Autowired
    private List<AsignacionCursoPensum> listaAsignacionCursoPensum;
//______________________________________________________________________________    
    @Autowired
    private List<Horario> listaHorario;
//______________________________________________________________________________
    private static Logger log = Logger.getLogger(ControladorAsignacionCursos.class);
//______________________________________________________________________________
    private static final String TITULO_MENSAJE = "miscursos.asignacionCursos.titulo";
//  _____________________________________________________________________________

    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina
     * <code>asignacionCursos.htm</code>. El metodo se encarga de validar: <ul>
     * <li>Que el usuario logueado sea un estudiante</li> <li>Que el estudiante
     * se encuentre inscrito</li> <li>Que el periodo de asignacion de cursos
     * este vigente</li> <li>Que el estudiante tengo aprobado el curso de
     * computacion</li> </ul>
     *
     * Asi mismo se encarga de iniciar los objetos que se usaran en la
     * pagina.</p>
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     * seran usados en la pagina
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(value = "asignacionCursos.htm", method = RequestMethod.GET)
    public String asignacionCursos(Model modelo, HttpServletRequest request) {
        try {
            DatosAsignacion datosAsignacion = new DatosAsignacion();

            //Obteniendo curso de computacion
            //this.cursoComputacion = this.servicioGeneralImpl.cargarEntidadPorID(Curso.class, 38);

            //Obteniendo semestre actual
            this.semestre = (Semestre) this.servicioSemestreImpl.getSemestreActivo();

            //Buscando usuario logueado por nombre
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            this.usuario = this.servicioUsuarioImpl.cargarUsuarioPorNombre(auth.getName().toString());

            //Validando que el usuario se haya encontrado y sea un estudiante
            if (this.usuario != null & this.usuario.getEstudiantes().toArray().length > 0) {
                this.estudiante = (Estudiante) this.usuario.getEstudiantes().toArray()[0];
            } else {
                modelo.addAttribute("errorEntidad", true);
                return "asignacion/asignacionCursos";
            }

            boolean inscrito =
                    this.servicioAsignacionEstudianteCarreraImpl.estaEstudianteInscrito(estudiante);
            //Validando que el estudiante se encuentre inscrito
            if (!inscrito) {
                modelo.addAttribute("estudianteNoInscrito", true);
                return "asignacion/asignacionCursos";
            }

            //Validar curso de computacion aprobado
            /*if(!servicioCursoAprobadoImpl.esCursoAprobado(asignacionEstudianteCarrera, cursoComputacion)){
             modelo.addAttribute("cursoComputacion",true);
             return "asignacion/asignacionCursos";
             }*/

            //Validar periodo de asignacion de cursos de semestre
            if (this.servicioCalendarioActividadesImpl.esFechaActividadValida(TipoActividad.ASIGNACION_SEMESTRE,
                    this.semestre,
                    new java.util.Date())) {
                datosAsignacion.setTipoAsignacion(TipoAsignacion.ASIGNACION_CURSOS_SEMESTRE);
                datosAsignacion.setTipoHorario(TipoHorario.SEMESTRE);
            } //Validar periodo de asignacion de cursos de vacaciones
            else if (this.servicioCalendarioActividadesImpl.esFechaActividadValida(TipoActividad.ASIGNACION_VACACIONES,
                    semestre,
                    new java.util.Date())) {
                datosAsignacion.setTipoAsignacion(TipoAsignacion.ASIGNACION_CURSOS_VACACIONES);
                datosAsignacion.setTipoHorario(TipoHorario.VACACIONES);
                datosAsignacion.setTipoRubro(TipoRubro.VACACIONES);
            } //Validar periodo de asignacion de cursos de primera retrasada
            else if (this.servicioCalendarioActividadesImpl.esFechaActividadValida(TipoActividad.ASIGNACION_PRIMERA_RESTRASADA,
                    semestre,
                    new java.util.Date())) {
                datosAsignacion.setTipoAsignacion(TipoAsignacion.ASIGNACION_PRIMERA_RETRASADA);
                datosAsignacion.setTipoHorario(TipoHorario.PRIMERA_RETRASADA);
                datosAsignacion.setTipoRubro(TipoRubro.PRIMERA_RETRASADA);
            } //Validar periodo de asignacion de cursos de segunda retrasada
            else if (this.servicioCalendarioActividadesImpl.esFechaActividadValida(TipoActividad.ASIGNACION_SEGUNDA_RETRASADA,
                    semestre,
                    new java.util.Date())) {
                datosAsignacion.setTipoAsignacion(TipoAsignacion.ASIGNACION_SEGUNDA_RETRASADA);
                datosAsignacion.setTipoHorario(TipoHorario.SEGUNDA_RETRASADA);
                datosAsignacion.setTipoRubro(TipoRubro.SEGUNDA_RETRASADA);
            } else {
                modelo.addAttribute("periodoInvalido", true);
                return "asignacion/asignacionCursos";
            }

            this.listaAEC = this.servicioAsignacionEstudianteCarreraImpl.getAsignacionEstudianteCarreraPorEstudiante(estudiante);
            modelo.addAttribute("listaAEC", this.listaAEC);
            modelo.addAttribute("datosAsignacion", datosAsignacion);

        } catch (Exception e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }
        return "asignacion/asignacionCursos";
    }
//  _____________________________________________________________________________

    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * POST de la pagina
     * <code>asignacionCursos.htm</code>. El metodo se encarga de inicializar
     * las listas de cursos y horarios disponibles para la carrera seleccionada
     * en el paso 1 anterior de la asignacion.
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     * seran usados en la pagina
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(value = "asignacionCursos.htm", method = RequestMethod.POST)
    public String asignacionCursos(@Valid DatosAsignacion datosAsignacion,
            BindingResult bindingResult,
            Model modelo,
            HttpServletRequest request) {
        String retorno = "redirect:index.htm";
        try {
            this.asignacionEstudianteCarrera = this.servicioAsignacionEstudianteCarreraImpl.cargarEntidadPorID(AsignacionEstudianteCarrera.class, datosAsignacion.getIdAsignacionEstudianteCarrera());
            pensumEstudianteCarrera = servicioPensumEstudianteCarrera.getPensumEstudianteCarreraValido(asignacionEstudianteCarrera);

            if (pensumEstudianteCarrera == null) {
                modelo.addAttribute("noPensumValido", true);
                return "asignacion/asignacionCursos";
            }

            this.listaAsignacionCursoPensum = this.servicioCursoImpl.getCursoAsignacion(pensumEstudianteCarrera.getPensum(),
                    this.semestre, datosAsignacion.getTipoHorario());

            if (this.listaAsignacionCursoPensum.isEmpty() && (datosAsignacion.getTipoAsignacion() != TipoAsignacion.ASIGNACION_PRIMERA_RETRASADA
                    || datosAsignacion.getTipoAsignacion() != TipoAsignacion.ASIGNACION_SEGUNDA_RETRASADA)) {
                modelo.addAttribute("noExisteHorario", true);
                return "asignacion/asignacionCursos";
            }

            this.listaHorario = this.servicioHorarioImpl.getHorario(this.listaAsignacionCursoPensum.get(0), this.semestre, datosAsignacion.getTipoHorario());

            if (datosAsignacion.getTipoAsignacion() == TipoAsignacion.ASIGNACION_CURSOS_SEMESTRE) {
                retorno = "asignacion/asignacionSemestre";
            } else if (datosAsignacion.getTipoAsignacion() == TipoAsignacion.ASIGNACION_CURSOS_VACACIONES) {
                retorno = "asignacion/asignacionVacaciones";
            } else if (datosAsignacion.getTipoAsignacion() == TipoAsignacion.ASIGNACION_PRIMERA_RETRASADA
                    | datosAsignacion.getTipoAsignacion() == TipoAsignacion.ASIGNACION_SEGUNDA_RETRASADA) {

                List<DetalleAsignacion> listaDetalleAsignacion = servicioDetalleAsignacionImpl
                        .getListadoDetalleAsignacion(semestre,
                        asignacionEstudianteCarrera,
                        TipoAsignacion.ASIGNACION_CURSOS_SEMESTRE);
                modelo.addAttribute("datosAsignacion", datosAsignacion);
                modelo.addAttribute("totalAsignaciones", listaDetalleAsignacion.size());
                modelo.addAttribute("listadoDetalleAsignacion", listaDetalleAsignacion);
                return "asignacion/asignacionRetrasada";
            }

            datosAsignacion.setTotalCursos(0);

            modelo.addAttribute("datosAsignacion", datosAsignacion);
            modelo.addAttribute("listaAsignacionCursoPensum", this.listaAsignacionCursoPensum);
            modelo.addAttribute("listaHorario", this.listaHorario);
        } catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }
        return retorno;
    }
//  _____________________________________________________________________________

    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina
     * <code>asignacionExitosa.htm</code>. El metodo se encarga de inicializar
     * las listas de detalleAsignacion y Asignacion en base al parametro que
     * contiene el id de la asignacion a enviar.
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     * seran usados en la pagina
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(value = "asignacionExitosa.htm", method = RequestMethod.GET)
    public String asignacionExitosa(@RequestParam Integer iascsvr,
            Model modelo,
            HttpServletRequest request) {
        Asignacion asignacion = this.servicioGeneralImpl.cargarEntidadPorID(Asignacion.class, iascsvr);
        List<DetalleAsignacion> listaDetalleAsignacion = this.servicioDetalleAsignacionImpl.getListadoDetalleAsignacion(asignacion);

        modelo.addAttribute("asignacion", asignacion);
        modelo.addAttribute("listaAsignacion", listaDetalleAsignacion);
        return "asignacion/asignacionExitosa";
    }

//  _____________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina
     * <code>asignacionExitosa.htm</code>. El metodo se encarga de inicializar
     * las listas de detalleAsignacion y Asignacion en base al parametro que
     * contiene el id de la asignacion a enviar.
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     * seran usados en la pagina
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(value = "getHorarioAsignacion.htm", method = RequestMethod.GET)
    public @ResponseBody
    @JsonIgnore
    List<Horario> getHorarioAsignacion(@RequestParam Short idAsignacionCursoPensum,
            @RequestParam String idTipoHorario,
            HttpServletRequest request) {
        AsignacionCursoPensum asignacionCursoPensum = null;
        semestre = servicioSemestreImpl.getSemestreActivo();
        try {
            asignacionCursoPensum = servicioGeneralImpl.cargarEntidadPorID(AsignacionCursoPensum.class, idAsignacionCursoPensum);
        } catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }
        TipoHorario tipoHorario = TipoHorario.valueOf(idTipoHorario);

        List secciones = servicioHorarioImpl.getSeccionesHorario(asignacionCursoPensum, semestre, tipoHorario);

        return secciones;//servicioHorarioImpl.getHorario(asignacionCursoPensum, semestre, tipoHorario);
    }
}
