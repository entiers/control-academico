/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.dominio.wrapper;

import gt.edu.usac.trabajosocial.anotacion.CarneEstudianteValidador;
import gt.edu.usac.trabajosocial.dominio.Estudiante;
import java.util.Date;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Daniel Castillo
 * @version 1.0
 */
public class WrapperEstudiante {

    @NotEmpty(message = "{validacion.campoObligatorio}")
    @Pattern(regexp = "|[0-9]{9}", message = "{validacion.carneInvalido}")
    @CarneEstudianteValidador
    private String carne;
//______________________________________________________________________________
    @NotEmpty(message = "{validacion.campoObligatorio}")
    @Size(max = 50, message = "{validacion.caracteresMaximos}")
    private String nombre;
//______________________________________________________________________________
    @NotEmpty(message = "{validacion.campoObligatorio}")
    @Size(max = 50, message = "{validacion.caracteresMaximos}")
    private String apellido;
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
    @Size(max = 100, message = "{validacion.caracteresMaximos}")
    @Email(message = "{validacion.emailInvalido}")
    private String email;
//______________________________________________________________________________
    @NotNull(message = "{validacion.campoObligatorio}")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Past(message = "{validacion.fechaPasada}")
    private Date fechaNacimiento;
//______________________________________________________________________________
    @Min(value = 1, message = "{validacion.seleccion}")
    private short idCarrera;
//______________________________________________________________________________
    private boolean requisitos;
//______________________________________________________________________________
    public WrapperEstudiante() {}
//______________________________________________________________________________
    public void agregarWrapper(Estudiante estudiante) {
        this.setCarne(estudiante.getCarne());
        this.setNombre(estudiante.getNombre());
        this.setApellido(estudiante.getApellido());
        this.setDireccion(estudiante.getDireccion());
        this.setTelefono(estudiante.getTelefono());
        this.setCelular(estudiante.getCelular());
        this.setEmail(estudiante.getEmail());
        this.setFechaNacimiento(estudiante.getFechaNacimiento());
        this.setRequisitos(estudiante.isRequisitos());
    }
//______________________________________________________________________________
    public void quitarWrapper(Estudiante estudiante) {
        estudiante.setCarne(this.getCarne());
        estudiante.setNombre(this.getNombre());
        estudiante.setApellido(this.getApellido());
        estudiante.setDireccion(this.getDireccion());
        estudiante.setTelefono(this.getTelefono());
        estudiante.setCelular(this.getCelular());
        estudiante.setEmail(this.getEmail());
        estudiante.setFechaNacimiento(this.getFechaNacimiento());
        estudiante.setRequisitos(this.isRequisitos());
    }
//______________________________________________________________________________
    public String getCarne() {
        return carne;
    }
//______________________________________________________________________________
    public void setCarne(String carne) {
        this.carne = carne;
    }
//______________________________________________________________________________
    public String getNombre() {
        return nombre;
    }
//______________________________________________________________________________
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
//______________________________________________________________________________
    public String getApellido() {
        return apellido;
    }
//______________________________________________________________________________
    public void setApellido(String apellido) {
        this.apellido = apellido;
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
    public String getTelefono() {
        return telefono;
    }
//______________________________________________________________________________
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
//______________________________________________________________________________
    public String getCelular() {
        return celular;
    }
//______________________________________________________________________________
    public void setCelular(String celular) {
        this.celular = celular;
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
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }
//______________________________________________________________________________
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
//______________________________________________________________________________
    public short getIdCarrera() {
        return idCarrera;
    }
//______________________________________________________________________________
    public void setIdCarrera(short idCarrera) {
        this.idCarrera = idCarrera;
    }
//______________________________________________________________________________
    public boolean isRequisitos() {
        return requisitos;
    }
//______________________________________________________________________________
    public void setRequisitos(boolean requisitos) {
        this.requisitos = requisitos;
    }
}
