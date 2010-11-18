/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.servicio.impl;

import gt.edu.usac.cats.dao.impl.DaoGeneralImpl;
import gt.edu.usac.cats.dominio.busqueda.DatosBusquedaUsuario;
import gt.edu.usac.cats.dominio.Usuario;
import gt.edu.usac.cats.servicio.ServicioUsuario;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
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

    @Override
    public Integer rowCount(DatosBusquedaUsuario datos) throws HibernateException {
         // se crea la consulta
        Criteria criteria = this.crearCriteriaBusqueda(datos);

        criteria.setProjection(Projections.rowCount());

        return ((Long) criteria.list().get(0)).intValue();
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de crear el objeto {@link Criteria} que se usa
     * para realizar las busquedas en base a los parametros de busqueda ingresados
     * por el usuario.</p>
     * @param datos Objeto {@link DatosBusquedaUsuario} que contiene los
     *        parametros de busqueda ingresados por el usuario
     * @return Criteria
     */
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

}
