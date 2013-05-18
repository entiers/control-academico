/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.dominio.busqueda;

import java.io.Serializable;
import javax.validation.constraints.Size;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
public class DatosBusquedaPersona implements Serializable {
    @Size(max = 100, message = "{validacion.caracteresMaximos}")
    private String registroPersonal;

    @Size(max = 15, message = "{validacion.caracteresMaximos}")
    private String nombre;

    private boolean habilitado;

    public DatosBusquedaPersona() {
        this.registroPersonal = "";
        this.nombre = "";
        this.habilitado = true;
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

    public boolean esValido(){
        if(this.registroPersonal.isEmpty() && this.nombre.isEmpty()){
            return false;
        }
        return true;
    }

}
