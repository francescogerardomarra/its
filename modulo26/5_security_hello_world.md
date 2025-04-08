# Security Hello World

We'll enhance an already built Spring Boot REST JPA web service by integrating **JWT authentication**.

Our goal is to secure specific endpoints while keeping others publicly accessible. Specifically, we’ll secure the endpoints `/shop/items` and `/shop/users`, allowing **admin-only** access using **JWT-based stateless authentication**.

We will build on a previously implemented Spring Boot 3.4.4 project using **Java 21**, introducing a robust and scalable authentication flow. The process will include utilizing **Basic Authentication** for the login and generating a **JWT token** that needs to be included in the `Authorization` header as a `Bearer` token when accessing protected resources.

The key steps for securing the admin-only endpoints are as follows:

1. An admin will send a **POST request** to `/shop/login` using **Basic Authentication**.
2. If the credentials are valid, a **JWT token** will be generated and returned.
3. The generated JWT token must be included as a `Bearer` token in the `Authorization` header to access protected endpoints such as `/shop/items` and `/shop/users`.
4. The JWT token will expire after **10 minutes** (`admin.jwt.claim.expiration.ms=600000`).
5. Once the JWT token expires, the admin will need to send another **POST request** to `/shop/login` with **Basic Authentication** to obtain a new JWT token.

---

## pom.xml
To enhance an already built Spring Boot REST web service by integrating **JWT authentication**, we will use Spring Security 6.x (with Spring Boot 3.x and Java 17+).

We need to add the following dependencies to the `pom.xml`. Each of these plays a crucial role in enabling secure authentication and authorization using JSON Web Tokens (JWTs).

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
- `logging.level.org.springframework.security=DEBUG`: Enables detailed logging for Spring Security internals useful for debugging authentication and authorization issues.
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
- `admin.jwt.claim.sub`: The subject (`sub`) claim that identifies typically the username or user ID.
- `admin.jwt.claim.role`: Sets the `role` claim in the JWT used to authorize endpoints.
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
    │   │           ├── filter
    │   │           │   └── AuthenticationTokenFilter.java
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
    │   │           │   │   └── SecurityConfig.java
    │   │           │   └── jwt
    │   │           │       └── AdminTokenProvider.java
    │   │           ├── service
    │   │           │   ├── ItemService.java
    │   │           │   ├── OrderItemService.java
    │   │           │   ├── OrderService.java
    │   │           │   └── UserService.java
    │   │           └── WebServiceRestSecurity.java
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

---

````java
package com.example.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

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
      // Return false if the token is invalid (e.g. incorrect signature, expired).
      return false;
    }
  }
}
````

---

## AuthenticationTokenFilter
The `AuthenticationTokenFilter` is a custom filter that is responsible for extracting, validating, and processing JWT tokens from incoming HTTP requests.

This is the definition of a custom filter that will later be registered with Spring Security to apply JWT authentication.

In Spring Security, you can register a custom filter and integrate it into the security filter chain. This registration will happen in the `SecurityConfig` class, where the filter will be added to the security filter chain to ensure that custom authentication logic, such as JWT token validation, is applied. By doing so, the custom filter ensures that only authenticated requests are allowed to access protected resources.

This filter is implemented by extending Spring's `OncePerRequestFilter`, ensuring it runs once per request. It extracts the JWT token from the `Authorization` header, validates it using the `AdminTokenProvider`, and sets the authenticated user's details in the `SecurityContext`. This setup is commonly used in stateless authentication where no session is maintained.

The `AuthenticationTokenFilter` bean will be registered within the `SecurityConfig` class and will be added to the Spring Security filter chain before the `UsernamePasswordAuthenticationFilter` to ensure JWTs are processed before any standard authentication mechanisms. Here's how it is registered:

### Annotations

- `@Component`: Marks this class as a Spring-managed component, allowing it to be auto-detected through classpath scanning and registered as a bean in the application context.
- `@Autowired`: Used on the constructor to inject the `AdminTokenProvider` dependency automatically from the Spring container.

### Constructor

```java
@Autowired
public AuthenticationTokenFilter(AdminTokenProvider adminTokenProvider) {
    this.adminTokenProvider = adminTokenProvider;
}
```

This constructor injects the `AdminTokenProvider`, which is a utility class responsible for validating the JWT and extracting its claims. The use of `@Autowired` allows Spring to provide this dependency automatically when creating an instance of `AuthenticationTokenFilter`.

