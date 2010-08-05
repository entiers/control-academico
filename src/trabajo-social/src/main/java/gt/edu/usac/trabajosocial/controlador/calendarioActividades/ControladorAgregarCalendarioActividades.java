/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.controlador.calendarioActividades;

import gt.edu.usac.trabajosocial.dominio.CalendarioActividades;
import gt.edu.usac.trabajosocial.dominio.Semestre;
import gt.edu.usac.trabajosocial.dominio.wrapper.WrapperCalendarioActividades;
import gt.edu.usac.trabajosocial.servicio.ServicioCalendarioActividades;
import gt.edu.usac.trabajosocial.servicio.ServicioSemestre;
import gt.edu.usac.trabajosocial.util.MensajePopup;
import gt.edu.usac.trabajosocial.util.Mensajes;
import java.util.List;
import javax.annotation.Resource;
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
 * Esta clase se encarga de almacenar los calendarios de actividades en la BD.
 * La informacion se pide en la pagina de <code>agregarCalendarioActividades.htm</code>.
 *
 * @author Mario Batres
 * @version 1.0
 */

@Controller("controladorAgregarCalendarioActividades")
@RequestMapping("agregarCalendarioActividades.htm")
public class ControladorAgregarCalendarioActividades {
//______________________________________________________________________________
    /**
     * <p>
     * Lleva el nombre del titulo para el mensaje en la pagina
     * <p>
     */
    private static final String TITULO_MENSAJE = "agregarCalendarioActividades.titulo";
//______________________________________________________________________________
    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorAgregarCalendarioActividades.class);

//______________________________________________________________________________
    /**
     * <p>Contiene metodos que permiten el manejo de la informacion relacionada
     * con el calendario de actividades en la base de datos. Este objeto se encuentra registrado
     * como un bean de servicio en Spring, por lo que este es el encargado de
     * inyectar la dependencia.</p>
     */
    @Resource
    protected ServicioCalendarioActividades servicioCalendarioActividadesImpl;
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
     * <p>Listado de todas las semestres disponibles.</p>
     */
    protected List <Semestre> listadoSemestres;
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
        modelo.addAttribute("wrapperCalendarioActividades", new WrapperCalendarioActividades());

        this.listadoSemestres = this.servicioSemestreImpl.getSemestres();
        modelo.addAttribute("semestres", this.listadoSemestres);

        return "calendarioActividades/agregarCalendarioActividades";
    }

//______________________________________________________________________________
    /**
     * <p>Este metodo es llamado cuando se realiza un SUBMIT desde la pagina de
     * agregar calendario de actividades. El metodo se encarga de agregar la informacion
     * ingresada en el formulario de la pagina en la base de datos, el procedimiento
     * que sigue el metodo es el siguiente:
     * <ul>
     * <li>Se realiza la validacion de datos ingresados, si algun dato no cumple
     * con las reglas de validacion se retorna a la pagina para que se muestren
     * los mensajes de error</li>
     * <li>Si la validacion tuvo exito se trata de agregar la informacion a la
     * base de datos por medio de {@link ServicioCalendarioActividades}</li>
     * <li>Se procesa el resultado de la operacion y se le indica a la pagina el
     * mensaje de respuesta que debe de mostrar, el mensaje puede ser de exito o
     * de error</li>
     * </ul></p>
     *
     * @param wrapperCalendarioActividades Pojo del tipo {@link WrapperCalendarioActividades}
     * @param bindingResult Objeto {@link BindingResult}, contiene el resultado de
     *        las validaciones
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @param request Objeto {@link HttpServletRequest}
     * @return String Con la url de la pagina a mostrar
     */
    @RequestMapping(method = RequestMethod.POST)
    public String submit(@Valid WrapperCalendarioActividades wrapperCalendarioActividades,
            BindingResult bindingResult,Model modelo, HttpServletRequest request) {
        // se validan los campos ingresados en el formulario, si existen errores
        // se regresa al formulario para que se muestren los mensajes correspondientes

        modelo.addAttribute("semestres", this.listadoSemestres);

        if(bindingResult.hasErrors())
            return "calendarioActividades/agregarCalendarioActividades";

        try {

            // se quita el envoltorio y se trata de agregar al calendario de actividades
            CalendarioActividades calendarioActividades = new CalendarioActividades();
            wrapperCalendarioActividades.quitarWrapper(calendarioActividades);
            this.servicioCalendarioActividadesImpl.agregarCalendarioActividades(calendarioActividades);

            modelo.addAttribute("wrapperCalendarioActividades", new WrapperCalendarioActividades());

            MensajePopup.crearMensajeRespuesta(request, TITULO_MENSAJE, "agregarCalendarioActividades.exito", true);
            // se registra el evento
            log.info("Agregar calendario de actividades, id: " + calendarioActividades.getIdCalendarioActividades());

        } catch (DataIntegrityViolationException e) {            
            MensajePopup.crearMensajeRespuesta(request, TITULO_MENSAJE, "agregarCalendarioActividades.dataIntegrityViolationException", false);
            log.warn(Mensajes.DATA_INTEGRITY_VIOLATION_EXCEPTION, e);

        } catch (DataAccessException e) {
            // error de acceso a datos
            MensajePopup.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        return "calendarioActividades/agregarCalendarioActividades";
    }

}
