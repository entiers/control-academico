
CREATE SEQUENCE control.lugar_nacimiento_id_lugar_nacimiento_seq_1;

CREATE TABLE control.lugar_nacimiento (
                id_lugar_nacimiento SMALLINT NOT NULL DEFAULT nextval('control.lugar_nacimiento_id_lugar_nacimiento_seq_1'),
                codigo SMALLINT NOT NULL,
                nombre VARCHAR(50),
                CONSTRAINT lugar_nacimiento_pk PRIMARY KEY (id_lugar_nacimiento)
);


ALTER SEQUENCE control.lugar_nacimiento_id_lugar_nacimiento_seq_1 OWNED BY control.lugar_nacimiento.id_lugar_nacimiento;

CREATE UNIQUE INDEX lugar_nacimiento_codigo_uk
 ON control.lugar_nacimiento
 ( codigo );

CREATE SEQUENCE control.nacionalidad_id_nacionalidad_seq_1;

CREATE TABLE control.nacionalidad (
                id_nacionalidad SMALLINT NOT NULL DEFAULT nextval('control.nacionalidad_id_nacionalidad_seq_1'),
                codigo SMALLINT NOT NULL,
                nombre VARCHAR(100),
                CONSTRAINT nacionalidad_pk PRIMARY KEY (id_nacionalidad)
);


ALTER SEQUENCE control.nacionalidad_id_nacionalidad_seq_1 OWNED BY control.nacionalidad.id_nacionalidad;

CREATE UNIQUE INDEX nacionalidad_codigo_uk
 ON control.nacionalidad
 ( codigo );

CREATE SEQUENCE control.situacion_id_situacion_seq;

CREATE TABLE control.situacion (
                id_situacion SMALLINT NOT NULL DEFAULT nextval('control.situacion_id_situacion_seq'),
                codigo SMALLINT NOT NULL,
                nombre VARCHAR(50) NOT NULL,
                descripcion VARCHAR(250),
                CONSTRAINT situacion_pk PRIMARY KEY (id_situacion)
);


ALTER SEQUENCE control.situacion_id_situacion_seq OWNED BY control.situacion.id_situacion;

CREATE UNIQUE INDEX situacion_codigo_uk
 ON control.situacion
 ( codigo ASC );

CREATE SEQUENCE control.documento_id_documento_seq;

CREATE TABLE control.Documento (
                id_documento SMALLINT NOT NULL DEFAULT nextval('control.documento_id_documento_seq'),
                nombre VARCHAR(150) NOT NULL,
                descripcion TEXT(250),
                CONSTRAINT documento_pk PRIMARY KEY (id_documento)
);


ALTER SEQUENCE control.documento_id_documento_seq OWNED BY control.Documento.id_documento;

CREATE SEQUENCE control.rol_id_rol_seq;

CREATE TABLE control.Rol (
                id_rol SMALLINT NOT NULL DEFAULT nextval('control.rol_id_rol_seq'),
                nombre VARCHAR(100) NOT NULL,
                descripcion VARCHAR(250),
                CONSTRAINT rol_pk PRIMARY KEY (id_rol)
);


ALTER SEQUENCE control.rol_id_rol_seq OWNED BY control.Rol.id_rol;

CREATE SEQUENCE control.perfil_id_perfil_seq;

CREATE TABLE control.Perfil (
                id_perfil SMALLINT NOT NULL DEFAULT nextval('control.perfil_id_perfil_seq'),
                nombre VARCHAR(50) NOT NULL,
                descripcion VARCHAR(250),
                CONSTRAINT perfil_pk PRIMARY KEY (id_perfil)
);


ALTER SEQUENCE control.perfil_id_perfil_seq OWNED BY control.Perfil.id_perfil;

CREATE SEQUENCE control.asignacion_rol_perfil_id_asignacion_rol_perfil_seq;

CREATE TABLE control.Asignacion_Rol_Perfil (
                id_asignacion_rol_perfil INTEGER NOT NULL DEFAULT nextval('control.asignacion_rol_perfil_id_asignacion_rol_perfil_seq'),
                id_perfil SMALLINT NOT NULL,
                id_rol SMALLINT NOT NULL,
                CONSTRAINT asignacion_rol_perfil_pk PRIMARY KEY (id_asignacion_rol_perfil)
);


ALTER SEQUENCE control.asignacion_rol_perfil_id_asignacion_rol_perfil_seq OWNED BY control.Asignacion_Rol_Perfil.id_asignacion_rol_perfil;

CREATE SEQUENCE control.usuario_id_usuario_seq_2;

CREATE TABLE control.Usuario (
                id_usuario INTEGER NOT NULL DEFAULT nextval('control.usuario_id_usuario_seq_2'),
                nombre_usuario VARCHAR(256) NOT NULL,
                password VARCHAR(256) NOT NULL,
                habilitado BOOLEAN NOT NULL,
                codigo_validacion_ VARCHAR(256),
                CONSTRAINT usuario_pk PRIMARY KEY (id_usuario)
);


ALTER SEQUENCE control.usuario_id_usuario_seq_2 OWNED BY control.Usuario.id_usuario;

CREATE UNIQUE INDEX usuario_nombre_uk
 ON control.Usuario
 ( nombre_usuario ASC );

