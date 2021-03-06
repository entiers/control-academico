/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.servicio.impl;

import gt.edu.usac.cats.dominio.Asignacion;
import gt.edu.usac.cats.dominio.AsignacionCursoPensum;
import gt.edu.usac.cats.dominio.AsignacionEstudianteCarrera;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.Horario;
import gt.edu.usac.cats.dominio.DetalleAsignacion;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.enums.TipoAsignacion;
import gt.edu.usac.cats.servicio.ServicioDetalleAsignacion;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;
import org.springframework.stereotype.Service;

/**
 * <p></p>
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
@Service("servicioDetalleAsignacionImpl")
public class ServicioDetalleAsignacionImpl extends ServicioGeneralImpl implements ServicioDetalleAsignacion{
//______________________________________________________________________________
    @Override
    public Integer getCountDetalleAsignacionXHorario(Horario horario) throws HibernateException {
        StringBuilder builder = new StringBuilder();
        builder.append("select count(dta) from DetalleAsignacion as dta")
               .append(" where dta.horario = :horario");

        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("horario", horario);
        
        return Integer.valueOf(query.list().get(0).toString());
    }
//______________________________________________________________________________
    @Override
    public List<DetalleAsignacion> getListadoDetalleAsignacion(Integer idHorario) throws HibernateException {
        StringBuilder builder = new StringBuilder();

        builder.append(" select deta from DetalleAsignacion deta ")
               .append(" where deta.horario.idHorario = :idHorario ")
               .append(" order by deta.asignacion.asignacionEstudianteCarrera.estudiante.carne");

        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("idHorario", idHorario);

        return query.list();
    }
//______________________________________________________________________________
    @Override
    public void cambioCierreSeccion(Horario horario,
                                    List lstIdDetalleAsignacion) throws HibernateException{
        DetalleAsignacion detAsign;
        for(Object idDetAsign: lstIdDetalleAsignacion){
            detAsign = this.cargarEntidadPorID(DetalleAsignacion.class, Integer.valueOf(idDetAsign.toString()));
            detAsign.setHorario(horario);
            this.actualizar(detAsign);
        }
    }
//______________________________________________________________________________
    @Override
    public List<DetalleAsignacion> getListadoDetalleAsignacion(AsignacionCursoPensum asignacionCursoPensum, Semestre semestre, AsignacionEstudianteCarrera asignacionEstudianteCarrera, TipoAsignacion tipoAsignacion) throws HibernateException {
        StringBuilder builder = new StringBuilder();
        builder.append("select det from DetalleAsignacion det ")
               .append("where det.horario.asignacionCursoPensum = :asignacionCursoPensum ")
               .append("and det.horario.semestre= :semestre ")
               .append("and det.asignacion.asignacionEstudianteCarrera = :asignacionEstudianteCarrera ")
               .append("and det.asignacion.tipoAsignacion = :tipoAsignacion ")
               .append("and not exists (select 'x' from Desasignacion des where des.detalleAsignacion=det)");

        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("asignacionCursoPensum", asignacionCursoPensum);
        query.setParameter("semestre", semestre);
        query.setParameter("asignacionEstudianteCarrera", asignacionEstudianteCarrera);
        query.setParameter("tipoAsignacion", tipoAsignacion);

        return query.list();
    }
    
    @Override
    public List<DetalleAsignacion> getListadoDetalleAsignacion(AsignacionCursoPensum asignacionCursoPensum, 
    Semestre semestre, AsignacionEstudianteCarrera asignacionEstudianteCarrera, 
    TipoAsignacion tipoAsignacion, String seccion) throws HibernateException {
        StringBuilder builder = new StringBuilder();
        builder.append("select det from DetalleAsignacion det ")
               .append("where det.horario.asignacionCursoPensum = :asignacionCursoPensum ")
               .append("and det.horario.semestre= :semestre ")
               .append("and det.asignacion.asignacionEstudianteCarrera = :asignacionEstudianteCarrera ")
               .append("and det.asignacion.tipoAsignacion = :tipoAsignacion ")
               .append("and det.horario.seccion = :seccion ") 
               .append("and not exists (select 'x' from Desasignacion des where des.detalleAsignacion=det)");

        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("asignacionCursoPensum", asignacionCursoPensum);
        query.setParameter("semestre", semestre);
        query.setParameter("asignacionEstudianteCarrera", asignacionEstudianteCarrera);
        query.setParameter("tipoAsignacion", tipoAsignacion);
        query.setParameter("seccion", seccion);

        return query.list();
    }    
//______________________________________________________________________________
    @Override
    public int getTotalAsignaciones(AsignacionCursoPensum asignacionCursoPensum, AsignacionEstudianteCarrera asignacionEstudianteCarrera, TipoAsignacion tipoAsignacion) throws HibernateException {
        StringBuilder builder = new StringBuilder();

        builder.append("select count(*) from DetalleAsignacion det ")
               .append("where det.horario.asignacionCursoPensum = :asignacionCursoPensum ")
               .append("and det.asignacion.asignacionEstudianteCarrera = :aec ")
               .append("and det.asignacion.tipoAsignacion = :tipoAsignacion ")
               .append("and det.horario.maestro = true ") 
               .append("and not exists (select 'x' from Desasignacion des where des.detalleAsignacion=det)");

        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("asignacionCursoPensum", asignacionCursoPensum);
        query.setParameter("aec", asignacionEstudianteCarrera);
        query.setParameter("tipoAsignacion", tipoAsignacion);
        
        List result = query.list();
        if(result.isEmpty())
            return 0;
        else if(result.get(0) == null)
            return 0;
        else
            return Integer.parseInt(result.get(0).toString());
    }
//______________________________________________________________________________
    @Override
    public List<DetalleAsignacion> getListadoDetalleAsignacion(Asignacion asignacion) throws HibernateException {
        StringBuilder builder = new StringBuilder();
        builder.append("select det from DetalleAsignacion det ")
               .append("where det.asignacion = :asignacion ")
               .append("and not exists (select 'x' from Desasignacion des where des.detalleAsignacion=det)");

        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("asignacion", asignacion);

        return query.list();
    }
//______________________________________________________________________________
    @Override
    public List<DetalleAsignacion> getListadoDetalleAsignacion(Semestre semestre, AsignacionEstudianteCarrera asignacionEstudianteCarrera, TipoAsignacion tipoAsignacion) throws HibernateException {
        StringBuilder builder = new StringBuilder();
        builder.append("select det from DetalleAsignacion det ")
               .append("where det.horario.semestre= :semestre ")
               .append("and det.asignacion.asignacionEstudianteCarrera = :asignacionEstudianteCarrera ")
               .append("and det.asignacion.tipoAsignacion = :tipoAsignacion ")
               .append("and not exists (select 'x' from Desasignacion des where des.detalleAsignacion=det)");

        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("semestre", semestre);
        query.setParameter("asignacionEstudianteCarrera", asignacionEstudianteCarrera);
        query.setParameter("tipoAsignacion", tipoAsignacion);

        return query.list();
    }
//______________________________________________________________________________
    @Override
    public List<DetalleAsignacion> getListadoDetalleAsignacion(Semestre semestre, Estudiante estudiante, TipoAsignacion tipoAsignacion) throws HibernateException {
        StringBuilder builder = new StringBuilder();
        builder.append("select det from DetalleAsignacion det ")
               .append("where det.horario.semestre= :semestre ")
               .append("and det.asignacion.asignacionEstudianteCarrera.estudiante = :estudiante ")
               .append("and det.asignacion.tipoAsignacion = :tipoAsignacion ")
               .append("and not exists (select 'x' from Desasignacion des where des.detalleAsignacion=det)");

        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("semestre", semestre);
        query.setParameter("estudiante", estudiante);
        query.setParameter("tipoAsignacion", tipoAsignacion);

        return query.list();
    }
//______________________________________________________________________________
    @Override
    public boolean tieneZonaMinima(AsignacionCursoPensum asignacionCursoPensum, AsignacionEstudianteCarrera asignacionEstudianteCarrera) throws HibernateException, IOException {
        // se carga la configuracion
        Properties properties = this.cargarProperties();
        int zonaMinima = Integer.valueOf(properties.getProperty("asignacion.zonaMinima"));

        StringBuilder builder = new StringBuilder();
        builder.append("select max(zona) from DetalleAsignacion det ")
               .append("where det.asignacion.asignacionEstudianteCarrera = :asignacionEstudianteCarrera ")
               .append("and det.horario.asignacionCursoPensum = :asignacionCursoPensum ")
               .append("and not exists (select 'x' from Desasignacion des where des.detalleAsignacion=det)");

        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("asignacionCursoPensum", asignacionCursoPensum);
        query.setParameter("asignacionEstudianteCarrera", asignacionEstudianteCarrera);

        List result = query.list();
        if(result.isEmpty() || result.get(0) == null)
            return false;
        else
            return Integer.valueOf(result.get(0).toString()) >= zonaMinima;
    }
//______________________________________________________________________________
    @Override
    public boolean tieneZonaMinima(AsignacionCursoPensum asignacionCursoPensum, AsignacionEstudianteCarrera asignacionEstudianteCarrera, Semestre semestre) throws HibernateException, IOException {
        // se carga la configuracion
        Properties properties = this.cargarProperties();
        int zonaMinima = Integer.valueOf(properties.getProperty("asignacion.zonaMinima"));

        StringBuilder builder = new StringBuilder();
        builder.append("select max(zona) from DetalleAsignacion det ")
               .append("where det.asignacion.asignacionEstudianteCarrera = :asignacionEstudianteCarrera ")
               .append("and det.horario.asignacionCursoPensum = :asignacionCursoPensum ")
               .append("and det.horario.semestre = :semestre ")
               .append("and not exists (select 'x' from Desasignacion des where des.detalleAsignacion=det)");

        Query query = this.daoGeneralImpl.getSesion().createQuery(builder.toString());
        query.setParameter("asignacionCursoPensum", asignacionCursoPensum);
        query.setParameter("asignacionEstudianteCarrera", asignacionEstudianteCarrera);
        query.setParameter("semestre", semestre);

        List result = query.list();
        if(result.isEmpty())
            return false;
        else if(result.get(0) == null)
            return false;
        else
            return Integer.valueOf(result.get(0).toString()) >= zonaMinima;
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de leer el archivo de propiedades que contiene
     * la configuracion de JavaMail para el envio de correos electronicos.</p>
     *
     * @param request Obejto HttpServletRequest
     * @return Properties Contiene la configuracion para JavaMail
     * @throws IOException Si no se pudo leer el archivo de propiedades
     */
    private Properties cargarProperties() throws IOException {
        // se crea el desencriptador
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("APP_ENCRYPTION_PASSWORD");

        // se crea el archivo de propiedades
        Properties properties = new EncryptableProperties(encryptor);
        properties.load(this.getClass().getResourceAsStream("/gt/edu/usac/cats/util/ConfProcesosNegocio.properties"));

        return properties;
    }
