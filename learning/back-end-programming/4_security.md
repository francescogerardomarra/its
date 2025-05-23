# Spring Security
Spring Security is a comprehensive framework for securing Java applications, especially those built with Spring. It provides a wide array of functionalities like authentication, authorization, protection against common vulnerabilities (e.g.: CSRF), and integrates easily with both web and enterprise applications.

---

## pom.xml
**Spring Boot** provides a set of **starter** POMs that group related dependencies together for ease of use. These starter POMs allow you to import a whole set of dependencies that are commonly used together.

**Spring Boot starters** simplify dependency management by providing predefined sets of commonly used libraries for specific functionalities (e.g. `spring-boot-starter-web` for web applications, `spring-boot-starter-data-jpa` for JPA, etc.).

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

With the release of **Spring Security 6.x**, significant changes have been made compared to the earlier **Spring Security 5.x**. The main differences between the two versions are:

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
    - If the authentication is successful (i.e. the username and encoded password match), the user is granted access to the requested resource.

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
            - Links usernames to roles (e.g. ADMIN, USER, GUEST).

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
        - Associates each username with an authority (e.g. ADMIN, USER).

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

- **Step 15**: This process repeats for each HTTP request. Even if the user has been authenticated once, the server will not remember the authentication for subsequent requests unless another mechanism (e.g. session management or tokens) is used.

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

- If authentication and role checks pass, the request proceeds to the corresponding handler (e.g. a controller method).

- If authentication or role checks fail, an HTTP 401 (Unauthorized) or 403 (Forbidden) response is returned.

- This setup ensures that only authenticated and authorized users can access specific parts of the application based on their roles.

---

## Form-Based Authentication
**Form-based authentication** is a widely used method for authenticating users in web applications, especially when a **better user experience** is desired compared to **Basic Authentication**.

When a user attempts to access a protected resource without being authenticated, the server responds by redirecting the client to a **login page**, where the user can input their **username** and **password**. This approach separates the login process from other interactions, unlike Basic Authentication, where credentials are sent with each request.

Once the user is authenticated, the server issues a **session cookie** (commonly `JSESSIONID`), which the client includes in subsequent requests to prove its identity. This allows the user to stay authenticated without resubmitting credentials for every request.

Unlike Basic Authentication, which is **stateless** and requires credentials with each request, **form-based authentication** uses **HTTP cookies** to maintain the user's authenticated state across multiple requests, making the experience more seamless.

By default, Spring Security provides a pre-built login page if none is specified.

