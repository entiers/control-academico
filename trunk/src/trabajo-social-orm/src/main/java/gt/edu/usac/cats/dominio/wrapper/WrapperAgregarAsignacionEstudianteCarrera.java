/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */


package gt.edu.usac.cats.dominio.wrapper;

import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.Carrera;
import gt.edu.usac.cats.dominio.HistorialAsignacionEstudianteCarrera;
import javax.validation.Valid;

/**
 * Contiene los atributos con validaciones que son utilizados para agregar un objeto de tipo
 * {@link AsignacionEstudianteCarrera}.
 *
 * @author Mario Batres
 * @version 1.0
 */
public class WrapperAgregarAsignacionEstudianteCarrera {

//______________________________________________________________________________
    private Carrera carrera;

//______________________________________________________________________________
    @Valid
    private WrapperHistorialAsignacionEstudianteCarrera wrapperHistorialAsignacionEstudianteCarrera;

//______________________________________________________________________________
    public WrapperAgregarAsignacionEstudianteCarrera() {
        wrapperHistorialAsignacionEstudianteCarrera = new  WrapperHistorialAsignacionEstudianteCarrera();        
    }

//______________________________________________________________________________
    /**
     * Agrega los atributos a los objetos de tipo {@link AsignacionEstudianteCarrera} y
     * {@link HistorialAsignacionEstudianteCarrera}
     *
     * @param asignacionEstudianteCarrera Objeto de tipo {@link AsignacionEstudianteCarrera}
     * @param historialAsigancionEstudianteCarrera Objeto de tipo {@link HistorialAsignacionEstudianteCarrera}
     */
    public void quitarWrapper(AsignacionEstudianteCarrera asignacionEstudianteCarrera
            , HistorialAsignacionEstudianteCarrera historialAsigancionEstudianteCarrera){
        asignacionEstudianteCarrera.setCarrera(this.getCarrera());
     
        wrapperHistorialAsignacionEstudianteCarrera.quitarWrapper(historialAsigancionEstudianteCarrera);

    }

    /**
     * @return the carrera
     */
    public Carrera getCarrera() {
        return carrera;
    }

    /**
     * @param carrera the carrera to set
     */
    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }


    /**
     *
     * Este objeto det tipo {@link WrapperHistorialAsignacionEstudianteCarrera}
     * que tiene los otros atributos que son necesario cuando se agrega un ojeto de tipo
     * {@link AsignacionEstudianteCarrera} desde los controladores.
     *
     *
     * @return the wrapperHistorialAsignacionEstudianteCarrera
     */
    public WrapperHistorialAsignacionEstudianteCarrera getWrapperHistorialAsignacionEstudianteCarrera() {
        return wrapperHistorialAsignacionEstudianteCarrera;
    }

    /**
     * @param wrapperHorarioAsignacionEstudianteCarrera the wrapperHistorialAsignacionEstudianteCarrera to set
     */
    public void setWrapperHistorialAsignacionEstudianteCarrera(WrapperHistorialAsignacionEstudianteCarrera wrapperHistorialAsignacionEstudianteCarrera) {
        this.wrapperHistorialAsignacionEstudianteCarrera = wrapperHistorialAsignacionEstudianteCarrera;
    }


}
