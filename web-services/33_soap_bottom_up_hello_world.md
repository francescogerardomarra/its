## Bottom-up SOAP WebService in Springboot
This project demonstrates the implementation of a **SOAP Web Service** using **Spring Boot**, following the **bottom-up** approach. In this approach, the SOAP web service is built starting from Java code (i.e. Java classes define the web service operations and data structures), rather than starting from a predefined WSDL file (top-down approach).

### Technologies Used
The project utilizes the following technologies:

- **Java 21**: The programming language used for developing the web service.
- **Spring Boot 3**: A framework that simplifies the setup and development of Java applications.
- **Spring Web Services (Spring WS)**: A Spring module for creating SOAP-based web services.
- **Jakarta XML Binding (JAXB)**: Used for converting Java objects to XML and vice versa.
- **Spring Data JPA**: Used for interacting with the database.
- **PostgreSQL**: The relational database used for persisting data.
- **Lombok**: A library to reduce boilerplate code in Java classes.
- **Maven**: A build automation tool for managing dependencies and project lifecycle.

### Project Overview
This project implements a SOAP-based web service that allows clients to:

- **Add a new person** to the database.
- **Retrieve a list of all persons** stored in the database.

The service exposes **two SOAP operations**, handled by a SOAP endpoint:

1. `InsertPersonRequest`: Allows adding a new person.
2. `GetAllPersonsRequest`: Fetches all persons from the database.

Unlike RESTful services that use a **Controller layer**, SOAP-based services in Spring Boot use an **Endpoint layer**. The **Endpoint layer** serves as an equivalent to the controller but is specifically designed to handle SOAP messages. It always exposes SOAP endpoints that process XML-based requests and responses, ensuring seamless communication with SOAP clients.

The SOAP service is structured with the following main components:

- **Model Classes**: Represent database entities (e.g. `Person`).
- **Repository Layer**: Interfaces with the database using Spring Data JPA.
- **Service Layer**: Contains business logic for managing persons.
- **SOAP Endpoint**: Exposes SOAP web service methods.
- **SOAP Request & Response Classes**: Used for serializing and deserializing XML requests/responses.
- **WebService Configuration**: Registers the SOAP service and generates a WSDL file.

The project follows best practices, such as dependency injection, separation of concerns, and clear API documentation. It also includes sample **SOAP request and response messages** that can be tested using tools like **Postman** or **cURL**.

- A detailed **Maven-based** Java project structure.
- Clear separation between **data persistence**, **business logic**, and **web service layers**.
- Implementation of **Spring Boot's built-in SOAP support**.
- Example **SOAP request & response messages** for testing the service.

This guide will walk you through the **project setup, implementation, and testing** of the SOAP web service.

### **Directory Structure**
```
springbootsoaphelloworld
└── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── example
    │   │           ├── config
    │   │           │   └── WebServiceConfig.java
    │   │           ├── endpoint
    │   │           │   └── PersonEndpoint.java
    │   │           ├── model
    │   │           │   └── Person.java
    │   │           ├── repository
    │   │           │   └── PersonRepository.java
    │   │           ├── service
    │   │           │   └── PersonService.java
    │   │           ├── soap
    │   │           │   ├── request
    │   │           │   │   ├── GetAllPersonsRequest.java
    │   │           │   │   └── InsertPersonRequest.java
    │   │           │   └── response
    │   │           │       ├── GetAllPersonsResponse.java
    │   │           │       └── InsertPersonResponse.java
    │   │           └── SoapApplication.java
    │   └── resources
    │       └── application.properties
    └── test
        └── java
```

### **Create a New Maven Project in IntelliJ**
1. Open IntelliJ IDEA.
2. Create a **Java Project with Maven** and set Java 21 as the Project SDK.
3. Use the following `pom.xml`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">

   <!-- Specifies the model version of the POM (Project Object Model). It is always 4.0.0 for Maven projects. -->
   <modelVersion>4.0.0</modelVersion>

   <!-- Group ID of the project: a unique identifier for the group that owns the project. -->
   <groupId>com.example</groupId>

   <!-- Artifact ID of the project: a unique name for the project, typically used in the repository. -->
   <artifactId>springbootsoaphelloworld</artifactId>

   <!-- Version of the project, typically used for version control. "SNAPSHOT" indicates an in-progress version. -->
   <version>0.0.1-SNAPSHOT</version>

   <!-- Packaging type of the project, which specifies the artifact format (e.g. jar, war). Here it is a JAR file. -->
   <packaging>jar</packaging>

   <properties>
      <!-- Specifies the Java version that the project is compatible with. -->
      <java.version>21</java.version> <!-- Set Java version to 21 -->
   </properties>

   <dependencies>
      <!-- Spring Boot Starter Web: Provides essential libraries for building web applications, including RESTful APIs. -->
      <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-web</artifactId>
         <version>3.2.2</version> <!-- Ensure this version matches your Spring Boot version -->
      </dependency>

      <!-- Spring Web Services (Spring WS) core: Provides core functionality for creating SOAP-based web services. -->
      <dependency>
         <groupId>org.springframework.ws</groupId>
         <artifactId>spring-ws-core</artifactId>
         <version>4.0.10</version> <!-- Ensure compatibility with Spring Boot 3.x -->
      </dependency>

      <!-- Spring Boot Starter Web Services: A starter for building SOAP-based web services in Spring Boot applications. -->
      <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-web-services</artifactId>
         <version>3.2.2</version>
      </dependency>

      <!-- Spring Boot Starter Data JPA: Provides integration with databases using Java Persistence API (JPA). -->
      <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-data-jpa</artifactId>
         <version>3.2.2</version>
      </dependency>

      <!-- Spring Core: Contains core functionality for Spring, like dependency injection and component management. -->
      <dependency>
         <groupId>org.springframework</groupId>
         <artifactId>spring-core</artifactId>
         <version>6.1.3</version> <!-- Ensure this version matches spring-context -->
      </dependency>

      <!-- Spring Context: Provides the context support, such as the application context for managing beans and configurations. -->
      <dependency>
         <groupId>org.springframework</groupId>
         <artifactId>spring-context</artifactId>
         <version>6.1.3</version> <!-- Same version as spring-core -->
      </dependency>

      <!-- JAXB API: Provides functionality for binding XML and Java objects, making it easier to convert XML to Java and vice versa. -->
      <dependency>
         <groupId>jakarta.xml.bind</groupId>
         <artifactId>jakarta.xml.bind-api</artifactId>
         <version>3.0.1</version>
      </dependency>

      <!-- JAXB Runtime: Provides the implementation for the JAXB API for XML binding. -->
      <dependency>
         <groupId>org.glassfish.jaxb</groupId>
         <artifactId>jaxb-runtime</artifactId>
         <version>3.0.1</version>
      </dependency>

      <!-- SLF4J API: Simple Logging Facade for Java, used by various libraries for logging. -->
      <dependency>
         <groupId>org.slf4j</groupId>
         <artifactId>slf4j-api</artifactId>
         <version>2.0.9</version>
         <scope>runtime</scope> <!-- The dependency is required only at runtime, not during compilation -->
      </dependency>

      <!-- PostgreSQL JDBC Driver: Needed to connect to a PostgreSQL database. -->
      <dependency>
         <groupId>org.postgresql</groupId>
         <artifactId>postgresql</artifactId>
         <version>42.6.0</version>
         <scope>runtime</scope> <!-- This dependency is required only during runtime -->
      </dependency>

      <!-- Lombok: A library that reduces boilerplate code (e.g. getter/setter methods, constructors). -->
      <dependency>
         <groupId>org.projectlombok</groupId>
         <artifactId>lombok</artifactId>
         <version>1.18.30</version>
         <scope>provided</scope> <!-- Lombok is provided at compile-time but does not need to be packaged in the JAR -->
      </dependency>
   </dependencies>

   <build>
      <plugins>
         <!-- Maven Compiler Plugin: Configures the Java compiler used in the build process. -->
         <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.11.0</version>
            <configuration>
               <!-- Specifies the source and target versions for compiling the code. -->
               <source>21</source> <!-- Java 21 is the source version for compiling -->
               <target>21</target> <!-- Java 21 is the target version for generating the bytecode -->
               <!-- Retain parameter names in the compiled bytecode for reflection purposes. -->
               <compilerArgument>-parameters</compilerArgument>
            </configuration>
         </plugin>
      </plugins>
   </build>
