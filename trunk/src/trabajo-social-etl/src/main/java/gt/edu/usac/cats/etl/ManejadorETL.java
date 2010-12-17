/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.etl;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Clase abstracta que lleva maneja el comportamiento para el llamado a las
 * clases que realizan los procesos de ETL.
 * <br/><br/>
 * Se utiliza el puerto 3334 para la apertura de un socket que es utilizado
 * por los diferentes procesos de ETL
 *
 * @author Mario Batres
 * @version 1.0
 */
public abstract class ManejadorETL {  

//______________________________________________________________________________
    /**
     * Método abstracto que obliga a implementarlo en las clases hijas.  Su función
     * prinicipal es llamar a las clases de los diferentes procesos ETL que existen.
     *
     * @return Arreglo bidimensional de cadenas que retornan lo ocurrido en la
     * realización del proceso.
     * 
     * @throws IOException
     */
    protected abstract String [][] realizarETL();
//______________________________________________________________________________
    /**
     * Método que se encarga de la apertura del socket para realizar el ETL Y se
     * hace el llamado al método <b>realizarETL</b> que es implementado en las clases
     * hijas.
     *
     * @return De tipo <b>String [][]</b>. Arreglo bidimensional de cadenas que
     * retornan lo ocurrido en la realización del proceso.
     *
     * @throws IOException
     */
    public String [][] realizar() throws IOException{
        ServerSocket serverSocket = null;
        String [][] ret = new String [0][0];
        try{
            serverSocket = new ServerSocket(3334);
            ret = this.realizarETL();
        }catch(IOException e){
            throw e;
        }finally{
            if(serverSocket != null){
                serverSocket.close();
            }
        }
        return ret;
        
    }
//______________________________________________________________________________
}
