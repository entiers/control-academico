/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.servicio;

import java.util.List;

import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.Carrera;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.HistorialAsignacionEstudianteCarrera;
import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * <p>Contiene los m&eacute;todos que permiten el manejo de la informaci&oacute;n relacionada
 * con la asignaci&oacute;n de un estudiante a una carrera en la base de datos.</p>
 *
 * @author Carlos Solorzano, Mario Batres
 * @version 1.0
 */
public interface ServicioAsignacionEstudianteCarrera extends ServicioGeneral{
//______________________________________________________________________________
    /**
     * <p>&Eacute;ste metodo se encarga de crear un listado de AsignacionEstudianteCarrera, el listado
     * se filtra en base la carrera, el numero de carnet de cada estudiante, en donde este
     * sea igual al anio actual, asi como el estudiante no debe de tener asignaciones previas</p>
     *
     * @param Carrera Contiene los filtros para el listado
     * @return List Listado de AsignacionEstudianteCarrera
     * @throws HibernateException Si ocurrio un error de acceso a datos
     */
    List<AsignacionEstudianteCarrera> getAsignacionEstudianteCarreraPrimerIngreso(Carrera carrera)
            throws HibernateException;
//______________________________________________________________________________
    /**
     * <p>Crear un listado de AsignacionEstudianteCarrera, el listado
     * se filtra en base al estudiante</p>
     *
     * @param estudiante pojo del tipo  {@link Estudiante}
     * @return List Listado de AsignacionEstudianteCarrera
     * @throws HibernateException Si ocurrio un error de acceso a datos
     */
    List<AsignacionEstudianteCarrera> getAsignacionEstudianteCarreraPorEstudiante(Estudiante estudiante)
            throws HibernateException;

//______________________________________________________________________________
    /**
     * &Eacute;ste m&eacute;todo realiza la b&uacute;squeda de las asignaciones con carreras del estudiante
     * que es enviado como param&eacute;tro y su estado en la base de datos.
     *
     * @param estudiante Objeto de tipo {@link Estudiante}
     * @param estado True si las asignaciones son v&aacute;lidas y False si es lo contrario.
     *
     * @return Lista de objetos de tipo {@link AsignacionEstudianteCarrera}
     *
     * @throws DataAccessException Si ocurri&oacute; un error de acceso a datos
     */
    List <AsignacionEstudianteCarrera> getAsignacionEstudianteCarrera(Estudiante estudiante
            , boolean estado) throws DataAccessException;

//______________________________________________________________________________
    /**
     * &Eacute;ste m&eacute;todo devuelve el listado de los objetos que no se ha asignado el estudiante,
     * que es enviado como param&eacute;tro.
     *
     * @param estudiante Objeto de tipo {@link Estudiante}
     *
     * @return Lista de boejtos de tipo {@link Carrera}
     *
     * @throws DataAccessException Si ocurri&oacute; un error de acceso a datos
     */
    List <Carrera> getListadoCarrerasNoAsignadasPorEstudiante(Estudiante estudiante)
            throws DataAccessException;

//______________________________________________________________________________
    /**
     * <p>&Eacute;ste m&eacute;todo agrega una asignaci&oacute;n a una carrera por un estudiante como
     * tambi&eacute;n el primer registro de su historial.</p>
     *
     * <p>Se realiza de esta manera ya que son varias fases, se debe de agregar primero
     * la asignaci&oacute;n y luego se agrega el historial.</p>
     *
     * @param asignacionEstudianteCarrera Objeto de tipo {@link AsignacionEstudianteCarrera}
     * @param historialAsignacionEstudianteCarrera Objeto de tipo {@link HistorialAsignacionEstudianteCarrera}
     *
     * @throws DataIntegrityViolationException Se efectua la excepcion si hay un nombre de usuario igual en la base de datos.
     * @throws DataAccessException Si ocurri&oacute; un error de acceso a datos
     */
    void agregarAsignacionEstudianteCarrera(AsignacionEstudianteCarrera asignacionEstudianteCarrera
            , HistorialAsignacionEstudianteCarrera historialAsignacionEstudianteCarrera)
            throws DataIntegrityViolationException, DataAccessException;

//______________________________________________________________________________
    /**
     * <p>&Eacute;ste m&eacute;todo realiza el cambio de una carrera de un estudiante.
     * Realiza la modificaci&oacute;n en el estado de la asignaci&oacute;n original y lo actualiza en la
     * base de datos y agregar la nueva asignaci&oacute;n como el primer registro del historial.</p>
     *
     *
     * @param asignacionEstudianteCarreraOriginal Asignaci&oacute;n origianal. Objeto de tipo {@link AsignacionEstudianteCarrera}
     * @param asignacionEstudianteCarreraNueva Nueva asignaci&oacute;n.  Objeto de tipo {@link AsignacionEstudianteCarrera}
     * @param historialAsignacionEstudianteCarrera Objeto de tipo {@link HistorialAsignacionEstudianteCarrera}
     *
     * @throws DataIntegrityViolationException Se efectua la excepcion si hay un nombre de usuario igual en la base de datos.
     * @throws DataAccessException Si ocurri&oacute; un error de acceso a datos
     */
    void realizarCambioAsignacionEstudianteCarrera(
            AsignacionEstudianteCarrera asignacionEstudianteCarreraOriginal
            , AsignacionEstudianteCarrera asignacionEstudianteCarreraNueva
            , HistorialAsignacionEstudianteCarrera historialAsignacionEstudianteCarrera)
            throws DataIntegrityViolationException, DataAccessException;
}
