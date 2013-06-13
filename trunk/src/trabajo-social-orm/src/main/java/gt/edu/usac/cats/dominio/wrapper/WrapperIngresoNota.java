/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.dominio.wrapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
public class WrapperIngresoNota implements Serializable {
//______________________________________________________________________________
    private List listZona;
//______________________________________________________________________________
    private List listLaboratorio;
//______________________________________________________________________________
    private List listFinal;
//______________________________________________________________________________
    private Boolean oficializar;
    
    private String linkValue;
    
    private Integer pagina;
    
    private List listExcusa;
//______________________________________________________________________________
    public WrapperIngresoNota() {
        oficializar = false;
        this.listFinal = new ArrayList();
        this.listLaboratorio = new ArrayList();
        this.listZona = new ArrayList();
        listExcusa = new ArrayList();
        linkValue="";
    }
    
//______________________________________________________________________________
    public List getListFinal() {
        return listFinal;
    }
//______________________________________________________________________________

    public void setListFinal(List listFinal) {
        this.listFinal = listFinal;
    }
//______________________________________________________________________________

    public List getListLaboratorio() {
        return listLaboratorio;
    }
//______________________________________________________________________________

    public void setListLaboratorio(List listLaboratorio) {
        this.listLaboratorio = listLaboratorio;
    }
//______________________________________________________________________________

    public List getListZona() {
        return listZona;
    }
//______________________________________________________________________________

    public void setListZona(List listZona) {
        this.listZona = listZona;
    }
//______________________________________________________________________________

    public Boolean getOficializar() {
        return oficializar;
    }
//______________________________________________________________________________

    public void setOficializar(Boolean oficializar) {
        this.oficializar = oficializar;
    }

    /**
     * @return the listExcusa
     */
    public List getListExcusa() {
        return listExcusa;
    }

    /**
     * @param listExcusa the listExcusa to set
     */
    public void setListExcusa(List listExcusa) {
        this.listExcusa = listExcusa;
    }

    /**
     * @return the linkValue
     */
    public String getLinkValue() {
        return linkValue;
    }

    /**
     * @param linkValue the linkValue to set
     */
    public void setLinkValue(String linkValue) {
        this.linkValue = linkValue;
    }

    /**
     * @return the pagina
     */
    public Integer getPagina() {
        return pagina;
    }

    /**
     * @param pagina the pagina to set
     */
    public void setPagina(Integer pagina) {
        this.pagina = pagina;
    }
}
