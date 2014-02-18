/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.servicio.impl;

import gt.edu.usac.cats.dominio.AsignacionCatedraticoHorario;
import gt.edu.usac.cats.dominio.Catedratico;
import gt.edu.usac.cats.dominio.Horario;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.enums.TipoHorario;
import gt.edu.usac.cats.servicio.ServicioAsignacionCatedraticoHorario;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
@Service("servicioAsignacionCatedraticoHorarioImpl")
public class ServicioAsignacionCatedraticoHorarioImpl extends ServicioGeneralImpl implements ServicioAsignacionCatedraticoHorario, Serializable {
//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de retornar un listado de 
     * AsignacionCatedraticoHorario en base a los parametros enviados
     * </p>
     * 
     * @param semestre pojo del tipo {@link Semestre}
     * @param catedratico pojo del tipo {@link Catedratico}
     * @return List<AsignacionCatedraticoHorario>
     * @throws DataAccessException
     */
    @Override
    public List<AsignacionCatedraticoHorario> getAsignacionCatedraticoHorario(Semestre semestre, Catedratico catedratico) throws DataAccessException {

        StringBuilder builder = new StringBuilder();
        builder.append("select aCH from AsignacionCatedraticoHorario aCH ")
               .append("where aCH.horario.semestre =:semestre ")
               .append("and aCH.catedratico =:catedratico");

        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("semestre", semestre);
        query.setParameter("catedratico", catedratico);

        return query.list();

    }
    
//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de crear un listado de horarios disponibles para 
     * un catedratico en especifico.</p>
     * 
     * @param semestre pojo del tipo {@link Semestre}
     * @param tipoHorario pojo del tipo {@link TipoHorario}
     * @return List Listado de horarios
     * @throws DataAccessException
     * Usado en el compo de la AsignacionCatedraticoHorario
     */    
    @Override
    public List<Horario> getHorarioDiponibleCatedratico(Semestre semestre, TipoHorario tipoHorario) throws DataAccessException {
        StringBuilder builder = new StringBuilder();

        builder.append(" select horario from Horario as horario")
               .append(" where horario.semestre = :semestre")
               .append(" and horario.tipo =:tipoHorario ")
               .append(" and horario.habilitado = true ")
               .append(" and horario.maestro = true ")
               .append(" and not exists elements (horario.asignacionCatedraticoHorarios) ")
               .append(" order by horario.asignacionCursoPensum.curso.nombre");
        
        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("semestre", semestre);
        query.setParameter("tipoHorario", tipoHorario);

        return query.list();
    }

}
