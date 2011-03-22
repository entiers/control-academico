/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.dominio.wrapper;

import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.Carrera;
import gt.edu.usac.cats.dominio.HistorialAsignacionEstudianteCarrera;
import javax.validation.Valid;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
public class WrapperAgregarAsignacionEstudianteCarrera {

    private Carrera carrera;

    
    @Valid
    private WrapperHistorialAsignacionEstudianteCarrera wrapperHistorialAsignacionEstudianteCarrera;

    public WrapperAgregarAsignacionEstudianteCarrera() {
        wrapperHistorialAsignacionEstudianteCarrera = new  WrapperHistorialAsignacionEstudianteCarrera();        
    }

    public void quitarWrapper(AsignacionEstudianteCarrera asignacionEstudianteCarrera
            , HistorialAsignacionEstudianteCarrera historialAsigancionEstudianteCarrera){
        asignacionEstudianteCarrera.setCarrera(this.getCarrera());
     
        wrapperHistorialAsignacionEstudianteCarrera.quitarWrapper(historialAsigancionEstudianteCarrera);

    }

    /**
     * @return the carrera
     */
    public Carrera getCarrera() {
        return carrera;
    }

    /**
     * @param carrera the carrera to set
     */
    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }


    /**
     * @return the wrapperHistorialAsignacionEstudianteCarrera
     */
    public WrapperHistorialAsignacionEstudianteCarrera getWrapperHistorialAsignacionEstudianteCarrera() {
        return wrapperHistorialAsignacionEstudianteCarrera;
    }

    /**
     * @param wrapperHorarioAsignacionEstudianteCarrera the wrapperHistorialAsignacionEstudianteCarrera to set
     */
    public void setWrapperHistorialAsignacionEstudianteCarrera(WrapperHistorialAsignacionEstudianteCarrera wrapperHistorialAsignacionEstudianteCarrera) {
        this.wrapperHistorialAsignacionEstudianteCarrera = wrapperHistorialAsignacionEstudianteCarrera;
    }


}
