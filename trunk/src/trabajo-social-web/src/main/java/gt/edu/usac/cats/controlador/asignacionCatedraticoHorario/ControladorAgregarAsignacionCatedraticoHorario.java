/*
 * Universidad de San Carlos de Guatemala
 * Escuela de Trabajo Social
 * Control Academico
 */

package gt.edu.usac.cats.controlador.asignacionCatedraticoHorario;


import gt.edu.usac.cats.dominio.AsignacionCatedraticoHorario;
import gt.edu.usac.cats.dominio.Catedratico;
import gt.edu.usac.cats.dominio.Horario;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.dominio.busqueda.DatosIngresoNota;
import gt.edu.usac.cats.enums.TipoHorario;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import java.io.Serializable;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
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
 * Controlador encargado de manejar las peticiones GET y POST de la p�gina 
 * agregarAsignacionCatedraticoHorario.htm. Dentro de la clase se realiza toda la
 * logica relacionada con la asignacion de un catedratico a un horario especifico
 * 
 * @author Carlos Solorzano
 * @version 1.0
 */
@Controller
@Scope(value = WebApplicationContext.SCOPE_SESSION)
@SessionAttributes(value={"semestre", "catedratico", "listadoHorario"}) 
public class ControladorAgregarAsignacionCatedraticoHorario extends ControladorAbstractoAsignacionCatedraticoHorario implements Serializable{
//______________________________________________________________________________       
    private static Logger log = Logger.getLogger(ControladorAgregarAsignacionCatedraticoHorario.class);
//_____________________________________________________________________________
    private static final String TITULO_MENSAJE = "asignacionHorarioCatedratico.titulo";
//______________________________________________________________________________    
    private List<Horario> listadoHorario;
//______________________________________________________________________________    
    private Catedratico catedratico;
//______________________________________________________________________________
    private Semestre semestre;
//______________________________________________________________________________
    
    /**
     * Metodo encargado de resolver las peticiones de tipo GET de la p�gina 
     * <p>agregarAsignacionCatedraticoHorario.htm</p> asi como de buscar al catedratico 
     * asociado al idCatedratico enviado por parametro y de inicializar los 
     * objetos utilizados en la pagina.
     * 
     * @param modelo
     * @param request
     * @param idCatedratico
     * @return 
     */
    @RequestMapping(value="agregarAsignacionCatedraticoHorario.htm",method= RequestMethod.GET)
    public String getAgregarCatHorario(Model modelo, HttpServletRequest request, Integer idCatedratico){

        if(idCatedratico==null){
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "asignacionHorarioCatedratico.idCatedratico.nulo", false);
            RequestUtil.agregarRedirect(request, "buscarCatedratico.htm");
            return "asignacionCatedraticoHorario/agregarAsignacionCatedraticoHorario";
        }
        try {
            this.catedratico = super.servicioGeneralImpl.cargarEntidadPorID(Catedratico.class, Short.parseShort(idCatedratico.toString()) );
            this.semestre = super.servicioSemestreImpl.getSemestreActivo();
            this.listadoHorario = this.servicioAsignacionCatedraticoHorarioImpl.getHorarioDiponibleCatedratico(this.semestre, TipoHorario.values()[0]);
            this.crearModelo(modelo);
        }
        catch(Exception ex){
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, null, "dataAccessException", false);
            RequestUtil.agregarRedirect(request, "buscarCatedratico.htm");
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, ex);
        }
        finally{
            return "asignacionCatedraticoHorario/agregarAsignacionCatedraticoHorario";
        }
        
    }            
//______________________________________________________________________________    
    /**
     * Metodo encargado de resolver las peticiones de tipo POST de la p�gina 
     * <p>agregarAsignacionCatedraticoHorario.htm</p>. Este metodo es el responsable
     * de crear la asignacion de un horario a un catedratico en especifico.     
     * 
     * @param modelo
     * @param request
     * @param idCatedratico
     * @return 
     */   
    @RequestMapping(value="agregarAsignacionCatedraticoHorario.htm",method= RequestMethod.POST)
    public String postRealizarAgregarCatHorario(@Valid DatosIngresoNota datosIngresoNota,
                                        BindingResult bindingResult,
                                        Model modelo, HttpServletRequest request){

        try{
            AsignacionCatedraticoHorario aCH = new AsignacionCatedraticoHorario();
            aCH.setCatedratico(this.catedratico);
            aCH.setHorario(datosIngresoNota.getHorario());
            super.servicioGeneralImpl.agregar(aCH);
            this.crearModelo(modelo);
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "asignacionHorarioCatedratico.exitosa", true);
            RequestUtil.agregarRedirect(request, "buscarCatedratico.htm");
        }
        catch(Exception ex){
             // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, null, "dataAccessException", false);
            RequestUtil.agregarRedirect(request, "buscarCatedratico.htm");
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, ex);
        }
        finally{
            return "asignacionCatedraticoHorario/agregarAsignacionCatedraticoHorario";
        }       
        
    }    
    
    private void crearModelo(Model modelo){
        modelo.addAttribute("datosIngresoNota", new DatosIngresoNota());
        modelo.addAttribute("catedratico", this.catedratico);
        modelo.addAttribute("listaTipoHorario",TipoHorario.values());
        modelo.addAttribute("listaHorario",this.listadoHorario);
        modelo.addAttribute("modeloOK",true);
    }
    
    //  _____________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina <code>getAsignacionHorarioCatedratico.htm</code>. El metodo se encarga
     * de inicializar las listas de horario en base a los parametros enviados.
     *
     * @param idTipoHorario tipo de horario a buscar
     * @return List<Horario> listado filtrado en base a los parametros enviados.
     */
    @RequestMapping(value = "getAsignacionHorarioCatedratico.htm", method = RequestMethod.GET)
    public @ResponseBody String getHorario(@RequestParam String idTipoHorario, HttpServletRequest request) {
        String strOptions = "";
        try {
            TipoHorario tipoHorario = TipoHorario.valueOf(idTipoHorario);
            this.listadoHorario = super.servicioAsignacionCatedraticoHorarioImpl.getHorarioDiponibleCatedratico(this.semestre,tipoHorario);                        
            for (Horario horario : this.listadoHorario){
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
}
