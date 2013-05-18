/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.controlador.pensum;

import gt.edu.usac.cats.dominio.Pensum;
import gt.edu.usac.cats.dominio.wrapper.WrapperPensum;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
@Controller
@RequestMapping(value="editarPensum.htm")
@Scope(value = WebApplicationContext.SCOPE_SESSION)
@SessionAttributes(value = {"pensum"})
public class ControladorEditarPensum extends ControladorAbstractoPensum implements Serializable{
//______________________________________________________________________________
    /**
     * <p>Se utiliza para mantener todos los datos del pensum que se
     * encontro en la busqueda.</p>
     */
    private Pensum pensum;

    //_____________________________________________________________________________

    /**
     * <p>Lleva el nombre del titulo para el mensaje en la pagina.</p>
     */
    private static final String TITULO_MENSAJE = "editarPensum.titulo";
//______________________________________________________________________________
    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorEditarPensum.class);

//______________________________________________________________________________

    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina <code>editarPensum.htm</code>. El metodo se encarga
     * de iniciar los objetos que se usaran en la pagina.</p>
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @param idPensum id del pensum a editar
     * @param request {@link HttpServletRequest}
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(method = RequestMethod.GET)
    public String crearFormularioEditar(Model modelo, Short idPensum, HttpServletRequest request) {

        if (!this.validarPensum(idPensum, this.pensum)) {
            return "redirect:buscarPensum.htm";
        }        

        WrapperPensum wrapperPensum = new WrapperPensum();

        //el pensum ha sido agregado con la funcion validar
        wrapperPensum.agregarWrapper(this.pensum);
        
        this.agregarAtributosDefault(modelo, wrapperPensum);
        return "pensum/editarPensum";
    }

    /**
     * <p>Este metodo se ejecuta cuando se presiona el boton de editar de la
     * pagina. El metodo se encarga de actualizar la informacion del pensum
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
    public String editar(Model modelo, @Valid WrapperPensum wrapperPensum, BindingResult bindingResult,
            HttpServletRequest request) {
// se validan los campos ingresados en el formulario, si existen errores
        // se regresa al formulario para que se muestren los mensajes correspondientes
        if (!bindingResult.hasErrors()) {
            try {
                wrapperPensum.quitarWrapper(this.pensum);

                this.servicioPensumImpl.actualizar(pensum);

                RequestUtil.agregarRedirect(request, "buscarPensum.htm");
                // se registra el evento
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "editarPensum.exito", true);
                log.info(Mensajes.EXITO_ACTUALIZACION + pensum.toString());

            } catch (DataIntegrityViolationException e) {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataIntegrityException", false);
                log.error(Mensajes.DATA_INTEGRITY_VIOLATION_EXCEPTION, e);
            } catch (DataAccessException e) {
                // error de acceso a datos
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
                log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);            
            }
        }

        this.agregarAtributosDefault(modelo, wrapperPensum);
        return "pensum/editarPensum";
    }

}
