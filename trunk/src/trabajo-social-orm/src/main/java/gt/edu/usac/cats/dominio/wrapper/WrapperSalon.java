/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.dominio.wrapper;

import gt.edu.usac.cats.dominio.Salon;
import java.io.Serializable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Contiene los atributos del Salon que seran ingresados
 * o actualizado a la BD. El wrapper se utiliza en las paginas de
 * <code>agregarSalon.htm</code> y <code>editarSalon.htm</code>.
 *
 * @author Mario Batres
 * @version 1.0
 */
public class WrapperSalon implements Serializable{
//______________________________________________________________________________
    @Max(value = 300, message="{validacion.maximo}")
    @Min(value = 1, message="{validacion.minimo}")
    private short capacidad;
//______________________________________________________________________________
    @NotEmpty(message="{validacion.campoObligatorio}")
    @Size(  max = 3, message = "{validacion.caracteresMaximos}")
    private String edificio;
//______________________________________________________________________________
    @Max(value = 300, message="{validacion.maximo}")
    @Min(value = 1, message="{validacion.minimo}")
    private short numero;
//______________________________________________________________________________
    /**
     * Constructor del wrapper, se inicializan los atributos a mostrar en las
     * paginas de <code>agregarSalon.htm</code> y <code>editarSalon.htm</code>.
     */
    public WrapperSalon() {
        this.capacidad = 1;
        this.edificio = "";
        this.numero = 1;
    }

//______________________________________________________________________________
    /**
     * Se agrega al wrapper la informacion del pojo de tipo {@link Salon}.
     *
     * @param curso Pojo de tipo {@link Salon}
     */
    public void agregarWrapper(Salon salon) {
        this.setCapacidad(salon.getCapacidad());
        this.setEdificio(salon.getEdificio());
        this.setNumero(salon.getNumero());

    }

//______________________________________________________________________________
    /**
     * Se agrega la informacion del pojo de tipo {@link Salon} al wrapper.
     *
     * @param curso Pojo de tipo {@link Salon}
     */
    public void quitarWrapper(Salon salon) {
        salon.setCapacidad(this.capacidad);
        salon.setEdificio(this.edificio);
        salon.setNumero(this.numero);
    }
//______________________________________________________________________________
    /**
     * @return La capacidad del salon
     */
    public short getCapacidad() {
        return capacidad;
    }

    /**
     * @param capacidad La capacidad del salon
     */
    public void setCapacidad(short capacidad) {
        this.capacidad = capacidad;
    }
//______________________________________________________________________________
    /**
     * @return the El edificio del salon
     */
    public String getEdificio() {
        return edificio;
    }

    /**
     * @param edificio El edificio del salon
     */
    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }
//______________________________________________________________________________
    /**
     * @return El numero del salon
     */
    public short getNumero() {
        return numero;
    }

    /**
     * @param numero El numero del salon
     */
    public void setNumero(short numero) {
        this.numero = numero;
    }
}
