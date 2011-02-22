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
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.Horario;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.dominio.Usuario;
import gt.edu.usac.cats.dominio.busqueda.DatosBusquedaCarrera;
import gt.edu.usac.cats.enums.TipoActividad;
import gt.edu.usac.cats.servicio.ServicioAsignacion;
import gt.edu.usac.cats.servicio.ServicioAsignacionCursoPensum;
import gt.edu.usac.cats.servicio.ServicioAsignacionEstudianteCarrera;
import gt.edu.usac.cats.servicio.ServicioCalendarioActividades;
import gt.edu.usac.cats.servicio.ServicioCurso;
import gt.edu.usac.cats.servicio.ServicioCursoAprobado;
import gt.edu.usac.cats.servicio.ServicioDetalleAsignacion;
import gt.edu.usac.cats.servicio.ServicioGeneral;
import gt.edu.usac.cats.servicio.ServicioHorario;
import gt.edu.usac.cats.servicio.ServicioSemestre;
import gt.edu.usac.cats.servicio.ServicioUsuario;
import gt.edu.usac.cats.util.EmailSender;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * Esta clase es el controlador que se encarga de la llamada al proceso de asignacion
 * de cursos de semestre de estudiante de reingreso
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
@Controller("controladorAsignacionCursos")
public class ControladorAsignacionCursos {
//______________________________________________________________________________
    private static Logger log = Logger.getLogger(ControladorAsignacionPrimerIngreso.class);
//_____________________________________________________________________________
    private static final String TITULO_MENSAJE = "miscursos.asignacionCursos.titulo";
//_____________________________________________________________________________
    private AsignacionEstudianteCarrera asignacionEstudianteCarrera;
//_____________________________________________________________________________
    private Curso cursoComputacion;
//_____________________________________________________________________________
    private Estudiante estudiante;
//_____________________________________________________________________________
    private Usuario usuario;
//_____________________________________________________________________________
    private Semestre semestre;
//_____________________________________________________________________________
    private List<AsignacionEstudianteCarrera> listaAEC;
//_____________________________________________________________________________
    private List<Curso> listaCurso;
//_____________________________________________________________________________
    private List<Horario> listaHorario;
//_____________________________________________________________________________
    private List<Horario> listaHorarioAsignacion;
//_____________________________________________________________________________
    private List<DetalleAsignacion> listaAsignacion;
//_____________________________________________________________________________
    @Resource
    private ServicioUsuario servicioUsuarioImpl;
//_____________________________________________________________________________
    @Resource
    private ServicioAsignacion servicioAsignacionImpl;
//_____________________________________________________________________________
    @Resource
    private ServicioAsignacionEstudianteCarrera servicioAsignacionEstudianteCarreraImpl;
//_____________________________________________________________________________
    @Resource
    private ServicioCalendarioActividades servicioCalendarioActividadesImpl;
//_____________________________________________________________________________
    @Resource
    private ServicioSemestre servicioSemestreImpl;
//_____________________________________________________________________________
    @Resource
    private ServicioCursoAprobado servicioCursoAprobadoImpl;
//_____________________________________________________________________________
    @Resource
    private ServicioCurso servicioCursoImpl;
//_____________________________________________________________________________
    @Resource
    private ServicioHorario servicioHorarioImpl;
//_____________________________________________________________________________
    @Resource
    private ServicioAsignacionCursoPensum servicioAsignacionCursoPensumImpl;
//_____________________________________________________________________________
    @Resource
    private ServicioGeneral servicioGeneralImpl;
//_____________________________________________________________________________
    @Resource
    private ServicioDetalleAsignacion servicioDetalleAsignacionImpl;
//_____________________________________________________________________________
    @Resource
    private EmailSender emailSender;
//_____________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina <code>asignacionCursos.htm</code>. El metodo se encarga
     * de validar:
     * <ul>
     *   <li>Que el usuario logueado sea un estudiante</li>
     *   <li>Que el estudiante se encuentre inscrito</li>
     *   <li>Que el periodo de asignacion de cursos este vigente</li>
     *   <li>Que el estudiante tengo aprobado el curso de computacion</li>
     * </ul>
     *
     * Asi mismo se encarga de iniciar los objetos que se usaran en la pagina.</p>
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(value="asignacionCursos.htm",method = RequestMethod.GET)
    public String getAsignacionCursos(Model modelo, HttpServletRequest request){
        try{
            //Obteniendo curso de computacion
            //this.cursoComputacion = this.servicioGeneralImpl.cargarEntidadPorID(Curso.class, 38);

            //Obteniendo semestre actual
            this.semestre = (Semestre) this.servicioSemestreImpl.getSemestreActivo();

            //Buscando usuario logueado por nombre
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            this.usuario = this.servicioUsuarioImpl.cargarUsuarioPorNombre(auth.getName().toString());

            //Validando que el usuario se haya encontrado y sea un estudiante
            if(this.usuario!=null & this.usuario.getEstudiantes().toArray().length>0)
                this.estudiante = (Estudiante) this.usuario.getEstudiantes().toArray()[0];
            else{
                modelo.addAttribute("errorEntidad", true);
                return "asignacion/asignacionCursos";
            }

            //Validando que el estudiante se encuentre inscrito
            if(!this.estudiante.isInscrito()){
                modelo.addAttribute("estudianteNoInscrito",true);
                return "asignacion/asignacionCursos";
            }

            //Validar periodo de asignacion de cursos de semestre
            if(!this.servicioCalendarioActividadesImpl.esFechaActividadValida
                                    (TipoActividad.ASIGNACION_SEMESTRE,
                                        semestre,
                                        new java.util.Date())){
                modelo.addAttribute("periodoInvalido",true);
                return "asignacion/asignacionCursos";
            }

            //Validar curso de computaci�n aprobado
          /*  if(!servicioCursoAprobadoImpl.esCursoAprobado(asignacionEstudianteCarrera, cursoComputacion)){
                modelo.addAttribute("cursoComputacion",true);
                return "asignacion/asignacionCursos";
            }*/


            this.listaAEC = this.servicioAsignacionEstudianteCarreraImpl.getAsignacionEstudianteCarreraPorEstudiante(estudiante);
            modelo.addAttribute("listaAEC", this.listaAEC);
            modelo.addAttribute("datosBusquedaCarrera",  new DatosBusquedaCarrera());
        } catch (Exception e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, null, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }
        return "asignacion/asignacionCursos";
    }
//  _____________________________________________________________________________
     /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * POST de la pagina <code>asignacionCursos.htm</code>. El metodo se encarga
     * de inicializar las listas de cursos y horarios disponibles para la carrera
     * seleccionada en el paso 1 anterior de la asignacion.
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(value="asignacionCursos.htm",method = RequestMethod.POST)
    public String postAsignacionCursos(@Valid DatosBusquedaCarrera datosBusquedaCarrera,
                                        BindingResult bindingResult,
                                        Model modelo,
                                        HttpServletRequest request) {
        try{
            this.asignacionEstudianteCarrera = this.servicioGeneralImpl.cargarEntidadPorID(AsignacionEstudianteCarrera.class, datosBusquedaCarrera.getIdAsignacionEstudianteCarrera());
            this.listaCurso = this.servicioCursoImpl.getCurso(this.asignacionEstudianteCarrera.getCarrera());
            this.listaHorario = this.servicioHorarioImpl.getHorario(this.listaCurso.get(0), semestre);
            this.listaHorarioAsignacion = new ArrayList<Horario>();
        } catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, null, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }        
        modelo.addAttribute("datosBusquedaCarrera",  new DatosBusquedaCarrera());
        modelo.addAttribute("listaCurso", this.listaCurso);
        modelo.addAttribute("listaHorario",this.listaHorario);
        return "asignacion/asignacionCursos2";
    }
