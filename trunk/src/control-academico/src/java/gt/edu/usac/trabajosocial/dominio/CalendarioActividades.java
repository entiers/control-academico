/*
 * @(#)CalendarioActividades.java   29.03.10
 * 
 * Universidad de San Carlos de Guatemala
 * Escuela de Trabajo Social
 * Control Academico
 *
 */



package gt.edu.usac.trabajosocial.dominio;

//Generated 16/03/2010 06:31:00 PM by Hibernate Tools 3.2.1.GA

//~--- JDK imports ------------------------------------------------------------

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * CalendarioActividades generated by hbm2java
 */
@Entity
@Table(
    name = "calendario_actividades",
    schema = "control"
)
public class CalendarioActividades implements java.io.Serializable {
    private String actividad;
    private Date fechaFin;
    private Date fechaInicio;
    private short idCalendarioActividades;
    private Semestre semestre;

    public CalendarioActividades() {}

    public CalendarioActividades(short idCalendarioActividades, Semestre semestre, Date fechaInicio, Date fechaFin,
                                 String actividad) {
        this.idCalendarioActividades = idCalendarioActividades;
        this.semestre = semestre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.actividad = actividad;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(
        name = "id_calendario_actividades",
        unique = true,
        nullable = false
    )
    public short getIdCalendarioActividades() {
        return this.idCalendarioActividades;
    }

    public void setIdCalendarioActividades(short idCalendarioActividades) {
        this.idCalendarioActividades = idCalendarioActividades;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "id_semestre",
        nullable = false
    )
    public Semestre getSemestre() {
        return this.semestre;
    }

    public void setSemestre(Semestre semestre) {
        this.semestre = semestre;
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
        nullable = false,
        length = 13
    )
    public Date getFechaFin() {
        return this.fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Column(
        name = "actividad",
        nullable = false
    )
    public String getActividad() {
        return this.actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
