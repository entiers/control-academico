/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.trabajosocial.dominio.wrapper;

import gt.edu.usac.trabajosocial.dominio.CalendarioActividades;
import gt.edu.usac.trabajosocial.dominio.Semestre;
import java.util.Date;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
public class WrapperCalendarioActividades {
//______________________________________________________________________________
    @NotEmpty(message = "{validacion.campoObligatorio}")
    private String actividad;
//______________________________________________________________________________
    @NotNull(message = "{validacion.campoObligatorio}")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date fechaFin;
//______________________________________________________________________________
    @NotNull(message = "{validacion.campoObligatorio}")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date fechaInicio;
//______________________________________________________________________________
    private Semestre semestre;
//______________________________________________________________________________
    
    public WrapperCalendarioActividades() {
        this.actividad = "";
        this.fechaFin = new Date();
        this.fechaInicio = new Date();
    }
//______________________________________________________________________________
    public void agregarWrapper(CalendarioActividades calendarioActividades) {
        this.setActividad(calendarioActividades.getActividad());
        this.setFechaFin(calendarioActividades.getFechaFin());
        this.setFechaInicio(calendarioActividades.getFechaInicio());
        this.setSemestre(calendarioActividades.getSemestre());
    }
//______________________________________________________________________________
    public void quitarWrapper(CalendarioActividades calendarioActividades) {
        calendarioActividades.setActividad(this.getActividad());
        calendarioActividades.setFechaFin(this.getFechaFin());
        calendarioActividades.setFechaInicio(this.getFechaInicio());
        calendarioActividades.setSemestre(this.getSemestre());
    }
//______________________________________________________________________________
    /**
     * @return the actividad
     */
    public String getActividad() {
        return actividad;
    }

    /**
     * @param actividad the actividad to set
     */
    public void setActividad(String actividad) {
        this.actividad = actividad;
    }
//______________________________________________________________________________
    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
//______________________________________________________________________________
    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
//______________________________________________________________________________
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
