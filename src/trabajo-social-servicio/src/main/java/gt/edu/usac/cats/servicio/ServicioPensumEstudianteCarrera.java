/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.servicio;

import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.PensumEstudianteCarrera;
import java.util.List;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
public interface ServicioPensumEstudianteCarrera extends ServicioGeneral {

    PensumEstudianteCarrera getPensumEstudianteCarreraValido(Estudiante estudiante)
            throws DataAccessException;


    PensumEstudianteCarrera getPensumEstudianteCarreraValido(AsignacionEstudianteCarrera asignacionEstudianteCarrera)
            throws DataAccessException;

    List <PensumEstudianteCarrera> getListadoPensumEstudianteCarreraNoValidos(Estudiante estudiante)
            throws DataAccessException;

    List <PensumEstudianteCarrera> getListadoPensumEstudianteCarreraNoValidos(AsignacionEstudianteCarrera asignacionEstudianteCarrera)
            throws DataAccessException;

}
