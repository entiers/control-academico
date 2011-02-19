/*
 * @(#)Horario.java   29.03.10
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
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Horario generated by hbm2java
 */
@Entity
@Table(
    name = "horario",
    schema = "control"
)
public class Horario implements java.io.Serializable {
    private Set<AsignacionCatedraticoHorario> asignacionCatedraticoHorarios =
        new HashSet<AsignacionCatedraticoHorario>(0);
    private Set<DetalleAsignacion> detalleAsignacions = new HashSet<DetalleAsignacion>(0);
    private Curso curso;
    private String dia;
    private boolean estado;
    private Date horaFin;
    private Date horaInicio;
    private int idHorario;
    private Salon salon;
    private String seccion;
    private Semestre semestre;
    private String tipo;

    public Horario() {}

    public Horario(int idHorario, Semestre semestre, Curso curso, Salon salon, Date horaInicio, Date horaFin,
                   String dia, String seccion, String tipo, boolean estado) {
        this.idHorario = idHorario;
        this.semestre = semestre;
        this.curso = curso;
        this.salon = salon;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.dia = dia;
        this.seccion = seccion;
        this.tipo = tipo;
        this.estado = estado;
    }

    public Horario(int idHorario, Semestre semestre, Curso curso, Salon salon, Date horaInicio, Date horaFin,
                   String dia, String seccion, String tipo, boolean estado,
                   Set<AsignacionCatedraticoHorario> asignacionCatedraticoHorarios,
                   Set<DetalleAsignacion> detalleAsignacions) {
        this.idHorario = idHorario;
        this.semestre = semestre;
        this.curso = curso;
        this.salon = salon;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.dia = dia;
        this.seccion = seccion;
        this.tipo = tipo;
        this.estado = estado;
        this.asignacionCatedraticoHorarios = asignacionCatedraticoHorarios;
        this.detalleAsignacions = detalleAsignacions;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
        name = "id_horario",
        unique = true,
        nullable = false
    )
    public int getIdHorario() {
        return this.idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "id_semestre",
        nullable = false
    )
    @JsonIgnore
    public Semestre getSemestre() {
        return this.semestre;
    }

    public void setSemestre(Semestre semestre) {
        this.semestre = semestre;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "id_curso",
        nullable = false
    )
    @JsonIgnore
    public Curso getCurso() {
        return this.curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "id_salon",
        nullable = false
    )
    @JsonIgnore
    public Salon getSalon() {
        return this.salon;
    }

    public void setSalon(Salon salon) {
        this.salon = salon;
    }

    @Temporal(TemporalType.TIME)
    @Column(
        name = "hora_inicio",
        nullable = false,
        length = 15
    )
    public Date getHoraInicio() {
        return this.horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    @Temporal(TemporalType.TIME)
    @Column(
        name = "hora_fin",
        nullable = false,
        length = 15
    )
    public Date getHoraFin() {
        return this.horaFin;
    }

    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }

    @Column(
        name = "dia",
        nullable = false,
        length = 10
    )
    public String getDia() {
        return this.dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    @Column(
        name = "seccion",
        nullable = false,
        length = 2
    )
    public String getSeccion() {
        return this.seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    @Column(
        name = "tipo",
        nullable = false,
        length = 20
    )
    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Column(
        name = "estado",
        nullable = false
    )
    public boolean isEstado() {
        return this.estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "horario"
    )
    @JsonIgnore
    public Set<AsignacionCatedraticoHorario> getAsignacionCatedraticoHorarios() {
        return this.asignacionCatedraticoHorarios;
    }

    public void setAsignacionCatedraticoHorarios(Set<AsignacionCatedraticoHorario> asignacionCatedraticoHorarios) {
        this.asignacionCatedraticoHorarios = asignacionCatedraticoHorarios;
    }

    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "horario"
    )
    @JsonIgnore
    public Set<DetalleAsignacion> getDetalleAsignacions() {
        return this.detalleAsignacions;
    }

    public void setDetalleAsignacions(Set<DetalleAsignacion> detalleAsignacions) {
        this.detalleAsignacions = detalleAsignacions;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
