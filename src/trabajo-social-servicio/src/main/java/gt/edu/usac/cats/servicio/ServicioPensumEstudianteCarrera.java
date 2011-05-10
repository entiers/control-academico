/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.servicio;

import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.Carrera;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.Pensum;
import gt.edu.usac.cats.dominio.PensumEstudianteCarrera;
import java.util.List;
import org.springframework.dao.DataAccessException;

/**
 * <p>Contiene los m&eacute;todos que permiten el manejo de la informaci&oacute;n relacionada
 * con la asignaci&oacute;n de un pensum con estudiante y carrera en la base de datos.</p>
 *
 * @author Mario Batres
 * @version 1.0
 */
public interface ServicioPensumEstudianteCarrera extends ServicioGeneral {
//______________________________________________________________________________
    /**
     * Obtiene el pensum vlido que tiene asignado un estudiante.
     *
     * @param estudiante Objeto de tipo {@link Estudiante}
     * @param carrera  Objeto de tipo {@link Carrera}
     *
     * @throws DataAccessException Si ocurri&oacute; un error de acceso a datos
     *
     * @return Objeto de tipo {@link PensumEstudianteCarrera}
     */
    PensumEstudianteCarrera getPensumEstudianteCarreraValido(Estudiante estudiante, Carrera carrera)
            throws DataAccessException;

//______________________________________________________________________________
    /**
     * Obitene el pensum v&aacute;lido que tiene un estudiante seg&uacute;n la carrera que tenga asignada.
     *
     * @param asignacionEstudianteCarrera Objeto de tipo {@link AsignacionEstudianteCarrera}
     *
     * @throws DataAccessException Si ocurri&oacute; un error de acceso a datos
     *
     * @return Objeto de tipo {@link PensumEstudianteCarrera}
     */
    PensumEstudianteCarrera getPensumEstudianteCarreraValido(AsignacionEstudianteCarrera asignacionEstudianteCarrera)
            throws DataAccessException;

//______________________________________________________________________________
    /**
     * Obtiene los pensums asignados no v&aacute;lidos de un estudiante.
     *
     * @param estudiante Objeto de tipo {@link Estudiante}
     *
     * @throws DataAccessException Si ocurri&oacute; un error de acceso a datos
     *
     * @return Lista de objetos de tipo {@link PensumEstudianteCarrera}
     */
    List <PensumEstudianteCarrera> getListadoPensumEstudianteCarreraNoValidos(Estudiante estudiante, Carrera carrera)
            throws DataAccessException;

//______________________________________________________________________________
    /**
     * Obtiene los pensums asignados no v&aacute;lidos de un estudiante seg&uacute;n la carrera que tenga asignada.
     *
     * @param asignacionEstudianteCarrera Objeto de tipo {@link AsignacionEstudianteCarrera}
     *
     * @throws DataAccessException Si ocurri&oacute; un error de acceso a datos
     *
     * @return Lista de objetos de tipo {@link PensumEstudianteCarrera}
     */
    List <PensumEstudianteCarrera> getListadoPensumEstudianteCarreraNoValidos(AsignacionEstudianteCarrera asignacionEstudianteCarrera)
            throws DataAccessException;

//______________________________________________________________________________
    /**
     * Obtiene los pensusm no asignados de un estudiante por la carrera que tenga asignada.
     *
     * @param asignacionEstudianteCarrera Objeto de tipo {@link AsignacionEstudianteCarrera}
     *
     * @throws DataAccessException Si ocurri&oacute; un error de acceso a datos
     */
    List <Pensum> getPensumsNoAsignadosAEstudianteCarrera(AsignacionEstudianteCarrera asignacionEstudianteCarrera)
            throws DataAccessException;

}
