/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.servicio.impl;

import gt.edu.usac.cats.dao.impl.DaoGeneralImpl;
import gt.edu.usac.cats.dominio.AsignacionUsuarioPerfil;
import gt.edu.usac.cats.dominio.Perfil;
import gt.edu.usac.cats.dominio.busqueda.DatosBusquedaUsuario;
import gt.edu.usac.cats.dominio.Usuario;
import gt.edu.usac.cats.servicio.ServicioUsuario;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * <p></p>
 *
 * @author Carlos Solórzano
 * @version 1.0
 */
@Service("servicioUsuarioImpl")
public class ServicioUsuarioImpl extends ServicioGeneralImpl implements ServicioUsuario {

    @Override
    public List<Usuario> getListadoUsuarios(DatosBusquedaUsuario datos) throws HibernateException {
        // se crea la consulta
        Criteria criteria = this.crearCriteriaBusqueda(datos);

        // se ordena el resultado
        if(datos.isOrdenAscendente())
            criteria.addOrder(Order.asc(datos.getColumnaOrden()));
        else
            criteria.addOrder(Order.desc(datos.getColumnaOrden()));

        // se pagina el resultado
        criteria.setFirstResult(datos.getPrimerRegistro());
        criteria.setMaxResults(DaoGeneralImpl.REGISTROS_MAXIMOS);

        return criteria.list();
    }
//______________________________________________________________________________
    @Override
    public Integer rowCount(DatosBusquedaUsuario datos) throws HibernateException {
         // se crea la consulta
        Criteria criteria = this.crearCriteriaBusqueda(datos);

        criteria.setProjection(Projections.rowCount());

        return ((Long) criteria.list().get(0)).intValue();
    }
//______________________________________________________________________________
    private Criteria crearCriteriaBusqueda(DatosBusquedaUsuario datos) {
        // se crea la consulta
        Criteria criteria = this.daoGeneralImpl.getSesion().createCriteria(Usuario.class);

        // se crean los filtro de la busqueda
        Criterion eqNombre = Restrictions.ilike("nombreUsuario", datos.getNombreUsuario(), MatchMode.ANYWHERE);
        Criterion eqHabilitado = Restrictions.eq("habilitado", datos.isHabilitado());

        criteria.add(eqHabilitado);

        if (!datos.getNombreUsuario().isEmpty())
            criteria.add(eqNombre);

        return criteria;
    }
//______________________________________________________________________________
    @Override
    public List<Perfil> getPerfilesAsignadosPorUsuario(Usuario usuario) throws DataAccessException {
        StringBuilder builder = new StringBuilder();
        builder.append(" select per from AsignacionUsuarioPerfil as aup ")
               .append(" inner join aup.perfil as per")
               .append(" where aup.usuario = :usuario")
               .append(" order by aup.perfil.nombre");

        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("usuario", usuario);
        return query.list();
    }
//______________________________________________________________________________
    @Override
    public List<Perfil> getPerfilesNoAsignadosPorUsuario(Usuario usuario) throws DataAccessException {
        StringBuilder builder = new StringBuilder();
        builder.append(" select perfil from Perfil as perfil where perfil.idPerfil not in ( ")
               .append(" select aup.perfil.idPerfil from AsignacionUsuarioPerfil as aup where " )
               .append(" aup.usuario = :usuario)")
               .append(" order by perfil.nombre");
        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("usuario", usuario);
        return query.list();
    }
//______________________________________________________________________________
    @Override
    public List<AsignacionUsuarioPerfil> getAsignacionUsuarioPerfilPorUsuario(Usuario usuario) throws DataAccessException {
        StringBuilder builder = new StringBuilder();
        builder.append(" select aup from AsignacionUsuarioPerfil as aup ")
               .append(" where aup.usuario = :usuario ")
               .append(" order by aup.perfil.nombre");
        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("usuario", usuario);
        return query.list();
    }

    @Override
    public Usuario cargarUsuarioPorNombre(String nombreUsuario) throws HibernateException {
        // se busca el usuario por nombre
        DetachedCriteria criteria = DetachedCriteria.forClass(Usuario.class);
        criteria.add(Restrictions.eq("nombreUsuario", nombreUsuario));

        // se retorna el usuario o null sino se encontro
        return this.daoGeneralImpl.uniqueResult(criteria);
    }

    @Override
    public Usuario getUsuarioPorEmail(String email) throws HibernateException {
        Usuario usuario = null;
        List lista;
        StringBuilder builderEst = new StringBuilder();
        StringBuilder builderCat = new StringBuilder();
        Query query;

        builderEst.append(" Select est.usuario from Estudiante as est")
               .append(" where est.email = :email ");
        query = this.daoGeneralImpl.getSesion().createQuery(builderEst.toString());
        query.setParameter("email", email);
        lista = query.list();
        if(lista.isEmpty()){
            builderCat.append(" Select cat.usuario from Catedratico as cat")
                   .append(" where cat.email = :email ");
            query = this.daoGeneralImpl.getSesion().createQuery(builderCat.toString());
            query.setParameter("email", email);
            lista = query.list();
        }
        if(!lista.isEmpty())
            usuario = (Usuario) lista.get(0);
        return usuario;
    }

}