</project>
```

**SOAP Handling with Spring WS and Spring Boot**

For this application, SOAP (Simple Object Access Protocol) is a primary communication protocol, and the project depends on key Spring components for building and managing SOAP web services.

1. **Spring WS Core (`spring-ws-core`)**  
   The dependency `org.springframework.ws:spring-ws-core` is fundamental for building SOAP-based web services with Spring Framework. This core library provides the necessary infrastructure to create and handle SOAP messages, along with utilities to integrate seamlessly with Spring's web module. The version used in this project is **4.0.10**, which ensures compatibility with Spring Boot 3.x, a critical aspect when dealing with the evolving Spring ecosystem.

2. **Spring Boot Starter Web Services (`spring-boot-starter-web-services`)**  
   To facilitate the creation of SOAP web services using Spring Boot, the `spring-boot-starter-web-services` dependency is included. This starter package bundles all the necessary libraries and configurations to create and deploy SOAP services efficiently, streamlining the setup process while ensuring tight integration with other Spring Boot modules. The version specified here, **3.2.2**, aligns with the project's overall Spring Boot version and enables compatibility with Spring Boot's more modern capabilities for web services.

**XML Handling: JAXB for Data Binding**

When working with SOAP, data is typically exchanged in XML format. The Java Architecture for XML Binding (JAXB) allows for easy conversion between Java objects and XML representations. The following dependencies are pivotal for handling XML data binding:

1. **JAXB API (`jakarta.xml.bind-api`)**  
   The `jakarta.xml.bind-api` dependency provides the necessary interfaces and classes for working with JAXB, enabling seamless marshalling (Java to XML) and unmarshalling (XML to Java) operations. The version used is **3.0.1**, which is the Jakarta EE 9+ version, aligning with the more recent Jakarta namespace shift from `javax` to `jakarta`.

2. **JAXB Runtime (`jaxb-runtime`)**  
   The runtime implementation of JAXB is provided by the `jaxb-runtime` dependency from `org.glassfish.jaxb`. This is responsible for executing the binding operations defined by the JAXB API, ensuring that the XML is correctly processed and converted into Java objects (and vice versa). The version **3.0.1** used here is consistent with the JAXB API, ensuring the entire stack is based on the latest Jakarta-based JAXB implementation.



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

### **Entity Class**
The `Person` class represents an entity in the context of Object-Relational Mapping (ORM) using Java Persistence API (JPA). It is specifically designed to map Java objects to a relational database, rather than dealing with SOAP messages or XML data. This class facilitates database operations by allowing the `Person` entity to be stored and retrieved from the "person" table in the database.

Using Spring Data JPA, this class is automatically mapped to a database table, enabling common database operations such as create, read, update, and delete (CRUD). The class is annotated with JPA annotations from the `jakarta.persistence` package, which is part of the transition from Java EE to Jakarta EE in Spring Boot 3.x and Java 21.

- **JPA Mapping:** The class is mapped to the "person" table in the database, with fields such as `id`, `name`, `surname`, `city`, and `age`.
- **Primary Key:** The `@Id` and `@GeneratedValue` annotations define `id` as the primary key with auto-increment behavior.
- **Lombok Annotations:** Lombok annotations like `@Getter`, `@Setter`, and `@NoArgsConstructor` automatically generate boilerplate code for getters, setters, and a no-argument constructor, simplifying development.

This class focuses solely on ORM and database mapping and is not involved in SOAP or XML processing.

````java
package com.example.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Starting with Spring Boot 3.x and Java 21, JPA annotations now use the 'jakarta.persistence' package instead of 'javax.persistence'.
// This is due to the migration from Java EE to Jakarta EE, which occurred after Java 17. The 'jakarta' namespace is now the standard for JPA and other Java EE technologies.
import jakarta.persistence.*;

/**
 * This class represents a Person entity in the context of Object-Relational Mapping (ORM) with JPA.
 * It is used for mapping Java objects (entities) to database tables, and allows for CRUD operations on the "person" table.
 *
 * This class is not related to SOAP web services but is used for managing database records using Spring Data JPA.
 * The 'jakarta.persistence' annotations are used for ORM mapping to interact with a relational database.
 */
@Entity // Marks the class as an entity that will be mapped to a database table
@Table(name = "person", schema = "my_schema") // Specifies the name of the table in the database and the schema it belongs to
@Getter // Lombok annotation that automatically generates getters for all fields
@Setter // Lombok annotation that automatically generates setters for all fields
@NoArgsConstructor // Lombok annotation to create a no-argument constructor which is required by Hibernate
public class Person {

    @Id // Denotes the primary key of the entity
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Specifies how the primary key is generated (auto-increment)
    private Long id;

    private String name; // The name of the person
    private String surname; // The surname of the person
    private String city; // The city where the person lives
    private int age; // The age of the person
}
````

### **Repository Interface**
The `PersonRepository` interface is part of the Spring Data JPA layer, providing an abstraction for interacting with the `Person` entity in the database. It extends `JpaRepository`, a core Spring Data JPA interface, which automatically provides implementations for common database operations, such as saving, retrieving, updating, and deleting entities.

By extending `JpaRepository`, the `PersonRepository` gains a variety of CRUD (Create, Read, Update, Delete) methods without the need for custom implementations. These methods allow for seamless integration with the database and facilitate managing `Person` entities.

- **Inheritance from `JpaRepository`:** By extending `JpaRepository<Person, Long>`, the repository is automatically equipped with common methods like `save()`, `findById()`, `findAll()`, and `deleteById()`, eliminating the need for boilerplate code.
- **Repository Annotations:** The `@Repository` annotation indicates that this interface is a Spring Data repository, making it eligible for component scanning and automatic dependency injection by the Spring container.
- **Default Methods Provided:** Common database operations, such as saving and deleting entities, are automatically implemented, allowing developers to focus on business logic rather than low-level data handling.

The `PersonRepository` simplifies database interaction for the `Person` entity, providing a clean and efficient way to perform CRUD operations without writing explicit SQL or query methods.

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

### **Service Class**
The `PersonService` class acts as the service layer for handling business logic related to `Person` entities. It interacts with the `PersonRepository` to perform database operations, such as saving and retrieving `Person` objects, while encapsulating the logic needed for those operations.

This service class follows the typical structure of a Spring service, where it is annotated with `@Service` to mark it as a Spring-managed component. The `PersonService` is designed to be injected into other components, such as controllers, where it can provide methods for interacting with the `Person` data.

- **Service Layer:** The `PersonService` provides a clean abstraction for the business logic. It interacts with the `PersonRepository` to perform CRUD operations on the `Person` entity, without exposing the direct database access methods to the rest of the application.
- **Dependency Injection:** The `PersonRepository` is injected into the service using Spring's `@Autowired` annotation, ensuring that the service has access to the repository for database interactions.
- **Business Methods:** The methods in `PersonService` such as `savePerson()` and `getAllPersons()` provide functionality to save a `Person` object to the database or retrieve a list of all `Person` objects. These methods delegate the actual data operations to the repository, keeping the service layer focused on business logic.

The `PersonService` class simplifies the handling of `Person` entities by encapsulating the logic of interacting with the database and offering a convenient API for the rest of the application.

```java
package com.example.service;

