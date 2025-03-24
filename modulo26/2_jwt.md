# JWT (JSON Web Token)
JSON Web Token (JWT) is an open standard (RFC 7519) that defines a compact and self-contained way for securely transmitting information between parties as a JSON object. This information can be verified and trusted because it is digitally signed using a secret (HMAC algorithm) or a public/private key pair (RSA or ECDSA).

JWTs are commonly used for **authentication** and **authorization** in web applications, APIs, and microservices architectures.

JWT is widely used due to its efficiency and security features. Some of its key benefits include:

1. **Stateless Authentication**: JWT eliminates the need for a session state stored on the server, allowing scalability in distributed systems.
2. **Compact & Fast**: Being a compact token, JWTs are easily transmitted over HTTP headers, making them suitable for web applications and mobile devices.
3. **Security**: JWTs can be signed (integrity protection) and optionally encrypted (confidentiality protection), ensuring secure data transmission.
4. **Interoperability**: JWTs are widely adopted across different programming languages and frameworks.
5. **Fine-grained Access Control**: JWT can include claims (permissions, user roles) in the payload, allowing precise access control.

---

## Authenticating Multiple Requests: JWT as a replacement for session cookies

Modern applications often require users to be authenticated across multiple requests. Managing this authentication process securely and efficiently is crucial. This article explores the common issues developers face with authentication, the evolution from Basic Authentication to session management, and how JWT (JSON Web Token) has become a standard solution to overcome many of these challenges.

### The Challenge: Managing User Authentication Across Requests

In a typical application, authentication involves two main concerns:

- **Identifying the user** (e.g., username and password).
- **Maintaining authentication status** across multiple requests.

Let’s walk through the journey of addressing this problem.

### Basic Authentication: The Starting Point

**Basic Authentication** involves sending the username and password with every HTTP request. While it's simple to implement, it comes with significant drawbacks:

#### Drawbacks of Basic Authentication:
- **Security Risks**: Credentials are sent with every request. Even over HTTPS, repeated transmission increases the risk of exposure.
- **Lack of Scalability**: Every request requires authentication against the server, often involving a database lookup.
- **State Management**: Basic Auth typically requires session handling to maintain user state, which can complicate scaling in distributed environments.
- **Inefficient for SSO**: Not suitable for cross-domain authentication or modern Single Sign-On (SSO) implementations.

### Improving the Flow: Session-Based Authentication

To avoid sending credentials repeatedly, applications began using session-based authentication. Here's how it works:

1. **User logs in with credentials.**
2. **Server authenticates the user and creates a session.**
3. **Session data is stored server-side (e.g., in-memory, Redis).**
4. **A session cookie is sent to the client.**
5. **Client includes the session cookie with every request.**

#### Session Data Typically Includes:
- **User identity** (e.g., user ID, username)
- **User roles and permissions** (e.g., `admin`, `user`)
- **Authentication claims** (e.g., email, attributes)
- **Expiration time**

While more secure than Basic Auth, session-based authentication introduces challenges:
- Requires server-side storage, complicating scalability.
- Not easily portable across distributed systems or APIs.
- Vulnerable to CSRF if not implemented properly.

### Enter JWT: A Modern, Stateless Solution

**JWT (JSON Web Token)** was introduced to address these limitations by enabling stateless, secure, and scalable authentication.

#### How JWT Works:
1. **User logs in with credentials.**
2. **Server authenticates and issues a signed JWT.**
3. **Client stores the JWT (e.g., in localStorage or memory).**
4. **Client sends the JWT in the `Authorization` header with each request.**
5. **Server verifies the token without needing to store session data.**

### Basic Authentication vs JWT

| Feature                     | Basic Authentication                      | JWT                                           |
|-----------------------------|-------------------------------------------|-----------------------------------------------|
| **Security of Credentials** | Sends username/password with each request | Token sent instead of credentials             |
| **Statelessness**           | No, requires server-side sessions         | Yes, fully stateless                          |
| **Session Management**      | Server stores user session                | Token stores claims, no server session needed |
| **Cross-Domain (SSO)**      | Poor support                              | Excellent support                             |
| **Token Expiration**        | Not built-in                              | Built-in with `exp` claim                     |
| **Revocation**              | Hard to implement                         | Supported via refresh tokens or blacklists    |
| **Custom Claims**           | Not supported                             | Supported (roles, permissions, etc.)          |

