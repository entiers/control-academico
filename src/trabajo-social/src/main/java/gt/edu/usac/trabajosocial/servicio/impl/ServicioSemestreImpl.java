/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.servicio.impl;

import gt.edu.usac.trabajosocial.dao.DaoGeneral;
import gt.edu.usac.trabajosocial.dominio.Semestre;
import gt.edu.usac.trabajosocial.servicio.ServicioSemestre;
import javax.annotation.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
@Service("servicioSemestreImpl")
public class ServicioSemestreImpl implements ServicioSemestre{

    /** <p>Permite el acceso y manipulacion a la base de datos.</p> */
    @Resource
    private DaoGeneral daoGeneralImpl;
//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de agregar los datos del semestre a la base
     * de datos.
         *
     * @param semestre Pojo del tipo {@link Semestre}
     * @throws DataIntegrityViolationException Se dio una violacion de integridad
     *         de los datos
     * @throws DataAccessException Ocurrio un error con el acceso a la base de
     *         datos
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void agregarSemestre(Semestre semestre)
            throws DataIntegrityViolationException, DataAccessException {

        this.daoGeneralImpl.save(semestre);
    }
}
