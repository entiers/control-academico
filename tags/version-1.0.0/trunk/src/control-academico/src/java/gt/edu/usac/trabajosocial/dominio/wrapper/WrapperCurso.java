
package gt.edu.usac.trabajosocial.dominio.wrapper;

import gt.edu.usac.trabajosocial.dominio.Curso;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

/**
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
    public WrapperCurso() {
        this.setCodigo("");
        this.setCreditosPracticos((short) 1);
        this.setCreditosPrerrequisito((short) 1);
        this.setCreditosTeoricos((short) 1);
        this.setNombre("");
        this.setSemestre((short) 1);
    }

//______________________________________________________________________________
    public void agregarWrapper(Curso curso) {
        this.setCodigo(curso.getCodigo());
        this.setCreditosPracticos(curso.getCreditosPracticos());
        this.setCreditosPrerrequisito(curso.getCreditosPrerrequisito());
        this.setCreditosTeoricos(curso.getCreditosTeoricos());
        this.setNombre(curso.getNombre());
        this.setSemestre(curso.getSemestre());
    }

//______________________________________________________________________________
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

//______________________________________________________________________________
    /**
     * @return the creditosPracticos
     */
    public short getCreditosPracticos() {
        return creditosPracticos;
    }

    /**
     * @param creditosPracticos the creditosPracticos to set
     */
    public void setCreditosPracticos(short creditosPracticos) {
        this.creditosPracticos = creditosPracticos;
    }

//______________________________________________________________________________
    /**
     * @return the creditosPrerrequisito
     */
    public short getCreditosPrerrequisito() {
        return creditosPrerrequisito;
    }

    /**
     * @param creditosPrerrequisito the creditosPrerrequisito to set
     */
    public void setCreditosPrerrequisito(short creditosPrerrequisito) {
        this.creditosPrerrequisito = creditosPrerrequisito;
    }

//______________________________________________________________________________
    /**
     * @return the creditosTeoricos
     */
    public short getCreditosTeoricos() {
        return creditosTeoricos;
    }

    /**
     * @param creditosTeoricos the creditosTeoricos to set
     */
    public void setCreditosTeoricos(short creditosTeoricos) {
        this.creditosTeoricos = creditosTeoricos;
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
     * @return the semestre
     */
    public short getSemestre() {
        return semestre;
    }

    /**
     * @param semestre the semestre to set
     */
    public void setSemestre(short semestre) {
        this.semestre = semestre;
    }

}