//  _____________________________________________________________________________
     /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * POST de la pagina <code>asignacionCursos2.htm</code>. El metodo se encarga
     * de cargar la lista de Horarios seleccionados para asignacion, asi como de
     * eliminar el curso y el horario seleccionados de la lista disponible.
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(value="agregarHorarioAsignacion.htm",method = RequestMethod.POST)
    public String agregarHorario(@Valid DatosBusquedaCarrera datosBusquedaCarrera,
                                        BindingResult bindingResult,
                                        Model modelo,
                                        HttpServletRequest request) {
        try{
            if(!listaCurso.isEmpty()){
                Curso curso = this.servicioGeneralImpl.cargarEntidadPorID(Curso.class, Short.parseShort(String.valueOf(datosBusquedaCarrera.getIdCurso())));
                Horario horario = this.servicioGeneralImpl.cargarEntidadPorID(Horario.class, datosBusquedaCarrera.getIdHorario());
                this.listaHorarioAsignacion.add(horario);
                for(int i=0;i<listaCurso.size();i++){
                    if(curso.getIdCurso() == listaCurso.get(i).getIdCurso())
                        listaCurso.remove(i);
                }
                if(!listaCurso.isEmpty())
                    this.listaHorario = this.servicioHorarioImpl.getHorario(this.listaCurso.get(0), semestre);
                else
                    this.listaHorario.clear();
            }
        } catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, null, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }
        modelo.addAttribute("listaCurso", this.listaCurso);
        modelo.addAttribute("listaHorario",this.listaHorario);
        modelo.addAttribute("horarioElegido", true);
        modelo.addAttribute("listadoHorarioAsignados", this.listaHorarioAsignacion);
        return "asignacion/asignacionCursos2";
    }
