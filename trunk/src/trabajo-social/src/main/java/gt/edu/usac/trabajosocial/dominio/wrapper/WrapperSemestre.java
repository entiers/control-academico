/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.dominio.wrapper;

import gt.edu.usac.trabajosocial.dominio.Semestre;
import java.util.Calendar;
import javax.validation.constraints.Min;

/**
 * Contiene los atributos del Semestre que serán ingresados a la BD. El wrapper
 * se utiliza en la páginas de <code>agregarSemestre.htm</code>
 *
 * @author Mario Batres
 * @version 1.0
 */
public class WrapperSemestre {
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
    /**
     * Constructor del wrapper, se inicializan los atributos a mostrar en la
     * página de <code>agregarSemestre.htm</code>.
     */
    public WrapperSemestre() {
        this.numero='1';
        this.anio = (short) calendar.get(Calendar.YEAR);
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
    }
//______________________________________________________________________________
    /**
     * @return El año del semestre
     */
    public short getAnio() {
        return anio;
    }

    /**
     * @param anio El año del semestre
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

}
