/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.servicio.impl;

import gt.edu.usac.cats.dominio.AsignacionCursoPensum;
import gt.edu.usac.cats.dominio.Carrera;
import gt.edu.usac.cats.dominio.Curso;
import gt.edu.usac.cats.dominio.Pensum;
import gt.edu.usac.cats.servicio.ServicioCarrera;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p></p>
 *
 * @author Daniel Castillo
 * @version 1.0
 */
@Service("servicioCarreraImpl")
public class ServicioCarreraImpl extends ServicioGeneralImpl implements ServicioCarrera {

    /**
     * <p>Constructor predeterminado de la clase.</p>
     */
    public ServicioCarreraImpl() {}
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
     * <p>Este metodo se encarga de borrar el objeto {@link Pensum}, enviado
     * como parametro, de la base de datos. Para que un pensum pueda ser
     * borrado debe de tener estado = 0.</p>
     *
     * @param pensum Objeto a borrar
     * @return true Si y solo si se borro el pensum
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean borrarPensum(Pensum pensum) throws DataAccessException {
        if(pensum.getEstado() == 0) {
            this.daoGeneralImpl.delete(pensum);
            return true;
        } else {
            return false;
        }
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de activar los pensum que se encuentran en
     * estado = 0, o estado de creacion.</p>
     *
     * @param pensum Objeto a borrar
     * @return true Si y solo si se activo el pensum
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean activarPensum(Pensum pensum) throws DataAccessException {
        if(pensum.getEstado() == 0) {
            pensum.setEstado(new Short("1"));
            this.daoGeneralImpl.update(pensum);
            return true;
        } else {
            return false;
        }
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de caducar los pensum. Los unicos pensum que
     * se pueden caducar son los pensum activos, los que tienen estado = 1.</p>
     *
     * @param pensum Objeto a caducar
     * @return true Si y solo si se caduco el pensum
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean caducarPensum(Pensum pensum) throws DataAccessException {
        if(pensum.getEstado() == 1) {
            pensum.setEstado(new Short("2"));
            pensum.setFechaFin(new Date());
            this.daoGeneralImpl.update(pensum);
            return true;
        } else {
            return false;
        }
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
    public Pensum buscarPensumPorCodigo(String codigo) throws DataAccessException {

        // se busca el pensum por el codigo
        DetachedCriteria criteria = DetachedCriteria.forClass(Pensum.class);
        criteria.add(Restrictions.eq("codigo", codigo));

        // se retorna el estudiante o null sino se encontro
        return this.daoGeneralImpl.uniqueResult(criteria);
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de buscar y obtener todos los cursos que no
     * estan actualmente asignados al pensum.</p>
     *
     * @param pensum {@link Pensum}
     * @return {@link List} Listado de {@link Curso}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    @Override
    public List<Curso> buscarCursosNoAsignados(Pensum pensum) throws DataAccessException {
        String hql = "FROM Curso AS C1 " +
                     "WHERE C1.idCurso NOT IN " +
                        "(SELECT C2.idCurso " +
                        "FROM Curso AS C2 " +
                            "JOIN C2.asignacionCursoPensums AS A1 " +
                            "JOIN A1.pensum AS P1 " +
                        "WHERE P1.idPensum = " + pensum.getIdPensum() + ")";

        return this.daoGeneralImpl.find(hql);
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo permite asignar el {@link Curso} enviado como parametro
     * al {@link Pensum} que tambien se envia como parametro.</p>
     *
     * @param pensum {@link Pensum} al que se la va a realizar la asignacion
     * @param curso {@link Curso} que se debe de asignar
     * @param obligatorio <code>true</code> si el {@link Curso} es obligatorio
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void agregarCursoPensum(Pensum pensum, Curso curso, boolean obligatorio) throws DataAccessException {
        AsignacionCursoPensum a = new AsignacionCursoPensum();
        a.setPensum(pensum);
        a.setCurso(curso);
        a.setObligatorio(obligatorio);

        this.daoGeneralImpl.save(a);
    }
}
