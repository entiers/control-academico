/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.controlador.calendarioActividades;

import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.servicio.ServicioCalendarioActividades;
import gt.edu.usac.cats.servicio.ServicioSemestre;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
public abstract class ControladorAbstractoCalendarioActividades implements Serializable {

    //______________________________________________________________________________
    /**
     * <p>Contiene metodos que son utilizados especificamente para la entidad calendario de actividades
     * {@link CalendarioActividades}</p>
     */
    @Resource
    protected ServicioCalendarioActividades servicioCalendarioActividadesImpl;
//______________________________________________________________________________
    /**
     * <p>Contiene metodos que son utilizados especificamente para la entidad semestre
     * {@link Semestre}</p>
     */
    @Resource
    protected ServicioSemestre servicioSemestreImpl;


}
