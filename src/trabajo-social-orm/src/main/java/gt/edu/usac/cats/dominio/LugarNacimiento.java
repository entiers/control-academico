package gt.edu.usac.cats.dominio;

// Generated Dec 6, 2010 5:32:53 PM by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

/**
 * LugarNacimiento generated by hbm2java
 */
@Entity
@Table(name="lugar_nacimiento"
    ,schema="control"
    , uniqueConstraints = @UniqueConstraint(columnNames="codigo")
)
public class LugarNacimiento  implements java.io.Serializable {


     private short idLugarNacimiento;
     private short codigo;
     private String nombre;
     private Set<Estudiante> estudiantes = new HashSet<Estudiante>(0);

    public LugarNacimiento() {
        this.idLugarNacimiento = 0;
    }


    public LugarNacimiento(short idLugarNacimiento, short codigo) {
        this.idLugarNacimiento = idLugarNacimiento;
        this.codigo = codigo;
    }
    public LugarNacimiento(short idLugarNacimiento, short codigo, String nombre, Set<Estudiante> estudiantes) {
       this.idLugarNacimiento = idLugarNacimiento;
       this.codigo = codigo;
       this.nombre = nombre;
       this.estudiantes = estudiantes;
    }

     @Id

    @Column(name="id_lugar_nacimiento", unique=true, nullable=false)
    public short getIdLugarNacimiento() {
        return this.idLugarNacimiento;
    }

    public void setIdLugarNacimiento(short idLugarNacimiento) {
        this.idLugarNacimiento = idLugarNacimiento;
    }

    @Column(name="codigo", unique=true, nullable=false)
    public short getCodigo() {
        return this.codigo;
    }

    public void setCodigo(short codigo) {
        this.codigo = codigo;
    }

    @Column(name="nombre", length=50)
    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="lugarNacimiento")
    public Set<Estudiante> getEstudiantes() {
        return this.estudiantes;
    }

    public void setEstudiantes(Set<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }

//______________________________________________________________________________
    @Transient
    public String getCodigoNombre(){
        return this.codigo + " - " + (this.nombre == null ? "" : this.nombre);
    }
}


