/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.velocity.extend;

import gt.edu.usac.cats.velocity.TemplateVelocity;
import gt.edu.usac.cats.velocity.contexto.RecordatorioContrasenya;
import java.io.IOException;
import java.io.Serializable;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

/**
 * Clase que extiende de la clase {@link TemplateVelocity} que contextualiza y configura
 * el template para el mensaje de recordatorio de contrasenya.
 *
 * @author Mario Batres
 * @version 1.0
 */
public class RecordatorioContrasenyaTemplateVelocity extends TemplateVelocity{
    
//______________________________________________________________________________
    /**
     * Objeto de tipo {@link RecordatorioContrasenya}
     */
    private RecordatorioContrasenya recordatorioContrasenya;

//______________________________________________________________________________
    /**
     * Contructor de la clase
     *
     * @param context Parametros a contextualizar y casteados a un objeto de tipo
     * {@link RecordatorioContrasenya}.
     *
     * @throws IOException Se ejecuta si hay una excepci&oacute; en la clase padre.
     */
    public RecordatorioContrasenyaTemplateVelocity(Serializable contexto) throws IOException {
        super();

        this.recordatorioContrasenya = (RecordatorioContrasenya) contexto;
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
        context.put("recordatorioContrasenya", this.recordatorioContrasenya);
        return engine.getTemplate("recordatorioContrasenyaTemplate.vm");
    }

}
