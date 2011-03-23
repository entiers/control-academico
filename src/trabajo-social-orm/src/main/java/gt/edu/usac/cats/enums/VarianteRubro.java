/*
 * Universidad de San Carlos de Guatemala
 * Escuela de Trabajo Social
 * Control Academico
 */

package gt.edu.usac.cats.enums;

/**
 * <p>Esta clase de tipo enum lleva el control de la variante de rubro
 * que son utilizados en las boletas de banco. Se debe mencionar que los valores de los
 * ids deben ser unicos ya que representan una parte importante en la funcionalidad
 * del sistema.</p>
 *
 * <p>Este clase se utiliza como un atributo de la clase {@link BoletaBanco}
 * y en la BD existe una columna <b>variante_rubro</b> donde se almacena
 * los id que estan siendo definidos en esta clase enum.
 * </p>

 * @author Carlos Solorzano
 * @version 1.0
 */
public enum VarianteRubro {
    CURSO(1,"Curso"),
    INSCRIPCION_EXAMEN(2,"Inscripción/examen")
    ;
//______________________________________________________________________________
    private int id;
    private String descripcion;
//______________________________________________________________________________
    private VarianteRubro(int id, String descripcion){
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
    public static VarianteRubro fromInt(int id){
        VarianteRubro [] varianteRubros = VarianteRubro.values();

        for(VarianteRubro varianteRubro : varianteRubros){
            if(varianteRubro.toInt() == id){
                return varianteRubro;
            }
        }

        return null;
    }

}
