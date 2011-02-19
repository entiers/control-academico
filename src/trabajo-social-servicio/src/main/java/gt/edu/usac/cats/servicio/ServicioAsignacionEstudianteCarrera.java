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
import org.hibernate.HibernateException;

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
     * @param estudiante pojo del tipo  {@linl Estudiante}
     * @return List Listado de AsignacionEstudianteCarrera
     * @throws HibernateException Si ocurrio un error de acceso a datos
     */
    List<AsignacionEstudianteCarrera> getAsignacionEstudianteCarreraPorEstudiante(Estudiante estudiante)
            throws HibernateException;
}
