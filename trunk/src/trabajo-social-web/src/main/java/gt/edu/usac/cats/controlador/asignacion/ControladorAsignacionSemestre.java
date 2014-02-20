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
import gt.edu.usac.cats.dominio.PensumEstudianteCarrera;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.dominio.busqueda.DatosAsignacion;
import gt.edu.usac.cats.enums.TipoAsignacion;
import gt.edu.usac.cats.enums.TipoHorario;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;

/**
 * Esta clase es el controlador que se encarga de la llamada al proceso de asignacion
 * de cursos de semestre de estudiante de reingreso
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
@Controller
@Scope(value = WebApplicationContext.SCOPE_SESSION)
@SessionAttributes(value={"listaAsignacion", "listaHorarioAsignacion", "asignacionEstudianteCarrera", 
    "semestre", "pensumEstudianteCarrera", "listaHorario", "listaAsignacionCursoPensum"}) 
public class ControladorAsignacionSemestre extends ControladorAbstractoAsignacion implements Serializable{
//______________________________________________________________________________
    private static Logger log = Logger.getLogger(ControladorAsignacionSemestre.class);
//______________________________________________________________________________
    private static final String TITULO_MENSAJE = "miscursos.asignacionCursos.titulo";
//______________________________________________________________________________
    private List<DetalleAsignacion> listaAsignacion;
//______________________________________________________________________________
    private List<Horario> listaHorarioAsignacion;
//______________________________________________________________________________
    private AsignacionEstudianteCarrera asignacionEstudianteCarrera;
//______________________________________________________________________________
    private Semestre semestre;
//______________________________________________________________________________
    private List <Horario> listaHorario;
//______________________________________________________________________________
    private List<AsignacionCursoPensum> listaAsignacionCursoPensum;
//______________________________________________________________________________    
    private PensumEstudianteCarrera pensumEstudianteCarrera;
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
            pensumEstudianteCarrera = servicioPensumEstudianteCarrera.getPensumEstudianteCarreraValido(asignacionEstudianteCarrera);
            
            //Validar que sea el primer curso a asignar
            if(datosAsignacion.getTotalCursos()==0){
                this.listaHorarioAsignacion =  new ArrayList<Horario>();
                listaHorario = new ArrayList<Horario>();
                listaAsignacionCursoPensum = servicioCursoImpl
                        .getCursoAsignacion(pensumEstudianteCarrera.getPensum(),
                                                                    semestre,TipoHorario.SEMESTRE);
            }

            //Cargar horario seleccionado al listadoHorarioAsignacion
            if (!listaAsignacionCursoPensum.isEmpty()) {
                AsignacionCursoPensum asignacionCursoPensum = servicioGeneralImpl.
                        cargarEntidadPorID(AsignacionCursoPensum.class, 
                        Short.parseShort(String.valueOf(datosAsignacion.getIdAsignacionCursoPensum())));
                /*
                Horario horario = servicioGeneralImpl
                        .cargarEntidadPorID(Horario.class, datosAsignacion.getIdHorario());
                */
                
                System.out.println(datosAsignacion.getSeccion());
                listaHorarioAsignacion.addAll(this.servicioHorarioImpl.getHorariosPorSecciones(datosAsignacion.getSeccion(), 
                        asignacionCursoPensum, 
                        semestre, 
                        datosAsignacion.getTipoHorario()));
                
                datosAsignacion.incrementarTotalCursos();
                for (int i = 0; i < listaAsignacionCursoPensum.size(); i++) {
                    if (listaAsignacionCursoPensum.get(i).getIdAsignacionCursoPensum()==asignacionCursoPensum.getIdAsignacionCursoPensum()) {
                        listaAsignacionCursoPensum.remove(i);
                    }
                }
                if (!listaAsignacionCursoPensum.isEmpty()) {
                    listaHorario = servicioHorarioImpl
                            .getHorario(listaAsignacionCursoPensum.get(0), semestre, 
                            TipoHorario.SEMESTRE);
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
        
        try {

            //Validando traslape de cursos
            
            if (servicioHorarioImpl.existeTraslape(listaHorarioAsignacion)) {
                System.out.println("Existe traslape");
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "miscursos.asignacionCursos.existeTraslape", false);
                return "asignacion/asignacionSemestre";
            }
             asignacionEstudianteCarrera = servicioGeneralImpl.cargarEntidadPorID(AsignacionEstudianteCarrera.class, datosAsignacion.getIdAsignacionEstudianteCarrera());
             
           // validando traslape con cursos asignados en el mismo semestre pero en diferente transaccion
            List<Horario> listaHorariosOtrasTrx = servicioHorarioImpl.getHorariosAsignados(asignacionEstudianteCarrera, semestre, datosAsignacion.getTipoHorario());
            listaHorariosOtrasTrx.addAll(listaHorarioAsignacion);
            if (servicioHorarioImpl.existeTraslape(listaHorariosOtrasTrx)
                    & (datosAsignacion.getTipoHorario() == TipoHorario.SEMESTRE | datosAsignacion.getTipoHorario() == TipoHorario.VACACIONES)) {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "miscursos.asignacionCursos.existeTraslape", false);
                return "asignacion/asignacionSemestre";
            }

            asignacionEstudianteCarrera = servicioGeneralImpl.cargarEntidadPorID(AsignacionEstudianteCarrera.class, datosAsignacion.getIdAsignacionEstudianteCarrera());
            pensumEstudianteCarrera = this.servicioPensumEstudianteCarrera.getPensumEstudianteCarreraValido(
                asignacionEstudianteCarrera);

            if (pensumEstudianteCarrera != null) {
                for (Horario horario : this.listaHorarioAsignacion) {
                   //Validando prerrequisitos por curso
                    System.out.println("valida prerrequisito: "+horario.getIdHorario()+ "seccion "+horario.getSeccion());
                    acp = (AsignacionCursoPensum) servicioAsignacionCursoPensumImpl.getListadoAsignacionCursoPensum(horario.getAsignacionCursoPensum(), pensumEstudianteCarrera.getPensum()).get(0);
                    List<Curso> prerrequisitos = servicioCursoAprobadoImpl.getCursoPrerrequisitoPendiente(asignacionEstudianteCarrera, horario.getAsignacionCursoPensum());
//                    System.out.println("prerrequisitos: "+prerrequisitos.size());
                    int creditosAprob = servicioCursoAprobadoImpl.getCreditosAprobados(asignacionEstudianteCarrera);
                    System.out.println("Creditos Aprobados: "+creditosAprob);
                    System.out.println("Creditos prerrequisito: "+acp.getCreditosPrerrequisito());
                    System.out.println(" prerrequisitos: "+prerrequisitos.isEmpty());
                    
                    
//MC VALIDACION PRERREQUISITO                    
                    if (!prerrequisitos.isEmpty()
                            | creditosAprob < acp.getCreditosPrerrequisito()) {
                        RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "miscursos.asignacionCursos.prerrequisitoPendiente", false);
                        return "asignacion/asignacionSemestre";
                    }

                    //Validando asignaciones en semestre actual
                    if (!servicioDetalleAsignacionImpl.getListadoDetalleAsignacion(horario.getAsignacionCursoPensum(), semestre, asignacionEstudianteCarrera,TipoAsignacion.ASIGNACION_CURSOS_SEMESTRE).isEmpty()) {
                        RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "miscursos.asignacionCursos.cursoYaAsignado", false);
                        return "asignacion/asignacionSemestre";
                    }

                    //Validando total de asignaciones por curso
                    if(servicioDetalleAsignacionImpl.getTotalAsignaciones(horario.getAsignacionCursoPensum(),
                               asignacionEstudianteCarrera, TipoAsignacion.ASIGNACION_CURSOS_SEMESTRE)>=3){
                        RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "miscursos.asignacionCursos.totalAsignacionesExcedidas", false);
                        return "asignacion/asignacionSemestre";
                    }

                }
            } else {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "miscursos.asignacionCursos.estudianteSinPensum", false);
                return "asignacion/asignacionSemestre";
            }

            //Realizando asignacion de cursos
            this.listaAsignacion = servicioAsignacionImpl.realizarAsignacionCursos(this.asignacionEstudianteCarrera, this.listaHorarioAsignacion,datosAsignacion.getTipoAsignacion());
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
        return "asignacion/asignacionSemestre";
    }

//  _____________________________________________________________________________
    private void cargarModelo(Model modelo){
        modelo.addAttribute("listaAsignacionCursoPensum", listaAsignacionCursoPensum);
        modelo.addAttribute("listaHorario", listaHorario);
        modelo.addAttribute("horarioElegido", true);
        modelo.addAttribute("listadoHorarioAsignados", this.listaHorarioAsignacion);
    }
}
