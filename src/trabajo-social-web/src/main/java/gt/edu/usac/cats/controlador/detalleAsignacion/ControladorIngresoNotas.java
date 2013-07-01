/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */
package gt.edu.usac.cats.controlador.detalleAsignacion;

import gt.edu.usac.cats.dominio.Catedratico;
import gt.edu.usac.cats.dominio.CursoAprobado;
import gt.edu.usac.cats.dominio.DetalleAsignacion;
import gt.edu.usac.cats.dominio.Horario;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.dominio.Usuario;
import gt.edu.usac.cats.dominio.busqueda.DatosBusquedaEstudiante;
import gt.edu.usac.cats.dominio.busqueda.DatosIngresoNota;
import gt.edu.usac.cats.dominio.wrapper.WrapperIngresoNota;
import gt.edu.usac.cats.enums.TipoActividad;
import gt.edu.usac.cats.enums.TipoHorario;
import gt.edu.usac.cats.servicio.ServicioAsignacionCatedraticoHorario;
import gt.edu.usac.cats.servicio.ServicioCalendarioActividades;
import gt.edu.usac.cats.servicio.ServicioCursoAprobado;
import gt.edu.usac.cats.servicio.ServicioHorario;
import gt.edu.usac.cats.servicio.ServicioSemestre;
import gt.edu.usac.cats.util.Mensajes;
import gt.edu.usac.cats.util.RequestUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
@Controller
@Scope(value = WebApplicationContext.SCOPE_SESSION)
@SessionAttributes(value = {"catedratico", "semestre", "listadoHorario", "esAdministrativo", "listadoDetalleAsignacion", "usuario", "horarioActual","excusasMap"})
public class ControladorIngresoNotas extends ControladorAbstractoIngresoNota implements Serializable {
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
    private List<DetalleAsignacion> listadoDetalleAsignacion;
//______________________________________________________________________________
    private Usuario usuario;
    
    private Horario horarioActual;
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
    @Resource
    private ServicioCursoAprobado servicioCursoAprobadoImpl;
//______________________________________________________________________________

    private HashMap<String, ArrayList<String>> excusasMap;
    
    /**
     * <p>Metodo que intercepta la peticion GET de la pagina ingresoNota.htm. Se
     * encarga de validar: <li>Usuario logueado sea un catedratico</li>
     * <li>Periodo valido de ingreso de notas</li> <li>Mostrar el listado de
     * cursos a los que el catedratico se encuentra asignado en el semestre
     * actual</li> </p>
     */
    @RequestMapping(value = "ingresoNota.htm", method = RequestMethod.GET)
    public String buscarEstudiantingresoes(Model modelo, HttpServletRequest request) {

        this.esAdministrativo = false;
        try {
            this.semestre = this.servicioSemestreImpl.getSemestreActivo();

            //Buscando usuario logueado por nombre
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            this.usuario = super.servicioUsuarioImpl.cargarUsuarioPorNombre(auth.getName().toString());

            //Validando que el usuario se haya encontrado y sea un catedratico
            if (this.usuario != null & this.usuario.getCatedraticos().toArray().length == 0) {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "ingresoNota.noEsCatedratico", false);
                RequestUtil.agregarRedirect(request, "index.htm");
                return "detalleAsignacion/ingresoNota";
            }

            this.catedratico = (Catedratico) this.usuario.getCatedraticos().toArray()[0];

            //Validar periodo de ingreso de notas
            if (!this.servicioCalendarioActividadesImpl.esFechaActividadValida(TipoActividad.INGRESO_NOTAS,
                    this.semestre,
                    new java.util.Date())) {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "ingresoNota.fueraPeriodo", false);
                RequestUtil.agregarRedirect(request, "index.htm");
                return "detalleAsignacion/ingresoNota";
            }

            //Validar cursos asignados a catedratico
            if (this.servicioAsignacionCatedraticoHorarioImpl.getAsignacionCatedraticoHorario(semestre, catedratico).isEmpty()) {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "ingresoNota.catedraticoSinCursos", false);
                RequestUtil.agregarRedirect(request, "index.htm");
                return "detalleAsignacion/ingresoNota";
            }

            this.listadoHorario = this.servicioHorarioImpl.getHorario(semestre, catedratico,
                    TipoHorario.values()[0]);
            excusasMap = new HashMap();
            ArrayList excusas =  new ArrayList();
            excusas.add("N/A");
            excusas.add("NSP");
            excusas.add("SDE");
           excusas.add("SZM");
            excusas.add("AC");
            excusasMap.put("listaEx", excusas);
        } catch (Exception ex) {
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, ex);
        }

        this.setModelo(modelo, false, new DatosIngresoNota());
        return "detalleAsignacion/ingresoNota";
    }

