/*
 * Universidad de San Carlos de Guatemala
 * Escuela de Trabajo Social
 * Control Academico
 */


package gt.edu.usac.cats.controlador.asignacion;

import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.Curso;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.Horario;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.dominio.Usuario;
import gt.edu.usac.cats.servicio.ServicioAsignacion;
import gt.edu.usac.cats.servicio.ServicioAsignacionCursoPensum;
import gt.edu.usac.cats.servicio.ServicioAsignacionEstudianteCarrera;
import gt.edu.usac.cats.servicio.ServicioCalendarioActividades;
import gt.edu.usac.cats.servicio.ServicioCurso;
import gt.edu.usac.cats.servicio.ServicioCursoAprobado;
import gt.edu.usac.cats.servicio.ServicioDetalleAsignacion;
import gt.edu.usac.cats.servicio.ServicioGeneral;
import gt.edu.usac.cats.servicio.ServicioHorario;
import gt.edu.usac.cats.servicio.ServicioSemestre;
import gt.edu.usac.cats.servicio.ServicioUsuario;
import gt.edu.usac.cats.util.EmailSender;
import java.util.List;
import javax.annotation.Resource;


/**
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
public abstract class ControladorAbstractoAsignacion {
//_____________________________________________________________________________
    protected AsignacionEstudianteCarrera asignacionEstudianteCarrera;
//_____________________________________________________________________________
    protected Curso cursoComputacion;
//_____________________________________________________________________________
    protected Estudiante estudiante;
//_____________________________________________________________________________
    protected Usuario usuario;
//_____________________________________________________________________________
    protected Semestre semestre;
//_____________________________________________________________________________
    protected List<AsignacionEstudianteCarrera> listaAEC;
//_____________________________________________________________________________
    protected List<Curso> listaCurso;
//_____________________________________________________________________________
    protected List<Horario> listaHorario;
//_____________________________________________________________________________
    @Resource
    protected ServicioUsuario servicioUsuarioImpl;
//_____________________________________________________________________________
    @Resource
    protected ServicioAsignacion servicioAsignacionImpl;
//_____________________________________________________________________________
    @Resource
    protected ServicioAsignacionEstudianteCarrera servicioAsignacionEstudianteCarreraImpl;
//_____________________________________________________________________________
    @Resource
    protected ServicioCalendarioActividades servicioCalendarioActividadesImpl;
//_____________________________________________________________________________
    @Resource
    protected ServicioSemestre servicioSemestreImpl;
//_____________________________________________________________________________
    @Resource
    protected ServicioCursoAprobado servicioCursoAprobadoImpl;
//_____________________________________________________________________________
    @Resource
    protected ServicioCurso servicioCursoImpl;
//_____________________________________________________________________________
    @Resource
    protected ServicioHorario servicioHorarioImpl;
//_____________________________________________________________________________
    @Resource
    protected ServicioAsignacionCursoPensum servicioAsignacionCursoPensumImpl;
//_____________________________________________________________________________
    @Resource
    protected ServicioGeneral servicioGeneralImpl;
//_____________________________________________________________________________
    @Resource
    protected ServicioDetalleAsignacion servicioDetalleAsignacionImpl;
//_____________________________________________________________________________
    @Resource
    protected EmailSender emailSender;


}
