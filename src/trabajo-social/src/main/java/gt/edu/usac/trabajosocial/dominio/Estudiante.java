/*
 * @(#)Estudiante.java   29.03.10
 * 
 * Universidad de San Carlos de Guatemala
 * Escuela de Trabajo Social
 * Control Academico
 *
 */



package gt.edu.usac.trabajosocial.dominio;

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
import javax.persistence.Transient;

/**
 * Estudiante generated by hbm2java
 */
@Entity
@Table(
    name = "estudiante",
    schema = "control"
)
public class Estudiante implements java.io.Serializable {
    private Set<CursoAprobado> cursoAprobados = new HashSet<CursoAprobado>(0);
    private Set<Desasignacion> desasignacions = new HashSet<Desasignacion>(0);
    private Set<CursoExtra> cursoExtras = new HashSet<CursoExtra>(0);
    private Set<ConteoAsignacion> conteoAsignacions = new HashSet<ConteoAsignacion>(0);
    private Set<Asignacion> asignacions = new HashSet<Asignacion>(0);
    private Set<AsignacionEstudianteCarrera> asignacionEstudianteCarreras = new HashSet<AsignacionEstudianteCarrera>(0);
    private Set<AsignacionDocumento> asignacionDocumentos = new HashSet<AsignacionDocumento>(0);
    private Set<CuentaCorriente> cuentaCorrientes = new HashSet<CuentaCorriente>(0);
    private int idEstudiante;
    private boolean requisitos;
    private Usuario usuario;
    private String carne;
    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;
    private String celular;
    private String email;
    private Date fechaNacimiento;
    private String password;


    public Estudiante() {}
//______________________________________________________________________________
    public Estudiante(int idEstudiante, Usuario usuario, String carne, String nombre, String apellido,
                      Date fechaNacimiento, boolean requisitos) {
        this.idEstudiante = idEstudiante;
        this.usuario = usuario;
        this.carne = carne;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.requisitos = requisitos;
    }
//______________________________________________________________________________
    public Estudiante(int idEstudiante, Usuario usuario, String carne, String nombre, String apellido, String direccion,
                      String telefono, String celular, String email, Date fechaNacimiento, boolean requisitos,
                      Set<CursoAprobado> cursoAprobados, Set<Desasignacion> desasignacions,
                      Set<Asignacion> asignacions, Set<AsignacionDocumento> asignacionDocumentos,
                      Set<CursoExtra> cursoExtras, Set<AsignacionEstudianteCarrera> asignacionEstudianteCarreras,
                      Set<ConteoAsignacion> conteoAsignacions) {
        this.idEstudiante = idEstudiante;
        this.usuario = usuario;
        this.carne = carne;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.telefono = telefono;
        this.celular = celular;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.requisitos = requisitos;
        this.cursoAprobados = cursoAprobados;
        this.desasignacions = desasignacions;
        this.asignacions = asignacions;
        this.asignacionDocumentos = asignacionDocumentos;
        this.cursoExtras = cursoExtras;
        this.asignacionEstudianteCarreras = asignacionEstudianteCarreras;
        this.conteoAsignacions = conteoAsignacions;
    }
//______________________________________________________________________________
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
        name = "id_estudiante",
        unique = true,
        nullable = false
    )
    public int getIdEstudiante() {
        return this.idEstudiante;
    }
//______________________________________________________________________________
    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }
//______________________________________________________________________________
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "id_usuario",
        nullable = false
    )
    public Usuario getUsuario() {
        return this.usuario;
    }
//______________________________________________________________________________
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
//______________________________________________________________________________
    @Column(
        name = "carne",
        nullable = false
    )
    public String getCarne() {
        return this.carne;
    }
//______________________________________________________________________________
    public void setCarne(String carne) {
        this.carne = carne;
    }
//______________________________________________________________________________
    @Column(
        name = "nombre",
        nullable = false,
        length = 50
    )
    public String getNombre() {
        return this.nombre;
    }
//______________________________________________________________________________
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
//______________________________________________________________________________
    @Column(
        name = "apellido",
        nullable = false,
        length = 50
    )
    public String getApellido() {
        return this.apellido;
    }
//______________________________________________________________________________
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
//______________________________________________________________________________
    @Column(
        name = "direccion",
        length = 200
    )
    public String getDireccion() {
        return this.direccion;
    }
