/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.servicio.impl;

import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.servicio.ServicioAsignacionPrimerIngreso;
import gt.edu.usac.cats.servicio.ServicioEstudiante;
import javax.annotation.Resource;
import java.util.List;

/*import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
*/
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
@Service("servicioAsignacionPrimerIngresoImpl")
public class ServicioAsignacionPrimerIngresoImpl implements ServicioAsignacionPrimerIngreso{
//_____________________________________________________________________________
    @Resource
    private ServicioEstudiante servicioEstudianteImpl;
//_____________________________________________________________________________
    @Override
    public void asignacionCursosPrimerIngreso(Integer idAsignacionPrimerIngreso) throws DataAccessException {
        //Obtener estudiantes de primer ingreso(carne con anio actual)
        List<Estudiante> lstEstudiantes = this.servicioEstudianteImpl.getListadoEstudiantesPrimerIngreso();
        if (!lstEstudiantes.isEmpty()){
            for (Estudiante estudiante: lstEstudiantes){
                System.out.println(estudiante.getCarne());
            }
        }
        else{
            System.out.println("No existen estudiantes");
        }
    }

}
