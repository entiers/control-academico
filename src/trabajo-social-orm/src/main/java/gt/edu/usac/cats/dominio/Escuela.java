/*
 * @(#)Escuela.java   29.03.10
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
import javax.persistence.UniqueConstraint;

/**
 * Escuela generated by hbm2java
 */
@Entity
@Table(
    name = "escuela",
    schema = "control",
    uniqueConstraints={
        @UniqueConstraint(name="escuela_codigo_uk", columnNames={"codigo"})
    }
)
public class Escuela implements java.io.Serializable {
    private Set<Carrera> carreras = new HashSet<Carrera>(0);
    private Set<AsignacionCatedraticoEscuela> asignacionCatedraticoEscuelas =
        new HashSet<AsignacionCatedraticoEscuela>(0);
    private String codigo;
    private String descripcion;
    private short idEscuela;
    private String nombre;

    public Escuela() {
        this.idEscuela = 0;
    }

    public Escuela(short idEscuela, String codigo, String nombre) {
        this.idEscuela = idEscuela;
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public Escuela(short idEscuela, String codigo, String nombre, String descripcion, Set<Carrera> carreras,
                   Set<AsignacionCatedraticoEscuela> asignacionCatedraticoEscuelas) {
        this.idEscuela = idEscuela;
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.carreras = carreras;
        this.asignacionCatedraticoEscuelas = asignacionCatedraticoEscuelas;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
        name = "id_escuela",
        unique = true,
        nullable = false
    )
    public short getIdEscuela() {
        return this.idEscuela;
    }

    public void setIdEscuela(short idEscuela) {
        this.idEscuela = idEscuela;
    }

    @Column(
        name = "codigo",
        nullable = false,
        length = 20
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
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "escuela"
    )
    public Set<Carrera> getCarreras() {
        return this.carreras;
    }

    public void setCarreras(Set<Carrera> carreras) {
        this.carreras = carreras;
    }

    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "escuela"
    )
    public Set<AsignacionCatedraticoEscuela> getAsignacionCatedraticoEscuelas() {
        return this.asignacionCatedraticoEscuelas;
    }

    public void setAsignacionCatedraticoEscuelas(Set<AsignacionCatedraticoEscuela> asignacionCatedraticoEscuelas) {
        this.asignacionCatedraticoEscuelas = asignacionCatedraticoEscuelas;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
