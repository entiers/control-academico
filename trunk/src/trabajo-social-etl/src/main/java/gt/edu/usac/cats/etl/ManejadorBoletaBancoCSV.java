/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.etl;

import registrocsv.etlboletabanco_0_1.ETLBoletaBanco;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
public class ManejadorBoletaBancoCSV extends ManejadorETL{

    @Override
    protected String[][] realizarETL() {
        ETLBoletaBanco etl = new ETLBoletaBanco();
        String [] args1 = {""};
        String [][] ret = etl.runJob(args1);
        return ret;
    }

}
