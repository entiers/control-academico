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
    
    ASIGNACION_CURSOS_SEMESTRE(2,"Asignacion semestre"),
    ASIGNACION_PRIMER_INGRESO(1,"Asignacion primer ingreso"),
    ASIGNACION_CURSOS_VACACIONES(3,"Asignacion vacaciones"),
    ASIGNACION_PRIMERA_RETRASADA(4,"Asignacion primera retrasada"),
    ASIGNACION_SEGUNDA_RETRASADA(5,"Asignacion segunda retrasada"),
    ASIGNACION_EQUIVALENCIA(6, "Asignacion equivalencia")
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
    public static TipoAsignacion fromInt(int id){
        TipoAsignacion [] tipoAsignaciones = TipoAsignacion.values();

        for(TipoAsignacion tipoAsignacion : tipoAsignaciones){
            if(tipoAsignacion.toInt() == id){
                return tipoAsignacion;
            }
        }

        return null;
    }
}
