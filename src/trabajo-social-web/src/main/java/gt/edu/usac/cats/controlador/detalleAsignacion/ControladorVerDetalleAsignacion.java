/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */
package gt.edu.usac.cats.controlador.detalleAsignacion;

import gt.edu.usac.cats.controlador.asignacion.ControladorAbstractoAsignacion;
import gt.edu.usac.cats.dominio.Asignacion;
import gt.edu.usac.cats.dominio.DetalleAsignacion;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.dominio.Usuario;
import gt.edu.usac.cats.enums.TipoActividad;
import gt.edu.usac.cats.enums.TipoAsignacion;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.velocity.FabricaTemplateVelocity;
import gt.edu.usac.cats.velocity.contexto.InformacionAsignacionEstudiante;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
@Controller
@Scope(value = WebApplicationContext.SCOPE_SESSION)
@SessionAttributes(value = {"asignacion", "periodoAsignacion", "listaDetalleAsignacion", "listaDA",
    "usuario", "estudiante", "semestre"})
public class ControladorVerDetalleAsignacion extends ControladorAbstractoAsignacion implements Serializable{

//______________________________________________________________________________
    private static Logger log = Logger.getLogger(ControladorVerDetalleAsignacion.class);
//______________________________________________________________________________
    private Asignacion asignacion;
//______________________________________________________________________________
    private boolean periodoAsignacion;
//______________________________________________________________________________
    private List<DetalleAsignacion> listaDetalleAsignacion;
//______________________________________________________________________________
    private List<DetalleAsignacion> listaDA;
//______________________________________________________________________________
    private Usuario usuario;
//______________________________________________________________________________
    private Estudiante estudiante;
//______________________________________________________________________________
    private Semestre semestre;
//______________________________________________________________________________    

    /**
     * <p>Metodo que intercepta la peticion GET de la pagina
     * verDetalleAsignacion.htm. Se encarga de validar: <li>Periodo valido de
     * asignacion de cursos</li> <li>Mostrar el listado de cursos a los que el
     * estudiante se encuentra asignado </li> </p>
     */
    @RequestMapping(value = "verDetalleAsignacion.htm", method = RequestMethod.GET)
    public String verDetalleAsignacion(Model modelo, HttpServletRequest request, int idAsignacion) {
        //Buscando usuario logueado por nombre
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        this.usuario = this.servicioUsuarioImpl.cargarUsuarioPorNombre(auth.getName().toString());

        //Validando que el usuario se haya encontrado y sea un estudiante
        if (this.usuario != null & this.usuario.getEstudiantes().toArray().length > 0) {
            this.estudiante = (Estudiante) this.usuario.getEstudiantes().toArray()[0];
        } else {
            modelo.addAttribute("errorEntidad", true);
            return "detalleAsignacion/verDetalleAsignacion";
        }

        this.semestre = this.servicioSemestreImpl.getSemestreActivo();
        this.asignacion = this.servicioSemestreImpl.cargarEntidadPorID(Asignacion.class, idAsignacion);

        //Validando que la asignacion enviada sea la del estudiante logueado
        if (!this.asignacion.getAsignacionEstudianteCarrera().getEstudiante().equals(this.estudiante)) {
            modelo.addAttribute("errorEstudianteAsignacion", true);
            return "detalleAsignacion/verDetalleAsignacion";
        }

        if (!this.asignacion.getDetalleAsignacions().isEmpty()) {
            Semestre semestreAsignacion = this.asignacion.getDetalleAsignacions().iterator().next().getHorario().getSemestre();
            
            this.periodoAsignacion = semestreAsignacion.equals(this.semestre);
        } else {
            modelo.addAttribute("errorAsignacionSinDetalle", true);
            return "detalleAsignacion/verDetalleAsignacion";
        }

        if (this.periodoAsignacion) {
            if (asignacion.getTipoAsignacion().equals(TipoAsignacion.ASIGNACION_CURSOS_SEMESTRE)
                    && this.servicioCalendarioActividadesImpl.esFechaActividadValida(TipoActividad.ASIGNACION_SEMESTRE,
                    this.semestre,
                    new java.util.Date())) {
                this.periodoAsignacion = true;
            } else if (asignacion.getTipoAsignacion().equals(TipoAsignacion.ASIGNACION_CURSOS_VACACIONES)
                    && this.servicioCalendarioActividadesImpl.esFechaActividadValida(TipoActividad.ASIGNACION_VACACIONES,
                    this.semestre,
                    new java.util.Date())) {
                this.periodoAsignacion = true;
            } else if (asignacion.getTipoAsignacion().equals(TipoAsignacion.ASIGNACION_PRIMERA_RETRASADA)
                    && this.servicioCalendarioActividadesImpl.esFechaActividadValida(TipoActividad.ASIGNACION_PRIMERA_RESTRASADA,
                    this.semestre,
                    new java.util.Date())) {
                this.periodoAsignacion = true;
            } else if (asignacion.getTipoAsignacion().equals(TipoAsignacion.ASIGNACION_SEGUNDA_RETRASADA)
                    && this.servicioCalendarioActividadesImpl.esFechaActividadValida(TipoActividad.ASIGNACION_SEGUNDA_RETRASADA,
                    this.semestre,
                    new java.util.Date())) {
                this.periodoAsignacion = true;
            }
        }

        this.listaDetalleAsignacion = this.servicioDetalleAsignacionImpl.getListadoDetalleAsignacion(asignacion);
        this.listaDA = new ArrayList<DetalleAsignacion>();
        this.setModelo(modelo);
        return "detalleAsignacion/verDetalleAsignacion";
    }

