/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.trabajosocial.dominio.wrapper;

import gt.edu.usac.trabajosocial.dominio.TipoAsignacion;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * Contiene los atributos del Tipo de Asignacion que seran ingresados
 * o actualizado a la BD. El wrapper se utiliza en las paginas de
 * <code>agregarTipoAsignacion.htm</code> y <code>editarTipoAsignacion.htm</code>.
 * @author Mario Batres
 * @version 1.0
 */

public class WrapperTipoAsignacion {
//______________________________________________________________________________
    private String descripcion;
//______________________________________________________________________________
    @NotEmpty(message="{validacion.campoObligatorio}")
    @Size(max = 100, message = "{validacion.caracteresMaximos}")
    private String nombre;
//______________________________________________________________________________
    private boolean habilitado;
//______________________________________________________________________________
    /**
     * Constructor del Tipo de Asignacion, se inicializan los atributos a mostrar en las
     * paginas de <code>agregarTipoAsignacion.htm</code> y <code>editarTipoAsignacion.htm</code>.
     */
    public WrapperTipoAsignacion() {
        this.descripcion = "";
        this.habilitado = true;
        this.nombre = "";
    }
//______________________________________________________________________________
    /**
     * Se agrega al wrapper la informacion del pojo de tipo {@link TipoAsignacion}.
     *
     * @param tipoAsignacion Pojo de tipo {@link TipoAsignacion}
     */
    public void agregarWrapper(TipoAsignacion tipoAsignacion) {
        this.descripcion = tipoAsignacion.getDescripcion();
        this.nombre = tipoAsignacion.getNombre();
        this.habilitado = tipoAsignacion.isHabilitado();
    }

//______________________________________________________________________________
    /**
     * Se agrega la informacion del pojo de tipo {@link TipoAsignacion} al wrapper.
     *
     * @param tipoAsignacion Pojo de tipo {@link TipoAsignacion}
     */
    public void quitarWrapper(TipoAsignacion tipoAsignacion) {
        tipoAsignacion.setDescripcion(descripcion);
        tipoAsignacion.setNombre(nombre);
        tipoAsignacion.setHabilitado(habilitado);
    }
//______________________________________________________________________________

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
//______________________________________________________________________________
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
//______________________________________________________________________________
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
