/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cats.controlador.horario;

import gt.edu.usac.cats.dominio.Curso;
import gt.edu.usac.cats.dominio.Salon;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.dominio.busqueda.DatosBusquedaHorario;
import gt.edu.usac.cats.dominio.wrapper.WrapperHorario;
import gt.edu.usac.cats.enums.Dia;
import gt.edu.usac.cats.servicio.ServicioHorario;
import gt.edu.usac.cats.servicio.ServicioSemestre;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.ui.Model;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
public abstract class ControladorAbstractoHorario {
//______________________________________________________________________________

    /**
     * <p>Listado de todas las cursos disponibles.</p>
     */
    protected List<Semestre> listadoSemestres;
//______________________________________________________________________________
    /**
     * <p>Listado de todas las salones disponibles.</p>
     */
    protected List<Salon> listadoSalones;
//______________________________________________________________________________
    /**
     * <p>Listado de todas las cursos disponibles.</p>
     */
    protected List<Curso> listadoCursos;
//______________________________________________________________________________
    /**
     * <p>Contiene metodos que permiten el manejo de la informacion relacionada
     * con el salon en la base de datos. Este objeto se encuentra registrado
     * como un bean de servicio en Spring, por lo que este es el encargado de
     * inyectar la dependencia.</p>
     */
    @Resource
    protected ServicioHorario servicioHorarioImpl;
//______________________________________________________________________________
    @Resource
    protected ServicioSemestre servicioSemestreImpl;

//______________________________________________________________________________
    protected void agregarAtributosDefault(Model modelo, WrapperHorario wrapperHorario, boolean buscar) {
        modelo.addAttribute("wrapperHorario", wrapperHorario);

        if (buscar) {
            this.listadoCursos = this.servicioHorarioImpl.listarEntidad(Curso.class, true, "nombre");
        }

        modelo.addAttribute("listadoCursos", this.listadoCursos);

        modelo.addAttribute("listadoDias", Dia.values());

        this.agregarAtributosDefault(modelo, buscar);
    }

//______________________________________________________________________________
    protected void agregarAtributosDefault(Model modelo, boolean buscar) {
        if (buscar) {
            this.listadoSalones = this.servicioHorarioImpl.listarEntidad(Salon.class);
            this.listadoSemestres = this.servicioSemestreImpl.listarSemestresParaBusqueda();
        }
        modelo.addAttribute("listadoSalones", this.listadoSalones);
        modelo.addAttribute("listadoSemestres", this.listadoSemestres);
    }
}
