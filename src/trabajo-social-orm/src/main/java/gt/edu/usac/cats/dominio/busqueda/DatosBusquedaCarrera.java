/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.dominio.busqueda;

/**
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
public class DatosBusquedaCarrera {
    private int idAsignacionEstudianteCarrera;
    private int idHorario;
    private int idCurso;

    public int getIdAsignacionEstudianteCarrera() {
        return idAsignacionEstudianteCarrera;
    }

    public void setIdAsignacionEstudianteCarrera(int idAsignacionEstudianteCarrera) {
        this.idAsignacionEstudianteCarrera = idAsignacionEstudianteCarrera;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }
}
