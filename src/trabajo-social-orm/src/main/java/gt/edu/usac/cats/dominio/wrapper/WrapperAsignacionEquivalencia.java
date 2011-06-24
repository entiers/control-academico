/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cats.dominio.wrapper;

import gt.edu.usac.cats.dominio.AsignacionEquivalencia;
import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
public class WrapperAsignacionEquivalencia {

    @NotEmpty(message = "{validacion.campoObligatorio}")
    @Size(max = 15, message = "{validacion.caracteresMaximos}")
    private String acuerdoNumero;

    @Size(max = 200, message = "{validacion.caracteresMaximos}")
    private String observaciones;

    private AsignacionEstudianteCarrera asignacionEstudianteCarrera;

    public WrapperAsignacionEquivalencia() {
        this.acuerdoNumero = "";
        this.observaciones = "";
    }

    public WrapperAsignacionEquivalencia(AsignacionEstudianteCarrera asignacionEstudianteCarrera) {
        this.acuerdoNumero = "";
        this.observaciones = "";
        this.asignacionEstudianteCarrera = asignacionEstudianteCarrera;
    }

    public void agregarWrapper(AsignacionEquivalencia asignacionEquivalencia) {
        this.setAcuerdoNumero(asignacionEquivalencia.getAcuerdoNumero());
        this.setObservaciones(asignacionEquivalencia.getObservaciones());
    }

    public void quitarWrapper(AsignacionEquivalencia asignacionEquivalencia){
        asignacionEquivalencia.setAcuerdoNumero(this.getAcuerdoNumero());
        asignacionEquivalencia.setObservaciones(this.getObservaciones());
    }
    

    /**
     * @return the acuerdoNumero
     */
    public String getAcuerdoNumero() {
        return acuerdoNumero;
    }

    /**
     * @param acuerdoNumero the acuerdoNumero to set
     */
    public void setAcuerdoNumero(String acuerdoNumero) {
        this.acuerdoNumero = acuerdoNumero;
    }

    /**
     * @return the observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * @param observaciones the observaciones to set
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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
    
}
