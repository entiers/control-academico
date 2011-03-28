/*
 * Universidad de San Carlos de Guatemala
 * Escuela de Trabajo Social
 * Control Academico
 */

package gt.edu.usac.cats.enums;

/**
 * <p>Esta clase de tipo enum lleva el control del tipo de rubro
 * que son utilizados en las boletas de banco. Se debe mencionar que los valores de los
 * ids deben ser unicos ya que representan una parte importante en la funcionalidad
 * del sistema.</p>
 *
 * <p>Este clase se utiliza como un atributo de la clase {@link BoletaBanco}
 * y en la BD existe una columna <b>tipo_rubro</b> donde se almacena
 * los id que estan siendo definidos en esta clase enum.
 * </p>

 * @author Carlos Solorzano
 * @version 1.0
 */
public enum TipoRubro {
    VACACIONES(1,"Escuela de vacaciones"),
    PRIMERA_RETRASADA(2,"Primera retrasada"),
    SEGUNDA_RETRASADA(3,"Segunda retrasada");
//______________________________________________________________________________
    private int id;
    private String descripcion;
//______________________________________________________________________________
    private TipoRubro(int id, String descripcion){
        this.id = id;
        this.descripcion = descripcion;
    }
//______________________________________________________________________________
    public int toInt(){
        return this.id;
    }
//______________________________________________________________________________
    public String getDescripcion(){
        return this.descripcion;
    }
//______________________________________________________________________________
    public static TipoRubro fromInt(int id){
        TipoRubro [] tipoRubros = TipoRubro.values();

        for(TipoRubro tipoRubro : tipoRubros){
            if(tipoRubro.toInt() == id){
                return tipoRubro;
            }
        }

        return null;
    }

}
