/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.servicio;

import gt.edu.usac.cats.dominio.AsignacionUsuarioPerfil;
import gt.edu.usac.cats.dominio.Perfil;
import gt.edu.usac.cats.dominio.Usuario;
import gt.edu.usac.cats.dominio.busqueda.DatosBusquedaUsuario;
import java.util.List;
import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessException;

/**
 * <p></p>
 *
 * @author Carlos Solórzano
 * @version 1.0
 */
public interface ServicioUsuario extends ServicioGeneral {

    //______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de buscar un usuario en base al nombre del mismo</p>
     *
     * @param nombre Contiene el nombre del usuario a buscar
     * @return Usuario
     * @throws HibernateException Si ocurrio un error de acceso a datos
     */
    Usuario cargarUsuarioPorNombre(String nombre)
            throws HibernateException;
    //______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de crear un listado de usuarios, el listado
     * se filtra en base a los datos de busqueda y se ordena en base al tipo
     * de orden y columna indicados.</p>
     *
     * @param datos Contiene los filtros para el listado
     * @return List Listado de usuarios
     * @throws HibernateException Si ocurrio un error de acceso a datos
     */
    List<Usuario> getListadoUsuarios(DatosBusquedaUsuario datos)
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
    Integer rowCount(DatosBusquedaUsuario datos)
            throws HibernateException;

//______________________________________________________________________________
    /**
     * @param usuario
     *
     * @throws DataAccessException
     *
     * @return
     **/
    List<Perfil> getPerfilesAsignadosPorUsuario(Usuario usuario)
            throws DataAccessException;
//______________________________________________________________________________
    /**
     * @param usuario
     *
     * @throws DataAccessException
     *
     * @return
     **/
    List<Perfil> getPerfilesNoAsignadosPorUsuario(Usuario usuario)
            throws DataAccessException;
//______________________________________________________________________________
    /**
     * @param usuario
     *
     * @throws DataAccessException
     *
     * @return
     **/
    List <AsignacionUsuarioPerfil> getAsignacionUsuarioPerfilPorUsuario(Usuario usuario)
            throws DataAccessException;

}