Here’s a basic configuration:

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
      http
          .authorizeRequests()
              .antMatchers("/public/**").permitAll() // Allow unauthenticated access to /public/**
              .anyRequest().authenticated() // All other requests require authentication
          .and()
          .formLogin()
              .permitAll(); // Explicitly allow unauthenticated access to the login page
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  @Bean
  public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    manager.createUser(User.withUsername("user")
            .password(passwordEncoder.encode("password"))
            .build());
    manager.createUser(User.withUsername("admin")
            .password(passwordEncoder.encode("admin"))
            .build());
    return manager;
  }
}
```

With this configuration, if a user tries to access a protected resource, Spring Security will automatically redirect them to a default login page. That is, if you don't configure anything special, Spring Security automatically provides a simple login page at `/login`.

In the following example:

- When the user tries to access a protected resource (e.g. `/protected-resource`), Spring Security will first check if the user is authenticated.
- Since the user is not authenticated, Spring Security will create a new HTTP session and generate a `JSESSIONID` cookie, which is sent to the client.
- **Important**: The initial session is **not tied to any authenticated user**. It is created solely to facilitate future redirection (i.e. the "saved request" for the user's original destination). This session is not used for authentication but for preserving the user’s destination until they log in.
- The user will be redirected to the login page (`/login`).
- The client (browser) will be redirected to the default login page (`/login`).
- The `JSESSIONID` cookie will be included in the request to ensure the session is maintained across the redirection, even though the user is not yet authenticated.
- Spring Security automatically generates a login form for the user.
- The form will typically have fields for the username and password, and the user is expected to enter their credentials.
- Once the user submits the login form with their credentials (e.g. username `user`, password `password`), a `POST` request is sent back to the server.
- The `JSESSIONID` cookie is included in this request to maintain the session while processing the authentication.
- After the server validates the user's credentials, Spring Security will authenticate the user and store the authentication information in the session (`SecurityContext`).
- To protect against session fixation attacks, by default the old session is invalidated, and a new session with a new `JSESSIONID` is created.
- **Session fixation** is a security vulnerability where an attacker tricks a user into using a known session ID; Spring Security enables "session fixation protection" by default.
- The session (`JSESSIONID`) is now associated with the authenticated user's information.
- The user will then be redirected to the originally requested protected resource (e.g. `/protected-resource`).
- On subsequent requests, the client sends the new `JSESSIONID` cookie to prove that the session exists and the user is authenticated.
- Spring Security uses the session to confirm the user's authentication, granting access to the protected resource.

***

````plaintext
GET /protected-resource HTTP/1.1
Host: example.com
````

````http
HTTP/1.1 302 Found
Location: /login
Set-Cookie: JSESSIONID=abcd1234
````

***

````plaintext
GET /login HTTP/1.1
Host: example.com
Cookie: JSESSIONID=abcd1234
````

````html
<!-- Default HTML Form provided by Spring Security -->
<form method="POST" action="/login">
    <input type="text" name="username">
    <input type="password" name="password">
    <button type="submit">Login</button>
</form>
````

***

````plaintext
POST /login HTTP/1.1
Host: example.com
Content-Type: application/x-www-form-urlencoded
Content-Length: 31
Cookie: JSESSIONID=abcd1234

username=user&password=password
````

````http
HTTP/1.1 302 Found
Location: /protected-resource
Set-Cookie: JSESSIONID=newSessionId1234; HttpOnly; Secure; SameSite=Strict
````

***

````plaintext
GET /protected-resource HTTP/1.1
Host: example.com
Cookie: JSESSIONID=newSessionId1234
````

````http
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 29

{
"message": "Access granted"
}
````

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
Content-Length: 29

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
Content-Length: 29

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
Content-Length: 29

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
        .formLogin()
            .permitAll()                          // Explicitly allow unauthenticated access to the login page
        .and()
        .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);  // Create a session if required
}
````

where we simply replaced `httpBasic() ` with `formLogin()`.

As result:

- form-based authentication is enabled;
- when users try to access a protected resource without being authenticated, they will be **redirected to a default login form**;
- this form prompts for the username and password to be submitted through an HTML-form.

For **form-based authentication**, the session will be created because it is deemed **required**.

In the following example:

- **Three HTTP requests and responses** are exchanged, starting with a **GET request** to access a protected resource, followed by a **POST request** to the login page, and finally, a request to access the protected resource again.
- The first request is a **GET request** to the protected resource (`/protected-resource`), which redirects the user to the login page (`/login`) as they are not authenticated. At this point, the server creates an initial session for future redirection and sets a simple session cookie (`JSESSIONID=abcd1234`).
- The second request is a **GET request** to the login page (`/login`). The browser sends the `JSESSIONID=abcd1234` cookie along with the request to maintain the session, though it's not authenticated yet. The login page is served with a form for the user to input their credentials.
- The user submits their credentials (e.g. `username=user`, `password=password`) via the login form. This triggers a **POST request** to the server to authenticate the user.
- The server processes the credentials, and upon successful authentication, responds with a **302 redirect** back to the originally requested protected resource (`/protected-resource`). The server also issues a new session cookie (`JSESSIONID=abcd5678; HttpOnly; Secure`) to replace the initial one, which now contains the authenticated user’s session details.
- The third request sends the **new session cookie** (`JSESSIONID=abcd5678`) along with the request to the protected resource, confirming the user is authenticated. The server grants access to the protected resource and responds with the requested content.

***

````plaintext
GET /protected-resource HTTP/1.1
Host: example.com
````

````http
HTTP/1.1 302 Found
Location: /login
Set-Cookie: JSESSIONID=abcd1234; Path=/
````

***

````plaintext
GET /login HTTP/1.1
Host: example.com
Cookie: JSESSIONID=abcd1234
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
Content-Length: 31
Cookie: JSESSIONID=abcd1234

username=user&password=password
````

````http
HTTP/1.1 302 Found
Location: /protected-resource
Set-Cookie: JSESSIONID=abcd5678; Path=/; HttpOnly; Secure; SameSite=Strict
````

***

````plaintext
GET /protected-resource HTTP/1.1
Host: example.com
Cookie: JSESSIONID=abcd5678
````

````http
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 31

{
"message": "Access granted"
}
````

***

#### `SessionCreationPolicy.ALWAYS`
When using `SessionCreationPolicy.ALWAYS`, Spring Security guarantees that a session will be created if one doesn't already exist. If the client sends a valid session cookie (e.g. `JSESSIONID`), the session will be reused. However, if no session exists, one will be created.

This ensures that authentication-related sessions are always created, even for stateless authentication mechanisms like **Basic Authentication**.

- **Session Creation**: A session is created when there is no existing session cookie (`JSESSIONID`).
- **Session Reuse**: If the client sends a valid `JSESSIONID` cookie, the existing session will be reused without creating a new one.
- **Stateless Authentication**: Even though mechanisms like Basic Authentication are stateless, Spring Security ensures that a session will be created.

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
Content-Length: 31
Set-Cookie: JSESSIONID=abcd1234; Path=/; Secure; HttpOnly; SameSite=Strict

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
Content-Length: 31

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
Content-Length: 31

{
"message": "Access granted"
}
````

***

#### `SessionCreationPolicy.NEVER`

When using `SessionCreationPolicy.NEVER`, Spring Security will **never** create a session, but it **will use** an existing session **if one already exists**.

This policy is useful when you want to integrate with an existing session management system but don't want Spring Security to initiate new sessions on its own.

- **No Session Creation**: Spring Security will not create a session.
- **Session Reuse**: If the client sends a valid `JSESSIONID` cookie, Spring Security will use the existing session.
- **Authentication Requirements**: Without an existing session, credentials must be provided with each request.

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

In the following example:

- three HTTP requests and responses are exchanged with `SessionCreationPolicy.NEVER`.
- the first request sends credentials in the `Authorization` header.
- the server authenticates the user, processes the request, but **does not create a session**.
- the second request does not send credentials and fails with **401 Unauthorized**.
- the third request includes a valid `JSESSIONID` cookie from a previously existing session, allowing access without resending credentials.

***

````plaintext
GET /protected-resource HTTP/1.1
Host: example.com
Authorization: Basic dXNlcjpwYXNzd29yZA==
````

````http
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 31

{
"message": "Access granted"
}
````

***

````plaintext
GET /protected-resource HTTP/1.1
Host: example.com
````

````http
HTTP/1.1 401 Unauthorized
Content-Length: 0
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
Content-Length: 31

{
"message": "Access granted"
}
````

***

With `NEVER`, Spring Security will not create a new session, but if a session already exists, it will be reused for authentication and authorization.

#### `SessionCreationPolicy.STATELESS`

When using `SessionCreationPolicy.STATELESS`, Spring Security will **neither create** nor **use** a session.

Every request must contain all the necessary authentication information (e.g. credentials), because Spring Security will **not** rely on any session state. Even if the client sends a valid session cookie (like `JSESSIONID`), Spring Security will ignore it.

- **No Session Creation**: Spring Security will not create a session.
- **No Session Reuse**: Spring Security will not use an existing session.
- **Authentication Requirements**: Every request must be independently authenticated.

So if the security filter chain is as follows:

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

there will be no session-reuse and no session-creation.

In the following example:

- three HTTP requests and responses are exchanged with `SessionCreationPolicy.STATELESS`.
- the first request sends credentials in the `Authorization` header.
- the server authenticates the user and processes the request.
- the second request does not send credentials and fails with **401 Unauthorized**.
- the third request sends a `JSESSIONID` cookie from a previously existing session, but Spring Security **ignores** it and requires credentials again.

***

````plaintext
GET /protected-resource HTTP/1.1
Host: example.com
Authorization: Basic dXNlcjpwYXNzd29yZA==
````

````http
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 31

{
"message": "Access granted"
}
````

***

````plaintext
GET /protected-resource HTTP/1.1
Host: example.com
````

````http
HTTP/1.1 401 Unauthorized
Content-Length: 0
````

***

````plaintext
GET /protected-resource HTTP/1.1
Host: example.com
Cookie: JSESSIONID=abcd1234
````

````http
HTTP/1.1 401 Unauthorized
Content-Length: 0
````

***

With `STATELESS`, Spring Security ignores any session (even valid `JSESSIONID` cookies) and always expects credentials with every request.

### Timeout
Session timeout determines how long a session remains active before expiring due to **inactivity**. This refers to the amount of time a session can remain **idle** (without user activity or requests) before it is **invalidated**.

For example, if an application has an **inactivity timeout** of **30 minutes**, and the user doesn’t make any requests within that time, the session will **expire** and the user will be logged out.

In Spring Security, when using `SessionCreationPolicy.IF_REQUIRED` along with **form-based login**, a session is guaranteed to be created upon successful authentication. Then we can configure various behaviors related to session timeout as follows:

```java
@Override
protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
            .antMatchers("/public/**", "/session-expired").permitAll() // Allow unauthenticated access to /public/** and /session-expired
            .anyRequest().authenticated() // All other requests require authentication
        .and()
        .formLogin()
            .permitAll() // Explicitly allow unauthenticated access to the login page
        .and()
        .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Create session only if required (will happen after form login)
            .sessionTimeout(Duration.ofMinutes(30)) // Set session timeout (inactivity timeout) to 30 minutes
            .invalidSessionUrl("/session-expired") // Redirect if session is invalid due to inactivity
            .maximumSessions(1) // Limit concurrent sessions per user
            .expiredUrl("/session-expired"); // Redirect if session expires
}
```

- **`sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)`**

    - Defines when Spring Security should create a session.

    - With **formLogin()**, a session **is created** after successful authentication.

    - **`SessionCreationPolicy.IF_REQUIRED`** ensures a session is created **only when necessary**, and since form login requires storing authentication data, a session will be created.

- **`sessionTimeout(Duration.ofMinutes(30))`**

    - Specifies the **inactivity timeout** for the session.

    - If the user is **idle for 30 minutes** without making any request, the session will automatically expire.

    - Behavior: After the inactivity timeout period, the session is invalidated and the user is redirected to `/session-expired`.

- **`invalidSessionUrl("/session-expired")`**

    - Specifies the URL to redirect users if their session becomes **invalid** (e.g. missing or corrupted session cookie).

    - If a user attempts to access a protected resource with an invalid session, they are redirected to `/session-expired`.

- **`maximumSessions(1)`**

    - Limits the number of concurrent sessions per user to **one**.

    - If the user logs in from a second device, the first session is invalidated.

    - Behavior: Ensures users can only have one active session at a time, enhancing security.

    - **JSESSIONID Management**: Only one valid **JSESSIONID** per user is allowed. New logins replace older sessions.

- **`expiredUrl("/session-expired")`**

    - Specifies where to redirect users when their session **expires** due to inactivity.

    - After a timeout, users will land on `/session-expired`, typically prompting them to log in again.

- **Note**: We ensure `/session-expired` is accessible to unauthenticated users:

    ```java
    .antMatchers("/public/**", "/session-expired").permitAll()
    ```

  This way, users whose sessions have expired or become invalid can access the page without needing an active session.

In summary:
- **Using `formLogin()` with `SessionCreationPolicy.IF_REQUIRED` guarantees session creation**.
- Sessions are subject to **inactivity timeout**.
- Invalid or expired sessions redirect users to **`/session-expired`**.
- Concurrent session control with **`maximumSessions(1)`** strengthens security.

This ensures a robust and secure session handling mechanism for stateful web applications.

### Logout
Logout functionality allows users to safely terminate their session and ensure that session data is properly cleared upon logout.

In this configuration, we handle user logout behavior, session invalidation, and cookie deletion, while using a form-based authentication (which will require an authentication-related session by default) without specifying a custom login page.

After a user logs out, they are redirected to the default Spring Security login page with a `logout` parameter, and their session is invalidated.

```java
@Override
protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
            .antMatchers("/public/**", "/session-expired").permitAll() // Allow unauthenticated access to public pages and session-expired
            .anyRequest().authenticated() // All other requests require authentication
        .and()
        .formLogin() 
            .permitAll() // Use default Spring Security login page
        .and()
        .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Create session only when needed
            .invalidSessionUrl("/session-expired") // Redirect if session is invalid due to inactivity
            .maximumSessions(1) // Limit concurrent sessions per user
            .expiredUrl("/session-expired") // Redirect if session expires
        .and()
        .logout()
            .logoutUrl("/logout") // URL to trigger logout
            .logoutSuccessUrl("/login?logout") // Redirect after successful logout
            .invalidateHttpSession(true) // Invalidate the session on logout
            .clearAuthentication(true) // Clear authentication data
            .deleteCookies("JSESSIONID") // Delete the session cookie
            .permitAll(); // Allow access to logout for all
}
```

- **`sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)`**
  - Creates a session **only when needed** (e.g. when a user authenticates).
  - More efficient compared to always creating sessions.
  - Fits naturally with `formLogin`, where session usage is expected.

- **`formLogin().permitAll()`**
  - Uses Spring Security's **default login page**.
  - Permits all users to access the login page.

- **`logoutUrl("/logout")`**
  - Defines the endpoint (`/logout`) that triggers the logout process.

- **`logoutSuccessUrl("/login?logout")`**
  - After logout, users are redirected to the default login page with a `logout` indicator for displaying logout success messages.

- **`invalidateHttpSession(true)`**
  - Forces session invalidation at logout.
  - Removes all session attributes for security.

- **`clearAuthentication(true)`**
  - Clears the user's authentication from the security context.

- **`deleteCookies("JSESSIONID")`**
  - Deletes the session cookie (`JSESSIONID`) to prevent session reuse and mitigate session fixation attacks.

- **`permitAll()` (on logout and login)**
  - Allows unauthenticated access to login and logout endpoints.
  - Important so users can log out even if their session is already expired.

- **`antMatchers("/public/**", "/session-expired").permitAll()`**
  - Allows access to public resources and session expiration handling without authentication.

### Length
When the server sends a `JSESSIONID` cookie without setting a `max-age` or `expiry` time, it becomes a **non-persistent cookie**:

````http
Set-Cookie: JSESSIONID=abc123; Path=/; HttpOnly; Secure; SameSite=Strict
````

In this example:

- The `JSESSIONID` cookie does not include an `expires` or `max-age` attribute, making it a session cookie that will be deleted when the browser is closed.
- The `Secure` flag ensures that the cookie is only sent over HTTPS connections.
- The `SameSite=Strict` attribute ensures that the cookie is sent only in first-party contexts, providing extra security by preventing cross-site request forgery (CSRF) attacks.

To wrap up, the duration of the `JSESSIONID` cookie is not directly communicated to the client, and it is typically managed internally by the server. The `JSESSIONID` cookie itself doesn't usually carry information about its expiration time, and users are generally unaware of the specifics of how long their session will last.

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

**Spring Security does not provide a built-in way to enforce the expiration of an authentication-related session and the underlying non-persistent cookie after a fixed duration, regardless of user activity**.

However, this behavior can be implemented manually by managing session expiration through custom components.

To achieve this, you need two main components:

- **SessionCreationListener**:  
  The session listener assigns a creation timestamp to each session when it's first created, ensuring there's a reference point for how long the session has been active.

- **SessionExpirationFilter**:  
  The filter checks if the session has surpassed its maximum allowed duration based on the stored creation time, and if so, invalidates the session and redirects the user to a session-expired page.

#### SessionCreationListener
In general, an `HttpSessionListener` is an interface used to track events related to HTTP sessions.

It provides two methods that you must override to handle session-related events:

- **`sessionCreated(HttpSessionEvent se)`**:
  - This method is called when a new HTTP session is created.
  - The method receives an `HttpSessionEvent` object, which contains the session that was just created.
  - You can use this method to perform tasks such as setting attributes in the session, like a timestamp of when the session was created (e.g. tracking the session’s creation time).

- **`sessionDestroyed(HttpSessionEvent se)`**:
  - This method is called when an HTTP session is invalidated or expires.
  - The method also receives an `HttpSessionEvent` object, which contains the session that was destroyed.
  - You can use this method to clean up resources related to the session, such as removing session attributes or logging session termination.

We then define a `SessionCreationListener` class implementing this interface and `@Override` the `sessionCreated(HttpSessionEvent se)` method in a way that:

- The session listener listens for new session creations.
- Upon session creation, it stores the current system time as the session creation time.
- This session creation timestamp will then be used to track the session’s age and determine if it has exceeded the maximum session duration.

```java
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionCreationListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setAttribute("sessionCreationTime", System.currentTimeMillis());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        // No specific action needed on destruction for this use case
    }
}
```

Then we can register the listener programmatically as a Spring bean:

```java
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionConfig {

    @Bean
    public ServletListenerRegistrationBean<SessionCreationListener> sessionListener() {
        return new ServletListenerRegistrationBean<>(new SessionCreationListener());
    }
}
```

#### SessionExpirationFilter
The `SessionExpirationFilter` is a custom filter designed to enforce a maximum session length in a Spring Boot application. It checks the session's creation time and invalidates the session if it exceeds the maximum allowed duration. This filter operates independently of user activity, ensuring that sessions are automatically expired once the defined time limit is reached.

It works as follows:
- **Session Retrieval**: The filter first checks if there is an existing session using `request.getSession(false)` to avoid creating a new session.
- **Creation Time Check**: If the session exists, it retrieves the creation time from the session attribute (`sessionCreationTime`), which was set by the `SessionCreationListener`.
- **Expiration Logic**: If the session's creation time exceeds the maximum allowed duration (e.g. 1 hour), the session is invalidated, and the user is redirected to a "session expired" page.
- **Non-expired Sessions**: If the session is within the allowed duration, the request continues through the filter chain.

```java
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.Duration;

