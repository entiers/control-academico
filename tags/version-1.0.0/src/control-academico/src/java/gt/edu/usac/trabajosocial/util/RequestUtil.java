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
public class RequestUtil {

    private boolean mostrar;
    private boolean limpiar;
    

    public RequestUtil(HttpServletRequest request) {
        this.parsear(request);
    }


    private void parsear(HttpServletRequest request){
        this.mostrar =
                    request.getAttribute("mostrarPopup") == null
                    ? false
                    : Boolean.parseBoolean(request.getAttribute("mostrarPopup").toString());


        this.limpiar =
                    request.getAttribute("limpiarCampos") == null
                    ? false
                    : Boolean.parseBoolean(request.getAttribute("limpiarCampos").toString());

    
    }

    /**
     * @return the mostrar
     */
    public boolean isMostrar() {
        return mostrar;
    }

    /**
     * @return the limpiar
     */
    public boolean isLimpiar() {
        return limpiar;
    }

    
}
