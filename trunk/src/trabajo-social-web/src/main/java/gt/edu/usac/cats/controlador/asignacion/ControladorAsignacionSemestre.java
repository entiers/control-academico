/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */
package gt.edu.usac.cats.controlador.asignacion;

import gt.edu.usac.cats.dominio.AsignacionCursoPensum;
import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.Curso;
import gt.edu.usac.cats.dominio.DetalleAsignacion;
import gt.edu.usac.cats.dominio.Horario;
import gt.edu.usac.cats.dominio.Pensum;
import gt.edu.usac.cats.dominio.busqueda.DatosAsignacion;
import gt.edu.usac.cats.enums.TipoAsignacion;
import gt.edu.usac.cats.enums.TipoHorario;
import gt.edu.usac.cats.enums.TipoRubro;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Esta clase es el controlador que se encarga de la llamada al proceso de asignacion
 * de cursos de semestre de estudiante de reingreso
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
@Controller("controladorAsignacionSemestre")
public class ControladorAsignacionSemestre extends ControladorAbstractoAsignacion {
//______________________________________________________________________________
    private static Logger log = Logger.getLogger(ControladorAsignacionSemestre.class);
//_______________________________________________________________________________
    private static final String TITULO_MENSAJE = "miscursos.asignacionCursos.titulo";
//_______________________________________________________________________________
    private List<DetalleAsignacion> listaAsignacion;
//_______________________________________________________________________________
    private List<Horario> listaHorarioAsignacion;
//_______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * POST de la pagina <code>agregarHorarioAsignacionSemestre.htm</code>. El metodo
     * se encarga de cargar la lista de Horarios seleccionados para asignacion, asi como de
     * eliminar el curso y el horario seleccionados de la lista disponible.
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(value = "agregarHorarioAsignacionSemestre.htm", method = RequestMethod.POST)
    public String agregarHorario(@Valid DatosAsignacion datosAsignacion,
            BindingResult bindingResult,
            Model modelo,
            HttpServletRequest request) {
        
        try {
            asignacionEstudianteCarrera = servicioGeneralImpl.cargarEntidadPorID(AsignacionEstudianteCarrera.class, datosAsignacion.getIdAsignacionEstudianteCarrera());
            semestre = servicioSemestreImpl.getSemestreActivo();

            //Validar que sea el primer curso a asignar
            if(datosAsignacion.getTotalCursos()==0){
                this.listaHorarioAsignacion =  new ArrayList<Horario>();
                listaHorario = new ArrayList<Horario>();
                listaCurso = servicioCursoImpl.getCursoAsignacion(asignacionEstudianteCarrera.getCarrera(),
                                                                    semestre,TipoHorario.SEMESTRE);
            }

            //Cargar horario seleccionado al listadoHorarioAsignacion
            if (!listaCurso.isEmpty()) {
                Curso curso = servicioGeneralImpl.cargarEntidadPorID(Curso.class, Short.parseShort(String.valueOf(datosAsignacion.getIdCurso())));
                Horario horario = servicioGeneralImpl.cargarEntidadPorID(Horario.class, datosAsignacion.getIdHorario());
                listaHorarioAsignacion.add(horario);
                datosAsignacion.incrementarTotalCursos();
                for (int i = 0; i < listaCurso.size(); i++) {
                    if (listaCurso.get(i).getIdCurso()==curso.getIdCurso()) {
                        listaCurso.remove(i);
                    }
                }
                if (!listaCurso.isEmpty()) {
                    listaHorario = servicioHorarioImpl.getHorario(listaCurso.get(0), semestre, TipoHorario.SEMESTRE);
                } else {
                    listaHorario.clear();
                }
            }
        } catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request,TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }
        this.cargarModelo(modelo);
        return "asignacion/asignacionSemestre";
    }
