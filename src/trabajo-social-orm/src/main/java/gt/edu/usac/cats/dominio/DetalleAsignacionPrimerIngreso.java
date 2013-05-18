/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.dominio;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Carlos Solorzano
 */
@Entity
@Table(
    name = "detalle_asignacion_primer_ingreso",
    schema = "control"
)
public class DetalleAsignacionPrimerIngreso implements Serializable {
    private Integer idDetalleAsignacionPrimerIngreso;
    private boolean asignado;    
    private Estudiante estudiante;    
    private AsignacionPrimerIngreso asignacionPrimerIngreso;

    public DetalleAsignacionPrimerIngreso() {
        this.asignado = true;
        this.idDetalleAsignacionPrimerIngreso = 0;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
        name = "id_detalle_asignacion_primer_ingreso",
        nullable = false,
        unique = true
    )
    public Integer getIdDetalleAsignacionPrimerIngreso() {
        return idDetalleAsignacionPrimerIngreso;
    }

    public void setIdDetalleAsignacionPrimerIngreso(Integer idDetalleAsignacionPrimerIngreso) {
        this.idDetalleAsignacionPrimerIngreso = idDetalleAsignacionPrimerIngreso;
    }

    @NotNull
    @Column(name = "asignado")
    public boolean getAsignado() {
        return asignado;
    }

    public void setAsignado(boolean asignado) {
        this.asignado = asignado;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "id_asignacion_primer_ingreso",
        nullable = false
    )
    public AsignacionPrimerIngreso getAsignacionPrimerIngreso() {
        return asignacionPrimerIngreso;
    }

    public void setAsignacionPrimerIngreso(AsignacionPrimerIngreso asignacionPrimerIngreso) {
        this.asignacionPrimerIngreso = asignacionPrimerIngreso;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "id_estudiante",
        nullable = false
    )
    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }
}
