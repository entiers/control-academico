/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.servicio;

import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.PensumEstudianteCarrera;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
public interface ServicioPensumEstudianteCarrera {

    PensumEstudianteCarrera getPensumEstudianteCarreraValido(Estudiante estudiante)
            throws DataAccessException;

}
