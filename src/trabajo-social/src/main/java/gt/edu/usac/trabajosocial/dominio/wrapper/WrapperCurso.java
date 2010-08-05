
package gt.edu.usac.trabajosocial.dominio.wrapper;

import gt.edu.usac.trabajosocial.dominio.Curso;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Contiene los atributos del Curso que seran ingresados
 * o actualizado a la BD. El wrapper se utiliza en las paginas de
 * <code>agregarCurso.htm</code> y <code>editarCurso.htm</code>.
 *
 * @author Mario Batres
 * @version 1.0
 */
public class WrapperCurso {
//______________________________________________________________________________
    @NotEmpty(message = "{validacion.campoObligatorio}")
    @Size(max = 15, message = "{validacion.caracteresMaximos}")
    private String codigo;
//______________________________________________________________________________
    @Min(value = 1, message = "{validacion.minimo}")
    private short creditosPracticos;
//______________________________________________________________________________
    @Min(value = 1, message = "{validacion.minimo}")
    private short creditosPrerrequisito;
//______________________________________________________________________________
    @Min(value = 1, message = "{validacion.minimo}")
    private short creditosTeoricos;
//______________________________________________________________________________
    @NotEmpty(message = "{validacion.campoObligatorio}")
    @Size(max = 50, message = "{validacion.caracteresMaximos}")
    private String nombre;
//______________________________________________________________________________
    @Min(value = 1, message = "{validacion.minimo}")
    @Max(value = 10, message = "{validacion.maximo}")
    private short semestre;
//______________________________________________________________________________
    /**
     * Constructor del wrapper, se inicializan los atributos a mostrar en las
     * paginas de <code>agregarCurso.htm</code> y <code>editarCurso.htm</code>.
     */
    public WrapperCurso() {
        this.setCodigo("");
        this.setCreditosPracticos((short) 1);
        this.setCreditosPrerrequisito((short) 1);
        this.setCreditosTeoricos((short) 1);
        this.setNombre("");
        this.setSemestre((short) 1);
    }

//______________________________________________________________________________
    /**
     * Se agrega al wrapper la informacion del pojo de tipo {@link Curso}.
     *
     * @param curso Pojo de tipo {@link Curso}
     */
    public void agregarWrapper(Curso curso) {
        this.setCodigo(curso.getCodigo());
        this.setCreditosPracticos(curso.getCreditosPracticos());
        this.setCreditosPrerrequisito(curso.getCreditosPrerrequisito());
        this.setCreditosTeoricos(curso.getCreditosTeoricos());
        this.setNombre(curso.getNombre());
        this.setSemestre(curso.getSemestre());
    }

//______________________________________________________________________________
    /**
     * Se agrega la informacion del pojo de tipo {@link Curso} al wrapper.
     *
     * @param curso Pojo de tipo {@link Curso}
     */
    public void quitarWrapper(Curso curso) {
        curso.setCodigo(this.getCodigo());
        curso.setCreditosPracticos(this.getCreditosPracticos());
        curso.setCreditosPrerrequisito(this.getCreditosPrerrequisito());
        curso.setCreditosTeoricos(this.getCreditosTeoricos());
        curso.setNombre(this.getNombre());
        curso.setSemestre(this.getSemestre());
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
     * @return Los creditos practicos del curso.
     */
    public short getCreditosPracticos() {
        return creditosPracticos;
    }

    /**
     * @param creditosPracticos Los creditos practicos del curso.
     */
    public void setCreditosPracticos(short creditosPracticos) {
        this.creditosPracticos = creditosPracticos;
    }

//______________________________________________________________________________
    /**
     * @return Los creditos que sirven como prerrequisito del curso
     */
    public short getCreditosPrerrequisito() {
        return creditosPrerrequisito;
    }

    /**
     * @param creditosPrerrequisito Los creditos que sirven como prerrequisito
     * del curso
     */
    public void setCreditosPrerrequisito(short creditosPrerrequisito) {
        this.creditosPrerrequisito = creditosPrerrequisito;
    }

//______________________________________________________________________________
    /**
     * @return Los creditos teoricos del curso.
     */
    public short getCreditosTeoricos() {
        return creditosTeoricos;
    }

    /**
     * @param creditosTeoricos Los creditos teoricos del curso.
     */
    public void setCreditosTeoricos(short creditosTeoricos) {
        this.creditosTeoricos = creditosTeoricos;
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

//______________________________________________________________________________
    /**
     * @return pojo de tipo {@link Semestre}
     */
    public short getSemestre() {
        return semestre;
    }

    /**
     * @param semestre pojo de tipo {@link Semestre}
     */
    public void setSemestre(short semestre) {
        this.semestre = semestre;
    }

}