    /**
     * Quitar un curso del listado de asignaciones
     *
     * @param detail Codigo del detalle asignacion a quitar
     * @param modelo
     * @param request
     * @return
     */
    @RequestMapping(value = "quitarDetalleAsignacion.htm", method = RequestMethod.POST)
    public String quitarDetalleAsignacion(@RequestParam("detail") int detail,
            Model modelo, HttpServletRequest request) {
        //Lo hice asi porque no se porque no me carga los Detalles asignacion por id :S        
        for (DetalleAsignacion detalleAsignacion : this.listaDetalleAsignacion) {
            if (detalleAsignacion.getIdDetalleAsignacion() == detail) {
                listaDA.add(detalleAsignacion);
                break;
            }
        }
        this.listaDetalleAsignacion.removeAll(listaDA);
        this.setModelo(modelo);
        return "detalleAsignacion/verDetalleAsignacion";
    }

    /**
     * Quitar un curso del listado de asignaciones
     *
     * @param detail Codigo del detalle asignacion a quitar
     * @param modelo
     * @param request
     * @return
     */
    @RequestMapping(value = "eliminarDetalleAsignacion.htm", method = RequestMethod.POST)
    public String eliminarDetalleAsignacion(Model modelo, HttpServletRequest request) throws HibernateException {
        try {
            this.servicioDetalleAsignacionImpl.eliminarDetalleAsignacion(listaDA);
            if (!listaDetalleAsignacion.isEmpty()) {
                InformacionAsignacionEstudiante iae = new InformacionAsignacionEstudiante();
                iae.setCarnet(estudiante.getCarne());
                iae.setNombre(estudiante.getNombre());
                iae.setFecha(asignacion.getFecha());
                iae.setTransaccion(asignacion.getTransaccion());
                try {
                    emailSenderVelocity.enviarCorreo("Cambios en boleta de asignacion",
                            estudiante.getEmail(),
                            FabricaTemplateVelocity.INFORMACION_ASIGNACION_ESTUDIANTE,
                            iae);
                } catch (MessagingException ex) {
                    log.error(Mensajes.MESSAGING_EXCEPTION, ex);
                }
            }
            modelo.addAttribute("operacionExitosa", true);
            this.setModelo(modelo);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(ControladorVerDetalleAsignacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "detalleAsignacion/verDetalleAsignacion";
    }

    private void setModelo(Model modelo) {
        modelo.addAttribute("asignacion", asignacion);
        modelo.addAttribute("periodoAsignacion", periodoAsignacion);
        modelo.addAttribute("listaDetalleAsignacion", listaDetalleAsignacion);
    }
}
