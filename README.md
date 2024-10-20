# Spaceship Project

This is a simple CRUD (Create, Read, Update, Delete) API for managing spaceships from popular series and movies. It provides basic operations to manage a spaceship database, with additional features such as search, validation, logging, exception handling, caching, and Swagger API documentation.

## Features

- **CRUD Operations**: Create, retrieve, update, and delete spaceships.
- **Search**: Filter spaceships by name.
- **Validation**: Spaceship IDs and names are validated to ensure consistency (e.g., no negative IDs, unique names).
- **Logging**: Tracks invalid inputs (such as negative IDs) using `@Aspect`.
- **Centralized Exception Handling**: Handles errors consistently across the application.
- **Caching**: Implements caching with EhCache to optimize performance.
- **Swagger UI**: Auto-generated API documentation for easier testing and exploration.

## Technologies

- **Java 21**
- **Spring Boot**
- **Spring Data JPA**
- **H2 In-Memory Database**
- **Lombok**
- **Maven**
- **EhCache**
- **Swagger**
- **JUnit**

## Prerequisites

Make sure you have the following installed:

- Java 21 or newer
- Maven 3.8+
- Docker

## Installation

To install and build the project, follow these steps:

1. Clone the repository:
    ```bash
    git clone https://github.com/Zeusrm1/SpaceshipW2M
    cd spaceship-project
    ```

2. Build the project with Maven:
    ```bash
    mvn clean install -DskipTests
    ```

## Usage

You can run the application using Docker.

Build the Docker image:
```bash
docker build -t spring-boot-app .
```
Run the Docker container:
```bash
docker-compose up
```

Or you can build and run with:
```bash
docker-compose up --build
``` 

The application will be available at http://localhost:8080.

To access the Swagger UI, navigate to http://localhost:8080/swagger-ui.html.

# Security

The application uses basic authentication with the following credentials:

- **User Name**: user
- **Password**: password

# Database

The application uses an in-memory H2 database.

To access the H2 console, navigate to http://localhost:8080/h2-console and use the following settings:

- **JDBC URL**: jdbc:h2:mem:spaceshipdb
- **Driver Class**: org.h2.Driver
- **User Name**: zeusW2M
- **Password**: zeusW2M

