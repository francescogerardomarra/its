# Spring Security
Spring Security is a comprehensive framework for securing Java applications, especially those built with Spring. It provides a wide array of functionalities like authentication, authorization, protection against common vulnerabilities (e.g.: CSRF), and integrates easily with both web and enterprise applications.

---

## pom.xml
**Spring Boot** provides a set of **starter** POMs that group related dependencies together for ease of use. These starter POMs allow you to import a whole set of dependencies that are commonly used together.

**Spring Boot starters** simplify dependency management by providing predefined sets of commonly used libraries for specific functionalities (e.g., `spring-boot-starter-web` for web applications, `spring-boot-starter-data-jpa` for JPA, etc.).

Spring Boot provides a **BOM (Bill of Materials)** that ensures you're using compatible versions of dependencies including **starters**. You don't need to specify version numbers for Spring Boot dependencies if you're using the Spring Boot BOM.

Once you've imported the **BOM**, you can add Spring Boot dependencies **without specifying their versions**, because they will be managed by the BOM:

````xml
<dependencyManagement>
    <dependencies>
        <!-- Import Spring Boot BOM (Bill of Materials) for version management -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-dependencies</artifactId>
            <version>2.5.4</version> <!-- Replace with the version you are using -->
            <scope>import</scope>
            <type>pom</type>
        </dependency>
    </dependencies>
</dependencyManagement>

<dependencies>
    <!-- Spring Boot Starter Web: for building web applications -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- Spring Boot Starter Data JPA: for JPA and database integration -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <!-- Spring Boot Starter Security: for adding security features -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>

    <!-- Optional: Spring Boot Starter Test for testing -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
````

If you need more control over the versions or if you're not using **Spring Boot starters**, you can directly import individual dependencies into your `pom.xml`. You will need to specify **both the group ID**, **artifact ID**, and the **version** of the dependency.

For Spring Security 5.x (with Spring Boot 2.x and Java 8+):

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <version>2.7.10</version> <!-- Spring Boot version compatible with Spring Security 5.x -->
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
    <version>2.7.10</version> <!-- Spring Boot version compatible with Spring Security 5.x -->
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
    <version>2.7.10</version> <!-- Spring Boot version compatible with Spring Security 5.x -->
</dependency>
```

For Spring Security 6.x (with Spring Boot 3.x and Java 17+):

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <version>3.4.4</version> <!-- Spring Boot version compatible with Spring Security 6.x -->
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
    <version>3.4.4</version> <!-- Spring Boot version compatible with Spring Security 6.x -->
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
    <version>3.4.4</version> <!-- Updated Spring Boot version for Spring Security 6.x -->
</dependency>
```

Spring Security is a widely-used framework for securing Java-based applications. With the release of **Spring Security 6.x**, significant changes have been made compared to the earlier **Spring Security 5.x**.

The main differences between the two versions are:

- **Java Version Support**:
    - **Spring Security 5.x**: Supports **Java 8** and higher, which means it works with older Java versions.
    - **Spring Security 6.x**: Requires **Java 17 or later**. This aligns with the broader move in the Spring Framework 6, which drops support for older Java versions. This shift ensures the framework leverages modern Java features, which provide better performance and security enhancements.

- **Removal of Deprecated Classes**:
    - **Spring Security 5.x**: Relies heavily on extending **WebSecurityConfigurerAdapter** for configuring security, where you override specific methods for HTTP security and authentication.
    - **Spring Security 6.x**: Removes **WebSecurityConfigurerAdapter** and introduces a **bean-based configuration** approach using `SecurityFilterChain` and `AuthenticationManager`. This provides more flexibility and aligns with functional programming paradigms in modern Java development.

- **New Configuration Approach**:
    - **Spring Security 5.x**: Configuration typically involved extending `WebSecurityConfigurerAdapter` and overriding specific methods to configure security features.
    - **Spring Security 6.x**: Adopts a more declarative, **bean-based approach** for configuration. This new configuration style uses `SecurityFilterChain` and `AuthenticationManager` beans to define security rules, offering a cleaner and more concise way to manage security.

- **Improved OAuth 2.0 and OAuth 2.1 Support**:
    - **Spring Security 5.x**: Introduced OAuth 2.0 support, but was more focused on client-side and resource server configurations.
    - **Spring Security 6.x**: Expands on this by offering full support for the **OAuth 2.1 specification** and provides **improved integration** with external OAuth 2.0 identity providers, making it easier to implement modern authentication protocols.

- **Password Encoding Enhancements**:
    - **Spring Security 5.x**: Provided support for common password encoders like **BCrypt**, **PBKDF2**, and **NoOpPasswordEncoder**.
    - **Spring Security 6.x**: Enhances password encoding with stronger, modern algorithms and better support for secure password management, making it easier to adopt the latest security standards.

- **Updated Dependencies for Spring Boot Compatibility**:
    - **Spring Security 5.x**: Compatible with **Spring Boot 2.x**, supporting Java 8 or higher.
    - **Spring Security 6.x**: Aligned with **Spring Boot 3.x**, which requires **Java 17 or higher**. Developers using Spring Boot 3.x should use the updated Spring Security starter, ensuring compatibility with the latest versions of both Spring Boot and Java.

**In the following we will use Spring Security 5.x; in the upcoming `webservicerestsecurity` project we will use Security 6.x.**

---

## Basic Example

Here’s a basic Spring Security configuration with HTTP Basic Authentication and in-memory details:

```java
package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  // Define PasswordEncoder bean
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(); // BCrypt hashing for secure password storage
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
            .authorizeRequests()                     // Step 1: Configure authorization
                .antMatchers("/public/**").permitAll()   // Step 2: Allow access to public URLs
                .anyRequest().authenticated()           // Step 3: Require authentication for all other requests
            .and()
            .httpBasic();                            // Step 4: Enable HTTP Basic Authentication
  }

  // Define UserDetailsService bean with injection for PasswordEncoder
  @Bean
  @Override
  public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    manager.createUser(User.withUsername("user")
            .password(passwordEncoder.encode("password"))  // Password is encoded here
            .build());
    return manager;
  }
}
```

### WebSecurityConfigurerAdapter
`WebSecurityConfigurerAdapter` is a base class in Spring Security that provides default implementations for several methods related to configuring web security. When you extend this class, you can override its methods to customize the security configuration for your application.  The `@Override`  annotation is used to indicate that a method is overriding a method in the superclass.

In this case, the `@Override` annotation is applied to the `configure(HttpSecurity http)` method, which is inherited from `WebSecurityConfigurerAdapter`. By overriding this method, you can customize how HTTP security is applied to the web application, such as controlling which URLs are accessible and how users are authenticated.

By extending `WebSecurityConfigurerAdapter` and overriding its methods, you can tailor the security settings to meet your specific requirements. For example, you can decide which endpoints are publicly accessible, configure authentication types, and apply authorization rules as needed.

### PasswordEncoder Bean
Imagine you have a special secret codebook where you write down all of your passwords. But instead of writing the password out directly, you mix it up using a secret pattern that only you know. This way, if someone finds your codebook, they won’t be able to read the passwords without knowing the secret pattern.

In this case, the object returned by `passwordEncoder()` is like a magical codebook that helps mix up your password so that nobody can read it easily. It makes your password safe and unreadable unless someone has the key to unlock it.

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(); // BCrypt hashing for secure password storage
}
```

The PasswordEncoder bean is used to hash user passwords securely before storing them.

The `BCryptPasswordEncoder` is a cryptographic algorithm that provides secure, slow hashing designed to be resistant to brute-force attacks. It is designed to be slow, which makes it more difficult for attackers to crack passwords using brute-force methods.

Some alternatives are:

````java
// Using BCryptPasswordEncoder for secure password hashing that is resistant to brute-force attacks
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}

// Using NoOpPasswordEncoder which does not encode passwords (for testing or non-secure use cases)
@Bean
public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance(); 
}

// Using Pbkdf2PasswordEncoder, a strong and configurable algorithm for password hashing, secure against dictionary attacks
@Bean
public PasswordEncoder passwordEncoder() {
    return new Pbkdf2PasswordEncoder();
}

// Using Argon2PasswordEncoder, one of the most secure password hashing algorithms, resistant to side-channel and GPU-based attacks
@Bean
public PasswordEncoder passwordEncoder() {
    return new Argon2PasswordEncoder();
}

// Using SCryptPasswordEncoder, designed to be memory and CPU-intensive, providing strong resistance to hardware-based attacks
@Bean
public PasswordEncoder passwordEncoder() {
    return new SCryptPasswordEncoder();
}
````

### UserDetailsService Bean and InMemoryUserDetailsManager
Think of `UserDetailsService` like a phone book, but for users on a website. It keeps track of all the people who can enter and what their passwords are. So, when someone tries to log in, we ask this phone book:

````text
"Do you know this person, and do they have the correct password?"
````

The `UserDetailsService` is like the helper who looks up the details about each person and checks if they are allowed to enter.

Now, `InMemoryUserDetailsManager` is like a special phone book that keeps all of the people’s details in a notebook inside the computer's memory. It’s really fast to look through because it’s all kept in the computer’s brain, but it’s not stored anywhere permanent (like a database).

```java
@Bean
@Override
public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user")
        .password(passwordEncoder.encode("password"))  // Password is encoded here
        .build());
        return manager;
        }
