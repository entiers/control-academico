
package gt.edu.usac.cats.dominio.wrapper;

import gt.edu.usac.cats.dominio.Curso;
import java.io.Serializable;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Contiene los atributos del Curso que seran ingresados
 * o actualizado a la BD. El wrapper se utiliza en las paginas de
 * <code>agregarCurso.htm</code> y <code>editarCurso.htm</code>.
 *
 * @author Mario Batres
 * @version 2.0
 */
public class WrapperCurso implements Serializable {
//______________________________________________________________________________
    @NotEmpty(message = "{validacion.campoObligatorio}")
    @Size(max = 15, message = "{validacion.caracteresMaximos}")
    private String codigo;
//______________________________________________________________________________
    @NotEmpty(message = "{validacion.campoObligatorio}")
    @Size(max = 100, message = "{validacion.caracteresMaximos}")
    private String nombre;
//______________________________________________________________________________
    /**
     * Constructor del wrapper, se inicializan los atributos a mostrar en las
     * paginas de <code>agregarCurso.htm</code> y <code>editarCurso.htm</code>.
     */
    public WrapperCurso() {
        this.codigo = "";
        this.nombre = "";

    }

//______________________________________________________________________________
    /**
     * Se agrega al wrapper la informacion del pojo de tipo {@link Curso}.
     *
     * @param curso Pojo de tipo {@link Curso}
     */
    public void agregarWrapper(Curso curso) {
        this.setCodigo(curso.getCodigo());        
        this.setNombre(curso.getNombre());
        
    }

//______________________________________________________________________________
    /**
     * Se agrega la informacion del pojo de tipo {@link Curso} al wrapper.
     *
     * @param curso Pojo de tipo {@link Curso}
     */
    public void quitarWrapper(Curso curso) {
        curso.setCodigo(this.getCodigo());        
        curso.setNombre(this.getNombre());        
    }

//______________________________________________________________________________
    /**
     * @return El codigo del curso
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo El codigo del curso
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
//______________________________________________________________________________
    /**
     * @return El nombre del curso
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre El nombre del curso
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



}
