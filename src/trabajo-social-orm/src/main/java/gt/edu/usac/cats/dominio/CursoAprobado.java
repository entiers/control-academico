/*
 * @(#)CursoAprobado.java   29.03.10
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

/**
 * CursoAprobado generated by hbm2java
 */
@Entity
@Table(name = "curso_aprobado",
schema = "control")
public class CursoAprobado implements java.io.Serializable {

    private int idCursoAprobado;
    private Asignacion asignacion;
    private AsignacionCursoPensum asignacionCursoPensum;
    private Date fechaAprobacion;
    private Short zona;
    private Short laboratorio;
    private Short examenFinal;
    private String observaciones;
    private Set<NotaIndicador> notaIndicadors = new HashSet<NotaIndicador>(0);
    private boolean ingresoManual;

    public CursoAprobado() {
        this.ingresoManual = false;
        this.idCursoAprobado = 0;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curso_aprobado",
    unique = true,
    nullable = false)
    public int getIdCursoAprobado() {
        return this.idCursoAprobado;
    }

    public void setIdCursoAprobado(int idCursoAprobado) {
        this.idCursoAprobado = idCursoAprobado;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_asignacion", nullable = false)
    public Asignacion getAsignacion() {
        return this.asignacion;
    }

    public void setAsignacion(Asignacion asignacion) {
        this.asignacion = asignacion;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_asignacion_curso_pensum", nullable = false)
    public AsignacionCursoPensum getAsignacionCursoPensum() {
        return this.asignacionCursoPensum;
    }

    public void setAsignacionCursoPensum(AsignacionCursoPensum asignacionCursoPensum) {
        this.asignacionCursoPensum = asignacionCursoPensum;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_aprobacion",
    length = 13)
    public Date getFechaAprobacion() {
        return this.fechaAprobacion;
    }

    public void setFechaAprobacion(Date fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    @Column(name = "zona")
    public Short getZona() {
        return this.zona;
    }

    public void setZona(Short zona) {
        this.zona = zona;
    }

    @Column(name = "laboratorio")
    public Short getLaboratorio() {
        return this.laboratorio;
    }

    public void setLaboratorio(Short laboratorio) {
        this.laboratorio = laboratorio;
    }

    @Column(name = "examen_final")
    public Short getExamenFinal() {
        return this.examenFinal;
    }

    public void setExamenFinal(Short examenFinal) {
        this.examenFinal = examenFinal;
    }

    @Column(name = "observaciones")
    public String getObservaciones() {
        return this.observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @OneToMany(cascade = CascadeType.ALL,
    fetch = FetchType.LAZY,
    mappedBy = "cursoAprobado")
    public Set<NotaIndicador> getNotaIndicadors() {
        return this.notaIndicadors;
    }

    public void setNotaIndicadors(Set<NotaIndicador> notaIndicadors) {
        this.notaIndicadors = notaIndicadors;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CursoAprobado (idCursoAprobado = ").
                append(this.idCursoAprobado).
                append(")");
        return builder.toString();
    }
    
    @Column(name = "ingreso_manual")    
    public boolean getIngresoManual() {
        return this.ingresoManual;
    }

    public void setIngresoManual(boolean ingresoManual) {
        this.ingresoManual = ingresoManual;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
