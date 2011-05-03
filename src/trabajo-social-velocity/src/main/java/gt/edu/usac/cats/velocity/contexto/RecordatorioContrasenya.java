/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.velocity.contexto;

import java.io.Serializable;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
public class RecordatorioContrasenya implements Serializable{
    private int idUsuario;
    private String codigoValidacion;

    public RecordatorioContrasenya() {
        this.idUsuario = 0;
        this.codigoValidacion = "";
    }

    public RecordatorioContrasenya(int idUsuario, String codigoValidacion) {
        this.idUsuario = idUsuario;
        this.codigoValidacion = codigoValidacion;
    }

    /**
     * @return the idUsuario
     */
    public int getIdUsuario() {
        return idUsuario;
    }

    /**
     * @param idUsuario the idUsuario to set
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * @return the transaccion
     */
    public String getCodigoValidacion() {
        return codigoValidacion;
    }

    /**
     * @param transaccion the transaccion to set
     */
    public void setCodigoValidacion(String codigoValidacion) {
        this.codigoValidacion = codigoValidacion;
    }

    


}
