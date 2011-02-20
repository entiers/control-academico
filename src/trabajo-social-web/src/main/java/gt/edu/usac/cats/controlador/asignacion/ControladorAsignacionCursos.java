/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.controlador.asignacion;

import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.Curso;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.Horario;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.dominio.Usuario;
import gt.edu.usac.cats.dominio.busqueda.DatosBusquedaCarrera;
import gt.edu.usac.cats.enums.TipoActividad;
import gt.edu.usac.cats.servicio.ServicioAsignacion;
import gt.edu.usac.cats.servicio.ServicioAsignacionEstudianteCarrera;
import gt.edu.usac.cats.servicio.ServicioCalendarioActividades;
import gt.edu.usac.cats.servicio.ServicioCurso;
import gt.edu.usac.cats.servicio.ServicioCursoAprobado;
import gt.edu.usac.cats.servicio.ServicioGeneral;
import gt.edu.usac.cats.servicio.ServicioHorario;
import gt.edu.usac.cats.servicio.ServicioSemestre;
import gt.edu.usac.cats.servicio.ServicioUsuario;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * Esta clase es el controlador que se encarga de la llamada al proceso de asignacion
 * de estudiantes de primer ingreso con carnet del a;o en que se ejecuta el mismo
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
@Controller("controladorAsignacionCursos")
public class ControladorAsignacionCursos {
//______________________________________________________________________________
    private static Logger log = Logger.getLogger(ControladorAsignacionPrimerIngreso.class);
//_____________________________________________________________________________
    private static final String TITULO_MENSAJE = "miscursos.asignacionCursos.titulo";
//_____________________________________________________________________________
    private AsignacionEstudianteCarrera asignacionEstudianteCarrera;
//_____________________________________________________________________________
    private Curso cursoComputacion;
//_____________________________________________________________________________
    private Estudiante estudiante;
//_____________________________________________________________________________
    private Usuario usuario;
//_____________________________________________________________________________
    private Semestre semestre;
//_____________________________________________________________________________
    private List<AsignacionEstudianteCarrera> listaAEC;
//_____________________________________________________________________________
    private List<Curso> listaCurso;
//_____________________________________________________________________________
    private List<Horario> listaHorario;
//_____________________________________________________________________________
    private List<Horario> listaHorarioAsignacion;
//_____________________________________________________________________________
    @Resource
    private ServicioUsuario servicioUsuarioImpl;
//_____________________________________________________________________________
    @Resource
    private ServicioAsignacionEstudianteCarrera servicioAsignacionEstudianteCarreraImpl;
//_____________________________________________________________________________
    @Resource
    private ServicioCalendarioActividades servicioCalendarioActividadesImpl;
//_____________________________________________________________________________
    @Resource
    private ServicioSemestre servicioSemestreImpl;
//_____________________________________________________________________________
    @Resource
    private ServicioCursoAprobado servicioCursoAprobadoImpl;
//_____________________________________________________________________________
    @Resource
    private ServicioCurso servicioCursoImpl;
//_____________________________________________________________________________
    @Resource
    private ServicioHorario servicioHorarioImpl;
//_____________________________________________________________________________
    @Resource
    private ServicioAsignacion servicioAsignacionImpl;
//_____________________________________________________________________________
    @Resource
    private ServicioGeneral servicioGeneralImpl;
//_____________________________________________________________________________
    @RequestMapping(value="asignacionCursos.htm",method = RequestMethod.GET)
    public String getAsignacionCursos(Model modelo, HttpServletRequest request) {
        //Obteniendo semestre actual
        Calendar calendar = Calendar.getInstance();
        this.semestre = (Semestre) this.servicioSemestreImpl.
                                        listarSemestres(Short.valueOf(String.valueOf(calendar.get(Calendar.YEAR))) , '1').get(0);

        //Buscando usuario logueado por nombre
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        this.usuario = this.servicioUsuarioImpl.cargarUsuarioPorNombre(auth.getName().toString());

        //Validando que el usuario se haya encontrado y sea un estudiante
        if(this.usuario!=null & this.usuario.getEstudiantes().toArray().length>0)
            this.estudiante = (Estudiante) this.usuario.getEstudiantes().toArray()[0];            
        else{
            modelo.addAttribute("errorEntidad", true);
            return "asignacion/asignacionCursos";
        }

        //Validando que el estudiante se encuentre inscrito
        if(!this.estudiante.isInscrito()){
            modelo.addAttribute("estudianteNoInscrito",true);
            return "asignacion/asignacionCursos";
        }

        //Validar perido de asignacion de cursos de semestre
        if(!this.servicioCalendarioActividadesImpl.esFechaActividadValida
                                (TipoActividad.ASIGNACION_SEMESTRE,
                                    semestre,
                                    new java.util.Date())){
            modelo.addAttribute("periodoInvalido",true);
            return "asignacion/asignacionCursos";
        }

        this.listaAEC = this.servicioAsignacionEstudianteCarreraImpl.getAsignacionEstudianteCarreraPorEstudiante(estudiante);
        modelo.addAttribute("listaAEC", this.listaAEC);
        modelo.addAttribute("datosBusquedaCarrera",  new DatosBusquedaCarrera());
        
        return "asignacion/asignacionCursos";
    }
//  _____________________________________________________________________________
    @RequestMapping(value="asignacionCursos.htm",method = RequestMethod.POST)
    public String postAsignacionCursos(@Valid DatosBusquedaCarrera datosBusquedaCarrera,
                                        BindingResult bindingResult,
                                        Model modelo,
                                        HttpServletRequest request) {
        this.asignacionEstudianteCarrera = this.servicioGeneralImpl.cargarEntidadPorID(AsignacionEstudianteCarrera.class, datosBusquedaCarrera.getIdAsignacionEstudianteCarrera());
        this.listaCurso = this.servicioCursoImpl.getCurso(this.asignacionEstudianteCarrera.getCarrera());
        this.listaHorario = this.servicioHorarioImpl.getHorario(this.listaCurso.get(0), semestre);
        this.listaHorarioAsignacion = new ArrayList<Horario>();
        modelo.addAttribute("datosBusquedaCarrera",  new DatosBusquedaCarrera());
        modelo.addAttribute("listaCurso", this.listaCurso);
        modelo.addAttribute("listaHorario",this.listaHorario);
        return "asignacion/asignacionCursos2";
    }
//  _____________________________________________________________________________
    @RequestMapping(value="agregarHorario.htm",method = RequestMethod.POST)
    public String agregarHorario(@Valid DatosBusquedaCarrera datosBusquedaCarrera,
                                        BindingResult bindingResult,
                                        Model modelo,
                                        HttpServletRequest request) {
        if(datosBusquedaCarrera.getIdCurso() != -1){
            Curso curso = this.servicioGeneralImpl.cargarEntidadPorID(Curso.class, Short.parseShort(String.valueOf(datosBusquedaCarrera.getIdCurso())));
            Horario horario = this.servicioGeneralImpl.cargarEntidadPorID(Horario.class, datosBusquedaCarrera.getIdHorario());
            this.listaHorarioAsignacion.add(horario);
            for(int i=0;i<listaCurso.size();i++){
                if(curso.getIdCurso() == listaCurso.get(i).getIdCurso())
                    listaCurso.remove(i);
            }
            for(int i=0;i<listaHorario.size();i++){
                if(horario.getIdHorario() == listaHorario.get(i).getIdHorario())
                    listaHorario.remove(i);
            }
        }
        modelo.addAttribute("listaCurso", this.listaCurso);
        modelo.addAttribute("listaHorario",this.listaHorario);
        modelo.addAttribute("horarioElegido", true);
        modelo.addAttribute("listadoHorarioAsignados", this.listaHorarioAsignacion);
        return "asignacion/asignacionCursos2";
    }
//  _____________________________________________________________________________
    @RequestMapping(value="realizarAsignacion.htm",method = RequestMethod.GET)
    public void realizarAsignacion(Model modelo,
                                     HttpServletRequest request) {
        
        this.servicioAsignacionImpl.realizarAsignacionCursos(this.asignacionEstudianteCarrera, listaHorario);
    }
//  _____________________________________________________________________________
    @RequestMapping(value="getHorario.htm", method=RequestMethod.GET)
    public @ResponseBody @JsonIgnore List<Horario> getHorario(@RequestParam Short idCurso) {
        Curso curso = this.servicioGeneralImpl.cargarEntidadPorID(Curso.class, idCurso);
        return this.servicioHorarioImpl.getHorario(curso, this.semestre);
    }
}
