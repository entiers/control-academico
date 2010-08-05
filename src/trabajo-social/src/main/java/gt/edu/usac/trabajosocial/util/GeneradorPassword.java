/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.util;

/**
 *
 * @author Daniel Castillo
 * @version 1.0
 */
public class GeneradorPassword {
    /**
     * <p>Listado de numero arabigos.</p>
     */
    private static final String NUMEROS = "0123456789";
//______________________________________________________________________________
    /**
     * <p>Listado de letras mayusculas.</p>
     */
    private static final String MAYUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
//______________________________________________________________________________
    /**
     * <p>Listado de letras minusculas.</p>
     */
    private static final String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";
//______________________________________________________________________________
    /**
     * <p>Listado de caracteres especiales.</p>
     */
    private static final String ESPECIALES = "@";
//______________________________________________________________________________
    /**
     * <p>Genera un {@link String} de 8 caracteres, la cadena incluye numeros,
     * letras mayusculas y minusculas y algunos caracteres especiales.</p>
     *
     * @return String
     */
    public static String generarPassword() {
        return generarPassword(8);
    }
//______________________________________________________________________________
    /**
     * <p>Genera un {@link String} de la longitud enviada, la cadena incluye
     * numeros, letras mayusculas y minusculas y algunos caracteres especiales.</p>
     *
     * @param longitud Longitud que tendra la cadena generada
     * @return String
     */
    public static String generarPassword(int longitud) {
        String key = NUMEROS + MAYUSCULAS + MINUSCULAS + ESPECIALES;
        return generarPassword(key, longitud);
    }
//______________________________________________________________________________
    /**
     * <p>Genera un {@link String} de 8 caracteres, la cadena incluye numero
     * letras mayusculas y minusculas y algunos caracteres especiales.</p>
     *
     * @param key Caracteres que se deben usar para generar la cadena
     * @param longitud Longitud que tendra la cadena generada
     * @return String
     */
    public static String generarPassword(String key, int longitud) {
        String pswd = "";

        for(int i = 0; i < longitud; i++) {
            int index = (int) (Math.random() * key.length());
            pswd += key.charAt(index);
        }

        return pswd;
    }
}
