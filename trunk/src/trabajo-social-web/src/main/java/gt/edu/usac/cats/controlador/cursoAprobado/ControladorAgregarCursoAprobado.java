/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.controlador.cursoAprobado;

import gt.edu.usac.cats.dominio.AsignacionCursoPensum;
import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.wrapper.WrapperCursoAprobado;
import gt.edu.usac.cats.enums.TipoAsignacion;
import gt.edu.usac.cats.servicio.ServicioAsignacionCursoPensum;
import gt.edu.usac.cats.servicio.ServicioAsignacionEstudianteCarrera;
import gt.edu.usac.cats.servicio.ServicioCursoAprobado;
import gt.edu.usac.cats.servicio.ServicioDetalleAsignacion;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author Carlos Solorzano
 * @version 1.0
 */

@Controller
@Scope(value = WebApplicationContext.SCOPE_SESSION)
@SessionAttributes(value={"estudiante", "listaAEC", "listaACP"})
public class ControladorAgregarCursoAprobado implements Serializable{

    private static String TITULO_MENSAJE = "admin.agregarCursoAprobado.titulo";
//______________________________________________________________________________    
    private static Logger log = Logger.getLogger(ControladorAgregarCursoAprobado.class);
//______________________________________________________________________________    
    private Estudiante estudiante;
//______________________________________________________________________________    
    private List<AsignacionEstudianteCarrera> listaAEC;
//______________________________________________________________________________    
    private List<AsignacionCursoPensum> listaACP;    
//______________________________________________________________________________
    @Resource
    protected ServicioAsignacionEstudianteCarrera servicioAsignacionEstudianteCarreraImpl;
//______________________________________________________________________________
    @Resource
    protected ServicioAsignacionCursoPensum servicioAsignacionCursoPensumImpl;
//______________________________________________________________________________
    @Resource
    protected ServicioCursoAprobado servicioCursoAprobadoImpl;
//______________________________________________________________________________
    @Resource
    protected ServicioDetalleAsignacion servicioDetalleAsignacionImpl;
//______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina <code>agregarCursoAprobado.htm</code>. El metodo se encarga
     * de iniciar los objetos que se usaran en la pagina.</p>
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(value = "agregarCursoAprobado.htm", method = RequestMethod.GET)    
    public String crearFormulario(Model modelo, int idEstudiante) {        
        WrapperCursoAprobado wrapperCursoAprobado = new WrapperCursoAprobado();
        this.estudiante = this.servicioAsignacionEstudianteCarreraImpl.cargarEntidadPorID(Estudiante.class, 
                idEstudiante);
        if(this.estudiante != null){
            this.listaAEC = this.servicioAsignacionEstudianteCarreraImpl.
                    getAsignacionEstudianteCarrera(estudiante, true);
            if(!this.listaAEC.isEmpty()){
                System.out.println("&&& asignacionEstudianteCarrera "+listaAEC.get(0));
                this.listaACP = this.servicioAsignacionCursoPensumImpl.cursosSinAprobarValidos(listaAEC.get(0));
                if(listaACP.isEmpty()){
                    modelo.addAttribute("estudianteSinCursos", true);
                }
            } else {
                modelo.addAttribute("estudianteSinCarrera", true);
            }
        } else {
            modelo.addAttribute("estudianteNoExiste", true);
        }
        
        this.cargarFormulario(modelo, wrapperCursoAprobado);
        return "cursoAprobado/agregarCursoAprobado";
    }
//______________________________________________________________________________
    /**
     * 
     * @param wrapperCursoAprobado
     * @param bindingResult
     * @param modelo
     * @param request
     * @return 
     */
    @RequestMapping(value = "agregarCursoAprobado.htm", method = RequestMethod.POST)
    public String agregarCursoAprobado(@Valid WrapperCursoAprobado wrapperCursoAprobado,
            BindingResult bindingResult, Model modelo, HttpServletRequest request) {       
        
        this.cargarFormulario(modelo, wrapperCursoAprobado);
        
        if (bindingResult.hasErrors()) {
            return "cursoAprobado/agregarCursoAprobado";
        }
        
        AsignacionEstudianteCarrera aec = this.servicioAsignacionCursoPensumImpl.cargarEntidadPorID(
                AsignacionEstudianteCarrera.class, wrapperCursoAprobado.getIdAsignacionEstudianteCarrera());
        
        AsignacionCursoPensum acp = this.servicioAsignacionCursoPensumImpl.cargarEntidadPorID(
                AsignacionCursoPensum.class, wrapperCursoAprobado.getIdAsignacionCursoPensum());
        
        //Validando prerequisitos de curso
        if (servicioCursoAprobadoImpl.getCursoPrerrequisitoPendiente(aec, acp).isEmpty()
                & servicioCursoAprobadoImpl.getCreditosAprobados(aec) < acp.getCreditosPrerrequisito()) {
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, 
                    "miscursos.asignacionCursos.prerrequisitoPendiente", false);
            return "cursoAprobado/agregarCursoAprobado";
        }        
        
