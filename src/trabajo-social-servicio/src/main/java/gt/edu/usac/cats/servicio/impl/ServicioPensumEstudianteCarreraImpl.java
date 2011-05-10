/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cats.servicio.impl;

import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.Carrera;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.Pensum;
import gt.edu.usac.cats.dominio.PensumEstudianteCarrera;
import gt.edu.usac.cats.servicio.ServicioPensumEstudianteCarrera;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * <p>Contiene la implementaci&oacute;n de los m&eacute;todos que permiten el manejo de la
 * informaci&oacute;n relacionada con la asignaci&oacute;n de pensum a un estudiante
 * de una carrera en la base de datos.</p>
 *
 * @author Mario Batres
 * @version 1.0
 */
@Service
public class ServicioPensumEstudianteCarreraImpl extends ServicioGeneralImpl implements ServicioPensumEstudianteCarrera {
//______________________________________________________________________________
    /**
     * Crea un criteria para la b&uacute;squeda de asignaciones de pensums por estudiante y carrera.
     * La b&uacute;squeda se realiza por estudiante.
     *
     * @param valido Param&eacute;tro de tipo boolean
     * @param estudiante Objeto de tipo {@link Estudiante}
     *
     * @return Objeto de tipo {@link DetachedCriteria}
     */
    private DetachedCriteria crearCriteriaPorEstudianteYValido(boolean valido, Estudiante estudiante, Carrera carrera) {
        DetachedCriteria criteria = DetachedCriteria.forClass(PensumEstudianteCarrera.class);
        criteria.createAlias("asignacionEstudianteCarrera", "aec");
        criteria.createAlias("pensum", "p");
        criteria.add(Restrictions.eq("aec.carrera", carrera));
        criteria.add(Restrictions.eqProperty("p.carrera", "aec.carrera"));
        criteria.add(Restrictions.eq("valido", valido));
        criteria.add(Restrictions.eq("aec.estudiante", estudiante));

        return criteria;
    }
//______________________________________________________________________________
    /**
     * Crea un criteria para la b&uacute;squeda de asignaciones de pensums por estudiante y carrera.
     * La b&uacute;squeda se realiza por la asignacion de un estudiante con la carrera.
     *
     * @param valido Param&eacute;tro de tipo boolean
     * @param asignacionEstudianteCarrera Objeto de tipo {@link AsignacionEstudianteCarrera}
     *
     * @return Objeto de tipo {@link DetachedCriteria}
     */
    private DetachedCriteria crearCriteriaPorAsignacionEstudianteCarreraYValido(boolean valido,
            AsignacionEstudianteCarrera asignacionEstudianteCarrera) {

        DetachedCriteria criteria = DetachedCriteria.forClass(PensumEstudianteCarrera.class);
        criteria.createAlias("pensum", "p");
        //solo para asegurarse que la carrera es la misma para la asignacion y el pensum
        criteria.createAlias("p.carrera", "c");
        criteria.add(Restrictions.eq("p.carrera", asignacionEstudianteCarrera.getCarrera()));
        criteria.add(Restrictions.eq("valido", valido));
        criteria.add(Restrictions.eq("asignacionEstudianteCarrera", asignacionEstudianteCarrera));

        return criteria;
    }
//______________________________________________________________________________
     /**
     * Obitene el pensum v&aacute;lido que tiene un estudiante seg&uacute;n la carrera que tenga asignada.
     *
     * @param asignacionEstudianteCarrera Objeto de tipo {@link AsignacionEstudianteCarrera}
     *
     * @throws DataAccessException Si ocurri&oacute; un error de acceso a datos
     *
     * @return Objeto de tipo {@link PensumEstudianteCarrera}
     */
    @Override
    public PensumEstudianteCarrera getPensumEstudianteCarreraValido(Estudiante estudiante, Carrera carrera)
            throws DataAccessException {
        //Se tiene el conocimiento que siempre habr'a un registro valido = true en la BD
        return this.daoGeneralImpl.uniqueResult(this.crearCriteriaPorEstudianteYValido(true, estudiante, carrera));
    }
//______________________________________________________________________________
    /**
     * Obtiene los pensums asignados no v&aacute;lidos de un estudiante.
     *
     * @param estudiante Objeto de tipo {@link Estudiante}
     *
     * @throws DataAccessException Si ocurri&oacute; un error de acceso a datos
     *
     * @return Lista de objetos de tipo {@link PensumEstudianteCarrera}
     */
    @Override
    public List<PensumEstudianteCarrera> getListadoPensumEstudianteCarreraNoValidos(Estudiante estudiante, Carrera carrera) throws DataAccessException {
        return this.daoGeneralImpl.find(this.crearCriteriaPorEstudianteYValido(false, estudiante, carrera));
    }

//______________________________________________________________________________
     /**
     * Obtiene los pensums asignados no v&aacute;lidos de un estudiante.
     *
     * @param estudiante Objeto de tipo {@link Estudiante}
     *
     * @throws DataAccessException Si ocurri&oacute; un error de acceso a datos
     *
     * @return Lista de objetos de tipo {@link PensumEstudianteCarrera}
     */
    @Override
    public PensumEstudianteCarrera getPensumEstudianteCarreraValido(AsignacionEstudianteCarrera asignacionEstudianteCarrera) throws DataAccessException {
        return this.daoGeneralImpl.uniqueResult(this.crearCriteriaPorAsignacionEstudianteCarreraYValido(true, asignacionEstudianteCarrera));
    }

//______________________________________________________________________________    
    /**
     * Obtiene los pensums asignados no v&aacute;lidos de un estudiante seg&uacute;n la carrera que tenga asignada.
     *
     * @param asignacionEstudianteCarrera Objeto de tipo {@link AsignacionEstudianteCarrera}
     *
     * @throws DataAccessException Si ocurri&oacute; un error de acceso a datos
     *
     * @return Lista de objetos de tipo {@link PensumEstudianteCarrera}
     */

    @Override
    public List<PensumEstudianteCarrera> getListadoPensumEstudianteCarreraNoValidos(AsignacionEstudianteCarrera asignacionEstudianteCarrera) throws DataAccessException {
        return this.daoGeneralImpl.find(this.crearCriteriaPorAsignacionEstudianteCarreraYValido(false, asignacionEstudianteCarrera));
    }

//______________________________________________________________________________    
     /**
     * Obtiene los pensusm no asignados de un estudiante por la carrera que tenga asignada.
     *
     * @param asignacionEstudianteCarrera Objeto de tipo {@link AsignacionEstudianteCarrera}
     *
     * @throws DataAccessException Si ocurri&oacute; un error de acceso a datos
     */
    @Override
    public List<Pensum> getPensumsNoAsignadosAEstudianteCarrera(AsignacionEstudianteCarrera asignacionEstudianteCarrera) throws DataAccessException {
        StringBuilder builder = new StringBuilder();

        builder.append(" select p from Pensum p");
        builder.append(" where p.idPensum not in (select pec.pensum.idPensum from PensumEstudianteCarrera pec");
        builder.append(" where pec.asignacionEstudianteCarrera = :asignacionEstudianteCarrera)");
        builder.append(" and p.carrera = :carrera");


        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("carrera", asignacionEstudianteCarrera.getCarrera());
        query.setParameter("asignacionEstudianteCarrera", asignacionEstudianteCarrera);

        return query.list();
    }
}
