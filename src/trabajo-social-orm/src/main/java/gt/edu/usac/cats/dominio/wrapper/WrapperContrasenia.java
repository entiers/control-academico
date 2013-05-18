/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.dominio.wrapper;

import java.io.Serializable;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Carlos Solorzano
 */
public class WrapperContrasenia implements Serializable{
//______________________________________________________________________________
    @NotEmpty(message="{validacion.campoObligatorio}")
    private String contraseniaAnterior;
//______________________________________________________________________________
    @NotEmpty(message="{validacion.campoObligatorio}")
    private String contrasenia1;
//______________________________________________________________________________
    @NotEmpty(message="{validacion.campoObligatorio}")
    private String contrasenia2;
//______________________________________________________________________________
    public String getContraseniaAnterior() {
        return contraseniaAnterior;
    }
//______________________________________________________________________________
    public void setContraseniaAnterior(String contraseniaAnterior) {
        this.contraseniaAnterior = contraseniaAnterior;
    }
//______________________________________________________________________________
    public String getContrasenia1() {
        return contrasenia1;
    }
//______________________________________________________________________________
    public void setContrasenia1(String contrasenia1) {
        this.contrasenia1 = contrasenia1;
    }
//______________________________________________________________________________
    public String getContrasenia2() {
        return contrasenia2;
    }
//______________________________________________________________________________
    public void setContrasenia2(String contrasenia2) {
        this.contrasenia2 = contrasenia2;
    }

}
