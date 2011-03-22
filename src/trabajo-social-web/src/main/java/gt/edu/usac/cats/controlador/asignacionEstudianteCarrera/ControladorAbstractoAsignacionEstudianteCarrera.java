/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cats.controlador.asignacionEstudianteCarrera;

import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.Carrera;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.dominio.Situacion;
import gt.edu.usac.cats.dominio.wrapper.WrapperAgregarAsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.wrapper.WrapperHistorialAsignacionEstudianteCarrera;
import gt.edu.usac.cats.servicio.ServicioAsignacionEstudianteCarrera;
import gt.edu.usac.cats.servicio.ServicioSemestre;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.ui.Model;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
public abstract class ControladorAbstractoAsignacionEstudianteCarrera {
//______________________________________________________________________________

    @Resource
    protected ServicioAsignacionEstudianteCarrera servicioAsignacionEstudianteCarreraImpl;
//______________________________________________________________________________
    @Resource
    protected ServicioSemestre servicioSemestreImpl;
//______________________________________________________________________________
    protected Estudiante estudiante;
//______________________________________________________________________________
    protected AsignacionEstudianteCarrera asignacionEstudianteCarrera;
    private List<Carrera> listadoCarrerasNoAsignadas;
//______________________________________________________________________________
    private List<AsignacionEstudianteCarrera> listadoAsignacionEstudianteCarrera;
//______________________________________________________________________________
    private List<Situacion> listadoSituaciones;
//______________________________________________________________________________
    private List<Semestre> listadoSemestres;

    private void agregarAtributosDeHistorial() {
        this.listadoSituaciones = this.servicioAsignacionEstudianteCarreraImpl.cargarEntidades(Situacion.class);
        this.listadoSemestres = this.servicioSemestreImpl.listarSemestresParaBusqueda();
    }
//______________________________________________________________________________

    protected void agregarAtributosDefault(Model modelo,
            WrapperAgregarAsignacionEstudianteCarrera wrapperAgregarAsignacionEstudianteCarrera,
            boolean autoOpenDialog, boolean realizarBusqueda) {

        if (realizarBusqueda) {
            this.listadoCarrerasNoAsignadas =
                    this.servicioAsignacionEstudianteCarreraImpl.getListadoCarrerasNoAsignadasPorEstudiante(this.estudiante);

            this.listadoAsignacionEstudianteCarrera =
                    this.servicioAsignacionEstudianteCarreraImpl.getAsignacionEstudianteCarrera(estudiante);


            this.agregarAtributosDeHistorial();

            Semestre semestreActivo = this.servicioSemestreImpl.getSemestreActivo();
            wrapperAgregarAsignacionEstudianteCarrera.getWrapperHistorialAsignacionEstudianteCarrera().setSemestre(semestreActivo);


        }


        modelo.addAttribute("autoOpenDialog", autoOpenDialog);
        modelo.addAttribute("estudiante", this.estudiante);
        modelo.addAttribute("listadoAsignacionEstudianteCarrera", this.listadoAsignacionEstudianteCarrera);

        if (this.listadoAsignacionEstudianteCarrera != null && this.listadoAsignacionEstudianteCarrera.size() < 2) {
            modelo.addAttribute("wrapperAgregarAsignacionEstudianteCarrera", wrapperAgregarAsignacionEstudianteCarrera);
            modelo.addAttribute("listadoCarrerasNoAsignadas", this.listadoCarrerasNoAsignadas);
            modelo.addAttribute("listadoSituaciones", this.listadoSituaciones);
            modelo.addAttribute("listadoSemestres", this.listadoSemestres);
            modelo.addAttribute("habilitarFormulario", true);
        }
    }

    protected void agregarAtributosDefaultHistorial(Model modelo,
            WrapperHistorialAsignacionEstudianteCarrera wrapperHistorialAsignacionEstudianteCarrera,
            boolean autoOpenDialog, boolean realizarBusqueda) {

        if (realizarBusqueda) {
            this.agregarAtributosDeHistorial();
        }

        modelo.addAttribute("listadoSituaciones", this.listadoSituaciones);
        modelo.addAttribute("listadoSemestres", this.listadoSemestres);
        modelo.addAttribute("estudiante", this.estudiante);
        modelo.addAttribute("asignacionEstudianteCarrera", this.asignacionEstudianteCarrera);
        modelo.addAttribute("wrapperHistorialAsignacionEstudianteCarrera", wrapperHistorialAsignacionEstudianteCarrera);
        modelo.addAttribute("autoOpenDialog", autoOpenDialog);
    }
}
