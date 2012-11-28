/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */
package gt.edu.usac.cats.servicio.impl;

import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.Carrera;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.HistorialAsignacionEstudianteCarrera;
import gt.edu.usac.cats.servicio.ServicioAsignacionEstudianteCarrera;
import java.util.Calendar;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 * <p>Contiene la implementaci&oacute;n de los m&eacute;todos que permiten el manejo de la
 * informaci&oacute;n relacionada con la asignaci&oacute;n de un estudiante
 * a una carrera en la base de datos.</p>
 *
 * @author Carlos Solorzano y Mario Batres
 * @version 1.0
 */
@Service("servicioAsignacionEstudianteCarreraImpl")
public class ServicioAsignacionEstudianteCarreraImpl extends ServicioGeneralImpl implements ServicioAsignacionEstudianteCarrera{
//______________________________________________________________________________
    @Override
      /**
     * <p>&Eacute;ste metodo se encarga de crear un listado de AsignacionEstudianteCarrera, el listado
     * se filtra en base la carrera, el numero de carnet de cada estudiante, en donde este
     * sea igual al anio actual, asi como el estudiante no debe de tener asignaciones previas</p>
     *
     * @param Carrera Contiene los filtros para el listado
     * @return List Listado de AsignacionEstudianteCarrera
     * @throws HibernateException Si ocurrio un error de acceso a datos
     */
    public List<AsignacionEstudianteCarrera> getAsignacionEstudianteCarreraPrimerIngreso(Carrera carrera) throws HibernateException {
        Calendar fecha = Calendar.getInstance();
        StringBuilder builder = new StringBuilder();
        builder.append(" select aec from AsignacionEstudianteCarrera as aec ")
               .append(" left join aec.asignacions as asign")
               .append(" where aec.carrera = :carrera")
               .append(" and aec.estudiante.carne like '")
               .append(String.valueOf(fecha.get(Calendar.YEAR)))
               .append("%' and asign.idAsignacion is null");

        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("carrera", carrera);

        return query.list();
    }
//______________________________________________________________________________
     /**
     * <p>Crear un listado de AsignacionEstudianteCarrera, el listado
     * se filtra en base al estudiante</p>
     *
     * @param estudiante pojo del tipo  {@link Estudiante}
     * @return List Listado de AsignacionEstudianteCarrera
     * @throws HibernateException Si ocurrio un error de acceso a datos
     */
    @Override
    public List<AsignacionEstudianteCarrera> getAsignacionEstudianteCarreraPorEstudiante(Estudiante estudiante){
        StringBuilder builder = new StringBuilder();
        builder.append(" select distinct aec from AsignacionEstudianteCarrera aec ")
               .append(" where aec.estudiante =:estudiante ")
               .append(" and exists elements(")
               .append("    aec.carrera.pensums.asignacionCursoPensums )");
        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("estudiante", estudiante);

        return query.list();
    }

//______________________________________________________________________________
    /**
     * &Eacute;ste m&eacute;todo realiza la b&uacute;squeda de las asignaciones con carreras del estudiante
     * que es enviado como param&eacute;tro y su estado en la base de datos.
     *
     * @param estudiante Objeto de tipo {@link Estudiante}
     * @param estado True si las asignaciones son v&aacute;lidas y False si es lo contrario.
     *
     * @return Lista de objetos de tipo {@link AsignacionEstudianteCarrera}
     *
     * @throws DataAccessException Si ocurri&oacute; un error de acceso a datos
     */
    @Override
    public List<AsignacionEstudianteCarrera> getAsignacionEstudianteCarrera(Estudiante estudiante
            , boolean estado) throws DataAccessException {
        DetachedCriteria criteria = DetachedCriteria.forClass(AsignacionEstudianteCarrera.class);
        criteria.add(Restrictions.eq("estado", estado));
        criteria.add(Restrictions.eq("estudiante", estudiante));
        return this.daoGeneralImpl.find(criteria);
    }

//______________________________________________________________________________
    /**
     * &Eacute;ste m&eacute;todo devuelve el listado de los objetos que no se ha asignado el estudiante,
     * que es enviado como param&eacute;tro.
     *
     * @param estudiante Objeto de tipo {@link Estudiante}
     *
     * @return Lista de boejtos de tipo {@link Carrera}
     *
     * @throws DataAccessException Si ocurri&oacute; un error de acceso a datos
     */
    @Override
    public List<Carrera> getListadoCarrerasNoAsignadasPorEstudiante(Estudiante estudiante) {
        StringBuilder builder = new StringBuilder();
        builder.append(" select c from Carrera c ")
                .append(" where c.idCarrera not in ( ")
                .append(" select acp.carrera.idCarrera from AsignacionEstudianteCarrera acp ")
                .append(" where acp.estudiante = :estudiante) ");

        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("estudiante", estudiante);

        return query.list();
    }

//______________________________________________________________________________
    @Override
     /**
     * <p>&Eacute;ste m&eacute;todo agrega una asignaci&oacute;n a una carrera por un estudiante como
     * tambi&eacute;n el primer registro de su historial.</p>
     *
     * <p>Se realiza de esta manera ya que son varias fases, se debe de agregar primero
     * la asignaci&oacute;n y luego se agrega el historial.</p>
     *
     * @param asignacionEstudianteCarrera Objeto de tipo {@link AsignacionEstudianteCarrera}
     * @param historialAsignacionEstudianteCarrera Objeto de tipo {@link HistorialAsignacionEstudianteCarrera}
     *
     * @throws DataIntegrityViolationException Se efectua la excepcion si hay un nombre de usuario igual en la base de datos.
     * @throws DataAccessException Si ocurri&oacute; un error de acceso a datos
     */
    public void agregarAsignacionEstudianteCarrera(AsignacionEstudianteCarrera asignacionEstudianteCarrera
            , HistorialAsignacionEstudianteCarrera historialAsignacionEstudianteCarrera)
        throws DataIntegrityViolationException, DataAccessException {
      
      
        this.daoGeneralImpl.save(asignacionEstudianteCarrera);

        historialAsignacionEstudianteCarrera.setAsignacionEstudianteCarrera(asignacionEstudianteCarrera);
        this.daoGeneralImpl.save(historialAsignacionEstudianteCarrera);
    }

//______________________________________________________________________________
      /**
     * <p>&Eacute;ste m&eacute;todo realiza el cambio de una carrera de un estudiante.
     * Realiza la modificaci&oacute;n en el estado de la asignaci&oacute;n original y lo actualiza en la
     * base de datos y agregar la nueva asignaci&oacute;n como el primer registro del historial.</p>
     *
     *
     * @param asignacionEstudianteCarreraOriginal Asignaci&oacute;n origianal. Objeto de tipo {@link AsignacionEstudianteCarrera}
     * @param asignacionEstudianteCarreraNueva Nueva asignaci&oacute;n.  Objeto de tipo {@link AsignacionEstudianteCarrera}
     * @param historialAsignacionEstudianteCarrera Objeto de tipo {@link HistorialAsignacionEstudianteCarrera}
     *
     * @throws DataIntegrityViolationException Se efectua la excepcion si hay un nombre de usuario igual en la base de datos.
     * @throws DataAccessException Si ocurri&oacute; un error de acceso a datos
     */
    @Override
    public void realizarCambioAsignacionEstudianteCarrera(AsignacionEstudianteCarrera asignacionEstudianteCarreraOriginal,
            AsignacionEstudianteCarrera asignacionEstudianteCarreraNueva
            , HistorialAsignacionEstudianteCarrera historialAsignacionEstudianteCarrera) throws DataIntegrityViolationException, DataAccessException {

        asignacionEstudianteCarreraOriginal.setEstado(false);

        asignacionEstudianteCarreraNueva.setEstudiante(asignacionEstudianteCarreraOriginal.getEstudiante());

        this.agregarAsignacionEstudianteCarrera(asignacionEstudianteCarreraNueva, historialAsignacionEstudianteCarrera);

        this.daoGeneralImpl.update(asignacionEstudianteCarreraOriginal);

    }

//______________________________________________________________________________

