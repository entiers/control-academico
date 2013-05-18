/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.dominio.busqueda;

import gt.edu.usac.cats.dominio.Horario;
import gt.edu.usac.cats.enums.TipoHorario;
import java.io.Serializable;

/**
 *
 * @author cats
 */
public class DatosIngresoNota implements Serializable{
//______________________________________________________________________________
    private TipoHorario tipoHorario;
//______________________________________________________________________________
    private Horario horario;
//______________________________________________________________________________
    public Horario getHorario() {
        return horario;
    }
//______________________________________________________________________________
    public void setHorario(Horario horario) {
        this.horario = horario;
    }
//______________________________________________________________________________
    public TipoHorario getTipoHorario() {
        return tipoHorario;
    }
//______________________________________________________________________________
    public void setTipoHorario(TipoHorario tipoHorario) {
        this.tipoHorario = tipoHorario;
    }
}
