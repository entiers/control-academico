/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.dominio.wrapper;

import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import java.io.Serializable;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Contiene los atributos y validaciones para modificar un objeto de tipo {@link AsignacionEstudianteCarrera}
 * desde las vistas y controladores.
 *
 * @author Mario Batres
 * @version 1.0
 */
public class WrapperModificarAsignacionEstudianteCarrera implements Serializable{
//______________________________________________________________________________
    private Integer idAsignacionEstudianteCarrera;
//______________________________________________________________________________    
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date fechaCierre;
//______________________________________________________________________________

    private boolean inscrito;

//______________________________________________________________________________

    
    public WrapperModificarAsignacionEstudianteCarrera() {
        this.fechaCierre = new Date();
        this.inscrito = true;
        this.idAsignacionEstudianteCarrera = 0;
    }
//______________________________________________________________________________
    /**
     * Otorga los atributos de este objeto al objeto de tipo {@link AsignacionEstudianteCarrera}
     *
     * @param asignacionEstudianteCarrera Objeto de tipo {@link AsignacionEstudianteCarrera}
     */
    public void quitarWrapper(AsignacionEstudianteCarrera asignacionEstudianteCarrera){
        asignacionEstudianteCarrera.setFechaCierre(getFechaCierre());
        asignacionEstudianteCarrera.setInscrito(this.inscrito);
    }
//______________________________________________________________________________
    /**
     * @return the idAsignacionEstudianteCarrera
     */
    public Integer getIdAsignacionEstudianteCarrera() {
        return idAsignacionEstudianteCarrera;
    }

    /**
     * @param idAsignacionEstudianteCarrera the idAsignacionEstudianteCarrera to set
     */
    public void setIdAsignacionEstudianteCarrera(Integer idAsignacionEstudianteCarrera) {
        this.idAsignacionEstudianteCarrera = idAsignacionEstudianteCarrera;
    }
//______________________________________________________________________________
    /**
     * @return the fechaCierre
     */
    public Date getFechaCierre() {
        return fechaCierre;
    }

    /**
     * @param fechaCierre the fechaCierre to set
     */
    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

//______________________________________________________________________________
    public boolean isInscrito() {
        return inscrito;
    }

    public void setInscrito(boolean inscrito) {
        this.inscrito = inscrito;
    }

}
