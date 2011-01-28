/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.servicio;

import gt.edu.usac.cats.dominio.Semestre;
import java.util.List;
import org.springframework.dao.DataAccessException;

/**
 * <p>Contiene los metodos que permiten el manejo de la informacion relacionada
 * con el semestre en la base de datos.</p>
 * @author Mario Batres
 * @version 1.0
 */


public interface ServicioSemestre extends ServicioGeneral {


    /**
     * Este metodo retorna todos los semestres cuyo anyo sea mayor o igual
     * al anyo actual.  Se debe de utilizar para la creacion de calendario de actividades.
     *
     *
     * @return Listado de semestres que fueron encontrados.
     *
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */
    List <Semestre> listarSemestresNoVencidos() throws DataAccessException;


    /**
     * Este metodo retorna el listado de semestres ordenados por anyo y mes descendentemente.
     * Es de utilizar para la busqueda y modificaciones de calendario de actividades.
     *
     * @return Listado de semestres que fueron encontrados.
     *
     * @throws DataAccessException Si ocurrio un error de acceso a datos     
     */

    List <Semestre> listarSemestresParaBusqueda() throws DataAccessException;
}
