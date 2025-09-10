# Library Management System (LMS)

This is a Library Management System (LMS) built with a microservices architecture using Spring Boot.

## Overview

The project consists of two main services:

*   **Student Service:** Manages student data and handles user authentication.
*   **Book Service:** Manages the book inventory, and the process of issuing and returning books.

## Features

### Student Service

*   CRUD operations for students.
*   User registration and login.
*   JWT-based authentication.

### Book Service

*   CRUD operations for books.
*   Issue books to students.
*   Return books.
*   A simple web interface for managing books and issues.

## Architecture

The LMS is designed as a set of independent microservices that communicate with each other.

*   **Student Service:** The entry point for authentication. It provides a REST API for managing students and generates a JWT upon successful login.
*   **Book Service:** This service handles all book-related operations. It has both a REST API and a Thymeleaf-based web UI. It communicates with the Student Service to fetch student data.

## Technologies Used

*   **Java:** Version 21
*   **Spring Boot:** Version 3.5.5
*   **Spring Security:** For authentication and authorization.
*   **JPA (Hibernate):** For object-relational mapping.
*   **MySQL:** As the relational database.
*   **Maven:** For dependency management.
*   **Lombok:** To reduce boilerplate code.
*   **Thymeleaf:** For the server-side rendered web interface in the Book Service.
*   **JWT:** For securing the APIs.

## Getting Started

### Prerequisites

*   Java 21
*   Maven
*   MySQL

### Database Setup

1.  Make sure you have MySQL running.
2.  Create a database named `library_db`.
3.  Update the database credentials in the `application.properties` file for both services if they differ from the default (`root`/`secret`).

### Installation & Running

1.  **Clone the repository:**
    ```bash
    git clone <repository-url>
    ```
2.  **Run the Student Service:**
    ```bash
    cd student-service
    mvn spring-boot:run
    ```
    The Student Service will start on port `8081`.

3.  **Run the Book Service:**
    ```bash
    cd book-service
    mvn spring-boot:run
    ```
    The Book Service will start on the default port `8080`.

## Configuration

The configuration for each service can be found in their respective `src/main/resources/application.properties` files.

### Student Service (`student-service/src/main/resources/application.properties`)

```properties
spring.application.name=student-service
server.port=8081
spring.datasource.url=jdbc:mysql://localhost:3306/library_db
spring.datasource.username=root
spring.datasource.password=secret
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
jwt.secret=7300f6b8e4ccf54fa2451bd617af2f0c08d1dd01dd0055cfefd0e97d13d56010
```

### Book Service (`book-service/src/main/resources/application.properties`)

```properties
spring.application.name=book-service
spring.datasource.url=jdbc:mysql://localhost:3306/library_db
spring.datasource.username=root
spring.datasource.password=secret
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
```

## How to Use

1.  Once both services are running, you can access the Book Service web interface at `http://localhost:8080`.
2.  You will be redirected to a login page. Use the credentials of a registered user to log in.
3.  After logging in, you can view, add, update, and delete books, as well as issue and return them.
