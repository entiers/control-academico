/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cats.controlador.horario;

import gt.edu.usac.cats.dominio.AsignacionCursoPensum;
import gt.edu.usac.cats.dominio.Salon;
import gt.edu.usac.cats.dominio.Semestre;
import gt.edu.usac.cats.dominio.wrapper.WrapperHorario;
import gt.edu.usac.cats.enums.Dia;
import gt.edu.usac.cats.enums.TipoHorario;
import gt.edu.usac.cats.servicio.ServicioHorario;
import gt.edu.usac.cats.servicio.ServicioSemestre;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
public abstract class ControladorAbstractoHorario implements Serializable {
//______________________________________________________________________________

    /**
     * <p>Contiene metodos que permiten el manejo de la informacion relacionada
     * con el salon en la base de datos. Este objeto se encuentra registrado
     * como un bean de servicio en Spring, por lo que este es el encargado de
     * inyectar la dependencia.</p>
     */
    @Autowired
    protected ServicioHorario servicioHorarioImpl;
//______________________________________________________________________________
    @Autowired
    protected ServicioSemestre servicioSemestreImpl;

//______________________________________________________________________________
    protected void agregarAtributosDefault(Model modelo,
            List<AsignacionCursoPensum> listadoCursos,
            List<Salon> listadoSalones,
            List<Semestre> listadoSemestres,
            WrapperHorario wrapperHorario) {
        modelo.addAttribute("wrapperHorario", wrapperHorario);
        listadoCursos = this.servicioHorarioImpl.listarEntidad(AsignacionCursoPensum.class, true, "pensum");
        modelo.addAttribute("listadoCursos", listadoCursos);
        modelo.addAttribute("listadoDias", Dia.values());
        modelo.addAttribute("listaTipoHorario", TipoHorario.values());

        agregarAtributosDefault(modelo, listadoSalones, listadoSemestres);
    }

//______________________________________________________________________________
    protected void agregarAtributosDefault(Model modelo, List<Salon> listadoSalones,
            List<Semestre> listadoSemestres) {

        listadoSalones = this.servicioHorarioImpl.listarEntidad(Salon.class);
        listadoSemestres = this.servicioSemestreImpl.listarSemestresParaBusqueda();
        modelo.addAttribute("listadoSalones", listadoSalones);
        modelo.addAttribute("listadoSemestres", listadoSemestres);
    }
}
