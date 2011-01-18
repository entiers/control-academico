/*
 * @(#)Perfil.java   29.03.10
 *
 * Universidad de San Carlos de Guatemala
 * Escuela de Trabajo Social
 * Control Academico
 *
 */

package gt.edu.usac.cats.dominio;

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
 *
 * @author Carlos Solorzano
 */
@Entity
@Table(
    name = "asignacion_primer_ingreso",
    schema = "control"
)
public class AsignacionPrimerIngreso implements java.io.Serializable {
    private Integer idAsignacionPrimerIngreso;
    private Date fechaInicio;
    private Date fechaFin;    
    private Usuario usuario;
    private Set<Asignacion> asignaciones = new HashSet<Asignacion>(0);

    public AsignacionPrimerIngreso() {
        this.fechaInicio = new Date();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
        name = "id_asignacion_primer_ingreso",
        nullable = false,
        unique = true
    )    
    public Integer getIdAsignacionPrimerIngreso() {
        return idAsignacionPrimerIngreso;
    }

    public void setIdAsignacionPrimerIngreso(Integer idAsignacionPrimerIngreso) {
        this.idAsignacionPrimerIngreso = idAsignacionPrimerIngreso;
    }

    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    @Column(name = "fecha_fin")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "id_usuario",        
        nullable = false
    )    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario idUsuario) {
        this.usuario = idUsuario;
    }

    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "asignacionPrimerIngreso"
    )
    public Set<Asignacion> getAsignaciones() {
        return asignaciones;
    }

    public void setAsignaciones(Set<Asignacion> asignaciones) {
        this.asignaciones = asignaciones;
    }


}
