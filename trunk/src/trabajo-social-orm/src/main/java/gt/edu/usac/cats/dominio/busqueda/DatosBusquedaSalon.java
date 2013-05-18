/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.dominio.busqueda;

import java.io.Serializable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * </p>Contiene los atributos de busqueda de salon. Se utiliza
 * en la pagina de <code>buscarSalon.htm</code>.</p>
 *
 * <p>La busqueda de salones se realiza solo por el eficio y numero de salon
 * que tiene asignado.</p>
 *
 * @author Mario Batres
 * @version 1.0
 */
public class DatosBusquedaSalon implements Serializable {
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
//______________________________________________________________________________
    /**
     * @return El edificio que tiene asignado el salon
     */
    public String getEdificio() {
        return edificio;
    }

    /**
     * @param edificio El edificio que tiene asignado el salon
     */
    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }
//______________________________________________________________________________
    /**
     * @return the El numero que tiene asignado el salon
     */
    public short getNumero() {
        return numero;
    }

    /**
     * @param numero El numero que tiene asignado el salon
     */
    public void setNumero(short numero) {
        this.numero = numero;
    }
}
