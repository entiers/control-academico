/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */
package gt.edu.usac.cats.dominio.wrapper;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Carlos Solórzano
 *
 * @version 1.0
 */
public class WrapperDatosPersonales {
//______________________________________________________________________________
    @Size(max = 200, message = "{validacion.caracteresMaximos}")
    private String direccion;
//______________________________________________________________________________
    @Pattern(regexp = "|[0-9]{8}", message = "{validacion.telefonoInvalido}")
    private String telefono;
//______________________________________________________________________________
    @Pattern(regexp = "|[0-9]{8}", message = "{validacion.telefonoInvalido}")
    private String celular;
//______________________________________________________________________________
    @NotEmpty(message = "{validacion.campoObligatorio}")
    @Size(max = 100, message = "{validacion.caracteresMaximos}")
    @Email(message = "{validacion.emailInvalido}")
    private String email;
//______________________________________________________________________________

    public WrapperDatosPersonales(){}

    public String getCelular() {
        return celular;
    }
//______________________________________________________________________________
    public void setCelular(String celular) {
        this.celular = celular;
    }
//______________________________________________________________________________
    public String getDireccion() {
        return direccion;
    }
//______________________________________________________________________________
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
//______________________________________________________________________________
    public String getEmail() {
        return email;
    }
//______________________________________________________________________________
    public void setEmail(String email) {
        this.email = email;
    }
//______________________________________________________________________________
    public String getTelefono() {
        return telefono;
    }
//______________________________________________________________________________
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}