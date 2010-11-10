/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.dominio.busqueda;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>Contiene los atributos de busqueda de cursos. Se utiliza en la pagina de
 * <code>buscarCurso.htm</code>.</p>
 *
 * <p>La busqueda de cursos se realiza solo por el codigo que tiene asignado.</p>
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
    /**
     * @return Codigo del curso
     */
    public String getCodigo() {
        return codigo;
    }
//______________________________________________________________________________
    /**
     * @param codigo Codigo del curso del que se va a buscar
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
