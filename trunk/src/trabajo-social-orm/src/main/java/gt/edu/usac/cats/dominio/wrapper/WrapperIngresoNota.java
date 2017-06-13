/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.dominio.wrapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

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
    
    private List notaFinal; //nota final zona mas final
//______________________________________________________________________________
    private Boolean oficializar;
    
    private String linkValue;
    
    private Integer pagina;
    
    
    private List listExcusa;
    
    @NotNull(message = "{validacion.campoObligatorio}")
    @DateTimeFormat(pattern = "dd-MM-yyyy")    
    private Date fechaNotas;
//______________________________________________________________________________
    public WrapperIngresoNota() {
        oficializar = false;
        this.listFinal = new LinkedList();
        this.listLaboratorio = new LinkedList();
        this.listZona = new LinkedList();
        listExcusa = new LinkedList();
        this.notaFinal  = new LinkedList();
        
        //initListExcusa();
        linkValue="";
        // fecha de ingreso de notas
        fechaNotas = new Date();
    }
    
////    private void initListExcusa(){
////        for (int i=0; i<10; i++){
////            listExcusa.add("");
////        }
////       
////    }
    
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

    /**
     * @return the listNota
     */
    public List getNotaFinal() {
        return notaFinal;
    }

    /**
     * @param listNota the listNota to set
     */
    public void setNotaFinal(List listNota) {
        this.notaFinal = listNota;
    }

    /**
     * @return the fechaNotas
     */
    public Date getFechaNotas() {
        return fechaNotas;
    }

    /**
     * @param fechaNotas the fechaNotas to set
     */
    public void setFechaNotas(Date fechaNotas) {
        this.fechaNotas = fechaNotas;
    }
}
