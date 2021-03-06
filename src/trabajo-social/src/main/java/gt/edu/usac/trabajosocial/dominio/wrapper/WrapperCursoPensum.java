/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.dominio.wrapper;

import javax.validation.constraints.Min;


/**
 *
 * @author Daniel Castillo
 */
public class WrapperCursoPensum {

    @Min(value = 1, message = "{validacion.seleccion}")
    private short idCurso;
    private boolean obligatorio;
//______________________________________________________________________________
    public short getIdCurso() {
        return idCurso;
    }
//______________________________________________________________________________
    public void setIdCurso(short idCurso) {
        this.idCurso = idCurso;
    }
//______________________________________________________________________________
    public boolean isObligatorio() {
        return obligatorio;
    }
//______________________________________________________________________________
    public void setObligatorio(boolean obligatorio) {
        this.obligatorio = obligatorio;
    }
}
