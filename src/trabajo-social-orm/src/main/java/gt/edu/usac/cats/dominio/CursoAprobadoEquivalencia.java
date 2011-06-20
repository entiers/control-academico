package gt.edu.usac.cats.dominio;
// Generated Jun 20, 2011 7:11:13 AM by Hibernate Tools 3.2.1.GA

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
 * CursoAprobadoEquivalencia generated by hbm2java
 */
@Entity
@Table(name = "curso_aprobado_equivalencia", schema = "control")
public class CursoAprobadoEquivalencia implements java.io.Serializable {

    private int idCursoAprobadoEquivalencia;
    private AsignacionEstudianteCarrera asignacionEstudianteCarrera;
    private String acuerdoNumero;
    private Integer observaciones;
    private Set<CursoAprobado> cursoAprobados = new HashSet<CursoAprobado>(0);

    public CursoAprobadoEquivalencia() {
    }

    public CursoAprobadoEquivalencia(int idCursoAprobadoEquivalencia, AsignacionEstudianteCarrera asignacionEstudianteCarrera, String acuerdoNumero) {
        this.idCursoAprobadoEquivalencia = idCursoAprobadoEquivalencia;
        this.asignacionEstudianteCarrera = asignacionEstudianteCarrera;
        this.acuerdoNumero = acuerdoNumero;
    }

    public CursoAprobadoEquivalencia(int idCursoAprobadoEquivalencia, AsignacionEstudianteCarrera asignacionEstudianteCarrera, String acuerdoNumero, Integer observaciones, Set<CursoAprobado> cursoAprobados) {
        this.idCursoAprobadoEquivalencia = idCursoAprobadoEquivalencia;
        this.asignacionEstudianteCarrera = asignacionEstudianteCarrera;
        this.acuerdoNumero = acuerdoNumero;
        this.observaciones = observaciones;
        this.cursoAprobados = cursoAprobados;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curso_aprobado_equivalencia", unique = true, nullable = false)
    public int getIdCursoAprobadoEquivalencia() {
        return this.idCursoAprobadoEquivalencia;
    }

    public void setIdCursoAprobadoEquivalencia(int idCursoAprobadoEquivalencia) {
        this.idCursoAprobadoEquivalencia = idCursoAprobadoEquivalencia;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_asignacion_estudiante_carrera", nullable = false)
    public AsignacionEstudianteCarrera getAsignacionEstudianteCarrera() {
        return this.asignacionEstudianteCarrera;
    }

    public void setAsignacionEstudianteCarrera(AsignacionEstudianteCarrera asignacionEstudianteCarrera) {
        this.asignacionEstudianteCarrera = asignacionEstudianteCarrera;
    }

    @Column(name = "acuerdo_numero", nullable = false, length = 20)
    public String getAcuerdoNumero() {
        return this.acuerdoNumero;
    }

    public void setAcuerdoNumero(String acuerdoNumero) {
        this.acuerdoNumero = acuerdoNumero;
    }

    @Column(name = "observaciones")
    public Integer getObservaciones() {
        return this.observaciones;
    }

    public void setObservaciones(Integer observaciones) {
        this.observaciones = observaciones;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cursoAprobadoEquivalencia")
    public Set<CursoAprobado> getCursoAprobados() {
        return this.cursoAprobados;
    }

    public void setCursoAprobados(Set<CursoAprobado> cursoAprobados) {
        this.cursoAprobados = cursoAprobados;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CursoAprobadoEquivalencia (idCursoAprobadoEquivalencia = ").append(this.idCursoAprobadoEquivalencia).
                append(", acuerdoNumero = \"").append(this.acuerdoNumero).append("\")");
        return super.toString();
    }
}
