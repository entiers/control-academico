/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.servicio;

import gt.edu.usac.trabajosocial.controlador.estudiante.DatosBusquedaEstudiante;
import gt.edu.usac.trabajosocial.dominio.Carrera;
import gt.edu.usac.trabajosocial.dominio.Estudiante;
import gt.edu.usac.trabajosocial.dominio.Usuario;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

/**
 *
 * @author Daniel Castillo
 * @version 1.0
 */
public interface ServicioEstudiante {


    /**
     * <p>Este metodo permite agregar la informacion de un estudiante a la base
     * de datos, el metodo ademas realiza la asignacion de carrera al
     * estudiante.</p>
     *
     * @param estudiante Pojo del tipo {@link Estudiante}
     * @param estudiante Pojo del tipo {@link Carrera}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     * @throws DataIntegrityViolationException Si ocurrio una violacion de
     *         de integridad de datos
     */
    void agregarEstudiante(Estudiante estudiante, Carrera carrera)
            throws DataIntegrityViolationException, DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Este metodo permite actualizar los datos de un estudiante.</p>
     *
     * @param estudiante Pojo del tipo {@link Estudiante}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    void actualizarEstudiante(Estudiante estudiante)
            throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Este metodo realiza la asignacion de carrera a un estudiante.</p>
     *
     * @param estudiante Pojo del tipo {@link Estudiante}
     * @param carrera Pojo del tipo {@link Carrera}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    void asignarCarrera(Estudiante estudiante, Carrera carrera)
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
     * @param ordenAscendente true si se quiere un listado en orden ascendente
     * @param columna Nombre de la columna por la cual se ordenara
     * @param firstResult Numero que indica el primer registro que se mostrara
     * @param maxResult Numero que indica la cantidad de registros que se mostraran
     * @return List Listado de estudiantes
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    List<Estudiante> getListadoEstudiantes(DatosBusquedaEstudiante datos, boolean ordenAscendente,
            String columna, int firstResult, int maxResult) throws DataAccessException;
}
