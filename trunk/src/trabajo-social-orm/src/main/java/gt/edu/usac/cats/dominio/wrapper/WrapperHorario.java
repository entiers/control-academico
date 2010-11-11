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
    @NotEmpty(message = "{validacion.campoObligatorio}")
    @Size(max = 10, message = "{validacion.caracteresMaximos}")
    private String dia;
//______________________________________________________________________________
    private boolean estado;
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
    @NotEmpty(message = "{validacion.campoObligatorio}")
    @Size(max = 20, message = "{validacion.caracteresMaximos}")
    private String tipo;
//______________________________________________________________________________
    
    private Curso curso;
//______________________________________________________________________________
    private Salon salon;
//______________________________________________________________________________
    private Semestre semestre;
//______________________________________________________________________________
     /**
     * Constructor del wrapper, se inicializan los atributos a mostrar en las
     * paginas de <code>agregarHorario.htm</code> y <code>editarHorario.htm</code>.
     */
    public WrapperHorario() {
        this.dia = "";
        this.estado = false;
        this.horaFin = new Date();
        this.horaInicio = new Date();
        this.tipo = "";
    }

//______________________________________________________________________________
    /**
     * Se agrega al wrapper la informacion del pojo de tipo {@link Horario}.
     *
     * @param horario Pojo de tipo {@link Horario}
     */
    public void agregarWrapper(Horario horario){
        this.dia = horario.getDia();
        this.curso = horario.getCurso();
        this.estado = horario.isEstado();
        this.horaFin = horario.getHoraFin();
        this.horaInicio = horario.getHoraInicio();
        this.salon = horario.getSalon();
        this.seccion = horario.getSeccion();
        this.semestre = horario.getSemestre();
        this.tipo = horario.getTipo();
    }
//______________________________________________________________________________
    /**
     * Se agrega la informacion del pojo de tipo {@link Horario} al wrapper.
     *
     * @param horario Pojo de tipo {@link Horario}
     */
    public void quitarWrapper(Horario horario){
        horario.setDia(this.dia);
        horario.setCurso(this.curso);
        horario.setEstado(this.estado);
        horario.setHoraFin(this.horaFin);
        horario.setHoraInicio(this.horaInicio);
        horario.setSalon(this.salon);
        horario.setSeccion(this.seccion);
        horario.setSemestre(this.semestre);
        horario.setTipo(this.tipo);
    }
//______________________________________________________________________________
    /**
     * @return El dia que sera impartido el curso
     */
    public String getDia() {
        return dia;
    }

    /**
     * @param dia El dia que sera impartido el curso
     */
    public void setDia(String dia) {
        this.dia = dia;
    }
//______________________________________________________________________________
    /**
     * @return El estado del horario
     */
    public boolean isEstado() {
        return estado;
    }

    /**
     * @param estado El estado del horario
     */
    public void setEstado(boolean estado) {
        this.estado = estado;
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
     * @return El tipo de horario
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo El tipo de horario
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
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

}
