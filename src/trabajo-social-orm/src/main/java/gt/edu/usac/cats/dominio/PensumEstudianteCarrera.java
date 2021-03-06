/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cats.dominio;

// Generated Mar 14, 2011 11:31:43 AM by Hibernate Tools 3.2.1.GA
import java.util.Date;
import javax.persistence.CascadeType;
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
import javax.persistence.UniqueConstraint;

/**
 * PensumEstudianteCarrera generated by hbm2java
 */
@Entity
@Table(name = "pensum_estudiante_carrera", schema = "control", uniqueConstraints =
@UniqueConstraint(name = "pensum_estudiante_carrera_uk", columnNames = {"id_asignacion_estudiante_carrera", "id_pensum"}))
public class PensumEstudianteCarrera implements java.io.Serializable {

    private Integer idPensumEstudianteCarrera;
    private AsignacionEstudianteCarrera asignacionEstudianteCarrera;
    private Pensum pensum;
    private boolean valido;
    private Date fechaFin;

    public PensumEstudianteCarrera() {
        this.idPensumEstudianteCarrera = 0;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pensum_estudiante_carrera", unique = true, nullable = false)
    public Integer getIdPensumEstudianteCarrera() {
        return this.idPensumEstudianteCarrera;
    }

    public void setIdPensumEstudianteCarrera(Integer idPensumEstudianteCarrera) {
        this.idPensumEstudianteCarrera = idPensumEstudianteCarrera;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_asignacion_estudiante_carrera", nullable = false)
    public AsignacionEstudianteCarrera getAsignacionEstudianteCarrera() {
        return this.asignacionEstudianteCarrera;
    }

    public void setAsignacionEstudianteCarrera(AsignacionEstudianteCarrera asignacionEstudianteCarrera) {
        this.asignacionEstudianteCarrera = asignacionEstudianteCarrera;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pensum", nullable = false)
    public Pensum getPensum() {
        return this.pensum;
    }

    public void setPensum(Pensum pensum) {
        this.pensum = pensum;
    }

    @Column(name = "valido", nullable = false)
    public boolean isValido() {
        return this.valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_fin", length = 13)
    public Date getFechaFin() {
        return this.fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PensumEstudianteCarrera (idPensumEstudianteCarrera=").append(this.idPensumEstudianteCarrera)
                .append(", valido=").append(this.valido).append(")");

        return builder.toString();
    }
}
