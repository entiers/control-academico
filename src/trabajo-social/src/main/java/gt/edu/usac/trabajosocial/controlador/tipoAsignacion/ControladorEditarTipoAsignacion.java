/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.trabajosocial.controlador.tipoAsignacion;

import gt.edu.usac.trabajosocial.dominio.TipoAsignacion;
import gt.edu.usac.trabajosocial.dominio.wrapper.WrapperTipoAsignacion;
import gt.edu.usac.trabajosocial.servicio.ServicioTipoAsignacion;
import gt.edu.usac.trabajosocial.util.MensajePopup;
import gt.edu.usac.trabajosocial.util.Mensajes;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Esta clase se encarga de modficar un tipo de asignación existente en la BD.
 * La información se pide en la página de <code>editarTipoAsignacion.htm</code>.
 *
 * @author Mario Batres
 * @version 1.0
 */
@Controller("controladorEditarTipoAsignacion")
@RequestMapping(value="editarTipoAsignacion.htm")
public class ControladorEditarTipoAsignacion {
    //_____________________________________________________________________________
    /**
     * <p>
     * Lleva el nombre del titulo para el mensaje en la página
     * <p>
     */
    private static final String TITULO_MENSAJE = "editarTipoAsignacion.titulo";

//______________________________________________________________________________
    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorEditarTipoAsignacion.class);
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
     * <p>Se utiliza para mantener todos los datos del tipo de asignacion que se
     * encontro en la busqueda.</p>
     */
    private TipoAsignacion tipoAsignacion;

//______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina <code>agregarTipoAsignacion.htm</code>. El metodo se encarga
     * de iniciar los objetos que se usaran en la pagina.</p>
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(method = RequestMethod.GET)
    public String crearFormulario(Model modelo, short idTipoAsignacion, HttpServletRequest request) {

        this.tipoAsignacion = this.servicioTipoAsignacionImpl.getTipoAsignacionPorID(idTipoAsignacion);

        if(this.tipoAsignacion == null){
            MensajePopup.crearMensajeRespuesta(request, TITULO_MENSAJE, "buscarTipoAsignacion.sinResultados", false);
            return "tipoAsignacion/buscarTipoAsignacion";
        }

        WrapperTipoAsignacion wrapperTipoAsignacion = new WrapperTipoAsignacion();
        wrapperTipoAsignacion.agregarWrapper(tipoAsignacion);
        // se agregan los objetos que se usaran en la pagina
        modelo.addAttribute("wrapperTipoAsignacion", wrapperTipoAsignacion);

        return "tipoAsignacion/editarTipoAsignacion";
    }

    /**
     * <p>Este metodo se ejecuta cuando se presiona el boton de editar de la
     * pagina. El metodo se encarga de actualizar la informacion del tipo de asignacion
     * que se obtuvo en la busqueda. El metodo realiza las siguiente acciones:
     * <ul>
     * <li>Realiza las validaciones de los datos del formulario</li>
     * <li>Delega la funcion de actualizacion a {@link ServicioCalendarioActividades}</li>
     * <li>Muestra un mensaje popup con el resultado de la operacion</li>
     * </ul></p>
     *
     * @param wrapperCalendarioActividades Pojo del tipo {@link WrapperCalendarioActividades}
     * @param bindingResult Ojeto {@link BindingResult} que realiza las validaciones
     * @param modelo Objeto {@link Model} que contiene los objeto de la pagina
     * @param request Objeto {@link HttpServletRequest}
     * @return String
     */

    @RequestMapping(method = RequestMethod.POST)
    public String editar(@Valid WrapperTipoAsignacion wrapperTipoAsignacion, BindingResult bindingResult,
            Model modelo, HttpServletRequest request){

        if(bindingResult.hasErrors()){
            return "tipoAsignacion/editarTipoAsignacion";
        }

        try {
            // se quita el envoltorio y se trata de actualizar al tipo de asignacion
            wrapperTipoAsignacion.quitarWrapper(this.tipoAsignacion);
            this.servicioTipoAsignacionImpl.actualizarTipoAsignacion(this.tipoAsignacion);

            // se registra el evento
            MensajePopup.crearMensajeRespuesta(request, TITULO_MENSAJE, "editarTipoAsignacion.exito", true);
            String msg = Mensajes.EXITO_ACTUALIZACION + "Tipo de Asignacion, ID = " + this.tipoAsignacion.getIdTipoAsignacion();
            log.info(msg);

            modelo.addAttribute("wrapperHorario", wrapperTipoAsignacion);

        } catch (DataAccessException e) {
            // error de acceso a datos
            MensajePopup.crearMensajeRespuesta(request, null, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        return "tipoAsignacion/editarTipoAsignacion";
    }
}
