/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.etl;

import junit.framework.TestCase;

/**
 *
 * @author shakamca
 */
public class FabricaManejadorETLTest extends TestCase {
    
    public FabricaManejadorETLTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    
    /**
     * Test of crear method, of class FabricaManejadorETL.
     */
    public void testCrear()  {


        //java.util.Properties defaultProps = new java.util.Properties();

        System.out.println("-- Realizar ETL --");
        System.out.println("-- Registro CSV --");        
        ManejadorETL instance = FabricaManejadorETL.BOLETA_BANCO_CSV.crear();
        int length = -1;

        try{
            length = instance.realizar().length;
        }catch(Exception e){
            e.printStackTrace();
            fail(e.getMessage());
        
        }
        assertEquals(1, length);
        System.out.println("--Finalizado--");
    }
    
    public static void main(String[] args){
        ManejadorETL instance = FabricaManejadorETL.BOLETA_BANCO_CSV.crear();
        int length = -1;
        try{
            length = instance.realizar().length;
        }catch(Exception e){
            e.printStackTrace();
            fail(e.getMessage());        
        }
    }

}
