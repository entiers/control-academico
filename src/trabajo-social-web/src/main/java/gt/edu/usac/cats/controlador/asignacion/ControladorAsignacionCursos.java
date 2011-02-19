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
import gt.edu.usac.cats.dominio.wrapper.WrapperAsignacionCursos;
import gt.edu.usac.cats.enums.TipoActividad;
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
    private Curso cursoComputacion;
//_____________________________________________________________________________
    private Estudiante estudiante;
//_____________________________________________________________________________
    private Usuario usuario;
//_____________________________________________________________________________
    private Semestre semestre;
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

        //Validar perido de asignacion de cursos de semestre
        if(!this.servicioCalendarioActividadesImpl.esFechaActividadValida
                                (TipoActividad.ASIGNACION_SEMESTRE,
                                    semestre,
                                    new java.util.Date())){
            modelo.addAttribute("periodoInvalido",true);
            return "asignacion/asignacionCursos";
        }
        
        modelo.addAttribute("wrapperAsignacionCursos", new WrapperAsignacionCursos());

        this.setModelo(modelo);
        return "asignacion/asignacionCursos";
    }

//  _____________________________________________________________________________
    @RequestMapping(value  = "asignacionCursos.htm",method = RequestMethod.POST)
    public String postAsignacionCursos(@Valid WrapperAsignacionCursos wrapperAsignacionCursos, BindingResult bindingResult,
                                Model modelo, HttpServletRequest request) {
        this.setModelo(modelo);
        System.out.println("Salon " + wrapperAsignacionCursos.getHorario().getSalon());
        System.out.println("Seccion " + wrapperAsignacionCursos.getHorario().getSeccion());
        System.out.println("Curso " + wrapperAsignacionCursos.getHorario().getCurso().getNombre());
        return "asignacion/asignacionCursos";
    }



    //Llenar combo Horarios
    @RequestMapping(value="getHorario.htm", method=RequestMethod.GET)
    public @ResponseBody @JsonIgnore List<Horario> getHorario(@RequestParam Short idCurso) {
        Curso curso = this.servicioGeneralImpl.cargarEntidadPorID(Curso.class, idCurso);
        return this.servicioHorarioImpl.getHorario(curso, this.semestre);
    }


    private void setModelo(Model modelo){
        //Agregando listados para combos
        List<AsignacionEstudianteCarrera> listaAEC = this.servicioAsignacionEstudianteCarreraImpl
                                                            .getAsignacionEstudianteCarreraPorEstudiante(estudiante);

        List<Curso> listaCurso = this.servicioCursoImpl.getCursoXCarrera(
                                                listaAEC.get(0).getCarrera());

        modelo.addAttribute("listaAEC", listaAEC);
        modelo.addAttribute("listaCurso", listaCurso);
        modelo.addAttribute("listaHorario",this.servicioHorarioImpl.getHorario(
                                                    listaCurso.get(0), semestre));
    }

}
