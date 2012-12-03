/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.servicio;

import gt.edu.usac.cats.dominio.Asignacion;
import gt.edu.usac.cats.dominio.AsignacionCursoPensum;
import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.Curso;
import gt.edu.usac.cats.dominio.DetalleAsignacion;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.Horario;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.enums.TipoAsignacion;
import java.io.IOException;
import java.util.List;
import org.hibernate.HibernateException;

/**
 * <p></p>
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
public interface ServicioDetalleAsignacion extends ServicioGeneral{
//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de crear un listado de AsignacionEstudianteCarrera, el listado
     * se filtra en base la carrera, el numero de carnet de cada estudiante, en donde este
     * sea igual al anio actual, asi como el estudiante no debe de tener asignaciones previas</p>
     *
     * @param horario (@link Horario) Contiene los filtros para el total
     * @return Integer Total de detalles de asignacion por horario
     * @throws HibernateException Si ocurrio un error de acceso a datos
     */
    Integer getCountDetalleAsignacionXHorario(Horario horario)
            throws HibernateException;
//______________________________________________________________________________
    /**
     * <p>Valida que el estudiante este asignado en un curso en un horario
     * especifico.</p>
     *
     * @param estdiante Contiene el estudiante a buscar (@link Estudiante)
     * @param horario contiene el horario por el que se busca (@link Horario)
     * @return boolean true si esta asignado, de lo contrario false
     * @throws HibernateException Si ocurrio un error de acceso a datos
     */
    List<DetalleAsignacion> getListadoDetalleAsignacion(Integer idHorario)
            throws HibernateException;

//______________________________________________________________________________
    /**
     * <p>Metodo que se encarga de realizar el cambio de asignacion en base
     * al horario.</p>
     *
     * @param horario Contiene el horario a reemplazar (@link Horario)
     * @param listadoDetalleAsignacion contiene el listado de idDetalleAsignacion a actualizar
     * @throws HibernateException Si ocurrio un error de acceso a datos
     */
    public void cambioCierreSeccion(Horario horario,
                                    List lstIdDetalleAsignacion)
            throws HibernateException;
//______________________________________________________________________________
    /**
     * <p>Listado de DetalleAsignacion.</p>
     *
     * @param curso Contiene el pojo del tipo {@link Curso} para filtrar los resultados
     * @param semestre Contiene el pojo del tipo {@link Semestre} para filtrar los resultados
     * @param asignacionEstudianteCarrera Contiene el pojo del tipo {@link AsignacionEstudianteCarrera} para filtrar los resultados
     * @param tipoAsignacion Contiene el pojo del tipo {@link TipoAsignacion} para filtrar los resultados
     * @throws HibernateException Si ocurrio un error de acceso a datos
     */
     List<DetalleAsignacion> getListadoDetalleAsignacion(AsignacionCursoPensum asignacionCursoPensum, 
             Semestre semestre, AsignacionEstudianteCarrera asignacionEstudianteCarrera, TipoAsignacion tipoAsignacion)
             throws HibernateException;

//______________________________________________________________________________
    /**
     * <p>Listado de DetalleAsignacion.</p>
     *
     * @param semestre Contiene el pojo del tipo {@link Semestre} para filtrar los resultados
     * @param asignacionEstudianteCarrera Contiene el pojo del tipo {@link AsignacionEstudianteCarrera} para filtrar los resultados
     * @param tipoAsignacion Contiene el pojo del tipo {@link TipoAsignacion} para filtrar los resultados
     * @throws HibernateException Si ocurrio un error de acceso a datos
     */
     List<DetalleAsignacion> getListadoDetalleAsignacion(Semestre semestre, AsignacionEstudianteCarrera asignacionEstudianteCarrera, TipoAsignacion tipoAsignacion)
             throws HibernateException;

//______________________________________________________________________________
    /**
     * <p>Listado de DetalleAsignacion de todas las carreras en que se encuentra
     * asignado el estudiante.</p>
     *
     * @param semestre Contiene el pojo del tipo {@link Semestre} para filtrar los resultados
     * @param estudiante Contiene el pojo del tipo {@link Estudiante} para filtrar los resultados
     * @param tipoAsignacion Contiene el pojo del tipo {@link TipoAsignacion} para filtrar los resultados
     * @throws HibernateException Si ocurrio un error de acceso a datos
     */
     List<DetalleAsignacion> getListadoDetalleAsignacion(Semestre semestre, Estudiante estudiante, TipoAsignacion tipoAsignacion)
             throws HibernateException;
//______________________________________________________________________________
    /**
     * <p>Listado de DetalleAsignacion.</p>
     *
     * @param Asignacion Contiene el pojo del tipo {@link Asignacion} para filtrar los resultados
     * @throws HibernateException Si ocurrio un error de acceso a datos
     */
     List<DetalleAsignacion> getListadoDetalleAsignacion(Asignacion asignacion)
             throws HibernateException;


//______________________________________________________________________________
    /**
     * <p>Total de veces en que un curso ha sido asignado a un estudiante en un
     * tipo de asignacion.</p>
     *
     * @param curso Contiene el pojo del tipo (@link Curso) para filtrar los resultados
     * @param asignacionEstudianteCarrera Contiene el pojo del tipo (@link AsignacionEstudianteCarrera) para filtrar los resultados
     * @param tipoAsignacion Contiene el pojo del tipo (@link TipoAsignacion) para filtrar los resultados
     * @throws HibernateException Si ocurrio un error de acceso a datos
     */
     int getTotalAsignaciones(AsignacionCursoPensum asignacionCursoPensum, AsignacionEstudianteCarrera asignacionEstudianteCarrera, TipoAsignacion tipoAsignacion)
             throws HibernateException;

 //______________________________________________________________________________
    /**
     * <p>Determina si un estudiante posee zona minima para un curso en particular.</p>
     *
     * @param curso Contiene el pojo del tipo {@link Curso} para filtrar los resultados
     * @param asignacionEstudianteCarrera Contiene el pojo del tipo {@link AsignacionEstudianteCarrera} para filtrar los resultados
     * @throws HibernateException Si ocurrio un error de acceso a datos
     */
     boolean tieneZonaMinima(AsignacionCursoPensum asignacionCursoPensum, AsignacionEstudianteCarrera asignacionEstudianteCarrera)
             throws HibernateException, IOException;
 //______________________________________________________________________________
    /**
     * <p>Determina si un estudiante posee zona minima para un curso en particular.</p>
     *
     * @param curso Contiene el pojo del tipo {@link Curso} para filtrar los resultados
     * @param asignacionEstudianteCarrera Contiene el pojo del tipo {@link AsignacionEstudianteCarrera} para filtrar los resultados
     * @param semestre Contiene el pojo del tipo {@link Semestre} para filtrar los resultados
     * @throws HibernateException Si ocurrio un error de acceso a datos
     */
     boolean tieneZonaMinima(AsignacionCursoPensum asignacionCursoPensum, AsignacionEstudianteCarrera asignacionEstudianteCarrera, Semestre semestre)
             throws HibernateException, IOException;
//______________________________________________________________________________     
     /**
      * <p>Método para realizar la eliminación de los detalles de asignacion para una asignacion determinada</p>
      * @param listadoEliminacion
      * @return
      * @throws HibernateException
      * @throws IOException 
      */
     void eliminarDetalleAsignacion(List<DetalleAsignacion> listadoEliminacion) throws HibernateException;

}
