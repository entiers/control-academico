/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.controlador.calendarioActividades;

import gt.edu.usac.trabajosocial.dominio.CalendarioActividades;
import gt.edu.usac.trabajosocial.dominio.Semestre;
import gt.edu.usac.trabajosocial.dominio.busqueda.DatosBusquedaCalendarioActividades;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Esta clase se encarga de modificar un calendario de actividades existente en
 * la BD. La informacion se pide en la pagina de <code>editarCalendarioActividades.htm</code>.
 * 
 * @author Mario Batres
 * @version 1.0
 */

@Controller("controladorEditarCalendarioActividades")
@RequestMapping("editarCalendarioActividades")
public class ControladorEditarCalendarioActividades {
//_____________________________________________________________________________
    /**
     * <p>
     * Lleva el nombre del titulo para el mensaje en la pagina
     * </p>
     */
    private static final String TITULO_MENSAJE = "editarCalendarioActividades.titulo";
//______________________________________________________________________________
    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorEditarCalendarioActividades.class);
//______________________________________________________________________________
    /**
     * <p>Listado de todas las semestres disponibles.</p>
     */
    protected List <Semestre> listadoSemestres;
//______________________________________________________________________________
    /**
     * <p>Contiene metodos basicos de acceso a la base de datos, estos metodos
     * permiten realizar operaciones basicas sobre cualquier tabla de la base
     * de datos.</p>
     */
    @Resource
    protected ServicioCalendarioActividades servicioCalendarioActividadesImpl;
//______________________________________________________________________________
    @Resource
    /**
     * <p>Contiene metodos que permiten el manejo de la informacion relacionada
     * con el semestre en la base de datos. Este objeto se encuentra registrado
     * como un bean de servicio en Spring, por lo que este es el encargado de
     * inyectar la dependencia.</p>
     */
    protected ServicioSemestre servicioSemestreImpl;
//______________________________________________________________________________

    /**
     * <p>Se utiliza para mantener todos los datos del calendario de actividades que se
     * encontro en la busqueda.</p>
     */
    private CalendarioActividades calendarioActividades;

//______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina <code>editarCalendarioActividades.htm</code>. El metodo se encarga
     * de iniciar los objetos que se usaran en la pagina.</p>
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(method = RequestMethod.GET)
    public String crearFormulario(Model modelo, short idCalendarioActividades, HttpServletRequest request){
        WrapperCalendarioActividades wrapperCalendarioActividades = new WrapperCalendarioActividades();

        this.calendarioActividades = this.servicioCalendarioActividadesImpl
                .getCalendarioActividadesPorID(idCalendarioActividades);
                
        
        if(this.calendarioActividades == null){
            MensajePopup.crearMensajeRespuesta(request, TITULO_MENSAJE, "buscarCalendarioActividades.sinResultados", false);
            return "calendarioActividades/buscarCalendarioActividades";
        }

        this.listadoSemestres = this.servicioSemestreImpl.getSemestres();


        wrapperCalendarioActividades.agregarWrapper(this.calendarioActividades);

        modelo.addAttribute("semestres", this.listadoSemestres);
        modelo.addAttribute("wrapperCalendarioActividades", wrapperCalendarioActividades);
        return "calendarioActividades/editarCalendarioActividades";
    }
//______________________________________________________________________________    
    /**
     * <p>Este metodo se ejecuta cuando se presiona el boton de editar de la
     * pagina <code>editarCalendarioActividades</code>. El metodo se encarga de
     * actualizar la informacion del calendario de actividades que se obtuvo en
     * la busqueda. El metodo realiza las siguiente acciones:
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
    @RequestMapping(method=RequestMethod.POST)
    public String editar(@Valid WrapperCalendarioActividades wrapperCalendarioActividades
            , BindingResult bindingResult, Model modelo, HttpServletRequest request){


        modelo.addAttribute("semestres", this.listadoSemestres);

        if(bindingResult.hasErrors()){
            return "calendarioActividades/editarCalendarioActividades";
        }

        try{
            // se quita el envoltorio y se trata de actualizar al calendario
            wrapperCalendarioActividades.quitarWrapper(this.calendarioActividades);

            this.servicioCalendarioActividadesImpl.actualizarCalendarioActividades(calendarioActividades);

            MensajePopup.crearMensajeRespuesta(request, TITULO_MENSAJE, "editarCalendarioActividades.exito", true);
            String msg = Mensajes.EXITO_ACTUALIZACION + "Calendario de Actividades, ID "
                    + this.calendarioActividades.getIdCalendarioActividades();
            log.info(msg);

            modelo.addAttribute("wrapperCalendarioActividades", wrapperCalendarioActividades);

        }catch(DataAccessException e){
            // error de acceso a datos
            MensajePopup.crearMensajeRespuesta(request, null, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        return "calendarioActividades/editarCalendarioActividades";
    }
}
