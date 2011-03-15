/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.servicio.impl;

import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.PensumEstudianteCarrera;
import gt.edu.usac.cats.servicio.ServicioPensumEstudianteCarrera;
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
public class ServicioPensumEstudianteCarreraImpl extends ServicioGeneralImpl implements ServicioPensumEstudianteCarrera{

    @Override
    public PensumEstudianteCarrera getPensumEstudianteCarreraValido(Estudiante estudiante) throws DataAccessException {
        DetachedCriteria criteria = DetachedCriteria.forClass(PensumEstudianteCarrera.class);

        criteria.add(Restrictions.eq("valido", true));
        criteria.add(Restrictions.eq("estudiante", estudiante));

        //Se tiene el conocimiento que siempre habrá un registro valido = true en la BD
        return this.daoGeneralImpl.uniqueResult(criteria);
    }

}
