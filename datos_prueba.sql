-- =============================================
-- SCRIPT DE DATOS DE PRUEBA
-- Empresa - Departamentos y Empleados
-- =============================================

USE empresa_db;

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE empleados;
TRUNCATE TABLE departamentos;
SET FOREIGN_KEY_CHECKS = 1;

-- Departamentos
INSERT INTO departamentos (nombre, planta, presupuesto) VALUES
('Tecnologia',        '2a Planta',   200000.00),
('Recursos Humanos',  '1a Planta',    80000.00),
('Marketing',         '3a Planta',   120000.00),
('Finanzas',          '4a Planta',   150000.00),
('Logistica',         'Planta Baja',  95000.00);

-- Empleados
INSERT INTO empleados (nombre, puesto, salario, fecha_contratacion, departamento_id) VALUES
('Ana Garcia',        'Desarrolladora Backend',  42000.00, '2022-03-15', 1),
('Carlos Lopez',      'Analista de Sistemas',    38000.00, '2021-06-01', 1),
('David Ruiz',        'DevOps',                  45000.00, '2020-09-12', 1),
('Maria Sanchez',     'Directora de RRHH',       55000.00, '2019-09-10', 2),
('Laura Fernandez',   'Tecnica de Seleccion',    32000.00, '2023-02-28', 2),
('Pedro Martinez',    'Community Manager',       30000.00, '2023-01-20', 3),
('Sofia Torres',      'Disenadora Grafica',      35000.00, '2022-11-05', 3),
('Javier Moreno',     'Director de Marketing',   60000.00, '2018-04-03', 3),
('Elena Navarro',     'Contable',                36000.00, '2021-07-19', 4),
('Raul Jimenez',      'Director Financiero',     65000.00, '2017-11-22', 4),
('Isabel Romero',     'Analista Financiera',     40000.00, '2022-05-30', 4),
('Miguel Herrera',    'Jefe de Almacen',         34000.00, '2020-01-15', 5),
('Lucia Castillo',    'Operaria de Logistica',   27000.00, '2023-06-01', 5);
