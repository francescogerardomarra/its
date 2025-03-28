# Spring Security
Spring Security is a comprehensive framework for securing Java applications, especially those built with Spring. It provides a wide array of functionalities like authentication, authorization, protection against common vulnerabilities (e.g., CSRF, session fixation), and integrates easily with both web and enterprise applications.

- **Authentication**: Verifying the identity of the user.
- **Authorization**: Checking if the authenticated user has permission to access a resource.
- **Security Filter Chain**: A series of filters that intercept and process HTTP requests to apply security policies like authentication and authorization.

---

## Setup

### pom.xml

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

### First Example

Here’s a basic Spring Security configuration with HTTP Basic Authentication and in-memory authentication:

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

---

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

- **How Spring Security Uses It**:
    - When an authentication request is made (for example, using HTTP Basic Authentication), Spring Security will call the UserDetailsService to retrieve user information.
    - The InMemoryUserDetailsManager will load the user by their username and compare the provided password with the encoded password stored in memory.
    - If the authentication is successful, the user will be granted access to the requested resource.

---

## Chained HTTP Security Configuration

```java
http
    .authorizeRequests()                     // Step 1: Begin authorization configuration
    .antMatchers("/public/**").permitAll()   // Step 2: Permit access to /public/** for everyone
    .anyRequest().authenticated()           // Step 3: Require authentication for all other requests
    .and()
    .httpBasic();                            // Step 4: Enable HTTP Basic Authentication
```

- **authorizeRequests()**: This begins the configuration of URL-based authorization. It defines which URLs are accessible and who can access them.
- **antMatchers("/public/**").permitAll()**: This allows any user (authenticated or not) to access any URLs matching `/public/**`. These URLs are publicly available.
- **anyRequest().authenticated()**: This requires authentication for any other URL that does not match `/public/**`. If a user tries to access a protected URL without being authenticated, they will be prompted to log in.
- **httpBasic()**: This enables HTTP Basic Authentication. This means that clients will send their username and password in the `Authorization` header of the HTTP request. Spring Security will then validate the credentials using the `UserDetailsService` (which in our case is backed by `InMemoryUserDetailsManager`).

---

# Basic Authentication

## Enabling Basic Authentication
Basic Authentication is a simple way to secure an API or web application by requiring the client to send a username and password in the request header.

You need to use methods like:

- `httpBasic()` enables Basic Authentication, requiring clients to send credentials in the request header.
- `antMatchers("/public/**").permitAll()` allows public resources to be accessed without authentication.
- `anyRequest().authenticated()` ensures that all other requests require authentication.

## In-Memory Authentication
Spring Security provides `InMemoryUserDetailsManager` for storing users and roles in memory. This is ideal for small applications or testing where persistence is unnecessary.

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

### Explanation:
- `InMemoryUserDetailsManager` stores user credentials and roles in memory.
- `passwordEncoder().encode("password")` encodes passwords before storing.

## Authentication with JDBC
For persistent user data, use `JdbcUserDetailsManager`, which loads user details from a database.

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

### Explanation:
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

### How `JdbcUserDetailsManager` Works:
- It interacts with a relational database to retrieve user credentials.
- By default, it queries standard Spring Security tables (`users` and `authorities`).
- It loads users using SQL queries and validates credentials against the stored encrypted passwords.
- You can customize queries by overriding default SQL statements.

