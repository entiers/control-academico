/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.dominio.wrapper;

//import gt.edu.usac.trabajosocial.anotacion.CodigoPensumValidador;
import gt.edu.usac.cats.dominio.Pensum;
import java.util.Date;
import javax.validation.constraints.Min;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Daniel Castillo
 * @version 1.0
 */
public class WrapperPensum {

    @NotEmpty(message = "{validacion.campoObligatorio}")
//    @CodigoPensumValidador
    private String codigo;
//______________________________________________________________________________
    @Min(value = 1, message = "{validacion.seleccion}")
    private short idCarrera;
//______________________________________________________________________________
    private Date fechaFin;
//______________________________________________________________________________
    private Date fechaInicio;
//______________________________________________________________________________
    private short estado;
//______________________________________________________________________________
    public WrapperPensum() {}
//______________________________________________________________________________
    public void agregarWrapper(Pensum pensum) {
        this.setCodigo(pensum.getCodigo());
        this.setEstado(pensum.getEstado());
        this.setFechaInicio(pensum.getFechaInicio());
        this.setFechaFin(pensum.getFechaFin());
    }
//______________________________________________________________________________
    public void quitarWrapper(Pensum pensum) {
        pensum.setCodigo(this.codigo);
        pensum.setEstado(this.estado);
        pensum.setFechaInicio(this.fechaInicio);
        pensum.setFechaFin(this.fechaFin);
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
    public short getIdCarrera() {
        return idCarrera;
    }
//______________________________________________________________________________
    public void setIdCarrera(short idCarrera) {
        this.idCarrera = idCarrera;
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
