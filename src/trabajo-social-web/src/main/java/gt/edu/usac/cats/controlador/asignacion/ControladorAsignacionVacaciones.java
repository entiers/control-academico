/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.controlador.asignacion;

import gt.edu.usac.cats.dominio.AsignacionCursoPensum;
import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.DetalleAsignacion;
import gt.edu.usac.cats.dominio.Horario;
import gt.edu.usac.cats.dominio.Pensum;
import gt.edu.usac.cats.dominio.busqueda.DatosAsignacion;
import gt.edu.usac.cats.enums.TipoAsignacion;
import gt.edu.usac.cats.enums.TipoHorario;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Esta clase es el controlador que se encarga de la llamada al proceso de asignacion
 * de cursos de vacaciones de estudiante de reingreso
 * 
 * @author Carlos Solorzano
 * @version 1.0
 */
@Controller("ControladorAsignacionVacaciones")
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class ControladorAsignacionVacaciones extends ControladorAbstractoAsignacion {
//______________________________________________________________________________
    private static Logger log = Logger.getLogger(ControladorAsignacionVacaciones.class);
//_______________________________________________________________________________
    private static final String TITULO_MENSAJE = "miscursos.asignacionCursos.titulo";
//_______________________________________________________________________________
    private List<DetalleAsignacion> listaAsignacion;
//_______________________________________________________________________________
    private List<Horario> listaHorarioAsignacion;
//_______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * POST de la pagina <code>asignacionSemestre.htm</code>. El metodo se encarga
     * de cargar la lista de Horarios seleccionados para asignacion, asi como de
     * eliminar el curso y el horario seleccionados de la lista disponible.
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     * @return String Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(value = "agregarHorarioAsignacionVacaciones.htm", method = RequestMethod.POST)
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
                listaAsignacionCursoPensum = servicioCursoImpl.getCursoAsignacion(pensumEstudianteCarrera.getPensum(),
                                                                    semestre,TipoHorario.VACACIONES);
            }

            //Validar que el estudiante no se asigne mas de dos cursos en vacaciones
            if(listaHorarioAsignacion.size()==2){
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "miscursos.asignacionCursos.vacaciones.limiteCursos", false);
                this.cargarModelo(modelo);
                return "asignacion/asignacionVacaciones";
            }
            else{
                //Cargar horario seleccionado al listadoHorarioAsignacion
                if (!listaAsignacionCursoPensum.isEmpty()) {
                    AsignacionCursoPensum asignacionCursoPensum = servicioGeneralImpl.
                            cargarEntidadPorID(AsignacionCursoPensum.class, Short.parseShort(String.valueOf(datosAsignacion.getIdAsignacionCursoPensum())));
                    Horario horario = servicioGeneralImpl.cargarEntidadPorID(Horario.class, datosAsignacion.getIdHorario());
                    listaHorarioAsignacion.add(horario);
                    datosAsignacion.incrementarTotalCursos();
                    for (int i = 0; i < listaAsignacionCursoPensum.size(); i++) {
                        if (listaAsignacionCursoPensum.get(i).getIdAsignacionCursoPensum()==asignacionCursoPensum.getIdAsignacionCursoPensum()) {
                            listaAsignacionCursoPensum.remove(i);
                        }
                    }
                    if (!listaAsignacionCursoPensum.isEmpty()) {
                        listaHorario = servicioHorarioImpl.getHorario(listaAsignacionCursoPensum.get(0), semestre, TipoHorario.VACACIONES);
                    } else {
                        listaHorario.clear();
                    }
                }
            }
        } catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request,TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }
        modelo.addAttribute("datosAsignacion", datosAsignacion);
        this.cargarModelo(modelo);
        return "asignacion/asignacionVacaciones";
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
    @RequestMapping(value = "realizarAsignacionVacaciones.htm", method = RequestMethod.POST)
    public String realizarAsignacion(@Valid DatosAsignacion datosAsignacion,
                                    BindingResult bindingResult,
                                    Model modelo,
                                    HttpServletRequest request) throws IOException {

        this.cargarModelo(modelo);
        
        //Validando traslape de cursos
        try {
            if (servicioHorarioImpl.existeTraslape(listaHorarioAsignacion)) {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "miscursos.asignacionCursos.existeTraslape", false);
                return "asignacion/asignacionVacaciones";
            }

            asignacionEstudianteCarrera = servicioGeneralImpl.cargarEntidadPorID(AsignacionEstudianteCarrera.class, datosAsignacion.getIdAsignacionEstudianteCarrera());
            Pensum pensum = this.servicioPensumEstudianteCarrera.getPensumEstudianteCarreraValido(
                asignacionEstudianteCarrera).getPensum();
            if (pensum != null) {
                //Validando que no tenga asignaciones previas y no exceda las permitidas
                List<DetalleAsignacion> listaDetAsigVacaciones = servicioDetalleAsignacionImpl.getListadoDetalleAsignacion(semestre, asignacionEstudianteCarrera, TipoAsignacion.ASIGNACION_CURSOS_VACACIONES);
                if (!listaDetAsigVacaciones.isEmpty()){
                    if(listaHorarioAsignacion.size() + listaDetAsigVacaciones.size() > 2){
                        RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "miscursos.asignacionCursos.vacaciones.limiteCursos", false);
                        this.cargarModelo(modelo);
                        return "asignacion/asignacionVacaciones";
                    }
                }
                for (Horario horario : this.listaHorarioAsignacion) {
                    //Validar zona minima
                    if(!servicioDetalleAsignacionImpl.tieneZonaMinima(horario.getAsignacionCursoPensum(), asignacionEstudianteCarrera)){
                        RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "miscursos.asignacionCursos.vacaciones.sinZonaMinima", false);
                        return "asignacion/asignacionVacaciones";
                    }

                    //Validando asignaciones en vacaciones
                    if (!servicioDetalleAsignacionImpl.getListadoDetalleAsignacion(horario.getAsignacionCursoPensum(), semestre, asignacionEstudianteCarrera,TipoAsignacion.ASIGNACION_CURSOS_VACACIONES).isEmpty()) {
                        RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "miscursos.asignacionCursos.cursoYaAsignado", false);
                        return "asignacion/asignacionVacaciones";
                    }

                    //Validando total de asignaciones por curso
                    if(servicioDetalleAsignacionImpl.getTotalAsignaciones(horario.getAsignacionCursoPensum(),
                               asignacionEstudianteCarrera, TipoAsignacion.ASIGNACION_CURSOS_VACACIONES)>=3){
                        RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "miscursos.asignacionCursos.totalAsignacionesExcedidas", false);
                        return "asignacion/asignacionVacaciones";
                    }

                    //Validando que el curso este pagado
                    if(servicioBoletaBancoImpl.listadoBoletaBanco(asignacionEstudianteCarrera.getEstudiante(),
                                                                horario.getAsignacionCursoPensum(), semestre, datosAsignacion.getTipoRubro()).isEmpty()){
                        RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "miscursos.asignacionCursos.cursoNoCancelado", false);
                        return "asignacion/asignacionVacaciones";
                    }
                }
            } else {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "miscursos.asignacionCursos.estudianteSinPensum", false);
                return "asignacion/asignacionVacaciones";
            }
            this.listaAsignacion = servicioAsignacionImpl.realizarAsignacionCursos(this.asignacionEstudianteCarrera, this.listaHorarioAsignacion,datosAsignacion.getTipoAsignacion());
            if (!this.listaAsignacion.isEmpty()) {
                this.enviarEmail(this.listaAsignacion);
                listaHorarioAsignacion.clear();
                return "redirect:asignacionExitosa.htm?iascsvr=" + this.listaAsignacion.get(0).getAsignacion().getIdAsignacion();
            }
        } catch (Exception e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }
        modelo.addAttribute("datosAsignacion", datosAsignacion);
        return "asignacion/asignacionVacaciones";
    }
//  _____________________________________________________________________________
    private void cargarModelo(Model modelo){
                modelo.addAttribute("listaAsignacionCursoPensum", listaAsignacionCursoPensum);
        modelo.addAttribute("listaHorario", listaHorario);
        modelo.addAttribute("horarioElegido", true);
        modelo.addAttribute("listadoHorarioAsignados", this.listaHorarioAsignacion);
    }

}
