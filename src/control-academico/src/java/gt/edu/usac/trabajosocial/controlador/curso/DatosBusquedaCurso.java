/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.trabajosocial.controlador.curso;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
public class DatosBusquedaCurso {
//______________________________________________________________________________
    @NotEmpty(message = "{validacion.campoObligatorio}")
    @Size(max = 15, message = "{validacion.caracteresMaximos}")
    private String codigo;

//______________________________________________________________________________
    public DatosBusquedaCurso() {
        this.codigo = "";
    }
    
//______________________________________________________________________________
    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }




}
