/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.dominio.busqueda;

import gt.edu.usac.cats.enums.TipoAsignacion;
import gt.edu.usac.cats.enums.TipoHorario;
import gt.edu.usac.cats.enums.TipoRubro;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
public class DatosAsignacion {
    private TipoAsignacion tipoAsignacion;
    private TipoHorario tipoHorario;
    private TipoRubro tipoRubro;
    private int idAsignacionEstudianteCarrera;
    private int idHorario;
    private int idAsignacionCursoPensum;
    private int totalCursos;
    private List detalleAsignacion;

    public DatosAsignacion(){
        this.detalleAsignacion = new ArrayList();
    }

    public int getIdAsignacionEstudianteCarrera() {
        return idAsignacionEstudianteCarrera;
    }

    public void setIdAsignacionEstudianteCarrera(int idAsignacionEstudianteCarrera) {
        this.idAsignacionEstudianteCarrera = idAsignacionEstudianteCarrera;
    }

    public int getIdAsignacionCursoPensum() {
        return idAsignacionCursoPensum;
    }

    public void setIdAsignacionCursoPensum(int idAsignacionCursoPensum) {
        this.idAsignacionCursoPensum = idAsignacionCursoPensum;
    }

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
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
}
