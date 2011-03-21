/**
 * Universidad de San Carlos de Guatemala
 * Escuela de Trabajo Social
 * Control Academico
 *
 */
package gt.edu.usac.cats.dominio.wrapper;

import gt.edu.usac.cats.dominio.Documento;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Contiene los atributos del Documento que seran ingresados
 * o actualizado a la BD. El wrapper se utiliza en las paginas de
 * <code>agregarDocumento.htm</code> y <code>editarDocumento.htm</code>.
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
public class WrapperDocumento {
//______________________________________________________________________________
    @NotEmpty(message = "{validacion.campoObligatorio}")
    @Size(max = 150, message = "{validacion.caracteresMaximos}")
    private String nombre;
//______________________________________________________________________________
    private String descripcion;
//______________________________________________________________________________
    /**
     * Constructor del wrapper, se inicializan los atributos a mostrar en las
     * paginas de <code>agregarDocumento.htm</code> y <code>editarDocumento.htm</code>.
     */
    public WrapperDocumento() {
        this.nombre = "";
        this.descripcion = "";
    }
//______________________________________________________________________________
    /**
     * Se agrega al wrapper la informacion del pojo de tipo {@link Documento}.
     *
     * @param documento Pojo de tipo {@link Documento}
     */
    public void agregarWrapper(Documento documento) {
        this.setNombre(documento.getNombre());
        this.setDescripcion(documento.getDescripcion());
    }

//______________________________________________________________________________
    /**
     * Se agrega la informacion del pojo de tipo {@link Curso} al wrapper.
     *
     * @param curso Pojo de tipo {@link Curso}
     */
    public void quitarWrapper(Documento documento) {
        documento.setNombre(this.getNombre());
        documento.setDescripcion(this.getDescripcion());
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
    public String getDescripcion() {
        return descripcion;
    }
//______________________________________________________________________________
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
