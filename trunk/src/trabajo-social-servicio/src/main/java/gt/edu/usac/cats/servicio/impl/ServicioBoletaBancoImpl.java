/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */


package gt.edu.usac.cats.servicio.impl;

import gt.edu.usac.cats.dominio.AsignacionCursoPensum;
import gt.edu.usac.cats.dominio.BoletaBanco;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.enums.TipoRubro;
import gt.edu.usac.cats.servicio.ServicioBoletaBanco;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
@Service("servicioBoletaBancoImpl")
public class ServicioBoletaBancoImpl extends ServicioGeneralImpl implements ServicioBoletaBanco{
//______________________________________________________________________________
    @Override
    public List<BoletaBanco> listadoBoletaBanco(Estudiante estudiante, AsignacionCursoPensum asignacionCursoPensum, Semestre semestre, TipoRubro tipoRubro) throws DataAccessException {
        DetachedCriteria criteria = DetachedCriteria.forClass(BoletaBanco.class);

        criteria.add(Restrictions.eq("estudiante", estudiante));
        criteria.add(Restrictions.eq("asignacionCursoPensum", asignacionCursoPensum));
        criteria.add(Restrictions.eq("semestre", semestre));
        criteria.add(Restrictions.eq("tipoRubro", tipoRubro));
        
        return this.daoGeneralImpl.find(criteria);
    }

}
