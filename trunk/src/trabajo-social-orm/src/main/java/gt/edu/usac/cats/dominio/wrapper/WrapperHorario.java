/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */
package gt.edu.usac.cats.dominio.wrapper;

import gt.edu.usac.cats.dominio.AsignacionCursoPensum;
import gt.edu.usac.cats.dominio.Horario;
import gt.edu.usac.cats.dominio.Salon;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.enums.TipoHorario;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Contiene los atributos del Horario que seran ingresados o actualizado a la
 * BD. El wrapper se utiliza en las paginas de
 * <code>agregarHorario.htm</code> y
 * <code>editarHorario.htm</code>.
 *
 * @author Mario Batres
 * @version 1.0
 */
public class WrapperHorario implements Serializable {

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
    @NotNull(message = "{validacion.campoObligatorio}")
    private AsignacionCursoPensum asignacionCursoPensum;
//______________________________________________________________________________
    @NotNull(message = "{validacion.campoObligatorio}")
    private Salon salon;
//______________________________________________________________________________
    @NotNull(message = "{validacion.campoObligatorio}")
    private Semestre semestre;
//______________________________________________________________________________
    @NotNull(message = "{validacion.campoObligatorio}")
    private String[] horarioDiasWrapper;
    @NotNull(message = "{validacion.campoObligatorio}")
    private TipoHorario tipoHorario;
    
    private Short idSalon;

    /**
     * Constructor del wrapper, se inicializan los atributos a mostrar en las
     * paginas de
     * <code>agregarHorario.htm</code> y
     * <code>editarHorario.htm</code>.
     */
    public WrapperHorario() {
        this.habilitado = true;
        this.horaFin = new Date();
        this.horaInicio = new Date();
        this.semestre = new Semestre();
        this.salon = new Salon();
        this.idSalon = 0;
        this.asignacionCursoPensum = new AsignacionCursoPensum();
    }

//______________________________________________________________________________
    /**
     * Se agrega al wrapper la informacion del pojo de tipo {@link Horario}.
     *
     * @param horario Pojo de tipo {@link Horario}
     */
    public void agregarWrapper(Horario horario) {
        this.asignacionCursoPensum = horario.getAsignacionCursoPensum();
        this.habilitado = horario.isHabilitado();
        this.horaFin = horario.getHoraFin();
        this.horaInicio = horario.getHoraInicio();
        this.salon = horario.getSalon();
        this.seccion = horario.getSeccion();
        this.semestre = horario.getSemestre();
        this.horarioDiasWrapper = horario.getHorarioDiasNumeroAsArrayString();
        this.setTipoHorario(horario.getTipo());
        this.idSalon = horario.getSalon().getIdSalon();
    }
//______________________________________________________________________________

    /**
     * Se agrega la informacion del pojo de tipo {@link Horario} al wrapper.
     *
     * @param horario Pojo de tipo {@link Horario}
     */
    public void quitarWrapper(Horario horario) {
        horario.setAsignacionCursoPensum(this.asignacionCursoPensum);
        horario.setHabilitado(this.habilitado);
        horario.setHoraFin(this.horaFin);
        horario.setHoraInicio(this.horaInicio);
        horario.setSalon(this.salon);
        horario.setSeccion(this.seccion);
        horario.setSemestre(this.semestre);
        //horario.setHorarioDias(this.horarioDias);
        horario.setTipo(getTipoHorario());
        if(horario.getSalon().getIdSalon() == 0){
            horario.getSalon().setIdSalon(idSalon);
        }
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
     * @return pojo de tipo {@link AsignacionCursoPensum}
     */
    public AsignacionCursoPensum getAsignacionCursoPensum() {
        return this.asignacionCursoPensum;
    }

    /**
     * @param curso pojo de tipo {@link Curso}
     */
    public void setAsignacionCursoPensum(AsignacionCursoPensum asignacionCursoPensum) {
        this.asignacionCursoPensum = asignacionCursoPensum;
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
    public String[] getHorarioDiasWrapper() {
        return horarioDiasWrapper;
    }

    /**
     * @param horarioDias the horarioDias to set
     */
    public void setHorarioDiasWrapper(String[] horarioDiasWrapper) {
        this.horarioDiasWrapper = horarioDiasWrapper;
    }

    /**
     * @return the tipoHorario
     */
    public TipoHorario getTipoHorario() {
        return tipoHorario;
    }

    /**
     * @param tipoHorario the tipoHorario to set
     */
    public void setTipoHorario(TipoHorario tipoHorario) {
        this.tipoHorario = tipoHorario;
    }

    public Short getIdSalon() {
        return idSalon;
    }

    public void setIdSalon(Short idSalon) {
        this.idSalon = idSalon;
        this.salon.setIdSalon(idSalon);
    }

}
