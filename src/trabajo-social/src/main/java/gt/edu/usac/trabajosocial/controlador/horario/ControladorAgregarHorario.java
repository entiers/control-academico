/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.controlador.horario;

import gt.edu.usac.trabajosocial.dominio.Curso;
import gt.edu.usac.trabajosocial.dominio.Horario;
import gt.edu.usac.trabajosocial.dominio.Salon;
import gt.edu.usac.trabajosocial.dominio.Semestre;
import gt.edu.usac.trabajosocial.dominio.wrapper.WrapperHorario;
import gt.edu.usac.trabajosocial.servicio.ServicioCurso;
import gt.edu.usac.trabajosocial.servicio.ServicioHorario;
import gt.edu.usac.trabajosocial.servicio.ServicioSalon;
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
 * Esta clase se encarga de almacenar horarios en la BD.
 * La informacion se pide en la pagina de <code>agregarHorario.htm</code>.
 *
 * @author Mario Batres
 * @version 1.0
 */

@Controller("controladorAgregarHorario")
@RequestMapping(value = "agregarHorario.htm")
public class ControladorAgregarHorario{
//_____________________________________________________________________________
    protected static final String [] LISTADO_DIAS =
    {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};
//______________________________________________________________________________
    /**
     * <p>Lleva el nombre del titulo para el mensaje en la pagina.<p>
     */
    private static String TITULO_MENSAJE = "agregarHorario.titulo";
//______________________________________________________________________________

    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorAgregarHorario.class);

//______________________________________________________________________________
    /**
     * <p>Listado de todas las cursos disponibles.</p>
     */
    protected List <Semestre> listadoSemestres;
//______________________________________________________________________________
    /**
     * <p>Listado de todas las salones disponibles.</p>
     */
    protected List <Salon> listadoSalones;

//______________________________________________________________________________
    /**
     * <p>Listado de todas las cursos disponibles.</p>
     */
    protected List <Curso> listadoCursos;
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
     * <p>Contiene metodos que permiten el manejo de la informacion relacionada
     * con el salon en la base de datos. Este objeto se encuentra registrado
     * como un bean de servicio en Spring, por lo que este es el encargado de
     * inyectar la dependencia.</p>
     */
    @Resource
    protected ServicioSalon servicioSalonImpl;

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
     * <p>Contiene metodos que permiten el manejo de la informacion relacionada
     * con el curso en la base de datos. Este objeto se encuentra registrado
     * como un bean de servicio en Spring, por lo que este es el encargado de
     * inyectar la dependencia.</p>
     */
    @Resource
    protected ServicioCurso servicioCursoImpl;

//______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina <code>agregarHorario.htm</code>. El metodo se encarga
     * de iniciar los objetos que se usaran en la pagina.</p>
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(method = RequestMethod.GET)
    public String crearFormulario(Model modelo) {

        // se agregan los objetos que se usaran en la pagina
        modelo.addAttribute("wrapperHorario", new WrapperHorario());

        this.listadoSalones = this.servicioSalonImpl.getSalones();
        this.listadoSemestres = this.servicioSemestreImpl.getSemestres();
        this.listadoCursos = this.servicioCursoImpl.getCursos();

        modelo.addAttribute("cursos", this.listadoCursos);
        modelo.addAttribute("salones", this.listadoSalones);
        modelo.addAttribute("semestres", this.listadoSemestres);
        modelo.addAttribute("dias", LISTADO_DIAS);

        return "horario/agregarHorario";
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo es llamado cuando se realiza un SUBMIT desde la pagina de
     * agregar horario <code>agregarHorario.htm</code>. El metodo se encarga de
     * agregar la informacion ingresada en el formulario de la pagina en la
     * base de datos, el procedimiento
     * que sigue el metodo es el siguiente:
     * <ul>
     * <li>Se realiza la validacion de datos ingresados, si algun dato no cumple
     * con las reglas de validacion se retorna a la pagina para que se muestren
     * los mensajes de error</li>
     * <li>Si la validacion tuvo exito se trata de agregar la informacion a la
     * base de datos por medio de {@link ServicioHorario}</li>
     * <li>Se procesa el resultado de la operacion y se le indica a la pagina el
     * mensaje de respuesta que debe de mostrar, el mensaje puede ser de exito o
     * de error</li>
     * </ul></p>
     *
     * @param horario Pojo del tipo {@link Horario}
     * @param resultado Objeto {@link BindingResult}, contiene el resultado de
     *        las validaciones
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @param request Objeto {@link HttpServletRequest}
     * @return String Con la url de la pagina a mostrar
     */
    @RequestMapping(method = RequestMethod.POST)
    public String submit(@Valid WrapperHorario wrapperHorario, BindingResult bindingResult,
            Model modelo, HttpServletRequest request) {

        modelo.addAttribute("cursos", this.listadoCursos);
        modelo.addAttribute("salones", this.listadoSalones);
        modelo.addAttribute("semestres", this.listadoSemestres);
        modelo.addAttribute("dias", LISTADO_DIAS);

        // se validan los campos ingresados en el formulario, si existen errores
        // se regresa al formulario para que se muestren los mensajes correspondientes
        if(bindingResult.hasErrors())
            return "horario/agregarHorario";

        try {
            // se obtiene la carrera seleccionada            

            // se quita el envoltorio y se trata de agregar el horario
            Horario horario = new Horario();
            wrapperHorario.quitarWrapper(horario);
            this.servicioHorarioImpl.agregarHorario(horario);

            modelo.addAttribute("wrapperHorario", new WrapperHorario());

            // se registra el evento
            MensajePopup.crearMensajeRespuesta(request, TITULO_MENSAJE, "agregarHorario.exito", true);
            String msg = Mensajes.EXITO_AGREGAR + "Horario, id " + horario.getIdHorario();
            log.info(msg);

        } catch (DataAccessException e) {
            // error de acceso a datos
            MensajePopup.crearMensajeRespuesta(request, null, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        return "horario/agregarHorario";
    }
}
