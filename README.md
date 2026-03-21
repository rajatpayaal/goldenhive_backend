# Golden Hive Backend

Spring Boot REST API backend for Golden Hive application.

## Prerequisites

- Java 21+
- Maven 3.9+
- Docker & Docker Compose (for containerized deployment)

## Project Structure

```
goldenhive_backend/
├── src/main/java/com/hive/goldenhive/
│   ├── GoldenHiveApplication.java
│   └── controller/
│       └── HealthController.java
├── src/main/resources/
│   └── application.properties
├── pom.xml
├── Dockerfile
├── docker-compose.yml
└── README.md
```

## Local Development

### Build the project

```bash
mvn clean package
```

### Run locally with Docker Compose

```bash
docker-compose up
```

This will start:
- MySQL database on port 3306
- Spring Boot application on port 8080

### Access the API

Health check endpoint:
```bash
curl http://localhost:8080/api/health
```

Expected response:
```json
{
  "status": "ok",
  "message": "Golden Hive Backend is running",
  "timestamp": 1621234567890
}
```

## GitHub Actions CI/CD

The project is configured with GitHub Actions for automatic:
- Code build
- Docker image creation
- Push to Docker Hub

### Setup

1. Add GitHub secrets:
   - `DOCKER_USERNAME` - Your Docker Hub username
   - `DOCKER_TOKEN` - Your Docker Personal Access Token

2. Push to `main` branch to trigger the workflow

## Database Configuration

Default configuration in `application.properties`:
- Database: MySQL
- Host: mysql (in Docker) / localhost (local)
- Port: 3306
- Database name: goldenhive
- User: root
- Password: root123

## Dockerfile

Multi-stage Dockerfile that:
1. Builds the JAR using Maven
2. Runs the JAR with OpenJDK 21

## License

MIT