### SQL Database Schema
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
    // Create an "admin" user with role "ADMIN"
    UserDetails admin = User.withUsername("admin")
            .password(passwordEncoder.encode("admin"))  // Encode password using the injected PasswordEncoder
            .roles("ADMIN")                               // Assign "ADMIN" role to the user
            .build();

    // Create a "user" user with role "USER"
    UserDetails user = User.withUsername("user")
            .password(passwordEncoder.encode("user"))   // Encode password using the injected PasswordEncoder
            .roles("USER")                                // Assign "USER" role to the user
            .build();

    // Return an InMemoryUserDetailsManager with the two users
    return new InMemoryUserDetailsManager(admin, user);
  }
}
```

### What Happens When a Request Comes Through

#### Request Matching
- Spring Security matches request URLs against the configured `antMatchers` rules:
  - If the URL matches `/public/**`, it is allowed without authentication (`permitAll()`).
  - If the URL matches `/admin/**`, the user must have the "ADMIN" role.
  - If the URL matches `/user/**`, the user must have the "USER" role.
  - Any other request requires authentication (`authenticated()`).

#### Authentication
- If a user is not authenticated, Spring Security triggers basic authentication (`httpBasic()`), prompting for credentials.
- The username and password are verified against the in-memory user store (`InMemoryUserDetailsManager`).

#### Role Authorization
- After authentication, Spring checks if the user has the required role for the requested URL.
- If the user lacks the appropriate role, the request is denied.

#### Successful Access
- If authentication and role checks pass, the request proceeds to the corresponding handler (e.g., a controller method).

#### Unsuccessful Access
- If authentication or role checks fail, an HTTP 401 (Unauthorized) or 403 (Forbidden) response is returned.

This setup ensures that requests are properly secured and only authorized users can access certain parts of the application based on their roles.

---

## Session Management

When Spring Security creates a session, it’s essentially managing the user's state across multiple HTTP requests. HTTP itself is stateless, meaning that each request is independent and doesn’t carry any information about previous requests. A session helps to maintain state by storing data that can be used in subsequent requests (like the user's authentication status, roles, and permissions).

In Spring Security, creating a session means generating a server-side session where certain information about the authenticated user is stored. This session is typically linked to the user’s interaction with the application, ensuring that subsequent requests can reference the same session and access the user’s authentication details without requiring them to log in again.

### JSESSIONID

When Spring Security creates a session, it typically adds a session identifier (JSESSIONID) to the HTTP response. This session identifier is placed in the response as a cookie (by default) or as part of the response headers. This allows the client (usually a browser) to track the session across requests.

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

In your original code, you are configuring Spring Security with the `HttpSecurity` object to specify how session management should be handled. Let’s look at how different session policies affect what happens with sessions.

#### 1. Default Behavior: `SessionCreationPolicy.IF_REQUIRED`

By default, Spring Security uses `SessionCreationPolicy.IF_REQUIRED`. This means that a session is created only when necessary, such as when the user logs in and authentication needs to be persisted across requests.

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

#### 2. `SessionCreationPolicy.ALWAYS`

With `SessionCreationPolicy.ALWAYS`, a session is created for every request, regardless of whether authentication is needed.

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

#### 3. `SessionCreationPolicy.NEVER`

With `SessionCreationPolicy.NEVER`, a session will never be created by Spring Security.

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

#### 4. `SessionCreationPolicy.STATELESS`

With `SessionCreationPolicy.STATELESS`, no session is created, and the application remains fully stateless.

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

### Summary

| Policy                                        | What happens on the server                                  | What happens in the response                        | What happens in subsequent requests                                           |
|-----------------------------------------------|-------------------------------------------------------------|-----------------------------------------------------|-------------------------------------------------------------------------------|
| `SessionCreationPolicy.ALWAYS`                | A session is always created.                                | `JSESSIONID` cookie is set with a session ID.       | The client sends the `JSESSIONID` cookie with each request.                   |
| `SessionCreationPolicy.IF_REQUIRED` (default) | A session is created only when required (e.g., user login). | `JSESSIONID` cookie is set if a session is created. | The client sends the `JSESSIONID` cookie with each request.                   |
| `SessionCreationPolicy.NEVER`                 | No session is created.                                      | No `JSESSIONID` cookie is set.                      | Authentication must be sent explicitly in request headers (e.g., Basic Auth). |
| `SessionCreationPolicy.STATELESS`             | No session is created and no state is maintained.           | No `JSESSIONID` cookie is set.                      | Authentication must be sent explicitly in request headers (e.g., Basic Auth). |

## CSRF Protection

Cross-Site Request Forgery (CSRF) is an attack where an attacker tricks an authenticated user into performing an unwanted action on a web application. The attacker exploits the trust the web application has in the user's browser.

### How a CSRF Attack Unfolds (Step-by-Step)

#### User Login and Session Cookies:
- The user logs into a web application and is authenticated.
- The server issues a session cookie (e.g., `JSESSIONID`), which identifies the user's session.
- The browser automatically includes this session cookie with every request to the server.

#### Attacker's Malicious Website:
- The attacker creates a malicious website that tricks the user into visiting it while logged into the vulnerable site.
- The malicious site sends a state-changing request (e.g., POST, PUT, DELETE) to the vulnerable web application. The browser includes the session cookie with the request, making it seem legitimate.

#### SameSite Cookie Policy:
- If the session cookie is not set with the `SameSite` attribute, it will be sent with cross-site requests, leaving the application vulnerable to CSRF.
  - `SameSite=Strict`: Cookies are only sent if the request originates from the same site.
  - `SameSite=Lax`: Cookies are sent with top-level navigations but not with subrequests.
  - `SameSite=None`: Cookies are sent with both same-site and cross-site requests.

#### Automatic Request Submission:
- The attacker injects JavaScript into the malicious website that automatically sends a state-changing request to the vulnerable web application.

#### Outcome:
- The attacker successfully performs actions that the user didn’t intend by exploiting the user’s session.

### CSRF Protection

In a REST client scenario, Spring Security will generate an anti-CSRF token for each session. This token must be included in state-changing requests (such as POST, PUT, DELETE).

In Spring Security, the CSRF token is typically included in cookies or response headers (depending on your configuration). The client will retrieve this token and include it in the headers of subsequent state-changing requests.

#### 1. CSRF Protection Enabled (Default)

By default, Spring Security enables CSRF protection. When enabled, it will generate a CSRF token for each session, which the client must include in state-changing requests. The token is usually returned to the client as a cookie or in the response headers.

##### Code Example: Spring Security Configuration (CSRF Enabled)

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

### How CSRF Tokens Are Transmitted for REST Clients

#### First Request (GET request):
- When the REST client makes a GET request to the server, Spring Security will send the CSRF token either in the response headers or in a cookie. The cookie is usually named `XSRF-TOKEN`.

#### Subsequent State-Changing Requests (POST, PUT, DELETE):
- After the client retrieves the CSRF token, it will include this token in the `X-CSRF-TOKEN` header of any subsequent state-changing requests (e.g., POST, PUT, DELETE).

##### Example of CSRF Token in Response

```http
HTTP/1.1 200 OK
Set-Cookie: XSRF-TOKEN=csrf-token-value; Path=/; HttpOnly

Response Body:
{
  "message": "Welcome!"
}
```

The client can extract the CSRF token from the `XSRF-TOKEN` cookie and use it in the `X-CSRF-TOKEN` header in subsequent requests.

#### 2. Sending the CSRF Token in Subsequent Requests

Here is an example using Java to send a POST request with the CSRF token:

```java
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;

public class RestClient {
    public static void main(String[] args) throws Exception {
        String urlString = "https://example.com/api/submit";
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("X-CSRF-TOKEN", "csrf-token-value");
        connection.setDoOutput(true);

        String jsonBody = "{\"key\":\"value\"}";
        try (OutputStream os = connection.getOutputStream()) {
            os.write(jsonBody.getBytes("utf-8"));
        }

        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode);
    }
}
```

### 3. Disabling CSRF Protection (If Needed)

If you are working with stateless authentication (e.g., JWT), you may not need CSRF protection. In such cases, you can disable CSRF protection:

```java
@Override
protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/public/**").permitAll()
        .antMatchers("/admin/**").hasRole("ADMIN")
        .antMatchers("/user/**").hasRole("USER")
        .anyRequest().authenticated()
        .and()
        .httpBasic();
}
```

---

