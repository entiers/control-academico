/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cats.util;

import gt.edu.usac.cats.velocity.FabricaTemplateVelocity;
import gt.edu.usac.cats.velocity.contexto.NuevoUsuario;
import gt.edu.usac.cats.velocity.contexto.ResumenAsignacionPrimerIngreso;
import gt.edu.usac.cats.velocity.contexto.DesasignacionEstudianteCurso;
import java.util.List;
import java.util.ArrayList;
import gt.edu.usac.cats.velocity.contexto.extras.HorarioCurso;
import gt.edu.usac.cats.velocity.contexto.InformacionAsignacionEstudiante;
import java.util.Date;
import gt.edu.usac.cats.velocity.contexto.RecordatorioContrasenya;
import java.io.Serializable;
import java.io.IOException;
import javax.mail.MessagingException;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.mail.MailException;

/**
 *
 * @author shakamca
 */
public class EmailSenderVelocityTest extends TestCase {

    public EmailSenderVelocityTest(String testName) {
        super(testName);
    }

    @Before
    @Override
    public void setUp() {
    }

    @After
    @Override
    public void tearDown() {
    }

    public void testApp() throws IOException, MailException, MessagingException  {
        /*EmailSenderVelocity ev = new EmailSenderVelocity();
        
        ev.enviarCorreo("RESUMEN_ASIGNACION_PRIMER_INGRESO", "shaka.mca2407@gmail.com",
                FabricaTemplateVelocity.RESUMEN_ASIGNACION_PRIMER_INGRESO,
                this.getDataResumenAsignacionPrimerIngreso());*/
        

    }

    public Serializable getDataNuevoUsuario() {
        NuevoUsuario emailNuevoUsuario = new NuevoUsuario();
        emailNuevoUsuario.setNombre("Mario Batres");
        emailNuevoUsuario.setNombreUsuario("shakamca");
        emailNuevoUsuario.setPassword("felicia");
        //emailNuevoUsuario.setCarnet(null);
        emailNuevoUsuario.setCarnet("200313171");

        return emailNuevoUsuario;
    }

    public Serializable getDataResumenAsignacionPrimerIngreso() {
        ResumenAsignacionPrimerIngreso rapi = new ResumenAsignacionPrimerIngreso();

        rapi.setFechaFin(new Date());
        rapi.setFechaInicio(new Date());
        rapi.setTotalAsignados(15);
        rapi.setTotalNoAsignados(20);


        return rapi;
    }

    public Serializable getDataDesasignacionEstudianteCurso() {
        DesasignacionEstudianteCurso edc = new DesasignacionEstudianteCurso("2003131171", "Mario Batres");


        List<HorarioCurso> listadoHorarioCursos = new ArrayList();

        listadoHorarioCursos.add(new HorarioCurso("01", "Curso 1", "A"));
        listadoHorarioCursos.add(new HorarioCurso("02", "Curso 2", "B"));
        listadoHorarioCursos.add(new HorarioCurso("03", "Curso 3", "C"));


        edc.setListadoHorarioCursos(listadoHorarioCursos);
        return edc;
    }

    public Serializable getDataRecordatorioUsuario() {
        return "shakamca";
    }

    public Serializable getDataInformacionAsignacionEstudiante() {
        return new InformacionAsignacionEstudiante("200313171", "Mario Batres", new Date(), "123-456-789-01234");
    }

    public Serializable getDataRecordatorioContrasenya() {
        return new RecordatorioContrasenya(1, "12345");
    }
}
