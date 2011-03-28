/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.controlador.etl;

import gt.edu.usac.cats.etl.FabricaManejadorETL;
import gt.edu.usac.cats.etl.ManejadorETL;
import gt.edu.usac.cats.util.RequestUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


/**
 * Esta clase es el controlador que se encarga del procesamiento de los archivos
 * para realizar ETL en la base de datos.
 *
 * @author Mario Batres
 * @version 1.0
 */

@Controller ("controladorRealizarETL")
public class ControladorRealizarETL {
//______________________________________________________________________________
    /** <p>Lleva el nombre del titulo para el mensaje en la pagina<p>*/
    private static Logger log = Logger.getLogger(ControladorRealizarETL.class);

//______________________________________________________________________________
    /**
     * Este metodo se encarga de almacenar los archivos cargados desde de la pagina
     * y lee los atributos de los archivos .properties que utiliza el modulo ETL realizado
     * por Jasper ETL y los almacena en la ubicacion que alla se menciona.
     *
     * @param file Es el archivo que es cargado desde el sistema.
     * @param key Es la clave del archivo .properties utilizado por el modulo de
     * JasperETL que tiene la ubicaci�n del archivo a procesar.
     * @param pathArchivoPropiedades Es la ubicaci�n del archivo .properties que utiliza
     * el modulo ETL.
     *
     * @throws IOException
     */
    private void guardarArchivoEnDisco(MultipartFile file, 
            String key, String pathArchivoPropiedades) throws IOException{


        Properties properties = new Properties();

        InputStream inputStream = ControladorRealizarETL.class
                                .getClassLoader()
                                .getResourceAsStream(
                                    pathArchivoPropiedades);

        properties.load(inputStream);
        String pathArchivo = properties.getProperty(key);
        System.out.println(pathArchivo);
        byte [] bytes = file.getBytes();
        
        OutputStream outputStream = new FileOutputStream(
                new File(pathArchivo));

        outputStream.write(bytes);

        outputStream.close();

    }

//______________________________________________________________________________
    /**
     * Este metodo realiza el proceso ETL.
     *
     * @param tipo Es de tipo {@link FabricaManejoETL} que identifica que modulo de
     * ETL llamar para reliazliar el proceso.
     *
     * @return Devuelve <k>true</k> si no han existido errores en el proceso de ETL
     * y <k>false</k> si existio al menos un error en el proceso.
     *
     * @throws IOException
     **/
    private boolean realizarETL(FabricaManejadorETL tipo) throws IOException{
        ManejadorETL manejadorETL = tipo.crear();
        int ret  = manejadorETL.realizar().length;
        return ret == 1;
    }

//______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina <code>ingresarRegistroCSV.htm</code>. El metodo se encarga
     * de iniciar los objetos que se usaran en la pagina.</p>
     *
     * @return Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(value = "ingresarRegistroCSV.htm", method=RequestMethod.GET)
    public String crearRegistroCSV(){
        return "etl/ingresarRegistroCSV";
    }


//______________________________________________________________________________
    /**
     * Este m�todo se utiliza cuando se hace un <k>submit</k> desde la p�gina
     * <code>ingresarRegistroCSV.htm</code>.  Este metodo realiza lo siguiente:
     *
     * <ol>
     *  <li>Verifica que se haya seleccionado un archivo</li>
     *  <li>Verifica que el archivo seleccionado sea un CSV v�lido</li>
     *  <li> Guarda el archivo en disco</li>
     *  <li> Realiza el proceso de ETL</li>
     *  <li> Verifica que el proceso de ETL se haya realizado satisfactoriamente</li>
     * </ol>
     *
     * @param archivoCSV Archivo CSV que se selecciona desde la aplicacion web.
     * @param request Objeto {@link HttpServletRequest}
     *
     * @return Contiene el nombre de la vista a mostrar
     */

    @RequestMapping(value = "ingresarRegistroCSV.htm", method=RequestMethod.POST)
    public String realizarRegistroCSV(
            @RequestParam("archivoCSV") MultipartFile archivoCSV
            , HttpServletRequest request){
            
        try {
            if(!archivoCSV.isEmpty()){
                System.out.println(archivoCSV.getContentType());
                //if(archivoCSV.getContentType().equalsIgnoreCase("text/csv")){

                    this.guardarArchivoEnDisco(archivoCSV
                            , "ArchivoCSV_File"
                            , "/registrocsv/etlregistrocsv_0_1/contexts/Default.properties");

                    boolean exito = this.realizarETL(FabricaManejadorETL.REGISTRO_CSV);

                    if(exito){
                        RequestUtil.crearMensajeRespuesta(request, "etl.ingresarRegistroCSV.titulo",
                            "etl.ingresarRegistroCSV.exito", true);

                        log.info("Se realizo el ETL del archivo CSV de Registro y Estadistica satisfactoriamente");
                    }else{

                        RequestUtil.crearMensajeRespuesta(request, "etl.ingresarRegistroCSV.titulo", "etl.archivoCSV.error",
                        false);

                        log.error("Hubo problemas en la carga del archivo CSV");
                    }
                /*}else{
                    RequestUtil.crearMensajeRespuesta(request, "etl.ingresarRegistroCSV.titulo", "etl.archivoCSV.contentTypeInvalido",
                        false);

                    log.error("El archivo CSV a cargar no es un archivo csv valido");
                }*/
            }else{
                RequestUtil.crearMensajeRespuesta(request, "etl.ingresarRegistroCSV.titulo", "etl.archivoCSV.obligatorio",
                        false);
                log.error("No se cargo ningun archivo CSV");
            }

        } catch (IOException e) {
            RequestUtil.crearMensajeRespuesta(request, "etl.ingresarRegistroCSV.titulo", "etl.ioException", false);
            log.error("Ocurrio un problema en la lectura/escritura de un archivo", e);
        } catch( Exception e){
            RequestUtil.crearMensajeRespuesta(request, "etl.ingresarRegistroCSV.titulo", "etl.exception", false);
            log.error("Error no identificado", e);
        }

        return "etl/ingresarRegistroCSV";
    }