//  _____________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina <code>asignacionCursos2.htm</code> por medio de AJAX.
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
    @RequestMapping(value="realizarAsignacion.htm",method = RequestMethod.GET)
    public @ResponseBody String realizarAsignacion( Model modelo,
                                                    HttpServletRequest request) throws IOException {
        modelo.addAttribute("listaCurso", this.listaCurso);
        modelo.addAttribute("listaHorario",this.listaHorario);
        modelo.addAttribute("horarioElegido", true);
        modelo.addAttribute("listadoHorarioAsignados", this.listaHorarioAsignacion);
        AsignacionCursoPensum acp;
        //Validando traslape de cursos
        try{
            if(this.servicioHorarioImpl.existeTraslape(listaHorarioAsignacion)){
                System.out.println("Error en traslape de cursos");
                RequestUtil.crearMensajeRespuesta(request, null, "miscursos.asignacionCursos.existeTraslape", false);
                return "asignacion/asignacionCursos2";
            }
            else{
           /*     for(Horario horario: listaHorarioAsignacion){
                    System.out.println("Curso: " + horario.getCurso().getNombre());
                    System.out.println("Seccion: " + horario.getSeccion());
                    //Validando prerrequisitos por curso
                    acp = servicioAsignacionCursoPensumImpl.getListadoAsignacionCursoPensum(horario.getCurso(),asignacionEstudianteCarrera.getEstudiante().getPensum()).get(0);
                    if(servicioCursoAprobadoImpl.getCursoPrerrequisitoPendiente(asignacionEstudianteCarrera, horario.getCurso()).isEmpty()
                       & servicioCursoAprobadoImpl.getCreditosAprobados(asignacionEstudianteCarrera)>=acp.getCreditosPrerrequisito()){
                        System.out.println("Error en prerrequisito de cursos");
                        RequestUtil.crearMensajeRespuesta(request, null, "miscursos.asignacionCursos.prerrequisitoPendiente", false);
                        return "asignacion/asignacionCursos2";
                    }
                    //Validando asignaciones en semestre actual
                    if(!servicioDetalleAsignacionImpl.getListadoDetalleAsignacion(horario.getCurso(), semestre, asignacionEstudianteCarrera).isEmpty()){
                        System.out.println("Error en asignaciòn de semestre actual");
                        RequestUtil.crearMensajeRespuesta(request, null, "miscursos.asignacionCursos.cursoYaAsignado", false);
                        return "asignacion/asignacionCursos2";
                    }
                }*/
                listaAsignacion = this.servicioAsignacionImpl.realizarAsignacionCursos
                                            (this.asignacionEstudianteCarrera, listaHorarioAsignacion);
                if (!listaAsignacion.isEmpty()){
                    RequestUtil.crearMensajeRespuesta(request, null, "dataAccessException", false);
                    this.enviarEmail(listaAsignacion);
                }
                else{
                    RequestUtil.crearMensajeRespuesta(request, null, "dataAccessException", false);
                    System.out.println("Error en realizar asigancion");
                }
            }
        }catch (Exception e) {
            // error de acceso a datos
            System.out.println("Error acceso a datos");
            RequestUtil.crearMensajeRespuesta(request, null, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }
        return "asignacion/asignacionCurso3";
    }
//  _____________________________________________________________________________
    @RequestMapping(value="asignacionCursos3.htm",method = RequestMethod.GET)
    public String mostrarAsignacion( Model modelo,HttpServletRequest request){
        modelo.addAttribute("asignacion",listaAsignacion.get(0).getAsignacion());
        modelo.addAttribute("listaAsignacion",listaAsignacion);
        return "asignacion/asignacionCursos3";
    }
//  _____________________________________________________________________________
    @RequestMapping(value="getHorario.htm", method=RequestMethod.GET)
    public @ResponseBody @JsonIgnore List<Horario> getHorario(@RequestParam Short idCurso,HttpServletRequest request) {
        Curso curso = null;
        try{
            curso = this.servicioGeneralImpl.cargarEntidadPorID(Curso.class, idCurso);
        } catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, null, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }
        return this.servicioHorarioImpl.getHorario(curso, this.semestre);
    }
//  _____________________________________________________________________________
    private void enviarEmail(List<DetalleAsignacion> listaAsignacion) throws IOException{

       /* String mensaje = "Estimado/a " + asignacionEstudianteCarrera.getEstudiante().getNombre() + ", \n\n"+
                         "La asignaci�n de cursos se ha realizado exit�samente: \n\n" +
                         "  - Estudiante: " + asignacionEstudianteCarrera.getEstudiante().getNombre() + "\n" +
                         "  - Carn�: " + asignacionEstudianteCarrera.getEstudiante().getCarne() + "\n" +
                         "  - Fecha: " + listaAsignacion.get(0).getAsignacion().getFecha() + "\n" +
                         "  - Transacci�n: " + listaAsignacion.get(0).getAsignacion().getTransaccion() + "\n\n" +
                         "";
        try {
            this.emailSender.setDestinatario(this.estudiante.getEmail());
            this.emailSender.setSubject("Boleta de Asignaci�n");
            this.emailSender.setMensaje(mensaje);
            // se trata de enviar el correo
            this.emailSender.enviarCorreo2();

        } catch (MessagingException ex) {            
            log.error(Mensajes.MESSAGING_EXCEPTION, ex);
        }*/
    }
}
