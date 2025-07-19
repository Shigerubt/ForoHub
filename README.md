# AluraForo

API REST para la gestión de tópicos en un foro, desarrollada con Java 17, Spring Boot y MySQL. Incluye autenticación JWT para proteger los endpoints y control de acceso seguro.

## Tecnologías utilizadas
- Java 17
- Spring Boot 3
- Spring Security
- Spring Data JPA
- MySQL
- Flyway
- JWT (Auth0 java-jwt)
- Maven

## Instalación y ejecución
1. Clona el repositorio.
2. Configura la base de datos MySQL y actualiza las credenciales en `src/main/resources/application.properties`.
3. Ejecuta las migraciones (Flyway lo hace automáticamente al iniciar la app).
4. Ejecuta la aplicación:
   ```sh
   ./mvnw spring-boot:run
   ```

## Endpoints principales

### Autenticación
- **POST /login**
  - Body JSON: `{ "login": "usuario1", "password": "123456" }`
  - Respuesta: `{ "token": "JWT_TOKEN" }`

### Gestión de tópicos (requiere JWT en header Authorization: Bearer TOKEN)
- **GET /topicos**: Listar todos los tópicos
- **POST /topicos**: Registrar un nuevo tópico
- **GET /topicos/{id}**: Consultar detalle de un tópico
- **PUT /topicos/{id}**: Actualizar un tópico
- **DELETE /topicos/{id}**: Eliminar un tópico

## Ejemplo de uso del token JWT
1. Haz login en `/login` y copia el valor de `token`.
2. En cada solicitud protegida, agrega el header:
   ```
   Authorization: Bearer TU_TOKEN_AQUI
   ```

## Créditos
- Autor: Hugo Arias
- Inspirado y guiado por Alura Latam y Oracle

