DROP DATABASE IF EXISTS clases;
CREATE DATABASE clases CHARACTER SET utf8mb4;
USE clases;

CREATE TABLE materia (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE alumno (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    promedio DOUBLE NOT NULL,
    id_materia INT UNSIGNED NOT NULL,
    FOREIGN KEY (id_materia) REFERENCES materia(id)
);

INSERT INTO materia VALUES (1, "Redes");
INSERT INTO materia VALUES (2, "Sistemas Operativos");
INSERT INTO materia VALUES (3, "Programación");
INSERT INTO materia VALUES (4, "Bases de datos");
INSERT INTO materia VALUES (5, "Tecnologias web");
INSERT INTO materia VALUES (6, "Estructuras de datos");
INSERT INTO materia VALUES (7, "Integración de soluciones");
INSERT INTO materia VALUES (8, "Organización de computadoras");
INSERT INTO materia VALUES (9, "Matemáticas discretas");

INSERT INTO alumno VALUES (1, "Sofía Martinez López", 8.5, 5);
INSERT INTO alumno VALUES (2, "Juan Pérez García", 9.5, 4);
INSERT INTO alumno VALUES (3, "Ana Sánchez Pérez", 7.5, 3);
INSERT INTO alumno VALUES (4, "Pedro García Martínez", 6.5, 2);
INSERT INTO alumno VALUES (5, "María López Sánchez", 5.5, 1);
INSERT INTO alumno VALUES (6, "Luis Martínez Pérez", 4.5, 9);
INSERT INTO alumno VALUES (7, "Carlos Pérez Martínez", 3.5, 8);
INSERT INTO alumno VALUES (8, "Fernanda García Sánchez", 2.5, 7);
INSERT INTO alumno VALUES (9, "Jorge Sánchez García", 1.5, 6);
INSERT INTO alumno VALUES (10, "Edson Lozano Alvarez", 0.5, 5);





