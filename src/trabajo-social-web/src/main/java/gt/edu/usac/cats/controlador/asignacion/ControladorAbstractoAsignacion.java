/*
 * Universidad de San Carlos de Guatemala
 * Escuela de Trabajo Social
 * Control Academico
 */

package gt.edu.usac.cats.controlador.asignacion;

import gt.edu.usac.cats.dominio.Asignacion;
import gt.edu.usac.cats.dominio.AsignacionCursoPensum;
import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.Curso;
import gt.edu.usac.cats.dominio.DetalleAsignacion;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.Horario;
import gt.edu.usac.cats.dominio.PensumEstudianteCarrera;
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
import gt.edu.usac.cats.util.EmailSenderVelocity;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.velocity.FabricaTemplateVelocity;
import gt.edu.usac.cats.velocity.contexto.InformacionAsignacionEstudiante;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import org.apache.log4j.Logger;
import org.springframework.ui.Model;


/**
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
public abstract class ControladorAbstractoAsignacion implements Serializable{
//______________________________________________________________________________
    private static Logger log = Logger.getLogger(ControladorAbstractoAsignacion.class);
//_____________________________________________________________________________
  /*  protected PensumEstudianteCarrera pensumEstudianteCarrera;
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
    protected List<AsignacionCursoPensum> listaAsignacionCursoPensum;
//_____________________________________________________________________________
    protected List<Horario> listaHorario;*/
    
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
    protected EmailSenderVelocity emailSenderVelocity;
//_____________________________________________________________________________
    protected void enviarEmail(List<DetalleAsignacion> listaAsignacion, AsignacionEstudianteCarrera asignacionEstudianteCarrera) throws IOException {

        Estudiante estudiante2 = asignacionEstudianteCarrera.getEstudiante();
        Asignacion asignacion = listaAsignacion.get(0).getAsignacion();

        InformacionAsignacionEstudiante iae = new InformacionAsignacionEstudiante();
        iae.setCarnet(estudiante2.getCarne());
        iae.setNombre(estudiante2.getNombre());
        iae.setFecha(asignacion.getFecha());
        iae.setTransaccion(asignacion.getTransaccion());
        StringBuilder htable = new StringBuilder("<table border=1><tr><th>Curso</th><th>Seccion</th></tr>");
        for (DetalleAsignacion det:listaAsignacion){
            htable.append("<tr><td>").append(det.getHorario().getAsignacionCursoPensum().getCurso().getCodigo()).append(" ");
            htable.append(det.getHorario().getAsignacionCursoPensum().getCurso().getNombre()).append("</td>");
            htable.append("<td>").append(det.getHorario().getSeccion()).append("</td></tr>");
          
        }
        htable.append("</table>");
        iae.setDetalleHtmlAsignacion(htable.toString());
        
        try {
            // se trata de enviar el correo
            if(estudiante2.getEmail() != null 
                    && estudiante2.getEmail().trim().length()>0){
                emailSenderVelocity.enviarCorreo("Boleta de asignacion",
                    estudiante2.getEmail(),
                    FabricaTemplateVelocity.INFORMACION_ASIGNACION_ESTUDIANTE,
                    iae);
            }

        } catch (Exception ex) {
            log.error(Mensajes.MAIL_EXCEPTION, ex);
        }
    }
    
//    protected void setModelo(Model modelo){
//        modelo.addAttribute("errorEntidad", false);
//        modelo.addAttribute("estudianteNoInscrito", false);
//        modelo.addAttribute("periodoInvalido",false);
//        modelo.addAttribute("cursoComputacion", false);
//        modelo.addAttribute("noExisteHorario", false);
//        modelo.addAttribute("noPensumValido", false);
//        
//    }
}
