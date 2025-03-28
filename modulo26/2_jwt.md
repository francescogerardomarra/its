# JWT
JSON Web Token (JWT) is an open standard (RFC 7519) that defines a compact and self-contained way for securely transmitting information between parties as a JSON object. This information can be verified and trusted because it is digitally signed using a secret (HMAC algorithm) or a public/private key pair (RSA or ECDSA).

JWTs are commonly used for **authentication** and **authorization** in web applications, APIs, and microservices architectures.

JWT is widely used due to its efficiency and security features. Some of its key benefits include:

1. **Stateless Authentication**: JWT eliminates the need for an authentication-related session state stored on the server, allowing scalability in distributed systems.
2. **Compact & Fast**: Being a compact token, JWTs are easily transmitted over HTTP headers, making them suitable for web applications and mobile devices.
3. **Security**: JWTs can be signed (integrity protection) and optionally encrypted (confidentiality protection), ensuring secure data transmission.
4. **Interoperability**: JWTs are widely adopted across different programming languages and frameworks.
5. **Fine-grained Access Control**: JWT can include claims (permissions, user roles) in the payload, allowing precise access control.

---

## Authenticating Multiple Requests: JWT as a replacement for session cookies

Modern applications often require users to be authenticated across multiple requests. Managing this authentication process securely and efficiently is crucial. This article explores the common issues developers face with authentication, the evolution from Basic Authentication to session management, and how JWT (JSON Web Token) has become a standard solution to overcome many of these challenges.

### Managing User Authentication Across Requests

In a typical application, authentication involves two main concerns:

- **Identifying the user** (e.g., username and password).
- **Maintaining authentication status** across multiple requests.

Let’s walk through the journey of addressing this problem.

### Basic Authentication

**Basic Authentication** involves sending the username and password with every HTTP request. While it's simple to implement, it comes with significant drawbacks:

#### Drawbacks of Basic Authentication:
- **Security Risks**: Credentials are sent with every request. Even over HTTPS, repeated transmission increases the risk of exposure.
- **Lack of Scalability**: Every request requires authentication against the server, often involving a database lookup.
- **State Management**: Basic Auth typically requires session handling to maintain user state, which can complicate scaling in distributed environments.
- **Inefficient for SSO**: Not suitable for cross-domain authentication or modern Single Sign-On (SSO) implementations.

### Session-Based Authentication

To avoid sending credentials repeatedly, applications began using session-based authentication. Here's how it works:

1. **User logs in with credentials.**
2. **Server authenticates the user and creates a session.**
3. **Authentication-related session data is stored server-side (e.g., in-memory, Redis).**
4. **A session cookie is sent to the client.**
5. **Client includes the session cookie with every request.**

#### Authentication-related Session Data Typically Includes:
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
5. **Server verifies the token without needing to store authentication-related session data.**

### Basic Authentication vs JWT

**Table 1**

| Feature                     | Basic Authentication                                     | JWT                                                                  |
|-----------------------------|----------------------------------------------------------|----------------------------------------------------------------------|
| **Security of Credentials** | Sends username/password with each request                | Token sent instead of credentials                                    |
| **Statelessness**           | No, requires server-side authentication-related sessions | Yes, fully stateless                                                 |
| **Session Management**      | Server stores user authentication-related session        | Token stores claims, no server authentication-related session needed |
| **Cross-Domain (SSO)**      | Poor support                                             | Excellent support                                                    |
| **Token Expiration**        | Not built-in                                             | Built-in with `exp` claim                                            |
| **Revocation**              | Hard to implement                                        | Supported via refresh tokens or blacklists                           |
| **Custom Claims**           | Not supported                                            | Supported (roles, permissions, etc.)                                 |

**Table 2**

