# Auto-Configuration in Spring Boot: Example with DataSource

## **Introduction to Auto-Configuration**
Spring Boot simplifies application development by reducing the need for extensive configuration. One of its key features is **auto-configuration**, which automatically sets up a Spring application based on the dependencies available on the classpath and the properties defined in `application.properties` or `application.yml`.

This approach exemplifies **convention over configuration**. Instead of requiring you to explicitly define common configurations (like setting up a `DataSource`), Spring Boot assumes sensible defaults and automatically configures them, reducing boilerplate code and allowing developers to focus on business logic.

---

## **Three Ways to Work with Databases in Spring Boot**
Spring Boot provides three main ways to work with a database:
1. **Plain JDBC** (Most control, but high boilerplate code)
2. **Spring JDBC Template** (Reduces boilerplate but still requires manual SQL)
3. **Spring Data JPA** (Most abstraction, recommended for CRUD applications)

---

## **Understanding the Controller-Service-Repository Layers**
When working with a database in a Spring Boot application, a common design pattern follows a layered architecture. The three primary layers involved are:

### **1. Controller Layer**
The Controller layer is responsible for handling HTTP requests and returning responses. It acts as the entry point for the client and delegates the business logic processing to the Service layer. This layer typically deals with request validation, authentication, and response formatting.

### **2. Service Layer**
The Service layer contains the business logic of the application. It interacts with the Repository layer to fetch or manipulate data and applies any necessary transformations. This layer helps maintain separation of concerns by keeping the business logic independent of the data access logic.

### **3. Repository Layer**
The Repository layer is responsible for interacting with the database. In Spring Boot, this is often implemented using Spring Data JPA, which provides an abstraction over CRUD operations and query execution. This layer ensures that database operations are performed efficiently and in a structured manner.

---

### **How These Layers Interact**
1. The **Controller** receives an HTTP request and delegates it to the **Service**.
2. The **Service** processes the request, applying business rules, and interacts with the **Repository** to fetch or update data.
3. The **Repository** communicates with the database and returns the required data to the **Service**.
4. The **Service** processes the data and returns it to the **Controller**.
5. The **Controller** sends the response back to the client.

This layered approach promotes modularity, maintainability, and scalability in a Spring Boot application.

## Connecting Spring Boot to PostgreSQL

### Introduction to PostgreSQL
PostgreSQL is a powerful, open-source relational database management system (RDBMS). It is known for its extensibility, standards compliance, and strong ACID (Atomicity, Consistency, Isolation, Durability) properties. It is widely used for both small and enterprise applications.

#### **PostgreSQL Objects Overview**
When setting up a PostgreSQL database for a Spring Boot application, the following objects are important:
- **Database (Mandatory)**: The central storage container.
- **Schema (Optional)**: Organizes database objects.
- **Tables (Mandatory)**: Stores actual data.
- **Users & Roles (Optional)**: Manage security (default `postgres` user exists).

By default:
- PostgreSQL has a superuser `postgres` with no password.
- Remote connections are **not** allowed unless configured.
- New users **must be created manually** for access control.

---
###  **Installing PostgreSQL on Ubuntu**
To install PostgreSQL and configure it for local development:

```bash
sudo apt update
sudo apt install postgresql postgresql-contrib -y
sudo systemctl start postgresql
sudo systemctl enable postgresql
```

####  **Set Password for Default User**
Since the default `postgres` user has no password, set one using the following steps:
```bash
sudo -u postgres psql
```
Inside the `psql` shell, assign a password:
```sql
ALTER USER "postgres" WITH PASSWORD 'yourpassword';
```
Exit `psql`:
```sql
\q
```

####  **Set Up the PostgreSQL Database (Using psql CLI)**
#####  **Login to PostgreSQL CLI**
```bash
sudo -u postgres psql
```
#####  **Create a Database**
```sql
CREATE DATABASE my_app_db;
```
#####  **Switch to the New Database**
```sql
\c my_app_db
```
#####  **Create a Schema**
```sql
CREATE SCHEMA my_schema;
```
#####  **Create a Table**
```sql
CREATE TABLE my_schema.person (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    surname VARCHAR(100) NOT NULL,
    city VARCHAR(100) NOT NULL,
    age INT NOT NULL
);
```
#### **Verify Database Creation**
```sql
\l  -- List databases
```
#####  **Verify Schema Creation**
```sql
\dn -- List schemas
```
#####  **Verify Table Creation**
```sql
\dt my_schema.*  -- List tables
```

---
<br>
<br>
<br>
<br>
<br>
IMPORTANT CAVEAT

**THE FOLLOWING EXAMPLES ASSUME Java 8 TO BE THE SDK IN USE; HOWEVER, IN THE PROVIDED ATTACHMENTS, YOU MAY FIND THE CODE OF THESE EXAMPLES FOR JAVA 11 AND JAVA 21 TOO; THERE ARE MINOR CHANGES FOR DEPENDENCIES VERSIONS AND COMPILE STRATEGY; COMPARE THE pom.xml AND THE CLASS Person.java. REMEMBER TO SWITCH PROJECT SDK WHEN USING THE JAVA 11 AND JAVA 21 VERSIONS OF THE EXAMPLE.**
<br>
<br>
<br>
<br>
<br>
---

## **Plain JDBC Example in Spring Boot with IntelliJ and Maven**

### **Create a New Maven Project in IntelliJ**
1. Open IntelliJ IDEA.
2. Create a **Java Project with Maven** and set Java 8 as the Project SDK.
3. Use the following `pom.xml`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>springbootjdbctemplate</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>

    <dependencies>
        <!-- Spring Boot Web Starter for REST Controllers -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <version>2.7.8</version> <!-- Ensure version matches the JDBC starter -->
        </dependency>

        <!-- Spring Boot JDBC Starter for plain JDBC and JDBC template -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
            <version>2.7.8</version> <!-- Specify the version explicitly -->
        </dependency>

        <!-- PostgreSQL Database Driver -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>42.5.0</version> <!-- Specify the version explicitly -->
            <scope>runtime</scope>
        </dependency>

        <!-- Lombok for reducing boilerplate code -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.26</version> <!-- Check for the latest version -->
            <scope>provided</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <!-- Configures the Maven Compiler Plugin to use Java 8 for source and target compatibility.
                     If this setting is missing, Maven will use its default Java version, which depends on the JDK version being used.
                     Older versions of Maven default to Java 1.5 or 1.6, while newer versions typically use the installed JDK version.
                     This could lead to unexpected compilation issues if the JDK is newer but the code relies on an older version's features. -->
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>8</source>  <!-- Sets the source (Java version used for compiling) to Java 8 -->
                    <target>8</target>  <!-- Sets the target (bytecode version) to Java 8 -->
                </configuration>
            </plugin>
        </plugins>
    </build>
    
