/*
 * @(#)Semestre.java   29.03.10
 * 
 * Universidad de San Carlos de Guatemala
 * Escuela de Trabajo Social
 * Control Academico
 *
 */



package gt.edu.usac.trabajosocial.dominio;

//Generated 16/03/2010 06:31:00 PM by Hibernate Tools 3.2.1.GA

//~--- JDK imports ------------------------------------------------------------

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Semestre generated by hbm2java
 */
@Entity
@Table(
    name = "semestre",
    schema = "control",
    uniqueConstraints={@UniqueConstraint(columnNames={"numero", "anio"})}
)
public class Semestre implements java.io.Serializable {
    private Set<CalendarioActividades> calendarioActividadeses = new HashSet<CalendarioActividades>(0);
    private Set<Horario> horarios = new HashSet<Horario>(0);
    private short anio;
    private short idSemestre;
    private char numero;

    public Semestre() {}

    public Semestre(short idSemestre, short anio, char numero) {
        this.idSemestre = idSemestre;
        this.anio = anio;
        this.numero = numero;
    }

    public Semestre(short idSemestre, short anio, char numero, Set<CalendarioActividades> calendarioActividadeses,
                    Set<Horario> horarios) {
        this.idSemestre = idSemestre;
        this.anio = anio;
        this.numero = numero;
        this.calendarioActividadeses = calendarioActividadeses;
        this.horarios = horarios;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
        name = "id_semestre",
        unique = true,
        nullable = false
    )
    public short getIdSemestre() {
        return this.idSemestre;
    }

    public void setIdSemestre(short idSemestre) {
        this.idSemestre = idSemestre;
    }

    @Column(
        name = "anio",
        nullable = false
    )
    public short getAnio() {
        return this.anio;
    }

    public void setAnio(short anio) {
        this.anio = anio;
    }

    @Column(
        name = "numero",
        nullable = false,
        length = 1
    )
    public char getNumero() {
        return this.numero;
    }

    public void setNumero(char numero) {
        this.numero = numero;
    }

    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "semestre"
    )
    public Set<CalendarioActividades> getCalendarioActividadeses() {
        return this.calendarioActividadeses;
    }

    public void setCalendarioActividadeses(Set<CalendarioActividades> calendarioActividadeses) {
        this.calendarioActividadeses = calendarioActividadeses;
    }

    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "semestre"
    )
    public Set<Horario> getHorarios() {
        return this.horarios;
    }

    public void setHorarios(Set<Horario> horarios) {
        this.horarios = horarios;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
