# GreenSpace Backend

The backend service for **GreenSpace**, an urban gardening platform that connects garden owners, gardeners, and community members. Built with **Java 21** and **Spring Boot**, it provides a robust RESTful API, real-time messaging, and secure authentication to power the GreenSpace frontend.

## 🚀 Technologies Used

- **Framework**: Spring Boot 3
- **Language**: Java 21
- **Security**: Spring Security & JWT (JSON Web Tokens)
- **Database**: MySQL (with Spring Data JPA / Hibernate)
- **Database Migrations**: Flyway
- **Caching & Pub/Sub**: Redis
- **Real-Time Communication**: Spring WebSockets
- **DTO Mapping**: MapStruct
- **Boilerplate Reduction**: Lombok
- **API Documentation**: OpenAPI (Swagger UI)
- **Containerization**: Docker & Docker Compose

## 📁 Project Structure

The project follows a standard layered architecture:

```
src/main/java/com/greenspace
 ├── config/       # Configuration classes (Security, Redis, WebSockets, OpenAPI)
 ├── controller/   # REST API controllers
 ├── dto/          # Data Transfer Objects for API requests/responses
 ├── entity/       # JPA domain entities
 ├── enums/        # Enumerations used across the domain
 ├── exception/    # Global exception handlers and custom exceptions
 ├── mapper/       # MapStruct mappers bridging Entities and DTOs
 ├── repository/   # Spring Data JPA repositories
 ├── security/     # JWT filters, UserDetails service, and Auth config
 ├── service/      # Business logic layer
 └── websocket/    # WebSocket handlers and listeners
```

## 🔑 Core Features

1. **Authentication & Authorization**: Role-based access control (ADMIN, OWNER, GARDENER) using JWT.
2. **Garden Management**: CRUD operations for garden spaces, including image management and status tracking.
3. **Marketplace / Products**: Manage gardening products, tools, and produce for sale or barter.
4. **Reservations System**: Workflow for garden booking requests (Pending, Accepted, Rejected).
5. **Real-time Chat**: WebSocket-based messaging between users (backed by Redis Pub/Sub for scalability).

## 🛠️ Prerequisites

To run this project locally, you need:
- **Java 21** installed (or rely on Docker)
- **Maven** (optional, wrapper is included)
- **Docker & Docker Compose** (for spinning up dependencies like MySQL and Redis)

## ⚙️ Setup & Installation

### 1. Configure Environment Variables
Copy the example environment file and populate it with your local secrets.
```bash
cp .env.example .env
```
Ensure you set the `DB_PASSWORD`, `MYSQL_ROOT_PASSWORD`, and a strong `JWT_SECRET` in your `.env` file.

### 2. Start Infrastructure via Docker Compose
GreenSpace relies on MySQL and Redis. Start them seamlessly using Docker Compose:
```bash
docker-compose up -d db redis
```

### 3. Run the Application
You can run the application directly using the Maven wrapper:
```bash
./mvnw spring-boot:run
```
Alternatively, you can start the entire stack (Backend, Frontend, DB, Redis) via Docker Compose:
```bash
docker-compose up -d --build
```

### 4. Database Initialization
**Flyway** is integrated into the application startup sequence. It will automatically detect the database and run all migration scripts located in `src/main/resources/db/migration/` (e.g., `V1__init_schema.sql`) when the Spring Boot application boots up.

## 📚 API Documentation

Once the application is running, you can explore and test the API endpoints interactively using Swagger UI:

- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **OpenAPI JSON Spec**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

## 🧪 Testing

To run the unit and integration tests:
```bash
./mvnw test
```

## 🤝 Contributing

When contributing to this repository, please ensure that:
1. All changes are covered by tests.
2. DTOs and Entities are strictly mapped using `MapStruct` (avoid manual mapping where possible).
3. Database schema modifications are handled gracefully by adding new `V{version}__description.sql` files in the Flyway migration folder.

## 📄 License

This project is proprietary and intended for the GreenSpace platform. See the `LICENSE` file for further details.
