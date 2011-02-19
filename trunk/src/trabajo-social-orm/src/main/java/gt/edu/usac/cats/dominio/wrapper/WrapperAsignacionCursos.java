/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.dominio.wrapper;

import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.Curso;
import gt.edu.usac.cats.dominio.Horario;

/**
 *
 * @author Carlos Solorzano
 */
public class WrapperAsignacionCursos {
    private AsignacionEstudianteCarrera asignEstCarrera;
    private Horario horario;
    private Curso curso;

    public WrapperAsignacionCursos(){}
    
    public AsignacionEstudianteCarrera getAsignEstCarrera() {
        return asignEstCarrera;
    }

    public void setAsignEstCarrera(AsignacionEstudianteCarrera asignEstCarrera) {
        this.asignEstCarrera = asignEstCarrera;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }


}
