# Jwt Hello World

We'll enhance an already built Spring Boot REST web service by integrating **JWT authentication**. Our goal is to secure specific endpoints while keeping others publicly accessible. Specifically, we’ll secure the endpoints `/shop/items` and `/shop/users`, allowing **admin-only** access using **JWT-based stateless authentication**.

We will build on a previously implemented Spring Boot 3.4.4 project using **Java 21**, introducing a robust and scalable authentication flow. The process will include utilizing **Basic Authentication** for the login and generating a **JWT token** that needs to be included in the `Authorization` header as a `Bearer` token when accessing protected resources.

The key steps for securing the admin-only endpoints are as follows:
1. An admin will send a **POST request** to `/shop/login` using **Basic Authentication**.
2. If the credentials are valid, a **JWT token** will be generated and returned.
3. The generated JWT token must be included as a `Bearer` token in the `Authorization` header to access protected endpoints such as `/shop/items` and `/shop/users`.

---

## pom.xml

To enhance an already built Spring Boot REST web service by integrating **JWT authentication**, we need to add the following dependencies to the `pom.xml`. Each of these plays a crucial role in enabling secure authentication and authorization using JSON Web Tokens (JWTs).

**spring-boot-starter-security**
```xml
<!-- Spring Boot Starter Security for enabling authentication and authorization features in the application -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
    <version>3.4.4</version> <!-- Updated Spring Boot version for security support -->
</dependency>
```
This dependency brings in Spring Security, which is the backbone of our authentication and authorization mechanisms. It provides the framework for securing endpoints, managing credentials, and integrating JWT authentication seamlessly.

**jjwt-api**
```xml
<!-- JJWT API - Provides the interface for creating and parsing JSON Web Tokens (JWTs). -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.11.5</version>
</dependency>
```
This is the core library that defines the API for creating, signing, and parsing JWT tokens.

**jjwt-impl**
```xml
<!-- JJWT Impl - Contains the implementation for creating and verifying JWTs, including signing and validation. -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.11.5</version>
    <scope>runtime</scope> <!-- Runtime scope indicates that this dependency is required only at runtime -->
</dependency>
```
This dependency contains the actual implementation for JWT creation, signing, and verification. It's needed at runtime to ensure tokens are properly generated and validated.

**jjwt-jackson**
```xml
<!-- JJWT Jackson - Provides support for serializing and deserializing JWTs using Jackson, which is the JSON library used by Spring Boot. -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.11.5</version>
    <scope>runtime</scope> <!-- Runtime scope as this is used only during runtime to work with JWTs -->
</dependency>
```
This integrates Jackson with JJWT to handle the conversion of JWT claims to and from JSON.

**jackson-databind**
```xml
<!-- Jackson Databind - Core dependency for JSON binding (serialization/deserialization) used by Spring Boot. -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.15.0</version>
</dependency>
```
Manually added to ensure Jackson is available when dealing with JWTs. Spring Boot would normally provide this via `spring-boot-starter-web`, but it’s explicitly added here to ensure compatibility and avoid class resolution issues when JWT dependencies are included.

**jackson-core**
```xml
<!-- Jackson Core - Core library for Jackson, needed for JSON processing, including parsing JSON into objects. -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-core</artifactId>
    <version>2.15.0</version>
</dependency>
```
A core part of the Jackson library that is essential for parsing and generating JSON.
Similar to `jackson-databind`, this was not necessary before adding JWT dependencies, but now is required to avoid issues.

**jackson-datatype-jsr310**
```xml
<!-- Jackson JSR310 - Jackson module for handling Java 8 Date/Time API types (like LocalDate, LocalDateTime, etc.). -->
<dependency>
    <groupId>com.fasterxml.jackson.datatype</groupId>
    <artifactId>jackson-datatype-jsr310</artifactId>
    <version>2.15.0</version>
</dependency>
```
Provides support for serializing and deserializing Java 8 Date and Time API objects like `LocalDateTime`, which is particularly useful when timestamps are part of JWT payloads or API responses.

**Full snippet**
````xml
        <!-- Spring Boot Starter Security for enabling authentication and authorization features in the application -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
            <version>3.4.4</version> <!-- Updated Spring Boot version for security support -->
        </dependency>

        <!-- JJWT API - Provides the interface for creating and parsing JSON Web Tokens (JWTs). -->
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-api</artifactId>
            <version>0.11.5</version>
        </dependency>

        <!-- JJWT Impl - Contains the implementation for creating and verifying JWTs, including signing and validation. -->
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-impl</artifactId>
            <version>0.11.5</version>
            <scope>runtime</scope> <!-- Runtime scope indicates that this dependency is required only at runtime -->
        </dependency>

        <!-- JJWT Jackson - Provides support for serializing and deserializing JWTs using Jackson, which is the JSON library used by Spring Boot. -->
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-jackson</artifactId>
            <version>0.11.5</version>
            <scope>runtime</scope> <!-- Runtime scope as this is used only during runtime to work with JWTs -->
        </dependency>

        <!-- Jackson Databind - Core dependency for JSON binding (serialization/deserialization) used by Spring Boot. -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.15.0</version>
            <!-- Explicitly added because JWT dependencies (like jjwt-jackson) do not include Jackson by default -->
            <!-- Before adding JWT-related dependencies, Spring Boot included Jackson automatically as part of the 'spring-boot-starter-web' dependency -->
            <!-- After adding JWT dependencies, we need to manually include Jackson to ensure compatibility with Spring Boot and avoid issues with missing classes (like 'DatatypeFeature') -->
        </dependency>

        <!-- Jackson Core - Core library for Jackson, needed for JSON processing, including parsing JSON into objects. -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-core</artifactId>
            <version>2.15.0</version>
            <!-- Explicitly added to ensure compatibility with Spring Boot and other Jackson modules -->
            <!-- Similar to 'jackson-databind', this was not necessary before adding JWT dependencies, but now is required to avoid issues. -->
        </dependency>

        <!-- Jackson JSR310 - Jackson module for handling Java 8 Date/Time API types (like LocalDate, LocalDateTime, etc.). -->
        <dependency>
            <groupId>com.fasterxml.jackson.datatype</groupId>
            <artifactId>jackson-datatype-jsr310</artifactId>
            <version>2.15.0</version>
            <!-- Necessary for properly serializing and deserializing Java 8 Date/Time types. -->
            <!-- Added to handle Date/Time types properly, which may now be needed when using Spring Boot with JWTs. -->
        </dependency>
````

---

## application.properties

### Logging Configuration
```properties
logging.level.org.springframework.security=DEBUG
logging.level.org.springframework.web=DEBUG
```
- `logging.level.org.springframework.security=DEBUG`: Enables detailed logging for Spring Security internals—useful for debugging authentication and authorization issues.
- `logging.level.org.springframework.web=DEBUG`: Enables detailed logging for HTTP requests and controller responses within Spring Web.

### Basic Authentication
```properties
admin.username=admin
admin.password=password123
```
- `admin.username`: Defines the static username for admin login.
- `admin.password`: Defines the static password for admin login.
  These credentials can be used for initial or fallback access where JWTs are not yet generated.

