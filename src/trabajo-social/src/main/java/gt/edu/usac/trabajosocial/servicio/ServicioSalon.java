/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.servicio;

import gt.edu.usac.trabajosocial.dominio.Salon;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * <p>Contiene los metodos que permiten el manejo de la informacion relacionada
 * con el salon en la base de datos.</p>
 *
 * @author Mario Batres
 * @version 1.0
 */
public interface ServicioSalon {

    /**
     * <p>Este metodo permite agregar la informacion de un salon a la base
     * de datos.</p>
     *
     * @param salon Pojo del tipo {@link salon}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     * @throws DataIntegrityViolationException Si ocurrio una violacion de
     *         de integridad de datos
     */
    void agregarSalon(Salon salon)
            throws DataIntegrityViolationException, DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Este metodo permite obtener un salon por el numero y el nombre del edificio</p>
     *
     * @param numero Numero del salon
     * @param edificio Nombre del edificio
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    Salon buscarSalonPorNumeroYEdificio(short numero, String edificio)
            throws DataAccessException;
//______________________________________________________________________________
    /**
     * <p>Este metodo permite actualizar la informacion de un salon a la base
     * de datos</p>
     *
     * @param salon Pojo del tipo {@link salon}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    void actualizarSalon (Salon salon)
            throws DataAccessException;

//______________________________________________________________________________
    /**
     * <p>Este metodo permite obtener todos los salones disponibles</p>
     *
     * @return List de tipo {@link Salon}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    List <Salon> getSalones() throws DataAccessException;
}
