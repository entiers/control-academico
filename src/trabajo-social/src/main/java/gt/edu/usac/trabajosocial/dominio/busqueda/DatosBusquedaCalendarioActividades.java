/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.dominio.busqueda;

import gt.edu.usac.trabajosocial.dominio.CalendarioActividades;
import gt.edu.usac.trabajosocial.dominio.Semestre;

/**
 * <p>Contiene los atributos de busqueda para Calendario de Actividades.  Se utiliza
 * en la página de <code>buscarCalendarioActividades.htm</code>.</p>
 *
 * <p>La busqueda de calendario de actividades se realiza solo por el semestre
 * que tiene asignado {@link Semestre}.</p>
 *
 * @see CalendarioActividades
 * @author Mario Batres
 * @version 1.0
 */
public class DatosBusquedaCalendarioActividades{
//______________________________________________________________________________
    private Semestre semestre;
//______________________________________________________________________________
    /**
     * @return pojo de tipo {@link Semestre}
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
