/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.servicio.impl;

import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.servicio.ServicioSemestre;
import java.util.Calendar;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * <p>Implementacion de los metodos de la interfaz {@link ServicioSemestre}, los
 * cuales permiten el manejo de la informacion relacionada con las semestres.
 * </p>
 *
 * @author Mario Batres
 * @version 1.0
 */

@Service
public class ServicioSemestreImpl extends ServicioGeneralImpl implements ServicioSemestre{


    /**
     * Este metodo retorna todos los semestres cuyo anyo sea mayor o igual
     * al anyo actual.  Se debe de utilizar para la creacion de calendario de actividades.
     *
     *
     * @return Listado de semestres que fueron encontrados.
     *
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    @Override
    public List<Semestre> listarSemestresNoVencidos() throws DataAccessException {
        DetachedCriteria criteria = DetachedCriteria.forClass(Semestre.class);

        Calendar calendar = Calendar.getInstance();
        criteria.add(Restrictions.ge("anio", (short) calendar.get(Calendar.YEAR)));

        return this.daoGeneralImpl.find(criteria);
    }



    /**
     * Este metodo retorna el listado de semestres ordenados por anyo y mes descendentemente.
     * Es de utilizar para la busqueda y modificaciones de calendario de actividades.
     *
     * @return Listado de semestres que fueron encontrados.
     *
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    @Override
    public List<Semestre> listarSemestresParaBusqueda() throws DataAccessException {
        DetachedCriteria criteria = DetachedCriteria.forClass(Semestre.class);

        criteria.addOrder(Order.desc("anio"));
        criteria.addOrder(Order.desc("numero"));

        return this.daoGeneralImpl.find(criteria);
    }

    @Override
    public List<Semestre> listarSemestres(Short anio, char numero) throws DataAccessException {
        DetachedCriteria criteria = DetachedCriteria.forClass(Semestre.class);

        criteria.add(Restrictions.eq("anio",anio));
        criteria.add(Restrictions.eq("numero",numero));
        
        return this.daoGeneralImpl.find(criteria);
    }

}
