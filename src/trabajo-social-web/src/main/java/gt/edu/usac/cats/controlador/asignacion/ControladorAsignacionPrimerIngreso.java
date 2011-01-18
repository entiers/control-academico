/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.controlador.asignacion;

//import gt.edu.usac.cats.dominio.AsignacionPrimerIngreso;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.Usuario;
import gt.edu.usac.cats.servicio.ServicioEstudiante;
import gt.edu.usac.cats.servicio.ServicioGeneral;
import gt.edu.usac.cats.servicio.ServicioUsuario;
import gt.edu.usac.cats.util.RequestUtil;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.List;


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
    private ServicioGeneral servicioGeneralImpl;
//_____________________________________________________________________________
    @Resource
    private ServicioEstudiante servicioEstudianteImpl;
//_____________________________________________________________________________
    @RequestMapping(value="asignacionPrimerIngreso.htm",method = RequestMethod.GET)
    public String validarPeriodoValidoAsignacion(Model modelo, HttpServletRequest request) {

        //Validar perido de asignacion primer ingreso
        if(true)
            modelo.addAttribute("periodoValido","true");
        else
            modelo.addAttribute("periodoValido","false");

        return "asignacion/asignacionPrimerIngreso";
    }

//_____________________________________________________________________________
    @RequestMapping(value="asignacionPrimerIngreso.htm",method=RequestMethod.POST)
    public String procesarAsignacionPrimerIngreso(HttpServletRequest request){

        //Buscar usuario para asociarlo en la asignacion de primer ingreso
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        this.usuario = this.servicioUsuarioImpl.cargarUsuarioPorNombre(auth.getName().toString());
        if(this.usuario==null){
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "buscarUsuario.sinResultados", false);
            return "asignacion/asignacionPrimerIngreso";
        }

        //Grabar asignacion de primer ingreso
        //AsignacionPrimerIngreso asignacionPrimerIngreso = new AsignacionPrimerIngreso(this.usuario);
        //this.servicioGeneralImpl.agregar(asignacionPrimerIngreso);

        //Obtener estudiantes de primer ingreso
       //List <Estudiante> lstEstudiantes = this.servicioEstudianteImpl.getListadoEstudiantesPrimerIngreso();

        return "asignacion/asignacionPrimerIngreso";
    }


}