</project>
```

#### Maven Dependencies Scope  
In Maven, dependencies have different **scopes** that determine their availability in various build phases. The default scope for a Maven dependency is **`compile`**, which means the dependency is available at compile-time, runtime, and included in dependent projects.  

However, some dependencies, like database drivers, are typically only required at **runtime** and not needed during compilation. In such cases, it's better to set the scope to **`runtime`** to avoid unnecessary dependencies in the compile phase.

Using **`runtime`** ensures that the PostgreSQL driver is available when running the application but not included in the compilation process, reducing unnecessary dependencies.

The **`provided`** scope ensures Lombok is available at compile-time but is not included in the final build, as it only generates code during compilation.

### **Basic `application.properties` for Database Configuration**
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/my_app_db
spring.datasource.username=postgres
spring.datasource.password=yourpassword
spring.datasource.driver-class-name=org.postgresql.Driver

server.port=8080
```

## **2. Project Directory Structure**
```
jdbc-springboot/
│── src/main/java/com/example/
│   ├── controller/
│   │   ├── PersonController.java
│   ├── service/
│   │   ├── PersonService.java
│   ├── repository/
│   │   ├── PersonRepository.java
│   ├── model/
│   │   ├── Person.java
│   ├── JdbcSpringBootApplication.java
│── src/main/resources/
│   ├── application.properties
│── pom.xml
```

## **3. Implementing the Layers**

### **Entity Model: `Person.java`**  

An **entity model** represents a real-world object mapped to a database table. In this case, the `Person` class corresponds to the `person` table in PostgreSQL. This class typically contains fields that match the table's columns and is used to transfer data between different application layers.

Using **Lombok** annotations, we can reduce boilerplate code by automatically generating getters, setters, and constructors.

#### **With Explicit Lombok Annotations**
```java
package com.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private String name;
    private String surname;
    private String city;
    private int age;
}
```

#### **With @Data (Shorthand)**
```java
package com.example.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private String name;
    private String surname;
    private String city;
    private int age;
}
```

The @Data annotation is a convenient shortcut that includes @Getter, @Setter, @ToString, @EqualsAndHashCode, and @RequiredArgsConstructor by default.

`@Data` implicitly includes a `@RequiredArgsConstructor`. This means Lombok will generate a constructor for any `final` fields and any fields annotated with `@NonNull`. So, if all fields in your class are either `final` or `@NonNull`, this constructor is effectively already generated by `@Data`.

If you want to add more specific constructors (e.g. for a default constructor or all-args constructor), you'd need to explicitly use `@NoArgsConstructor` and `@AllArgsConstructor`.

### **Repository Layer: `PersonRepository.java`**
```java
package com.example.repository;

import com.example.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class PersonRepository {
    private final DataSource dataSource;

    @Autowired // Explicitly declare constructor injection
    public PersonRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addPerson(Person person) {
        String sql = "INSERT INTO my_schema.person (name, surname, city, age) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, person.getName());
            statement.setString(2, person.getSurname());
            statement.setString(3, person.getCity());
            statement.setInt(4, person.getAge());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

`DataSource` is an interface from the **Java JDBC API** (`javax.sql.DataSource`) that provides a standardized way to manage database connections. In a **Spring Boot** project, `DataSource` is automatically configured by Spring Boot's **auto-configuration mechanism** based on the database settings provided in `application.properties` or `application.yml`.  

By default, Spring Boot detects the database driver and configures a connection pool (e.g. **HikariCP**, **Tomcat JDBC**, or **Apache DBCP**) to efficiently manage connections. If no database settings are provided, Spring Boot may default to an **in-memory database** like H2.  

In your `PersonRepository` class, `DataSource` is injected via **constructor injection**. Spring Boot's **dependency injection container** automatically provides an instance of `DataSource` that matches the configured database settings, allowing the repository to obtain connections and execute SQL queries.

### **Service Layer: `PersonService.java`**
```java
package com.example.service;

import com.example.model.Person;
import com.example.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired // Explicitly declare constructor injection
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void savePerson(Person person) {
        personRepository.addPerson(person);
    }
}
```

### **Controller Layer: `PersonController.java`**
```java
package com.example.controller;

import com.example.model.Person;
import com.example.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/person")
public class PersonController {
    private final PersonService personService;

    @Autowired // Explicitly declare constructor injection
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/add")
    public String addPerson(@RequestParam String name,
                            @RequestParam String surname,
                            @RequestParam String city,
                            @RequestParam int age) {
        Person person = new Person(name, surname, city, age);
        personService.savePerson(person);
        return "Person added successfully";
    }
}
```

### **Entry Point: `JdbcSpringBootApplication.java`**  

The entry point of a Spring Boot application is the class annotated with `@SpringBootApplication`. This class initializes the Spring context and starts the embedded server.

```java
package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JdbcSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(JdbcSpringBootApplication.class, args);
    }
}
```

## **4. Running the Application in IntelliJ**
1. Open IntelliJ and load the project.
2. Navigate to `JdbcSpringBootApplication.java`.
3. Click the **Run (▶) button** in IntelliJ to start the application.

## **5. Testing the API**
Run from within a terminal:

```bash
curl -X POST "http://localhost:8080/api/person/add" \
     -d "name=John" \
     -d "surname=Doe" \
     -d "city=NewYork" \
     -d "age=30"

```

If successful, the response will be:

```
Person added successfully
```

The operation of inserting a new person into the system must use **POST** and not **GET** because it involves **creating new data** on the server. The **POST** method is specifically designed for operations that modify data, such as adding, updating, or deleting resources. 

- **GET** requests are intended for **retrieving data**, and they should be idempotent, meaning they do not change the state of the server. Using GET for an action that modifies the server's data (like adding a new person) would violate this principle.
- **POST** is used for actions that create or modify resources. In this case, it’s necessary to send the data (name, surname, city, age) in the request body, which aligns with the intended behavior of the endpoint (adding a new person).

In summary, **POST** should be used for actions that change the state of the server, such as creating or updating data, while **GET** is used for retrieving information without causing side effects.

**POST** is considered non-idempotent, meaning that if you send the same request multiple times, it may create multiple identical resources. In this case, posting the same person twice will result in two separate records being added to the database, each with the same details, potentially creating duplicates.

## **6. Verifying the Inserted Data in PostgreSQL**
### **Login to PostgreSQL**
```bash
sudo -u postgres psql -d my_app_db
```
### **Check Inserted Data**
```sql
SELECT * FROM my_schema.person;
```
### **Expected Output:**
```
 id |  name  | surname |   city   | age
----+--------+---------+----------+-----
  1 | John   | Doe     | NewYork  |  30
