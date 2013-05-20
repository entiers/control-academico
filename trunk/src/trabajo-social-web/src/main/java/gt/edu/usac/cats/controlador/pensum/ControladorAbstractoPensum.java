/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cats.controlador.pensum;

import gt.edu.usac.cats.dominio.AsignacionCursoPensum;
import gt.edu.usac.cats.dominio.Carrera;
import gt.edu.usac.cats.dominio.Pensum;
import gt.edu.usac.cats.dominio.wrapper.WrapperAsignacionCursoPensum;
import gt.edu.usac.cats.dominio.wrapper.WrapperPensum;
import gt.edu.usac.cats.servicio.ServicioPensum;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.ui.Model;

/**
 * Clase abstracta que tiene los m&eacute;todos y/o atributos que son utilizados
 * para toda la funcionalidad de Pensum en el sistema.
 *
 * @author Mario Batres
 * @version 1.0
 */
public abstract class ControladorAbstractoPensum {

//______________________________________________________________________________
    /**
     * <p>Contiene metodos que permiten el manejo de la informacion relacionada
     * con el pensum en la base de datos. Este objeto se encuentra registrado
     * como un bean de servicio en Spring, por lo que este es el encargado de
     * inyectar la dependencia.</p>
     */
    @Resource
    protected ServicioPensum servicioPensumImpl;

//______________________________________________________________________________
    /**
     * Agrega los atributos default para la p&aacute;ginas que tengan que ver
     * con el pensum.
     *
     * @param modelo Objeto de tipo {@link Model}
     * @param wrapperPensum Objeto de tipo {@link WrapperPensum}
     */
    protected void agregarAtributosDefault(Model modelo, WrapperPensum wrapperPensum) {
        List<Carrera> listadoCarreras = this.servicioPensumImpl
                .listarEntidad(Carrera.class, true, "codigo");

        System.out.println("Hannah says:  " + wrapperPensum.getCarrera().getIdCarrera());
        modelo.addAttribute("wrapperPensum", wrapperPensum);
        if (listadoCarreras != null) {
            modelo.addAttribute("listadoCarreras", listadoCarreras);
        }
    }
//______________________________________________________________________________

    /**
     * Este m&eacute;todo se utiliza para validar que el pensum exista en la BD
     * como el id que es pasado como par&aacute;metro en un query string.
     *
     * @param idPensum Id del pensum que ha sido pasado como parametro en el
     * query string para buscar el pensum en la BD.
     *
     * @return <ul><li><b><em>true</em></b> Si es v&aacute;lido</li>
     * <li><b><em>false</em></b> Si no es v&aacute;lido</li></ul>
     */
    protected Pensum validarPensum(Short idPensum) {

        if (idPensum == null) {
            return null;
        }

        Pensum pensum = this.servicioPensumImpl.cargarEntidadPorID(Pensum.class, idPensum);

        return pensum;
    }
//______________________________________________________________________________

    /**
     * Este m&eacute;todo se utiliza para validar que la asignaci&oacute;n del
     * curso con el pensum exista en la BD como el id que es pasado como
     * par&aacute;metro en un query string.
     *
     * @param idAsignacionCursoPensum Id de la asignaci&oacute;n del curso con
     * el pensum que ha sido pasado como parametro en el query string para
     * buscar el pensum en la BD.
     *
     * @return <ul><li><b><em>true</em></b> Si es v&aacute;lido</li>
     * <li><b><em>false</em></b> Si no es v&aacute;lido</li></ul>
     */
    protected AsignacionCursoPensum validarAsignacionCursoPensum(Short idAsignacionCursoPensum) {
        if (idAsignacionCursoPensum == null) {
            return null;
        }

        AsignacionCursoPensum asignacionCursoPensum = 
                this.servicioPensumImpl.cargarEntidadPorID(AsignacionCursoPensum.class, idAsignacionCursoPensum);
        return asignacionCursoPensum;
    }

//______________________________________________________________________________
    /**
     * Agrega los atributos default para la p&aacute;ginas que tengan que ver
     * con la asignaci&oacute;n del curso con el pensum.
     *
     * @param modelo Objeto de tipo {@link Model}
     * @param wrapperAsignacionCursoPensum Objeto de tipo
     * {@link WrapperAsignacionCursoPensum}
     */
    protected void agregarAtributosDefaultAsignacionCursoPensum(Model modelo,
            Pensum pensum, WrapperAsignacionCursoPensum wrapperAsignacionCursoPensum) {
        modelo.addAttribute("wrapperAsignacionCursoPensum", wrapperAsignacionCursoPensum);
        //El pensum es asigando el metodo anterior
        modelo.addAttribute("pensum", pensum);
        modelo.addAttribute("listadoCursosNoAsignados", this.servicioPensumImpl.getCursosNoAsignados(pensum));
    }

    //______________________________________________________________________________
    /**
     * Agrega los atributos default para la p&aacute;ginas que tengan que ver
     * con la administraci&oacute;n de prerrequisitos.
     *
     * @param modelo Objeto de tipo {@link Model}
     */
    protected void agregarAtributosDefaultAdministracionPrerrequisitos(Model modelo,
            Pensum pensum, AsignacionCursoPensum asignacionCursoPensum) {
        modelo.addAttribute("pensum", pensum);
        modelo.addAttribute("asignacionCursoPensum", asignacionCursoPensum);
        modelo.addAttribute("listadoCursosDePensum", this.servicioPensumImpl.getAsignacionCursoPensumsPorPensumYNoACP(pensum, asignacionCursoPensum));
    }
}
