/*
 * @(#)Curso.java   29.03.10
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
import javax.persistence.Transient;

/**
 * Curso generated by hbm2java
 */
@Entity
@Table(
    name = "curso",
    schema = "control"
)
public class Curso implements java.io.Serializable {
    private short idCurso;
     private String codigo;
     private String nombre;
     private Set<ProgramaCurso> programaCursos = new HashSet<ProgramaCurso>(0);
     private Set<CursoAprobado> cursoAprobados = new HashSet<CursoAprobado>(0);
     private Set<AsignacionCursoPensum> asignacionCursoPensums = new HashSet<AsignacionCursoPensum>(0);
     private Set<Horario> horarios = new HashSet<Horario>(0);
     private Set<ConteoAsignacion> conteoAsignacions = new HashSet<ConteoAsignacion>(0);


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_curso", unique=true, nullable=false)
    public short getIdCurso() {
        return this.idCurso;
    }

    public void setIdCurso(short idCurso) {
        this.idCurso = idCurso;
    }

    @Column(name="codigo", nullable=false, length=15)
    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Column(name="nombre", nullable=false, length=50)
    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="curso")
    public Set<ProgramaCurso> getProgramaCursos() {
        return this.programaCursos;
    }

    public void setProgramaCursos(Set<ProgramaCurso> programaCursos) {
        this.programaCursos = programaCursos;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="curso")
    public Set<CursoAprobado> getCursoAprobados() {
        return this.cursoAprobados;
    }

    public void setCursoAprobados(Set<CursoAprobado> cursoAprobados) {
        this.cursoAprobados = cursoAprobados;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="curso")
    public Set<AsignacionCursoPensum> getAsignacionCursoPensums() {
        return this.asignacionCursoPensums;
    }

    public void setAsignacionCursoPensums(Set<AsignacionCursoPensum> asignacionCursoPensums) {
        this.asignacionCursoPensums = asignacionCursoPensums;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="curso")
    public Set<Horario> getHorarios() {
        return this.horarios;
    }

    public void setHorarios(Set<Horario> horarios) {
        this.horarios = horarios;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="curso")
    public Set<ConteoAsignacion> getConteoAsignacions() {
        return this.conteoAsignacions;
    }

    public void setConteoAsignacions(Set<ConteoAsignacion> conteoAsignacions) {
        this.conteoAsignacions = conteoAsignacions;
    }

    /**
     * Concatena el codigo con el nombre.  Es de utilizada para la visualizaci&oacute;n
     * en las vistas.  Este atributo <b>NO</b> pertenece a la BD.
     *
     * @return Cadena de car&aacute;cteres con la concatenaci&oacute;n del codigo y el nombre.
     */
    @Transient
    public String getCodigoNombre(){
        return this.codigo + " - " + this.nombre;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
