# prueba-api

REST API desarrollada con Java 21 y Spring Boot como parte de la prueba técnica para Chakray Consulting.

---

## Tecnologías

- Java 21
- Spring Boot 4.x
- Spring Security + JWT
- AES-256 para encriptación de contraseñas
- Flyway (rama `feature/db`)
- PostgreSQL (rama `feature/db`)
- Docker
- Swagger / OpenAPI
- JUnit 5 + Mockito

---

## Rama feature/db
Se creao para persistir la información del usuario en PostgreSQL en vez de una lista.

## Requisitos

- Docker y Docker Compose instalados
- Archivo `.env` configurado en la raíz del proyecto

---

## Variables de entorno

Crea un archivo `.env` en la raíz del proyecto con el siguiente contenido:

```env
AES_SECRET=B7eJlHgOjkcduAyQf0RMv4bSCiZWK6mG
JWT_SECRET=F+qZMXg76fufJNpo8PR779brM84QpO8NpPyunvWap58=
JWT_EXPIRATION=86400000
```

---

## Ejecución con Docker

```bash
docker compose up --build
```

La aplicación estará disponible en `http://localhost:8080`.

Para detener los contenedores:

```bash
docker compose down
```

---

## Ejecución local (sin Docker)

1. Configura las variables de entorno en tu IDE o sistema operativo
2. Ejecuta la aplicación desde Eclipse o con Maven:

```bash
mvn spring-boot:run
```

---

## Endpoints disponibles

### Usuarios

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/users` | Retorna todos los usuarios |
| GET | `/users?sortedBy=name` | Retorna usuarios ordenados por el campo indicado |
| GET | `/users?filter=name+co+Luis` | Retorna usuarios filtrados |
| POST | `/users` | Crea un nuevo usuario |
| PATCH | `/users/{id}` | Actualiza un usuario por ID |
| DELETE | `/users/{id}` | Elimina un usuario por ID |

### Autenticación

| Método | Endpoint | Descripción |
|---|---|---|
| POST | `/login` | Autentica al usuario y retorna un JWT |

#### Parámetros de ordenamiento (`sortedBy`)

`email`, `name`, `phone`, `taxId`, `createdAt`, `id`

#### Operadores de filtrado (`filter`)

| Operador | Descripción | Ejemplo |
|---|---|---|
| `co` | Contiene | `name+co+Luis` |
| `eq` | Igual a | `taxId+eq+AAAA0101011A1` |
| `sw` | Comienza con | `email+sw+usuario` |
| `ew` | Termina con | `email+ew+gmail.com` |

> **Nota:** Al usar el filtro en la URL, encode el `+` como `%2B`. Ejemplo: `/users?filter=name%2Bco%2BLuis`

---

## Documentación Swagger

Con la aplicación corriendo, accede a:

```
http://localhost:8080/swagger-ui.html
```

---

## Colección Postman

Importa el archivo `postman_collection.json` incluido en la raíz del proyecto para probar todos los endpoints directamente desde Postman.

---

## Pruebas unitarias

```bash
mvn test
```