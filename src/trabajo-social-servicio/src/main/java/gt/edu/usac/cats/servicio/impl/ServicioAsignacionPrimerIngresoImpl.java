/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.servicio.impl;

import gt.edu.usac.cats.servicio.ServicioAsignacionPrimerIngreso;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Carlos Solórzani
 * @version 1.0
 */
public class ServicioAsignacionPrimerIngresoImpl implements ServicioAsignacionPrimerIngreso, Tasklet{

    @Override
    public void asignacionCursosPrimerIngreso() throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
