# Spring Boot: Overview and First Steps

Spring Boot is a framework designed to simplify the development of Java-based applications by following the principle of **convention over configuration**. It eliminates the need for extensive XML or Java-based configuration, enabling developers to focus on building the application rather than configuring the environment.

---

## Key Features of Spring Boot

### 1. Convention Over Configuration
- Spring Boot provides sensible defaults, reducing the need for explicit configurations.
- For instance, if you include a dependency for a web application, Spring Boot automatically configures an embedded web server like Tomcat.

### 2. Embedded Server Support
- Spring Boot allows running applications with embedded web servers such as Tomcat, Jetty, or Undertow.
- No need to deploy WAR files; applications can run directly as standalone JAR files.

### 3. Auto-Configuration
- Automatically configures Spring beans and settings based on the dependencies present in the project.

### 4. Production-Ready Features
- Includes monitoring, metrics, and health checks for easier deployment and management in production environments.

---

## Compatibility of Spring Versions with Java Versions

| Spring Boot Version | Java Compatibility   |
|---------------------|----------------------|
| 2.x (i.e. 2.7.x)   | Java 8, 11, and above |
| 3.x (i.e. 3.0.x)   | Java 17 and above    |

To ensure compatibility across environments, we will use Spring Boot 2.7.x in this example, as it supports Java 11, which is the minimum version required for our project.

---

## Creating and Running a Hello World Application

### Prerequisites
Ensure the following are installed on your system:

- Java Development Kit (e.g. OpenJDK 11 or higher)
- Maven
- IntelliJ IDEA

---

### Step 1: Create a New Spring Boot Project

#### Option 1: Using IntelliJ IDEA
1. Open IntelliJ IDEA.
2. Click on **New Project**.
3. Select **Maven** from the list of project templates.
4. Configure the project:
   - **Group ID**: `com.example`
   - **Artifact ID**: `hello-world`
   - **Version**: Leave as default (`1.0-SNAPSHOT`).
5. Set the project SDK:
   - Ensure you select an appropriate Java Development Kit (JDK) version.
   - If no JDK is configured, click `Add SDK` to set it up.
6. Click **Finish** to create the project.
7. Open the `pom.xml` file and manually add the Spring Boot dependencies (see Step 2 below).

#### Option 2: Using start.spring.io

1. Open a browser and navigate to [Spring Initializr](https://start.spring.io/).
2. Configure the project with the following settings:
   - **Project:** Maven
   - **Language:** Java
   - **Spring Boot Version:** 2.7.x (compatible with Java 11)
   - **Group:** `com.example`
   - **Artifact:** `hello-world`
   - **Dependencies:** Add **Spring Web**.

3. Click the **Generate** button to download the project as a ZIP file.
4. Extract the ZIP file to a directory on your computer.
5. Open the project in IntelliJ IDEA:
   - Click **File > Open**.
   - Navigate to the extracted project folder and select it.

---

### Step 2: Add Spring Boot Dependencies

Edit the `pom.xml` file to include the following:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.example</groupId>
    <artifactId>hello-world</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <properties>
        <spring-boot.version>2.7.5</spring-boot.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
            <version>${spring-boot.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <version>${spring-boot.version}</version>
        </dependency>
    </dependencies>

</project>
```

This configuration ensures that Spring Boot 2.7.5 (compatible with Java 11) is explicitly specified, avoiding issues caused by Maven downloading an incompatible version.

---

### Step 3: Write the Application Code

Create the main application class in `src/main/java/com/example/helloworld/HelloWorldApplication.java`:

```java
package com.example.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class HelloWorldApplication {
    public static void main(String[] args) {
        SpringApplication.run(HelloWorldApplication.class, args);
    }
}

@RestController
@RequestMapping("/api")
class HelloWorldController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, World!";
    }
}
```

---

### Step 4: Run the Application

#### Option 1: Using IntelliJ IDEA
1. Open the project in IntelliJ IDEA.
2. Navigate to `HelloWorldApplication.java`.
3. Click the green **Run** button next to the `main` method.
4. Once started, the application will be accessible at `http://localhost:8080/api/hello`.

#### Option 2: Using the Command Line
1. Package the application using Maven:
   ```bash
   mvn clean package
   ```

2. Run the application:
   ```bash
   java -jar target/hello-world-0.0.1-SNAPSHOT.jar
   ```

3. Access the application at `http://localhost:8080/api/hello`.

---

### Step 5: Verify Output
When accessing `http://localhost:8080/api/hello` in a browser or using `curl`, you should see:
```text
Hello, World!
```

#### Testing Using a Browser
1. Open any web browser (e.g. Chrome, Firefox, or Edge).
2. Type the following URL into the address bar and press **Enter**:
   ```
   http://localhost:8080/api/hello
   ```
3. The browser should display the following response:
   ```text
   Hello, World!
   ```

#### Testing Using Command Line (curl)
1. Open a terminal on your machine.
2. Execute the following command:
   ```bash
   curl http://localhost:8080/api/hello
   ```
3. The terminal should display the following response:
   ```text
   Hello, World!
   ```

---

### Summary
- Spring Boot simplifies Java application development by reducing boilerplate code through auto-configuration and convention over configuration.
- Creating a basic "Hello, World!" application is straightforward using IntelliJ IDEA or the command line.
- With embedded servers and production-ready features, Spring Boot is ideal for developing modern Java applications.

