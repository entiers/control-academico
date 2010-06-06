/**
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.anotacion;

import gt.edu.usac.trabajosocial.anotacion.impl.CodigoCatedraticoValidadorImpl;
import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * <p>Anotacion utilizada para validar el codigo de catedratico, la validacion
 * consiste en evitar que se ingresen codigos de catedratico repetidos.</p>
 *
 * @author Daniel Castillo
 * @version 1.0
 */
@Documented
@Constraint(validatedBy = CodigoCatedraticoValidadorImpl.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
public @interface CodigoCatedraticoValidador {
    String message() default "{validacion.codigoCatedraticoRepetido}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
