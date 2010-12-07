/*
 * @(#)AsignacionCursoPensum.java   29.03.10
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * AsignacionCursoPensum generated by hbm2java
 */
@Entity
@Table(
    name = "asignacion_curso_pensum",
    schema = "control"
)
public class AsignacionCursoPensum implements java.io.Serializable {
     private short idAsignacionCursoPensum;
     private Curso curso;
     private Pensum pensum;
     private boolean obligatorio;
     private Short creditosPracticos;
     private Short creditosPrerrequisito;
     private Short creditosTeoricos;
     private short numeroSemestre;
     private Set<AsignacionCursoPensum> asignacionCursoPensumsForIdCursoPensumPrerequisito = new HashSet<AsignacionCursoPensum>(0);
     private Set<AsignacionCursoPensum> asignacionCursoPensumsForIdCursoPensum = new HashSet<AsignacionCursoPensum>(0);

    public AsignacionCursoPensum() {}

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_asignacion_curso_pensum", unique=true, nullable=false)
    public short getIdAsignacionCursoPensum() {
        return this.idAsignacionCursoPensum;
    }

    public void setIdAsignacionCursoPensum(short idAsignacionCursoPensum) {
        this.idAsignacionCursoPensum = idAsignacionCursoPensum;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_curso", nullable=false)
    public Curso getCurso() {
        return this.curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_pensum", nullable=false)
    public Pensum getPensum() {
        return this.pensum;
    }

    public void setPensum(Pensum pensum) {
        this.pensum = pensum;
    }

    @Column(name="obligatorio", nullable=false)
    public boolean isObligatorio() {
        return this.obligatorio;
    }

    public void setObligatorio(boolean obligatorio) {
        this.obligatorio = obligatorio;
    }

    @Column(name="creditos_practicos")
    public Short getCreditosPracticos() {
        return this.creditosPracticos;
    }

    public void setCreditosPracticos(Short creditosPracticos) {
        this.creditosPracticos = creditosPracticos;
    }

    @Column(name="creditos_prerrequisito")
    public Short getCreditosPrerrequisito() {
        return this.creditosPrerrequisito;
    }

    public void setCreditosPrerrequisito(Short creditosPrerrequisito) {
        this.creditosPrerrequisito = creditosPrerrequisito;
    }

    @Column(name="creditos_teoricos")
    public Short getCreditosTeoricos() {
        return this.creditosTeoricos;
    }

    public void setCreditosTeoricos(Short creditosTeoricos) {
        this.creditosTeoricos = creditosTeoricos;
    }

    @Column(name="numero_semestre", nullable=false)
    public short getNumeroSemestre() {
        return this.numeroSemestre;
    }

    public void setNumeroSemestre(short numeroSemestre) {
        this.numeroSemestre = numeroSemestre;
    }
@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinTable(name="prerrequisito", schema="control", joinColumns = {
        @JoinColumn(name="id_curso_pensum", nullable=false, updatable=false) }, inverseJoinColumns = {
        @JoinColumn(name="id_curso_pensum_prerequisito", nullable=false, updatable=false) })
    public Set<AsignacionCursoPensum> getAsignacionCursoPensumsForIdCursoPensumPrerequisito() {
        return this.asignacionCursoPensumsForIdCursoPensumPrerequisito;
    }

    public void setAsignacionCursoPensumsForIdCursoPensumPrerequisito(Set<AsignacionCursoPensum> asignacionCursoPensumsForIdCursoPensumPrerequisito) {
        this.asignacionCursoPensumsForIdCursoPensumPrerequisito = asignacionCursoPensumsForIdCursoPensumPrerequisito;
    }
@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinTable(name="prerrequisito", schema="control", joinColumns = {
        @JoinColumn(name="id_curso_pensum_prerequisito", nullable=false, updatable=false) }, inverseJoinColumns = {
        @JoinColumn(name="id_curso_pensum", nullable=false, updatable=false) })
    public Set<AsignacionCursoPensum> getAsignacionCursoPensumsForIdCursoPensum() {
        return this.asignacionCursoPensumsForIdCursoPensum;
    }

    public void setAsignacionCursoPensumsForIdCursoPensum(Set<AsignacionCursoPensum> asignacionCursoPensumsForIdCursoPensum) {
        this.asignacionCursoPensumsForIdCursoPensum = asignacionCursoPensumsForIdCursoPensum;
    }



}


//~ Formatted by Jindent --- http://www.jindent.com
