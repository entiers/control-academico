/*
 * @(#)AsignacionEstudianteCarrera.java   29.03.10
 *
 * Universidad de San Carlos de Guatemala
 * Escuela de Trabajo Social
 * Control Academico
 *
 */



package gt.edu.usac.cats.dominio;

//~--- JDK imports ------------------------------------------------------------

//Generated 16/03/2010 06:31:00 PM by Hibernate Tools 3.2.1.GA
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
 * AsignacionEstudianteCarrera generated by hbm2java
 */
@Entity
@Table(
    name = "asignacion_estudiante_carrera",
    schema = "control"
)
public class AsignacionEstudianteCarrera implements java.io.Serializable {
    private Carrera carrera;
    private Estudiante estudiante;
    private Date fechaFin;
    private Date fechaInicio;
    private int idAsignacionEstudianteCarrera;

    public AsignacionEstudianteCarrera() {}

    public AsignacionEstudianteCarrera(int idAsignacionEstudianteCarrera, Estudiante estudiante, Carrera carrera,
                                       Date fechaInicio) {
        this.idAsignacionEstudianteCarrera = idAsignacionEstudianteCarrera;
        this.estudiante = estudiante;
        this.carrera = carrera;
        this.fechaInicio = fechaInicio;
    }

    public AsignacionEstudianteCarrera(int idAsignacionEstudianteCarrera, Estudiante estudiante, Carrera carrera,
                                       Date fechaInicio, Date fechaFin) {
        this.idAsignacionEstudianteCarrera = idAsignacionEstudianteCarrera;
        this.estudiante = estudiante;
        this.carrera = carrera;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
        name = "id_asignacion_estudiante_carrera",
        unique = true,
        nullable = false
    )
    public int getIdAsignacionEstudianteCarrera() {
        return this.idAsignacionEstudianteCarrera;
    }

    public void setIdAsignacionEstudianteCarrera(int idAsignacionEstudianteCarrera) {
        this.idAsignacionEstudianteCarrera = idAsignacionEstudianteCarrera;
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
        name = "id_carrera",
        nullable = false
    )
    public Carrera getCarrera() {
        return this.carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    @Temporal(TemporalType.DATE)
    @Column(
        name = "fecha_inicio",
        nullable = false,
        length = 13
    )
    public Date getFechaInicio() {
        return this.fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    @Temporal(TemporalType.DATE)
    @Column(
        name = "fecha_fin",
        length = 13
    )
    public Date getFechaFin() {
        return this.fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
