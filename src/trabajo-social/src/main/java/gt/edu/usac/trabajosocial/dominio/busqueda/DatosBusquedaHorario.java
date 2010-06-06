/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.trabajosocial.dominio.busqueda;

import gt.edu.usac.trabajosocial.dominio.Salon;
import gt.edu.usac.trabajosocial.dominio.Semestre;

/**
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
     * @return the salon
     */
    public Salon getSalon() {
        return salon;
    }

    /**
     * @param salon the salon to set
     */
    public void setSalon(Salon salon) {
        this.salon = salon;
    }

    /**
     * @return the semestre
     */
    public Semestre getSemestre() {
        return semestre;
    }

    /**
     * @param semestre the semestre to set
     */
    public void setSemestre(Semestre semestre) {
        this.semestre = semestre;
    }

}
