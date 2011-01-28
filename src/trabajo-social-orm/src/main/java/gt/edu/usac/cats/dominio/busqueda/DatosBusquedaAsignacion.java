/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.dominio.busqueda;

import gt.edu.usac.cats.dominio.TipoAsignacion;
import javax.validation.constraints.Min;

/**
 * <p>Esta clase tiene como objetivo almacenar los datos que se ingresan en los
 * formularios de busquedas de usuarios.</p>
 *
 * @author Carlos Solórzano
 * @version 1.0
 */
public class DatosBusquedaAsignacion {

    @Min(value=2000, message="{validacion.minimo}")
    private Integer anio;
    private TipoAsignacion tipoAsignacion;

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
    public DatosBusquedaAsignacion(){
        this.ordenAscendente = true;
        this.columnaOrden = "fecha";
        this.primerRegistro = 0;
    }
//______________________________________________________________________________
    public TipoAsignacion getTipoAsignacion() {
        return tipoAsignacion;
    }
//______________________________________________________________________________
    public void setTipoAsignacion(TipoAsignacion tipoAsignacion) {
        this.tipoAsignacion = tipoAsignacion;
    }
//______________________________________________________________________________
    public Integer getAnio() {
        return this.anio;
    }
//______________________________________________________________________________
    public void setAnio(Integer anio) {
        this.anio = anio;
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

}
