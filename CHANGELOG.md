# Changelog

All notable changes to this project will be documented in this file.

## [1.0.0] - 2026-03-20

### Added
- JWT authentication
  - `POST /api/v1/auth/login` authenticates with username and password and returns a Bearer JWT.
  - JWT includes `sub` (username), `userId` (user UUID), and `role` claims.
  - Invalid credentials return `401 Unauthorized`.
- Stateless security
  - Stateless session policy with JWT-based request authentication.
  - Authorization header (`Bearer <token>`) is parsed and mapped to `AuthenticatedUser` principal.
- User registration
  - `POST /api/v1/users` creates a new user and returns `201 Created` with `Location` header.
  - Request validation:
    - `username`: required, length 2-50
    - `email`: required, valid format, max length 255
    - `password`: required, length 8-100
  - Conflict handling:
    - Duplicate username returns `409 Conflict`
    - Duplicate email returns `409 Conflict`
  - Validation errors return `400 Bad Request`.
- User management and profile access
  - `GET /api/v1/users/me` returns the currently authenticated user profile.
  - `GET /api/v1/users/{uuid}` allows access for:
    - the user who owns the UUID
    - users with `ROLE_ADMIN`