//______________________________________________________________________________
    /**
     * <p>Metodo que intercepta la peticion GET de la pagina ingresoNota.htm. Se
     * encarga de validar: <li>Usuario logueado sea un catedratico</li>
     * <li>Periodo valido de ingreso de notas</li> <li>Mostrar el listado de
     * cursos a los que el catedratico se encuentra asignado en el semestre
     * actual</li> </p>
     */
    @RequestMapping(value = "ingresoNotaAdmin.htm", method = RequestMethod.GET)
    public String getIngresoNotaAdmin(Model modelo, HttpServletRequest request) {

        this.esAdministrativo = true;
        try {
            this.semestre = this.servicioSemestreImpl.getSemestreActivo();
            this.listadoHorario = this.servicioHorarioImpl.getHorario(this.semestre, TipoHorario.values()[0]);

        } catch (Exception ex) {
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, ex);
        }

        this.setModelo(modelo, false, new DatosIngresoNota());

        return "detalleAsignacion/ingresoNota";
    }

//______________________________________________________________________________
    /**
     * <p>Metodo que recibe las peticiones POST de la pagina ingresoNota.htm. Se
     * encarga de buscar los estudiantes asignados al horario seleccionado</p>
     */
    @RequestMapping(value = "ingresoNota.htm", method = RequestMethod.POST)
    public String crearFormulario(@Valid DatosIngresoNota datosIngresoNota, BindingResult bindingResult,
            Model modelo, HttpServletRequest request) {

        try {
            datosIngresoNota.setHorario(this.servicioHorarioImpl.cargarEntidadPorID(Horario.class, datosIngresoNota.getHorario().getIdHorario()));
            this.listadoDetalleAsignacion = this.servicioDetalleAsignacionImpl.
                    getListadoDetalleAsignacion(datosIngresoNota.getHorario().getIdHorario());
            this.horarioActual = datosIngresoNota.getHorario();
        } catch (Exception ex) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, ex);
        }

        WrapperIngresoNota win = new WrapperIngresoNota();
        for (int i=0; i < 10; i++){
            win.getListExcusa().add("XXX");
        } 
        modelo.addAttribute("wrapperIngresoNota", new WrapperIngresoNota());


        if (listadoDetalleAsignacion!=null && !this.listadoDetalleAsignacion.isEmpty()) {
            modelo.addAttribute("nombreCurso", this.listadoDetalleAsignacion.get(0).getHorario().getAsignacionCursoPensum().getCurso().getNombre());
        }
        this.setModelo(modelo, true, datosIngresoNota);

        return "detalleAsignacion/ingresoNota";
    }
