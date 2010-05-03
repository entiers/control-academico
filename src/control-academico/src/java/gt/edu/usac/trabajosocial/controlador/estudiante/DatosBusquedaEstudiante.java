/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.controlador.estudiante;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * <p>Esta clase tiene como objetivo almacenar los datos que se ingresan en los
 * formularios de busquedas de estudiantes.</p>
 * 
 * @author Daniel Castillo
 * @version 1.0
 */
public class DatosBusquedaEstudiante {

    @Pattern(regexp = "|[0-9]{9}", message = "{validacion.carneInvalido}")
    private String carneBusqueda;

    @Size(max = 50, message = "{validacion.caracteresMaximos}")
    private String nombreBusqueda;

    @Size(max = 50, message = "{validacion.caracteresMaximos}")
    private String apellidoBusqueda;


    public DatosBusquedaEstudiante(){
        this.carneBusqueda = "";
        this.nombreBusqueda = "";
        this.apellidoBusqueda = "";
    }
//______________________________________________________________________________
    public String getCarneBusqueda() {
        return carneBusqueda;
    }
//______________________________________________________________________________
    public void setCarneBusqueda(String carneBusqueda) {
        this.carneBusqueda = carneBusqueda;
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
