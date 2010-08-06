/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.trabajosocial.servicio.impl;

import gt.edu.usac.trabajosocial.dao.DaoGeneral;
import gt.edu.usac.trabajosocial.dominio.Rol;
import gt.edu.usac.trabajosocial.servicio.ServicioRol;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


/**
* <p>Contiene la implementación de los metodos que permiten el manejo de la
* informacion relacionada con el rol en la base de datos.</p>
*
* @author Mario Batres
* @version 1.0
*/

@Service
public class ServicioRolImpl implements ServicioRol{
//______________________________________________________________________________
    /** <p>Permite el acceso y manipulacion a la base de datos.</p> */
    @Resource
    private DaoGeneral daoGeneralImpl;
//______________________________________________________________________________
    /**
     * <p>Este metodo permite actualizar la informacion de un rol  a la base
     * de datos</p>
     *
     * @param salon Pojo del tipo {@link Rol}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    @Override
    public void actualizarRol(Rol rol) throws DataAccessException {
        this.daoGeneralImpl.update(rol);
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo permite obtener todos los roles disponibles</p>
     *
     * @return List de tipo {@link Rol}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    @Override
    public List<Rol> getRoles() throws DataAccessException {
        DetachedCriteria criteria = DetachedCriteria.forClass(Rol.class);
        criteria.addOrder(Order.asc("nombre"));
        return this.daoGeneralImpl.find(criteria);
    }

//______________________________________________________________________________
    /**
     * <p>Este metodo obtiene el rol por su ID</p>
     *
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    @Override
    public Rol getRolPorID(short idRol) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Rol.class);
        criteria.add(Restrictions.eq("idRol", idRol));
        return this.daoGeneralImpl.uniqueResult(criteria);
    }

}