```

This confirms that the row has been inserted successfully!

---

## **Spring Boot JDBC Template Example (Less Boilerplate, SQL Control)**

Spring JDBC Template simplifies interactions with the database while still allowing custom SQL.

Using **Spring's JdbcTemplate** over plain JDBC offers several advantages. It simplifies database operations by handling boilerplate code like opening/closing connections, managing exceptions, and automatically releasing resources. JdbcTemplate also provides a cleaner, more consistent API for executing SQL queries, reducing the risk of errors and making the code easier to maintain. Additionally, it integrates seamlessly with Spring's transaction management and helps prevent common issues like SQL injection and connection leaks.

---

### **Directory Structure**
```
spring-boot-jdbc/
├── src/main/java/com/example/
│   ├── JdbcTemplateSpringBootApplication.java  # Main Application
│   ├── controller/
│   │   ├── PersonController.java
│   ├── service/
│   │   ├── PersonService.java
│   ├── repository/
│   │   ├── PersonRepository.java
│   ├── model/
│   │   ├── Person.java
├── src/main/resources/
│   ├── application.properties
├── pom.xml
```

---

### **Create a New Maven Project in IntelliJ**
1. Open IntelliJ IDEA.
2. Create a **Java Project with Maven** and set Java 8 as the Project SDK.
3. Use the following `pom.xml`:

#### **Maven Dependencies (`pom.xml`)**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>springbootjdbctemplate</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>

    <dependencies>
        <!-- Spring Boot Web Starter for REST Controllers -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <version>2.7.8</version> <!-- Ensure version matches the JDBC starter -->
        </dependency>

        <!-- Spring Boot JDBC Starter for plain JDBC and JDBC template -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
            <version>2.7.8</version> <!-- Specify the version explicitly -->
        </dependency>

        <!-- PostgreSQL Database Driver -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>42.5.0</version> <!-- Specify the version explicitly -->
            <scope>runtime</scope>
        </dependency>

        <!-- Lombok for reducing boilerplate code -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.26</version> <!-- Check for the latest version -->
            <scope>provided</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <!-- Configures the Maven Compiler Plugin to use Java 8 for source and target compatibility.
                     If this setting is missing, Maven will use its default Java version, which depends on the JDK version being used.
                     Older versions of Maven default to Java 1.5 or 1.6, while newer versions typically use the installed JDK version.
                     This could lead to unexpected compilation issues if the JDK is newer but the code relies on an older version's features. -->
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>8</source>  <!-- Sets the source (Java version used for compiling) to Java 8 -->
                    <target>8</target>  <!-- Sets the target (bytecode version) to Java 8 -->
                </configuration>
            </plugin>
        </plugins>
    </build>
    
</project>
```

#### **Maven Scopes Explanation**

**Maven Dependency Scopes: `runtime` vs `provided`**

When managing dependencies in Maven, the scope defines when a dependency is required during the build process. It helps optimize build times, control runtime environments, and avoid unnecessary dependencies in the final artifact. Here's a breakdown of the `runtime` scope used for the PostgreSQL JDBC driver and the `provided` scope used for Lombok.

- **PostgreSQL JDBC Driver: `runtime`**

**Default Scope: `compile`**  
By default, Maven assigns the `compile` scope, meaning dependencies are required both during compilation and runtime. However, some dependencies, like the PostgreSQL JDBC driver, are only needed at runtime.

**Why Use `runtime`?**  
For PostgreSQL, the JDBC driver is required only when the application connects to the database at runtime, not during compilation. Using `runtime` scope:
- **Includes the dependency only at runtime**, reducing compilation phase overhead.
- Avoids packaging the dependency in the final artifact during development, keeping it lighter.
- Ensures the driver is available in the runtime classpath but not the compile-time classpath.

- **Lombok: `provided`**

**Default Scope: `compile`**  
Lombok, which generates boilerplate code during compilation, is also assigned the `compile` scope by default. However, it’s unnecessary at runtime.

**Why Use `provided`?**  
Lombok is used during development for generating code (like getters, setters), but it’s not needed after the application is compiled. Using `provided` scope:
- **Includes Lombok only at compile-time**, but excludes it from the final runtime artifact.
- Assumes the runtime environment will provide Lombok if needed.
- Reduces the size of the final artifact by excluding development-only dependencies.

**Summary of Default Scopes vs. Specific Scopes**

| Dependency             | Default Scope | Chosen Scope | Explanation                                                                                                                                     |
|------------------------|---------------|--------------|-------------------------------------------------------------------------------------------------------------------------------------------------|
| PostgreSQL JDBC Driver | `compile`     | `runtime`    | PostgreSQL is needed only at runtime for database connectivity, reducing unnecessary dependency during compile phase.                           |
| Lombok                 | `compile`     | `provided`   | Lombok is required at compile-time for code generation, but not at runtime. The `provided` scope ensures it's excluded from the final artifact. |

- **`compile` (default scope)**: Available at both compile-time and runtime, and included in the final packaged artifact (JAR, WAR).
- **`provided`**: Required at compile-time but not included in the final artifact. Assumes the runtime environment (e.g. application server) provides it. Common for servlet APIs and libraries like Lombok.
- **`runtime`**: Required only at runtime, not at compile-time. Useful for dependencies like JDBC drivers that are needed for execution but not during the build process.
- **`test`**: Required only during test compilation and execution. Not included in the final artifact, used exclusively for testing purposes.

---

### **Spring JDBC Template `application.properties`**
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/my_app_db
spring.datasource.username=postgres
spring.datasource.password=yourpassword
spring.datasource.driver-class-name=org.postgresql.Driver

server.port=8081
```

#### **Default Behaviour for DataSource Properties**

When configuring the `application.properties` for a Spring Boot project, certain properties are essential for establishing a connection to your database, while others are optional or have default values if omitted. Below is an explanation of what happens when these properties are omitted:

- **`spring.datasource.url`**:  
  This is a required property, as it defines the JDBC URL for connecting to the database. If omitted, Spring Boot will attempt to connect to an in-memory database (like H2) by default. It's crucial to define this property with the correct URL to connect to your actual database, such as `jdbc:postgresql://localhost:5432/my_app_db`.

- **`spring.datasource.username`**:  
  This property specifies the database username. If omitted, Spring Boot defaults to using the `root` username, which works for some databases but may cause issues depending on your specific setup. It's recommended to always define this for consistency and security.

- **`spring.datasource.password`**:  
  This is the database password for the username provided. If omitted, no password will be used, which is not recommended, especially in production environments. Defining the password ensures secure connections to the database.

- **`spring.datasource.driver-class-name`**:  
  This property defines the JDBC driver to be used. While Spring Boot generally auto-detects the appropriate driver based on the database URL, it's always a good practice to explicitly define it. For example, the PostgreSQL driver class name is `org.postgresql.Driver`. Omitting this may lead to issues if auto-detection fails.

#### **Optional Configuration**  

- **`spring.datasource.hikari.maximum-pool-size`**:  
  This specifies the maximum number of connections in the HikariCP connection pool, which Spring Boot uses by default for database connections. If omitted, Spring Boot will default to a pool size of 10. However, adjusting this value may be necessary depending on the application's load and connection requirements.

