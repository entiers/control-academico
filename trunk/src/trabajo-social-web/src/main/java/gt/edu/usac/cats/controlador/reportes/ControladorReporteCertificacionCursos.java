/*
* Sistema de Control Academico
* Escuela de Trabajo Social
* Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.controlador.reportes;

//~--- non-JDK imports --------------------------------------------------------
import gt.edu.usac.cats.dominio.Carrera;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.Usuario;
import gt.edu.usac.cats.enums.ControlReporte;
import gt.edu.usac.cats.enums.TipoAsignacion;
import gt.edu.usac.cats.servicio.ServicioGeneral;
import gt.edu.usac.cats.servicio.ServicioUsuario;
import gt.edu.usac.cats.util.RequestUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;

/**
 * Esta clase es el controlador que se encarga de la llamada al reporte certificaciones de cursos
 *
 * @author Ing. Carlos Solorzano
 */

@Controller
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class ControladorReporteCertificacionCursos implements Serializable{
//  _____________________________________________________________________________
    @Resource
    private ServicioGeneral servicioGeneralImpl;
    @Resource
    private ServicioUsuario servicioUsuarioImpl;
     private Usuario usuario;
    private Estudiante estudiante;
     private static final String TITULO_MENSAJE = "miscursos.ganados";
    
//  _____________________________________________________________________________
    /**
     * @param modelo
     * @param request
     * @return
     */
    @RequestMapping(value  = "rptCertificacionCursos.htm",method = RequestMethod.GET)
    public String getRptCertificaciones(Model modelo, HttpServletRequest request) {        
        modelo.addAttribute("listadoCarrera",servicioGeneralImpl.listarEntidad(Carrera.class));
        modelo.addAttribute("nombreControlReporte",ControlReporte.CERTIFICACION_CURSOS);     
        return "reportes/rptCertificacionCursos";
    }
//  _____________________________________________________________________________
    /**
     * @param modelo
     * @param request
     * @return
     */
    @RequestMapping(value  = "rptNotasCursosAsignados.htm",method = RequestMethod.GET)
    public String getRptNotasCursosAsignados(Model modelo, HttpServletRequest request) {                
        modelo.addAttribute("nombreControlReporte",ControlReporte.NOTAS_CURSOS_ASIGNADOS);     
        return "reportes/rptNotasCursosAsignados";
    }
//
    
    @RequestMapping(
     value = "generarCursosGanadosEstudiante.htm",
     method = RequestMethod.GET)
    public String generarCursosGanadosEstudiante(Model modelo, HttpServletRequest request) {
        // Buscar usuario para determinar si es estudiante o no
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        usuario = this.servicioUsuarioImpl.cargarUsuarioPorNombre(auth.getName().toString());

        if (usuario == null) {
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "buscarUsuario.sinResultados", false);
            modelo.addAttribute("error", true);
            return "asignacion/generarCursosGanadosEstudiante";
        }

        Set<Estudiante> stEst = usuario.getEstudiantes();
        if (!stEst.isEmpty()) {
            this.estudiante = stEst.iterator().next();
        } else {
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "buscarEstudiante.sinResultados", false);
            modelo.addAttribute("error", true);
            return "asignacion/generarCursosGanadosEstudiante";
        }
        modelo.addAttribute("carne", estudiante.getCarne());
        modelo.addAttribute("listadoCarrera",servicioGeneralImpl.listarEntidad(Carrera.class));
        //modelo.addAttribute("nombreControlReporte",ControlReporte.CERTIFICACION_CURSOS);     
        modelo.addAttribute("nombreControlReporte",ControlReporte.CURSOS_GANADOS_ESTUDIANTE);     
        modelo.addAttribute("mostrarCarne", false);
        //return "reportes/rptCertificacionCursos";
        return "reportes/rptCursosGanadosEstudiante";
    }
    @RequestMapping(
     value = "generarHistorialCursosAsignadosEstudiante.htm",
     method = RequestMethod.GET)
    public String generarHistorialCursosAsignadosEstudiante(Model modelo, HttpServletRequest request) {
        // Buscar usuario para determinar si es estudiante o no
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        usuario = this.servicioUsuarioImpl.cargarUsuarioPorNombre(auth.getName().toString());

        if (usuario == null) {
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "buscarUsuario.sinResultados", false);
            modelo.addAttribute("error", true);
            return "asignacion/generarHistorialCursosAsignadosEstudiante";
        }

        Set<Estudiante> stEst = usuario.getEstudiantes();
        if (!stEst.isEmpty()) {
            this.estudiante = stEst.iterator().next();
        } else {
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "buscarEstudiante.sinResultados", false);
            modelo.addAttribute("error", true);
            return "asignacion/generarHistorialCursosAsignadosEstudiante";
        }
        modelo.addAttribute("carne", estudiante.getCarne());
        modelo.addAttribute("nombreControlReporte",ControlReporte.NOTAS_CURSOS_ASIGNADOS);     
        modelo.addAttribute("mostrarCarne", false);
        //return "reportes/rptCertificacionCursos";
        return "reportes/rptNotasCursosAsignados";
    }
    
    

    
    /**Listado de estudiantes con notas a partir del portal del docente*/
    

}