//______________________________________________________________________________
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
//______________________________________________________________________________
    @Column(
        name = "telefono",
        length = 10
    )
    public String getTelefono() {
        return this.telefono;
    }
//______________________________________________________________________________
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
//______________________________________________________________________________
    @Column(
        name = "celular",
        length = 10
    )
    public String getCelular() {
        return this.celular;
    }
//______________________________________________________________________________
    public void setCelular(String celular) {
        this.celular = celular;
    }
//______________________________________________________________________________
    @Column(
        name = "email",
        length = 100
    )
    public String getEmail() {
        return this.email;
    }
//______________________________________________________________________________
    public void setEmail(String email) {
        this.email = email;
    }
//______________________________________________________________________________
    @Temporal(TemporalType.DATE)
    @Column(
        name = "fecha_nacimiento",
        nullable = false,
        length = 13
    )
    public Date getFechaNacimiento() {
        return this.fechaNacimiento;
    }
//______________________________________________________________________________
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
//______________________________________________________________________________
    @Column(
        name = "requisitos",
        nullable = false
    )
    public boolean isRequisitos() {
        return this.requisitos;
    }
//______________________________________________________________________________
    public void setRequisitos(boolean requisitos) {
        this.requisitos = requisitos;
    }
//______________________________________________________________________________
    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "estudiante"
    )
    public Set<CursoAprobado> getCursoAprobados() {
        return this.cursoAprobados;
    }
//______________________________________________________________________________
    public void setCursoAprobados(Set<CursoAprobado> cursoAprobados) {
        this.cursoAprobados = cursoAprobados;
    }
//______________________________________________________________________________
    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "estudiante"
    )
    public Set<Desasignacion> getDesasignacions() {
        return this.desasignacions;
    }
//______________________________________________________________________________
    public void setDesasignacions(Set<Desasignacion> desasignacions) {
        this.desasignacions = desasignacions;
    }
//______________________________________________________________________________
    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "estudiante"
    )
    public Set<Asignacion> getAsignacions() {
        return this.asignacions;
    }
//______________________________________________________________________________
    public void setAsignacions(Set<Asignacion> asignacions) {
        this.asignacions = asignacions;
    }
//______________________________________________________________________________
    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "estudiante"
    )
    public Set<AsignacionDocumento> getAsignacionDocumentos() {
        return this.asignacionDocumentos;
    }
//______________________________________________________________________________
    public void setAsignacionDocumentos(Set<AsignacionDocumento> asignacionDocumentos) {
        this.asignacionDocumentos = asignacionDocumentos;
    }
//______________________________________________________________________________
    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "estudiante"
    )
    public Set<CuentaCorriente> getCuentaCorrientes() {
        return this.cuentaCorrientes;
    }
//______________________________________________________________________________
    public void setCuentaCorrientes(Set<CuentaCorriente> cuentaCorrientes) {
        this.cuentaCorrientes = cuentaCorrientes;
    }
//______________________________________________________________________________
    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "estudiante"
    )
    public Set<CursoExtra> getCursoExtras() {
        return this.cursoExtras;
    }
//______________________________________________________________________________
    public void setCursoExtras(Set<CursoExtra> cursoExtras) {
        this.cursoExtras = cursoExtras;
    }
//______________________________________________________________________________
    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "estudiante"
    )
    public Set<AsignacionEstudianteCarrera> getAsignacionEstudianteCarreras() {
        return this.asignacionEstudianteCarreras;
    }
//______________________________________________________________________________
    public void setAsignacionEstudianteCarreras(Set<AsignacionEstudianteCarrera> asignacionEstudianteCarreras) {
        this.asignacionEstudianteCarreras = asignacionEstudianteCarreras;
    }
//______________________________________________________________________________
    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "estudiante"
    )
    public Set<ConteoAsignacion> getConteoAsignacions() {
        return this.conteoAsignacions;
    }
//______________________________________________________________________________
    public void setConteoAsignacions(Set<ConteoAsignacion> conteoAsignacions) {
        this.conteoAsignacions = conteoAsignacions;
    }
//______________________________________________________________________________
    @Transient
    public String getPassword() {
        return password;
    }
//______________________________________________________________________________
    public void setPassword(String password) {
        this.password = password;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
