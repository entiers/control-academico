/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cats.controlador.reporte;

import gt.edu.usac.cats.dominio.Carrera;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.Usuario;
import gt.edu.usac.cats.dominio.busqueda.DatosBusquedaAsignacion;
import gt.edu.usac.cats.dominio.conf.Reporte;
import gt.edu.usac.cats.enums.ControlReporte;
import gt.edu.usac.cats.enums.TipoAsignacion;
import gt.edu.usac.cats.servicio.ServicioGeneral;
import gt.edu.usac.cats.servicio.ServicioUsuario;
import gt.edu.usac.cats.util.RequestUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
@Controller
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class ControladorReporte implements Serializable{

//______________________________________________________________________________
    @Resource
    private ServicioGeneral servicioGeneralImpl;
//______________________________________________________________________________

    private void crearReporte(ControlReporte controlReporte, Map parametros, HttpServletResponse response) throws JRException, SQLException, IOException, URISyntaxException {

        Reporte reporte = this.servicioGeneralImpl.cargarEntidadPorID(Reporte.class, controlReporte);

        String jrxml = this.getClass().getResource("listado/" + reporte.getArchivoJrxml() + ".jrxml").toURI().getPath();

        parametros.put("SUBREPORT_DIR", this.getClass().getResource("listado").toURI().getPath());

        JasperReport jasperReport = JasperCompileManager.compileReport(jrxml);
        WebApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
        DriverManagerDataSource dataSource = (DriverManagerDataSource) applicationContext.getBean("dataSource");

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource.getConnection());

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, output);

        response.setHeader("Content-Disposition", "filename=\"" + reporte.getNombrePdf() + ".pdf\"");
        response.setContentType("application/pdf");
        output.writeTo(response.getOutputStream());
        response.getOutputStream().flush();
    }

    @RequestMapping(value = "generarReporte.htm", method = RequestMethod.POST)
    public void generarReporte(String nombreControlReporte,
            String[] nombreParametro, String[] valorParametro, String[] tipoParametro,
            HttpServletResponse response)
            throws JRException, SQLException, IOException, ClassNotFoundException, URISyntaxException {

        Map parametros = this.obtenerParametrosPorDefault();

        if (nombreParametro != null) {
                System.out.println("param.nombre: "+nombreParametro.length);
                System.out.println("param.tipo: "+valorParametro.length);
                System.out.println("param.valor: "+tipoParametro.length);
            for (int i = 0; i < nombreParametro.length; i++) {

                if (tipoParametro[i].equalsIgnoreCase("integer")) {
                    parametros.put(nombreParametro[i], new Integer(Integer.parseInt(valorParametro[i].toString())));
                } else {
                    if (valorParametro[i].equalsIgnoreCase("boolean")) {
                        parametros.put(nombreParametro[i], Boolean.parseBoolean(valorParametro[i].toString()));
                    } else {
                        parametros.put(nombreParametro[i], valorParametro[i].toString());
                    }
                }
            }
        }
        System.out.println("Nombre Reporte: "+nombreControlReporte);
        ControlReporte controlReporte = ControlReporte.valueOf(nombreControlReporte);
        System.out.println("&&&&&& controlReporte: "+parametros);
        
        if (controlReporte.equals(ControlReporte.CERTIFICACION_CURSOS) ||
                controlReporte.equals(ControlReporte.CURSOS_GANADOS_ESTUDIANTE)){
            parametros.put("fechahoy", generarFechaLetras());
        }
        
        this.crearReporte(controlReporte, parametros, response);
    }

    private String generarFechaLetras(){ 
        Calendar cal = Calendar.getInstance();
        int dia = cal.get(Calendar.DAY_OF_MONTH);
        int mes = cal.get(Calendar.MONTH);
        String diatexto = "";
        switch(dia){
            case 1: diatexto="uno"; break;
            case 2: diatexto="dos"; break;
            case 3: diatexto="tres"; break;
            case 4: diatexto="cuatro"; break;
            case 5: diatexto="cinco"; break;
            case 6: diatexto="seis"; break;
            case 7: diatexto="siete"; break;
            case 8: diatexto="ocho"; break;
            case 9: diatexto="nueve"; break;
            case 10: diatexto="diez"; break;             
            case 11: diatexto="once"; break;
            case 12: diatexto="doce"; break;
            case 13: diatexto="trece"; break;
            case 14: diatexto="catorce"; break;
            case 15: diatexto="quince"; break;
            case 16: diatexto="dieciseis"; break;
            case 17: diatexto="diecisiete"; break;
            case 18: diatexto="dieciocho"; break;
            case 19: diatexto="diecinueve"; break;
            case 20: diatexto="veinte"; break;
            case 21: diatexto="veintiuno"; break;
            case 22: diatexto="veintidos"; break;
            case 23: diatexto="veintitres"; break;
            case 24: diatexto="veinticuatro"; break;
            case 25: diatexto="veinticinco"; break;             
            case 26: diatexto="veintiseis"; break;
            case 27: diatexto="veintisiete"; break;
            case 28: diatexto="veintiocho"; break;
            case 29: diatexto="veintinueve"; break;
            case 30: diatexto="treinta"; break;                
            case 31: diatexto="treinta y uno"; break;                
        }
        diatexto+=" dias ";
        switch (mes){
            case Calendar.JANUARY: diatexto+=" del mes de enero";break;
            case Calendar.FEBRUARY: diatexto+=" del mes de febrero";break;
            case Calendar.MARCH: diatexto+=" del mes de marzo";break;
            case Calendar.APRIL: diatexto+=" del mes de abril ";break;
            case Calendar.MAY: diatexto+=" del mes de mayo ";break;
            case Calendar.JUNE: diatexto+=" del mes de junio ";break;
            case Calendar.JULY: diatexto+=" del mes de julio ";break;
            case Calendar.AUGUST: diatexto+=" del mes de agosto ";break;                
            case Calendar.SEPTEMBER: diatexto+=" del mes de septiembre ";break;
            case Calendar.OCTOBER: diatexto+=" del mes de octubre ";break;
            case Calendar.NOVEMBER: diatexto+=" del mes de noviembre ";break;
            case Calendar.DECEMBER: diatexto+=" del mes de diciembre ";break;                
        }
        diatexto+=" del "+cal.get(Calendar.YEAR);
        diatexto="a los "+diatexto;
        return diatexto;
    }
        private Map obtenerParametrosPorDefault() {
            
        return new HashMap();
    }
        
}
