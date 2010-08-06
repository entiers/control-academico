/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.trabajosocial.servicio.impl;

import gt.edu.usac.trabajosocial.dao.DaoGeneral;
import gt.edu.usac.trabajosocial.dominio.AsignacionRolPerfil;
import gt.edu.usac.trabajosocial.dominio.Perfil;
import gt.edu.usac.trabajosocial.servicio.ServicioAsignacionRolPerfil;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author shakamca
 */
@Service
public class ServicioAsignacionRolPerfilImpl implements ServicioAsignacionRolPerfil{

    @Resource
    private DaoGeneral daoGeneralImpl;

    @Override
    public List getAsignacionRolPerfilPorPerfil(Perfil perfil) throws DataAccessException {
/*
        DetachedCriteria criteria = DetachedCriteria.forClass(AsignacionRolPerfil.class);
        criteria.add(Restrictions.eq("perfil", perfil));

        List <AsignacionRolPerfil> listadoAsignacionRolPerfil = this.daoGeneralImpl.find(criteria);
*/

        StringBuilder builder = new StringBuilder();
        builder.append(" select * from control.rol r ");
        builder.append(" left outer join  ");
        builder.append(" (select * from control.asignacion_rol_perfil where id_perfil = :idPerfil) arf ");
        builder.append(" on arf.id_rol =  r.id_rol ");
        builder.append(" order by nombre ");

        SQLQuery query = this.daoGeneralImpl.getSesion().createSQLQuery(builder.toString());
        query.setParameter("idPerfil", perfil.getIdPerfil());

        System.out.println("je suis ici");
        List list = query.list();
        for(Iterator it = list.iterator(); it.hasNext();){
            System.out.println(it.next());
        }

        return list;
    }

}
