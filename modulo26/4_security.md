# Spring Security
Spring Security is a comprehensive framework for securing Java applications, especially those built with Spring. It provides a wide array of functionalities like authentication, authorization, protection against common vulnerabilities (e.g.: CSRF, session fixation, session hijacking), and integrates easily with both web and enterprise applications.

Main concepts involved:

- **Authentication**: Verifying the identity of the user.
- **Authorization**: Checking if the authenticated user has permission to access a resource.
- **Security Filter Chain**: A series of filters that intercept and process HTTP requests to apply security policies like authentication and authorization.

---

## pom.xml

To set up Spring Security in an already existing Spring Boot project, add the following dependency to your `pom.xml`:

```xml
    <!-- Spring Boot Starter Security (for security configurations) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
        <version>3.4.4</version>
    </dependency>

```

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
`WebSecurityConfigurerAdapter` is a base class in Spring Security that provides default implementations for several methods related to configuring web security. When you extend this class, you can override its methods to customize the security configuration for your application.

That it, it is a convenience class that makes it easy to set up security configurations in Spring. By extending this class, you gain access to several methods that can be overridden to configure HTTP security, authentication mechanisms, and more.

The `@Override`  annotation is used to indicate that a method is overriding a method in the superclass. In this case, the `@Override` annotation is applied to the `configure(HttpSecurity http)` method, which is inherited from `WebSecurityConfigurerAdapter`. By overriding this method, you can customize how HTTP security is applied to the web application, such as controlling which URLs are accessible and how users are authenticated.

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

The PasswordEncoder bean is used to hash user passwords securely before storing them. The `BCryptPasswordEncoder` is a cryptographic algorithm that provides secure, slow hashing designed to be resistant to brute-force attacks.

BCrypt is a strong, adaptive hashing algorithm designed to be slow, which makes it more difficult for attackers to crack passwords using brute-force methods.

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
Think of `UserDetailsService` like a phone book, but for users on a website. It keeps track of all the people who can enter and what their passwords are. So, when someone tries to log in, we ask this phone book, "Do you know this person, and do they have the correct password?"

The `UserDetailsService` is like the helper who looks up the details about each person and checks if they are allowed to enter.

Now, `InMemoryUserDetailsManager` is like a special phone book that keeps all of the people’s details in a notebook inside the computer's memory. It’s really fast to look through because it’s all kept in the computer’s brain, but it’s not stored anywhere permanent (like a library). If you turn off the computer, the phone book gets erased.

So, the `InMemoryUserDetailsManager` is like a phone book that’s kept in the computer’s short-term memory for quick lookups, but if you restart the computer, it forgets who was in the phone book.

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

- **UserDetailsService**: This method defines the UserDetailsService bean. UserDetailsService is an interface used by Spring Security to fetch user details (such as username, password, and authorities) for authentication. The InMemoryUserDetailsManager is a simple implementation of UserDetailsService that holds users in memory instead of a database.

- **InMemoryUserDetailsManager**:
    - It is used to manage user details in memory. In this case, a user with the username "user" and an encoded password "password" is created and stored.
    - When Spring Security attempts to authenticate a user, it uses the InMemoryUserDetailsManager to find the user’s details (such as username, password, and authorities).
    - **Best Practice**: Hardcoding passwords directly in the source code, as done in this example, should be avoided. For better security, consider using externalized secrets management strategies, such as loading encrypted passwords from a secure file, database, or using tools like HashiCorp Vault, AWS Secrets Manager, or environment variables to inject sensitive data securely into the application.
    - The `InMemoryUserDetailsManager` is suitable for simple use cases, such as development or small applications.

- **How Spring Security Uses It**:
    - When an authentication request is made (for example, using HTTP Basic Authentication), Spring Security will call the UserDetailsService to retrieve user information.
    - The InMemoryUserDetailsManager will load the user by their username and compare the provided password with the encoded password stored in memory.
    - If the authentication is successful, the user will be granted access to the requested resource.

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
In Spring Security, a **security filter chain** is a sequence of filters automatically applied to secure your application. These filters handle tasks such as authentication, authorization, and protection against common threats. The code below demonstrates how Spring Security’s **fluent API** allows developers to configure the behavior of this security filter chain through **in-place method calls**.

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
Basic Authentication is a simple way to secure an API or web application by requiring the client to send a username and password in the request header.

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

