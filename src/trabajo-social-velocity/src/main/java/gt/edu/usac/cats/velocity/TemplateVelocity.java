/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */
package gt.edu.usac.cats.velocity;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Properties;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

/**
 * Clase abstracta que se encarga de cargar las propiedades de Velocity y contruir
 * el texto a base de alg&uacute;n template configurados en las clases hijas.
 *
 * @author Mario Batres
 * @version 1.0
 */
public abstract class TemplateVelocity {

//______________________________________________________________________________
    /** Objeto de tipo {@link VelocityEngine}*/
    protected VelocityEngine engine;

//______________________________________________________________________________
    /**
     * Constructor de la case abstracta que obligadamente es llamada en los constructores
     * de las clases hijas.
     *
     * @exception IOException Se ejecuta si no encuentra o existe alg&acute;n problema
     * en el archivo de propiedades de Velocity, ya que sin ellos no se puede generar los archivos.
     */
    public TemplateVelocity() throws IOException {
        this.inicializarPropiedades();
    }
//______________________________________________________________________________
    /**
     * M&eacute;todo abstracto que se encarga de contextualizar los atributos al template
     * e indicar el nombre del template a utilizar.
     *
     * @param context Objeto de tipo {@link VelocityContext}
     */
    protected abstract Template contextualizarTemplate(VelocityContext context);

//______________________________________________________________________________
    /**
     * M&eacute;todo que se engarca de ingresar atributos default que pueden o no pueden
     * ser usados en los templates.
     *
     * @param context Objeto de tipo {@link VelocityContext}
     */
    private void contextualizarAtributosDefault(VelocityContext context){
        Properties properties = new Properties();
        try{

            properties.load(TemplateVelocity.class.getResourceAsStream("/sitio.properties"));

            context.put("url", properties.get("url"));
            context.put("urlHTTPS", properties.get("urlHTTPS"));
        }catch(IOException e){

        }

    }
//______________________________________________________________________________
    /**
     * M&eacute;todo que se encarga de inicializar las propiedades de Velocity.
     *
     * @exception IOException Se ejecuta si no encuntra o existe alg&acute;n problema
     * en el archivo de propiedades de Velocity, ya que sin ellos no se puede generar los archivos.
     */
    private void inicializarPropiedades() throws IOException {
        Properties properties = new Properties();
        properties.load(TemplateVelocity.class.getResourceAsStream("/velocity.properties"));

        this.engine = new VelocityEngine(properties);
    }
//______________________________________________________________________________
    /**
     * M&eacute;todo que se engarga de transformar el template y devolverlo ya transformado.
     *
     * @return Objeto de tipo {@link Writer}.  Contiene la transformaci&oacute;n del template.
     */
    public Writer transformarTemplate() {
        
        VelocityContext context = new VelocityContext();

        Template template = this.contextualizarTemplate(context);

        this.contextualizarAtributosDefault(context);

        StringWriter writer = new StringWriter();
        template.merge(context, writer);

        return writer;
    }
}
