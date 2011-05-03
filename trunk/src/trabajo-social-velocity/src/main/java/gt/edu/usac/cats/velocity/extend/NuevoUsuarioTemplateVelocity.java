/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */
package gt.edu.usac.cats.velocity.extend;

import gt.edu.usac.cats.velocity.TemplateVelocity;
import gt.edu.usac.cats.velocity.contexto.NuevoUsuario;
import java.io.IOException;
import java.io.Serializable;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

/**
 * Clase que extiende de la clase {@link TemplateVelocity} que contextualiza y configura
 * el template para el mensaje de nuevos usuarios.
 * *
 * @author Mario Batres
 * @version 1.0
 */
public class NuevoUsuarioTemplateVelocity extends TemplateVelocity {
    
//______________________________________________________________________________
    /**
     * Objeto de tipo {@link NuevoUsuario}
     */
    private NuevoUsuario nuevoUsuario;

//______________________________________________________________________________
    /**
     * Contructor de la clase
     *
     * @param context Parametros a contextualizar y casteados a un objeto de tipo
     * {@link NuevoUsuario}.
     *
     * @throws IOException Se ejecuta si hay una excepci&oacute; en la clase padre.
     */
    public NuevoUsuarioTemplateVelocity(Serializable contexto) throws IOException {
        super();
        this.nuevoUsuario = (NuevoUsuario) contexto;
    }
    
//______________________________________________________________________________
    /**
     * M&eacute;todo heredado que se encarga de contextualizar los atributos al template
     * e indicar el nombre del template a utilizar.
     *
     * @param context Objeto de tipo {@link VelocityContext}
     */    
    @Override
    protected Template contextualizarTemplate(VelocityContext context) {
        context.put("nuevoUsuario", this.nuevoUsuario);
        return engine.getTemplate("nuevoUsuarioTemplate.vm");
    }

}
