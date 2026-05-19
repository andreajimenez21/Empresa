# Empresa – Gestión de Departamentos y Empleados

Proyecto desarrollado con Spring Boot para gestionar departamentos y empleados de una empresa. Incluye API REST completa e interfaz web con Thymeleaf.

## Tecnologías

- Java 17
- Spring Boot 3.2.5
- Spring Data JPA / Hibernate
- Thymeleaf
- MySQL 8 (Docker)
- Maven

## Requisitos previos

- Java 17+
- Maven 3.8+
- Docker Desktop

## Poner en marcha la base de datos

La base de datos corre en Docker. Desde la raíz del proyecto ejecutar:

docker compose up -d

Esto levanta un contenedor MySQL con la base de datos empresa_db en el puerto 13307. Las tablas las crea Hibernate automáticamente al arrancar la aplicación.

## Ejecutar el proyecto

mvn spring-boot:run

La aplicación estará disponible en http://localhost:8080/web/departamentos

## Datos de prueba

Se incluye el fichero datos_prueba.sql con departamentos y empleados de ejemplo. Para cargarlo con Docker en ejecución:

docker exec -i empresa_ajp-db-1 mysql -u user -puser empresa_db < datos_prueba.sql

## Interfaz web

/web/departamentos              Listado con filtros por nombre y planta
/web/departamentos/nueva        Crear departamento
/web/departamentos/{id}         Detalle con empleados y salario total
/web/departamentos/editar/{id}  Editar departamento
/web/empleados                  Listado con filtros por puesto y salario
/web/empleados/nueva            Crear empleado
/web/empleados/editar/{id}      Editar empleado
/web/empleados/mover/{id}       Mover empleado a otro departamento

## API REST

GET    /api/departamentos                        Listar (filtros: ?nombre= ?planta=)
GET    /api/departamentos/{id}                   Obtener por ID
POST   /api/departamentos                        Crear
PUT    /api/departamentos/{id}                   Actualizar
DELETE /api/departamentos/{id}                   Eliminar en cascada con sus empleados
GET    /api/departamentos/{id}/salario-total      Salario total del departamento
GET    /api/departamentos/{id}/empleados          Empleados de un departamento

GET    /api/empleados                            Listar (filtros: ?puesto= ?salarioMin= ?salarioMax=)
GET    /api/empleados/{id}                       Obtener por ID
POST   /api/empleados                            Crear
PUT    /api/empleados/{id}                       Actualizar
DELETE /api/empleados/{id}                       Eliminar
PATCH  /api/empleados/{id}/mover?departamentoId= Mover a otro departamento

## Estructura del proyecto

src/main/java/com/andrea/empresa/
├── models/
│   ├── Departamento.java
│   └── Empleado.java
├── repositories/
│   ├── DepartamentoRepository.java
│   └── EmpleadoRepository.java
├── services/
│   └── EmpresaService.java
└── controllers/
    ├── ApiController.java
    └── WebController.java

## Funcionalidades

- CRUD completo de Departamentos y Empleados (API REST + Web)
- Relación OneToMany Departamento → Empleado con JPA
- Borrado en cascada al eliminar un departamento
- Filtrar departamentos por nombre o planta
- Filtrar empleados por puesto y rango salarial
- Calcular salario total por departamento
- Mover un empleado a otro departamento
- Vista de detalle con lista de empleados del departamento