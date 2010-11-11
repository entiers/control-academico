/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.dominio.wrapper;

import gt.edu.usac.cats.dominio.Perfil;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Contiene los atributos del Perfil que seran ingresados
 * o actualizado a la BD. El wrapper se utiliza en las paginas de
 * <code>agregarPerfil.htm</code> y <code>editarPerfil.htm</code>.
 *
 * @author Mario Batres
 * @version 1.0
 */

public class WrapperPerfil {
    @NotEmpty(message = "{validacion.campoObligatorio}")
    @Size(max = 50, message = "{validacion.caracteresMaximos}")
    private String nombre;
//______________________________________________________________________________
    @Size(max = 250, message = "{validacion.caracteresMaximos}")
    private String descripcion;

    public WrapperPerfil() {
        this.nombre = "";
        this.descripcion = "";
    }
//______________________________________________________________________________
    /**
     * Se agrega al wrapper la informacion del pojo de tipo {@link Perfil}.
     *
     * @param perfil Pojo de tipo {@link Perfil}
     */
    public void agregarWrapper(Perfil perfil){
        this.setNombre(perfil.getNombre());
        this.setDescripcion(perfil.getDescripcion());
    }

//______________________________________________________________________________
    /**
     * Se agrega la informacion del pojo de tipo {@link Perfil} al wrapper.
     *
     * @param perfil Pojo de tipo {@link Perfil}
     */
    public void quitarWrapper(Perfil perfil){
        perfil.setNombre(this.getNombre());
        perfil.setDescripcion(this.getDescripcion());
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
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    
}
