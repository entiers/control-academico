/*
 * @(#)Catedratico.java   29.03.10
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Catedratico generated by hbm2java
 */
@Entity
@Table(
    name = "catedratico",
    schema = "control"
)
public class Catedratico implements java.io.Serializable {
    private Set<AsignacionCatedraticoEscuela> asignacionCatedraticoEscuelas =
        new HashSet<AsignacionCatedraticoEscuela>(0);
    private Set<AsignacionCatedraticoHorario> asignacionCatedraticoHorarios =
        new HashSet<AsignacionCatedraticoHorario>(0);
    private String apellido;
    private String celular;
    private String codigo;
    private String direccion;
    private String email;
    private short idCatedratico;
    private String nombre;
    private String profesion;
    private String telefono;
    private Usuario usuario;

    public Catedratico() {}

    public Catedratico(short idCatedratico, Usuario usuario, String codigo, String nombre, String apellido,
                       String profesion) {
        this.idCatedratico = idCatedratico;
        this.usuario = usuario;
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.profesion = profesion;
    }

    public Catedratico(short idCatedratico, Usuario usuario, String codigo, String nombre, String apellido,
                       String profesion, String direccion, String telefono, String celular, String email,
                       Set<AsignacionCatedraticoEscuela> asignacionCatedraticoEscuelas,
                       Set<AsignacionCatedraticoHorario> asignacionCatedraticoHorarios) {
        this.idCatedratico = idCatedratico;
        this.usuario = usuario;
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.profesion = profesion;
        this.direccion = direccion;
        this.telefono = telefono;
        this.celular = celular;
        this.email = email;
        this.asignacionCatedraticoEscuelas = asignacionCatedraticoEscuelas;
        this.asignacionCatedraticoHorarios = asignacionCatedraticoHorarios;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
        name = "id_catedratico",
        unique = true,
        nullable = false
    )
    public short getIdCatedratico() {
        return this.idCatedratico;
    }

    public void setIdCatedratico(short idCatedratico) {
        this.idCatedratico = idCatedratico;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "id_usuario",
        nullable = false
    )
    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Column(
        name = "codigo",
        nullable = false,
        length = 15
    )
    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
        name = "apellido",
        nullable = false,
        length = 50
    )
    public String getApellido() {
        return this.apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    @Column(
        name = "profesion",
        nullable = false,
        length = 100
    )
    public String getProfesion() {
        return this.profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    @Column(
        name = "direccion",
        length = 200
    )
    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Column(
        name = "telefono",
        length = 10
    )
    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Column(
        name = "celular",
        length = 10
    )
    public String getCelular() {
        return this.celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    @Column(
        name = "email",
        length = 100
    )
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "catedratico"
    )
    public Set<AsignacionCatedraticoEscuela> getAsignacionCatedraticoEscuelas() {
        return this.asignacionCatedraticoEscuelas;
    }

    public void setAsignacionCatedraticoEscuelas(Set<AsignacionCatedraticoEscuela> asignacionCatedraticoEscuelas) {
        this.asignacionCatedraticoEscuelas = asignacionCatedraticoEscuelas;
    }

    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "catedratico"
    )
    public Set<AsignacionCatedraticoHorario> getAsignacionCatedraticoHorarios() {
        return this.asignacionCatedraticoHorarios;
    }

    public void setAsignacionCatedraticoHorarios(Set<AsignacionCatedraticoHorario> asignacionCatedraticoHorarios) {
        this.asignacionCatedraticoHorarios = asignacionCatedraticoHorarios;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
