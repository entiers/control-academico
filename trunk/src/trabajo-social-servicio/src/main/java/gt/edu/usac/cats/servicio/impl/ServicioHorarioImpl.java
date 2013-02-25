/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.servicio.impl;

import gt.edu.usac.cats.dominio.AsignacionCursoPensum;
import gt.edu.usac.cats.dominio.Catedratico;
import gt.edu.usac.cats.dominio.Horario;
import gt.edu.usac.cats.dominio.HorarioDia;
import gt.edu.usac.cats.dominio.Salon;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.enums.TipoHorario;
import gt.edu.usac.cats.servicio.ServicioHorario;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 * <p>Contiene la implementacion de los metodos que permiten el manejo de la
 * informacion relacionada con el horario en la base de datos.</p>
 *
 * @see ServicioHorario
 * @author Mario Batres
 * @version 1.0
 */
@Service("servicioHorarioImpl")
public class ServicioHorarioImpl extends ServicioGeneralImpl implements ServicioHorario {

    /**
     * <p>Constructor predeterminado de la clase.</p>
     */
    public ServicioHorarioImpl() {}
//______________________________________________________________________________
    /**
     * <p>Este metodo permite obtener un salon por el numero y el nombre del
     * edificio.</p>
     *
     * @param numero Numero del salon
     * @param edificio Nombre del edificio
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    @Override
    public Salon buscarSalonPorNumeroYEdificio(short numero, String edificio)
            throws DataAccessException {

        DetachedCriteria criteria = DetachedCriteria.forClass(Horario.class);

        criteria.add(Restrictions.and(
                Restrictions.eq("numero", numero),
                Restrictions.eq("edificio", edificio))
                );

        return this.daoGeneralImpl.uniqueResult(criteria);
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo permite agregar la informacion de un horario a la base
     * de datos.</p>
     *
     * @param salon Pojo del tipo {@link Salon}
     * @param semestre Pojo del tipo {@link Semestre}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     * @return List
     */
    @Override
    public List <Horario> buscarHorarioPorSalonYSemestre(Salon salon, Semestre semestre)
        throws DataAccessException {

        DetachedCriteria criteria = DetachedCriteria.forClass(Horario.class);

        criteria.add(Restrictions.and(
                Restrictions.eq("salon", salon),
                Restrictions.eq("semestre", semestre))
                );

        criteria.addOrder(Order.asc("horaInicio"));
        criteria.addOrder(Order.asc("horaFin"));
        criteria.addOrder(Order.asc("seccion"));

        return this.daoGeneralImpl.find(criteria);
    }

//______________________________________________________________________________
    @Override
    public Horario getHorarioPorCursoPrimerIngreso(AsignacionCursoPensum asignacionCursoPensum) throws DataAccessException {
        Calendar fecha = Calendar.getInstance();
        
        StringBuilder builder = new StringBuilder();

        builder.append(" select horario.idHorario from Horario as horario ")
               .append(" left join horario.detalleAsignacions as det ")
               .append(" where horario.asignacionCursoPensum = :asignacionCursoPensum ")
               .append(" and horario.tipo = :tipo")
               .append(" and horario.semestre.numero='1' ")
               .append(" and horario.semestre.anio= ")
               .append( String.valueOf(fecha.get(Calendar.YEAR)))
               .append(" and horario.salon.capacidad > (")
               .append("    select count(*) from DetalleAsignacion det")
               .append("    where det.horario = horario")
               .append(" )")
               .append(" group by horario.idHorario")
               .append(" order by count(det)");

        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("AsignacionCursoPensum", asignacionCursoPensum);
        query.setParameter("tipo", TipoHorario.SEMESTRE);

        List list = query.list();
        if (!list.isEmpty()){
            return this.cargarEntidadPorID(Horario.class, Integer.valueOf(list.get(0).toString())) ;
        }
        return null;
    }
//______________________________________________________________________________
    @Override
    public List<Horario> getHorario(AsignacionCursoPensum asignacionCursoPensum, Semestre semestre)
            throws DataAccessException {
        DetachedCriteria criteria = DetachedCriteria.forClass(Horario.class);

        criteria.add(Restrictions.and(
                Restrictions.eq("asignacionCursoPensum", asignacionCursoPensum),
                Restrictions.eq("semestre", semestre))
                );

        return this.daoGeneralImpl.find(criteria);
    }
//______________________________________________________________________________
    @Override
    public List<Horario> getHorarioCambioSeccion(Horario horario) throws DataAccessException {
        StringBuilder builder = new StringBuilder();

        builder.append(" select horario from Horario as horario ")
               .append(" where horario.idHorario <> :idHorario" )
               .append(" and horario.asignacionCursoPensum = :asignacionCursoPensum ")
               .append(" and horario.semestre = :semestre ");
           
        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("idHorario", horario.getIdHorario());
        query.setParameter("asignacionCursoPensum", horario.getAsignacionCursoPensum());
        query.setParameter("semestre", horario.getSemestre());

        return query.list();
    }
//______________________________________________________________________________
    @Override
    public List<Horario> getHorario(AsignacionCursoPensum asignacionCursoPensum) throws DataAccessException {
        StringBuilder builder = new StringBuilder();

        builder.append("select horario from Horario as horario ")
               .append("where horario.asignacionCursoPensum = :asignacionCursoPensum ")
               .append("order by horario.asignacionCursoPensum.curso.nombre");
           
        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("asignacionCursoPensum", asignacionCursoPensum);

        return query.list();
    }

