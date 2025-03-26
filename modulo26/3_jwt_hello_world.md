# Jwt Hello World

In this article, we'll enhance an already built Spring Boot REST web service by integrating **JWT authentication**. Our goal is to secure specific endpoints while keeping others publicly accessible. Specifically, we’ll secure the endpoints `/shop/items` and `/shop/users`, allowing **admin-only** access using **JWT-based stateless authentication**.

We will build on a previously implemented Spring Boot 3.4.4 project using **Java 21**, introducing a robust and scalable authentication flow. The process will include utilizing **Basic Authentication** for the login and generating a **JWT token** that needs to be included in the `Authorization` header as a `Bearer` token when accessing protected resources.

The key steps for securing the admin-only endpoints are as follows:
1. An admin will send a **POST request** to `/shop/login` using **Basic Authentication**.
2. If the credentials are valid, a **JWT token** will be generated and returned.
3. The generated JWT token must be included as a `Bearer` token in the `Authorization` header to access protected endpoints such as `/shop/items` and `/shop/users`.

By the end of this article, you’ll have successfully added JWT authentication to an existing Spring Boot REST web service, ensuring secure, stateless access to specific endpoints. This solution will minimize session management overhead and allow for easy scalability in the future.

---

## pom.xml

To implement JWT authentication in your Spring Boot application, several dependencies need to be added to support different aspects of the process. Here's an overview of each one:

- **spring-boot-starter-security**: This dependency enables Spring Security, which is essential for providing authentication and authorization features in your application. It integrates with Spring Boot to secure your API endpoints, allowing you to manage user authentication, including support for JWT-based authentication. It is included to enable authentication and authorization features in the application.

- **jjwt-api**: This is the interface layer of the JJWT library. It provides the necessary methods for creating and parsing **JSON Web Tokens (JWTs)**. This dependency allows your application to handle the structure and claims of the JWTs securely, defining how tokens should be created and validated. It provides the interface for creating and parsing JSON Web Tokens (JWTs).

- **jjwt-impl**: This dependency contains the actual implementation for creating and verifying JWTs. It provides the necessary code for signing and validating JWTs, ensuring the integrity of the token. It is required only at runtime, as it is not needed during the compile phase. It contains the implementation for creating and verifying JWTs, including signing and validation.

- **jjwt-jackson**: This module provides support for serializing and deserializing JWTs using **Jackson**, which is the default JSON library used by Spring Boot. It enables the conversion of JWTs to and from JSON format, allowing your application to process tokens effectively. This dependency is necessary to work with JWTs in a Spring Boot environment, which relies on Jackson for JSON processing. It provides support for serializing and deserializing JWTs using Jackson.

- **jackson-databind**: This is a core Jackson library responsible for serializing Java objects to JSON and deserializing JSON into Java objects. It is explicitly added because the JWT-related dependencies, such as **jjwt-jackson**, do not include Jackson by default. Before adding JWT dependencies, Spring Boot automatically included Jackson as part of the **spring-boot-starter-web** dependency. However, after adding JWT dependencies, you need to manually include Jackson to ensure proper compatibility with Spring Boot and avoid issues with missing classes, such as `DatatypeFeature`.

- **jackson-core**: This is the core library for Jackson, providing low-level functionality for parsing and generating JSON. It is required to ensure compatibility with other Jackson modules like **jackson-databind** and **jackson-datatype-jsr310**. This library is needed for JSON processing, including parsing JSON into Java objects.

- **jackson-datatype-jsr310**: This module is needed to handle Java 8 Date/Time API types (such as `LocalDate`, `LocalDateTime`, etc.) for serialization and deserialization. It ensures that Java 8 Date/Time types are properly processed when working with JWTs. It is necessary for handling Date/Time types, which may be used in your application when working with JWTs in Spring Boot.

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

## Directory Structure
After integrating JWT authentication, the project directory structure is updated with the addition of new classes. Here’s a breakdown of the key changes:

