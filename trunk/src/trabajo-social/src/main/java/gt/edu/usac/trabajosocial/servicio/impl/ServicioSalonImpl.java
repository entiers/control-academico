/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.servicio.impl;

import gt.edu.usac.trabajosocial.dao.DaoGeneral;
import gt.edu.usac.trabajosocial.dominio.Salon;
import gt.edu.usac.trabajosocial.servicio.ServicioSalon;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>Contiene la implementacion de los metodos que permiten el manejo de la
 * informacion relacionada con el salon en la base de datos.</p>
 *
 * @see ServicioSalon
 * @author Mario Batres
 * @version 1.0
 */

@Service("servicioSalonImpl")
public class ServicioSalonImpl implements ServicioSalon {

    /** <p>Permite el acceso y manipulacion a la base de datos.</p> */
    @Resource
    private DaoGeneral daoGeneralImpl;
//______________________________________________________________________________
     /**
     * <p>Este metodo se encarga de agregar los datos del salon a la base
     * de datos.
         *
     * @param salon Pojo del tipo {@link Salon}
     * @throws DataIntegrityViolationException Se dio una violacion de integridad
     *         de los datos
     * @throws DataAccessException Ocurrio un error con el acceso a la base de
     *         datos
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void agregarSalon(Salon salon) 
            throws DataIntegrityViolationException, DataAccessException {

        this.daoGeneralImpl.save(salon);
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo permite obtener un salon por el numero y el nombre del edificio</p>
     *
     * @param numero Numero del salon
     * @param edificio Nombre del edificio
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    @Override
    public Salon buscarSalonPorNumeroYEdificio(short numero, String edificio)
            throws DataAccessException {

        DetachedCriteria criteria = DetachedCriteria.forClass(Salon.class);

        criteria.add(Restrictions.and(
                Restrictions.eq("numero", numero),
                Restrictions.eq("edificio", edificio))
                );

        return this.daoGeneralImpl.uniqueResult(criteria);
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo permite actualizar la informacion de un salon a la base
     * de datos</p>
     *
     * @param salon Pojo del tipo {@link salon}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void actualizarSalon(Salon salon) 
            throws DataAccessException {

        this.daoGeneralImpl.update(salon);
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo permite obtener todos los salones disponibles</p>
     *
     * @return List de tipo {@link Salon}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    @Override
    public List<Salon> getSalones() throws DataAccessException {
        return this.daoGeneralImpl.loadAll(Salon.class);
    }
}
