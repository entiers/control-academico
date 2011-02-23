/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.edu.usac.cats.controlador.persona;

import gt.edu.usac.cats.servicio.ServicioPersona;
import gt.edu.usac.cats.util.EmailSender;
import javax.annotation.Resource;

/**
 *
 * @author Mario Batres
 * @version 1.0
 */
public abstract class ControladorAbstractoPersona {

//______________________________________________________________________________
    @Resource
    protected ServicioPersona servicioPersonaImpl;

//______________________________________________________________________________
    @Resource
    protected EmailSender emailSender;


}