- **LoginController.java**: This new controller is added under `com.example.controller` to handle the login process using Basic Authentication. It is responsible for authenticating the admin and generating the JWT token.

- **Security-related classes**: A new package `com.example.security` is introduced, which contains the following classes:
    - **AdminUserConfig.java**: This class defines configurations specific to the admin user, ensuring proper access control and security settings.
    - **SecurityConfig.java**: This configuration class handles the overall security settings for the application, including JWT authentication and authorization mechanisms.
    - **AdminTokenProvider.java**: Responsible for generating and validating JWT tokens specifically for admin users.
    - **TokenAuthenticationFilter.java**: This filter intercepts incoming requests to check for a valid JWT token in the request headers, ensuring protected endpoints are only accessible with valid tokens.

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
    │   │           │       ├── admin
    │   │           │       │   └── AdminTokenProvider.java
    │   │           │       └── filter
    │   │           │           └── TokenAuthenticationFilter.java
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

## application.properties

- **Logging Configuration:**
    - `logging.level.org.springframework.security=DEBUG`: Enables detailed logging for security operations within Spring Security.
    - `logging.level.org.springframework.web=DEBUG`: Enables detailed logging for web operations within Spring Web.

- **Basic Authentication:**
    - `admin.username=admin`: Defines the username for the admin user for Basic Authentication.
    - `admin.password=password123`: Defines the password for the admin user for Basic Authentication.

- **JWT Configuration:**
    - `jwt.secret=VGhpcyBpcyBhIHNlY3VyZSBqZXN0IGtleSB0aGF0IGlzIGJlYXJlZCBvbiBhIFNoYTI1NiBtZWRpYSB0aGF0IGlzIGxvbmcgaW5vdWdoIHRoZSBjZXJ0YWluIHNlY3VyaXR5IHN0YW5kYXJkLCBhbmQgaXMgZXhwbGVjdGVkIHRvIGJlIGF1dGhvcml6ZWQuIFRoaXMga2V5IHdpbGwgYmUgc2lnbmVkIHdpdGggc2lnbmVkYXR1cmUgYWxnb3JpdGhtcyBzbyBpdCBpcyBhIHN0cm9uZyBzZWNyZXQuCg==`:
        - The `jwt.secret` value is a **Base64-encoded string** used as the **secret key** to sign and verify JWT tokens.
        - **Why Base64 encoding?**
            - Base64 encoding is commonly used to represent binary data (like the secret key) in an ASCII string format. This is helpful because the secret key is often a binary string, which may contain characters that are not easily handled in standard text formats.
            - Base64 encoding makes the secret key compatible with systems that are not binary-friendly (such as HTTP headers or configuration files).
            - Base64 ensures the key can be easily transferred or stored as text, while still being reversible to its original binary form when needed for cryptographic operations like signing the JWT.
        - **Decoding the secret:**
            - If we decode this Base64 string, we get the following result:

            ```
            This is a secure jwt key that is beared on a Sha256 media that is long inouh the certain security standard, and is expected to be authorized.
            ```

            - **What does this mean?**
                - The decoded string is a human-readable version of the secret, but it is not a cryptographic key in its usable form.
                - In practice, we don't use the decoded string directly for signing JWTs, but rather, we use the original Base64-encoded string to ensure the cryptographic process is secure.
                - This decoded string seems to provide context or documentation regarding the secret key, such as its purpose (`secure jwt key`) and intended use (with SHA256 media and security standards).
                - However, **you should never expose your decoded secrets publicly** as they could provide hints or insight into how the key is used and potentially compromise your system.

        - The key is long because it is used with the `HS512` signing algorithm, which requires a sufficiently long and secure key to prevent brute-force attacks.
        - When signing the token with this key, we use the `HS512` algorithm, which ensures that the token is both secure and verifiable.
        - If the key were shorter, it wouldn't be sufficient for the `HS512` algorithm, which requires the secret to be at least 512 bits for the signing process to be cryptographically secure.

    - `admin.jwt.claim.sub=admin_sub`: Specifies the subject claim (`sub`) for the JWT token, representing the admin user's unique identifier (`admin_sub`).

    - `admin.jwt.claim.role=admin`: Specifies the role claim for the JWT token, setting the user role to `admin`.

    - `admin.jwt.claim.permission=read,write,delete`: Defines the permissions claim for the JWT token, listing the actions (`read`, `write`, and `delete`) the `admin` user is allowed to perform.

    - `admin.jwt.admin.expiration.ms=600000`: Sets the expiration time for the JWT token in milliseconds. `600000` ms equals 10 minutes, meaning the token will expire after 10 minutes.

    - `admin.jwt.claim.refresh.expiration.ms=1800000`: Sets the expiration time for the refresh token in milliseconds. `1800000` ms equals 30 minutes, meaning the refresh token will expire after 30 minutes.