### JWT Configuration
```properties
jwt.secret=VGhpcyBpcyBhIHNlY3VyZSBqZXN0IGtleSB0aGF0IGlzIGJlYXJlZCBvbiBhIFNoYTI1NiBtZWRpYSB0aGF0IGlzIGxvbmcgaW5vdWdoIHRoZSBjZXJ0YWluIHNlY3VyaXR5IHN0YW5kYXJkLCBhbmQgaXMgZXhwbGVjdGVkIHRvIGJlIGF1dGhvcml6ZWQuIFRoaXMga2V5IHdpbGwgYmUgc2lnbmVkIHdpdGggc2lnbmVkYXR1cmUgYWxnb3JpdGhtcyBzbyBpdCBpcyBhIHN0cm9uZyBzZWNyZXQuCg==
```
- This is a **Base64-encoded** string representing the JWT secret key.
- The encoded secret is used with the `HS512` algorithm to sign and validate JWTs.
- **HS512** requires a long, secure key to avoid brute-force attacks. A short key could compromise token integrity.
- **HS512** requires the key to be at least **512 bits (64 bytes)**, and this is the length of the **decoded key**.
- The **decoded key** is the one that determines whether it meets the size requirements for **HS512**.
- **Decoded value:**
  ```
  This is a secure jwt key that is beared on a Sha256 media that is long inouh the certain security standard, and is expected to be authorized.
  ```
- Even though the decoded version looks human-readable, it’s not directly used in code. The encoded value is used to ensure security compatibility.
- **Decoded length**: This decoded key has a length of **192 characters**, and since each character is **1 byte**, this gives us a **192-byte key**.
- **HS512** requires a **64-byte (512-bit)** minimum length, so this key is **longer than necessary** for **HS512**, making it suitable.

```properties
admin.jwt.claim.sub=admin_sub
admin.jwt.claim.role=admin
admin.jwt.claim.permission=read,write,delete
admin.jwt.claim.expiration.ms=600000
```
- `admin.jwt.claim.sub`: The subject (`sub`) claim that identifies the principal—typically the username or user ID.
- `admin.jwt.claim.role`: Sets the `role` claim in the JWT—used to authorize endpoints.
- `admin.jwt.claim.permission`: A custom `permission` claim listing specific actions allowed by the token bearer.
- `admin.jwt.claim.expiration.ms`: JWT expiration time in milliseconds (10 minutes here).

---

### Final application.properties
```properties
# Logging
logging.level.org.springframework.security=DEBUG
logging.level.org.springframework.web=DEBUG

# Basic authentication
admin.username=admin
admin.password=password123

# JWT
jwt.secret=VGhpcyBpcyBhIHNlY3VyZSBqZXN0IGtleSB0aGF0IGlzIGJlYXJlZCBvbiBhIFNoYTI1NiBtZWRpYSB0aGF0IGlzIGxvbmcgaW5vdWdoIHRoZSBjZXJ0YWluIHNlY3VyaXR5IHN0YW5kYXJkLCBhbmQgaXMgZXhwbGVjdGVkIHRvIGJlIGF1dGhvcml6ZWQuIFRoaXMga2V5IHdpbGwgYmUgc2lnbmVkIHdpdGggc2lnbmVkYXR1cmUgYWxnb3JpdGhtcyBzbyBpdCBpcyBhIHN0cm9uZyBzZWNyZXQuCg==

admin.jwt.claim.sub=admin_sub
admin.jwt.claim.role=admin
admin.jwt.claim.permission=read,write,delete
admin.jwt.claim.expiration.ms=600000
```

---

## Directory Structure
After integrating JWT authentication, the project directory structure is updated with the addition of new classes. Here’s a breakdown of the key changes:

- **LoginController.java**: This new controller is added under `com.example.controller` to handle the login process using Basic Authentication. It is responsible for authenticating the admin and generating the JWT token.

- **Security-related classes**: A new package `com.example.security` is introduced, which contains the following classes:
    - **AdminUserConfig.java**: This class defines configurations specific to the admin user, ensuring proper access control and security settings.
    - **SecurityConfig.java**: This configuration class handles the overall security settings for the application, including JWT authentication and authorization mechanisms.
    - **AdminTokenProvider.java**: Responsible for generating and validating JWT tokens specifically for admin users.
    - **AuthenticationTokenFilter.java**: This filter intercepts incoming requests to check for a valid JWT token in the request headers, ensuring protected endpoints are only accessible with valid tokens.

These new classes are part of the security mechanism that enforces JWT authentication for certain endpoints, enabling admin-only access.

````text
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── example
    │   │           ├── controller
    │   │           │   ├── ItemController.java
    │   │           │   ├── LoginController.java
    │   │           │   ├── OrderController.java
    │   │           │   ├── OrderItemController.java
    │   │           │   └── UserController.java
    │   │           ├── dto
    │   │           │   ├── item
    │   │           │   │   └── ItemDTO.java
    │   │           │   ├── order
    │   │           │   │   └── OrderDTO.java
    │   │           │   ├── orderitem
    │   │           │   │   └── OrderItemDTO.java
    │   │           │   └── user
    │   │           │       └── UserDTO.java
    │   │           ├── model
    │   │           │   ├── enums
    │   │           │   │   └── OrderStatus.java
    │   │           │   ├── Item.java
    │   │           │   ├── key
    │   │           │   │   └── OrderItemKey.java
    │   │           │   ├── OrderItem.java
    │   │           │   ├── Order.java
    │   │           │   └── User.java
    │   │           ├── repository
    │   │           │   ├── ItemRepository.java
    │   │           │   ├── OrderItemRepository.java
    │   │           │   ├── OrderRepository.java
    │   │           │   └── UserRepository.java
    │   │           ├── rest
    │   │           │   ├── request
    │   │           │   │   ├── item
    │   │           │   │   │   └── ItemRequest.java
    │   │           │   │   ├── order
    │   │           │   │   │   ├── OrderRequest.java
    │   │           │   │   │   └── RemoveOrderRequest.java
    │   │           │   │   ├── orderitem
    │   │           │   │   │   ├── AddItemToOrderRequest.java
    │   │           │   │   │   └── RemoveItemFromOrderRequest.java
    │   │           │   │   └── user
    │   │           │   │       ├── RemoveUserRequest.java
    │   │           │   │       └── UserRequest.java
    │   │           │   └── response
    │   │           │       ├── item
    │   │           │       │   └── ItemResponse.java
    │   │           │       ├── order
    │   │           │       │   ├── OrderResponse.java
    │   │           │       │   └── RemoveOrderResponse.java
    │   │           │       ├── orderitem
    │   │           │       │   ├── AddItemToOrderResponse.java
    │   │           │       │   └── RemoveItemFromOrderResponse.java
    │   │           │       └── user
    │   │           │           ├── RemoveUserResponse.java
    │   │           │           └── UserResponse.java
    │   │           ├── security
    │   │           │   ├── config
    │   │           │   │   ├── admin
    │   │           │   │   │   └── AdminUserConfig.java
    │   │           │   │   └── chain
    │   │           │   │       └── SecurityConfig.java
    │   │           │   └── jwt
    │   │           │       ├── provider
    │   │           │       │   └── AdminTokenProvider.java
    │   │           │       └── filter
    │   │           │           └── AuthenticationTokenFilter.java
    │   │           ├── service
    │   │           │   ├── ItemService.java
    │   │           │   ├── OrderItemService.java
    │   │           │   ├── OrderService.java
    │   │           │   └── UserService.java
    │   │           └── WebServiceRest.java
    │   └── resources
    │       └── application.properties
    └── test
        └── java
````

---

## AdminTokenProvider
The `AdminTokenProvider` is a Spring-managed component responsible for generating, validating, and parsing JWT tokens specifically for administrative users. It encapsulates the logic to securely construct a JWT token with defined claims (subject, role, permissions), sign it with a secret key, and subsequently validate and parse incoming tokens to authorize admin-level access.

This bean is a central security component used in the authentication and authorization flows:

- **In `AuthenticationTokenFilter`**, it is injected and used to:
    - `validateToken(token)`: Ensure the token is authentic and has not expired.
    - `getClaims(token)`: Extract claims to identify the admin's identity, role, and permissions.

- **In `LoginController`**, it is used to generate tokens upon successful login:
  ```java
  String token = tokenProvider.generateToken();
  ```
  This token is returned to the client for future authenticated requests.