### `doFilterInternal()` Method

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
        - Without a valid authentication object in the `SecurityContext`, the user will be treated as unauthenticated, and any attempt to access protected endpoints will likely result in access being denied (e.g. returning a **401 Unauthorized** response).

- **Proceed with the Filter Chain**:
    - After setting the authentication object, the filter proceeds with the filter chain, passing the request along to the next filter or handler.
    - The downstream components will now be able to access the `SecurityContext` and retrieve the user's authentication details, ensuring that all security checks are properly applied.
    - **Downstream Filters**:
        - Any subsequent filters or components that rely on the `SecurityContext` to check the user's roles, permissions, or any other security-related information will fail to retrieve any valid user data if the authentication object is not set.
        - This can lead to security breaches, where unauthorized users gain access to sensitive resources, or it could result in denial of service if access is mistakenly blocked for legitimate users.

### `getTokenFromRequest()` Method

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

***

````java
package com.example.filter;

import com.example.security.jwt.AdminTokenProvider;
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
We want to implement the following behaviour:

- **1.** The filter chain should apply **Basic Authentication** for requests to `/shop/login` to authenticate the admin user.
- **2.** Upon successful authentication, the filter chain should generate a **JWT token** and include it in the response body.
- **3.** The filter chain should validate and process the **JWT token** on every request to protected endpoints (e.g. `/shop/items`, `/shop/users`).
- **4.** The JWT token should be passed in the `Authorization` header as a **Bearer token** for access to protected endpoints.
- **5.** The **`AuthenticationTokenFilter`** should intercept requests to protected endpoints, extracting the JWT token from the `Authorization` header.
- **6.** The **`AuthenticationTokenFilter`** should validate the JWT token by checking its **signature** and **expiration**. If the token is valid and not expired, the filter chain should allow access to the protected resources.
- **7.** If the JWT token has expired, the filter should reject the request and respond with an appropriate error (e.g. HTTP status 401 Unauthorized). The admin will need to authenticate again by sending a new **POST request** to `/shop/login` using **Basic Authentication**.

A security filter chain implementing such behaviour for our session-less application using **Spring Security 5.x** would be:

````java
@Configuration
@EnableWebSecurity  // Enables Spring Security's web security configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${admin.username}")
    private String adminUsername;  // The admin username is fetched from the application's properties.
  
    @Value("${admin.password}")
    private String adminPassword;  // The admin password is fetched from the application's properties.
  
    private final AuthenticationTokenFilter authenticationTokenFilter;
    
    @Autowired
    public SecurityConfig(AuthenticationTokenFilter authenticationTokenFilter) {
        this.authenticationTokenFilter = authenticationTokenFilter;
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Basic Authentication configuration
        http
            .authorizeRequests()
                .antMatchers("/shop/login").authenticated() // Require authentication for login endpoint.
                .anyRequest().permitAll() // Allow all other requests without authentication.
            .and()
            .httpBasic() // Enable Basic Authentication.
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless session policy.
            .and()
            .csrf().disable(); // Disable CSRF as we are using stateless authentication.

        // JWT Filter Chain for protected endpoints (after Basic Authentication)
        http
            .authorizeRequests()
                .antMatchers("/shop/users", "/shop/items").authenticated() // Protect these endpoints with JWT authentication.
                .anyRequest().permitAll() // Allow all other requests without authentication.
            .and()
            .addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class) // Add custom JWT filter.
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless session policy.
            .and()
            .csrf().disable(); // Disable CSRF as we're using stateless authentication.
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Use BCrypt for password hashing. BCrypt is a strong algorithm for password encryption.
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService adminUserDetailsService(PasswordEncoder passwordEncoder) {
      // Create the admin user in memory with a username and encoded password.
      return new InMemoryUserDetailsManager(
              User.withUsername(adminUsername)  // Set the admin username.
                      .password(passwordEncoder.encode(adminPassword))  // Encode the password before storing it.
                      .build()  // Build and return the UserDetails object for the admin user.
      );
    }
}
````

If we want to migrate this code to **Spring Security 6.x**, the following observations hold:

**Configuration Approach**

- **Spring Security 5.x:**
  - Security is configured by extending `WebSecurityConfigurerAdapter`.
  - You override the `configure(HttpSecurity http)` method to set up security settings.
  - Uses imperative style with chained method calls.