CREATE TABLE control.asignacion_primer_ingreso (
                id_asignacion_primer_ingreso INTEGER NOT NULL,
                fecha_inicio TIMESTAMP NOT NULL,
                fecha_fin TIMESTAMP NOT NULL,
                id_usuario INTEGER NOT NULL,
                CONSTRAINT asignacion_primer_ingreso_pk PRIMARY KEY (id_asignacion_primer_ingreso)
);


CREATE SEQUENCE control.log_id_log_seq;

CREATE TABLE control.Log (
                id_log INTEGER NOT NULL DEFAULT nextval('control.log_id_log_seq'),
                fecha TIMESTAMP NOT NULL,
                prioridad VARCHAR(32),
                logger VARCHAR(1024),
                mensaje TEXT(256),
                id_usuario INTEGER NOT NULL,
                CONSTRAINT log_pk PRIMARY KEY (id_log)
);


ALTER SEQUENCE control.log_id_log_seq OWNED BY control.Log.id_log;

CREATE SEQUENCE control.asignacion_usuario_perfil_id_asignacion_usuario_perfil_seq;

CREATE TABLE control.Asignacion_Usuario_Perfil (
                id_asignacion_usuario_perfil INTEGER NOT NULL DEFAULT nextval('control.asignacion_usuario_perfil_id_asignacion_usuario_perfil_seq'),
                id_usuario INTEGER NOT NULL,
                id_perfil SMALLINT NOT NULL,
                CONSTRAINT asignacion_usuario_perfil_pk PRIMARY KEY (id_asignacion_usuario_perfil)
);


ALTER SEQUENCE control.asignacion_usuario_perfil_id_asignacion_usuario_perfil_seq OWNED BY control.Asignacion_Usuario_Perfil.id_asignacion_usuario_perfil;

CREATE UNIQUE INDEX asignacion_usuario_perfil_uk
 ON control.Asignacion_Usuario_Perfil
 ( id_usuario, id_perfil );

CREATE SEQUENCE control.tipo_asignacion_id_tipo_asignacion_seq_1;

CREATE TABLE control.Tipo_Asignacion (
                id_tipo_asignacion SMALLINT NOT NULL DEFAULT nextval('control.tipo_asignacion_id_tipo_asignacion_seq_1'),
                nombre VARCHAR(100) NOT NULL,
                descripcion TEXT(250),
                habilitado BOOLEAN NOT NULL,
                CONSTRAINT tipo_asignacion_pk PRIMARY KEY (id_tipo_asignacion)
);


ALTER SEQUENCE control.tipo_asignacion_id_tipo_asignacion_seq_1 OWNED BY control.Tipo_Asignacion.id_tipo_asignacion;

CREATE SEQUENCE control.area_estudio_id_area_estudio_seq;

CREATE TABLE control.Area_Estudio (
                id_area_estudio SMALLINT NOT NULL DEFAULT nextval('control.area_estudio_id_area_estudio_seq'),
                nombre VARCHAR(50) NOT NULL,
                descripcion TEXT(250),
                CONSTRAINT area_estudio_pk PRIMARY KEY (id_area_estudio)
);


ALTER SEQUENCE control.area_estudio_id_area_estudio_seq OWNED BY control.Area_Estudio.id_area_estudio;

CREATE SEQUENCE control.indicador_id_indicador_seq_1;

CREATE TABLE control.Indicador (
                id_indicador SMALLINT NOT NULL DEFAULT nextval('control.indicador_id_indicador_seq_1'),
                codigo SMALLINT NOT NULL,
                nombre VARCHAR(100) NOT NULL,
                descripcion TEXT(250),
                id_area_estudio SMALLINT NOT NULL,
                CONSTRAINT indicador_pk PRIMARY KEY (id_indicador)
);


ALTER SEQUENCE control.indicador_id_indicador_seq_1 OWNED BY control.Indicador.id_indicador;

CREATE SEQUENCE control.escuela_id_escuela_seq;

CREATE TABLE control.Escuela (
                id_escuela SMALLINT NOT NULL DEFAULT nextval('control.escuela_id_escuela_seq'),
                codigo SMALLINT,
                nombre VARCHAR(100) NOT NULL,
                descripcion TEXT(250),
                CONSTRAINT escuela_pk PRIMARY KEY (id_escuela)
);


ALTER SEQUENCE control.escuela_id_escuela_seq OWNED BY control.Escuela.id_escuela;

CREATE UNIQUE INDEX escuela_codigo_uk
 ON control.Escuela
 ( codigo );

CREATE SEQUENCE control.carrera_id_carrera_seq;

CREATE TABLE control.Carrera (
                id_carrera SMALLINT NOT NULL DEFAULT nextval('control.carrera_id_carrera_seq'),
                codigo SMALLINT,
                nombre VARCHAR(100),
                descripcion TEXT(250),
                id_escuela SMALLINT NOT NULL,
                nivel SMALLINT NOT NULL,
                CONSTRAINT carrera_pk PRIMARY KEY (id_carrera)
);


ALTER SEQUENCE control.carrera_id_carrera_seq OWNED BY control.Carrera.id_carrera;

CREATE UNIQUE INDEX carrera_codigo_uk
 ON control.Carrera
 ( codigo );

