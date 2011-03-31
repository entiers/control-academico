/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.dominio.wrapper;

import gt.edu.usac.cats.dominio.HistorialAsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.dominio.Situacion;
import java.util.Date;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Contiene los atributos necesarios y sus validaciones agegar un objeto de tipo
 * {@link HistorialAsignacionEstudianteCarrera} desde las vistas y controladores.
 * Este objeto tambi&eacute;n es utilizando en {@link WrapperAsignacionEstudianteCarrera}.
 *
 * @author Mario Batres
 * @version 1.0
 */
public class WrapperHistorialAsignacionEstudianteCarrera {
//______________________________________________________________________________
    @NotNull(message = "{validacion.campoObligatorio}")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date fechaInscripcion;

//______________________________________________________________________________
    private Situacion situacion;

//______________________________________________________________________________
    private Semestre semestre;

    public WrapperHistorialAsignacionEstudianteCarrera() {
        this.fechaInscripcion = new Date();
    }

    /**
     * Agrega los atributos del objeto de tipo {@link HistorialAsignacionEstudianteCarrera}
     * a este objeto.
     *
     * @param historialAsignacionEstudianteCarrera Objeto de tipo {@link HistorialAsignacionEstudianteCarrera}
     */
    public void agregarWrapper(HistorialAsignacionEstudianteCarrera historialAsignacionEstudianteCarrera) {
        this.setFechaInscripcion(historialAsignacionEstudianteCarrera.getFechaInscripcion());
        this.setSituacion(historialAsignacionEstudianteCarrera.getSituacion());
        this.setSemestre(historialAsignacionEstudianteCarrera.getSemestre());

    }

    /**
     * Otorga los atributos de este objeto al objeto de tipo {@link HistorialAsignacionEstudianteCarrera}
     *
     * @param historialAsignacionEstudianteCarrera Objeto de tipo {@link HistorialAsignacionEstudianteCarrera}
     */
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
