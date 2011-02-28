/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.enums;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
public enum Dia {
    DOMINGO(1, "DOM"),
    LUNES(2, "LUN"),
    MARTES(3, "MAR"),
    MIERCOLES(4, "MIE"),
    JUEVES(5, "JUE"),
    VIERNES(6, "VIE"),
    SABADO(7, "SAB");


    private int numeroDia;
    private String nombreDia;

    private Dia(int numeroDia, String nombreDia) {
        this.numeroDia = numeroDia;
        this.nombreDia = nombreDia;
    }

    /**
     * @return the numeroDia
     */
    public int getNumeroDia() {
        return numeroDia;
    }

    /**
     * @return the nombreDia
     */
    public String getNombreDia() {
        return nombreDia;
    }


    public static Dia fromInt(int numeroDia){
        Dia [] dias = Dia.values();

        for(Dia dia : dias){
            if(dia.getNumeroDia() == numeroDia){
                return dia;
            }
        }

        return null;
    }

}
