/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.servicio;

import gt.edu.usac.cats.dominio.Carrera;
import gt.edu.usac.cats.dominio.Curso;
import gt.edu.usac.cats.dominio.Horario;
import gt.edu.usac.cats.dominio.Salon;
import gt.edu.usac.cats.dominio.Semestre;
import java.util.List;
import org.springframework.dao.DataAccessException;

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
    List <Horario> buscarHorarioPorSalonYSemestre(Salon salon, Semestre semestre)
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
    Horario getHorarioPorCursoPrimerIngreso(Curso curso)
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
    List <Horario> getHorario(Curso curso, Semestre semestre)
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
    List <Horario> getHorarioCambioSeccion(Horario horario)
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
    List <Horario> getHorario(Curso curso)
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
}