- **Spring Security 6.x:**
  - Security is configured using `SecurityFilterChain` beans.
  - You define one or more `SecurityFilterChain` beans, each representing a specific configuration.
  - Introduces the `Customizer` interface for declarative configuration.
  - Configuration is more modular and flexible.

**Defining Multiple Security Filter Chains**

- **Spring Security 5.x:**
  - All security rules are defined within the single `configure(HttpSecurity http)` method.
  - Use path matchers (`antMatchers`) to handle different paths.

- **Spring Security 6.x:**
  - You can define multiple `SecurityFilterChain` beans, each with specific configuration.
  - SecurityFilterChains are assigned an `@Order` annotation to control their priority.

**CSRF Protection**

- **Spring Security 5.x:**
  - CSRF protection can be disabled globally within the `configure(HttpSecurity http)` method using `.csrf().disable()`.

- **Spring Security 6.x:**
  - CSRF protection can be disabled per `SecurityFilterChain` bean using `.csrf(AbstractHttpConfigurer::disable)`.

**Custom Filters (e.g. JWT Filter)**

- **Spring Security 5.x:**
  - Custom filters are added inside the `configure(HttpSecurity http)` method using `.addFilterBefore()` or `.addFilterAfter()`.

- **Spring Security 6.x:**
  - Custom filters are added within the `SecurityFilterChain` beans using `.addFilterBefore()` or `.addFilterAfter()`.

**Authentication Manager**

- **Spring Security 5.x:**
  - `WebSecurityConfigurerAdapter` provides an `AuthenticationManager` by default, so you do not need to explicitly define it.

- **Spring Security 6.x:**
  - Since `WebSecurityConfigurerAdapter` has been removed, you must explicitly define an `AuthenticationManager` bean when necessary.
  - Use `http.getSharedObject(AuthenticationManager.class)` to retrieve the `AuthenticationManager` from `HttpSecurity`.

  **What You Should Do:**
  - If staying on **Spring Security 5.x**, you don't need to define an `AuthenticationManager` bean.
  - If migrating to **Spring Security 6.x**, explicitly define an `AuthenticationManager` bean.

**Security Filter Chain Customization**

- **Spring Security 5.x:**
  - Security configuration is more imperative, defined in a single method.
  - Limited ability to define different rules for different parts of the application.

- **Spring Security 6.x:**
  - Configuration is declarative and modular with `SecurityFilterChain` beans.
  - Easier to create multiple filter chains for different URL patterns or authentication mechanisms.

**Session Creation Policy**

- **Spring Security 5.x:**
  - Session creation policy is set within the `configure(HttpSecurity http)` method.

- **Spring Security 6.x:**
  - Each `SecurityFilterChain` bean can independently manage its session creation policy.

As result, the final `SecurityConfig` class follows:

````java
package com.example.security.config;

import com.example.filter.AuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class for HTTP security, defining two separate security chains.
 * One chain is for Basic Authentication used for login, and another is for JWT Authentication
 * applied to the protected resources.
 * <p>
 * The two chains are ordered to provide specific security configurations for different parts of the application:
 * - **Basic Authentication** for login (`/shop/login` endpoint).
 * - **JWT Authentication** for the remaining protected endpoints (`/shop/users`, `/shop/items`).
 * <p>
 * Flow:
 * 1. Basic Authentication is applied to login requests, where users authenticate and receive a JWT token.
 * 2. JWT Authentication is then applied to further protected endpoints using the `TokenAuthenticationFilter`.
 */
@Configuration
@EnableWebSecurity  // Enables Spring Security's web security configuration
public class SecurityConfig {

    @Value("${admin.username}")
    private String adminUsername;  // The admin username is fetched from the application's properties.

    @Value("${admin.password}")
    private String adminPassword;  // The admin password is fetched from the application's properties.

    private final AuthenticationTokenFilter authenticationTokenFilter;

    /**
     * Constructor to inject the custom TokenAuthenticationFilter.
     * This filter is responsible for validating JWT tokens in requests.
     */
    @Autowired
    public SecurityConfig(AuthenticationTokenFilter authenticationTokenFilter) {
        this.authenticationTokenFilter = authenticationTokenFilter;
    }

