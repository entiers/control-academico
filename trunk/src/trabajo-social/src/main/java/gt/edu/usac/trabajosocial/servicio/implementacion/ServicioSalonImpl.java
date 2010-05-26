/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.trabajosocial.servicio.implementacion;

import gt.edu.usac.trabajosocial.dao.DaoGeneral;
import gt.edu.usac.trabajosocial.dominio.Salon;
import gt.edu.usac.trabajosocial.servicio.ServicioSalon;
import javax.annotation.Resource;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */

@Service("servicioSalonImpl")
public class ServicioSalonImpl implements ServicioSalon{
//______________________________________________________________________________
    /** Permite el acceso y manipulacion a la base de datos */
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
    public void agregarSalon(Salon salon) throws DataIntegrityViolationException, DataAccessException {
        this.daoGeneralImpl.save(salon);
    }

    @Override
    public Salon buscarSalonPorNumeroYEdificio(short numero, String edificio) throws DataAccessException {
        DetachedCriteria criteria = DetachedCriteria.forClass(Salon.class);

        criteria.add(Restrictions.and(
                Restrictions.eq("numero", numero),
                Restrictions.eq("edificio", edificio))
                );


        return this.daoGeneralImpl.uniqueResult(criteria);
    }

    @Override
    public void actualizarSalon(Salon salon) throws DataAccessException {
        this.daoGeneralImpl.update(salon);
    }

    
}
