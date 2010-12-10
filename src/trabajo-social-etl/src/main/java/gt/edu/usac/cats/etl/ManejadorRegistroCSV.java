/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.etl;

import registrocsv.etlregistrocsv_0_1.ETLRegistroCSV;

/**
 * Clase que hereda el comportamiento de {@link ManejadorETL}, esta clase lleva
 * el control del llamado el módulo {@link ETLRegistroCSV} que realiza el ETL
 * del archivo CSV que proporciona Registro y Estadística con el control de los
 * estudiantes incritos (nuevos y de reingreso).
 *
 * @author Mario Batres
 * @version 1.0
 * @see ManejadorETL
 */
public class ManejadorRegistroCSV extends ManejadorETL {

    /**
     * Método que se debe de sobreescribir para indicar qué módulo de ETL se
     * estará usando.
     *
     * @return Arreglo bidimensional de cadenas que retornan lo ocurrido en la
     * realización del proceso.
     * 
     * @throws IOException
     */
    @Override
    protected String[][] realizarETL() {
        ETLRegistroCSV etl = new ETLRegistroCSV();
        String [] args1 = {""};
        String [][] ret = etl.runJob(args1);
        return ret;
    }

}
