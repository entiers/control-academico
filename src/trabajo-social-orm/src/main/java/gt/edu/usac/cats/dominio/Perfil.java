/*
 * @(#)Perfil.java   29.03.10
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

/**
 * Perfil generated by hbm2java
 */
@Entity
@Table(
    name = "perfil",
    schema = "control"
)
public class Perfil implements java.io.Serializable {
    private Set<AsignacionRolPerfil> asignacionRolPerfils = new HashSet<AsignacionRolPerfil>(0);
    private Set<AsignacionUsuarioPerfil> asignacionUsuarioPerfils = new HashSet<AsignacionUsuarioPerfil>(0);
    private String descripcion;
    private short idPerfil;
    private String nombre;

    public Perfil() {}

    public Perfil(short idPerfil, String nombre) {
        this.idPerfil = idPerfil;
        this.nombre = nombre;
    }

    public Perfil(short idPerfil, String nombre, String descripcion, Set<AsignacionRolPerfil> asignacionRolPerfils,
                  Set<AsignacionUsuarioPerfil> asignacionUsuarioPerfils) {
        this.idPerfil = idPerfil;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.asignacionRolPerfils = asignacionRolPerfils;
        this.asignacionUsuarioPerfils = asignacionUsuarioPerfils;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
        name = "id_perfil",
        unique = true,
        nullable = false
    )
    public short getIdPerfil() {
        return this.idPerfil;
    }

    public void setIdPerfil(short idPerfil) {
        this.idPerfil = idPerfil;
    }

    @Column(
        name = "nombre",
        nullable = false,
        length = 50
    )
    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(
        name = "descripcion",
        length = 250
    )
    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "perfil"
    )
    public Set<AsignacionRolPerfil> getAsignacionRolPerfils() {
        return this.asignacionRolPerfils;
    }

    public void setAsignacionRolPerfils(Set<AsignacionRolPerfil> asignacionRolPerfils) {
        this.asignacionRolPerfils = asignacionRolPerfils;
    }

    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "perfil"
    )
    public Set<AsignacionUsuarioPerfil> getAsignacionUsuarioPerfils() {
        return this.asignacionUsuarioPerfils;
    }

    public void setAsignacionUsuarioPerfils(Set<AsignacionUsuarioPerfil> asignacionUsuarioPerfils) {
        this.asignacionUsuarioPerfils = asignacionUsuarioPerfils;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
