/*
 * @(#)Usuario.java   29.03.10
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
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Usuario generated by hbm2java
 */
@Entity
@Table(
    name = "usuario",
    schema = "control"
)
public class Usuario implements java.io.Serializable {
    private Set<Catedratico> catedraticos = new HashSet<Catedratico>(0);
    private Set<Log> logs = new HashSet<Log>(0);
    private Set<Estudiante> estudiantes = new HashSet<Estudiante>(0);
    private Set<AsignacionUsuarioPerfil> asignacionUsuarioPerfils = new HashSet<AsignacionUsuarioPerfil>(0);
    private boolean habilitado;
    private short idUsuario;
    @NotNull
    private String nombreUsuario;
    @NotNull
    private String password;

    public Usuario() {}

    public Usuario(short idUsuario, String nombreUsuario, String password, boolean habilitado) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.habilitado = habilitado;
    }

    public Usuario(short idUsuario, String nombreUsuario, String password, boolean habilitado,
                   Set<Catedratico> catedraticos, Set<Log> logs, Set<AsignacionUsuarioPerfil> asignacionUsuarioPerfils,
                   Set<Estudiante> estudiantes) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.habilitado = habilitado;
        this.catedraticos = catedraticos;
        this.logs = logs;
        this.asignacionUsuarioPerfils = asignacionUsuarioPerfils;
        this.estudiantes = estudiantes;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(
        name = "id_usuario",
        unique = true,
        nullable = false
    )
    public short getIdUsuario() {
        return this.idUsuario;
    }

    public void setIdUsuario(short idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Column(
        name = "nombre_usuario",
        nullable = false,
        length = 256
    )
    public String getNombreUsuario() {
        return this.nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    @Column(
        name = "password",
        nullable = false,
        length = 256
    )
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(
        name = "habilitado",
        nullable = false
    )
    public boolean isHabilitado() {
        return this.habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "usuario"
    )
    public Set<Catedratico> getCatedraticos() {
        return this.catedraticos;
    }

    public void setCatedraticos(Set<Catedratico> catedraticos) {
        this.catedraticos = catedraticos;
    }

    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "usuario"
    )
    public Set<Log> getLogs() {
        return this.logs;
    }

    public void setLogs(Set<Log> logs) {
        this.logs = logs;
    }

    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "usuario"
    )
    public Set<AsignacionUsuarioPerfil> getAsignacionUsuarioPerfils() {
        return this.asignacionUsuarioPerfils;
    }

    public void setAsignacionUsuarioPerfils(Set<AsignacionUsuarioPerfil> asignacionUsuarioPerfils) {
        this.asignacionUsuarioPerfils = asignacionUsuarioPerfils;
    }

    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "usuario"
    )
    public Set<Estudiante> getEstudiantes() {
        return this.estudiantes;
    }

    public void setEstudiantes(Set<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
