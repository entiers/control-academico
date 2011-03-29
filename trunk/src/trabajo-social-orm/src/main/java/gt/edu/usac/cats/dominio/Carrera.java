/*
 * @(#)Carrera.java   29.03.10
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

/**
 * Carrera generated by hbm2java
 */
@Entity
@Table(
    name = "carrera",
    schema = "control",
    uniqueConstraints={
        @UniqueConstraint(name="carrera_codigo_uk", columnNames={"codigo"})
    }
)
public class Carrera implements java.io.Serializable {
    private Set<Pensum> pensums = new HashSet<Pensum>(0);
    private Set<AsignacionEstudianteCarrera> asignacionEstudianteCarreras = new HashSet<AsignacionEstudianteCarrera>(0);
    private String codigo;
    private String descripcion;
    private Escuela escuela;
    private short idCarrera;
    private String nombre;
    private short nivel;

    public Carrera() {}

    public Carrera(Escuela escuela, String codigo, String nombre) {        
        this.escuela = escuela;
        this.codigo = codigo;
        this.nombre = nombre;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
        name = "id_carrera",
        unique = true,
        nullable = false
    )
    public short getIdCarrera() {
        return this.idCarrera;
    }

    public void setIdCarrera(short idCarrera) {
        this.idCarrera = idCarrera;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "id_escuela",
        nullable = false
    )
    public Escuela getEscuela() {
        return this.escuela;
    }

    public void setEscuela(Escuela escuela) {
        this.escuela = escuela;
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

    @Column(
        name = "nivel",
        nullable=false
    )
    public short getNivel() {
        return nivel;
    }
    
    public void setNivel(short nivel) {
        this.nivel = nivel;
    }


    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "carrera"
    )
    public Set<Pensum> getPensums() {
        return this.pensums;
    }

    public void setPensums(Set<Pensum> pensums) {
        this.pensums = pensums;
    }

    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "carrera"
    )
    public Set<AsignacionEstudianteCarrera> getAsignacionEstudianteCarreras() {
        return this.asignacionEstudianteCarreras;
    }

    public void setAsignacionEstudianteCarreras(Set<AsignacionEstudianteCarrera> asignacionEstudianteCarreras) {
        this.asignacionEstudianteCarreras = asignacionEstudianteCarreras;
    }


    @Transient
    public String getCodigoNombre(){
        return this.codigo + " - " + this.nombre;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com