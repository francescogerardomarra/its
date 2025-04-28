# Dependency Injection in Spring Boot

## 1. Introduction to Beans in Spring Boot

In Spring Boot, a **Bean** is an object that is managed by the Spring IoC (Inversion of Control) container. These beans are instantiated, assembled, and managed by Spring, allowing for loose coupling between the various components of an application.

### What is a Bean?

- A **Bean** is a Java object that is instantiated, configured, and managed by a Spring IoC container.
- Beans are the backbone of a Spring application; they form the core of the dependency injection mechanism.

### How to Define a Bean?

There are different ways to define a bean in Spring Boot, primarily using annotations:

1. **`@Component`**
2. **`@Service`**
3. **`@Repository`**
4. **`@Controller`**
5. **`@Bean`** (used in configuration classes)

Each of these annotations has a specific use case but they all serve the purpose of defining a bean in the Spring context.

## 2. Types of Beans and Their Purpose

### 2.1 `@Component`

- Marks a Java class as a Spring component.
- Generic stereotype for any Spring-managed component.

```java
@Component
public class MyComponent {
    // business logic
}
```

### 2.2 `@Service`

- Specialization of `@Component`.
- Used to define service layer classes.

```java
@Service
public class MyService {
    // business logic
}
```

### 2.3 `@Repository`

- Specialization of `@Component`.
- Used for Data Access Objects (DAO) or repository classes.

```java
@Repository
public class MyRepository {
    // database interaction logic
}
```

### 2.4 `@Controller`

- Specialization of `@Component`.
- Used to define a web controller in Spring MVC.

```java
@Controller
public class MyController {
    // handling web requests
}
```

### 2.5 `@Bean`

- Used within a `@Configuration` class to explicitly declare a single bean.

```java
@Configuration
public class AppConfig {

    @Bean
    public MyService myService() {
        return new MyService();
    }
}
```

### 2.6 `@Configuration`

- A class annotated with `@Configuration` is a configuration class that can contain `@Bean` methods.
- The Spring container processes the `@Configuration` class to generate Spring beans and manage their dependencies.

```java
@Configuration
public class MyConfig {

    @Bean
    public MyService myService() {
        return new MyService();
    }
}
```

## 3. Bean Scopes in Spring Boot

### 3.1 Singleton

- Default scope in Spring.
- A single instance of the bean is created for the entire Spring container.

### 3.2 Prototype

- A new instance of the bean is created every time it is requested.

```java
@Component
@Scope("prototype")
public class MyPrototypeBean {
    // prototype bean logic
}
```

### 3.3 Request

- A single instance of the bean is created for each HTTP request.

### 3.4 Session

- A single instance of the bean is created for each HTTP session.

## 4. Qualifiers in Spring Boot

When multiple beans of the same type are present, we use **`@Qualifier`** to specify which bean to inject.

```java
@Component("firstService")
public class FirstService {
    // implementation
}

@Component("secondService")
public class SecondService {
    // implementation
}

@Component
public class Consumer {
    private final FirstService firstService;

    public Consumer(@Qualifier("firstService") FirstService firstService) {
        this.firstService = firstService;
    }
}
```

## 5. Injecting Beans

There are several ways to inject beans:

- **Constructor Injection** (Recommended)
- **Setter Injection**
- **Field Injection** (using `@Autowired`)

### Constructor Injection

**Note**: Implicit constructor injection works only with one-constructor classes. For clarity and best practices, we explicitly use the `@Autowired` annotation with constructor injection.

#### Explicit Constructor Injection

When you explicitly use the `@Autowired` annotation, Spring knows to inject the necessary dependency into the constructor.

```java
@Component
public class MyComponent {
    private final MyService myService;

    @Autowired
    public MyComponent(MyService myService) {
        this.myService = myService;
    }
}
```

#### Explanation:
Here, the `@Autowired` annotation tells Spring to inject an instance of `MyService` into the constructor of `MyComponent`. 
- This syntax is explicit and works with any number of constructors.

#### Implicit Constructor Injection

Spring 4.3+ allows for implicit constructor injection, which means that if there is only **one constructor** in the class, Spring will automatically use it for dependency injection **without needing the `@Autowired` annotation**.

```java
@Component
public class MyComponent {
    private final MyService myService;

    public MyComponent(MyService myService) {
        this.myService = myService;
    }
}
```

#### Explanation:
In this case, Spring will automatically detect that `MyComponent` has only one constructor and will inject the `MyService` dependency into it.
The `@Autowired` annotation is **not required** because there is only one constructor.

