/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.trabajosocial.util;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */


public class MensajePopup {
    
    
    /**
     * <p>Este metodo se encarga de agregar los parametros necesarios en el
     * {@link HttpServletRequest} para que se muestre el mensaje popup de
     * resultados.</p>
     *
     * @param request Objeto {@link HttpServletRequest}
     * @param exito Si es true el mensaje a mostrar es de exito, si es false
     *        el mensaje a mostrar es de error
     * @param mensaje Texto que mostrar el mensaje
     */
    public static void configurar(HttpServletRequest request, boolean exito,
            boolean limpiar, String tituloMensaje, String mensaje) {

        request.setAttribute("limpiarCampos", limpiar);
        request.setAttribute("mostrarPopup", true);
        request.setAttribute("cuerpoMensaje", mensaje);

        if(exito) {
            request.setAttribute("tituloMensaje", tituloMensaje);
            request.setAttribute("cssMensaje", "cssMensajeExito");

        } else {
            request.setAttribute("tituloMensaje", "tituloError");
            request.setAttribute("cssMensaje", "cssMensajeError");
        }
    }
}
