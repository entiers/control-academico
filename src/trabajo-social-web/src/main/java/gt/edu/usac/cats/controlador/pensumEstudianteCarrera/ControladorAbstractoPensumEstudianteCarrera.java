/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */
package gt.edu.usac.cats.controlador.pensumEstudianteCarrera;

import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.Pensum;
import gt.edu.usac.cats.dominio.PensumEstudianteCarrera;
import gt.edu.usac.cats.dominio.wrapper.WrapperEquivalenciaPorPensum;
import gt.edu.usac.cats.servicio.ServicioPensumEstudianteCarrera;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.ui.Model;

/**
 * Clase abstracta que lleva el control de m&eacute;dos y atributos que son utilizados
 * en los controladores {@link ControladorEliminarPensumEstudianteCarrera } y
 * {@link ControladorMostrarPensumEstudianteCarrera}.
 *
 * @author Mario Batres
 * @version 1.0
 */
public abstract class ControladorAbstractoPensumEstudianteCarrera {
//______________________________________________________________________________

    /**
     * <p>Contiene metodos que permiten el manejo de la informaci&oacute;n relacionada
     * con el con el objeto de tipo {@link PensumEstudianteCarrera}en la base
     * de datos. Este objeto se encuentra registrado como un bean de servicio
     * en Spring, por lo que este es el encargado de
     * inyectar la dependencia.</p>     
     */
    @Resource
    protected ServicioPensumEstudianteCarrera servicioPensumEstudianteCarreraImpl;

    /**
     * Agrega los atributos default en la pa&acute;gina de <code>mostrarPensumEstudianteCarrera.htm</code>.
     * Realiza las b&uacute;squedas necesarias para ser mostrados.
     *
     * @param modelo Objeto de tipo {@link Model}
     * @param pensumEstudianteCarrera Objeto que sirve como wrapper en la vista.
     * Objeto de tipo {@link PensumEstudianteCarrera}
     * @param autoOpenDialogAsignar Variable que le indica a la vista si debe mostrar el di&aacute;logo
     * para agregar una asignaci&oacute;n de un pensum.
     *
     */
    protected void agregarAtributosDefault(Model modelo,
            Pensum pensum,
            AsignacionEstudianteCarrera asignacionEstudianteCarrera,
            PensumEstudianteCarrera pensumEstudianteCarrera,
            List<PensumEstudianteCarrera> listadoPensumEstudianteCarreraNoValidos,
            List<Pensum> listadoPensumsNoAsignadosAEsutudianteCarrera,
            WrapperEquivalenciaPorPensum wrapperEquivalenciaPorPensum,
            boolean autoOpenDialogAsignar,
            boolean realizarBusqueda) {

        if (realizarBusqueda) {
            //Se obtiene los no validos
            listadoPensumEstudianteCarreraNoValidos =
                    this.servicioPensumEstudianteCarreraImpl.getListadoPensumEstudianteCarreraNoValidos(asignacionEstudianteCarrera);


            listadoPensumsNoAsignadosAEsutudianteCarrera =
                    this.servicioPensumEstudianteCarreraImpl.getPensumsNoAsignadosAEstudianteCarrera(asignacionEstudianteCarrera);
        }

        modelo.addAttribute("estudiante", asignacionEstudianteCarrera.getEstudiante());
        modelo.addAttribute("carrera", asignacionEstudianteCarrera.getCarrera());
        modelo.addAttribute("pensum", pensum);
        modelo.addAttribute("pensumEstudianteCarrera", pensumEstudianteCarrera);
        modelo.addAttribute("listadoPensumEstudianteCarreraNoValidos", listadoPensumEstudianteCarreraNoValidos);
        modelo.addAttribute("listadoPensumsNoAsignadosAEsutudianteCarrera", listadoPensumsNoAsignadosAEsutudianteCarrera);
        modelo.addAttribute("autoOpenDialog", autoOpenDialogAsignar);
        modelo.addAttribute("wrapperEquivalenciaPorPensum", wrapperEquivalenciaPorPensum);
    }
}