- **`server.port`**:  
  This defines the port on which the Spring Boot application will run. By default, Spring Boot uses port `8080`. If you want to run the application on a different port (e.g. `8081` to avoid conflicts with another service), you can specify this property. If omitted, the default port of `8080` will be used.

---

### **Entity Model: `Person.java`**
An **Entity/Model class** is typically used in Java applications to represent data. This class may hold fields corresponding to the columns in a database (for an entity), or it may represent a data structure in the application (for a model). For instance, in this example, the `Person` class is used to represent a person, with fields like `id`, `name`, `surname`, `city`, and `age`.

In this response, I’ll provide two versions of the `Person` class:

1. **Using Lombok `@Data`**: This annotation automatically generates getters, setters, `toString()`, `equals()`, and `hashCode()` methods.
2. **Using Lombok `@Getter`/`@Setter` Explicitly**: Instead of using `@Data`, we will use `@Getter` and `@Setter` to explicitly generate the getter and setter methods only.


**Using Lombok `@Data`**:

```java
package com.example.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private String name;
    private String surname;
    private String city;
    private int age;
}
```
Explanation:
- `@Data` generates getters, setters, `toString()`, `equals()`, and `hashCode()`.
- `@AllArgsConstructor` generates a constructor with all fields.
- `@NoArgsConstructor` generates a no-args constructor.

**Using Lombok `@Getter`/`@Setter` Explicitly**

```java
package com.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private String name;
    private String surname;
    private String city;
    private int age;
}
```

---

### **Repository Layer: `PersonRepository.java`**
```java
package com.example.repository;

import com.example.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PersonRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired // Explicitly declare constructor injection
    public PersonRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertPerson(Person person) {
        String sql = "INSERT INTO my_schema.person (name, surname, city, age) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, person.getName(), person.getSurname(), person.getCity(), person.getAge());
    }
}
```

#### **Explanation**

The **Repository Layer** is responsible for managing the persistence of data by interacting directly with the database. In this case, the `PersonRepository` class handles all database operations related to the `Person` entity. It uses Spring's `JdbcTemplate` to execute SQL queries, such as inserting a new person into the database.

The role of the repository is to provide a clean abstraction for the service layer to interact with the database. It focuses purely on performing CRUD (Create, Read, Update, Delete) operations and does not involve itself with any business logic. **This separation of concerns allows the service layer to focus on business logic while relying on the repository to handle data persistence.**

One of the key benefits of the repository layer is **simplification**. By consolidating all database operations into a single class, it reduces redundancy and makes the codebase easier to maintain. If changes are needed in the way the data is accessed, such as switching from `JdbcTemplate` to another data access technology like JPA, these changes can be made within the repository without affecting the rest of the application.

Additionally, the repository layer provides **flexibility**. By abstracting database operations, the implementation details of how data is accessed are hidden from the rest of the application. This makes it easier to swap out database technologies or modify SQL queries without having to refactor other layers of the application. Finally, by leveraging Spring's `JdbcTemplate`, the repository also provides **SQL abstraction**, helping to prevent issues like SQL injection and reducing the chance of errors in SQL syntax.

---

### **Service Layer: `PersonService.java`**
```java
package com.example.service;

import com.example.model.Person;
import com.example.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired // Explicitly declare constructor injection
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void savePerson(Person person) {
        personRepository.insertPerson(person);
    }
}
```

#### **Explanation**

The **Service Layer** serves as a bridge between the presentation layer (such as controllers) and the data access layer (repositories). It is responsible for handling the application's business logic, ensuring that any necessary rules or operations are performed before data is persisted or retrieved.

In this case, the `PersonService` class contains methods that define how business logic should be applied when working with `Person` data. For example, the `addPerson()` method delegates the responsibility of inserting a person to the `PersonRepository`. However, the service layer can also include additional operations, such as validation or complex transformations, before the data is saved.

The key benefit of the service layer is **modularization**. By separating the business logic from the repository and presentation layers, the application becomes cleaner and more maintainable. The service layer centralizes the logic, making it easier to manage and update. This separation also enhances **testability**, as the service layer can be tested independently using mock repositories. Finally, the service layer ensures **consistency**, as all business logic is located in one place, avoiding duplication and minimizing the risk of errors.

In summary, the service layer plays a vital role in organizing business logic, improving code clarity, and ensuring a clean separation of concerns between the different layers of the application.

---

### **Controller Layer: `PersonController.java`**
```java
package com.example.controller;

import com.example.model.Person;
import com.example.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/person")
public class PersonController {
    private final PersonService personService;

    @Autowired // Explicitly declare constructor injection
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/add")
    public String addPerson(@RequestParam String name,
                            @RequestParam String surname,
                            @RequestParam String city,
                            @RequestParam int age) {
        Person person = new Person(name, surname, city, age);
        personService.savePerson(person);
        return "Person added successfully";
    }
}
```

#### **Explanation**

The **Controller Layer** in this application acts as the entry point for handling HTTP requests. It is responsible for receiving requests from the client, delegating the processing of those requests to the appropriate service layer, and returning a response back to the client.

In this case, the `PersonController` class handles incoming HTTP POST requests to add a new person. The controller receives the request, which includes a `Person` object in the body, and calls the `addPerson()` method of the `PersonService` to handle the logic. After the service layer processes the request, the controller sends a response back to the client indicating the result, such as "Person added successfully."

The role of the controller is to **decouple the HTTP handling** from the core business logic. It ensures that the service layer is only responsible for processing business rules, while the controller focuses on interacting with the client and mapping the response back. This separation of concerns improves **maintainability** and **scalability**, as changes to how HTTP requests are processed (e.g. changing API endpoints or request formats) can be made without altering the underlying service or repository logic.

### How They Work Together:

- **Interaction Flow**:
  - The **controller** receives HTTP requests from the client.
  - It calls the appropriate methods in the **service layer** (e.g. `addPerson()` in `PersonService`).
  - The service layer delegates the actual data handling to the **repository layer** (e.g. `PersonRepository`).
  - The repository uses **JdbcTemplate** to interact with the database and perform SQL operations.
  - The controller sends the response back to the client after the service layer processes the request.

- **Decoupling**:
  - The controller **decouples** HTTP handling from the business logic. 
  - The **service layer** handles business operations, while the controller manages communication with the client.
  - This separation allows changes in one layer (like database access or API endpoints) without affecting others (service or controller).

- **Scalability**:
  - As the application grows, additional methods can be added to the service or repository layers without disrupting existing functionality.
  - New features or complex business rules can be implemented in the service layer, and the repository layer can evolve to support new data sources or more sophisticated queries.

---

### **Entry Point: `JdbcTemplateSpringBootApplication.java`**
```java
package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JdbcTemplateSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(JdbcTemplateSpringBootApplication.class, args);
    }
}
```

---

