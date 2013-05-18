
package gt.edu.usac.cats.dominio.wrapper;

import gt.edu.usac.cats.dominio.CursoAprobado;
import gt.edu.usac.cats.enums.TipoAsignacion;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author Carlos Solorzano
 * @version 1.0
 */
public class WrapperCursoAprobado implements Serializable{
//______________________________________________________________________________
    @NotNull(message = "{validacion.campoObligatorio}")
    @DateTimeFormat(pattern = "dd-MM-yyyy")    
    private Date fechaAprobacion;
//______________________________________________________________________________    
    @Min(value = 0, message = "{validacion.minimo}")
    @Max(value = 30, message = "{validacion.maximo}")
    @NotNull(message = "{validacion.campoObligatorio}")
    private Short zona;    
//______________________________________________________________________________    
    @Min(value = 0, message = "{validacion.minimo}")
    @Max(value = 70, message = "{validacion.maximo}")
    @NotNull(message = "{validacion.campoObligatorio}")
    private Short examenFinal;    
//______________________________________________________________________________
    @NotEmpty(message = "{validacion.campoObligatorio}")    
    private String observacion;    
//______________________________________________________________________________      
    private TipoAsignacion tipoAsignacion;
//______________________________________________________________________________    
    private short idAsignacionCursoPensum;
//______________________________________________________________________________      
    private int idAsignacionEstudianteCarrera;
    
    public void agregarWrapper(CursoAprobado cursoAprobado) {
        this.setFechaAprobacion(cursoAprobado.getFechaAprobacion());        
        this.setZona(cursoAprobado.getZona());
        this.setExamenFinal(cursoAprobado.getExamenFinal());
        this.setObservacion(cursoAprobado.getObservaciones()); 
        this.setTipoAsignacion(cursoAprobado.getAsignacion().getTipoAsignacion());        
    }

    public void quitarWrapper(CursoAprobado cursoAprobado) {
        cursoAprobado.setFechaAprobacion(this.getFechaAprobacion());        
        cursoAprobado.setZona(this.getZona());
        cursoAprobado.setExamenFinal(this.getExamenFinal());
        cursoAprobado.setObservaciones(this.getObservacion());
    }

    public Short getExamenFinal() {
        return examenFinal;
    }

    public void setExamenFinal(Short examenFinal) {
        this.examenFinal = examenFinal;
    }

    public Date getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(Date fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public TipoAsignacion getTipoAsignacion() {
        return tipoAsignacion;
    }

    public void setTipoAsignacion(TipoAsignacion tipoAsignacion) {
        this.tipoAsignacion = tipoAsignacion;
    }

    public Short getZona() {
        return zona;
    }

    public void setZona(Short zona) {
        this.zona = zona;
    }

    public short getIdAsignacionCursoPensum() {
        return idAsignacionCursoPensum;
    }

    public void setIdAsignacionCursoPensum(short idAsignacionCursoPensum) {
        this.idAsignacionCursoPensum = idAsignacionCursoPensum;
    }

    public int getIdAsignacionEstudianteCarrera() {
        return idAsignacionEstudianteCarrera;
    }

    public void setIdAsignacionEstudianteCarrera(int idAsignacionEstudianteCarrera) {
        this.idAsignacionEstudianteCarrera = idAsignacionEstudianteCarrera;
    }

}