//______________________________________________________________________________

    /**
     * Metodo encargado de procesar el ingreso de notas
     */
    @RequestMapping(value = "guardarOficializar.htm", method = RequestMethod.POST)
    public String metodoPost(@Valid WrapperIngresoNota wrapperIngresoNota, BindingResult bindingResult,
            Model modelo, HttpServletRequest request) {
        

        String pag = request.getParameter("linkValue");
        int pagina = pag ==null?1:getPageNumber(pag);
        if (pagina == -1){ // es la ultima
            int inicio = listadoDetalleAsignacion.size()%10;
            
        }
        short notaFinal, zona, laboratorio, examenFinal;
        this.setModelo(modelo, false, new DatosIngresoNota());
        try {
            int i = 0;
            int pagActual = pagina!= -1?pagina-1:(this.listadoDetalleAsignacion.size()/10)+1;
            List<DetalleAsignacion> temp; 
//            System.out.println("%%%% pagina:"+pagina);
//            System.out.println("%%%% pagActual:"+pagActual);
//            System.out.println("%%%% listado:"+this.listadoDetalleAsignacion.size());
           
            if (pagina!=-1 && pagActual >=1){
                temp = this.listadoDetalleAsignacion.subList((pagActual-1)*10, (pagActual*10));
//                System.out.println("%%% rango ini: "+(pagActual-1)*10);
//            System.out.println("%%% rango final: "+((pagActual*10)-1));
           }else {
                temp = this.listadoDetalleAsignacion.subList((pagActual-1)*10, this.listadoDetalleAsignacion.size());
//                System.out.println("%%% rango ini&: "+(pagActual-1)*10);
//                System.out.println("%%% rango final&: "+(this.listadoDetalleAsignacion.size()));
                
            }
            //System.out.println("%%% temp: "+temp.size());
            
            
            //for (DetalleAsignacion detAsign : this.listadoDetalleAsignacion) {
            
            for (DetalleAsignacion detAsign : temp) {
                int sizeZona = wrapperIngresoNota.getListZona().size();
//                System.out.println("%%% i: "+i);
//                System.out.println("%%%%%%%%% listZona.size "+sizeZona);
//                System.out.println("%%%%%%%%% listZona.ultimo "+wrapperIngresoNota.getListZona().get(sizeZona-1));
//                System.out.println("%%%%%%%%% listZona.ultimo "+wrapperIngresoNota.getListZona().get(i));
                if (i >= wrapperIngresoNota.getListZona().size()) {
                    break;
                }
                zona = Short.valueOf(wrapperIngresoNota.getListZona().get(i).toString());
                //laboratorio = Short.valueOf(wrapperIngresoNota.getListLaboratorio().get(i).toString());
                examenFinal = Short.valueOf(wrapperIngresoNota.getListFinal().get(i).toString());
                notaFinal = (short) (zona + examenFinal);
                detAsign.setZona(zona);
                //detAsign.setLaboratorio(laboratorio);
                detAsign.setLaboratorio((short) 0);
                detAsign.setExamenFinal(examenFinal);
                detAsign.setOficializado(wrapperIngresoNota.getOficializar());
                detAsign.setExcusa(wrapperIngresoNota.getListExcusa().get(i).toString());
                
                //System.out.println("actualizar: "+detAsign.getAsignacion().getAsignacionEstudianteCarrera().getEstudiante().getCarne());
                super.servicioDetalleAsignacionImpl.actualizar(detAsign);

                if (wrapperIngresoNota.getOficializar()) {
                    if (notaFinal >= detAsign.getHorario().getAsignacionCursoPensum().getNotaAprobacion()
                            && this.servicioCursoAprobadoImpl.getCursoAprobado(detAsign.getAsignacion(),
                            detAsign.getHorario().getAsignacionCursoPensum()) == null) {
                        CursoAprobado cursoAprobado = new CursoAprobado();
                        cursoAprobado.setAsignacion(detAsign.getAsignacion());
                        cursoAprobado.setAsignacionCursoPensum(detAsign.getHorario().getAsignacionCursoPensum());
                        cursoAprobado.setExamenFinal(examenFinal);
                        cursoAprobado.setFechaAprobacion(new Date());
                        cursoAprobado.setLaboratorio((short) 0);
                        cursoAprobado.setZona(zona);
                        super.servicioUsuarioImpl.agregar(cursoAprobado);
                    }
                }
                i++;
            }
            if (wrapperIngresoNota.getOficializar()) {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "ingresoNota.oficializadas", true);
            } else {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "ingresoNota.guardadas", true);
            }

        } catch (Exception ex) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, ex);
        }

//        if (this.esAdministrativo) {
//            RequestUtil.agregarRedirect(request, "ingresoNotaAdmin.htm");
//        } else {
//            RequestUtil.agregarRedirect(request, "ingresoNota.htm");
//        }
        
//       return "detalleAsignacion/ingresoNota";
        DatosIngresoNota din = new DatosIngresoNota();
        din.setHorario(horarioActual);
        
        if (pagina != -1){
             return paginarNotasPag(modelo, din, request);
        }
        if (this.esAdministrativo) {
            RequestUtil.agregarRedirect(request, "ingresoNotaAdmin.htm");
        } else {
            RequestUtil.agregarRedirect(request, "ingresoNota.htm");
        }
        return "detalleAsignacion/ingresoNota";
        

        
    }
