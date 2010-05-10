/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.controlador.catedratico;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * <p>Esta clase tiene como objetivo almacenar los datos que se ingresan en los
 * formularios de busquedas de catedraticos.</p>
 *
 * @author Daniel Castillo
 * @version 1.0
 */
public class DatosBusquedaCatedratico {

    @Pattern(regexp = "|[0-9]{9}", message = "{validacion.codigoCatedraticoInvalido}")
    private String codigoBusqueda;

    @Size(max = 50, message = "{validacion.caracteresMaximos}")
    private String nombreBusqueda;

    @Size(max = 50, message = "{validacion.caracteresMaximos}")
    private String apellidoBusqueda;


    public DatosBusquedaCatedratico(){
        this.codigoBusqueda = "";
        this.nombreBusqueda = "";
        this.apellidoBusqueda = "";
    }
//______________________________________________________________________________
    public String getCodigoBusqueda() {
        return codigoBusqueda;
    }
//______________________________________________________________________________
    public void setCodigoBusqueda(String codigoBusqueda) {
        this.codigoBusqueda = codigoBusqueda;
    }
//______________________________________________________________________________
    public String getNombreBusqueda() {
        return nombreBusqueda;
    }
//______________________________________________________________________________
    public void setNombreBusqueda(String nombreBusqueda) {
        this.nombreBusqueda = nombreBusqueda;
    }
//______________________________________________________________________________
    public String getApellidoBusqueda() {
        return apellidoBusqueda;
    }
//______________________________________________________________________________
    public void setApellidoBusqueda(String apellidoBusqueda) {
        this.apellidoBusqueda = apellidoBusqueda;
    }
}
