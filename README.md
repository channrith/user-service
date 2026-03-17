# User Service

Spring Boot user management microservice with registration API.

## Prerequisites

- **Java 25**
- **Maven** (or use the included wrapper `./mvnw`)
- **MariaDB** (or MySQL) — ensure a database exists and matches `application.yaml` (default: `user_service_db` on `localhost:3306`)

## Build

```bash
./mvnw clean package -DskipTests
```

Or with tests:

```bash
./mvnw clean package
```

The runnable JAR is produced at `target/user-service-0.0.1-SNAPSHOT.jar`.

## Run

**Option 1 — Maven:**

```bash
./mvnw spring-boot:run
```

**Option 2 — JAR:**

```bash
java -jar target/user-service-0.0.1-SNAPSHOT.jar
```

The application listens on **port 8081** by default.

## Authentication

Registration requires a valid **access token**. First log in to obtain a token, then send it when registering new users.

### Login

**POST** `/api/v1/auth/login` (no authentication required)

Request body:

```json
{
  "username": "john",
  "password": "Password123!"
}
```

**cURL example:**

```bash
curl -X POST http://localhost:8081/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"john","password":"Password123!"}'
```

**Success:** `200 OK` with JSON:

```json
{
  "accessToken": "<JWT>",
  "tokenType": "Bearer",
  "expiresInSeconds": 86400
}
```

Use the token in subsequent requests: `Authorization: Bearer <accessToken>`.

**Errors:** `400` for validation failures, `401` for invalid username or password.

You need at least one user in the database to log in (e.g. seed the default user below once, or create via SQL).

### Register a user

**POST** `/api/v1/users` — **requires** `Authorization: Bearer <accessToken>` (obtain from login).

Example body:

```json
{
  "username": "john",
  "email": "john@example.com",
  "password": "Password123!"
}
```

**cURL example (after logging in):**

```bash
# 1) Login and save token (e.g. TOKEN=$(curl -s -X POST http://localhost:8081/api/v1/auth/login ...))
# 2) Register with token
curl -X POST http://localhost:8081/api/v1/users \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN" \
  -d '{"username":"john","email":"john@example.com","password":"Password123!"}'
```

**Success:** `201 Created` with `Location: /api/v1/users/{id}` and the created user (without password) in the response body.

**Errors:** `400` for validation failures, `401` if missing or invalid token, `409` if username or email is already in use.

### Default user (for first login / seeding)

To get a first token, you need at least one user. Example user you can insert once (password is BCrypt-hashed for `Password123!`) or register via a one-off public script if you temporarily open registration:

| username | email             | password     |
|----------|-------------------|--------------|
| john     | john@example.com  | Password123! |

If the DB is empty, create this user once (e.g. via a DB seed or by temporarily permitting unauthenticated POST `/api/v1/users`), then use it to log in and register others with a token.
