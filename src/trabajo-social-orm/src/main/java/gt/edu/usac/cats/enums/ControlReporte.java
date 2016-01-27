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
public enum ControlReporte {

    CALENDARIO_ACTIVIDADES(1),
    DETALLE_ASIGNACION_PRIMER_INGRESO(2),
    DETALLE_ASIGNACION(3),
    HORARIO(4),
    PENSUM_ESTUDIO(5),
    LISTADO_ESTUDIANTE_PENSUM_ASIGNADO(6),
    LISTADO_CARRERAS_SIMULTANEAS(7),
    LISTADO_ASIGNACION_POR_CARRERA(8),
    LISTADO_ASIGNACION_POR_CURSO(9),
    ACTA_NOTAS(10),
    CERTIFICACION_CURSOS(11),
    NOTAS_CURSOS_ASIGNADOS(12),
    LISTADO_ESTUDIANTE_CON_NOTAS(13),
    ACTA_PRELIMINAR_NOTAS(14),
    CURSOS_GANADOS_ESTUDIANTE(15); // reporte igual al certificado, pero aparece en el perfil del estudiante

    private int id;

    private ControlReporte(int id){
        this.id = id;
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
     *<p>Este metodo retorna el objeto de {@link Reporte} dependiendo del i
     * que es enviado como parametro</p>
     *
     * @param id Valor de tipo int que representa el id del reporte
     * Cuyo id es almacenado en la BD.
     */
    public static ControlReporte fromInt(int id){
        ControlReporte [] controlReportes = ControlReporte.values();
        
        for(ControlReporte controlReporte : controlReportes){
            System.out.println("Control: "+controlReporte.name());
            if(controlReporte.toInt() == id){
                return controlReporte;
            }
        }

        return null;
    }

}