    /**
     * Configures the first security filter chain for Basic Authentication.
     * This filter chain applies only to the `/shop/login` endpoint, allowing authentication via Basic Auth.
     * <p>
     * - Only the `/shop/login` endpoint is secured with Basic Authentication.
     * - Once the user authenticates successfully, they will receive a JWT token to use for future requests.
     * - The session is stateless, meaning no session is stored on the server.
     * <p>
     * The `AuthenticationManager` bean plays a critical role here by validating the user credentials during the Basic Authentication process.
     * Spring Security uses the `AuthenticationManager` to authenticate the user based on the `Authorization` header sent with the request.
     * The authentication process checks the provided username and password against the user details, ensuring that the requestor has valid credentials.
     * <p>
     * If authentication is successful, the user is granted access to the `/shop/login` endpoint, and a JWT token is issued for further requests.
     *
     * @param http The HttpSecurity object used to configure security settings.
     * @return The configured SecurityFilterChain for Basic Authentication.
     * @throws Exception if there are errors in configuring HTTP security.
     */
    @Bean
    @Order(1)  // The first security chain with a higher priority (lower order).
    public SecurityFilterChain basicAuthFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/shop/login")  // Apply this filter chain only to the /shop/login endpoint.
                .authorizeHttpRequests(authz -> authz
                        .anyRequest().authenticated()  // Require authentication for this endpoint.
                )
                .httpBasic(Customizer.withDefaults())  // Enable Basic Authentication.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Stateless session policy.
                .csrf(AbstractHttpConfigurer::disable);  // Disable CSRF as we are using stateless authentication.

        return http.build();  // Build and return the configured filter chain.
    }

    /**
     * Configures the second security filter chain for JWT Authentication.
     * This filter chain applies to the `/shop/users` and `/shop/items` endpoints, which require a valid JWT.
     * <p>
     * - Requests to `/shop/users` and `/shop/items` are protected and require JWT authentication.
     * - Any other request that doesn't match these endpoints is allowed without authentication.
     * - The `TokenAuthenticationFilter` is added to process and validate the JWT token in the Authorization header.
     * <p>
     * The `AuthenticationManager` is implicitly involved in this filter chain as part of the overall authentication process.
     * When a request is made to `/shop/users` or `/shop/items`, the `TokenAuthenticationFilter` extracts the JWT from the `Authorization` header
     * and validates it. If the token is valid, the user is authenticated, and the request proceeds.
     * If no valid token is provided or the token is invalid, the request will be rejected based on the configured security filters.
     * <p>
     * The `AuthenticationManager` is essential here for validating the user’s credentials once the token is parsed. It also helps in
     * setting up the authentication context, ensuring the user is authorized to access these endpoints with the valid JWT.
     *
     * @param http The HttpSecurity object used to configure security settings.
     * @return The configured SecurityFilterChain for JWT Authentication.
     * @throws Exception if there are errors in configuring HTTP security.
     */
    @Bean
    @Order(2)  // The second security chain with lower priority (higher order).
    public SecurityFilterChain jwtFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/shop/users", "/shop/items").authenticated()  // Protect these endpoints with JWT authentication.
                        .anyRequest().permitAll()  // Allow all other requests without authentication.
                )
                .addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)  // Add the custom JWT filter before the default authentication filter.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Stateless session policy.
                .csrf(AbstractHttpConfigurer::disable);  // Disable CSRF as we're using stateless authentication.

        return http.build();  // Build and return the configured filter chain.
    }

    /**
     * Bean configuration for `PasswordEncoder`.
     * This bean is used to securely encode and verify user passwords during authentication processes.
     * It is typically used when creating users (e.g., during registration) and when validating passwords during login.
     * <p>
     * In this case, we use `BCryptPasswordEncoder`, a widely used hashing algorithm, to securely hash passwords.
     * BCrypt is designed to be computationally expensive, which makes it resistant to brute force and rainbow table attacks.
     * <p>
     * The `PasswordEncoder` bean is used by Spring Security during authentication, particularly in conjunction with the
     * `UserDetailsService`. It is essential for encoding passwords before storing them and validating them during the
     * authentication process.
     *
     * @return A `BCryptPasswordEncoder` instance that Spring Security will use for encoding and verifying passwords.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Use BCrypt for password hashing. BCrypt is a strong algorithm for password encryption.
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean configuration for the `AuthenticationManager`.
     * This bean is responsible for handling user authentication, validating their credentials.
     * The `AuthenticationManager` is used for both Basic Authentication and JWT Authentication.
     * <p>
     * In Spring Security 6.x, since `WebSecurityConfigurerAdapter` has been removed, 
     * you must explicitly define an `AuthenticationManager` bean to handle authentication.
     * </p>
     * <p>
     * This configuration ensures that the `AuthenticationManager` is set up to support custom authentication flows, 
     * including Basic Authentication and JWT, and integrates seamlessly with custom filters like `TokenAuthenticationFilter`.
     * </p>
     * <p>
     * The `AuthenticationManager` is crucial in validating credentials: for Basic Authentication, it checks credentials in the 
     * `Authorization` header, and for JWT Authentication, it validates the JWT token.
     * </p>
     *
     * @param http The `HttpSecurity` object for configuring security settings, including authentication.
     * @return The `AuthenticationManager` used for authenticating users throughout the application’s security filters.
     * @throws Exception if issues occur during HTTP security configuration or `AuthenticationManager` initialization.
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        // Retrieve the shared AuthenticationManager from the HttpSecurity object to ensure it's correctly configured
        // with the customized security settings and filters.
        return http.getSharedObject(AuthenticationManager.class);
    }

    /**
     * Bean configuration for the `UserDetailsService` used to load the admin user details.
     * This bean is responsible for loading user-specific data, such as username and password, for authentication.
     * We configure the `InMemoryUserDetailsManager` here for simplicity and to avoid needing a persistent storage solution.
     * It provides a user repository that is kept in memory, which is useful for simple scenarios like testing or small apps.
     * <p>
     * The `UserDetailsService` interface is central to Spring Security's authentication process. In this case, we are using it
     * to load the admin user from in-memory storage and apply password encoding with `PasswordEncoder` to securely store
     * the password.
     *
     * @param passwordEncoder The `PasswordEncoder` bean used to encode the admin user's password before storing it.
     * @return A `UserDetailsService` instance that Spring Security will use to load the admin user during authentication.
     */
    @Bean
    public UserDetailsService adminUserDetailsService(PasswordEncoder passwordEncoder) {
        // Create the admin user in memory with a username and encoded password.
        return new InMemoryUserDetailsManager(
                User.withUsername(adminUsername)  // Set the admin username.
                        .password(passwordEncoder.encode(adminPassword))  // Encode the password before storing it.
                        .build()  // Build and return the UserDetails object for the admin user.
        );
    }
}
````

