/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.controlador.perfil;

import gt.edu.usac.cats.dominio.Perfil;
import gt.edu.usac.cats.dominio.wrapper.WrapperPerfil;
import gt.edu.usac.cats.servicio.ServicioGeneral;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
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
 * Esta clase se encarga de almacenar los perfiles en la BD.
 * La informacion se pide en la pagina de <code>editarPerfil.htm</code>.
 *
 * @author Mario Batres
 * @version 1.0
 */

@Controller
@RequestMapping(value="editarPerfil.htm")
public class ControladorEditarPerfil {
//_____________________________________________________________________________
    /**
     * <p>
     * Lleva el nombre del titulo para el mensaje en la pagina
     * <p>
     */
    private static final String TITULO_MENSAJE = "editarPerfil.titulo";

//______________________________________________________________________________
    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorEditarPerfil.class);
//______________________________________________________________________________
    /**
     * <p>Contiene metodos que permiten el manejo de la informacion relacionada
     * con el perfil en la base de datos. Este objeto se encuentra registrado
     * como un bean de servicio en Spring, por lo que este es el encargado de
     * inyectar la dependencia.</p>
     */
    @Resource
    protected ServicioGeneral servicioGeneralImpl;
//______________________________________________________________________________

    /**
     * <p>Se utiliza para mantener todos los datos del perfil que se
     * encontro en la busqueda.</p>
     */
    private Perfil perfil;

//______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina <code>agregarPerfil.htm</code>. El metodo se encarga
     * de iniciar los objetos que se usaran en la pagina.</p>
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @param idPerfil Identificador del pojo {@link Perfil} que
     *        se va a mostrar para poder ser modificado.
     * @param request Objeto {@link HttpServletRequest}
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(method = RequestMethod.GET)
    public String crearFormulario(Model modelo, short idPerfil, HttpServletRequest request) {

        this.perfil = this.servicioGeneralImpl.cargarEntidadPorID(Perfil.class, idPerfil);

        if(this.perfil == null){
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "buscarPerfil.sinResultados", false);
            return "perfil/buscarPerfil";
        }

        WrapperPerfil wrapperPerfil = new WrapperPerfil();
        wrapperPerfil.agregarWrapper(perfil);
        // se agregan los objetos que se usaran en la pagina
        modelo.addAttribute("wrapperPerfil", wrapperPerfil);

        return "perfil/editarPerfil";
    }

    /**
     * <p>Este metodo se ejecuta cuando se presiona el boton de editar de la
     * pagina. El metodo se encarga de actualizar la informacion del perfil
     * que se obtuvo en la busqueda. El metodo realiza las siguiente acciones:
     * <ul>
     * <li>Realiza las validaciones de los datos del formulario</li>
     * <li>Delega la funcion de actualizacion a {@link ServicioCalendarioActividades}</li>
     * <li>Muestra un mensaje popup con el resultado de la operacion</li>
     * </ul></p>
     *
     * @param wrapperPerfil Pojo del tipo {@link WrapperPerfil}
     * @param bindingResult Ojeto {@link BindingResult} que realiza las validaciones
     * @param modelo Objeto {@link Model} que contiene los objeto de la pagina
     * @param request Objeto {@link HttpServletRequest}
     * @return String
     */

    @RequestMapping(method = RequestMethod.POST)
    public String editar(@Valid WrapperPerfil wrapperPerfil, BindingResult bindingResult,
            Model modelo, HttpServletRequest request){

        if(bindingResult.hasErrors()){
            return "perfil/editarPerfil";
        }

        try {
            // se quita el envoltorio y se trata de actualizar al perfil
            wrapperPerfil.quitarWrapper(this.perfil);
            this.servicioGeneralImpl.actualizar(this.perfil);

            // se registra el evento
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "editarPerfil.exito", true);
            String msg = Mensajes.EXITO_ACTUALIZACION + "Perfil, ID = " + this.perfil.getIdPerfil();
            log.info(msg);

            modelo.addAttribute("wrapperHorario", wrapperPerfil);

        } catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, null, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        return "perfil/editarPerfil";
    }

}