One such implementation is the `InMemoryUserDetailsManager`, which allows you to inject hardcoded user-password pairs directly into the system. While this is simple and useful for small applications or testing scenarios, it does not scale well for larger applications. As the number of users grows, hardcoding all user credentials in memory becomes impractical and inefficient. Additionally, the `InMemoryUserDetailsManager` is volatile, meaning that once the application is restarted, all stored user data is lost.

For applications with many users or those requiring persistence, relying on an in-memory solution is not ideal. In such cases, a more scalable solution, like storing user credentials in a database or using an external identity provider, would be necessary to ensure security, scalability, and persistence over time.

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

- **Step 1**: Spring Security enforces authentication for all incoming requests, except those explicitly permitted (e.g., `/public/**`).
- **Step 2**: When a client sends a request with **Basic Authentication credentials**, it includes the credentials in the HTTP `Authorization` header. The header looks like this:

  `Authorization: Basic dXNlcjpwYXNzd29yZA==`

  Here, `dXNlcjpwYXNzd29yZA==` is the **Base64-encoded** value of `username:password` (e.g., `user:password`).

- **Step 3**: Spring Security extracts the **Base64-encoded credentials** from the `Authorization` header.

- **Step 4**: Spring Security decodes the **Base64** value to retrieve the username and password (e.g., `user:password`).

- **Step 5**: The **`userDetailsService`** bean (implemented with `InMemoryUserDetailsManager`) is called to retrieve the user details associated with the username.
    - The `InMemoryUserDetailsManager` contains the hardcoded user credentials (e.g., `"user"` and `"admin"`) along with their corresponding **hashed passwords**.

- **Step 6**: Spring Security uses the **configured `PasswordEncoder`** (in this case, `BCryptPasswordEncoder`) to hash the provided password and compare it with the stored, hashed version.
    - The password from the `Authorization` header is compared against the hashed password stored in memory.