#### When Are the Two Syntaxes Equivalent?

Both the explicit and implicit constructor injection approaches are **equivalent** when the class has **only one constructor**.

- **With `@Autowired`**:
  - Explicitly adds clarity to the code, making it clear that constructor injection is intended.
  - Useful when you have multiple constructors and want to specify which one Spring should use.

- **Without `@Autowired`**:
  - Simplifies the code when there is only one constructor and Spring can automatically inject the dependency.
  - Spring knows that the single constructor is the one to use for injection.

#### When Are They Not Equivalent?

- If the class has **multiple constructors**, **explicit `@Autowired`** is required. This is because Spring needs to know which constructor to use for dependency injection. Without it, Spring won’t know which constructor to pick, and it will throw an exception during the context startup.

```java
@Component
public class MyComponent {
    private final MyService myService;

    // Multiple constructors, so we must explicitly specify @Autowired
    @Autowired
    public MyComponent(MyService myService) {
        this.myService = myService;
    }

    @Autowired
    public MyComponent(MyService myService, AnotherService anotherService) {
        this.myService = myService;
    }
}
```

#### Summary

1. **Explicit Constructor Injection**:
   - Use `@Autowired` to explicitly tell Spring which constructor to use.
   - Recommended for clarity and when you have more than one constructor in the class.

2. **Implicit Constructor Injection**:
   - When there is **only one constructor**, Spring will automatically inject the dependencies without the need for `@Autowired`.
   - This can simplify the code, but it might reduce clarity if you are not aware of the implicit behavior.

3. **Best Practice**:
   - It’s often considered better practice to **explicitly annotate constructors with `@Autowired`** to ensure that the injection is clear to anyone reading the code. This approach also avoids potential issues when the class is later modified to include multiple constructors.


### Setter Injection

```java
@Component
public class MyComponent {
    private MyService myService;

    @Autowired
    public void setMyService(MyService myService) {
        this.myService = myService;
    }
}
```

### Field Injection

```java
@Component
public class MyComponent {
    @Autowired
    private MyService myService;
}
```

## 6. Creating a Maven Spring Boot Project in IntelliJ IDEA

### Prerequisites

- JDK 11 pre-installed.
- Maven installed.
- IntelliJ IDEA.

### Steps to Create the Project

1. Open IntelliJ IDEA.
2. Click on **File** > **New** > **Project**.
3. In the **New Project** dialog, select **Maven** from the left panel.
4. Provide the necessary details:
   - GroupId: `com.example`
   - ArtifactId: `spring-boot-maven-project`
   - Version: `1.0-SNAPSHOT`
   - Name: `spring-boot-maven-project`
5. Specify the project location and click **Finish** to create the project.

### Directory Structure

Once the project is created, you should see something similar:

```
project-root
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com/example
│   │   │       ├── App.java          <-- Main application entry point to be created
│   │   │       ├── MyService.java    <-- Service class to be created
│   │   │       └── MyController.java <-- Controller class to be created
│   │   └── resources
│   │       └── application.properties
│   └── test
│       ├── java
│       │   └── com/example
│       │       └── AppTest.java
│       └── resources
└── pom.xml
```

### Adding `pom.xml`

Here is a sample `pom.xml` for a Spring Boot project:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>spring-boot-dependency-injection</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>spring-boot-dependency-injection</name>
    <description>Demo project for Spring Boot Dependency Injection</description>

    <properties>
        <spring.version>5.3.10</spring.version>
        <spring-boot.version>2.5.6</spring-boot.version>
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

### Adding a Bean and Injecting It

1. Create a `Service` class:

```java
@Service
public class MyService {
    public String serve() {
        return "Service is working";
    }
}
```

2. Create a `Controller` class to use the service:

```java
@RestController
public class MyController {
    private final MyService myService;

    @Autowired
    public MyController(MyService myService) {
        this.myService = myService;
    }

    @GetMapping("/test")
    public String test() {
        return myService.serve();
    }
}
```

3. Create the entry point to the Spring Boot application. It starts up the Spring context and enables component scanning:

```java
package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
```

### Running the Application

1. Right-click on the `Application` class and choose **Run**.
2. Open a web browser and go to `http://localhost:8080/test`.
3. You should see the output `Service is working`.

## 7. Conclusion

In this tutorial, we covered the basics of beans in Spring Boot, their types, scopes, and how to inject them. We also demonstrated how to create a simple Maven Spring Boot project in IntelliJ IDEA to illustrate dependency injection in practice.