---

## JWT basic flow
JWT tokens typically consist of three parts:
1. **Header**: Contains the metadata about the token (e.g., the signing algorithm used, such as HMAC or RSA).
2. **Payload**: Contains the claims or data that you want to transmit (e.g., user ID, permissions, expiration time).
3. **Signature**: A cryptographic signature to verify that the token has not been tampered with. This signature is generated using a secret key or a public/private key pair.

When a user logs in, the server generates a JWT token with the user's information and signs it. The user then includes this token in subsequent requests, and the server verifies the token's validity (using the signature) before granting access to resources.

While Basic Authentication is simple and effective for small or internal applications, JWT provides a more scalable, secure, and flexible solution for modern web applications, APIs, and distributed systems. By addressing security risks like password exposure, enhancing scalability with stateless authentication, supporting Single Sign-On (SSO), and providing better session management, JWT has become the preferred method for handling authentication in many modern applications.

---

## JWT Components

A **JSON Web Token (JWT)** is a compact, URL-safe token format used for authentication and secure data exchange between parties. It consists of three parts, separated by dots (`.`):

```
Header.Payload.Signature
```

Each of these components is **Base64Url encoded**, which ensures that the encoded string is URL-safe and can be transmitted over the internet without issues. Unlike regular Base64 encoding, Base64Url replaces characters like `+` and `/` with `-` and `_` respectively, and omits padding characters like `=`.

---

## Header

The **header** contains metadata about the token, including the algorithm used to sign it and the type of token.

### Header Parameters

#### `alg` (Algorithm)
- Specifies the algorithm used for signing the token.
- Common values: `HS256`, `RS256`, `ES256`.

#### `typ` (Type)
- Identifies the token type, typically set to `JWT`.

### Example Header JSON
```json
{
  "alg": "HS256",
  "typ": "JWT"
}
```

### Base64Url Encoding
Encoded header:
```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9
```

---

## Payload

The **payload** is the part of a JSON Web Token (JWT) that contains the **claims**—statements about an entity (typically a user) and additional metadata. While the payload is encoded, it is **not encrypted**, meaning its contents can be read by anyone with access to the token.

Claims are key-value pairs that provide information to the recipient of the token. They play a central role in defining the token’s purpose and context. Claims can be categorized into three types: **registered**, **public**, and **private**.

### Registered Claims
Registered claims are a set of predefined claims that are **recommended** by the JWT specification (RFC 7519). These claims are not mandatory, but they have **well-defined semantics** to support interoperability across different systems. Some of these claims may be used during **token validation**.

- `iss` (Issuer): Identifies the principal that issued the JWT. Often used to verify the origin of the token.
- `sub` (Subject): Identifies the subject of the token, usually the user.
- `aud` (Audience): Identifies the intended recipients of the token. The receiving system can verify if it is the intended audience.
- `exp` (Expiration Time): Defines the token’s expiration. If the current time is after this timestamp, the token is considered invalid.
- `nbf` (Not Before): Specifies the time before which the token must not be accepted.
- `iat` (Issued At): Indicates when the token was issued. Can be used to determine token age.
- `jti` (JWT ID): A unique identifier for the token, useful for preventing replay attacks.

These claims are **not reserved keywords** in the programming sense, but they are **standardized fields** recognized by JWT libraries and frameworks. Including or omitting them affects how the token is validated and interpreted.

#### Example Registered Claims JSON
```json
{
  "sub": "1234567890",
  "name": "John Doe",
  "admin": true,
  "iat": 1710336000,
  "exp": 1710422400
}
```

#### Base64Url Encoding
Encoded payload:
```
eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTcxMDMzNjAwMH0
```

#### Example of Registered Claim Validation in Code (Java)
Here's a simplified example of how a JWT library might use registered claims during validation in Java using the `java-jwt` library:

```java
Algorithm algorithm = Algorithm.HMAC256("secret");
JWTVerifier verifier = JWT.require(algorithm)
    .withIssuer("https://auth.myapp.com")
    .withAudience("my-api")
    .build();

DecodedJWT jwt = verifier.verify(token);
System.out.println("Token is valid. Subject: " + jwt.getSubject());
```

