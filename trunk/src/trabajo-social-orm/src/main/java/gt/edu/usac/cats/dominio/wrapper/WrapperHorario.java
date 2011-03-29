/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.dominio.wrapper;

import gt.edu.usac.cats.dominio.Curso;
import gt.edu.usac.cats.dominio.Horario;
import gt.edu.usac.cats.dominio.Salon;
import gt.edu.usac.cats.dominio.Semestre;
import java.util.Date;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Contiene los atributos del Horario que seran ingresados
 * o actualizado a la BD. El wrapper se utiliza en las paginas de
 * <code>agregarHorario.htm</code> y <code>editarHorario.htm</code>.
 *
 * @author Mario Batres
 * @version 1.0
 */
public class WrapperHorario {
    
//______________________________________________________________________________
    private boolean habilitado;
//______________________________________________________________________________
    @DateTimeFormat(pattern = "HH:mm")
    private Date horaFin;
//______________________________________________________________________________
    @DateTimeFormat(pattern = "HH:mm")
    private Date horaInicio;
//______________________________________________________________________________
    @NotEmpty(message = "{validacion.campoObligatorio}")
    @Size(max = 2, message = "{validacion.caracteresMaximos}")
    private String seccion;    
//______________________________________________________________________________
    
    private Curso curso;
//______________________________________________________________________________
    private Salon salon;
//______________________________________________________________________________
    private Semestre semestre;
//______________________________________________________________________________

    private String [] horarioDiasWrapper;

     /**
     * Constructor del wrapper, se inicializan los atributos a mostrar en las
     * paginas de <code>agregarHorario.htm</code> y <code>editarHorario.htm</code>.
     */
    public WrapperHorario() {        
        this.habilitado = true;
        this.horaFin = new Date();
        this.horaInicio = new Date();        
    }

//______________________________________________________________________________
    /**
     * Se agrega al wrapper la informacion del pojo de tipo {@link Horario}.
     *
     * @param horario Pojo de tipo {@link Horario}
     */
    public void agregarWrapper(Horario horario){        
        this.curso = horario.getCurso();
        this.habilitado = horario.isHabilitado();
        this.horaFin = horario.getHoraFin();
        this.horaInicio = horario.getHoraInicio();
        this.salon = horario.getSalon();
        this.seccion = horario.getSeccion();
        this.semestre = horario.getSemestre();        
        this.horarioDiasWrapper = horario.getHorarioDiasNumeroAsArrayString();
    }
//______________________________________________________________________________
    /**
     * Se agrega la informacion del pojo de tipo {@link Horario} al wrapper.
     *
     * @param horario Pojo de tipo {@link Horario}
     */
    public void quitarWrapper(Horario horario){
        horario.setCurso(this.curso);
        horario.setHabilitado(this.habilitado);
        horario.setHoraFin(this.horaFin);
        horario.setHoraInicio(this.horaInicio);
        horario.setSalon(this.salon);
        horario.setSeccion(this.seccion);
        horario.setSemestre(this.semestre);        
        //horario.setHorarioDias(this.horarioDias);
    }
//______________________________________________________________________________
    /**
     * @return El estado del horario
     */
    public boolean isHabilitado() {
        return habilitado;
    }

    /**
     * @param estado El estado del horario
     */
    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }
//______________________________________________________________________________
    /**
     * @return La hora de finalizacion del curso
     */
    public Date getHoraFin() {
        return horaFin;
    }

    /**
     * @param horaFin La hora de finalizacion del curso
     */
    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }
//______________________________________________________________________________
    /**
     * @return La hora de inicio del curso
     */
    public Date getHoraInicio() {
        return horaInicio;
    }

    /**
     * @param horaInicio La hora de inicio del curso
     */
    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }
//______________________________________________________________________________
    /**
     * @return La seccion del curso en el horario.
     */
    public String getSeccion() {
        return seccion;
    }

    /**
     * @param seccion La seccion del curso en el horario.
     */
    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }
//______________________________________________________________________________
    /**
     * @return pojo de tipo {@link Curso}
     */
    public Curso getCurso() {
        return curso;
    }

    /**
     * @param curso pojo de tipo {@link Curso}
     */
    public void setCurso(Curso curso) {
        this.curso = curso;
    }
//______________________________________________________________________________
    /**
     * @return pojo de tipo {@link Salon}
     */
    public Salon getSalon() {
        return salon;
    }

    /**
     * @param salon pojo de tipo {@link Salon}
     */
    public void setSalon(Salon salon) {
        this.salon = salon;
    }
//______________________________________________________________________________
    /**
     * @return pojo de tipo {@link Semestre}
     */
    public Semestre getSemestre() {
        return semestre;
    }

    /**
     * @param semestre pojo de tipo {@link Semestre}
     */
    public void setSemestre(Semestre semestre) {
        this.semestre = semestre;
    }

    /**
     * @return the horarioDias
     */
    public String [] getHorarioDiasWrapper() {
        return horarioDiasWrapper;
    }

    /**
     * @param horarioDias the horarioDias to set
     */
    public void setHorarioDiasWrapper(String [] horarioDiasWrapper) {
        this.horarioDiasWrapper = horarioDiasWrapper;
    }

}