/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

    package gt.edu.usac.cats.enums;

/**
 * <p>Esta clase de tipo enum lleva el control del tipo de asignacion
 * que son utilizados en la asignacion.  No se manej'o
 * a nivel de BD debido a estas constantes seran utilizadas en algunos
 * m'odulos del proyecto.  Se debe de mencionar que los valores de los id's deben
 * de ser unicos ya que representan una parte importante en la funcionalidad
 * del sistema.</p>
 *
 * <p>Este clase se utiliza como un atributo de la clase {@link Asignacion}
 * y en la BD existe una columna <b>tipo_asignacion</b> donde se almacena
 * los id que estan siendo definidos en esta clase enum.
 * </p>
 *
 * @author Mario Batres
 * @version 1.0
 */
public enum TipoAsignacion {
    DEFAULT(1, "Default"),
    ASIGNACION_PRIMER_INGRESO(2,"Asignación de cursos de primer ingreso"),
    ASIGNACION_CURSOS_SEMESTRE(3,"Asignación de cursos en semestre")
            ;

    private int id;
    private String descripcion;

    private TipoAsignacion(int id, String descripcion){
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
     *<p>Este metodo retorna el objeto de {@link TipoAsignacion} dependiendo del id
     * que es enviado como parametro</p>
     *
     * @param id Valor de tipo int que representa el id del tipo de asignacion.
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
}