---

## AdminTokenProvider

The `AdminTokenProvider` class is a critical component in handling JSON Web Tokens (JWT) for admin users within an application. It is responsible for generating, validating, and parsing JWT tokens, which are used to authenticate and authorize admin users.

### Overview of the Class

This class works with Spring's dependency injection and the JJWT library to manage the lifecycle of JWT tokens, allowing for secure user authentication. Here's a breakdown of what each part of the class does:

- **Secret Key Initialization:**
    - The class uses a secret key for signing JWT tokens. This secret key is injected from the application’s properties file and is used in the creation of the JWT signature.

- **Token Creation:**
    - The `generateToken()` method creates a new JWT with embedded claims such as the user's role, permissions, and subject (the identity of the admin). The generated token is signed with the secret key.

- **Token Validation:**
    - The `validateToken()` method ensures that the token is valid, checking both its signature and expiration date to confirm its authenticity.

- **Claims Extraction:**
    - The `getClaims()` method allows the extraction of claims from a given token, which can be used to determine the user's role, permissions, and whether the token is still valid.

### Detailed Explanation of Methods

#### `init()`

The `init()` method is annotated with `@Autowired` and is responsible for initializing the secret key that will be used to sign JWT tokens. It takes the secret key string (injected from the application properties file) and converts it into a `Key` object, which is used for cryptographic signing.

**Why is it called `init()`?**

- The method is called `init()` because it serves as an initialization function, configuring the class by converting the secret key string into a format suitable for cryptographic operations.
- In Spring, `@Autowired` annotation ensures that this method is called during the bean's lifecycle, and it will be invoked automatically when the `AdminTokenProvider` class is instantiated. The method doesn't require explicit invocation anywhere else in the code; Spring manages its call.

While you don't see `init()` being explicitly called in other parts of the code, Spring uses dependency injection to invoke it behind the scenes when the `AdminTokenProvider` is created, ensuring that the secret key is set up before the provider is used to generate or validate tokens.

#### `generateToken()`

This method is responsible for generating a new JWT. It:

- Sets the **subject**, **role**, and **permissions** claims from the injected values.
- Adds an issued timestamp (`setIssuedAt(new Date())`) to indicate when the token was created.
- Adds an expiration timestamp, calculated based on the expiration time configured in the properties file.
- Signs the JWT with the **secret key** using the **HS512** algorithm for security.
- Returns the generated JWT token as a compact string.

**What it does:**
- This method is called when you need to issue a new JWT token (e.g., after a successful login). It ensures that the JWT contains essential information, such as who the user is (subject), what they can do (permissions), and when their token will expire.

#### `getClaims(String token)`

The `getClaims()` method extracts the claims from the JWT token. Claims in a JWT contain important data about the user, such as their identity (subject), roles, permissions, and expiration date.

**What it does:**
- The method parses the JWT token using the **secret key** and returns the body of the JWT, which contains the claims.
- It uses the `Jwts.parserBuilder()` to validate the JWT’s signature using the `secretKey`, ensuring that the token has not been tampered with.
- The method returns a `Claims` object, which can be used to access the claims, such as the subject, role, and expiration date.