### **Instructions to Run the Application**
1. Open **IntelliJ IDEA**.
2. Ensure **PostgreSQL is running** and the database/schema/table exist.
3. Click the **Run (▶) button** in IntelliJ to start the application.

---

### **Testing the Endpoint**
Run from within a terminal:

```bash
curl -X POST "http://localhost:8081/api/person/add" \
     -d "name=John" \
     -d "surname=Doe" \
     -d "city=NewYork" \
     -d "age=30"

```

If successful, the response will be:

```
Person added successfully
```

The operation of inserting a new person into the system must use **POST** and not **GET** because it involves **creating new data** on the server. The **POST** method is specifically designed for operations that modify data, such as adding, updating, or deleting resources. 

- **GET** requests are intended for **retrieving data**, and they should be idempotent, meaning they do not change the state of the server. Using GET for an action that modifies the server's data (like adding a new person) would violate this principle.
- **POST** is used for actions that create or modify resources. In this case, it’s necessary to send the data (name, surname, city, age) in the request body, which aligns with the intended behavior of the endpoint (adding a new person).

In summary, **POST** should be used for actions that change the state of the server, such as creating or updating data, while **GET** is used for retrieving information without causing side effects.

**POST** is considered non-idempotent, meaning that if you send the same request multiple times, it may create multiple identical resources. In this case, posting the same person twice will result in two separate records being added to the database, each with the same details, potentially creating duplicates.

---

### **Verify Inserted Data in PostgreSQL**
Login to `psql` and check:
```bash
sudo -u postgres psql -d my_app_db
```
```sql
SELECT * FROM my_schema.person;
```
If successful, you should see:
```
id | name | surname | city      | age
---+------+---------+-----------+----
 1 | John | Doe     | New York  | 30
```

### **Key Takeaways**
✅ **Spring JDBC Template** reduces boilerplate SQL operations.
✅ **`JdbcTemplate`** simplifies querying and updating without manual connection handling.
✅ **Separation of concerns** (Controller → Service → Repository) improves maintainability.
✅ **Lombok** eliminates the need for boilerplate code like getters/setters.

Now your application is ready with **Spring JDBC Template** and a **structured architecture**! 🚀

---

## Spring Data JPA with Hibernate and PostgreSQL

Spring Data JPA is a higher-level, abstraction-driven framework for data access in Java applications, built on the JPA (Java Persistence API) standard. It simplifies interaction with relational databases by providing a repository pattern and automatic implementations for common operations such as CRUD, eliminating the need for boilerplate code.

### The Object-Relational Impedance Mismatch

The mismatch between the object-oriented world and the relational database world is often referred to as the **Object-Relational Impedance Mismatch**. This occurs because:

- **Objects vs. Tables:** In object-oriented programming (OOP), data is stored as objects with properties and methods, whereas in relational databases, data is stored in tables with rows and columns.
  
- **Inheritance vs. Joins:** In OOP, inheritance is a common concept where objects can inherit attributes and behaviors from parent classes. In relational databases, however, tables typically don't have inheritance, and relationships are often represented through joins or foreign keys.

- **Complexity of Relationships:** OOP allows for complex relationships between objects, such as polymorphism and encapsulation. Relational databases represent relationships through simple keys, which can make modeling complex object structures harder.

- **Data Integrity and Constraints:** In OOP, objects can contain both data and behavior (methods), while in relational databases, data is separated from logic, and data integrity is maintained through constraints like primary keys, foreign keys, etc.

This **Object-Relational Impedance Mismatch** creates challenges when trying to map object-oriented models to relational database models. This is where tools like **Hibernate** and **JPA** come in, bridging the gap between the two worlds.

### How Hibernate and Spring Data JPA Help

Spring Data JPA and Hibernate solve the Object-Relational Impedance Mismatch by providing **Object-Relational Mapping (ORM)**. Hibernate is an ORM framework that translates Java objects into relational database tables, allowing developers to work with objects while still storing data in a relational database. This reduces the complexity of directly mapping between objects and relational tables.

Compared to Spring JDBC Template, which requires manual mapping of database rows to Java objects, Spring Data JPA abstracts these complexities by allowing developers to work with entities. It automatically handles the translation of objects to tables and vice versa, letting developers focus on business logic instead of low-level database operations.

The key advantages of Spring Data JPA include automatic query generation, built-in pagination, and seamless integration with the JPA ecosystem (including Hibernate). These features enhance developer productivity, simplify database interactions, and improve code maintainability, making it a more efficient and scalable solution compared to Spring JDBC Template.

#### The Role and Need of Hibernate in Spring Data JPA

Hibernate is an object-relational mapping (ORM) framework that serves as the underlying implementation for Spring Data JPA. While Spring Data JPA defines the high-level abstraction for data access, Hibernate takes care of the actual database operations, translating Java objects into relational tables and managing the complexities of the **relational-object mismatch**.

Hibernate provides critical features such as automatic table creation, lazy loading, caching, and transaction management, all of which simplify database interactions. It also supports advanced features like fetching strategies, cascading operations, and custom queries. These capabilities are essential for effective and efficient data persistence within Spring-based applications.

In short, Hibernate is essential in Spring Data JPA to implement the JPA specifications, enabling smooth interaction between Java objects and relational databases. It abstracts low-level database operations, solving the **relational-object mismatch**, and provides powerful tools for managing persistence more easily and effectively.

### Summary

- **Spring Data JPA:**
  - A high-level, abstraction-driven framework for data access in Java applications.
  - Based on the JPA (Java Persistence API) standard, simplifying database operations.
  - Provides a repository pattern and automatic CRUD implementations.
  - Bridges the gap between relational databases and object-oriented programming, reducing boilerplate code.

- **Improvements Over Spring JDBC Template:**
  - **Less Boilerplate Code:** No need for manual mapping between database rows and Java objects.
  - **Automatic Query Generation:** Queries are generated automatically based on method names.
  - **Focus on Business Logic:** Developers can focus on business logic instead of low-level database interactions.
  - **Object-Oriented Approach:** Leverages entities to handle relational-object mismatch, simplifying database access.

- **Role and Need of Hibernate in Spring Data JPA:**
  - **ORM Provider:** Hibernate manages the object-relational mapping (ORM) in Spring Data JPA.
  - **Automatic Persistence:** Converts Java objects into relational tables and vice versa.
  - **Advanced Features:** Supports lazy loading, caching, transaction management, and custom queries.
  - **Solves Relational-Object Mismatch:** Abstracts complex database operations, making persistence management easier and more efficient.

### **Directory Structure**
```
spring-boot-jpa/
├── src/main/java/com/example/
│   ├── JpaApplication.java  # Main Application
│   ├── controller/
│   │   ├── PersonController.java
│   ├── service/
│   │   ├── PersonService.java
│   ├── repository/
│   │   ├── PersonRepository.java
│   ├── model/
│   │   ├── Person.java
├── src/main/resources/
│   ├── application.properties
├── pom.xml
```