import com.example.model.Person;
import com.example.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

   // Retrieve all the persons
   public List<Person> getAllPersons() {
      return personRepository.findAll(); // Retrieves all persons from the database using JpaRepository
   }
}
```

### **Endpoint Class**
The `PersonEndpoint` class serves as the SOAP equivalent of a REST controller in a Spring application. Unlike a `@RestController`, which handles RESTful web service requests (typically in JSON or XML format), a `@Endpoint` handles SOAP requests and responses in XML format. This class is annotated with `@Endpoint`, which designates it as a SOAP web service endpoint, and it processes XML messages based on the SOAP protocol.

- **SOAP Endpoint:** The `@Endpoint` annotation marks this class as a SOAP endpoint, allowing it to handle XML-based SOAP messages, unlike REST controllers that deal with JSON. The methods in this class are mapped to specific SOAP request elements using the `@PayloadRoot` annotation, linking them to the corresponding XML structure in the SOAP message.

- **Automatic XML-Marshalling/Unmarshalling:** One of the key benefits of using Spring Web Services is that XML serialization and deserialization (marshalling and unmarshalling) are handled automatically. We do not need to manually convert Java objects into XML or vice versa. Annotations like `@RequestPayload` and `@ResponsePayload` ensure that incoming SOAP requests are automatically deserialized into Java objects, and the response is automatically serialized into the correct XML format.

    - **`@RequestPayload`**: This annotation binds the incoming SOAP request body (in XML format) to a Java object. Under the hood, Spring Web Services automatically converts the XML data into the corresponding Java object. This process is called unmarshalling or deserialization. So when a SOAP request like `InsertPersonRequest` arrives, it is automatically converted into a Java object (`InsertPersonRequest`) to be used within the method.

    - **`@ResponsePayload`**: This annotation marks the method's return value to be serialized into XML format for the SOAP response. The object returned by the method, such as a `InsertPersonResponse` or `GetAllPersonsResponse`, is automatically converted into an XML structure. This eliminates the need for manual marshalling, ensuring the response is formatted according to the SOAP specification.

- **Business Logic Integration:** This class integrates with the service layer (`PersonService`) to handle the logic of saving and retrieving `Person` data from the database. The SOAP methods work with custom SOAP request and response objects (`InsertPersonRequest`, `InsertPersonResponse`, `GetAllPersonsResponse`) that represent the structure of the XML messages, but the actual data interaction with the database happens in the service layer, keeping the endpoint focused on handling the SOAP-specific aspects.

- The method `addPerson` is mapped to a SOAP request with the `InsertPersonRequest` root element, which is automatically deserialized by Spring into a `InsertPersonRequest` Java object. This object is then used to create a new `Person` entity, which is persisted via the service layer. The response (`InsertPersonResponse`) is also automatically converted into XML format for the client.

    - **Example of unmarshalling with `@RequestPayload`**: When a SOAP request is received with an XML body that includes `<InsertPersonRequest>` elements, Spring Web Services automatically binds these elements to the properties of the `InsertPersonRequest` Java object. This happens behind the scenes without manual intervention, allowing you to directly work with Java objects in your method.

- The method `getAllPersons` handles a request to retrieve all `Person` records. The list of `Person` entities is fetched using the `PersonService`, then converted into a list of `InsertPersonResponse` objects. Each `InsertPersonResponse` is automatically serialized into XML and returned to the client.

    - **Example of marshalling with `@ResponsePayload`**: The method returns a `GetAllPersonsResponse` object, which contains a list of `InsertPersonResponse` objects. When returning this object, Spring automatically serializes it into XML format, ensuring that the response follows the correct SOAP XML structure.

The `PersonEndpoint` class effectively bridges the gap between SOAP web services and the underlying database by leveraging Spring Web Services' support for XML binding. This allows for seamless handling of SOAP messages without requiring manual marshalling or unmarshalling, simplifying the development of robust, XML-based web services. The `@PayloadRoot`, `@RequestPayload`, and `@ResponsePayload` annotations streamline the SOAP request and response processing, while the service layer focuses on business logic, leaving the `PersonEndpoint` to manage the SOAP-specific interaction.

This automatic handling of XML conversions via annotations simplifies development by abstracting the complexities of marshalling and unmarshalling, reducing the amount of boilerplate code needed. By integrating the service layer with the endpoint, the architecture keeps the logic organized and separate from the concerns of SOAP message handling.

```java
// This is the package declaration, indicating where the class resides in the project
package com.example.endpoint;