#### `validateToken(String token)`

This method checks the validity of the JWT token. It does two things:
1. **Validates the Signature**: Ensures that the token’s signature matches the expected signature using the secret key.
2. **Checks Expiration**: It verifies if the token has expired by comparing its expiration timestamp with the current system time.

**What it does:**
- It calls `getClaims()` to extract the claims from the token.
- If the token is expired (i.e., if the expiration date is before the current date), it throws a `JwtException`.
- If the signature is invalid or the token is expired, the method returns `false`.
- If everything is valid, it returns `true`.

**Why it is important:**
- This method ensures that a JWT token is both authentic (signed with the correct secret key) and still valid (not expired). This is crucial for ensuring secure authentication and authorization processes.

### Summary

- **Purpose**: The `AdminTokenProvider` class is used to create and validate JWT tokens, specifically for admin users, ensuring secure authentication and authorization.
- **Key Functions**: It generates JWT tokens with claims, validates their integrity and expiration, and allows claims to be extracted for further authorization checks.
- **Dependency Injection**: Spring’s `@Autowired` manages the initialization of the secret key through the `init()` method. This method is crucial for setting up the key used for cryptographic signing and is automatically invoked during the bean initialization process.
- **Security**: By using a secure signing algorithm (`HS512`), the class ensures that JWT tokens are cryptographically signed and cannot be tampered with, maintaining the integrity of the authentication process.

This class forms the backbone of the JWT-based authentication system for admin users in the application, enabling both secure token creation and validation.

---

## TokenAuthenticationFilter

The `TokenAuthenticationFilter` is a filter that is responsible for extracting, validating, and processing JWT tokens from incoming HTTP requests. It ensures that users are authenticated based on the validity of their JWT tokens. If a token is valid, the user's authentication context is set, enabling access to secured resources.

### Overview of the Class

This class extends Spring's `OncePerRequestFilter`, ensuring that the filter logic is executed once for every request that comes through the filter chain. It works by validating the JWT token extracted from the `Authorization` header of the HTTP request. If the token is valid, it sets the authenticated user’s details in the Spring Security context, allowing the user to proceed with the request.

Key points:
- **Token Extraction**: The JWT token is extracted from the `Authorization` header.
- **Token Validation**: The token is validated using the `AdminTokenProvider` class.
- **Authentication Context**: If the token is valid, the user's details are set in the authentication context.
- **Proceeding with the Request**: After handling the authentication, the filter continues the request processing by invoking the `filterChain.doFilter()` method.

### Detailed Explanation of Methods

#### `doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)`

This is the core method of the filter, which is invoked for every incoming HTTP request that passes through the filter chain.

- **Extracts the Token**: The method starts by extracting the JWT token from the request using the `getTokenFromRequest()` helper method. The token is expected to be in the `Authorization` header with a "Bearer " prefix.

- **Validates the Token**: If the token is present, it calls `adminTokenProvider.validateToken(token)` to validate the token's integrity and check its expiration.

- **Authentication Creation**: If the token is valid, it extracts the claims using `adminTokenProvider.getClaims(token)`. The subject of the token, which is typically the admin's username, is retrieved from the claims. A `UsernamePasswordAuthenticationToken` is then created with the username (the credentials are `null` since JWT is used for authentication).

- **Set Authentication Context**: The `SecurityContextHolder.getContext().setAuthentication(authentication)` line sets the created authentication in the Spring Security context, which marks the user as authenticated.

- **Proceeding with the Request**: After setting the authentication, the `filterChain.doFilter(request, response)` method is called to continue the request processing, allowing other filters or the controller logic to execute.

**Purpose**: This method ensures that the user is authenticated before accessing secured resources. If the JWT token is invalid or missing, the user will not be authenticated and will likely receive an HTTP 401 Unauthorized response.

#### `getTokenFromRequest(HttpServletRequest request)`