### **Create a New Maven Project in IntelliJ**
1. Open IntelliJ IDEA.
2. Create a **Java Project with Maven** and set Java 8 as the Project SDK..
3. Use the following `pom.xml`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>springbootjpa</artifactId> <!-- Renamed artifact to reflect JPA usage -->
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>

    <dependencies>
        <!-- Spring Boot Web Starter for REST Controllers -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <version>2.7.8</version> <!-- Ensure version matches the Spring Boot version -->
        </dependency>

        <!-- Spring Boot JPA Starter for Spring Data JPA and Hibernate -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
            <version>2.7.8</version> <!-- Specify the version explicitly -->
        </dependency>

        <!-- PostgreSQL Database Driver -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>42.5.0</version> <!-- Specify the version explicitly -->
            <scope>runtime</scope>
        </dependency>

        <!-- Lombok for reducing boilerplate code -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.26</version> <!-- Check for the latest version -->
            <scope>provided</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <!-- Configures the Maven Compiler Plugin to use Java 8 for source and target compatibility -->
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>8</source>  <!-- Sets the source (Java version used for compiling) to Java 8 -->
                    <target>8</target>  <!-- Sets the target (bytecode version) to Java 8 -->
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

**JPA and Hibernate Dependencies in Maven POM**

- **Spring Boot Starter Data JPA**: This dependency includes everything needed to integrate Spring Data JPA into your Spring Boot project. It automatically brings in libraries such as Spring Data JPA and sets up necessary configurations for interacting with databases using JPA, making it easier to work with entities and repositories. Additionally, it automatically includes Hibernate as the default JPA provider, so you don’t need to manually add it as a separate dependency.

- **Hibernate Core**: Hibernate is the implementation of JPA (Java Persistence API). However, when using `spring-boot-starter-data-jpa`, Hibernate is already included as a transitive dependency, meaning you don't need to manually add it. Hibernate provides the actual functionality for object-relational mapping (ORM). It converts Java objects into database tables and handles the persistence layer's complexity, such as transaction management, lazy loading, and caching. Hibernate is what powers Spring Data JPA and ensures that the JPA standards are fulfilled.

Together, these dependencies allow you to efficiently interact with relational databases using the JPA standard, with Hibernate handling the ORM functionality and Spring Data JPA simplifying data access through repositories.

### **Configure Database Connection**
Add `application.properties`:

```properties
# Data source configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/my_app_db
spring.datasource.username=postgres
spring.datasource.password=yourpassword
spring.datasource.driver-class-name=org.postgresql.Driver

# Hibernate properties
# Automatically updates the database schema based on the entities
spring.jpa.hibernate.ddl-auto=update

# Logs the SQL statements to the console for debugging
spring.jpa.show-sql=true

# Formats the SQL logs to make them readable
spring.jpa.properties.hibernate.format_sql=true

# Server port configuration
server.port=8082
```

**JPA and Hibernate Properties in `application.properties`**

- **spring.jpa.properties.hibernate.dialect**: This property defines the Hibernate dialect that tells Hibernate how to communicate with a specific database. The `org.hibernate.dialect.PostgreSQL95Dialect` is used for PostgreSQL, enabling Hibernate to generate appropriate SQL queries for this database.

- **spring.jpa.hibernate.ddl-auto**: This property controls how Hibernate manages the database schema. Setting it to `update` means Hibernate will automatically update the schema to match the entity definitions without dropping existing data. Other possible values are `create`, `create-drop`, and `validate`.

- **spring.jpa.show-sql**: This property enables logging of the SQL statements executed by Hibernate. Setting it to `true` is useful for debugging and monitoring the SQL queries generated by JPA.

- **spring.jpa.properties.hibernate.format_sql**: When set to `true`, this property formats the SQL statements for better readability in the logs. It’s mainly used for debugging to make SQL queries more understandable.

Together, these properties help configure Hibernate's behavior and logging, facilitating the connection to PostgreSQL, managing database schema updates, and logging SQL statements for monitoring purposes.

### **Entity Class**
```java
package com.example.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity // This annotation is needed because we are using Spring Data JPA
@Table(name = "person", schema = "my_schema")
@Getter
@Setter
@NoArgsConstructor // Hibernate requires a no-argument constructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String city;
    private int age;
}
```

In a Spring Data JPA application, the `Person` class is an entity that is mapped to a database table, and it requires annotations like `@Entity` and `@Table`.

- The `@Entity` annotation tells Hibernate that this class represents a table in the database.
- The `@Table` annotation is used to specify the exact name of the table and the schema, which is especially important if you're working with a non-default schema (in this case, "my_schema").

These annotations are necessary for Spring Data JPA to recognize the class and automatically map it to the corresponding table in the database, which is not needed when using a plain JDBC template where you would manually write SQL queries to interact with the database.

For Spring Data JPA, the only constructor strictly required is a no-argument constructor.

- This is because Hibernate needs this constructor to instantiate the entity when saving or fetching data from the database.
- The `@NoArgsConstructor` annotation from Lombok automatically generates this no-argument constructor, which is sufficient for Hibernate to work.

The `id` field, which is annotated with `@Id` and `@GeneratedValue(strategy = GenerationType.IDENTITY)`, is auto-generated by the database.

- Hibernate does not need it to be passed when creating new `Person` instances.
- The database will handle the assignment of the `id` when the entity is saved. Therefore, you don’t need to manually write a constructor that includes the `id` field.

In this case, using `@AllArgsConstructor` or `@RequiredArgsConstructor` is unnecessary because:

- They would generate constructors that include the `id`, which should not be set manually.

The use of Lombok annotations like `@Getter` and `@Setter` simplifies the code by automatically generating getters and setters for all fields, reducing boilerplate code.

Overall, the only necessary constructor for Spring Data JPA is the no-argument constructor, and Hibernate will take care of managing the `id` field automatically when inserting or fetching `Person` records.

**JPA and Hibernate Annotations in the Entity Class**

- **@Entity**: This annotation marks the class as a JPA entity, meaning it represents a table in the relational database. It’s required for Spring Data JPA to recognize this class as a persistent entity that can be managed by Hibernate.

- **@Table**: The `@Table` annotation allows you to specify the name of the database table and the schema where the entity will be mapped. In this case, the entity `Person` is mapped to the table `person` in the schema `my_schema`.

- **@Id**: This annotation defines the primary key of the entity. It tells JPA which field uniquely identifies each record in the table.

- **@GeneratedValue**: This annotation specifies how the primary key value is generated. The `strategy = GenerationType.IDENTITY` indicates that the primary key will be auto-incremented by the database (commonly used for PostgreSQL, MySQL, etc.).

- **@Getter, @Setter**: These annotations are part of the Lombok library and automatically generate getter and setter methods for all the fields in the class, reducing boilerplate code.

