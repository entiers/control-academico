/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.servicio.implementacion;

import gt.edu.usac.trabajosocial.dao.DaoGeneral;
import gt.edu.usac.trabajosocial.dominio.AsignacionCatedraticoEscuela;
import gt.edu.usac.trabajosocial.dominio.Catedratico;
import gt.edu.usac.trabajosocial.dominio.Escuela;
import gt.edu.usac.trabajosocial.dominio.Usuario;
import gt.edu.usac.trabajosocial.servicio.ServicioCatedratico;
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
@Service("servicioCatedraticoImpl")
public class ServicioCatedraticoImpl implements ServicioCatedratico {

    /** Permite el acceso y manipulacion a la base de datos */
    @Resource
    private DaoGeneral daoGeneralImpl;


    /**
     * <p>Constructor predeterminado de la clase.</p>
     */
    public ServicioCatedraticoImpl(){}
//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de agregar los datos del catedratico a la base
     * de datos. Para agregar la informacion del catedratico el metodo realiza
     * los siguientes pasos:
     * <ul>
     * <li>Crea un {@link Usuario} para el catedratico</li>
     * <li>Almacena la informacion del {@link Catedratico}</li>
     * <li>Y por ultimo realiza la asignacion de la {@link Escuela} al
     * catedratico</li>
     * </ul>
     * Los pasos anteriores se realizan dentro de una transaccion por lo tanto,
     * o se ejecutan todos o no se ejecuta ninguno.</p>
     *
     * @param catedratico Pojo del tipo {@link Catedratico}
     * @param escuela Pojo del tipo {@link Escuela}
     * @throws DataIntegrityViolationException Se dio una violacion de integridad
     *         de los datos
     * @throws DataAccessException Ocurrio un error con el acceso a la base de
     *         datos
     */
    @Transactional(rollbackFor = Exception.class)
    public void agregarCatedratico(Catedratico catedratico, Escuela escuela)
            throws DataIntegrityViolationException, DataAccessException {

        // se genera el usuario para el estudiante
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(catedratico.getCodigo());
        usuario.setPassword(catedratico.getCodigo());
        usuario.setHabilitado(true);

        // se guarda el usuario en la BD
        this.daoGeneralImpl.save(usuario);

        // se le asigna el usuario al catedratico
        catedratico.setUsuario(usuario);

        // se guarda el estudiante en la BD
        this.daoGeneralImpl.save(catedratico);

        // se asigna la carrera al estudiante
        this.asignarEscuela(catedratico, escuela);
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo permite actualizar los datos de un catedratico.</p>
     *
     * @param catedratico Pojo del tipo {@link Catedratico}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    @Transactional(rollbackFor = Exception.class)
    public void actualizarCatedratico(Catedratico catedratico)
            throws DataAccessException {

        this.daoGeneralImpl.update(catedratico);
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo asigna una {@link Escuela} al {@link Catedratico}. El
     * metodo se encarga de crear el objeto {@link AsignacionCatedraticoEscuela}
     * el cual representa la asignacion de escuela y catedratico.</p>
     *
     * @param catedratico Pojo del tipo {@link Catedratico}
     * @param escuela Pojo del tipo {@link Escuela}
     * @throws DataAccessException Ocurrio un error con el acceso a la base de
     *         datos
     */
    @Transactional(rollbackFor = Exception.class)
    public void asignarEscuela(Catedratico catedratico, Escuela escuela)
            throws DataAccessException {

        // se crea la nueva asignacion de carrera del estudiante
        AsignacionCatedraticoEscuela asignacionEscuela = new AsignacionCatedraticoEscuela();
        asignacionEscuela.setEscuela(escuela);
        asignacionEscuela.setCatedratico(catedratico);
        asignacionEscuela.setFechaInicio(new Date());

        // se guarda la asignacion en la base de datos
        this.daoGeneralImpl.save(asignacionEscuela);
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo permite la busqueda de catedraticos por su codigo de
     * personal.</p>
     *
     * @param codigo Numero de personal del catedratico
     * @return Catedratico
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    public Catedratico buscarCatedraticoPorCodigo(String codigo)
            throws DataAccessException {

        // se busca el estudiante por el numero de carne
        DetachedCriteria criteria = DetachedCriteria.forClass(Catedratico.class);
        criteria.add(Restrictions.eq("codigo", codigo));

        // se retorna el estudiante o null sino se encontro
        return this.daoGeneralImpl.uniqueResult(criteria);
    }
}