//______________________________________________________________________________     
     /**
      * <p>Metodo para realizar la eliminacion de los detalles de asignacion para una asignacion determinada</p>
      * @param listadoEliminacion
      * @return
      * @throws HibernateException
      * @throws IOException 
      */
    @Override
    public void eliminarDetalleAsignacion(List<DetalleAsignacion> listadoEliminacion) throws HibernateException {        
        if(!listadoEliminacion.isEmpty()){            
            Asignacion asignacion = this.cargarEntidadPorID(Asignacion.class, listadoEliminacion.get(0).getAsignacion().getIdAsignacion());
            
            for(DetalleAsignacion detalleAsignacion : listadoEliminacion){
                
                //AsignacionCursoPensum asignacionCursoPensum, 
                //Semestre semestre, AsignacionEstudianteCarrera asignacionEstudianteCarrera, 
                //TipoAsignacion tipoAsignacion, String seccion
                List<DetalleAsignacion> detalles = this.getListadoDetalleAsignacion(detalleAsignacion.getHorario().getAsignacionCursoPensum(),
                        detalleAsignacion.getHorario().getSemestre(), detalleAsignacion.getAsignacion().getAsignacionEstudianteCarrera(),
                        detalleAsignacion.getAsignacion().getTipoAsignacion(),
                        detalleAsignacion.getHorario().getSeccion());
                for (DetalleAsignacion det2:detalles){
                    this.daoGeneralImpl.delete(det2);
                }
                //this.daoGeneralImpl.delete(detalleAsignacion);
            }
            if(this.getListadoDetalleAsignacion(asignacion).isEmpty()){
                this.daoGeneralImpl.delete(asignacion);
            }
        }        
    }
}
