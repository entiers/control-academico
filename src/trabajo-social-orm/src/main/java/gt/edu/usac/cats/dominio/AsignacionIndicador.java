/*
 * @(#)AsignacionIndicador.java   29.03.10
 *
 * Universidad de San Carlos de Guatemala
 * Escuela de Trabajo Social
 * Control Academico
 *
 */



package gt.edu.usac.cats.dominio;

//Generated 16/03/2010 06:31:00 PM by Hibernate Tools 3.2.1.GA

//~--- JDK imports ------------------------------------------------------------

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * AsignacionIndicador generated by hbm2java
 */
@Entity
@Table(
    name = "asignacion_indicador",
    schema = "control"
)
public class AsignacionIndicador implements java.io.Serializable {
    private int idAsignacionIndicador;
    private Indicador indicador;
    private ProgramaCurso programaCurso;

    public AsignacionIndicador() {}

    public AsignacionIndicador(int idAsignacionIndicador, Indicador indicador, ProgramaCurso programaCurso) {
        this.idAsignacionIndicador = idAsignacionIndicador;
        this.indicador = indicador;
        this.programaCurso = programaCurso;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
        name = "id_asignacion_indicador",
        unique = true,
        nullable = false
    )
    public int getIdAsignacionIndicador() {
        return this.idAsignacionIndicador;
    }

    public void setIdAsignacionIndicador(int idAsignacionIndicador) {
        this.idAsignacionIndicador = idAsignacionIndicador;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "id_indicador",
        nullable = false
    )
    public Indicador getIndicador() {
        return this.indicador;
    }

    public void setIndicador(Indicador indicador) {
        this.indicador = indicador;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "id_programa_curso",
        nullable = false
    )
    public ProgramaCurso getProgramaCurso() {
        return this.programaCurso;
    }

    public void setProgramaCurso(ProgramaCurso programaCurso) {
        this.programaCurso = programaCurso;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