// Import necessary classes
import com.example.model.Person; // Import the Person model (entity for database mapping)
import com.example.service.PersonService; // Import the service layer to handle business logic
import com.example.soap.response.GetAllPersonsResponse;
import com.example.soap.request.InsertPersonRequest; // Import the SOAP request model for incoming data
import com.example.soap.response.InsertPersonResponse; // Import the SOAP response model to send back data
import org.springframework.beans.factory.annotation.Autowired; // Import Spring's @Autowired annotation for constructor injection
import org.springframework.ws.server.endpoint.annotation.Endpoint; // Import the @Endpoint annotation to define the SOAP endpoint
import org.springframework.ws.server.endpoint.annotation.PayloadRoot; // Import to define the root element of the SOAP request
import org.springframework.ws.server.endpoint.annotation.RequestPayload; // Import to bind the SOAP request body to method parameters
import org.springframework.ws.server.endpoint.annotation.ResponsePayload; // Import to define the response body for SOAP responses

import java.util.List;
import java.util.stream.Collectors;

/**
 * PersonEndpoint is a SOAP web service endpoint class that handles incoming SOAP requests.
 *
 * The @Endpoint annotation marks this class as a SOAP web service endpoint. This is different
 * from a @RestController in that it handles SOAP requests instead of REST requests, and is designed
 * to work with the XML-based SOAP protocol.
 *
 * The @PayloadRoot annotation is used to define the root element of the incoming SOAP request.
 * This is how the method maps to a specific XML structure in the SOAP message.
 *
 * The Person class is an entity model used for database mapping (e.g. with JPA), and is not
 * directly related to the SOAP request/response mapping. The entity represents the data that is
 * saved to the database, while the SOAP request and response classes are responsible for XML
 * serialization and deserialization.
 */
@Endpoint // Marks this class as a SOAP web service endpoint. Unlike @RestController, @Endpoint is for SOAP services.
public class PersonEndpoint {

   // Define the namespace URI for the SOAP service.
   // This ensures that SOAP messages with the correct namespace are routed to this endpoint.
   // The namespace serves as a unique identifier for the SOAP messages, preventing name conflicts
   // between different services and ensuring that this endpoint processes requests from the correct service.
   // The SOAP request will need to reference the namespace URI (http://example.com/soap) and use the correct element name (in this case, "InsertPersonRequest").
   private static final String NAMESPACE_URI = "http://example.com/soap";

   // Explicit constructor injection for PersonService to handle business logic.
   // This service is responsible for saving Person data to the database.
   private final PersonService personService;

   // Constructor with @Autowired annotation to automatically inject the PersonService into this class
   @Autowired
   public PersonEndpoint(PersonService personService) {
      this.personService = personService; // Assign injected service to the instance variable
   }

   /**
    * This method is mapped to the 'InsertPersonRequest' SOAP message.
    * The @PayloadRoot annotation binds this method to the incoming SOAP request with the given namespace and local part.
    * It means that this method will handle SOAP requests with the localPart "InsertPersonRequest" in the specified namespace.
    *
    * @PayloadRoot(namespace = NAMESPACE_URI, localPart = "InsertPersonRequest") means that this method listens to
    * SOAP messages with this exact structure in the XML.
    *
    * The namespace ensures that this method will only handle SOAP requests related to the specified service,
    * avoiding conflicts with other services that may use similar element names in their XML structure.
    *
    * @RequestPayload: This annotation is used to bind the incoming SOAP request body to the method parameter (in this case, 'request').
    * Underneath, Spring Web Services automatically deserializes the XML payload from the SOAP message and maps it
    * into the 'InsertPersonRequest' Java object. This deserialization happens transparently, so the 'request' parameter is
    * populated with the corresponding values from the XML body (e.g. name, surname, city, and age).
    *
    * @ResponsePayload: This annotation indicates that the method will return a SOAP response body. The response object
    * will be serialized into XML format, and Spring Web Services handles this serialization automatically.
    * In this case, the 'InsertPersonResponse' object will be converted into a SOAP XML response and sent back to the client.
    * This makes it easy to return complex objects as part of a SOAP response without manually converting them to XML.
    */
   @PayloadRoot(namespace = NAMESPACE_URI, localPart = "InsertPersonRequest") // The method is mapped to the InsertPersonRequest SOAP message
   @ResponsePayload // The method will return a SOAP response, to be serialized into XML
   public InsertPersonResponse addPerson(@RequestPayload InsertPersonRequest request) {
      // Create a new Person entity and populate its fields from the SOAP request
      Person person = new Person();
      person.setName(request.getName()); // Set the name of the person from the SOAP request
      person.setSurname(request.getSurname()); // Set the surname of the person from the SOAP request
      person.setCity(request.getCity()); // Set the city of the person from the SOAP request
      person.setAge(request.getAge()); // Set the age of the person from the SOAP request

      // Save the new person object to the database using the PersonService
      personService.savePerson(person); // Calls the service layer to persist the Person entity

      // Create a SOAP response object to return to the client
      InsertPersonResponse response = new InsertPersonResponse();
      response.setMessage("Person added successfully!"); // Set the success message in the response

      // Return the populated response object, which will be serialized into a SOAP response
      return response; // Sends the response back to the client in SOAP format
   }

