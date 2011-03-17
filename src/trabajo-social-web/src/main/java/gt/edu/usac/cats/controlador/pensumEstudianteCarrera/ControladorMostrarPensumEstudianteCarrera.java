/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.controlador.pensumEstudianteCarrera;

import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.Pensum;
import gt.edu.usac.cats.dominio.PensumEstudianteCarrera;
import gt.edu.usac.cats.servicio.ServicioPensumEstudianteCarrera;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
@Controller
public class ControladorMostrarPensumEstudianteCarrera {
    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorMostrarPensumEstudianteCarrera.class);

    private AsignacionEstudianteCarrera asignacionEstudianteCarrera;

    private Pensum pensum;

    @Resource
    private ServicioPensumEstudianteCarrera servicioPensumEstudianteCarreraImpl;

    private void agregarAtributosDefault(Model modelo, PensumEstudianteCarrera pensumEstudianteCarrera
            , boolean autoOpenDialogAsignar){       

        //Se obtiene los no válidos
        List <PensumEstudianteCarrera> listadoPensumEstudianteCarreraNoValidos
                = this.servicioPensumEstudianteCarreraImpl.getListadoPensumEstudianteCarreraNoValidos(asignacionEstudianteCarrera);



        List <Pensum> listadoPensumsNoAsignadosAEsutudianteCarrera =
                this.servicioPensumEstudianteCarreraImpl.getPensumsNoAsignadosAEstudianteCarrera(asignacionEstudianteCarrera);

        modelo.addAttribute("estudiante", asignacionEstudianteCarrera.getEstudiante());
        modelo.addAttribute("carrera", asignacionEstudianteCarrera.getCarrera());
        modelo.addAttribute("pensum", pensum);
        modelo.addAttribute("pensumEstudianteCarrera", pensumEstudianteCarrera);
        modelo.addAttribute("listadoPensumEstudianteCarreraNoValidos", listadoPensumEstudianteCarreraNoValidos);
        modelo.addAttribute("pensumEstudianteCarrera", new PensumEstudianteCarrera());
        modelo.addAttribute("listadoPensumsNoAsignadosAEsutudianteCarrera", listadoPensumsNoAsignadosAEsutudianteCarrera);
        modelo.addAttribute("autoOpenDialogAsignar", autoOpenDialogAsignar);
    }

    @RequestMapping(value="mostrarPensumEstudianteCarrera.htm", method=RequestMethod.GET)
    public String mostrarPensumEstudianteCarrera(Model modelo
            , Integer idAsignacionEstudianteCarrera){

        if(idAsignacionEstudianteCarrera == null){
            return "redirect:buscarEstudiante.htm";
        }

        //Se obtiene la asignación del estudiante con la carrera
        this.asignacionEstudianteCarrera= this.servicioPensumEstudianteCarreraImpl
                .cargarEntidadPorID(
                    AsignacionEstudianteCarrera.class
                    , idAsignacionEstudianteCarrera
                    );

        if(asignacionEstudianteCarrera == null){
            return "redirect:buscarEstudiante.htm";
        }

        PensumEstudianteCarrera pensumEstudianteCarrera = this.servicioPensumEstudianteCarreraImpl.getPensumEstudianteCarreraValido(
                asignacionEstudianteCarrera);

        this.pensum = null;
        //Se obtiene las relaciones con el pensum válido
        if(pensumEstudianteCarrera != null){
            this.pensum = pensumEstudianteCarrera.getPensum();
        }

        this.agregarAtributosDefault(modelo, new PensumEstudianteCarrera(), false);

        return "pensumEstudianteCarrera/mostrarPensumEstudianteCarrera";
    }


    @RequestMapping(value="asignarPensumEstudianteCarrera.htm", method=RequestMethod.POST)
    public String asignar(Model modelo, @Valid PensumEstudianteCarrera pensumEstudianteCarrera
            , BindingResult bindingResult, HttpServletRequest request){

        boolean autoOpenDialogAsignar = false;
        if(!bindingResult.hasErrors()){
            try{
                
                pensumEstudianteCarrera.setAsignacionEstudianteCarrera(asignacionEstudianteCarrera);
                pensumEstudianteCarrera.setValido(true);                
                this.servicioPensumEstudianteCarreraImpl.agregar(pensumEstudianteCarrera);
                
                RequestUtil.agregarRedirect(request, "mostrarPensumEstudianteCarrera.htm?idAsignacionEstudianteCarrera=" 
                        + this.asignacionEstudianteCarrera.getIdAsignacionEstudianteCarrera());
                RequestUtil.crearMensajeRespuesta(request, "asignarPensumEstudianteCarrera.titulo", "asignarPensumEstudianteCarrera.exito", true);
                log.info(Mensajes.EXITO_AGREGAR + "PensumEstudianteCarrera, id " + pensumEstudianteCarrera.getIdPensumEstudianteCarrera());                

            }catch(DataIntegrityViolationException e){
                RequestUtil.crearMensajeRespuesta(request, "asignarPensumEstudianteCarrera.titulo", "dataIntegrityViolatioException", false);
                log.error(Mensajes.DATA_INTEGRITY_VIOLATION_EXCEPTION, e);
            }catch(DataAccessException e){
                RequestUtil.crearMensajeRespuesta(request, "asignarPensumEstudianteCarrera.titulo", "dataAccessException", false);
                log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
            }
        }else{
            autoOpenDialogAsignar = true;
            this.agregarAtributosDefault(modelo, pensumEstudianteCarrera, autoOpenDialogAsignar);
        }
        
        return "pensumEstudianteCarrera/mostrarPensumEstudianteCarrera";
    }

}
