/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.servicio;

import gt.edu.usac.trabajosocial.dominio.Carrera;
import gt.edu.usac.trabajosocial.dominio.Pensum;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

/**
 *
 * @author Daniel Castillo
 * @version 1.0
 */
public interface ServicioPensum {

    /**
     * <p>Este metodo permite la creación de un nuevo pensum, el metodo solo
     * crea el pensum no realiza la asignacion de cursos al pensum.</p>
     * 
     * @param carrera Pojo de tipo {@link Carrera}
     * @param pensum Pojo del tipo {@link Pensum}
     * @throws DataIntegrityViolationException Si ocurre un error de integridad
     *         de datos, en este caso se ingreso un codigo ya existente
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    void agregarPensum(Pensum pensum, Carrera carrera)
            throws DataIntegrityViolationException, DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Este metodo realiza busquedas de pensum por medio de su codigo, como el
     * codigo de pensum es unico, el metodo solo retorna un objeto {@link Pensum},
     * en el caso de no encontrar ningun objeto retorna <code>null</code>.</p>
     *
     * @param codigo Codigo de pensum
     * @return Pensum Si encuentra uno, o <code>null</code> de no encontrar
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    Pensum buscarPensumPorCodigo(String codigo)
            throws DataAccessException;
}