//  _____________________________________________________________________________

    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina
     * <code>getHorarioCatedratico.htm</code>. El metodo se encarga de
     * inicializar las listas de horario en base a los parametros enviados.
     *
     * @param idTipoHorario tipo de horario a buscar
     * @return List<Horario> listado filtrado en base a los parametros enviados.
     */
    @RequestMapping(value = "getHorarioCatedratico.htm", method = RequestMethod.GET)
    public @ResponseBody
    String getHorario(@RequestParam String idTipoHorario,
            HttpServletRequest request) {
        String strOptions = "";
        try {
            TipoHorario tipoHorario = TipoHorario.valueOf(idTipoHorario);
            if (this.esAdministrativo) {
                this.listadoHorario = this.servicioHorarioImpl.getHorario(this.semestre, tipoHorario);
            } else {
                this.listadoHorario = this.servicioHorarioImpl.getHorario(this.semestre, this.catedratico,
                        tipoHorario);
            }
            for (Horario horario : this.listadoHorario) {
                strOptions += "<option value=\"" + horario.getIdHorario() + "\">"
                        + horario.getAsignacionCursoPensum().getCurso().getNombre() + " - " + horario.getSeccion()
                        + "</option>";
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
    private void setModelo(Model modelo, boolean mostrarAsignaciones, DatosIngresoNota datosIngresoNota) {
        if (excusasMap == null){
            excusasMap = new HashMap();
            ArrayList excusas =  new ArrayList();
             excusas.add("N/A");
            excusas.add("NSP");
            excusas.add("SDE");
           excusas.add("SZM");
            excusas.add("AC");
            excusasMap.put("listaEx", excusas);
        }
        modelo.addAttribute("excusasList", excusasMap.get("listaEx"));
        modelo.addAttribute("datosIngresoNota", datosIngresoNota);
        modelo.addAttribute("listaTipoHorario", TipoHorario.values());
        modelo.addAttribute("listaHorario", this.listadoHorario);
        if (this.listadoDetalleAsignacion != null) {
            modelo.addAttribute("listadoDetalleAsignacion", this.listadoDetalleAsignacion);
        }
        modelo.addAttribute("mostrarEstudiantes", mostrarAsignaciones);
        modelo.addAttribute("validacionesOK", true);
        modelo.addAttribute("esAdministrativo", this.esAdministrativo);

        if (mostrarAsignaciones) {
            modelo.addAttribute("limiteZona", datosIngresoNota.getHorario().getAsignacionCursoPensum().getZona());
            modelo.addAttribute("limiteExamenFinal", datosIngresoNota.getHorario().getAsignacionCursoPensum().getExamenFinal());
        } else {
            modelo.addAttribute("limiteZona", 0);
            modelo.addAttribute("limiteExamenFinal", 0);
        }

    }
    

    @RequestMapping(value = "paginarNotasPag.htm", method = RequestMethod.GET)
    public String paginarNotasPag(Model modelo, DatosIngresoNota datosIngresoNota, 
            HttpServletRequest request) {
        modelo.addAttribute("pageSize", 10);
        modelo.addAttribute("listaTipoHorario", TipoHorario.values());
        modelo.addAttribute("listaHorario", this.listadoHorario);
        this.listadoDetalleAsignacion = this.servicioDetalleAsignacionImpl.
                getListadoDetalleAsignacion(datosIngresoNota.getHorario().getIdHorario());
        if (this.listadoDetalleAsignacion != null) {
            modelo.addAttribute("listadoDetalleAsignacion", this.listadoDetalleAsignacion);
        }
        modelo.addAttribute("mostrarEstudiantes", true);
        modelo.addAttribute("validacionesOK", true);
        modelo.addAttribute("esAdministrativo", this.esAdministrativo);
        modelo.addAttribute("wrapperIngresoNota", new WrapperIngresoNota());
        modelo.addAttribute("limiteZona", horarioActual.getAsignacionCursoPensum().getZona());
        modelo.addAttribute("limiteExamenFinal", horarioActual.getAsignacionCursoPensum().getExamenFinal());

                if (excusasMap == null){
            excusasMap = new HashMap();
            ArrayList excusas =  new ArrayList();
             excusas.add("N/A");
            excusas.add("NSP");
            excusas.add("SDE");
           excusas.add("SZM");
            excusas.add("AC");
            excusasMap.put("listaEx", excusas);
        }
        modelo.addAttribute("excusasList", excusasMap.get("listaEx"));
        
        RequestUtil.agregarRedirect(request, request.getParameter("linkValue"));
        return "detalleAsignacion/ingresoNota";
    }

    /**
     * @return the horarioActual
     */
    public Horario getHorarioActual() {
        return horarioActual;
    }

    /**
     * @param horarioActual the horarioActual to set
     */
    public void setHorarioActual(Horario horarioActual) {
        this.horarioActual = horarioActual;
    }
    
    private int getPageNumber(String url){
        String value = url;//"paginarNotasPag.htm?d-3585356-p=2&horario.idHorario=4498&tipoHorario=SEMESTRE";
        int init = value.indexOf("-p=");
        if (init != -1){
            value = value.substring(init);
          int end = value.indexOf("&");
          //System.out.println(value.substring(value.indexOf("=")+1, end));
          return Integer.parseInt(value.substring(value.indexOf("=")+1, end));          
        }else{
            return -1;
        }

    }

    /**
     * @return the excusasMap
     */
    public HashMap<String, ArrayList<String>> getExcusasMap() {
        return excusasMap;
    }

    /**
     * @param excusasMap the excusasMap to set
     */
    public void setExcusasMap(HashMap<String, ArrayList<String>> excusasMap) {
        this.excusasMap = excusasMap;
    }


}
