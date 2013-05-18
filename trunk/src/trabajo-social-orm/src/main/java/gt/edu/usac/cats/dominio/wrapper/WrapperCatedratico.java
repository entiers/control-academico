/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.dominio.wrapper;

//import gt.edu.usac.trabajosocial.anotacion.CodigoCatedraticoValidador;
import gt.edu.usac.cats.dominio.Catedratico;
import java.io.Serializable;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Daniel Castillo
 * @version 1.0
 */
public class WrapperCatedratico implements Serializable{

    @NotEmpty(message = "{validacion.campoObligatorio}")
    @Pattern(regexp = "|[0-9]{9}", message = "{validacion.codigoCatedraticoInvalido}")
//    @CodigoCatedraticoValidador
    private String codigo;
//______________________________________________________________________________
    @NotEmpty(message = "{validacion.campoObligatorio}")
    @Size(max = 50, message = "{validacion.caracteresMaximos}")
    private String nombre;
//______________________________________________________________________________
    @NotEmpty(message = "{validacion.campoObligatorio}")
    @Size(max = 50, message = "{validacion.caracteresMaximos}")
    private String apellido;
//______________________________________________________________________________
    @NotEmpty(message = "{validacion.campoObligatorio}")
    @Size(max = 100, message = "{validacion.caracteresMaximos}")
    private String profesion;
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
    @Min(value = 1, message = "{validacion.seleccion}")
    private short idEscuela;
//______________________________________________________________________________
    public WrapperCatedratico() {}
//______________________________________________________________________________
    public void agregarWrapper(Catedratico catedratico) {
        this.setCodigo(catedratico.getCodigo());
        this.setNombre(catedratico.getNombre());
        this.setApellido(catedratico.getApellido());
        this.setProfesion(catedratico.getProfesion());
        this.setDireccion(catedratico.getDireccion());
        this.setTelefono(catedratico.getTelefono());
        this.setCelular(catedratico.getCelular());
        this.setEmail(catedratico.getEmail());
    }
//______________________________________________________________________________
    public void quitarWrapper(Catedratico catedratico) {
        catedratico.setCodigo(this.getCodigo());
        catedratico.setNombre(this.getNombre());
        catedratico.setApellido(this.getApellido());
        catedratico.setProfesion(this.getProfesion());
        catedratico.setDireccion(this.getDireccion());
        catedratico.setTelefono(this.getTelefono());
        catedratico.setCelular(this.getCelular());
        catedratico.setEmail(this.getEmail());
    }
//______________________________________________________________________________
    public String getCodigo() {
        return codigo;
    }
//______________________________________________________________________________
    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
    public String getProfesion() {
        return profesion;
    }
//______________________________________________________________________________
    public void setProfesion(String profesion) {
        this.profesion = profesion;
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
    public short getIdEscuela() {
        return idEscuela;
    }
//______________________________________________________________________________
    public void setIdEscuela(short idEscuela) {
        this.idEscuela = idEscuela;
    }
}
