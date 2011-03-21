/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.dominio.busqueda;

import javax.validation.constraints.Size;

/**
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
public class DatosBusquedaDocumento {
    @Size(max = 150, message = "{validacion.caracteresMaximos}")
    private String nombre;

    public DatosBusquedaDocumento() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean esValido(){
        if(this.nombre.isEmpty()){
            return false;
        }
        return true;
    }
}