CREATE SEQUENCE control.pensum_id_pensum_seq_1;

CREATE TABLE control.Pensum (
                id_pensum SMALLINT NOT NULL DEFAULT nextval('control.pensum_id_pensum_seq_1'),
                codigo VARCHAR(20) NOT NULL,
                estado SMALLINT,
                fecha_inicio DATE NOT NULL,
                fecha_fin DATE,
                id_carrera SMALLINT NOT NULL,
                CONSTRAINT pensum_pk PRIMARY KEY (id_pensum)
);


ALTER SEQUENCE control.pensum_id_pensum_seq_1 OWNED BY control.Pensum.id_pensum;

CREATE UNIQUE INDEX pensum_codigo_unique
 ON control.Pensum
 ( codigo ASC );

CREATE SEQUENCE control.salon_id_salon_seq;

CREATE TABLE control.Salon (
                id_salon SMALLINT NOT NULL DEFAULT nextval('control.salon_id_salon_seq'),
                numero SMALLINT NOT NULL,
                edificio VARCHAR(3) NOT NULL,
                capacidad SMALLINT NOT NULL,
                CONSTRAINT salon_pk PRIMARY KEY (id_salon)
);


ALTER SEQUENCE control.salon_id_salon_seq OWNED BY control.Salon.id_salon;

CREATE SEQUENCE control.estudiante_id_estudiante_seq;

CREATE TABLE control.Estudiante (
                id_estudiante INTEGER NOT NULL DEFAULT nextval('control.estudiante_id_estudiante_seq'),
                carne VARCHAR(10) NOT NULL,
                nombre VARCHAR(50) NOT NULL,
                direccion VARCHAR(200),
                telefono VARCHAR(10),
                celular VARCHAR(10),
                email VARCHAR(100),
                fecha_nacimiento DATE NOT NULL,
                requisitos BOOLEAN DEFAULT false,
                id_usuario INTEGER NOT NULL,
                sexo CHAR(1) NOT NULL,
                carne_modificado VARCHAR(10),
                nov VARCHAR(12),
                id_pensum SMALLINT NOT NULL,
                id_lugar_nacimiento SMALLINT NOT NULL,
                id_nacionalidad SMALLINT NOT NULL,
                CONSTRAINT estudiante_pk PRIMARY KEY (id_estudiante)
);


ALTER SEQUENCE control.estudiante_id_estudiante_seq OWNED BY control.Estudiante.id_estudiante;

CREATE UNIQUE INDEX estudiante_carne_uk
 ON control.Estudiante
 ( carne ASC );

CREATE TABLE control.detalle_asignacion_primer_ingreso (
                id_detalle_asignacion_primer_ingreso INTEGER NOT NULL,
                asignado BOOLEAN NOT NULL,
                id_asignacion_primer_ingreso INTEGER NOT NULL,
                id_estudiante INTEGER NOT NULL,
                CONSTRAINT detalle_asignacion_primer_ingreso_pk PRIMARY KEY (id_detalle_asignacion_primer_ingreso)
);


CREATE SEQUENCE control.cuenta_corriente_id_cuenta_corriente_seq;

CREATE TABLE control.Cuenta_Corriente (
                id_cuenta_corriente INTEGER NOT NULL DEFAULT nextval('control.cuenta_corriente_id_cuenta_corriente_seq'),
                codigo VARCHAR(20) NOT NULL,
                activa BOOLEAN NOT NULL,
                id_estudiante INTEGER NOT NULL,
                CONSTRAINT cuenta_corriente_pk PRIMARY KEY (id_cuenta_corriente)
);


ALTER SEQUENCE control.cuenta_corriente_id_cuenta_corriente_seq OWNED BY control.Cuenta_Corriente.id_cuenta_corriente;

CREATE SEQUENCE control.movimiento_id_movimiento_seq;

CREATE TABLE control.Movimiento (
                id_movimiento INTEGER NOT NULL DEFAULT nextval('control.movimiento_id_movimiento_seq'),
                boleta INTEGER NOT NULL,
                fecha DATE NOT NULL,
                codigo_movimiento VARCHAR(10) NOT NULL,
                monto NUMERIC(6,2) NOT NULL,
                id_cuenta_corriente INTEGER NOT NULL,
                CONSTRAINT movimiento_pk PRIMARY KEY (id_movimiento)
);


ALTER SEQUENCE control.movimiento_id_movimiento_seq OWNED BY control.Movimiento.id_movimiento;

CREATE SEQUENCE control.curso_extra_id_curso_extra_seq;

CREATE TABLE control.Curso_Extra (
                id_curso_extra INTEGER NOT NULL DEFAULT nextval('control.curso_extra_id_curso_extra_seq'),
                nombre VARCHAR(150) NOT NULL,
                tipo BOOLEAN NOT NULL,
                horas SMALLINT NOT NULL,
                creditos SMALLINT NOT NULL,
                id_estudiante INTEGER NOT NULL,
                CONSTRAINT curso_extra_pk PRIMARY KEY (id_curso_extra)
);


ALTER SEQUENCE control.curso_extra_id_curso_extra_seq OWNED BY control.Curso_Extra.id_curso_extra;

