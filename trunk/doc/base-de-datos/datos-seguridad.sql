-- Script para insertar datos en las tablas usadas para la seguridad
-- el script inserta todos los roles del sistema y luego se asignan a
-- los perfiles basicos. El script tambien crea el usuario root al cual
-- se le asigna el perfil root que tiene todos los roles del sistema.

-- Se borra todo la informacion de las tablas 
DELETE FROM control.log;
DELETE FROM control.asignacion_usuario_perfil;
DELETE FROM control.asignacion_rol_perfil;
DELETE FROM control.usuario;
DELETE FROM control.perfil;
DELETE FROM control.rol;

-- Se crean los roles del sistema
INSERT INTO control.rol(id_rol, nombre, descripcion)
    VALUES (1, 'USUARIO_REGISTRADO', 'Necesario para acceder a las paginas seguras');
INSERT INTO control.rol(id_rol, nombre, descripcion)
    VALUES (2, 'AGREGAR_ESTUDIANTE', 'Permite agregar nuevos estudiantes al sistema');
INSERT INTO control.rol(id_rol, nombre, descripcion)
    VALUES (3, 'EDITAR_ESTUDIANTE', 'Permite editar la informacion de un estudiante existente en el sistema');
INSERT INTO control.rol(id_rol, nombre, descripcion)
    VALUES (4, 'DAR_BAJA_ESTUDIANTE', 'Permite dar de baja a un estudiantes');
INSERT INTO control.rol(id_rol, nombre, descripcion)
    VALUES (5, 'BUSCAR_ESTUDIANTE', 'Permite realizar busquedas de estudiantes');
INSERT INTO control.rol(id_rol, nombre, descripcion)
    VALUES (6, 'AGREGAR_CATEDRATICO', 'Permite agregar nuevos catedraticos al sistema');
INSERT INTO control.rol(id_rol, nombre, descripcion)
    VALUES (7, 'EDITAR_CATEDRATICO', 'Permite editar la informacion de un catedratico existente en el sistema');
INSERT INTO control.rol(id_rol, nombre, descripcion)
    VALUES (8, 'DAR_BAJA_CATEDRATICO', 'Permite dar de baja a un catedratico');
INSERT INTO control.rol(id_rol, nombre, descripcion)
    VALUES (9, 'BUSCAR_CATEDRATICO', 'Permite realizar busquedas de catedraticos');
	
-- Se crean los perfiles basicos del sistema
INSERT INTO control.perfil(id_perfil, nombre, descripcion)
    VALUES (1, 'ROOT', 'Perfil con todos los roles, permite acceso total al sistema');
INSERT INTO control.perfil(id_perfil, nombre, descripcion)
    VALUES (2, 'ESTUDIANTE', 'Perfil con los roles para un estudiante');
INSERT INTO control.perfil(id_perfil, nombre, descripcion)
    VALUES (3, 'CATEDRATICO', 'Perfil con los roles para un catedratico');
	
-- Se asignan todos los roles al perfil root
INSERT INTO control.asignacion_rol_perfil(id_asignacion_rol_perfil, id_perfil, id_rol)
    VALUES (1, 1, 1);
INSERT INTO control.asignacion_rol_perfil(id_asignacion_rol_perfil, id_perfil, id_rol)
    VALUES (2, 1, 2);
INSERT INTO control.asignacion_rol_perfil(id_asignacion_rol_perfil, id_perfil, id_rol)
    VALUES (3, 1, 3);
INSERT INTO control.asignacion_rol_perfil(id_asignacion_rol_perfil, id_perfil, id_rol)
    VALUES (4, 1, 4);
INSERT INTO control.asignacion_rol_perfil(id_asignacion_rol_perfil, id_perfil, id_rol)
    VALUES (5, 1, 5);
INSERT INTO control.asignacion_rol_perfil(id_asignacion_rol_perfil, id_perfil, id_rol)
    VALUES (6, 1, 6);
INSERT INTO control.asignacion_rol_perfil(id_asignacion_rol_perfil, id_perfil, id_rol)
    VALUES (7, 1, 7);
INSERT INTO control.asignacion_rol_perfil(id_asignacion_rol_perfil, id_perfil, id_rol)
    VALUES (8, 1, 8);
INSERT INTO control.asignacion_rol_perfil(id_asignacion_rol_perfil, id_perfil, id_rol)
    VALUES (9, 1, 9);
	
-- Insertar el usario root, este usuario tiene acceso a todo
INSERT INTO control.usuario(id_usuario, nombre_usuario, "password", habilitado)
    VALUES (1, 'root', 'root', true);

-- Se asigna el el perfil root al usuario root
INSERT INTO control.asignacion_usuario_perfil(id_asignacion_usuario_perfil, id_usuario, id_perfil)
    VALUES (1, 1, 1);