---

## LoginController

The `LoginController` is a Spring REST controller that handles login requests, supporting **Basic Authentication** for user login and subsequently generating a **JWT token** for authenticated requests. It serves as the endpoint for obtaining a JWT token after authenticating a user through Basic Authentication.

### **`@RestController` Annotation**:

The `@RestController` annotation marks this class as a **Spring MVC controller** that handles HTTP requests and returns responses directly (usually in the form of JSON or other serialized objects). This is a specialized version of `@Controller` and `@ResponseBody` combined, which ensures that data returned from each method is written directly to the HTTP response body.

- **Automatic Response Body Handling**:
    - Methods in this class do not need the `@ResponseBody` annotation as the `@RestController` ensures that the return value will be directly sent as the HTTP response.

### **`@RequestMapping("/shop/login")` Annotation**:

The `@RequestMapping` annotation specifies the **URL path** that this controller will handle. In this case, all methods in this controller will be associated with the `/shop/login` endpoint.

- **Endpoint Binding**:
    - The `LoginController` class is responsible for handling requests made to `/shop/login`. It specifically manages POST requests to authenticate users and provide JWT tokens.

### **Field Declaration for `AdminTokenProvider`**:

The class has a field of type `AdminTokenProvider`, which is used to generate the JWT token after the user has been authenticated.

- **Role of `AdminTokenProvider`**:
    - `AdminTokenProvider` is a service that contains the logic for generating a secure JWT token after user authentication. This token is required for subsequent requests that need to be authenticated.

### **Constructor-Based Dependency Injection (`@Autowired`)**:

- **`@Autowired` Annotation**:
    - The constructor of the `LoginController` is annotated with `@Autowired`, which allows Spring to inject the required `AdminTokenProvider` bean into the controller.
    - **Dependency Injection** ensures that the controller has access to the `AdminTokenProvider` without needing to manually instantiate it.

- **Token Provider Injection**:
    - The `tokenProvider` is injected into the controller and used within the `login()` method to generate the JWT token. This allows the controller to delegate the token generation logic to the `AdminTokenProvider`.

### **`login()` Method**:

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

### **Flow Explanation**:

- **Request Flow**:
    1. A client sends a **POST request** to `/shop/login` with **Basic Authentication** credentials (username and password).
    2. Spring Security intercepts the request and performs authentication, likely using the `InMemoryUserDetailsManager` and the configured `PasswordEncoder` as detailed in the security configuration.
    3. If authentication is successful, the controller's `login()` method is invoked.

- **Token Generation**:
    4. The `login()` method then uses the `AdminTokenProvider` to generate a **JWT token**.
    5. The generated JWT token is sent back to the client with the **"Bearer"** prefix as a response. The client can then use this token to authenticate future requests by including it in the `Authorization` header (e.g. `Authorization: Bearer <JWT-TOKEN>`).

### **`AdminTokenProvider` Class**:

The `AdminTokenProvider` is a service or component responsible for the actual generation of JWT tokens. It typically uses a **JWT library** to encode the token, with the following general process:

- **Token Claims**:
    - The `generateToken()` method in the `AdminTokenProvider` encapsulates the logic for setting the claims in the JWT, such as the user's roles, username, and any expiration time.

- **Signing the Token**:
    - The `AdminTokenProvider` signs the token with a secret key to ensure that the token cannot be tampered with.

- **Role in Authentication**:
    - The generated token allows the client to authenticate subsequent requests to protected endpoints by including the JWT in the `Authorization` header.

### **How This Fits in the Spring Security Configuration**:

While the `LoginController` is not directly tied to the security configuration class, it works seamlessly with the overall **Spring Security setup** by utilizing **Basic Authentication** for the login process and relying on **JWT tokens** for subsequent authentication.

- **Basic Authentication**:
    - Spring Security’s authentication manager, as configured in the `SecurityConfig` class, will handle the **Basic Authentication** of the user's credentials when the client sends the request.

- **JWT Token for Further Authentication**:
    - Once authenticated, the `login()` method in the controller generates a JWT token using the `AdminTokenProvider`.
    - This JWT token is used in future requests to authenticate the user against protected endpoints (e.g. `/shop/items`, `/shop/users`), where the **JWT Authentication filter** in `SecurityConfig` comes into play.

````java
package com.example.controller;

import com.example.security.jwt.AdminTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller responsible for handling login requests.
 * This controller supports Basic Authentication to authenticate users and then generates a JWT token for further requests.
 *
 * Flow:
 * 1. The user hits the /shop/login endpoint with Basic Authentication credentials (username and password).
 * 2. If authentication is successful, the system generates a JWT token using the AdminTokenProvider.
 * 3. The generated JWT token is returned to the user with the "Bearer" prefix, which can be used for authenticated requests.
 */
@RestController
@RequestMapping("/shop/login")
public class LoginController {

    // AdminTokenProvider used for generating JWT tokens
    private final AdminTokenProvider tokenProvider;

    /**
     * Constructor-based dependency injection to provide the AdminTokenProvider.
     *
     * @param tokenProvider The AdminTokenProvider to generate JWT tokens.
     */
    @Autowired
    public LoginController(AdminTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    /**
     * Login endpoint to authenticate the user via Basic Authentication.
     * Once authenticated, this method will generate a JWT token and return it to the client.
     *
     * @return ResponseEntity containing the JWT token as a Bearer token.
     *         The token can then be used for subsequent authenticated requests.
     */
    @PostMapping
    public ResponseEntity<?> login() {
        // Generate the JWT token using the AdminTokenProvider
        // The AdminTokenProvider encapsulates the logic of creating a secure JWT token for the admin.
        String token = tokenProvider.generateToken();

        // Return the generated JWT token to the client as a Bearer token.
        // The "Bearer " prefix indicates that this token is for authentication purposes.
        return ResponseEntity.ok().body("Bearer " + token);
    }
}
````

---

# API Testing & Authentication Guide

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

To run the `WebServiceRestSecurity` application in IntelliJ:

**Open the Project in IntelliJ IDEA**
- Launch IntelliJ IDEA and choose **File -> Open**.
- Select the root folder of the project and open it. IntelliJ will auto-detect `pom.xml` and use Maven.

**Set Project JDK to 21**
- Navigate to **File -> Project Structure -> Project**.
- Set **Project SDK** to **JDK 21**. If not available, add it manually.

**Run the Application**
- Locate the main class `WebServiceRestSecurity` (the one with the `main` method).
- Click the green Run arrow or use **Run -> Run 'WebServiceRestSecurity'**.

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
- Do not assume any `user_id` or `item_id`, always retrieve dynamically from database or previous response.