In this example, the verifier will check whether the `iss` and `aud` claims match the expected values. If they don’t, an exception is thrown.

### Public Claims
Public claims are custom claims intended to be shared openly, provided they don’t conflict with registered claims. These should be **defined in a way that avoids collisions**, typically using **namespaced keys** or referencing public claim registries.

While they may seem similar to private claims, public claims are designed to be more **standardized** across applications, allowing external services or libraries to potentially recognize and act on them. They serve as a middle ground between the formal structure of registered claims and the freeform nature of private ones.

Example:
```json
{
  "https://mycompany.com/role": "admin",
  "https://mycompany.com/permissions": ["read", "write", "delete"]
}
```

#### Example Use Case for a Public Claim
Suppose you’re integrating with a third-party analytics dashboard that reads JWTs to determine a user’s access level. Rather than using a private claim (which the third-party wouldn’t recognize), you could use a **namespaced public claim** like:

```json
{
  "https://analytics.vendor.com/access-tier": "premium"
}
```

Because it’s public and namespaced, the third-party service knows to look for that claim and interpret its value. This allows multiple systems to interoperate without having to pre-negotiate all custom field names.

### Private Claims
Private claims are completely **custom** and are used to transmit information **agreed upon between the issuer and the consumer** of the token. These are not registered or intended for public use, and are the most flexible.

They do **not play a role in standard JWT validation**, but they can be used to enforce application-specific logic.

Example:
```json
{
  "user_id": "abc-123",
  "department": "engineering",
  "theme": "dark-mode"
}
```

#### Example Use of a Private Claim in Java
```java
String department = jwt.getClaim("department").asString();
if ("engineering".equals(department)) {
    // custom app logic for engineering department
}
```

You are free to include any number of private claims to carry information your application needs. Just be aware that because the payload is readable, sensitive data should not be stored unless the token is encrypted.

---

## Signature

The **signature** ensures the integrity and authenticity of the token. It is generated by encoding the header and payload, concatenating them with a dot (`.`), and signing the result using a secret key or a private key, depending on the algorithm.

### Signing Process (e.g., using HMAC SHA-256)
```
HMACSHA256(
  base64UrlEncode(header) + "." + base64UrlEncode(payload),
  secret
)
```

If the secret key is `mysecretkey`, the resulting signature might look like:
```
SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
```

### Signing Algorithms

#### HMAC (Hash-based Message Authentication Code)
- Uses a shared secret key.
- Example: `HS256`

#### RSA (Rivest-Shamir-Adleman)
- Uses a public-private key pair.
- Example: `RS256`

#### ECDSA (Elliptic Curve Digital Signature Algorithm)
- Uses elliptic curve cryptography.
- Example: `ES256`

---

## Final JWT Structure

A full JWT consists of the Base64Url-encoded header, payload, and signature. Before encoding and signing, the JWT components look like this:

**Header (JSON):**
```json
{
  "alg": "HS256",
  "typ": "JWT"
}
```

**Payload (JSON):**
```json
{
  "sub": "1234567890",
  "name": "John Doe",
  "admin": true,
  "iat": 1710336000,
  "exp": 1710422400
}
```

**Signature (Raw Input for HMAC SHA-256):**
```
HMACSHA256(
  base64UrlEncode(header) + "." + base64UrlEncode(payload),
  secret
)
```

After encoding and signing, the resulting JWT looks like:
```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTcxMDMzNjAwMCwiZXhwIjoxNzEwNDIyNDAwfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
```

This compact, URL-safe string can now be used as an access token in HTTP headers or other data transmissions.

---

## Verification Process
1. Decode the JWT.
2. Verify the signature using the appropriate algorithm and key.
3. Validate claims (e.g., expiration time, issuer).

---

# JWT Usage
JWT (JSON Web Token) is a compact, URL-safe means of representing claims securely between two parties. JWTs are commonly used for authentication and information exchange in web applications and APIs. A JWT consists of three parts:

1. **Header**: Contains metadata about the token, such as the signing algorithm.
2. **Payload**: Holds the claims, which can include user details and additional metadata.
3. **Signature**: Used to verify that the token has not been tampered with.

JWTs are encoded using Base64URL and signed using either an HMAC secret or an asymmetric key pair.

