/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.dominio.wrapper;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
public class WrapperRecordarContrasenia {
//______________________________________________________________________________
    @NotEmpty(message="{validacion.campoObligatorio}")
    @Size(max = 100, message = "{validacion.caracteresMaximos}")
    @Email(message = "{validacion.emailInvalido}")
    private String email;
//______________________________________________________________________________
    @NotEmpty(message="{validacion.campoObligatorio}")
    private String captcha;
//______________________________________________________________________________
    public WrapperRecordarContrasenia(){
        this.email = "";
        this.captcha = "";
    }
//______________________________________________________________________________
    public String getCaptcha() {
        return captcha;
    }
//______________________________________________________________________________
    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
//______________________________________________________________________________
    public String getEmail() {
        return email;
    }
//______________________________________________________________________________
    public void setEmail(String email) {
        this.email = email;
    }

}
