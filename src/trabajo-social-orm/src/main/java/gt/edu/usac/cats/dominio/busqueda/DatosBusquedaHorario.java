/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.dominio.busqueda;

import gt.edu.usac.cats.dominio.AsignacionCursoPensum;
import gt.edu.usac.cats.dominio.Salon;
import gt.edu.usac.cats.dominio.Semestre;
import java.io.Serializable;

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
public class DatosBusquedaHorario implements Serializable{
    private short idSalon;
//______________________________________________________________________________
    private Salon salon;
//______________________________________________________________________________
    private Semestre semestre;
//______________________________________________________________________________
    private AsignacionCursoPensum asignacionCursoPensum;
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
    public AsignacionCursoPensum getAsignacionCursoPensum() {
        return asignacionCursoPensum;
    }
    /**
     * @param curso pojo de tipo {@link Curso}
     */
    public void setAsignacionCursoPensum(AsignacionCursoPensum asignacionCursoPensum) {
        this.asignacionCursoPensum = asignacionCursoPensum;
    }

    public short getIdSalon() {
        return idSalon;
    }

    public void setIdSalon(short idSalon) {
        this.idSalon = idSalon;
        this.salon = new Salon();
        this.salon.setIdSalon(idSalon);
    }
    
    

}