CREATE SEQUENCE control.asignacion_documento_id_asignacion_documento_seq;

CREATE TABLE control.Asignacion_Documento (
                id_asignacion_documento SMALLINT NOT NULL DEFAULT nextval('control.asignacion_documento_id_asignacion_documento_seq'),
                id_documento SMALLINT NOT NULL,
                id_estudiante INTEGER NOT NULL,
                CONSTRAINT asignacion_documento_pk PRIMARY KEY (id_asignacion_documento)
);


ALTER SEQUENCE control.asignacion_documento_id_asignacion_documento_seq OWNED BY control.Asignacion_Documento.id_asignacion_documento;

CREATE SEQUENCE control.asignacion_estudiante_carrera_id_asignacion_estudiante_carre995;

CREATE TABLE control.Asignacion_Estudiante_Carrera (
                id_asignacion_estudiante_carrera INTEGER NOT NULL DEFAULT nextval('control.asignacion_estudiante_carrera_id_asignacion_estudiante_carre995'),
                id_estudiante INTEGER NOT NULL,
                id_carrera SMALLINT NOT NULL,
                fecha_cierre DATE,
                CONSTRAINT asignacion_estudiante_carrera_pk PRIMARY KEY (id_asignacion_estudiante_carrera)
);


ALTER SEQUENCE control.asignacion_estudiante_carrera_id_asignacion_estudiante_carre995 OWNED BY control.Asignacion_Estudiante_Carrera.id_asignacion_estudiante_carrera;

CREATE UNIQUE INDEX asignacion_estudiante_carrera_uk
 ON control.Asignacion_Estudiante_Carrera
 ( id_estudiante, id_carrera );

CREATE SEQUENCE control.asignacion_id_asignacion_seq_1;

CREATE TABLE control.Asignacion (
                id_asignacion INTEGER NOT NULL DEFAULT nextval('control.asignacion_id_asignacion_seq_1'),
                transaccion VARCHAR(20) NOT NULL,
                fecha TIMESTAMP NOT NULL,
                id_tipo_asignacion SMALLINT NOT NULL,
                id_asignacion_estudiante_carrera INTEGER NOT NULL,
                id_asignacion_primer_ingreso INTEGER NOT NULL,
                CONSTRAINT asignacion_pk PRIMARY KEY (id_asignacion)
);


ALTER SEQUENCE control.asignacion_id_asignacion_seq_1 OWNED BY control.Asignacion.id_asignacion;

CREATE SEQUENCE control.catedratico_id_catedratico_seq;

CREATE TABLE control.Catedratico (
                id_catedratico SMALLINT NOT NULL DEFAULT nextval('control.catedratico_id_catedratico_seq'),
                codigo VARCHAR(15) NOT NULL,
                nombre VARCHAR(50) NOT NULL,
                apellido VARCHAR(50) NOT NULL,
                profesion VARCHAR(100) NOT NULL,
                direccion VARCHAR(200),
                telefono VARCHAR(10),
                celular VARCHAR(10),
                email VARCHAR(100),
                id_usuario INTEGER NOT NULL,
                CONSTRAINT catedratico_pk PRIMARY KEY (id_catedratico)
);


ALTER SEQUENCE control.catedratico_id_catedratico_seq OWNED BY control.Catedratico.id_catedratico;

CREATE UNIQUE INDEX catedratico_codigo_unique
 ON control.Catedratico
 ( codigo ASC );

CREATE SEQUENCE control.asignacion_catedratico_escuela_id_asignacion_catedratico_esc319;

CREATE TABLE control.Asignacion_Catedratico_Escuela (
                id_asignacion_catedratico_escuela SMALLINT NOT NULL DEFAULT nextval('control.asignacion_catedratico_escuela_id_asignacion_catedratico_esc319'),
                fecha_inicio DATE NOT NULL,
                fecha_fin DATE,
                id_catedratico SMALLINT NOT NULL,
                id_escuela SMALLINT NOT NULL,
                CONSTRAINT asignacion_catedratico_escuela_pk PRIMARY KEY (id_asignacion_catedratico_escuela)
);


ALTER SEQUENCE control.asignacion_catedratico_escuela_id_asignacion_catedratico_esc319 OWNED BY control.Asignacion_Catedratico_Escuela.id_asignacion_catedratico_escuela;

CREATE SEQUENCE control.semestre_id_semestre_seq;

CREATE TABLE control.Semestre (
                id_semestre SMALLINT NOT NULL DEFAULT nextval('control.semestre_id_semestre_seq'),
                anio SMALLINT NOT NULL,
                numero CHAR(1) NOT NULL,
                observacion VARCHAR(1000),
                CONSTRAINT semestre_pk PRIMARY KEY (id_semestre)
);


ALTER SEQUENCE control.semestre_id_semestre_seq OWNED BY control.Semestre.id_semestre;

CREATE INDEX semestre_anio_numero_uk
 ON control.Semestre
 ( anio, numero );

CREATE SEQUENCE control.historial_asignacion_estudiante_carrera_id_historial_asignac355;

