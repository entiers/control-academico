/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.controlador.horario;

import gt.edu.usac.trabajosocial.dominio.Curso;
import gt.edu.usac.trabajosocial.dominio.Salon;
import gt.edu.usac.trabajosocial.dominio.Semestre;
import gt.edu.usac.trabajosocial.servicio.ServicioGeneral;
import gt.edu.usac.trabajosocial.servicio.ServicioHorario;
import java.util.List;
import javax.annotation.Resource;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
public abstract class ControladorHorarioAbstracto {
    
    /**
     * <p>Contiene metodos que permiten el manejo de la informacion relacionada
     * con el horario en la base de datos. Este objeto se encuentra registrado
     * como un bean de servicio en Spring, por lo que este es el encargado de
     * inyectar la dependencia.</p>
     */
    @Resource
    protected ServicioHorario servicioHorarioImpl;
//______________________________________________________________________________
    /**
     * <p>Contiene metodos basicos de acceso a la base de datos, estos metodos
     * permiten realizar operaciones basicas sobre cualquier tabla de la base
     * de datos.</p>
     */
    @Resource
    protected ServicioGeneral servicioGeneralImpl;
//______________________________________________________________________________
    /**
     * <p>Listado de todas las cursos disponibles.</p>
     */
    protected List <Curso> listadoCursos;
//______________________________________________________________________________
    /**
     * <p>Listado de todas las salones disponibles.</p>
     */
    protected List <Salon> listadoSalones;
//______________________________________________________________________________
    /**
     * <p>Listado de todas las semestres disponibles.</p>
     */
    protected List <Semestre> listadoSemestres;
//______________________________________________________________________________
    protected static final String [] LISTADO_DIAS =
    {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};
}
