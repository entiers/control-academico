/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.servicio;

import gt.edu.usac.cats.dominio.AsignacionCursoPensum;
import gt.edu.usac.cats.dominio.Curso;
import gt.edu.usac.cats.dominio.Pensum;
import java.util.List;
import org.springframework.dao.DataAccessException;

/**
 * <p>Contiene los metodos que permiten el manejo de la informaci&oacute;n relacionada
 * con el pensum y asignaci&oacute;n de cursos al pensum en la base de datos.</p>
 *
 * @author Mario Batres
 * @version 1.0
 */

public interface ServicioPensum extends ServicioGeneral{


    /**
     * Este m&eacute;todo se encarga de obtener los cursos que <b>NO</b> han sido asignados
     * al pensum que es enviado como par&aacute;metro.
     *
     * @param pensum Objeto de tipo {@link Pensum}
     *
     * @throws DataAccessException Si ocurri&oacute; un error de acceso a datos
     *
     * @return List con atributos de tipo {@link Curso}
     */
    List <Curso> getCursosNoAsignados(Pensum pensum)
            throws DataAccessException ;

    /**
     * Este m&eacute;todo se encarga de obetner todas las asignaciones de cursos
     * sin incluir la asignaci&oacute;n que es enviada como par&aacute;metro.  Esto se utiliza
     * en la administraci&oacute;n de prerrequisitos.
     *
     * @param pensum Objeto de tipo {@link Pensum}
     * @param asignacionCursoPensum Objeto de tipo {@link AsignacionCursoPensum}
     *
     * @return List con atributos de tipo {@link AsignacionCursoPensum}
     * @throws DataAccessException Si ocurri&oacute; un error de acceso a datos
     */
    List <AsignacionCursoPensum> getAsignacionCursoPensumsPorPensumYNoACP(Pensum pensum, AsignacionCursoPensum asignacionCursoPensum)
            throws DataAccessException ;

}
