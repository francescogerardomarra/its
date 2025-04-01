# Spring Security
Spring Security is a comprehensive framework for securing Java applications, especially those built with Spring. It provides a wide array of functionalities like authentication, authorization, protection against common vulnerabilities (e.g.: CSRF, session fixation, session hijacking), and integrates easily with both web and enterprise applications.

Main concepts involved:

- **Authentication**: Verifying the identity of the user.
- **Authorization**: Checking if the authenticated user has permission to access a resource.
- **Security Filter Chain**: A series of filters that intercept and process HTTP requests to apply security policies like authentication and authorization.

---

## pom.xml

To set up Spring Security in a Spring Boot project, add the following dependencies to your `pom.xml`:

```xml
<dependencies>
    <!-- Spring Boot Starter Web (for RESTful services) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- Spring Boot Starter Security (for security configurations) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
        <version>3.4.4</version>
    </dependency>
</dependencies>
```

---

## First Example

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

---

## Code digging

### WebSecurityConfigurerAdapter and `@Override`

`WebSecurityConfigurerAdapter` is a base class in Spring Security that provides default implementations for several methods related to configuring web security. When you extend this class, you can override its methods to customize the security configuration for your application.

- **`WebSecurityConfigurerAdapter`**: This is a convenience class that makes it easy to set up security configurations in Spring. By extending this class, you gain access to several methods that can be overridden to configure HTTP security, authentication mechanisms, and more.

- **`@Override` Annotation**: This annotation is used to indicate that a method is overriding a method in the superclass. In this case, the `@Override` annotation is applied to the `configure(HttpSecurity http)` method, which is inherited from `WebSecurityConfigurerAdapter`. By overriding this method, you can customize how HTTP security is applied to the web application, such as controlling which URLs are accessible and how users are authenticated.

By extending `WebSecurityConfigurerAdapter` and overriding its methods, you can tailor the security settings to meet your specific requirements. For example, you can decide which endpoints are publicly accessible, configure authentication types, and apply authorization rules as needed.

### PasswordEncoder Bean

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(); // BCrypt hashing for secure password storage
}
```

**Purpose**: The PasswordEncoder bean is used to hash user passwords securely before storing them. The `BCryptPasswordEncoder` is a cryptographic algorithm that provides secure, slow hashing designed to be resistant to brute-force attacks.

**Why BCrypt?**: BCrypt is a strong, adaptive hashing algorithm designed to be slow, which makes it more difficult for attackers to crack passwords using brute-force methods.

### UserDetailsService Bean and InMemoryUserDetailsManager

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

### Chained HTTP Security Configuration
A **security filter chain** is a sequence of filters that Spring Security automatically uses to secure the application by handling authentication, authorization, and other security-related tasks.

In Spring Security, the chained method calls you see in the code below provide a **fluent API** to configure various security mechanisms for your web application.

This approach makes the configuration concise, readable, and intuitive. It's important to understand that these methods do not explicitly define individual filters but instead configure how Spring Security’s **security filter chain** behaves.

```java
http
        .authorizeRequests()                     // Step 1: Begin authorization configuration
        .antMatchers("/public/**").permitAll()   // Step 2: Permit access to /public/** for everyone
        .anyRequest().authenticated()           // Step 3: Require authentication for all other requests
        .and()
        .httpBasic();                            // Step 4: Enable HTTP Basic Authentication
