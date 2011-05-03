/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.velocity.contexto;

import gt.edu.usac.cats.velocity.contexto.extras.HorarioCurso;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
public class DesasignacionEstudianteCurso implements Serializable{
    private String carnet;
    private String nombre;

    private List <HorarioCurso> listadoHorarioCursos;

    public DesasignacionEstudianteCurso() {
        this.carnet = "";
        this.nombre = "";
        
    }

    public DesasignacionEstudianteCurso(String carnet, String nombre) {
        this.nombre = nombre;
        this.carnet = carnet;
    }



    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the carnet
     */
    public String getCarnet() {
        return carnet;
    }

    /**
     * @param carnet the carnet to set
     */
    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    /**
     * @return the listadoHorarioCursos
     */
    public List<HorarioCurso> getListadoHorarioCursos() {
        return listadoHorarioCursos;
    }

    /**
     * @param listadoHorarioCursos the listadoHorarioCursos to set
     */
    public void setListadoHorarioCursos(List<HorarioCurso> listadoHorarioCursos) {
        this.listadoHorarioCursos = listadoHorarioCursos;
    }


    /**
     * Retorna la fecha actual con el formato dd/MM/yyyy.
     *
     * @return Objeto de tipo {@link String}
     */
    public String getFechaActualFormat(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(new Date());
    }
}
