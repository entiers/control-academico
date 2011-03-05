/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */
package gt.edu.usac.cats.dominio.wrapper;

//import gt.edu.usac.trabajosocial.anotacion.CodigoPensumValidador;
import gt.edu.usac.cats.dominio.Carrera;
import gt.edu.usac.cats.dominio.Pensum;
import java.util.Date;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Daniel Castillo
 * @version 1.0
 */
public class WrapperPensum {

    @NotEmpty(message = "{validacion.campoObligatorio}")
    @Size(max = 15, message = "{validacion.caracteresMaximos}")
    private String codigo;
//______________________________________________________________________________    
    private Carrera carrera;
//______________________________________________________________________________
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date fechaFin;
//______________________________________________________________________________
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date fechaInicio;
//______________________________________________________________________________
    private short estado;
//______________________________________________________________________________

    public WrapperPensum() {
        this.codigo = "";
        this.fechaFin = new Date();
        this.fechaInicio = this.fechaFin;
        this.estado = (short) 0;
    }
//______________________________________________________________________________

    public void agregarWrapper(Pensum pensum) {
        this.setCodigo(pensum.getCodigo());
        this.setEstado(pensum.getEstado());
        this.setFechaInicio(pensum.getFechaInicio());
        this.setFechaFin(pensum.getFechaFin());
        this.setCarrera(pensum.getCarrera());
    }
//______________________________________________________________________________

    public void quitarWrapper(Pensum pensum) {
        pensum.setCodigo(this.codigo);
        pensum.setEstado(this.estado);
        pensum.setFechaInicio(this.fechaInicio);
        pensum.setFechaFin(this.fechaFin);
        pensum.setCarrera(this.carrera);
    }
//______________________________________________________________________________

    public String getCodigo() {
        return codigo;
    }
//______________________________________________________________________________

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
//______________________________________________________________________________

    public Carrera getCarrera() {
        return carrera;
    }
//______________________________________________________________________________

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }
//______________________________________________________________________________

    public Date getFechaFin() {
        return fechaFin;
    }
//______________________________________________________________________________

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
//______________________________________________________________________________

    public Date getFechaInicio() {
        return fechaInicio;
    }
//______________________________________________________________________________

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
//______________________________________________________________________________

    public short getEstado() {
        return estado;
    }
//______________________________________________________________________________

    public void setEstado(short estado) {
        this.estado = estado;
    }
}
