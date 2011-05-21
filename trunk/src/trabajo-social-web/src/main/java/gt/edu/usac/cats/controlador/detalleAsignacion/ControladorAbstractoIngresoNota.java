/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.controlador.detalleAsignacion;

import gt.edu.usac.cats.dominio.DetalleAsignacion;
import gt.edu.usac.cats.dominio.Usuario;
import gt.edu.usac.cats.servicio.ServicioDetalleAsignacion;
import gt.edu.usac.cats.servicio.ServicioUsuario;
import java.util.List;
import javax.annotation.Resource;

/**
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
public abstract class ControladorAbstractoIngresoNota {
//______________________________________________________________________________
    protected static String TITULO_MENSAJE = "ingresoNota.titulo";
//______________________________________________________________________________
    protected List<DetalleAsignacion> listadoDetalleAsignacion;
//______________________________________________________________________________
    protected Usuario usuario;
//______________________________________________________________________________
    @Resource
    protected ServicioDetalleAsignacion servicioDetalleAsignacionImpl;
//______________________________________________________________________________
    @Resource
    protected ServicioUsuario servicioUsuarioImpl;

}
