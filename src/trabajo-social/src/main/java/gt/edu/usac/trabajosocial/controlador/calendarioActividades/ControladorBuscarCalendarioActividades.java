/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.controlador.calendarioActividades;

import gt.edu.usac.trabajosocial.dominio.CalendarioActividades;
import gt.edu.usac.trabajosocial.dominio.Semestre;
import gt.edu.usac.trabajosocial.dominio.busqueda.DatosBusquedaCalendarioActividades;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Esta clase se encarga de la búsqueda de Calendarios de Actividades en la BD
 * para mostrarlos en la página de <code>buscarCalendarioActividades.htm</code>.
 *
 * @author Mario Batres
 * @version 1.0
 */

@Controller("controladorBuscarCalendarioActividades")
@RequestMapping("buscarCalendarioActividades.htm")
public class ControladorBuscarCalendarioActividades {
//_____________________________________________________________________________
    /**
     * <p>
     * Lleva el nombre del titulo para el mensaje en la página
     * <p>
     */
    private static final String TITULO_MENSAJE = "buscarCalendarioActividades.titulo";
//______________________________________________________________________________
    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorBuscarCalendarioActividades.class);
//______________________________________________________________________________
    /**
     * <p>Listado de todas las semestres disponibles.</p>
     */
    private List <Semestre> listadoSemestres;
//______________________________________________________________________________

    /**
     * <p>Listado de todos los calendario de actividades disponibles.</p>
     */
    private List <CalendarioActividades> listadoCalendarioActividades;

//______________________________________________________________________________
    /**
     * <p>Contiene metodos basicos de acceso a la base de datos, estos metodos
     * permiten realizar operaciones basicas sobre cualquier tabla de la base
     * de datos.</p>
     */
    @Resource
    protected ServicioSemestre servicioSemestreImpl;
//______________________________________________________________________________
    /**
     * <p>Contiene metodos basicos de acceso a la base de datos, estos metodos
     * permiten realizar operaciones basicas sobre cualquier tabla de la base
     * de datos.</p>
     */
    @Resource
    protected ServicioCalendarioActividades servicioCalendarioActividadesImpl;
//______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina <code>buscarCalendarioActividades.htm</code>. El metodo se encarga
     * de iniciar los objetos que se usaran en la pagina.</p>
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(method = RequestMethod.GET)
    public String crearFormulario(Model modelo){
        this.listadoSemestres = this.servicioSemestreImpl.getSemestres();
        modelo.addAttribute("datosBusquedaCalendarioActividades", new DatosBusquedaCalendarioActividades());
        modelo.addAttribute("semestres", this.listadoSemestres);
        return "calendarioActividades/buscarCalendarioActividades";
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cuando se solicita una busqueda desde la pagina
     * de buscar calendario de actividades. Las busquedas solo se realizan por el
     * semestre asignado al curso de actividades. El metodo realiza los siguientes
     * pasos:
     * <ul>
     * <li>Se valida que al menos se envio un parametro de busqueda</li>
     * <li>Valida los datos ingresados</li>
     * <li>Se delega la busqueda al metodo <code>buscar</code></li>
     * </ul>
     * </p>
     *
     * @param datosBusquedaCalendarioActividades Contiene los parametros de la busqueda
     * @param bindingResult Objeto {@link BindingResult} que valida los datos
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @param request Peticion HTTP
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(method = RequestMethod.POST)
    public String buscar(@Valid DatosBusquedaCalendarioActividades datosBusquedaCalendarioActividades,
            BindingResult bindingResult, Model modelo, HttpServletRequest request){
        modelo.addAttribute("semestres", this.listadoSemestres);

        if(bindingResult.hasErrors())
            return "calendarioActividades/buscarCalendarioActividades";

        try{
            Semestre semestre = datosBusquedaCalendarioActividades.getSemestre();

            this.listadoCalendarioActividades = this.servicioCalendarioActividadesImpl.getCalendarioActividadesPorSemestre(semestre);

            if(this.listadoCalendarioActividades == null || this.listadoCalendarioActividades.size() == 0) {
                MensajePopup.crearMensajeRespuesta(request, TITULO_MENSAJE, "buscarHorario.sinResultados", true);
            }

            modelo.addAttribute("listadoCalendarioActividades", this.listadoCalendarioActividades);


        }catch(DataAccessException e){
            MensajePopup.crearMensajeRespuesta(request, null, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }
        return "calendarioActividades/buscarCalendarioActividades";
    }
}