@Component
public class SessionExpirationFilter extends OncePerRequestFilter {

    private static final long MAX_SESSION_DURATION = Duration.ofHours(1).toMillis(); // 1 hour

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false); // Do not create session if not exists

        if (session != null) {
            Long creationTime = (Long) session.getAttribute("sessionCreationTime");
            if (creationTime != null && System.currentTimeMillis() - creationTime > MAX_SESSION_DURATION) {
                session.invalidate();
                response.sendRedirect("/session-expired");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
```

Then you need to perform the **registration** of this custom filter in your `SecurityConfig` `@Configuration` class.

When you want to implement **custom logic in session management** (such as session expiration), it’s crucial to control **when** the custom logic is executed relative to **authentication**.

First off, `UsernamePasswordAuthenticationFilter` is a Spring Security filter responsible for handling the process of authenticating a user based on their username and password. It plays a crucial role in HTTP-based authentication, particularly when the user logs in with their credentials, such as through **form-based** or **basic authentication**.

Although you don't explicitly define this filter class, Spring Security automatically registers it in the security filter chain by default when using basic or form-based authentication. This filter ensures that user credentials are processed correctly during the authentication process, allowing Spring Security to authenticate the user successfully.

Having said that, in this case, using `addFilterBefore()` you add the **custom filter** before the **`UsernamePasswordAuthenticationFilter`** in the filter chain to ensure that session expiration is checked **before authentication** takes place.

In the following `SecurityConfig` class, we configure Spring Security to handle form-based authentication with custom session expiration logic. A custom `SessionExpirationFilter` is injected via constructor injection and added before the `UsernamePasswordAuthenticationFilter` to check for session validity before authentication.

The session management is set to create sessions only when required and form-based authentication does require a session to function.

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final SessionExpirationFilter sessionExpirationFilter;

    // Explicit Constructor injection of the SessionExpirationFilter
    @Autowired
    public SecurityConfig(SessionExpirationFilter sessionExpirationFilter) {
        this.sessionExpirationFilter = sessionExpirationFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .addFilterBefore(sessionExpirationFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests()
                .antMatchers("/public/**", "/session-expired").permitAll() // Allow unauthenticated access to /public/** and /session-expired
                .anyRequest().authenticated() // All other requests require authentication
            .and()
            .formLogin()
                .permitAll() // Explicitly allow unauthenticated access to the login page
            .and()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Create session only if required (will happen after form login)
                .invalidSessionUrl("/session-expired") // Redirect to /session-expired if the session is invalid due to inactivity
                .maximumSessions(1) // Limit to 1 concurrent session per user
                .expiredUrl("/session-expired") // Redirect to /session-expired if the session expires
            .and()
            .logout()
                .logoutUrl("/logout") // URL for logging out
                .logoutSuccessUrl("/login?logout") // Redirect to /login?logout on successful logout
                .invalidateHttpSession(true) // Invalidate the session on logout
                .clearAuthentication(true) // Clear authentication data
                .deleteCookies("JSESSIONID") // Delete session cookies
                .permitAll(); // Allow unauthenticated access to logout
    }
    
    // more code here
}
```

---

## CSRF protection

### Attack overview
Cross-Site Request Forgery (CSRF) is an attack where an attacker tricks an authenticated user into performing an unwanted action on a web application. The attacker exploits the trust the web application has in the user's browser.

**CSRF** is primarily a concern for **stateful, session-based** applications, where the server maintains session data and identifies users based on session IDs stored in **cookies**. In these applications, browsers automatically send cookies with each request to the same domain, which can be exploited by attackers. For instance, an attacker could trick a user into making a malicious request, and the browser would automatically send the session cookie with that request, making it appear legitimate to the server.

However, **CSRF** is *not* generally a concern for **stateless applications**, such as those using **JWT** (JSON Web Tokens) for authentication. In these applications, the token is typically passed explicitly in the HTTP headers (e.g. in the `Authorization: Bearer <token>` header), *not* as cookies. Since tokens are not automatically included in requests by the browser, attackers cannot trigger requests that would automatically include the token. As a result, stateless **JWT-based** applications are less vulnerable to CSRF.

That said, **CSRF** can still be a problem in stateless applications under certain conditions. If the **JWT** is stored in **cookies** (instead of being passed in headers), it could be vulnerable to **CSRF**, as cookies would be sent automatically with cross-site requests. Additionally, if tokens are stored insecurely in places like *localStorage* or *sessionStorage*, they could be exposed through **XSS** (Cross-Site Scripting) attacks, potentially allowing attackers to use the token in malicious requests, which could resemble **CSRF** attacks.

In summary:

- CSRF is an attack where an attacker tricks an authenticated user into performing an unwanted action on a web application.
- Exploits the trust a web application has in the user's browser.
- CSRF is a concern for applications that maintain session data and identify users based on session IDs stored in cookies.
- Browsers automatically send cookies with each request to the same domain.
- Attackers can trick the user into making a malicious request.
- The browser automatically sends the session cookie, making the request appear legitimate to the server.
- Stateless applications using JWT for authentication are less vulnerable to CSRF.
- Tokens are typically passed in HTTP headers (e.g. `Authorization: Bearer <token>`), not stored in cookies.
- Since tokens are not automatically sent with requests by the browser, attackers cannot trigger requests that would include the token.
- If JWT is stored in cookies (instead of HTTP headers), it can be vulnerable to CSRF attacks because cookies are automatically sent with cross-site requests.
- If JWTs are stored insecurely (e.g. in `localStorage` or `sessionStorage`), they can be exposed via Cross-Site Scripting (XSS) attacks.
- Attackers could potentially steal the token and use it in malicious requests, similar to CSRF attacks.

#### Unfolding
1. User Login and Session Cookies:
   - The user logs into a web application and is authenticated.
   - The server issues a session cookie (e.g. `JSESSIONID`), which identifies the user's authentication.
   - The browser automatically includes this session cookie with every request to the server.

2. Attacker's Malicious Website:
   - The attacker creates a malicious website and tricks the user into visiting it while they are logged into the vulnerable web application.
   - Once the user visits the malicious site, an HTML page is rendered, and embedded JavaScript executes automatically in the browser.
   - The JavaScript sends a state-changing request (e.g. POST, PUT, DELETE) to the vulnerable web application without the user’s knowledge.
   - The request includes the session cookie automatically, as the user is authenticated on the vulnerable site.
   - Because the request includes the session cookie, the web application treats it as a legitimate request from the authenticated user.
   - The malicious action is processed by the web application, even though it originated from the attacker's site.

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
Anti-CSRF tokens are **unique, randomly generated tokens** that are associated with an authentication-related session.

Cookies are automatically sent with every request, which could lead to potential token theft or manipulation.

These tokens are **not stored in cookies** but are usually exchanged using:

- the **HTTP body**;
- **custom HTTP headers** (e.g. `X-CSRF-Token` or `X-XSRF-Token`).

The server validates these tokens to ensure that the request originates from the legitimate user and not from a malicious website. By requiring the client to programmatically include the token in the request body or custom headers (e.g. `X-CSRF-Token` or `X-XSRF-Token`), the server ensures that the request is intentional and legitimate.

Anti-CSRF tokens are **not stored in cookies** to prevent attackers from exploiting the token via **cross-site scripting (XSS)** attacks and to protect against **Cross-Site Request Forgery (CSRF)** attacks.

Anti-CSRF tokens provide an **additional layer of protection** by ensuring that requests made to the server must contain a valid token that matches what the server expects.
Even if an attacker manages to **bypass the SameSite cookie restrictions** (for example, by tricking the user into making a request via a **malicious link**), the request will still be **rejected**.
This happens because the attacker does not have access to the **correct CSRF token**, which is required to make a valid request.

Thus, Anti-CSRF tokens work alongside `SameSite` cookie settings, ensuring that **only legitimate requests** are processed, even in cases where `SameSite` protections are insufficient.

In the following example, after successful authentication, an authentication-related cookie is issued to the client and an **_Anti-CSRF token_** (`abc123xyz456`) is included as a custom header (**_X-CSRF-Token_**), which will be used by the client for subsequent requests to prevent CSRF attacks:

````http
HTTP/1.1 200 OK
Date: Mon, 08 Apr 2025 10:00:00 GMT
Server: Apache/2.4.41 (Unix)
Content-Type: text/html; charset=UTF-8
Set-Cookie: JSESSIONID=1234567890abcdef; Path=/; HttpOnly; Secure; SameSite=Strict
X-CSRF-Token: abc123xyz456
Content-Length: 37

{
  "message": "Welcome, JohnDoe!"
}
````

Alternatively, the **_Anti-CSRF token_** (`abc123xyz456`) can be included in the response body:

````http
HTTP/1.1 200 OK
Date: Mon, 08 Apr 2025 10:00:00 GMT
Server: Apache/2.4.41 (Unix)
Content-Type: application/json
Set-Cookie: JSESSIONID=1234567890abcdef; Path=/; HttpOnly; Secure; SameSite=Strict
Content-Length: 76

{
  "message": "Welcome, JohnDoe!",
  "csrfToken": "abc123xyz456"
}
````

Subsequent authenticated requests may include the Anti-CSRF token as a custom header:

```http
GET /protected-resource HTTP/1.1
Host: example.com
Cookie: JSESSIONID=1234567890abcdef
X-CSRF-Token: abc123xyz456
```

Given that **_CSRF_** is a concern in **_stateful session-based_** applications, in **_Spring Security_** it becomes more relevant to discuss **_CSRF protection_** in the context of **_form-based authentication_**, rather than **_basic authentication_**, because the former requires authentication-related sessions.

By default, Spring Security provides protection against CSRF attacks if and only if:

- a **state-changing requests** is performed:

| **HTTP Method** | **Requires CSRF Token?** | **Explanation**                                                                                                                                    |
|-----------------|--------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------|
| **POST**        | **Yes**                  | CSRF protection is required to prevent an attacker from submitting a form or making a request on behalf of the user.                               |
| **PUT**         | **Yes**                  | PUT requests are state-changing and therefore require a CSRF token to protect against unauthorized updates.                                        |
| **DELETE**      | **Yes**                  | DELETE requests are also state-changing and require a CSRF token to prevent unauthorized deletions.                                                |
| **PATCH**       | **Yes**                  | PATCH requests modify resources, so CSRF protection is necessary to avoid malicious changes.                                                       |
| **GET**         | **No**                   | GET requests are considered safe (they do not modify state), so CSRF protection is not needed for GET requests under normal CSRF protection rules. |

- the `SessionCreationPolicy` requires a session:

| **SessionCreationPolicy** | **CSRF Protection** | **Explanation**                                                                                                                                                          |
|---------------------------|---------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **`ALWAYS`**              | **Enabled**         | CSRF protection is enabled because a session is created and maintained by default. This is typical for traditional web applications that use cookies for authentication. |
| **`IF_REQUIRED`**         | **Enabled**         | CSRF protection is enabled because sessions are created when required. This is also common in traditional web apps with session-based authentication.                    |
| **`NEVER`**               | **Disabled**        | No session is created, so CSRF protection is unnecessary. Typically used for stateless APIs or applications that use JWT tokens for authentication.                      |
| **`STATELESS`**           | **Disabled**        | No session is created, and stateless authentication (like JWT) is used, so CSRF protection is not needed. Common in API-based or microservices architectures.            |


CSRF protection involves the use of an anti-CSRF token, which is generated per session and must be included in state-changing requests.

In form-based authentication, the security mechanism generates the **CSRF token** on the server side when the login page is requested. This token is associated with the user's session and is embedded in the login form as a **hidden input field**. The server ensures that each user session has a unique CSRF token to prevent attackers from using a stolen session to submit unauthorized requests.

After the user submits the login form, the **CSRF token** is expected to be present in the request body as part of the form data (typically under the name `csrfToken`). For any subsequent state-changing POST requests (such as profile updates), the server expects the **CSRF token** to be included in the request body, and it must match the token associated with the user's session. If the token is missing or invalid, the server will reject the request with a **403 Forbidden** response.

If you want to turn on CSRF protection by force, you can add `.csrf().enable()` to your security filter chain.

So the following:

````java
@Override
protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
            .antMatchers("/public/**", "/session-expired").permitAll() // Allow unauthenticated access to public pages and session-expired
            .anyRequest().authenticated() // All other requests require authentication
        .and()
        .formLogin() 
            .permitAll() // Use default Spring Security login page
        .and()
        .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Create session only when needed
            .invalidSessionUrl("/session-expired") // Redirect if session is invalid due to inactivity
            .maximumSessions(1) // Limit concurrent sessions per user
            .expiredUrl("/session-expired") // Redirect if session expires
        .and()
        .logout()
            .logoutUrl("/logout") // URL to trigger logout
            .logoutSuccessUrl("/login?logout") // Redirect after successful logout
            .invalidateHttpSession(true) // Invalidate the session on logout
            .clearAuthentication(true) // Clear authentication data
            .deleteCookies("JSESSIONID") // Delete the session cookie
            .permitAll(); // Allow access to logout for all
}
````

is equivalent to:

````java
@Override
protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf() // Enable CSRF protection explicitly
            .and() // Continue the configuration chain
        .authorizeRequests()
            .antMatchers("/public/**", "/session-expired").permitAll() // Allow unauthenticated access to public pages and session-expired
            .anyRequest().authenticated() // All other requests require authentication
        .and()
        .formLogin()
            .permitAll() // Use default Spring Security login page
        .and()
        .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Create session only when needed
            .invalidSessionUrl("/session-expired") // Redirect if session is invalid due to inactivity
            .maximumSessions(1) // Limit concurrent sessions per user
            .expiredUrl("/session-expired") // Redirect if session expires
        .and()
        .logout()
            .logoutUrl("/logout") // URL to trigger logout
            .logoutSuccessUrl("/login?logout") // Redirect after successful logout
            .invalidateHttpSession(true) // Invalidate the session on logout
            .clearAuthentication(true) // Clear authentication data
            .deleteCookies("JSESSIONID") // Delete the session cookie
            .permitAll(); // Allow access to logout for all
}
````

