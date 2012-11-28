/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */
package gt.edu.usac.cats.controlador.asignacionEstudianteCarrera;

import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.Carrera;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.dominio.Situacion;
import gt.edu.usac.cats.dominio.wrapper.WrapperAgregarAsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.wrapper.WrapperEquivalenciaPorCarrera;
import gt.edu.usac.cats.dominio.wrapper.WrapperHistorialAsignacionEstudianteCarrera;
import gt.edu.usac.cats.servicio.ServicioAsignacionEstudianteCarrera;
import gt.edu.usac.cats.servicio.ServicioSemestre;
import gt.edu.usac.cats.dominio.wrapper.WrapperModificarAsignacionEstudianteCarrera;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.ui.Model;

/**
 * Clase abstracta que lleva el control de m&eacute;dos y atributos que son utilizados
 * en los controladores {@link ControladorCambiarAsignacionEstudianteCarrera }, *
 * {@link ControladorEliminarHistorialAsignacionEstudianteCarrera},
 * {@link ControladorMostrarAsignacionEstudianteCarrera } y
 * {@link ControladorMostrarHistorialAsignacionEstudianteCarrera}.
 *
 * @author Mario Batres
 * @version 1.0
 */
public abstract class ControladorAbstractoAsignacionEstudianteCarrera {
//______________________________________________________________________________

