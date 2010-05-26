/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.trabajosocial.servicio.implementacion;

import gt.edu.usac.trabajosocial.dao.DaoGeneral;
import gt.edu.usac.trabajosocial.dominio.Horario;
import gt.edu.usac.trabajosocial.dominio.Salon;
import gt.edu.usac.trabajosocial.dominio.Semestre;
import gt.edu.usac.trabajosocial.servicio.ServicioHorario;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */

@Service("servicioHorarioImpl")
public class ServicioHorarioImpl implements ServicioHorario {

//______________________________________________________________________________
    /** Permite el acceso y manipulacion a la base de datos */
    @Resource
    private DaoGeneral daoGeneralImpl;
    
//______________________________________________________________________________
    @Transactional(rollbackFor = Exception.class)
    public void agregarHorario(Horario horario) throws DataIntegrityViolationException, DataAccessException {
        this.daoGeneralImpl.save(horario);
    }
//______________________________________________________________________________
    public List <Horario> buscarHorarioPorSalonYSemestre(Salon salon, Semestre semestre)
            throws DataAccessException {

        DetachedCriteria criteria = DetachedCriteria.forClass(Horario.class);

        criteria.add(Restrictions.and(
                Restrictions.eq("salon", salon),
                Restrictions.eq("semestre", semestre))
                );

        return this.daoGeneralImpl.find(criteria);
    }
    
//______________________________________________________________________________
    public void actualizarHorario(Horario horario)
        throws DataAccessException{
        this.daoGeneralImpl.update(horario);
    }
}
