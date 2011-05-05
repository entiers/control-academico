package gt.edu.usac.cats.velocity;

import gt.edu.usac.cats.velocity.contexto.DesasignacionEstudianteCurso;
import gt.edu.usac.cats.velocity.contexto.InformacionAsignacionEstudiante;
import gt.edu.usac.cats.velocity.contexto.NuevoUsuario;
import gt.edu.usac.cats.velocity.contexto.RecordatorioContrasenya;
import gt.edu.usac.cats.velocity.contexto.ResumenAsignacionPrimerIngreso;
import gt.edu.usac.cats.velocity.contexto.extras.HorarioCurso;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() throws IOException
    {
        
/*
        
        TemplateVelocity tm = FabricaTemplateVelocity.NUEVO_USUARIO.crear(getDataNuevoUsuario());
        Writer writer = tm.transformarTemplate();
        System.out.println(writer);
        System.out.println("------------------------------------------------");

        tm = FabricaTemplateVelocity.RESUMEN_ASIGNACION_PRIMER_INGRESO.crear(getDataResumenAsignacionPrimerIngreso());
        writer = tm.transformarTemplate();
        System.out.println(writer);
        System.out.println("------------------------------------------------");

        tm = FabricaTemplateVelocity.DESASIGNACION_ESTUDIANTE_CURSO.crear(getDataDesasignacionEstudianteCurso());
        writer = tm.transformarTemplate();
        System.out.println(writer);
        System.out.println("------------------------------------------------");

        tm = FabricaTemplateVelocity.RECORDATORIO_USUARIO.crear(getDataRecordatorioUsuario());
        writer = tm.transformarTemplate();
        System.out.println(writer);
        System.out.println("------------------------------------------------");

        tm = FabricaTemplateVelocity.INFORMACION_ASIGNACION_ESTUDIANTE.crear(getDataInformacionAsignacionEstudiante());
        writer = tm.transformarTemplate();
        System.out.println(writer);
        System.out.println("------------------------------------------------");

        tm = FabricaTemplateVelocity.RECORDATORIO_CONTRASENYA.crear(getDataRecordatorioContrasenya());
        writer = tm.transformarTemplate();
        System.out.println(writer);
        System.out.println("------------------------------------------------");*/

        //assertTrue( true );
    }

    public Serializable getDataNuevoUsuario(){
        NuevoUsuario emailNuevoUsuario = new NuevoUsuario();
        emailNuevoUsuario.setNombre("Mario Batres");
        emailNuevoUsuario.setNombreUsuario("shakamca");
        emailNuevoUsuario.setPassword("felicia");
        //emailNuevoUsuario.setCarnet(null);
        emailNuevoUsuario.setCarnet("200313171");

        return emailNuevoUsuario;
    }

    public Serializable getDataResumenAsignacionPrimerIngreso(){
        ResumenAsignacionPrimerIngreso rapi = new ResumenAsignacionPrimerIngreso();

        rapi.setFechaFin(new Date());
        rapi.setFechaInicio(new Date());
        rapi.setTotalAsignados(15);
        rapi.setTotalNoAsignados(20);


        return rapi;
    }

    public Serializable getDataDesasignacionEstudianteCurso(){
        DesasignacionEstudianteCurso edc = new DesasignacionEstudianteCurso("2003131171", "Mario Batres");


        List <HorarioCurso> listadoHorarioCursos = new ArrayList();
        
        listadoHorarioCursos.add(new HorarioCurso("01", "Curso 1", "A"));
        listadoHorarioCursos.add(new HorarioCurso("02", "Curso 2", "B"));
        listadoHorarioCursos.add(new HorarioCurso("03", "Curso 3", "C"));


        edc.setListadoHorarioCursos(listadoHorarioCursos);
        return edc;
    }

    public Serializable getDataRecordatorioUsuario(){
        return "shakamca";
    }

    public Serializable getDataInformacionAsignacionEstudiante(){
        return new InformacionAsignacionEstudiante("200313171", "Mario Batres", new Date(), "123-456-789-01234");
    }

    public Serializable getDataRecordatorioContrasenya(){
        return new RecordatorioContrasenya(1, "12345");
    }
}
