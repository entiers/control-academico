/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
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
 * Este controlador lleva el manejo de las vistas <code>mostrarPensumEstudianteCarrera.htm</code>
 * y <code>agregarPensumEstudianteCarrera.htm</code>, esta &uacute;ltima no tiene una vista fisica
 * solamente es una llamada l&oacute;gica y redirecciona a la primera vista.
 *
 * @author Mario Batres
 * @version 1.0
 */
@Controller
public class ControladorMostrarPensumEstudianteCarrera extends ControladorAbstractoPensumEstudianteCarrera{
//______________________________________________________________________________
    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorMostrarPensumEstudianteCarrera.class);

//______________________________________________________________________________
    /**
     * Crea todo los atributos necesarios para mostrar en la vista <code>mostrarPensumEstudianteCarrera.htm</code>
     * cuando se hace una llamada de tipo <b>GET</b>.
     * Valida que el id de la asignacion del estudiante con la carrera sea valido y exista en la base de datos.
     *
     * @param modelo Objeto de tipo {@link Model}
     * @param idAsignacionEstudianteCarrera Objeto de tipo {@link Integer}.  Recibe el id del
     * objeto de tipo {AsignacionEstudiantecarrera}.
     *
     * @return String con el nombre de la vista a mostrar
     *
     */
    @RequestMapping(value="mostrarPensumEstudianteCarrera.htm", method=RequestMethod.GET)
    public String mostrarPensumEstudianteCarrera(Model modelo
            , Integer idAsignacionEstudianteCarrera){

        if(idAsignacionEstudianteCarrera == null){
            return "redirect:buscarEstudiante.htm";
        }

        //Se obtiene la asignacion del estudiante con la carrera
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
        //Se obtiene las relaciones con el pensum valido
        if(pensumEstudianteCarreraValido != null){
            this.pensum = pensumEstudianteCarreraValido.getPensum();
            modelo.addAttribute("idPensumEstudianteCarreraValido", pensumEstudianteCarreraValido.getIdPensumEstudianteCarrera());
        }

        this.agregarAtributosDefault(modelo, new PensumEstudianteCarrera(), false, true);

        return "pensumEstudianteCarrera/mostrarPensumEstudianteCarrera";
    }

//______________________________________________________________________________
    /**
     * Este m&eacute;todo se activa cuando se hace una llama de tipo <b>POST</b> a la
     * vista <code>asignarPensumEstudianteCarrera.htm</code>.  La cual tiene la
     * funci&eocute;n de agregar un nuevo objeto {@link PensumEstudianteCarrera} a la
     * base de datos.
     *
     * @param modelo Objeto de tipo {@link Model}
     * @param pensumEstudianteCarrera Objeto de tipo {@link PensumEstudianteCarrera}
     * @param bindingResult Objeto de tipo {@link BindingResult}
     * @param request Objeto de tipo {@link HttpServletRequest}
     *
     * @return String con el nombre de la vista a mostrar
     */

    @RequestMapping(value="asignarPensumEstudianteCarrera.htm", method=RequestMethod.POST)
    public String asignar(Model modelo, @Valid PensumEstudianteCarrera pensumEstudianteCarrera
            , BindingResult bindingResult, HttpServletRequest request){

        if(!bindingResult.hasErrors()){
            try{
                
                pensumEstudianteCarrera.setAsignacionEstudianteCarrera(asignacionEstudianteCarrera);
                pensumEstudianteCarrera.setValido(true);                
                this.servicioPensumEstudianteCarreraImpl.agregar(pensumEstudianteCarrera);
                
                RequestUtil.agregarRedirect(request, "mostrarPensumEstudianteCarrera.htm?idAsignacionEstudianteCarrera=" 
                        + this.asignacionEstudianteCarrera.getIdAsignacionEstudianteCarrera());
                RequestUtil.crearMensajeRespuesta(request, "asignarPensumEstudianteCarrera.titulo", "asignarPensumEstudianteCarrera.exito", true);
                log.info(Mensajes.EXITO_AGREGAR + pensumEstudianteCarrera.toString());

            }catch(DataIntegrityViolationException e){
                RequestUtil.crearMensajeRespuesta(request, "asignarPensumEstudianteCarrera.titulo", "dataIntegrityViolatioException", false);
                log.error(Mensajes.DATA_INTEGRITY_VIOLATION_EXCEPTION, e);
                this.agregarAtributosDefault(modelo, pensumEstudianteCarrera, false, false);
            }catch(DataAccessException e){
                RequestUtil.crearMensajeRespuesta(request, "asignarPensumEstudianteCarrera.titulo", "dataAccessException", false);
                log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
                this.agregarAtributosDefault(modelo, pensumEstudianteCarrera, false, false);
            }
        }else{            
            this.agregarAtributosDefault(modelo, pensumEstudianteCarrera, false, false);
        }
        
        return "pensumEstudianteCarrera/mostrarPensumEstudianteCarrera";
    }

}
