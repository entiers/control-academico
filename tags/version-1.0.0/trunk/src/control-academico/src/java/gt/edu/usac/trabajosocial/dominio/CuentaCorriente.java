/*
 * @(#)CuentaCorriente.java   13.04.10
 * 
 * Universidad de San Carlos de Guatemala
 * Escuela de Trabajo Social
 * Control Academico
 *
 */



package gt.edu.usac.trabajosocial.dominio;

//Generated 13/04/2010 08:26:39 PM by Hibernate Tools 3.2.1.GA

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

/**
 * CuentaCorriente generated by hbm2java
 */
@Entity
@Table(
    name = "cuenta_corriente",
    schema = "control"
)
public class CuentaCorriente implements java.io.Serializable {
    private Set<Movimiento> movimientos = new HashSet<Movimiento>(0);
    private boolean activa;
    private String codigo;
    private Estudiante estudiante;
    private int idCuentaCorriente;

    public CuentaCorriente() {}

    public CuentaCorriente(int idCuentaCorriente, Estudiante estudiante, String codigo, boolean activa) {
        this.idCuentaCorriente = idCuentaCorriente;
        this.estudiante = estudiante;
        this.codigo = codigo;
        this.activa = activa;
    }

    public CuentaCorriente(int idCuentaCorriente, Estudiante estudiante, String codigo, boolean activa,
                           Set<Movimiento> movimientos) {
        this.idCuentaCorriente = idCuentaCorriente;
        this.estudiante = estudiante;
        this.codigo = codigo;
        this.activa = activa;
        this.movimientos = movimientos;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
        name = "id_cuenta_corriente",
        unique = true,
        nullable = false
    )
    public int getIdCuentaCorriente() {
        return this.idCuentaCorriente;
    }

    public void setIdCuentaCorriente(int idCuentaCorriente) {
        this.idCuentaCorriente = idCuentaCorriente;
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
        name = "activa",
        nullable = false
    )
    public boolean isActiva() {
        return this.activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "cuentaCorriente"
    )
    public Set<Movimiento> getMovimientos() {
        return this.movimientos;
    }

    public void setMovimientos(Set<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
