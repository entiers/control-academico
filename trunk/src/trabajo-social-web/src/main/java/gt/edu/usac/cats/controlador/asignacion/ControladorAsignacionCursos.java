/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.controlador.asignacion;

import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.dominio.busqueda.DatosAsignacion;
import gt.edu.usac.cats.enums.TipoActividad;
import gt.edu.usac.cats.enums.TipoAsignacion;
import gt.edu.usac.cats.enums.TipoHorario;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author cats
 */
@Controller("controladorAsignacionCursos")
@RequestMapping(value="asignacionCursos.htm")
public class ControladorAsignacionCursos extends ControladorAbstractoAsignacion{

//______________________________________________________________________________
    private static Logger log = Logger.getLogger(ControladorAsignacionCursos.class);
//_____________________________________________________________________________
    private static final String TITULO_MENSAJE = "miscursos.asignacionCursos.titulo";
//  _____________________________________________________________________________
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
    @RequestMapping(method = RequestMethod.GET)
    public String getAsignacionCursos(Model modelo, HttpServletRequest request) {
        try {
            DatosAsignacion datosAsignacion = new DatosAsignacion();

            //Obteniendo curso de computacion
            //this.cursoComputacion = this.servicioGeneralImpl.cargarEntidadPorID(Curso.class, 38);

            //Obteniendo semestre actual
            this.semestre = (Semestre) this.servicioSemestreImpl.getSemestreActivo();

            //Buscando usuario logueado por nombre
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            this.usuario = this.servicioUsuarioImpl.cargarUsuarioPorNombre(auth.getName().toString());

            //Validando que el usuario se haya encontrado y sea un estudiante
            if (this.usuario != null & this.usuario.getEstudiantes().toArray().length > 0) {
                this.estudiante = (Estudiante) this.usuario.getEstudiantes().toArray()[0];
            } else {
                modelo.addAttribute("errorEntidad", true);
                return "asignacion/asignacionCursos";
            }

            //Validando que el estudiante se encuentre inscrito
            if (!this.estudiante.isInscrito()) {
                modelo.addAttribute("estudianteNoInscrito", true);
                return "asignacion/asignacionCursos";
            }

            //Validar curso de computacion aprobado
            /*if(!servicioCursoAprobadoImpl.esCursoAprobado(asignacionEstudianteCarrera, cursoComputacion)){
            modelo.addAttribute("cursoComputacion",true);
            return "asignacion/asignacionCursos";
            }*/

            //Validar periodo de asignacion de cursos de semestre
            if (this.servicioCalendarioActividadesImpl.esFechaActividadValida(TipoActividad.ASIGNACION_SEMESTRE,
                                                                    this.semestre,
                                                                    new java.util.Date()))
                datosAsignacion.setTipoAsignacion(TipoAsignacion.ASIGNACION_CURSOS_SEMESTRE);
            //Validar periodo de asignacion de cursos de vacaciones
            else if(this.servicioCalendarioActividadesImpl.esFechaActividadValida(TipoActividad.ASIGNACION_VACACIONES,
                                                                    semestre,
                                                                    new java.util.Date()))
                datosAsignacion.setTipoAsignacion(TipoAsignacion.ASIGNACION_CURSOS_VACACIONES);
            //Validar periodo de asignacion de cursos de primera retrasada
            else if(this.servicioCalendarioActividadesImpl.esFechaActividadValida(TipoActividad.ASIGNACION_PRIMERA_RESTRASADA,
                                                                    semestre,
                                                                    new java.util.Date()))
                datosAsignacion.setTipoAsignacion(TipoAsignacion.ASIGNACION_PRIMERA_RETRASADA);
            //Validar periodo de asignacion de cursos de segunda retrasada
            else if(this.servicioCalendarioActividadesImpl.esFechaActividadValida(TipoActividad.ASIGNACION_SEGUNDA_RETRASADA,
                                                                    semestre,
                                                                    new java.util.Date()))
                datosAsignacion.setTipoAsignacion(TipoAsignacion.ASIGNACION_SEGUNDA_RETRASADA);
            else {
                modelo.addAttribute("periodoInvalido", true);
                return "asignacion/asignacionCursos";
            }

            this.listaAEC = this.servicioAsignacionEstudianteCarreraImpl.getAsignacionEstudianteCarreraPorEstudiante(estudiante);
            modelo.addAttribute("listaAEC", this.listaAEC);
            modelo.addAttribute("datosAsignacion", datosAsignacion);
        } catch (Exception e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }
        return "asignacion/asignacionCursos";
    }

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
    @RequestMapping(value = "asignacionCursos.htm", method = RequestMethod.POST)
    public String postAsignacionCursos(@Valid DatosAsignacion datosAsignacion,
                                        BindingResult bindingResult,
                                        Model modelo,
                                        HttpServletRequest request) {
        String retorno = "redirect:index.htm";
        try {
            this.asignacionEstudianteCarrera = this.servicioAsignacionEstudianteCarreraImpl.cargarEntidadPorID(AsignacionEstudianteCarrera.class, datosAsignacion.getIdAsignacionEstudianteCarrera());

            if(datosAsignacion.getTipoAsignacion()==TipoAsignacion.ASIGNACION_CURSOS_SEMESTRE){
                this.listaCurso = this.servicioCursoImpl.getCursoAsignacion(this.asignacionEstudianteCarrera.getCarrera(),
                                                                            this.semestre,TipoHorario.SEMESTRE);
                this.listaHorario = this.servicioHorarioImpl.getHorario(this.listaCurso.get(0), this.semestre, TipoHorario.SEMESTRE);
                retorno = "asignacion/asignacionSemestre";
            }
            else if(datosAsignacion.getTipoAsignacion() == TipoAsignacion.ASIGNACION_CURSOS_VACACIONES){
                this.listaCurso = this.servicioCursoImpl.getCursoAsignacion(this.asignacionEstudianteCarrera.getCarrera(),
                                                                            this.semestre,TipoHorario.VACACIONES);
                this.listaHorario = this.servicioHorarioImpl.getHorario(this.listaCurso.get(0), this.semestre, TipoHorario.VACACIONES);
                retorno = "asignacion/asignacionVacaciones";
            }
            else if(datosAsignacion.getTipoAsignacion() == TipoAsignacion.ASIGNACION_PRIMERA_RETRASADA){
                this.listaCurso = this.servicioCursoImpl.getCursoAsignacion(this.asignacionEstudianteCarrera.getCarrera(),
                                                                            this.semestre,TipoHorario.PRIMERA_RETRASADA);
                this.listaHorario = this.servicioHorarioImpl.getHorario(this.listaCurso.get(0), this.semestre, TipoHorario.PRIMERA_RETRASADA);
                retorno = "asignacion/asignacionRetrasadas";
            }
            else if(datosAsignacion.getTipoAsignacion() == TipoAsignacion.ASIGNACION_SEGUNDA_RETRASADA){
                this.listaCurso = this.servicioCursoImpl.getCursoAsignacion(this.asignacionEstudianteCarrera.getCarrera(),
                                                                            this.semestre,TipoHorario.SEGUNDA_RETRASADA);
                this.listaHorario = this.servicioHorarioImpl.getHorario(this.listaCurso.get(0), this.semestre, TipoHorario.SEGUNDA_RETRASADA);
                retorno = "asignacion/asignacionRetrasadas";
            }

            datosAsignacion.setTotalCursos(0);
            
            modelo.addAttribute("datosAsignacion", datosAsignacion);
            modelo.addAttribute("listaCurso", this.listaCurso);
            modelo.addAttribute("listaHorario", this.listaHorario);
        } catch (DataAccessException e) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }
        return retorno;
    }   

}
