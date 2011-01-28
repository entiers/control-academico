/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.controlador.semestre;

import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.dominio.wrapper.WrapperSemestre;
import gt.edu.usac.cats.servicio.ServicioSemestre;
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
 * Esta clase se encarga de almacenar los semestres en la BD.
 * La informacion se pide en la pagina de <code>agregarSemestre.htm</code>.
 *
 * @author Mario Batres
 * @version 1.0
 */

@Controller("controladorAgregarSemestre")
@RequestMapping(value = "agregarSemestre.htm")
public class ControladorAgregarSemestre {

    /**
     * <p>Lleva el nombre del titulo para el mensaje en la pagina.<p>
     */
    private static final String TITULO_MENSAJE = "agregarSemestre.titulo";
//______________________________________________________________________________
    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorAgregarSemestre.class);
//______________________________________________________________________________
    /**
     * <p>Contiene metodos que permiten el manejo de la informacion relacionada
     * con el semestre en la base de datos. Este objeto se encuentra registrado
     * como un bean de servicio en Spring, por lo que este es el encargado de
     * inyectar la dependencia.</p>
     */
    @Resource
    protected ServicioSemestre servicioSemestreImpl;
    
//______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina <code>agregarSemestre.htm</code>. El metodo se encarga
     * de iniciar los objetos que se usaran en la pagina.</p>
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(method = RequestMethod.GET)
    public String crearFormulario(Model modelo) {

        // se agregan los objetos que se usaran en la pagina
        modelo.addAttribute("wrapperSemestre", new WrapperSemestre());

        return "semestre/agregarSemestre";
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo es llamado cuando se realiza un SUBMIT desde la pagina de
     * agregar semestre <code>agregarSemestre.htm</code>. El metodo se encarga
     * de agregar la informacion ingresada en el formulario de la pagina en la
     * base de datos, el procedimiento que sigue el metodo es el siguiente:
     * <ul>
     * <li>Se realiza la validacion de datos ingresados, si algun dato no cumple
     * con las reglas de validacion se retorna a la pagina para que se muestren
     * los mensajes de error</li>
     * <li>Si la validacion tuvo exito se trata de agregar la informacion a la
     * base de datos por medio de {@link ServicioSemestre}</li>
     * <li>Se procesa el resultado de la operacion y se le indica a la pagina el
     * mensaje de respuesta que debe de mostrar, el mensaje puede ser de exito o
     * de error</li>
     * </ul></p>
     *
     * @param wrapperSemestre Pojo del tipo {@link WrapperSemestre}
     * @param bindingResult Objeto {@link BindingResult}, contiene el resultado de
     *        las validaciones
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @param request Objeto {@link HttpServletRequest}
     * @return String Con la url de la pagina a mostrar
     */
    @RequestMapping(method = RequestMethod.POST)
    public String submit(@Valid WrapperSemestre wrapperSemestre, BindingResult bindingResult,
            Model modelo, HttpServletRequest request) {
        // se validan los campos ingresados en el formulario, si existen errores
        // se regresa al formulario para que se muestren los mensajes correspondientes
        if(bindingResult.hasErrors())
            return "semestre/agregarSemestre";

        try {

            // se quita el envoltorio y se trata de agregar al semestre
            Semestre semestre = new Semestre();
            wrapperSemestre.quitarWrapper(semestre);
            this.servicioSemestreImpl.agregar(semestre);

            // se registra el evento
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "agregarSemestre.exito", true);
            String msg = Mensajes.EXITO_AGREGAR + "Semestre, anio " + semestre.getAnio() + " y numero " + semestre.getNumero();
            log.info(msg);

        } catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, null, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        return "semestre/agregarSemestre";
    }
}
