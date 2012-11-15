/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.controlador.detalleAsignacion;

import gt.edu.usac.cats.dominio.Catedratico;
import gt.edu.usac.cats.dominio.DetalleAsignacion;
import gt.edu.usac.cats.dominio.Horario;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.dominio.busqueda.DatosIngresoNota;
import gt.edu.usac.cats.dominio.wrapper.WrapperIngresoNota;
import gt.edu.usac.cats.enums.TipoActividad;
import gt.edu.usac.cats.enums.TipoHorario;
import gt.edu.usac.cats.servicio.ServicioAsignacionCatedraticoHorario;
import gt.edu.usac.cats.servicio.ServicioCalendarioActividades;
import gt.edu.usac.cats.servicio.ServicioHorario;
import gt.edu.usac.cats.servicio.ServicioSemestre;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Carlos Solorzano
 * @version 1.0
 */


@Controller
public class ControladorIngresoNotas extends ControladorAbstractoIngresoNota{
//______________________________________________________________________________
    private static Logger log = Logger.getLogger(ControladorIngresoNotas.class);
//______________________________________________________________________________
    private Catedratico catedratico;
//______________________________________________________________________________
    private Semestre semestre;
//______________________________________________________________________________
    private List<Horario> listadoHorario;
//______________________________________________________________________________
    private boolean esAdministrativo = false;
//______________________________________________________________________________
    @Resource
    private ServicioCalendarioActividades servicioCalendarioActividadesImpl;
//______________________________________________________________________________
    @Resource
    private ServicioSemestre servicioSemestreImpl;
//______________________________________________________________________________
    @Resource
    private ServicioHorario servicioHorarioImpl;
//______________________________________________________________________________
    @Resource
    private ServicioAsignacionCatedraticoHorario servicioAsignacionCatedraticoHorarioImpl;
//______________________________________________________________________________
     /**
     * <p>Metodo que intercepta la peticion GET de la pagina ingresoNota.htm. 
      *Se encarga de validar: 
      * <li>Usuario logueado sea un catedratico</li>
      * <li>Periodo valido de ingreso de notas</li>
      * <li>Mostrar el listado de cursos a los que el catedratico se encuentra
      * asignado en el semestre actual</li>
      * </p>
     */
    @RequestMapping(value="ingresoNota.htm",method=RequestMethod.GET)
    public String buscarEstudiantes(Model modelo, HttpServletRequest request) {

        this.esAdministrativo = false;
        try{
            this.semestre = this.servicioSemestreImpl.getSemestreActivo();

            //Buscando usuario logueado por nombre
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            super.usuario = super.servicioUsuarioImpl.cargarUsuarioPorNombre(auth.getName().toString());

            //Validando que el usuario se haya encontrado y sea un catedratico
            if (super.usuario != null & super.usuario.getCatedraticos().toArray().length == 0) {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "ingresoNota.noEsCatedratico", false);
                RequestUtil.agregarRedirect(request, "index.htm");
                return "detalleAsignacion/ingresoNota";
            }

            this.catedratico = (Catedratico) super.usuario.getCatedraticos().toArray()[0];

            //Validar periodo de ingreso de notas
            if (!this.servicioCalendarioActividadesImpl.esFechaActividadValida(TipoActividad.INGRESO_NOTAS,
                                                                        this.semestre,
                                                                        new java.util.Date())){
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "ingresoNota.fueraPeriodo", false);
                RequestUtil.agregarRedirect(request, "index.htm");
                return "detalleAsignacion/ingresoNota";
            }

            //Validar cursos asignados a catedratico
            if(this.servicioAsignacionCatedraticoHorarioImpl.getAsignacionCatedraticoHorario(semestre, catedratico).isEmpty()){
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "ingresoNota.catedraticoSinCursos", false);
                RequestUtil.agregarRedirect(request, "index.htm");
                return "detalleAsignacion/ingresoNota";
            }

            this.listadoHorario = this.servicioHorarioImpl.getHorario(semestre, catedratico,
                                        TipoHorario.values()[0]);

        }
        catch(Exception ex){
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, ex);
        }

        this.setModelo(modelo,false, new DatosIngresoNota());
        return "detalleAsignacion/ingresoNota";
    }

//______________________________________________________________________________
     /**
     * <p>Metodo que intercepta la peticion GET de la pagina ingresoNota.htm.
      *Se encarga de validar:
      * <li>Usuario logueado sea un catedratico</li>
      * <li>Periodo valido de ingreso de notas</li>
      * <li>Mostrar el listado de cursos a los que el catedratico se encuentra
      * asignado en el semestre actual</li>
      * </p>
     */
    @RequestMapping(value="ingresoNotaAdmin.htm",method=RequestMethod.GET)
    public String getIngresoNotaAdmin(Model modelo, HttpServletRequest request) {

        this.esAdministrativo = true;
        try{
            this.semestre = this.servicioSemestreImpl.getSemestreActivo();
            this.listadoHorario = this.servicioHorarioImpl.getHorario(this.semestre,TipoHorario.values()[0]);

        }
        catch(Exception ex){
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, ex);
        }

        this.setModelo(modelo,false, new DatosIngresoNota());
        return "detalleAsignacion/ingresoNota";
    }

