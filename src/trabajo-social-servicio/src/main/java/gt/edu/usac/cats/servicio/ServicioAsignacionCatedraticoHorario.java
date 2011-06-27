/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.servicio;

import gt.edu.usac.cats.dominio.AsignacionCatedraticoHorario;
import gt.edu.usac.cats.dominio.Catedratico;
import gt.edu.usac.cats.dominio.Horario;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.enums.TipoHorario;
import java.util.List;
import org.springframework.dao.DataAccessException;

/**
 * <p>Contiene los metodos que permiten el manejo de la informacion relacionada
 * con la asignacionCatedraticoHorario en la base de datos.</p>
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
public interface ServicioAsignacionCatedraticoHorario extends ServicioGeneral {
    
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
    List<AsignacionCatedraticoHorario> getAsignacionCatedraticoHorario(Semestre semestre, Catedratico catedratico)
            throws DataAccessException;
    
    //______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de crear un listado de horarios disponibles para 
     * un catedratico en especifico.</p>
     * 
     * @param semestre pojo del tipo {@link Semestre}
     * @param tipoHorario pojo del tipo {@link TipoHorario}
     * @return List Listado de horarios
     * @throws DataAccessException
     */
    List<Horario> getHorarioDiponibleCatedratico(Semestre semestre, TipoHorario tipoHorario)
            throws DataAccessException;    

}
