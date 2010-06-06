/**
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.anotacion.impl;

import gt.edu.usac.trabajosocial.anotacion.CodigoCatedraticoValidador;
import gt.edu.usac.trabajosocial.dominio.Catedratico;
import gt.edu.usac.trabajosocial.servicio.ServicioCatedratico;
import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.dao.DataAccessException;

/**
 * <p>Esta clase es una implementacion de la interfaz {@link ConstraintValidator}
 * la cual es una interfaz para realizar validaciones por medio de anotaciones.
 * La clase realiza la validacion del codigo de un {@link Catedratico}, el cual debe
 * de ser unico, por lo que esta clase se encarga de averiguar si el codigo que
 * se ingresa no esta en uso por otro objeto {@link Catedratico}.</p>
 *
 * @author Daniel Castillo
 * @version 1.0
 */
public class CodigoCatedraticoValidadorImpl implements ConstraintValidator<CodigoCatedraticoValidador, String> {

    /**
     * <p>Contiene metodos que permiten el manejo de la informacion relacionada
     * con el Catedratico en la base de datos. Este objeto se encuentra registrado
     * como un bean de servicio en Spring, por lo que este es el encargado de
     * inyectar la dependencia.</p>
     */
    @Resource
    private ServicioCatedratico servicioCatedraticoImpl;
//______________________________________________________________________________
    /**
     * <p>Este metodo se llama cuando se crea la anotacion, en este metodo se
     * realizan todas las tareas y asignaciones necesarias para que se pueda
     * realizar la validacion.</p>
     *
     * @param constraintAnnotation
     */
    @Override
    public void initialize(CodigoCatedraticoValidador constraintAnnotation) {}
//______________________________________________________________________________
    /**
     * <p>Este metodo es el encargado de realizar la validacion del campo al
     * cual se asigno la anotacion. En este caso la valicion es de comprobar si
     * el codigo de catedratico que se ingresa no se encuentra en uso, en el caso
     * de que el codigo este en uso se retorna <code>false</code>, y si el codigo
     * no esta en uso se retorna <code>true</code>.</p>
     *
     * @param value Valor ingresado en el campo
     * @param context
     * @return true Si y solo si el codigo no esta en uso, <code>false</code> de
     *              lo contrario
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            Catedratico catedratico = this.servicioCatedraticoImpl.buscarCatedraticoPorCodigo(value);
            if(catedratico == null)
                return true;
            else
                return false;
        } catch (DataAccessException e) {
            return true;
        }
    }
}
