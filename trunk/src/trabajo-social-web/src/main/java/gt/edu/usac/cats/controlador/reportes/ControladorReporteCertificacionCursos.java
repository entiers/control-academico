/*
* Sistema de Control Academico
* Escuela de Trabajo Social
* Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.controlador.reportes;

//~--- non-JDK imports --------------------------------------------------------
import gt.edu.usac.cats.dominio.Carrera;
import gt.edu.usac.cats.enums.ControlReporte;
import gt.edu.usac.cats.servicio.ServicioGeneral;
import javax.annotation.Resource;

import org.apache.log4j.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Esta clase es el controlador que se encarga de la llamada al reporte certificaciones de cursos
 *
 * @author Ing. Carlos Solorzano
 */

@Controller("controladorReporteCertificacionCursos")
public class ControladorReporteCertificacionCursos {
//______________________________________________________________________________
    private static String TITULO_MENSAJE = "rptCertificacionCursos.titulo";
//______________________________________________________________________________
    private static Logger log = Logger.getLogger(ControladorReporteCertificacionCursos.class);
//  _____________________________________________________________________________
    @Resource
    private ServicioGeneral servicioGeneralImpl;
//  _____________________________________________________________________________
    /**
     * @param modelo
     * @param request
     * @return
     */
    @RequestMapping(value  = "rptCertificacionCursos.htm",method = RequestMethod.GET)
    public String getRptCertificaciones(Model modelo, HttpServletRequest request) {        
        modelo.addAttribute("listadoCarrera",servicioGeneralImpl.listarEntidad(Carrera.class));
        modelo.addAttribute("nombreControlReporte",ControlReporte.CERTIFICACION_CURSOS);     
        return "reportes/rptCertificacionCursos";
    }
//  _____________________________________________________________________________
    /**
     * @param modelo
     * @param request
     * @return
     */
    @RequestMapping(value  = "rptNotasCursosAsignados.htm",method = RequestMethod.GET)
    public String getRptNotasCursosAsignados(Model modelo, HttpServletRequest request) {                
        modelo.addAttribute("nombreControlReporte",ControlReporte.NOTAS_CURSOS_ASIGNADOS);     
        return "reportes/rptNotasCursosAsignados";
    }

}