//______________________________________________________________________________
    /**
     * <p>Metodo que recibe las peticiones POST de la pagina ingresoNota.htm.
     * Se encarga de buscar los estudiantes asignados al horario seleccionado</p>
     */
    @RequestMapping(value="ingresoNota.htm",method=RequestMethod.POST)
    public String crearFormulario(@Valid DatosIngresoNota datosIngresoNota, BindingResult bindingResult,
            Model modelo, HttpServletRequest request) {
        
        try{
            datosIngresoNota.setHorario(this.servicioHorarioImpl.cargarEntidadPorID(Horario.class, datosIngresoNota.getHorario().getIdHorario()));
            super.listadoDetalleAsignacion = super.servicioDetalleAsignacionImpl.
                    getListadoDetalleAsignacion(datosIngresoNota.getHorario().getIdHorario());
        }
        catch (Exception ex){
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, ex);
        }

        modelo.addAttribute("wrapperIngresoNota", new WrapperIngresoNota());


        if(!super.listadoDetalleAsignacion.isEmpty())
            modelo.addAttribute("nombreCurso",super.listadoDetalleAsignacion.get(0).getHorario().getAsignacionCursoPensum().getCurso().getNombre());
        
        this.setModelo(modelo, true, datosIngresoNota);        
        
        return "detalleAsignacion/ingresoNota";
    }
//______________________________________________________________________________
    /**
     * Metodo encargado de procesar el ingreso de notas
     */

    @RequestMapping(value="guardarOficializar.htm",method=RequestMethod.POST)
    public String metodoPost(@Valid WrapperIngresoNota wrapperIngresoNota, BindingResult bindingResult,
            Model modelo, HttpServletRequest request) {

        this.setModelo(modelo, false, new DatosIngresoNota());
        try{
            int i=0;
            for(DetalleAsignacion detAsign : super.listadoDetalleAsignacion){
                detAsign.setZona(Short.valueOf(wrapperIngresoNota.getListZona().get(i).toString()));
                detAsign.setLaboratorio(Short.valueOf(wrapperIngresoNota.getListLaboratorio().get(i).toString()));
                detAsign.setExamenFinal(Short.valueOf(wrapperIngresoNota.getListFinal().get(i).toString()));
                detAsign.setOficializado(wrapperIngresoNota.getOficializar());
                super.servicioDetalleAsignacionImpl.actualizar(detAsign);
                i++;
            }
            if(wrapperIngresoNota.getOficializar())
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "ingresoNota.oficializadas", true);
            else
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "ingresoNota.guardadas", true);

        }
        catch(Exception ex){
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, ex);
        }

        if(this.esAdministrativo)
            RequestUtil.agregarRedirect(request, "ingresoNotaAdmin.htm");
        else
            RequestUtil.agregarRedirect(request, "ingresoNota.htm");
        return "detalleAsignacion/ingresoNota";
    }
//  _____________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina <code>getHorarioCatedratico.htm</code>. El metodo se encarga
     * de inicializar las listas de horario en base a los parametros enviados.
     *
     * @param idTipoHorario tipo de horario a buscar
     * @return List<Horario> listado filtrado en base a los parametros enviados.
     */
    @RequestMapping(value = "getHorarioCatedratico.htm", method = RequestMethod.GET)
    public @ResponseBody String getHorario(@RequestParam String idTipoHorario,
                                                    HttpServletRequest request) {
        String strOptions = "";
        try {
            TipoHorario tipoHorario = TipoHorario.valueOf(idTipoHorario);
            if(this.esAdministrativo)
                this.listadoHorario = this.servicioHorarioImpl.getHorario(this.semestre, tipoHorario);
            else
                this.listadoHorario = this.servicioHorarioImpl.getHorario(this.semestre, this.catedratico,
                                    tipoHorario);
            for (Horario horario : this.listadoHorario){
                strOptions += "<option value=\"" + horario.getIdHorario() + "\">" +
                        horario.getAsignacionCursoPensum().getCurso().getNombre() + " - " + horario.getSeccion() +
                        "</option>";
            }
        } catch (Exception e) {
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }
        return strOptions;
    }
//  _____________________________________________________________________________
    /**
     * Metodo encargado de inicializar el modelo enviado como parametro
     *
     * @param modelo
     * @param mostrarAsignaciones
     */
    private void setModelo(Model modelo, boolean mostrarAsignaciones, DatosIngresoNota datosIngresoNota){

        modelo.addAttribute("datosIngresoNota", datosIngresoNota);
        modelo.addAttribute("listaTipoHorario",TipoHorario.values());
        modelo.addAttribute("listaHorario",this.listadoHorario);
        modelo.addAttribute("listadoDetalleAsignacion", super.listadoDetalleAsignacion);
        modelo.addAttribute("mostrarEstudiantes", mostrarAsignaciones);
        modelo.addAttribute("validacionesOK", true);
        modelo.addAttribute("esAdministrativo", this.esAdministrativo);
        
        if(mostrarAsignaciones){
            modelo.addAttribute("limiteZona", datosIngresoNota.getHorario().getAsignacionCursoPensum().getZona());
            modelo.addAttribute("limiteExamenFinal", datosIngresoNota.getHorario().getAsignacionCursoPensum().getExamenFinal());
        }
        else{
            modelo.addAttribute("limiteZona", 0);
            modelo.addAttribute("limiteExamenFinal",0);
        }
    }

}
