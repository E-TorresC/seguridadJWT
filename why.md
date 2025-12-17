# PASO A REALIZAR PARA LA IMPLEMENTACION DE SEGURIDAD JWT

### 1. Script SQL MySQL – Modelo de base de datos

### 2. Estructura de paquetes propuesta

### 2.1. Agregar las dependencias adicionales necesarias:

- mapstruct
- jjwt-api
- jjwt-impl
- jjwt-jackson
- mapstruct-processor (en plugins)

### 3. Implementacion de codigo:

#### 4. Implementar Enum de estado de usuario

- UserStatus

La clase UserStatus se utiliza para representar y controlar los diferentes
estados operativos de un usuario dentro de un sistema seguro. Su finalidad
es ofrecer un mecanismo claro y robusto para validar condiciones de acceso,
implementar políticas de seguridad, mantener la integridad de los datos y 
soportar flujos administrativos asociados a la vida útil de una cuenta de 
usuario.

#### 5. Entidades de dominio (limpias)

- Role

La clase Role representa la entidad encargada de definir los distintos roles
del sistema, permitiendo controlar la autorización de usuarios mediante
permisos asociados. Cada rol posee un identificador único, un nombre 
estandarizado (como ROLE_ADMIN o ROLE_VENDEDOR), una descripción opcional 
y campos de auditoría para registrar su creación y actualización. Esta entidad
sirve como base para asignar roles a los usuarios y gestionar sus niveles de
acceso dentro de la arquitectura de seguridad.


- User

La clase User representa la entidad central del sistema de autenticación
y autorización, encargada de almacenar la información esencial de cada usuario,
incluyendo credenciales, datos personales, estado operativo (UserStatus) 
y auditoría. Además, incorpora campos para el control de intentos fallidos
y registro de accesos, permitiendo fortalecer la seguridad ante posibles 
ataques. Su relación muchos a muchos con la entidad Role define los permisos
que posee cada usuario, convirtiéndola en un componente fundamental para 
gestionar el acceso y el comportamiento de los usuarios dentro de la plataforma.

#### 6. Repositorios

- UserRepository

La interfaz UserRepository define el acceso a los datos de la entidad User
mediante consultas especializadas que permiten buscar usuarios por nombre,
correo o ambos, verificar la existencia de credenciales únicas y contar
usuarios según su estado. Extiende JpaRepository, lo que proporciona
operaciones CRUD completas y optimizadas, convirtiéndose en el componente
fundamental para interactuar con la base de datos dentro del módulo de usuarios
y soportar los procesos de autenticación, validación y administración del sistema.

- RoleRepository

#### 7. DTOs (entrada y salida)

- UserCreateRequest
- UserUpdateRequest
- RoleRequest
- LoginRequest
- RefreshTokenRequest
- ----
- UserResponse
- RoleResponse
- LoginResponse

#### 8. Mappers (MapStruct)

- UserMapper

La interfaz UserMapper define, mediante MapStruct, las reglas de conversión
entre las entidades de dominio del usuario y sus respectivos DTOs, garantizando
una transformación consistente, segura y desacoplada. Permite crear entidades
a partir de solicitudes de registro inicializando valores por defecto, generar
respuestas enriquecidas incluyendo los nombres de los roles asignados y 
actualizar usuarios existentes ignorando campos nulos. Con ello, estandariza
el intercambio de datos entre las capas web y de dominio, asegurando claridad
y mantenibilidad dentro del módulo de usuarios.


- RoleMapper

#### 9. Servicios

- UserService
- RoleService
- -----
- UserServiceImpl
- RoleServiceImpl

#### 10. Integración con Spring Security y JWT

- CustomUserDetails
- CustomUserDetailsService
- JwtTokenProvider
- JwtAuthenticationFilter
- SecurityConfig

#### 11. Controladores REST

- AuthController
- UserController
- RoleController

#### 12. cURL para probar en postman

#### 12.A AUTH

- Login

curl --location 'http://localhost:8080/api/v1/auth/login' \
--header 'Content-Type: application/json' \
--data '{
"usernameOrEmail": "erick",
"password": "admin123"
}'

- Refresh Token

curl --location 'http://localhost:8080/api/v1/auth/refresh' \
--header 'Content-Type: application/json' \
--data '{
"refreshToken": "PEGUE_AQUI_SU_REFRESH_TOKEN"
}'

#### 12.B USUARIOS

- Crear usuario

curl --location 'http://localhost:8080/api/v1/users' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer YOUR_ACCESS_TOKEN_HERE' \
--data-raw '{
"username": "erick",
"email": "erick@example.com",
"password": "admin123",
"firstName": "erick",
"lastName": "Torres",
"roles": [
"ROLE_ADMIN"
]
}'

- Actualizar usuario

curl --location --request PUT 'http://localhost:8080/api/v1/users/1' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer YOUR_ACCESS_TOKEN_HERE' \
--data-raw '{
"firstName": "John Updated",
"lastName": "Doe Updated",
"email": "jdoe.updated@example.com",
"status": "ACTIVE",
"roles": [
"ROLE_ADMIN"
]
}'


- Cambiar estado del usuario

curl --location --request PATCH 'http://localhost:8080/api/v1/users/2/status/BLOCKED' \
--header 'Authorization: Bearer YOUR_ACCESS_TOKEN_HERE'


- Obtener usuario por ID

curl --location 'http://localhost:8080/api/v1/users/2' \
--header 'Authorization: Bearer YOUR_ACCESS_TOKEN_HERE'


- Listar todos los usuarios

curl --location 'http://localhost:8080/api/v1/users' \
--header 'Authorization: Bearer YOUR_ACCESS_TOKEN_HERE'


- Eliminación lógica de usuario

curl --location --request DELETE 'http://localhost:8080/api/v1/users/2' \
--header 'Authorization: Bearer YOUR_ACCESS_TOKEN_HERE'


#### 12.C ROLES


- Crear rol

curl --location 'http://localhost:8080/api/v1/roles' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer YOUR_ACCESS_TOKEN_HERE' \
--data '{
"name": "ROLE_ADMIN",
"description": "Rol con acceso administrativo completo"
}'


- Actualizar rol

curl --location --request PUT 'http://localhost:8080/api/v1/roles/1' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer YOUR_ACCESS_TOKEN_HERE' \
--data '{
"name": "ROLE_VENDEDOR",
"description": "Rol para gestionar ventas y productos"
}'


- Obtener rol por ID

curl --location 'http://localhost:8080/api/v1/roles/1' \
--header 'Authorization: Bearer YOUR_ACCESS_TOKEN_HERE'


- Listar todos los roles

curl --location 'http://localhost:8080/api/v1/roles' \
--header 'Authorization: Bearer YOUR_ACCESS_TOKEN_HERE'


- Eliminar rol (DELETE lógico/físico según su servicio)

curl --location --request DELETE 'http://localhost:8080/api/v1/roles/1' \
--header 'Authorization: Bearer YOUR_ACCESS_TOKEN_HERE'










