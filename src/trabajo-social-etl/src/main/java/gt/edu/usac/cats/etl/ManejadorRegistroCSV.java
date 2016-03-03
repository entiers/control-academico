/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.etl;

//import cats.etlregistrocsv_0_1.ETLRegistroCSV;
import cats.etlregistrocsv_0_1.ETLRegistroCSV;

import com.csvreader.CsvReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Properties;
//import registrocsv.etlregistrocsv_0_1.ETLRegistroCSV;

/**
 * Clase que hereda el comportamiento de {@link ManejadorETL}, esta clase lleva
 * el control del llamado el m�dulo {@link ETLRegistroCSV} que realiza el ETL
 * del archivo CSV que proporciona Registro y Estad�stica con el control de los
 * estudiantes incritos (nuevos y de reingreso).
 *
 * @author Mario Batres
 * @version 1.0
 * @see ManejadorETL
 */
public class ManejadorRegistroCSV extends ManejadorETL {

    /**
     * Metodo que se debe de sobreescribir para indicar que modulo de ETL se
     * estara usando.
     *
     * @return Arreglo bidimensional de cadenas que retornan lo ocurrido en la
     * realizacion del proceso.
     * 
     * @throws IOException
     */
    @Override
    protected String[][] realizarETL() {
        ETLRegistroCSV etl = new ETLRegistroCSV();
        String [] args1 = {""};
        String [][] ret = etl.runJob(args1);

        try{
            this.enviarEmails();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        return ret;
    }

    /**
     *
     *
     */
    private void enviarEmails() throws FileNotFoundException, IOException{
        Properties properties = new Properties();
        properties.load(ManejadorRegistroCSV.class.
                getResourceAsStream("/registrocsv/jobusuario_0_1/contexts/Default.properties"));


        CsvReader reader = new CsvReader(
                new FileInputStream(properties.getProperty("SalidaUsuariosNuevos_File")),
                properties.getProperty("SalidaUsuariosNuevos_FieldSeparator").charAt(0),
                Charset.forName(properties.getProperty("SalidaUsuariosNuevos_Encoding")));

        reader.readHeaders();

        while(reader.readRecord()){
            String nombreUsuario = reader.get("nombre_usuario");
            String carne = reader.get("carne");
            String nombre = reader.get("nombre");
            String email = reader.get("email");
            String password = reader.get("password");
            
          

            StringBuilder builder = new StringBuilder();

            builder.append("SendEmail(nombreUsuario = ").append(nombreUsuario);
            builder.append(", carne = ").append(carne);
            builder.append(", nombre = ").append(nombre);
            builder.append(", email = ").append(email);
            builder.append(", password = ").append(password).append(")");

            System.out.println(builder.toString());

        }

        reader.close();
    }


}
