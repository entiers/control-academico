/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cats.controlador.reporte;

import gt.edu.usac.cats.dominio.conf.Reporte;
import gt.edu.usac.cats.enums.ControlReporte;
import gt.edu.usac.cats.servicio.ServicioGeneral;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Controller;
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
        ControlReporte controlReporte = ControlReporte.valueOf(nombreControlReporte);
        System.out.println("&&&&&& controlReporte: "+parametros);
        this.crearReporte(controlReporte, parametros, response);
    }

    private Map obtenerParametrosPorDefault() {
        return new HashMap();
    }
}
