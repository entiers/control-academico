/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.controlador.semestre;

import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.servicio.ServicioSemestre;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.ui.Model;

/**
 * <p>Se encarga de llevar los metodos cuyo comportamiento es el mismo para las
 * clases controlador que esten relacionado con semestre.</p>
 *
 * @author Mario Batres
 * @version 1.0
 */
public abstract class ControladorAbstractoSemestre {

//______________________________________________________________________________
    /**
     * <p>Contiene metodos que permiten el manejo de la informacion relacionada
     * con el semestre en la base de datos. Este objeto se encuentra registrado
     * como un bean de servicio en Spring, por lo que este es el encargado de
     * inyectar la dependencia.</p>
     */
    @Resource
    protected ServicioSemestre servicioSemestreImpl;

//______________________________________________________________________________
    /**
     * Este metodo se encarga de crear el formulario para la pagina <code>buscarSemestre.htm</code>,
     * es utilizado en la clase {@link ControladorEditarSemestre} y {@link ControladorBuscarSemestre}.
     *
     * @param modelo Objeto {@link Model} que contiene todos los objetos que
     *        seran usados en la pagina
     *
     * @return Contiene el nombre de la vista a mostrar
     */
    protected String crearFormularioBuscarSemestre(Model modelo){
        List <Semestre> listadoSemestres = this.servicioSemestreImpl.listarSemestresParaBusqueda();
        modelo.addAttribute("listadoSemestres", listadoSemestres);
        return "semestre/buscarSemestre";
    }
}