This method is responsible for extracting the JWT token from the `Authorization` header of the HTTP request.

- **Extract Authorization Header**: The method first retrieves the `Authorization` header from the incoming request using `request.getHeader("Authorization")`.

- **Check for Bearer Token**: The method checks if the header starts with the "Bearer " prefix, which is the standard format for passing JWT tokens. If the prefix is found, it strips off the prefix and returns the actual JWT token.

- **Return Token or Null**: If the token is present and correctly formatted, it is returned. If the header is missing or doesn't contain a valid Bearer token, the method returns `null`.

**Purpose**: This method ensures that the token is extracted correctly from the request and formatted properly (with the "Bearer " prefix removed). It enables the rest of the filter to work with the JWT token as a string.

### Why This Filter is Important

- **Secure API Endpoints**: The `TokenAuthenticationFilter` helps secure your application by checking the authenticity of the JWT token for each incoming request to protected endpoints.

- **Decouples Authentication Logic**: It separates the logic of authentication (validating and processing JWT tokens) from the rest of the application. This helps keep your authentication process modular and clean.

- **Efficient Authentication**: It ensures that once a user’s JWT token is validated, their identity is set in the `SecurityContext`, allowing subsequent requests to be authenticated automatically without needing to re-authenticate the user each time.

- **Supports Stateless Authentication**: By relying on JWT tokens for authentication, this filter supports a stateless authentication model, where there is no need to store session data on the server. All necessary information is stored within the JWT itself.

### Conclusion

- **Purpose**: The `TokenAuthenticationFilter` is a security filter that ensures only authenticated users with valid JWT tokens can access protected resources.
- **How it Works**: It extracts the JWT token from the request, validates it, and sets the authentication context if the token is valid. The filter continues the request processing after authentication.
- **Role in Application Security**: It provides an important piece of the security infrastructure, making sure that only authenticated users with valid tokens can interact with the application’s secured endpoints.

This filter plays a crucial role in ensuring that your application's authentication is handled securely and efficiently using JWT tokens.

---

## SecurityConfig

### Overview

The `SecurityConfig` class configures **Spring Security** for the application, setting up security filter chains and beans to manage user authentication and authorization processes. The class is marked with `@Configuration`, indicating that it's a source of bean definitions and configurations for the Spring application context. These beans are made available to the Spring context and are injected into various components that need them.

The two main security mechanisms in this configuration are **Basic Authentication** (for login) and **JWT Authentication** (for protected resources). Beans like the `AuthenticationManager`, `PasswordEncoder`, `UserDetailsService`, and custom filters like `TokenAuthenticationFilter` are defined in this configuration class.

### Key Beans Defined in `SecurityConfig`

1. **`AuthenticationManager` Bean**:
    - **Purpose**: The `AuthenticationManager` bean is responsible for handling user authentication by validating the credentials (username and password). This bean is critical for both **Basic Authentication** and **JWT Authentication**.
    - **Role**: It is used during the authentication process to validate the user credentials against a storage mechanism (e.g., in-memory user store or JWT token). The `AuthenticationManager` is implicitly used by Spring Security when authenticating users during login or JWT token validation.
    - **Injection**: It is not explicitly injected in the security filter chains. Instead, it is automatically used by Spring Security when authenticating users during login or JWT token validation.

2. **`PasswordEncoder` Bean**:
    - **Purpose**: The `PasswordEncoder` bean is used to encode passwords securely before storing them and to verify passwords during authentication. In this configuration, `BCryptPasswordEncoder` is used, which is a widely used algorithm known for its resistance to brute-force attacks.
    - **Role**: It plays a crucial role in ensuring that user passwords are not stored in plaintext. When a user tries to log in (either using Basic Authentication or JWT-based authentication), the `PasswordEncoder` is used to encode passwords before they are compared with stored hashed passwords.
    - **Injection**: It is injected into the `adminUserDetailsService` bean (through the method parameter), which uses it to encode the admin password before storing it in memory. It is indirectly used when the user’s password is checked against the encoded value during the authentication process.

