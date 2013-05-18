/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */
package gt.edu.usac.cats.controlador.horario;

import gt.edu.usac.cats.dominio.Horario;
import gt.edu.usac.cats.dominio.Salon;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.dominio.busqueda.DatosBusquedaHorario;
import gt.edu.usac.cats.enums.ControlReporte;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import java.util.List;
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
 * Esta clase se encarga de la busqueda de Horarios en la BD
 * para mostrarlos en la pagina de <code>buscarHorario.htm</code>.
 *
 * @author Mario Batres
 * @version 1.0
 */
@Controller("controladorBuscarHorario")
@Scope(value = WebApplicationContext.SCOPE_SESSION)
@SessionAttributes(value={"listadoSemestres", "listadoSalones"}) 
public class ControladorBuscarHorario extends ControladorAbstractoHorario {
//______________________________________________________________________________

    /**
     * <p>Listado de todas las cursos disponibles.</p>
     */
    private List<Semestre> listadoSemestres;
//______________________________________________________________________________
    /**
     * <p>Listado de todas las salones disponibles.</p>
     */
    private List<Salon> listadoSalones;
 
//_____________________________________________________________________________    
    /**
     * <p>Lleva el nombre del titulo para el mensaje en la pagina.</p>
     */
    private static final String TITULO_MENSAJE = "buscarHorario.titulo";
//______________________________________________________________________________
    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorBuscarHorario.class);
//______________________________________________________________________________
    /**
     * <p>Listado de todas las horarios disponibles.</p>
     */
    protected List<Horario> listadoHorarios;
//______________________________________________________________________________

    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina <code>buscarHorario.htm</code>. El metodo se encarga
     * de iniciar los objetos que se usaran en la pagina.</p>
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(value = "buscarHorario.htm", method = RequestMethod.GET)
    public String crearFormulario(Model modelo) {
        this.listadoHorarios = null;
        this.agregarAtributosDefaultBusqueda(modelo, new DatosBusquedaHorario(), true);
        return "horario/buscarHorario";
    }
//______________________________________________________________________________

    @RequestMapping(value = "buscarHorarioPag.htm", method = RequestMethod.GET)
    public String buscarHorarioPag(Model modelo, DatosBusquedaHorario datosBusquedaHorario,
            HttpServletRequest request) {

        //para evitar error de LAZY, se vuelve a buscar, SE DEBE DE ENCONTRAR OTRA FORMA
        this.realizarBusqueda(datosBusquedaHorario, request);

        this.agregarAtributosDefaultBusqueda(modelo, datosBusquedaHorario, false);
        return "horario/buscarHorario";
    }

    //______________________________________________________________________________
    protected void agregarAtributosDefaultBusqueda(Model modelo, DatosBusquedaHorario datosBusquedaHorario, boolean buscar) {
        modelo.addAttribute("datosBusquedaHorario", datosBusquedaHorario);
        modelo.addAttribute("nombreControlReporte", ControlReporte.HORARIO);
        modelo.addAttribute("listadoHorarios", this.listadoHorarios);
        this.agregarAtributosDefault(modelo, listadoSalones, listadoSemestres, buscar);

    }
//______________________________________________________________________________

    /**
     * <p>Este metodo se ejecuta cuando se solicita una busqueda desde la pagina
     * de buscar horario. Las busquedas solo se realizan por el
     * salon y semestre asigando al horari. El metodo realiza los siguientes
     * pasos:
     * <ul>
     * <li>Se valida que al menos se envio un parametro de busqueda</li>
     * <li>Valida los datos ingresados</li>
     * <li>Se delega la busqueda al metodo <code>buscar</code></li>
     * </ul>
     * </p>
     *
     * @param datosBusquedaHorario Contiene los parametros de la busqueda
     * @param bindingResult Objeto {@link BindingResult} que valida los datos
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @param request Peticion HTTP
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(value = "buscarHorario.htm", method = RequestMethod.POST)
    public String buscar(@Valid DatosBusquedaHorario datosBusquedaHorario, BindingResult bindingResult,
            Model modelo, HttpServletRequest request) {

        if (!bindingResult.hasErrors()) {
           this.realizarBusqueda(datosBusquedaHorario, request);
        }

        this.agregarAtributosDefaultBusqueda(modelo, datosBusquedaHorario, false);
        return "horario/buscarHorario";
    }

//______________________________________________________________________________
    private void realizarBusqueda(DatosBusquedaHorario datosBusquedaHorario, HttpServletRequest request){
         try {
                Salon salon = datosBusquedaHorario.getSalon();
                Semestre semestre = datosBusquedaHorario.getSemestre();

                this.listadoHorarios = this.servicioHorarioImpl.buscarHorarioPorSalonYSemestre(salon, semestre);

                if (listadoHorarios == null || listadoHorarios.isEmpty()) {
                    RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "buscarHorario.sinResultados", true);
                }

            } catch (DataAccessException e) {
                // error de acceso a datos
                RequestUtil.crearMensajeRespuesta(request, null, "dataAccessException", false);
                log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
            }
    }
}
