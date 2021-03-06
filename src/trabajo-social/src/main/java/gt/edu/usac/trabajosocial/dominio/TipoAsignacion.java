/*
 * @(#)TipoAsignacion.java   29.03.10
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

/**
 * TipoAsignacion generated by hbm2java
 */
@Entity
@Table(
    name = "tipo_asignacion",
    schema = "control"
)
public class TipoAsignacion implements java.io.Serializable {
    private Set<Asignacion> asignacions = new HashSet<Asignacion>(0);
    private String descripcion;
    private short idTipoAsignacion;
    private String nombre;
    private boolean habilitado;

    public TipoAsignacion() {}

    public TipoAsignacion(short idTipoAsignacion, String nombre) {
        this.idTipoAsignacion = idTipoAsignacion;
        this.nombre = nombre;
    }

    public TipoAsignacion(short idTipoAsignacion, String nombre, String descripcion, Set<Asignacion> asignacions) {
        this.idTipoAsignacion = idTipoAsignacion;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.asignacions = asignacions;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
        name = "id_tipo_asignacion",
        unique = true,
        nullable = false
    )
    public short getIdTipoAsignacion() {
        return this.idTipoAsignacion;
    }

    public void setIdTipoAsignacion(short idTipoAsignacion) {
        this.idTipoAsignacion = idTipoAsignacion;
    }

    @Column(
        name = "nombre",
        nullable = false,
        length = 100
    )
    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(name = "descripcion")
    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @OneToMany(
        //cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "tipoAsignacion"
    )
    public Set<Asignacion> getAsignacions() {
        return this.asignacions;
    }

    public void setAsignacions(Set<Asignacion> asignacions) {
        this.asignacions = asignacions;
    }

    @Column(
        name = "habilitado",
        nullable = false
    )
    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }
    
}


//~ Formatted by Jindent --- http://www.jindent.com
