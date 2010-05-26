/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.trabajosocial.dominio.wrapper;


import gt.edu.usac.trabajosocial.dominio.Semestre;
import java.util.Calendar;
import javax.validation.constraints.Min;



/**
 *
 * @author Mario Batres
 * @version 1.0
 */
public class WrapperSemestre {

    private static Calendar calendar;

    static{
        calendar = Calendar.getInstance();
    }


    @Min(value=2000, message="{validacion.minimo}")
    private short anio;

    //@NotEmpty(message = "{validacion.campoObligatorio}")
    //@Pattern(regexp = "|[1-2]{1}", message = "{validacion.carneInvalido}")
    private char numero;

    public WrapperSemestre() {
        this.numero='1';
        this.anio = (short) calendar.get(Calendar.YEAR);
    }

//______________________________________________________________________________
    public void agregarWrapper(Semestre semestre) {
        this.setAnio(semestre.getAnio());
        this.setNumero(semestre.getNumero());

    }

//______________________________________________________________________________
    public void quitarWrapper(Semestre semestre) {
        semestre.setAnio(this.getAnio());
        semestre.setNumero(this.getNumero());
    }
//______________________________________________________________________________
    /**
     * @return the anio
     */
    public short getAnio() {
        return anio;
    }

    /**
     * @param anio the anio to set
     */
    public void setAnio(short anio) {
        this.anio = anio;
    }

    /**
     * @return the numero
     */
    public char getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(char numero) {
        this.numero = numero;
    }

}
