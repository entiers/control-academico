/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.dominio.busqueda;

import gt.edu.usac.trabajosocial.dominio.Semestre;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
public class DatosBusquedaCalendarioActividades{

    private Semestre semestre;

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
