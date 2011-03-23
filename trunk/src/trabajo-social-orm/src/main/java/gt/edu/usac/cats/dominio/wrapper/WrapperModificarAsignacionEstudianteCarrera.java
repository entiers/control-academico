/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.dominio.wrapper;

import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import java.util.Date;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
public class WrapperModificarAsignacionEstudianteCarrera {
//______________________________________________________________________________
    private Integer idAsignacionEstudianteCarrera;
//______________________________________________________________________________    
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date fechaCierre;
//______________________________________________________________________________

    public WrapperModificarAsignacionEstudianteCarrera() {
        this.fechaCierre = new Date();
    }
//______________________________________________________________________________
    public void quitarWrapper(AsignacionEstudianteCarrera asignacionEstudianteCarrera){
        asignacionEstudianteCarrera.setFechaCierre(getFechaCierre());
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

    

}