3. **`UserDetailsService` Bean**:
    - **Purpose**: This bean is used to load user-specific data, such as usernames and passwords, and roles for authentication. In this case, the `InMemoryUserDetailsManager` is used, which is a simple in-memory user store for the admin user.
    - **Role**: The `UserDetailsService` bean is responsible for loading the admin user details during the authentication process, verifying the username and password, and applying role-based authorization.
    - **Injection**: The `UserDetailsService` bean is not directly injected into any classes in the `SecurityConfig`. Instead, it is used implicitly by Spring Security to authenticate the admin user when the `AuthenticationManager` is invoked.

4. **`TokenAuthenticationFilter` Bean**:
    - **Purpose**: The `TokenAuthenticationFilter` is a custom filter responsible for processing the JWT token. It extracts the token from the `Authorization` header, validates it, and sets the authentication context for the user if the token is valid.
    - **Role**: This custom filter plays a critical role in **JWT Authentication**. When a request is made to protected endpoints (such as `/shop/users` or `/shop/items`), this filter validates the JWT token, extracts the user's credentials (if valid), and places the authentication context into Spring Security’s context.
    - **Injection**: The `TokenAuthenticationFilter` is injected into the `SecurityConfig` constructor and is used in the JWT filter chain. It is added to the security filter chain using `addFilterBefore()` to ensure it runs before other default filters like `UsernamePasswordAuthenticationFilter`.

### Explanation of the Beans' Roles and Where They Are Used

The beans defined in this configuration class serve specific roles in Spring Security's overall flow. Let’s look at each bean’s role and where it gets used:

1. **AuthenticationManager**:
    - **Role**: The `AuthenticationManager` is responsible for validating credentials during both Basic Authentication and JWT Authentication processes.
    - **Where it is Used**: It is not explicitly referenced in the filter chains but is implicitly invoked during the authentication process when Spring Security handles authentication requests. For example, when using **Basic Authentication**, the `AuthenticationManager` will check the provided username and password. In **JWT Authentication**, the `AuthenticationManager` will be used as part of the overall authentication flow when validating the user's credentials extracted from the JWT token.

2. **PasswordEncoder**:
    - **Role**: Ensures that passwords are securely hashed using the BCrypt algorithm before they are stored or compared with stored credentials.
    - **Where it is Used**: The `PasswordEncoder` is injected into the `adminUserDetailsService` bean. This bean uses it to encode the admin user's password before storing it in memory. It is indirectly used when the user’s password is checked against the encoded value during the authentication process.

3. **UserDetailsService**:
    - **Role**: Responsible for loading user-specific data (username, password, roles) during the authentication process.
    - **Where it is Used**: This bean is used by Spring Security to load the details of the admin user for authentication. Specifically, when a user attempts to authenticate (e.g., logging in with the admin username and password), Spring Security uses this service to retrieve the user’s information for authentication. The `UserDetailsService` bean is used by Spring Security's internal authentication mechanism, leveraging the `AuthenticationManager`.

4. **TokenAuthenticationFilter**:
    - **Role**: This custom filter handles the extraction and validation of the JWT token from incoming requests. It ensures that the token is valid and authenticates the user.
    - **Where it is Used**: The `TokenAuthenticationFilter` is explicitly injected into the `SecurityConfig` class and added to the second security filter chain (JWT filter chain). It is used to secure the `/shop/users` and `/shop/items` endpoints by validating the JWT token in the `Authorization` header.

### Security Filter Chains and CSRF Protection

The configuration defines two security filter chains, one for **Basic Authentication** and one for **JWT Authentication**. These chains dictate how requests are handled and secured.

- **Basic Authentication Filter Chain**:
    - **Purpose**: This chain applies **Basic Authentication** to the `/shop/login` endpoint, allowing the user to authenticate using a username and password.
    - **CSRF Protection**: Since this filter chain is meant for stateless authentication (with a simple login process), CSRF protection is **disabled** explicitly using `csrf(AbstractHttpConfigurer::disable)`. This is standard for RESTful APIs, where CSRF is not required, as the session is not maintained.

