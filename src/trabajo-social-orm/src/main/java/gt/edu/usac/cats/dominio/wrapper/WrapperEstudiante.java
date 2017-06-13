/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.dominio.wrapper;

//import gt.edu.usac.trabajosocial.anotacion.CarneEstudianteValidador;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.LugarNacimiento;
import gt.edu.usac.cats.dominio.Nacionalidad;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Daniel Castillo y Mario Batres
 *
 * @version 3.0
 */
public class WrapperEstudiante implements Serializable{

    @NotEmpty(message = "{validacion.campoObligatorio}")
    @Pattern(regexp = "|[0-9]{5,9}", message = "{validacion.carneInvalido}")
  //  @CarneEstudianteValidador
    private String carne;
    
    @NotEmpty(message = "{validacion.campoObligatorio}")
    @Size(max = 13, message = "{validacion.caracteresMaximos}")
    private String cui;
//______________________________________________________________________________
    @NotEmpty(message = "{validacion.campoObligatorio}")
    @Size(max = 100, message = "{validacion.caracteresMaximos}")
    private String nombre;
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
    @NotNull(message = "{validacion.campoObligatorio}")
 //   @DateTimeFormat(pattern = "dd-MM-yyyy")
 //   @Past(message = "{validacion.fechaPasada}")
    private String fechaNacimiento;
//______________________________________________________________________________
    /*@Min(value = 1, message = "{validacion.seleccion}")
    private short idCarrera;*/
//______________________________________________________________________________
    private boolean requisitos;
//______________________________________________________________________________
   @NotNull(message = "{validacion.campoObligatorio}")    
    private char sexo;
//______________________________________________________________________________
    private LugarNacimiento lugarNacimiento;
//______________________________________________________________________________
    private Nacionalidad nacionalidad;
//______________________________________________________________________________
    private String carneModificado;
//______________________________________________________________________________
    private String nov;
//______________________________________________________________________________
    private boolean inscrito;
//______________________________________________________________________________

    public WrapperEstudiante() {
        this.sexo = 'M';
        this.inscrito = true;
    }
    
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//______________________________________________________________________________
    public void agregarWrapper(Estudiante estudiante) {

        this.setCarne(estudiante.getCarne());
        this.setNombre(estudiante.getNombre());        
        this.setDireccion(estudiante.getDireccion());
        this.setTelefono(estudiante.getTelefono());
        this.setCelular(estudiante.getCelular());
        this.setEmail(estudiante.getEmail());
        this.setFechaNacimiento(estudiante.getFechaNacimiento() == null? "":sdf.format(estudiante.getFechaNacimiento()));
        this.setRequisitos(estudiante.isRequisitos());
        this.setSexo(estudiante.getSexo());
        this.setLugarNacimiento(estudiante.getLugarNacimiento());
        this.setNacionalidad(estudiante.getNacionalidad());
        this.setCarneModificado(estudiante.getCarneModificado());
        this.setNov(estudiante.getNov());
       this.setCui(estudiante.getCui().toString());


    }
//______________________________________________________________________________
    public void quitarWrapper(Estudiante estudiante) {
        
        estudiante.setCarne(this.getCarne());
        estudiante.setNombre(this.getNombre());        
        estudiante.setDireccion(this.getDireccion());
        estudiante.setTelefono(this.getTelefono());
        estudiante.setCelular(this.getCelular());
        estudiante.setEmail(this.getEmail());
        try {
            estudiante.setFechaNacimiento(sdf.parse(this.getFechaNacimiento()));
        } catch (ParseException ex) {
            Logger.getLogger(WrapperEstudiante.class.getName()).log(Level.SEVERE, null, ex);
        }
        estudiante.setRequisitos(this.isRequisitos());
        estudiante.setSexo(this.getSexo());
        estudiante.setLugarNacimiento(this.getLugarNacimiento());
        estudiante.setNacionalidad(this.getNacionalidad());
        estudiante.setCarneModificado(this.getCarneModificado());
        estudiante.setNov(this.getNov());        
       estudiante.setCui(Long.parseLong(this.getCui()));
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
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }
//______________________________________________________________________________
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    /*
//______________________________________________________________________________
    public short getIdCarrera() {
        return idCarrera;
    }
//______________________________________________________________________________
    public void setIdCarrera(short idCarrera) {
        this.idCarrera = idCarrera;
    }*/
//______________________________________________________________________________
    public boolean isRequisitos() {
        return requisitos;
    }
//______________________________________________________________________________
    public void setRequisitos(boolean requisitos) {
        this.requisitos = requisitos;
    }
//______________________________________________________________________________
    public char getSexo() {
        return sexo;
    }
    
    public void setSexo(char sexo) {
        this.sexo = sexo;
    }
//______________________________________________________________________________    
    public LugarNacimiento getLugarNacimiento() {
        return lugarNacimiento;
    }

    public void setLugarNacimiento(LugarNacimiento lugarNacimiento) {
        this.lugarNacimiento = lugarNacimiento;
    }
//______________________________________________________________________________
    public Nacionalidad getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(Nacionalidad nacionalidad) {
        this.nacionalidad = nacionalidad;
    }
//______________________________________________________________________________
    public String getCarneModificado() {
        return carneModificado;
    }

    public void setCarneModificado(String carneModificado) {
        this.carneModificado = carneModificado;
    }
//______________________________________________________________________________
    public String getNov() {
        return nov;
    }

    public void setNov(String nov) {
        this.nov = nov;
    }    

    /**
     * @return the cui
     */
    public String getCui() {
        return cui;
    }

    /**
     * @param cui the cui to set
     */
    public void setCui(String cui) {
        this.cui = cui;
    }
}