CREATE TABLE control.historial_asignacion_estudiante_carrera (
                id_historial_asignacion_estudiante_carrera INTEGER NOT NULL DEFAULT nextval('control.historial_asignacion_estudiante_carrera_id_historial_asignac355'),
                id_asignacion_estudiante_carrera INTEGER NOT NULL,
                fecha_inscripcion DATE NOT NULL,
                id_situacion SMALLINT NOT NULL,
                id_semestre SMALLINT NOT NULL,
                CONSTRAINT historial_asignacion_estudiante_carrera_pk PRIMARY KEY (id_historial_asignacion_estudiante_carrera)
);


ALTER SEQUENCE control.historial_asignacion_estudiante_carrera_id_historial_asignac355 OWNED BY control.historial_asignacion_estudiante_carrera.id_historial_asignacion_estudiante_carrera;

CREATE SEQUENCE control.calendario_actividades_id_calendario_actividades_seq;

CREATE TABLE control.Calendario_Actividades (
                id_calendario_actividades SMALLINT NOT NULL DEFAULT nextval('control.calendario_actividades_id_calendario_actividades_seq'),
                fecha_inicio DATE NOT NULL,
                fecha_fin DATE NOT NULL,
                actividad TEXT(250) NOT NULL,
                id_semestre SMALLINT NOT NULL,
                tipo_actividad INTEGER,
                CONSTRAINT calendario_actividades_pk PRIMARY KEY (id_calendario_actividades)
);


ALTER SEQUENCE control.calendario_actividades_id_calendario_actividades_seq OWNED BY control.Calendario_Actividades.id_calendario_actividades;

CREATE SEQUENCE control.curso_id_curso_seq;

CREATE TABLE control.Curso (
                id_curso SMALLINT NOT NULL DEFAULT nextval('control.curso_id_curso_seq'),
                codigo VARCHAR(15) NOT NULL,
                nombre VARCHAR(50) NOT NULL,
                CONSTRAINT curso_pk PRIMARY KEY (id_curso)
);


ALTER SEQUENCE control.curso_id_curso_seq OWNED BY control.Curso.id_curso;

CREATE SEQUENCE control.programa_curso_id_programa_curso_seq_1;

CREATE TABLE control.Programa_Curso (
                id_programa_curso SMALLINT NOT NULL DEFAULT nextval('control.programa_curso_id_programa_curso_seq_1'),
                fecha_aprobacion DATE NOT NULL,
                fecha_anulacion DATE,
                id_curso SMALLINT NOT NULL,
                CONSTRAINT programa_curso_pk PRIMARY KEY (id_programa_curso)
);


ALTER SEQUENCE control.programa_curso_id_programa_curso_seq_1 OWNED BY control.Programa_Curso.id_programa_curso;

CREATE SEQUENCE control.asignacion_indicador_id_asignacion_indicador_seq;

CREATE TABLE control.Asignacion_Indicador (
                id_asignacion_indicador INTEGER NOT NULL DEFAULT nextval('control.asignacion_indicador_id_asignacion_indicador_seq'),
                id_indicador SMALLINT NOT NULL,
                id_programa_curso SMALLINT NOT NULL,
                CONSTRAINT asignacion_indicador_pk PRIMARY KEY (id_asignacion_indicador)
);


ALTER SEQUENCE control.asignacion_indicador_id_asignacion_indicador_seq OWNED BY control.Asignacion_Indicador.id_asignacion_indicador;

CREATE SEQUENCE control.conteo_asignacion_id_conteo_asignacion_seq;

CREATE TABLE control.Conteo_Asignacion (
                id_conteo_asignacion INTEGER NOT NULL DEFAULT nextval('control.conteo_asignacion_id_conteo_asignacion_seq'),
                cantidad_asignaciones SMALLINT NOT NULL,
                id_estudiante INTEGER NOT NULL,
                id_curso SMALLINT NOT NULL,
                CONSTRAINT conteo_asignacion_pk PRIMARY KEY (id_conteo_asignacion)
);


ALTER SEQUENCE control.conteo_asignacion_id_conteo_asignacion_seq OWNED BY control.Conteo_Asignacion.id_conteo_asignacion;

CREATE SEQUENCE control.desasignacion_id_desasignacion_seq;

CREATE TABLE control.Desasignacion (
                id_desasignacion INTEGER NOT NULL DEFAULT nextval('control.desasignacion_id_desasignacion_seq'),
                fecha DATE NOT NULL,
                observacion TEXT(250),
                id_estudiante INTEGER NOT NULL,
                id_curso SMALLINT NOT NULL,
                CONSTRAINT desasignacion_pk PRIMARY KEY (id_desasignacion)
);


ALTER SEQUENCE control.desasignacion_id_desasignacion_seq OWNED BY control.Desasignacion.id_desasignacion;

CREATE SEQUENCE control.curso_aprobado_id_curso_aprobado_seq;

CREATE TABLE control.Curso_Aprobado (
                id_curso_aprobado INTEGER NOT NULL DEFAULT nextval('control.curso_aprobado_id_curso_aprobado_seq'),
                fecha_aprobacion DATE NOT NULL,
                zona SMALLINT NOT NULL,
                laboratorio SMALLINT NOT NULL,
                examen_final SMALLINT NOT NULL,
                observaciones TEXT(250),
                id_curso SMALLINT NOT NULL,
                id_asignacion INTEGER NOT NULL,
                CONSTRAINT curso_aprobado_pk PRIMARY KEY (id_curso_aprobado)
);