   /**
    * This method handles the retrieval of all persons.
    * It is mapped to the 'GetAllPersonsRequest' SOAP message, which will trigger this method when requested.
    *
    * @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllPersonsRequest") ensures that this method
    * listens to incoming SOAP requests with the specified namespace and local part ("GetAllPersonsRequest").
    * This namespace uniquely identifies the service and avoids conflicts with other services that may have similar
    * element names in their XML messages. The method is executed when a SOAP request with this structure is received.
    *
    * @ResponsePayload: This annotation is used to mark the return value of the method as the SOAP response body.
    * Underneath, Spring Web Services automatically serializes the response object (in this case, 'GetAllPersonsResponse')
    * into XML format. This means that you don't need to manually convert the 'GetAllPersonsResponse' object into XML;
    * Spring handles this serialization automatically, making it easy to return complex objects as part of a SOAP response.
    *
    * Note: While this method doesn't use @RequestPayload (since it doesn't require any request data in the body),
    * if it had, the XML request body would be deserialized into a corresponding Java object automatically by Spring.
    */
   @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllPersonsRequest") // This method is mapped to the SOAP request 'GetAllPersonsRequest' with the defined namespace
   @ResponsePayload // The method will return a SOAP response that will be serialized into XML format
   public GetAllPersonsResponse getAllPersons() {
      // Step 1: Retrieve all persons from the service layer
      // Here we use the personService to get all the persons from the database (or any data source).
      // The service is responsible for the logic of fetching the persons.
      List<Person> persons = personService.getAllPersons();

      // Step 2: Create a new response object to hold the list of InsertPersonResponse objects
      // The response will be serialized into XML to send back to the client. This will hold all the person data.
      GetAllPersonsResponse response = new GetAllPersonsResponse();

      // Step 3: Convert each Person entity into a InsertPersonResponse object
      // Since the SOAP response format requires a specific structure, we map each Person entity
      // to a InsertPersonResponse. InsertPersonResponse contains a message property to display basic person info.
      List<InsertPersonResponse> InsertPersonResponses = persons.stream()
              .map(person -> {
                 // Step 4: Create a new InsertPersonResponse for each Person entity
                 // Here we create a new InsertPersonResponse object and populate it with data from the Person entity.
                 InsertPersonResponse InsertPersonResponse = new InsertPersonResponse();

                 // Step 5: Set the message with all the fields (name, surname, city, and age)
                 String fullMessage = String.format("Person: %s %s, City: %s, Age: %d",
                         person.getName(), person.getSurname(), person.getCity(), person.getAge());
                 InsertPersonResponse.setMessage(fullMessage); // Set the full message

                 return InsertPersonResponse; // Return the InsertPersonResponse object for this person
              })
              .collect(Collectors.toList()); // Collect all the mapped InsertPersonResponse objects into a list

      // Step 6: Set the list of InsertPersonResponse objects into the response object
      // The GetAllPersonsResponse object holds a list of InsertPersonResponse objects, which is returned to the client.
      response.setPersons(InsertPersonResponses);

      // Step 7: Return the response
      // This will be automatically converted into a SOAP XML response and sent back to the client.
      return response;
   }
}
```

### **WebService Config Class**
The `WebServiceConfig` class plays a pivotal role in configuring and setting up the SOAP web service in a Spring Boot application. By leveraging Spring's configuration and web services support, it ensures that SOAP requests are correctly routed and handled by the appropriate endpoints. This class uses Spring Web Services' `@EnableWs` annotation to enable SOAP web services and provides key setup for handling SOAP messaging.

### Key Features:
- **`@Configuration` Annotation:** This annotation marks the class as a Spring configuration class. This is important because it tells Spring that this class contains beans and configuration settings that need to be loaded and used within the application context. In Spring Boot, a configuration class like this helps centralize the setup of various components like web services, data sources, and more. The configuration class ensures that all the necessary beans and services for SOAP handling are correctly instantiated and available.

- **`@EnableWs` Annotation:** The `@EnableWs` annotation is crucial for enabling Spring Web Services support within a Spring Boot application. By adding this annotation, you are telling Spring to enable the necessary infrastructure to handle SOAP web service communication, including message dispatching, endpoint routing, and WSDL handling. Without `@EnableWs`, Spring Boot wouldn't be aware of how to manage SOAP-specific resources, such as WSDL files and the dispatching of SOAP messages to endpoints.

- A **Servlet** is a Java class that handles HTTP requests and generates HTTP responses in a web application. It runs on a web server or servlet container (like Apache Tomcat) and provides a mechanism for web servers to interact with Java-based applications.

- **`MessageDispatcherServlet` Bean:** This servlet is at the heart of the SOAP web service setup. It is responsible for receiving incoming SOAP requests and dispatching them to the appropriate endpoint (like the `PersonEndpoint`). The servlet acts as a controller for SOAP messages and ensures they are processed by the correct method in the SOAP endpoint class.

    - **Role in Routing:** The `MessageDispatcherServlet` automatically processes incoming SOAP requests, based on the URL patterns defined in the configuration (`/ws/*` in this case). This routing feature ensures that the SOAP messages are correctly forwarded to the methods in the `PersonEndpoint` class for further handling.

    - **WSDL Location Transformation:** The servlet also takes care of transforming WSDL file locations to match the request URL. This is important because clients might request the WSDL definition, and the servlet ensures that the WSDL file references the correct service endpoints and methods.

- **`ServletRegistrationBean` Bean:** The `ServletRegistrationBean` is a Spring bean that is used to register the `MessageDispatcherServlet` with the servlet container. It not only creates an instance of the `MessageDispatcherServlet` but also configures the servlet's URL mapping. The mapping `/ws/*` ensures that all SOAP requests are directed to this servlet. This means that all SOAP requests, regardless of the specific SOAP operation or endpoint, will first be handled by this servlet, which then dispatches the request to the correct method in the endpoint.

The `WebServiceConfig` class is crucial in setting up the SOAP infrastructure for a Spring Boot application. By utilizing Spring's configuration capabilities, the class registers the `MessageDispatcherServlet` to handle SOAP requests and ensures that the necessary SOAP infrastructure is correctly initialized and available.

This configuration allows for seamless communication between the client and the SOAP endpoints, enabling Spring Boot to handle WSDL, request routing, and SOAP message dispatching automatically. The `@EnableWs` annotation ensures that Spring Web Services' features are active, making it possible to handle XML-based SOAP requests with minimal configuration and effort.

The `WebServiceConfig` class ensures that the application can handle SOAP requests, route them to the correct endpoints, and generate the appropriate WSDL for service discovery, playing a critical role in the smooth operation of the SOAP-based web service in the Spring Boot environment.

````java
package com.example.config; // Defines the package where this class belongs

// Import necessary Spring and Spring-WS classes

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.ws.wsdl.wsdl11.Wsdl11Definition;

/**
* WebServiceConfig is a configuration class for setting up the SOAP web service in a Spring Boot application.
* It registers the MessageDispatcherServlet, which is responsible for handling incoming SOAP requests and routing them
* to the appropriate endpoint.
  */
  @Configuration // Marks this class as a Spring configuration class, enabling Spring to detect and use it
  @EnableWs // Enables Spring Web Services support (necessary for SOAP web services)
  public class WebServiceConfig {

  /**
   * Registers the MessageDispatcherServlet, which processes SOAP requests.
   *
   * @param applicationContext The Spring application context, automatically injected by Spring
   * @return A ServletRegistrationBean that registers the servlet and maps it to a specific URL pattern
     */
     @Bean // Marks this method as a Spring-managed bean, allowing Spring to initialize and manage it
     public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
     // Create an instance of MessageDispatcherServlet, which is responsible for handling SOAP requests
     MessageDispatcherServlet servlet = new MessageDispatcherServlet();

     // Set the application context so that the servlet can find the Spring-managed SOAP endpoints
     servlet.setApplicationContext(applicationContext);

     // Ensures that WSDL file locations in responses are correctly transformed to match the request URL
     servlet.setTransformWsdlLocations(true);

     // Registers the servlet and maps it to handle requests at "/ws/*"
     // This means all SOAP requests should be sent to URLs starting with "/ws/"
     return new ServletRegistrationBean<>(servlet, "/ws/*");
     }
     }
