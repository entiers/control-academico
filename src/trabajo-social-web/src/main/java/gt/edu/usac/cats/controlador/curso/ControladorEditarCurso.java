/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.controlador.curso;

import gt.edu.usac.cats.dominio.Curso;
import gt.edu.usac.cats.dominio.busqueda.DatosBusquedaCurso;
import gt.edu.usac.cats.dominio.wrapper.WrapperCurso;
import gt.edu.usac.cats.servicio.ServicioCurso;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import java.io.Serializable;
import javax.annotation.Resource;
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
 * Esta clase se encarga de buscar y modficar un curso existente en la BD.
 * La informacion se pide en la pagina de <code>buscarCurso.htm</code>.
 * Y se modifica en la pagina de <code>editarCurso.htm</code>.
 *
 * @author Mario Batres
 * @version 1.0
 */

@Controller
@Scope(value = WebApplicationContext.SCOPE_SESSION)
@SessionAttributes(value={"curso"})
public class ControladorEditarCurso implements Serializable {

    /**
     * <p>
     * Lleva el nombre del titulo para el mensaje en la pagina
     * <p>
     */
    private static String TITULO_MENSAJE = "editarCurso.titulo";
//______________________________________________________________________________
    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorEditarCurso.class);
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
     * <p>Se utiliza para mantener todos los datos del curso que se
     * encontro en la busqueda.</p>
     */
    private Curso curso;
//______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina <code>editarCurso.htm</code>. El metodo se encarga
     * de iniciar los objetos que se usaran en la pagina.</p>
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(value = "editarCurso.htm", method = RequestMethod.GET)
    public String crearFormulario(Model modelo) {

        // se agregan los objetos que se usaran en la pagina
        modelo.addAttribute("wrapperCurso", new WrapperCurso());
        modelo.addAttribute("datosBusquedaCurso", new DatosBusquedaCurso());

        return "curso/editarCurso";
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cuando se solicita una busqueda desde la pagina
     * de editar curso. Las busquedas solo se realizan por el código. El metodo
     * realiza los siguientes pasos:
     * <ul>
     * <li>Valida que el codigo ingresado sea valido</li>
     * <li>Si el codigo es valido se realiza la busqueda y se muestra la
     * informacion del curso en la pagina, si la busqueda no genera
     * resultados se muestra un mensaje popup</li>
     * <li>Si ocurre un error de acceso a la base de datos se muestra un mensaje
     * popup indicando del error</li>
     * </ul>
     * </p>
     *
     * @param datosBusquedaCurso Contiene los parametros de la busqueda, en
     *        este caso solo el numero de carne
     * @param resultado Objeto {@link BindingResult} que valida los datos
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @param request Peticion HTTP
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(value = "buscarEditarCurso.htm", method = RequestMethod.POST)
    public String buscarCurso(@Valid DatosBusquedaCurso datosBusquedaCurso,
            BindingResult bindingResult, Model modelo, HttpServletRequest request) {

        // se crea el envoltorio para el curso
        WrapperCurso wrapperCurso = new WrapperCurso();
        modelo.addAttribute("wrapperCurso", wrapperCurso);

        // se obtiene el carne ingresado para realizar la busqueda
        String codigo = datosBusquedaCurso.getCodigo();

        if(codigo.isEmpty() || bindingResult.hasErrors()){
            return "curso/editarCurso";
        }
        try {
            // se realiza la busqueda del curso
            this.curso = this.servicioCursoImpl.buscarCursoPorCodigo(codigo);

            if(this.curso == null) {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "editarCurso.sinResultados", true);
            } else {
                wrapperCurso.agregarWrapper(this.curso);
            }
        } catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, null, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        return "curso/editarCurso";
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cuando se presiona el boton de editar de la
     * pagina <code>editarCurso.htm</code>. El metodo se encarga de actualizar
     * la informacion del curso que se obtuvo en la busqueda. El metodo realiza
     * las siguiente acciones:
     * <ul>
     * <li>Realiza las validaciones de los datos del formulario</li>
     * <li>Delega la funcion de actualizacion a {@link ServicioCurso}</li>
     * <li>Muestra un mensaje popup con el resultado de la operacion</li>
     * </ul></p>
     *
     * @param curso Pojo del tipo {@link Curso}
     * @param bindingResult Ojeto {@link BindingResult} que realiza las validaciones
     * @param modelo Objeto {@link Model} que contiene los objeto de la pagina
     * @param request Objeto {@link HttpServletRequest}
     * @return String
     */
    @RequestMapping(value = "editarCurso.htm", method = RequestMethod.POST)
    public String submit(@Valid WrapperCurso wrapperCurso, BindingResult bindingResult,
            Model modelo, HttpServletRequest request) {

        modelo.addAttribute("datosBusquedaCurso", new DatosBusquedaCurso());

        // se validan los campos ingresados en el formulario, si existen errores
        // se regresa al formulario para que se muestren los mensajes correspondientes

        if(bindingResult.hasErrors()) {
            return "curso/editarCurso";
        }
        
        try {
            // se quita el envoltorio y se trata de actualizar al curso
            wrapperCurso.quitarWrapper(this.curso);
            this.servicioCursoImpl.actualizar(this.curso);

            // se registra el evento
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "editarCurso.exito", true);
            String msg = Mensajes.EXITO_ACTUALIZACION + "Curso, codigo " + this.curso.getCodigo();
            log.info(msg);

        } catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, null, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        return "curso/editarCurso";
    }
}
