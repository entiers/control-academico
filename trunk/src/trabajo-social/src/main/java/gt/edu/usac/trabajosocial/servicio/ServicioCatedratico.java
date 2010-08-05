/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.servicio;

import gt.edu.usac.trabajosocial.dominio.Catedratico;
import gt.edu.usac.trabajosocial.dominio.Escuela;
import gt.edu.usac.trabajosocial.dominio.Usuario;
import gt.edu.usac.trabajosocial.dominio.busqueda.DatosBusquedaCatedratico;
import java.util.List;
import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

/**
 *
 * @author Daniel Castillo
 * @version 1.0
 */
public interface ServicioCatedratico {

    /**
     * <p>Este metodo permite agregar la informacion de un catedratico a la base
     * de datos, el metodo ademas realiza la asignacion de escuela al
     * catedratico.</p>
     *
     * @param catedratico Pojo del tipo {@link Catedratico}
     * @param escuela Pojo del tipo {@link Escuela}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     * @throws DataIntegrityViolationException Si ocurrio una violacion de
     *         de integridad de datos
     */
    void agregarCatedratico(Catedratico catedratico, Escuela escuela)
            throws DataIntegrityViolationException, DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Este metodo permite actualizar los datos de un catedratico.</p>
     *
     * @param catedratico Pojo del tipo {@link Catedratico}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    void actualizarCatedratico(Catedratico catedratico)
            throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Este metodo realiza la asignacion de escuela a un catedratico.</p>
     *
     * @param catedratico Pojo del tipo {@link Catedratico}
     * @param escuela Pojo del tipo {@link Escuela}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    void asignarEscuela(Catedratico catedratico, Escuela escuela)
            throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Este metodo realiza la asignacion del perfil CATEDRATICO a un
     * catedratico. Este perfil contiene todos los permisos/roles para las
     * operaciones un catedratico puede realizar en el sistema. Para realizar
     * la asignacion se debe de enviar el usuario asignado al catedratico.</p>
     *
     * @param usuario Pojo del tipo {@link Usuario}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    public void asignarPerfil(Usuario usuario)
            throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de habilitar o deshabilitar el acceso de un
     * {@link Catedratico} al sistema.</p>
     *
     * @param catedratico Pojo del tipo {@link Catedratico}
     * @param habilitar <code>true</code> para habilitar y <code>false</code>
     *        para deshabilitar
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    void habilitarCatedratico(Catedratico catedratico, boolean habilitar)
            throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Este metodo permite la busqueda de catedraticos por su codigo de
     * personal.</p>
     *
     * @param codigo Numero de personal del catedratico
     * @return Catedratico
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    Catedratico buscarCatedraticoPorCodigo(String codigo)
            throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Este metodo obtiene el {@link Usuario} del {@link Catedratico}.</p>
     *
     * @param catedratico Pojo del tipo {@link Catedratico}
     * @return Usuario
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    Usuario getUsuarioCatedratico(Catedratico catedratico) throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de crear un listado de catedraticos, el listado
     * se filtra en base a los datos de busqueda y se ordena en base al tipo
     * de orden y columna indicados.</p>
     *
     * @param datos Contiene los filtros para el listado
     * @return List Listado de estudiantes
     * @throws HibernateException Si ocurrio un error de acceso a datos
     */
    List<Catedratico> getListadoCatedraticos(DatosBusquedaCatedratico datos)
            throws HibernateException;
//______________________________________________________________________________
    /**
     * <p>Este metodo obtiene la cantidad de registros que retornaria una
     * busqueda hecha en base a los parametros de busqueda enviados por el
     * usuario.</p>
     *
     * @param datos Contiene los filtros para el listado
     * @return Integer Cantidad de registros
     * @throws HibernateException Si ocurrio un error de acceso a datos
     */
    Integer rowCount(DatosBusquedaCatedratico datos)
            throws HibernateException;
}