    @Override
    public boolean existeTraslape(List<Horario> listadoHorario) throws DataAccessException {

        //Validando que traslape de horario
        for(Horario h1 : listadoHorario){
            for(Horario h2 : listadoHorario){
                if(h1.getIdHorario()!=h2.getIdHorario()){
                    if((h2.getHoraFin().compareTo(h1.getHoraInicio()) > 0 & h2.getHoraFin().compareTo(h1.getHoraFin()) <= 0)
                        | (h2.getHoraInicio().compareTo(h1.getHoraFin()) < 0 & h2.getHoraInicio().compareTo(h1.getHoraInicio()) >= 0)
                        | (h2.getHoraInicio().compareTo(h1.getHoraInicio()) <= 0 & h2.getHoraFin().compareTo(h1.getHoraFin()) >= 0  ))
                    {
                        //Validando traslape de dias
                        for(Iterator <HorarioDia> it1 = h1.getHorarioDias().iterator(); it1.hasNext();){
                            HorarioDia horarioDia1 = it1.next();
                            for(Iterator <HorarioDia> it2 = h2.getHorarioDias().iterator(); it2.hasNext();){
                               HorarioDia horarioDia2 = it2.next();
                                    if(horarioDia1.getNumeroDia()==horarioDia2.getNumeroDia()){
                                        return true;
                                    }
                            }
                        }
                    }
                    
                }
            }
        }
        return false;
    }


//______________________________________________________________________________
    /**
     * Este metodo se encarga de actualizar un horario a la base de datos.  El proceso es el siguiente
     *
     * <ul>
     *  <li> Se actualiza el horario </li>
     *  <li> Se eliminan los dias relacionados con el horario</li>
     *  <li> Se ingresan los nuevos dias relacionados al horario</li>
     * </ul>
     * @param horario El nuevo horario a agregar.
     * @param horarioDiasWrapper Array de cadena de los dias.
     *
     * @throws DataIntegrityViolationException Se efectua la excepcion si hay un nombre de usuario igual en la base de datos.
     * @throws DataAccessException Se efectua si se puede acceder a la base de datos.
     */
    @Override
    public void actualizarHorario(Horario horario, String[] horarioDiasWrapper) throws DataIntegrityViolationException,  DataAccessException{
        Session sesion = this.daoGeneralImpl.getSesion();


        horario.setHorarioDias(null);
        this.actualizar(horario);

        Query query = sesion.createQuery("delete from HorarioDia where horario = :horario");
        query.setParameter("horario", horario);
        query.executeUpdate();
        
        for (String numeroDia : horarioDiasWrapper) {
            this.agregar(new HorarioDia(horario, Integer.parseInt(numeroDia)));
        }
    }


//______________________________________________________________________________
    /**
     * Este metodo se encarga de agregar un nuevo horario a la base de datos.  El proceso es el siguiente
     *
     * <ul>
     *  <li> Se ingresa el horario </li>
     *  <li> Se ingresan los dias relacionados al horario</li>
     * </ul>
     * @param horario El nuevo horario a agregar.
     * @param horarioDiasWrapper Array de cadena de los dias.
     *
     * @throws DataIntegrityViolationException Se efectua la excepcion si hay un nombre de usuario igual en la base de datos.
     * @throws DataAccessException Se efectua si se puede acceder a la base de datos.
     */