Using the above configuration, in the following example:

- **Initial Non-Authenticated Request**
  - A **GET request** is made to access the protected resource (`/protected-resource`).
  - The server responds with a **302 redirect** to the login page, as the user is not authenticated.
  - The server sets a session cookie (`JSESSIONID=abcd1234`) for the new session.

- **GET Request to Login Page**
  - The browser sends a **GET request** to the login page (`/login`) with the session cookie (`JSESSIONID=abcd1234`) attached.
  - The server responds by serving the login page, which includes the login form with a hidden **CSRF token** for the session.

- **POST Request to Login**
  - The user submits their credentials (username, password) and the **CSRF token** via a **POST request** to authenticate the user.
  - The server processes the credentials and the **CSRF token**, then responds with a **302 redirect** to the originally requested protected resource (`/protected-resource`).
  - A new session cookie (`JSESSIONID=abcd5678; HttpOnly; Secure; SameSite=Strict`) is set to replace the initial one, indicating successful authentication.

- **Accessing the Protected Resource After Authentication**
  - A **GET request** is made to the protected resource (`/protected-resource`) with the new session cookie (`JSESSIONID=abcd5678`), confirming the user is authenticated.
  - The server grants access to the protected resource and responds with the requested content.
  - **No CSRF token is required** for this **GET request** since it's non-state-changing.

