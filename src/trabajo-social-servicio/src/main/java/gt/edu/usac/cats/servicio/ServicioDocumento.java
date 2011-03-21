/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */


package gt.edu.usac.cats.servicio;

import gt.edu.usac.cats.dominio.Documento;
import java.util.List;
import org.springframework.dao.DataAccessException;

/**
 * <p>Contiene los metodos que permiten el manejo de la informacion relacionada
 * con los documentos en la base de datos.</p>
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
public interface ServicioDocumento extends ServicioGeneral{
//______________________________________________________________________________
    /**
     * <p>Este metodo obtiene todos los documentos que contengan el nombre
     * en la base de datos.</p>
     *
     * @throws DataAccessException Si ocurrio un error de acceso a datos
     */

    List<Documento> getDocumento(String nombre)
            throws DataAccessException;

}
