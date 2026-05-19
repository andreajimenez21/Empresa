# 🏢 Empresa – Gestión de Departamentos y Empleados

Proyecto Spring Boot con API REST y interfaz web (Thymeleaf) para gestionar departamentos y empleados.
**Tema 5** · Relación `Departamento → Empleado` (OneToMany / ManyToOne).

---

## ⚙️ Requisitos previos

- Java 17+
- Maven 3.8+
- MySQL 8.0+

---

## 🗄️ Configuración de la base de datos

1. Abre MySQL y crea la base de datos:

```sql
CREATE DATABASE empresa_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. Edita `src/main/resources/application.properties` con tus credenciales:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/empresa_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=TU_PASSWORD
```

> Hibernate creará las tablas automáticamente al iniciar (`ddl-auto=update`).

---

## ▶️ Cómo ejecutar el proyecto

```bash
# Clonar el repositorio
git clone <URL_DEL_REPOSITORIO>
cd empresa

# Compilar y arrancar
mvn spring-boot:run
```

La aplicación estará disponible en: **http://localhost:8080**

---

## 🌐 Interfaz Web

| URL | Descripción |
|-----|-------------|
| `/web/departamentos` | Listado de departamentos (con filtros) |
| `/web/departamentos/nueva` | Crear nuevo departamento |
| `/web/departamentos/{id}` | Detalle + lista de empleados del departamento |
| `/web/departamentos/editar/{id}` | Editar departamento |
| `/web/empleados` | Listado de empleados (filtro por puesto y salario) |
| `/web/empleados/nueva` | Crear nuevo empleado |
| `/web/empleados/editar/{id}` | Editar empleado |
| `/web/empleados/mover/{id}` | Mover empleado a otro departamento |

---

## 📡 API REST

### Departamentos

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/departamentos` | Listar todos (filtros: `?nombre=` `?planta=`) |
| `GET` | `/api/departamentos/{id}` | Obtener por ID |
| `POST` | `/api/departamentos` | Crear departamento |
| `PUT` | `/api/departamentos/{id}` | Actualizar departamento |
| `DELETE` | `/api/departamentos/{id}` | Eliminar (y sus empleados en cascada) |
| `GET` | `/api/departamentos/{id}/empleados` | Empleados de un departamento |
| `GET` | `/api/departamentos/{id}/salario-total` | Salario total del departamento |

### Empleados

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/empleados` | Listar todos (filtros: `?puesto=` `?salarioMin=` `?salarioMax=`) |
| `GET` | `/api/empleados/{id}` | Obtener por ID |
| `POST` | `/api/empleados` | Crear empleado |
| `PUT` | `/api/empleados/{id}` | Actualizar empleado |
| `DELETE` | `/api/empleados/{id}` | Eliminar empleado |
| `PATCH` | `/api/empleados/{id}/mover?departamentoId=` | Mover a otro departamento |

### Ejemplos con curl

```bash
# Crear departamento
curl -X POST http://localhost:8080/api/departamentos \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Tecnología","planta":"2ª Planta","presupuesto":200000}'

# Crear empleado
curl -X POST http://localhost:8080/api/empleados \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Ana López","puesto":"Desarrolladora","salario":42000,"fechaContratacion":"2022-03-15","departamento":{"id":1}}'

# Filtrar empleados por puesto y salario
curl "http://localhost:8080/api/empleados?puesto=Desarrolladora&salarioMin=30000&salarioMax=60000"

# Mover empleado al departamento 2
curl -X PATCH "http://localhost:8080/api/empleados/1/mover?departamentoId=2"

# Obtener salario total del departamento 1
curl http://localhost:8080/api/departamentos/1/salario-total
```

---

## 🏗️ Estructura del proyecto

```
src/main/java/com/andrea/empresa/
├── EmpresaApplication.java
├── models/
│   ├── Departamento.java      (@Entity, @OneToMany)
│   └── Empleado.java          (@Entity, @ManyToOne)
├── repositories/
│   ├── DepartamentoRepository.java
│   └── EmpleadoRepository.java
├── services/
│   └── EmpresaService.java
└── controllers/
    ├── ApiController.java     (@RestController → /api/...)
    └── WebController.java     (@Controller → /web/...)

src/main/resources/
├── application.properties
└── templates/
    ├── departamentos/
    │   ├── lista.html
    │   ├── nueva.html
    │   ├── editar.html
    │   └── detalle.html
    └── empleados/
        ├── lista.html
        ├── nueva.html
        ├── editar.html
        ├── detalle.html
        └── mover.html
```

---

## ✅ Funcionalidades implementadas

- [x] CRUD completo de Departamentos (API REST + Web)
- [x] CRUD completo de Empleados (API REST + Web)
- [x] Relación OneToMany Departamento → Empleado con JPA
- [x] Borrado en cascada (eliminar departamento elimina sus empleados)
- [x] Filtrar departamentos por nombre o planta
- [x] Filtrar empleados por puesto y/o rango salarial
- [x] Calcular salario total por departamento
- [x] Mover empleado a otro departamento (cambio de FK)
- [x] Vista de detalle del departamento con lista de empleados
