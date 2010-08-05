/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.trabajosocial.controlador.tipoAsignacion;

import gt.edu.usac.trabajosocial.dominio.TipoAsignacion;
import gt.edu.usac.trabajosocial.servicio.ServicioTipoAsignacion;
import gt.edu.usac.trabajosocial.util.MensajePopup;
import gt.edu.usac.trabajosocial.util.Mensajes;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */

@Controller("controladorEliminarTipoAsignacion")
@RequestMapping(value="eliminarTipoAsignacion.htm")
public class ControladorEliminarTipoAsignacion {
//_____________________________________________________________________________
    /**
     * <p>
     * Lleva el nombre del titulo para el mensaje en la pagina
     * <p>
     */
    private static final String TITULO_MENSAJE = "eliminarTipoAsignacion.titulo";

//______________________________________________________________________________
    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorEliminarTipoAsignacion.class);
    
//______________________________________________________________________________
    /**
     * <p>Contiene metodos que permiten el manejo de la informacion relacionada
     * con el semestre en la base de datos. Este objeto se encuentra registrado
     * como un bean de servicio en Spring, por lo que este es el encargado de
     * inyectar la dependencia.</p>
     */
    @Resource
    protected ServicioTipoAsignacion servicioTipoAsignacionImpl;
//______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina <code>buscarTipoAsignacion.htm</code> en el enlace para
     * eliminar . El metodo se encarga de eliminar o desactivar el objeto de tipo
     * {@link TipoAsignacion}.</p>
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @param idTipoAsignacion Identificador del pojo {@link TipoAsignacion} que
     *        se va a eliminar o desactivar.
     * @param request Objeto {@link HttpServletRequest}
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(method=RequestMethod.GET)
    public String eliminar(Model modelo, short idTipoAsignacion, HttpServletRequest request){
                
        try{
            TipoAsignacion tipoAsignacion = this.servicioTipoAsignacionImpl.getTipoAsignacionPorID(idTipoAsignacion);
         
            try{
                this.servicioTipoAsignacionImpl.elimiarTipoAsignacion(tipoAsignacion);            
            }catch(DataIntegrityViolationException e){
                log.info("Tipo de Asignacion, ID = " + tipoAsignacion.getIdTipoAsignacion() + " tienen asignaciones asociadas");
                log.error(Mensajes.DATA_INTEGRITY_VIOLATION_EXCEPTION, e);

                //Se desactiva el tipo de asignacion
                tipoAsignacion.setHabilitado(false);
                this.servicioTipoAsignacionImpl.actualizarTipoAsignacion(tipoAsignacion);
            }

            List <TipoAsignacion> listadoTipoAsignacion = this.servicioTipoAsignacionImpl.buscarTipoAsignacion();
            modelo.addAttribute("listadoTipoAsignacion", listadoTipoAsignacion);

            // se registra el evento
            MensajePopup.crearMensajeRespuesta(request, TITULO_MENSAJE, "eliminarTipoAsignacion.exito", true);
            String msg = Mensajes.EXITO_BORRAR + "Tipo de Asignacion, ID = " + tipoAsignacion.getIdTipoAsignacion();
            log.info(msg);
        }catch(DataAccessException e){
            // error de acceso a datos
            MensajePopup.crearMensajeRespuesta(request, null, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }
        return "tipoAsignacion/buscarTipoAsignacion";
    }
}
