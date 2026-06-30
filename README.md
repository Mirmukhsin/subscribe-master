# subscribe-master

Backend API for managing paid subscriptions (Netflix, Spotify, ChatGPT, etc.), tracking expenses across currencies, and notifying users before payment dates.

## Tech Stack

- Java 21, Spring Boot 3.5
- PostgreSQL + Liquibase (schema migrations)
- Redis (currency rate caching)
- Spring Security + JWT (access + refresh tokens)
- MapStruct (entity-DTO mapping)
- Apache POI (Excel report export)
- JUnit 5 + Mockito + AssertJ (tests)
- Docker Compose

## Prerequisites

- Java 21
- Maven 3.9+
- Docker & Docker Compose

## Run with Docker Compose (recommended)

```bash
# 1. Build the jar
mvn clean package -DskipTests

# 2. Start app + postgres + redis
docker compose up --build
```

App will be available at:
- API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html

## Run Locally (without Docker)

1. Start PostgreSQL and Redis locally
2. Update `src/main/resources/application-dev.yaml` with your local DB credentials
3. Run:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## Run Tests

```bash
mvn test
```

## Default Test Users (seeded via Liquibase)

| Email | Password | Role |
|---|----------|---|
| admin@subtracker.com | admin123 | ADMIN |
| john@gmail.com | john123  | USER |

## API Overview

| Endpoint | Description |
|---|---|
| `POST /users/register` | Register new user |
| `POST /users/login` | Login, returns access + refresh token |
| `POST /users/refresh` | Get new access token |
| `POST /users/logout` | Revoke refresh token |
| `GET /subs/all` | List all available subscriptions (public) |
| `POST /users/subscribe/{subId}` | Subscribe to a service |
| `PUT /users/unsubscribe/{subId}` | Change subscription status |
| `GET /users/statistics/mostExpSub` | Most expensive subscription for a month |
| `GET /users/statistics/monthlyExpense` | Total monthly expense |
| `GET /users/statistics/expenseInPeriod` | Spending trend over N months |
| `GET /users/report` | Download annual report (Excel) |

Full interactive API docs available via Swagger UI once the app is running.