```

Essentially:

- this configuration is part of the **security filter chain** in Spring Security, though you're not manually defining each individual filter;
- you are specifying the **behavior** of the security filters that Spring Security will automatically use when processing HTTP requests;
- the filters themselves are built-in components in Spring Security, and this configuration simply influences how they operate;
- you're configuring the **security filter chain**, which is a sequence of security-related filters that Spring Security uses behind the scenes to secure your application;
- when a request is processed, Spring Security applies a series of filters in a specific order to handle different security tasks such as authentication, authorization, and protection against common attacks;
- the chained method calls, such as `.authorizeRequests()` and `.httpBasic()`, modify how these built-in filters behave, but they don’t explicitly define or create new filters;

More details follow:

1. **authorizeRequests()**: This method starts the process of configuring URL-based authorization. It tells Spring Security to begin analyzing incoming HTTP requests and applying the necessary security measures for different URL patterns.

2. **antMatchers("/public/**")**: This method defines the URL pattern to which specific authorization rules will apply. In this case, it targets all URLs that start with `/public/`. This is part of how Spring Security configures the behavior of the **authorization filters**. **ant** in `antMatchers` comes from "Ant-style path patterns", which is a pattern matching system similar to how files are matched in a file system, using wildcards (`*` and `**`).

3. **permitAll()**: After specifying the URL pattern with `antMatchers()`, the `permitAll()` method allows unrestricted access to those URLs. The result is that the URLs matching `/public/**` will be accessible by any user, regardless of whether they are authenticated or not. This alters the behavior of the **authorization filter**, making sure that these URLs are not protected by any authentication rules.

4. **anyRequest().authenticated()**: This method ensures that any request not matching the previously defined patterns (such as `/public/**`) will require authentication. It modifies the behavior of the **authorization filter** to enforce access restrictions for all other URLs.

5. **httpBasic()**: By calling `httpBasic()`, you are instructing Spring Security to use **HTTP Basic Authentication**. This means that Spring Security will check for a username and password in the HTTP request header and will validate them using the `UserDetailsService`. In this case, the `UserDetailsService` is configured to look up user details from an in-memory store, where user credentials are stored.

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

You need to use methods like:

- `antMatchers("/public/**").permitAll()` allows public resources to be accessed without authentication.
- `anyRequest().authenticated()` ensures that all other requests require authentication.
- `httpBasic()` enables Basic Authentication, requiring clients to send credentials in the request header.

### InMemoryUserDetailsManager
Spring Security provides `InMemoryUserDetailsManager` for storing users in memory. This is ideal for small applications or testing where persistence is unnecessary.

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

Spring Security enforces authentication for all incoming requests, except those explicitly permitted (e.g., "/public/**"). When a client sends a request containing Basic Authentication credentials (i.e., a username and password), Spring Security delegates the authentication process to the `userDetailsService` bean.

These credentials are typically included in the HTTP `Authorization` header using the Basic Authentication scheme. The header looks like this:

```
Authorization: Basic dXNlcjpwYXNzd29yZA==
```

Here, `dXNlcjpwYXNzd29yZA==` is the Base64-encoded value of `username:password` (e.g., `user:password`).

Spring Security extracts the credentials from this header and uses the `userDetailsService` (implemented with `InMemoryUserDetailsManager`) to retrieve the user details. It then uses the configured `PasswordEncoder` to compare the incoming plain-text password with the stored, hashed version.

If the password matches, authentication succeeds, and the user gains access to the requested protected resource.

### JdbcUserDetailsManager
For persistent user credentials, use `JdbcUserDetailsManager`, which loads user details from a database.

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

`JdbcUserDetailsManager` works as follows:
- It interacts with a relational database to retrieve user credentials.
- By default, it queries standard Spring Security tables (`users` and `authorities`):

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

- It loads users using SQL queries and validates credentials against the stored encrypted passwords.
- You can customize queries by overriding default SQL statements.

More details follow:
- **`@Bean` annotation**:
  - Marks the method as a bean definition, allowing Spring to manage the lifecycle of the returned object.
  - This method will return a `JdbcUserDetailsManager` bean, which is responsible for managing user details in a JDBC-based store (usually a relational database).

- **Method Parameters**:
  - `DataSource dataSource`: This is the database connection that `JdbcUserDetailsManager` will use to interact with the database.
  - `PasswordEncoder passwordEncoder`: This is the password encoder that will be used to encode passwords before storing them in the database.

- **`JdbcUserDetailsManager` Creation**:
  - `JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);`
    - A new instance of `JdbcUserDetailsManager` is created, initialized with the provided `DataSource`. This class is responsible for user authentication and authorization using a JDBC-based data source.

- **Setting the Password Encoder**:
  - `userDetailsManager.setPasswordEncoder(passwordEncoder);`
    - Explicitly sets the `PasswordEncoder` to be used by the `JdbcUserDetailsManager`.
    - The `PasswordEncoder` ensures that user passwords are securely hashed before being stored in the database and during authentication.

- **Return Statement**:
  - `return userDetailsManager;`
    - Returns the configured `JdbcUserDetailsManager` bean, which will now be managed by Spring and can be injected wherever needed (e.g., for user authentication in Spring Security).

Due to the configuration:

```java
.anyRequest().authenticated()           // Require authentication for all other requests
.and()
.httpBasic();                            // Enable HTTP Basic Authentication
```

Spring Security enforces authentication for all incoming requests, except those explicitly permitted (e.g., "/public/**"). When a client sends a request containing Basic Authentication credentials (i.e., a username and password), Spring Security delegates the authentication process to the `userDetailsService` bean.

In this case, authentication is handled by `JdbcUserDetailsManager`, which retrieves user details from a database via JDBC. This manager uses the configured `PasswordEncoder` to securely store and validate passwords. The credentials are included in the HTTP `Authorization` header using the Basic Authentication scheme:

```
Authorization: Basic dXNlcjpwYXNzd29yZA==
```

Here, `dXNlcjpwYXNzd29yZA==` is the Base64-encoded value of `username:password` (e.g., `user:password`).

Spring Security extracts the credentials from this header, retrieves the corresponding user details from the database using `JdbcUserDetailsManager`, and compares the provided password with the stored, hashed password using the configured `PasswordEncoder`.

If the password matches, authentication succeeds, and the user gains access to the requested protected resource.

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
Set-Cookie: JSESSIONID=12345abcde; Path=/; HttpOnly
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

That is, when no session creation policy is defined in the security filter chain, **Spring Security uses `SessionCreationPolicy.IF_REQUIRED` as the default**. This means that **the decision to create a session is left to the framework** based on the authentication mechanism being used.

However, this behavior can be **unpredictable** because Spring Security might create a session when it deems it necessary (for example, if you're using stateful authentication methods like form login or HTTP Basic). This unpredictability can lead to issues, especially in stateless applications where you don’t want a session to be created.

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
| `SessionCreationPolicy.IF_REQUIRED` (default) | A session is created only when deemed necessary (unpredictable).                   | `JSESSIONID` cookie is set if a session is created. | The client sends the `JSESSIONID` cookie with each request.                   |
| `SessionCreationPolicy.NEVER`                 | No session is created. However, pre-existing session data **might** still be used. | No `JSESSIONID` cookie is set.                      | Authentication must be sent explicitly in request headers (e.g., Basic Auth). |
| `SessionCreationPolicy.STATELESS`             | No session is created, and no state is maintained.                                 | No `JSESSIONID` cookie is set.                      | Authentication must be sent explicitly in request headers (e.g., JWT).        |

---

## CSRF protection
Cross-Site Request Forgery (CSRF) is an attack where an attacker tricks an authenticated user into performing an unwanted action on a web application. The attacker exploits the trust the web application has in the user's browser.

### Unfolding
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

### SameSite Cookie Policy
The **SameSite=Strict** attribute ensures that the cookie is **only sent** when the request originates from the same domain as the cookie. This means the cookie **will not be sent** with **cross-site requests**, even if the user navigates to the site from a different domain or performs an action such as clicking a link from another site.

This security feature helps to prevent **Cross-Site Request Forgery (CSRF)** attacks by ensuring that **cookies** (such as session cookies) are **not automatically sent** with requests made from a different origin (i.e. another website).

```http
HTTP/1.1 200 OK
Set-Cookie: jsessionid=abc123; SameSite=Strict; Path=/; HttpOnly; Secure
```

### Anti-CSRF token
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

#### As a Custom Header
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

#### Within the HTTP Body
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

#### anti-CSRF and Spring Security

how to generate an anti-csrf token in sprnig security
how to check it upon requests
how to enable disable

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/public/**").permitAll()
            .antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/user/**").hasRole("USER")
            .anyRequest().authenticated()
            .and()
            .httpBasic()
            .and()
            .csrf()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.withUsername("admin")
            .password(passwordEncoder.encode("admin"))
            .roles("ADMIN")
            .build();

        UserDetails user = User.withUsername("user")
            .password(passwordEncoder.encode("user"))
            .roles("USER")
            .build();

        return new InMemoryUserDetailsManager(admin, user);
    }
}
```

---

