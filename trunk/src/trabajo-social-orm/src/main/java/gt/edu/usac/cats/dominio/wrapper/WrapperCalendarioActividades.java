/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.dominio.wrapper;

import gt.edu.usac.cats.dominio.CalendarioActividades;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.enums.TipoActividad;
import java.util.Date;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Contiene los atributos del Calendario de Actividades que seran ingresados
 * o actualizado a la BD. El wrapper se utiliza en las paginas de
 * <code>agregarCalendarioActividades.htm</code> y
 * <code>editarCalendarioActividades.htm</code>.
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
    @NotNull(message = "{validacion.campoObligatorio}")
    private Semestre semestre;
//______________________________________________________________________________
    private TipoActividad tipoActividad;
//______________________________________________________________________________
    /**
     * Constructor del wrapper, se inicializan los atributos a mostrar en las
     * paginas de <code>agregarCalendarioActividades.htm</code> y
     * <code>editarCalendarioActividades.htm</code>.
     */
    public WrapperCalendarioActividades() {
        this.actividad = "";
        this.fechaFin = new Date();
        this.fechaInicio = new Date();
    }
//______________________________________________________________________________
    /**
     * Se agrega al wrapper la informacion del pojo de tipo {@link CalendarioActividades}.
     *
     * @param curso Pojo de tipo {@link CalendarioActividades}
     */
    public void agregarWrapper(CalendarioActividades calendarioActividades) {
        this.setActividad(calendarioActividades.getActividad());
        this.setFechaFin(calendarioActividades.getFechaFin());
        this.setFechaInicio(calendarioActividades.getFechaInicio());
        this.setSemestre(calendarioActividades.getSemestre());
        this.setTipoActividad(calendarioActividades.getTipoActividad());
    }
//______________________________________________________________________________
    /**
     * Se agrega la informacion del pojo de tipo {@link CalendarioActividades} al wrapper.
     *
     * @param curso Pojo de tipo {@link CalendarioActividades}
     */
    public void quitarWrapper(CalendarioActividades calendarioActividades) {
        calendarioActividades.setActividad(this.getActividad());
        calendarioActividades.setFechaFin(this.getFechaFin());
        calendarioActividades.setFechaInicio(this.getFechaInicio());
        calendarioActividades.setSemestre(this.getSemestre());
        calendarioActividades.setTipoActividad(this.getTipoActividad());
    }
//______________________________________________________________________________
    /**
     * @return La actividad del calendario de actividades
     */
    public String getActividad() {
        return actividad;
    }
//______________________________________________________________________________
    /**
     * @param actividad La actividad del calendario de actividades
     */
    public void setActividad(String actividad) {
        this.actividad = actividad;
    }
//______________________________________________________________________________
    /**
     * @return La fecha de finalizacion del calendario de actividades.
     */
    public Date getFechaFin() {
        return fechaFin;
    }
//______________________________________________________________________________
    /**
     * @param fechaFin La fecha de finalizacion del calendario de actividades.
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
//______________________________________________________________________________
    /**
     * @return La fecha de inicio del calendario de actividades.
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }
//______________________________________________________________________________
    /**
     * @param fechaInicio La fecha de inicio del calendario de actividades.
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
//______________________________________________________________________________
    /**
     * @return pojo de tipo {@link Semestre}
     */
    public Semestre getSemestre() {
        return semestre;
    }
//______________________________________________________________________________
    /**
     * @param semestre pojo de tipo {@link Semestre}
     */
    public void setSemestre(Semestre semestre) {
        this.semestre = semestre;
    }

    /**
     * @return the tipoActividad
     */
    public TipoActividad getTipoActividad() {
        return tipoActividad;
    }

    /**
     * @param tipoActividad the tipoActividad to set
     */
    public void setTipoActividad(TipoActividad tipoActividad) {
        this.tipoActividad = tipoActividad;
    }
}
