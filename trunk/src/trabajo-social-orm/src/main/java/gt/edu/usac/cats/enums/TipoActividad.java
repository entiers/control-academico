/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.enums;

/**
 * <p>Esta clase de tipo enum lleva el control del tipo de actividad
 * que son utilizados en el calendario de actividades.  No se manej'o
 * a nivel de BD debido a estas constantes seran utilizadas en algunos
 * m'odulos del proyecto.  Se debe de mencionar que los valores de los id's deben
 * de ser unicos ya que representan una parate importante en la funcionalidad
 * del sistema.</p>
 *
 * <p>Este clase se utiliza como un atributo de la clase {@link CalendarioActividades}
 * y en la BD existe una columna <b>tipo_actividad</b> donde se almacena
 * los id que estan siendo definidos en esta clase enum.
 * </p>
 *
 * @author Mario Batres
 * @version 1.0
 */
public enum TipoActividad {
    SEMESTRE(1, "Semestre"), 
    PRIMERA_RESTRASADA(2, "Primera Retrasada"), 
    SEGUNDA_RETRASADA(3, "Segunda Retrasada"), 
    CURSO_VACACIONES(4, "Curso de Vacaciones") 
    ;


    private int id;
    private String descripcion;

    private TipoActividad(int id, String descripcion){
        this.id = id;
        this.descripcion = descripcion;
    }

    /**
     * <p> Retorna el id del tipo de actividad que es almacenado en la BD.</p>
     *
     * @return Valor de tipo int.
     */
    public int toInt(){
        return this.id;
    }


    /**
     *<p> Retorna la descripcion del tipo de actividad, se utiliza para mostrarlo
     * en las vistas donde sea necesario <p>
     *
     * @return Objeto de tipo {@link String}
     */
    public String getDescripcion(){
        return this.descripcion;
    }



    /**
     *<p>Este metodo retorna el objeto de {@link TipoActividad} dependiendo del i
     * que es enviado como parametro</p>
     *
     * @param id Valor de tipo int que representa el id del tipo de actividad.
     * Cuyo id es almacenado en la BD.
     */
    public static TipoActividad fromInt(int id){
        TipoActividad [] tipoActividades = TipoActividad.values();

        for(TipoActividad tipoActividad : tipoActividades){
            if(tipoActividad.toInt() == id){
                return tipoActividad;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return "(" + this.id + ", " + this.descripcion + ")";
    }



}
