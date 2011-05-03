/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.velocity.extend;

import gt.edu.usac.cats.velocity.TemplateVelocity;
import gt.edu.usac.cats.velocity.contexto.DesasignacionEstudianteCurso;
import java.io.IOException;
import java.io.Serializable;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

/**
 * Clase que extiende de la clase {@link TemplateVelocity} que contextualiza y configura
 * el template para el mensaje de desasignaciones de cursos de un estudiante. 
 *
 * @author Mario Batres
 * @version 1.0
 */
public class DesasignacionEstudianteCursoTemplateVelocity extends TemplateVelocity{

//______________________________________________________________________________
    /**
     * Objeto de tipo {@link DesasignacionEstudianteCurso}
     */
    private DesasignacionEstudianteCurso desasignacionEstudianteCurso;

//______________________________________________________________________________
    /**
     * Contructor de la clase
     *
     * @param context Parametros a contextualizar y casteados a un objeto de tipo
     * {@link DesasignacionEstudianteCurso}.
     *
     * @throws IOException Se ejecuta si hay una excepci&oacute; en la clase padre.
     */
    public DesasignacionEstudianteCursoTemplateVelocity(Serializable context) throws IOException {
        super();

        this.desasignacionEstudianteCurso = (DesasignacionEstudianteCurso) context;
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
        context.put("desasignacionEstudianteCurso", this.desasignacionEstudianteCurso);
        return engine.getTemplate("desasignacionEstudianteCursoTemplate.vm");
    }

}