### Annotations
- `@Component`: This annotation marks `AdminTokenProvider` as a Spring component, allowing Spring to detect it during component scanning and manage it as a singleton bean. It makes the class eligible for dependency injection into other beans like `AuthenticationTokenFilter` and `LoginController`.

- `@Value`: Used to inject configuration properties (defined in the application properties file) into the bean's fields. This includes the JWT secret, claims such as subject, role, permissions, and expiration duration.

### `generateToken()`

```java
public String generateToken() {
    Key secretKey = new SecretKeySpec(secretKeyString.getBytes(), SignatureAlgorithm.HS512.getJcaName());

    return Jwts.builder()
            .setSubject(subject)
            .claim("role", role)
            .claim("permissions", permissions)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
            .signWith(secretKey, SignatureAlgorithm.HS512)
            .compact();
}
```

**Explanation:**
- Initializes a secret key from the configured secret string using the HS512 algorithm.
- Constructs a JWT token using the JJWT builder:
    - Sets the `subject`, `role`, and `permissions` claims.
    - Assigns the current time as the issue date.
    - Calculates the expiration based on the configured duration.
    - Signs the token with the generated secret key.
- Returns the final compact JWT string.

### `getClaims(String token)`

```java
public Claims getClaims(String token) {
    Key secretKey = new SecretKeySpec(secretKeyString.getBytes(), SignatureAlgorithm.HS512.getJcaName());

    return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .getBody();
}
```

**Explanation:**
- Reconstructs the secret key used to sign the JWT.
- Uses JJWT's parser to:
    - Validate the signature.
    - Parse the token and extract the `Claims` (like subject, role, and permissions).
- Returns only the claims body of the token.

This method is crucial for extracting meaningful data from the token, used during request authorization.

### `validateToken(String token)`

```java
public boolean validateToken(String token) {
    try {
        Claims claims = getClaims(token);

        Date expirationDate = claims.getExpiration();
        if (expirationDate.before(new Date())) {
            throw new JwtException("Token has expired");
        }

        return true;
    } catch (JwtException e) {
        return false;
    }
}
```

**Explanation:**
- Retrieves the claims from the token.
- Checks if the token's expiration time has passed.
- If valid and not expired, returns `true`.
- Catches and handles `JwtException` to safely return `false` for invalid or expired tokens.

This validation is fundamental to the security of any endpoint protected by JWT-based authentication.

The `AdminTokenProvider` is thus a reusable, secure, and configurable utility for JWT management, facilitating both token generation during login and token verification during request filtering.

````java
package com.example.security.jwt.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

/**
 * This class is responsible for generating and validating JWT tokens for admin users.
 * It allows you to create a JWT token with claims such as subject, role, and permissions,
 * as well as validate and extract the claims from an incoming JWT token.
 * <p>
 * The JWT token is signed using a secret key, which is injected from the application properties.
 * This class also provides the logic to validate whether the token has expired.
 * <p>
 * Flow:
 * 1. The JWT token is generated with various claims (subject, role, permissions) and signed with a secret key.
 * 2. The generated token can be returned to the user (e.g., after a successful login).
 * 3. When the token is sent back to the server, it can be validated to ensure the user is authenticated and authorized.
 * 4. Claims from the token can be extracted and used to make authorization decisions.
 */
@Component
public class AdminTokenProvider {

    // Injected properties for secret key and various claims.
    @Value("${jwt.secret}")
    private String secretKeyString;

    @Value("${admin.jwt.claim.sub}")
    private String subject;  // The subject for the JWT comes from properties file.

    @Value("${admin.jwt.claim.role}")
    private String role;  // The role claim comes from properties file.

    @Value("${admin.jwt.claim.permission}")
    private String permissions;  // Permissions are specified in the properties file.

    @Value("${admin.jwt.claim.expiration.ms}")
    private long expirationTime;  // Token expiration time is specified in the properties file.

    /**
     * Generates a JWT token with the subject, role, permissions, and expiration time.
     * <p>
     * This method uses the JJWT library to create a JWT token, adds claims (such as subject, role, and permissions),
     * sets the issued and expiration times, and signs the token with the secret key.
     *
     * @return The generated JWT token as a string.
     */
    public String generateToken() {
        /**
         * Initializes the secret key from the provided secret string in the application properties.
         * This secret key is used to sign the JWT token with the HS512 algorithm.
         *
         * The secret key is passed to the `SecretKeySpec` constructor to create a `Key` object
         * which will be used to sign the JWT token.
         */
        Key secretKey = new SecretKeySpec(secretKeyString.getBytes(), SignatureAlgorithm.HS512.getJcaName());

        return Jwts.builder()
                .setSubject(subject)  // Set the subject for the JWT token (admin identity).
                .claim("role", role)  // Add the user's role to the claims.
                .claim("permissions", permissions)  // Add the user's permissions to the claims.
                .setIssuedAt(new Date())  // Set the current date/time as the token issue date.
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))  // Set the token expiration time.
                .signWith(secretKey, SignatureAlgorithm.HS512)  // Sign the token with the secret key using HS512 algorithm.
                .compact();  // Return the JWT token as a string.
    }

    /**
     * Extracts and parses the claims from the provided JWT token.
     * <p>
     * The JWT token is parsed using the secret key, and the claims are returned in the form of a `Claims` object.
     * The claims can include subject, role, permissions, and expiration date, which are used for authorization checks.
     *
     * @param token The JWT token from which the claims will be extracted.
     * @return The claims extracted from the JWT token.
     */
    public Claims getClaims(String token) {
        /**
         * Initializes the secret key from the provided secret string in the application properties.
         * This secret key is used to sign the JWT token with the HS512 algorithm.
         *
         * The secret key is passed to the `SecretKeySpec` constructor to create a `Key` object.
         */
        Key secretKey = new SecretKeySpec(secretKeyString.getBytes(), SignatureAlgorithm.HS512.getJcaName());

        // Parse the JWT token and extract the claims using the secret key.
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)  // Set the secret key to validate the JWT signature.
                .build()
                .parseClaimsJws(token)  // Parse the JWT and extract claims.
                .getBody();  // Return the claims body (excluding the header and signature).
    }

    /**
     * Validates the provided JWT token.
     * <p>
     * This method validates the token by checking if the signature is correct and if the token has expired.
     * If the token is valid, it returns `true`; otherwise, it returns `false`.
     *
     * @param token The JWT token to validate.
     * @return `true` if the token is valid, `false` if the token is invalid or expired.
     */
    public boolean validateToken(String token) {
        try {
            // Extract claims from the token to check validity.
            Claims claims = getClaims(token);

            // Check if the token has expired by comparing the expiration date with the current time.
            Date expirationDate = claims.getExpiration();
            if (expirationDate.before(new Date())) {
                throw new JwtException("Token has expired");  // Token has expired, throw an exception.
            }

            // Token is valid if not expired and signature is correct.
            return true;
        } catch (JwtException e) {
            // Return false if the token is invalid (e.g., incorrect signature, expired).
            return false;
        }
    }
}
````

---

## AuthenticationTokenFilter
The `AuthenticationTokenFilter` is a custom filter that is responsible for extracting, validating, and processing JWT tokens from incoming HTTP requests.

This is the definition of a custom filter that will later be registered with Spring Security to apply JWT authentication.

In Spring Security, using `HttpSecurity`, you can register a custom filter and integrate it into the security filter chain. This registration will happen in the `SecurityConfig` class, where the filter will be added to the security filter chain to ensure that custom authentication logic, such as JWT token validation, is applied. By doing so, the custom filter ensures that only authenticated requests are allowed to access protected resources.

### Overview about Filters
A **filter** is an object that performs filtering tasks on either the **request** or the **response** in a Spring-based application. Filters are part of the servlet container and allow you to manipulate the request and/or response before it reaches the targeted servlet or after the servlet has processed it.