| **Problem**                          | **Basic Authentication**                                                                                                  | **JWT Resolution**                                                                                                                                        |
|--------------------------------------|---------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Security Risks**                   | Transmits credentials (username and password) in plaintext with each request, making them vulnerable to interception.     | JWT tokens are digitally signed, providing integrity and authenticity. Tokens can be encrypted to ensure confidentiality of sensitive information.        |
| **Scalability and Performance**      | Requires server validation with each request, leading to performance overhead, especially in distributed systems.         | JWT tokens are self-contained, reducing the need for server-side validation, which improves scalability and performance.                                  |
| **Session Management**               | Relies on server-side authentication-related session management, which can be cumbersome and prone to scalability issues. | JWT tokens eliminate the need for server-side authentication-related session management, improving scalability and reducing overhead.                     |
| **Security Features**                | Lacks built-in support for features like token expiration, role-based access control, or user-specific claims.            | JWT tokens can include expiration times, user roles, permissions, and custom claims, enabling fine-grained access control and enhanced security policies. |
| **Interoperability and Flexibility** | Relies heavily on HTTP headers, which might not be suitable for all types of requests.                                    | JWT tokens can be transmitted via headers, query parameters, or request bodies, providing greater flexibility and interoperability.                       |

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

### 🧩 Public Claims (with Examples)

#### What Are Public Claims?

**Public claims** are custom claims meant to be **shared and recognized across different systems and services**. They follow naming conventions—usually involving **namespaces**—to avoid clashes with standard or other custom claims.

At their core, public and private claims are structurally the same: they are both key-value pairs in a JWT payload. What differentiates them is **intent and interpretation**.

- **Public claims** are **meant to be shared** and interpreted by external systems.
- **Private claims** are **meant for internal use** within your ecosystem.

#### Naming Convention

The key naming convention for public claims is **namespacing**, typically using a **URI or domain-based structure**.

Why?
1. It ensures uniqueness
2. It points to an authoritative source (your domain)
3. It allows third parties to locate documentation (if you publish it)

Example:
```json
"https://mycompany.com/role": "editor"
```
This namespaced key reduces the risk of name collision and signals that `mycompany.com` owns the definition.

✅ Good: `https://yourdomain.com/subscriptionTier`: `"pro"`
❌ Bad: `subscriptionTier`: `"pro"` — might conflict with other claims named the same.

> 🔍 Important: Simply using a namespaced URI does **not automatically** make the claim meaningful to third parties. It must be accompanied by documentation or agreement.

#### When to Use Public Claims

Use **public claims** when:
- Data in the JWT will be consumed by **third-party systems** or **external APIs**
- The meaning of the claim should be **standardized** or **documented**
- You want to ensure **cross-system compatibility**

##### Example Use Case
You're issuing a token for a customer using a third-party helpdesk system that determines support level based on user tier:
```json
"https://support.mycompany.com/accessLevel": "premium"
```
This is only meaningful if `support.mycompany.com` (or a partner using it) has access to **documentation that defines `accessLevel`** and understands what "premium" means.

Without documentation, it's just a string—they won't know what to do with it.

#### When to Use Private Claims

Use **private claims** when:
- Data is meant for **internal use** only
- You're communicating between **your own microservices or internal systems**
- There’s no need to document or expose the claim to external consumers

##### Example:
```json
"role": "admin"
```
This is fine if only your internal services consume the JWT. But it won’t mean anything to a third-party tool unless pre-agreed.

#### Real-World Comparison

| Feature               | Public Claim                                | Private Claim                  |
|----------------------|----------------------------------------------|--------------------------------|
| Naming convention    | Namespaced (e.g., URL-style)                 | Free-form                     |
| Shared externally?   | Yes, with docs or agreement                  | No (unless pre-agreed)        |
| Risk of conflicts    | Low (if namespaced)                          | High                          |
| Use case example     | `"https://api.partner.com/role": "admin"`    | `"role": "admin"`             |
| Self-explanatory?    | Only if namespace is documented              | No                            |

#### Advantages of Using Public Claims

1. **Interoperability**
  - Can be reliably used across systems and tools
  - ✅ Example: A third-party BI tool reads `"https://mycompany.com/plan": "enterprise"`, and your docs define what "enterprise" means

2. **Avoiding Conflicts**
  - Namespacing avoids overlap with registered claims like `email`, `sub`, or `iss`

3. **Standardization**
  - Your ecosystem can rely on consistent claim names
  - ✅ Example: `"https://mycompany.com/tenantId"` is recognized across all your services

4. **Data Sharing**
  - Enables clean B2B and third-party integrations
  - ✅ Example: A payment processor reads `"https://mycompany.com/accountStatus": "active"` and understands the account state

> 📘 Tip: If you’re building a public API or identity provider, always document your public claim names and expected values.

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

