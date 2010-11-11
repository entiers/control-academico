/*
 * @(#)Desasignacion.java   29.03.10
 *
 * Universidad de San Carlos de Guatemala
 * Escuela de Trabajo Social
 * Control Academico
 *
 */



package gt.edu.usac.cats.dominio;

//Generated 16/03/2010 06:31:00 PM by Hibernate Tools 3.2.1.GA

//~--- JDK imports ------------------------------------------------------------

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Desasignacion generated by hbm2java
 */
@Entity
@Table(
    name = "desasignacion",
    schema = "control"
)
public class Desasignacion implements java.io.Serializable {
    private Curso curso;
    private Estudiante estudiante;
    private Date fecha;
    private int idDesasignacion;
    private String observacion;

    public Desasignacion() {}

    public Desasignacion(int idDesasignacion, Estudiante estudiante, Curso curso, Date fecha) {
        this.idDesasignacion = idDesasignacion;
        this.estudiante = estudiante;
        this.curso = curso;
        this.fecha = fecha;
    }

    public Desasignacion(int idDesasignacion, Estudiante estudiante, Curso curso, Date fecha, String observacion) {
        this.idDesasignacion = idDesasignacion;
        this.estudiante = estudiante;
        this.curso = curso;
        this.fecha = fecha;
        this.observacion = observacion;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
        name = "id_desasignacion",
        unique = true,
        nullable = false
    )
    public int getIdDesasignacion() {
        return this.idDesasignacion;
    }

    public void setIdDesasignacion(int idDesasignacion) {
        this.idDesasignacion = idDesasignacion;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "id_estudiante",
        nullable = false
    )
    public Estudiante getEstudiante() {
        return this.estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "id_curso",
        nullable = false
    )
    public Curso getCurso() {
        return this.curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    @Temporal(TemporalType.DATE)
    @Column(
        name = "fecha",
        nullable = false,
        length = 13
    )
    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Column(name = "observacion")
    public String getObservacion() {
        return this.observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