```

- **UserDetailsService**: This method defines the `UserDetailsService` bean, which is an interface used by Spring Security to retrieve user information (like the username, password, and roles) for authentication purposes. The `InMemoryUserDetailsManager` is a simple implementation of `UserDetailsService` that stores user details in memory, rather than in a persistent database.

- **InMemoryUserDetailsManager**:
    - The `InMemoryUserDetailsManager` is used to store user details temporarily in the computer's memory. Think of it as a fast, in-memory "phone book" where user details are quickly accessible while the application is running. However, unlike a database, this data is not stored permanently and is lost when the application stops.
    - In this example, a user with the username "user" and an encoded password "password" is created and stored in memory.
    - When Spring Security needs to authenticate a user, it queries the `InMemoryUserDetailsManager` to retrieve the user's information, such as their username, password, and assigned roles.
    - **Password Encoding**: Instead of storing a user's password in plain text (which could be insecure), the password is encoded before being saved. Imagine having a secret codebook where you write down all your passwords, but instead of writing the password directly, you scramble it using a pattern only you know. Even if someone finds your codebook, they won’t be able to read the passwords unless they know the pattern.
        - The `PasswordEncoder` works like that secret codebook. It transforms the password into a scrambled version, ensuring that even if the encoded password is exposed, it cannot be easily decoded by an attacker.
        - The method `passwordEncoder.encode("password")` scrambles the password, making it unreadable unless someone knows how to decode it using the same algorithm and key.
    - **Best Practice**: Hardcoding passwords directly in your code (like in this example) is not a secure practice. For better security, it’s recommended to use externalized secrets management solutions. You can securely store passwords in a secure file, database, or use tools like HashiCorp Vault, AWS Secrets Manager, or environment variables to inject sensitive data into the application securely.
    - The `InMemoryUserDetailsManager` is suitable for simple or temporary use cases, like development or small applications. For production environments with more users or long-term data storage needs, a more permanent solution, like a database, is recommended.
    - When an authentication request is made (for example, using HTTP Basic Authentication), Spring Security calls the `UserDetailsService` to fetch the user details.
    - The `InMemoryUserDetailsManager` will then compare the username provided in the request with the stored user information in memory, and check if the password matches by comparing the encoded version of the password.
    - If the authentication is successful (i.e., the username and encoded password match), the user is granted access to the requested resource.

- **Multiple users**:
  - If you need to define additional users, you can simply add more `manager.createUser()` calls to the `InMemoryUserDetailsManager`. For example:

```java
@Bean
@Override
public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

    manager.createUser(User.withUsername("user")
            .password(passwordEncoder.encode("password"))
            .build());

    manager.createUser(User.withUsername("admin")
            .password(passwordEncoder.encode("adminPassword"))
            .build());

    manager.createUser(User.withUsername("guest")
            .password(passwordEncoder.encode("guestPassword"))
            .build());
    
    return manager;
}
```

### Security filter chain
In Spring Security, a **security filter chain** is a sequence of filters automatically applied to secure your application. These filters handle tasks such as authentication, authorization, and protection against common threats.

The code below demonstrates how Spring Security’s **fluent API** allows developers to configure the behavior of this security filter chain through **in-place method calls**.

Each method in the chain returns the object itself (or another object with similar methods), allowing for method chaining and enabling the developer to write more readable and expressive code. Methods like `.authorizeRequests()`, `.antMatchers()`, `.permitAll()`, and others are chained together, modifying the security configuration in a clean and concise manner.

Thi is how the security filter chain is configured in this example:

```java
http
        .authorizeRequests()                     // Step 1: Start configuring authorization
            .antMatchers("/public/**").permitAll()   // Step 2: Allow unrestricted access to /public/**
            .anyRequest().authenticated()           // Step 3: Require authentication for all other requests
        .and()
        .httpBasic();                            // Step 4: Enable HTTP Basic Authentication
