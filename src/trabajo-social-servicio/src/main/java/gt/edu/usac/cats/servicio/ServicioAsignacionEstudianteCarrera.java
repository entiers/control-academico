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
 * <p></p>
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
public interface ServicioAsignacionEstudianteCarrera extends ServicioGeneral{
//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de crear un listado de AsignacionEstudianteCarrera, el listado
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


    List <AsignacionEstudianteCarrera> getAsignacionEstudianteCarrera(Estudiante estudiante
            , boolean estado) throws DataAccessException;


    List <Carrera> getListadoCarrerasNoAsignadasPorEstudiante(Estudiante estudiante)
            throws DataAccessException;


    void agregarAsignacionEstudianteCarrera(AsignacionEstudianteCarrera asignacionEstudianteCarrera
            , HistorialAsignacionEstudianteCarrera historialAsignacionEstudianteCarrera)
            throws DataIntegrityViolationException, DataAccessException;


    void realizarCambioAsignacionEstudianteCarrera(
            AsignacionEstudianteCarrera asignacionEstudianteCarreraOriginal
            , AsignacionEstudianteCarrera asignacionEstudianteCarreraNueva
            , HistorialAsignacionEstudianteCarrera historialAsignacionEstudianteCarrera)
            throws DataIntegrityViolationException, DataAccessException;
}
