/*
 * Universidad de San Carlos de Guatemala
 * Escuela de Trabajo Social
 * Control Academico
 */

package gt.edu.usac.cats.controlador.asignacionCatedraticoHorario;


import gt.edu.usac.cats.dominio.AsignacionCatedraticoHorario;
import gt.edu.usac.cats.dominio.Catedratico;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import java.io.Serializable;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;

/**
 * Controlador encargado de manejar las peticiones GET y POST de la p�gina 
 * buscarAsignacionCatedraticoHorario.htm. La clase se encarga de manejar
 * la logica para la consulta de las asignaciones de horarios de un catedratico 
 * en especifico
 * 
 * @author Carlos Solorzano
 * @version 1.0
 */
@Controller
@Scope(value = WebApplicationContext.SCOPE_SESSION)
@SessionAttributes(value={"catedratico"}) 
public class ControladorBuscarAsignacionCatedraticoHorario extends ControladorAbstractoAsignacionCatedraticoHorario implements Serializable{
//______________________________________________________________________________
    private static Logger log = Logger.getLogger(ControladorAgregarAsignacionCatedraticoHorario.class);
//______________________________________________________________________________
    private static final String TITULO_MENSAJE = "asignacionHorarioCatedratico.titulo";
//______________________________________________________________________________    
    private Catedratico catedratico;
//______________________________________________________________________________
    /**
     * Metodo encargado de resolver las peticiones tipo get de la pagina 
     * <p>buscarAsignacionCatedraticoHorario.htm</p>. Asi mismo se encarga de 
     * buscar las asignaciones para el catedratico especificado.
     *
     *
     * @param modelo
     * @param idCatedratico
     * @param request
     * @return
     */
    @RequestMapping(value="buscarAsignacionCatedraticoHorario.htm",method= RequestMethod.GET)
    public String getBuscarCatHorario(Model modelo, @RequestParam Integer idCatedratico,
            HttpServletRequest request){

        List<AsignacionCatedraticoHorario> listaACH;
        try{
            this.catedratico = super.servicioGeneralImpl.cargarEntidadPorID(Catedratico.class, Short.parseShort(idCatedratico.toString()) );
            listaACH = super.servicioAsignacionCatedraticoHorarioImpl.getAsignacionCatedraticoHorario(
                    super.servicioSemestreImpl.getSemestreActivo(), this.catedratico);
            modelo.addAttribute("catedratico", this.catedratico);
            modelo.addAttribute("listaACH", listaACH);
        }
        catch(org.hibernate.ObjectNotFoundException ex){
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "asignacionHorarioCatedratico.catedraticoNoEncontrado", false);
            RequestUtil.agregarRedirect(request, "buscarCatedratico.htm");
        }
        catch(Exception ex){
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, null, "dataAccessException", false);
            RequestUtil.agregarRedirect(request, "buscarCatedratico.htm");
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, ex);
        }
        finally{
            return "asignacionCatedraticoHorario/buscarAsignacionCatedraticoHorario";
        }
        
    }

    @RequestMapping(value="eliminarACH.htm",method= RequestMethod.GET)
    public @ResponseBody String eliminarAsignacionCatedraticoHorario(@RequestParam int idACH,
                            HttpServletRequest request){
    
        try{
            AsignacionCatedraticoHorario ach = this.servicioGeneralImpl.cargarEntidadPorID(AsignacionCatedraticoHorario.class, idACH);
            this.servicioGeneralImpl.borrar(ach);
        }
        catch(Exception ex){
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, null, "dataAccessException", false);
            RequestUtil.agregarRedirect(request, "buscarCatedratico.htm");
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, ex);
            return "dataAccessException";
        }
        finally{
            return "ok";
        }
    }
    
}
