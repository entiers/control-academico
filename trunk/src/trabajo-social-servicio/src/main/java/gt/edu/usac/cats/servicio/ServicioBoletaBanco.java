/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */


package gt.edu.usac.cats.servicio;

import gt.edu.usac.cats.dominio.BoletaBanco;
import gt.edu.usac.cats.dominio.Curso;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.enums.TipoRubro;
import gt.edu.usac.cats.enums.VarianteRubro;
import java.util.List;
import org.springframework.dao.DataAccessException;

/**
 * <p>Contiene los metodos que permiten el manejo de la informacion relacionada
 * con la boleta del banco en la base de datos.</p>
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
public interface ServicioBoletaBanco extends ServicioGeneral{
//______________________________________________________________________________
    /**
     * Obtiene un listado de las boletas del banco que cumpla con las restricciones
     * especificadas.
     *
     * @param estudiante POJO que hacer referencia a la clase {@link Estudiante}
     * @param curso POJO que hacer referencia a la clase {@link Curso}
     * @param semestre POJO que hacer referencia a la clase {@link Semestre}
     * @param tipoRubro POJO que hacer referencia a la clase {@link TipoRubro}
     * @param varianteRubro POJO que hacer referencia a la clase {@link VarianteRubro}
     * @return List<BoletaBanco> Listado de boletas que cumplan las restricciones
     * @throws DataAccessException
     */
    List<BoletaBanco> listadoBoletaBanco(Estudiante estudiante, Curso curso, Semestre semestre, TipoRubro tipoRubro)
            throws DataAccessException;

}
