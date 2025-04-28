# Auto-Configuration of Spring Security in Spring Boot

Spring Boot simplifies the setup and configuration of a Spring application, and **auto-configuration** is one of its most powerful features. With Spring Security, Spring Boot automatically configures the necessary components to secure your application. In this article, we'll explore how Spring Boot uses auto-configuration to secure your endpoints, including how to enable **Basic Authentication** for accessing protected resources.

## Overview

In this example, **we assume that JDK 11 is yoour project SDK in IntelliJ**, and we'll show how to:

- Set up Spring Boot with Spring Security.
- Secure a RESTful endpoint with **Basic Authentication**.
- Expose actuator endpoints and secure them as well.

Spring Boot's **auto-configuration** feature will automatically configure security settings for us, so we don't need to manually configure beans like `HttpSecurity` or authentication mechanisms unless we need custom behavior.

## Steps for Auto-Configuring Spring Security

1. **Add Dependencies**: Include the necessary dependencies in `pom.xml` to enable Spring Security and Spring Boot's auto-configuration.
2. **Create a Simple Controller**: Add an endpoint that will be secured with Basic Authentication.
3. **Configure Basic Authentication**: Use `application.properties` to set up basic authentication credentials.

### 1. Add Dependencies in `pom.xml`

We will need the following dependencies:
- `spring-boot-starter-web`: To create a web application.
- `spring-boot-starter-security`: To automatically configure Spring Security.
- `spring-boot-starter-actuator`: To expose and secure actuator endpoints.

Here’s the `pom.xml` configuration for a simple Spring Boot application using **Spring Security** and **Actuator**:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>spring-security-demo</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <properties>
        <spring.boot.version>2.7.7</spring.boot.version> <!-- Compatible with JDK 11 -->
    </properties>

    <dependencies>
        <!-- Spring Boot Starter Web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <version>${spring.boot.version}</version>
        </dependency>

        <!-- Spring Boot Starter Security -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
            <version>${spring.boot.version}</version>
        </dependency>

        <!-- Spring Boot Starter Actuator -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
            <version>${spring.boot.version}</version>
        </dependency>

        <!-- Jackson for JSON processing (required by Spring Boot Web) -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.13.3</version> <!-- Explicit Jackson version -->
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
                    <source>11</source>  <!-- Sets the source (Java version used for compiling) to Java 8 -->
                    <target>11</target>  <!-- Sets the target (bytecode version) to Java 8 -->
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>
```

### 2. Create a Simple Controller and an Entry Point

Next, we will create a simple `@RestController` with a secure endpoint. This will be the resource that we access via HTTP, and it will be protected by Basic Authentication.

```java
package com.example.springsecuritydemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecureController {

    @GetMapping("/secure-endpoint")
    public String secureEndpoint() {
        return "This is a secure endpoint!";
    }
}
```

Create the class **SecurityDemoApplication.java** with the main method:

```java
package com.example.springsecuritydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecurityDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityDemoApplication.class, args);
    }
}
```


### 3. Configure Basic Authentication

Spring Boot automatically configures HTTP Basic Authentication when `spring-boot-starter-security` is included in the dependencies. However, you can customize the username and password via the `application.properties` file.

Create the `application.properties` file in the `src/main/resources` directory:

```properties
# application.properties

# Basic Authentication settings
spring.security.user.name=admin
spring.security.user.password=admin123

# Expose actuator endpoints
management.endpoints.web.exposure.include=health,info,metrics
management.endpoint.health.show-details=always
management.endpoints.web.base-path=/actuator

server.port=8080
```

In this configuration:

- The default username is set to `admin` and the password to `admin123`.
- We also expose common actuator endpoints (`/actuator/health`, `/actuator/info`, and `/actuator/metrics`).

### 4. Run the Application from IntelliJ IDEA

To run the application directly from IntelliJ IDEA, follow these steps:

1. Open your project in IntelliJ IDEA and set JDK 11 as your Project SDK.
2. Navigate to the `SpringBootSecurityDemoApplication.java` file.
3. Right-click on the file and select **Run 'SecurityDemoApplication.java'**.

Alternatively, you can click the **Run** button in the top-right corner of IntelliJ IDEA (the green play button).

This will start the Spring Boot application and make it accessible on `http://localhost:8080`.

### 5. Accessing the Secured Endpoint

You can access the `/secure-endpoint` using HTTP Basic Authentication. For example:

#### Using Curl:
```bash
curl -u admin:admin123 http://localhost:8080/secure-endpoint
```

You should see the response:  **This is a secure endpoint!**

#### Using a browser
Open your browser and navigate to:

```text
http://localhost:8080/secure-endpoint
```

you will be prompted with basic authentication details.


#### Accessing Actuator Endpoints:
Similarly, you can access actuator endpoints. For example, access the health endpoint:

```bash
curl -u admin:admin123 http://localhost:8080/actuator/info
```

