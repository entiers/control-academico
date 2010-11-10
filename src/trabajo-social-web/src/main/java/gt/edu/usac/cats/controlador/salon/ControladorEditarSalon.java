/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.controlador.salon;

import gt.edu.usac.cats.dominio.Salon;
import gt.edu.usac.cats.dominio.busqueda.DatosBusquedaSalon;
import gt.edu.usac.cats.dominio.wrapper.WrapperSalon;
import gt.edu.usac.cats.servicio.ServicioHorario;
import gt.edu.usac.cats.util.RequestUtil;
import gt.edu.usac.cats.util.Mensajes;
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
 * Esta clase se encarga de buscar y modficar un salon existente en la BD.
 * La informacion se pide en la pagina de <code>buscarSalon.htm</code>.
 * Y se modifica en la pagina de <code>editarSalon.htm</code>.
 *
 * @author Mario Batres
 * @version 1.0
 */
@Controller("controladorEditarSalon")
public class ControladorEditarSalon {
    
    /**
     * <p>Lleva el nombre del titulo para el mensaje en la pagina.<p>
     */
    private static String TITULO_MENSAJE = "editarSalon.titulo";
//______________________________________________________________________________
    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorEditarSalon.class);
//______________________________________________________________________________
    /**
     * <p>Contiene metodos que permiten el manejo de la informacion relacionada
     * con el salon en la base de datos. Este objeto se encuentra registrado
     * como un bean de servicio en Spring, por lo que este es el encargado de
     * inyectar la dependencia.</p>
     */
    @Resource
    protected ServicioHorario servicioHorarioImpl;
//______________________________________________________________________________
    /**
     * <p>Se utiliza para mantener todos los datos del salon que se
     * encontro en la busqueda.</p>
     */
    private Salon salon;
//______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina <code>editarSalon.htm</code>. El metodo se encarga
     * de iniciar los objetos que se usaran en la pagina.</p>
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(value = "editarSalon.htm", method = RequestMethod.GET)
    public String crearFormulario(Model modelo) {

        // se agregan los objetos que se usaran en la pagina
        modelo.addAttribute("wrapperSalon", new WrapperSalon());
        modelo.addAttribute("datosBusquedaSalon", new DatosBusquedaSalon());

        return "salon/editarSalon";
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cuando se solicita una busqueda desde la pagina
     * de editar salon. Las busquedas solo se realizan por el codigo. El metodo
     * realiza los siguientes pasos:
     * <ul>
     * <li>Valida que el codigo ingresado sea valido</li>
     * <li>Si el codigo es valido se realiza la busqueda y se muestra la
     * informacion del salon en la pagina, si la busqueda no genera
     * resultados se muestra un mensaje popup</li>
     * <li>Si ocurre un error de acceso a la base de datos se muestra un mensaje
     * popup indicando del error</li>
     * </ul>
     * </p>
     *
     * @param datosBusquedaSalon Contiene los parametros de la busqueda, en
     *        este caso solo el numero de carne
     * @param resultado Objeto {@link BindingResult} que valida los datos
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @param request Peticion HTTP
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(value = "buscarEditarSalon.htm", method = RequestMethod.POST)
    public String buscarSalon(@Valid DatosBusquedaSalon datosBusquedaSalon,
            BindingResult bindingResult, Model modelo, HttpServletRequest request) {

        // se crea el envoltorio para el salon
        WrapperSalon wrapperSalon = new WrapperSalon();
        modelo.addAttribute("wrapperSalon", wrapperSalon);

        // se obtiene el carne ingresado para realizar la busqueda
        short numero = datosBusquedaSalon.getNumero();
        String edificio = datosBusquedaSalon.getEdificio();

        if(bindingResult.hasErrors())
            return "salon/editarSalon";

        try {
            // se realiza la busqueda del salon
            this.salon = this.servicioHorarioImpl.buscarSalonPorNumeroYEdificio(numero, edificio);

            if(this.salon == null)
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "editarSalon.sinResultados", true);
            else
                wrapperSalon.agregarWrapper(this.salon);

        } catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, null, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        return "salon/editarSalon";
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cuando se presiona el boton de editar de la
     * pagina. El metodo se encarga de actualizar la informacion del salon
     * que se obtuvo en la busqueda. El metodo realiza las siguiente acciones:
     * <ul>
     * <li>Realiza las validaciones de los datos del formulario</li>
     * <li>Delega la funcion de actualizacion a {@link ServicioSalon}</li>
     * <li>Muestra un mensaje popup con el resultado de la operacion</li>
     * </ul></p>
     *
     * @param salon Pojo del tipo {@link Salon}
     * @param bindingResult Ojeto {@link BindingResult} que realiza las validaciones
     * @param modelo Objeto {@link Model} que contiene los objeto de la pagina
     * @param request Objeto {@link HttpServletRequest}
     * @return String
     */
    @RequestMapping(value = "editarSalon.htm", method = RequestMethod.POST)
    public String submit(@Valid WrapperSalon wrapperSalon, BindingResult bindingResult,
            Model modelo, HttpServletRequest request) {

        modelo.addAttribute("datosBusquedaSalon", new DatosBusquedaSalon());

        // se validan los campos ingresados en el formulario, si existen errores
        // se regresa al formulario para que se muestren los mensajes correspondientes

        if(bindingResult.hasErrors())
            return "salon/editarSalon";

        try {
            // se quita el envoltorio y se trata de actualizar al salon
            wrapperSalon.quitarWrapper(this.salon);
            this.servicioHorarioImpl.actualizar(this.salon);

            // se registra el evento
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "editarSalon.exito", true);
            String msg = Mensajes.EXITO_ACTUALIZACION + "Salon, numero " + this.salon.getNumero();
            log.info(msg);

        } catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, null, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        return "salon/editarSalon";
    }
}