    @Override
    public void agregarHorario(Horario horario, String [] horarioDiasWrapper) throws DataIntegrityViolationException,  DataAccessException {
        this.agregar(horario);

        for (String numeroDia : horarioDiasWrapper) {
            this.agregar(new HorarioDia(horario, Integer.parseInt(numeroDia)));
        }

    }

    //______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de devolver los horarios disponibles en el
     * semestre indicado, de una carrera especifica y del tipoHorario especificado.</p>
     *
     * @param asignacionCursoPensum Pojo del tipo {@link AsignacionCursoPensum}
     * @param semestre Pojo del tipo {@link Semestre}
     * @param tipoHorario Pojo del tipo {@link TipoHorario}
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     * @return List
     */
    @Override
    public List<Horario> getHorario(AsignacionCursoPensum asignacionCursoPensum, Semestre semestre, TipoHorario tipoHorario) throws DataAccessException {
        StringBuilder builder = new StringBuilder();

        builder.append(" select horario from Horario as horario ")
               .append(" where horario.asignacionCursoPensum = :asignacionCursoPensum ")
               .append(" and horario.semestre = :semestre")
               .append(" and horario.tipo = :tipoHorario")
               .append(" and horario.salon.capacidad > (")
               .append("    select count(*) from DetalleAsignacion det")
               .append("    where det.horario = horario")
               .append(" ) order by horario.asignacionCursoPensum.curso.nombre");

        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("asignacionCursoPensum", asignacionCursoPensum);
        query.setParameter("semestre", semestre);
        query.setParameter("tipoHorario", tipoHorario);

        return query.list();
    }

    @Override
    public List<Horario> getHorario(Semestre semestre, Catedratico catedratico, TipoHorario tipoHorario) throws DataAccessException {
        StringBuilder builder = new StringBuilder();

        builder.append(" select horario from Horario as horario")
               .append(" inner join horario.asignacionCatedraticoHorarios aCH")
               .append(" where horario.semestre = :semestre")
               .append(" and horario.tipo =:tipoHorario")
               .append(" and aCH.catedratico = :catedratico order by horario.asignacionCursoPensum.curso.nombre");

        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("semestre", semestre);
        query.setParameter("catedratico", catedratico);
        query.setParameter("tipoHorario", tipoHorario);

        return query.list();
    }

    @Override
    public List<Horario> getHorario(Semestre semestre, TipoHorario tipoHorario) throws DataAccessException {
        StringBuilder builder = new StringBuilder();

        builder.append(" select horario from Horario as horario")
               .append(" where horario.semestre = :semestre")
               .append(" and horario.tipo =:tipoHorario ")
               .append(" order by horario.asignacionCursoPensum.curso.nombre");
        
        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("semestre", semestre);
        query.setParameter("tipoHorario", tipoHorario);

        return query.list();
    }

    @Override
    public List getSeccionesHorario(AsignacionCursoPensum asignacionCursoPensum, Semestre semestre, TipoHorario tipoHorario) throws DataAccessException {
        DetachedCriteria criteria = DetachedCriteria.forClass(Horario.class);
        criteria.add(Restrictions.eq("semestre", semestre));
        criteria.add(Restrictions.eq("tipo", tipoHorario));
        criteria.add(Restrictions.eq("asignacionCursoPensum", asignacionCursoPensum));
        criteria.setProjection(Projections.groupProperty("seccion"));
        
        return this.daoGeneralImpl.find(criteria);
    }

    @Override
    public List<Horario> getHorariosPorSecciones(String seccion, 
    AsignacionCursoPensum asignacionCursoPensum, 
    Semestre semestre, TipoHorario tipoHorario) throws DataAccessException {
        DetachedCriteria criteria = DetachedCriteria.forClass(Horario.class);
        criteria.add(Restrictions.eq("seccion", seccion));
        criteria.add(Restrictions.eq("semestre", semestre));
        criteria.add(Restrictions.eq("tipo", tipoHorario));
        criteria.add(Restrictions.eq("asignacionCursoPensum", asignacionCursoPensum));
        
        
        return this.daoGeneralImpl.find(criteria);
    }
 

}
