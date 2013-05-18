/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.dominio.wrapper;

import gt.edu.usac.cats.dominio.Semestre;
import java.io.Serializable;
import java.util.Calendar;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * Contiene los atributos del Semestre que seran ingresados a la BD. El wrapper
 * se utiliza en la paginas de <code>agregarSemestre.htm</code>
 *
 * @author Mario Batres
 * @version 1.0
 */
public class WrapperSemestre implements Serializable{
//______________________________________________________________________________
    private static Calendar calendar;

    static{
        calendar = Calendar.getInstance();
    }
//______________________________________________________________________________
    @Min(value=2000, message="{validacion.minimo}")
    private short anio;
//______________________________________________________________________________
    private char numero;
//______________________________________________________________________________
    @Size(max = 1000, message = "{validacion.caracteresMaximos}")
    private String observacion;
//______________________________________________________________________________
    private boolean activo;
//______________________________________________________________________________
    /**
     * Constructor del wrapper, se inicializan los atributos a mostrar en la
     * pagina de <code>agregarSemestre.htm</code>.
     */
    public WrapperSemestre() {
        this.numero='1';
        this.anio = (short) calendar.get(Calendar.YEAR);
        this.activo = true;
        this.observacion = "";
    }

//______________________________________________________________________________
    /**
     * Se agrega al wrapper la informacion del pojo de tipo {@link Semestre}.
     *
     * @param curso Pojo de tipo {@link Semestre}
     */
    public void agregarWrapper(Semestre semestre) {
        this.setAnio(semestre.getAnio());
        this.setNumero(semestre.getNumero());
        this.setActivo(semestre.isActivo());
        this.setObservacion(semestre.getObservacion());
    }

//______________________________________________________________________________
    /**
     * Se agrega la informacion del pojo de tipo {@link Semestre} al wrapper.
     *
     * @param curso Pojo de tipo {@link Semestre}
     */
    public void quitarWrapper(Semestre semestre) {
        semestre.setAnio(this.getAnio());
        semestre.setNumero(this.getNumero());
        semestre.setActivo(this.isActivo());
        semestre.setObservacion(this.getObservacion());
    }
//______________________________________________________________________________
    /**
     * @return El anio del semestre
     */
    public short getAnio() {
        return anio;
    }

    /**
     * @param anio El anio del semestre
     */
    public void setAnio(short anio) {
        this.anio = anio;
    }
//______________________________________________________________________________
    /**
     * @return El numero del semestre
     */
    public char getNumero() {
        return numero;
    }

    /**
     * @param numero El numero del semestre
     */
    public void setNumero(char numero) {
        this.numero = numero;
    }

    /**
     * @return the observacion
     */
    public String getObservacion() {
        return observacion;
    }

    /**
     * @param observacion the observacion to set
     */
    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    /**
     * @return the activo
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * @param activo the activo to set
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    

}
