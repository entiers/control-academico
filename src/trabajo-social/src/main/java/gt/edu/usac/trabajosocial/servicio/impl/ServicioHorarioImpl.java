/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.servicio.impl;

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
 * <p>Contiene la implementación de los metodos que permiten el manejo de la
 * informacion relacionada con el horario en la base de datos.</p>
 *
 * @see ServicioHorario
 * @author Mario Batres
 * @version 1.0
 */
@Service("servicioHorarioImpl")
public class ServicioHorarioImpl implements ServicioHorario {

    /** <p>Permite el acceso y manipulacion a la base de datos.</p> */
    @Resource
    private DaoGeneral daoGeneralImpl; 
//______________________________________________________________________________
    /**
     * <p></p>
     * @param horario
     * @throws DataIntegrityViolationException
     * @throws DataAccessException
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void agregarHorario(Horario horario) 
            throws DataIntegrityViolationException, DataAccessException {

        this.daoGeneralImpl.save(horario);
    }
//______________________________________________________________________________
    /**
     * <p></p>
     * @param salon
     * @param semestre
     * @return
     * @throws DataAccessException
     */
    @Override
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
    /**
     * <p>Este metodo permite actualizar la informacion de un horario a la base
     * de datos.
     *
     * @param horario Pojo del tipo {@link Horario}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     * @throws DataIntegrityViolationException Si ocurrio una violacion de
     *         de integridad de datos
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void actualizarHorario(Horario horario)
        throws DataAccessException {

        this.daoGeneralImpl.update(horario);
    }

//______________________________________________________________________________
    /**
     * <p>Obtiene el horario según el id que poose</p>
     *
     * @param id Identificador del horario
     *
     * @throws DataAccessException Si oucrrió un error de acesso a datos
     */
    @Override
    public Horario getHorarioPorID(int id) throws DataAccessException {
        DetachedCriteria criteria = DetachedCriteria.forClass(Horario.class);
        criteria.add(Restrictions.eq("idHorario", id));

        return this.daoGeneralImpl.uniqueResult(criteria);
    }
}
