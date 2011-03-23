/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */


package gt.edu.usac.cats.controlador.asignacion;

import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.Desasignacion;
import gt.edu.usac.cats.dominio.DetalleAsignacion;
import gt.edu.usac.cats.dominio.Estudiante;
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
import gt.edu.usac.cats.util.EmailSender;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.ws.BindingType;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
@Controller("controladorDesAsignacionCursos")
@RequestMapping(value="desAsignacionCursos.htm")
public class ControladorDesAsignacionCursos {
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
    private EmailSender emailSender;
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

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        String mensaje = "Estimado/a " + this.estudiante.getNombre() + ", <br/><br/>" +
                         "La desasignación de cursos se ha realizado exitosamente: <br/><br/>" +
                         "  - Estudiante: " + this.estudiante.getNombre() + "<br/>" +
                         "  - Carne: " + this.estudiante.getCarne() + "<br/>" +
                         "  - Fecha: " +  df.format(today) + "<br/><br/>" +
                         "Cursos desasignados:<br/>";

        mensaje+="<table align='center' width='50%'><thead><tr>" +
                      " <td width='25%'><b>Código</b></td>" +
                      " <td width='50%'><b>Curso</b></td>" +
                      " <td width='25%'><b>Sección</b></td>" +
                    "</tr></thead>";
        for(Desasignacion desasignacion : listaDesasignacion){
            mensaje+="<tr>" +
                      " <td>" + desasignacion.getDetalleAsignacion().getHorario().getCurso().getCodigo() + "</td>" +
                      " <td>" + desasignacion.getDetalleAsignacion().getHorario().getCurso().getNombre() + "</td>" +
                      " <td>" + desasignacion.getDetalleAsignacion().getHorario().getSeccion() + "</td>" +
                    "</tr>";
        }

        mensaje+="</table><br/><br/>Control Académico<br/>Escuela de Trabajo Social";
        try {
            // se trata de enviar el correo
            this.emailSender.enviarCorreo("Confirmación desasignación de cursos", this.estudiante.getEmail(), mensaje);

        } catch (MessagingException ex) {
            log.error(Mensajes.MESSAGING_EXCEPTION, ex);
        }
    }

}
