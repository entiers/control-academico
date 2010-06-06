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
public class RequestUtil {

    public RequestUtil() {}
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
