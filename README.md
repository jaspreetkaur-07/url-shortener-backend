# URL Shortener - Backend

A REST API built with Spring Boot that shortens long URLs and tracks click analytics.

## Features
- Shorten long URLs into short, shareable codes
- Redirect short URLs to original destination
- Track click count for each shortened URL
- H2 in-memory database (easily switchable to MySQL/PostgreSQL)

## Tech Stack
- Java 17
- Spring Boot (Web, Data JPA)
- H2 Database
- Gradle
- Lombok

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/shorten` | Shorten a URL |
| GET | `/{shortCode}` | Redirect to original URL |

### Example Request
```json
POST /api/shorten
{
  "originalUrl": "https://www.example.com/very/long/url"
}
```

### Example Response
```json
{
  "shortCode": "aB3xZ9",
  "originalUrl": "https://www.example.com/very/long/url",
  "shortUrl": "http://localhost:8080/aB3xZ9"
}
```

## How to Run
1. Clone the repository
2. Open in IntelliJ IDEA
3. Run `DemoApplication.java`
4. Server starts at `http://localhost:8080`

## Frontend Repository
[url-shortener-frontend](https://github.com/jaspreetkaur-07/url-shortener-frontend)