/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.servicio;

import gt.edu.usac.cats.dominio.CalendarioActividades;
import gt.edu.usac.cats.dominio.Semestre;
import java.util.List;
import org.springframework.dao.DataAccessException;

/**
 * <p>Contiene los metodos que permiten el manejo de la informacion relacionada
 * con las actividades. Entre las actividades se encuentran:
 * <ul>
 * <li>Creacion de semestres</li>
 * <li>Manipulacion de calendarios de actividades</li>
 * </ul>
 * </p>
 *
 * @author Mario Batres
 * @version 1.0
 */
public interface ServicioActividad extends ServicioGeneral {

//______________________________________________________________________________
    /**
     * <p>Obtiene todos los calendarios de actividades por el semestre.</p>
     *
     * @param semestre Pojo del tipo {@link semestre}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     **/
    List <CalendarioActividades> getCalendarioActividadesPorSemestre(Semestre semestre)
            throws DataAccessException;
}