---

## Generating JWT
Generating a JWT involves the following steps:

1. **Construct the Header**: Defines the type of token (`JWT`) and the algorithm used for signing (e.g., `HS256`, `RS256`).
2. **Define the Payload**: Contains the claims, such as user identifiers, expiration, roles, and other metadata.
3. **Sign the Token**: Uses a secret key or private key to generate the signature.
4. **Encode the JWT**: The header, payload, and signature are concatenated using dots (`.`) to form the final token.

### Example JWT Structure (JSON):
```text
{
  "header": {
    "alg": "HS256",
    "typ": "JWT"
  },
  "payload": {
    "sub": "1234567890",
    "name": "John Doe",
    "iat": 1710000000,
    "exp": 1710600000
  },
  "signature": "HMACSHA256(Base64UrlEncode(header) + "." + Base64UrlEncode(payload), secret)"
}
```

---

## Sending JWT in HTTP Requests
Once generated, a JWT must be included in HTTP requests to authenticate users. The most common method is via the `Authorization` header using the `Bearer` scheme.

### Example HTTP Request with JWT:
```json
{
  "method": "GET",
  "url": "https://api.example.com/protected-resource",
  "headers": {
    "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNzEwMDAwMDAwLCJleHAiOjE3MTA2MDAwMDB9.abcdefg12345"
  }
}
```

---

## Validating JWT
JWT validation ensures that the token is legitimate, has not been tampered with, and is still valid. Validation includes:

1. **Checking the Signature**: Verifying the token’s signature using the shared secret (HMAC) or public key (RSA/ECDSA).
2. **Decoding the Token**: Extracting and reading the payload.
3. **Verifying Claims**: Ensuring mandatory claims like `iss` (issuer), `aud` (audience), and `sub` (subject) match expected values.
4. **Checking Expiration**: Ensuring the token has not expired (`exp` claim).

### Example JWT Validation (JSON Response):
```json
{
  "isValid": true,
  "claims": {
    "sub": "1234567890",
    "name": "John Doe",
    "iat": 1710000000,
    "exp": 1710600000
  },
  "signatureVerified": true,
  "issuerVerified": true,
  "expired": false
}
```

---

## JWT Expiration
Expiration (`exp` claim) defines when a JWT becomes invalid. Tokens should have a reasonable lifespan to minimize security risks. If a token expires, clients need to obtain a new one through a refresh mechanism or re-authentication.

### Example Expired JWT Response (JSON):
```json
{
  "error": "TokenExpiredError",
  "message": "The provided JWT has expired",
  "expiredAt": 1710600000
}
```

**Best Practices for Handling Expiration:**
- Use short-lived tokens with refresh tokens for long-term access.
- Implement token rotation to mitigate replay attacks.
- Ensure secure storage and transmission of JWTs.

---

## JWT Security

JSON Web Tokens (JWTs) are widely used in modern applications for stateless authentication and authorization. While JWTs provide convenience and scalability, they also introduce unique security challenges. In this section, we'll delve into the critical aspects of JWT security, including token expiration, revocation, scope, and leakage, along with best practices for secure implementation.

### Token Expiration

Tokens should have a finite lifespan to limit exposure in case of compromise. JWTs include an `exp` (expiration) claim that defines the token's validity period. Short-lived tokens reduce the attack surface:

```java
String token = Jwts.builder()
    .setSubject("1234567890")
    .setIssuedAt(new Date(System.currentTimeMillis()))
    .setExpiration(new Date(System.currentTimeMillis() + 900_000)) // 15 minutes
    .signWith(SignatureAlgorithm.HS256, secretKey)
    .compact();
```

A common pattern is to combine short-lived access tokens with refresh tokens that can be revoked or rotated, minimizing the need to reauthenticate users while maintaining security.

### Token Revocation

JWTs are self-contained and typically not stored server-side, making revocation non-trivial. Several strategies can mitigate this:

- **Blacklist Storage**: Maintain a list of revoked token IDs (jti) or subjects, checked on each request. This introduces state but allows immediate invalidation.
- **Refresh Token Rotation**: Issue a new refresh token on each use, invalidating the previous. Detection of reuse indicates compromise.
- **Short-lived Tokens**: Reduce reliance on revocation by minimizing token lifespan.

