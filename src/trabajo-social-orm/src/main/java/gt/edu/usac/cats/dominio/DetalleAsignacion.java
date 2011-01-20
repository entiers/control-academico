/*
 * @(#)DetalleAsignacion.java   29.03.10
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * DetalleAsignacion generated by hbm2java
 */
@Entity
@Table(
    name = "detalle_asignacion",
    schema = "control"
)
public class DetalleAsignacion implements java.io.Serializable {
    private Set<NotaIndicador> notaIndicadors = new HashSet<NotaIndicador>(0);
    private Asignacion asignacion;
    private short examenFinal;
    private Horario horario;
    private int idDetalleAsignacion;
    private short laboratorio;
    private short zona;

    public DetalleAsignacion() {
        this.zona = 0;
        this.laboratorio = 0;
        this.examenFinal = 0;
    }

    public DetalleAsignacion(int idDetalleAsignacion, Asignacion asignacion, Horario horario, short zona,
                             short laboratorio, short examenFinal) {
        this.idDetalleAsignacion = idDetalleAsignacion;
        this.asignacion = asignacion;
        this.horario = horario;
        this.zona = zona;
        this.laboratorio = laboratorio;
        this.examenFinal = examenFinal;
    }

    public DetalleAsignacion(int idDetalleAsignacion, Asignacion asignacion, Horario horario, short zona,
                             short laboratorio, short examenFinal, Set<NotaIndicador> notaIndicadors) {
        this.idDetalleAsignacion = idDetalleAsignacion;
        this.asignacion = asignacion;
        this.horario = horario;
        this.zona = zona;
        this.laboratorio = laboratorio;
        this.examenFinal = examenFinal;
        this.notaIndicadors = notaIndicadors;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
        name = "id_detalle_asignacion",
        unique = true,
        nullable = false
    )
    public int getIdDetalleAsignacion() {
        return this.idDetalleAsignacion;
    }

    public void setIdDetalleAsignacion(int idDetalleAsignacion) {
        this.idDetalleAsignacion = idDetalleAsignacion;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "id_asignacion",
        nullable = false
    )
    public Asignacion getAsignacion() {
        return this.asignacion;
    }

    public void setAsignacion(Asignacion asignacion) {
        this.asignacion = asignacion;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "id_horario",
        nullable = false
    )
    public Horario getHorario() {
        return this.horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    @Column(
        name = "zona",
        nullable = false
    )
    public short getZona() {
        return this.zona;
    }

    public void setZona(short zona) {
        this.zona = zona;
    }

    @Column(
        name = "laboratorio",
        nullable = false
    )
    public short getLaboratorio() {
        return this.laboratorio;
    }

    public void setLaboratorio(short laboratorio) {
        this.laboratorio = laboratorio;
    }

    @Column(
        name = "examen_final",
        nullable = false
    )
    public short getExamenFinal() {
        return this.examenFinal;
    }

    public void setExamenFinal(short examenFinal) {
        this.examenFinal = examenFinal;
    }

    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "detalleAsignacion"
    )
    public Set<NotaIndicador> getNotaIndicadors() {
        return this.notaIndicadors;
    }

    public void setNotaIndicadors(Set<NotaIndicador> notaIndicadors) {
        this.notaIndicadors = notaIndicadors;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
