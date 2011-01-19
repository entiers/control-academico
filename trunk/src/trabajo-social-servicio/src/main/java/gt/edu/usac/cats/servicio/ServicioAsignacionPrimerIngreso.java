/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.servicio;


import gt.edu.usac.cats.dominio.AsignacionPrimerIngreso;
import org.springframework.dao.DataAccessException;

/**
 * <p>
 * Servicio para manejar el proceso de asignaci�n de cursos a estudiantes
 * de primer ingreso
 * </p>
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
public interface ServicioAsignacionPrimerIngreso{

    /**
     * <p>Este proceso se encarga de realizar la asignaciona de cursos a
     * estudiantes de primer ingreso</p>
     *
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    public void asignacionCursosPrimerIngreso(AsignacionPrimerIngreso asignacionPrimerIngreso)
            throws DataAccessException;
}
