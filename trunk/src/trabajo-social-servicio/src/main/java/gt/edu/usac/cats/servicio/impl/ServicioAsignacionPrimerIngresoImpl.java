/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.servicio.impl;


import javax.annotation.Resource;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;
import java.util.List;
import gt.edu.usac.cats.dominio.Asignacion;
import gt.edu.usac.cats.dominio.AsignacionCursoPensum;
import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.AsignacionPrimerIngreso;
import gt.edu.usac.cats.dominio.Carrera;
import gt.edu.usac.cats.dominio.Curso;
import gt.edu.usac.cats.dominio.DetalleAsignacion;
import gt.edu.usac.cats.dominio.DetalleAsignacionPrimerIngreso;
import gt.edu.usac.cats.dominio.Horario;
import gt.edu.usac.cats.enums.TipoAsignacion;
import gt.edu.usac.cats.servicio.ServicioAsignacionEstudianteCarrera;
import gt.edu.usac.cats.servicio.ServicioAsignacionPrimerIngreso;
import gt.edu.usac.cats.servicio.ServicioCurso;
import gt.edu.usac.cats.servicio.ServicioEstudiante;
import gt.edu.usac.cats.servicio.ServicioGeneral;
import gt.edu.usac.cats.servicio.ServicioHorario;
import gt.edu.usac.cats.servicio.ServicioUsuario;
import gt.edu.usac.cats.util.EmailSenderVelocity;
import gt.edu.usac.cats.velocity.FabricaTemplateVelocity;
import gt.edu.usac.cats.velocity.contexto.ResumenAsignacionPrimerIngreso;
import java.util.Date;
import javax.mail.MessagingException;
import org.hibernate.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
@Service("servicioAsignacionPrimerIngresoImpl")
public class ServicioAsignacionPrimerIngresoImpl extends ServicioGeneralImpl implements ServicioAsignacionPrimerIngreso {
//_____________________________________________________________________________
    @Resource
    private ServicioAsignacionEstudianteCarrera servicioAsignacionEstudianteCarreraImpl;
//_____________________________________________________________________________
    @Resource
    private ServicioHorario servicioHorarioImpl;
//_____________________________________________________________________________
    @Resource
    private ServicioGeneral servicioGeneralImpl;
//_____________________________________________________________________________
    @Resource
    private ServicioCurso servicioCursoImpl;
//_____________________________________________________________________________
    @Resource
    private ServicioEstudiante servicioEstudianteImpl;
//_____________________________________________________________________________
    @Resource
    private ServicioUsuario servicioUsuarioImpl;
//_____________________________________________________________________________
    @Resource
    private EmailSenderVelocity emailSenderVelocity;
//_____________________________________________________________________________
    @Override
    public void asignacionCursosPrimerIngreso(AsignacionPrimerIngreso asignacionPrimerIngreso) throws DataAccessException {
        List<AsignacionEstudianteCarrera> lstAEC;

        int totalAsignaciones;
        int totEstudianteAsignados = 0;
        int totEstudianteNoAsignados = 0;

        //Cargar configuraciones
        Properties prop = this.cargarConfiguraciones();

        //Obtener todas las carreras existentes
        List<Carrera> lstCarreras = this.servicioGeneralImpl.cargarEntidades(Carrera.class);
        if (!lstCarreras.isEmpty()){
            for(Carrera carrera: lstCarreras){
                //Obtener estudiantes de primer ingreso por carrera
                lstAEC = this.servicioAsignacionEstudianteCarreraImpl.getAsignacionEstudianteCarreraPrimerIngreso(carrera);
                //Obtener cursos por carrera
                List<AsignacionCursoPensum> lstCursoPrimerSemestre = this.servicioCursoImpl.getCursoPrimerSemestreXCarrera(carrera);
                if(!lstAEC.isEmpty()){
                    for(AsignacionEstudianteCarrera aec: lstAEC){
                        if (!lstCursoPrimerSemestre.isEmpty()){
                            Asignacion asignacion = new Asignacion();
                            asignacion.setTransaccion(this.getUUID());
                            asignacion.setAsignacionEstudianteCarrera(aec);
                            asignacion.setAsignacionPrimerIngreso(asignacionPrimerIngreso);
                            asignacion.setTipoAsignacion(TipoAsignacion.ASIGNACION_PRIMER_INGRESO);
                            this.servicioGeneralImpl.agregar(asignacion);

                            totalAsignaciones = 0;
                            for (AsignacionCursoPensum asignacionCursoPensum: lstCursoPrimerSemestre){
                                if(!this.servicioEstudianteImpl.tieneCursoAsignadoPrimerIngreso(aec.getEstudiante(), asignacionCursoPensum.getCurso())){
                                    
                                    Horario horario = this.servicioHorarioImpl.getHorarioPorCursoPrimerIngreso(asignacionCursoPensum);
                                    if (horario != null){
                                        DetalleAsignacion detAsign = new DetalleAsignacion();
                                        detAsign.setAsignacion(asignacion);
                                        detAsign.setHorario(horario);
                                        this.servicioGeneralImpl.agregar(detAsign);
                                        totalAsignaciones++;
                                    }
                                }
                            }
                            //Grabar detalle del proceso de asignacion para reporte
                            DetalleAsignacionPrimerIngreso dapi = new DetalleAsignacionPrimerIngreso();
                            dapi.setEstudiante(aec.getEstudiante());
                            dapi.setAsignacionPrimerIngreso(asignacionPrimerIngreso);
                            //Validar que el estudiante tenga asignaciones
                            if (totalAsignaciones==0){
                                this.servicioGeneralImpl.borrar(asignacion);
                                dapi.setAsignado(false);
                                totEstudianteNoAsignados++;
                            }
                            else
                                totEstudianteAsignados++;

                            this.servicioGeneralImpl.agregar(dapi);
                        }

                    }
                }
            }
        }
        //Actualizar fecha final del proceso de asignacion
        asignacionPrimerIngreso.setFechaFin(new Date());
        this.servicioGeneralImpl.actualizar(asignacionPrimerIngreso);

        //Enviar correo con detalle del proceso
        this.enviarCorreo(asignacionPrimerIngreso, totEstudianteAsignados, totEstudianteNoAsignados);
  }