- **JWT Authentication Filter Chain**:
    - **Purpose**: This filter chain secures the `/shop/users` and `/shop/items` endpoints by requiring a valid JWT token for authentication.
    - **CSRF Protection**: Similarly, CSRF protection is **disabled** for the same reasons — the application is stateless and uses JWTs for authentication.

### Conclusion

In summary, the `SecurityConfig` class is crucial for setting up security in a Spring application, and the beans defined in this configuration play specific roles in handling authentication and authorization. These beans are made available to the Spring context and are injected where necessary. Here's a summary of key beans:

- **`AuthenticationManager`**: Used for authentication during both Basic Authentication and JWT Authentication, though it’s not directly injected into the filters.
- **`PasswordEncoder`**: Used to securely hash passwords, especially in the `adminUserDetailsService`.
- **`UserDetailsService`**: Used by Spring Security to load the admin user’s details for authentication.
- **`TokenAuthenticationFilter`**: A custom filter used to validate JWT tokens for protected endpoints.

These beans are all part of the **Spring Security** configuration and enable the application to authenticate and authorize users effectively. By making these beans available to the Spring context, the security configuration ensures that different parts of the application are secured according to the specified rules.

---

## AdminUserConfig

The `AdminUserConfig` class is a configuration class responsible for setting up an in-memory user store for an admin user in a Spring Security-based application. This class leverages the power of Spring's **dependency injection** and **auto-configuration** features to make the admin user configuration available throughout the application.

### 1. **`@Configuration` Annotation**:

The `@Configuration` annotation marks the `AdminUserConfig` class as a **configuration class**. This tells Spring that the class contains bean definitions, and Spring should process this class to register the beans within the **Spring Application Context**.

- **Bean Definitions**:
    - The class defines beans related to the admin user’s credentials, such as a `UserDetailsService` and a `PasswordEncoder`.
    - These beans are automatically registered in the Spring context when the application starts.

### 2. **`@Value` Annotation for Property Injection**:

The `@Value` annotation is used to inject values from the **application properties** (or `application.yml`) into the fields `adminUsername` and `adminPassword`.

- **Property Injection**:
    - The `adminUsername` and `adminPassword` fields are populated by Spring with values from the application's properties file (e.g., `admin.username` and `admin.password`).
    - This allows the class to access sensitive configuration values (like the admin credentials) directly from the properties file.

### 3. **Bean Method Definitions**:

#### 3.1. **`adminUserDetailsService()` Method**:

This method defines a `UserDetailsService` bean, which Spring Security uses to load user-specific data (username, password, roles, etc.) for authentication.

- **`InMemoryUserDetailsManager`**:
    - The method creates an `InMemoryUserDetailsManager` that holds user data in memory.
    - The `InMemoryUserDetailsManager` is configured with a single admin user. The admin username and password are fetched from the `application.properties` file, and the password is **encoded** using a `PasswordEncoder`.

- **Role of `UserDetailsService`**:
    - The `UserDetailsService` bean is a core component in the Spring Security authentication process.
    - Spring Security automatically uses this service to load the admin user's details during authentication (e.g., checking credentials when logging in).

- **Password Encoding**:
    - The `PasswordEncoder` bean is used to encode the password before storing it in the `InMemoryUserDetailsManager`. This ensures that sensitive user data (the password) is stored securely.

  **Note**: This bean is automatically available in the Spring context and is injected into the `adminUserDetailsService` method by Spring.

#### 3.2. **`passwordEncoder()` Method**:

This method defines a `PasswordEncoder` bean. A `PasswordEncoder` is used to securely encode passwords during authentication.

- **`BCryptPasswordEncoder`**:
    - The `passwordEncoder()` method returns an instance of `BCryptPasswordEncoder`, a widely used hashing algorithm for securely hashing and validating passwords.
    - **BCrypt** is computationally expensive, which makes it resistant to brute-force and rainbow table attacks.

