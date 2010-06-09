/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.trabajosocial.servicio;

import gt.edu.usac.trabajosocial.dominio.CalendarioActividades;
import gt.edu.usac.trabajosocial.dominio.Semestre;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
public interface ServicioCalendarioActividades {

//______________________________________________________________________________
    /**
     * <p>Este metodo permite agregar la informacion de un calendario de
     * actividades a la base de datos.
     *
     * @param calendarioActividades Pojo del tipo {@link CalendarioActividades}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     * @throws DataIntegrityViolationException Si ocurrio una violacion de
     *         de integridad de datos
     */
    void agregarCalendarioActividades(CalendarioActividades calendarioActividades)
        throws DataIntegrityViolationException, DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Obtiene todos los calendarios de actividades por el semestre</p>
     *
     * @param semestre Pojo del tipo {@link semestre}
     *
     * @throws DataAccessException Si ocurri� un error de acceso a datos
     **/
    List <CalendarioActividades> getCalendarioActividadesPorSemestre(Semestre semestre)
            throws DataAccessException;

//______________________________________________________________________________
    /**
     * <p>Obtiene el calendario de actividades seg�n el id que poose</p>
     *
     * @param id Identificador del Calendario de Actividades
     *
     * @throws DataAccessException Si oucrri� un error de acesso a datos
     */
    CalendarioActividades getCalendarioActividadesPorID(short id)
            throws DataAccessException;

//______________________________________________________________________________
     /**
     * <p>Este metodo permite actualizar la informacion de un calendario de
     * actividades a la base de datos.
     *
     * @param calendarioActividades Pojo del tipo {@link CalendarioActividades}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     * @throws DataIntegrityViolationException Si ocurrio una violacion de
     *         de integridad de datos
     */
    void actualizarCalendarioActividades(CalendarioActividades calendarioActividades)
            throws DataAccessException;

}