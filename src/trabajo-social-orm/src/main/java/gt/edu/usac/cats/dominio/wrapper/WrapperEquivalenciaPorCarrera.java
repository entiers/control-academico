/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cats.dominio.wrapper;

import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
public class WrapperEquivalenciaPorCarrera {
    
    private AsignacionEstudianteCarrera asignacionEstudianteCarreraOriginal;
    private AsignacionEstudianteCarrera asignacionEstudianteCarreraEquivalencia;

    public WrapperEquivalenciaPorCarrera() {}

    /**
     * @return the asignacionEstudianteCarreraOriginal
     */
    public AsignacionEstudianteCarrera getAsignacionEstudianteCarreraOriginal() {
        return asignacionEstudianteCarreraOriginal;
    }

    /**
     * @param asignacionEstudianteCarreraOriginal the asignacionEstudianteCarreraOriginal to set
     */
    public void setAsignacionEstudianteCarreraOriginal(AsignacionEstudianteCarrera asignacionEstudianteCarreraOriginal) {
        this.asignacionEstudianteCarreraOriginal = asignacionEstudianteCarreraOriginal;
    }

    /**
     * @return the asignacionEstudianteCarreraEquivalencia
     */
    public AsignacionEstudianteCarrera getAsignacionEstudianteCarreraEquivalencia() {
        return asignacionEstudianteCarreraEquivalencia;
    }

    /**
     * @param asignacionEstudianteCarreraEquivalencia the asignacionEstudianteCarreraEquivalencia to set
     */
    public void setAsignacionEstudianteCarreraEquivalencia(AsignacionEstudianteCarrera asignacionEstudianteCarreraEquivalencia) {
        this.asignacionEstudianteCarreraEquivalencia = asignacionEstudianteCarreraEquivalencia;
    }

    
    
}
