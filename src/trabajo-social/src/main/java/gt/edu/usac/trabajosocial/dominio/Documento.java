/*
 * @(#)Documento.java   29.03.10
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
 * Documento generated by hbm2java
 */
@Entity
@Table(
    name = "documento",
    schema = "control"
)
public class Documento implements java.io.Serializable {
    private Set<AsignacionDocumento> asignacionDocumentos = new HashSet<AsignacionDocumento>(0);
    private String descripcion;
    private short idDocumento;
    private String nombre;

    public Documento() {}

    public Documento(short idDocumento, String nombre) {
        this.idDocumento = idDocumento;
        this.nombre = nombre;
    }

    public Documento(short idDocumento, String nombre, String descripcion,
                     Set<AsignacionDocumento> asignacionDocumentos) {
        this.idDocumento = idDocumento;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.asignacionDocumentos = asignacionDocumentos;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
        name = "id_documento",
        unique = true,
        nullable = false
    )
    public short getIdDocumento() {
        return this.idDocumento;
    }

    public void setIdDocumento(short idDocumento) {
        this.idDocumento = idDocumento;
    }

    @Column(
        name = "nombre",
        nullable = false,
        length = 150
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
        mappedBy = "documento"
    )
    public Set<AsignacionDocumento> getAsignacionDocumentos() {
        return this.asignacionDocumentos;
    }

    public void setAsignacionDocumentos(Set<AsignacionDocumento> asignacionDocumentos) {
        this.asignacionDocumentos = asignacionDocumentos;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
