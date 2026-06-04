# Star Wars API

REST API for Star Wars character data with basic authentication and caching, built with Spring Boot.

## Quickstart

```bash
mvn spring-boot:run
```

The server starts on `http://localhost:8080`.

## How to Run Tests

```bash
mvn test
```

## Design Decisions / Trade-offs

- **In-memory token store (`ConcurrentHashMap`)**: Simple and thread-safe. Tokens are lost on restart. For production, a persistent store (Redis or a database) would be used instead.
- **In-memory caching (`@Cacheable` + Caffeine)**: Avoids redundant SWAPI calls for the same page. Cache is bounded to 100 entries and expires after 5 minutes. For production, Redis would allow cache persistence and sharing across instances.
- **No real credential validation**: The login endpoint accepts any username/email and issues UUID tokens, as specified by the mocked authentication requirement.
- **Global exception handler (`@RestControllerAdvice`)**: Centralises all error responses into a consistent `{ status, error, message }` shape, keeping controllers clean.
- **`ResourceAccessException` caught in `SwapiClient`**: Network-level failures (no internet, DNS errors) are caught explicitly and rethrown as `SwapiUnavailableException`, so a single handler in `GlobalExceptionHandler` covers both network failures and SWAPI 5xx responses.

## Authentication Flow

1. **Login** — `POST /auth/login` with `{ username, email }` returns `{ accessToken, refreshToken, user }`. Tokens are UUID strings stored in memory.
2. **Access protected route** — Include `Authorization: Bearer <accessToken>` header on `GET /favourites`. The token is validated against the in-memory store.
3. **Token refresh** — If the access token is invalid or expired, call `POST /auth/refresh` with `{ refreshToken }` to receive a new `{ accessToken }`. The old access token is invalidated.
4. **Logout** — `POST /auth/logout` with `Authorization: Bearer <accessToken>` removes both the access token and its associated refresh token from the store, returning `204 No Content`.