    /**
     * Verifica la inscripci&oacute;n de un estudiante en la carrera que es enviada
     * como par&aacute;metro.
     *
     * @param estudiante Objeto de tipo {@link Estudiante}
     * @param carrera Objeto de tipo {@link Carrera}
     *
     * @throws DataAccessException Si ocurri&oacute; un error de acceso a datos
     */

    @Override
    public boolean estaEstudianteInscrito(Estudiante estudiante, Carrera carrera) throws DataAccessException {
        DetachedCriteria criteria = DetachedCriteria.forClass(AsignacionEstudianteCarrera.class);
        criteria.add(Restrictions.eq("carrera", carrera));
        criteria.add(Restrictions.eq("estudiante", estudiante));

        AsignacionEstudianteCarrera aec = (AsignacionEstudianteCarrera) this.daoGeneralImpl.uniqueResult(criteria);

        return aec.isInscrito();
    }

//______________________________________________________________________________
    /**
     * Verifica si el estudiante est&aacute; asignado en al menos una carrera
     *
     * @param estudiante Objeto de tipo {@link Estudiante}
     *
     * @throws DataAccessException Si ocurri&oacute; un error de acceso a datos
     */
    @Override
    public boolean estaEstudianteInscrito(Estudiante estudiante) throws DataAccessException {
        DetachedCriteria criteria = DetachedCriteria.forClass(AsignacionEstudianteCarrera.class);
        criteria.setProjection(Projections.rowCount());
        criteria.add(Restrictions.eq("inscrito", true));
        criteria.add(Restrictions.eq("estudiante", estudiante));

        Long count = (Long) this.daoGeneralImpl.uniqueResult(criteria);
        return count.intValue() > 0;
    }

    
}