- **@NoArgsConstructor**: This Lombok annotation generates a constructor with no arguments, which is required by JPA for entity instantiation.

These annotations play a critical role in defining the structure and behavior of the entity class, enabling Spring Data JPA and Hibernate to correctly map the class to the database and manage persistence operations.

**DDL information in the different approaches**

In the Spring JDBC approach, we explicitly write raw SQL statements for operations like inserting a record into the database. This means that table schema details, such as `my_schema.person`, are defined at the SQL level within repository methods. While this approach provides fine-grained control over queries, it also introduces boilerplate code and tight coupling between the database structure and repository logic.

For example, in a Spring JDBC repository:

```java
@Repository
public class PersonRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertPerson(Person person) {
        String sql = "INSERT INTO my_schema.person (name, surname, city, age) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, person.getName(), person.getSurname(), person.getCity(), person.getAge());
    }
}
```

On the other hand, with Spring Data JPA, we define the DDL-related information directly in the entity class using annotations like `@Entity`, `@Table(name = "person", schema = "my_schema")`, and `@Id`. This approach eliminates the need to manually write SQL statements for basic operations, as JPA handles query generation automatically.

Here’s how the entity is defined in JPA:

```java
@Entity
@Table(name = "person", schema = "my_schema")
@Getter
@Setter
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String city;
    private int age;
}
```

By embedding schema details at the class level, we work primarily with objects and abstract away the complexities of direct SQL manipulation. This results in cleaner, more maintainable, and less error-prone code while leveraging the power of ORM (Object-Relational Mapping).

### **Repository Interface**
```java
package com.example.repository;

import com.example.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    // By extending JpaRepository, the following methods are provided by default:

    // 1. save(S entity) - Saves a given entity, either inserting or updating it.
    // 2. saveAll(Iterable<S> entities) - Saves all given entities.
    // 3. findById(ID id) - Retrieves an entity by its id. Returns an Optional.
    // 4. existsById(ID id) - Checks whether an entity with the given id exists.
    // 5. findAll() - Retrieves all entities of type T.
    // 6. findAllById(Iterable<ID> ids) - Retrieves all entities with the given ids.
    // 7. count() - Returns the number of entities of type T.
    // 8. deleteById(ID id) - Deletes the entity with the given id.
    // 9. delete(T entity) - Deletes a given entity.
    // 10. deleteAllById(Iterable<? extends ID> ids) - Deletes all entities with the given ids.
    // 11. deleteAll(Iterable<? extends T> entities) - Deletes all given entities.
    // 12. deleteAll() - Deletes all entities of type T.

    // These methods are automatically implemented by Spring Data JPA, so no need to manually define them.
}
```

**PersonRepository Interface in Spring Data JPA**

The `PersonRepository` interface in Spring Data JPA extends `JpaRepository`, providing seamless interaction with the database for the `Person` entity without requiring custom SQL or JPQL queries.

1. **Spring Data JPA and `JpaRepository`**:  
   By extending `JpaRepository`, the `PersonRepository` inherits several powerful methods for performing common database operations. These built-in methods are automatically available without needing any manual implementation, such as:

   - **`save()`**: Saves a new `Person` entity or updates an existing one.
   - **`findAll()`**: Retrieves all `Person` records from the database.
   - **`findById()`**: Retrieves a specific `Person` by its unique identifier (primary key).
   - **`delete()`**: Deletes a `Person` entity from the database.
   - **`count()`**: Returns the total number of `Person` records in the table.

2. **No Custom Code in Repository**:  
   `PersonRepository` does not need any custom query code. The methods are automatically implemented by Spring Data JPA based on the repository’s method signatures. For example:

   - Calling `personRepository.save(person)` will automatically generate the necessary SQL to insert or update a `Person` entity.
   - Similarly, calling `personRepository.findById(id)` will generate the SQL to retrieve a `Person` by its ID, without additional configuration.

   This functionality is powered by the **Repository Pattern**, which Spring Data JPA leverages to automatically implement data access methods. This reduces boilerplate code and increases maintainability and productivity.

3. **`@Repository` Annotation**:  
   The `@Repository` annotation marks the `PersonRepository` interface as a Spring-managed component. Spring automatically handles its lifecycle and makes it available for dependency injection wherever needed in the application.

4. **`JpaRepository<Person, Long>`**:  
   By extending `JpaRepository<Person, Long>`, the `PersonRepository` inherits a comprehensive set of CRUD operations tailored to the `Person` entity, which has a primary key of type `Long`. This means `PersonRepository` can access methods like `save()`, `findAll()`, `findById()`, and `delete()`, all without any extra code.

5. **Summary**:
   - The `PersonRepository` interface extends `JpaRepository`, automatically providing built-in CRUD operations for the `Person` entity without requiring custom database queries.
   - Spring Data JPA generates the SQL or JPQL queries at runtime based on the method names, making the codebase cleaner and reducing the need for low-level SQL management.
   - No custom implementation is needed within the `PersonRepository` itself because Spring Data JPA handles query generation and execution automatically.

**Understanding `JpaRepository<Person, Long>`**

By extending `JpaRepository<Person, Long>`, the `PersonRepository` interface inherits a comprehensive set of CRUD (Create, Read, Update, Delete) operations specifically designed for the `Person` entity. The generic parameters `<Person, Long>` indicate that the repository is managing `Person` entities and that the primary key (ID) is of type `Long`.

Key Benefits:

- **Built-in CRUD operations:** The repository automatically provides methods like `save()`, `findAll()`, `findById()`, and `delete()` without requiring explicit implementation.
- **Less boilerplate code:** Eliminates the need for writing SQL queries or manually implementing repository logic.
- **Integration with JPA and Hibernate:** Works seamlessly with JPA’s ORM capabilities, handling entity persistence and retrieval efficiently.
- **Optional Pagination and Sorting:** By extending `JpaRepository`, we also inherit methods that allow sorting and paginating results easily, such as `findAll(Sort sort)` and `findAll(Pageable pageable).`
- **Type Safety:** Since we specify `Person` as the entity type and `Long` as the ID type, Spring Data JPA enforces correct usage at compile-time.

Example usage of the `PersonRepository`:

```java
@Autowired
private PersonRepository personRepository;

public void exampleUsage() {
    // Saving a new person
    Person person = new Person();
    person.setName("John");
    person.setSurname("Doe");
    person.setCity("New York");
    person.setAge(30);
    personRepository.save(person);

    // Finding all persons
    List<Person> people = personRepository.findAll();
    
    // Finding a person by ID
    Optional<Person> foundPerson = personRepository.findById(1L);
    foundPerson.ifPresent(System.out::println);
}
```

By leveraging `JpaRepository`, we significantly reduce the amount of code needed to interact with the database while maintaining flexibility and efficiency in data management.

**While Spring Data JPA provides a powerful set of CRUD operations out of the box, there are cases where more complex queries, such as those involving specific filtering criteria, cannot be autogenerated. In these scenarios, we need to define custom queries using JPQL (Java Persistence Query Language) or native SQL.**

