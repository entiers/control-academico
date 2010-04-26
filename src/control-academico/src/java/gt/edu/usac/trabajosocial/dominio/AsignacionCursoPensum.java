/*
 * @(#)AsignacionCursoPensum.java   29.03.10
 * 
 * Universidad de San Carlos de Guatemala
 * Escuela de Trabajo Social
 * Control Academico
 *
 */



package gt.edu.usac.trabajosocial.dominio;

//Generated 16/03/2010 06:31:00 PM by Hibernate Tools 3.2.1.GA

//~--- JDK imports ------------------------------------------------------------

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * AsignacionCursoPensum generated by hbm2java
 */
@Entity
@Table(
    name = "asignacion_curso_pensum",
    schema = "control"
)
public class AsignacionCursoPensum implements java.io.Serializable {
    private AreaEstudio areaEstudio;
    private Curso curso;
    private short idAsignacionCursoPensum;
    private boolean obligatorio;
    private Pensum pensum;

    public AsignacionCursoPensum() {}

    public AsignacionCursoPensum(short idAsignacionCursoPensum, Curso curso, AreaEstudio areaEstudio, Pensum pensum,
                                 boolean obligatorio) {
        this.idAsignacionCursoPensum = idAsignacionCursoPensum;
        this.curso = curso;
        this.areaEstudio = areaEstudio;
        this.pensum = pensum;
        this.obligatorio = obligatorio;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(
        name = "id_asignacion_curso_pensum",
        unique = true,
        nullable = false
    )
    public short getIdAsignacionCursoPensum() {
        return this.idAsignacionCursoPensum;
    }

    public void setIdAsignacionCursoPensum(short idAsignacionCursoPensum) {
        this.idAsignacionCursoPensum = idAsignacionCursoPensum;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "id_curso",
        nullable = false
    )
    public Curso getCurso() {
        return this.curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "id_area_estudio",
        nullable = false
    )
    public AreaEstudio getAreaEstudio() {
        return this.areaEstudio;
    }

    public void setAreaEstudio(AreaEstudio areaEstudio) {
        this.areaEstudio = areaEstudio;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "id_pensum",
        nullable = false
    )
    public Pensum getPensum() {
        return this.pensum;
    }

    public void setPensum(Pensum pensum) {
        this.pensum = pensum;
    }

    @Column(
        name = "obligatorio",
        nullable = false
    )
    public boolean isObligatorio() {
        return this.obligatorio;
    }

    public void setObligatorio(boolean obligatorio) {
        this.obligatorio = obligatorio;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com