## JWT validation
JWT validation ensures that the token is legitimate, has not been tampered with, and is still valid. Validation includes:

1. **Checking the Signature**: Upon receiving them from clients, verifying the token’s signature using the shared secret (HMAC) or public key (RSA/ECDSA).
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

### Integrity & Tampering Prevention

Integrity ensures that the token hasn’t been altered. This is done by verifying the signature of the JWT using the same secret (HS256) or a public key (RS256).

**Always verify the token signature before using any of its claims.** Avoid accepting unsigned tokens or those signed with weak or unexpected algorithms.

### Replay Attacks

Token replay occurs when an attacker captures a valid token and reuses it. Mitigations include:

- **Use short-lived tokens**
- **Bind tokens to client context** (e.g., IP, user-agent fingerprint)
- **Track usage** via jti or nonce claims

### Best Practices

#### Use Strong Algorithms
Always use robust signing algorithms like `RS256` (asymmetric) or `HS256` (symmetric). Avoid `none` or outdated algorithms.

Use key rotation strategies to limit the blast radius of a compromised key.

#### Keep Tokens Short-lived
Short-lived tokens reduce risk from token leakage. Aim for access tokens with a TTL under 15 minutes when possible.

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

#### Token Payload Size Considerations
JWTs are transmitted on every request, so size impacts performance. Avoid bloated payloads:

- Strip unused claims
- Use compact claim names
- Externalize large data (e.g., permissions)

#### Use HTTPS
Always transmit JWTs over HTTPS to prevent MITM attacks and token theft.

---

## JWT Flows

JWT flows describe the lifecycle and usage patterns of JSON Web Tokens across authentication and authorization processes in modern web applications. Understanding these flows is critical for designing secure, scalable, and efficient systems that leverage JWTs to manage user identity and access rights.

JWTs are not just static tokens; they move through various stages of generation, transmission, validation, and renewal. Each of these stages is part of a broader flow that dictates how security, performance, and user experience are handled.

### User Authentication Flow

In the user authentication flow, JWTs are primarily used to assert the identity of a user after a successful login attempt. Here’s how the flow typically works:

1. **User Login**: A user submits their credentials (e.g., username and password) to an authentication endpoint.

```
POST /api/login
Content-Type: application/json
Authorization: Basic YWxpY2U6c2VjdXJlcGFzc3dvcmQxMjM=
{}
```

2. **Token Generation**: If the credentials are valid, the server generates a JWT containing claims such as the user ID, issued time (`iat`), and expiration time (`exp`). Optionally, custom claims may be included.

**Example JWT Payload:**
```json
{
  "sub": "1234567890",
  "name": "Alice",
  "role": "admin",
  "iat": 1711274873,
  "exp": 1711278473
}
```

3. **Token Delivery**: The JWT is returned to the client, **typically via a JSON response**. The client stores the token (commonly in localStorage or memory).

```
HTTP/1.1 200 OK
Content-Type: application/json

{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

4. **Token Usage**: For subsequent requests, the client includes the JWT in the `Authorization` header using the `Bearer` schema.

```
GET /api/profile
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

5. **Token Validation**: The server verifies the token's signature and claims. If valid, the request proceeds with the user's identity established.

This flow eliminates the need for server-side authentication-related sessions, enabling stateless authentication.

### Authorization Flows

JWTs also play a central role in defining and enforcing what authenticated users are allowed to do:

#### Role-Based Access Control (RBAC)

1. **Token Enrichment**: When issuing the JWT, the server includes claims such as `role`, `permissions`, or `scopes`.

**Example JWT Payload with Permissions:**
```json
{
  "sub": "1234567890",
  "name": "Alice",
  "role": "admin",
  "permissions": ["read:users", "write:users"],
  "iat": 1711274873,
  "exp": 1711278473
}
```

2. **Policy Enforcement**: Downstream services or middleware inspect these claims to determine whether a user has the necessary rights to perform an action.

3. **Decentralized Enforcement**: Since all required claims are embedded in the token, services can enforce authorization without querying a central authority.

### Token Refresh Flow
Since access tokens (JWTs) are often short-lived, refresh tokens are used to obtain new access tokens without requiring re-authentication.

