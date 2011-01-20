/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.servicio;

import gt.edu.usac.cats.dominio.Horario;
import org.hibernate.HibernateException;

/**
 * <p></p>
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
public interface ServicioDetalleAsignacion extends ServicioGeneral{
//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de crear un listado de AsignacionEstudianteCarrera, el listado
     * se filtra en base la carrera, el numero de carnet de cada estudiante, en donde este
     * sea igual al anio actual, asi como el estudiante no debe de tener asignaciones previas</p>
     *
     * @param horario (@link Horario) Contiene los filtros para el total
     * @return Integer Total de detalles de asignacion por horario
     * @throws HibernateException Si ocurrio un error de acceso a datos
     */
    Integer getCountDetalleAsignacionXHorario(Horario horario)
            throws HibernateException;
}
