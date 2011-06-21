/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.dominio.wrapper;

import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.Pensum;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
public class WrapperEquivalenciaPorPensum {
    private AsignacionEstudianteCarrera asignacionEstudianteCarrera;
    private Pensum pensumOriginal;
    private Pensum pensumEquivalencia;

    public WrapperEquivalenciaPorPensum() {}


    public WrapperEquivalenciaPorPensum(AsignacionEstudianteCarrera asignacionEstudianteCarrera,
            Pensum pensumEquivalencia) {

        this.asignacionEstudianteCarrera = asignacionEstudianteCarrera;
        this.pensumEquivalencia = pensumEquivalencia;
    }

    
    /**
     * @return the asignacionEstudianteCarrera
     */
    public AsignacionEstudianteCarrera getAsignacionEstudianteCarrera() {
        return asignacionEstudianteCarrera;
    }

    /**
     * @param asignacionEstudianteCarrera the asignacionEstudianteCarrera to set
     */
    public void setAsignacionEstudianteCarrera(AsignacionEstudianteCarrera asignacionEstudianteCarrera) {
        this.asignacionEstudianteCarrera = asignacionEstudianteCarrera;
    }

    /**
     * @return the pensumOriginal
     */
    public Pensum getPensumOriginal() {
        return pensumOriginal;
    }

    /**
     * @param pensumOriginal the pensumOriginal to set
     */
    public void setPensumOriginal(Pensum pensumOriginal) {
        this.pensumOriginal = pensumOriginal;
    }

    /**
     * @return the pensumEquivalencia
     */
    public Pensum getPensumEquivalencia() {
        return pensumEquivalencia;
    }

    /**
     * @param pensumEquivalencia the pensumEquivalencia to set
     */
    public void setPensumEquivalencia(Pensum pensumEquivalencia) {
        this.pensumEquivalencia = pensumEquivalencia;
    }

    
}
