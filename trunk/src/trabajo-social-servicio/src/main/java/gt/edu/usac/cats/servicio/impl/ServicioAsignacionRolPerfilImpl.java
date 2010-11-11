/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.servicio.impl;

import gt.edu.usac.cats.dominio.AsignacionRolPerfil;
import gt.edu.usac.cats.dominio.Perfil;
import gt.edu.usac.cats.dominio.Rol;
import gt.edu.usac.cats.servicio.ServicioAsignacionRolPerfil;
import java.util.List;
import org.hibernate.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
@Service
public class ServicioAsignacionRolPerfilImpl extends ServicioGeneralImpl implements  ServicioAsignacionRolPerfil{
    
//______________________________________________________________________________
    
    @Override
    public List<Rol> getRolesAsignadosPorPerfil(Perfil perfil) throws DataAccessException {
        StringBuilder builder = new StringBuilder();
        builder.append(" select rol from AsignacionRolPerfil as arf ")
               .append(" inner join  arf.rol as rol ")
               .append(" where arf.perfil = :perfil")
               .append(" order by rol.nombre");

        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("perfil", perfil);
        return query.list();
    }
//______________________________________________________________________________
    @Override
    public List<Rol> getRolesNoAsignadosPorPerfil(Perfil perfil) throws DataAccessException {
        StringBuilder builder = new StringBuilder();
        builder.append(" select rol from Rol as rol where rol.idRol not in ( ")
               .append(" select arp.rol.idRol from AsignacionRolPerfil as arp where " )
               .append(" arp.perfil = :perfil)")
               .append(" order by rol.nombre");

        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("perfil", perfil);
        return query.list();
    }

//______________________________________________________________________________
    @Override
    public List<AsignacionRolPerfil> getAsignacionRolPerfilPorPerfil(Perfil perfil) throws DataAccessException {
        StringBuilder builder = new StringBuilder();
        builder.append(" select arf from AsignacionRolPerfil as arf ")
               .append(" inner join  arf.rol as rol ")
               .append(" where arf.perfil = :perfil")
               .append(" order by rol.nombre");

        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("perfil", perfil);
        return query.list();
    }
}
