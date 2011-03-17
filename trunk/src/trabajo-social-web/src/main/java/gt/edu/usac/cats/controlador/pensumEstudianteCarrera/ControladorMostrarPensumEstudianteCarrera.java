/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.controlador.pensumEstudianteCarrera;

import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.PensumEstudianteCarrera;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
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
public class ControladorMostrarPensumEstudianteCarrera extends ControladorAbstractoPensumEstudianteCarrera{
    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorMostrarPensumEstudianteCarrera.class);

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

        PensumEstudianteCarrera pensumEstudianteCarreraValido = this.servicioPensumEstudianteCarreraImpl.getPensumEstudianteCarreraValido(
                asignacionEstudianteCarrera);

        this.pensum = null;
        //Se obtiene las relaciones con el pensum válido
        if(pensumEstudianteCarreraValido != null){
            this.pensum = pensumEstudianteCarreraValido.getPensum();
            modelo.addAttribute("idPensumEstudianteCarreraValido", pensumEstudianteCarreraValido.getIdPensumEstudianteCarrera());
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
