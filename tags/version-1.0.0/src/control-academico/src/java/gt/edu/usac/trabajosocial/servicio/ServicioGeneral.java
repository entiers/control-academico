/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.servicio;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

/**
 *
 * @author Daniel Castillo
 * @version 1.0
 */
public interface ServicioGeneral {


    /**
     * <p>Se encarga de hacer persistente la informacion de una nueva entidad
     * en la base de datos.</p>
     *
     * @param entidad Instancia de cualquiera de los POJO que se encuentran en
     *        <code>controlacademico.pojo</code>
     * @throws org.springframework.dao.DataIntegrityViolationException Esta
     *         excepcion se lanza cuando se intenta duplicar el valor de un
     *         campo de tipo UNIQUE
     * @throws org.springframework.dao.DataAccessException Si ocurre algun error
     *         con el accedo a la base de datos
     */
    void agregar(Object entidad) throws DataIntegrityViolationException, DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Se encarga de hacer persistente la informacion de una nueva entidad
     * en la base de datos.</p>
     *
     * @param entidad Instancia de cualquiera de los POJO que se encuentran en
     *        <code>controlacademico.pojo</code>
     * @throws org.springframework.dao.DataIntegrityViolationException Esta
     *         excepcion se lanza cuando se intenta duplicar el valor de un
     *         campo de tipo UNIQUE
     * @throws org.springframework.dao.DataAccessException Si ocurre algun error
     *         con el accedo a la base de datos
     */
    void persistir(Object entidad) throws DataIntegrityViolationException, DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Se encarga de actualizar la informacion de una entidad que se
     * encuentra almacenada en la base de datos.</p>
     *
     * @param entidad Instancia de cualquiera de los POJO que se encuentran en
     *        <code>controlacademico.pojo</code>
     * @throws org.springframework.dao.DataIntegrityViolationException Esta
     *         excepcion se lanza cuando se intenta duplicar el valor de un
     *         campo de tipo UNIQUE
     * @throws org.springframework.dao.DataAccessException Si ocurre algun error
     *         con el accedo a la base de datos
     */
    void actualizar(Object entidad) throws DataIntegrityViolationException, DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Se encarga de agregar o actualizar la informacion de una entidad.</p>
     *
     * @param entidad Instancia de cualquiera de los POJO que se encuentran en
     *        <code>controlacademico.pojo</code>
     * @throws org.springframework.dao.DataIntegrityViolationException Esta
     *         excepcion se lanza cuando se intenta duplicar el valor de un
     *         campo de tipo UNIQUE
     * @throws org.springframework.dao.DataAccessException Si ocurre algun error
     *         con el accedo a la base de datos
     */
    void agregarActualizar(Object entidad) throws DataIntegrityViolationException, DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Se encarga de hacer persistentes o de actualizar todas las entidades
     * contenidas en el objeto <code>Collection</code>.</p>
     *
     * @param entidades
     * @throws org.springframework.dao.DataIntegrityViolationException Esta
     *         excepcion se lanza cuando se intenta duplicar el valor de un
     *         campo de tipo UNIQUE
     * @throws org.springframework.dao.DataAccessException Si ocurre algun error
     *         con el accedo a la base de datos
     */
    void agregarActualizarTodo(Collection entidades) throws DataIntegrityViolationException, DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Se encarga de eliminar una entidad de la base de datos.</p>
     *
     * @param entidad Instancia de cualquiera de los POJO que se encuentran en
     *        <code>controlacademico.pojo</code>
     * @throws org.springframework.dao.DataIntegrityViolationException Esta
     *         excepcion se lanza cuando el registro se encuentra refernciado por
     *         otras tablas
     * @throws org.springframework.dao.DataAccessException Si ocurre algun error
     *         con el accedo a la base de datos
     */
    void borrar(Object entidad) throws DataIntegrityViolationException, DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Este metodo realiza la busqueda de una entidad por medio de su llave
     * primaria, el metodo asume que la entidad si existe en la base de datos,
     * de no existir se lanza una excepcion. El tipo de la entidad se establece
     * con el parametro <T>, este parametro tomara el tipo de la clase que se
     * envia como parametro.</p>
     *
     * @param <T> Se utiliza para establecer el tipo de la instancia de entidad
     *        que sera retornada.
     * @param claseEntidad Clase de la entidad a buscar
     * @param id Llave primaria de la entidad
     * @return <T> Instancia del mismo tipo que se define en el parametro <T>
     * @throws org.springframework.dao.DataAccessException Si ocurre algun error
     *         con el accedo a la base de datos
     */
    <T> T cargarEntidadPorID(Class<T> claseEntidad, Serializable id) throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Este metodo crea un listado de todas la entidades de cierto tipo que
     * se encuentran en la base de datos. El tipo de la entidad se establece
     * con el parametro <T>, este parametro tomara el tipo de la clase que se
     * envia como parametro. Sino se encuentra ninguna entidad el metodo lanza
     * una excepcion.</p>
     *
     * @param <T> Se utiliza para establecer el tipo de la instancia de entidad
     *        que sera retornada.
     * @param claseEntidad Clase de la entidad a buscar
     * @return List Listado que contiene objetos del mismo tipo que se define
     *         en el parametro <T>
     * @throws org.springframework.dao.DataAccessException Si ocurre algun error
     *         con el accedo a la base de datos
     */
    <T> List<T> cargarEntidades(Class<T> claseEntidad) throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Realiza la misma funcion que el metodo {@link cargarEntidades()} la
     * diferencia es que este metodo si no encuentra ninguna entidad retorna una
     * lista vacia.</p>
     *
     * @param <T> Se utiliza para establecer el tipo de la instancia de entidad
     *        que sera retornada.
     * @param claseEntidad Clase de la entidad a buscar
     * @return List Listado que contiene objetos del mismo tipo que se define
     *         en el parametro <T>
     * @throws org.springframework.dao.DataAccessException Si ocurre algun error
     *         con el accedo a la base de datos
     */
    <T> List<T> listarEntidad(Class<T> claseEntidad) throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Realiza la misma funcion que el metodo {@link cargarEntidades()} la
     * diferencia es que este metodo si no encuentra ninguna entidad retorna una
     * lista vacia. La lista que se retorna se encuentra ordenada en base a la
     * columna y orden que se establecen por medio de los parametros que se
     * envian.</p>
     *
     * @param <T> Se utiliza para establecer el tipo de la instancia de entidad
     *        que sera retornada.
     * @param claseEntidad Clase de la entidad a buscar
     * @param ordenAscendente Si el valor es <code>true</code> la lista se
     *        ordena de forma ascendente, de lo contrario se ordena de forma
     *        descendente.
     * @param columna Columna por la cual se hara el ordenamiento del listado
     * @return List Listado que contiene objetos del mismo tipo que se define
     *         en el parametro <T>
     * @throws org.springframework.dao.DataAccessException Si ocurre algun error
     *         con el accedo a la base de datos
     */
    <T> List<T> listarEntidad(Class<T> claseEntidad, boolean ordenAscendente, String columna) throws DataAccessException;
}