In Spring, filters are commonly used to process requests or responses in a way that is **independent of the business logic**. Filters sit in between the client and the servlet (or controller), intercepting and potentially modifying the request and/or response before the request reaches the servlet or after the servlet generates the response.

#### Definition, Chaining, Registration
From a theoretical point of view, the implementation lifecycle of a filter can be broken down into three main stages:

1. **Definition**: In this stage, the filter is created by implementing the `jakarta.servlet.Filter` interface or by extending an abstract class like `GenericFilter`. The filter class contains the logic that defines how incoming requests and outgoing responses should be processed, typically by overriding methods like `doFilter()`.
2. **Chaining**: As part of the definition, the **filter chain** comes into play. A filter chain allows multiple filters to be executed in a specific order. When the `doFilter()` method is called, the filter can either continue processing the request by calling `chain.doFilter(request, response)` or halt the chain. This chaining mechanism ensures that each filter in the chain can perform its task before passing the request to the next filter or the target resource (like a servlet or a JSP).
3. **Registration**: Once defined, the filter must be registered with the web application so that it can be invoked during the request-response cycle. This registration can be done either through the `web.xml` file (in older servlet versions) or via annotations like `@WebFilter` (in modern servlet-based applications). The filter is mapped to specific URL patterns or servlet paths to determine when it should be executed.

In summary, the filter lifecycle involves:
- **Definition** of the filter's behavior.
- **Chaining** of filters to ensure that multiple filters can work in sequence on the request and response.
- **Registration** of the filter within the web application's configuration.

#### Lifecycle
As soon as a request is made to the application, a **request-response pair** is created and associated throughout the entire request lifecycle. Here's how this flow works in the context of filters:

1. **Request Handling**:
    - When the HTTP request reaches the application, a **request object** is created, and a **response object** is also prepared, even though it is initially empty (the response body hasn't been generated yet). This response object is created early by the servlet container to ensure that a response can be sent back to the client once the request is processed.
    - Both the request and response are passed through the filter chain together.

2. **Filters and Request-Response Interaction**:
    - Filters receive both the **request** and **response** objects and can interact with them.
    - **Request Interception**: Filters can inspect, log, or modify the incoming **request** before it reaches the servlet or controller.
    - **Response Interception**: Even before the controller processes the request, the filter has access to the **response** object. Although the response body is empty at this point, filters can set or modify **response headers**, like setting authentication or CORS headers.

   The key here is that the **response object** exists from the very beginning of the request lifecycle, even before the controller generates any content for it. Filters can modify headers or perform actions like logging, authentication checks, or other pre-processing tasks on the response, even though its body hasn't been populated yet.

3. **Controller Processing**:
    - After the request passes through all filters, it reaches the controller, where the request is processed. The controller generates content for the **response** object, such as setting the HTTP status, adding headers, and generating the response body.

4. **Post-Processing by Filters**:
    - Once the controller has generated the response, the response object travels back through the filter chain. Filters can now inspect or modify the **response** before it is sent to the client. This is typically where you can perform logging or modify the content of the response (e.g., adding custom headers or logging response times).

Filters can intercept and modify both **requests** and **responses** at various stages of the HTTP request lifecycle:
- **Request Interception**: Filters can alter or inspect the request before it reaches the servlet or controller.
- **Response Interception**: Filters can manipulate the response that is sent back to the client after the servlet has processed the request.

Filters have the ability to:
- **Modify the request**: Add additional headers, parameters, or modify the request body.
- **Log request details**: Useful for auditing or debugging.
- **Authenticate requests**: A typical use case is JWT (JSON Web Token) authentication.
- **Modify the response**: Add headers, change the response body, or handle logging.
- **Redirect or terminate the request/response**: For example, if authentication fails, you might stop the request processing and return a 401 Unauthorized response.

#### Use Cases
**Authentication**
A filter can intercept incoming requests to verify the presence of a JWT token in the request headers, validate the token, and allow or deny access to the resources based on the token's validity.

**Logging**
A filter can log details about incoming requests (e.g., request method, URL, headers, body) for auditing, debugging, or monitoring purposes.

#### Filter Chain
A **filter chain** is a sequence of filters that process a request and/or response in the order they are defined. Each filter in the chain can:

1. Perform its own task (e.g., authentication, logging, etc.).
2. Decide whether to pass control to the next filter in the chain.
3. Optionally modify the request or response.

The **Filter Chain** is an essential part of Spring’s filter mechanism. The filter chain determines the order in which filters are executed and ensures that the request and response are passed from one filter to another, ultimately reaching the servlet or controller.

#### jakarta.servlet.Filter
In Spring Boot, a filter is an object that implements the `jakarta.servlet.Filter` interface.

The most generic approach is to directly implement the `jakarta.servlet.Filter` interface, overriding its methods:

````java
package jakarta.servlet;

public interface Filter {
    void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
        throws IOException, ServletException;

    void init(FilterConfig filterConfig) throws ServletException;
    void destroy();
}
````

The `jakarta.servlet.Filter` interface has the three key methods above.

If a filter is correctly **defined, registered and chained**, its three methods (`init()`, `doFilter()`, and `destroy()`) will be called automatically by the servlet container at the appropriate points in the lifecycle. Here's how each method fits into the lifecycle:

1. **`init(FilterConfig filterConfig)`**:
    - This method is called once when the filter is created. It’s typically used for initialization tasks, like reading configuration parameters, setting up resources, or preparing anything that the filter needs to operate. The `FilterConfig` provides access to the filter's initialization parameters and the `ServletContext` for the filter.

2. **`doFilter(ServletRequest request, ServletResponse response, FilterChain chain)`**:
    - This is the main method that intercepts the request and response. You can modify the request and response, perform logging, check authentication, manipulate headers, or any other tasks before passing control along the chain.
    - **Request-Response Pair**: As soon as a request is made to the application, a **request-response pair** is created and associated throughout the entire request lifecycle. This pair represents the HTTP request (from the client) and the response (that will be sent back to the client). In the context of filters:
        - The `request` object represents the HTTP request received from the client.
        - The `response` object represents the HTTP response that will be returned to the client.
    - You **must** call `chain.doFilter(request, response)` to pass the request and response to the next filter or servlet in the chain. If you don't call this method, the request/response will be stuck in the filter, and no further processing will occur. It’s crucial to allow the request to continue through the chain to complete the processing and return the response.
    - If you don't call `chain.doFilter(request, response)` in any filter—whether it's the last one or not:
        - The **request will not proceed** to the next filter or to the controller (or servlet).
        - The **response will not be generated** or sent back to the client.
        - The **request will be blocked** in the current filter.
        - Any **subsequent filters, including the controller or servlet, will not be reached**.
        - The **response will not be processed** either.
    - The `doFilter` method provides `ServletRequest` and `ServletResponse` objects, which are generic types. In many cases, you'll need more specific functionality, such as working with HTTP-specific headers, request parameters, or status codes. This is where **casting** becomes useful:
        - **`HttpServletRequest`**: By casting the generic `ServletRequest` to an `HttpServletRequest`, you can access HTTP-specific features like headers, cookies, parameters, and methods such as `getMethod()` or `getRequestURI()`.
        - **`HttpServletResponse`**: Similarly, casting the generic `ServletResponse` to an `HttpServletResponse` allows you to interact with HTTP-specific features like status codes, headers, and the response body.
      
3. **`destroy()`**:
    - This method is called when the filter is destroyed, typically when the servlet container shuts down or when the filter is no longer needed. You can use this method for cleanup tasks, such as releasing resources (e.g., closing database connections or cleaning up thread pools). The container automatically calls this method before destroying the filter instance.

As mentioned earlier, when a request is made to the application, a **request-response pair** is created and associated throughout the entire request lifecycle. Here’s how this flow works in the context of filters:

- When a request is made, the `ServletRequest` object is created to represent the HTTP request.
- The `ServletResponse` object is created to represent the HTTP response that will be sent back to the client.
- In the `doFilter()` method, the filter can inspect and modify both the request and the response objects before passing them to the next entity in the chain (whether it’s another filter or the target servlet).
- The filter must call `chain.doFilter(request, response)` to continue the request-response flow, allowing other filters or the servlet to process the request and generate a response.
- Without calling `chain.doFilter()`, the request/response processing will be halted in the filter, and no further action will be taken.

#### OncePerRequestFilter, GenericFilterBean
Apart from implementing this interface directly, a filter can be defined by extending specific abstract filter classes always of type `jakarta.servlet.Filter`.

These abstract classes simply offer default implementations of some of the methods from the `Filter` interface, allowing you to focus on overriding just the `doFilter()` method if no other customization is needed. You can define a filter either by directly implementing the `jakarta.servlet.Filter` interface or by extending an abstract class like `GenericFilter`, which is still part of the `jakarta.servlet.Filter` ecosystem.

Available Abstract Classes for Filter Implementation:

1. **`OncePerRequestFilter`**:
    - This class ensures that the filter is executed only once per request, even if the request undergoes multiple forwarding or dispatching during its lifecycle. In traditional servlet-based applications, requests can be forwarded from one resource to another (e.g., from one servlet to another or from a controller to a view). This means that a single HTTP request may pass through multiple resources, and without proper handling, a filter might execute multiple times during these forwards.
    - **Forwarding and Its Impact**: When forwarding occurs, the same HTTP request is internally routed to a different resource without the client being aware. However, this can cause filters to be executed repeatedly if the forwarding process isn't managed carefully. For example, if a filter is responsible for tasks like logging, authentication, or setting headers, it could execute multiple times across different forwards, resulting in duplicate log entries, unnecessary re-authentication, or redundant changes to the response.
    - **How `OncePerRequestFilter` Avoids Multiple Executions**: The `OncePerRequestFilter` class is specifically designed to prevent filters from running multiple times during the same request, even when forwarding or dispatching occurs. It achieves this by ensuring that the filter's actions are only performed once during the entire request lifecycle. The filter tracks whether it has already been executed for the current request and skips further executions if it has.
    - This is especially important when filters are involved in critical tasks like session validation, logging, or resource cleanup. Without this safeguard, a filter could cause unintended side effects if executed multiple times, potentially leading to errors or performance issues.
    - **Benefits**:
        - **Simplicity**: The `OncePerRequestFilter` reduces the complexity of filter management by handling forwarding and dispatching transparently, without requiring developers to manually track filter executions.
        - **Efficiency**: It ensures that the filter only performs necessary tasks once, preventing redundant actions and improving the overall efficiency of the web application.
        - **Consistency**: By guaranteeing that the filter runs only once, it avoids issues like double logging or reapplying filters that could cause inconsistencies in behavior.

    - **Example Use Case**: Imagine a filter that logs request details for monitoring purposes. Without `OncePerRequestFilter`, this logging could be duplicated if the request is forwarded to several resources (like different servlets or views). By using `OncePerRequestFilter`, the logging will happen only once for the entire request, regardless of how many forwards or dispatches occur.

2. **`GenericFilterBean`**:
    - This is a Spring-specific class that implements the `jakarta.servlet.Filter` interface and offers more Spring-friendly features, such as dependency injection.
    - It allows you to easily integrate Spring beans into your filter, which is helpful for service or repository injections.

#### Registration Methods
In Spring Boot, filters can be defined by either implementing the `jakarta.servlet.Filter` interface or by extending a superclass like `OncePerRequestFilter`. After defining the filter, you need to register it to make it part of the Spring Boot application context. Depending on how you define and register the filter, the filter may or may not be a Spring Bean. Below are the main methodologies for filter registration:

1. **Registering a Filter as a Bean in a `@Configuration` Class**:
    - **Filter Definition**: The filter is defined as a standard class, often implementing the `jakarta.servlet.Filter` interface or extending `OncePerRequestFilter`. It is not necessarily a Spring Bean at this point.
    - **Registration Flow**: The filter is **registered as a Spring Bean** within a `@Configuration` class, typically using the `@Bean` annotation. The registration is done through a `FilterRegistrationBean`, which allows you to configure details such as URL patterns, filter order, and other properties.
    - **Bean Creation**: The filter itself is **not a Spring Bean initially**, but it is instantiated and registered as a Spring Bean during the configuration process. The `FilterRegistrationBean` acts as a container that allows customization of the filter's behavior.
    - **Summary**: This approach gives you full control over the filter's lifecycle, and the filter is explicitly registered as a Spring Bean during the registration process.

````java
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class FilterConfig {

    // Registering the filter as a Spring Bean within a @Configuration class
    @Bean
    public FilterRegistrationBean<CustomFilter> registrationBean() {
        FilterRegistrationBean<CustomFilter> registrationBean = new FilterRegistrationBean<>();
        
        // Create an instance of CustomFilter and register it
        registrationBean.setFilter(new CustomFilter());
        
        // Specify URL patterns where the filter should apply
        registrationBean.addUrlPatterns("/api/*");

        // Set the filter order to 1, which means this filter has the highest priority 
        // and will be executed first in the filter chain. Filters with lower order values 
        // are given higher priority and are executed before those with higher order values.
        // This setting ensures that this filter runs early in the processing sequence, 
        // allowing it to handle requests or responses before any other filters with a higher 
        // order (i.e., values greater than 1) are applied.
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
````

````java
// Custom filter class implementing jakarta.servlet.Filter
public class CustomFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Filter initialization logic (if needed)
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // Custom logic for request processing (e.g., JWT validation)
        System.out.println("Authenticating request...");

        // Pass the request/response along the filter chain
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Cleanup logic (if needed)
    }
}
````

2. **Using `@WebFilter` Annotation with `@ServletComponentScan`**:
    - **Filter Definition**: The filter is defined by implementing the `jakarta.servlet.Filter` interface or extending `OncePerRequestFilter`, and it is annotated with the `@WebFilter` annotation.
    - **Registration Flow**: The filter **is not a Spring Bean**. Instead, the `@ServletComponentScan` annotation in the Spring Boot application automatically registers the filter with the servlet container.
    - **Bean Creation**: The filter definition itself is **not a Spring Bean**, but Spring Boot automatically detects and registers it via the servlet container without requiring a Spring Bean definition.
    - **Summary**: This is a more automated registration process where the filter is integrated into the servlet context without explicitly being a Spring Bean.

````java
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = "/api/*") // Define the URL pattern this filter applies to
public class CustomFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Filter initialization logic (if needed)
        System.out.println("Filter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // Pre-processing logic before passing the request along the filter chain
        System.out.println("Request is being filtered");

        // Pass request and response to the next filter in the chain
        chain.doFilter(request, response);

        // Post-processing logic after the request has been processed by the chain
        System.out.println("Response is being filtered");
    }

    @Override
    public void destroy() {
        // Cleanup resources (if needed)
        System.out.println("Filter destroyed");
    }
}
````

````java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import jakarta.servlet.annotation.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan  // This enables automatic scanning and registration of filters and servlets
public class MySpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(MySpringBootApplication.class, args);
    }
}
````

3. **Using `@Component` with `FilterRegistrationBean`**:
    - **Filter Definition**: The filter is defined as a Spring Bean by annotating it with `@Component`, either implementing the `jakarta.servlet.Filter` interface or extending `OncePerRequestFilter`.
    - **Registration Flow**: The filter is **already a Spring Bean** due to the `@Component` annotation. The filter is automatically registered by Spring as part of the application context. You can customize the filter's registration by using a `FilterRegistrationBean`, where you can specify URL patterns, order, and other configurations.
    - **Bean Creation**: The filter definition is a Spring Bean right from the start because of the `@Component` annotation. It’s registered directly with Spring Boot during application startup.
    - **Summary**: This approach simplifies filter registration by letting Spring manage the filter as a bean while still allowing customization through `FilterRegistrationBean`.

