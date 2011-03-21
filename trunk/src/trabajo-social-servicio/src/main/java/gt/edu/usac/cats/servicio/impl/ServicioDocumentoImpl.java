/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.servicio.impl;

import gt.edu.usac.cats.dominio.Documento;
import gt.edu.usac.cats.servicio.ServicioDocumento;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
@Service("servicioDocumentoImpl")
public class ServicioDocumentoImpl extends ServicioGeneralImpl implements ServicioDocumento {

    @Override
    public List<Documento> getDocumento(String nombre) throws DataAccessException {
        DetachedCriteria criteria = DetachedCriteria.forClass(Documento.class);
        criteria.add(Restrictions.ilike("nombre", nombre, MatchMode.ANYWHERE));

        return this.daoGeneralImpl.find(criteria);
    }

}
