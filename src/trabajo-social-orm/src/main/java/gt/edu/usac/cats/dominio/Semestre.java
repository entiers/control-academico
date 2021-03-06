/*
 * @(#)Semestre.java   29.03.10
 * 
 * Universidad de San Carlos de Guatemala
 * Escuela de Trabajo Social
 * Control Academico
 *
 */
package gt.edu.usac.cats.dominio;

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
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

/**
 * Semestre generated by hbm2java
 */
@Entity
@Table(
    name = "semestre",
schema = "control",
uniqueConstraints = {
    @UniqueConstraint(name = "semestre_anio_numero_uk", columnNames = {"anio", "numero"})
})
public class Semestre implements java.io.Serializable {

    private Short idSemestre;
    private short anio;
    private char numero;
    private String observacion;
    private boolean activo;
    private Set<CalendarioActividades> calendarioActividadeses = new HashSet<CalendarioActividades>(0);
    private Set<HistorialAsignacionEstudianteCarrera> historialAsignacionEstudianteCarreras = new HashSet<HistorialAsignacionEstudianteCarrera>(0);
    private Set<Horario> horarios = new HashSet<Horario>(0);

    public Semestre() {
        this.idSemestre = 0;
    }

    public Semestre(short anio, char numero) {
        this.anio = anio;
        this.numero = numero;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
        name = "id_semestre",
    unique = true,
    nullable = false)
    public Short getIdSemestre() {
        return this.idSemestre;
    }

    public void setIdSemestre(Short idSemestre) {
        this.idSemestre = idSemestre;
    }

    @Column(
        name = "anio",
    nullable = false)
    public short getAnio() {
        return this.anio;
    }

    public void setAnio(short anio) {
        this.anio = anio;
    }

    @Column(
        name = "numero",
    nullable = false,
    length = 1)
    public char getNumero() {
        return this.numero;
    }

    public void setNumero(char numero) {
        this.numero = numero;
    }
//______________________________________________________________________________    

    @Column(
        name = "observacion",
    length = 1000,
    nullable = true)
    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
//______________________________________________________________________________

    @OneToMany(
        cascade = CascadeType.ALL,
    fetch = FetchType.LAZY,
    mappedBy = "semestre")
    public Set<CalendarioActividades> getCalendarioActividadeses() {
        return this.calendarioActividadeses;
    }

    public void setCalendarioActividadeses(Set<CalendarioActividades> calendarioActividadeses) {
        this.calendarioActividadeses = calendarioActividadeses;
    }
//______________________________________________________________________________

    @OneToMany(
        cascade = CascadeType.ALL,
    fetch = FetchType.LAZY,
    mappedBy = "semestre")
    public Set<Horario> getHorarios() {
        return this.horarios;
    }

    public void setHorarios(Set<Horario> horarios) {
        this.horarios = horarios;
    }
//______________________________________________________________________________

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "semestre")
    public Set<HistorialAsignacionEstudianteCarrera> getHistorialAsignacionEstudianteCarreras() {
        return this.historialAsignacionEstudianteCarreras;
    }

    public void setHistorialAsignacionEstudianteCarreras(Set<HistorialAsignacionEstudianteCarrera> historialAsignacionEstudianteCarreras) {
        this.historialAsignacionEstudianteCarreras = historialAsignacionEstudianteCarreras;
    }

//______________________________________________________________________________
    @Column(
        columnDefinition = "default true",
    name = "activo",
    nullable = false)
    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    //______________________________________________________________________________
    @Transient
    public String getAnyoNumero() {
        return this.anio + " - " + this.numero;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Semestre (idSemestre = ").append(this.idSemestre)
                .append(", anio = ").append(this.anio)
                .append(", numero = ").append(this.numero)
                .append(", activo = ").append(this.activo)
                .append(")");

        return super.toString();
    }
}

//~ Formatted by Jindent --- http://www.jindent.com