````

### **Mapper classes for SOAP messages**

#### Insert person
These classes are designed to map between SOAP XML messages and Java objects for a web service that handles person-related data. Using **JAXB annotations**, they automatically convert XML data to Java objects (deserialization) and Java objects back to XML (serialization). This mapping is essential for processing SOAP requests and generating responses.

1. **`InsertPersonRequest` (SOAP Request Mapper):**
    - **Purpose**: Converts incoming SOAP request XML into a Java object.
    - **Role**: Represents a person’s details (name, surname, city, age) in the request message.
    - **Annotations**: `@XmlRootElement` marks it as the root XML element, and `@XmlElement` maps the fields to XML elements.

2. **`InsertPersonResponse` (SOAP Response Mapper):**
    - **Purpose**: Converts the server’s response into SOAP XML format.
    - **Role**: Holds a simple message (e.g. "Person added successfully") to be returned as a response.
    - **Annotations**: Similar annotations (`@XmlRootElement`, `@XmlElement`) are used to serialize the response message.

These mappers simplify the exchange of data between the web service and client by automatically handling the XML-to-object and object-to-XML transformations.

```java
// This is the package declaration, indicating where this class resides in the project
package com.example.soap.request;

// Importing required annotations from Jakarta XML Binding (JAXB) to handle XML serialization and deserialization
import jakarta.xml.bind.annotation.XmlAccessType; // Defines the access level for the properties
import jakarta.xml.bind.annotation.XmlAccessorType; // Specifies how JAXB will access the fields
import jakarta.xml.bind.annotation.XmlElement; // Used to mark fields to be included in XML
import jakarta.xml.bind.annotation.XmlRootElement; // Marks the class as the root element in the XML
import lombok.Getter; // Lombok annotation to generate getter methods automatically
import lombok.Setter; // Lombok annotation to generate setter methods automatically

/**
 * This class is used for deserializing the SOAP XML request message.
 *
 * When a SOAP request is sent to the web service, it is formatted as an XML message.
 * This class serves as the Java object representation of that XML message,
 * and it will be used to bind (deserialize) the XML data into a Java object.
 * The annotations provided by JAXB allow for automatic conversion between
 * XML and Java objects, so the incoming SOAP message can be easily handled.
 *
 * In particular, this class represents the data structure of a "InsertPersonRequest"
 * in the SOAP message, which includes the person's name, surname, city, and age.
 */
@XmlRootElement(name = "InsertPersonRequest", namespace = "http://example.com/soap") // This annotation marks the class as the root element in the XML document; it also ensures the correct namespace for the SOAP request message.
@XmlAccessorType(XmlAccessType.FIELD) // This tells JAXB to use the fields for XML binding (instead of getter/setter methods)
@Getter // Lombok annotation to automatically generate getter methods for all fields
@Setter // Lombok annotation to automatically generate setter methods for all fields
public class InsertPersonRequest {

    // Field to store the person's name, annotated with @XmlElement to specify it's a part of the XML structure
    @XmlElement(required = true) // The "name" field is mandatory in the XML document
    private String name; // The person's name as a string

    // Field to store the person's surname, annotated with @XmlElement to specify it's a part of the XML structure
    @XmlElement(required = true) // The "surname" field is mandatory in the XML document
    private String surname; // The person's surname as a string

    // Field to store the person's city, annotated with @XmlElement to specify it's a part of the XML structure
    @XmlElement(required = true) // The "city" field is mandatory in the XML document
    private String city; // The city where the person lives, as a string

    // Field to store the person's age, annotated with @XmlElement to specify it's a part of the XML structure
    @XmlElement(required = true) // The "age" field is mandatory in the XML document
    private int age; // The person's age as an integer
}
```

````java
// This is the package declaration, indicating where this class resides in the project
package com.example.soap.response;

// Importing required annotations from Jakarta XML Binding (JAXB) to handle XML serialization and deserialization
import jakarta.xml.bind.annotation.XmlAccessType; // Defines the access level for the properties
import jakarta.xml.bind.annotation.XmlAccessorType; // Specifies how JAXB will access the fields
import jakarta.xml.bind.annotation.XmlElement; // Used to mark fields to be included in XML
import jakarta.xml.bind.annotation.XmlRootElement; // Marks the class as the root element in the XML
import lombok.Getter; // Lombok annotation to generate getter methods automatically
import lombok.Setter; // Lombok annotation to generate setter methods automatically

/**
 * This class is used for serializing the SOAP XML response message.
 *
 * Once the SOAP request is processed by the web service, the server needs to send back a response.
 * This response is formatted as an XML message, and this class is responsible for converting (serializing)
 * the Java object into the corresponding XML structure for the SOAP response.
 *
 * This class represents the "InsertPersonResponse" in the SOAP response message, which includes a message (e.g. 
 * "Person added successfully!").
 */
@XmlRootElement(name = "InsertPersonResponse", namespace = "http://example.com/soap") // This annotation marks the class as the root element in the XML document; it also ensures the correct namespace for the SOAP response message.
@XmlAccessorType(XmlAccessType.FIELD) // This tells JAXB to use the fields for XML binding (instead of getter/setter methods)
@Getter // Lombok annotation to automatically generate getter methods for all fields
@Setter // Lombok annotation to automatically generate setter methods for all fields
public class InsertPersonResponse {

    // Field to store the message, annotated with @XmlElement to specify it's a part of the XML structure
    @XmlElement(required = true) // The "message" field is mandatory in the XML document
    private String message; // A string message to be sent as a part of the response (e.g. "Person added successfully!")
}
````

#### Retrieve all persons
These classes handle the mapping of SOAP request and response messages related to retrieving all persons. They convert SOAP XML messages into Java objects (deserialization) and Java objects back to SOAP XML messages (serialization) using **JAXB annotations**.