        //Validando nota de aprobacion
        if(wrapperCursoAprobado.getExamenFinal() + wrapperCursoAprobado.getZona() < 
                acp.getPensum().getNotaAprobacion()){
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, 
                    "admin.agregarCursoAprobado.notaAprobacionInvalida", false);
            return "cursoAprobado/agregarCursoAprobado";
        }
        
        //Agregando curso aprobado
        this.servicioCursoAprobadoImpl.agregarCursoAprobado(aec, acp, wrapperCursoAprobado);            
        
        //Registrando evento
        RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, 
                    "admin.agregarCursoAprobado.exito", true);
        String msg = Mensajes.EXITO_AGREGAR + "CursoAprobado, Estudiante: " + estudiante.getCarne() + 
                " Asignacion curso pensum: " + wrapperCursoAprobado.getIdAsignacionCursoPensum();
        log.info(msg);
        
        //Inicializando componentes       
        if(this.estudiante != null){
            this.listaAEC = this.servicioAsignacionEstudianteCarreraImpl.getAsignacionEstudianteCarrera(estudiante, true);
            if(!this.listaAEC.isEmpty()){
                this.listaACP = this.servicioAsignacionCursoPensumImpl.cursosSinAprobarValidos(listaAEC.get(0));
                if(listaACP.isEmpty()){
                    modelo.addAttribute("estudianteSinCursos", true);
                }
            } else {
                modelo.addAttribute("estudianteSinCarrera", true);
            }
        } else {
            modelo.addAttribute("estudianteNoExiste", true);
        }
        wrapperCursoAprobado = new WrapperCursoAprobado();        
        this.cargarFormulario(modelo, wrapperCursoAprobado);
        
        return "cursoAprobado/agregarCursoAprobado";
    }
//______________________________________________________________________________
    @RequestMapping(value = "getListaCursosSinAprobarValidos.htm", method = RequestMethod.GET)
    public @ResponseBody @JsonIgnore List<AsignacionCursoPensum> getHorario(@RequestParam int idAEC,                                                            
                                                            HttpServletRequest request) {
        
        AsignacionEstudianteCarrera aec = this.servicioAsignacionEstudianteCarreraImpl.
                cargarEntidadPorID(AsignacionEstudianteCarrera.class, idAEC);
        return this.servicioAsignacionCursoPensumImpl.cursosSinAprobarValidos(aec);
    }    
    
//______________________________________________________________________________
    /**
     * 
     * @param modelo
     * @param wrapperCursoAprobado 
     */
    private void cargarFormulario(Model modelo, WrapperCursoAprobado wrapperCursoAprobado){
        modelo.addAttribute("wrapperCursoAprobado", wrapperCursoAprobado);        
        modelo.addAttribute("estudiante", this.estudiante);
        modelo.addAttribute("listaAEC", this.listaAEC);        
        modelo.addAttribute("listaACP", this.listaACP);
        modelo.addAttribute("listaTipoAsignacion", TipoAsignacion.values());
    }
}
