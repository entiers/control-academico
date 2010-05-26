/*
 * @(#)Movimiento.java   13.04.10
 * 
 * Universidad de San Carlos de Guatemala
 * Escuela de Trabajo Social
 * Control Academico
 *
 */



package gt.edu.usac.trabajosocial.dominio;

//Generated 13/04/2010 08:26:39 PM by Hibernate Tools 3.2.1.GA

//~--- JDK imports ------------------------------------------------------------

import java.math.BigDecimal;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Movimiento generated by hbm2java
 */
@Entity
@Table(
    name = "movimiento",
    schema = "control"
)
public class Movimiento implements java.io.Serializable {
    private int boleta;
    private String codigoMovimiento;
    private CuentaCorriente cuentaCorriente;
    private Date fecha;
    private int idMovimiento;
    private BigDecimal monto;

    public Movimiento() {}

    public Movimiento(int idMovimiento, CuentaCorriente cuentaCorriente, int boleta, Date fecha,
                      String codigoMovimiento, BigDecimal monto) {
        this.idMovimiento = idMovimiento;
        this.cuentaCorriente = cuentaCorriente;
        this.boleta = boleta;
        this.fecha = fecha;
        this.codigoMovimiento = codigoMovimiento;
        this.monto = monto;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
        name = "id_movimiento",
        unique = true,
        nullable = false
    )
    public int getIdMovimiento() {
        return this.idMovimiento;
    }

    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "id_cuenta_corriente",
        nullable = false
    )
    public CuentaCorriente getCuentaCorriente() {
        return this.cuentaCorriente;
    }

    public void setCuentaCorriente(CuentaCorriente cuentaCorriente) {
        this.cuentaCorriente = cuentaCorriente;
    }

    @Column(
        name = "boleta",
        nullable = false
    )
    public int getBoleta() {
        return this.boleta;
    }

    public void setBoleta(int boleta) {
        this.boleta = boleta;
    }

    @Temporal(TemporalType.DATE)
    @Column(
        name = "fecha",
        nullable = false,
        length = 13
    )
    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Column(
        name = "codigo_movimiento",
        nullable = false,
        length = 10
    )
    public String getCodigoMovimiento() {
        return this.codigoMovimiento;
    }

    public void setCodigoMovimiento(String codigoMovimiento) {
        this.codigoMovimiento = codigoMovimiento;
    }

    @Column(
        name = "monto",
        nullable = false,
        precision = 6
    )
    public BigDecimal getMonto() {
        return this.monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
