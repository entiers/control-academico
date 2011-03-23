/*
 * Universidad de San Carlos de Guatemala
 * Escuela de Trabajo Social
 * Control Academico
 */


package gt.edu.usac.cats.controlador.asignacion;

import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.Curso;
import gt.edu.usac.cats.dominio.DetalleAsignacion;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.Horario;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.dominio.Usuario;
import gt.edu.usac.cats.servicio.ServicioAsignacion;
import gt.edu.usac.cats.servicio.ServicioAsignacionCursoPensum;
import gt.edu.usac.cats.servicio.ServicioAsignacionEstudianteCarrera;
import gt.edu.usac.cats.servicio.ServicioBoletaBanco;
import gt.edu.usac.cats.servicio.ServicioCalendarioActividades;
import gt.edu.usac.cats.servicio.ServicioCurso;
import gt.edu.usac.cats.servicio.ServicioCursoAprobado;
import gt.edu.usac.cats.servicio.ServicioDetalleAsignacion;
import gt.edu.usac.cats.servicio.ServicioGeneral;
import gt.edu.usac.cats.servicio.ServicioHorario;
import gt.edu.usac.cats.servicio.ServicioPensumEstudianteCarrera;
import gt.edu.usac.cats.servicio.ServicioSemestre;
import gt.edu.usac.cats.servicio.ServicioUsuario;
import gt.edu.usac.cats.util.EmailSender;
import gt.edu.usac.cats.util.Mensajes;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import org.apache.log4j.Logger;


/**
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
public abstract class ControladorAbstractoAsignacion {
//______________________________________________________________________________
    private static Logger log = Logger.getLogger(ControladorAbstractoAsignacion.class);
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
    protected ServicioPensumEstudianteCarrera servicioPensumEstudianteCarrera;
//_____________________________________________________________________________
    @Resource
    protected ServicioBoletaBanco servicioBoletaBancoImpl;
//_____________________________________________________________________________
    @Resource
    protected EmailSender emailSender;
//_____________________________________________________________________________
    protected void enviarEmail(List<DetalleAsignacion> listaAsignacion) throws IOException {

        String mensaje = "Estimado/a " + asignacionEstudianteCarrera.getEstudiante().getNombre() + ", <br/><br/>"
                + "La asignacion de cursos se ha realizado exitosamente: <br/><br/>"
                + "  - Estudiante: " + asignacionEstudianteCarrera.getEstudiante().getNombre() + "<br/>"
                + "  - Carne: " + asignacionEstudianteCarrera.getEstudiante().getCarne() + "<br/>"
                + "  - Fecha: " + listaAsignacion.get(0).getAsignacion().getFecha() + "<br/>"
                + "  - Transaccion: " + listaAsignacion.get(0).getAsignacion().getTransaccion() + "<br/><br/>"
                + "Control Academico<br/>Escuela de Trabajo Social";
        try {
            // se trata de enviar el correo
            emailSender.enviarCorreo("Boleta de Asignación", asignacionEstudianteCarrera.getEstudiante().getEmail(), mensaje);

        } catch (MessagingException ex) {
            log.error(Mensajes.MESSAGING_EXCEPTION, ex);
        }
    }
}
