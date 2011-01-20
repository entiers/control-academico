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
import gt.edu.usac.cats.dominio.DetalleAsignacion;
import gt.edu.usac.cats.dominio.Horario;
import gt.edu.usac.cats.dominio.TipoAsignacion;
import gt.edu.usac.cats.servicio.ServicioAsignacionEstudianteCarrera;
import gt.edu.usac.cats.servicio.ServicioAsignacionPrimerIngreso;
import gt.edu.usac.cats.servicio.ServicioDetalleAsignacion;
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
    private ServicioDetalleAsignacion servicioDetalleAsignacionImpl;
//_____________________________________________________________________________
    @Override
    public void asignacionCursosPrimerIngreso(AsignacionPrimerIngreso asignacionPrimerIngreso) throws DataAccessException {
        List<AsignacionEstudianteCarrera> lstAEC;
        List<Horario> lstHorarioPrimerSemestre;

        //Cargar configuraciones
        Properties prop = this.cargarConfiguraciones();
        TipoAsignacion tipoAsignacion = this.servicioGeneralImpl.cargarEntidadPorID(TipoAsignacion.class,
                Short.valueOf(prop.getProperty("asignacionPrimerIngreso.TipoAsignacion")));

        //Obtener todas las carreras existentes
        List<Carrera> lstCarreras = this.servicioGeneralImpl.cargarEntidades(Carrera.class);
        if (!lstCarreras.isEmpty()){
            for(Carrera carrera: lstCarreras){
                //Obtener horarios para carrera
                lstHorarioPrimerSemestre = this.servicioHorarioImpl.getHorarioPrimerSemestre(carrera);
                if (!lstHorarioPrimerSemestre.isEmpty()){
                    //Si existen horarios disponibles seleccionar estudiantes de primer ingreso de dicha carrera
                    lstAEC = this.servicioAsignacionEstudianteCarreraImpl.getAsignacionEstudianteCarreraPrimerIngreso(carrera);
                    if(!lstAEC.isEmpty()){
                        for(AsignacionEstudianteCarrera aec: lstAEC){
                            //Crear nueva asignacion
                            Asignacion asignacion = new Asignacion();                            
                            asignacion.setTransaccion(this.getUUID());
                            asignacion.setAsignacionEstudianteCarrera(aec);
                            asignacion.setAsignacionPrimerIngreso(asignacionPrimerIngreso);
                            asignacion.setTipoAsignacion(tipoAsignacion);
                            this.servicioGeneralImpl.agregar(asignacion);
                            //Crear nuevo detalle asignacion
                            for (Horario horario: lstHorarioPrimerSemestre){               
                                if(horario.getSalon().getCapacidad()>this.servicioDetalleAsignacionImpl.getCountDetalleAsignacionXHorario(horario)){
                                    DetalleAsignacion detAsign = new DetalleAsignacion();
                                    detAsign.setAsignacion(asignacion);
                                    detAsign.setHorario(horario);
                                    this.servicioGeneralImpl.agregar(detAsign);
                                }//Ya esta llena la seccion
                            }
                        }
                    }//No hay estudiantes de primer ingreso sin asignar
                }//No hay horario configurado para la carrera especificada
            }
        }//No hay carreras configuradas
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
