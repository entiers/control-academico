/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */


package gt.edu.usac.cats.controlador.asignacion;


import gt.edu.usac.cats.dominio.Desasignacion;
import gt.edu.usac.cats.dominio.DetalleAsignacion;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.Horario;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.dominio.Usuario;
import gt.edu.usac.cats.dominio.busqueda.DatosAsignacion;
import gt.edu.usac.cats.enums.TipoActividad;
import gt.edu.usac.cats.enums.TipoAsignacion;
import gt.edu.usac.cats.servicio.ServicioCalendarioActividades;
import gt.edu.usac.cats.servicio.ServicioDetalleAsignacion;
import gt.edu.usac.cats.servicio.ServicioGeneral;
import gt.edu.usac.cats.servicio.ServicioSemestre;
import gt.edu.usac.cats.servicio.ServicioUsuario;
import gt.edu.usac.cats.util.EmailSenderVelocity;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import gt.edu.usac.cats.velocity.FabricaTemplateVelocity;
import gt.edu.usac.cats.velocity.contexto.DesasignacionEstudianteCurso;
import gt.edu.usac.cats.velocity.contexto.extras.HorarioCurso;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
@Controller("controladorDesAsignacionCursos")
@RequestMapping(value="desAsignacionCursos.htm")
@Scope(value = WebApplicationContext.SCOPE_SESSION)
@SessionAttributes(value={"semestre", "estudiante"}) 
public class ControladorDesAsignacionCursos implements Serializable{
//______________________________________________________________________________
    private static Logger log = Logger.getLogger(ControladorDesAsignacionCursos.class);
//______________________________________________________________________________
    private static final String TITULO_MENSAJE = "miscursos.desAsignacionCursos.titulo";
//  ____________________________________________________________________________
    private Semestre semestre;
//  ____________________________________________________________________________
    private Estudiante estudiante;
//  ____________________________________________________________________________
    @Resource
    private ServicioDetalleAsignacion servicioDetalleAsignacionImpl;
//  ____________________________________________________________________________
    @Resource
    private ServicioSemestre servicioSemestreImpl;
//  ____________________________________________________________________________
    @Resource
    private ServicioUsuario servicioUsuarioImpl;
//_____________________________________________________________________________
    @Resource
    private ServicioCalendarioActividades servicioCalendarioActividadesImpl;
//_____________________________________________________________________________
    @Resource
    private ServicioGeneral servicioGeneralImpl;
//_____________________________________________________________________________
    @Resource
    private EmailSenderVelocity emailSenderVelocity;
//  ____________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina <code>asignacionCursos.htm</code>. El metodo se encarga
     * de validar. Asi mismo se encarga de iniciar los objetos que se usaran en
     * la pagina.</p>
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(method=RequestMethod.GET)
    public String getDesAsignacionCursos(Model modelo,
                                        HttpServletRequest request){

        try{
            //Cargar semestre activo
            this.semestre = this.servicioSemestreImpl.getSemestreActivo();

            //Validar periodo de des asignacion de cursos
            if (!this.servicioCalendarioActividadesImpl.esFechaActividadValida(TipoActividad.DESASIGNACION_CURSOS,
                                                                    this.semestre,
                                                                    new java.util.Date())){
                modelo.addAttribute("periodoInvalido", true);
                return "asignacion/desAsignacionCursos";
            }

            //Buscando usuario logueado por nombre
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = this.servicioUsuarioImpl.cargarUsuarioPorNombre(auth.getName().toString());

            //Validando que el usuario se haya encontrado y sea un estudiante
            if (usuario != null & usuario.getEstudiantes().toArray().length > 0) {
                this.estudiante = (Estudiante) usuario.getEstudiantes().toArray()[0];
            } else {
                modelo.addAttribute("errorEntidad", true);
                return "asignacion/desAsignacionCursos";
            }

            //Cargar listado de asignaciones en semestre actual
            this.setModelo(modelo);
            modelo.addAttribute("datosAsignacion", new DatosAsignacion());
            
        }
        catch (Exception e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }
        return "asignacion/desAsignacionCursos";
    }

