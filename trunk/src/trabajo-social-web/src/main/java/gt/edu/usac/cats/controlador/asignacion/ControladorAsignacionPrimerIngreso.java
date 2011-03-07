/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.controlador.asignacion;

import gt.edu.usac.cats.dominio.AsignacionPrimerIngreso;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.dominio.Usuario;
import gt.edu.usac.cats.enums.TipoActividad;
import gt.edu.usac.cats.servicio.ServicioGeneral;
import gt.edu.usac.cats.servicio.ServicioUsuario;
import gt.edu.usac.cats.servicio.ServicioAsignacionPrimerIngreso;
import gt.edu.usac.cats.servicio.ServicioCalendarioActividades;
import gt.edu.usac.cats.servicio.ServicioSemestre;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import java.util.Calendar;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.dao.DataAccessException;


/**
 * Esta clase es el controlador que se encarga de la llamada al proceso de asignacion
 * de estudiantes de primer ingreso con carnet del a;o en que se ejecuta el mismo
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
@Controller("controladorAsignacionPrimerIngreso")
public class ControladorAsignacionPrimerIngreso {
//______________________________________________________________________________
    private static Logger log = Logger.getLogger(ControladorAsignacionPrimerIngreso.class);
//_____________________________________________________________________________
    private static final String TITULO_MENSAJE = "asignacionPrimerIngreso.titulo";
//_____________________________________________________________________________
    private Usuario usuario;
//_____________________________________________________________________________
    @Resource
    private ServicioUsuario servicioUsuarioImpl;
//_____________________________________________________________________________
    @Resource
    private ServicioAsignacionPrimerIngreso servicioAsignacionPrimerIngresoImpl;
//_____________________________________________________________________________
    @Resource
    private ServicioSemestre servicioSemestreImpl;
//_____________________________________________________________________________
    @Resource
    private ServicioGeneral servicioGeneralImpl;
//_____________________________________________________________________________
    @Resource
    private ServicioCalendarioActividades servicioCalendarioActividadesImpl;
//_____________________________________________________________________________
    @RequestMapping(value="asignacionPrimerIngreso.htm",method = RequestMethod.GET)
    public String validarPeriodoValidoAsignacion(Model modelo, HttpServletRequest request) {
        
        Calendar calendar = Calendar.getInstance();
        Semestre semestre = (Semestre) this.servicioSemestreImpl.getSemestreActivo();

        modelo.addAttribute("procesoEjecutado","false");
        //Validar perido de asignacion primer ingreso
        if(this.servicioCalendarioActividadesImpl.esFechaActividadValida
                (TipoActividad.ASIGNACION_SEMESTRE,semestre,new java.util.Date()))
            modelo.addAttribute("periodoValido","true");
        else
            modelo.addAttribute("periodoValido","false");

        return "asignacion/asignacionPrimerIngreso";
    }

//_____________________________________________________________________________
    @RequestMapping(value="asignacionPrimerIngreso.htm",method=RequestMethod.POST)
    public String mostrarMensajeUsuario(Model modelo,HttpServletRequest request){
        modelo.addAttribute("procesoEjecutado","true");
        return "asignacion/asignacionPrimerIngreso";
    }

//_____________________________________________________________________________
    @RequestMapping(value="ajxEjecutarAsignacionPrimerIngreso.htm",method=RequestMethod.GET)
    public void procesarAsignacionPrimerIngreso(HttpServletRequest request){
        //Buscar usuario para asociarlo en la asignacion de primer ingreso
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        usuario = this.servicioUsuarioImpl.cargarUsuarioPorNombre(auth.getName().toString());
        if(usuario==null){
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "buscarUsuario.sinResultados", false);
        }

        try{
            //Grabar asignacion de primer ingreso
            AsignacionPrimerIngreso asignacionPrimerIngreso = new AsignacionPrimerIngreso();
            asignacionPrimerIngreso.setUsuario(usuario);
            this.servicioGeneralImpl.agregar(asignacionPrimerIngreso);

            //Llamar a proceso de asignacion de primer ingreso
            this.servicioAsignacionPrimerIngresoImpl.asignacionCursosPrimerIngreso(asignacionPrimerIngreso);
        }
        catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, null, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }
    }
}
