/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.servicio.impl;

import gt.edu.usac.trabajosocial.dao.DaoGeneral;
import gt.edu.usac.trabajosocial.dominio.Curso;
import gt.edu.usac.trabajosocial.servicio.ServicioCurso;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>Contiene la implementacion de los metodos que permiten el manejo de la
 * informacion relacionada con el curso en la base de datos.</p>
 *
 * @see ServicioCurso
 * @author Mario Batres
 * @version 1.0
 */
@Service("servicioCursoImpl")
public class ServicioCursoImpl implements ServicioCurso{

    /** <p>Permite el acceso y manipulacion a la base de datos.</p> */
    @Resource
    private DaoGeneral daoGeneralImpl;
//______________________________________________________________________________
     /**
     * <p>Este metodo se encarga de agregar los datos del curso a la base
     * de datos.</p>
     *
     * @param curso Pojo del tipo {@link Curso}
     * @throws DataIntegrityViolationException Se dio una violacion de integridad
     *         de los datos
     * @throws DataAccessException Ocurrio un error con el acceso a la base de
     *         datos
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void agregarCurso(Curso curso) 
            throws DataIntegrityViolationException, DataAccessException {

        this.daoGeneralImpl.save(curso);
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo permite actualizar los datos del curso.</p>
     *
     * @param curso Pojo del tipo {@link Curso}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void actualizarCurso(Curso curso)
            throws DataAccessException {

        this.daoGeneralImpl.update(curso);
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo permite la busqueda de curso por su código.</p>
     *
     * @param codigo Código del curso
     * @return Curso
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    @Override
    public Curso buscarCursoPorCodigo(String codigo) 
            throws DataAccessException {

        // se busca el estudiante por el numero de carne
        DetachedCriteria criteria = DetachedCriteria.forClass(Curso.class);
        criteria.add(Restrictions.eq("codigo", codigo));

        // se retorna el estudiante o null sino se encontro
        return this.daoGeneralImpl.uniqueResult(criteria);
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo permite obtener todos los salones disponibles</p>
     *
     * @return List de tipo {@link Salon}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    @Override
    public List<Curso> getCursos() throws DataAccessException {
        return this.daoGeneralImpl.loadAll(Curso.class);
    }
}
