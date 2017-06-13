/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.dominio.wrapper;

import gt.edu.usac.cats.dominio.Persona;
import gt.edu.usac.cats.dominio.Usuario;
import java.io.Serializable;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
*
 * Contiene los atributos de la persona que seran ingresados
 * o actualizado a la BD. El wrapper se utiliza en las paginas de
 * <code>agregarPersona.htm</code> y <code>editarPersona.htm</code>.
 * 
 * @author Mario Batres
 * @version 1.0
 */
public class WrapperPersona implements Serializable{
    @NotEmpty(message="{validacion.campoObligatorio}")
    @Size(max = 100, message = "{validacion.caracteresMaximos}")
     private String nombre;
    
    @Size(max = 15, message = "{validacion.caracteresMaximos}")
     private String registroPersonal;
    
        @Size(max = 13, message = "{validacion.caracteresMaximos}")
     private String cui;
    
    @NotEmpty(message="{validacion.campoObligatorio}")
    @Size(max = 200, message = "{validacion.caracteresMaximos}")
     private String direccion;


    @NotEmpty(message="{validacion.campoObligatorio}")
    @Pattern(regexp = "|[0-9]{8}", message = "{validacion.telefonoInvalido}")
     private String telefono;


    @Pattern(regexp = "|[0-9]{8}", message = "{validacion.telefonoInvalido}")
     private String celular;
    
    @NotEmpty(message="{validacion.campoObligatorio}")
    @Email(message = "{validacion.emailInvalido}")
     private String email;

     private char sexo;

     @NotEmpty(message="{validacion.campoObligatorio}")
     private String nombreUsuario;

     private boolean habilitado;

    public WrapperPersona() {
        this.nombre = "";
        this.registroPersonal = "";
        this.direccion = "";
        this.telefono = "";
        this.celular = "";
        this.email = "";
        this.sexo = 'M';
        this.nombreUsuario = "";
        this.habilitado = true;
        this.cui="";
    }

    public String getCui() {
        return cui;
    }

    public void setCui(long cui) {
        this.cui = String.valueOf(cui);
    }


//______________________________________________________________________________
    /**
     * Se agrega al wrapper la informacion del pojo de tipo {@link Persona}.
     *
     * @param curso Pojo de tipo {@link Persona}
     */
    public void agregarWrapper(Persona persona) {

        this.setNombre(persona.getNombre());
        this.setRegistroPersonal(persona.getRegistroPersonal());
        this.setDireccion(persona.getDireccion());
        this.setTelefono(persona.getTelefono());
        this.setCelular(persona.getCelular());
        this.setEmail(persona.getEmail());
        this.setSexo(persona.getSexo());
        this.setNombreUsuario(persona.getUsuario().getNombreUsuario());
        this.setHabilitado(persona.isHabilitado());
        this.setCui(persona.getCui());
        
    }

//______________________________________________________________________________
    /**
     * Se agrega la informacion del pojo de tipo {@link Persona} al wrapper.
     *
     * @param curso Pojo de tipo {@link Persona}
     */
    public void quitarWrapper(Persona persona) {
        persona.setNombre(this.getNombre());
        persona.setRegistroPersonal(this.getRegistroPersonal());
        persona.setDireccion(this.getDireccion());
        persona.setTelefono(this.getTelefono());
        persona.setCelular(this.getCelular());
        persona.setEmail(this.getEmail());
        persona.setSexo(this.getSexo());
        persona.setHabilitado(this.isHabilitado());
        persona.setCui(Long.parseLong(this.getCui()));
        if(persona.getUsuario() == null){
            Usuario usuario = new Usuario();
            usuario.setNombreUsuario(this.getNombreUsuario());
            persona.setUsuario(usuario);
        }
        else{
            persona.getUsuario().setNombreUsuario(this.getNombreUsuario());
        }

    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the registroPersonal
     */
    public String getRegistroPersonal() {
        return registroPersonal;
    }

    /**
     * @param registroPersonal the registroPersonal to set
     */
    public void setRegistroPersonal(String registroPersonal) {
        this.registroPersonal = registroPersonal;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the celular
     */
    public String getCelular() {
        return celular;
    }

    /**
     * @param celular the celular to set
     */
    public void setCelular(String celular) {
        this.celular = celular;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the sexo
     */
    public char getSexo() {
        return sexo;
    }

    /**
     * @param sexo the sexo to set
     */
    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    /**
     * @return the nombreUsuario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * @param nombreUsuario the nombreUsuario to set
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * @return the habilitado
     */
    public boolean isHabilitado() {
        return habilitado;
    }

    /**
     * @param habilitado the habilitado to set
     */
    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

}
