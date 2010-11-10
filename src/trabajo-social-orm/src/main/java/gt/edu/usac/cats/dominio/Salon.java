/*
 * @(#)Salon.java   29.03.10
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

/**
 * Salon generated by hbm2java
 */
@Entity
@Table(
    name = "salon",
    schema = "control"
)
public class Salon implements java.io.Serializable {
    private Set<Horario> horarios = new HashSet<Horario>(0);
    private short capacidad;
    private String edificio;
    private short idSalon;
    private short numero;

    public Salon() {}

    public Salon(short idSalon, short numero, String edificio, short capacidad) {
        this.idSalon = idSalon;
        this.numero = numero;
        this.edificio = edificio;
        this.capacidad = capacidad;
    }

    public Salon(short idSalon, short numero, String edificio, short capacidad, Set<Horario> horarios) {
        this.idSalon = idSalon;
        this.numero = numero;
        this.edificio = edificio;
        this.capacidad = capacidad;
        this.horarios = horarios;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
        name = "id_salon",
        unique = true,
        nullable = false
    )
    public short getIdSalon() {
        return this.idSalon;
    }

    public void setIdSalon(short idSalon) {
        this.idSalon = idSalon;
    }

    @Column(
        name = "numero",
        nullable = false
    )
    public short getNumero() {
        return this.numero;
    }

    public void setNumero(short numero) {
        this.numero = numero;
    }

    @Column(
        name = "edificio",
        nullable = false,
        length = 3
    )
    public String getEdificio() {
        return this.edificio;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    @Column(
        name = "capacidad",
        nullable = false
    )
    public short getCapacidad() {
        return this.capacidad;
    }

    public void setCapacidad(short capacidad) {
        this.capacidad = capacidad;
    }

    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "salon"
    )
    public Set<Horario> getHorarios() {
        return this.horarios;
    }

    public void setHorarios(Set<Horario> horarios) {
        this.horarios = horarios;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
