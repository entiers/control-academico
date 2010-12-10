/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.etl;

import registrocsv.etlregistrocsv_0_1.ETLRegistroCSV;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
public class ManejadorRegistroCSV extends ManejadorETL {

    @Override
    protected String[][] realizarETL() {
        ETLRegistroCSV etl = new ETLRegistroCSV();
        String [] args1 = {""};
        String [][] ret = etl.runJob(args1);
        return ret;
    }

}
