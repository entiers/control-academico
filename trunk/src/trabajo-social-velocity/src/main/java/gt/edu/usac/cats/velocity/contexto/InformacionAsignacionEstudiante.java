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
 * @author Mario Batres
 * @version 1.0
 */
public class InformacionAsignacionEstudiante implements Serializable {
    private String carnet;
    private String nombre;
    private Date fecha;
    private String transaccion;
    private String detalleHtmlAsignacion;

    public InformacionAsignacionEstudiante() {
        this.carnet = "";
        this.nombre = "";
        this.fecha = new Date();
        this.transaccion = "";
    }

    public InformacionAsignacionEstudiante(String carnet, String nombre, Date fecha, String transaccion) {
        this.carnet = carnet;
        this.nombre = nombre;
        this.fecha = fecha;
        this.transaccion = transaccion;
    }

    /**
     * @return the carnet
     */
    public String getCarnet() {
        return carnet;
    }

    /**
     * @param carnet the carnet to set
     */
    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the transaccion
     */
    public String getTransaccion() {
        return transaccion;
    }

    /**
     * @param transaccion the transaccion to set
     */
    public void setTransaccion(String transaccion) {
        this.transaccion = transaccion;
    }

    /**
     * Retorna la fecha actual con el formato dd/MM/yyyy.
     *
     * @return Objeto de tipo {@link String}
     */
    public String getFechaFormat(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(this.fecha);
    }

    /**
     * @return the detalleHtmlAsignacion
     */
    public String getDetalleHtmlAsignacion() {
        return detalleHtmlAsignacion;
    }

    /**
     * @param detalleHtmlAsignacion the detalleHtmlAsignacion to set
     */
    public void setDetalleHtmlAsignacion(String detalleHtmlAsignacion) {
        this.detalleHtmlAsignacion = detalleHtmlAsignacion;
    }

}
