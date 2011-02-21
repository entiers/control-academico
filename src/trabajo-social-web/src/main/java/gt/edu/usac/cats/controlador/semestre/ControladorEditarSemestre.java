/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.controlador.semestre;

import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.dominio.wrapper.WrapperSemestre;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
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
 * Esta clase se encarga de editar o modificar un semestre en la base de datos.
 *
 * @author Mario Batres
 * @version 1.0
 */
@Controller
@RequestMapping(value = "editarSemestre.htm")
public class ControladorEditarSemestre extends ControladorAbstractoSemestre{
    /**
     * <p>Lleva el nombre del titulo para el mensaje en la pagina.<p>
     */
    private static final String TITULO_MENSAJE = "editarSemestre.titulo";
//______________________________________________________________________________
    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorEditarSemestre.class);
//______________________________________________________________________________

    private Semestre semestre;
//______________________________________________________________________________

    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina <code>editarSemestre.htm</code>. El metodo se encarga
     * de iniciar los objetos que se usaran en la pagina.</p>
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     *
     * @param idSemestre Id del semestre para editar
     *
     * @param request Objeto de tipo {@link HttpServletRequest}
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(method = RequestMethod.GET)
    public String crearFormulario(Model modelo, Short idSemestre, HttpServletRequest request) {

        WrapperSemestre wrapperSemestre = new WrapperSemestre();
        this.semestre = this.servicioSemestreImpl.cargarEntidadPorID(Semestre.class, idSemestre);

        if(this.semestre == null){
            String ret = this.crearFormularioBuscarSemestre(modelo);
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "editarSemestre.sinResultados", false);
            return ret;
        }

        wrapperSemestre.agregarWrapper(semestre);

        modelo.addAttribute("wrapperSemestre", wrapperSemestre);

        return "semestre/editarSemestre";
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cuando se presiona el boton de editar de la
     * pagina <code>editarSemestre.htm</code>. El metodo se encarga de
     * actualizar la informacion del semestre que se obtuvo en
     * la busqueda. El metodo realiza las siguiente acciones:
     * <ul>
     * <li>Realiza las validaciones de los datos del formulario</li>
     * <li>Delega la funcion de actualizacion a {@link ServicioSemestre}</li>
     * <li>Muestra un mensaje popup con el resultado de la operacion</li>
     * </ul></p>
     *
     * @param wrapperSemestre Pojo del tipo {@link WrapperSemestre}
     * @param bindingResult Ojeto {@link BindingResult} que realiza las validaciones
     * @param modelo Objeto {@link Model} que contiene los objeto de la pagina
     * @param request Objeto {@link HttpServletRequest}
     *
     * @return Nombre de la vista a mostrar
     */
    @RequestMapping(method = RequestMethod.POST)
    public String editar(@Valid WrapperSemestre wrapperSemestre,
            BindingResult bindingResult, Model modelo, HttpServletRequest request){



        if(bindingResult.hasErrors()) {
            return "semestre/editarSemestre";
        }

        try {
            // se quita el envoltorio y se trata de actualizar al calendario
            wrapperSemestre.quitarWrapper(this.semestre);

            this.servicioSemestreImpl.actualizar(this.semestre);

            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "editarSemestre.exito", true);
            RequestUtil.agregarRedirect(request, "buscarSemestre.htm");


            String msg = Mensajes.EXITO_ACTUALIZACION + "Semestre, ID = "
                    + this.semestre.getIdSemestre();
            log.info(msg);

            modelo.addAttribute("wrapperSemestre", wrapperSemestre);



        } catch(DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, null, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        return "semestre/editarSemestre";
    }

}
