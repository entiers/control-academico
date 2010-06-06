/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.util;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Mario Batres
 * @author Daniel Castillo
 * @version 2.0
 */
public class MensajePopup {
    

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
}
