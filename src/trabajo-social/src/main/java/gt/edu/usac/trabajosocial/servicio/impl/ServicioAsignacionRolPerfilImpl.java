/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.trabajosocial.servicio.impl;

import gt.edu.usac.trabajosocial.dao.DaoGeneral;
import gt.edu.usac.trabajosocial.dominio.Perfil;
import gt.edu.usac.trabajosocial.dominio.Rol;
import gt.edu.usac.trabajosocial.servicio.ServicioAsignacionRolPerfil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Mario Batres
 */
@Service
public class ServicioAsignacionRolPerfilImpl implements  ServicioAsignacionRolPerfil{
    @Resource
    private DaoGeneral daoGeneralImpl;

    @Override
    public List<Rol> getRolesAsignadosPorPerfil(Perfil perfil) throws DataAccessException {
        StringBuilder builder = new StringBuilder();
        builder.append(" select rol from AsignacionRolPerfil as arf ");
        builder.append(" inner join  arf.rol as rol ");
        builder.append(" where arf.perfil = :perfil");

        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("perfil", perfil);
        return query.list();
    }

    @Override
    public List<Rol> getRolesNoAsignadosPorPerfil(Perfil perfil) throws DataAccessException {
        List <Rol> listadoRol = this.getRolesAsignadosPorPerfil(perfil);

        List values = new ArrayList();
        for(Iterator it = listadoRol.iterator(); it.hasNext();){
            Rol rol = (Rol) it.next();
            values.add(rol.getIdRol());
        }
        DetachedCriteria criteria = DetachedCriteria.forClass(Rol.class);
        criteria.add(Restrictions.not(
                Restrictions.in("idRol", values)
        ));
        return this.daoGeneralImpl.find(criteria);
    }

}
