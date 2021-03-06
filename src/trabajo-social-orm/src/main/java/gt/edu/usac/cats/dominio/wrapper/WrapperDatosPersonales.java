/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */
package gt.edu.usac.cats.dominio.wrapper;

import gt.edu.usac.cats.dominio.Catedratico;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.Persona;
import java.io.Serializable;
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
public class WrapperDatosPersonales implements Serializable{
//______________________________________________________________________________
    @Size(max = 200, message = "{validacion.caracteresMaximos}")
    private String direccion;
//______________________________________________________________________________
    @Pattern(regexp = "|[0-9]{8}", message = "{validacion.telefonoInvalido}")
    private String telefono;
//______________________________________________________________________________
    @Pattern(regexp = "|[0-9]{8}", message = "{validacion.telefonoInvalido}")
    private String celular;
    
    
    @NotEmpty(message = "{validacion.campoObligatorio}")
    @Pattern(regexp = "|[0-9]{13}", message = "{validacion.telefonoInvalido}")
    private String cui;    


//______________________________________________________________________________
    @NotEmpty(message = "{validacion.campoObligatorio}")
    @Size(max = 100, message = "{validacion.caracteresMaximos}")
    @Email(message = "{validacion.emailInvalido}")
    private String email;
//______________________________________________________________________________
    private String profesion;
//______________________________________________________________________________

    public WrapperDatosPersonales(){
        this.direccion ="";
        this.telefono="";
        this.celular="";
        this.email="";
        this.profesion="";
        this.cui="";
    }

    public void agregarWrapper(Estudiante estudiante){
        this.setDireccion(estudiante.getDireccion());
        this.setTelefono(estudiante.getTelefono());
        this.setCelular(estudiante.getCelular());
        this.setEmail(estudiante.getEmail());
   //     this.setCui(String.valueOf(estudiante.getCui()));
        
    }

    public void agregarWrapper(Catedratico catedratico){
        this.setDireccion(catedratico.getDireccion());
        this.setTelefono(catedratico.getTelefono());
        this.setCelular(catedratico.getCelular());
        this.setEmail(catedratico.getEmail());
        this.setProfesion(catedratico.getProfesion());
        this.setCui(String.valueOf(catedratico.getCui()));
    }

    public void agregarWrapper(Persona persona){
        this.setDireccion(persona.getDireccion());
        this.setTelefono(persona.getTelefono());
        this.setCelular(persona.getCelular());
        this.setEmail(persona.getEmail());
        this.setCui(String.valueOf(persona.getCui()));
    }

    public void quitarWrapper(Estudiante estudiante){
        estudiante.setDireccion(this.getDireccion());
        estudiante.setTelefono(this.getTelefono());
        estudiante.setCelular(this.getCelular());
        estudiante.setEmail(this.getEmail());
     //   estudiante.setCui(Long.parseLong(this.getCui()));
    }

    public void quitarWrapper(Catedratico catedratico){
        catedratico.setDireccion(this.getDireccion());
        catedratico.setTelefono(this.getTelefono());
        catedratico.setCelular(this.getCelular());
        catedratico.setEmail(this.getEmail());
        catedratico.setProfesion(this.getProfesion());
        catedratico.setCui(Long.parseLong(this.getCui()));
    }

    public void quitarWrapper(Persona persona){
        persona.setDireccion(this.getDireccion());
        persona.setTelefono(this.getTelefono());
        persona.setCelular(this.getCelular());
        persona.setEmail(this.getEmail());
        persona.setCui(Long.parseLong(this.getCui()));
    }

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
//______________________________________________________________________________
    public String getProfesion() {
        return profesion;
    }
//______________________________________________________________________________
    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }
    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }    
}