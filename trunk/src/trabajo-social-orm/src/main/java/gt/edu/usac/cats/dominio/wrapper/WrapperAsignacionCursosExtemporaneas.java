/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */


package gt.edu.usac.cats.dominio.wrapper;

import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.enums.TipoHorario;

/**
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
public class WrapperAsignacionCursosExtemporaneas {
//______________________________________________________________________________
    private TipoHorario tipoHorario;
//______________________________________________________________________________
    private AsignacionEstudianteCarrera asignacionEstudianteCarrera;
//______________________________________________________________________________
    public AsignacionEstudianteCarrera getAsignacionEstudianteCarrera() {
        return asignacionEstudianteCarrera;
    }
//______________________________________________________________________________
    public void setAsignacionEstudianteCarrera(AsignacionEstudianteCarrera asignacionEstudianteCarrera) {
        this.asignacionEstudianteCarrera = asignacionEstudianteCarrera;
    }
//______________________________________________________________________________
    public TipoHorario getTipoHorario() {
        return tipoHorario;
    }
//______________________________________________________________________________
    public void setTipoHorario(TipoHorario tipoHorario) {
        this.tipoHorario = tipoHorario;
    }
}
