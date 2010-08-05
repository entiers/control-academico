/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.util;

import gt.edu.usac.trabajosocial.dao.DaoGeneral;
import gt.edu.usac.trabajosocial.dominio.Log;
import gt.edu.usac.trabajosocial.dominio.Usuario;
import gt.edu.usac.trabajosocial.seguridad.UserDetailsImpl;
import java.util.Date;
import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author Daniel Castillo
 * @version 1.0
 */
public class SpringHibernateAppender extends AppenderSkeleton implements Appender {
    
    /** Bean encargado de los accesos a la base de datos */
    private DaoGeneral daoGeneralImpl;


    /**
     * <p>Crea una nueva instancia de la clase.</p>
     */
    public SpringHibernateAppender () {}
//______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cada vez que alguna de las clases contenidas en
     * el paquete <code>org.ui</code> hace una peticion de log. El metodo
     * se encarga de obtener toda la informacion del log generado y crea el
     * registro correspondiente en la tabla Log de la base de datos.</p>
     *
     * @param event LoggingEvent evento generado cada vez que se hace una
     *        peticion de log
     */
    @Override
    protected void append(LoggingEvent event) {
        try {
            // se verifica la inyeccion del bean encargado del acceso a la base de datos
            if(this.daoGeneralImpl == null)
                this.inyectarDependencia();

            // se registra el evento de log
            Log log = this.getLog(event);
            if(log != null)
                this.guardarLog(log);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cuando se cierra este Appender. Se encarga
     * de liberar los recursos utilizados.</p>
     */
    @Override
    public void close() {}
//______________________________________________________________________________
    /**
     * <p>Indica se de debe de utilizar un Layout para el formateo de los
     * mensajes de log.</p>
     * @return true si y solo si se debe de usar un Layout
     */
    @Override
    public boolean requiresLayout() {
        return false;
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de obtener la informacion de la solicitud de
     * log y crear, en base a esta informacion, el objeto POJO correspondiente
     * a la tabla de Log en la base de datos.</p>
     *
     * @param event LoggingEvent evento generado cada vez que se hace una
     *        peticion de log
     * @return
     */
    private Log getLog(LoggingEvent event) {
        try {
            // se crea el POJO
            Log log = new Log();
            log.setFecha(new Date());
            log.setPrioridad(event.getLevel().toString());
            log.setLogger(event.getLoggerName());
            log.setMensaje(event.getRenderedMessage());

            return log;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de obtener la instancia del DAO que contiene
     * Spring. Esto se hace debido a que Log4j se inicia antes que Spring, por lo
     * tanto no se puede inyectar la dependencia en el momento que se inicia
     * esta clase. Entonces la forma de obtener una instancia de la clase DAO, es
     * en tiempo de ejecucion accediendo al contexto de la aplicacion para luego
     * solitar el contexto de Spring y de ultimo obtener el bean.</p>
     */
    private void inyectarDependencia() {
        // se obtiene el contexto de Spring
        WebApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();

        // se solicita la inyeccion de dependencia
        this.daoGeneralImpl = (DaoGeneral) applicationContext.getBean("daoGeneralImpl");
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de guardar la informacion del log en la
     * base de datos.</p>
     * 
     * @param log
     */
    private void guardarLog(Log log) {
        // se obtiene el usuario que genero el evento
        log.setUsuario(this.getUsuario());

        // se almacena el log
        this.daoGeneralImpl.save(log);
    }
//______________________________________________________________________________
    /**
     * <p>Obtiene el {@link Usuario} asociado a la sesion.</p>
     *
     * @return Usuario
     */
    private Usuario getUsuario() {
        // se obtiene el contexto de seguridad para obtener el
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();

        return userDetails.getUsuario();
    }
}
