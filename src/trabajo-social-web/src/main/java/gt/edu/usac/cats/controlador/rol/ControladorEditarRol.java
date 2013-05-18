/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.controlador.rol;

import gt.edu.usac.cats.dominio.Rol;
import gt.edu.usac.cats.dominio.wrapper.WrapperRol;
import gt.edu.usac.cats.servicio.ServicioGeneral;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import java.io.Serializable;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;

/**
 * Esta clase se encarga de almacenar los roles en la BD.
 * La informacion se pide en la pagina de <code>editarRol.htm</code>.
 *
 * @author Mario Batres
 * @version 1.0
 */

@Controller
@RequestMapping(value="editarRol.htm")
@Scope(value = WebApplicationContext.SCOPE_SESSION)
@SessionAttributes(value = {"rol"})
public class ControladorEditarRol implements Serializable{
    //_____________________________________________________________________________
    /**
     * <p>
     * Lleva el nombre del titulo para el mensaje en la pagina
     * <p>
     */
    private static final String TITULO_MENSAJE = "editarRol.titulo";

//______________________________________________________________________________
    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorEditarRol.class);
//______________________________________________________________________________
    /**
     * <p>Contiene metodos que permiten el manejo de la informacion relacionada
     * con el rol en la base de datos. Este objeto se encuentra registrado
     * como un bean de servicio en Spring, por lo que este es el encargado de
     * inyectar la dependencia.</p>
     */
    @Resource
    protected ServicioGeneral servicioGeneralImpl;
//______________________________________________________________________________

    /**
     * <p>Se utiliza para mantener todos los datos del rol que se
     * encontro en la busqueda.</p>
     */
    private Rol rol;

//______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina <code>agregarRol.htm</code>. El metodo se encarga
     * de iniciar los objetos que se usaran en la pagina.</p>
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @param idRol Identificador del pojo {@link Rol} que
     *        se va a mostrar para poder ser modificado.
     * @param request Objeto {@link HttpServletRequest}
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(method = RequestMethod.GET)
    public String crearFormulario(Model modelo, short idRol, HttpServletRequest request) {

        this.rol = this.servicioGeneralImpl.cargarEntidadPorID(Rol.class, idRol);

        if(this.rol == null){
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "buscarRol.sinResultados", false);
            return "rol/buscarRol";
        }

        WrapperRol wrapperRol = new WrapperRol();
        wrapperRol.agregarWrapper(rol);
        // se agregan los objetos que se usaran en la pagina
        modelo.addAttribute("wrapperRol", wrapperRol);

        return "rol/editarRol";
    }

    /**
     * <p>Este metodo se ejecuta cuando se presiona el boton de editar de la
     * pagina. El metodo se encarga de actualizar la informacion del rol
     * que se obtuvo en la busqueda. El metodo realiza las siguiente acciones:
     * <ul>
     * <li>Realiza las validaciones de los datos del formulario</li>
     * <li>Delega la funcion de actualizacion a {@link ServicioCalendarioActividades}</li>
     * <li>Muestra un mensaje popup con el resultado de la operacion</li>
     * </ul></p>
     *
     * @param wrapperRol Pojo del tipo {@link WrapperRol}
     * @param bindingResult Ojeto {@link BindingResult} que realiza las validaciones
     * @param modelo Objeto {@link Model} que contiene los objeto de la pagina
     * @param request Objeto {@link HttpServletRequest}
     * @return String
     */

    @RequestMapping(method = RequestMethod.POST)
    public String editar(@Valid WrapperRol wrapperRol, BindingResult bindingResult,
            Model modelo, HttpServletRequest request){

        if(bindingResult.hasErrors()){
            return "rol/editarRol";
        }

        try {
            // se quita el envoltorio y se trata de actualizar al rol
            wrapperRol.quitarWrapper(this.rol);
            this.servicioGeneralImpl.actualizar(this.rol);

            // se registra el evento
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "editarRol.exito", true);
            String msg = Mensajes.EXITO_ACTUALIZACION + "Rol, ID = " + this.rol.getIdRol();
            log.info(msg);

            modelo.addAttribute("wrapperHorario", wrapperRol);

        } catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, null, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        return "rol/editarRol";
    }

}
