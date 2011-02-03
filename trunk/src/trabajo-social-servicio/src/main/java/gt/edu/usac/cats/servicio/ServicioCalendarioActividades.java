/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.servicio;

import gt.edu.usac.cats.dominio.CalendarioActividades;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.enums.TipoActividad;
import java.util.Date;
import java.util.List;
import org.springframework.dao.DataAccessException;

/**
 * <p>Contiene los metodos que permiten el manejo de la informacion relacionada
 * con el calendario de actividades.
 * </p>
 *
 * @author Mario Batres
 * @version 1.0
 */
public interface ServicioCalendarioActividades extends ServicioGeneral {

//______________________________________________________________________________
    /**
     * <p>Obtiene todos los calendarios de actividades por el semestre.</p>
     *
     * @param semestre Pojo del tipo {@link semestre}
     * @throws DataAccessException
     *
     * @return Listado de calendario de actividades que fueron econtrador.
     **/
    List <CalendarioActividades> getCalendarioActividadesPorSemestre(Semestre semestre)
            throws DataAccessException;

//______________________________________________________________________________
    /**
     *
     * <p>Verifica si existe un tipo de actividad en un semestre.  Segun la reglas del negocio
     * se utiliza este metodo si el calendario de actividades tiene un tipo de actividad,
     * de lo contrario no se debe utilizar.</p>
     *
     * @param tipoActividad Objeto enum {@link TipoActividad}
     * @param semestre Objeto {@link Semestre}
     *
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     *
     * @return 
     *  <p>
     *      <ul>
     *          <li><b>True</b> - Si existe un tipo de actividad por el semestre enviado</li>
     *          <li><b>False</b> - Si no existe un tipo de actividad por el semestre enviado</li>
     *      </ul>
     * </p>
     */
    boolean existeTipoActividadPorSemestre(TipoActividad tipoActividad, Semestre semestre)
            throws DataAccessException;

//______________________________________________________________________________
    /**
     * <p>Realiza una consulta a la base de datos para verificar que la fecha que es enviada
     * como parametro sea valida en el rango que ha sido almacenado por tipo de actividad y semestre</p>
     *
     * @param tipoActividad Pojo de tipo {@link TipoActividad}
     * @param semestre Pojo de tipo {@link Semestre}     
     * @param fecha Fecha para comprobar si es valida en el rango que ha sido almacenado en el calendario de actividades.
     *
     * @return
     *  <p>
     *      <ul>
     *          <li><b>True</b> - Si la fecha es valida</li>
     *          <li><b>False</b> - Si la fecha no es valida</li>
     *      </ul>
     * </p>
     *
     * @exception DataAccessException
     */
    boolean esFechaActividadValida(TipoActividad tipoActividad, Semestre semestre, Date fecha) throws DataAccessException;
}
