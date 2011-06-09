/*
 * @(#)BoletaBanco.java   29.03.10
 *
 * Universidad de San Carlos de Guatemala
 * Escuela de Trabajo Social
 * Control Academico
 *
 */

package gt.edu.usac.cats.dominio;
// Generated 23/03/2011 12:56:07 AM by Hibernate Tools 3.2.1.GA


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

/**
 * BoletaBanco generated by hbm2java
 */
@Entity
@Table(name="boleta_banco"
    ,schema="control"
)
public class BoletaBanco  implements java.io.Serializable {
    private int idBoletaBanco;
    private Semestre semestre;
    private Estudiante estudiante;
    private Curso curso;
    private int ordenPago;
    private int numeroBoleta;
    private short tipoRubro;
    private short varianteRubro;
    private Date fechaPago;

    public BoletaBanco() {
    }

    public BoletaBanco(int idBoletaBanco, Semestre semestre, Estudiante estudiante, Curso curso, int ordenPago, int numeroBoleta, short tipoRubro, short varianteRubro, Date fechaPago) {
       this.idBoletaBanco = idBoletaBanco;
       this.semestre = semestre;
       this.estudiante = estudiante;
       this.curso = curso;
       this.ordenPago = ordenPago;
       this.numeroBoleta = numeroBoleta;
       this.tipoRubro = tipoRubro;
       this.varianteRubro = varianteRubro;
       this.fechaPago = fechaPago;
    }
   
    @Id 
    @Column(name="id_boleta_banco", unique=true, nullable=false)
    public int getIdBoletaBanco() {
        return this.idBoletaBanco;
    }
    
    public void setIdBoletaBanco(int idBoletaBanco) {
        this.idBoletaBanco = idBoletaBanco;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_semestre", nullable=false)
    public Semestre getSemestre() {
        return this.semestre;
    }
    
    public void setSemestre(Semestre semestre) {
        this.semestre = semestre;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_estudiante", nullable=false)
    public Estudiante getEstudiante() {
        return this.estudiante;
    }
    
    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_curso", nullable=false)
    public Curso getCurso() {
        return this.curso;
    }
    
    public void setCurso(Curso curso) {
        this.curso = curso;
    }
    
    @Column(name="orden_pago", nullable=false)
    public int getOrdenPago() {
        return this.ordenPago;
    }
    
    public void setOrdenPago(int ordenPago) {
        this.ordenPago = ordenPago;
    }
    
    @Column(name="numero_boleta", nullable=false)
    public int getNumeroBoleta() {
        return this.numeroBoleta;
    }
    
    public void setNumeroBoleta(int numeroBoleta) {
        this.numeroBoleta = numeroBoleta;
    }
    
    @Column(name="tipo_rubro", nullable=false)
    @Type(
        type = "gt.edu.usac.cats.enums.GenericEnumUserType",
        parameters = {
            @Parameter(
                name = "enumClass",
                value = "gt.edu.usac.cats.enums.TipoRubro"
            ),
            @Parameter(
                name = "identifierMethod",
                value = "toInt"
            ),
            @Parameter(
                name = "valueOfMethod",
                value = "fromInt"
            )
        }
    )
    public short getTipoRubro() {
        return this.tipoRubro;
    }
    
    public void setTipoRubro(short tipoRubro) {
        this.tipoRubro = tipoRubro;
    }
    
    @Column(name="variante_rubro", nullable=false)
    @Type(
        type = "gt.edu.usac.cats.enums.GenericEnumUserType",
        parameters = {
            @Parameter(
                name = "enumClass",
                value = "gt.edu.usac.cats.enums.VarianteRubro"
            ),
            @Parameter(
                name = "identifierMethod",
                value = "toInt"
            ),
            @Parameter(
                name = "valueOfMethod",
                value = "fromInt"
            )
        }
    )
    public short getVarianteRubro() {
        return this.varianteRubro;
    }
    
    public void setVarianteRubro(short varianteRubro) {
        this.varianteRubro = varianteRubro;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="fecha_pago", nullable=false, length=13)
    public Date getFechaPago() {
        return this.fechaPago;
    }
    
    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

}