    /**
     * <p>Contiene metodos que permiten el manejo de la informaci&oacute;n relacionada
     * con el con el objeto de tipo {@link AsignacionEstudianteCarrera} en la base
     * de datos. Este objeto se encuentra registrado como un bean de servicio
     * en Spring, por lo que este es el encargado de
     * inyectar la dependencia.</p>
     */
    @Resource
    protected ServicioAsignacionEstudianteCarrera servicioAsignacionEstudianteCarreraImpl;
//______________________________________________________________________________
    /**
     * <p>Contiene metodos que permiten el manejo de la informaci&oacute;n relacionada
     * con el con el objeto de tipo {@link Semestre} en la base
     * de datos. Este objeto se encuentra registrado como un bean de servicio
     * en Spring, por lo que este es el encargado de
     * inyectar la dependencia.</p>
     */
    @Resource
    protected ServicioSemestre servicioSemestreImpl;
//______________________________________________________________________________
    /**
     * Objeto de tipo {@link Estudiante} que ha sido seleccionado.
     */
    protected Estudiante estudiante;
//______________________________________________________________________________
    /**
     * Objeto de tipo {@link AsignacionEstudianteEstudiante} que ha sido seleccionado.
     */
    protected AsignacionEstudianteCarrera asignacionEstudianteCarrera;
//______________________________________________________________________________
    /**
     * Lista de objetos de tipo {@link Carrera} que mantiene
     * los resultados de las b&uacute;squeda de las carreras no asignadas
     * del estudiante que ha sido seleccinado.
     */
    private List<Carrera> listadoCarrerasNoAsignadas;
//______________________________________________________________________________
    /**
     * Lista de objetos de tipo {@link AsignacionEstudianteCarrera} que mantiene
     * los resultados de las b&uacute;squeda de las asignaciones de estudiante
     * y carrera
     */
    private List<AsignacionEstudianteCarrera> listadoAsignacionEstudianteCarrera;
//______________________________________________________________________________
    /**
     * Lista de objetos de tipo {@link AsignacionEstudianteCarrera} que mantiene
     * los resultados de las b&uacute;squeda de las no asignaciones de estudiante
     * y carrera
     */
    private List<AsignacionEstudianteCarrera> listadoAsignacionEstudianteCarreraNoAsignadas;
//______________________________________________________________________________
    /**
     * Lista de objetos de tipo {@link Situacion} que mantiene
     * los resultados de las b&uacute;squeda en la base de datos.
     */
    private List<Situacion> listadoSituaciones;
//______________________________________________________________________________
    /**
     * Lista de objetos de tipo {@link Semestre} que mantiene
     * los resultados de las b&uacute;squeda en la base de datos.
     */
    private List<Semestre> listadoSemestres;

//______________________________________________________________________________
    /**
     * Lista los atributos que son necesarios para agregar o modificar
     * un objeto de tipo {@link HistorialAsignacionEstudianteCarrera}
     */
    private void listarAtributosDeHistorial() {
        this.listadoSituaciones = this.servicioAsignacionEstudianteCarreraImpl.cargarEntidades(Situacion.class);
        this.listadoSemestres = this.servicioSemestreImpl.listarSemestresParaBusqueda();
    }

//______________________________________________________________________________
    /**
     * Busca y agrega los atributos necesarios para la creaci&oacute;n de los
     * componentes en la vista <code>mostrarAsignacionEstudianteCarrera.htm</code>.
     * Es utilizado en el controlador {@link ControladorMostrarAsignacionEstudianteCarrera}.
     *
     * @param modelo Objeto de tipo {@link Model}
     * @param wrapperAgregarAsignacionEstudianteCarrera
     * Objeto de tipo {@link WrapperAgregarAsignacionEstudianteCarrera}
     * @param autoOpenDialog Indica si se habilita o no el dialogo para agregar
     * un objeto de tipo {@link AsignacionEstudianteCarrera}
     * @param wrapperModificarAsignacionEstudianteCarrera
     * Objeto de tipo {@link WrapperModificarAsignacionEstudianteCarrera}
     * @param autoOpenDialogModificar Indica si se habilita o no el dialogo para modificar
     * un objeto de tipo {@link AsignacionEstudianteCarrera}
     * @param realizarBusqueda Indica si se realiza la b&uacute;squeda o no de algunos
     * atributos.
     */
    protected void agregarAtributosDefault(Model modelo,
            WrapperAgregarAsignacionEstudianteCarrera wrapperAgregarAsignacionEstudianteCarrera,
            boolean autoOpenDialog,
            WrapperModificarAsignacionEstudianteCarrera wrapperModificarAsignacionEstudianteCarrera,
            boolean autoOpenDialogModificar, 
            boolean realizarBusqueda,
            WrapperEquivalenciaPorCarrera wrapperEquivalenciaPorCarrera) {

        if (realizarBusqueda) {
            this.listadoCarrerasNoAsignadas =
                    this.servicioAsignacionEstudianteCarreraImpl.getListadoCarrerasNoAsignadasPorEstudiante(this.estudiante);

            this.listadoAsignacionEstudianteCarrera =
                    this.servicioAsignacionEstudianteCarreraImpl.getAsignacionEstudianteCarrera(estudiante, true);

            this.listadoAsignacionEstudianteCarreraNoAsignadas =
                    this.servicioAsignacionEstudianteCarreraImpl.getAsignacionEstudianteCarrera(estudiante, false);

            this.listarAtributosDeHistorial();

            Semestre semestreActivo = this.servicioSemestreImpl.getSemestreActivo();
            wrapperAgregarAsignacionEstudianteCarrera.getWrapperHistorialAsignacionEstudianteCarrera().setSemestre(semestreActivo);
        }

        modelo.addAttribute("autoOpenDialog", autoOpenDialog);
        modelo.addAttribute("autoOpenDialogModificar", autoOpenDialogModificar);
        modelo.addAttribute("estudiante", this.estudiante);
        modelo.addAttribute("listadoAsignacionEstudianteCarrera", this.listadoAsignacionEstudianteCarrera);
        modelo.addAttribute("listadoAsignacionEstudianteCarreraNoAsignadas", this.listadoAsignacionEstudianteCarreraNoAsignadas);
        modelo.addAttribute("wrapperAgregarAsignacionEstudianteCarrera", wrapperAgregarAsignacionEstudianteCarrera);
        modelo.addAttribute("wrapperEquivalenciaPorCarrera", wrapperEquivalenciaPorCarrera);
        modelo.addAttribute("listadoCarrerasNoAsignadas", this.listadoCarrerasNoAsignadas);
        modelo.addAttribute("listadoSituaciones", this.listadoSituaciones);
        modelo.addAttribute("listadoSemestres", this.listadoSemestres);
        if (this.listadoAsignacionEstudianteCarrera == null || this.listadoAsignacionEstudianteCarrera.size() < 2) {
            modelo.addAttribute("habilitarFormulario", true);
        }

        modelo.addAttribute("wrapperModificarAsignacionEstudianteCarrera", wrapperModificarAsignacionEstudianteCarrera);
        modelo.addAttribute("wrapperEquivalenciaPorCarrera", new WrapperEquivalenciaPorCarrera());

    }

//______________________________________________________________________________
    /**
     * Busca y agrega los atributos necesarios para la creaci&oacute;n de los
     * componentes en la vista <code>cambiarAsignacionEstudianteCarrera.htm</code>.
     * Es utilizado en el controlador {@link ControladorCambiarAsignacionEstudianteCarrera}.
     *
     * @param modelo Objeto de tipo {@link Model}
     * @param wrapperAgregarAsignacionEstudianteCarrera  Objeto de tipo {@link WrapperAgregarAsignacionEstudianteCarrera}
     * @param realizarBusqueda
     */
    protected void agregarAtributosDefaultCambiarAsignacion(Model modelo,
            WrapperAgregarAsignacionEstudianteCarrera wrapperAgregarAsignacionEstudianteCarrera,
            boolean realizarBusqueda) {

        modelo.addAttribute("asignacionEstudianteCarrera", this.asignacionEstudianteCarrera);

        this.estudiante = this.asignacionEstudianteCarrera.getEstudiante();
        this.agregarAtributosDefault(modelo, wrapperAgregarAsignacionEstudianteCarrera,
                false, null, false, realizarBusqueda,null);

    }

//______________________________________________________________________________
    /**
     * Busca y agrega los atributos necesarios para la creaci&oacute;n de los 
     * componentes en la vista <code>mostrarHistorialAsignacionEstudianteCarrera.htm</code>.
     * Es utilizado en el controlador {@link ControladorMostrarHistorialAsignacionEstudianteCarrera}.
     *
     * @param modelo Objeto de tipo {@link Model}
     * @param idHistorialAsignacionEstudianteCarrera
     * @param wrapperHistorialAsignacionEstudianteCarrera Objeto de tipo {@link WrapperHistorialAsignacionEstudianteCarrera}
     * @param autoOpenDialog  Indica si se habilita o no el dialogo para agregar
     * objeto de tipo {@link HistorialAsignacionEstudianteCarrera}
     * @param wrapperModificarHistorialAsignacionEstudianteCarrera Objeto de tipo {@link WrapperHistorialAsignacionEstudianteCarrera}
     * @param autoOpenDialogModificar Indica si se habilita o no el dialgo para modificar
     * objeto de tipo {@link HistorialAsignacionEstudianteCarrera}
     * @param realizarBusqueda Indica si se realiza la b&uacute;squeda o no de algunos
     * atributos.
     */
    protected void agregarAtributosDefaultHistorial(Model modelo, Integer idHistorialAsignacionEstudianteCarrera,
            WrapperHistorialAsignacionEstudianteCarrera wrapperHistorialAsignacionEstudianteCarrera,
            boolean autoOpenDialog,
            WrapperHistorialAsignacionEstudianteCarrera wrapperModificarHistorialAsignacionEstudianteCarrera,
            boolean autoOpenDialogModificar, boolean realizarBusqueda) {

        if (realizarBusqueda) {
            this.listarAtributosDeHistorial();
        }

        modelo.addAttribute("listadoSituaciones", this.listadoSituaciones);
        modelo.addAttribute("listadoSemestres", this.listadoSemestres);
        modelo.addAttribute("estudiante", this.estudiante);
        modelo.addAttribute("asignacionEstudianteCarrera", this.asignacionEstudianteCarrera);
        modelo.addAttribute("wrapperHistorialAsignacionEstudianteCarrera", wrapperHistorialAsignacionEstudianteCarrera);
        modelo.addAttribute("wrapperModificarHistorialAsignacionEstudianteCarrera", wrapperModificarHistorialAsignacionEstudianteCarrera);
        modelo.addAttribute("autoOpenDialog", autoOpenDialog);
        modelo.addAttribute("autoOpenDialogModificar", autoOpenDialogModificar);
        modelo.addAttribute("idHistorialAsignacionEstudianteCarrera", idHistorialAsignacionEstudianteCarrera);
    }
}
