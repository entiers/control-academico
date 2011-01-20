/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.servicio.impl;

import gt.edu.usac.cats.dominio.Asignacion;
import javax.annotation.Resource;
import java.util.List;

import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.AsignacionPrimerIngreso;
import gt.edu.usac.cats.dominio.Curso;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.Horario;
import gt.edu.usac.cats.dominio.TipoAsignacion;
import gt.edu.usac.cats.servicio.ServicioAsignacionPrimerIngreso;
import gt.edu.usac.cats.servicio.ServicioCurso;
import gt.edu.usac.cats.servicio.ServicioEstudiante;
import gt.edu.usac.cats.servicio.ServicioGeneral;
import gt.edu.usac.cats.servicio.ServicioHorario;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
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
    private ServicioEstudiante servicioEstudianteImpl;
//_____________________________________________________________________________
    @Resource
    private ServicioHorario servicioHorarioImpl;
//_____________________________________________________________________________
    @Resource
    private ServicioGeneral servicioGeneralImpl;
//_____________________________________________________________________________
    @Override
    public void asignacionCursosPrimerIngreso(AsignacionPrimerIngreso asignacionPrimerIngreso) throws DataAccessException {
        //Cargar configuraciones
        Properties prop = this.cargarConfiguraciones();        

        //Obtener estudiantes de primer ingreso(carne con anio actual)
        List<Estudiante> lstEstudiantes = this.servicioEstudianteImpl.getListadoEstudiantesPrimerIngreso();
        Set<AsignacionEstudianteCarrera> lstAEC;
        TipoAsignacion tipoAsignacion = this.servicioGeneralImpl.cargarEntidadPorID(TipoAsignacion.class, Short.valueOf(prop.getProperty("asignacionPrimerIngreso.TipoAsignacion")));

        if (!lstEstudiantes.isEmpty()){            
            for (Estudiante estudiante: lstEstudiantes){
                lstAEC = estudiante.getAsignacionEstudianteCarreras();
                for (AsignacionEstudianteCarrera aec: lstAEC){
                    //Obtener cursos de primer semestre de la carrera del estudiante
                    List<Horario> lstHorarioPrimerSemestre = this.servicioHorarioImpl.getHorarioPrimerSemestre(aec.getCarrera());
                    if (!lstHorarioPrimerSemestre.isEmpty()){
                        //Crear nueva asignaci�n para el estudiante
                        Asignacion asignacion = new Asignacion();
                        //Generarar ID unico de asignacion por medio de UUID en base al carne y fecha
                        //asignacion.setTransaccion(this.getUUID(estudiante.getCarne() + asignacion.getFecha().toString()));
                        asignacion.setTransaccion("123445");
                        asignacion.setAsignacionEstudianteCarrera(aec);
                        asignacion.setAsignacionPrimerIngreso(asignacionPrimerIngreso);
                        asignacion.setTipoAsignacion(tipoAsignacion);
                        this.servicioGeneralImpl.agregar(asignacion);
                    }
                    else{
                        System.out.println("No existen horarios disponibles");
                    }
                }
            }
        }
        else{
            System.out.println("No existen estudiantes");
        }
    }


    private String getUUID(String cadena){
        UUID id = UUID.fromString(cadena);
        return id.toString();
    }

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