### **Service Class**
```java
package com.example.service;

import com.example.model.Person;
import com.example.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    // Save or update a person
    public Person savePerson(Person person) {
        return personRepository.save(person); // Uses JpaRepository's save() method
    }
}
```

**Person Service**

The `PersonService` class contains the business logic related to the `Person` entity and interacts with the repository to persist `Person` data in the database. It serves as a middle layer between the web layer (e.g. controllers) and the data layer (repositories), helping to separate application logic from data access.

**`@Service` Annotation**:  
The `@Service` annotation designates the `PersonService` class as a service component within the Spring application context. It indicates that the class contains business logic, and Spring should manage it using dependency injection. The service can be injected into other components, such as controllers, enabling access to business operations.

- **Constructor Injection**:  
  The `PersonRepository` is injected into the `PersonService` class through constructor injection. Constructor injection is preferred in Spring because it ensures that the service's dependencies are provided when the object is created. This approach also promotes immutability, testability, and maintainability of the service class.

- **`savePerson()` Method**:  
  The `savePerson()` method in the `PersonService` class accepts a `Person` object and calls `personRepository.save()` to persist the new `Person` entity to the database. This method abstracts the logic of saving a `Person` and encapsulates the interaction with the repository. By delegating persistence operations to the repository, it follows the principle of separation of concerns and makes the code easier to maintain and update in the future.

### **Controller Class**
```java
package com.example.controller;

import com.example.model.Person;
import com.example.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    private final PersonService personService;

    @Autowired // Constructor injection
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/add")
    public String addPerson(@RequestParam String name,
                            @RequestParam String surname,
                            @RequestParam String city,
                            @RequestParam int age) {

        // Create a new Person object using the request parameters
        Person person = new Person();
        person.setName(name);
        person.setSurname(surname);
        person.setCity(city);
        person.setAge(age);

        // Call the service to save the person
        personService.savePerson(person);

        // Return a success message
        return "Person added successfully!";
    }
}
```

### **Main Application Class**
```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpaApplication {
    public static void main(String[] args) {
        SpringApplication.run(JpaApplication.class, args);
    }
}
```

### **Run the Application in IntelliJ**
1. Open `JpaApplication.java` in IntelliJ.
2. Click the **Run (▶) button** in IntelliJ to start the application.

### **Insert a Person via API**
Run from within a terminal:

```bash
curl -X POST "http://localhost:8082/api/person/add" \
     -d "name=John" \
     -d "surname=Doe" \
     -d "city=NewYork" \
     -d "age=30"

```

If successful, the response will be:

```
Person added successfully
```

The operation of inserting a new person into the system must use **POST** and not **GET** because it involves **creating new data** on the server. The **POST** method is specifically designed for operations that modify data, such as adding, updating, or deleting resources. 

- **GET** requests are intended for **retrieving data**, and they should be idempotent, meaning they do not change the state of the server. Using GET for an action that modifies the server's data (like adding a new person) would violate this principle.
- **POST** is used for actions that create or modify resources. In this case, it’s necessary to send the data (name, surname, city, age) in the request body, which aligns with the intended behavior of the endpoint (adding a new person).

In summary, **POST** should be used for actions that change the state of the server, such as creating or updating data, while **GET** is used for retrieving information without causing side effects.

**POST** is considered non-idempotent, meaning that if you send the same request multiple times, it may create multiple identical resources. In this case, posting the same person twice will result in two separate records being added to the database, each with the same details, potentially creating duplicates.

### **Verify Inserted Data in PostgreSQL**
Login to `psql` and check:
```bash
sudo -u postgres psql -d my_app_db
```
```sql
SELECT * FROM my_schema.person;
```

If successful, you should see:
```
 id | name | surname |   city   | age
----+------+---------+----------+-----
  1 | Jane | Doe     | New York |  30
```

---

## **Comparison of Approaches**
| Feature              | Plain JDBC            | Spring JDBC Template | Spring Data JPA       |
|----------------------|-----------------------|----------------------|-----------------------|
| Configuration Effort | High (Manual Setup)   | Moderate (Template)  | Minimal (Auto-config) |
| Boilerplate Code     | High                  | Medium               | Low                   |
| Query Customization  | Full SQL control      | SQL with templates   | Derived queries, JPQL |
| Performance Overhead | Lower overhead        | Moderate             | Slightly higher       |
| Best For             | Complex DB operations | Intermediate SQL use | Standard CRUD apps    |

- Use **Plain JDBC** when you need **fine-grained control** over SQL execution.
- Use **Spring JDBC Template** when you need structured SQL handling without ORM overhead.
- Use **Spring Data JPA** when you want simplicity and rapid development.

Spring Boot’s **auto-configuration** significantly reduces development time, especially with **Spring Data JPA**, allowing for faster and cleaner implementation of data access logic.

---

## **Handling Custom Queries in Spring Data JPA**

While Spring Data JPA provides a powerful set of CRUD operations out of the box, there are cases where more complex queries, such as those involving specific filtering criteria, cannot be autogenerated. In these scenarios, we need to define custom queries using JPQL (Java Persistence Query Language) or native SQL.

### **Defining Custom Queries with JPQL**
JPQL is similar to SQL but operates on entity objects instead of database tables. We can define JPQL queries using the `@Query` annotation in our repository interface.

#### **Example: Fetching People by City Using JPQL**
Suppose we need to retrieve a list of people who live in a specific city. We can define the query as follows:

```java
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    @Query("SELECT p FROM Person p WHERE p.city = :city")
    List<Person> findPeopleByCity(@Param("city") String city);
}
```

### **Using Native Queries**
When JPQL does not provide the necessary capabilities, we can use native SQL queries:

```java
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    @Query(value = "SELECT * FROM my_schema.person WHERE city = :city", nativeQuery = true)
    List<Person> findPeopleByCityNative(@Param("city") String city);
}
```

### **Usage Examples**
#### **Using JPQL Query**
```java
@Autowired
private PersonRepository personRepository;

public void findPeopleInCityExample() {
    List<Person> people = personRepository.findPeopleByCity("New York");
    people.forEach(person -> System.out.println(person.getName()));
}
```

#### **Using Native Query**
```java
@Autowired
private PersonRepository personRepository;

public void findPeopleInCityNativeExample() {
    List<Person> people = personRepository.findPeopleByCityNative("Los Angeles");
    people.forEach(person -> System.out.println(person.getName()));
}
```

### **Choosing Between JPQL and Native Queries**
- **Use JPQL** when working with entities and wanting to take advantage of Hibernate’s object mapping.
- **Use Native Queries** when dealing with database-specific functions or performance optimizations.

By leveraging JPQL and native queries, we can extend Spring Data JPA’s functionality to handle complex queries efficiently while maintaining the benefits of an ORM-based approach.