    //  ____________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina <code>asignacionCursos.htm</code>. El metodo se encarga
     * de validar. Asi mismo se encarga de iniciar los objetos que se usaran en
     * la pagina.</p>
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(method=RequestMethod.POST)
    public String realizarDesAsignacionCursos(@Valid DatosAsignacion datosAsignacion,
                                            BindingResult bindingResult,
                                            Model modelo,
                                            HttpServletRequest request){
        DetalleAsignacion detAsign;
        List<Desasignacion> listaDesasignacion = new ArrayList<Desasignacion>();

        try{
            //Cargando modelo
            this.setModelo(modelo);
            modelo.addAttribute("datosAsignacion", datosAsignacion);

            //Validando que se hayan seleccionado cursos
            if(datosAsignacion.getDetalleAsignacion()!=null){
                //Crear desasignaciones
                for(Object idDetalleAsignacion: datosAsignacion.getDetalleAsignacion()){
                    detAsign = this.servicioGeneralImpl.cargarEntidadPorID(DetalleAsignacion.class,
                                    Integer.valueOf(idDetalleAsignacion.toString()));
                    Desasignacion desasignacion = new Desasignacion();
                    desasignacion.setDetalleAsignacion(detAsign);
                    desasignacion.setObservacion("Desasignación de cursos desde el sistema.");
                    this.servicioGeneralImpl.agregar(desasignacion);
                    listaDesasignacion.add(desasignacion);
                }

                //Enviar correo de confirmacion
                this.enviarEmail(listaDesasignacion);
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "miscursos.desAsignacionCursos.exito", true);
                RequestUtil.agregarRedirect(request, "index.htm");
            }
            else {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "miscursos.desAsignacionCursos.sinHorarioSeleccionado", false);
            }
        }
        catch (Exception e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }
        return "asignacion/desAsignacionCursos";
    }

    private void setModelo(Model modelo){
        List<DetalleAsignacion> listaDetalleAsignacion = servicioDetalleAsignacionImpl
                                        .getListadoDetalleAsignacion(this.semestre,this.estudiante,
                                            TipoAsignacion.ASIGNACION_CURSOS_SEMESTRE
                                        );
        //Agregando objetos al modelo
        modelo.addAttribute("totalAsignaciones", listaDetalleAsignacion.size());
        modelo.addAttribute("listadoDetalleAsignacion", listaDetalleAsignacion);

    }

    //_____________________________________________________________________________
    protected void enviarEmail(List<Desasignacion> listaDesasignacion) throws IOException {

        DesasignacionEstudianteCurso dec = new DesasignacionEstudianteCurso();

        dec.setCarnet(this.estudiante.getCarne());
        dec.setNombre(this.estudiante.getNombre());

        List <HorarioCurso> listadoHorarioCursos = new ArrayList();

        for(Desasignacion desasignacion : listaDesasignacion){
            HorarioCurso horarioCurso = new HorarioCurso();
            
            Horario horario = desasignacion.getDetalleAsignacion().getHorario();
            horarioCurso.setCodigo(horario.getAsignacionCursoPensum().getCurso().getCodigo());
            horarioCurso.setNombre(horario.getAsignacionCursoPensum().getCurso().getNombre());
            horarioCurso.setSeccion(horario.getSeccion());

            listadoHorarioCursos.add(horarioCurso);
        }

        dec.setListadoHorarioCursos(listadoHorarioCursos);

        
        try {
            // se trata de enviar el correo
            this.emailSenderVelocity.enviarCorreo(
                    "Confirmacion de desasignacion de cursos",
                    this.estudiante.getEmail(),
                    FabricaTemplateVelocity.DESASIGNACION_ESTUDIANTE_CURSO,
                    dec);

        } catch (MessagingException ex) {
            log.error(Mensajes.MESSAGING_EXCEPTION, ex);
        }
    }

}