Example blacklist strategy:
```java
Set<String> revokedJtis = new HashSet<>(Arrays.asList("abc123", "def456"));

public boolean isTokenRevoked(Claims claims) {
    String jti = claims.getId();
    return revokedJtis.contains(jti);
}
```

### Token Scope

Scopes define the level of access granted by the token, typically through a `scope` claim:
```java
String token = Jwts.builder()
    .setSubject("user1")
    .claim("scope", "read:messages write:messages")
    .signWith(SignatureAlgorithm.HS256, secretKey)
    .compact();
```

Fine-grained scope enforcement is essential for least-privilege access control. Use middleware or filters to validate scopes at runtime.

Example:
```java
String requiredScope = "write:messages";
String tokenScope = claims.get("scope", String.class);

if (!Arrays.asList(tokenScope.split(" ")).contains(requiredScope)) {
    throw new SecurityException("Insufficient scope");
}
```

### Token Leakage

JWTs are bearer tokens: anyone in possession of a valid token can access protected resources. Leakage can occur via:

- Logging JWTs inadvertently
- Storing tokens in insecure local storage or cookies
- Transmitting tokens over non-HTTPS channels

To mitigate leakage:

- Never log entire JWTs
- Store tokens in secure HTTP-only, SameSite cookies if applicable
- Enforce HTTPS across all services
- Monitor for token misuse with behavioral analysis

### Ensuring JWT Integrity & Preventing Tampering

Integrity ensures that the token hasn’t been altered. This is done by verifying the signature of the JWT using the same secret (HS256) or a public key (RS256):

```java
Claims claims = Jwts.parser()
    .setSigningKey(publicKey)
    .parseClaimsJws(token)
    .getBody();
```

Always verify the token signature before using any of its claims. Avoid accepting unsigned tokens or those signed with weak or unexpected algorithms.

### Replay Attacks

Token replay occurs when an attacker captures a valid token and reuses it. Mitigations include:

- **Use short-lived tokens**
- **Bind tokens to client context** (e.g., IP, user-agent fingerprint)
- **Track usage** via jti or nonce claims

```java
String jti = claims.getId();
if (usedJtis.contains(jti)) {
    throw new SecurityException("Replay detected");
} else {
    usedJtis.add(jti);
}
```

### Best Practices

#### Use Strong Algorithms
Always use robust signing algorithms like `RS256` (asymmetric) or `HS256` (symmetric). Avoid `none` or outdated algorithms.

```java
String token = Jwts.builder()
    .setClaims(claims)
    .signWith(SignatureAlgorithm.RS256, privateKey)
    .compact();
```

Use key rotation strategies to limit the blast radius of a compromised key.

#### Keep Tokens Short-lived
Short-lived tokens reduce risk from token leakage. Aim for access tokens with a TTL under 15 minutes when possible.

Combine with rotating refresh tokens for seamless UX:
```java
Map<String, String> tokenPair = new HashMap<>();
tokenPair.put("access_token", generateAccessToken(user));
tokenPair.put("refresh_token", generateRefreshToken(user));
```

#### Avoid Storing Sensitive Information
JWTs are base64-encoded, not encrypted. Avoid including PII, passwords, or secrets.

Instead of:
```java
claims.put("email", "alice@example.com");
claims.put("ssn", "123-45-6789");
```

Use opaque identifiers and fetch sensitive data from secure backend services.

#### Token Validation and Verification
Always validate the following claims during verification:

- `exp` (expiration time)
- `nbf` (not before)
- `iss` (issuer)
- `aud` (audience)

```java
Claims claims = Jwts.parser()
    .requireIssuer("my-auth-server")
    .requireAudience("my-api")
    .setSigningKey(publicKey)
    .parseClaimsJws(token)
    .getBody();
```

#### Token Payload Size Considerations
JWTs are transmitted on every request, so size impacts performance. Avoid bloated payloads:

- Strip unused claims
- Use compact claim names
- Externalize large data (e.g., permissions)

#### Use HTTPS
Always transmit JWTs over HTTPS to prevent MITM attacks and token theft.

Enforce secure headers like:
```http
Strict-Transport-Security: max-age=63072000; includeSubDomains
```

---
