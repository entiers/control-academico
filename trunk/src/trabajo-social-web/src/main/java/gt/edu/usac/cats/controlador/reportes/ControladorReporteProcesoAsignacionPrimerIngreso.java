/*
* Sistema de Control Academico
* Escuela de Trabajo Social
* Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.controlador.reportes;

//~--- non-JDK imports --------------------------------------------------------

import gt.edu.usac.cats.dominio.busqueda.DatosRptAsignacionPrimerIngreso;
import gt.edu.usac.cats.enums.ControlReporte;
import gt.edu.usac.cats.servicio.ServicioAsignacionPrimerIngreso;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import javax.annotation.Resource;

import org.apache.log4j.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.dao.DataAccessException;
import org.springframework.validation.BindingResult;

/**
 * Esta clase es el controlador que se encarga de la llamada al reporte del proceso de
 * asignacion de primer ingreso
 *
 * @author Carlos Solorzano
 */

@Controller("controladorReporteProcesoAsignacionPrimerIngreso")
public class ControladorReporteProcesoAsignacionPrimerIngreso {
//______________________________________________________________________________
    private static String TITULO_MENSAJE = "agregarCurso.titulo";
//______________________________________________________________________________
    private static Logger log = Logger.getLogger(ControladorReporteProcesoAsignacionPrimerIngreso.class);
//  _____________________________________________________________________________
    @Resource
    private ServicioAsignacionPrimerIngreso servicioAsignacionPrimerIngresoImpl;
//  _____________________________________________________________________________
    @RequestMapping(
        value  = "rptProcesoAsignacionPrimerIngreso.htm",
        method = RequestMethod.GET
    )
    public String procesoAsignacionPrimerIngreso(Model modelo, HttpServletRequest request) {
        modelo.addAttribute("post", false);
        modelo.addAttribute("datosRptAsignacionPrimerIngreso", new DatosRptAsignacionPrimerIngreso());
        return "reportes/rptProcesoAsignacionPrimerIngreso";
    }
//  _____________________________________________________________________________
    @RequestMapping(
        value  = "rptProcesoAsignacionPrimerIngreso.htm",
        method = RequestMethod.POST
    )
    public String buscarProcesoAsignacionPrimerIngreso(@Valid DatosRptAsignacionPrimerIngreso datosRptAsignacionPrimerIngreso
                                , BindingResult bindingResult
                                , Model modelo, HttpServletRequest request) {
        try{
            modelo.addAttribute("nombreControlReporte",ControlReporte.DETALLE_ASIGNACION_PRIMER_INGRESO);
            modelo.addAttribute("listadoAPI",
                        this.servicioAsignacionPrimerIngresoImpl
                            .getAsignacionPrimerIngreso(datosRptAsignacionPrimerIngreso.getNombreUsuario(),
                                                        datosRptAsignacionPrimerIngreso.getFechaInicio(),
                                                        datosRptAsignacionPrimerIngreso.getFechaFin()));
        } catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }
        modelo.addAttribute("post", true);
        return "reportes/rptProcesoAsignacionPrimerIngreso";
    }

}