- **Step 7**: If the **password matches** (i.e., the provided password's hash matches the stored hash), the authentication **succeeds**.
    - The user is granted access to the protected resource they requested.

- **Step 8**: If the **password does not match** or no matching user is found, authentication **fails**, and the user is denied access.
    - In this case, Spring Security typically returns a **401 Unauthorized** HTTP status code.

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

The `JdbcUserDetailsManager` is used to retrieve and manage user credentials stored in a relational database. It provides a persistent solution for applications that need to scale and store user data, especially for large applications.

- **Database Interaction**:  
  It interacts with a relational database to load user credentials and authorities, which are necessary for user authentication and authorization.

- **Default Database Schema**:  
  By default, `JdbcUserDetailsManager` works with two core tables: `users` and `authorities`. These tables hold the necessary information for user authentication and the roles or permissions associated with the user.

  The `users` table stores the basic user information, including the username, password (encrypted), and the status of the user (enabled or disabled).

  The `authorities` table is used to store user roles or permissions. It links the username to specific roles (e.g., `ADMIN`, `USER`, `GUEST`), which are then used by Spring Security to grant or restrict access to certain resources.

  Example schema for these tables:

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
  
- **`users` Table**:  
  The `users` table contains the basic information for each user, including the `username`, their `encrypted password`, and an `enabled` status. The `enabled` status indicates whether the user is allowed to authenticate and log into the system. If the user is marked as disabled (`enabled = false`), they cannot log in, even if they provide the correct username and password.

- **`authorities` Table**:  
  The `authorities` table stores the roles or permissions assigned to each user. Each entry in this table links a user (via their `username`) to a particular `authority` (which could be a role such as `ADMIN`, `USER`, `MANAGER`, etc.).

  This table is critical for authorization. Once a user is authenticated, Spring Security checks the entries in the `authorities` table to determine what resources or actions the user is authorized to access. For example, if a user has the `ADMIN` authority, they may have access to admin-only pages, while a `USER` may have more limited access.

- **Customizing SQL Queries**:  
  While `JdbcUserDetailsManager` uses default SQL queries to interact with the `users` and `authorities` tables, you can customize these queries to fit the structure of your database. For example, if you have a custom schema or additional tables to store user data, you can override the default queries to match your specific setup.

- **`@Bean` Annotation**:  
  The `@Bean` annotation is used to define methods that return Spring-managed objects. In this case, the `JdbcUserDetailsManager` is defined as a bean, which means Spring will manage its lifecycle and inject it where necessary for user authentication and authorization.

- **Method Parameters**:
    - `DataSource dataSource`: The `DataSource` provides the connection to the database. The `JdbcUserDetailsManager` uses this connection to query the `users` and `authorities` tables and retrieve user details.
    - `PasswordEncoder passwordEncoder`: The `PasswordEncoder` is used to encode passwords before storing them in the database. It also ensures that passwords are properly hashed during authentication so that the original password is never exposed.

- **Creating the `JdbcUserDetailsManager`**:
  The `JdbcUserDetailsManager` is created and initialized with a `DataSource` (the database connection). This class is responsible for handling user authentication and authorization via JDBC.

  ```java
  JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
  ```

- **Setting the Password Encoder**:  
  By configuring the `PasswordEncoder`, we ensure that passwords are securely hashed before being stored in the database and during the authentication process. This is crucial for protecting sensitive information, as plain-text passwords are never stored. The `PasswordEncoder` ensures that even if the database is compromised, the actual passwords cannot be easily retrieved or used.

  For example, using `BCryptPasswordEncoder` applies a strong hashing algorithm that makes it computationally expensive to crack the password hashes, enhancing the security of the system.

- **Returning the Bean**:  
  After configuring the `JdbcUserDetailsManager` with the necessary data source and password encoder, the manager is returned from the method as a Spring bean. By declaring it as a bean, Spring will manage its lifecycle, allowing it to be automatically injected into other components (such as Spring Security’s authentication filters) where user authentication is required.
  This enables Spring Security to leverage the `JdbcUserDetailsManager` for authenticating users based on the credentials stored in the relational database.
  ```java
  return userDetailsManager;
  ```

Due to the configuration:

```java
.anyRequest().authenticated()           // Require authentication for all other requests
.and()
.httpBasic();                            // Enable HTTP Basic Authentication
```

- **Step 1**: Spring Security enforces authentication for all incoming requests, except those explicitly permitted (e.g., `/public/**`).

- **Step 2**: When a client sends a request with **Basic Authentication credentials**, it includes the credentials in the HTTP `Authorization` header. The header looks like this:

  `Authorization: Basic dXNlcjpwYXNzd29yZA==`

  Here, `dXNlcjpwYXNzd29yZA==` is the **Base64-encoded** value of `username:password` (e.g., `user:password`).

- **Step 3**: Spring Security extracts the **Base64-encoded credentials** from the `Authorization` header.

- **Step 4**: Spring Security decodes the **Base64** value to retrieve the username and password (e.g., `user:password`).

- **Step 5**: Spring Security delegates the authentication process to the **`JdbcUserDetailsManager`** bean, which is configured to retrieve user details from the relational database.

- **Step 6**: The **`JdbcUserDetailsManager`** queries the database (typically using SQL) to retrieve the user details associated with the provided username.
    - It checks the `users` table for the matching username and retrieves the stored **hashed password**, **enabled status**, and any additional user details.

- **Step 7**: Spring Security uses the **configured `PasswordEncoder`** (in this case, `BCryptPasswordEncoder`) to compare the provided password (after hashing it) with the stored, hashed password in the database.

- **Step 8**: If the **password matches** (i.e., the hashed version of the provided password matches the stored hash), the authentication **succeeds**.
    - The user is granted access to the requested protected resource.

- **Step 9**: If the **password does not match** or no matching user is found, authentication **fails**.
    - In this case, Spring Security typically returns a **401 Unauthorized** HTTP status code, indicating that the credentials were invalid or the user does not exist.

- **Step 10**: If authentication is successful, Spring Security proceeds to handle the request, applying any security policies such as role-based access control (RBAC) or any other configured rules (e.g., permission checks) before granting access to the requested resource.

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

### What Happens When a Request Comes Through

#### Request Matching
- Spring Security matches request URLs against the configured `antMatchers` rules:
  - If the URL matches `/public/**`, it is allowed without authentication (`permitAll()`).
  - If the URL matches `/admin/**`, the user must have the "ADMIN" role.
  - If the URL matches `/user/**`, the user must have the "USER" role.
  - **Important**: rules about the role are defined before authentication but are **only applied after the user is authenticated**.
  - Any other request requires authentication (`authenticated()`).

#### Authentication
- If a user is not authenticated, Spring Security triggers basic authentication (`httpBasic()`), prompting for credentials.
- The username and password are verified against the in-memory user store (`InMemoryUserDetailsManager`).
- **Authentication happens first**: This process establishes the user’s identity and retrieves the roles associated with that user. Only after successful authentication will Spring Security apply the role-based rules.

#### Role Authorization
- After the user is authenticated, Spring Security checks if the user has the required role for the requested URL.
  - For example, if the request matches `/admin/**`, Spring Security will check if the user has the "ADMIN" role using the `hasRole()` method.
- **Role checks only happen after authentication**. Until authentication is successful, Spring Security cannot determine the user's roles.
- If the user lacks the appropriate role, the request is denied.

#### Successful Access
- If authentication and role checks pass, the request proceeds to the corresponding handler (e.g., a controller method).

#### Unsuccessful Access
- If authentication or role checks fail, an HTTP 401 (Unauthorized) or 403 (Forbidden) response is returned.

This setup ensures that requests are properly secured, and only authenticated and authorized users can access certain parts of the application based on their roles.

---

## Authentication-related session management
In Spring Security, **creating a session means generating a server-side session where certain information about the authenticated user is stored**. This session is typically linked to the user’s interaction with the application, ensuring that subsequent requests can reference the same session and access the user’s authentication details without requiring them to log in again.

When Spring Security creates a session, it’s essentially managing the user's authentication state across multiple HTTP requests. HTTP itself is stateless, meaning that each request is independent and doesn’t carry any information about previous requests. A session helps to maintain state by storing data that can be used in subsequent requests (like the user's authentication status, roles, and permissions).

