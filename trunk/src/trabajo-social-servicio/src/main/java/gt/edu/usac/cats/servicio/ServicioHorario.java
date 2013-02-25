/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */
package gt.edu.usac.cats.servicio;

import gt.edu.usac.cats.dominio.AsignacionCursoPensum;
import gt.edu.usac.cats.dominio.Catedratico;
import gt.edu.usac.cats.dominio.Curso;
import gt.edu.usac.cats.dominio.Horario;
import gt.edu.usac.cats.dominio.Salon;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.enums.TipoHorario;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * <p>Contiene los metodos que permiten el manejo de la informacion relacionada
 * con el horario en la base de datos.</p>
 *
 * @author Mario Batres
 * @version 1.0
 */
public interface ServicioHorario extends ServicioGeneral {

    /**
     * <p>Este metodo permite obtener un salon por el numero y el nombre del
     * edificio.</p>
     *
     * @param numero Numero del salon
     * @param edificio Nombre del edificio
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */

    Salon buscarSalonPorNumeroYEdificio(short numero, String edificio)
            throws DataAccessException;
//______________________________________________________________________________

    /**
     * <p>Este metodo permite agregar la informacion de un horario a la base
     * de datos.</p>
     *
     * @param salon Pojo del tipo {@link Salon}
     * @param semestre Pojo del tipo {@link Semestre}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     * @return List
     */

    List<Horario> buscarHorarioPorSalonYSemestre(Salon salon, Semestre semestre)
            throws DataAccessException;
//______________________________________________________________________________

    /**
     * <p>Este metodo se encarga de devolver los horarios disponibles en el
     * primer semestres de una carrera especifica.</p>
     *
     * @param curso Pojo del tipo {@link Curso}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     * @return List
     */

    Horario getHorarioPorCursoPrimerIngreso(AsignacionCursoPensum asignacionCursoPensum)
            throws DataAccessException;

//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de devolver los horarios disponibles en el
     * semestre indicado, de una carrera especifica.</p>
     *
     * @param curso Pojo del tipo {@link Curso}
     * @param semestre Pojo del tipo {@link Semestre}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     * @return List
     */

    List<Horario> getHorario(AsignacionCursoPensum asignacionCursoPensumrso, Semestre semestre)
            throws DataAccessException;

//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de devolver los horarios disponibles en el
     * semestre indicado, de una carrera especifica y del tipoHorario especificado.</p>
     *
     * @param curso Pojo del tipo {@link Curso}
     * @param semestre Pojo del tipo {@link Semestre}
     * @param tipoHorario Pojo del tipo {@link TipoHorario}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     * @return List
     */

    List<Horario> getHorario(AsignacionCursoPensum asignacionCursoPensum, Semestre semestre, TipoHorario tipoHorario)
            throws DataAccessException;


//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de devolver los horarios disponibles para realizar
     * un cambio de seccion. Se toma como referencia el horario enviado como parametro,
     * en donde el curso y el semestre sean el mismo, pero la seccion no.</p>
     *
     * @param horario Pojo del tipo {@link Horario}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     * @return List
     */

    List<Horario> getHorarioCambioSeccion(Horario horario)
            throws DataAccessException;

//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de devolver los horarios disponibles para realizar
     * un cambio de seccion. Se toma como referencia el horario enviado como parametro,
     * en donde el curso y el semestre sean el mismo, pero la seccion no.</p>
     *
     * @param horario Pojo del tipo {@link Horario}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     * @return List
     */
    List<Horario> getHorario(AsignacionCursoPensum asignacionCursoPensum)
            throws DataAccessException;
//______________________________________________________________________________

    /**
     * <p>Este metodo se encarga de validar si dentro de la lista de horarios que se
     * envia por parametro existe un traslape.</p>
     *
     * @param horario Pojo del tipo {@link Horario}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     * @return List
     */
    boolean existeTraslape(List<Horario> listadoHorario)
            throws DataAccessException;

//______________________________________________________________________________
    /**
     * Este metodo se encarga de actualizar un horario a la base de datos.  El proceso es el siguiente
     *
     * <ul>
     *  <li> Se actualiza el horario </li>
     *  <li> Se eliminan los dias relacionados con el horario</li>
     *  <li> Se ingresan los nuevos dias relacionados al horario</li>
     * </ul>
     * @param horario El nuevo horario a agregar.
     * @param horarioDiasWrapper Array de cadena de los dias.
     *
     * @throws DataIntegrityViolationException Se efectua la excepcion si hay un nombre de usuario igual en la base de datos.
     * @throws DataAccessException Se efectua si se puede acceder a la base de datos.
     */
    public void actualizarHorario(Horario horario, String[] horarioDiasWrapper)
            throws DataIntegrityViolationException, DataAccessException;

//______________________________________________________________________________
    /**
     * Este metodo se encarga de agregar un nuevo horario a la base de datos.  El proceso es el siguiente
     *
     * <ul>
     *  <li> Se ingresa el horario </li>
     *  <li> Se ingresan los dias relacionados al horario</li>
     * </ul>
     * @param horario El nuevo horario a agregar.
     * @param horarioDiasWrapper Array de cadena de los dias.
     *
     * @throws DataIntegrityViolationException Se efectua la excepcion si hay un nombre de usuario igual en la base de datos.
     * @throws DataAccessException Se efectua si se puede acceder a la base de datos.
     */
    
    public void agregarHorario(Horario horario, String[] horarioDiasWrapper)
            throws DataIntegrityViolationException, DataAccessException;

//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de crear un listado de horarios, el listado
     * se filtra en base a los parametros especificados.</p>
     *
     * @param semestre pojo del tipo {@link Semestre}
     * @param catedratico pojo del tipo {@link Catedratico}
     * @param tipoHorario pojo del tipo {@link TipoHorario}
     * @return List Listado de horarios
     * @throws HibernateException Si ocurrio un error de acceso a datos
     */
    List<Horario> getHorario(Semestre semestre, Catedratico catedratico, TipoHorario tipoHorario)
            throws DataAccessException;

//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de crear un listado de horarios, el listado
     * se filtra en base a los parametros especificados.</p>
     * 
     * @param semestre pojo del tipo {@link Semestre}
     * @param tipoHorario pojo del tipo {@link TipoHorario}
     * @return List Listado de horarios
     * @throws DataAccessException
     */
    List<Horario> getHorario(Semestre semestre, TipoHorario tipoHorario)
            throws DataAccessException;
    
    
//______________________________________________________________________________
    /**
     * 
     */
    List getSeccionesHorario(AsignacionCursoPensum asignacionCursoPensum, Semestre semestre, TipoHorario tipoHorario)
            throws DataAccessException;
    
    
//______________________________________________________________________________
    /**
     * 
     */
    List <Horario> getHorariosPorSecciones(String seccion, AsignacionCursoPensum asignacionCursoPensum, 
            Semestre semestre, TipoHorario tipoHorario)
            throws DataAccessException;

}
