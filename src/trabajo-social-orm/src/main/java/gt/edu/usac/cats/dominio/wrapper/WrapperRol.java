/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.dominio.wrapper;

import gt.edu.usac.cats.dominio.Rol;
import javax.validation.constraints.Size;

/**
 * Contiene los atributos del Rol que seran ingresados
 * o actualizado a la BD. El wrapper se utiliza en las paginas de
 * <code>editarRol.htm</code>.
 *
 * @author Mario Batres
 * @version 1.0
 */
public class WrapperRol {
    private String nombre;

    @Size(max = 250, message = "{validacion.caracteresMaximos}")
    private String descripcion;
//______________________________________________________________________________
    public WrapperRol() {
        this.descripcion = "";
        this.nombre = "";
    }

//______________________________________________________________________________
    /**
     * Se agrega al wrapper la informacion del pojo de tipo {@link Rol}.
     *
     * @param rol Pojo de tipo {@link Rol}
     */
    public void agregarWrapper(Rol rol){
        this.setNombre(rol.getNombre());
        this.setDescripcion(rol.getDescripcion());
    }

//______________________________________________________________________________
    /**
     * Se agrega la informacion del pojo de tipo {@link Rol} al wrapper.
     *
     * @param rol Pojo de tipo {@link Rol}
     */
    public void quitarWrapper(Rol rol){
        rol.setNombre(this.getNombre());
        rol.setDescripcion(this.descripcion);
    }

    public String getNombre() {
        return nombre;
    }

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
