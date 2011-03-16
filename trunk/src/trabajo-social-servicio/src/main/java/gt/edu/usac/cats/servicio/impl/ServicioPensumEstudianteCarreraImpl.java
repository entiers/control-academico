/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cats.servicio.impl;

import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.PensumEstudianteCarrera;
import gt.edu.usac.cats.servicio.ServicioPensumEstudianteCarrera;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
@Service
public class ServicioPensumEstudianteCarreraImpl extends ServicioGeneralImpl implements ServicioPensumEstudianteCarrera {

    private DetachedCriteria crearCriteriaPorEstudianteYValido(boolean valido, Estudiante estudiante) {
        DetachedCriteria criteria = DetachedCriteria.forClass(PensumEstudianteCarrera.class);
        criteria.createAlias("asignacionEstudianteCarrera", "aec");
        criteria.createAlias("pensum", "p");
        //solo para asegurarse que la carrera es la misma para la asignacion y el pensum
        criteria.createAlias("p.carrera", "c");
        criteria.add(Restrictions.eqProperty("p.carrera", "aec.carrera"));
        criteria.add(Restrictions.eq("valido", valido));
        criteria.add(Restrictions.eq("aec.estudiante", estudiante));

        return criteria;
    }

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

    @Override
    public PensumEstudianteCarrera getPensumEstudianteCarreraValido(Estudiante estudiante) throws DataAccessException {
        //Se tiene el conocimiento que siempre habrá un registro valido = true en la BD
        return this.daoGeneralImpl.uniqueResult(this.crearCriteriaPorEstudianteYValido(true, estudiante));
    }

    @Override
    public List<PensumEstudianteCarrera> getListadoPensumEstudianteCarreraNoValidos(Estudiante estudiante) throws DataAccessException {
        return this.daoGeneralImpl.find(this.crearCriteriaPorEstudianteYValido(false, estudiante));
    }

    @Override
    public PensumEstudianteCarrera getPensumEstudianteCarreraValido(AsignacionEstudianteCarrera asignacionEstudianteCarrera) throws DataAccessException {
        return this.daoGeneralImpl.uniqueResult(this.crearCriteriaPorAsignacionEstudianteCarreraYValido(true, asignacionEstudianteCarrera));
    }

    @Override
    public List<PensumEstudianteCarrera> getListadoPensumEstudianteCarreraNoValidos(AsignacionEstudianteCarrera asignacionEstudianteCarrera) throws DataAccessException {
        return this.daoGeneralImpl.find(this.crearCriteriaPorAsignacionEstudianteCarreraYValido(false, asignacionEstudianteCarrera));
    }
}
