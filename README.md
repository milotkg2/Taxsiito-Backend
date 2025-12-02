# TaxSIIto Backend

Sebastian Esparza 
Camilo Romero

Backend API REST para TaxSIIto - Sistema de asistencia tributaria con tienda online.

## 🛠️ Tecnologías

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **MySQL 8.x**
- **Swagger/OpenAPI 3**
- **Lombok**

## 📋 Requisitos Previos

1. **Java 17** o superior
2. **Maven 3.8+**
3. **MySQL 8.x** (recomendado: MySQL Workbench)

## 🗄️ Configuración de Base de Datos

### Crear la base de datos en MySQL Workbench:

```sql
CREATE DATABASE taxsiito_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### Configurar credenciales

Edita el archivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/taxsiito_db
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password
```

## 🚀 Ejecución

### Compilar el proyecto:

```bash
cd Backend
mvn clean install
```

### Ejecutar la aplicación:

```bash
mvn spring-boot:run
```

### La aplicación estará disponible en:

- **API**: http://localhost:8080/api
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api-docs

## 📖 Documentación

- **[Documentación de APIs](./DOCUMENTACION_APIS.md)**: Documentación completa de todos los endpoints
- **[Guía de Integración](./GUIA_INTEGRACION.md)**: Guía para integrar el frontend con el backend

## 👥 Usuarios de Prueba

Al iniciar, se crean automáticamente estos usuarios:

| Rol | Correo | Contraseña |
|-----|--------|------------|
| ADMIN | admin@gmail.com | admin1234 |
| VENDEDOR | vendedor@gmail.com | vendedor1234 |
| CLIENTE | cliente@gmail.com | cliente1234 |

## 📚 Endpoints Principales

### Autenticación (`/api/auth`)
- `POST /login` - Iniciar sesión
- `POST /register` - Registrar usuario

### Usuarios (`/api/usuarios`)
- `GET /` - Listar usuarios
- `GET /{id}` - Obtener usuario
- `POST /` - Crear usuario
- `PUT /{id}` - Actualizar usuario
- `DELETE /{id}` - Eliminar usuario

### Productos (`/api/productos`)
- `GET /` - Listar productos activos
- `GET /{id}` - Obtener producto
- `POST /` - Crear producto
- `PUT /{id}` - Actualizar producto
- `DELETE /{id}` - Eliminar producto
- `GET /buscar?nombre=` - Buscar productos
- `GET /categoria/{id}` - Productos por categoría

### Categorías (`/api/categorias`)
- `GET /` - Listar categorías activas
- `POST /` - Crear categoría
- `PUT /{id}` - Actualizar categoría
- `DELETE /{id}` - Eliminar categoría

### Órdenes (`/api/ordenes`)
- `GET /` - Listar órdenes
- `GET /{id}` - Obtener orden
- `POST /` - Crear orden
- `PATCH /{id}/estado` - Actualizar estado
- `GET /usuario/{id}` - Órdenes por usuario

### FAQs - ChatSIIto (`/api/faqs`)
- `GET /` - Listar preguntas activas
- `GET /buscar?texto=` - Buscar preguntas
- `POST /` - Crear pregunta
- `PUT /{id}` - Actualizar pregunta
- `DELETE /{id}` - Eliminar pregunta

## 📁 Estructura del Proyecto

```
Backend/
├── src/main/java/com/taxsiito/backend/
│   ├── config/          # Configuraciones (CORS, Swagger)
│   ├── controller/      # Controladores REST
│   ├── dto/             # Data Transfer Objects
│   ├── model/           # Entidades JPA
│   ├── repository/      # Repositorios JPA
│   └── service/         # Lógica de negocio
├── src/main/resources/
│   └── application.properties
└── pom.xml
```

## 🎨 Paleta de Colores

- **Primario**: Amarillo (#FFD700)
- **Secundario**: Azul (#0066CC)

---

© 2025 TaxSIIto. Todos los derechos reservados.
"# Taxsiito-Backend"  
