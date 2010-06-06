/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.trabajosocial.dominio.busqueda;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
public class DatosBusquedaSalon {
    @NotEmpty(message="{validacion.campoObligatorio}")
    @Size(max = 3, message = "{validacion.caracteresMaximos}")
    private String edificio;

    @Max(value = 50, message="{validacion.maximo}")
    @Min(value = 1, message="{validacion.minimo}")
    private short numero;

    public DatosBusquedaSalon() {
        this.edificio = "";
        this.numero = 1;
    }

    /**
     * @return the edificio
     */
    public String getEdificio() {
        return edificio;
    }

    /**
     * @param edificio the edificio to set
     */
    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    /**
     * @return the numero
     */
    public short getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(short numero) {
        this.numero = numero;
    }






}
