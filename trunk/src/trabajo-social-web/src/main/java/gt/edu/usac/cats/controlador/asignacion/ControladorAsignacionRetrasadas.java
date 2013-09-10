/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.controlador.asignacion;

import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.DetalleAsignacion;
import gt.edu.usac.cats.dominio.Horario;
import gt.edu.usac.cats.dominio.Pensum;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.dominio.busqueda.DatosAsignacion;
import gt.edu.usac.cats.enums.TipoAsignacion;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
@Controller("ControladorAsignacionRetrasadas")
@Scope(value = WebApplicationContext.SCOPE_SESSION)
@SessionAttributes(value={"listaAsignacion", "semestre", "asignacionEstudianteCarrera"})
public class ControladorAsignacionRetrasadas extends ControladorAbstractoAsignacion implements Serializable{
//______________________________________________________________________________
    private static Logger log = Logger.getLogger(ControladorAsignacionRetrasadas.class);
//______________________________________________________________________________
    private static final String TITULO_MENSAJE = "miscursos.asignacionCursos.titulo";
//______________________________________________________________________________
    private List<DetalleAsignacion> listaAsignacion;
//______________________________________________________________________________
    private Semestre semestre;
//______________________________________________________________________________
    private AsignacionEstudianteCarrera asignacionEstudianteCarrera;
//______________________________________________________________________________
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
    @RequestMapping(value = "realizarAsignacionRetrasada.htm", method = RequestMethod.POST)
    public String realizarAsignacion(@Valid DatosAsignacion datosAsignacion,
                                    BindingResult bindingResult,
                                    Model modelo,
                                    HttpServletRequest request) throws IOException {

        //Validando traslape de cursos
        try {
            asignacionEstudianteCarrera = servicioGeneralImpl.cargarEntidadPorID(AsignacionEstudianteCarrera.class, datosAsignacion.getIdAsignacionEstudianteCarrera());
            semestre = servicioSemestreImpl.getSemestreActivo();
            List<DetalleAsignacion> listaDetalleAsignacion
                                    = servicioDetalleAsignacionImpl.getListadoDetalleAsignacion(semestre,
                                            asignacionEstudianteCarrera,
                                            TipoAsignacion.ASIGNACION_CURSOS_SEMESTRE
                                        );
            //Agregando objetos al modelo
            modelo.addAttribute("datosAsignacion", datosAsignacion);
            modelo.addAttribute("totalAsignaciones", listaDetalleAsignacion.size());
            modelo.addAttribute("listadoDetalleAsignacion", listaDetalleAsignacion);

            List<Horario> listaHorarioAsignacion = new ArrayList<Horario>();
            Pensum pensum = this.servicioPensumEstudianteCarrera.getPensumEstudianteCarreraValido(
                    asignacionEstudianteCarrera).getPensum();

            if (pensum != null) {
                if (datosAsignacion.getDetalleAsignacion()!=null){
                    for (Object idDetalleAsignacion : datosAsignacion.getDetalleAsignacion()) {
                        DetalleAsignacion detalleAsignacion = servicioGeneralImpl.cargarEntidadPorID(DetalleAsignacion.class, Integer.valueOf(idDetalleAsignacion.toString()));
                        Horario horario = detalleAsignacion.getHorario();

                        //Validando asignacion de curso en semestre actual
                        if (servicioDetalleAsignacionImpl.getListadoDetalleAsignacion(horario.getAsignacionCursoPensum(), semestre, asignacionEstudianteCarrera, TipoAsignacion.ASIGNACION_CURSOS_SEMESTRE).isEmpty()) {
                            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "miscursos.asignacionCursos.retrasada.cursoNoAsignado", false);
                            return "asignacion/asignacionRetrasada";
                        }

                        //Validar zona minima en semestre actual
                        if(!servicioDetalleAsignacionImpl.tieneZonaMinima(horario.getAsignacionCursoPensum(), asignacionEstudianteCarrera, semestre)){
                            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "miscursos.asignacionCursos.vacaciones.sinZonaMinima", false);
                            return "asignacion/asignacionRetrasada";
                        }

                        //Validando asignaciones en retrasada
                        if (!servicioDetalleAsignacionImpl.getListadoDetalleAsignacion(horario.getAsignacionCursoPensum(), semestre, asignacionEstudianteCarrera,datosAsignacion.getTipoAsignacion()).isEmpty()) {
                            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "miscursos.asignacionCursos.cursoYaAsignado", false);
                            return "asignacion/asignacionRetrasada";
                        }

                        //Validando que el curso este pagado
                        //MCTEMPORAL
//                        if(servicioBoletaBancoImpl.listadoBoletaBanco(asignacionEstudianteCarrera.getEstudiante(),
//                                                                    horario.getAsignacionCursoPensum(), semestre, datosAsignacion.getTipoRubro()).isEmpty()){
//                            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "miscursos.asignacionCursos.cursoNoCancelado", false);
//                            return "asignacion/asignacionRetrasada";
//                        }

                        listaHorarioAsignacion.add(horario);
                    }
                }
                else {
                    RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "miscursos.asignacionCursos.retrasada.sinHorarioSeleccionado", false);
                    return "asignacion/asignacionRetrasada";
                }
            } else {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "miscursos.asignacionCursos.estudianteSinPensum", false);
                return "asignacion/asignacionRetrasada";
            }
            if(!listaHorarioAsignacion.isEmpty()){
                this.listaAsignacion = servicioAsignacionImpl.realizarAsignacionCursos(asignacionEstudianteCarrera, listaHorarioAsignacion,datosAsignacion.getTipoAsignacion());
                if (!this.listaAsignacion.isEmpty()) {
                    this.enviarEmail(this.listaAsignacion, this.asignacionEstudianteCarrera);
                    return "redirect:asignacionExitosa.htm?iascsvr=" + this.listaAsignacion.get(0).getAsignacion().getIdAsignacion();
                }
            }
            
        } catch (Exception e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }
        return "asignacion/asignacionRetrasada";
    }
}