#### Steps:
- After login, the server issues:
    - Short-lived access token (JWT)
    - Long-lived refresh token (opaque or JWT)
- When the access token expires, the client sends the refresh token to a refresh endpoint.
- Server validates the refresh token and issues a new access token.

```http
POST /token/refresh
Content-Type: application/json

{
  "refresh_token": "<refresh-token>"
}
```

#### Best Practices:
- Store refresh tokens securely (HttpOnly cookies or secure storage).
- Bind refresh tokens to the client/device.
- Revoke refresh tokens on logout or anomaly detection.

### Impersonation Flow
This flow allows an admin or system component to act on behalf of another user. JWTs help encode both the impersonating and impersonated user identities.

#### Steps:
- Admin requests to impersonate user123.
- Backend verifies admin privileges.
- Backend issues a JWT with claims identifying both users.

```json
{
  "sub": "user123",
  "impersonated_by": "admin789",
  "roles": ["user"],
  "scope": "impersonation"
}
```

#### Security Considerations:
- Limit scope and lifetime of impersonation tokens.
- Log all impersonation actions for auditing.
- Use specific claims (`impersonated_by`, `delegated_roles`) to prevent abuse.

### Token Rotation Flow
Token rotation helps mitigate replay attacks and detect token misuse. Every refresh cycle generates a new refresh token, and the previous one is invalidated.

#### Steps:
- User logs in and receives initial access and refresh tokens.
- On token refresh:
    - Server issues new access and refresh tokens.
    - Server stores a token ID or hash for tracking.
    - Old refresh token becomes invalid.

#### Server-side Storage:
- Maintain a whitelist or blacklist of valid refresh token IDs.
- Use JTI (JWT ID) claim to uniquely identify tokens.

```json
{
  "jti": "abc123",
  "sub": "user123",
  "exp": 1711400000
}
```

---

## JWT Storage

Storing JWTs is a crucial part of implementing secure authentication and authorization workflows. Once a token is issued, its storage location significantly impacts both security and application behavior. Below is a deep dive into key considerations and practices for storing JWTs.

### Why JWT Storage Matters on the Client Side
- **Session persistence**: Clients need to persist JWTs across requests to maintain authenticated sessions.
- **Security context**: Improper storage can expose tokens to XSS or CSRF attacks.
- **Token accessibility**: The chosen storage method determines how and when a token can be accessed by client-side scripts or sent automatically with requests.

### When and Where to Store JWTs
- **Authentication tokens** should be stored immediately after a successful login.
- **Authorization tokens** (e.g., access/refresh tokens) may be stored temporarily or with long-lived storage depending on their purpose and lifespan.
- **Browser vs Application Clients**:
  - **Browser-based clients** require careful storage due to exposure to web-based attacks.
  - **Applications** have more control over storage but still need to avoid insecure file systems or memory leaks.

### JWT Storage Options in Browsers

#### **localStorage**
- **Pros**:
  - Easy to use and persists even after the browser tab is closed.
  - Accessible via JavaScript without needing to send it to the server on every request.

- **Cons & Risks**:
  - **Vulnerable to XSS Attacks**: Any malicious script injected through XSS can access JWTs stored in localStorage. This exposes the token to theft and potential misuse by an attacker.
  - **Impact of XSS**: If an attacker steals the JWT, they can impersonate the user and perform unauthorized actions, compromising sensitive data.

#### **sessionStorage**
- **Pros**:
  - Easy to use and persists only during the browser tab session (cleared once the tab is closed).

- **Cons & Risks**:
  - **Vulnerable to XSS Attacks**: Like localStorage, sessionStorage is accessible via JavaScript and can be exploited through XSS vulnerabilities.
  - **Impact of XSS**: An attacker could steal the JWT during the session and use it to impersonate the user, though the risk window is shorter compared to localStorage.

#### **Cookies**
- **Pros**:
  - Automatically sent with every HTTP request, reducing the need for manual token handling.
  - Can be secured with `HttpOnly` and `Secure` flags.

