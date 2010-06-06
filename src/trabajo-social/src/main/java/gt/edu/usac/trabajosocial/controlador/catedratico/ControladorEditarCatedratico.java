/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.controlador.catedratico;

import gt.edu.usac.trabajosocial.dominio.Catedratico;
import gt.edu.usac.trabajosocial.dominio.busqueda.DatosBusquedaCatedratico;
import gt.edu.usac.trabajosocial.dominio.wrapper.WrapperCatedratico;
import gt.edu.usac.trabajosocial.servicio.ServicioCatedratico;
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
 *
 * @author Daniel Castillo
 * @version 1.0
 */
@Controller("controladorEditarCatedratico")
public class ControladorEditarCatedratico {

    /**
     * <p>Lleva el nombre del titulo para el mensaje en la página.</p>
     */
    private static String TITULO_MENSAJE = "editarCatedratico.titulo";
//______________________________________________________________________________
    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorEditarCatedratico.class);
//______________________________________________________________________________
    /**
     * <p>Contiene metodos que permiten el manejo de la informacion relacionada
     * con el catedratico en la base de datos. Este objeto se encuentra registrado
     * como un bean de servicio en Spring, por lo que este es el encargado de
     * inyectar la dependencia.</p>
     */
    @Resource
    protected ServicioCatedratico servicioCatedraticoImpl;
//______________________________________________________________________________
    /**
     * <p>Se utiliza para mantener todos los datos del catedratico que se
     * encontro en la busqueda.</p>
     */
    private Catedratico catedratico;
//______________________________________________________________________________
    /**
     * <p>Constructor de la clase, no realiza ninguna accion.</p>
     */
    public ControladorEditarCatedratico() {}
//______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina <code>editarCatedratico.htm</code>. El metodo se encarga
     * de iniciar los objetos que se usaran en la pagina.</p>
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(value = "editarCatedratico.htm", method = RequestMethod.GET)
    public String crearFormulario(Model modelo) {

        // se agregan los objetos que se usaran en la pagina
        modelo.addAttribute("wrapperCatedratico", new WrapperCatedratico());
        modelo.addAttribute("datosBusquedaCatedratico", new DatosBusquedaCatedratico());

        return "catedratico/editarCatedratico";
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cuando se solicita una busqueda desde la pagina
     * de editar catedratico. Las busquedas solo se realizan por el codigo de
     * personal del catedratico. El metodo realiza los siguientes pasos:
     * <ul>
     * <li>Valida que el codigo de personal ingresado sea valido</li>
     * <li>Si el codigo es valido se realiza la busqueda y se muestra la
     * informacion del catedratico en la pagina, si la busqueda no genera
     * resultados se muestra un mensaje popup</li>
     * <li>Si ocurre un error de acceso a la base de datos se muestra un mensaje
     * popup indicando del error</li>
     * </ul>
     * </p>
     *
     * @param datosBusquedaCatedratico Contiene los parametros de la busqueda, en
     *        este caso solo el codigo de personal
     * @param bindingResult Objeto {@link BindingResult} que valida los datos
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @param request Peticion HTTP
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(value = "buscarEditarCatedratico.htm", method = RequestMethod.POST)
    public String buscarCatedratico(@Valid DatosBusquedaCatedratico datosBusquedaCatedratico,
            BindingResult bindingResult, Model modelo, HttpServletRequest request) {

        // se crea el envoltorio para el catedratico
        WrapperCatedratico wrapperCatedratico = new WrapperCatedratico();
        modelo.addAttribute("wrapperCatedratico", wrapperCatedratico);

        // se obtiene el carne ingresado para realizar la busqueda
        String codigo = datosBusquedaCatedratico.getCodigoBusqueda();

        if(codigo.isEmpty() || bindingResult.hasErrors())
            return "catedratico/editarCatedratico";

        try {
            // se realiza la busqueda del catedratico
            this.catedratico = this.servicioCatedraticoImpl.buscarCatedraticoPorCodigo(codigo);

            if(this.catedratico == null)
                MensajePopup.crearMensajeRespuesta(request, TITULO_MENSAJE, "editarCatedratico.sinResultados", true);
            else
                wrapperCatedratico.agregarWrapper(this.catedratico);

        } catch (DataAccessException e) {
            // error de acceso a datos
            MensajePopup.crearMensajeRespuesta(request, null, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        return "catedratico/editarCatedratico";
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cuando se presiona el boton de editar de la
     * pagina. El metodo se encarga de actualizar la informacion del catedratico
     * que se obtuvo en la busqueda. El metodo realiza las siguiente acciones:
     * <ul>
     * <li>Realiza las validaciones de los datos del formulario</li>
     * <li>Delega la funcion de actualizacion a {@link ServicioCatedratico}</li>
     * <li>Muestra un mensaje popup con el resultado de la operacion</li>
     * </ul></p>
     *
     * @param catedratico Pojo del tipo {@link Catedratico}
     * @param bindingResult Ojeto {@link BindingResult} que realiza las validaciones
     * @param modelo Objeto {@link Model} que contiene los objeto de la pagina
     * @param request Objeto {@link HttpServletRequest}
     * @return String
     */
    @RequestMapping(value = "editarCatedratico.htm", method = RequestMethod.POST)
    public String submit(@Valid WrapperCatedratico wrapperCatedratico, BindingResult bindingResult,
            Model modelo, HttpServletRequest request) {

        modelo.addAttribute("datosBusquedaCatedratico", new DatosBusquedaCatedratico());

        // se validan los campos ingresados en el formulario, si existen errores
        // se regresa al formulario para que se muestren los mensajes correspondientes
        // en este caso se pregunta si la cantidad de errores es mayor a 2
        // debido a que no se esta realizando actualizacion de la escuela ni
        // asignacion de codigo de personal, por lo tanto no se utiliza el campo
        // codigo ni el campo idEscuela y como estos tiene asociado un validador
        // entonces siempre daran error
        if(bindingResult.hasErrors() && bindingResult.getErrorCount() > 2)
            return "catedratico/editarCatedratico";

        try {
            // se quita el envoltorio y se trata de actualizar al catedratico
            wrapperCatedratico.quitarWrapper(this.catedratico);
            this.servicioCatedraticoImpl.actualizarCatedratico(this.catedratico);

            // se registra el evento
            MensajePopup.crearMensajeRespuesta(request, TITULO_MENSAJE, "editarCatedratico.exito", true);
            String msg = Mensajes.EXITO_ACTUALIZACION + "Catedratico, codigo " + this.catedratico.getCodigo();
            log.info(msg);

        } catch (DataAccessException e) {
            // error de acceso a datos
            MensajePopup.crearMensajeRespuesta(request, null, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        return "catedratico/editarCatedratico";
    }
}