/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.dominio;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * HistorialAsignacionEstudianteCarrera
 */

@Entity
@Table(
    name = "historial_asignacion_estudiante_carrera",
    schema = "control",
    uniqueConstraints={
        @UniqueConstraint(name = "asignacion_estudiante_carrera_semestre_uk",
            columnNames = {"id_asignacion_estudiante_carrera", "id_situacion", "id_semestre"})
    }
)
public class HistorialAsignacionEstudianteCarrera implements java.io.Serializable{

     private int idHistorialAsignacionEstudianteCarrera;
     private Situacion situacion;
     private Semestre semestre;
     private AsignacionEstudianteCarrera asignacionEstudianteCarrera;
     private Date fechaInscripcion;
    
//______________________________________________________________________________
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
        name = "id_historial_asignacion_estudiante_carrera",
        unique = true,
        nullable = false
    )
    public int getIdHistorialAsignacionEstudianteCarrera() {
        return idHistorialAsignacionEstudianteCarrera;
    }


    public void setIdHistorialAsignacionEstudianteCarrera(int idHistorialAsignacionEstudianteCarrera) {
        this.idHistorialAsignacionEstudianteCarrera = idHistorialAsignacionEstudianteCarrera;
    }

//______________________________________________________________________________
    @Temporal(TemporalType.DATE)
    @Column(
        name = "fecha_inscripcion",
        nullable = false,
        length = 13
    )
    public Date getFechaInscripcion() {
        return this.fechaInscripcion;
    }

    public void setFechaInscripcion(Date fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

//______________________________________________________________________________
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "id_asignacion_estudiante_carrera",
        nullable = false
    )
    public AsignacionEstudianteCarrera getAsignacionEstudianteCarrera() {
        return asignacionEstudianteCarrera;
    }


    public void setAsignacionEstudianteCarrera(AsignacionEstudianteCarrera asignacionEstudianteCarrera) {
        this.asignacionEstudianteCarrera = asignacionEstudianteCarrera;
    }
//______________________________________________________________________________
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "id_semestre",
        nullable = false
    )
    public Semestre getSemestre() {
        return semestre;
    }

    public void setSemestre(Semestre semestre) {
        this.semestre = semestre;
    }
//______________________________________________________________________________
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "id_situacion",
        nullable = false
    )
    public Situacion getSituacion() {
        return situacion;
    }

    public void setSituacion(Situacion situacion) {
        this.situacion = situacion;
    }    
}
