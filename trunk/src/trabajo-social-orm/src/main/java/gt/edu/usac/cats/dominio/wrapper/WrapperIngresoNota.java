/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.dominio.wrapper;

import java.util.List;

/**
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
public class WrapperIngresoNota {
    private List listZona;
    private List listLaboratorio;
    private List listFinal;
    private Boolean oficializar;

    public WrapperIngresoNota() {
        oficializar = false;
    }
   
    public List getListFinal() {
        return listFinal;
    }

    public void setListFinal(List listFinal) {
        this.listFinal = listFinal;
    }

    public List getListLaboratorio() {
        return listLaboratorio;
    }

    public void setListLaboratorio(List listLaboratorio) {
        this.listLaboratorio = listLaboratorio;
    }

    public List getListZona() {
        return listZona;
    }

    public void setListZona(List listZona) {
        this.listZona = listZona;
    }

    public Boolean getOficializar() {
        return oficializar;
    }

    public void setOficializar(Boolean oficializar) {
        this.oficializar = oficializar;
    }

}
