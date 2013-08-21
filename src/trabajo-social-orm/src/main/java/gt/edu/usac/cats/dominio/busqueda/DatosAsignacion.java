/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.dominio.busqueda;

import gt.edu.usac.cats.enums.TipoAsignacion;
import gt.edu.usac.cats.enums.TipoHorario;
import gt.edu.usac.cats.enums.TipoRubro;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
public class DatosAsignacion implements Serializable {
    private TipoAsignacion tipoAsignacion;
    private TipoHorario tipoHorario;
    private TipoRubro tipoRubro;
    private Integer idAsignacionEstudianteCarrera;
    private Integer idHorario;
    private Short idAsignacionCursoPensum;
    private int totalCursos;
    private List detalleAsignacion;
    @NotEmpty(message = "{validacion.campoObligatorio}")
    private String seccion;

    public DatosAsignacion(){
        this.detalleAsignacion = new ArrayList();
    }

    public Integer getIdAsignacionEstudianteCarrera() {
        return idAsignacionEstudianteCarrera;
    }

    public void setIdAsignacionEstudianteCarrera(Integer idAsignacionEstudianteCarrera) {
        this.idAsignacionEstudianteCarrera = idAsignacionEstudianteCarrera;
    }

    public Short getIdAsignacionCursoPensum() {
        return idAsignacionCursoPensum;
    }

    public void setIdAsignacionCursoPensum(Short idAsignacionCursoPensum) {
        this.idAsignacionCursoPensum = idAsignacionCursoPensum;
    }

    public Integer getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(Integer idHorario) {
        this.idHorario = idHorario;
    }

    public TipoAsignacion getTipoAsignacion() {
        return tipoAsignacion;
    }

    public void setTipoAsignacion(TipoAsignacion tipoAsignacion) {
        this.tipoAsignacion = tipoAsignacion;
    }

    public int getTotalCursos() {
        return totalCursos;
    }

    public void setTotalCursos(int totalCursos){
        this.totalCursos =  totalCursos;
    }

    public TipoHorario getTipoHorario() {
        return tipoHorario;
    }

    public void setTipoHorario(TipoHorario tipoHorario) {
        this.tipoHorario = tipoHorario;
    }

    public void incrementarTotalCursos() {
        this.totalCursos++;
    }

    public List getDetalleAsignacion() {
        return detalleAsignacion;
    }

    public void setDetalleAsignacion(List detalleAsignacion) {
        this.detalleAsignacion = detalleAsignacion;
    }

    public TipoRubro getTipoRubro() {
        return tipoRubro;
    }

    public void setTipoRubro(TipoRubro tipoRubro) {
        this.tipoRubro = tipoRubro;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }
    
    
}
