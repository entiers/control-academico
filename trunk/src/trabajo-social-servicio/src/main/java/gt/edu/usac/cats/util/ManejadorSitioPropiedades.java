/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;

/**
 *
 * @author Carlos Solorzano
 * @auhtor Mario Batres
 *
 * @version 1.0
 */
public class ManejadorSitioPropiedades {

    //______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de leer el archivo de propiedades que contiene
     * la configuracion de JavaMail para el envio de correos electronicos.</p>
     *
     * @param request Obejto HttpServletRequest
     * @return Properties Contiene la configuracion para JavaMail
     * @throws IOException Si no se pudo leer el archivo de propiedades
     * @throws URISyntaxException Se efectua si existe un problema en la lectura del path del archivo de configuración.
     */
    public static Properties leer() throws IOException, URISyntaxException {
        
        // se obtiene la path absoluto del archivo de propiedades
        String nombreArchivo = ManejadorSitioPropiedades.class.getResource("/sitio.properties").toURI().getPath();

        // se crea el desencriptador
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("APP_ENCRYPTION_PASSWORD");

        // se crea el archivo de propiedades
        Properties properties = new EncryptableProperties(encryptor);
        properties.load(new FileInputStream(nombreArchivo));

        return properties;
    }
}