- **Cons & Risks**:
  - **Susceptible to CSRF**: If cookies are not configured with `SameSite`, they can be sent with cross-site requests, allowing attackers to forge actions (CSRF) on behalf of an authenticated user.
    - Example: A malicious website can trigger an unwanted action on a site the user is logged into by leveraging the JWT stored in cookies.
  - **Mitigating CSRF**: Use `SameSite` cookies (`Strict` or `Lax`), or implement anti-CSRF tokens to prevent unauthorized requests.
  - **XSS Risks**: While the `HttpOnly` flag protects cookies from JavaScript access, an attacker may still be able to exfiltrate tokens via server-side vulnerabilities or intercept cookies in transit (without HTTPS).

### Handling JWTs in Different Contexts
- **Client-side handling**:
  - Tokens are used to attach authorization headers to API requests.
  - Must ensure minimal exposure to JavaScript context.
- **Server-side handling**:
  - Tokens may be stored in secure cookies, server sessions, or verified per request without storage.
  - Optional caching for performance, especially for token introspection or validation.

### Secure Storage During Authentication / Authorization
- Store access tokens in short-lived memory if possible.
- Use refresh tokens with longer expiration in `HttpOnly` cookies or encrypted storage.
- Implement token rotation to minimize the window of misuse.

### Best Practices for Secure JWT Storage
- **Use short-lived access tokens** and rotate them frequently.
- **Store refresh tokens in secure, `HttpOnly`, `SameSite=Strict` cookies**.
- **Avoid exposing tokens to JavaScript** unless absolutely necessary.
- **Encrypt sensitive JWTs** if stored in databases or persistent caches.
- **Do not store JWTs in plain text** in local files or unprotected client storage.
- **Invalidate tokens server-side** when no longer needed or after logout.

### Storing JWTs in Backend Systems
- **Database storage** (e.g., for refresh tokens):
  - Encrypt before storing.
  - Use indexed expiration timestamps for cleanup.
- **Session stores** (e.g., Redis):
  - Ideal for stateless authentication fallback or blacklisting.
  - Supports TTL and efficient revocation.
- **Cache layers**:
  - Reduce validation overhead.
  - Use hashed keys and encrypted payloads.

### Advanced Security Techniques
- **Token encryption**:
  - Use JWE (JSON Web Encryption) if sensitive data is in the payload.
- **Obfuscation**:
  - While not a substitute for encryption, it can deter casual inspection.
- **Token fingerprinting**:
  - Bind tokens to user agents or IPs to prevent misuse.
- **Subtle logging**:
  - Never log full JWTs in plaintext; truncate or hash before logging.

---

## JWS, JWK, JWE
In this section, we delve deeper into advanced concepts surrounding JWTs (JSON Web Tokens), focusing on key management, encryption, signing, and validation. These mechanisms enhance the security and flexibility of JWT usage in distributed systems.

### Token Signing with JSON Web Signature (JWS)
What we presented so far is JWS which is the most common method for ensuring data integrity and authenticity of a JWT.

A JWS token consists of:
- Header
- Payload
- Signature

**Example of JWS Header:**
```json
{
  "alg": "RS256",
  "typ": "JWT",
  "kid": "1234"
}
```

**Flow:**
1. Create a base64url-encoded header and payload.
2. Sign the concatenated string `header.payload` with the private key.
3. Append the signature to form the JWT.

**Sample JWT (truncated):**
```
eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIn0.SflKx...xyz
```

### JSON Web Key (JWK) for Key Management

A **JSON Web Key (JWK)** is a JSON-formatted data structure that represents a cryptographic key. It's primarily used to manage public keys for verifying JWTs or encrypting data. When issuing or consuming JWTs, JWKs allow systems to publish, discover, and use keys in a standardized, interoperable way.

JWK provides a standardized way to distribute public keys securely and reliably. By publishing a JWK Set at a known endpoint and referencing keys via `kid` values, systems can validate JWTs without manual key distribution or configuration changes.

In secure systems, the private key is never exposed — it stays with the issuer (authorization server), while public keys are safely distributed via JWKs for signature verification.

#### Why JWKs Matter
JWKs are essential for:
- **Key discovery**: Clients can fetch keys dynamically instead of hardcoding them.
- **Key rotation**: New keys can be added to the JWK Set, and old ones retired.
- **Multi-tenant scenarios**: Multiple keys can be maintained in one JWKS, each with a unique ID.

