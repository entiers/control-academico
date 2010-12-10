/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.etl;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
public enum FabricaManejadorETL{
    REGISTRO_CSV;

    public ManejadorETL crear(){
        switch(this){
            case REGISTRO_CSV:
                return new ManejadorRegistroCSV();
        }
        return null;
    }
}
