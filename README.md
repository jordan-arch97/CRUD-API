# Overwatch Hero Gallery

A Spring Boot app with FreeMarker pages and a REST API for managing Overwatch heroes, backed by PostgreSQL (Neon) and deployed to Render.

**Live site:** https://overwatch-hero-api-gylg.onrender.com/

> The free Render service sleeps when idle, so the first load can take about a minute.

## Pages

| Route | Description |
|---|---|
| `GET /` | Gallery of hero cards with search (`?name=` and `?role=`) |
| `GET /about` | About the project and how it is built |
| `GET /characters/{id}` | Hero details with Edit and Delete buttons |
| `GET /characters/new` | Add hero form |
| `POST /characters` | Save a new hero, then redirect to the gallery |
| `GET /characters/{id}/edit` | Edit form, pre-filled |
| `POST /characters/{id}` | Update the hero, then redirect to its details |
| `POST /characters/{id}/delete` | Delete the hero, then redirect to the gallery |

An unknown id shows a "Hero not found" page.

## API endpoints

| Method | Path | Description |
|---|---|---|
| GET | `/api/characters` | Get all heroes |
| GET | `/api/characters/{id}` | Get one hero |
| POST | `/api/characters` | Add a hero |
| PUT | `/api/characters/{id}` | Update a hero |
| DELETE | `/api/characters/{id}` | Delete a hero |
| GET | `/api/characters?role=Damage` | Filter by role |
| GET | `/api/characters?name=tra` | Search by name |
