# Embedded Servers in Spring Boot

## Introduction

In web application development, a server plays a pivotal role in managing HTTP requests and responses. Traditionally, deploying a Java-based web application required setting up an external application server like Apache Tomcat or Jetty. However, this setup added complexity in terms of configuration, deployment, and maintenance. Spring Boot simplifies this process with its embedded server feature, making web application development and deployment more straightforward.

### Why Do We Need a Server for an Application?

A server is essential for hosting applications that need to interact with clients, typically via HTTP. It listens for incoming requests, processes them, and returns responses. Examples include:
- Serving web pages.
- Providing RESTful APIs for client applications.

Without a server, applications would lack the ability to communicate over networks, effectively isolating them from users and other systems.

### How Does Spring Boot Resolve This?

Spring Boot provides a mechanism for embedding servers directly within the application. This eliminates the need to deploy the application to an external server. Key benefits include:
- **Portability**: The application can run as a standalone JAR file with the embedded server.
- **Ease of Deployment**: No need for server setup or configuration.
- **Consistency**: The embedded server ensures uniform behavior across environments.

Spring Boot supports several embedded servers, such as Tomcat, Jetty, and Undertow, with Tomcat being the default.

---

## Practical Example: Hello World with Embedded Server

### Prerequisites

1. **Environment**:
   - OpenJDK 11.
   - Maven.
   - IntelliJ IDEA Community Edition.
   - Spring Boot 2.7.5 (compatible with OpenJDK 11).
2. **Tools**:
   - Internet access for downloading dependencies.

### Step-by-Step Guide

#### 1. Create a New Spring Boot Project

1. Open IntelliJ IDEA.
2. Select **File > New > Project**.
3. Choose **Maven** from the list of project templates.
4. Configure the project:
   - **Group ID**: `com.example`
   - **Artifact ID**: `helloworld`
   - **Version**: Leave as default (`1.0-SNAPSHOT`).
5. Set the project SDK:
   - Ensure it points to OpenJDK 11.
   - If no JDK is configured, click `Add SDK` to set it up.
6. Click **Finish** to create the project.
7. Open the `pom.xml` file and manually add the Spring Boot dependencies (see the next step).

#### 2. Add Maven Dependencies

Edit the `pom.xml` file to include the following configuration:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.example</groupId>
    <artifactId>helloworld</artifactId>
    <version>0.0.1-SNAPSHOT</version>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
            <version>2.7.5</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <version>2.7.5</version>
        </dependency>
    </dependencies>

</project>
```

#### 3. Directory Structure

The structure of the project should look like this:
```
helloworld/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/helloworld/
│   │   │       └── HelloworldApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/
│   └── test/
├── pom.xml
```

#### 4. Code Implementation

##### 4.1 Application Class

Create a main application class in `src/main/java/com/example/helloworld/HelloworldApplication.java`:

```java
package com.example.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class HelloworldApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloworldApplication.class, args);
    }

    @RestController
    @RequestMapping("/api")
    public static class HelloController {

        @GetMapping("/hello")
        public String sayHello() {
            return "Hello, World!";
        }
    }
}
```

##### 4.2 Configuration File

The `application.properties` file can remain empty for this basic example.

```properties
# application.properties
```

#### 5. Run the Application

1. In IntelliJ IDEA, open the `HelloworldApplication` class.
2. Click the green run button next to the `main` method.
3. The embedded Tomcat server will start, and you should see output similar to:
   ```
   Tomcat started on port(s): 8080 (http)
   ```

#### 6. Access the Application

1. Open a browser.
2. Navigate to `http://localhost:8080/api/hello`.
3. You should see the response:
   ```
   Hello, World!
   ```

---

## Summary

Spring Boot's embedded server simplifies web application development and deployment by eliminating the need for external server configurations. This feature not only saves time but also enhances portability and consistency across different environments. In this guide, we demonstrated a "Hello, World!" example using an embedded Tomcat server, showcasing how easy it is to get started with Spring Boot.

---