**`GetAllPersonsRequest` (SOAP Request Mapper):**
- **Purpose**: This class is used for handling the SOAP request when the client requests to retrieve all persons.
- **Role**: It typically doesn't require any specific input from the client, as the request is simply asking for all available persons. This means the class may be empty or contain optional parameters in the future if needed (e.g. pagination or filters).
- **Annotations**:
    - `@XmlRootElement` marks the class as the root element in the SOAP XML request message, ensuring the correct format and namespace for the SOAP request.
    - Since the request doesn't have any fields, this class can be simple or even empty.

**`GetAllPersonsResponse` (SOAP Response Mapper):**
- **Purpose**: This class handles the SOAP response sent back to the client after processing the `GetAllPersonsRequest`. It contains the list of persons retrieved from the server.
- **Role**: It holds a list of `InsertPersonResponse` objects (each representing an individual person), which include details like name, surname, city, and age of each person.
- **Annotations**:
    - `@XmlRootElement` marks this class as the root element of the SOAP XML response message, ensuring the response is formatted correctly with the appropriate namespace.
    - `@XmlElement` is used to specify that the `persons` field (a list of `InsertPersonResponse` objects) must be included as an XML element in the response. The list of `InsertPersonResponse` objects will be serialized into the corresponding XML structure.

These classes allow easy conversion between SOAP XML messages and Java objects, handling the requests and responses related to retrieving a list of all persons.

````java
package com.example.soap.request;  // The package declaration, organizing this class within the project structure

