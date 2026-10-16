# Overwatch Hero REST API

A RESTful CRUD API built with Spring Boot, Spring Data JPA, and PostgreSQL (hosted on Neon). It stores and serves the Overwatch heroes from my Assignment 2 character gallery website, and is deployed to Render with Docker.

**Character type:** Overwatch heroes (Tracer, Cassidy, Genji, Freja, and Emre).

## Live API

https://overwatch-hero-api-gylg.onrender.com/api/characters

> **Cold-start note:** This runs on Render's free tier, which sleeps when idle. The first request after a quiet period can take up to a minute to respond. Later requests are fast.

## Entity: `OverwatchHero` (table `characters`)

| Attribute | Type | Rules |
|---|---|---|
| characterId | long | Unique, auto-generated |
| name | String | Required (not null, not blank) |
| description | String | Required (not null, not blank) |
| role | String | Optional. Tank, Damage, or Support |
| debutDate | LocalDate | Optional. Format `yyyy-MM-dd` |

## Endpoints

| Method | Path | Description | Success status |
|---|---|---|---|
| GET | `/api/characters` | Get all characters | 200 |
| GET | `/api/characters/{id}` | Get one character by ID | 200 |
| POST | `/api/characters` | Add a character | 201 |
| PUT | `/api/characters/{id}` | Update a character | 200 |
| DELETE | `/api/characters/{id}` | Delete a character | 204 |
| GET | `/api/characters?role=Damage` | Filter by role | 200 |
| GET | `/api/characters?name=tra` | Search by name (partial, case-insensitive) | 200 |

**Errors:** an ID that doesn't exist returns `404`. A POST or PUT with a missing or blank `name` or `description` returns `400`.

### Example request body (POST and PUT)

```json
{
  "name": "Tracer",
  "description": "A former Overwatch agent who can blink through space and rewind time.",
  "role": "Damage",
  "debutDate": "2016-05-24"
}
```

## Running locally

1. Set the `SPRING_DATASOURCE_URL` environment variable to your PostgreSQL JDBC connection string.
2. From that same terminal, run `./mvnw spring-boot:run` (`.\mvnw.cmd spring-boot:run` on Windows).
3. The API is available at `http://localhost:8080/api/characters`.

## Tech stack

Java 25, Spring Boot, Spring Data JPA, Bean Validation, PostgreSQL (Neon), Docker, Render.