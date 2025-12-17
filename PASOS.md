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

1) Configuración base del proyecto
	1.1. application.properties

2) Capa compartida (lo más “fundacional”)
	2.1. UserStatus (enum)
	2.2. Excepciones y manejo global

3) Dominio (Entidades JPA)
	3.1. Role
	3.2. User (con @ManyToMany a Role)

4) Persistencia (Repositories)
	RoleRepository
	UserRepository

5) DTOs (Request/Response)
	5.1. Requests
	LoginRequest, RefreshTokenRequest
	RoleRequest
	UserCreateRequest, UserUpdateRequest

5.2. Responses
	LoginResponse
	RoleResponse
	UserResponse

6) Mappers (MapStruct)
	RoleMapper
	UserMapper

7) Servicios de negocio
	7.1. Interfaces
	RoleService
	UserService

7.2. Implementaciones
	RoleServiceImpl
	UserServiceImpl

8) Seguridad (Spring Security + JWT)
	8.1. CustomUserDetails
   **8.2. CustomUserDetailsService**

   **8.3. JwtTokenProvider**

   **8.4. JwtAuthenticationFilter**

   **8.5. SecurityConfig**




9) Controladores (Web/API)
   **9.1. AuthController**

   **9.2. RoleController y UserController**

   **9.3. JwtTestController**


Secuencia resumida (lista final “copiable”)

application.properties

UserStatus

BusinessException, ResourceNotFoundException, ApiError, GlobalExceptionHandler

Entidades: Role → User

Repositorios: RoleRepository, UserRepository

DTOs request/response (todos)

Mappers: RoleMapper, UserMapper

Servicios: interfaces → implementaciones (RoleService*, UserService*)

Seguridad: CustomUserDetails → CustomUserDetailsService → JwtTokenProvider → JwtAuthenticationFilter → SecurityConfig

Controladores: AuthController → RoleController → UserController → JwtTestController



Flujo de ejecución real (para entender “cómo funciona”)

Cuando un cliente llama a un endpoint protegido:

JwtAuthenticationFilter intercepta la request.

Lee header Authorization.

JwtTokenProvider.validateToken() valida firma y expiración.

Extrae username (getUsernameFromToken()).

CustomUserDetailsService.loadUserByUsername() busca en BD.

Crea Authentication con authorities y lo guarda en SecurityContext.

Spring Security evalúa reglas de SecurityConfig.authorizeHttpRequests().

Si pasa, entra al Controller; si no, responde 401/403.

En login:

AuthController.login() llama authenticationManager.authenticate().

El DaoAuthenticationProvider usa CustomUserDetailsService + PasswordEncoder.

Si credenciales correctas: genera tokens con JwtTokenProvider.



Flujo de recuperación de contrase;a por correo electronico


1. **Dependencia de correo (configuración del proyecto)**

    * **Archivo modificado:** `pom.xml` (se añadió el starter de mail).

2. **Configuración SMTP y URL de reset (configuración de aplicación)**

    * **Archivo modificado:** `src/main/resources/application.properties` (se añadieron propiedades `spring.mail.*`, `app.mail.from`, `app.frontend.reset-url`).

3. **Persistencia del token de recuperación (modelo de datos)**

    * **Clase creada:** `com.seguridadjwt.modules.user.domain.entity.PasswordResetToken`

4. **Acceso a datos del token (repositorio)**

    * **Interfaz creada:** `com.seguridadjwt.modules.user.domain.repository.PasswordResetTokenRepository`

5. **Contratos de entrada (DTOs de la API)**

    * **Clase creada:** `com.seguridadjwt.modules.user.web.dto.request.ForgotPasswordRequest`
    * **Clase creada:** `com.seguridadjwt.modules.user.web.dto.request.ResetPasswordRequest`

6. **Utilidad de seguridad para token y hash (infraestructura/seguridad)**

    * **Clase creada:** `com.seguridadjwt.shared.security.TokenUtils`

7. **Envío de correo (servicio de infraestructura)**

    * **Interfaz creada:** `com.seguridadjwt.shared.mail.MailService`
    * **Clase creada:** `com.seguridadjwt.shared.mail.MailServiceImpl`

8. **Lógica de negocio de recuperación (servicio de dominio)**

    * **Interfaz creada:** `com.seguridadjwt.modules.user.domain.service.PasswordResetService`
    * **Clase creada:** `com.seguridadjwt.modules.user.domain.service.PasswordResetServiceImpl`

9. **Exposición de endpoints (controlador)**

    * **Clase modificada:** `com.seguridadjwt.modules.user.web.controller.AuthController`
      (se agregaron los endpoints `forgot-password` y `reset-password`).

10. **Seguridad: permitir endpoints públicos + CORS global (configuración de seguridad)**

* **Clase modificada:** `com.seguridadjwt.shared.security.SecurityConfig`
  (se añadió `permitAll` a los nuevos endpoints y se mantiene CORS para `http://localhost:4200`).

11. **Validación final del flujo (pruebas funcionales)**

* **Acciones realizadas:** probar `forgot-password` → revisar correo → probar `reset-password` → probar `login` con nueva contraseña (y validar tabla `password_reset_tokens` en MySQL).

Si desea, le describo el mismo orden pero ahora del **frontend Angular** (archivos exactos creados/modificados) para que quede documentado completo de extremo a extremo.