// Importing the necessary JAXB annotation for XML binding
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * This class is used for deserializing the SOAP request to retrieve all persons.
 *
 * The class represents the SOAP request structure, and JAXB annotations are used
 * to map the SOAP XML request message to a Java object.
 *
 * This particular class does not contain any fields because the request
 * to retrieve all persons may not need any input data (it's simply asking for all the persons).
 * However, if needed, additional request fields could be added here in the future.
 */
@XmlRootElement(name = "GetAllPersonsRequest", namespace = "http://example.com/soap")
// The @XmlRootElement annotation marks this class as the root element in the SOAP request XML message.
// - 'name' defines the name of the root element in the SOAP request (GetAllPersonsRequest).
// - 'namespace' specifies the XML namespace to ensure the SOAP message is correctly identified and parsed.
public class GetAllPersonsRequest {
    // This class is kept simple, potentially empty, as it represents a request to fetch all persons
    // Additional details (e.g. filters, sorting parameters) could be added if the request needs them.
}

````

````java
package com.example.soap.response;  // The package declaration, organizing the class in a specific namespace within the project

// Importing necessary classes for JAXB annotations, which help convert between XML and Java objects
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

// Importing Lombok annotations to generate getter and setter methods automatically
import lombok.Getter;
import lombok.Setter;

// Importing List from Java’s utility library to represent a list of objects
import java.util.List;

/**
 * This class is used for serializing (converting to XML) the SOAP XML response message
 * that contains a list of persons. It will be used in the SOAP response, which
 * will be converted to XML by JAXB annotations.
 */
@XmlRootElement(name = "GetAllPersonsResponse", namespace = "http://example.com/soap")
// The @XmlRootElement annotation specifies that this class represents the root element of the XML
// response message. The 'name' defines the name of the root element in XML (GetAllPersonsResponse).
// The 'namespace' ensures that the SOAP response is correctly mapped with the specified namespace URI.
@XmlAccessorType(XmlAccessType.FIELD)
// @XmlAccessorType(XmlAccessType.FIELD) means that JAXB will access the fields directly (not getters/setters)
// when mapping XML to Java objects. This approach is often used for simplicity in XML binding.
@Getter  // Lombok annotation to automatically generate getter methods for the fields in this class
@Setter  // Lombok annotation to automatically generate setter methods for the fields in this class
public class GetAllPersonsResponse {

    /**
     * A list of InsertPersonResponse objects that will be included in the SOAP response.
     * Each InsertPersonResponse contains information about a person, such as their name,
     * which will be serialized to XML.
     */
    @XmlElement(required = true)
    // The @XmlElement annotation marks the 'persons' field to be included as an XML element in the SOAP response.
    // The 'required = true' indicates that this element must be present in the XML response.
    private List<InsertPersonResponse> persons;  // A list of InsertPersonResponse objects to be serialized into the XML response
}
````

The flow will be as follows:

1. **SOAP Request**:
- The SOAP request comes in as an XML message, which includes a **namespace URI** and a **local part**. The **namespace URI** ensures that the request is mapped to the correct endpoint, while the **local part** identifies the specific element being requested.
- Example SOAP message:
  ```xml
  <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                    xmlns:web="http://example.com/soap">
      <soapenv:Header/>
      <soapenv:Body>
         <web:InsertPersonRequest>
            <web:name>John</web:name>
            <web:surname>Doe</web:surname>
            <web:city>Springfield</web:city>
            <web:age>30</web:age>
         </web:InsertPersonRequest>
      </soapenv:Body>
  </soapenv:Envelope>
  ```
- In the above XML:
    - `soapenv:Envelope` is part of the SOAP envelope structure.
    - `web:InsertPersonRequest` is the body of the request, containing the actual data (name, surname, etc.) under the specified namespace `http://example.com/soap`.

2. **MessageDispatcherServlet**:
- The `MessageDispatcherServlet` is responsible for receiving the incoming SOAP message and forwarding it to the correct **SOAP endpoint**.
- It listens for requests at the `/ws/*` URL pattern. When a request with this pattern is received, the servlet matches the request to the appropriate handler method based on the XML structure and namespace.
- It then forwards the unmarshalled request to the endpoint (e.g. `PersonEndpoint`).

3. **Unmarshalling**:
- Spring Web Services automatically **unmarshals** the SOAP XML into the corresponding Java object (`InsertPersonRequest` in this case).
- JAXB (Jakarta XML Binding) annotations like `@XmlRootElement`, `@XmlElement`, and `@XmlAccessorType` define how the XML elements map to Java fields.
- For example, the `name`, `surname`, `city`, and `age` elements from the SOAP request XML are mapped to the fields in the `InsertPersonRequest` class.
- This unmarshalling process happens automatically during the request handling, converting the XML into the Java object so it can be easily processed.

4. **Matching by `@PayloadRoot`**:
- The **`@PayloadRoot`** annotation on the handler method defines how Spring should match incoming SOAP requests to specific methods. It plays a crucial role in routing SOAP messages based on their structure.
- The `namespace` attribute in the annotation corresponds to the **namespace URI** from the SOAP message (in this case, `http://example.com/soap`).
- The `localPart` attribute corresponds to the **local part** (element name) of the SOAP message, such as `InsertPersonRequest` in this case.
- This ensures that the correct method in the `PersonEndpoint` class is invoked, based on the specific SOAP operation being requested.

5. **Automatic Routing**:
- Once the SOAP request is unmarshalled (i.e. the XML is converted into a `InsertPersonRequest` object), Spring Web Services matches the **namespace URI** and **local part** of the SOAP message to the `@PayloadRoot` annotation on the endpoint method.
- If there is a match, Spring automatically routes the request to the corresponding method.
- For example, the method `addPerson` will be invoked when the SOAP message has a `InsertPersonRequest` root element under the correct namespace.
- Inside the method, the unmarshalled `InsertPersonRequest` object will be available, allowing the endpoint to process the data (such as saving a person to the database).
- The method then generates a **SOAP response** (e.g. `InsertPersonResponse`), which is serialized back into XML and returned to the client.

This automatic routing by namespace URI and XML structure allows Spring Web Services to handle SOAP requests in a streamlined manner without requiring manual routing or complex configuration.

### **Main Application Class**
```java
package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SoapApplication {
   public static void main(String[] args) {
      SpringApplication.run(SoapApplication.class, args);
   }
}
```

### **Run the Application in IntelliJ**
1. Open `SoapApplication.java` in IntelliJ.
2. Click the **Run (▶) button** in IntelliJ to start the application.

#### **Insert a Person via API**

**SOAP Request Message**
Save the following SOAP request as `soap-insert-person.xml`:

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:ex="http://example.com/soap">
   <soapenv:Header/>
   <soapenv:Body>
      <ex:InsertPersonRequest>
         <name>John</name>
         <surname>Doe</surname>
         <city>NewYork</city>
         <age>30</age>
      </ex:InsertPersonRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

**Using cURL**
Execute the following command in the terminal:

```bash
curl -X POST http://localhost:8080/ws \
    -H "Content-Type: text/xml;charset=UTF-8" \
    -H "SOAPAction: \"\"" \
    -d @soap-insert-person.xml
```

**Using Postman**
1. Open **Postman**.
2. Click **New Request**.
3. Set the request type to **POST**.
4. Enter the URL: `http://localhost:8080/ws`
5. Go to the **Headers** tab and add:
    - `Content-Type: text/xml;charset=UTF-8`
    - `SOAPAction:` (leave empty)
6. Go to the **Body** tab, select **raw**, and paste the SOAP XML request.
7. Click **Send**.
8. Verify the response.

---

After inserting a person via API, you can verify the data in PostgreSQL.

```bash
sudo -u postgres psql -d my_app_db
```

```sql
SELECT * FROM my_schema.person;
```

```
 id | name | surname |   city   | age
----+------+---------+----------+-----
  1 | John | Doe     | New York |  30
```

---

#### **Retrieve All Persons via API**

**SOAP Request Message**
Save the following request as `soap-get-all-persons.xml`:

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:tns="http://example.com/soap">
    <soapenv:Header/>
    <soapenv:Body>
        <tns:GetAllPersonsRequest/>
    </soapenv:Body>
</soapenv:Envelope>
```

**Using cURL**
```bash
curl -X POST http://localhost:8080/ws \
    -H "Content-Type: text/xml;charset=UTF-8" \
    -H "SOAPAction: \"\"" \
    -d @soap-get-all-persons.xml
```

**Using Postman**
1. Open **Postman**.
2. Click **New Request**.
3. Set the request type to **POST**.
4. Enter the URL: `http://localhost:8080/ws`
5. Go to the **Headers** tab and add:
    - `Content-Type: text/xml;charset=UTF-8`
    - `SOAPAction:` (leave empty)
6. Go to the **Body** tab, select **raw**, and paste the SOAP XML request.
7. Click **Send**.
8. Verify the response.

---

By following this guide, you have successfully:
- Inserted a person via SOAP API using `cURL` and `Postman`.
- Verified the inserted data in PostgreSQL.
- Retrieved all persons via SOAP API using `cURL` and `Postman`.

This approach ensures seamless integration with the SOAP web service for data insertion and retrieval.

---

# Understanding POST in the Context of SOAP and REST

## SOAP and POST

**SOAP (Simple Object Access Protocol)** is a protocol for exchanging structured information in web service implementations. It relies on XML messaging and typically uses HTTP or SMTP for communication. SOAP messages are usually sent via the **POST** method because SOAP requires a specific XML format within the request body. This XML format contains both the data and the necessary instructions for the server to process the request. The **POST** method allows SOAP to send the complete SOAP envelope with this structured information as the HTTP request body.

## REST and POST

In contrast, **REST (Representational State Transfer)** is an **architectural style**, not a protocol. RESTful APIs use standard HTTP methods such as:
- **GET** - Retrieve a resource
- **POST** - Create a new resource
- **PUT** - Update an existing resource
- **DELETE** - Remove a resource
- **PATCH** - Modify part of a resource

Resources in REST are usually represented in **JSON** or **XML**, with JSON being the preferred format in modern APIs. REST focuses on manipulating resources rather than performing specific operations as SOAP does.

## Key Differences in Using POST

While both SOAP and REST commonly use **POST**, their approaches differ:

### **SOAP: Action-Oriented**
- SOAP focuses on **actions or operations**.
- A **SOAP request** sent via **POST** contains an **XML document (SOAP envelope)** in the HTTP request body.
- This XML envelope encapsulates:
    - The operation to be performed
    - Any necessary parameters
- Essentially, a **SOAP message represents a remote procedure call (RPC)**, instructing the server on what action to take.

### **REST: Resource-Oriented**
- REST focuses on **resources**.
- A **POST** request in REST typically **creates** a resource or **triggers an action**.
- Unlike SOAP, REST does not define actions explicitly; instead, it manipulates resources.

## Why SOAP Can Feel Like REST

1. **Both Use HTTP Methods**: REST uses **POST** to send data and create resources, while SOAP also relies on **POST** to send structured XML data. This can create the illusion that they are similar, even though their core purposes differ.
2. **SOAP Messages Are Complex**: SOAP requires **XML** and a predefined message structure, making **POST** the most efficient method for encapsulating complex data and instructions. In contrast, REST is more flexible and less rigid in data formatting.

## Conclusion

While both SOAP and REST use the **POST** method, their **intentions** and **implementations** differ:
- **SOAP** uses **POST** to send structured XML messages that define operations and parameters for remote procedure calls.
- **REST** uses **POST** to create or modify **resources** within a more flexible architecture.

Even though they share the same HTTP verb, their **philosophies and applications are distinct**.