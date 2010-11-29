/*
 * @(#)Situacion.java   28.11.10
 *
 * Universidad de San Carlos de Guatemala
 * Escuela de Trabajo Social
 * Control Academico
 *
 */

package gt.edu.usac.cats.dominio;

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
 *
 * Situacion
 */
@Entity
@Table(
    name = "situacion",
    schema = "control",
    uniqueConstraints={
        @UniqueConstraint(name="situacion_codigo_uk", columnNames={"codigo"})
    }
)
public class Situacion implements java.io.Serializable {
    private int idSituacion;
    private short codigo;
    private String nombre;
    private String descripcion;
    private Set<AsignacionEstudianteCarrera> asignacionEstudianteCarreras = new HashSet<AsignacionEstudianteCarrera>(0);


    public Situacion(){}
//______________________________________________________________________________
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
        name = "id_situacion",
        unique = true,
        nullable = false
    )
    public int getIdSituacion() {
        return idSituacion;
    }

    public void setIdSituacion(int idSituacion) {
        this.idSituacion = idSituacion;
    }
//______________________________________________________________________________
    @Column(
        name = "codigo",
        nullable = false,
        unique = true
    )
    public short getCodigo() {
        return codigo;
    }

    public void setCodigo(short codigo) {
        this.codigo = codigo;
    }
//______________________________________________________________________________
    @Column(
        name = "nombre",
        nullable = false,
        length = 50
    )
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
//______________________________________________________________________________
    @Column(
        name = "descripcion",
        length = 250
    )
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
//______________________________________________________________________________
    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "situacion"
    )
    public Set<AsignacionEstudianteCarrera> getAsignacionEstudianteCarreras() {
        return this.asignacionEstudianteCarreras;
    }

    public void setAsignacionEstudianteCarreras(Set<AsignacionEstudianteCarrera> asignacionEstudianteCarreras) {
        this.asignacionEstudianteCarreras = asignacionEstudianteCarreras;
    }
//______________________________________________________________________________
}
