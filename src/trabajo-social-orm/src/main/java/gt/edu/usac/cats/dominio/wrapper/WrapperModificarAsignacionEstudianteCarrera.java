/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.dominio.wrapper;

import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Contiene los atributos y validaciones para modificar un objeto de tipo {@link AsignacionEstudianteCarrera}
 * desde las vistas y controladores.
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
    /**
     * Otorga los atributos de este objeto al objeto de tipo {@link AsignacionEstudianteCarrera}
     *
     * @param asignacionEstudianteCarrera Objeto de tipo {@link AsignacionEstudianteCarrera}
     */
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