- **Role of `PasswordEncoder`**:
    - Spring Security uses this `PasswordEncoder` during the authentication process to hash and verify passwords. It ensures that passwords are not stored in plain text and are verified in a secure manner.

### 4. **How Spring Security Uses These Beans**:

Even though `AdminUserConfig` is not explicitly injected into the `SecurityConfig` class, the beans defined in it are still automatically available to Spring Security. Here’s how this works:

#### 4.1. **Automatic Bean Discovery**:

- **Spring’s Auto-Configuration**:
    - Spring Boot and Spring Security are designed to automatically discover beans defined in the Spring context.
    - The beans defined in `AdminUserConfig` (like `UserDetailsService` and `PasswordEncoder`) are **automatically registered in the Spring context** during application startup because the class is annotated with `@Configuration` and contains `@Bean`-annotated methods.

- **Dependency Injection by Type**:
    - Spring Security **auto-discovers** the `UserDetailsService` and `PasswordEncoder` beans by their type during the application context initialization.
    - These beans are injected into the security filters or authentication managers as needed.

#### 4.2. **Role of `UserDetailsService` in Authentication**:

- **Authentication Manager**:
    - In Spring Security, the `AuthenticationManager` is responsible for authenticating users. When a user logs in, the `AuthenticationManager` needs to load user details (e.g., username, password, roles) for authentication.
    - The `AuthenticationManager` uses the `UserDetailsService` (which is `InMemoryUserDetailsManager` in this case) to retrieve the admin user details.

- **Admin User Details**:
    - The `UserDetailsService` defined in `AdminUserConfig` returns the admin user with the configured username and encoded password.
    - Spring Security uses this service to authenticate the admin user against the stored credentials.

#### 4.3. **Role of `PasswordEncoder`**:

- **Password Validation**:
    - The `PasswordEncoder` (i.e., `BCryptPasswordEncoder`) is used to verify that the password entered by the user matches the encoded password stored in the `InMemoryUserDetailsManager`.
    - This ensures that passwords are never stored or compared in plain text and are securely validated.

### 5. **How It All Comes Together in `SecurityConfig`**:

While `SecurityConfig` does not explicitly inject `AdminUserConfig`, the `SecurityConfig` class still benefits from the beans defined in `AdminUserConfig` due to the auto-configuration and bean discovery mechanisms provided by Spring.

- **Implicit Bean Injection**:
    - Spring Security automatically injects the `UserDetailsService` and `PasswordEncoder` beans into the authentication process, even though `SecurityConfig` does not explicitly reference them.
    - The beans are picked up from the Spring context, and Spring Security uses them for user authentication when requests are made to the protected endpoints.

### 6. **Final Flow**:
- The `UserDetailsService` (i.e., `InMemoryUserDetailsManager`) and `PasswordEncoder` beans are registered in the Spring context by `AdminUserConfig`.
- Spring Security’s **auto-configuration** mechanism ensures these beans are available to components like the `AuthenticationManager`.
- The **AuthenticationManager** uses the `UserDetailsService` to load user data and the `PasswordEncoder` to validate passwords during the login process.

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
curl -X GET "http://localhost:8080/shop/users?email=alice@example.com" \
     -H "Authorization: Bearer <YOUR_TOKEN>"
```

### Retrieve a User by Username
```bash
curl -X GET "http://localhost:8080/shop/users?username=alice01" \
     -H "Authorization: Bearer <YOUR_TOKEN>"
```

### Update a User
```bash
curl -X PUT http://localhost:8080/shop/users \
     -H "Authorization: Bearer <YOUR_TOKEN>" \
     -H "Content-Type: application/json" \
     -d '{
           "username": "alice_updated",
           "email": "alice@example.com"
         }'
```

Check update:
```sql
SELECT * FROM shop_schema.User WHERE email = 'alice@example.com';
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
