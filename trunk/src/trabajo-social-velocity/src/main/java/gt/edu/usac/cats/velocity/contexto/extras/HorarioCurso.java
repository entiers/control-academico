/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.velocity.contexto.extras;

/**
 * Clase que lleva los datos
 *
 * @author Mario Batres
 * @version 1.0
 */
public class HorarioCurso {

    private String nombre;
    private String codigo;
    private String seccion;

    public HorarioCurso() {
        this.nombre = "";
        this.codigo = "";
        this.seccion = "";
    }

    public HorarioCurso(String codigo, String nombre, String seccion) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.seccion = seccion;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the seccion
     */
    public String getSeccion() {
        return seccion;
    }

    /**
     * @param seccion the seccion to set
     */
    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    

}
