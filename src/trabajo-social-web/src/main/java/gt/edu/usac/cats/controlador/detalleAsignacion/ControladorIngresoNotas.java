/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.controlador.detalleAsignacion;

import gt.edu.usac.cats.dominio.DetalleAsignacion;
import gt.edu.usac.cats.dominio.wrapper.WrapperIngresoNota;
import gt.edu.usac.cats.servicio.ServicioDetalleAsignacion;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Carlos Solorzano
 * @version 1.0
 */


@Controller
public class ControladorIngresoNotas {

    private List<DetalleAsignacion> listadoDetalleAsignacion;

    @Resource
    private ServicioDetalleAsignacion servicioDetalleAsignacionImpl;

    @RequestMapping(value="ingresoNota.htm",method=RequestMethod.GET)
    public String crearFormulario(Model modelo) {
        this.listadoDetalleAsignacion = this.servicioDetalleAsignacionImpl.getListadoDetalleAsignacion(58);
        modelo.addAttribute("listadoDetalleAsignacion", this.listadoDetalleAsignacion);
        modelo.addAttribute("wrapperIngresoNota", new WrapperIngresoNota());

        return "detalleAsignacion/ingresoNota";
    }

    @RequestMapping(value="ingresoNota.htm",method=RequestMethod.POST)
    public String metodoPost(@Valid WrapperIngresoNota wrapperIngresoNota, BindingResult bindingResult,
            Model modelo, HttpServletRequest request) {

        int i=0;
        for(DetalleAsignacion detAsign : this.listadoDetalleAsignacion){
            detAsign.setZona(Short.valueOf(wrapperIngresoNota.getListZona().get(i).toString()));
            detAsign.setLaboratorio(Short.valueOf(wrapperIngresoNota.getListLaboratorio().get(i).toString()));
            detAsign.setExamenFinal(Short.valueOf(wrapperIngresoNota.getListFinal().get(i).toString()));
            detAsign.setOficializado(wrapperIngresoNota.getOficializar());
            this.servicioDetalleAsignacionImpl.actualizar(detAsign);
            i++;
        }

        return "detalleAsignacion/ingresoNota";
    }

}
