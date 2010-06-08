/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.servicio;

import gt.edu.usac.trabajosocial.dominio.Carrera;
import gt.edu.usac.trabajosocial.dominio.Curso;
import gt.edu.usac.trabajosocial.dominio.Pensum;
import java.util.List;
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
     * <p>Este metodo se encarga de borrar el objeto {@link Pensum}, enviado
     * como parametro, de la base de datos.</p>
     *
     * @param pensum Objeto a borrar
     * @return true Si y solo si se borro el pensum
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    boolean borrarPensum(Pensum pensum) throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de activar los pensum que se encuentran en
     * estado = 0, o estado de creacion.</p>
     *
     * @param pensum Objeto a borrar
     * @return true Si y solo si se activo el pensum
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    boolean activarPensum(Pensum pensum) throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de caducar los pensum. Los unicos pensum que
     * se pueden caducar son los pensum activos, los que tienen estado = 1.</p>
     *
     * @param pensum Objeto a caducar
     * @return true Si y solo si se caduco el pensum
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    boolean caducarPensum(Pensum pensum) throws DataAccessException;
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
    Pensum buscarPensumPorCodigo(String codigo) throws DataAccessException;
//______________________________________________________________________________
    List<Curso> buscarCursosAsignados(Pensum pensum)
            throws DataAccessException;
//______________________________________________________________________________
    List<Curso> buscarCursosNoAsignados(Pensum pensum)
            throws DataAccessException;
//______________________________________________________________________________
    void agregarCursoPensum(Pensum pensum, Curso curso, boolean obligatorio)
            throws DataAccessException;
}
