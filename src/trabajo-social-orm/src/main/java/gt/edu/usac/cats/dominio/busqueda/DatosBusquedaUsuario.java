/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.dominio.busqueda;

import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>Esta clase tiene como objetivo almacenar los datos que se ingresan en los
 * formularios de busquedas de usuarios.</p>
 *
 * @author Carlos Solórzano
 * @version 1.0
 */
public class DatosBusquedaUsuario implements Serializable{

    @Pattern(regexp = "[A-Za-z0-9]*(._-)*", message = "{validacion.nombreUsuarioInvalido}")
    @Size (max=256)
    private String nombreUsuario;
    private boolean habilitado;

    /**
     * <p>Indica si el resultado se debe de ordenar de forma ascendente (true) o
     * descendente (false).</p>
     */
    private boolean ordenAscendente;

    /**
     * <p>Nombre de la columna que se usara para ordenar el listado.</p>
     */
    private String columnaOrden;

    /**
     * <p>Indica el primer registro que se mostrara en el listado, este permite
     * hacer paginacion de los resultados.</p>
     */
    private int primerRegistro;

    /**
     * <p>Constructor de la clase, inicializa las variables.</p>
     */
    public DatosBusquedaUsuario(){
        this.nombreUsuario = "";
        this.habilitado = true;
        this.ordenAscendente = true;
        this.columnaOrden = "nombreUsuario";
        this.primerRegistro = 0;
    }
//______________________________________________________________________________
    public boolean isHabilitado() {
        return habilitado;
    }
//______________________________________________________________________________
    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }
//______________________________________________________________________________
    public String getColumnaOrden() {
        return columnaOrden;
    }
//______________________________________________________________________________
    public void setColumnaOrden(String columnaOrden) {
        this.columnaOrden = columnaOrden;
    }
//______________________________________________________________________________
    public String getNombreUsuario() {
        return nombreUsuario;
    }
//______________________________________________________________________________
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
//______________________________________________________________________________
    public boolean isOrdenAscendente() {
        return ordenAscendente;
    }
//______________________________________________________________________________
    public void setOrdenAscendente(boolean ordenAscendente) {
        this.ordenAscendente = ordenAscendente;
    }
//______________________________________________________________________________
    public int getPrimerRegistro() {
        return primerRegistro;
    }
//______________________________________________________________________________
    public void setPrimerRegistro(int primerRegistro) {
        this.primerRegistro = primerRegistro;
    }
//______________________________________________________________________________
    public void aumentarPrimerRegistro() {
//        this.primerRegistro += BotonesPaginacion.REGISTROS_MAXIMOS;
    }
//______________________________________________________________________________
    public void disminuirPrimerRegistro() {
//        this.primerRegistro -= BotonesPaginacion.REGISTROS_MAXIMOS;
    }
//______________________________________________________________________________
    public void inicializarPrimerRegistro() {
        this.primerRegistro = 0;
    }
//______________________________________________________________________________
    public boolean isEmpty() {
        if(this.nombreUsuario.isEmpty())
            return true;
        else
            return false;
    }

}
