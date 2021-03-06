/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */
package gt.edu.usac.cats.controlador.detalleAsignacion;

import static gt.edu.usac.cats.controlador.detalleAsignacion.ControladorAbstractoIngresoNota.TITULO_MENSAJE;
import gt.edu.usac.cats.dominio.Catedratico;
import gt.edu.usac.cats.dominio.CursoAprobado;
import gt.edu.usac.cats.dominio.DetalleAsignacion;
import gt.edu.usac.cats.dominio.Horario;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.dominio.Usuario;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
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
@SessionAttributes(value = {"catedratico", "semestre", "listadoHorario", "esAdministrativo", "listadoDetalleAsignacion", "usuario", "horarioActual", "excusasMap", "lastPage", "fechaNotas"})
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
    private static int PAGINEO = 10;
    private boolean lastPage = false;

    /**
     * <p>
     * Metodo que intercepta la peticion GET de la pagina ingresoNota.htm. Se
     * encarga de validar: <li>Usuario logueado sea un catedratico</li>
     * <li>Periodo valido de ingreso de notas</li> <li>Mostrar el listado de
     * cursos a los que el catedratico se encuentra asignado en el semestre
     * actual</li> </p>
     * Se ejecuta al buscar ya con tipo horario y horario.
     */
    @RequestMapping(value = "ingresoNota.htm", method = RequestMethod.GET)
    public String buscarEstudiantingresoes(Model modelo, HttpServletRequest request) {

        this.esAdministrativo = false;
        try {
            this.semestre = this.servicioSemestreImpl.getSemestreActivo();
            System.out.println("MC: Semestre: " + this.semestre);

            //Buscando usuario logueado por nombre
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            this.usuario = super.servicioUsuarioImpl.cargarUsuarioPorNombre(auth.getName().toString());
            System.out.println("MC: Usuario: " + this.usuario);
            System.out.println("MC: Usuario.catedraticos: " + this.usuario.getCatedraticos());

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
                //return "detalleAsignacion/ingresoNota";
            }

            //Validar cursos asignados a catedratico
            if (this.servicioAsignacionCatedraticoHorarioImpl.getAsignacionCatedraticoHorario(semestre, catedratico).isEmpty()) {
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "ingresoNota.catedraticoSinCursos", false);
                RequestUtil.agregarRedirect(request, "index.htm");
                return "detalleAsignacion/ingresoNota";
            }

            this.listadoHorario = this.servicioHorarioImpl.getHorario(semestre, catedratico,
                    TipoHorario.values()[0], true);
            excusasMap = new HashMap();
            ArrayList excusas = new ArrayList();
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
     * <p>
     * Metodo que intercepta la peticion GET de la pagina ingresoNota.htm. Se
     * encarga de validar: <li>Usuario logueado sea un catedratico</li>
     * <li>Periodo valido de ingreso de notas</li> <li>Mostrar el listado de
     * cursos a los que el catedratico se encuentra asignado en el semestre
     * actual</li> </p>
     * Se ejecuta la prmera vez que se muestra la pagina, cuando es root
     * extraordinario
     */
    @RequestMapping(value = "ingresoNotaAdmin.htm", method = RequestMethod.GET)
    public String getIngresoNotaAdmin(Model modelo, HttpServletRequest request) {
        //System.out.println("Entra aca....... MCNOV%");
        this.esAdministrativo = true;
        try {
            this.semestre = this.servicioSemestreImpl.getSemestreActivo();
            //  System.out.println("&& semestre: " + this.semestre.getIdSemestre());

            this.listadoHorario = this.servicioHorarioImpl.getHorario(this.semestre, TipoHorario.values()[0], true);

        } catch (Exception ex) {
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, ex);
        }

        this.setModelo(modelo, false, new DatosIngresoNota());

        return "detalleAsignacion/ingresoNota";
    }

