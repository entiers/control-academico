/*
 * @(#)Desasignacion.java   29.03.10
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

/**
 * Desasignacion generated by hbm2java
 */
@Entity
@Table(
    name = "desasignacion",
    schema = "control"
)
public class Desasignacion implements java.io.Serializable {
    private DetalleAsignacion detalleAsignacion;
    private Date fecha;
    private int idDesasignacion;
    private String observacion;

    public Desasignacion() {
        this.fecha = new Date();
    }

    public Desasignacion(int idDesasignacion, DetalleAsignacion detalleAsignacion, Date fecha) {
        this.idDesasignacion = idDesasignacion;
        this.detalleAsignacion = detalleAsignacion;
        this.fecha = fecha;
    }

    public Desasignacion(int idDesasignacion, DetalleAsignacion detalleAsignacion, Date fecha, String observacion) {
        this.idDesasignacion = idDesasignacion;
        this.detalleAsignacion = detalleAsignacion;
        this.fecha = fecha;
        this.observacion = observacion;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
        name = "id_desasignacion",
        unique = true,
        nullable = false
    )
    public int getIdDesasignacion() {
        return this.idDesasignacion;
    }

    public void setIdDesasignacion(int idDesasignacion) {
        this.idDesasignacion = idDesasignacion;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "id_detalle_asignacion",
        nullable = false
    )
    public DetalleAsignacion getDetalleAsignacion() {
        return this.detalleAsignacion;
    }

    public void setDetalleAsignacion(DetalleAsignacion detalleAsignacion) {
        this.detalleAsignacion = detalleAsignacion;
    }

    @Temporal(TemporalType.DATE)
    @Column(
        name = "fecha",
        nullable = false,
        length = 13
    )
    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Column(name = "observacion")
    public String getObservacion() {
        return this.observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com