ALTER SEQUENCE control.curso_aprobado_id_curso_aprobado_seq OWNED BY control.Curso_Aprobado.id_curso_aprobado;

CREATE SEQUENCE control.asignacion_curso_pensum_id_asignacion_curso_pensum_seq;

CREATE TABLE control.Asignacion_Curso_Pensum (
                id_asignacion_curso_pensum SMALLINT NOT NULL DEFAULT nextval('control.asignacion_curso_pensum_id_asignacion_curso_pensum_seq'),
                id_curso SMALLINT NOT NULL,
                id_pensum SMALLINT NOT NULL,
                numero_semestre SMALLINT NOT NULL,
                obligatorio BOOLEAN NOT NULL,
                creditos_teoricos SMALLINT,
                creditos_practicos SMALLINT,
                creditos_prerrequisito SMALLINT,
                CONSTRAINT asignacion_curso_pensum_pk PRIMARY KEY (id_asignacion_curso_pensum)
);


ALTER SEQUENCE control.asignacion_curso_pensum_id_asignacion_curso_pensum_seq OWNED BY control.Asignacion_Curso_Pensum.id_asignacion_curso_pensum;

CREATE TABLE control.Prerrequisito (
                id_curso_pensum SMALLINT NOT NULL,
                id_curso_pensum_prerequisito SMALLINT NOT NULL,
                CONSTRAINT new_table_pk PRIMARY KEY (id_curso_pensum, id_curso_pensum_prerequisito)
);


CREATE SEQUENCE control.horario_id_horario_seq;

CREATE TABLE control.Horario (
                id_horario INTEGER NOT NULL DEFAULT nextval('control.horario_id_horario_seq'),
                hora_inicio TIME NOT NULL,
                hora_fin TIME NOT NULL,
                dia VARCHAR(10) NOT NULL,
                seccion VARCHAR(2) NOT NULL,
                tipo VARCHAR(20) NOT NULL,
                estado BOOLEAN NOT NULL,
                id_salon SMALLINT NOT NULL,
                id_curso SMALLINT NOT NULL,
                id_semestre SMALLINT NOT NULL,
                CONSTRAINT horario_pk PRIMARY KEY (id_horario)
);


ALTER SEQUENCE control.horario_id_horario_seq OWNED BY control.Horario.id_horario;

CREATE SEQUENCE control.asignacion_catedratico_horario_id_asignacion_catedratico_hor543;

CREATE TABLE control.Asignacion_Catedratico_Horario (
                id_asignacion_catedratico_horario INTEGER NOT NULL DEFAULT nextval('control.asignacion_catedratico_horario_id_asignacion_catedratico_hor543'),
                id_catedratico SMALLINT NOT NULL,
                id_horario INTEGER NOT NULL,
                CONSTRAINT asignacion_catedratico_horario_pk PRIMARY KEY (id_asignacion_catedratico_horario)
);


ALTER SEQUENCE control.asignacion_catedratico_horario_id_asignacion_catedratico_hor543 OWNED BY control.Asignacion_Catedratico_Horario.id_asignacion_catedratico_horario;

CREATE SEQUENCE control.detalle_asignacion_id_detalle_asignacion_seq;

CREATE TABLE control.Detalle_Asignacion (
                id_detalle_asignacion INTEGER NOT NULL DEFAULT nextval('control.detalle_asignacion_id_detalle_asignacion_seq'),
                zona SMALLINT NOT NULL,
                laboratorio SMALLINT NOT NULL,
                examen_final SMALLINT NOT NULL,
                id_horario INTEGER NOT NULL,
                id_asignacion INTEGER NOT NULL,
                CONSTRAINT detalle_asignacion_pk PRIMARY KEY (id_detalle_asignacion)
);


ALTER SEQUENCE control.detalle_asignacion_id_detalle_asignacion_seq OWNED BY control.Detalle_Asignacion.id_detalle_asignacion;

CREATE SEQUENCE control.nota_indicador_id_nota_indicador_seq;

CREATE TABLE control.Nota_Indicador (
                id_nota_indicador INTEGER NOT NULL DEFAULT nextval('control.nota_indicador_id_nota_indicador_seq'),
                nota SMALLINT NOT NULL,
                id_indicador SMALLINT NOT NULL,
                id_detalle_asignacion INTEGER NOT NULL,
                id_curso_aprobado INTEGER NOT NULL,
                CONSTRAINT nota_indicador_pk PRIMARY KEY (id_nota_indicador)
);


ALTER SEQUENCE control.nota_indicador_id_nota_indicador_seq OWNED BY control.Nota_Indicador.id_nota_indicador;