#### Structure of a Public JWK
Here's a simplified example of a public JWK representing an RSA key:
```json
{
  "kty": "RSA",
  "kid": "1234",
  "use": "sig",
  "alg": "RS256",
  "n": "0vx7...", 
  "e": "AQAB"      
}
```
- `kty`: Key type (e.g., `RSA` or `EC` for elliptic curve)
- `kid`: Key ID – uniquely identifies this key in a set
- `use`: What the key is used for – `sig` (signature) or `enc` (encryption)
- `alg`: Algorithm this key is intended to be used with
- `n`: The base64url-encoded modulus for RSA public key
- `e`: The base64url-encoded public exponent for RSA public key

#### How It Works: End-to-End Example
Let’s walk through the flow of how a JWK is actually used in the context of verifying a JWT:

1. **JWT is received as part of a request**:
  - JWT header contains `alg` and `kid`:
   ```json
   {
     "alg": "RS256",
     "typ": "JWT",
     "kid": "1234"
   }
   ```

2. **Locate the correct public key**:
  - The consumer of the token makes an HTTP GET request to a JWKS endpoint (e.g., `https://example.com/.well-known/jwks.json`).
  - This endpoint returns a **JSON Web Key Set** (JWKS), which is an array of JWKs:
   ```json
   {
     "keys": [
       {
         "kty": "RSA",
         "kid": "1234",
         "use": "sig",
         "alg": "RS256",
         "n": "0vx7...",
         "e": "AQAB"
       },
       {
         "kty": "RSA",
         "kid": "5678",
         "use": "sig",
         "alg": "RS256",
         "n": "1abc...",
         "e": "AQAB"
       }
     ]
   }
   ```

3. **Select the matching key**:
  - Use the `kid` in the JWT header to find the matching key in the JWKS.

4. **Reconstruct the public key**:
  - Libraries use the `n` and `e` values to rebuild the RSA public key in memory.

5. **Verify the client JWT**:
  - The JWT library uses the public key to verify the JWT's signature.

#### Why `kid` Is Important
- Helps identify which key to use when multiple keys exist.
- Enables **key rotation**: You can issue new JWTs with a different `kid` and new key, while still accepting old tokens until they expire.

---

### JWE

JSON Web Encryption (JWE) is a specification that ensures **confidentiality** of JWT payloads by **encrypting the content**. This is in contrast to JSON Web Signature (JWS), which **only signs** the payload for authenticity and integrity but **does not hide** its contents.

In other words:
- With **JWS**, anyone who intercepts the token (after HTTPS termination) can read the payload, though they can’t modify it without breaking the signature.
- With **JWE**, the **payload is encrypted**, so even after HTTPS is unwrapped, the data remains unreadable unless the recipient has the decryption key.

This makes JWE ideal for scenarios where **sensitive information** must be protected, even from intermediaries.

**A JSON Web Encryption (JWE) is only encrypted, not signed.** It is used to encrypt the payload to ensure confidentiality, but it does not inherently provide authenticity or integrity like a signature would.

If you need both confidentiality (encryption) and authenticity (signature), you typically use a combination of JWE and JSON Web Signature (JWS).

#### JWE Token Structure
A JWE consists of 5 base64url-encoded parts, separated by dots:
```
<Protected Header>.<Encrypted Key>.<Initialization Vector>.<Ciphertext>.<Authentication Tag>
```
Each part serves a specific purpose:

- **Protected Header**: Specifies algorithms and key identifiers.
- **Encrypted Key**: The symmetric key (used to encrypt the payload) itself encrypted using the recipient's public key.
- **Initialization Vector (IV)**: Adds randomness to the encryption process.
- **Ciphertext**: The encrypted payload data.
- **Authentication Tag**: Verifies the integrity and authenticity of the encrypted content.

#### Example JWE Header
```json
{
  "alg": "RSA-OAEP",      // Key encryption algorithm
  "enc": "A256GCM",       // Content encryption algorithm
  "kid": "5678"           // Key identifier
}
```

#### 📦 Illustrative JWE Token Example
```
eyJhbGciOiJSU0EtT0FFUCIsImVuYyI6IkEyNTZHQ00iLCJraWQiOiI1Njc4In0
.
Z3Vlc3MtcGFzc3dvcmQ
.
48V1_ALb6US04U3b
.
5eym8TW_c8SuK0ltJ3rpYg
.
XFBoMYUZodetZdvTiFvSkQ
```
