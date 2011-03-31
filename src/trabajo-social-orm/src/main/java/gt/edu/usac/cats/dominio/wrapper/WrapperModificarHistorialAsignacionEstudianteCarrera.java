/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.dominio.wrapper;

/**
 * <p>Esta clase es una clase hija de {@link WrapperHistorialAsignacionEstudianteCarrera}
 * ya que tienen la misma funcionalidad en cuantos a sus atributos y validaciones.</p>
 *
 * <p>Se realiza de esta forma para que el objeto de tipo {@link BindingResult} no d&eacute;
 * ning&uacute;n problema a la hora de mostrar los errores en las vistas.</p>
 * @author Mario Batres
 * @version 1.0
 */
public class WrapperModificarHistorialAsignacionEstudianteCarrera extends WrapperHistorialAsignacionEstudianteCarrera {

    /**
     * Constructor de la clase, llama al constructor de la clase padre.
     */
    public WrapperModificarHistorialAsignacionEstudianteCarrera() {
        super();
    }
}
