/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.dominio.busqueda;

import gt.edu.usac.cats.dominio.Curso;
import gt.edu.usac.cats.dominio.Salon;
import gt.edu.usac.cats.dominio.Semestre;

/**
 * <p>Contiene los atributos de busqueda de horarios. Se utiliza en la pagina
 * de <code>buscarHorario.htm</code>.</p>
 *
 * <p>La busqueda de horarios se realiza solo por el salon y semestre que
 * tiene asignado.</p>
 *
 * @author Mario Batres
 * @version 1.0
 */
public class DatosBusquedaHorario {
//______________________________________________________________________________
    private Salon salon;
//______________________________________________________________________________
    private Semestre semestre;
//______________________________________________________________________________
    private Curso curso;
//______________________________________________________________________________
    /**
     * @return pojo de tipo {@link Salon}
     */
    public Salon getSalon() {
        return salon;
    }

    /**
     * @param pojo de tipo {@link Salon}
     */
    public void setSalon(Salon salon) {
        this.salon = salon;
    }

//______________________________________________________________________________
    /**
     * @return the semestre
     */
    public Semestre getSemestre() {
        return semestre;
    }

    /**
     * @param semestre pojo de tipo {@link Semestre}
     */
    public void setSemestre(Semestre semestre) {
        this.semestre = semestre;
    }
//______________________________________________________________________________
    /**
     * @return pojo de tipo {@link Curso}
     */
    public Curso getCurso() {
        return curso;
    }
    /**
     * @param curso pojo de tipo {@link Curso}
     */
    public void setCurso(Curso curso) {
        this.curso = curso;
    }

}
