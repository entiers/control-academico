
/*
* Sistema de Control Academico
* Escuela de Trabajo Social
* Universidad de San Carlos de Guatemala
 */
package gt.edu.usac.cats.controlador.asignacion;

//~--- non-JDK imports --------------------------------------------------------

import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.Usuario;
import gt.edu.usac.cats.dominio.busqueda.DatosBusquedaAsignacion;
import gt.edu.usac.cats.enums.ControlReporte;
import gt.edu.usac.cats.enums.TipoAsignacion;
import gt.edu.usac.cats.servicio.ServicioAsignacion;
import gt.edu.usac.cats.servicio.ServicioGeneral;
import gt.edu.usac.cats.servicio.ServicioUsuario;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.log4j.Logger;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//~--- JDK imports ------------------------------------------------------------
import java.util.Set;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;

/**
 * Esta clase es el controlador que se encarga de la llamada a la consulta de cursos
 * asignados por estudiante en un anio y numero determinado
 *
 * @author Carlos Solorzano
 */
@Controller
@Scope(value = WebApplicationContext.SCOPE_SESSION)
@SessionAttributes(value={"estudiante", "usuario"}) 
public class ControladorBuscarAsignacionPorEstudiante implements Serializable{

//  _____________________________________________________________________________
    private static final String TITULO_MENSAJE = "miscursos.asignados";
//  ______________________________________________________________________________
    private static Logger log = Logger.getLogger(ControladorBuscarAsignacionPorEstudiante.class);
//  _____________________________________________________________________________
    private Estudiante estudiante;
//  _____________________________________________________________________________
    private Usuario usuario;
//  _____________________________________________________________________________
    @Resource
    private ServicioGeneral servicioGeneralImpl;
//  _____________________________________________________________________________
    @Resource
    private ServicioUsuario servicioUsuarioImpl;
//  _____________________________________________________________________________
    @Resource
    private ServicioAsignacion servicioAsignacionImpl;
//  muestra el formulario para realizar la busqueda por estudiante. mc.
    @RequestMapping(
        value  = "buscarAsignacionPorEstudiante.htm",
        method = RequestMethod.GET
    )
    public String asignacionPorEstudiante(Model modelo, HttpServletRequest request) {
        // Buscar usuario para determinar si es estudiante o no
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        usuario = this.servicioUsuarioImpl.cargarUsuarioPorNombre(auth.getName().toString());

        if (usuario == null) {
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "buscarUsuario.sinResultados", false);
            modelo.addAttribute("error", true);
            return "asignacion/buscarAsignacionPorEstudiante";
        }

        Set<Estudiante> stEst = usuario.getEstudiantes();
        if (!stEst.isEmpty()) {
            this.estudiante = stEst.iterator().next();
        } else {
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "buscarEstudiante.sinResultados", false);
            modelo.addAttribute("error", true);
            return "asignacion/buscarAsignacionPorEstudiante";
        }

        modelo.addAttribute("datosBusquedaAsignacion", new DatosBusquedaAsignacion());
        this.setModelo(modelo,false);
        
        return "asignacion/buscarAsignacionPorEstudiante";
    }

    //  realiza la accion de buscar la asignacion por estudiante. mc.
    @RequestMapping(
        value  = "buscarAsignacionPorEstudiante.htm",
        method = RequestMethod.POST
    )
    public String buscarAsignacionPorEstudiante(@Valid DatosBusquedaAsignacion datosBusquedaAsignacion, BindingResult bindingResult,
                                Model modelo, HttpServletRequest request) {
        this.setModelo(modelo,true);
        try{
            modelo.addAttribute("nombreControlReporte",ControlReporte.DETALLE_ASIGNACION);
            modelo.addAttribute("listadoAsignacion",
                                this.servicioAsignacionImpl.buscarAsignacionPorEstudiante(this.estudiante,
                                                                                        datosBusquedaAsignacion.getTipoAsignacion(),
                                                                                        datosBusquedaAsignacion.getAnio()));
        }
        catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }
        //buscar Asignacion
        
        return "asignacion/buscarAsignacionPorEstudiante";
    }


    private void setModelo(Model modelo,boolean post) {
        Calendar fecha = Calendar.getInstance();
        int anio = fecha.get(Calendar.YEAR);
        List listadoAnio = new ArrayList();
        for(int i=anio;i>anio-3;i--){
            listadoAnio.add(i);
        }
        modelo.addAttribute("post", post);
        modelo.addAttribute("error",false);
        modelo.addAttribute("lstAnio",listadoAnio);
        modelo.addAttribute("listadoTipoAsignacion",
                            TipoAsignacion.values());
    }
}