/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.trabajosocial.dominio.busqueda;

import gt.edu.usac.trabajosocial.dominio.Salon;
import gt.edu.usac.trabajosocial.dominio.Semestre;

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

}
