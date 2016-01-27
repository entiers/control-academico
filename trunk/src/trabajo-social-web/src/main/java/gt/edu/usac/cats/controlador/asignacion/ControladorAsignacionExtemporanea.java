/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */
package gt.edu.usac.cats.controlador.asignacion;

import gt.edu.usac.cats.dominio.AsignacionCursoPensum;
import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.DetalleAsignacion;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.Horario;
import gt.edu.usac.cats.dominio.Pensum;
import gt.edu.usac.cats.dominio.PensumEstudianteCarrera;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.dominio.busqueda.DatosAsignacion;
import gt.edu.usac.cats.dominio.wrapper.WrapperAsignacionCursosExtemporaneas;
import gt.edu.usac.cats.enums.TipoAsignacion;
import gt.edu.usac.cats.enums.TipoHorario;
import gt.edu.usac.cats.enums.TipoRubro;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;

/**
 * Clase encargadda de manejar el proceso de asignacion de cursos extemporaneos
 * por parte de un usuario administrativo
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
@Controller("controladorAsignacionExtemporanea")
@Scope(value = WebApplicationContext.SCOPE_SESSION)
@SessionAttributes(value = {
    "semestre", "estudiante", "listaAEC", "asignacionEstudianteCarrera",
    "pensumEstudianteCarrera", "listaAsignacionCursoPensum", "listaAsignacion", "listaHorarioAsignacion", "listaHorario"
})
public class ControladorAsignacionExtemporanea extends ControladorAbstractoAsignacion implements Serializable {
//______________________________________________________________________________    

    private Semestre semestre;
//______________________________________________________________________________    
    private Estudiante estudiante;
//______________________________________________________________________________    
    private List<AsignacionEstudianteCarrera> listaAEC;
//______________________________________________________________________________    
    private AsignacionEstudianteCarrera asignacionEstudianteCarrera;
//______________________________________________________________________________    
    private PensumEstudianteCarrera pensumEstudianteCarrera;
//______________________________________________________________________________    
    private List<AsignacionCursoPensum> listaAsignacionCursoPensum;
//______________________________________________________________________________
    private List<DetalleAsignacion> listaAsignacion;
//______________________________________________________________________________
    private List<Horario> listaHorarioAsignacion;
//______________________________________________________________________________
    private List<Horario> listaHorario;
//______________________________________________________________________________
    private static Logger log = Logger.getLogger(ControladorAsignacionExtemporanea.class);
//______________________________________________________________________________
    private static final String TITULO_MENSAJE = "miscursos.asignacionCursos.titulo";

    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina
     * <code>asignacionCursosAdmin.htm</code>. El metodo se encarga inicializar
     * los objetos que se usaran en la pagina.</p>
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     * seran usados en la pagina
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(value = "asignacionCursosAdmin.htm", method = RequestMethod.GET)
    public String getAsignacionCursosAdmin(Model modelo, HttpServletRequest request,
            @RequestParam Integer idEstudiante) {

        modelo.addAttribute("wrapperAsignacionCursosExtemporaneas", new WrapperAsignacionCursosExtemporaneas());

        //Validando parametros en el request
        if (idEstudiante == null) {
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "admin.asignacionCursos.sinParamEstudiante", false);
            RequestUtil.agregarRedirect(request, "buscarEstudiante.htm");
            return "asignacion/asignacionCursosAdmin";
        }

        try {
            //Buscando estudiante
            this.estudiante = this.servicioGeneralImpl.cargarEntidadPorID(Estudiante.class, idEstudiante.intValue());
            if (this.estudiante == null) {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "admin.asignacionCursos.estudianteNoEncontrado", false);
                RequestUtil.agregarRedirect(request, "buscarEstudiante.htm");
                return "asignacion/asignacionCursosAdmin";
            }

            this.listaAEC = this.servicioAsignacionEstudianteCarreraImpl.
                    getAsignacionEstudianteCarrera(this.estudiante, true);
            if (this.listaAEC.isEmpty()) {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "admin.asignacionCursos.estudianteNoEncontrado", false);
                RequestUtil.agregarRedirect(request, "buscarEstudiante.htm");
                return "asignacion/asignacionCursosAdmin";
            }
        } catch (Exception ex) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, ex);
        }

        modelo.addAttribute("listaTipoHorario", TipoHorario.values());
        modelo.addAttribute("listadoAEC", this.listaAEC);
        modelo.addAttribute("estudiante", this.estudiante);

        return "asignacion/asignacionCursosAdmin";
    }
//_____________________________________________________________________________

    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * POST de la pagina
     * <code>asignacionCursosAdmin.htm</code>. El metodo se encarga de validar
     * que el estudiante se encuentre inscrito en la carrera seleccionada asi
     * como de establecer los parametros iniciales para realizar la asignacion
     * de cursos.</p>
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     * seran usados en la pagina
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(value = "asignacionCursosAdmin.htm", method = RequestMethod.POST)
    public String postAsignacionCursosAdmin(Model modelo, HttpServletRequest request,
            @Valid WrapperAsignacionCursosExtemporaneas wrapperAsignacionCursosExtemporaneas) {

        modelo.addAttribute("estudiante", this.estudiante);

        this.asignacionEstudianteCarrera = this.servicioGeneralImpl.
                cargarEntidadPorID(
                AsignacionEstudianteCarrera.class,
                wrapperAsignacionCursosExtemporaneas.getAsignacionEstudianteCarrera().getIdAsignacionEstudianteCarrera());

        pensumEstudianteCarrera = servicioPensumEstudianteCarrera.getPensumEstudianteCarreraValido(asignacionEstudianteCarrera);

        if (pensumEstudianteCarrera == null) {
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "asignacionExtemporanea.pensumEstudianteCarrera.null", false);
            modelo.addAttribute("listaTipoHorario", TipoHorario.values());
            modelo.addAttribute("listadoAEC", this.listaAEC);

            return "asignacion/asignacionCursosAdmin";
        }

        //Validando estudiante inscrito
        if (!this.servicioAsignacionEstudianteCarreraImpl.estaEstudianteInscrito(
                this.asignacionEstudianteCarrera.getEstudiante(),
                this.asignacionEstudianteCarrera.getCarrera())) {
            modelo.addAttribute("wrapperAsignacionCursosExtemporaneas", wrapperAsignacionCursosExtemporaneas);
            modelo.addAttribute("listaTipoHorario", TipoHorario.values());
            modelo.addAttribute("listadoAEC", this.listaAEC);
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "admin.asignacionCursos.estudianteNoInscrito", false);
            return "asignacion/asignacionCursosAdmin";
        }

        DatosAsignacion datosAsignacion = new DatosAsignacion();

        datosAsignacion.setTipoHorario(wrapperAsignacionCursosExtemporaneas.getTipoHorario());
        datosAsignacion.setIdAsignacionEstudianteCarrera(wrapperAsignacionCursosExtemporaneas.
                getAsignacionEstudianteCarrera().getIdAsignacionEstudianteCarrera());

        if (wrapperAsignacionCursosExtemporaneas.getTipoHorario() == TipoHorario.SEMESTRE) {
            datosAsignacion.setTipoAsignacion(TipoAsignacion.ASIGNACION_CURSOS_SEMESTRE);
        } else if (wrapperAsignacionCursosExtemporaneas.getTipoHorario() == TipoHorario.VACACIONES) {
            datosAsignacion.setTipoAsignacion(TipoAsignacion.ASIGNACION_CURSOS_VACACIONES);
            datosAsignacion.setTipoRubro(TipoRubro.VACACIONES);
        } else if (wrapperAsignacionCursosExtemporaneas.getTipoHorario() == TipoHorario.PRIMERA_RETRASADA) {
            datosAsignacion.setTipoAsignacion(TipoAsignacion.ASIGNACION_PRIMERA_RETRASADA);
            datosAsignacion.setTipoRubro(TipoRubro.PRIMERA_RETRASADA);
        } else if (wrapperAsignacionCursosExtemporaneas.getTipoHorario() == TipoHorario.SEGUNDA_RETRASADA) {
            datosAsignacion.setTipoAsignacion(TipoAsignacion.ASIGNACION_SEGUNDA_RETRASADA);
            datosAsignacion.setTipoRubro(TipoRubro.SEGUNDA_RETRASADA);
        }

        this.semestre = this.servicioSemestreImpl.getSemestreActivo();


        
        this.listaAsignacionCursoPensum = this.servicioCursoImpl.getCursoAsignacion(pensumEstudianteCarrera.getPensum(),
                this.semestre, datosAsignacion.getTipoHorario());

        System.out.println("**** listaAsignacionCursoPensum.size: "+this.listaAsignacionCursoPensum.size());
        if (this.listaAsignacionCursoPensum.isEmpty()) {
            modelo.addAttribute("wrapperAsignacionCursosExtemporaneas", wrapperAsignacionCursosExtemporaneas);
            modelo.addAttribute("listaTipoHorario", TipoHorario.values());
            modelo.addAttribute("listadoAEC", this.listaAEC);
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "miscursos.asignacionCursos.noExisteHorario", false);
            return "asignacion/asignacionCursosAdmin";
        }
//        System.out.println("*** pensum: "+pensumEstudianteCarrera.getPensum());
//        System.out.println("*** semestre: "+this.semestre.getIdSemestre());
//        System.out.println("*** tipoHorario: "+datosAsignacion.getTipoHorario());
//        System.out.println("*** asignacionCursoPensum: "+this.listaAsignacionCursoPensum.get(0));
        
        
        //this.listaHorario = this.servicioHorarioImpl.getHorario(this.listaAsignacionCursoPensum.get(0), this.semestre, datosAsignacion.getTipoHorario());
        this.listaHorario = this.servicioHorarioImpl.getHorario(this.listaAsignacionCursoPensum.get(0), this.semestre, datosAsignacion.getTipoHorario());

        modelo.addAttribute("datosAsignacion", datosAsignacion);
        //modelo.addAttribute("listaCurso", this.listaAsignacionCursoPensum);
        modelo.addAttribute("listaAsignacionCursoPensum", this.listaAsignacionCursoPensum);
        modelo.addAttribute("listaHorario", this.listaHorario);

        return "asignacion/realizarAsignacionExtemporanea";
    }

//_______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * POST de la pagina
     * <code>agregarHorarioAsignacionExtemporanea.htm</code>. El metodo se
     * encarga de cargar la lista de Horarios seleccionados para asignacion, asi
     * como de eliminar el curso y el horario seleccionados de la lista
     * disponible.
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     * seran usados en la pagina
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(value = "agregarHorarioAsignacionExtemporanea.htm", method = RequestMethod.POST)
    public String agregarHorario(DatosAsignacion datosAsignacion,
            Model modelo,
            HttpServletRequest request) {

        try {
            asignacionEstudianteCarrera = servicioGeneralImpl.cargarEntidadPorID(AsignacionEstudianteCarrera.class, datosAsignacion.getIdAsignacionEstudianteCarrera());
            semestre = servicioSemestreImpl.getSemestreActivo();
            pensumEstudianteCarrera = servicioPensumEstudianteCarrera.getPensumEstudianteCarreraValido(asignacionEstudianteCarrera);

            //Validar que sea el primer curso a asignar
            if (datosAsignacion.getTotalCursos() == 0) {
                this.listaHorarioAsignacion = new ArrayList<Horario>();
                listaHorario = new ArrayList<Horario>();
                listaAsignacionCursoPensum = servicioCursoImpl.getCursoAsignacion(pensumEstudianteCarrera.getPensum(),
                        semestre, TipoHorario.SEMESTRE);
            }

            //Validar que el estudiante no se asigne mas de dos cursos en vacaciones
            if (listaHorarioAsignacion.size() == 2 & datosAsignacion.getTipoHorario() == TipoHorario.VACACIONES) {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "miscursos.asignacionCursos.vacaciones.limiteCursos", false);
                this.cargarModelo(modelo);
                return "asignacion/asignacionVacaciones";
            }

            //Cargar horario seleccionado al listadoHorarioAsignacion
            if (!listaAsignacionCursoPensum.isEmpty()) {
                AsignacionCursoPensum asignacionCursoPensum =
                        servicioGeneralImpl.cargarEntidadPorID(AsignacionCursoPensum.class, Short.parseShort(String.valueOf(datosAsignacion.getIdAsignacionCursoPensum())));
                
                //Horario horario = servicioGeneralImpl.cargarEntidadPorID(Horario.class, datosAsignacion.getIdHorario());
                List<Horario> horarios = servicioHorarioImpl.getHorario(asignacionCursoPensum, semestre,datosAsignacion.getSeccion());  //mc
                
                //listaHorarioAsignacion.add(horarios);
                listaHorarioAsignacion.addAll(horarios); //mc
                
                datosAsignacion.incrementarTotalCursos();
                for (int i = 0; i < listaAsignacionCursoPensum.size(); i++) {
                    if (listaAsignacionCursoPensum.get(i).getIdAsignacionCursoPensum() == asignacionCursoPensum.getIdAsignacionCursoPensum()) {
                        listaAsignacionCursoPensum.remove(i);
                    }
                }
                if (!listaAsignacionCursoPensum.isEmpty()) {
                    listaHorario = servicioHorarioImpl.getHorario(listaAsignacionCursoPensum.get(0), semestre, TipoHorario.SEMESTRE);
                } else {
                    listaHorario.clear();
                }
            }
        } catch (Exception e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        modelo.addAttribute("estudiante", this.estudiante);
        this.cargarModelo(modelo);
        return "asignacion/realizarAsignacionExtemporanea";
    }
//_______________________________________________________________________________

    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * POST de la pagina
     * <code>realizarAsignacionExtemporanea.htm</code>. El metodo se encarga de
     * validar el tipo de asignacion para realizarla haciendo las validaciones
     * correspondientes por cada tipo.
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     * seran usados en la pagina
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(value = "realizarAsignacionExtemporanea.htm", method = RequestMethod.POST)
    public String realizarAsignacion(DatosAsignacion datosAsignacion,
            Model modelo,
            HttpServletRequest request) {

        this.cargarModelo(modelo);
        AsignacionCursoPensum acp;

        try {

              
            //Validando traslape de cursos recien agregados
            System.out.println("1.Size de listaHorarioAsignacion :"+listaHorarioAsignacion.size());
            if (servicioHorarioImpl.existeTraslape(listaHorarioAsignacion)
                    & (datosAsignacion.getTipoHorario() == TipoHorario.SEMESTRE | datosAsignacion.getTipoHorario() == TipoHorario.VACACIONES)) {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "miscursos.asignacionCursos.existeTraslape", false);
                return "asignacion/realizarAsignacionExtemporanea";
            }
            System.out.println("2.No hay traslape :");
            asignacionEstudianteCarrera = servicioGeneralImpl.cargarEntidadPorID(AsignacionEstudianteCarrera.class, datosAsignacion.getIdAsignacionEstudianteCarrera());
           // validando traslape con cursos asignados en el mismo semestre pero en diferente transaccion
            List<Horario> listaHorariosOtrasTrx = servicioHorarioImpl.getHorariosAsignados(asignacionEstudianteCarrera, semestre, datosAsignacion.getTipoHorario());
            if (servicioHorarioImpl.existeTraslape(listaHorariosOtrasTrx)
                    & (datosAsignacion.getTipoHorario() == TipoHorario.SEMESTRE | datosAsignacion.getTipoHorario() == TipoHorario.VACACIONES)) {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "miscursos.asignacionCursos.existeTraslape", false);
                return "asignacion/realizarAsignacionExtemporanea";
            }
            
            
            Pensum pensum = this.servicioPensumEstudianteCarrera.getPensumEstudianteCarreraValido(
                    asignacionEstudianteCarrera).getPensum();

            if (pensum != null) {

                if (datosAsignacion.getTipoAsignacion() == TipoAsignacion.ASIGNACION_CURSOS_VACACIONES) {
                    //Validando que no tenga asignaciones previas y no exceda las permitidas
                    List<DetalleAsignacion> listaDetAsigVacaciones = servicioDetalleAsignacionImpl.
                            getListadoDetalleAsignacion(semestre, asignacionEstudianteCarrera, datosAsignacion.getTipoAsignacion());
                    if (!listaDetAsigVacaciones.isEmpty()) {
                        if (listaHorarioAsignacion.size() + listaDetAsigVacaciones.size() > 2) {
                            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "miscursos.asignacionCursos.vacaciones.limiteCursos", false);
                            this.cargarModelo(modelo);
                            return "asignacion/realizarAsignacionExtemporanea";
                        }
                    }
                }

                for (Horario horario : this.listaHorarioAsignacion) {
                    //Validando prerrequisitos por curso
                    acp = (AsignacionCursoPensum) servicioAsignacionCursoPensumImpl.getListadoAsignacionCursoPensum(horario.getAsignacionCursoPensum(), pensum).get(0);
//MC VALIDA PRERREQUISITO
                    if (!servicioCursoAprobadoImpl.getCursoPrerrequisitoPendiente(asignacionEstudianteCarrera, horario.getAsignacionCursoPensum()).isEmpty()
                           | servicioCursoAprobadoImpl.getCreditosAprobados(asignacionEstudianteCarrera) < acp.getCreditosPrerrequisito()) {
                        RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "miscursos.asignacionCursos.prerrequisitoPendiente", false);
                        return "asignacion/realizarAsignacionExtemporanea";
                    }

                    //Validando asignaciones en semestre actual
                    if (!servicioDetalleAsignacionImpl.getListadoDetalleAsignacion(horario.getAsignacionCursoPensum(), semestre, asignacionEstudianteCarrera, datosAsignacion.getTipoAsignacion()).isEmpty()) {
                        RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "miscursos.asignacionCursos.cursoYaAsignado", false);
                        return "asignacion/realizarAsignacionExtemporanea";
                    }

                    //Validando total de asignaciones por curso
                    if (servicioDetalleAsignacionImpl.getTotalAsignaciones(horario.getAsignacionCursoPensum(),
                            asignacionEstudianteCarrera, datosAsignacion.getTipoAsignacion()) > 3) {
                        RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "miscursos.asignacionCursos.totalAsignacionesExcedidas", false);
                        return "asignacion/realizarAsignacionExtemporanea";
                    }

                    //Validando que el curso este pagado
//                    if (datosAsignacion.getTipoAsignacion() != TipoAsignacion.ASIGNACION_CURSOS_SEMESTRE
//                            & servicioBoletaBancoImpl.listadoBoletaBanco(asignacionEstudianteCarrera.getEstudiante(),
//                            horario.getAsignacionCursoPensum(), semestre, datosAsignacion.getTipoRubro()).isEmpty()) {
//                        RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "miscursos.asignacionCursos.cursoNoCancelado", false);
//                        return "asignacion/realizarAsignacionExtemporanea";
//                    }

                    //Validar zona minima
                    if (datosAsignacion.getTipoAsignacion() != TipoAsignacion.ASIGNACION_CURSOS_SEMESTRE
                            & !servicioDetalleAsignacionImpl.tieneZonaMinima(horario.getAsignacionCursoPensum(), asignacionEstudianteCarrera)) {
                        RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "miscursos.asignacionCursos.vacaciones.sinZonaMinima", false);
                        return "asignacion/realizarAsignacionExtemporanea";
                    }

                    if (datosAsignacion.getTipoAsignacion() == TipoAsignacion.ASIGNACION_PRIMERA_RETRASADA
                            | datosAsignacion.getTipoAsignacion() == TipoAsignacion.ASIGNACION_SEGUNDA_RETRASADA) {
                        //Validando que el curso este asignado en el semestre actual
                        if (servicioDetalleAsignacionImpl.getListadoDetalleAsignacion(horario.getAsignacionCursoPensum(), semestre, asignacionEstudianteCarrera, TipoAsignacion.ASIGNACION_CURSOS_SEMESTRE).isEmpty()) {
                            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "miscursos.asignacionCursos.retrasada.cursoNoAsignado", false);
                            return "asignacion/realizarAsignacionExtemporanea";
                        }
                    }

                }
            } else {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "miscursos.asignacionCursos.estudianteSinPensum", false);
                return "asignacion/realizarAsignacionExtemporanea";
            }

            //Realizando asignacion de cursos
            this.listaAsignacion = servicioAsignacionImpl.realizarAsignacionCursos(this.asignacionEstudianteCarrera,
                    this.listaHorarioAsignacion, datosAsignacion.getTipoAsignacion());
            if (!this.listaAsignacion.isEmpty()) {
                this.enviarEmail(this.listaAsignacion, this.asignacionEstudianteCarrera);
                listaHorarioAsignacion.clear();
                return "redirect:asignacionExitosa.htm?iascsvr=" + this.listaAsignacion.get(0).getAsignacion().getIdAsignacion();
            }
        } catch (Exception e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        return "asignacion/realizarAsignacionExtemporanea";
    }

    //  _____________________________________________________________________________
    private void cargarModelo(Model modelo) {
        modelo.addAttribute("listaAsignacionCursoPensum", listaAsignacionCursoPensum);
        modelo.addAttribute("listaHorario", listaHorario);
        modelo.addAttribute("horarioElegido", true);
        modelo.addAttribute("listadoHorarioAsignados", this.listaHorarioAsignacion);
    }
}
