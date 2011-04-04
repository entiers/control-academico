/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cats.controlador.pensum;

import gt.edu.usac.cats.dominio.Pensum;
import gt.edu.usac.cats.dominio.wrapper.WrapperPensum;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
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
 *
 * @author Mario Batres
 * @version 1.0
 */
@Controller
@RequestMapping(value = "agregarPensum.htm")
public class ControladorAgregarPensum extends ControladorAbstractoPensum {
    //______________________________________________________________________________

    /**
     * <p>Lleva el nombre del titulo para el mensaje en la pagina.<p>
     */
    private static String TITULO_MENSAJE = "agregarPensum.titulo";
//______________________________________________________________________________
    /**
     * <p>Matiene una bitacora de lo realizado por esta clase.</p>
     */
    private static Logger log = Logger.getLogger(ControladorAgregarPensum.class);
//______________________________________________________________________________

    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina <code>agregarPensum.htm</code>. El metodo se encarga
     * de iniciar los objetos que se usaran en la pagina.</p>
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(method = RequestMethod.GET)
    public String crearFormulario(Model modelo) {
        this.agregarAtributosDefault(modelo, new WrapperPensum());

        return "pensum/agregarPensum";
    }
//______________________________________________________________________________

    /**
     * <p>Este metodo es llamado cuando se realiza un SUBMIT desde la pagina de
     * agregar pensum <code>agregarPensum.htm</code>. El metodo se encarga de
     * agregar la informacion ingresada en el formulario de la pagina en la
     * base de datos, el procedimiento
     * que sigue el metodo es el siguiente:
     * </p>
     *
     * @param Pensum Pojo del tipo {@link Pensum}
     * @param resultado Objeto {@link BindingResult}, contiene el resultado de
     *        las validaciones
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @param request Objeto {@link HttpServletRequest}
     * @return String Con la url de la pagina a mostrar
     */
    @RequestMapping(method = RequestMethod.POST)
    public String submit(Model modelo, @Valid WrapperPensum wrapperPensum, BindingResult bindingResult,
            HttpServletRequest request) {

        // se validan los campos ingresados en el formulario, si existen errores
        // se regresa al formulario para que se muestren los mensajes correspondientes
        if (!bindingResult.hasErrors()) {
            try {
                this.pensum = new Pensum();
                wrapperPensum.quitarWrapper(this.pensum);

                this.servicioPensumImpl.agregar(this.pensum);


                wrapperPensum = new WrapperPensum();
                // se registra el evento
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "agregarPensum.exito", true);
                log.info(Mensajes.EXITO_AGREGAR + this.pensum.toString());


            } catch (DataIntegrityViolationException e) {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataIntegrityException", false);
                log.error(Mensajes.DATA_INTEGRITY_VIOLATION_EXCEPTION, e);
            } catch (DataAccessException e) {
                // error de acceso a datos
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
                log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
            }
        }
        this.agregarAtributosDefault(modelo, wrapperPensum);
        return "pensum/agregarPensum";
    }
}