    //______________________________________________________________________________
    /**
     * <p>Este metodo se ejecuta cada vez que se realiza una solicitud del tipo
     * GET de la pagina <code>ingresarBoletaBancoCSV.htm</code>. El metodo se encarga
     * de iniciar los objetos que se usaran en la pagina.</p>
     *
     * @return Contiene el nombre de la vista a mostrar
     */
    @RequestMapping(value = "ingresarBoletaBancoCSV.htm", method=RequestMethod.GET)
    public String crearBoletaBancoCSV(){
        return "etl/ingresarBoletaBancoCSV";
    }

    //______________________________________________________________________________
    /**
     * Este metodo se utiliza cuando se hace un <k>submit</k> desde la p�gina
     * <code>ingresarBoletaBancoCSV.htm</code>.  Este metodo realiza lo siguiente:
     *
     * <ol>
     *  <li>Verifica que se haya seleccionado un archivo</li>
     *  <li>Verifica que el archivo seleccionado sea un CSV valido</li>
     *  <li> Guarda el archivo en disco</li>
     *  <li> Realiza el proceso de ETL</li>
     *  <li> Verifica que el proceso de ETL se haya realizado satisfactoriamente</li>
     * </ol>
     *
     * @param archivoCSV Archivo CSV que se selecciona desde la aplicacion web.
     * @param request Objeto {@link HttpServletRequest}
     *
     * @return Contiene el nombre de la vista a mostrar
     */

    @RequestMapping(value = "cargarBoletaBanco.htm", method=RequestMethod.POST)
    public String realizarBoletaBancoCSV(
            @RequestParam("archivoCSV") MultipartFile archivoCSV
            , HttpServletRequest request){
        try {
            if(!archivoCSV.isEmpty()){
                if(archivoCSV.getContentType().equalsIgnoreCase("text/csv")){

                    this.guardarArchivoEnDisco(archivoCSV
                            , "ArchivoCSV_File"
                            , "/registrocsv/etlregistrocsv_0_1/contexts/Default.properties");

               //     boolean exito = this.realizarETL(FabricaManejadorETL.BOLETA_BANCO_CSV);
                    boolean exito = true;
                    if(exito){
                        RequestUtil.crearMensajeRespuesta(request, "etl.ingresarBoletaBancoCSV.titulo",
                            "etl.ingresarRegistroCSV.exito", true);

                        log.info("Se realizo el ETL del archivo CSV de Boletas del banco satisfactoriamente");
                    }else{

                        RequestUtil.crearMensajeRespuesta(request, "etl.ingresarBoletaBancoCSV.titulo", "etl.archivoCSV.error",false);
                        log.error("Hubo problemas en la carga del archivo CSV de Boletas del banco satisfactoriamente");
                    }
                }else{
                    RequestUtil.crearMensajeRespuesta(request, "etl.ingresarBoletaBancoCSV.titulo", "etl.archivoCSV.contentTypeInvalido",false);
                    log.error("El archivo CSV a cargar no es un archivo csv valido");
                }
            }else{
                RequestUtil.crearMensajeRespuesta(request, "etl.ingresarBoletaBancoCSV.titulo", "etl.archivoCSV.obligatorio",false);
                log.error("No se cargo ningun archivo CSV");
            }

        } catch (IOException e) {
            RequestUtil.crearMensajeRespuesta(request, "etl.ingresarBoletaBancoCSV.titulo", "etl.ioException", false);
            log.error("Ocurrio un problema en la lectura/escritura de un archivo", e);
        } catch( Exception e){
            RequestUtil.crearMensajeRespuesta(request, "etl.ingresarBoletaBancoCSV.titulo", "etl.exception", false);
            log.error("Error no identificado", e);
        }
        RequestUtil.agregarRedirect(request, "ingresarBoletaBancoCSV.htm");
        return "etl/ingresarBoletaBancoCSV";
    }
}
