/*
 * Universidad de San Carlos de Guatemala
 * Escuela de Trabajo Social
 * Control Academico
 */

package gt.edu.usac.cats.controlador.asignacionCatedraticoHorario;

import gt.edu.usac.cats.dominio.Catedratico;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.servicio.ServicioAsignacionCatedraticoHorario;
import gt.edu.usac.cats.servicio.ServicioGeneral;
import gt.edu.usac.cats.servicio.ServicioSemestre;
import javax.annotation.Resource;

/**
 * Clase abstracta que contiene los atributos generales para el manejo 
 * de las asignaciones de catedraticos a horarios. 
 * 
 * @author Carlos Solorzano
 * @version 1.0
 */
public abstract class ControladorAbstractoAsignacionCatedraticoHorario {
//______________________________________________________________________________    
    protected Catedratico catedratico;
//______________________________________________________________________________
    protected Semestre semestre;
//______________________________________________________________________________    
    @Resource
    protected ServicioAsignacionCatedraticoHorario servicioAsignacionCatedraticoHorarioImpl;                     
//______________________________________________________________________________
    @Resource
    protected ServicioGeneral servicioGeneralImpl;   
//______________________________________________________________________________    
    @Resource
    protected ServicioSemestre servicioSemestreImpl;    
}