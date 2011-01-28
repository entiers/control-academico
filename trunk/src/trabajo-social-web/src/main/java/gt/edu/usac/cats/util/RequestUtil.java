/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.util;

import gt.edu.usac.cats.dao.impl.DaoGeneralImpl;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Mario Batres
 * @author Daniel Castillo
 * @version 2.0
 */
public class RequestUtil {

    public RequestUtil() {}
//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de agregar los parametros necesarios en el
     * {@link HttpServletRequest} para que se muestre un mensaje popup con los
     * resultados de las operaciones realizadas por el sistema.</p>
     *
     * @param request Peticion HTTP
     * @param titulo String que tiene el titulo del mensaje a mostrar, en el
     *        caso de ser un mensaje de error el valor de este parametro no se
     *        utiliza por lo que puede ser <code>null</code>
     * @param texto String con el texto que mostrara el mensaje
     * @param exito <code>true</code> si el mensaje es de exito y <code>false</code>
     *        si el mensaje es de error
     */
    public static void crearMensajeRespuesta(HttpServletRequest request,
            String titulo, String texto, boolean exito) {

        request.setAttribute("mostrarPopupMensaje", true);
        request.setAttribute("textoPopupMensaje", texto);

        // se muestra un mensaje de exito
        if(exito) {
            request.setAttribute("tituloPopupMensaje", titulo);
            request.setAttribute("cssPopupMensaje", "cssPopupMensajeExito");
            request.setAttribute("limpiarCampos", true);
        } else {
            request.setAttribute("tituloPopupMensaje", "tituloMensajePopupError");
            request.setAttribute("cssPopupMensaje", "cssPopupMensajeError");
            request.setAttribute("limpiarCampos", false);
        }
    }
//______________________________________________________________________________
    /**
     * <p>Agregar el atributo <b>"url"</b> al request con el valor que es enviado en el parametro
     * para poder ser utilizado en las vista para saber a donde redirigir cuando se presiona el boton
     * de aceptar de los popups.</p>
     *
     * @param request Objeto {@link HttpServletRequest}
     * @param url El url o la pagina a la que se quiere realizar el redirect.
     */
    public static void agregarRedirect(HttpServletRequest request, String url){
        request.setAttribute("url", url);

    }

//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de configurar los botones de paginacion para
     * que estos solo se muestren cuando sea necesario</p>
     *
     * @param request Objeto {@link HttpServletRequest}
     * @param primerRegistro Numero del primer registro que se debe de mostrar
     * @param rowCount Total de registros que genero la busqueda
     */
    public static void configurarPaginacion(HttpServletRequest request,
            int primerRegistro, int rowCount) {

        // configuracion de boton Anterior de la paginacion
        if(primerRegistro == 0)
            request.setAttribute("disabledBtnAnterior", false);
        else
            request.setAttribute("disabledBtnAnterior", true);

        // configuracion de boton Siguiente de la paginacion
        primerRegistro += DaoGeneralImpl.REGISTROS_MAXIMOS;
        if(primerRegistro < rowCount)
            request.setAttribute("disabledBtnSiguiente", true);
        else
            request.setAttribute("disabledBtnSiguiente", false);
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo permite obtener el valor de un atributo boolean que se
     * encuentra en el request. Se debe de tomar en cuenta que si el atributo
     * no existe en el request se retorna <code>false</code>.</p>
     *
     * @param request Peticion HTTP
     * @param nombreAtributo Atributo del que se quiere su valor
     * @return boolean Valor del atributo
     */
    public static boolean getValorBoolean(HttpServletRequest request, String nombreAtributo) {
        boolean valor = request.getAttribute(nombreAtributo) == null
                ? false
                : Boolean.parseBoolean(request.getAttribute(nombreAtributo).toString());

        return valor;
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo permite obtener el valor de un atributo String que se
     * encuentra en el request. Se debe de tomar en cuenta que si el atributo
     * no existe en el request se retorna una cadena vacia.</p>
     *
     * @param request Peticion HTTP
     * @param nombreAtributo Atributo del que se quiere su valor
     * @return String Valor del atributo
     */
    public static String getValorString(HttpServletRequest request, String nombreAtributo) {
        String valor = request.getAttribute(nombreAtributo) == null
                ? ""
                : request.getAttribute(nombreAtributo).toString();

        return valor;
    }
}