- **State-Changing POST Request with CSRF Token**
  - A **state-changing POST request** is made to the protected resource with a valid **CSRF token** to perform an action (e.g. updating the profile).
  - The server validates the **CSRF token** and processes the request successfully.

- **State-Changing POST Request Without CSRF Token**
  - Another **state-changing POST request** is made to the protected resource without including the **CSRF token**.
  - The server rejects the request with a **403 Forbidden** response due to the missing or invalid **CSRF token**.

***

````plaintext
GET /protected-resource HTTP/1.1
Host: example.com
````

````http
HTTP/1.1 302 Found
Location: /login
Set-Cookie: JSESSIONID=abcd1234; Path=/
````

***

````plaintext
GET /login HTTP/1.1
Host: example.com
Cookie: JSESSIONID=abcd1234
````

````html
<!-- HTML Form for login with CSRF token -->
<form method="POST" action="/login">
    <input type="hidden" name="csrfToken" value="csrf-token-xyz">
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
Content-Length: 73
Cookie: JSESSIONID=abcd1234

username=user&password=password&csrfToken=csrf-token-xyz
````

````http
HTTP/1.1 302 Found
Location: /protected-resource
Set-Cookie: JSESSIONID=abcd5678; Path=/; HttpOnly; Secure; SameSite=Strict
````

