/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.util;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Daniel Castillo
 * @version 2.0
 */
public class BotonesPaginacion {

    /**
     * <p>Cantidad maxima de registros que se mostraran en los listados.</p>
     */
    public final static int REGISTROS_MAXIMOS = 3;


    /**
     * <p>Este metodo se encarga de configurar los botones de paginacion para
     * que estos solo se muestren cuando sea necesario</p>
     *
     * @param request Objeto {@link HttpServletRequest}
     * @param primerRegistro Numero del primer registro que se debe de mostrar
     * @param rowCount Total de registros que genero la busqueda
     */
    public static void configurar(HttpServletRequest request,
            int primerRegistro, int rowCount) {

        // configuracion de boton Anterior de la paginacion
        if(primerRegistro == 0)
            request.setAttribute("disabledBtnAnterior", false);
        else
            request.setAttribute("disabledBtnAnterior", true);

        // configuracion de boton Siguiente de la paginacion
        primerRegistro += REGISTROS_MAXIMOS;
        if(primerRegistro < rowCount)
            request.setAttribute("disabledBtnSiguiente", true);
        else
            request.setAttribute("disabledBtnSiguiente", false);
    }
}
