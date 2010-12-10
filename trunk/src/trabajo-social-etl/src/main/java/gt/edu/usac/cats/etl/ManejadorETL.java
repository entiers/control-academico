/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.etl;

import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
public abstract class ManejadorETL {  

//______________________________________________________________________________
    protected abstract String [][] realizarETL();
//______________________________________________________________________________
    /*private void abrirSocket() throws IOException{
        if (serverSocket == null || serverSocket.isClosed()){
            serverSocket = new ServerSocket(3334);
        }
    }
//______________________________________________________________________________
    private void cerrarSocket(){
        if (serverSocket != null && !serverSocket.isClosed()){
            serverSocket.isClosed();
        }
    }*/
//______________________________________________________________________________
    public String [][] realizar() throws IOException{
        ServerSocket serverSocket = new ServerSocket(3334);        
        String [][]ret = this.realizarETL();
        serverSocket.close();
        return ret;
    }
//______________________________________________________________________________

}