    @Override
    public List<AsignacionPrimerIngreso> getAsignacionPrimerIngreso(String nombreUsuario,
                                              Date fechaInicio,
                                              Date fechaFin) throws DataAccessException {
        fechaInicio = new Date(fechaInicio.getYear(), fechaInicio.getMonth(), fechaInicio.getDate(), 0,0,0);
        fechaFin = new Date(fechaFin.getYear(), fechaFin.getMonth(), fechaFin.getDate(), 23,59,59);
        StringBuilder builder = new StringBuilder();
        builder.append("select api from AsignacionPrimerIngreso as api")
               .append(" where api.usuario.nombreUsuario like :nombreUsuario")
               .append(" and api.fechaInicio between :fechaInicio and :fechaFin");

        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("nombreUsuario", "%" + nombreUsuario + "%");
        query.setParameter("fechaInicio", fechaInicio);
        query.setParameter("fechaFin", fechaFin);
        return query.list();
    }
 //______________________________________________________________________________
     /**
     * <p>Metodo para generar UUID.</p>
     *
     * @return String con UUID
     */
    private String getUUID(){
        UUID id = UUID.randomUUID();
        return id.toString();
    }
//______________________________________________________________________________
     /**
     * <p>Metodo para generar cargar archivo de propiedades con configuraciones
      * de los procesos de negocio.</p>
     *
     * @return Properties
     */
    private Properties cargarConfiguraciones(){
        Properties pro=new Properties();
        try{
            pro.load(this.getClass().getResourceAsStream("/gt/edu/usac/cats/util/ConfProcesosNegocio.properties"));
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        return pro;
    }

    private void enviarCorreo(AsignacionPrimerIngreso asignacionPrimerIngreso,
                                int totalAsignados,
                                int totalNoAsignados){

        ResumenAsignacionPrimerIngreso rapi = new ResumenAsignacionPrimerIngreso();

        rapi.setFechaFin(asignacionPrimerIngreso.getFechaFin());
        rapi.setFechaInicio(asignacionPrimerIngreso.getFechaInicio());
        rapi.setTotalAsignados(totalAsignados);
        rapi.setTotalNoAsignados(totalNoAsignados);
        
        String correo = this.servicioUsuarioImpl.getCorreoPorUsuario(asignacionPrimerIngreso.getUsuario());

        if(!correo.isEmpty()){

            try{
                emailSenderVelocity.enviarCorreo("Proceso de asignacion de primer ingreso",
                        correo,
                        FabricaTemplateVelocity.NUEVO_USUARIO, rapi);

            }catch(MailException e){

            }catch(IOException e){

            }catch(MessagingException e){

            }
        }


    }
}