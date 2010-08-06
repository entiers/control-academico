/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.trabajosocial.servicio.impl;

import gt.edu.usac.trabajosocial.dao.DaoGeneral;
import gt.edu.usac.trabajosocial.dominio.Perfil;
import gt.edu.usac.trabajosocial.servicio.ServicioPerfil;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * <p>Contiene la implementación de los metodos que permiten el manejo de la
 * informacion relacionada con el perfil en la base de datos.</p>
 *
 * @author Mario Batres
 * @version 1.0
 */

@Service
public class ServicioPerfilImpl implements ServicioPerfil {
//______________________________________________________________________________
    /** <p>Permite el acceso y manipulacion a la base de datos.</p> */
    @Resource
    private DaoGeneral daoGeneralImpl;

//______________________________________________________________________________
    /**
     * <p>Este metodo permite agregar la informacion de un perfil a la base
     * de datos.</p>
     *
     * @param salon Pojo del tipo {@link Perfil}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */

    @Override
    public void agregarPerfil(Perfil perfil) throws DataAccessException {
        this.daoGeneralImpl.save(perfil);
    }

//______________________________________________________________________________
    /**
     * <p>Este metodo permite actualizar la informacion de un perfil  a la base
     * de datos</p>
     *
     * @param salon Pojo del tipo {@link Perfil}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    @Override
    public void actualizarPerfil(Perfil perfil) throws DataAccessException {
        this.daoGeneralImpl.update(perfil);
    }

//______________________________________________________________________________
    /**
     * <p>Este metodo permite obtener todos los perfiles disponibles</p>
     *
     * @return List de tipo {@link Perfil}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    @Override
    public List<Perfil> getPerfiles() throws DataAccessException {
        DetachedCriteria criteria = DetachedCriteria.forClass(Perfil.class);
        criteria.addOrder(Order.asc("nombre"));
        return this.daoGeneralImpl.find(criteria);
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo obtiene el perfil por su ID</p>
     *
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    @Override
    public Perfil getPerfilPorID(short idPerfil) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Perfil.class);
        criteria.add(Restrictions.eq("idPerfil", idPerfil));
        return this.daoGeneralImpl.uniqueResult(criteria);
    }

}
