/*
 * Universidad de San Carlos de Guatemala
 * Escuela de Trabajo Social
 * Control Academico
 */

package gt.edu.usac.cats.enums;

/**
 * <p>Esta clase de tipo enum lleva el control del tipo de horario
 * que son utilizados en los horarios de cursos. Se debe mencionar que los valores de los
 * ids deben ser unicos ya que representan una parte importante en la funcionalidad
 * del sistema.</p>
 *
 * <p>Este clase se utiliza como un atributo de la clase {@link Horario}
 * y en la BD existe una columna <b>tipo</b> donde se almacena
 * los id que estan siendo definidos en esta clase enum.
 * </p>
 *
 * @author Carlos Solorzano
 * @version 1.0
 */
public enum TipoHorario {
    SEMESTRE(1,"Semestre"),
    VACACIONES(2,"Vacaciones"),
    PRIMERA_RETRASADA(3,"Primera retrasada"),
    SEGUNDA_RETRASADA(4,"Segunda retrasada")
    ;

    private int id;
    private String descripcion;

    private TipoHorario(int id, String descripcion){
        this.id = id;
        this.descripcion = descripcion;
    }

    /**
     * <p> Retorna el id del tipo de horario que es almacenado en la BD.</p>
     *
     * @return Valor de tipo int.
     */
    public int toInt(){
        return this.id;
    }

    /**
     *<p> Retorna la descripcion del tipo horario, se utiliza para mostrarlo
     * en las vistas donde sea necesario <p>
     *
     * @return Objeto de tipo {@link String}
     */
    public String getDescripcion(){
        return this.descripcion;
    }

    /**
     *<p>Este metodo retorna el objeto de {@link TipoHorario} dependiendo del id
     * que es enviado como parametro</p>
     *
     * @param id Valor de tipo int que representa el id del tipo horario.
     * Cuyo id es almacenado en la BD.
     */
    public static TipoHorario fromInt(int id){
        TipoHorario [] tipoHorarios = TipoHorario.values();

        for(TipoHorario tipoHorario : tipoHorarios){
            if(tipoHorario.toInt() == id){
                return tipoHorario;
            }
        }
        return null;
    }
}
