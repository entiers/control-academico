/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.servicio.impl;

import gt.edu.usac.cats.dominio.Carrera;
import gt.edu.usac.cats.dominio.Curso;
import gt.edu.usac.cats.dominio.Horario;
import gt.edu.usac.cats.dominio.Salon;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.servicio.ServicioHorario;
import java.util.Calendar;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
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

        return this.daoGeneralImpl.find(criteria);
    }

//______________________________________________________________________________
    @Override
    public Horario getHorarioPorCursoPrimerIngreso(Curso curso) throws DataAccessException {
        Calendar fecha = Calendar.getInstance();
        
        StringBuilder builder = new StringBuilder();

        builder.append(" select horario.idHorario from Horario as horario ")
               .append(" left join horario.detalleAsignacions as det ")
               .append(" where horario.curso = :curso ")
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
        query.setParameter("curso", curso);

        List list = query.list();
        if (!list.isEmpty())
            return this.cargarEntidadPorID(Horario.class, Integer.valueOf(list.get(0).toString())) ;
        else
            return null;
    }
//______________________________________________________________________________
    @Override
    public List<Horario> getHorario(Curso curso, Semestre semestre)
            throws DataAccessException {
        DetachedCriteria criteria = DetachedCriteria.forClass(Horario.class);

        criteria.add(Restrictions.and(
                Restrictions.eq("curso", curso),
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
               .append(" and horario.curso = :curso ")
               .append(" and horario.semestre = :semestre ");
           
        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("idHorario", horario.getIdHorario());
        query.setParameter("curso", horario.getCurso());
        query.setParameter("semestre", horario.getSemestre());

        return query.list();
    }
//______________________________________________________________________________
    @Override
    public List<Horario> getHorario(Curso curso) throws DataAccessException {
        StringBuilder builder = new StringBuilder();

        builder.append("select horario from Horario as horario ")
               .append("where horario.curso = :curso");
           
        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("curso", curso);

        return query.list();
    }

    @Override
    public boolean existeTraslape(List<Horario> listadoHorario) throws DataAccessException {
        for(Horario h1 : listadoHorario){
            for(Horario h2 : listadoHorario){
                if(h1.getIdHorario()!=h2.getIdHorario()){
                    if((h2.getHoraFin().compareTo(h1.getHoraInicio()) > 0 & h2.getHoraFin().compareTo(h1.getHoraFin()) <= 0)
                        | (h2.getHoraInicio().compareTo(h1.getHoraFin()) < 0 & h2.getHoraInicio().compareTo(h1.getHoraInicio()) >= 0)
                        | (h2.getHoraInicio().compareTo(h1.getHoraInicio()) <= 0 & h2.getHoraFin().compareTo(h1.getHoraFin()) >= 0  ))
                        return true;
                }
            }
        }
        return false;
    }


}