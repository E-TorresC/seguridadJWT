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

#### 5. Entidades de dominio (limpias)

- Role
- User

#### 6. Repositorios

- UserRepository
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










