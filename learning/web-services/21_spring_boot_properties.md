# Properties and YAML Files in Spring Boot

## Introduction

Spring Boot uses properties files and YAML files to configure application behavior. These files centralize configuration, making it easy to manage and maintain settings like server ports, environment-specific properties, and custom settings. While `.properties` files were traditionally used, `.yaml` files are often preferred due to their readability and structured format.

---

## Full Instructions for Setting Up and Running the Project in IntelliJ

### Prerequisites

1. **Install IntelliJ IDEA**: Ensure that IntelliJ IDEA Community Edition is already installed.
2. **Install JDK 11**: Verify that OpenJDK 11 is installed and configured in IntelliJ.
3. **Install Maven**: Ensure Maven is installed or use IntelliJ's built-in Maven.

### Project Structure
1. As usual, create a Maven project in IntelliJ; it will have the following structure:

```
properties-demo
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           └── propertiesdemo
│   │   │               ├── PropertiesDemoApplication.java
│   │   │               ├── ProfileController.java
│   │   │               ├── CustomPropertiesController.java
│   │   │               ├── AppPropertiesController.java
│   │   │               └── ProfileBeanController.java
│   │   └── resources
│   │       ├── application.yml
│   │       ├── application-dev.yml
│   │       └── application-prod.yml
├── pom.xml
```

2. **Add an entry point**:

Add the Spring Boot entry point class:

```java
package com.example.propertiesdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PropertiesDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PropertiesDemoApplication.class, args);
    }
}
```

3. **Run the Application**:
   - Locate the main class, `PropertiesDemoApplication.java`.
   - Right-click on the class and select **Run 'PropertiesDemoApplication.main()'**.

---

## Role of Properties Files

Properties files define key-value pairs that configure the application. Examples include:
- Setting the server port.
- Specifying environment-specific configurations using profiles.
- Defining custom properties for use in the application.

---

## Locations for Properties Files

Spring Boot loads properties and YAML files from the following default locations:
1. **`src/main/resources/application.properties` or `application.yml`**.
2. External locations (specified at runtime using `--spring.config.location`).

The files are loaded in the following order of precedence:
1. `application-{profile}.properties` or `application-{profile}.yml` (profile-specific).
2. `application.properties` or `application.yml` (default settings).

---

## Using YAML Files in Spring Boot

### Example: Setting the Server Port

Create a `src/main/resources/application.yml` file:

```yaml
server:
  port: 9090
```

This changes the default server port from 8080 to 9090. 

#### Testing:
1. Run the application.
2. Navigate to `http://localhost:9090`.

---

## Using Profiles

Profiles allow different configurations for different environments (e.g. `dev`, `prod`).

### Example: Profile-Specific YAML Files

Create the following files:

**`src/main/resources/application.yml`**:
```yaml
spring:
  profiles:
    active: dev
```

**`src/main/resources/application-dev.yml`**:
```yaml
server:
  port: 8081

message: "Development Environment"
```

**`src/main/resources/application-prod.yml`**:
```yaml
server:
  port: 8082

message: "Production Environment"
```

### Accessing Profile-Specific Properties in Code

Create a controller to display the active profile's message:

```java
package com.example.propertiesdemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

    @Value("${message}")
    private String message;

    @GetMapping("/profile-message")
    public String getMessage() {
        return message;
    }
}
```

#### Testing:
1. Run the application.
2. Access `http://localhost:8081/profile-message` (for `dev` profile).
3. Switch the profile to `prod` in `application.yml` and test at `http://localhost:8082/profile-message`.

---

## General Purpose Properties

Define custom properties in `application.yml`:

```yaml
custom:
  greeting: "Welcome to Spring Boot!"
```

Access these properties using the `@Value` annotation:

```java
package com.example.propertiesdemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomPropertiesController {

    @Value("${custom.greeting}")
    private String greeting;

    @GetMapping("/greeting")
    public String getGreeting() {
        return greeting;
    }
}
```

#### Testing:
1. Run the application.
2. Navigate to `http://localhost:9090/greeting`.

---

## Accessing Properties with `@ConfigurationProperties`

This approach binds multiple properties into a POJO.

### Example: Binding Custom Properties

**`application.yml`**:
```yaml
app:
  name: "MyApp"
  version: "1.0"
```

**Create a Configuration Class**:

```java
package com.example.propertiesdemo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private String name;
    private String version;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
```

**Controller to Use Bound Properties**:

```java
package com.example.propertiesdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppPropertiesController {

    private final AppProperties appProperties;

    // Explicit constructor injection with @Autowired
    @Autowired
    public AppPropertiesController(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @GetMapping("/app-info")
    public String getAppInfo() {
        return "Name: " + appProperties.getName() + ", Version: " + appProperties.getVersion();
    }
}
```

#### Testing:
1. Run the application.
2. Navigate to `http://localhost:9090/app-info`.

---

## Using `@Profile` for Conditional Beans

The `@Profile` annotation allows defining beans that should only be loaded for specific profiles.

### Example: Defining Profile-Specific Beans

**`application.yml`**:
```yaml
spring:
  profiles:
    active: dev
```

**ProfileBean.java**:
```java
package com.example.propertiesdemo;

public class ProfileBean {

    private final String message;

    public ProfileBean(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
```

**Configuration Class**:
```java
package com.example.propertiesdemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ProfileConfig {

    @Bean
    @Profile("dev")
    public ProfileBean devProfileBean() {
        return new ProfileBean("Dev Profile Bean");
    }

    @Bean
    @Profile("prod")
    public ProfileBean prodProfileBean() {
        return new ProfileBean("Prod Profile Bean");
    }
}
```

**Controller to Use Profile-Specific Bean**:
```java
package com.example.propertiesdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileBeanController {

    private final ProfileBean profileBean;

    // Explicit constructor injection with @Autowired
    @Autowired
    public ProfileBeanController(ProfileBean profileBean) {
        this.profileBean = profileBean;
    }

    @GetMapping("/profile-bean")
    public String getProfileBeanMessage() {
        return profileBean.getMessage();
    }
}
```

#### Testing:
1. Run the application with the `dev` profile active.
2. Access `http://localhost:8081/profile-bean`.
3. Change the profile to `prod` in `application.yml` and test at `http://localhost:8082/profile-bean`.

---

## Conclusion

This guide covered the essential usage of properties and YAML files in Spring Boot, including setting the server port, utilizing profiles, defining general-purpose properties, and accessing them using annotations like `@Value` and `@ConfigurationProperties`. We also explored conditional bean loading with the `@Profile` annotation.

By following the provided examples and instructions, you should be able to leverage the flexibility of Spring Boot's configuration system to build applications tailored to different environments and requirements.



