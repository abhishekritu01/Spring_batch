
[//]: # (This file is best viewed in a markdown viewer)

# README

## Description
This repository contains a simple example of a Spring Boot Batch application. It demonstrates how to create batch jobs using the Spring Batch framework in a Spring Boot application.

## Prerequisites

- Java 8 or higher
- Maven 3.6.3 or higher
- spring-boot
- spring-boot-starter-batch
- spring-boot-starter-data-jpa
- spring-boot-starter-web
- postgresql
- lombok
- JPA (Java Persistence API)


## Installation
- Clone the repository using the following command:
```bash
git clone
```

- Navigate to the project directory:
```bash
cd spring-boot-batch-processing
```

- Run the application using the following command:
```bash
mvn spring-boot:run
```

- The application will start and run on port 8080.
- Open a web browser and navigate to the following URL:
```bash
http://localhost:8080
```

## Usage
- The application contains a simple batch job that reads data from a CSV file and writes it to a database.
- The CSV file is located in the resources folder of the project.
- The batch job is configured in the BatchConfiguration class.
- The job reads data from the CSV file using a FlatFileItemReader and writes it to the database using a JpaItemWriter.
- The job is executed when the application starts.
- The data is written to the database using a JPA repository.
- The data can be viewed by navigating to the following URL:
```bash
http://localhost:8080/users

```

## Configuration
- The application.properties file contains the configuration for the database connection.
- The application uses a PostgreSQL database.
- The database connection properties can be configured in the application.properties file.
- The database schema is created automatically when the application starts.
- The schema is created using the schema.sql file located in the resources folder.
- The schema.sql file contains the SQL script to create the database table.
- The schema is created using the JPA entity class User.
- The User class is annotated with the @Entity annotation to indicate that it is a JPA entity.
- The User class is also annotated with the @Table annotation to specify the name of the database table.
- The User class contains fields that map to the columns in the database table.
- The User class is used by the JPA repository to interact with the database.
- The JPA repository is created by extending the JpaRepository interface.
- The JpaRepository interface provides CRUD operations for the database table.
- The JPA repository is autowired into the BatchConfiguration class to write data to the database.
- The application.properties file contains the configuration for the batch job.

## License
This project is licensed under the MIT License - see the LICENSE file for details.