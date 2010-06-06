-- script para agregar una escuela y una carrera para pruebas
-- escuelas
INSERT INTO control.escuela(id_escuela, codigo, nombre, descripcion)
    VALUES (1, '001', 'Escuela de Trabajo Social', '');
-- carreras
INSERT INTO control.carrera(id_carrera, codigo, nombre, descripcion, id_escuela)
    VALUES (1, '001', 'Licenciatura en Trabajo Social', '', 1);