//_______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina <code>asignacionSemestre.htm</code> por medio de AJAX.
     * El metodo se encarga de realizar la asignacion de los horarios seleccionados
     * en la listaHorarioAsignacion, despues de haber realizado la validacion de:
     * <ul>
     *  <li>En el listado de horarios no existan traslapes</li>
     *  <li>Los cursos prerrequisito de cada curso a asignar se encuentre aprobado</li>
     *  <li>Se tenga por lo menos la misma cantidad de creditos prerrequisito</li>
     *  <li>No se tenga asignado el mismo curso en el presente semestre</li>
     * </ul>
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(value = "realizarAsignacionSemestre.htm", method = RequestMethod.POST)
    public String realizarAsignacion(@Valid DatosAsignacion datosAsignacion,
                                    BindingResult bindingResult,
                                    Model modelo,
                                    HttpServletRequest request) throws IOException {

        this.cargarModelo(modelo);
        AsignacionCursoPensum acp;
        //Validando traslape de cursos
        try {
            if (servicioHorarioImpl.existeTraslape(listaHorarioAsignacion)) {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "miscursos.asignacionCursos.existeTraslape", false);
                return "asignacion/asignacionSemestre";
            } else {
                asignacionEstudianteCarrera = servicioGeneralImpl.cargarEntidadPorID(AsignacionEstudianteCarrera.class, datosAsignacion.getIdAsignacionEstudianteCarrera());
                Pensum pensum = this.servicioPensumEstudianteCarrera.getPensumEstudianteCarreraValido(
                    asignacionEstudianteCarrera.getEstudiante()).getPensum();
                if (pensum != null) {
                    for (Horario horario : this.listaHorarioAsignacion) {
                       //Validando prerrequisitos por curso
                        acp = (AsignacionCursoPensum) servicioAsignacionCursoPensumImpl.getListadoAsignacionCursoPensum(horario.getCurso(), pensum).get(0);

                        if (servicioCursoAprobadoImpl.getCursoPrerrequisitoPendiente(asignacionEstudianteCarrera, horario.getCurso()).isEmpty()
                                & servicioCursoAprobadoImpl.getCreditosAprobados(asignacionEstudianteCarrera) < acp.getCreditosPrerrequisito()) {
                            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "miscursos.asignacionCursos.prerrequisitoPendiente", false);
                            return "asignacion/asignacionSemestre";
                        }
                        //Validando asignaciones en semestre actual
                        if (!servicioDetalleAsignacionImpl.getListadoDetalleAsignacion(horario.getCurso(), semestre, asignacionEstudianteCarrera,TipoAsignacion.ASIGNACION_CURSOS_SEMESTRE).isEmpty()) {
                            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "miscursos.asignacionCursos.cursoYaAsignado", false);
                            return "asignacion/asignacionSemestre";
                        }

                        //Validando total de asignaciones por curso
                        if(servicioDetalleAsignacionImpl.getTotalAsignaciones(horario.getCurso(),
                                   asignacionEstudianteCarrera, TipoAsignacion.ASIGNACION_CURSOS_SEMESTRE)>=3){
                            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "miscursos.asignacionCursos.totalAsignacionesExcedidas", false);
                            return "asignacion/asignacionSemestre";
                        }

                    }
                } else {
                    RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "miscursos.asignacionCursos.estudianteSinPensum", false);
                    return "asignacion/asignacionSemestre";
                }
                this.listaAsignacion = servicioAsignacionImpl.realizarAsignacionCursos(this.asignacionEstudianteCarrera, this.listaHorarioAsignacion,datosAsignacion.getTipoAsignacion());
                if (!this.listaAsignacion.isEmpty()) {
                    this.enviarEmail(this.listaAsignacion);
                    listaHorarioAsignacion.clear();
                    return "redirect:asignacionExitosa.htm?iascsvr=" + this.listaAsignacion.get(0).getAsignacion().getIdAsignacion();
                } 
            }
        } catch (Exception e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }
        return "asignacion/asignacionSemestre";
    }

//  _____________________________________________________________________________
    private void cargarModelo(Model modelo){
        modelo.addAttribute("listaCurso", listaCurso);
        modelo.addAttribute("listaHorario", listaHorario);
        modelo.addAttribute("horarioElegido", true);
        modelo.addAttribute("listadoHorarioAsignados", this.listaHorarioAsignacion);
    }
}