### JSESSIONID
When Spring Security creates an authentication-related session, it typically adds a session identifier (JSESSIONID) to the HTTP response. This session identifier is placed in the response as a cookie (by default) or as part of the response headers. This allows the client (usually a browser) to track the session across requests.

For example, a server-side session might result in the following:

- **Session ID**: A unique identifier for the session (e.g., `JSESSIONID=12345abcde`).
  - This is stored as a cookie on the client side (browser), which is sent with subsequent requests to maintain the session.
- **Other session data**: Information about the authenticated user, such as their roles, permissions, or user-specific data might be stored in the session on the server side, but this data is not automatically sent in the response. It’s only accessed from the server's session store when needed.

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
You can configure Spring Security with the `HttpSecurity` object to specify how session management should be handled.

**Creating a session typically means that a `JSESSIONID` is introduced** (stored in the user's browser as a cookie) to maintain the session state across requests. This introduces session management overhead and can lead to security concerns. With stateless authentication, there's no need for the server to store session state, and the token (e.g., JWT) itself is sufficient for each request.

Let’s look at how different session policies affect what happens with sessions.

#### `SessionCreationPolicy.IF_REQUIRED`

By default, Spring Security uses `SessionCreationPolicy.IF_REQUIRED`. This means that a session is created only when deemed necessary by the framework.

That is, when no session creation policy is defined in the security filter chain, **Spring Security uses `SessionCreationPolicy.IF_REQUIRED` as the default**. This means that **the decision to create a session is left to the framework** based on the authentication mechanism being used (e.g. Basic Authentication).

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

#### `SessionCreationPolicy.ALWAYS`
With `SessionCreationPolicy.ALWAYS`, a session is created for every request even if one already exists.

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
| Policy                                        | What happens on the server                                                         | What happens in the response                        | What happens in subsequent requests                                           |
|-----------------------------------------------|------------------------------------------------------------------------------------|-----------------------------------------------------|-------------------------------------------------------------------------------|
| `SessionCreationPolicy.ALWAYS`                | A session is always created, even if one already exists.                           | `JSESSIONID` cookie is set with a session ID.       | The client sends the `JSESSIONID` cookie with each request.                   |
| `SessionCreationPolicy.IF_REQUIRED` (default) | A session is created only when deemed necessary.                                   | `JSESSIONID` cookie is set if a session is created. | The client sends the `JSESSIONID` cookie with each request.                   |
| `SessionCreationPolicy.NEVER`                 | No session is created. However, pre-existing session data **might** still be used. | No `JSESSIONID` cookie is set.                      | Authentication must be sent explicitly in request headers (e.g., Basic Auth). |
| `SessionCreationPolicy.STATELESS`             | No session is created, and no state is maintained.                                 | No `JSESSIONID` cookie is set.                      | Authentication must be sent explicitly in request headers (e.g., JWT).        |

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
        - If your application is **stateful** (e.g., maintains user session information), a session will be created when the user first authenticates.
        - If your application is **stateless** (e.g., REST APIs), no session will be created unless explicitly required.

    - This approach provides flexibility, ensuring that a session is created only when necessary, based on your application's needs.

- **`sessionTimeout(Duration.ofMinutes(30))`**

    - This setting specifies the **inactivity timeout** for the session.

    - **Inactivity Timeout**: The session will be considered expired if the user does not perform any action (e.g., make a request) within the defined period (30 minutes in this case).
        - If the user is idle for 30 minutes without interacting with the application, the session will automatically expire.

    - **Behavior**: After the inactivity timeout period, the session will be invalidated, and the user will be redirected to the `/session-expired` URL.

- **`invalidSessionUrl("/session-expired")`**

    - This setting specifies the URL to redirect the user if their session is considered **invalid**.

    - **Invalid Session**: A session is invalid if it is explicitly invalidated or if there is an issue (e.g., session data corruption or missing session cookies).

    - **Behavior**: If a user tries to access a resource with an invalid session, they will be redirected to `/session-expired`.
        - This page informs the user that their session is no longer valid and may prompt them to log in again.

- **`maximumSessions(1)`**

    - This configuration limits the number of concurrent sessions a user can have.

    - **One Session per User**: Setting `maximumSessions(1)` ensures that only one active session is allowed per user.
        - If the user logs in from one device and then logs in from another, the first session will be invalidated.

    - **Behavior**: This is useful when you want to enforce **single-session login**, meaning users cannot have multiple sessions across different devices or browsers.

- **`expiredUrl("/session-expired")`**

    - This setting defines the URL to redirect the user when their session **expires** due to inactivity.

    - **Expired Session**: A session expires when it has been inactive for a certain period, defined by the session timeout setting.

    - **Behavior**: If the session expires due to inactivity (e.g., the user hasn't made any request for a specified period), they will be redirected to `/session-expired`.
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
                .deleteCookies("JSESSIONID")  // Delete session cookies (e.g., JSESSIONID)
                .permitAll();  // Allow all users to access the logout URL
    }
```

### Logout Configuration

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

---

## CSRF protection

### Attack overview
Cross-Site Request Forgery (CSRF) is an attack where an attacker tricks an authenticated user into performing an unwanted action on a web application. The attacker exploits the trust the web application has in the user's browser.

**CSRF** is primarily a concern for **stateful, session-based** applications, where the server maintains session data and identifies users based on session IDs stored in **cookies**. In these applications, browsers automatically send cookies with each request to the same domain, which can be exploited by attackers. For instance, an attacker could trick a user into making a malicious request, and the browser would automatically send the session cookie with that request, making it appear legitimate to the server.

However, **CSRF** is *not* generally a concern for **stateless applications**, such as those using **JWT** (JSON Web Tokens) for authentication. In these applications, the token is typically passed explicitly in the HTTP headers (e.g., in the `Authorization: Bearer <token>` header), *not* as cookies. Since tokens are not automatically included in requests by the browser, attackers cannot trigger requests that would automatically include the token. As a result, stateless **JWT-based** applications are less vulnerable to CSRF.

That said, **CSRF** can still be a problem in stateless applications under certain conditions. If the **JWT** is stored in **cookies** (instead of being passed in headers), it could be vulnerable to **CSRF**, as cookies would be sent automatically with cross-site requests. Additionally, if tokens are stored insecurely in places like *localStorage* or *sessionStorage*, they could be exposed through **XSS** (Cross-Site Scripting) attacks, potentially allowing attackers to use the token in malicious requests, which could resemble **CSRF** attacks.

In summary, while **CSRF** is mostly a concern for **session-based applications**, it can still be a risk for stateless **JWT-based** applications if the token is improperly stored in **cookies** or exposed through other vulnerabilities like **XSS**. Therefore, while **CSRF** is less of an issue in stateless systems, proper token storage and handling are still essential to maintain security.

#### 1. Unfolding
1. User Login and Session Cookies:
   - The user logs into a web application and is authenticated.
   - The server issues a session cookie (e.g., `JSESSIONID`), which identifies the user's session.
   - The browser automatically includes this session cookie with every request to the server.

2. Attacker's Malicious Website:
   - The attacker creates a malicious website that tricks the user into visiting it while logged into the vulnerable site.
   - The attacker injects JavaScript into the malicious website that automatically sends a state-changing request to the vulnerable web application.
   - The malicious site sends a state-changing request (e.g., POST, PUT, DELETE) to the vulnerable web application. The browser includes the session cookie with the request, making it seem legitimate.

3. Outcome:
   - The attacker successfully performs actions that the user didn’t intend by exploiting the user’s session.

#### 2. SameSite Cookie Policy
The **SameSite=Strict** attribute ensures that the cookie is **only sent** when the request originates from the same domain as the cookie. This means the cookie **will not be sent** with **cross-site requests**, even if the user navigates to the site from a different domain or performs an action such as clicking a link from another site.

This security feature helps to prevent **Cross-Site Request Forgery (CSRF)** attacks by ensuring that **cookies** (such as session cookies) are **not automatically sent** with requests made from a different origin (i.e. another website).

```http
HTTP/1.1 200 OK
Set-Cookie: jsessionid=abc123; SameSite=Strict; Path=/; HttpOnly; Secure
```

#### 3. Anti-CSRF token
Anti-CSRF tokens are **unique, randomly generated tokens** that are associated with a user's session.

These tokens are **not stored in cookies** but are usually exchanged:
- within the **HTTP body**;
- **custom HTTP headers** (e.g. `X-CSRF-Token` or `X-XSRF-Token`).

The server validates these tokens to ensure that the request originates from the legitimate user and not from a malicious website.

The primary goal of Anti-CSRF tokens is to protect against **Cross-Site Request Forgery (CSRF)** attacks.

Anti-CSRF tokens are **not stored in cookies** to prevent attackers from exploiting the token via **cross-site scripting (XSS)** attacks. Cookies are automatically sent with every request, which could lead to potential token theft or manipulation. By requiring the client to programmatically include the token in the request body or custom headers (e.g., `X-CSRF-Token` or `X-XSRF-Token`), the server ensures that the request is intentional and legitimate.

Anti-CSRF tokens provide an **additional layer of protection** by ensuring that requests made to the server must contain a valid token that matches what the server expects.

- Even if an attacker manages to **bypass the SameSite cookie restrictions** (for example, by tricking the user into making a request via a **malicious link**), the request will still be **rejected**.
- This happens because the attacker does not have access to the **correct CSRF token**, which is required to make a valid request.

Thus, Anti-CSRF tokens work alongside `SameSite` cookie settings, ensuring that **only legitimate requests** are processed, even in cases where `SameSite` protections are insufficient.

**As a Custom Header:**

1. **Token Generation**:
   - Upon session creation, the server generates a unique Anti-CSRF token and associates it with the user session (stored server-side).
   - The server sends the token to the client, typically as part of the initial HTTP response in a custom response header (e.g., `X-CSRF-Token` or `X-XSRF-Token`).

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
    - If the token in the request doesn't match the server-side token, the server rejects the request as potentially originating from a malicious source (e.g., a CSRF attack).
    - If the tokens match, the server considers the request legitimate and processes it accordingly, allowing the requested action to proceed (e.g., updating a resource, submitting a form).

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
        .authorizeRequests()
            .antMatchers("/public/**").permitAll()  // Allow public URLs to be accessed without authentication
            .anyRequest().authenticated()  // Require authentication for other requests
        .and()
        .httpBasic()  // Enable basic HTTP authentication
        .and()
        .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)  // Only create a session when required
            .invalidSessionUrl("/session-expired")  // Redirect users if their session becomes invalid
            .maximumSessions(1)  // Limit to one concurrent session per user (optional)
            .expiredUrl("/session-expired")  // Redirect when the session expires
        .and()
        .logout()
            .logoutUrl("/logout")  // URL that triggers the logout process
            .logoutSuccessUrl("/login?logout")  // Redirect URL after successful logout
            .invalidateHttpSession(true)  // Invalidate the session when logging out
            .clearAuthentication(true)  // Clear authentication data on logout
            .deleteCookies("JSESSIONID")  // Delete session cookies (e.g., JSESSIONID)
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

For **REST APIs with JSON responses**, Spring Security automatically handles the CSRF token by including it in the HTTP response headers. When a state-changing request (e.g., POST, PUT, DELETE) is made, Spring Security will include the CSRF token in the `X-CSRF-TOKEN` HTTP header in the response.

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
            .deleteCookies("JSESSIONID")  // Delete session cookies (e.g., JSESSIONID)
            .permitAll()  // Allow all users to access the logout URL
        .and()
        .csrf()  // Explicitly enable CSRF protection
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());  // Customizing token storage to cookies (not recommended due to security risks)
}
```

- **Customizing Token Storage:** The `.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())` stores the CSRF token in a cookie rather than in the session. The cookie is marked with the `HttpOnly` flag set to `false`, meaning it can be accessed by JavaScript running in the browser. This approach is often used for single-page applications (SPAs) or situations where the CSRF token needs to be shared across multiple requests.
- **Important:** Storing the CSRF token in cookies exposes you to XSS (Cross-Site Scripting) risks, as malicious scripts can potentially steal tokens from the cookie. As a result, this customization is generally not recommended unless you have additional security measures in place, such as Content Security Policy (CSP) and XSS protection. It's safer to stick to the default behavior of storing the CSRF token in the session.

#### Disable
In cases where you're working with a stateless application (e.g., using JWT or token-based authentication), you might want to disable CSRF protection since sessions are not used and thus no anti-CSRF token is needed.

In stateless applications, such as those using **JWT** or other **token-based authentication** methods, you may want to disable **CSRF protection**. This is because, in a stateless application, no session is maintained for tracking user state, and CSRF tokens are not required.

When you configure **SessionCreationPolicy** to `STATELESS`, Spring Security will not create or manage sessions. Since CSRF protection is designed to work with sessions (using an anti-CSRF token stored in the session), it becomes unnecessary in stateless applications. Therefore, you can disable CSRF protection explicitly using the `csrf().disable()` method.

Disabling CSRF protection is appropriate in scenarios such as:
- The application is stateless, meaning it does not rely on sessions.
- Authentication is handled via tokens (e.g., JWT) passed in headers or other parts of the request.
- CSRF protection is irrelevant because there is no session to secure.

To disable CSRF protection, simply use the `csrf().disable()` method in your Spring Security configuration. By doing so, Spring Security will skip CSRF checks for all incoming requests, which is essential for applications that do not use sessions for tracking user state.

This approach ensures that requests can be authenticated based on tokens, without the need for session-based protections. However, ensure that your token management (e.g., validating JWT tokens on every request) is secure, as CSRF protection will not be in place.

```java
@Override
protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
            .antMatchers("/public/**").permitAll()
            .anyRequest().authenticated()
        .and()
        .httpBasic()
        .and()
        .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .csrf().disable()
        .logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login?logout")
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .deleteCookies("JSESSIONID")
            .permitAll();
}
```

#### Final considerations
- **Session Expiration:** When the session expires (due to inactivity, for example), the anti-CSRF token tied to that session is also invalidated. If the user tries to make a state-changing request after their session expires, the request will be rejected with a `403 Forbidden` response, as the CSRF token is no longer valid.
- **Logout:** When the user logs out, the session is invalidated. This also invalidates the anti-CSRF token. If the user attempts to submit a request after logging out, it will be rejected because the session and the associated anti-CSRF token no longer exist.
- **Avoid Storing CSRF Tokens in Cookies:** The anti-CSRF token should never be stored in cookies. If malicious JavaScript (e.g., via XSS) can access the cookie, the attacker may steal the token and bypass CSRF protection.
- **Using HTTP Headers for CSRF Tokens:** It's safer to include the anti-CSRF token in HTTP headers (`X-CSRF-TOKEN`) for AJAX requests or in form fields for traditional form submissions. This ensures the token is not exposed to JavaScript, reducing the risk of XSS.
- You should disable CSRF protection in cases where:
  - The application is stateless (using JWT or OAuth2 tokens).
  - There's no session management, and token handling is done client-side.
- For traditional web applications, where sessions are used, CSRF protection should remain enabled.

---

## Authentication-related vs Non-Authentication session data
