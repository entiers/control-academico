/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.controlador.detalleAsignacion;

import gt.edu.usac.cats.dominio.Asignacion;
import gt.edu.usac.cats.dominio.DetalleAsignacion;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.dominio.Usuario;
import gt.edu.usac.cats.enums.TipoActividad;
import gt.edu.usac.cats.servicio.ServicioCalendarioActividades;
import gt.edu.usac.cats.servicio.ServicioDetalleAsignacion;
import gt.edu.usac.cats.servicio.ServicioSemestre;
import gt.edu.usac.cats.servicio.ServicioUsuario;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Carlos Solorzano
 * @version 1.0
 */


@Controller
public class ControladorVerDetalleAsignacion{
//______________________________________________________________________________
    private static Logger log = Logger.getLogger(ControladorVerDetalleAsignacion.class);
//______________________________________________________________________________
    private Asignacion asignacion;
//______________________________________________________________________________
    private Semestre semestre;
//______________________________________________________________________________
    private Usuario usuario;
//______________________________________________________________________________
    private Estudiante estudiante;
//_____________________________________________________________________________
    private boolean periodoAsignacion;
//______________________________________________________________________________
    private List<DetalleAsignacion> listaDetalleAsignacion;
//______________________________________________________________________________
    @Resource
    private ServicioCalendarioActividades servicioCalendarioActividadesImpl;
//______________________________________________________________________________
    @Resource
    private ServicioSemestre servicioSemestreImpl;
//______________________________________________________________________________
    @Resource
    private ServicioDetalleAsignacion servicioDetalleAsignacionImpl;
//______________________________________________________________________________
    @Resource
    private ServicioUsuario servicioUsuarioImpl;
//______________________________________________________________________________
     /**
     * <p>Metodo que intercepta la peticion GET de la pagina verDetalleAsignacion.htm. 
      *Se encarga de validar: 
      * <li>Periodo valido de asignacion de cursos</li>
      * <li>Mostrar el listado de cursos a los que el estudiante se encuentra asignado </li>
      * </p>
     */
    @RequestMapping(value="verDetalleAsignacion.htm",method=RequestMethod.GET)
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
        if(!this.asignacion.getAsignacionEstudianteCarrera().getEstudiante().equals(this.estudiante)){
            modelo.addAttribute("errorEstudianteAsignacion", true);
            return "detalleAsignacion/verDetalleAsignacion";
        }
        
        if(this.asignacion.getDetalleAsignacions() != null){
            Semestre semestreAsignacion = this.asignacion.getDetalleAsignacions().iterator().next().getHorario().getSemestre();
            this.periodoAsignacion = semestreAsignacion.equals(this.semestre);
        } else {
            modelo.addAttribute("errorAsignacionSinDetalle", true);
            return "detalleAsignacion/verDetalleAsignacion";
        }
        
        this.periodoAsignacion = this.periodoAsignacion && this.servicioCalendarioActividadesImpl.esFechaActividadValida(TipoActividad.ASIGNACION_SEMESTRE,
                                                                    this.semestre,
                                                                    new java.util.Date()) || 
                this.servicioCalendarioActividadesImpl.esFechaActividadValida(TipoActividad.ASIGNACION_VACACIONES,
                                                                    this.semestre,
                                                                    new java.util.Date()) ||
                this.servicioCalendarioActividadesImpl.esFechaActividadValida(TipoActividad.ASIGNACION_PRIMERA_RESTRASADA,
                                                                    this.semestre,
                                                                    new java.util.Date()) ||
                this.servicioCalendarioActividadesImpl.esFechaActividadValida(TipoActividad.ASIGNACION_SEGUNDA_RETRASADA,
                                                                    this.semestre,
                                                                    new java.util.Date());
        this.listaDetalleAsignacion = this.servicioDetalleAsignacionImpl.getListadoDetalleAsignacion(asignacion);
        this.setModelo(modelo);
        return "detalleAsignacion/verDetalleAsignacion";
    }
    
    private void setModelo(Model modelo){
        modelo.addAttribute("asignacion", asignacion);
        modelo.addAttribute("periodoAsignacion", periodoAsignacion);
        modelo.addAttribute("listaDetalleAsignacion", listaDetalleAsignacion);
    }
}
