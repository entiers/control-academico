/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.servicio.impl;

import gt.edu.usac.trabajosocial.dao.DaoGeneral;
import gt.edu.usac.trabajosocial.dominio.Carrera;
import gt.edu.usac.trabajosocial.dominio.Pensum;
import gt.edu.usac.trabajosocial.servicio.ServicioPensum;
import java.util.Date;
import javax.annotation.Resource;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Daniel Castillo
 * @version 1.0
 */
@Service("servicioPensumImpl")
public class ServicioPensumImpl implements ServicioPensum {

    /** Permite el acceso y manipulacion a la base de datos */
    @Resource
    private DaoGeneral daoGeneralImpl;
//______________________________________________________________________________
    /**
     * <p>Constructor predeterminado de la clase.</p>
     */
    public ServicioPensumImpl(){}
//______________________________________________________________________________
    /**
     * <p>Este metodo permite la creación de un nuevo pensum, el metodo solo
     * crea el pensum no realiza la asignacion de cursos al pensum. Los pasos
     * que sigue el metodo para crear el pensum son:
     * <ul>
     * <li>Agrega el estado del pensum, el estado indica que se puede hacer con
     * el pensum. Los estados pueden ser: 0 pensum en creacion, 1 pensum vigente
     * y 2 pensum caducado. En este caso el estado se coloca en 0.</li>
     * <li>Se agrega la fecha inicio.</li>
     * <li>Se asigna la carrera a la que pertenece el pensum.</li>
     * <li>Se hace persistente la informacion.</li>
     * </ul>
     * </p>
     *
     * @param carrera Pojo de tipo {@link Carrera}
     * @param pensum Pojo del tipo {@link Pensum}
     * @throws DataIntegrityViolationException Si ocurre un error de integridad
     *         de datos, en este caso se ingreso un codigo ya existente
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void agregarPensum(Pensum pensum, Carrera carrera)
            throws DataIntegrityViolationException, DataAccessException {

        // el estado establece que se puede hacer con el pensum
        pensum.setEstado(new Short("0"));

        // se agrega la fecha actual como fecha de inicio
        pensum.setFechaInicio(new Date());

        // se agrega la carrera
        pensum.setCarrera(carrera);
        
        // se guarda el pensum en la BD
        this.daoGeneralImpl.save(pensum);
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo realiza busquedas de pensum por medio de su codigo, como el
     * codigo de pensum es unico, el metodo solo retorna un objeto {@link Pensum},
     * en el caso de no encontrar ningun objeto retorna <code>null</code>.</p>
     *
     * @param codigo Codigo de pensum
     * @return Pensum Si encuentra uno, o <code>null</code> de no encontrar
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    @Override
    public Pensum buscarPensumPorCodigo(String codigo)
            throws DataAccessException {

        // se busca el pensum por el codigo
        DetachedCriteria criteria = DetachedCriteria.forClass(Pensum.class);
        criteria.add(Restrictions.eq("codigo", codigo));

        // se retorna el estudiante o null sino se encontro
        return this.daoGeneralImpl.uniqueResult(criteria);
    }
}
