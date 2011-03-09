/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cats.dominio.wrapper;

import gt.edu.usac.cats.dominio.AsignacionCursoPensum;
import gt.edu.usac.cats.dominio.Curso;
import gt.edu.usac.cats.dominio.Pensum;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Contiene los atributos de {@link AsignacionCursoPensum} que seran ingresados
 * o actualizado a la BD.
 *
 * @author Mario Batres
 * @version 1.0
 */
public class WrapperAsignacionCursoPensum {

    private Curso curso;
    private Pensum pensum;
    private boolean obligatorio;
    @Min(value = 0, message = "{validacion.minimo}")
    private Short creditosPracticos;
    @Min(value = 0, message = "{validacion.minimo}")
    private Short creditosPrerrequisito;
    @Min(value = 0, message = "{validacion.minimo}")
    private Short creditosTeoricos;
    @Min(value = 0, message = "{validacion.minimo}")
    @Max(value = 10, message = "{validacion.maximo}")
    private short numeroSemestre;

    public WrapperAsignacionCursoPensum() {
        this.obligatorio = true;
        this.numeroSemestre = (short) 0;
        this.creditosPracticos = (short) 0;
        this.creditosPrerrequisito = (short) 0;
        this.creditosTeoricos = (short) 0;
    }
//______________________________________________________________________________

    /**
     * Se agrega al wrapper la informacion del pojo de tipo {@link AsignacionCursoPensum}.
     *
     * @param asignacionCursoPensumPojo de tipo {@link AsignacionCursoPensum}
     */
    public void agregarWrapper(AsignacionCursoPensum asignacionCursoPensum) {
        this.setCreditosPracticos(asignacionCursoPensum.getCreditosPracticos());
        this.setCreditosPrerrequisito(asignacionCursoPensum.getCreditosPrerrequisito());
        this.setCreditosTeoricos(asignacionCursoPensum.getCreditosTeoricos());
        this.setCurso(asignacionCursoPensum.getCurso());
        this.setPensum(asignacionCursoPensum.getPensum());
        this.setNumeroSemestre(asignacionCursoPensum.getNumeroSemestre());
        this.setObligatorio(asignacionCursoPensum.isObligatorio());
    }

//______________________________________________________________________________
    /**
     * Se agrega la informacion del pojo de tipo {@link AsignacionCursoPensum} al wrapper.
     *
     * @param horario Pojo de tipo {@link Horario}
     * @param quitar Sirve para quitar el curso y el pensum del wrapper y pasarlo
     * al original.
     */
    public void quitarWrapper(AsignacionCursoPensum asignacionCursoPensum, boolean quitar) {
        asignacionCursoPensum.setCreditosPracticos(this.getCreditosPracticos());
        asignacionCursoPensum.setCreditosPrerrequisito(this.getCreditosPrerrequisito());
        asignacionCursoPensum.setCreditosTeoricos(this.getCreditosTeoricos());
        asignacionCursoPensum.setNumeroSemestre(this.getNumeroSemestre());
        asignacionCursoPensum.setObligatorio(this.isObligatorio());

        if (quitar) {
            asignacionCursoPensum.setCurso(this.getCurso());
            asignacionCursoPensum.setPensum(this.getPensum());
        }
    }

//______________________________________________________________________________
    /**
     * @return the curso
     */
    public Curso getCurso() {
        return curso;
    }

    /**
     * @param curso the curso to set
     */
    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    /**
     * @return the pensum
     */
    public Pensum getPensum() {
        return pensum;
    }

    /**
     * @param pensum the pensum to set
     */
    public void setPensum(Pensum pensum) {
        this.pensum = pensum;
    }

    /**
     * @return the obligatorio
     */
    public boolean isObligatorio() {
        return obligatorio;
    }

    /**
     * @param obligatorio the obligatorio to set
     */
    public void setObligatorio(boolean obligatorio) {
        this.obligatorio = obligatorio;
    }

    /**
     * @return the creditosPracticos
     */
    public Short getCreditosPracticos() {
        return creditosPracticos;
    }

    /**
     * @param creditosPracticos the creditosPracticos to set
     */
    public void setCreditosPracticos(Short creditosPracticos) {
        this.creditosPracticos = creditosPracticos;
    }

    /**
     * @return the creditosPrerrequisito
     */
    public Short getCreditosPrerrequisito() {
        return creditosPrerrequisito;
    }

    /**
     * @param creditosPrerrequisito the creditosPrerrequisito to set
     */
    public void setCreditosPrerrequisito(Short creditosPrerrequisito) {
        this.creditosPrerrequisito = creditosPrerrequisito;
    }

    /**
     * @return the creditosTeoricos
     */
    public Short getCreditosTeoricos() {
        return creditosTeoricos;
    }

    /**
     * @param creditosTeoricos the creditosTeoricos to set
     */
    public void setCreditosTeoricos(Short creditosTeoricos) {
        this.creditosTeoricos = creditosTeoricos;
    }

    /**
     * @return the numeroSemestre
     */
    public short getNumeroSemestre() {
        return numeroSemestre;
    }

    /**
     * @param numeroSemestre the numeroSemestre to set
     */
    public void setNumeroSemestre(short numeroSemestre) {
        this.numeroSemestre = numeroSemestre;
    }
}
