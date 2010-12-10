/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.etl;

/**
 * Clase enum fábrica que se encarga de crear las instancias de los objetos que
 * manejan las llamadas a los distintos ETL que hay.
 *
 * @author Mario Batres
 * @version 1.0
 */
public enum FabricaManejadorETL{
//______________________________________________________________________________
    /** Maneja el tipo para el ETL de RegistroCSV  */
    REGISTRO_CSV;
//______________________________________________________________________________
    /**
     * Método que se encarga de crear la instancia de acuerdo al tipo enum de
     * donde es llamado.
     *
     * @return Instancia de tipo {@link ManejadorETL}     *
     */
    public ManejadorETL crear(){
        switch(this){
            case REGISTRO_CSV:
                return new ManejadorRegistroCSV();
        }
        return null;
    }
//______________________________________________________________________________
}