```

Each method in the configuration chain returns the same object (or an object with similar methods) **in-place**, allowing you to chain multiple method calls together in a fluid and intuitive manner. This makes it easy to configure the security behavior without having to create new objects or repeat code.

Essentially:

- This configuration is part of the **security filter chain** in Spring Security, though you're not manually defining each individual filter.
- You are specifying the **behavior** of the security filters that Spring Security will automatically use when processing HTTP requests.
- The filters themselves are built-in components in Spring Security, and this configuration simply influences how they operate.
- You're configuring the **security filter chain**, which is a sequence of security-related filters that Spring Security uses behind the scenes to secure your application.
- When a request is processed, Spring Security applies a series of filters in a specific order to handle different security tasks such as authentication, authorization, and protection against common attacks.
- The chained method calls, such as `.authorizeRequests()` and `.httpBasic()`, modify how these built-in filters behave, but they don’t explicitly define or create new filters.

This is what each in-place method does:

- **authorizeRequests()**: This method starts the process of configuring URL-based authorization. It tells Spring Security to begin analyzing incoming HTTP requests and apply security measures according to the specified URL patterns.

- **antMatchers("/public/**")**: This method defines the URL pattern for which specific authorization rules will apply. In this case, it matches any URL starting with `/public/`. This uses **Ant-style path patterns**, which are a flexible way to match URL paths, similar to how wildcards are used in file system patterns (`*` and `**`).

- **permitAll()**: After specifying the URL pattern with `antMatchers()`, `permitAll()` allows unrestricted access to those URLs. This bypasses authentication or any access control, meaning that URLs matching `/public/**` can be accessed by any user, whether they are authenticated or not. This modifies the behavior of the **authorization filter** to ensure these paths are publicly accessible.

- **anyRequest().authenticated()**: This method ensures that any request not matching the previously defined patterns (like `/public/**`) will require authentication. It enforces authentication for all other URL patterns, modifying the behavior of the **authorization filter** to restrict access to authenticated users only.

- **httpBasic()**: By calling `httpBasic()`, you configure Spring Security to use **HTTP Basic Authentication**. With this enabled, Spring Security expects the HTTP request to contain a username and password in the header. These credentials are validated using a `UserDetailsService` (in this case, one that might pull user data from an in-memory store).

---

## Basic Authentication
**Basic Authentication** involves sending the username and password with every HTTP request. This happens because **Basic Authentication** is stateless by design. Unlike some other authentication mechanisms that can maintain a session, Basic Authentication does not retain any memory of previous interactions between the client and the server.

HTTP itself is a **stateless protocol**, meaning that each request is independent of any previous requests. This stateless nature of HTTP means that the server doesn't store information about prior requests, including authentication status.

Since Basic Authentication doesn't use sessions or cookies to remember an authenticated user, the client must include the **username and password** with each request to prove its identity again. The server does not "remember" that the client was authenticated previously; it simply checks the credentials provided in each request.

When a client wants to authenticate itself to a server, it sends the credentials (username and password) in the `Authorization` header of each HTTP request. These credentials are typically encoded in **Base64** format, creating a string like `username:password`. This encoded string is then included in the `Authorization` header.

Here is an example of how the credentials are expected in a POST request when Basic Authentication is used:

```http
POST /api/protected-resource HTTP/1.1
Host: example.com
Authorization: Basic dXNlcjpwYXNzd29yZA==  // Base64-encoded username:password
Content-Type: application/json

{
    "data": "sample data"
}
```

### InMemoryUserDetailsManager
As we know, in Spring Security, the "phone book" for user credentials (username and password) is the `UserDetailsService`. This service is responsible for looking up user details and checking if the provided credentials match. However, there are different types of "phone books" that can be used to store and manage these credentials.

One such implementation is the `InMemoryUserDetailsManager`, which allows you to inject hardcoded user-password pairs directly into the system. While this is simple and useful for small applications or testing scenarios, it does not scale well for larger applications. As the number of users grows, hardcoding all user credentials in memory becomes impractical and inefficient.

For applications with many users or those requiring persistence, relying on an in-memory solution is not ideal. In such cases, a more scalable solution, like storing user credentials in a database or using an external identity provider, would be necessary to ensure security, scalability, and persistence over time.

In the provided code, the `passwordEncoder.encode("password")` method scrambles the password `"password"` using the algorithm defined in the `PasswordEncoder` bean (in this case, BCrypt). This makes the password unreadable unless someone has access to the secret algorithm and its parameters. So, even if an attacker gains access to the application’s memory or database, they won’t be able to easily decipher the original password, because it has been securely transformed using the "codebook."

The `PasswordEncoder` is injected into the `userDetailsService` method to ensure that passwords are securely encoded before they are added to the `InMemoryUserDetailsManager`. This approach follows best practices for storing passwords and ensures that user data remains protected from unauthorized access.

In short, the `PasswordEncoder` is like a "magic" codebook that ensures passwords are stored in a safe, unreadable format, preventing security breaches due to exposed passwords.

```java
package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
            .authorizeRequests()
                .antMatchers("/public/**").permitAll()   // Allow public access to specific URLs
                .anyRequest().authenticated()           // Require authentication for all other requests
            .and()
            .httpBasic();                            // Enable HTTP Basic Authentication
  }

  // Define the PasswordEncoder bean
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(); // BCrypt hashing for secure password storage
  }

  // Inject PasswordEncoder via method parameters
  @Override
  @Bean
  public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    manager.createUser(User.withUsername("user")
            .password(passwordEncoder.encode("password")) // Encoding the password
            .build());
    manager.createUser(User.withUsername("admin")
            .password(passwordEncoder.encode("admin")) // Encoding the password
            .build());
    return manager;
  }
}
```
Due to the configuration:

```java
.anyRequest().authenticated()           // Require authentication for all other requests
.and()
.httpBasic();                            // Enable HTTP Basic Authentication
```

- **Step 1**: Spring Security enforces authentication for all incoming requests, except those explicitly permitted (e.g. `/public/**`).
- **Step 2**: When a client sends a request with **Basic Authentication credentials**, it includes the credentials in the HTTP `Authorization` header. The header looks like this:

  `Authorization: Basic dXNlcjpwYXNzd29yZA==`

  Here, `dXNlcjpwYXNzd29yZA==` is the **Base64-encoded** value of `username:password` (e.g. `user:password`).

- **Step 3**: Spring Security extracts the **Base64-encoded credentials** from the `Authorization` header.

- **Step 4**: Spring Security decodes the **Base64** value to retrieve the username and password (e.g. `user:password`).

- **Step 5**: The **`userDetailsService`** bean (implemented with `InMemoryUserDetailsManager`) is called to retrieve the user details associated with the username.
    - The `InMemoryUserDetailsManager` contains the hardcoded user credentials (e.g. `"user"` and `"admin"`) along with their corresponding **hashed passwords**.

- **Step 6**: Spring Security uses the **configured `PasswordEncoder`** (in this case, `BCryptPasswordEncoder`) to hash the provided password and compare it with the stored, hashed version.
    - The password from the `Authorization` header is compared against the hashed password stored in memory.

- **Step 7**: If the **password matches** (i.e. the provided password's hash matches the stored hash), the authentication **succeeds**.
    - The user is granted access to the protected resource they requested.

- **Step 8**: If the **password does not match** or no matching user is found, authentication **fails**, and the user is denied access.
    - In this case, Spring Security typically returns a **401 Unauthorized** HTTP status code.

- **Step 9**: After the initial successful authentication, each subsequent request from the client will need to include the **username and password** in the `Authorization` header again.

- **Step 10**: Since **Basic Authentication** is stateless, the server does not remember previous authentication. Each request is treated as a new request, requiring the client to send the **credentials** every time.

- **Step 11**: If the **client omits** the `Authorization` header or sends **incorrect credentials**, the server will respond with a **401 Unauthorized** status code, prompting the client to provide valid credentials.

- **Step 12**: If the client correctly resends the credentials in the `Authorization` header, the server will revalidate the username and password and either grant or deny access based on the comparison of the credentials.

- **Step 13**: This process repeats with each HTTP request, maintaining the stateless nature of Basic Authentication, ensuring the client always provides credentials for each request.

### JdbcUserDetailsManager
As we know, in Spring Security, the "phone book" for user credentials (username and password) is the `UserDetailsService`. This service is responsible for looking up user details and verifying if the provided credentials match. However, there are different types of "phone books" that can be used to store and manage these credentials.

One such implementation is the `JdbcUserDetailsManager`, which allows you to retrieve user details from a relational database. Unlike the `InMemoryUserDetailsManager`, which stores credentials in memory, `JdbcUserDetailsManager` persists user data in a database, making it a more scalable and robust solution. This is especially useful for applications with many users or those requiring persistence beyond application restarts.

While the `InMemoryUserDetailsManager` is great for small-scale or test applications, it doesn't scale well as the user base grows, since all user data is lost upon restart. In contrast, `JdbcUserDetailsManager` ensures that user credentials are stored securely in a database, which offers better scalability, persistence, and the ability to manage larger user populations.

For applications that require persistence, such as those with a large user base or those requiring long-term storage, using a database-backed solution like `JdbcUserDetailsManager` provides a more reliable and secure option for handling user credentials.

```java
package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
            .authorizeRequests()
                .antMatchers("/public/**").permitAll()
                .anyRequest().authenticated()
            .and()
            .httpBasic();
  }

  // Define JdbcUserDetailsManager bean
  @Bean
  public JdbcUserDetailsManager userDetailsService(DataSource dataSource, PasswordEncoder passwordEncoder) {
    JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
    userDetailsManager.setPasswordEncoder(passwordEncoder);  // Set the PasswordEncoder explicitly
    return userDetailsManager;
  }

  // Define PasswordEncoder bean
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();  // Password encoding using BCrypt
  }
}
```

- **JdbcUserDetailsManager in Spring Security**

    - The `JdbcUserDetailsManager` is used to retrieve and manage user credentials stored in a relational database.
    - It offers a persistent solution for applications that need to scale and store user data securely, making it ideal for large applications.

- **Key Features:**

    - **Database Interaction:**

        - Interacts with a relational database to load user credentials and authorities required for user authentication and authorization.

    - **Default Database Schema:**

        - Relies on two core tables: `users` and `authorities`.
        - These tables store the necessary information for user authentication and roles/permissions.
        - **users table:**
            - Stores username, password (**hashed**), and enabled status.
        - **authorities table:**
            - Links usernames to roles (e.g., ADMIN, USER, GUEST).

- **Example Schema:**

```sql
CREATE TABLE users (
    username VARCHAR(50) NOT NULL PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL
);

CREATE TABLE authorities (
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    FOREIGN KEY (username) REFERENCES users(username)
);
```

- **Table Breakdown:**

    - **users Table:**

        - Contains user information: username, **hashed** password, and enabled status.
        - If `enabled = false`, the user is prevented from logging in.

    - **authorities Table:**

        - Holds user roles or permissions.
        - Associates each username with an authority (e.g., ADMIN, USER).

- **Customizing SQL Queries:**

    - JdbcUserDetailsManager uses default queries but you can customize them if your schema differs.

```java
userDetailsManager.setUsersByUsernameQuery("SELECT username, password, enabled FROM my_users WHERE username = ?");
userDetailsManager.setAuthoritiesByUsernameQuery("SELECT username, authority FROM my_user_roles WHERE username = ?");
```

- **Using the @Bean Annotation:**

    - Defines methods that return Spring-managed objects.
    - Declaring JdbcUserDetailsManager as a bean allows Spring to manage its lifecycle.

- **Method Parameters:**

    - **DataSource:**
        - Provides the database connection used to query the `users` and `authorities` tables.

    - **PasswordEncoder:**
        - Defines the hashing algorithm used to handle passwords.
        - Passwords are **never stored in clear**, they are **stored as hashed values**.
        - The `PasswordEncoder` is **critical** for:
            - **Hashing** the password before storage.
            - **Verifying** the password during login by applying the same hashing algorithm and comparing the hashes.
        - Without configuring a `PasswordEncoder`, the application would not know how to match the provided password with the stored hashed password.

- **Example: Setting Up JdbcUserDetailsManager:**

```java
JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
```

- **Setting the Password Encoder:**

    - **Essential for security**, as passwords must never be stored as plain text.
    - Passwords are **stored as hashed values** using the algorithm defined by the `PasswordEncoder`.
    - During authentication, the provided password is **hashed using the same algorithm** and compared with the stored hash.
    - Example using BCrypt:

```java
PasswordEncoder encoder = new BCryptPasswordEncoder();
```

- **Important:** Even if the database is compromised, only **hashed passwords** are exposed, making it difficult to retrieve the original passwords.

- **Returning the Bean:**

    - After configuration, return the `JdbcUserDetailsManager` as a Spring bean:

```java
return userDetailsManager;
```

Due to the configuration:

```java
.anyRequest().authenticated()           // Require authentication for all other requests
.and()
.httpBasic();                            // Enable HTTP Basic Authentication
```

- **Step 1**: Spring Security enforces authentication for all incoming requests, except those explicitly permitted (e.g. `/public/**`).

- **Step 2**: When a client sends a request with **Basic Authentication credentials**, it includes the credentials in the HTTP `Authorization` header. The header looks like this:

  `Authorization: Basic dXNlcjpwYXNzd29yZA==`

  Here, `dXNlcjpwYXNzd29yZA==` is the **Base64-encoded** value of `username:password` (e.g. `user:password`).

- **Step 3**: Spring Security extracts the **Base64-encoded credentials** from the `Authorization` header.

- **Step 4**: Spring Security decodes the **Base64** value to retrieve the username and password (e.g. `user:password`).

- **Step 5**: Spring Security delegates the authentication process to the **`JdbcUserDetailsManager`** bean, which is configured to retrieve user details from the relational database.

- **Step 6**: The **`JdbcUserDetailsManager`** queries the database (typically using SQL) to retrieve the user details associated with the provided username.
    - It checks the `users` table for the matching username and retrieves the stored **hashed password**, **enabled status**, and any additional user details.

- **Step 7**: Spring Security uses the **configured `PasswordEncoder`** (in this case, `BCryptPasswordEncoder`) to compare the provided password (after hashing it) with the stored, hashed password in the database.

- **Step 8**: If the **password matches** (i.e. the hashed version of the provided password matches the stored hash), the authentication **succeeds**.
    - The user is granted access to the requested protected resource.

- **Step 9**: If the **password does not match** or no matching user is found, authentication **fails**.
    - In this case, Spring Security typically returns a **401 Unauthorized** HTTP status code, indicating that the credentials were invalid or the user does not exist.

- **Step 10**: If authentication is successful, Spring Security proceeds to handle the request.

- **Step 11**: After the initial successful authentication, each subsequent request from the client must include the **username and password** in the `Authorization` header again, as Basic Authentication is stateless.

- **Step 12**: Since **Basic Authentication** is stateless, the server does not retain any memory of the previous request. Each request is treated independently, requiring the client to send the **credentials** every time.

- **Step 13**: If the **client omits** the `Authorization` header or sends **incorrect credentials**, Spring Security will respond with a **401 Unauthorized** status code, prompting the client to resend valid credentials.

- **Step 14**: If the client resends the **correct credentials** in the `Authorization` header, Spring Security will repeat the authentication process:
    - The **`JdbcUserDetailsManager`** will query the database again to verify the credentials, check the user’s **enabled status**, and retrieve the **hashed password**.

- **Step 15**: This process repeats for each HTTP request. Even if the user has been authenticated once, the server will not remember the authentication for subsequent requests unless another mechanism (e.g., session management or tokens) is used.

---

## Role-Based Authentication
Role-based authorization restricts access based on assigned roles.

```java
package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // Configure HTTP security
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/public/**").permitAll()         // Allow access to "/public/**" without authentication
                .antMatchers("/admin/**").hasRole("ADMIN")     // Only "ADMIN" role can access "/admin/**"
                .antMatchers("/user/**").hasRole("USER")       // Only "USER" role can access "/user/**"
                .anyRequest().authenticated()                  // All other requests need authentication
            .and()
            .httpBasic();                                     // Use HTTP Basic Authentication
    }

  // PasswordEncoder bean for password encoding
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();   // BCryptPasswordEncoder for securely encoding passwords
  }

  // UserDetailsService bean definition with PasswordEncoder injected
  @Override
  @Bean
  public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
      InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

      // Create an "admin" user with role "ADMIN"
      manager.createUser(User.withUsername("admin")
              .password(passwordEncoder.encode("admin"))  // Encode password
              .roles("ADMIN")                             // Assign role
              .build());

      // Create a "user" user with role "USER"
      manager.createUser(User.withUsername("user")
              .password(passwordEncoder.encode("user"))   // Encode password
              .roles("USER")                              // Assign role
              .build());

      return manager;
  }
}
```

- Spring Security matches request URLs against the configured `antMatchers` rules:
    - `/public/**` is allowed without authentication (`permitAll()`).
    - `/admin/**` requires the user to have the "ADMIN" role.
    - `/user/**` requires the user to have the "USER" role.
    - Rules about roles are defined before authentication, but **they are applied only after the user has been authenticated**.
    - Any other request requires authentication (`authenticated()`).

- If a request matches a protected URL (like `/admin/**` or `/user/**`), the user must be authenticated via HTTP Basic Authentication (`httpBasic()`), which prompts for credentials.
- The username and password are verified against the in-memory user store (`InMemoryUserDetailsManager`).
- **Authentication happens first**: Spring Security establishes the user’s identity by verifying the credentials provided.
- Once authentication is successful, Spring Security retrieves the user's roles and applies the role-based rules.

- After authentication, Spring Security checks if the user has the required role for the requested URL.
    - For example, if the request matches `/admin/**`, it checks if the user has the "ADMIN" role using `hasRole()`.
- Role-based checks are applied **only after successful authentication**, as the system needs to know the user’s identity before checking their roles.
- If the user doesn't have the appropriate role, access is denied.

- If authentication and role checks pass, the request proceeds to the corresponding handler (e.g., a controller method).

- If authentication or role checks fail, an HTTP 401 (Unauthorized) or 403 (Forbidden) response is returned.

- This setup ensures that only authenticated and authorized users can access specific parts of the application based on their roles.

---

## Authentication-related session management
**Creating an authentication-related session means generating a server-side session, referenced by a unique identifier, where certain information about the authenticated user is stored**.

This session is typically linked to the user’s interaction with the application, ensuring that subsequent requests can reference the same session and access the user’s authentication details without requiring them to log in again.

### JSESSIONID
When a server creates an authentication-related session, it typically adds a session identifier `JSESSIONID` to the HTTP response.

This session identifier is placed in the response as a cookie or as part of the response headers. This allows the client (usually a browser) to track the session across requests.

For example, a server-side session might result in the following:

- **Session ID**: A unique identifier for the session (e.g. `JSESSIONID=12345abcde`).
  - This is stored as a cookie on the client side (browser), which is sent with subsequent requests to maintain the session.
- **Other session data**: Information about the authenticated user, such as their roles, permissions, or user-specific data might be stored in the session on the server side, but this data is not automatically sent in the response. It’s only accessed from the server's session store when needed under the underlying `JSESSIONID`.

Here’s an example of a response with a session ID in a cookie:

```http
HTTP/1.1 200 OK
Set-Cookie: JSESSIONID=12345abcde; Path=/; HttpOnly; Secure; SameSite=Strict
Content-Type: text/html; charset=UTF-8
```

When a session is created:

- **Server-Side**: The server generates a session and stores session-specific data (such as authentication details) on the server. This data could include the user’s roles, permissions, or other contextual information.
- **Client-Side**: The client (browser or HTTP client) receives a session cookie (`JSESSIONID` by default). This cookie is sent with every subsequent request to the server, allowing the server to look up the session and retrieve any data that was stored for that user.

For every subsequent request, the client sends the session cookie (`JSESSIONID`) back to the server as part of the HTTP request headers.

Example request:

```http
GET /user/profile HTTP/1.1
Host: example.com
Cookie: JSESSIONID=12345abcde
```

The server uses the session ID in the cookie to:

- Look up the user’s session on the server.
- Retrieve any session-specific data (such as authentication details, user roles, etc.) associated with that session.
- Use that data to authorize the user’s access to the requested resource.

### SessionCreationPolicies
There are mainly two ways to preserve authentication and authorization across multiple requests without performing Basic Authentication each time:

- **stateful:** using an _authentication-related session_ i.e. a `JSESSIONID` is introduced and sent to the client as a cookie to maintain the session state across requests; this introduces session management overhead and can lead to security concerns;
- **state-less:** using session-less authentication with no need for the server to store session state; a token (e.g. JWT) itself is sufficient for each request.

In Spring Security both ways are feasible.

Herein we look at how it is possible to create and manage authentication-related sessions by specifying a `SessionCreationPolicy`.

#### `SessionCreationPolicy.IF_REQUIRED`

By default, Spring Security uses `SessionCreationPolicy.IF_REQUIRED`. This means that an authentication-related session is created only when deemed necessary by the framework.

As result, the following:

```java
@Override
protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
            .antMatchers("/public/**").permitAll()
            .anyRequest().authenticated()
            .and()
        .httpBasic();
}
```

is equivalent to:

````java
@Override
protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
            .antMatchers("/public/**").permitAll()  // Allow public access to specific URLs
            .anyRequest().authenticated()          // Require authentication for all other requests
            .and()
        .httpBasic()                               // Enable HTTP Basic Authentication
        .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);  // Default, session is created if needed
}
````

That is, when no session creation policy is defined in the security filter chain, Spring Security uses `SessionCreationPolicy.IF_REQUIRED` as the default.

This means that **the decision to create an authentication-related session is left to the framework** based on the authentication mechanism being used.

**Scenario 1: no session is required**

Basic Authentication is inherently **stateless**, so if the security filter chain is as follows:

````java
@Override
protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
            .antMatchers("/public/**").permitAll()  // Allow public access to specific URLs
            .anyRequest().authenticated()          // Require authentication for all other requests
            .and()
        .httpBasic()                               // Enable HTTP Basic Authentication
        .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);  // Default, session is created if needed
}
````

no session will be created at all because it is deemed **not required**

In the following example:

- three HTTP requests and responses are exchanged with **Basic Authentication**;
- each request sends the credentials in the `Authorization` header
- the server responds with a **`200 OK`** status, and each time, a message is returned indicating access is granted;
- no session cookies are sent, which means no session is created, maintaining the stateless nature of **Basic Authentication**. The behavior of each request and response shows that authentication is done per request without relying on session storage.

***

````plaintext
GET /protected-resource HTTP/1.1
Host: example.com
Authorization: Basic dXNlcjpwYXNzd29yZA==
````

```http
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 20

{
"message": "Access granted"
}
```

***

````plaintext
GET /protected-resource HTTP/1.1
Host: example.com
Authorization: Basic dXNlcjpwYXNzd29yZA==
````

```http
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 20

{
"message": "Access granted"
}
```

***

````plaintext
GET /protected-resource HTTP/1.1
Host: example.com
Authorization: Basic dXNlcjpwYXNzd29yZA==
````

````http
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 20

{
"message": "Access granted"
}
````

***

**Scenario 2: session is required**

As opposite to Basic Authentication which is **stateless**, if **form-based authentication** is in use then Spring Security will consider an authentication-related session as required and will introduce a `JSESSIONID` as a session cookie.

In this case the security filter chain will look like as follows:

````java
@Override
protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
            .antMatchers("/public/**").permitAll()  // Allow public access to specific URLs
            .anyRequest().authenticated()          // Require authentication for all other requests
            .and()
        .formLogin()                               // Enable form-based authentication
        .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);  // Create a session if required
}
````

where we simply replaced `httpBasic() ` with `formLogin()`.

As result:

- form-based authentication is enabled;
- when users try to access a protected resource without being authenticated, they will be redirected to a default login form;
- this form prompts for the username and password to be submitted through an HTML-form.

For **form-based authentication**, the session will be created because it is deemed **required**.

In the following example:

- **three HTTP requests and responses** are exchanged, starting with a **GET request** to access a protected resource, followed by a **POST request** to the login page, and finally, a request to access the protected resource again;
- the first request is a **GET request** to the protected resource, which redirects the user to the login page as they are not authenticated;
- the second request is a **POST request** where the user submits their credentials (username and password) via the login form;
- the server processes the credentials, responds with a **302 redirect** back to the protected resource, and sets a **session cookie** (like `JSESSIONID`) to maintain the user's authenticated session;
- the third request sends the session cookie along with the request to the protected resource, confirming the user is authenticated and granting access to the resource.

***

````plaintext
GET /protected-resource HTTP/1.1
Host: example.com
````

````http
HTTP/1.1 302 Found
Location: /login
````

***

````plaintext
GET /login HTTP/1.1
Host: example.com
````

````html
<!-- HTML Form for login -->
<form method="POST" action="/login">
    <label for="username">Username:</label>
    <input type="text" id="username" name="username">
    <label for="password">Password:</label>
    <input type="password" id="password" name="password">
    <button type="submit">Login</button>
</form>
````

***

````plaintext
POST /login HTTP/1.1
Host: example.com
Content-Type: application/x-www-form-urlencoded
Content-Length: 43

username=user&password=password
````

````http
HTTP/1.1 302 Found
Location: /protected-resource
Set-Cookie: JSESSIONID=abcd1234
````

***

````plaintext
GET /protected-resource HTTP/1.1
Host: example.com
Cookie: JSESSIONID=abcd1234
````

````http
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 20

{
"message": "Access granted"
}
````

***

#### `SessionCreationPolicy.ALWAYS`
When using `SessionCreationPolicy.ALWAYS`, Spring Security guarantees that a session will be created if one doesn't already exist. If the client sends a valid session cookie (e.g., `JSESSIONID`), the session will be reused. However, if no session exists, one will be created. This ensures that authentication-related sessions are always created, even for stateless authentication mechanisms like **Basic Authentication**.

- **Session Creation**: A session is created when there is no existing session cookie (`JSESSIONID`).
- **Session Reuse**: If the client sends a valid `JSESSIONID` cookie, the existing session will be reused without creating a new one.
- **Stateless Authentication**: Even though mechanisms like Basic Authentication are stateless, Spring Security ensures that a session will be created if necessary.

This behavior ensures that sessions are always available, providing more flexibility in scenarios where you want to keep track of user sessions even for stateless requests.

```java
@Override
protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
            .antMatchers("/public/**").permitAll()
            .anyRequest().authenticated()
            .and()
        .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
            .and()
        .httpBasic();
}
```

In the following example:

- three HTTP requests and responses are exchanged, but this time with `SessionCreationPolicy.ALWAYS`.
- the first request sends credentials in the `Authorization` header.
- the server responds with a **`200 OK`** status, creates a session, and sends a session cookie (`JSESSIONID`).
- in the subsequent requests, no credentials are included, but the session is reused using the `JSESSIONID` cookie, maintaining the user's authenticated session.

***

````plaintext
GET /protected-resource HTTP/1.1
Host: example.com
Authorization: Basic dXNlcjpwYXNzd29yZA==
````

````http
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 20
Set-Cookie: JSESSIONID=abcd1234

{
"message": "Access granted"
}
````

***

````plaintext
GET /protected-resource HTTP/1.1
Host: example.com
Cookie: JSESSIONID=abcd1234
````

````http
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 20

{
"message": "Access granted"
}
````

***

````plaintext
GET /protected-resource HTTP/1.1
Host: example.com
Cookie: JSESSIONID=abcd1234
````

````http
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 20

{
"message": "Access granted"
}
````

***

#### `SessionCreationPolicy.NEVER`
With `SessionCreationPolicy.NEVER`, a session will never be created by Spring Security.

Even though no new sessions will be created, existing session data could still persist and be used.

The server may still have session data from previous requests and use them if sessions were created earlier (even though they won't create new ones).

```java
@Override
protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
            .antMatchers("/public/**").permitAll()
            .anyRequest().authenticated()
            .and()
        .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.NEVER)
            .and()
        .httpBasic();
}
```

#### `SessionCreationPolicy.STATELESS`
With `SessionCreationPolicy.STATELESS`, no session is created, and the application remains fully stateless.

Even if there was session data (such as a session ID) from a previous request, it will be ignored. Each request is treated independently, and all necessary information (like authentication) must be provided with the request itself, typically through tokens.

**This ensures that each request is independent, with no session state maintained or used on the server, which is crucial for stateless architectures like REST APIs.**

```java
@Override
protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
            .antMatchers("/public/**").permitAll()
            .anyRequest().authenticated()
            .and()
        .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
        .httpBasic();
}
```

#### Summary
| Policy                                        | What happens on the server                                                         | What happens in the response                        | What happens in subsequent requests                                          |
|-----------------------------------------------|------------------------------------------------------------------------------------|-----------------------------------------------------|------------------------------------------------------------------------------|
| `SessionCreationPolicy.ALWAYS`                | A session is always created, even if one already exists.                           | `JSESSIONID` cookie is set with a session ID.       | The client sends the `JSESSIONID` cookie with each request.                  |
| `SessionCreationPolicy.IF_REQUIRED` (default) | A session is created only when deemed necessary.                                   | `JSESSIONID` cookie is set if a session is created. | The client sends the `JSESSIONID` cookie with each request.                  |
| `SessionCreationPolicy.NEVER`                 | No session is created. However, pre-existing session data **might** still be used. | No `JSESSIONID` cookie is set.                      | Authentication must be sent explicitly in request headers (e.g. Basic Auth). |
| `SessionCreationPolicy.STATELESS`             | No session is created, and no state is maintained.                                 | No `JSESSIONID` cookie is set.                      | Authentication must be sent explicitly in request headers (e.g. JWT).        |

### Timeout
Session timeout determines how long a session remains active before expiring due to **inactivity**. This refers to the amount of time a session can remain **idle** (without user activity or requests) before it is **invalidated**.

For example, if an application has an **inactivity timeout** of **30 minutes**, and the user doesn’t make any requests within that time, the session will **expire** and the user will be logged out.

Recall that, in Spring Security, when using `SessionCreationPolicy.IF_REQUIRED` a session is created only if necessary. Then we can configure various behaviour of a session timeout as follows: 

```java
@Override
protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
            .antMatchers("/public/**", "/session-expired").permitAll() // Allow unauthenticated access to /session-expired
            .anyRequest().authenticated() // All other requests require authentication
        .and()
        .httpBasic()
        .and()
        .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Create session only if required
            .sessionTimeout(Duration.ofMinutes(30)) // Set session timeout (inactivity timeout) to 30 minutes
            .invalidSessionUrl("/session-expired") // Redirect if session is invalid due to inactivity
            .maximumSessions(1) // Optional: Limit concurrent sessions per user
            .expiredUrl("/session-expired"); // Redirect if session expires
}
```

- **`sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)`**

    - This setting defines when Spring Security should create a session.

    - **`SessionCreationPolicy.IF_REQUIRED`** (default behavior): A session is created **only if needed**.
        - If your application is **stateful** (e.g. maintains user session information), a session will be created when the user first authenticates.
        - If your application is **stateless** (e.g. REST APIs), no session will be created unless explicitly required.

    - This approach provides flexibility, ensuring that a session is created only when necessary, based on your application's needs.

- **`sessionTimeout(Duration.ofMinutes(30))`**

    - This setting specifies the **inactivity timeout** for the session.

    - **Inactivity Timeout**: The session will be considered expired if the user does not perform any action (e.g. make a request) within the defined period (30 minutes in this case).
        - If the user is idle for 30 minutes without interacting with the application, the session will automatically expire.

    - **Behavior**: After the inactivity timeout period, the session will be invalidated, and the user will be redirected to the `/session-expired` URL.

- **`invalidSessionUrl("/session-expired")`**

    - This setting specifies the URL to redirect the user if their session is considered **invalid**.

    - **Invalid Session**: A session is invalid if it is explicitly invalidated or if there is an issue (e.g. session data corruption or missing session cookies).

    - **Behavior**: If a user tries to access a resource with an invalid session, they will be redirected to `/session-expired`.
        - This page informs the user that their session is no longer valid and may prompt them to log in again.

- **`maximumSessions(1)`**

    - This configuration limits the number of concurrent sessions a user can have.

    - **One Session per User**: Setting `maximumSessions(1)` ensures that only one active session is allowed per user.
        - If the user logs in from one device and then logs in from another, the first session will be invalidated.

    - **Behavior**: This is useful when you want to enforce **single-session login**, meaning users cannot have multiple sessions across different devices or browsers.

    - **JSESSIONID Management**: The **JSESSIONID** is a session identifier that tracks a user's active session in a web application. With **`maximumSessions(1)`** in place, the user can only have one valid **JSESSIONID** at any given time.
        - If the user logs in from a new device or browser, the previously issued **JSESSIONID** will be invalidated, and a new **JSESSIONID** will be generated for the new session.
        - This ensures that a user's identity is tied to a single active session at any time, making it easier to manage session security.

    - **Security Considerations**: This approach helps mitigate risks like session hijacking or unauthorized access across multiple devices, as only one session can be active per user. It ensures that users do not maintain multiple concurrent sessions, which can be particularly important for high-security environments.

- **`expiredUrl("/session-expired")`**

    - This setting defines the URL to redirect the user when their session **expires** due to inactivity.

    - **Expired Session**: A session expires when it has been inactive for a certain period, defined by the session timeout setting.

    - **Behavior**: If the session expires due to inactivity (e.g. the user hasn't made any request for a specified period), they will be redirected to `/session-expired`.
        - This page typically informs the user that their session has timed out and prompts them to log in again.

- **Note**: We have added `/session-expired` to the list of **public URLs** in the `authorizeRequests()` section:
    ```java
    .antMatchers("/public/**", "/session-expired").permitAll() // Allow unauthenticated access to /session-expired
    ```
    - This ensures that the **`/session-expired`** URL is publicly accessible, meaning users can visit this page even if they are not authenticated.

### Logout
Logout functionality allows users to safely end their session and ensures that session data is properly cleared upon logout.

In this configuration, we handle user logout behavior, session invalidation, and cookie deletion to ensure that the session is completely cleared when the user logs out.

For example, after a user logs out, they will be redirected to the login page with a `logout` parameter, and their session will be invalidated.

```java
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/public/**", "/session-expired", "/login").permitAll()  // Allow unauthenticated access to /public/**, /session-expired, and /login
                .anyRequest().authenticated()  // All other requests require authentication
            .and()
            .httpBasic()
            .and()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)  // Create session only if required
                .sessionTimeout(Duration.ofMinutes(30))  // Set session timeout (inactivity timeout) to 30 minutes
                .invalidSessionUrl("/session-expired")  // Redirect if session is invalid due to inactivity
                .maximumSessions(1)  // Optional: Limit concurrent sessions per user
                .expiredUrl("/session-expired")  // Redirect if session expires
            .and()
            .logout()
                .logoutUrl("/logout")  // URL that triggers the logout process
                .logoutSuccessUrl("/login?logout")  // Redirect URL after successful logout
                .invalidateHttpSession(true)  // Invalidate the session when logging out
                .clearAuthentication(true)  // Clear authentication data on logout
                .deleteCookies("JSESSIONID")  // Delete session cookies (e.g. JSESSIONID)
                .permitAll();  // Allow all users to access the logout URL
    }
```

- **`logoutUrl("/logout")`:**
    - Defines the URL to trigger the logout process.
    - Users can log out by accessing `/logout`, and their session will be invalidated.

- **`logoutSuccessUrl("/login?logout")`:**
    - After successfully logging out, users will be redirected to the login page with a `logout` parameter in the URL (`/login?logout`).
    - This parameter can be used to display a logout success message to the user.

- **`invalidateHttpSession(true)`:**
    - Ensures that the session is invalidated when the user logs out.
    - This ensures that all session attributes are removed.

- **`clearAuthentication(true)`:**
    - Clears authentication data, effectively logging the user out of the system.
    - This makes sure that the user’s security context is fully cleared.

- **`deleteCookies("JSESSIONID")`:**
    - Deletes the session cookie (`JSESSIONID`) when the user logs out.
    - This helps prevent session hijacking, ensuring that the session identifier is no longer accessible after logout.

- **`permitAll()`:**
    - Ensures that the logout URL (`/logout`) is accessible to everyone, including unauthenticated users.
    - This allows any user to log out, even if they aren't currently logged in.

- **`antMatchers("/public/**", "/session-expired", "/login", "/logout").permitAll()`:**
  - **`/public/**`**, **`/session-expired`**, **`/login`**, and **`/logout`** are publicly accessible.
  - **`/logout`**: Allows users to log out, clearing session data and redirecting them to the login page.
  - **`/login?logout`**: Redirects to the login page after successful logout, with a `logout` parameter to indicate the logout success.
  - **`/session-expired`**: A page that informs users when their session has expired or is invalidated.

### Duration

#### Overview
In most web applications, the duration of the `JSESSIONID` cookie is not directly communicated to the client, and it is typically managed internally by the server. The `JSESSIONID` cookie itself doesn't usually carry information about its expiration time, and users are generally unaware of the specifics of how long their session will last.

Session cookies, like `JSESSIONID`, typically do not carry a `max-age` or `expiry` attribute. Instead, they last as long as the session is active (i.e. until the browser is closed, unless the session is explicitly invalidated by the server). This behavior is because session cookies are **non-persistent cookies**. They are stored in the session storage of the browser, meaning they only exist during the browser session. When the session ends (i.e. the browser is closed), the cookie is automatically deleted. The browser handles this automatically without needing an explicit expiration time.

When the server sends a `JSESSIONID` cookie without setting a `max-age` or `expiry` time, it becomes a session cookie:

````http
Set-Cookie: JSESSIONID=abc123; Path=/; HttpOnly; Secure; SameSite=Strict
````

In this example:

- The `JSESSIONID` cookie does not include an `expires` or `max-age` attribute, making it a session cookie that will be deleted when the browser is closed.
- The `Secure` flag ensures that the cookie is only sent over HTTPS connections.
- The `SameSite=Strict` attribute ensures that the cookie is sent only in first-party contexts, providing extra security by preventing cross-site request forgery (CSRF) attacks.

In contrast, **persistent cookies** are cookies that have a specified expiration date or maximum age, meaning they remain stored in the user's browser until that time, even if the browser is closed and reopened. These cookies are used when you want to retain user-specific data across sessions, such as login credentials or preferences.

For example, setting a persistent cookie involves explicitly defining an expiration date or time, which is done using the `max-age` or `expires` attributes in the `Set-Cookie` header:

````http
Set-Cookie: username=john_doe; Max-Age=2592000; Path=/; HttpOnly; Secure; SameSite=Strict
````

In this example:

- The `Max-Age` attribute is set to 2592000 seconds (30 days), making this a persistent cookie.
- The cookie will remain in the user's browser for 30 days, even if the user closes and reopens the browser.
- The `Secure` flag ensures it is only sent over HTTPS.
- The `SameSite=Strict` attribute provides additional security by restricting the cookie to first-party contexts only.

#### SessionExpirationFilter
We know that if you set a session timeout in **Spring Security**, the server will automatically invalidate the session after the specified time of inactivity.

To implement a **maximum session length** (not just an inactivity timeout), **Spring Security** doesn’t provide a built-in way to enforce an absolute "maximum session length" (like an expiration after a certain duration regardless of activity). But, you can implement this behavior by managing the session expiration using a **custom filter**.

To achieve a maximum session length, you can create a custom filter that tracks when the session was created and manually invalidates the session after a certain period, independent of inactivity. We will see an approach to add a custom session expiration filter.

**Definition and chaining**

This is an example of a custom filter **definition** and **chaining** for this purpose:

```java
public class SessionExpirationFilter extends OncePerRequestFilter {

    private static final long MAX_SESSION_DURATION = Duration.ofHours(1).toMillis(); // 1 hour max session length
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            Long creationTime = (Long) session.getAttribute("sessionCreationTime");
            if (creationTime == null) {
                creationTime = System.currentTimeMillis();
                session.setAttribute("sessionCreationTime", creationTime);
            }
            
            if (System.currentTimeMillis() - creationTime > MAX_SESSION_DURATION) {
                session.invalidate();  // Invalidate session after max duration
                response.sendRedirect("/session-expired");  // Redirect to session expired page
                return;  // Stop further filter chain processing
            }
        }
        
        filterChain.doFilter(request, response);  // Continue filter chain
    }
}
```

- **Class Declaration:**
    - `SessionExpirationFilter` extends `OncePerRequestFilter`, meaning the filter runs once per HTTP request.

- **Constant Definition:**
    - `MAX_SESSION_DURATION` is set to 1 hour in milliseconds (`Duration.ofHours(1).toMillis()`), defining the maximum session duration.

- **Method `doFilterInternal`:**
    - This method is the core of the filter, called during every HTTP request.

    - **Get the Session:**
      ```java
      HttpSession session = request.getSession(false);
      ```
        - `request.getSession(false)` tries to get the current session without creating a new one.
        - If there's no session, it returns `null`.

    - **Check if Session Exists:**
      ```java
      if (session != null) {
      ```
        - If the session is not `null`, the code continues to handle the session expiration logic.

    - **Check or Set Session Creation Time:**
      ```java
      Long creationTime = (Long) session.getAttribute("sessionCreationTime");
      if (creationTime == null) {
          creationTime = System.currentTimeMillis();
          session.setAttribute("sessionCreationTime", creationTime);
      }
      ```
        - It checks if the session has an attribute `sessionCreationTime`. If it doesn't exist, it sets the creation time to the current system time (`System.currentTimeMillis()`).
        - This attribute is used to track how long the session has been active.

    - **Check for Session Expiration:**
      ```java
      if (System.currentTimeMillis() - creationTime > MAX_SESSION_DURATION) {
      ```
        - It checks if the current time minus the session creation time exceeds the maximum allowed session duration (`MAX_SESSION_DURATION`).
        - If the session has exceeded the duration, it invalidates the session and redirects the user.

    - **Invalidate the Session and Redirect:**
      ```java
      session.invalidate();  // Invalidate session after max duration
      response.sendRedirect("/session-expired");  // Redirect to session expired page
      return;  // Stop further filter chain processing
      ```
        - If the session has expired, it invalidates the session and sends a redirect response to `/session-expired`.
        - The `return` statement ensures that the filter chain does not continue processing further filters or requests after the session is invalidated.

    - **Continue the Filter Chain:**
      ```java
      filterChain.doFilter(request, response);
      ```
        - If the session is still valid, the filter chain proceeds to the next filter or the target resource.

**Registration**

Then you need to perform the **registration** of this custom filter in your `SecurityConfig` `@Configuration` class.

When you want to implement **custom logic in session management** (such as session expiration), it’s crucial to control **when** the custom logic is executed relative to **authentication**.

We need to know that `UsernamePasswordAuthenticationFilter` is a Spring Security filter responsible for handling the process of authenticating a user based on their username and password. It plays a crucial role in HTTP-based authentication, particularly when the user logs in with their credentials, such as through form-based or basic authentication. Although you don't explicitly define this filter in your configuration, Spring Security automatically registers it in the security filter chain by default when using basic or form-based authentication. This filter ensures that user credentials are processed correctly during the authentication process, allowing Spring Security to authenticate the user successfully.

Having said that, in this case, using `addFilterBefore()` you add the **custom filter** before the **`UsernamePasswordAuthenticationFilter`** in the filter chain to ensure that session expiration is checked **before authentication** takes place. By doing this, your custom filter will be executed **first**. It will check whether the session has expired, and if it has, it will invalidate the session and redirect the user before any authentication attempts are made:

````java
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .addFilterBefore(new SessionExpirationFilter(), UsernamePasswordAuthenticationFilter.class)  // Add SessionExpirationFilter before authentication filter
            .authorizeRequests()
                .antMatchers("/public/**", "/session-expired", "/login").permitAll()  // Allow unauthenticated access to /public/**, /session-expired, and /login
                .anyRequest().authenticated()  // All other requests require authentication
            .and()
            .httpBasic()
            .and()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)  // Create session only if required
                .sessionTimeout(Duration.ofMinutes(30))  // Set session timeout (inactivity timeout) to 30 minutes
                .invalidSessionUrl("/session-expired")  // Redirect if session is invalid due to inactivity
                .maximumSessions(1)  // Optional: Limit concurrent sessions per user
                .expiredUrl("/session-expired")  // Redirect if session expires
            .and()
            .logout()
                .logoutUrl("/logout")  // URL that triggers the logout process
                .logoutSuccessUrl("/login?logout")  // Redirect URL after successful logout
                .invalidateHttpSession(true)  // Invalidate the session when logging out
                .clearAuthentication(true)  // Clear authentication data on logout
                .deleteCookies("JSESSIONID")  // Delete session cookies (e.g. JSESSIONID)
                .permitAll();  // Allow all users to access the logout URL
    }
````

- **Adding the `SessionExpirationFilter` Before the `UsernamePasswordAuthenticationFilter`**
    - The custom `SessionExpirationFilter` is added before the default `UsernamePasswordAuthenticationFilter` in the filter chain.
    - This ensures that the session expiration is checked before the user is authenticated.
    - If the session is expired, the filter will invalidate the session and redirect the user to the `/session-expired` page.

- **Authorization Rules**
    - `.authorizeRequests()` starts the section for defining access control rules for specific URLs.
    - `.antMatchers("/public/**", "/session-expired", "/login").permitAll()` allows **unauthenticated** access to `/public/**`, `/session-expired`, and `/login` URLs.
    - `.anyRequest().authenticated()` requires authentication for **any other request** that is not explicitly allowed.

- **HTTP Basic Authentication**
    - `.httpBasic()` enables **basic HTTP authentication** for the application.
    - This allows users to authenticate by sending a username and password directly in the request header.

- **Session Management Configuration**
    - `.sessionManagement()` configures session behavior:
        - `.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)` means the session is created only when needed (i.e. when the user is authenticated).
        - `.sessionTimeout(Duration.ofMinutes(30))` sets the session timeout to 30 minutes of inactivity.
        - `.invalidSessionUrl("/session-expired")` redirects to the `/session-expired` URL if the session is invalidated.
        - `.maximumSessions(1)` limits the user to only one concurrent session.
        - `.expiredUrl("/session-expired")` redirects to `/session-expired` if the session expires.

- **Logout Configuration**
    - `.logout()` configures the logout process:
        - `.logoutUrl("/logout")` specifies the URL that triggers the logout.
        - `.logoutSuccessUrl("/login?logout")` redirects the user to the login page with a logout query parameter after successful logout.
        - `.invalidateHttpSession(true)` invalidates the HTTP session on logout.
        - `.clearAuthentication(true)` clears authentication data on logout.
        - `.deleteCookies("JSESSIONID")` deletes the `JSESSIONID` cookie, which is used to identify the session.
        - `.permitAll()` ensures that the logout URL is accessible by everyone, even unauthenticated users.

---

## CSRF protection

### Attack overview
Cross-Site Request Forgery (CSRF) is an attack where an attacker tricks an authenticated user into performing an unwanted action on a web application. The attacker exploits the trust the web application has in the user's browser.

**CSRF** is primarily a concern for **stateful, session-based** applications, where the server maintains session data and identifies users based on session IDs stored in **cookies**. In these applications, browsers automatically send cookies with each request to the same domain, which can be exploited by attackers. For instance, an attacker could trick a user into making a malicious request, and the browser would automatically send the session cookie with that request, making it appear legitimate to the server.

However, **CSRF** is *not* generally a concern for **stateless applications**, such as those using **JWT** (JSON Web Tokens) for authentication. In these applications, the token is typically passed explicitly in the HTTP headers (e.g. in the `Authorization: Bearer <token>` header), *not* as cookies. Since tokens are not automatically included in requests by the browser, attackers cannot trigger requests that would automatically include the token. As a result, stateless **JWT-based** applications are less vulnerable to CSRF.

That said, **CSRF** can still be a problem in stateless applications under certain conditions. If the **JWT** is stored in **cookies** (instead of being passed in headers), it could be vulnerable to **CSRF**, as cookies would be sent automatically with cross-site requests. Additionally, if tokens are stored insecurely in places like *localStorage* or *sessionStorage*, they could be exposed through **XSS** (Cross-Site Scripting) attacks, potentially allowing attackers to use the token in malicious requests, which could resemble **CSRF** attacks.

In summary, while **CSRF** is mostly a concern for **session-based applications**, it can still be a risk for stateless **JWT-based** applications if the token is improperly stored in **cookies** or exposed through other vulnerabilities like **XSS**. Therefore, while **CSRF** is less of an issue in stateless systems, proper token storage and handling are still essential to maintain security.

#### Unfolding
1. User Login and Session Cookies:
   - The user logs into a web application and is authenticated.
   - The server issues a session cookie (e.g. `JSESSIONID`), which identifies the user's session.
   - The browser automatically includes this session cookie with every request to the server.

2. Attacker's Malicious Website:
   - The attacker creates a malicious website that tricks the user into visiting it while logged into the vulnerable site.
   - The attacker injects JavaScript into the malicious website that automatically sends a state-changing request to the vulnerable web application.
   - The malicious site sends a state-changing request (e.g. POST, PUT, DELETE) to the vulnerable web application. The browser includes the session cookie with the request, making it seem legitimate.

3. Outcome:
   - The attacker successfully performs actions that the user didn’t intend by exploiting the user’s session.

#### SameSite Cookie Policy
The **SameSite=Strict** attribute ensures that the cookie is **only sent** when the request originates from the same domain as the cookie. This means the cookie **will not be sent** with **cross-site requests**, even if the user navigates to the site from a different domain or performs an action such as clicking a link from another site.

This security feature helps to prevent **Cross-Site Request Forgery (CSRF)** attacks by ensuring that **cookies** (such as session cookies) are **not automatically sent** with requests made from a different origin (i.e. another website).

```http
HTTP/1.1 200 OK
Set-Cookie: jsessionid=abc123; SameSite=Strict; Path=/; HttpOnly; Secure
```

#### Anti-CSRF token
Anti-CSRF tokens are **unique, randomly generated tokens** that are associated with a user's session.

These tokens are **not stored in cookies** but are usually exchanged:
- within the **HTTP body**;
- **custom HTTP headers** (e.g. `X-CSRF-Token` or `X-XSRF-Token`).

The server validates these tokens to ensure that the request originates from the legitimate user and not from a malicious website.

The primary goal of Anti-CSRF tokens is to protect against **Cross-Site Request Forgery (CSRF)** attacks.

Anti-CSRF tokens are **not stored in cookies** to prevent attackers from exploiting the token via **cross-site scripting (XSS)** attacks. Cookies are automatically sent with every request, which could lead to potential token theft or manipulation. By requiring the client to programmatically include the token in the request body or custom headers (e.g. `X-CSRF-Token` or `X-XSRF-Token`), the server ensures that the request is intentional and legitimate.

Anti-CSRF tokens provide an **additional layer of protection** by ensuring that requests made to the server must contain a valid token that matches what the server expects.

- Even if an attacker manages to **bypass the SameSite cookie restrictions** (for example, by tricking the user into making a request via a **malicious link**), the request will still be **rejected**.
- This happens because the attacker does not have access to the **correct CSRF token**, which is required to make a valid request.

Thus, Anti-CSRF tokens work alongside `SameSite` cookie settings, ensuring that **only legitimate requests** are processed, even in cases where `SameSite` protections are insufficient.

**As a Custom Header:**

1. **Token Generation**:
   - Upon session creation, the server generates a unique Anti-CSRF token and associates it with the user session (stored server-side).
   - The server sends the token to the client, typically as part of the initial HTTP response in a custom response header (e.g. `X-CSRF-Token` or `X-XSRF-Token`).

2. **Response example with anti-CSRF token**:
   - **Content-Type: application/json**: Specifies that the response body contains JSON data, ensuring proper handling and parsing.
   - **Cache-Control: no-cache**: Instructs the browser or intermediary caches that the response should not be cached, preventing potential attacks using stale tokens.
   - **Pragma: no-cache**: An older HTTP/1.0 header similar to `Cache-Control: no-cache`, included for backward compatibility.

```http
HTTP/1.1 200 OK
Content-Type: application/json
Cache-Control: no-cache
Pragma: no-cache
X-CSRF-Token: abc123xyz456  // The Anti-CSRF token sent to the client

{
    "status": "success",
    "message": "CSRF token generated successfully"
}
```

3. **Client storage**
   - The token is then stored client-side for future use.
   - The client is responsible for programmatically including the token in each request that requires authentication.
   - The token is added to the `X-CSRF-Token` header for API requests.

4. **Request example with anti-CSRF token**:

```http
POST /protected-resource HTTP/1.1
Host: example.com
Content-Type: application/json
X-CSRF-Token: abc123xyz456  // The CSRF token sent in the header

{
    "someData": "example"
}
```

5. **Token Validation**:
   - When the server receives the request, it verifies that the Anti-CSRF token provided by the client in the `X-CSRF-Token` header matches the one stored for the current session.
   - If the token in the request does not match the server-side token, the server will reject the request as it may have originated from a malicious source.
   - If the tokens match, the request is considered legitimate, and the server processes it.

**Within the HTTP Body:**

1. **Token Generation**:
    - Upon session creation, the server generates a unique Anti-CSRF token and associates it with the user session (usually stored server-side).
    - The server sends the token to the client, typically as part of the **response body** (instead of a custom header).

2. **Response example with Anti-CSRF token in the body**:
    - **Content-Type: application/json**: Specifies that the response body contains JSON data, ensuring proper handling and parsing.
    - **Cache-Control: no-cache**: Instructs the browser or intermediary caches not to cache the response, preventing attacks using stale tokens.
    - **Pragma: no-cache**: Similar to `Cache-Control: no-cache` for backward compatibility with HTTP/1.0.

```http
HTTP/1.1 200 OK
Content-Type: application/json
Cache-Control: no-cache
Pragma: no-cache

{
    "status": "success",
    "message": "CSRF token generated successfully",
    "csrfToken": "abc123xyz456"
}
```

3. **Client storage**:
    - The client stores the token locally (e.g. in memory) for future use.
    - The client is responsible for programmatically including the token in each request that requires authentication.
    - The token is typically added to the `X-CSRF-Token` header for API requests.

4. **Request example with Anti-CSRF token**:

```http
POST /protected-resource HTTP/1.1
Host: example.com
Content-Type: application/json
X-CSRF-Token: abc123xyz456  // The CSRF token sent in the request header

{
    "someData": "example"
}
```

5. **Token Validation**:
    - When the server receives the request, it checks that the Anti-CSRF token in the `X-CSRF-Token` header matches the token associated with the current session.
    - If the token in the request doesn't match the server-side token, the server rejects the request as potentially originating from a malicious source (e.g. a CSRF attack).
    - If the tokens match, the server considers the request legitimate and processes it accordingly, allowing the requested action to proceed (e.g. updating a resource, submitting a form).

### Anti-CSRF in Spring Security
In a stateful application, such as one using Spring Security with session management, authentication-related data is tied to an active session. This session is crucial for tracking user state across multiple requests. However, this introduces the risk of Cross-Site Request Forgery (CSRF) attacks, where a malicious actor can trick an authenticated user into performing unintended actions.

#### Default Token Exchange
By default, Spring Security provides protection against CSRF attacks to ensure that state-changing requests (like POST, PUT, and DELETE) are valid and come from the authenticated user. CSRF protection involves the use of an anti-CSRF token, which is generated per session and included in state-changing requests.

When a session is active, Spring Security checks whether the request contains the correct CSRF token, which ensures that the request is indeed initiated by the user and not an attacker.

Spring Security automatically manages session creation and CSRF protection. The anti-CSRF token is generated for each session, and the session's validity is checked during state-changing requests.

```java
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .addFilterBefore(new SessionExpirationFilter(), UsernamePasswordAuthenticationFilter.class)  // Add SessionExpirationFilter before authentication filter
            .authorizeRequests()
                .antMatchers("/public/**", "/session-expired", "/login").permitAll()  // Allow unauthenticated access to /public/**, /session-expired, and /login
                .anyRequest().authenticated()  // All other requests require authentication
            .and()
            .httpBasic()
            .and()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)  // Create session only if required
                .sessionTimeout(Duration.ofMinutes(30))  // Set session timeout (inactivity timeout) to 30 minutes
                .invalidSessionUrl("/session-expired")  // Redirect if session is invalid due to inactivity
                .maximumSessions(1)  // Optional: Limit concurrent sessions per user
                .expiredUrl("/session-expired")  // Redirect if session expires
            .and()
            .logout()
                .logoutUrl("/logout")  // URL that triggers the logout process
                .logoutSuccessUrl("/login?logout")  // Redirect URL after successful logout
                .invalidateHttpSession(true)  // Invalidate the session when logging out
                .clearAuthentication(true)  // Clear authentication data on logout
                .deleteCookies("JSESSIONID")  // Delete session cookies (e.g. JSESSIONID)
                .permitAll();  // Allow all users to access the logout URL
    }
```

In this configuration, CSRF protection is enabled by default. The anti-CSRF token is linked to the user's session. When a state-changing request (such as a POST, PUT, or DELETE) is made, the anti-CSRF token must be validated to ensure the request is legitimate.

For **HTML forms**, Spring Security automatically handles the CSRF token for you. When your application returns an HTML form in a response, Spring Security will inject the CSRF token into the form as a hidden field. This means that you don’t need to manually add the token to the form.

Whenever a form is submitted (for example, a POST request), the CSRF token that was automatically added to the form will be sent back to the server. Spring Security will then validate this token to ensure the request is legitimate and originates from the authenticated user.

This automatic handling is done seamlessly by Spring Security, and you don’t have to worry about adding the token yourself. All state-changing requests (like POST, PUT, DELETE) will have the CSRF token included, as it’s automatically injected into the form by the security framework when the page is served.

```html
<form method="POST" action="/submit">
    <input type="hidden" name="_csrf" value="your-csrf-token-here" />
    <button type="submit">Submit</button>
</form>
```

For **REST APIs with JSON responses**, Spring Security automatically handles the CSRF token by including it in the HTTP response headers. When a state-changing request (e.g. POST, PUT, DELETE) is made, Spring Security will include the CSRF token in the `X-CSRF-TOKEN` HTTP header in the response.

The client is expected to send this token back with any state-changing requests in the `X-CSRF-TOKEN` header. Spring Security will then validate this token to ensure the request is legitimate and originates from the authenticated user.

This behavior is automatic, and developers do not need to manually include the CSRF token in each request. Spring Security ensures that any state-changing requests that require CSRF protection are validated by including the token in the response headers and expecting it to be returned in the request headers.

```http
HTTP/1.1 200 OK
X-CSRF-TOKEN: your-csrf-token-here
Content-Type: application/json
{
"message": "Request successful"
}
```

#### Customisation
To explicitly enable CSRF protection in Spring Security, you can configure it within your `HttpSecurity` configuration. By default, CSRF protection is enabled when session management is in use. However, you can explicitly configure it for additional customization.

Here’s how you can enable CSRF protection:

```java
        ...........
        .logout()
            .logoutUrl("/logout")  // URL that triggers the logout process
            .logoutSuccessUrl("/login?logout")  // Redirect URL after successful logout
            .invalidateHttpSession(true)  // Invalidate the session when logging out
            .clearAuthentication(true)  // Clear authentication data on logout
            .deleteCookies("JSESSIONID")  // Delete session cookies (e.g. JSESSIONID)
            .permitAll()  // Allow all users to access the logout URL
        .and()
        .csrf()  // Explicitly enable CSRF protection
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());  // Customizing token storage to cookies (not recommended due to security risks)
}
```

- **Customizing Token Storage:** The `.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())` stores the CSRF token in a cookie rather than in the session. The cookie is marked with the `HttpOnly` flag set to `false`, meaning it can be accessed by JavaScript running in the browser. This approach is often used for single-page applications (SPAs) or situations where the CSRF token needs to be shared across multiple requests.
- **Important:** Storing the CSRF token in cookies exposes you to XSS (Cross-Site Scripting) risks, as malicious scripts can potentially steal tokens from the cookie. As a result, this customization is generally not recommended unless you have additional security measures in place, such as Content Security Policy (CSP) and XSS protection. It's safer to stick to the default behavior of storing the CSRF token in the session.

#### Disable
In cases where you're working with a stateless application (e.g. using JWT or token-based authentication), you might want to disable CSRF protection since sessions are not used and thus no anti-CSRF token is needed.

In stateless applications, such as those using **JWT** or other **token-based authentication** methods, you may want to disable **CSRF protection**. This is because, in a stateless application, no session is maintained for tracking user state, and CSRF tokens are not required.

When you configure **SessionCreationPolicy** to `STATELESS`, Spring Security will not create or manage sessions. Since CSRF protection is designed to work with sessions (using an anti-CSRF token stored in the session), it becomes unnecessary in stateless applications. Therefore, you can disable CSRF protection explicitly using the `csrf().disable()` method.

Disabling CSRF protection is appropriate in scenarios such as:
- The application is stateless, meaning it does not rely on sessions.
- Authentication is handled via tokens (e.g. JWT) passed in headers or other parts of the request.
- CSRF protection is irrelevant because there is no session to secure.

To disable CSRF protection, simply use the `csrf().disable()` method in your Spring Security configuration. By doing so, Spring Security will skip CSRF checks for all incoming requests, which is essential for applications that do not use sessions for tracking user state.

This approach ensures that requests can be authenticated based on tokens, without the need for session-based protections. However, ensure that your token management (e.g. validating JWT tokens on every request) is secure, as CSRF protection will not be in place.

```java
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .addFilterBefore(new SessionExpirationFilter(), UsernamePasswordAuthenticationFilter.class)  // Add SessionExpirationFilter before authentication filter
            .authorizeRequests()
                .antMatchers("/public/**", "/session-expired", "/login").permitAll()  // Allow unauthenticated access to /public/**, /session-expired, and /login
                .anyRequest().authenticated()  // All other requests require authentication
            .and()
            .httpBasic()
            .and()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)  // Create session only if required
                .sessionTimeout(Duration.ofMinutes(30))  // Set session timeout (inactivity timeout) to 30 minutes
                .invalidSessionUrl("/session-expired")  // Redirect if session is invalid due to inactivity
                .maximumSessions(1)  // Optional: Limit concurrent sessions per user
                .expiredUrl("/session-expired")  // Redirect if session expires
            .and()
            .csrf().disable()
            .logout()
                .logoutUrl("/logout")  // URL that triggers the logout process
                .logoutSuccessUrl("/login?logout")  // Redirect URL after successful logout
                .invalidateHttpSession(true)  // Invalidate the session when logging out
                .clearAuthentication(true)  // Clear authentication data on logout
                .deleteCookies("JSESSIONID")  // Delete session cookies (e.g. JSESSIONID)
                .permitAll();  // Allow all users to access the logout URL
    }
```

#### Final considerations
- **Session Expiration:** When the session expires (due to inactivity, for example), the anti-CSRF token tied to that session is also invalidated. If the user tries to make a state-changing request after their session expires, the request will be rejected with a `403 Forbidden` response, as the CSRF token is no longer valid.
- **Logout:** When the user logs out, the session is invalidated. This also invalidates the anti-CSRF token. If the user attempts to submit a request after logging out, it will be rejected because the session and the associated anti-CSRF token no longer exist.
- **Avoid Storing CSRF Tokens in Cookies:** The anti-CSRF token should never be stored in cookies. If malicious JavaScript (e.g. via XSS) can access the cookie, the attacker may steal the token and bypass CSRF protection.
- **Using HTTP Headers for CSRF Tokens:** It's safer to include the anti-CSRF token in HTTP headers (`X-CSRF-TOKEN`) for AJAX requests or in form fields for traditional form submissions. This ensures the token is not exposed to JavaScript, reducing the risk of XSS.
- You should disable CSRF protection in cases where:
  - The application is stateless (using JWT or OAuth2 tokens).
  - There's no session management, and token handling is done client-side.
- For traditional web applications, where sessions are used, CSRF protection should remain enabled.

---

## Authentication-related vs Non-Authentication Session Data

In Spring Security, the session is crucial for managing the user’s authentication state across multiple requests. This is important because HTTP is inherently stateless, meaning each request is independent and doesn’t carry context from previous ones. Without sessions, users would need to authenticate on every request. However, it's important to distinguish between authentication-related session data and non-authentication session data, as they may need different handling.

### Authentication-related session data
In Spring Security, a session is primarily used to manage user authentication. When a user logs in, Spring Security creates a server-side session that holds important details related to authentication, such as:

- Authentication status (whether the user is logged in or not)
- Roles and permissions assigned to the user
- User-specific data, like the user ID, necessary to maintain their authenticated session

This session is associated with a unique identifier, typically stored in a cookie called `JSESSIONID`. This identifier is sent with each request, ensuring that the user’s authentication state is preserved across interactions.

**Important note:** The `JSESSIONID` is used specifically to manage authentication in a session. When a user makes multiple requests, the session ID is sent with each request to maintain the user’s authentication state.

### Non-authentication session data
Non-authentication session data refers to other types of data that may be stored within the session, unrelated to the user’s authentication status. For example, consider a shopping cart:

Once a user logs in, the session can store information like the items in their shopping cart, user preferences, or any other data necessary for the application's functionality.

Although Spring Security handles the authentication-related session data, non-authentication data can also be stored in the same session. This means that the same session, identified by a `JSESSIONID`, can hold both authentication-related data (e.g. user roles) and non-authentication-related data (e.g. shopping cart items).

Here’s how you can add non-authentication data, like a shopping cart, to a session in a Spring Boot application:

```java
import javax.servlet.http.HttpSession;

@Controller
public class ShoppingCartController {

    @PostMapping("/add-to-cart")
    public String addToCart(HttpSession session, @RequestParam String item) {
        // Get the shopping cart from the session, or create a new one if it doesn't exist
        List<String> cart = (List<String>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }
        
        // Add the new item to the cart
        cart.add(item);
        
        // Store the updated cart back in the session
        session.setAttribute("cart", cart);
        
        return "cart";
    }
}
```

In this example, we’re storing a shopping cart in the session using the `HttpSession` object. The `cart` attribute is added to the session, which is a non-authentication-related piece of data. This cart can persist across requests using the same `JSESSIONID` used for authentication.

### Session-less authentication vs. Session-based authentication
Although Spring Security typically uses session-based authentication, you can also implement stateless authentication mechanisms, such as JSON Web Tokens (JWTs). With JWT-based authentication, the user's authentication state is stored within the token, and no server-side session is needed.

However, even with stateless authentication, you may still use sessions to store non-authentication data, like a shopping cart or user preferences. This means your application can be stateless for authentication while remaining stateful for other data.

### CSRF Protection and Session-based authentication
For session-based authentication, it’s critical to implement anti-CSRF.

CSRF protection helps secure stateful sessions by preventing unauthorized requests made on behalf of an authenticated user.

In contrast, when using stateless authentication (e.g. JWT), anti-CSRF protection is generally unnecessary because the authentication state is stored within the token itself, not in a session.

**CSRF protection is only relevant when you’re dealing with session-based authentication mechanisms, as it prevents attackers from exploiting session cookies (`JSESSIONID`) or other session identifiers.**

---