````java
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component  // This makes the filter a Spring Bean
public class CustomFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic (if needed)
        System.out.println("Custom Filter Initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // Pre-processing logic
        System.out.println("Custom Filter Processing Request");

        // Pass the request along the filter chain
        chain.doFilter(request, response);

        // Post-processing logic
        System.out.println("Custom Filter Processing Response");
    }

    @Override
    public void destroy() {
        // Cleanup logic (if needed)
        System.out.println("Custom Filter Destroyed");
    }
}
````

4. **Registering a Custom Filter with Spring Security Using `HttpSecurity`**:
    - **Filter Definition**: The filter is defined by implementing `jakarta.servlet.Filter` or extending `OncePerRequestFilter`, as a custom filter for Spring Security.
    - **Registration Flow**: The filter may or may not be a Spring Bean. If it is a Spring Bean, it can be injected directly into the Spring Security configuration. If it's not a Spring Bean, it can still be registered programmatically using `http.addFilterBefore()` or `http.addFilterAfter()` within the `HttpSecurity` configuration to integrate it into the security filter chain; in Spring Security, the **security filter chain** is a sequence of filters that intercept HTTP requests and apply security-related logic, such as authentication and authorization. Each filter in the chain performs a specific task, such as checking credentials or verifying permissions. The filters are applied in a specific order to process incoming requests and outgoing responses.
    - **Bean Creation**: The filter is **not automatically registered as a Spring Bean** unless explicitly defined as one (via `@Bean` or `@Component`). It is manually registered during Spring Security configuration.
    - **Summary**: This method integrates the filter into the Spring Security filter chain, but the filter doesn't have to be a Spring Bean unless needed for dependency injection.