ALTER TABLE control.Estudiante ADD CONSTRAINT lugar_nacimiento_estudiante_fk
FOREIGN KEY (id_lugar_nacimiento)
REFERENCES control.lugar_nacimiento (id_lugar_nacimiento)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Estudiante ADD CONSTRAINT nacionalidad_estudiante_fk
FOREIGN KEY (id_nacionalidad)
REFERENCES control.nacionalidad (id_nacionalidad)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.historial_asignacion_estudiante_carrera ADD CONSTRAINT situacion_historial_asignacion_estudiante_carrera_fk
FOREIGN KEY (id_situacion)
REFERENCES control.situacion (id_situacion)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Asignacion_Documento ADD CONSTRAINT documento_asignacion_documento_fk
FOREIGN KEY (id_documento)
REFERENCES control.Documento (id_documento)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Asignacion_Rol_Perfil ADD CONSTRAINT rol_asignacion_rol_perfil_fk
FOREIGN KEY (id_rol)
REFERENCES control.Rol (id_rol)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Asignacion_Usuario_Perfil ADD CONSTRAINT perfil_asignacion_usuario_perfil_fk
FOREIGN KEY (id_perfil)
REFERENCES control.Perfil (id_perfil)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Asignacion_Rol_Perfil ADD CONSTRAINT perfil_asignacion_rol_perfil_fk
FOREIGN KEY (id_perfil)
REFERENCES control.Perfil (id_perfil)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Catedratico ADD CONSTRAINT usuario_catedratico_fk
FOREIGN KEY (id_usuario)
REFERENCES control.Usuario (id_usuario)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Estudiante ADD CONSTRAINT usuario_estudiante_fk
FOREIGN KEY (id_usuario)
REFERENCES control.Usuario (id_usuario)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Asignacion_Usuario_Perfil ADD CONSTRAINT usuario_asignacion_usuario_perfil_fk
FOREIGN KEY (id_usuario)
REFERENCES control.Usuario (id_usuario)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Log ADD CONSTRAINT usuario_log_fk
FOREIGN KEY (id_usuario)
REFERENCES control.Usuario (id_usuario)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.asignacion_primer_ingreso ADD CONSTRAINT usuario_asignacion_primer_ingreso_fk
FOREIGN KEY (id_usuario)
REFERENCES control.Usuario (id_usuario)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Asignacion ADD CONSTRAINT asignacion_primer_ingreso_asignacion_fk
FOREIGN KEY (id_asignacion_primer_ingreso)
REFERENCES control.asignacion_primer_ingreso (id_asignacion_primer_ingreso)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.detalle_asignacion_primer_ingreso ADD CONSTRAINT asignacion_primer_ingreso_detalle_asignacion_primer_ingreso_fk
FOREIGN KEY (id_asignacion_primer_ingreso)
REFERENCES control.asignacion_primer_ingreso (id_asignacion_primer_ingreso)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Asignacion ADD CONSTRAINT tipo_asignacion_asignacion_fk
FOREIGN KEY (id_tipo_asignacion)
REFERENCES control.Tipo_Asignacion (id_tipo_asignacion)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Indicador ADD CONSTRAINT area_estudio_indicador_fk
FOREIGN KEY (id_area_estudio)
REFERENCES control.Area_Estudio (id_area_estudio)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Asignacion_Indicador ADD CONSTRAINT indicador_asignacion_indicador_fk
FOREIGN KEY (id_indicador)
REFERENCES control.Indicador (id_indicador)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Nota_Indicador ADD CONSTRAINT indicador_nota_indicador_fk
FOREIGN KEY (id_indicador)
REFERENCES control.Indicador (id_indicador)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Carrera ADD CONSTRAINT escuela_carrera_fk
FOREIGN KEY (id_escuela)
REFERENCES control.Escuela (id_escuela)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Asignacion_Catedratico_Escuela ADD CONSTRAINT escuela_asignacion_catedratico_escuela_fk
FOREIGN KEY (id_escuela)
REFERENCES control.Escuela (id_escuela)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Pensum ADD CONSTRAINT carrera_pensum_fk
FOREIGN KEY (id_carrera)
REFERENCES control.Carrera (id_carrera)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Asignacion_Estudiante_Carrera ADD CONSTRAINT carrera_asignacion_estudiante_carrera_fk
FOREIGN KEY (id_carrera)
REFERENCES control.Carrera (id_carrera)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Asignacion_Curso_Pensum ADD CONSTRAINT pensum_asignacion_curso_pensum_fk
FOREIGN KEY (id_pensum)
REFERENCES control.Pensum (id_pensum)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Estudiante ADD CONSTRAINT pensum_estudiante_fk
FOREIGN KEY (id_pensum)
REFERENCES control.Pensum (id_pensum)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Horario ADD CONSTRAINT salon_horario_fk
FOREIGN KEY (id_salon)
REFERENCES control.Salon (id_salon)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Asignacion_Estudiante_Carrera ADD CONSTRAINT estudiante_asignacion_estudiante_carrera_fk
FOREIGN KEY (id_estudiante)
REFERENCES control.Estudiante (id_estudiante)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Desasignacion ADD CONSTRAINT estudiante_desasignacion_fk
FOREIGN KEY (id_estudiante)
REFERENCES control.Estudiante (id_estudiante)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Conteo_Asignacion ADD CONSTRAINT estudiante_conteo_asignacion_fk
FOREIGN KEY (id_estudiante)
REFERENCES control.Estudiante (id_estudiante)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Asignacion_Documento ADD CONSTRAINT estudiante_asignacion_documento_fk
FOREIGN KEY (id_estudiante)
REFERENCES control.Estudiante (id_estudiante)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Curso_Extra ADD CONSTRAINT estudiante_curso_extra_fk
FOREIGN KEY (id_estudiante)
REFERENCES control.Estudiante (id_estudiante)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Cuenta_Corriente ADD CONSTRAINT estudiante_cuenta_corriente_fk
FOREIGN KEY (id_estudiante)
REFERENCES control.Estudiante (id_estudiante)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.detalle_asignacion_primer_ingreso ADD CONSTRAINT estudiante_detalle_asignacion_primer_ingreso_fk
FOREIGN KEY (id_estudiante)
REFERENCES control.Estudiante (id_estudiante)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Movimiento ADD CONSTRAINT cuenta_corriente_movimiento_fk
FOREIGN KEY (id_cuenta_corriente)
REFERENCES control.Cuenta_Corriente (id_cuenta_corriente)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.historial_asignacion_estudiante_carrera ADD CONSTRAINT asignacion_estudiante_carrera_historial_asignacion_estudiant883
FOREIGN KEY (id_asignacion_estudiante_carrera)
REFERENCES control.Asignacion_Estudiante_Carrera (id_asignacion_estudiante_carrera)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Asignacion ADD CONSTRAINT asignacion_estudiante_carrera_asignacion_fk
FOREIGN KEY (id_asignacion_estudiante_carrera)
REFERENCES control.Asignacion_Estudiante_Carrera (id_asignacion_estudiante_carrera)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Detalle_Asignacion ADD CONSTRAINT asignacion_detalle_asignacion_fk
FOREIGN KEY (id_asignacion)
REFERENCES control.Asignacion (id_asignacion)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Curso_Aprobado ADD CONSTRAINT asignacion_curso_aprobado_fk
FOREIGN KEY (id_asignacion)
REFERENCES control.Asignacion (id_asignacion)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Asignacion_Catedratico_Horario ADD CONSTRAINT catedratico_asignacion_catedratico_horario_fk
FOREIGN KEY (id_catedratico)
REFERENCES control.Catedratico (id_catedratico)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Asignacion_Catedratico_Escuela ADD CONSTRAINT catedratico_asignacion_catedratico_escuela_fk
FOREIGN KEY (id_catedratico)
REFERENCES control.Catedratico (id_catedratico)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Horario ADD CONSTRAINT semestre_horario_fk
FOREIGN KEY (id_semestre)
REFERENCES control.Semestre (id_semestre)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Calendario_Actividades ADD CONSTRAINT semestre_calendario_actividades_fk
FOREIGN KEY (id_semestre)
REFERENCES control.Semestre (id_semestre)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.historial_asignacion_estudiante_carrera ADD CONSTRAINT semestre_historial_asignacion_estudiante_carrera_fk
FOREIGN KEY (id_semestre)
REFERENCES control.Semestre (id_semestre)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Horario ADD CONSTRAINT curso_horario_fk
FOREIGN KEY (id_curso)
REFERENCES control.Curso (id_curso)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Asignacion_Curso_Pensum ADD CONSTRAINT curso_asignacion_curso_pensum_fk
FOREIGN KEY (id_curso)
REFERENCES control.Curso (id_curso)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Curso_Aprobado ADD CONSTRAINT curso_curso_aprobado_fk
FOREIGN KEY (id_curso)
REFERENCES control.Curso (id_curso)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Desasignacion ADD CONSTRAINT curso_desasignacion_fk
FOREIGN KEY (id_curso)
REFERENCES control.Curso (id_curso)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Conteo_Asignacion ADD CONSTRAINT curso_conteo_asignacion_fk
FOREIGN KEY (id_curso)
REFERENCES control.Curso (id_curso)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Programa_Curso ADD CONSTRAINT curso_programa_curso_fk
FOREIGN KEY (id_curso)
REFERENCES control.Curso (id_curso)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Asignacion_Indicador ADD CONSTRAINT programa_curso_asignacion_indicador_fk
FOREIGN KEY (id_programa_curso)
REFERENCES control.Programa_Curso (id_programa_curso)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Nota_Indicador ADD CONSTRAINT curso_aprobado_nota_indicador_fk
FOREIGN KEY (id_curso_aprobado)
REFERENCES control.Curso_Aprobado (id_curso_aprobado)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Prerrequisito ADD CONSTRAINT asignacion_curso_pensum_id_curso_fk
FOREIGN KEY (id_curso_pensum)
REFERENCES control.Asignacion_Curso_Pensum (id_asignacion_curso_pensum)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Prerrequisito ADD CONSTRAINT asignacion_curso_pensum_prerrequisito_fk
FOREIGN KEY (id_curso_pensum_prerequisito)
REFERENCES control.Asignacion_Curso_Pensum (id_asignacion_curso_pensum)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Detalle_Asignacion ADD CONSTRAINT horario_detalle_asignacion_fk
FOREIGN KEY (id_horario)
REFERENCES control.Horario (id_horario)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Asignacion_Catedratico_Horario ADD CONSTRAINT horario_asignacion_catedratico_horario_fk
FOREIGN KEY (id_horario)
REFERENCES control.Horario (id_horario)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE control.Nota_Indicador ADD CONSTRAINT detalle_asignacion_nota_indicador_fk
FOREIGN KEY (id_detalle_asignacion)
REFERENCES control.Detalle_Asignacion (id_detalle_asignacion)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;
