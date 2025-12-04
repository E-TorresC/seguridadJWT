🧩 Módulo 3 – Usuarios, Roles y Seguridad (Spring Boot 2025)

Sistema modular desarrollado con Spring Boot 3.3+, que implementa un mecanismo de autenticación y autorización empresarial basado en JWT (Access Token + Refresh Token), manejo de roles, permisos y validación de endpoints.

Este módulo forma parte del ecosistema Mueblería MG, pero se publica como un proyecto independiente para su evaluación y reutilización.

📌 Características principales
🔐 1. Autenticación (Login)

Login por username/email + password.

Generación de:

Access Token (JWT)

Refresh Token

Validación con PasswordEncoder (BCrypt).

Manejo seguro de credenciales.

🔒 2. Autorización

Roles y permisos por endpoint.

Restricción según:

Rol del usuario: ADMIN, USER.

Método HTTP permitido.

Políticas de acceso por nivel.

Filtro de autorización global.

🛡️ 3. Seguridad con JWT

Arquitectura stateless.

Filtros:

Filtro de autenticación.

Filtro de autorización.

Manejo centralizado de excepciones.

CORS configurado.

Renovación de token mediante refresh token.

🧭 4. Endpoints protegidos

El módulo incluye endpoints clasificados como:

Públicos (login, registro opcional).

Protegidos por rol.

Protegidos por JWT.

Incluye también un endpoint de prueba que retorna una lista de productos dummy para validar accesos.

🗂️ Estructura del Proyecto

modules/user/domain/entity
User.java

modules/user/domain/repository
Repositorios de usuario y roles

modules/user/domain/service
UserService
UserServiceImpl

modules/user/web/controller
Controladores REST del módulo usuario

modules/user/web/dto
DTOs de entrada y salida

modules/user/web/mapper
Mappers con MapStruct

shared/security
JwtUtils.java
SecurityConfig.java
AuthenticationFilter.java
AuthorizationFilter.java
UserStatus.java
shared/exception
GlobalExceptionHandler.java

🧬 Modelo de Datos
User
id
username
email
password (encriptada)
status
roles (Set<Role>)
auditoría básica
Role
id
name (ADMIN, USER)

🚀 Endpoints Principales

🔓 Auth
Método	Endpoint	Descripción
POST	/api/auth/login	Autenticación y generación de tokens
POST	/api/auth/refresh	Renueva access token

👤 User
Método	Endpoint	Rol	Descripción
GET	/api/users	ADMIN	Listado de usuarios
POST	/api/users	ADMIN	Crear usuario
PUT	/api/users/{id}	ADMIN	Actualizar usuario
DELETE	/api/users/{id}	ADMIN	Eliminación lógica

🧪 Endpoint de Prueba Protegido
Método	Endpoint	Rol
GET	/api/test/products	USER o ADMIN

Sirve para probar accesos protegidos con JWT sin depender de otros módulos.

🔧 Configuración de Seguridad
1. Sesión Stateless
sessionCreationPolicy(SessionCreationPolicy.STATELESS)

2. CSRF Deshabilitado
.csrf(csrf -> csrf.disable())

3. Filtros registrados en orden

AuthenticationFilter
AuthorizationFilter
ExceptionFilter

4. Rutas Públicas
/api/auth/**  
/swagger-ui/**  
/v3/api-docs/**

🧿 Tecnologías empleadas

Java 17
Spring Boot 3.3+
Spring Security 6
JPA/Hibernate
MySQL 8
JWT (jjwt-api)
Lombok
MapStruct

🧪 Pruebas con cURL
Login
curl -X POST http://localhost:8080/api/auth/login \
-H "Content-Type: application/json" \
-d "{\"username\":\"admin\",\"password\":\"admin123\"}"

Acceso protegido
curl -X GET http://localhost:8080/api/test/products \
-H "Authorization: Bearer <TOKEN_AQUI>"

🧱 Estado actual del módulo

✔ Módulo completo, funcional e integrado.
✔ Seguridad implementada bajo estándares empresariales 2025.
✔ Endpoints protegidos por rol y JWT.
✔ Refresh Token disponible.
✔ Pruebas básicas incluidas.

📄 Licencia

Proyecto académico profesional con fines educativos.
Puedes reutilizarlo para fines personales o de aprendizaje.

✨ Autor

Erick Torres – Software Architecture & Backend Engineering
Proyecto Mueblería MG – 2025