````java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomSecurityFilter customSecurityFilter;

    @Autowired
    // Injecting the CustomSecurityFilter because it is a Spring Bean
    public SecurityConfig(CustomSecurityFilter customSecurityFilter) {
        this.customSecurityFilter = customSecurityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/api/**").authenticated()  // Example: Secure endpoints
                .anyRequest().permitAll()               // Allow other requests
            .and()
            .addFilterBefore(customSecurityFilter, UsernamePasswordAuthenticationFilter.class);  // Register filter before Spring Security's default filter

        return http.build();
    }
}
````

````java
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.io.IOException;

@Component  // Making the filter a Spring Bean
public class CustomSecurityFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Custom filter logic here
        System.out.println("Custom Security Filter Applied");
        
        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
````

- **Method 1**: Register as a `@Bean` in a `@Configuration` class with `FilterRegistrationBean`. The filter is defined as a class and is **later registered as a Spring Bean** during the registration process.
- **Method 2**: Use `@WebFilter` annotation and `@ServletComponentScan`. The filter is **not a Spring Bean**, and Spring Boot automatically registers it with the servlet container.
- **Method 3**: Use `@Component` annotation with `FilterRegistrationBean`. The filter is defined as a Spring Bean and automatically registered by Spring.
- **Method 4**: Register the custom filter using `HttpSecurity` in Spring Security. The filter may or may not be a Spring Bean, but it is directly integrated into the security filter chain.

#### Overview of the custom Filter class

The `AuthenticationTokenFilter` is a Spring Security filter responsible for processing JSON Web Tokens (JWT) from incoming HTTP requests. It is part of a security mechanism used to authenticate requests to protected endpoints in a stateless manner. By intercepting each HTTP request, the filter ensures that a valid JWT token is present and sets the authentication context for authenticated users.

This filter is implemented by extending Spring's `OncePerRequestFilter`, ensuring it runs once per request. It extracts the JWT token from the `Authorization` header, validates it using the `AdminTokenProvider`, and sets the authenticated user's details in the `SecurityContext`. This setup is commonly used in stateless authentication where no session is maintained.

The `AuthenticationTokenFilter` bean will be registered within the `SecurityConfig` class via the `HttpSecurity` configuration. It will be added to the Spring Security filter chain before the `UsernamePasswordAuthenticationFilter` to ensure JWTs are processed before any standard authentication mechanisms. Here's how it is registered:

```java
public SecurityFilterChain jwtFilterChain(HttpSecurity http) throws Exception {
    http
            .authorizeHttpRequests(authz -> authz
                    .requestMatchers("/shop/users", "/shop/items").authenticated()
                    .anyRequest().permitAll()
            )
            .addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .csrf(AbstractHttpConfigurer::disable);

    return http.build();
}
```

##### Annotations

- `@Component`: Marks this class as a Spring-managed component, allowing it to be auto-detected through classpath scanning and registered as a bean in the application context.
- `@Autowired`: Used on the constructor to inject the `AdminTokenProvider` dependency automatically from the Spring container.

###### Constructor

```java
@Autowired
public AuthenticationTokenFilter(AdminTokenProvider adminTokenProvider) {
    this.adminTokenProvider = adminTokenProvider;
}
```

This constructor injects the `AdminTokenProvider`, which is a utility class responsible for validating the JWT and extracting its claims. The use of `@Autowired` allows Spring to provide this dependency automatically when creating an instance of `AuthenticationTokenFilter`.

###### `doFilterInternal()` Method

```java
@Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
    String token = getTokenFromRequest(request);

    if (token != null && adminTokenProvider.validateToken(token)) {
        var claims = adminTokenProvider.getClaims(token);
        String username = claims.getSubject();

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(username, null, null);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    filterChain.doFilter(request, response);
}
```

This is the core method of the filter and is executed for every HTTP request.

- **Extract the JWT from the `Authorization` Header**:
    - The filter begins by checking the incoming HTTP request for the `Authorization` header, which typically contains the JWT (JSON Web Token).
    - The JWT is usually passed in the format `Bearer <token>`. If the header exists and is correctly formatted, the filter extracts the token for further validation.

- **Validate the Token and Retrieve Claims**:
    - If the token is present, the filter validates it. This involves checking its signature, expiration, and other claims to ensure it is a legitimate and valid JWT.
    - Once validated, it retrieves the claims contained within the token, including the username or any other custom claims that were embedded when the token was generated.

- **Extract the Username and Create Authentication Object**:
    - After retrieving the claims, the filter extracts the username (or other identifying information) from the token.
    - This username is used to create a `UsernamePasswordAuthenticationToken` object, which represents the authenticated user. This object encapsulates the user's identity and any granted authorities (roles or permissions).

- **Set Authentication Object into the `SecurityContext`**:
    - The `UsernamePasswordAuthenticationToken` object is then set into Spring Security's `SecurityContext`.
    - This step is crucial because the `SecurityContext` stores the authentication details for the current request, making it available to the rest of the Spring Security system.
    - This allows Spring Security to recognize that the user is authenticated and apply any necessary security rules or authorization checks.
    - **Authentication and Authorization**:
        - If the `SecurityContext` is not populated with the `UsernamePasswordAuthenticationToken`, Spring Security cannot authorize access to secure resources.
        - Without a valid authentication object in the `SecurityContext`, the user will be treated as unauthenticated, and any attempt to access protected endpoints will likely result in access being denied (e.g., returning a **401 Unauthorized** response).

- **Proceed with the Filter Chain**:
    - After setting the authentication object, the filter proceeds with the filter chain, passing the request along to the next filter or handler.
    - The downstream components will now be able to access the `SecurityContext` and retrieve the user's authentication details, ensuring that all security checks are properly applied.
    - **Downstream Filters**:
        - Any subsequent filters or components that rely on the `SecurityContext` to check the user's roles, permissions, or any other security-related information will fail to retrieve any valid user data if the authentication object is not set.
        - This can lead to security breaches, where unauthorized users gain access to sensitive resources, or it could result in denial of service if access is mistakenly blocked for legitimate users.

###### `getTokenFromRequest()` Method

```java
private String getTokenFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");

    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
        return bearerToken.substring(7);
    }

    return null;
}
```

This helper method extracts the JWT token from the HTTP `Authorization` header.

- It first fetches the `Authorization` header from the request.
- It checks whether the header starts with the `Bearer ` prefix.
- If it does, it removes the prefix and returns the token.
- If the header is missing or improperly formatted, it returns `null`.

This ensures that only properly formatted Bearer tokens are processed by the filter.

With this setup, the `AuthenticationTokenFilter` acts as a gatekeeper for protected routes, authenticating users based on JWT tokens and integrating seamlessly into the Spring Security filter chain.

````java
package com.example.security.jwt.filter;

import com.example.security.jwt.provider.AdminTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter responsible for extracting the JWT token from incoming requests, validating it,
 * and setting the authentication context for authenticated users.
 *
 * This filter ensures that for every request (except those that are excluded from security),
 * the token is checked, and if valid, the user's authentication details are set in the SecurityContext.
 *
 * Flow:
 * 1. Extract the JWT token from the "Authorization" header of the incoming request.
 * 2. Validate the token using the AdminTokenProvider.
 * 3. If valid, extract the claims from the token and create an Authentication object.
 * 4. Set the Authentication object in the SecurityContext to authenticate the user.
 * 5. Proceed with the request by calling filterChain.doFilter().
 */
@Component
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    // AdminTokenProvider is used to validate the JWT token and extract claims from it.
    private final AdminTokenProvider adminTokenProvider;

    /**
     * Constructor for injecting the AdminTokenProvider.
     *
     * @param adminTokenProvider The AdminTokenProvider used for validating and processing the JWT token.
     */
    @Autowired
    public AuthenticationTokenFilter(AdminTokenProvider adminTokenProvider) {
        this.adminTokenProvider = adminTokenProvider;
    }

    /**
     * This method is invoked for every HTTP request that passes through the filter chain.
     * It extracts the JWT token from the request, validates it, and sets the authentication in the SecurityContext
     * if the token is valid.
     *
     * @param request The HttpServletRequest, which contains the incoming request details.
     * @param response The HttpServletResponse, which is used to send the response to the client.
     * @param filterChain The FilterChain, which allows further filtering of the request.
     *
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException If an input/output error occurs.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Extract the token from the request's Authorization header
        String token = getTokenFromRequest(request);

        // If the token is not null and valid, authenticate the user
        if (token != null && adminTokenProvider.validateToken(token)) {
            // Get claims from the JWT token
            var claims = adminTokenProvider.getClaims(token);

            // Extract the username from the claims
            String username = claims.getSubject();

            // Create an authentication token with the username (null credentials as we use JWT for authentication)
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, null, null);

            // Set the authentication in the SecurityContext, marking the user as authenticated
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }

    /**
     * Extracts the JWT token from the Authorization header of the HTTP request.
     *
     * The token should be sent with the "Bearer " prefix in the Authorization header.
     * This method checks for that prefix and returns the actual token.
     *
     * @param request The HttpServletRequest object from which the token is extracted.
     * @return The JWT token as a string, or null if the token is not present or improperly formatted.
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        // Get the Authorization header
        String bearerToken = request.getHeader("Authorization");

        // Check if the header contains a valid Bearer token
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            // Extract the token by removing the "Bearer " prefix
            return bearerToken.substring(7);  // Return the token without the "Bearer " prefix
        }

        // Return null if the token is missing or improperly formatted
        return null;
    }
}
````

---

## SecurityConfig


---

## AdminUserConfig


---

## LoginController

The `LoginController` is a Spring REST controller that handles login requests, supporting **Basic Authentication** for user login and subsequently generating a **JWT token** for authenticated requests. It serves as the endpoint for obtaining a JWT token after authenticating a user through Basic Authentication.

### 1. **`@RestController` Annotation**:

The `@RestController` annotation marks this class as a **Spring MVC controller** that handles HTTP requests and returns responses directly (usually in the form of JSON or other serialized objects). This is a specialized version of `@Controller` and `@ResponseBody` combined, which ensures that data returned from each method is written directly to the HTTP response body.

- **Automatic Response Body Handling**:
    - Methods in this class do not need the `@ResponseBody` annotation as the `@RestController` ensures that the return value will be directly sent as the HTTP response.

### 2. **`@RequestMapping("/shop/login")` Annotation**:

The `@RequestMapping` annotation specifies the **URL path** that this controller will handle. In this case, all methods in this controller will be associated with the `/shop/login` endpoint.

- **Endpoint Binding**:
    - The `LoginController` class is responsible for handling requests made to `/shop/login`. It specifically manages POST requests to authenticate users and provide JWT tokens.

### 3. **Field Declaration for `AdminTokenProvider`**:

The class has a field of type `AdminTokenProvider`, which is used to generate the JWT token after the user has been authenticated.

- **Role of `AdminTokenProvider`**:
    - `AdminTokenProvider` is a service that contains the logic for generating a secure JWT token after user authentication. This token is required for subsequent requests that need to be authenticated.

### 4. **Constructor-Based Dependency Injection (`@Autowired`)**:

- **`@Autowired` Annotation**:
    - The constructor of the `LoginController` is annotated with `@Autowired`, which allows Spring to inject the required `AdminTokenProvider` bean into the controller.
    - **Dependency Injection** ensures that the controller has access to the `AdminTokenProvider` without needing to manually instantiate it.

- **Token Provider Injection**:
    - The `tokenProvider` is injected into the controller and used within the `login()` method to generate the JWT token. This allows the controller to delegate the token generation logic to the `AdminTokenProvider`.

### 5. **`login()` Method**:

This method handles POST requests made to `/shop/login`, performing the following tasks:

- **Authentication Flow**:
    - Upon receiving a request, the controller relies on **Basic Authentication** for authenticating the user. The actual authentication logic (validating username/password) is handled upstream by Spring Security, likely using the `AuthenticationManager` and `UserDetailsService` configured in the security configuration.

- **Token Generation**:
    - After successful authentication, the `login()` method calls the `generateToken()` method of the `AdminTokenProvider` to create a JWT token. This token represents the authenticated user's session.
    - The `generateToken()` method typically generates the token by encoding user information (such as the username and roles) into the token, making it secure for future use.

- **Response Creation**:
    - The JWT token is returned to the client with the **Bearer** prefix, which is the standard for token-based authentication in HTTP headers.
    - This prefix indicates that the token is intended to be used for **Bearer authentication**, and it must be included in subsequent requests that need to be authenticated.

- **ResponseEntity**:
    - The response is wrapped in a `ResponseEntity` object, which allows setting HTTP status codes and headers. In this case, the response is returned with a status code of `200 OK` along with the token in the response body.

### 6. **Flow Explanation**:

- **Request Flow**:
    1. A client sends a **POST request** to `/shop/login` with **Basic Authentication** credentials (username and password).
    2. Spring Security intercepts the request and performs authentication, likely using the `InMemoryUserDetailsManager` and the configured `PasswordEncoder` as detailed in the security configuration.
    3. If authentication is successful, the controller's `login()` method is invoked.

- **Token Generation**:
    4. The `login()` method then uses the `AdminTokenProvider` to generate a **JWT token**.
    5. The generated JWT token is sent back to the client with the **"Bearer"** prefix as a response. The client can then use this token to authenticate future requests by including it in the `Authorization` header (e.g., `Authorization: Bearer <JWT-TOKEN>`).

### 7. **`AdminTokenProvider` Class**:

The `AdminTokenProvider` is a service or component responsible for the actual generation of JWT tokens. It typically uses a **JWT library** to encode the token, with the following general process:

- **Token Claims**:
    - The `generateToken()` method in the `AdminTokenProvider` encapsulates the logic for setting the claims in the JWT, such as the user's roles, username, and any expiration time.

- **Signing the Token**:
    - The `AdminTokenProvider` signs the token with a secret key to ensure that the token cannot be tampered with.

- **Role in Authentication**:
    - The generated token allows the client to authenticate subsequent requests to protected endpoints by including the JWT in the `Authorization` header.

### 8. **How This Fits in the Spring Security Configuration**:

While the `LoginController` is not directly tied to the security configuration class, it works seamlessly with the overall **Spring Security setup** by utilizing **Basic Authentication** for the login process and relying on **JWT tokens** for subsequent authentication.

- **Basic Authentication**:
    - Spring Security’s authentication manager, as configured in the `SecurityConfig` class, will handle the **Basic Authentication** of the user's credentials when the client sends the request.

- **JWT Token for Further Authentication**:
    - Once authenticated, the `login()` method in the controller generates a JWT token using the `AdminTokenProvider`.
    - This JWT token is used in future requests to authenticate the user against protected endpoints (e.g., `/shop/items`, `/shop/users`), where the **JWT Authentication filter** in `SecurityConfig` comes into play.

### 9. **Conclusion**:

The `LoginController` provides a clean and simple way to authenticate users using **Basic Authentication** and subsequently generate a **JWT token** for future authenticated requests. The flow involves authentication by Spring Security, token generation using the `AdminTokenProvider`, and returning the token to the client, which can then be used for subsequent requests.

This controller fits into the broader **Spring Security** framework by allowing the secure generation of authentication tokens and providing a clear separation between authentication and authorization responsibilities.

---

# WebServiceRestJWT - API Testing & Authentication Guide

## Connecting to PostgreSQL

To verify inserted or updated records, connect to the existing PostgreSQL database and switch to the correct schema.

### PostgreSQL CLI
```bash
sudo -u postgres psql
```

### Connect to Database
```sql
\c my_app_db
```

---

## Running the Spring Boot Application

To run the `WebServiceRestJWT` application in IntelliJ:

**Open the Project in IntelliJ IDEA**
- Launch IntelliJ IDEA and choose **File -> Open**.
- Select the root folder of the project and open it. IntelliJ will auto-detect `pom.xml` and use Maven.

**Set Project JDK to 21**
- Navigate to **File -> Project Structure -> Project**.
- Set **Project SDK** to **JDK 21**. If not available, add it manually.

**Run the Application**
- Locate the main class `WebServiceRest` (the one with the `main` method).
- Click the green Run arrow or use **Run -> Run 'WebServiceRestJwt'**.

**Verify Startup**
- Check logs in IntelliJ’s terminal. Look for confirmation that Spring Boot started successfully.

---

## Authenticating to Get a JWT Token

Before calling protected endpoints, retrieve a valid JWT token.

### Login Endpoint (Basic Auth)
```http
POST /shop/login
```

**Credentials**
- Username: `admin`
- Password: `password123`

### cURL Request to Retrieve Token
```bash
curl -X POST http://localhost:8080/shop/login \
     -u admin:password123
```

### Example Response
```
Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

The response contains a Bearer token. Use this token in the `Authorization` header for all subsequent API requests.

### Decode JWT Token (Optional)
To verify the token’s claims, decode it using:
- [jwt.io](https://jwt.io)
- Or command-line:

```bash
TOKEN=your.jwt.token.here
IFS='.' read -ra TOKENS <<< "$TOKEN" && echo "${TOKENS[1]}" | base64 -d | jq
```

Expected claims:
```json
{
  "sub": "admin_sub",
  "role": "admin",
  "permission": "read,write,delete",
  "iat": 1711458912,
  "exp": 1711459512
}
```

Note: `iat` (issued at) and `exp` (expiration) are dynamic and depend on when the token is generated.

---

## API Testing - ItemController

### Create an Item
```bash
curl -X POST http://localhost:8080/shop/items \
     -H "Authorization: Bearer <YOUR_TOKEN>" \
     -H "Content-Type: application/json" \
     -d '{
           "name": "Smartphone",
           "description": "Latest smartphone with 5G",
           "price": 999.99
         }'
```

Verify insertion in PostgreSQL:
```sql
SELECT * FROM shop_schema.Item WHERE name = 'Smartphone';
```

### Retrieve an Item by ID
Replace `<ID>` with a real `item_id` retrieved from the DB or earlier response.
```bash
curl -X GET http://localhost:8080/shop/items/<ID> \
     -H "Authorization: Bearer <YOUR_TOKEN>"
```

### Update an Item
```bash
curl -X PUT http://localhost:8080/shop/items/<ID> \
     -H "Authorization: Bearer <YOUR_TOKEN>" \
     -H "Content-Type: application/json" \
     -d '{
           "name": "Smartphone Pro",
           "description": "Upgraded smartphone with better camera",
           "price": 1099.99
         }'
```

Check the updated item in DB:
```sql
SELECT * FROM shop_schema.Item WHERE name = 'Smartphone Pro';
```

---

## API Testing - UserController

### Create a User
```bash
curl -X POST http://localhost:8080/shop/users \
     -H "Authorization: Bearer <YOUR_TOKEN>" \
     -H "Content-Type: application/json" \
     -d '{
           "username": "alice007",
           "email": "alice7@example.com"
         }'
```

Check insertion:
```sql
SELECT * FROM shop_schema.User WHERE email = 'alice7@example.com';
```

### Retrieve a User by ID
Use a valid user_id from the DB:
```bash
curl -X GET "http://localhost:8080/shop/users?id=<USER_ID>" \
     -H "Authorization: Bearer <YOUR_TOKEN>"
```

### Retrieve a User by Email
```bash
curl -X GET "http://localhost:8080/shop/users?email=alice7@example.com" \
     -H "Authorization: Bearer <YOUR_TOKEN>"
```

### Retrieve a User by Username
```bash
curl -X GET "http://localhost:8080/shop/users?username=alice007" \
     -H "Authorization: Bearer <YOUR_TOKEN>"
```

### Update a User
```bash
curl -X PUT http://localhost:8080/shop/users \
     -H "Authorization: Bearer <YOUR_TOKEN>" \
     -H "Content-Type: application/json" \
     -d '{
           "username": "alice_updated",
           "email": "alice7@example.com"
         }'
```

Check update:
```sql
SELECT * FROM shop_schema.User WHERE email = 'alice7@example.com';
```

### Delete a User
```bash
curl -X DELETE http://localhost:8080/shop/users \
     -H "Authorization: Bearer <YOUR_TOKEN>" \
     -H "Content-Type: application/json" \
     -d '{
           "user_id": <USER_ID>
         }'
```

Check deletion:
```sql
SELECT * FROM shop_schema.User WHERE user_id = <USER_ID>;
```

---

## Security Testing Tips

Try the following scenarios to test security robustness:

- **Expired Token:** Wait 10+ minutes (token expiration) and retry an endpoint. Expect `403 Forbidden`.
- **Tampered Token:** Modify any character in the token and retry. Expect `403 Forbidden`.
- **Incorrect Basic Auth:** Try wrong credentials on `/shop/login`. Expect `403 Forbidden`.

---

## Notes
- Ensure the server is running at `http://localhost:8080`.
- All endpoints now require a valid Bearer token in the `Authorization` header.
- Use `curl -i` for more detailed responses during testing.
- Do not assume any `user_id` or `item_id`—always retrieve dynamically from database or previous response.
