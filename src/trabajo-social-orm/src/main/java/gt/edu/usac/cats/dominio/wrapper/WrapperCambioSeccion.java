/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.dominio.wrapper;

import gt.edu.usac.cats.dominio.Horario;
import java.util.List;


/**
 *
 * @author Carlos Solorzano
 */
public class WrapperCambioSeccion {
//______________________________________________________________________________
    private Horario horario;
//______________________________________________________________________________
    private List detalleAsignacion;
//______________________________________________________________________________
    public WrapperCambioSeccion(){}
//______________________________________________________________________________
    public List getDetalleAsignacion() {
        return detalleAsignacion;
    }
//______________________________________________________________________________
    public void setDetalleAsignacion(List detalleAsignacion) {
        this.detalleAsignacion = detalleAsignacion;
    }
//______________________________________________________________________________
    public Horario getHorario() {
        return horario;
    }
//______________________________________________________________________________
    public void setHorario(Horario horario) {
        this.horario = horario;
    }


}
