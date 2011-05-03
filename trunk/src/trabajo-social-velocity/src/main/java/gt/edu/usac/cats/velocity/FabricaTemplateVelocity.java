/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */
package gt.edu.usac.cats.velocity;

import gt.edu.usac.cats.velocity.extend.DesasignacionEstudianteCursoTemplateVelocity;
import gt.edu.usac.cats.velocity.extend.InformacionAsignacionEstudianteTemplateVelocity;
import gt.edu.usac.cats.velocity.extend.NuevoUsuarioTemplateVelocity;
import gt.edu.usac.cats.velocity.extend.RecordatorioContrasenyaTemplateVelocity;
import gt.edu.usac.cats.velocity.extend.RecordatorioUsuarioTemplateVelocity;
import gt.edu.usac.cats.velocity.extend.ResumenAsignacionPrimerIngresoTemplateVelocity;
import java.io.IOException;
import java.io.Serializable;

/**
 * Este clase enum se encarga de crear las instancias de los templates que est&aacute;n disponibles.
 *
 * @author Mario Batres
 * @version 1.0
 */
public enum FabricaTemplateVelocity {

    NUEVO_USUARIO,
    RESUMEN_ASIGNACION_PRIMER_INGRESO,
    DESASIGNACION_ESTUDIANTE_CURSO,
    RECORDATORIO_USUARIO,
    INFORMACION_ASIGNACION_ESTUDIANTE,
    RECORDATORIO_CONTRASENYA;


    /**
     * Crea la instancia de los templates a utilizar.
     *
     * @param contexto Propiedades a ser contextualizadas en los templates.
     *
     * @return Objeto de tipo {@link TemplateVelocity}
     *
     * @throws IOException Excepci&oacute;n que es obtenida en la lectura
     * de las propiedades de velocity en la clase {@link TemplateVelocity}
     */
    public TemplateVelocity crear(Serializable contexto) throws IOException {
        switch (this) {
            case NUEVO_USUARIO:
                return new NuevoUsuarioTemplateVelocity(contexto);

            case RESUMEN_ASIGNACION_PRIMER_INGRESO:
                return new ResumenAsignacionPrimerIngresoTemplateVelocity(contexto);

            case DESASIGNACION_ESTUDIANTE_CURSO:
                return new DesasignacionEstudianteCursoTemplateVelocity(contexto);

            case RECORDATORIO_USUARIO:
                return new RecordatorioUsuarioTemplateVelocity(contexto);

            case INFORMACION_ASIGNACION_ESTUDIANTE:
                return new InformacionAsignacionEstudianteTemplateVelocity(contexto);

            case RECORDATORIO_CONTRASENYA:
                return new RecordatorioContrasenyaTemplateVelocity(contexto);
        }

        return null;
    }
}
