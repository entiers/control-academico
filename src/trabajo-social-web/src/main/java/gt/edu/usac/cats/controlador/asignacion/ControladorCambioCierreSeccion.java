/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */
package gt.edu.usac.cats.controlador.asignacion;

import gt.edu.usac.cats.dominio.AsignacionCursoPensum;
import gt.edu.usac.cats.dominio.Horario;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.dominio.busqueda.DatosBusquedaHorario;
import gt.edu.usac.cats.dominio.wrapper.WrapperCambioSeccion;
import gt.edu.usac.cats.servicio.ServicioDetalleAsignacion;
import gt.edu.usac.cats.servicio.ServicioGeneral;
import gt.edu.usac.cats.servicio.ServicioHorario;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;

/**
 * Esta clase es el controlador que se encarga de la llamada al la solicitud del cambio
 * de asignacion en una seccion, y en dado caso, el cierre de la misma.
 * 
 * @author Carlos Solorzano
 * @version 1.0
 */
@Controller
@Scope(value = WebApplicationContext.SCOPE_SESSION)
@SessionAttributes(value={"horario"}) 
public class ControladorCambioCierreSeccion implements Serializable {
//______________________________________________________________________________
    private static Logger log = Logger.getLogger(ControladorCambioCierreSeccion.class);
//_____________________________________________________________________________
    private static final String TITULO_MENSAJE = "cambioCierreSeccion.titulo";
//_____________________________________________________________________________
    private Horario horario;
//_____________________________________________________________________________
    @Resource
    private ServicioGeneral servicioGeneralImpl;
//_____________________________________________________________________________
    @Resource
    private ServicioHorario servicioHorarioImpl;
//_____________________________________________________________________________
    @Resource
    private ServicioDetalleAsignacion servicioDetalleAsignacionImpl;
//_____________________________________________________________________________
    @RequestMapping(value="cambioCierreSeccion.htm",method = RequestMethod.GET)
    public String setPagina(Model modelo, HttpServletRequest request) {
        modelo.addAttribute("datosBusquedaHorario", new DatosBusquedaHorario());
        this.setModelo(modelo, false);
        return "asignacion/cambioCierreSeccion";
    }
//  _____________________________________________________________________________
    @RequestMapping(value  = "cambioCierreSeccion.htm",method = RequestMethod.POST)
    public String buscarHorarioDisponible(@Valid DatosBusquedaHorario datosBusquedaHorario, BindingResult bindingResult,
                                Model modelo, HttpServletRequest request) {
        
        try{
            //buscarHorario
            List listadoHorario = this.servicioHorarioImpl.getHorario(datosBusquedaHorario.getAsignacionCursoPensum(),
                                                    datosBusquedaHorario.getSemestre());
            
            modelo.addAttribute("listadoHorario",listadoHorario);
            modelo.addAttribute("listadoHorarioSize",listadoHorario.size());
        }
        catch(DataAccessException e){
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }
        
        this.setModelo(modelo, true);
        return "asignacion/cambioCierreSeccion";
    }
//_____________________________________________________________________________
    @RequestMapping(value="cambioCierreSeccion2.htm",method = RequestMethod.GET)
    public String setCierreSeccion2(Integer idHorario,
                                    Model modelo,
                                    HttpServletRequest request) {
        try{
            this.horario = this.servicioGeneralImpl.cargarEntidadPorID(Horario.class, idHorario);
            if(horario!=null){
                modelo.addAttribute("wrapperCambioSeccion", new WrapperCambioSeccion());
                this.setModelo2(modelo,idHorario);
            }
        }
        catch(DataAccessException e){
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }
        return "asignacion/cambioCierreSeccion2";
    }
//  _____________________________________________________________________________
    @RequestMapping(value  = "cambioCierreSeccion2.htm",method = RequestMethod.POST)
    public String cambiarSeccion(@Valid WrapperCambioSeccion wrapperCambioSeccion, BindingResult bindingResult,
                                Model modelo, HttpServletRequest request) {
        this.setModelo2(modelo, this.horario.getIdHorario());


        this.servicioDetalleAsignacionImpl.
                cambioCierreSeccion(wrapperCambioSeccion.getHorario(), wrapperCambioSeccion.getDetalleAsignacion());
        
        RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "cambioCierreSeccion.exito", true);

        return "redirect:cambioCierreSeccion.htm";
    }

//  _____________________________________________________________________________
    private void setModelo(Model modelo,boolean post){
        modelo.addAttribute("post", post);
        modelo.addAttribute("listadoCursos",
                        this.servicioHorarioImpl.listarEntidad(AsignacionCursoPensum.class,true, "curso.idCurso"));
        modelo.addAttribute("listadoSemestres",
                        this.servicioGeneralImpl.listarEntidad(Semestre.class));
    }
//_____________________________________________________________________________
    private void setModelo2(Model modelo, Integer idHorario){
        modelo.addAttribute("listadoHorarios",
                        this.servicioHorarioImpl.getHorarioCambioSeccion(horario));
        modelo.addAttribute("listadoEstudiantes",
                        this.servicioDetalleAsignacionImpl.getListadoDetalleAsignacion(idHorario));
    }
}
