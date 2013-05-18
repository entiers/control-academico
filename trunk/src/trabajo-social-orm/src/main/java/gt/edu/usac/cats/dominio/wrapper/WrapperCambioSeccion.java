/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.dominio.wrapper;

import gt.edu.usac.cats.dominio.Horario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Carlos Solorzano
 */
public class WrapperCambioSeccion implements Serializable{
//______________________________________________________________________________
    private Horario horario;
//______________________________________________________________________________
    private List detalleAsignacion;
//______________________________________________________________________________
    public WrapperCambioSeccion(){
        this.horario = new Horario();
        this.detalleAsignacion = new ArrayList();
    }
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