//______________________________________________________________________________
    /**
     * <p>
     * Metodo que recibe las peticiones POST de la pagina ingresoNota.htm. Se
     * encarga de buscar los estudiantes asignados al horario seleccionado</p>
     */
    @RequestMapping(value = "ingresoNota.htm", method = RequestMethod.POST)
    public String crearFormulario(@Valid DatosIngresoNota datosIngresoNota, BindingResult bindingResult,
            Model modelo, HttpServletRequest request) {

        System.out.println("MC- POST(IngresoNota.htm) datosIngresoNota: " + datosIngresoNota);


        try {
            datosIngresoNota.setHorario(this.servicioHorarioImpl.cargarEntidadPorID(Horario.class, datosIngresoNota.getHorario().getIdHorario()));
    //        System.out.println("MC- POST(IngresoNota.htm) datosIngresoNota. horario: " + datosIngresoNota.getHorario().getIdHorario());

    //        System.out.println("MC- POST(IngresoNota.htm) datosIngresoNota: " + datosIngresoNota.getHorario());
            this.listadoDetalleAsignacion = this.servicioDetalleAsignacionImpl.
                    getListadoDetalleAsignacion(datosIngresoNota.getHorario().getIdHorario());
            System.out.println("MC- POST(IngresoNota.htm) listadoDetalleAsignacion: " + this.listadoDetalleAsignacion);

            this.horarioActual = datosIngresoNota.getHorario();

        } catch (Exception ex) {
            // error de acceso a datos
            RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, ex);
        }

        WrapperIngresoNota win = new WrapperIngresoNota();
        for (int i = 0; i < PAGINEO; i++) {
            win.getListExcusa().add("XXX");
        }
        //modelo.addAttribute("wrapperIngresoNota", new WrapperIngresoNota());
        modelo.addAttribute("wrapperIngresoNota", win);
        System.out.println("*/*/*/*==> wrapperIngresoNota size: "+win.getListZona().size());
        System.out.println("/**/*/*===> detalleAsignacion "+listadoDetalleAsignacion.size());

        if (listadoDetalleAsignacion != null && !this.listadoDetalleAsignacion.isEmpty()) {
            // System.out.println("haoy alumnos MCNOV% metodo crearFormulario");
            modelo.addAttribute("nombreCurso", this.listadoDetalleAsignacion.get(0).getHorario().getAsignacionCursoPensum().getCurso().getNombre());
            modelo.addAttribute("numeroSeccion", this.listadoDetalleAsignacion.get(0).getHorario().getSeccion());
            modelo.addAttribute("ultimaPag", false);
            modelo.addAttribute("soloUna", listadoDetalleAsignacion.size() <= PAGINEO);
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

        System.out.println("Ingresa a metodo post");
        String pag = request.getParameter("linkValue");
        System.out.println("linkValue " + pag);
        int paginaSig = pag == null || pag.trim().isEmpty() ? -1 : getPageNumber(pag);
        System.out.println("*pagina= " + paginaSig);
        short notaFinal, zona, laboratorio, examenFinal;

        this.setModelo(modelo, false, new DatosIngresoNota());
        try {
            int i = 0;
            int pagActual;
            if (paginaSig != -1) {
                pagActual = paginaSig - 1;
                modelo.addAttribute("ultimaPag", false);
            } else { // ultima pagina
                int division = (int) Math.ceil(this.listadoDetalleAsignacion.size() / 10.0);
                pagActual = division;
                modelo.addAttribute("ultimaPag", true);
            }
            //int pagActual = paginaSig!= -1?paginaSig-1:(this.listadoDetalleAsignacion.size()/10)+1;

            //System.out.println("*pagActual= "+pagActual);
            List<DetalleAsignacion> temp;
            System.out.println("oficializando " + wrapperIngresoNota.getOficializar());
            if (wrapperIngresoNota.getOficializar() == true) {
                temp = this.listadoDetalleAsignacion;
                System.out.println("oficializando " + temp.size());
            } else {
                System.out.println("pagActual: " + pagActual);
                if (paginaSig != -1) {
                    temp = this.listadoDetalleAsignacion.subList((pagActual * PAGINEO) - PAGINEO, (pagActual * PAGINEO));
                    //System.out.println("sublist: "+((pagActual*PAGINEO)-PAGINEO)+"  , "+(pagActual*PAGINEO));

                } else { // ultima pagina
                    temp = this.listadoDetalleAsignacion.subList((pagActual * PAGINEO) - PAGINEO, this.listadoDetalleAsignacion.size());
                    //System.out.println("e sublist: "+((pagActual*PAGINEO)-PAGINEO)+"  , "+this.listadoDetalleAsignacion.size());
                }

            }
            //System.out.println("%%% temp: "+temp.size());

            //for (DetalleAsignacion detAsign : this.listadoDetalleAsignacion) {
            if (wrapperIngresoNota.getOficializar() == false) { // guardar las notas de la pagina
                Date fechaHoy = Calendar.getInstance().getTime();
                for (DetalleAsignacion detAsign : temp) {
                    System.out.println(" i " + i);
                    if (i >= wrapperIngresoNota.getListZona().size()) {
                        break;
                    }
                    zona = Short.valueOf(wrapperIngresoNota.getListZona().get(i).toString());
                    examenFinal = Short.valueOf(wrapperIngresoNota.getListFinal().get(i).toString());
                    notaFinal = (short) (zona + examenFinal);
                    detAsign.setZona(zona);
                    detAsign.setLaboratorio((short) 0);
                    detAsign.setExamenFinal(examenFinal);
                    detAsign.setOficializado(wrapperIngresoNota.getOficializar());
                    detAsign.setExcusa(wrapperIngresoNota.getListExcusa().get(i).toString());
                    detAsign.setFechaActa(fechaHoy);
                    super.servicioDetalleAsignacionImpl.actualizar(detAsign);

                    i++;
                }
                RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "ingresoNota.guardadas", true);
            } else { // oficializa
                System.out.println("Oficializando..............");
                System.out.println("WrapperIngresoNotas.oficializar: " + wrapperIngresoNota.getOficializar());
                // verifica que TODAS LAS NOTAS HAYAN SIDO GUARDADAS PREVIO A SER OFICIALIZADAS
                boolean notasGuardadas = true;
                for (DetalleAsignacion detAsign : this.listadoDetalleAsignacion) {
                    if (detAsign.getFechaActa() == null) {
                        RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "No se han guardado notas", false);
                        notasGuardadas = false;
                        break;
                    }
                }
                if (notasGuardadas) {

                    for (DetalleAsignacion detAsign : this.listadoDetalleAsignacion) {
                        System.out.println("fechaActa: " + detAsign.getFechaActa());

                        notaFinal = (short) (detAsign.getZona() + detAsign.getExamenFinal());
                        detAsign.setOficializado(wrapperIngresoNota.getOficializar());
                        detAsign.setFechaActa(wrapperIngresoNota.getFechaNotas()); // actualiza fecha

                        super.servicioDetalleAsignacionImpl.actualizar(detAsign);

                        if (wrapperIngresoNota.getOficializar()) {

                            if (this.esAdministrativo) {
                                // Admin.:no valida que no exista el curso como curso aprobado
                                
                                 CursoAprobado ca = this.servicioCursoAprobadoImpl.getCursoAprobado(detAsign.getAsignacion(), 
                                                            detAsign.getHorario().getAsignacionCursoPensum());
                                 
                                if (notaFinal >= detAsign.getHorario().getAsignacionCursoPensum().getPensum().getNotaAprobacion()) {
                                    System.out.println("Esta aprobado "+detAsign.getAsignacion());
                                    CursoAprobado cursoAprobado = new CursoAprobado();
                                    cursoAprobado.setAsignacion(detAsign.getAsignacion());
                                    cursoAprobado.setAsignacionCursoPensum(detAsign.getHorario().getAsignacionCursoPensum());
                                    cursoAprobado.setExamenFinal(detAsign.getExamenFinal());
                                    // se cambia por la fecha de acta - mc
                                    cursoAprobado.setFechaAprobacion(wrapperIngresoNota.getFechaNotas());
                                    System.out.println("**** fechaNotas: " + wrapperIngresoNota.getFechaNotas());
                                    cursoAprobado.setLaboratorio((short) 0);
                                    cursoAprobado.setZona(detAsign.getZona());
                                    
                                   
                                    
                                    if (ca !=null){
                                        ca.setExamenFinal(detAsign.getExamenFinal());
                                        // se cambia por la fecha de acta - mc
                                        ca.setFechaAprobacion(wrapperIngresoNota.getFechaNotas());

                                        ca.setLaboratorio((short) 0);
                                        ca.setZona(detAsign.getZona());
                                        super.servicioUsuarioImpl.agregarActualizar(ca);
                                    }else{
                                        super.servicioUsuarioImpl.agregarActualizar(cursoAprobado);
                                    }
                                } else{// si no esta aprobado
                                    if (ca != null){
                                        super.servicioUsuarioImpl.borrar(ca);
                                    }
                                }   
                            } else {
                                // Docente. valida que no exista como curso aprobado
                                if (notaFinal >= detAsign.getHorario().getAsignacionCursoPensum().getPensum().getNotaAprobacion()
                                        && this.servicioCursoAprobadoImpl.getCursoAprobado(detAsign.getAsignacion(),
                                                detAsign.getHorario().getAsignacionCursoPensum()) == null) {
                                    CursoAprobado cursoAprobado = new CursoAprobado();
                                    cursoAprobado.setAsignacion(detAsign.getAsignacion());
                                    cursoAprobado.setAsignacionCursoPensum(detAsign.getHorario().getAsignacionCursoPensum());
                                    cursoAprobado.setExamenFinal(detAsign.getExamenFinal());
                                    // se cambia por la fecha de acta - mc
                                    cursoAprobado.setFechaAprobacion(wrapperIngresoNota.getFechaNotas());
                                    System.out.println("**** fechaNotas: " + wrapperIngresoNota.getFechaNotas());
                                    cursoAprobado.setLaboratorio((short) 0);
                                    cursoAprobado.setZona(detAsign.getZona());
                                    super.servicioUsuarioImpl.agregar(cursoAprobado);
                                }
                            }
                        }
                    }
                    RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "ingresoNota.oficializadas", true);
                }
            }
        } catch (Exception ex) {
            // error de acceso a datos
            ex.printStackTrace();
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

        if (paginaSig != -1) {
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
     * <p>
     * Este metodo se ejecuta cada vez que se realiza una solicitud del tipo GET
     * de la pagina <code>getHorarioCatedratico.htm</code>. El metodo se encarga
     * de inicializar las listas de horario en base a los parametros enviados.
     *
     * @param idTipoHorario tipo de horario a buscar
     * @return List<Horario> listado filtrado en base a los parametros enviados.
     */
    @RequestMapping(value = "getHorarioCatedratico.htm", method = RequestMethod.GET)
    @ResponseBody
    public String getHorario(@RequestParam String idTipoHorario,
            HttpServletRequest request) {
        System.out.println("<<<<<< idTipoHorario" + idTipoHorario);
        String strOptions = "";
        try {
            TipoHorario tipoHorario = TipoHorario.valueOf(idTipoHorario);
            if (this.esAdministrativo) {
                this.listadoHorario = this.servicioHorarioImpl.getHorario(this.semestre, tipoHorario, true);
            } else {
                this.listadoHorario = this.servicioHorarioImpl.getHorario(this.semestre, this.catedratico,
                        tipoHorario, true);
            }

            for (Horario horario : this.listadoHorario) {
                strOptions += "<option value=\"" + horario.getIdHorario() + "\">"
                        + "[" + horario.getAsignacionCursoPensum().getCurso().getCodigo() + "] "
                        + horario.getAsignacionCursoPensum().getCurso().getNombre() + " - " + horario.getSeccion()
                        + "</option>";
            }
        } catch (Exception e) {
            //RequestUtil.crearMensajeRespuesta(request, TITULO_MENSAJE, "dataAccessException", false);
            log.error(Mensajes.DATA_ACCESS_EXCEPTION, e);
        }

        System.out.println("opciones: " + strOptions);
        return strOptions;
    }
//  _____________________________________________________________________________

    /**
     * Metodo encargado de inicializar el modelo enviado como parametro
     *
     * @param modelo
     * @param mostrarAsignaciones Llamado cuando entra por primera vez.
     */
    private void setModelo(Model modelo, boolean mostrarAsignaciones, DatosIngresoNota datosIngresoNota) {
        if (excusasMap == null) {
            excusasMap = new HashMap();
            ArrayList excusas = new ArrayList();
            excusas.add("N/A");
            excusas.add("NSP");
            excusas.add("SDE");
            excusas.add("SZM");
            excusas.add("AC");
            excusasMap.put("listaEx", excusas);
        }
        System.out.println("Hoario actual MCHOOY: " + this.horarioActual);
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
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String fechaActaActual = sdf.format(Calendar.getInstance().getTime());
        System.out.println(">>>>>>>>>>>>>>>>>>>>> mostrarAsignaciones: "+mostrarAsignaciones);
        if (mostrarAsignaciones) {
            modelo.addAttribute("limiteZona", datosIngresoNota.getHorario().getAsignacionCursoPensum().getZona());
            modelo.addAttribute("limiteExamenFinal", datosIngresoNota.getHorario().getAsignacionCursoPensum().getExamenFinal());
            if (this.listadoDetalleAsignacion.size() > 0
                    && this.listadoDetalleAsignacion.get(0).getFechaActa() != null) {
                fechaActaActual = sdf.format(this.listadoDetalleAsignacion.get(0).getFechaActa());
            }
        } else {
            modelo.addAttribute("limiteZona", 0);
            modelo.addAttribute("limiteExamenFinal", 0);
        }
        modelo.addAttribute("fechaNotas", fechaActaActual);
    }

    @RequestMapping(value = "paginarNotasPag.htm", method = RequestMethod.GET)
    public String paginarNotasPag(Model modelo, DatosIngresoNota datosIngresoNota,
            HttpServletRequest request) {
        // obtiene del request la pagina actual
        Enumeration keys = request.getParameterNames();
        String pagkey = null;
        while (keys.hasMoreElements()) {
            String k = (String) keys.nextElement();
            System.out.println(">>>> key:" + k);
            if (k.endsWith("-p")) {
                pagkey = k;
                break;
            }
        }
        int pagina = -1;
        // paginaron con guardar y seguir
        if (pagkey == null) {
            pagkey = "linkValue";
            pagina = getPageNumber(request.getParameter(pagkey));
        } else {
            pagina = Integer.parseInt(request.getParameter(pagkey));
        }

        // fin de la obtencion de la pagina actual
        modelo.addAttribute("pageSize", 10);
        modelo.addAttribute("listaTipoHorario", TipoHorario.values());
        modelo.addAttribute("listaHorario", this.listadoHorario);
        this.listadoDetalleAsignacion = this.servicioDetalleAsignacionImpl.
                getListadoDetalleAsignacion(datosIngresoNota.getHorario().getIdHorario());

        if (this.listadoDetalleAsignacion != null) {
            modelo.addAttribute("listadoDetalleAsignacion", this.listadoDetalleAsignacion);
            if (listadoDetalleAsignacion != null && !this.listadoDetalleAsignacion.isEmpty()) {
                modelo.addAttribute("nombreCurso", this.listadoDetalleAsignacion.get(0).getHorario().getAsignacionCursoPensum().getCurso().getNombre());
                modelo.addAttribute("numeroSeccion", this.listadoDetalleAsignacion.get(0).getHorario().getSeccion());
            }

//            for(DetalleAsignacion det: this.listadoDetalleAsignacion){
//                System.out.println("****"+det.getZona());
//                System.out.println("****"+det.getExamenFinal());
//                System.out.println("****"+det.isOficializado());
//                System.out.println("****"+det.getExcusa());
//            }
        }

        modelo.addAttribute("mostrarEstudiantes", true);
        modelo.addAttribute("validacionesOK", false);
        modelo.addAttribute("esAdministrativo", this.esAdministrativo);

        WrapperIngresoNota win = new WrapperIngresoNota();
        for (int i = 0; i < PAGINEO; i++) {
            win.getListExcusa().add("XXX");
        }

        modelo.addAttribute("wrapperIngresoNota", win);
        modelo.addAttribute("limiteZona", horarioActual.getAsignacionCursoPensum().getZona());
        modelo.addAttribute("limiteExamenFinal", horarioActual.getAsignacionCursoPensum().getExamenFinal());
        System.out.println("** MC FECHANOTAS PAG: " + this.listadoDetalleAsignacion.get(0).getFechaActa());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//        modelo.addAttribute("fechaNotas", sdf.format(this.listadoDetalleAsignacion.get(0).getFechaActa()));
        if (this.listadoDetalleAsignacion.get(0).getFechaActa() != null){
                modelo.addAttribute("fe chaNotas", sdf.format(this.listadoDetalleAsignacion.get(0).getFechaActa()));
        }else{
            modelo.addAttribute("fechaNotas", sdf.format(Calendar.getInstance().getTime()));
        }
        if (excusasMap == null) {
            excusasMap = new HashMap();
            ArrayList excusas = new ArrayList();
            excusas.add("N/A");
            excusas.add("NSP");
            excusas.add("SDE");
            excusas.add("SZM");
            excusas.add("AC");
            excusasMap.put("listaEx", excusas);
        }
        System.out.println("Pagina paginada: " + pagina);
        modelo.addAttribute("excusasList", excusasMap.get("listaEx"));
        modelo.addAttribute("soloUna", listadoDetalleAsignacion.size() <= (PAGINEO * pagina));
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

    private int getPageNumber(String url) {
        System.out.println("* " + url);
        String value = url;//"paginarNotasPag.htm?d-3585356-p=2&horario.idHorario=4498&tipoHorario=SEMESTRE";
        int init = value.indexOf("-p=");
        if (init != -1) {
            value = value.substring(init);
            int end = value.indexOf("&");
            //System.out.println(value.substring(value.indexOf("=")+1, end));
            return Integer.parseInt(value.substring(value.indexOf("=") + 1, end));
        } else {
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

    /**
     * @return the isLastPage
     */
    public boolean isLastPage() {
        return lastPage;
    }

    /**
     * @param isLastPage the isLastPage to set
     */
    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }

}
