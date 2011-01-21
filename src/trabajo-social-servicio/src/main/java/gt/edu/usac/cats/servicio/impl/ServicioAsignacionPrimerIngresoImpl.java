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
import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.AsignacionPrimerIngreso;
import gt.edu.usac.cats.dominio.Carrera;
import gt.edu.usac.cats.dominio.Curso;
import gt.edu.usac.cats.dominio.DetalleAsignacion;
import gt.edu.usac.cats.dominio.DetalleAsignacionPrimerIngreso;
import gt.edu.usac.cats.dominio.Horario;
import gt.edu.usac.cats.dominio.TipoAsignacion;
import gt.edu.usac.cats.servicio.ServicioAsignacionEstudianteCarrera;
import gt.edu.usac.cats.servicio.ServicioAsignacionPrimerIngreso;
import gt.edu.usac.cats.servicio.ServicioCurso;
import gt.edu.usac.cats.servicio.ServicioDetalleAsignacion;
import gt.edu.usac.cats.servicio.ServicioEstudiante;
import gt.edu.usac.cats.servicio.ServicioGeneral;
import gt.edu.usac.cats.servicio.ServicioHorario;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
@Service("servicioAsignacionPrimerIngresoImpl")
public class ServicioAsignacionPrimerIngresoImpl implements ServicioAsignacionPrimerIngreso{
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
    @Override
    public void asignacionCursosPrimerIngreso(AsignacionPrimerIngreso asignacionPrimerIngreso) throws DataAccessException {
        List<AsignacionEstudianteCarrera> lstAEC;
        
        int totalAsignaciones;

        //Cargar configuraciones
        Properties prop = this.cargarConfiguraciones();
        TipoAsignacion tipoAsignacion = this.servicioGeneralImpl.cargarEntidadPorID(TipoAsignacion.class,
                Short.valueOf(prop.getProperty("asignacionPrimerIngreso.TipoAsignacion")));

        //Obtener todas las carreras existentes
        List<Carrera> lstCarreras = this.servicioGeneralImpl.cargarEntidades(Carrera.class);
        if (!lstCarreras.isEmpty()){
            for(Carrera carrera: lstCarreras){
                lstAEC = this.servicioAsignacionEstudianteCarreraImpl.getAsignacionEstudianteCarreraPrimerIngreso(carrera);
                //Obtener cursos por carrera
                List<Curso> lstCursoPrimerSemestre = this.servicioCursoImpl.getCursoPrimerSemestreXCarrera(carrera);
                if(!lstAEC.isEmpty()){
                    for(AsignacionEstudianteCarrera aec: lstAEC){                        
                        if (!lstCursoPrimerSemestre.isEmpty()){
                            Asignacion asignacion = new Asignacion();
                            asignacion.setTransaccion(this.getUUID());
                            asignacion.setAsignacionEstudianteCarrera(aec);
                            asignacion.setAsignacionPrimerIngreso(asignacionPrimerIngreso);
                            asignacion.setTipoAsignacion(tipoAsignacion);
                            this.servicioGeneralImpl.agregar(asignacion);

                            totalAsignaciones = 0;
                            for (Curso curso: lstCursoPrimerSemestre){
                                if(!this.servicioEstudianteImpl.tieneCursoAsignadoPrimerIngreso(aec.getEstudiante(), curso)){
                                    Horario horario = this.servicioHorarioImpl.getHorarioPorCursoPrimerIngreso(curso);
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
                            }
                            this.servicioGeneralImpl.agregar(dapi);
                        }
                        
                    }
                }
            }
        }
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
}
