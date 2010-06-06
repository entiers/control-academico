/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.trabajosocial.dominio.busqueda;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
public class DatosBusquedaCurso {

    @NotEmpty(message = "{validacion.campoObligatorio}")
    @Size(max = 15, message = "{validacion.caracteresMaximos}")
    private String codigo;

//______________________________________________________________________________
    public DatosBusquedaCurso() {
        this.codigo = "";
    }
//______________________________________________________________________________
    public String getCodigo() {
        return codigo;
    }
//______________________________________________________________________________
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