***

````plaintext
GET /protected-resource HTTP/1.1
Host: example.com
Cookie: JSESSIONID=abcd5678
````

````http
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 33

{
"message": "Access granted"
}
````

***

````plaintext
POST /protected-resource HTTP/1.1
Host: example.com
Content-Type: application/x-www-form-urlencoded
Cookie: JSESSIONID=abcd5678

action=updateProfile&csrfToken=csrf-token-xyz
````

````http
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 32

{
"message": "Profile updated"
}
````

***

````plaintext
POST /protected-resource HTTP/1.1
Host: example.com
Content-Type: application/x-www-form-urlencoded
Cookie: JSESSIONID=abcd5678

action=updateProfile
````

````http
HTTP/1.1 403 Forbidden
Content-Type: application/json
Content-Length: 44

{
"error": "CSRF token missing or invalid"
}
````

***

#### Customisation
If a session is required, CSRF protection is enabled by default in Spring Security for state-changing requests.

For additional customization, you can configure the policy.

For example, if you want to exchange the token as a cookie (insecure):

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

- The `.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())` stores the CSRF token in a cookie rather than in the session. The cookie is marked with the `HttpOnly` flag set to `false`, meaning it can be accessed by JavaScript running in the browser. This approach is often used for single-page applications (SPAs) or situations where the CSRF token needs to be shared across multiple requests.
- Storing the CSRF token in cookies exposes you to XSS (Cross-Site Scripting) risks, as malicious scripts can potentially steal tokens from the cookie. As a result, this customization is generally not recommended unless you have additional security measures in place, such as Content Security Policy (CSP) and XSS protection. It's safer to stick to the default behavior of storing the CSRF token in the session.

#### Disable
In stateless applications, such as those using **JWT** or other **token-based authentication** methods, no session is maintained for tracking user state and CSRF tokens are not required.

So if you set **SessionCreationPolicy** to `STATELESS`, Spring Security will disable CSRF protection by default

Disabling CSRF protection is appropriate in scenarios such as:

- The application is stateless, meaning it does not rely on sessions.
- Authentication is handled via tokens (e.g. JWT) passed in headers or other parts of the request.
- CSRF protection is irrelevant because there is no session to secure.

If you want to disable CSRF protection explicitly, use the `csrf().disable()` method:

````java
@Override
protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()  // Explicitly disable CSRF protection
        .authorizeRequests()
            .antMatchers("/public/**").permitAll()
            .anyRequest().authenticated()
            .and()
        .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
        .httpBasic();
}
````
