/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cats.servicio.impl;


import gt.edu.usac.cats.dominio.AsignacionCursoPensum;
import gt.edu.usac.cats.dominio.Curso;
import gt.edu.usac.cats.dominio.Pensum;
import gt.edu.usac.cats.servicio.ServicioPensum;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * <p>Contiene la implementaci&oacute;n de los m&eacute;todos que permiten el manejo de la
 * informaci&oacute;n relacionada con el pensum y asignaci&oacute;n de cursos al pensum en la base de datos.</p>
 *
 * @see ServicioHorario
 * @author Mario Batres
 * @version 1.0
 */
@Service
public class ServicioPensumImpl extends ServicioGeneralImpl implements ServicioPensum {

    /**
     * Este m&eacute;todo se encarga de obtener los cursos que <b>NO</b> han sido asignados
     * al pensum que es enviado como par&aacute;metro.
     *
     * @param pensum Objeto de tipo {@link Pensum}
     *
     * @throws DataAccessException Si ocurri&oacute; un error de acceso a datos
     *
     * @return List con atributos de tipo {@link Curso}
     */

    @Override
    public List<Curso> getCursosNoAsignados(Pensum pensum) throws DataAccessException {

        StringBuilder builder = new StringBuilder();
        builder.append("from Curso c ");
        builder.append("where c.idCurso not in (select acp.curso.idCurso from AsignacionCursoPensum acp ");
        builder.append("where acp.pensum = :pensum");
        builder.append(")order by c.codigo");

        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("pensum", pensum);

        return query.list();
    }

     /**
     * Este m&eacute;todo se encarga de obetner todas las asignaciones de cursos
     * sin incluir la asignaci&oacute;n que es enviada como par&aacute;metro.  Esto se utiliza
     * en la administraci&oacute;n de prerrequisitos.
     *
     * @param pensum Objeto de tipo {@link Pensum}
     * @param asignacionCursoPensum Objeto de tipo {@link AsignacionCursoPensum}
     *
     * @return List con atributos de tipo {@link AsignacionCursoPensum}
     * @throws DataAccessException Si ocurri&oacute; un error de acceso a datos
     */
    @Override
    public List<AsignacionCursoPensum> getAsignacionCursoPensumsPorPensumYNoACP(Pensum pensum, AsignacionCursoPensum asignacionCursoPensum)
            throws DataAccessException {

        DetachedCriteria criteria = DetachedCriteria.forClass(AsignacionCursoPensum.class);
        criteria.add(Restrictions.not(Restrictions.eq("idAsignacionCursoPensum", asignacionCursoPensum.getIdAsignacionCursoPensum())));
        criteria.add(Restrictions.eq("pensum", pensum));
        criteria.addOrder(Order.asc("numeroSemestre"));

        return this.daoGeneralImpl.find(criteria);
    }

    @Override
    public List<Pensum> getListadoOtrosPensums(Short idPensum) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Pensum.class);
        criteria.add(Restrictions.not(Restrictions.eq("idPensum", idPensum)));
        criteria.addOrder(Order.asc("codigo"));
        return this.daoGeneralImpl.find(criteria);
    }
}
