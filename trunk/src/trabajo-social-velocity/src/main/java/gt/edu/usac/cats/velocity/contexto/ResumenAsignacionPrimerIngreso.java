/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.velocity.contexto;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author shakamca
 */
public class ResumenAsignacionPrimerIngreso implements Serializable{

    private DateFormat dateFormat;

    private Date fechaInicio;
    private Date fechaFin;
    private int totalAsignados;
    private int totalNoAsignados;

    public ResumenAsignacionPrimerIngreso() {
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:ss");

        this.fechaInicio = new Date();
        this.fechaInicio = new Date();
        this.totalAsignados = 0;
        this.totalNoAsignados = 0;
    }

    public ResumenAsignacionPrimerIngreso(Date fechaInicio, Date fechaFin, int totalAsignados, int totalNoAsignados) {
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:ss");

        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.totalAsignados = totalAsignados;
        this.totalNoAsignados = totalNoAsignados;
    }

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

    /**
     * @return the totalAsignados
     */
    public int getTotalAsignados() {
        return totalAsignados;
    }

    /**
     * @param totalAsignados the totalAsignados to set
     */
    public void setTotalAsignados(int totalAsignados) {
        this.totalAsignados = totalAsignados;
    }

    /**
     * @return the totalNoAsignados
     */
    public int getTotalNoAsignados() {
        return totalNoAsignados;
    }

    /**
     * @param totalNoAsignados the totalNoAsignados to set
     */
    public void setTotalNoAsignados(int totalNoAsignados) {
        this.totalNoAsignados = totalNoAsignados;
    }

    /**
     * Retorna la fecha de inicio con el formato dd/MM/yyyy hh:mm.
     *
     * @return Objeto de tipo {@link String}
     */
    public String getFechaInicioFormat(){        
        return (this.fechaInicio != null)
                ? this.dateFormat.format(this.fechaInicio) : null;
    }

    /**
     * Retorna la fecha de fin con el formato dd/MM/yyyy hh:mm.
     *
     * @return Objeto de tipo {@link String}
     */

    public String getFechaFinFormat(){
        return (this.fechaFin != null)
                ? this.dateFormat.format(this.fechaFin) : null;
    }

}
