/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.dominio.wrapper;

import gt.edu.usac.cats.dominio.Curso;
import gt.edu.usac.cats.dominio.Pensum;
import java.io.Serializable;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
public class WrapperCursoPensumEquivalencia implements Serializable{

    private Pensum pensumOriginal;

    private Pensum pensumEquivalente;

    private Curso cursoOriginal;

    private Curso cursoEquivalente;

    
    public WrapperCursoPensumEquivalencia() {
        this.pensumEquivalente = new Pensum();
        this.pensumEquivalente.setIdPensum((short) 0);
        this.pensumOriginal = new Pensum();
        this.pensumOriginal.setIdPensum((short) 0);
        this.cursoEquivalente = new Curso();
        this.cursoEquivalente.setIdCurso((short) 0);
        this.cursoOriginal = new Curso();
        this.cursoOriginal.setIdCurso((short) 0);
    }

    
    
    /**
     * @return the pensumOriginal
     */
    public Pensum getPensumOriginal() {
        return pensumOriginal;
    }

    /**
     * @param pensumOriginal the pensumOriginal to set
     */
    public void setPensumOriginal(Pensum pensumOriginal) {
        this.pensumOriginal = pensumOriginal;
    }

    /**
     * @return the pensumEquivalente
     */
    public Pensum getPensumEquivalente() {
        return pensumEquivalente;
    }

    /**
     * @param pensumEquivalente the pensumEquivalente to set
     */
    public void setPensumEquivalente(Pensum pensumEquivalente) {
        this.pensumEquivalente = pensumEquivalente;
    }

    /**
     * @return the cursoOriginal
     */
    public Curso getCursoOriginal() {
        return cursoOriginal;
    }

    /**
     * @param cursoOriginal the cursoOriginal to set
     */
    public void setCursoOriginal(Curso cursoOriginal) {
        this.cursoOriginal = cursoOriginal;
    }

    /**
     * @return the cursoEquivalente
     */
    public Curso getCursoEquivalente() {
        return cursoEquivalente;
    }

    /**
     * @param cursoEquivalente the cursoEquivalente to set
     */
    public void setCursoEquivalente(Curso cursoEquivalente) {
        this.cursoEquivalente = cursoEquivalente;
    }

    
}
