# Auto-Configuration in Spring Boot: Example with Spring Actuator

## Introduction to Spring Boot Actuator

Spring Boot Actuator provides production-ready features to help monitor and manage your application. It exposes several built-in endpoints, such as health checks, metrics, and application environment details, that help you monitor and manage your application in production environments.

Spring Boot Actuator auto-configures these endpoints based on the libraries present on the classpath and the configuration in your `application.properties` or `application.yml` file.

## What is Spring Boot Actuator?

Spring Boot Actuator provides a set of built-in HTTP endpoints that give insights into the application’s health, environment, metrics, and much more. It provides easy-to-use features for operations such as monitoring, auditing, and exposing application health.

Spring Boot includes many endpoints by default:
- `/actuator/health` – Provides health status of the application
- `/actuator/metrics` – Exposes application metrics
- `/actuator/info` – Displays custom information about the application

You can enable or disable specific endpoints via your `application.properties` file.

## Adding Spring Boot Actuator Dependency

To enable Spring Boot Actuator in your project, you need to include the `spring-boot-starter-actuator` dependency in your `pom.xml` file:

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
</dependencies>
```

### Configuring Actuator Endpoints in `application.properties`

You can configure which endpoints to expose and secure in your application via the `application.properties` file. Here's an example configuration:

```properties
# application.properties
management.endpoints.web.exposure.include=health,info,metrics
management.endpoint.health.show-details=always
management.endpoints.web.base-path=/actuator
```

In this configuration:

- Only `health`, `info`, and `metrics` endpoints are exposed.
- The health endpoint will always show detailed information.
- The base path for all actuator endpoints is set to `/actuator`.

# Steps to Set Up a Simple Spring Boot Actuator Project

This guide walks you through creating a Spring Boot application with Maven in IntelliJ using JDK 11, with Spring Boot Actuator for auto-configuration. This example demonstrates how to set up a basic project and test the Actuator's auto-configuration with minimal setup.

## Step 1: Create a Maven Project in IntelliJ with JDK 11

1. Open IntelliJ IDEA and create a new Maven project.
2. Select **JDK 11** as your project SDK.
3. Choose a **GroupId** (e.g. `com.example`) and **ArtifactId** (e.g. `actuator-demo`).
4. Create the project with an empty `pom.xml` file.

## Step 2: Add Dependencies to `pom.xml`

Update your `pom.xml` with the necessary dependencies for Spring Boot, Spring Boot Starter Actuator, and other basic configurations. Here's the content for your `pom.xml`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>actuator-demo</artifactId>
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

Spring Boot version: `2.7.7` is compatible with JDK 11.  
Spring Framework version: `5.3.22` is compatible with JDK 11.  
The `spring-boot-starter-actuator` provides the built-in actuator functionalities.

## Step 3: Create the Main Application Class

Create a Java class for the main application entry point. This class will run the Spring Boot application.

```java
// ActuatorDemoApplication.java
package com.example.actuatordemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ActuatorDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActuatorDemoApplication.class, args);
    }
}
```

This class is annotated with `@SpringBootApplication`, which enables auto-configuration, component scanning, and other Spring Boot features.

## Step 4: Add Actuator Configuration in `application.properties`

Create an `application.properties` file to configure Actuator's behavior, such as which endpoints to expose.

```properties
# application.properties
management.endpoints.web.exposure.include=health,info,metrics
management.endpoint.health.show-details=always
management.endpoints.web.base-path=/actuator

server.port=8080
```

This configuration:

- Exposes `health` and `info` actuator endpoints.
- Ensures that detailed health information is shown.
- Changes the base path for actuator endpoints to `/actuator`.

## Step 5: Test Spring Boot Actuator

Spring Boot automatically configures the actuator endpoints. To test, follow these steps:

1. Run the application directly from IntelliJ:
   - Open `ActuatorDemoApplication.java` (the main class).
   - Click on the green run button next to the `main` method or right-click the file and choose **Run 'ActuatorDemoApplication'**.
   
2. Once the application is running, visit the following URLs in your browser or Postman:
   - **Health check**: `http://localhost:8080/actuator/health`
   - **Info**: `http://localhost:8080/actuator/info`

   You should see JSON responses with information about the application's health and info (including the version and custom properties if you've added them):
   
```bash
{"status":"UP","components":{"diskSpace":{"status":"UP","details":{"total":250375106560,"free":114215342080,"threshold":10485760,"exists":true}},"ping":{"status":"UP"}}}
```

## Step 6: Add Custom Health Indicator

To make it more interesting, you can add a custom health indicator. Create a `CustomHealthIndicator` class:

```java
// CustomHealthIndicator.java
package com.example.actuatordemo;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        // Custom health check logic
        boolean isHealthy = true;  // Here you could check some service or condition
        if (isHealthy) {
            return Health.up().withDetail("customService", "Available").build();
        } else {
            return Health.down().withDetail("customService", "Unavailable").build();
        }
    }
}
```

This custom health indicator adds an additional detail under `/actuator/health` with the status of the `customService`:

```bash
{"status":"UP","components":{"custom":{"status":"UP","details":{"customService":"Available"}},"diskSpace":{"status":"UP","details":{"total":250375106560,"free":114215862272,"threshold":10485760,"exists":true}},"ping":{"status":"UP"}}}
```

## Step 7: Run and Verify

After making these changes, run the application directly from IntelliJ:

- Open the `ActuatorDemoApplication.java` file.
- Click the **Run** button on the top right of IntelliJ or press `Shift + F10` to start the application.

Once the application is running, visit the actuator endpoints:

- **Health check**: `http://localhost:8080/actuator/health`
- **Info**: `http://localhost:8080/actuator/info`

