/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.servicio;

import gt.edu.usac.cats.dominio.busqueda.DatosBusquedaEstudiante;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.Curso;
import gt.edu.usac.cats.dominio.Usuario;
import java.util.List;
import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

/**
* <p>Contiene los metodos que permiten el manejo de la informacion relacionada
 * con el estudiante en la base de datos.</p>
 *
 * @author Daniel Castillo
 * @version 1.0
 */
public interface ServicioEstudiante extends ServicioGeneral {
//______________________________________________________________________________
    /**
     * <p>Este metodo permite agregar la informacion de un estudiante a la base
     * de datos, el metodo ademas realiza la asignacion de carrera al
     * estudiante.</p>
     *
     * @param estudiante Pojo del tipo {@link Estudiante}     
     *
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     * @throws DataIntegrityViolationException Si ocurrio una violacion de
     *         de integridad de datos
     */

    void agregarEstudiante(Estudiante estudiante)
            throws DataIntegrityViolationException, DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Este metodo realiza la asignacion de carrera a un estudiante.</p>
     *
     * @param estudiante Pojo del tipo {@link Estudiante}
     * @param carrera Pojo del tipo {@link Carrera}
     * @param semestre Pojo del tipo {@link Semestre}
     * @param situacion Enum del tipo {@link situacion}
     *
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    /*void asignarCarrera(Estudiante estudiante, Carrera carrera, Semestre semestre, Situacion situacion)
            throws DataAccessException;*/
//______________________________________________________________________________
    /**
     * <p>Este metodo realiza la asignacion del perfil ESTUDIANTE a un
     * estudiante. Este perfil contiene todos los permisos/roles para las
     * operaciones un estudiante puede realizar en el sistema. Para realizar la
     * asignacion se debe de enviar el usuario asignado al estudiante.</p>
     *
     * @param usuario Pojo del tipo {@link Usuario}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    public void asignarPerfil(Usuario usuario)
            throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de habilitar o deshabilitar el acceso de un
     * {@link Estudiante} al sistema.</p>
     *
     * @param estudiante Pojo del tipo {@link Estudiante}
     * @param habilitar <code>true</code> para habilitar y <code>false</code>
     *        para deshabilitar
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    void habilitarEstudiante(Estudiante estudiante, boolean habilitar)
            throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Este metodo permite la busqueda de estudiantes por su numero de
     * carne.</p>
     *
     * @param carne Numero de carne del estudiante
     * @return Estudiante
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    Estudiante buscarEstudiantePorCarne(String carne) throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Este metodo obtiene el {@link Usuario} del {@link Estudiante}.</p>
     *
     * @param estudiante Pojo del tipo {@link Estudiante}
     * @return Usuario
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    Usuario getUsuarioEstudiante(Estudiante estudiante) throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de crear un listado de estudiantes, el listado
     * se filtra en base a los datos de busqueda y se ordena en base al tipo
     * de orden y columna indicados.</p>
     *
     * @param datos Contiene los filtros para el listado
     * @return List Listado de estudiantes
     * @throws HibernateException Si ocurrio un error de acceso a datos
     */
    List<Estudiante> getListadoEstudiantes(DatosBusquedaEstudiante datos)
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
    Integer rowCount(DatosBusquedaEstudiante datos)
            throws HibernateException;
 //______________________________________________________________________________
    /**
     * <p>Valida que el estudiante este asignado en un curso en un horario
     * especifico.</p>
     *
     * @param estdiante Contiene el estudiante a buscar (@link Estudiante)
     * @param horario contiene el horario por el que se busca (@link Horario)
     * @return boolean true si esta asignado, de lo contrario false 
     * @throws HibernateException Si ocurrio un error de acceso a datos
     */
    boolean tieneCursoAsignadoPrimerIngreso(Estudiante estudiante, Curso curso)
            throws HibernateException;
}
