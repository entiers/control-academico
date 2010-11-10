/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.dominio.busqueda;

//import gt.edu.usac.trabajosocial.util.BotonesPaginacion;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * <p>Esta clase tiene como objetivo almacenar los datos que se ingresan en los
 * formularios de busquedas de catedraticos.</p>
 *
 * @author Daniel Castillo
 * @version 1.0
 */
public class DatosBusquedaCatedratico {

    @Pattern(regexp = "|[0-9]{9}", message = "{validacion.codigoCatedraticoInvalido}")
    private String codigoBusqueda;

    @Size(max = 50, message = "{validacion.caracteresMaximos}")
    private String nombreBusqueda;

    @Size(max = 50, message = "{validacion.caracteresMaximos}")
    private String apellidoBusqueda;

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


    public DatosBusquedaCatedratico(){
        this.codigoBusqueda = "";
        this.nombreBusqueda = "";
        this.apellidoBusqueda = "";
        this.ordenAscendente = true;
        this.columnaOrden = "codigo";
        this.primerRegistro = 0;
    }
//______________________________________________________________________________
    public String getCodigoBusqueda() {
        return codigoBusqueda;
    }
//______________________________________________________________________________
    public void setCodigoBusqueda(String codigoBusqueda) {
        this.codigoBusqueda = codigoBusqueda;
    }
//______________________________________________________________________________
    public String getNombreBusqueda() {
        return nombreBusqueda;
    }
//______________________________________________________________________________
    public void setNombreBusqueda(String nombreBusqueda) {
        this.nombreBusqueda = nombreBusqueda;
    }
//______________________________________________________________________________
    public String getApellidoBusqueda() {
        return apellidoBusqueda;
    }
//______________________________________________________________________________
    public void setApellidoBusqueda(String apellidoBusqueda) {
        this.apellidoBusqueda = apellidoBusqueda;
    }
//______________________________________________________________________________
    public boolean isEmpty() {
        if(this.codigoBusqueda.isEmpty() && this.nombreBusqueda.isEmpty() && this.apellidoBusqueda.isEmpty())
            return true;
        else
            return false;
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
    public String getColumnaOrden() {
        return columnaOrden;
    }
//______________________________________________________________________________
    public void setColumnaOrden(String columnaOrden) {
        this.columnaOrden = columnaOrden;
    }
//______________________________________________________________________________
    public int getPrimerRegistro() {
        return primerRegistro;
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
