/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.dao.DataAccessException;

/**
 * Clase abstracta que contiene metodos que realizan operaciones basicas con la
 * base de datos, estas operaciones son: select, insert, update y delete. La
 * logica de estas operaciones es porporcionada por la clase
 * <code>HibernateDaoSupport</code>.
 *
 * @author Daniel Castillo
 *
 * @version 1.0
 */
public interface DaoGeneral {


    /**
     * <p>Se encarga de hacer persistente la informacion de la entidad.</p>
     *
     * @param entidad Instancia de cualquiera de los POJO que se encuentran en
     *        <code>controlacademico.pojo</code>
     * @throws org.springframework.dao.DataAccessException Si ocurre algun error
     *         con el accedo a la base de datos
     */
    void save(Object entidad) throws DataAccessException;
//______________________________________________________________________________
   /**
     * <p>Se encarga de hacer persistente la informacion de la entidad.</p>
    *
     * @param entidad Instancia de cualquiera de los POJO que se encuentran en
     *        <code>controlacademico.pojo</code>
     * @throws org.springframework.dao.DataAccessException Si ocurre algun error
     *         con el accedo a la base de datos
     */
    void persist(Object entidad) throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Se encarga de actualizar la informacion de la entidad.</p>
     *
     * @param entidad Instancia de cualquiera de los POJO que se encuentran en
     *        <code>controlacademico.pojo</code>
     * @throws org.springframework.dao.DataAccessException Si ocurre algun error
     *         con el accedo a la base de datos
     */
    void update(Object entidad) throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Se encarga de guardar o actualizar la informacion de la entidad que
     * se envia como parametro. Este metodo es util cuando no se sabe si la
     * informacion de la entidad ya ha sido guardada.</p>
     *
     * @param entidad Instancia de cualquiera de los POJO que se encuentran en
     *        <code>controlacademico.pojo</code>
     * @throws org.springframework.dao.DataAccessException Si ocurre algun error
     *         con el accedo a la base de datos
     */
    void saveOrUpdate(Object entidad) throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Se encarga de hacer persistentes o de actualizar todas las entidades
     * contenidas en el objeto <code>Collection</code>.</p>
     *
     * @param entidades
     * @throws org.springframework.dao.DataAccessException Si ocurre algun error
     *         con el accedo a la base de datos
     */
    void saveOrUpdateAll(Collection entidades) throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Se encarga de borrar la informacion de la entidad.</p>
     *
     * @param entidad Instancia de cualquiera de los POJO que se encuentran en
     *        <code>controlacademico.pojo</code>
     * @throws org.springframework.dao.DataAccessException Si ocurre algun error
     *         con el accedo a la base de datos
     */
    void delete(Object entidad) throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Se encarga de actualizar/eliminar todos los objetos de acuerdo a la
     * consulta enviada como parametro</p>
     *
     * @param queryString Consulta hql
     * @throws org.springframework.dao.DataAccessException Si ocurre algun error
     *         con el accedo a la base de datos
     */
    void bulkUpdate(String queryString) throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Se encarga de actualizar/eliminar todos los objetos de acuerdo a la
     * consulta enviada como parametro. Si en la consulta se incluye una variable
     * esta debe de representarse con el signo ? y el valor de la variable se
     * recibe en el parametro value.</p>
     *
     * @param queryString Consulta hql
     * @param value Valor para la variable en la consulta
     * @throws org.springframework.dao.DataAccessException Si ocurre algun error
     *         con el accedo a la base de datos
     */
    void bulkUpdate(String queryString, Object value) throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Este metodo realiza la busqueda de una entidad por medio de su llave
     * primaria, el metodo asume que la entidad si existe en la base de datos,
     * de no existir se lanza una excepcion. El tipo de la entidad se establece
     * con el parametro <T>, este parametro tomara el tipo de la clase que se
     * envia como parametro.</p>
     *
     * @param <T> Se utiliza para establecer el tipo de la instancia de entidad
     *        que sera retornada
     * @param claseEntidad Clase de la entidad a buscar
     * @param id Llave primaria de la entidad
     * @return <T> Instancia del mismo tipo que se define en el parametro <T>
     * @throws org.springframework.dao.DataAccessException Si ocurre algun error
     *         con el accedo a la base de datos
     */
    <T> T load(Class<T> claseEntidad, Serializable id) throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Este metodo crea un listado de todas la entidades de cierto tipo que
     * se encuentran en la base de datos. El tipo de la entidad se establece
     * con el parametro <T>, este parametro tomara el tipo de la clase que se
     * envia como parametro. Sino se encuentra ninguna entidad el metodo lanza
     * una excepcion.</p>
     *
     * @param <T> Se utiliza para establecer el tipo de la instancia de entidad
     *        que sera retornada
     * @param claseEntidad Clase de la entidad a buscar
     * @return List Listado que contiene objetos del mismo tipo que se define
     *         en el parametro <T>
     * @throws org.springframework.dao.DataAccessException Si ocurre algun error
     *         con el accedo a la base de datos
     */
    <T> List<T> loadAll(Class<T> claseEntidad) throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Crea un listado de todas las entidades que se encuentran en determinada
     * tabla de la base de datos. La tabla se determina por la clase que se
     * envia como parametro.</p>
     *
     * @param <T> Se utiliza para establecer el tipo de la instancia de entidad
     *        que sera retornada
     * @param claseEntidad Clase de la entidad a buscar
     * @return List Listado que contiene objetos del mismo tipo que se define
     *         en el parametro <T>
     * @throws org.springframework.dao.DataAccessException Si ocurre algun error
     *         con el accedo a la base de datos
     */
    <T> List<T> find(Class<T> claseEntidad) throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Realiza una busqueda en la base de datos, la busqueda se realiza en
     * base a la consulta HQL que se envia como parametro.</p>
     *
     * @param <T> Se utiliza para establecer el tipo de la instancia de entidad
     *        que sera retornada
     * @param hql Consulta que se realizara en la base de datos
     * @return List Listado que contiene objetos del mismo tipo que se define
     *         en el parametro <T>
     * @throws org.springframework.dao.DataAccessException Si ocurre algun error
     *         con el accedo a la base de datos
     */
    <T> List<T> find(String hql) throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Realiza una busqueda en la base de datos, la busqueda se realiza en
     * base a la consulta HQL que se envia como parametro. La direfencia de este
     * metodo es que la consulta HQL contiene una variable, a dicha variable
     * se le asigna un valor en el momento en que se ejecuta. El valor de esta
     * variable es el valor que se envia como parametro.</p>
     *
     * @param <T> Se utiliza para establecer el tipo de la instancia de entidad
     *        que sera retornada
     * @param hql Consulta que se realizara en la base de datos
     * @param valor Valor para la variable que esta en la consulta HQL
     * @return List Listado que contiene objetos del mismo tipo que se define
     *         en el parametro <T>
     * @throws org.springframework.dao.DataAccessException Si ocurre algun error
     *         con el accedo a la base de datos
     */
    <T> List<T> find(String hql, Object valor) throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Realiza una consulta en la base de datos, la consulta a realizar esta
     * definida por el objeto <code>DetachedCriteria</code> que se envia como
     * parametro.</p>
     *
     * @param <T> Se utiliza para establecer el tipo de la instancia de entidad
     *        que sera retornada
     * @param criteria Objeto <code>DetachedCriteria</code> que define la
     *        consulta que sera realizada
     * @return List Listado que contiene objetos del mismo tipo que se define
     *         en el parametro <T>
     * @throws org.springframework.dao.DataAccessException Si ocurre algun error
     *         con el accedo a la base de datos
     */
    <T> List<T> find(DetachedCriteria criteria) throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Realiza una busqueda en la base de datos, la busqueda se realiza en
     * base a la consulta HQL que se envia como parametro. La direfencia de este
     * metodo es que la consulta HQL contiene una variable, a dicha variable
     * se le asigna un valor en el momento en que se ejecuta. El valor de esta
     * variable es el valor que se envia como parametro. Este metodo asume que
     * la consulta solo devolvera un unico valor, de lo contrario se lanza una
     * excepciom.</p>
     *
     * @param <T> Se utiliza para establecer el tipo de la instancia de entidad
     *        que sera retornada
     * @param hql Consulta que se realizara en la base de datos
     * @param valor Valor para la variable que esta en la consulta HQL
     * @return List Listado que contiene objetos del mismo tipo que se define
     *         en el parametro <T>
     * @throws org.springframework.dao.DataAccessException Si ocurre algun error
     *         con el accedo a la base de datos
     */
    <T> T uniqueResult(String hql, Object valor) throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Realiza una consulta en la base de datos, la consulta a realizar esta
     * definida por el objeto <code>DetachedCriteria</code> que se envia como
     * parametro. Este metodo asume que la consulta solo devolvera un unico
     * valor, de lo contrario se lanza una excepciom.</p>
     * 
     * @param <T> Se utiliza para establecer el tipo de la instancia de entidad
     *        que sera retornada
     * @param criteria Objeto <code>DetachedCriteria</code> que define la
     *        consulta que sera realizada
     * @throws org.springframework.dao.DataAccessException Si ocurre algun error
     *         con el accedo a la base de datos
     */
    <T> T uniqueResult(DetachedCriteria criteria) throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Este metodo retorna el objeto {@link Session} que utiliza el dao.</p>
     *
     * @return Session
     */
    Session getSesion();
}
