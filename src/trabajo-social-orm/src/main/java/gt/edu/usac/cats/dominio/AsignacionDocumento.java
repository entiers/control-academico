/*
 * @(#)AsignacionDocumento.java   29.03.10
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
 * AsignacionDocumento generated by hbm2java
 */
@Entity
@Table(
    name = "asignacion_documento",
    schema = "control"
)
public class AsignacionDocumento implements java.io.Serializable {
    private Documento documento;
    private Estudiante estudiante;
    private short idAsignacionDocumento;

    public AsignacionDocumento() {}

    public AsignacionDocumento(short idAsignacionDocumento, Documento documento, Estudiante estudiante) {
        this.idAsignacionDocumento = idAsignacionDocumento;
        this.documento = documento;
        this.estudiante = estudiante;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
        name = "id_asignacion_documento",
        unique = true,
        nullable = false
    )
    public short getIdAsignacionDocumento() {
        return this.idAsignacionDocumento;
    }

    public void setIdAsignacionDocumento(short idAsignacionDocumento) {
        this.idAsignacionDocumento = idAsignacionDocumento;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "id_documento",
        nullable = false
    )
    public Documento getDocumento() {
        return this.documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "id_estudiante",
        nullable = false
    )
    public Estudiante getEstudiante() {
        return this.estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
