/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.controlador.cursoAprobado;

import gt.edu.usac.cats.dominio.Asignacion;
import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.CursoAprobado;
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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author Carlos Solorzano
 * @version 1.0
 */

@Controller
@Scope(value = WebApplicationContext.SCOPE_SESSION)
@SessionAttributes(value={"estudiante", "listaAEC", "cursoAprobado", "listaCursoAprobado"})
public class ControladorModificarCursoAprobado implements Serializable{

    private static String TITULO_MENSAJE = "admin.modificarCursoAprobado.titulo";
//______________________________________________________________________________    
    private static Logger log = Logger.getLogger(ControladorModificarCursoAprobado.class);
//______________________________________________________________________________    
    private Estudiante estudiante;
//______________________________________________________________________________        
    private CursoAprobado cursoAprobado;
//______________________________________________________________________________    
    private List<AsignacionEstudianteCarrera> listaAEC;
//______________________________________________________________________________    
    private List<CursoAprobado> listaCursoAprobado;    
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
    @RequestMapping(value = "modificarCursoAprobadoLista.htm", method = RequestMethod.GET)
    public String crearFormulario(Model modelo, int idEstudiante) {                
        this.estudiante = this.servicioAsignacionEstudianteCarreraImpl.cargarEntidadPorID(Estudiante.class, 
                idEstudiante);
        if(this.estudiante != null){
            this.listaAEC = this.servicioAsignacionEstudianteCarreraImpl.getAsignacionEstudianteCarrera(estudiante, true);                
            modelo.addAttribute("estudianteSinCursosModificables", this.listaAEC.isEmpty());            
        } else {
            modelo.addAttribute("estudianteNoExiste", true);        
        }        
        modelo.addAttribute("estudiante", this.estudiante);
        modelo.addAttribute("wrapperCursoAprobado", new WrapperCursoAprobado());
        modelo.addAttribute("listaAEC", this.listaAEC);
        return "cursoAprobado/modificarCursoAprobadoLista";
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
    @RequestMapping(value = "modificarCursoAprobadoLista.htm", method = RequestMethod.POST)
    public String agregarCursoAprobado(@Valid WrapperCursoAprobado wrapperCursoAprobado,
            BindingResult bindingResult, Model modelo, HttpServletRequest request) {
        modelo.addAttribute("estudiante", this.estudiante);
        modelo.addAttribute("wrapperCursoAprobado", wrapperCursoAprobado);
        modelo.addAttribute("listaAEC", this.listaAEC);
        AsignacionEstudianteCarrera aec = this.servicioAsignacionEstudianteCarreraImpl.cargarEntidadPorID(
                AsignacionEstudianteCarrera.class, wrapperCursoAprobado.getIdAsignacionEstudianteCarrera());
        
        this.listaCursoAprobado = this.servicioCursoAprobadoImpl.listaCursoAprobadoModificable(aec);
        
        modelo.addAttribute("listaCursoAprobado", this.listaCursoAprobado);
        return "cursoAprobado/modificarCursoAprobadoLista";
    }     

    
    @RequestMapping(value = "modificarCursoAprobado.htm", method = RequestMethod.GET)
    public String modificarCursoAprobadoAccion(Model modelo, int idCA) {
        cursoAprobado = this.servicioCursoAprobadoImpl.cargarEntidadPorID(CursoAprobado.class, idCA);
        
        WrapperCursoAprobado wrapperCursoAprobado = new WrapperCursoAprobado();
        wrapperCursoAprobado.agregarWrapper(cursoAprobado);
        
        modelo.addAttribute("listaTipoAsignacion", TipoAsignacion.values());
        modelo.addAttribute("cursoAprobado", this.cursoAprobado);
        modelo.addAttribute("estudiante", this.estudiante);
        modelo.addAttribute("wrapperCursoAprobado", wrapperCursoAprobado);
        return "cursoAprobado/modificarCursoAprobado";
    }

    @RequestMapping(value = "modificarCursoAprobado.htm", method = RequestMethod.POST)
    public String modificarCursoAprobadoAccionPost(@Valid WrapperCursoAprobado wrapperCursoAprobado,
            BindingResult bindingResult, Model modelo, HttpServletRequest request) {
        modelo.addAttribute("listaTipoAsignacion", TipoAsignacion.values());
        modelo.addAttribute("cursoAprobado", this.cursoAprobado);
        modelo.addAttribute("estudiante", this.estudiante);
        modelo.addAttribute("wrapperCursoAprobado", wrapperCursoAprobado);
        
        if(!bindingResult.hasErrors()){
            //Validando nota de aprobacion
            if(wrapperCursoAprobado.getExamenFinal() + wrapperCursoAprobado.getZona() < 
                    this.cursoAprobado.getAsignacionCursoPensum().getNotaAprobacion()){
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, 
                        "admin.agregarCursoAprobado.notaAprobacionInvalida", false);
                return "cursoAprobado/modificarCursoAprobado";
            }

            //Actualizando curso aprobado
            wrapperCursoAprobado.quitarWrapper(cursoAprobado);
            this.servicioCursoAprobadoImpl.actualizar(cursoAprobado);
            
            Asignacion asignacion = cursoAprobado.getAsignacion();
            asignacion.setTipoAsignacion(wrapperCursoAprobado.getTipoAsignacion());
            this.servicioCursoAprobadoImpl.actualizar(asignacion);
            
            //Registrando evento
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, 
                        "admin.modificarCursoAprobado.exito", true);
            String msg = Mensajes.EXITO_ACTUALIZACION + "CursoAprobado, Estudiante: " + estudiante.getCarne() + 
                    " Asignacion curso pensum: " + this.cursoAprobado.getAsignacionCursoPensum().getIdAsignacionCursoPensum();
            log.info(msg);
        }
        return "cursoAprobado/modificarCursoAprobado";
    }
}
