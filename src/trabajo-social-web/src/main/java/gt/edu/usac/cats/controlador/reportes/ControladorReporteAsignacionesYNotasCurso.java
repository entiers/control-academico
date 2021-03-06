/*
* Sistema de Control Academico
* Escuela de Trabajo Social
* Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.controlador.reportes;

//~--- non-JDK imports --------------------------------------------------------
import gt.edu.usac.cats.dominio.Catedratico;
import gt.edu.usac.cats.dominio.Horario;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.dominio.Usuario;
import gt.edu.usac.cats.enums.ControlReporte;
import gt.edu.usac.cats.enums.TipoHorario;
import gt.edu.usac.cats.servicio.ServicioHorario;
import gt.edu.usac.cats.servicio.ServicioSemestre;
import gt.edu.usac.cats.servicio.ServicioUsuario;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;

/**
 * Esta clase es el controlador que se encarga de la llamada al reporte de asignaciones
 * por curso
 *
 * @author Carlos Solorzano
 */

@Controller
@Scope(value = WebApplicationContext.SCOPE_SESSION)
@SessionAttributes(value = {"usuario", "catedratico", "semestre"})
public class ControladorReporteAsignacionesYNotasCurso implements Serializable {
//______________________________________________________________________________
    private static String TITULO_MENSAJE = "rptAsignacionCurso.titulo";
//______________________________________________________________________________
    private static Logger log = Logger.getLogger(ControladorReporteAsignacionesYNotasCurso.class);
//______________________________________________________________________________
    private Usuario usuario;
//______________________________________________________________________________
    private Catedratico catedratico;
//______________________________________________________________________________    
    private Semestre semestre;
//______________________________________________________________________________
    @Resource
    private ServicioSemestre servicioSemestreImpl;
//______________________________________________________________________________
    @Resource
    private ServicioHorario servicioHorarioImpl;
//______________________________________________________________________________
    @Resource
    private ServicioUsuario servicioUsuarioImpl;
    
    @Resource
    private ServicioUsuario servicioAsignacionCursoPensumImpl;    
//______________________________________________________________________________
    /**
     * * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina <code>rptAsignacionCurso.htm</code>. El metodo se encarga
     * de inicializar las listas de horario en base a los parametros enviados.
     * 
     * @param modelo
     * @param request
     * @return
     */
    @RequestMapping(value  = "rptAsignacionCurso.htm",method = RequestMethod.GET)
    public String getRptAsignacionCurso(Model modelo, HttpServletRequest request) {

        this.semestre = this.servicioSemestreImpl.getSemestreActivo();
        modelo.addAttribute("nombreControlReporte",ControlReporte.LISTADO_ASIGNACION_POR_CURSO);
        this.crearModelo(modelo, request);
        return "reportes/rptAsignacionCurso";
    }
//  _____________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina <code>rptNotasCurso.htm</code>. El metodo se encarga
     * de inicializar las listas de horario en base a los parametros enviados.
     *
     * @param modelo
     * @param request
     * @return
     */
    @RequestMapping(value  = "rptNotasCurso.htm",method = RequestMethod.GET)
    public String getRptNotasCurso(Model modelo, HttpServletRequest request) {

        this.semestre = this.servicioSemestreImpl.getSemestreActivo();
        modelo.addAttribute("nombreControlReporte",ControlReporte.ACTA_NOTAS);
        this.crearModelo(modelo, request);
        return "reportes/rptNotasCurso";
    }
//  _____________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina <code>getHorarioRpt.htm</code>. El metodo se encarga
     * de inicializar las listas de horario en base a los parametros enviados.
     *
     * @param idTipoHorario tipo de horario a buscar
     * @return List<Horario> listado filtrado en base a los parametros enviados.
     */
    @RequestMapping(value = "getHorarioRpt.htm", method = RequestMethod.GET)
    public @ResponseBody String getHorario(@RequestParam int idTipoHorario,
                                                    HttpServletRequest request) {
        String strOptions = "";
        List<Horario> listadoHorario;
        try {
            TipoHorario tipoHorario = TipoHorario.fromInt(idTipoHorario);
            if(this.catedratico == null){
                listadoHorario = this.servicioHorarioImpl.getHorario(this.semestre, tipoHorario, true);
            } else {
                listadoHorario = this.servicioHorarioImpl.getHorario(this.semestre, this.catedratico,
                                    tipoHorario, true);
            }
            
            for (Horario horario : listadoHorario){
                strOptions += "<option value=\"" + horario.getIdHorario() + "\">" +
                        horario.getAsignacionCursoPensum().getCurso().getNombre() + " - " + horario.getSeccion() +
                        "</option>";
            }
        } catch (Exception e) {
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }
        return strOptions;
    }

//  _____________________________________________________________________________
    /**
     * * <p>Este metodo es generalizado para los reportes de asignaciones por curso
     * y notas por curso. Su objetivo es inicializar los elementos de la vista para
     * la generacion de ambos reportes</p>
     * 
     * @param modelo
     * @param request
     */
    private void crearModelo(Model modelo, HttpServletRequest request){
        modelo.addAttribute("listaTipoHorario", TipoHorario.values());
        try{
            //Buscando usuario logueado por nombre
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            this.usuario = this.servicioUsuarioImpl.cargarUsuarioPorNombre(auth.getName().toString());

            //Validando que el usuario se haya encontrado y sea un catedratico
            if (this.usuario != null & this.usuario.getCatedraticos().toArray().length != 0){
                this.catedratico = (Catedratico) this.usuario.getCatedraticos().toArray()[0];
                modelo.addAttribute("listadoHorario", servicioHorarioImpl.
                    getHorario(this.semestre, this.catedratico, TipoHorario.SEMESTRE, true));
            }
            else {
               
                modelo.addAttribute("listadoHorario", servicioHorarioImpl.
                    getHorarioConNotas(this.semestre, TipoHorario.SEMESTRE, true));
            }
        }
        catch(Exception ex){
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, ex);
        }
    }
   
    
    /**Listado de estudiantes con notas desde el usuario del docente*/
    
    @RequestMapping(value  = "rptListadoEstudiantesConNota.htm",method = RequestMethod.GET)
    public String getRptListadoEstudiantesConNotas(Model modelo, HttpServletRequest request) {        

        this.semestre = this.servicioSemestreImpl.getSemestreActivo();
        modelo.addAttribute("nombreControlReporte",ControlReporte.LISTADO_ESTUDIANTE_CON_NOTAS);
        this.crearModelo(modelo, request);
        return "reportes/rptListadoEstudiantesConNotas";       
    }
    
        @RequestMapping(value  = "rptActaPreliminarNotas.htm",method = RequestMethod.GET)
    public String getRptActaPreliminarNotas(Model modelo, HttpServletRequest request) {        

        this.semestre = this.servicioSemestreImpl.getSemestreActivo();
            System.out.println(">>>>>>>>>>> "+ControlReporte.ACTA_PRELIMINAR_NOTAS);
        modelo.addAttribute("nombreControlReporte",ControlReporte.ACTA_PRELIMINAR_NOTAS);
        this.crearModelo(modelo, request);
        return "reportes/rptActaPreliminarNotas";       
    }
    
     

}
