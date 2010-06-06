/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.trabajosocial.dominio.wrapper;

import gt.edu.usac.trabajosocial.dominio.Curso;
import gt.edu.usac.trabajosocial.dominio.Horario;
import gt.edu.usac.trabajosocial.dominio.Salon;
import gt.edu.usac.trabajosocial.dominio.Semestre;
import java.util.Date;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
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
    @DateTimeFormat(pattern = "hh:mm")
    private Date horaFin;
//______________________________________________________________________________
    @DateTimeFormat(pattern = "hh:mm")
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
    

    public WrapperHorario() {
        this.dia = "";
        this.estado = false;
        this.horaFin = new Date();
        this.horaInicio = new Date();
        this.tipo = "";
    }

//______________________________________________________________________________
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
     * @return the dia
     */
    public String getDia() {
        return dia;
    }

    /**
     * @param dia the dia to set
     */
    public void setDia(String dia) {
        this.dia = dia;
    }

    /**
     * @return the estado
     */
    public boolean isEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    /**
     * @return the horaFin
     */
    public Date getHoraFin() {
        return horaFin;
    }

    /**
     * @param horaFin the horaFin to set
     */
    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }

    /**
     * @return the horaInicio
     */
    public Date getHoraInicio() {
        return horaInicio;
    }

    /**
     * @param horaInicio the horaInicio to set
     */
    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    /**
     * @return the seccion
     */
    public String getSeccion() {
        return seccion;
    }

    /**
     * @param seccion the seccion to set
     */
    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the curso
     */
    public Curso getCurso() {
        return curso;
    }

    /**
     * @param curso the curso to set
     */
    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    /**
     * @return the salon
     */
    public Salon getSalon() {
        return salon;
    }

    /**
     * @param salon the salon to set
     */
    public void setSalon(Salon salon) {
        this.salon = salon;
    }

    /**
     * @return the semestre
     */
    public Semestre getSemestre() {
        return semestre;
    }

    /**
     * @param semestre the semestre to set
     */
    public void setSemestre(Semestre semestre) {
        this.semestre = semestre;
    }




}
