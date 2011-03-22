/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cats.dominio.wrapper;

import gt.edu.usac.cats.dominio.HistorialAsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.dominio.Situacion;
import java.util.Date;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
public class WrapperHistorialAsignacionEstudianteCarrera {

    @NotNull(message = "{validacion.campoObligatorio}")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date fechaInscripcion;

    private Situacion situacion;

    private Semestre semestre;

    public WrapperHistorialAsignacionEstudianteCarrera() {
        this.fechaInscripcion = new Date();
    }

    public void agregarWrapper(HistorialAsignacionEstudianteCarrera historialAsignacionEstudianteCarrera) {
        this.setFechaInscripcion(historialAsignacionEstudianteCarrera.getFechaInscripcion());
        this.setSituacion(historialAsignacionEstudianteCarrera.getSituacion());
        this.setSemestre(historialAsignacionEstudianteCarrera.getSemestre());

    }

    public void quitarWrapper(HistorialAsignacionEstudianteCarrera historialAsignacionEstudianteCarrera) {
        historialAsignacionEstudianteCarrera.setFechaInscripcion(this.getFechaInscripcion());
        historialAsignacionEstudianteCarrera.setSituacion(this.getSituacion());
        historialAsignacionEstudianteCarrera.setSemestre(this.getSemestre());
    }

    /**
     * @return the fechaIncripcion
     */
    public Date getFechaInscripcion() {
        return fechaInscripcion;
    }

    /**
     * @param fechaIncripcion the fechaIncripcion to set
     */
    public void setFechaInscripcion(Date fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    /**
     * @return the situacion
     */
    public Situacion getSituacion() {
        return situacion;
    }

    /**
     * @param situacion the situacion to set
     */
    public void setSituacion(Situacion situacion) {
        this.situacion = situacion;
    }

    /**
     * @return the semestre
     */
    public Semestre getSemestre() {
        return semestre;
    }

    /**
     * @param semestre the semestre to set
     */
    public void setSemestre(Semestre semestre) {
        this.semestre = semestre;
    }
}
