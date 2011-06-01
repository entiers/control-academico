/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */
package gt.edu.usac.cats.controlador.calendarioActividades;

import gt.edu.usac.cats.dominio.CalendarioActividades;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.dominio.busqueda.DatosBusquedaCalendarioActividades;
import gt.edu.usac.cats.enums.ControlReporte;
import gt.edu.usac.cats.util.RequestUtil;
import gt.edu.usac.cats.util.Mensajes;
import java.util.List;
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
 * Esta clase se encarga de la busqueda de Calendarios de Actividades en la BD
 * para mostrarlos en la pagina de <code>buscarCalendarioActividades.htm</code>.
 *
 * @author Mario Batres
 * @version 1.0
 */
@Controller
public class ControladorBuscarCalendarioActividades extends ControladorAbstractoCalendarioActividades {
//_____________________________________________________________________________

    /**
     * <p>
     * Lleva el nombre del titulo para el mensaje en la pagina
     * </p>
     */
    private static final String TITULO_MENSAJE = "buscarCalendarioActividades.titulo";
//______________________________________________________________________________
    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorBuscarCalendarioActividades.class);
//______________________________________________________________________________
    /**
     * <p>Listado de todos los calendario de actividades disponibles.</p>
     */
    private List<CalendarioActividades> listadoCalendarioActividades;

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
    @RequestMapping(value = "buscarCalendarioActividades.htm", method = RequestMethod.GET)
    public String crearFormulario(Model modelo) {
        this.listadoSemestres = this.servicioSemestreImpl.listarSemestresParaBusqueda();
        this.listadoCalendarioActividades = null;

        this.agregarAtributosDefault(modelo, new DatosBusquedaCalendarioActividades());
        return "calendarioActividades/buscarCalendarioActividades";
    }
//______________________________________________________________________________

    /**
     * @param modelo
     * @param datosBusquedaCalendarioActividades
     *
     * @return
     */
    @RequestMapping(value = "buscarCalendarioActividadesPag.htm", method = RequestMethod.GET)
    public String buscarCalendarioActividadesPag(Model modelo, DatosBusquedaCalendarioActividades datosBusquedaCalendarioActividades) {
        this.agregarAtributosDefault(modelo, datosBusquedaCalendarioActividades);
        return "calendarioActividades/buscarCalendarioActividades";
    }

//______________________________________________________________________________
    /**
     * @param modelo
     * @param datosBusquedaCalendarioActividades
     *
     */
    public void agregarAtributosDefault(Model modelo,
            DatosBusquedaCalendarioActividades datosBusquedaCalendarioActividades) {

        modelo.addAttribute("datosBusquedaCalendarioActividades", datosBusquedaCalendarioActividades);
        modelo.addAttribute("listadoSemestres", this.listadoSemestres);
        modelo.addAttribute("listadoCalendarioActividades", this.listadoCalendarioActividades);
        modelo.addAttribute("nombreControlReporte", ControlReporte.CALENDARIO_ACTIVIDADES);
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
    @RequestMapping(value = "buscarCalendarioActividades.htm", method = RequestMethod.POST)
    public String buscar(@Valid DatosBusquedaCalendarioActividades datosBusquedaCalendarioActividades,
            BindingResult bindingResult, Model modelo, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return "calendarioActividades/buscarCalendarioActividades";
        }

        try {
            Semestre semestre = datosBusquedaCalendarioActividades.getSemestre();

            this.listadoCalendarioActividades = this.servicioCalendarioActividadesImpl.getCalendarioActividadesPorSemestre(semestre);

            if (this.listadoCalendarioActividades == null || this.listadoCalendarioActividades.isEmpty()) {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "buscarHorario.sinResultados", true);
            }

        } catch (DataAccessException e) {
            RequestUtil.crearMensajeRespuesta(request, null, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        this.agregarAtributosDefault(modelo, datosBusquedaCalendarioActividades);
        return "calendarioActividades/buscarCalendarioActividades";
    }
}
