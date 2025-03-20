# Authentication

Authentication is the process of verifying the identity of a user, system, or entity before granting access to a resource. It is a fundamental security mechanism that ensures only authorized users can interact with specific services, applications, or data.

1. **Security:** Prevents unauthorized access to sensitive information.
2. **Trust:** Ensures that users interacting with a system are who they claim to be.
3. **Data Protection:** Helps in safeguarding personal, financial, and organizational data.
4. **Compliance:** Many regulations (GDPR, HIPAA) require strong authentication mechanisms.
5. **Prevention of Identity Theft:** Ensures that malicious actors cannot impersonate legitimate users.

Authentication is essential in various domains, including web applications, cloud computing, banking systems, and enterprise security frameworks.

---

## Authentication vs. Authorization
Though often used interchangeably, authentication and authorization serve different purposes:

**Authentication** is the process of verifying the identity of an entity (user, system, or service). It answers the question, *"Who are you?"* Authentication typically involves the presentation of credentials, such as usernames, passwords, biometrics, or cryptographic tokens, which the system verifies before granting access.

**Authorization** determines the access rights and privileges of an authenticated entity. It answers the question, *"What are you allowed to do?"* Even after authentication, a user may have restricted access based on role-based policies, access control lists, or other permission management techniques.

For example:
- A user logging into an online banking portal undergoes authentication.
- The system then checks the user’s authorization level to determine if they can view their account balance or initiate transactions.

---

## Basic Concepts

### **Identity**
- A unique representation of an entity within a system.
- Can be a username, email address, or a digital certificate.

### **Credentials**
- Data used to prove identity, including passwords, PINs, security keys, or biometric data.
- Strong credentials reduce the risk of impersonation and unauthorized access.

### **Session Management**
- Maintains an authenticated user's state across multiple interactions with a system.
- Techniques include:
  - Session cookies (HTTP cookies storing session identifiers)
  - Token-based authentication (JWT, OAuth tokens)
  - Secure session handling to prevent session hijacking and fixation attacks.

### **Password Hashing and Storage**
- Storing passwords in plain text is a significant security risk.
- Secure systems hash passwords using cryptographic algorithms
- **Salting** is the process of adding random data (a "salt") to the password before hashing it. This makes it much more difficult for attackers to use precomputed hash databases (such as rainbow tables) to crack passwords.
  - The salt is unique for each user and stored alongside the hashed password.
  - Salting ensures that even if two users have the same password, their stored hashes will be different, adding an additional layer of security.

### **Authentication Protocols**
- Standardized frameworks to facilitate secure authentication across applications.
- Examples include:
  - OAuth 2.0 (for authorization)
  - OpenID Connect (OIDC) (for authentication)
  - SAML (Security Assertion Markup Language) for enterprise authentication
  - Kerberos for network authentication

### **Base64**
- **Base64 encoding** is a binary-to-text encoding scheme that represents binary data in an ASCII string format by translating it into a radix-64 representation.
- It is commonly used to encode binary data, such as images or files, into text so that it can be transmitted over text-based protocols like email or HTTP.

**Example 1:**
- **Encoding**:  
  Let's encode the string "hello" into Base64.
  1. First, the ASCII values of each character in "hello" are obtained:
    - `h = 104`, `e = 101`, `l = 108`, `l = 108`, `o = 111`
    - These values are based on the **ASCII table**:
      ```
      Character  | ASCII Value
      ------------------------
      h          | 104
      e          | 101
      l          | 108
      o          | 111
      ```

  2. The string is then converted to binary using the ASCII values:
    - `h = 01101000`, `e = 01100101`, `l = 01101100`, `l = 01101100`, `o = 01101111`

  3. The binary data is grouped into 6-bit chunks:
    - `011010 000110 010101 101100 011011 000110 111100`

  4. Each 6-bit group is mapped to its corresponding Base64 character using a predefined **Base64 index table**:
     ```
     Index  | Base64 Character
     -------------------------
     0      | A
     1      | B
     2      | C
     3      | D
     ...
     25     | Z
     26     | a
     ...
     52     | 0
     53     | 1
     ...
     63     | /
     ```

  5. The Base64-encoded output is `aGVsbG8=`.
    - Notice the `=` character at the end, which is used as **padding**. Padding is added when the input data isn't a multiple of 3 bytes (24 bits), ensuring the final Base64 string has a length that's a multiple of 4 characters.

- **Decoding**:  
  To decode `aGVsbG8=` back to the original string "hello":
  1. The Base64 string `aGVsbG8=` is split into 6-bit chunks:
    - `a = 26`, `G = 6`, `V = 21`, `s = 44`, `b = 27`, `G = 6`, `8 = 60`

  2. These numbers are converted back into their original 8-bit binary format:
    - `a = 011010`, `G = 000110`, `V = 010101`, `s = 101100`, `b = 011011`, `G = 000110`, `8 = 111100`

  3. The binary groups are concatenated and decoded back to their ASCII characters:
    - The final decoded output is "hello".

**Example 2:**
- **Encoding**:  
  Let's encode some binary data into Base64.

  1. First, the binary data we want to encode is as follows:
     ```
     10110011 01100001 11011010
     ```
     These are three bytes (24 bits) of raw binary data, which might represent part of a file, like a PDF or an image.

  2. The binary data is grouped into 6-bit chunks:
     ```
     101100 110110 000111 011010
     ```

  3. Each 6-bit group is mapped to its corresponding Base64 character using a predefined **Base64 index table**:
     ```
     Index  | Base64 Character
     -------------------------
     0      | A
     1      | B
     2      | C
     3      | D
     ...
     25     | Z
     26     | a
     ...
     52     | 0
     53     | 1
     ...
     63     | /
     ```
     Using the above table, we map the 6-bit groups:
    - `101100` → `44` (decimal) → Base64 Character: `s`
    - `110110` → `54` (decimal) → Base64 Character: `2`
    - `000111` → `7` (decimal) → Base64 Character: `H`
    - `011010` → `26` (decimal) → Base64 Character: `a`

  4. The Base64-encoded output is `s2Ha`.
    - Notice that **no padding** is needed here since the binary data is exactly 24 bits (3 bytes), which is divisible by 3. Padding (`=`) is only used when the data is not a multiple of 3 bytes.

- **Decoding**:  
  To decode `s2Ha` back to the original binary data:

  1. The Base64 string `s2Ha` is split into 6-bit groups:
    - `s` → `44` → `101100`
    - `2` → `54` → `110110`
    - `H` → `7` → `000111`
    - `a` → `26` → `011010`

  2. These binary values are concatenated back into the original 24-bit binary data:
     ```
     10110011 01100001 11011010
     ```

  3. The decoded output is the original binary data, which corresponds to the same raw data that was initially encoded.

**Special Characters in Base64 Encoding**:
- Base64 encoding may introduce special characters like `/`, `+`, and `=` in the final encoded string:
  - `/` and `+` are part of the Base64 index table.
  - `=` is a padding character used to ensure the output is a multiple of 4 characters in length.
- For example, when encoding the string "hello", the final Base64 output is `aGVsbG8=`, where `=` is the padding character.

**Data Expansion**:
- Base64 encoding **expands the size** of the original data. For every 3 bytes of input, Base64 encodes it into 4 characters, leading to about a **33% increase** in data size.
  - For example, the ASCII string "hello" (5 characters) becomes a 24-bit value, which results in a 32-bit Base64 string with 4 characters. The `=` padding adds one more character, making it 8 characters in total.

**Main Applications:**
- **Data Encoding**: Used to encode binary data (like files or images) for safe transmission over text-based protocols (e.g., email, HTTP).
- **Authentication**: Base64 is used to encode credentials (like username and password) in HTTP Basic Authentication, often in the form of an `Authorization` header. Standard Base64 is compatible with HTTP headers, making it a good choice for transmitting authentication information.
- **Data Storage**: Base64 is used in various data storage scenarios, where binary data needs to be encoded into a text format (e.g., embedding binary data into JSON or XML).
- **Token Encoding**: While **JWTs** (JSON Web Tokens) are typically encoded in **Base64 URL-safe**, other token formats may use standard Base64 encoding for secure transmission across text-based protocols.

### **Base64 for URLs**
- It is a variant of the standard Base64 encoding scheme designed to be URL-safe. It modifies the Base64 character set to avoid characters that may have special meanings in URLs or might be URL-encoded.
- The two characters `/` and `+`, which are part of the standard Base64 alphabet, are replaced by `-` and `_`, respectively, to make the encoding URL-safe.
- Base64 for URLs is used in scenarios like embedding data within URLs or encoding tokens (like JWTs) that are often transmitted over HTTP in a URL-friendly manner.

**Example:**
- **Encoding**:  
  Let's encode some binary data using Base64 for URLs.

  1. First, the binary data we want to encode is as follows:
     ```
     10110011 01100001 11011010
     ```
     These are three bytes (24 bits) of raw binary data, similar to the binary data in Example 2.

  2. The binary data is grouped into 6-bit chunks:
     ```
     101100 110110 000111 011010
     ```

  3. Each 6-bit group is mapped to its corresponding Base64 character using a modified **Base64 URL-safe index table**:
     ```
     Index  | Base64 URL-Safe Character
     -------------------------
     0      | A
     1      | B
     2      | C
     3      | D
     ...
     25     | Z
     26     | a
     ...
     52     | 0
     53     | 1
     ...
     62     | -
     63     | _
     ```

     Using the above table, we map the 6-bit groups:
    - `101100` → `44` (decimal) → Base64 URL-Safe Character: `s`
    - `110110` → `54` (decimal) → Base64 URL-Safe Character: `2`
    - `000111` → `7` (decimal) → Base64 URL-Safe Character: `H`
    - `011010` → `26` (decimal) → Base64 URL-Safe Character: `a`

  4. The Base64 URL-safe encoded output is `s2Ha`.
    - Note that **no padding** (`=`) is used here since the binary data is a multiple of 3 bytes (24 bits), just like in the previous example.

- **Decoding**:  
  To decode `s2Ha` back to the original binary data:

  1. The Base64 URL-safe string `s2Ha` is split into 6-bit groups:
    - `s` → `44` → `101100`
    - `2` → `54` → `110110`
    - `H` → `7` → `000111`
    - `a` → `26` → `011010`

  2. These binary values are concatenated back into the original 24-bit binary data:
     ```
     10110011 01100001 11011010
     ```

  3. The decoded output is the original binary data.

**Special Characters in Base64 for URLs**:
- Base64 for URLs replaces the `/` and `+` characters with `-` and `_`, respectively, to make the encoding URL-safe. This modification prevents issues with special characters that could interfere with URL parsing.
  - For example, `/` is often used in URL paths and `+` has a special meaning in query strings (it represents spaces).
  - The Base64 URL-safe character set:
    ```
    - `/` → `-`
    - `+` → `_`
    ```

**Data Expansion**:
- Like standard Base64 encoding, **Base64 for URLs** also causes data expansion. The encoded output is approximately **33% larger** than the original data.
  - For every 3 bytes of input, Base64 URL-safe encoding results in 4 characters of output.
  - Padding (`=`) is not always included, but if needed (when the input isn't a multiple of 3 bytes), it is typically omitted or replaced with an empty string in URLs.

**Main Applications:**
- **URL Safe Encoding**: Base64 URL-safe encoding is used to encode binary data in URLs, ensuring special characters like `/`, `+`, and `=` are replaced with URL-safe characters (`-` and `_`). This is important for embedding images, files, or data in query parameters.

- **Token Encoding**: Base64 URL-safe encoding is commonly used for encoding tokens like **JWTs** (JSON Web Tokens) for web authentication and session management, ensuring compatibility with URL transmission. However, **sending JWTs in URLs** is generally **not recommended** due to security risks:

  - **Data Visibility during HTTPS Transmission**:
    - **Cleartext Data**: The **domain name** (e.g., `example.com`) and **IP address** are visible during the DNS lookup but encrypted after the SSL/TLS connection is established. All data (including URLs, headers, and body) is encrypted during transmission.

    - **Encrypted Communication**: Routers only see **encrypted packets** during HTTPS transmission, preventing them from accessing the contents (e.g., JWTs). However, sending JWTs in URLs should be avoided due to risks like exposure in browser history, server logs, and referrer headers. It's safer to send JWTs in **HTTP headers**.

  These issues are relevant even though JWTs will not be exposed on the network during HTTPS transmission. The primary concern is **exposure in browser history, logs, and referrers**, making it safer to use **HTTP headers** (`Authorization: Bearer <JWT>`) instead of including JWTs in URLs.

- **Data Transmission**: Used for transmitting data securely over HTTP in URL-safe formats, ensuring compatibility and making it easy to parse by web browsers and servers.

---

## Classification by Type of Factor

Authentication can be classified based on the **type of factor** used. This classification is **not based on the number of factors** (e.g., single-factor or multi-factor authentication), but rather on the type of credential involved.

### **Something You Know**
Authentication methods relying on information that only the user should know:

- **Passwords and PINs**: The most common forms of authentication based on knowledge.

#### Password Policies
- **Length**: Typically, a minimum of 12–16 characters.
- **Complexity**: A mix of uppercase, lowercase, numbers, and special characters.
- **Expiration**: Passwords should be changed periodically (but not too frequently, unless compromised).
- **Account Lockout**: To prevent brute-force attacks, accounts lock after a set number of failed login attempts.

#### Password Cracking Protection
- **Hashing** and **salting** passwords before storage ensures they aren't stored as plaintext, preventing rainbow table attacks.

---

### **Something You Have**
Authentication methods requiring a **physical item** the user possesses to authenticate:

- **Smartcards**: Cryptographic storage devices that require a PIN.
- **Hardware Tokens**: Devices that generate OTPs without internet access.
- **Mobile Devices**: Can be used to receive OTPs via SMS, authenticator apps, or push notifications.

#### One-Time Passwords (OTP)
- Temporary, single-use passwords to mitigate replay attacks, such as:
  - **Time-Based OTP (TOTP)**: Generated using a secret key and timestamp.
  - **Counter-Based OTP (HOTP)**: Incremented after each use.

#### Token-Based Authentication
- **JWTs**, OAuth 2.0 tokens, and other cryptographic tokens are used for securing sessions in web applications or APIs. These fall under **"Something You Have"** because tokens are stored or managed on the user's device or through a session.

#### Passwordless Authentication
- Passwordless methods eliminate traditional passwords and rely on physical items such as mobile devices or hardware security keys for authentication via OTPs, magic links, or public-key cryptography (WebAuthn, FIDO2).

---

### **Something You Are (Biometrics)**
Authentication methods using **unique physical or behavioral traits** to verify identity:

- **Fingerprint Recognition**: Common in mobile devices and secure systems.
- **Facial Recognition**: Uses unique facial features (e.g., distance between eyes, nose shape) to verify identity.
  - Techniques include **2D image processing** and **3D mapping**.
- **Retina Scanning**: Maps the unique pattern of blood vessels in the retina.
- **Behavioral Biometrics**: Analyzes user behavior such as:
  - **Keystroke dynamics**
  - **Mouse movement patterns**
  - **Gait recognition**

---

## Classification by Number of Factors

Authentication systems can be classified based on the **number of factors** used, providing different levels of security:

### **Single-Factor Authentication (SFA)**
- The simplest form of authentication, requiring only **one credential** (e.g., a password or PIN).
- Vulnerable to brute-force attacks, phishing, and credential theft.

### **Two-Factor Authentication (2FA)**
- Enhances security by requiring **two independent authentication factors**, typically:
  - **Something you know** (password, PIN)
  - **Something you have** (OTP, security token, mobile app confirmation)
- Commonly used in online banking, social media, and enterprise security solutions.

### **Multi-Factor Authentication (MFA)**
- Extends beyond 2FA by incorporating **three or more factors**, including:
  - **Something you are** (biometrics, behavior-based authentication)
  - **Something you have** (smartcards, hardware tokens)
  - **Something you know** (passwords, PINs)
- Provides significantly stronger protection against sophisticated cyber threats.

---

# Authentication Protocols

Authentication protocols are essential mechanisms for verifying identities within a networked environment.

These protocols combine various authentication concepts—such as the **type of factor** (e.g., something you know, have, or are) and the **number of factors** (e.g., single, two, or multi-factor authentication) to provide tailored solutions based on security needs, user experience, and interoperability requirements. By leveraging these concepts, protocols ensure that only authorized entities can access protected resources, balancing security and usability.

## HTTP Basic Authentication

- A simple mechanism for authenticating users over HTTP.
- Sends the **username** and **password** encoded in **Base64** (not encrypted) in the `Authorization` header.
  - **Base64** encoding is used to safely encode the username and password into a format that can be transmitted over HTTP.
  - **Base64** ensures the credentials are transmitted as a valid ASCII string. While Base64 encoding may include characters like `+`, `/`, and `=`, these are compatible with HTTP headers and do not cause issues in this context.
  - In URLs or other applications, these special characters could cause problems because of their specific meanings (e.g., `+` represents space, `/` is a path separator, and `=` is used for padding or query parameters).
  - **Base64** encoding is suitable for Basic Authentication because HTTP headers can safely handle special characters like `+`, `/`, and `=`, without interfering with the header structure.
  - However, Base64 encoding is **not encryption**, rather it is simply a way to encode data to be transmitted over text-based protocols. It makes the data suitable for HTTP transmission but does not protect the data from being intercepted or decoded.
  - Since Base64 encoding is reversible, anyone who intercepts the Base64-encoded credentials can easily decode them, making Basic Authentication **insecure** unless used with HTTPS.
  - To avoid issues when transmitting data in URLs or other contexts, **Base64URL encoding** is often preferred. This encoding replaces `+`, `/`, and `=` with URL-safe alternatives (`-`, `_`, no padding), making it suitable for use in URLs and headers, such as for **JWTs**.

- **Headers are not encrypted by default**: HTTP headers are part of the HTTP request and response, but they are **transmitted securely** if **HTTPS** is used, because HTTPS (SSL/TLS) encrypts the entire communication channel between the client and server.

- **HTTPS Encryption**:
  - While HTTP headers themselves aren't individually "encrypted," the entire HTTP message (including headers, cookies, and body) is encrypted when using HTTPS. This encryption ensures that an attacker cannot intercept or tamper with the headers during transmission over the network.
  - When HTTPS is used, even though the headers are visible to both the client and server, they are **protected by the encrypted tunnel**, making it much harder for attackers to eavesdrop or manipulate the data.
  - In **HTTPS**, what is exposed during routing:
    - **Visible to routers**:
      - **Domain name** (e.g., `example.com`) and **IP address** during DNS lookup.
    - **Not visible to routers**:
      - **HTTP headers**, including the `Authorization` header containing the Base64-encoded credentials, **are encrypted** within the HTTPS tunnel.
      - **Body content** and other sensitive data are also encrypted.
      - **URLs** (e.g., query parameters) are encrypted as part of the full HTTPS transmission.

- **Risks of Base64 Encoding**:
  - **Base64 is not secure** as it only encodes the data, meaning anyone with access to the encoded string can decode it back to plain text.
  - Even when HTTPS is used to protect the transmission, **Base64-encoded credentials are still vulnerable** to exposure in insecure situations, such as:
    - **Man-in-the-middle attacks** if HTTPS is not used.
    - **Exposure in server logs** if the `Authorization` header is logged or stored insecurely.
    - **Browser history**: If the credentials are stored in URLs (e.g., as query parameters), they can be exposed through browser history or the referer header.
    - **Referrer headers**: Credentials could be inadvertently passed along to other sites if redirected in a way that includes the `Authorization` header.

- **Example request**:
  ```http
  GET /protected-resource HTTP/1.1
  Host: example.com
  Authorization: Basic dXNlcjpwYXNzd29yZA==
  ```

- **Base64-encoded credentials**:
  - The string `dXNlcjpwYXNzd29yZA==` is a Base64-encoded version of the plaintext string `user:password`.
  - **Base64 encoding** converts binary data (in this case, the username and password) into a text format so that it can be safely transmitted over HTTP headers.
  - This encoded string can be easily decoded back to its original form (`user:password`), which is why Base64 encoding is **not a secure method** for protecting credentials.

- **Insecure without HTTPS**:
  - HTTP Basic Authentication is very insecure on its own because the credentials are easily decoded if intercepted. The **Base64-encoded credentials** can be decoded back to plain text (`user:password`), revealing the sensitive information.
  - It should **always be used with HTTPS** to ensure that the transmission is secure, protecting the credentials during transit by encrypting the entire HTTP message.

## HTTP Digest Authentication

- **Overview**:
  - HTTP Digest Authentication is a more secure alternative to Basic Authentication. It enhances security by using a hashing mechanism and a challenge-response process to authenticate users, reducing the risk of credential theft.

- **How It Works**:
  1. **Server sends a challenge**: When a client requests a protected resource, the server sends a `WWW-Authenticate` header with a `nonce` (a unique value) to the client. The nonce is a one-time random value used in the hashing process to ensure that the request is fresh and prevents replay attacks.
  2. **Client sends hashed credentials**: The client hashes the combination of the username, password, HTTP method, requested resource, and the nonce. This hash is then sent in the `Authorization` header as part of the HTTP request.
  3. **Server verifies the hash**: The server performs the same hashing process with the stored credentials (username and password) and the nonce. If the hashes match, the user is authenticated. Otherwise, the request is denied.

- **Key Concepts**:
  - **Nonce**: Short for *"number used once"*, a nonce is a unique value generated by the server. It is sent to the client as part of the authentication challenge. The nonce prevents replay attacks by ensuring that each authentication request is unique.
  - **Hashing**: Instead of sending the password in plain text, the credentials are hashed using an algorithm (typically MD5) in combination with the nonce, HTTP method, and requested URI. This means that the password is never sent over the network in plaintext, reducing the risk of it being intercepted.

- **Example Process**:
  1. Client sends a request for a protected resource (e.g., `/protected-resource`).
  2. Server responds with a `401 Unauthorized` status and a `WWW-Authenticate` header containing a `nonce`, like so:
     ```
     WWW-Authenticate: Digest realm="Example", qop="auth", nonce="dcd98b7102dd2f0e8b2e3a7f4b3d7f2f2d8c3ad5f3b8c1b4", opaque="5ccc069c403ebaf9f0171e9517f40e41"
     ```
  3. The client hashes the credentials (e.g., `username:password`), the HTTP method (e.g., `GET`), and the URI (e.g., `/protected-resource`) along with the nonce provided by the server. The client then sends an `Authorization` header with the hashed result:
     ```
     Authorization: Digest username="user", realm="Example", nonce="dcd98b7102dd2f0e8b2e3a7f4b3d7f2f2d8c3ad5f3b8c1b4", uri="/protected-resource", response="5ccc069c403ebaf9f0171e9517f40e41", opaque="5ccc069c403ebaf9f0171e9517f40e41"
     ```
  4. The server takes the same input data (username, password, HTTP method, requested URI, and nonce) and hashes it. If the server's computed hash matches the `response` value in the client’s `Authorization` header, authentication succeeds, and access to the resource is granted.

- **Advantages over Basic Authentication**:
  - **More Secure**: HTTP Digest Authentication is less vulnerable to credential theft than Basic Authentication because the password is never sent in plaintext. Instead, a hashed value is transmitted, making it much harder for attackers to steal the password even if they intercept the communication.
  - **Nonce Usage**: The use of nonces ensures that each authentication request is unique, preventing replay attacks (where an attacker intercepts and replays a valid request to gain unauthorized access).
  - **Challenge-Response Mechanism**: Digest Authentication uses a challenge-response model, which adds a layer of security by ensuring that both the client and server are involved in the authentication process.

- **Risks**:
  - **Still susceptible to man-in-the-middle attacks** if used without HTTPS: While Digest Authentication is more secure than Basic Authentication, it still sends data over the network. Without HTTPS, an attacker could intercept the request and perform a man-in-the-middle attack, capturing the nonce and response data.
  - **Digest Algorithm Weakness**: The MD5 hashing algorithm used in Digest Authentication is considered weak by modern cryptographic standards. Although it is still more secure than Basic Authentication, it may not provide sufficient protection against determined attackers.

- **Example request using Digest Authentication**:
  ```http
  GET /protected-resource HTTP/1.1
  Host: example.com
  Authorization: Digest username="user", realm="Example", nonce="dcd98b7102dd2f0e8b2e3a7f4b3d7f2f2d8c3ad5f3b8c1b4", uri="/protected-resource", response="5ccc069c403ebaf9f0171e9517f40e41", opaque="5ccc069c403ebaf9f0171e9517f40e41"
  ```
  
- **Encryption Requirement**:  
  Just like Basic Authentication, Digest Authentication should always be used over HTTPS to guarantee that the entire communication, including the hashed credentials, is encrypted during transmission.

  Without HTTPS, Digest Authentication is susceptible to interception, leaving it vulnerable to attacks if attackers can access the network, thus compromising the security and integrity of the authentication process.

## Single Sign-On (SSO)
SSO allows users to authenticate once and gain access to multiple services without re-authenticating.

- Eliminates the need for multiple passwords.
- Typically implemented using:
  - OAuth 2.0
  - OpenID Connect
  - SAML

## OAuth 2.0
- A framework for delegated authorization.
- Roles:
  - **Resource Owner** (User)
  - **Client** (Application requesting access)
  - **Authorization Server** (Issues tokens)
  - **Resource Server** (Validates tokens)
- Authorization flow:
  1. Client requests authorization from Resource Owner.
  2. Resource Owner grants authorization.
  3. Client receives **Access Token** from Authorization Server.
  4. Client uses Access Token to request resources.

## SAML (Security Assertion Markup Language)
- XML-based protocol for authentication and authorization.
- Common in enterprise SSO scenarios.
- Components:
  - **Identity Provider (IdP)**: Authenticates users.
  - **Service Provider (SP)**: Relies on IdP for authentication.
- Authentication flow:
  1. User attempts to access a service.
  2. SP redirects user to IdP.
  3. IdP authenticates user and returns SAML Assertion.
  4. SP grants access based on the assertion.

---

# Authentication Tokens

In this section, we explore **token-based authentication**, a widely used method in modern authentication protocols. Token-based authentication enhances security by replacing traditional username/password authentication, leveraging the **"Something You Have"** factor.

Before diving into authentication tokens, it's important to clarify what **session data required for authentication** refers to. This session data typically includes the information needed to authenticate a user and manage their access across multiple requests, such as:

- **User identity** (e.g., user ID, username).
- **User roles and permissions** (e.g., `admin`, `user`).
- **Authentication claims** (e.g., email, user-specific attributes).
- **Expiration time** (e.g., time when the authentication token or session becomes invalid).

Now, let's introduce two types of authentication tokens:

- **JWTs (JSON Web Tokens)**: JWTs are **self-contained** tokens that carry **authentication-related session data** directly within the token itself. Since they are **stateless**, JWTs do not require the server to store session data. The token includes everything needed for authentication, such as user identity, roles, permissions, and expiration time. JWTs are typically stored on the user's device (e.g., in `localStorage` or `sessionStorage`), making them a form of **"Something You Have"**.

- **Session Cookies**: Session cookies store a **session ID** on the user's device, which is automatically sent with each HTTP request. The session ID serves as a reference to the **authentication-related session data** stored on the server, such as the user’s identity, roles, and permissions. The server uses this session ID to fetch the corresponding session data, meaning session management is centralized on the server. Like JWTs, session cookies are also a form of **"Something You Have"**, but unlike JWTs, they rely on the server to manage and store authentication session data.

## JSON Web Tokens (JWT)

- **JWTs** are self-contained tokens used for authentication and authorization. They consist of three parts:
  - **Header**: Typically contains the type of the token (JWT) and the signing algorithm (e.g., HMAC, RSA, or ECC). This section tells the recipient how to verify the authenticity of the token.
  - **Payload**: Contains the claims, which are the statements about an entity (typically, the user) and additional data. Claims can include information like the user’s ID, roles, or permissions, and are not encrypted, so they can be decoded by anyone with access to the token.
  - **Signature**: Created by signing the encoded Header and Payload with a secret key using the specified algorithm (e.g., HMAC, RSA, or ECC). The signature ensures that the token has not been tampered with.

  - Example JWT:
    ```json
    {
      "alg": "HS256",
      "typ": "JWT"
    }.
    {
      "sub": "1234567890",
      "name": "John Doe",
      "iat": 1516239022
    }.
    signature
    ```

- **Token-Based Authentication**:
  - **JWTs**, like other token-based authentication methods, eliminate the need for traditional username and password authentication. Once the user logs in, a JWT is issued, and subsequent requests to protected resources use the JWT to verify the user's identity without requiring a password on each request.

- **Base64URL Encoding**:
  - The **Header** and **Payload** of a JWT are **Base64URL encoded**. This encoding is specifically used because JWTs are often passed in **URLs** and must be safely transmitted in HTTP requests and URLs. Unlike regular **Base64**, **Base64URL** replaces characters like `+`, `/`, and `=` with URL-safe alternatives (`-`, `_`), making the JWT safe to include in URLs and HTTP headers.
  - **Base64 encoding** is not encryption; it simply converts binary data into a string format for safe transmission over text-based protocols like HTTP. While Base64 encoding ensures the data is compatible with URLs and headers, it does not provide any security on its own. To ensure the authenticity of the data, the JWT is signed with a secret key.

- **JWT in URL**:
  - Since JWTs are often used in **URLs**, **Base64URL encoding** is employed to make the JWT URL-safe, ensuring it can be included in URLs without interfering with URL structures. While the JWT is encrypted during transmission over **HTTPS**, passing JWTs in URLs poses additional risks, such as exposure in browser histories, referrer headers, or server logs.

- **JWT Theft Risk**:
  - If a JWT is stolen (e.g., intercepted by an attacker), it can be used to impersonate the legitimate user. Since JWTs are used to maintain sessions, their theft allows unauthorized access to resources until the token expires or is revoked.

- **JWT vs Protocol**:
  - **JWT** is a **data format** that defines how authentication information should be structured, signed, and verified. It does not specify how data should be transmitted or how servers should respond to requests.
  - **Protocols** like **OAuth 2.0** and **HTTPS** define how data is exchanged between systems, and **JWT** is used as part of the payload to represent authentication or session-related data.

- **Encryption in HTTPS**:
  - In an **HTTPS** request, the entire communication, including the JWT in the `Authorization` header, is encrypted during transmission. However, if a JWT is passed in the URL, the URL itself may be logged or exposed in browser history, posing additional risks.

## Cookies

### What is a Cookie?

A **cookie** is a small piece of data that the server sends to the client’s browser. The browser stores this data and sends it back with subsequent requests to the same server. Cookies are commonly used for maintaining stateful information like user preferences, session data, or tracking user behavior across requests.

Cookies can be classified into two main types based on their lifespan and behavior upon browser closure: **persistent cookies** and **non-persistent cookies** (also called **session cookies**). The primary distinction between them is whether they are stored after the browser is closed.

A **persistent cookie** is set with an **Expires** or **Max-Age** attribute, which tells the browser when the cookie should expire. The cookie remains on the user's device until that date, even if the browser is closed and reopened. If neither attribute is specified, the cookie is treated as a session cookie.

Persistent cookies are stored on the user's device for a specific period, even after the browser is closed. They remain on the client’s device until their expiration date is reached, which can range from days to years.

#### Use Case: Tracking User Behavior and Profiling

Cookies are widely used for user behavior tracking and profiling, especially for personalized advertising. For instance, cookies can store a user’s interactions with a website and later use that information to display targeted ads.

1. **user_id**: A unique identifier for a user.
2. **user_preferences**: Data on the user’s preferences, like product categories or recent activity.

When a user visits a website, the server might set cookies like:

```http
Set-Cookie: user_id=xyz123; Max-Age=31536000; Path=/; Secure; SameSite=Lax;
Set-Cookie: user_preferences=category=electronics&last_visited=tv; Max-Age=31536000; Path=/; Secure; SameSite=Lax;
```

These cookies track the user's activity and allow the server to serve personalized content. For example, an advertisement company can use these cookies to display ads for electronics (based on the user’s interest in TVs).

The cookies store key-value pairs that provide insight into the user’s behavior and interests. This information allows the website to create a tailored experience for the user. For instance:

- **user_id** is a unique identifier for the user.
- **user_preferences** tracks user interest in electronics, such as TVs.

These cookies help the website provide personalized experiences and serve ads based on the user's interests. For example, if the user has shown interest in TVs, the website may display targeted ads for electronics in future visits.

When the user revisits the website or makes another request, the browser automatically sends the stored cookies with the HTTP request. The browser follows specific rules to determine whether to include cookies:

1. **Domain**: The cookie is sent only to the domain that set it. Cookies are tied to specific domains and will not be sent to other domains.
2. **Path**: The cookie is sent only for requests to paths within the specified scope. For example, a cookie set for `/shop` will not be sent with requests to `/blog`.
3. **Secure Flag**: The cookie is only sent over HTTPS connections if marked as `Secure`.
4. **SameSite Policy**: The `SameSite` attribute of a cookie helps to control when the cookie is sent with cross-site requests. Cross-site requests are requests that are made from one domain (website) to another domain. This is common in scenarios where a webpage from one website makes a request to another website, for example, when loading resources like images, scripts, or making API calls.

  - **Cross-site request example**: Imagine you're logged into a banking website (`bank.com`) and visit a blog (`blog.com`). While on the blog site, an embedded advertisement or an iframe might make a request to the bank's website to load certain resources or even submit forms. This is a cross-site request because the request originates from a domain (e.g., `blog.com`) that is different from the one the user is authenticated on (e.g., `bank.com`).

   The `SameSite` cookie attribute allows website owners to specify the conditions under which cookies should be sent along with these types of cross-site requests. It has three main values:

  - **`SameSite=Strict`**: With this setting, the cookie is **not sent** with any cross-site requests, even if the request originates from the same user who is logged in. This means that the cookie will only be sent in **first-party** contexts (requests initiated by the same domain). For example, if you're on `bank.com` and make a request to `bank.com`, the cookie will be sent, but if you go to `blog.com` and it tries to make a request to `bank.com`, the cookie will not be included. This is the most restrictive setting and is useful to prevent cross-site request forgery (CSRF) attacks.

  - **`SameSite=Lax`**: With this setting, the cookie will be sent with **top-level navigations** to the origin site (such as when the user clicks a link to `bank.com` from another site), but **not** with other cross-site requests like those initiated by embedded resources or AJAX requests. It's a more lenient policy than `Strict`, as it allows cookies to be sent in some cross-site requests but still limits automatic sending of cookies in cases like embedded ads or iframes.

  - **`SameSite=None`**: This setting allows the cookie to be sent with **all** cross-site requests, regardless of whether the request is initiated by a first-party or third-party site. If you use `SameSite=None`, it’s important to also mark the cookie with the **`Secure`** flag, ensuring the cookie is only sent over HTTPS connections. This setting is typically used for cross-site functionality like embedded widgets or third-party login systems.

For example, the browser will send:

```http
Cookie: user_id=xyz123; user_preferences=category=electronics&last_visited=tv;
```

The server uses these cookies to identify the user, retrieve their preferences, and deliver personalized content, such as displaying electronics ads if the user has shown interest in TVs.

### Session Cookies

Session cookies are a specific type of cookie used to maintain a user's authentication state during a session. 

Unlike persistent cookies, session cookies do not stay on the client after the browser is closed. They are deleted once the browser session ends.

This **non-persistent** behavior occurs because session cookies are not assigned an **Expires** or **Max-Age** attribute. As a result, the browser automatically deletes these cookies when the session ends, meaning the data is not retained across sessions. This ensures that session data is not stored beyond the active browsing session, minimizing the risk of sensitive information being exposed or misused after the browser is closed.

They work as follows:

- **Session Creation**: When a user logs in, the server generates a session ID and sends it to the client in the form of a session cookie.
- **Subsequent Requests**: The browser automatically includes the session cookie in subsequent requests, allowing the server to retrieve session data associated with the session ID.

Since session cookies are stored only in the browser's memory, they are deleted when the browser is closed, ensuring that the session does not persist across sessions.

#### Setting Session Cookies with `Set-Cookie`

The server uses the `Set-Cookie` header to send a session cookie to the client. Common attributes used for session cookies include:

- **`HttpOnly`**: Prevents JavaScript from accessing the cookie.
- **`Secure`**: Ensures the cookie is only sent over HTTPS.
- **`SameSite`**: Helps mitigate CSRF by controlling how cookies are sent in cross-origin requests.

**Example**:

```http
Set-Cookie: session_id=abc123; HttpOnly; Secure; SameSite=Strict; Path=/;
```

This session cookie:

- Is sent only over HTTPS connections, ensuring the data is encrypted during transmission.
- Cannot be accessed by JavaScript running on the client side, as it is marked with the `HttpOnly` flag, reducing the risk of theft through XSS attacks.
- Will not be included in cross-origin requests due to the `SameSite=Strict` attribute, helping protect against CSRF attacks.
- Does not have an `Expires` or `Max-Age` attribute, which means it will be automatically deleted when the browser is closed, ensuring that session data does not persist across different browsing sessions.

#### Security Considerations for Session Cookies

##### XSS Attack Flow:

1. An attacker exploits a vulnerability in a website to inject malicious JavaScript.
2. The victim visits the website, and the injected script runs in the browser.
3. If the session cookie is **not** marked as **HttpOnly**, the malicious script can access the session cookie.
4. The attacker sends the stolen session cookie to their server, hijacking the user’s session.

##### CSRF Attack Flow:

1. **User Authentication**: The user logs into a legitimate website (e.g., a bank), and their session cookie is stored in the browser. This cookie is used to authenticate their requests to the website.

2. **Malicious Website Creation**: The attacker creates a malicious website or a page. On this page, the attacker embeds a form or script that sends a request to the target website (the one the user is logged into). For example, the attacker may create a form that submits a request to transfer money or change account settings.

   Here's an example of a hidden form the attacker might use:
   ```html
   <form action="https://bank.com/transfer" method="POST" id="maliciousForm">
     <input type="hidden" name="amount" value="1000">
     <input type="hidden" name="recipient" value="attackerAccount">
   </form>
   <script>
     document.getElementById('maliciousForm').submit();
   </script>
   ```
3. **Victim Visits Malicious Website**: The victim, who is already logged into the target website (e.g., a bank), unknowingly visits the attacker's malicious website. While on this site, the victim's browser automatically triggers the malicious script or form that was embedded by the attacker.

4. **Automatic Request with Session Cookie**: The victim’s browser, which is already authenticated with the target website, **might automatically include the session cookie in the HTTP request under certain conditions**:

  - **If `SameSite=Strict`**: The session cookie will **not** be sent with cross-site requests. This means the victim’s browser will **not** include the session cookie with the request to the malicious website. Therefore, the CSRF attack **will not succeed** because the target website will not receive the session cookie.

  - **If `SameSite=Lax` (default)**: The session cookie **will** be sent with top-level navigations (e.g., clicking on a link) but **not** with other cross-site requests like form submissions or AJAX calls. If the victim visits the malicious site via a top-level navigation (e.g., by clicking a link from the malicious site), the session cookie will be sent, and the CSRF attack **could succeed**.

  - **If `SameSite=None`**: The session cookie **will** be sent with all requests, including cross-site requests. If the victim visits the malicious website, the browser will include the session cookie in the forged request, allowing the CSRF attack **to succeed**. However, `SameSite=None` must be used in conjunction with the `Secure` flag, which requires the cookie to be sent only over HTTPS connections.

5. **Target Website Processes the Request**: The target website receives the forged request along with the victim's session cookie. Since the request includes the valid session cookie, the website assumes the request is legitimate and processes it as if it were sent by the authenticated user. The attacker takes advantage of this trust to perform actions without the victim's consent.

6. **Unauthorized Action Taken**: The target website carries out the action requested by the attacker, such as transferring money or changing account settings. Because the website trusts the session cookie as proof of the victim’s identity, it does not question the legitimacy of the request, and the attacker succeeds in performing unauthorized actions using the victim’s account.

##### Mitigation Strategies:
- **HttpOnly**: This flag prevents JavaScript from accessing the session cookie, protecting it from theft via XSS.
- **Secure**: Ensures the cookie is only transmitted over HTTPS, preventing interception during transmission.
- **SameSite**: Helps mitigate CSRF by controlling how cookies are sent with cross-origin requests.
  - **SameSite=Strict**: Ensures cookies are only sent with same-origin requests, providing the strongest protection against CSRF.
  - **SameSite=Lax**: Allows cookies to be sent with top-level navigation (e.g., clicking a link to your site), but prevents them from being sent with cross-site subrequests.
  - **SameSite=None**: Allows cross-site requests, but it must be combined with the **Secure** flag.
- **Non-persistent nature of session cookies**: Since session cookies are tied to the browser session, they are deleted when the browser is closed. This limits the risk of session hijacking over extended periods, as the session cookie does not persist between sessions.
- **Session expiry**: Without the **Expires** or **Max-Age** attributes, the session cookie is a **session cookie** and will expire when the user closes their browser. This ensures session information is not stored beyond the session duration.
- **Session hijacking**: If session cookies are improperly configured (e.g., not using **Secure**, **HttpOnly**, and **SameSite**), they are vulnerable to attacks like XSS and CSRF.

##### Cookies and HTTPS**
- **Headers are not encrypted**: HTTP headers, including cookies, are sent as part of the HTTP request and response. However, these headers are **transmitted securely** if **HTTPS** is being used, because HTTPS (SSL/TLS) encrypts the entire communication channel between the client and the server.
- **HTTPS Encryption**: While HTTP headers themselves aren't "encrypted" in the sense that each individual header is hidden, the entire HTTP message, including headers, cookies, and body, is encrypted when using HTTPS. This ensures that an attacker can't intercept or tamper with the headers during transmission over the network. Therefore, when using HTTPS, even though the headers are visible to the client and server, **they are protected by the encrypted channel**, making it much harder for attackers to eavesdrop on or manipulate the data.

### JWT vs Session Cookies
Just to remind you, **authentication-related session data** refers to the information stored on the server to manage a user's authentication state across multiple requests. This typically includes:

- **Authentication information** (e.g., user ID, roles, permissions)
- **Session state** (e.g., session expiration time, login status)

Now, let's compare **session cookies** and **JWTs**:

- **Session cookies** are generally considered more secure than **JWTs** because they do not store sensitive authentication-related session data on the client side. Instead, they store a **session ID** that references authentication-related session data stored securely on the server. This reduces the risk of exposing sensitive information if the cookie is intercepted. In contrast, **JWTs** can contain full user authentication-related information (such as user ID, roles, and permissions) within the token itself, which makes them more vulnerable if the token is compromised.

- **Session cookies** can be automatically included with every HTTP request by the browser. This provides a seamless experience for users since they don’t need to manually send authentication tokens in headers. On the other hand, **JWTs** must be manually included in each HTTP request, typically via HTTP headers (e.g., `Authorization: Bearer <token>`), adding some complexity to the request process.

- **Session cookies** rely on **server-side authentication-related session storage**. The **session ID** is stored in the cookie, and the server uses this ID to look up the authentication-related session data associated with it. The actual authentication-related session data—such as user authentication details and session state (e.g., expiration time)—is stored on the server, and the client only stores a reference to it (the session ID). This means that authentication session management is entirely handled by the server, and the client doesn’t need to manage or store any authentication-related session data beyond the session ID.

- **JWTs**, on the other hand, are **stateless** and **self-contained**. The token itself contains all the authentication-related session data needed to authenticate the user, such as user identity, roles, permissions, and expiration time. This is possible thanks to **claims** in the JWT, which hold the necessary user information and session data. Since **JWTs** store this information directly in the token as **claims**, the server does not need to store authentication-related session data. The server only validates the JWT using its signature. This makes JWTs ideal for APIs or distributed systems, where a **stateless** authentication approach is preferred, allowing the server to function without having to track authentication-related session data for each user.

- **Session cookies** are **stateful** and rely on **server-side authentication-related session storage**. The session ID stored in the cookie is used by the server to retrieve the user's authentication-related session data. This centralized management of authentication-related session data is common in traditional web applications. The cookie typically contains only a reference to the session data stored on the server, rather than the actual data itself.

| Feature                                    | JWT                                                                                                                                   | Session Cookies                                                                                                                                     |
|--------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------|
| **Security**                               | JWTs store authentication-related session data directly in the token on the client side, which can be more vulnerable if intercepted. | Session cookies store only a session ID, reducing the exposure of sensitive data by keeping authentication-related session data on the server side. |
| **Automatic Inclusion in Requests**        | Must be manually included in HTTP headers (e.g., `Authorization: Bearer <token>`).                                                    | Can be automatically included with every HTTP request by the browser, providing a seamless experience for the user.                                 |
| **Authentication-related Session Storage** | Stateless and self-contained; the server does not store authentication-related session data, only validates the JWT using its claims. | Stateful; session ID stored in the cookie, and authentication-related session data is managed on the server side.                                   |
| **Session Management**                     | Server does not need to store authentication-related session data beyond validating the JWT's claims.                                 | Authentication-related session management is centralized on the server, where session data is stored and maintained.                                |
| **Use Case**                               | Ideal for APIs or distributed systems where a stateless authentication approach is preferred.                                         | Primarily used in traditional web applications with centralized server-side authentication-related session management.                              |

### Token Storage

#### Browsers
When deciding where to store authentication tokens (including session cookies and JWTs), security should be a top priority. Common client-side storage options like **local storage** and **session storage** are vulnerable to **XSS (Cross-Site Scripting)** attacks, as they can be accessed by JavaScript. **HTTP-only cookies**, however, provide a more secure alternative, as they cannot be accessed by JavaScript and are more resistant to XSS attacks. Nevertheless, additional security measures like the **Secure** and **SameSite** flags must be implemented to protect cookies from cross-site request attacks and ensure that tokens are only transmitted securely over HTTPS.

- **Local Storage**:
  - **Persistent**: Data stored in **local storage** persists even after the browser is closed, allowing users to stay logged in across sessions without needing to re-authenticate.
  - **Vulnerable to XSS Attacks**: **XSS (Cross-Site Scripting)** is a security vulnerability where an attacker injects malicious JavaScript into a webpage. If a website is vulnerable to XSS, malicious scripts can access local storage and steal tokens.
    - **Example of XSS Attack**: An attacker might inject a script that reads the content of local storage (`localStorage.getItem('auth_token')`) and sends it to a malicious server, where the token can be used to impersonate the user.

  - **Why Local Storage is Vulnerable**:
    - Local storage is accessible by JavaScript, meaning that if an attacker can inject malicious JavaScript, they can easily steal tokens stored in local storage.
    - **Solution**: To protect against XSS, avoid storing sensitive data like JWTs or session tokens in local storage. Instead, consider using **HTTP-only cookies**, which are not accessible to JavaScript.

  - **Implementation of Local Storage**:
    - **localStorage** is a simple key-value storage mechanism built into modern web browsers, accessible via JavaScript.
    - Data is stored indefinitely until explicitly removed using JavaScript (`localStorage.removeItem()`) or until the user clears their browser cache.
    - Example: Storing a token in local storage:

      ```javascript
      localStorage.setItem('auth_token', 'your-authentication-token');
      ```

- **Session Storage**:
  - **More Secure**: Data in **session storage** is available only for the duration of the browser session (i.e., until the browser tab is closed). This limits the exposure time if an attacker manages to exploit an XSS vulnerability.
  - **Cleared on Session End**: When the user closes the browser or tab, session storage is automatically cleared, reducing the risk of session hijacking once the session has ended.

  - **Why Session Storage is More Secure**:
    - While session storage is still vulnerable to XSS attacks (because JavaScript can access it), its **short-lived nature** makes it **less risky** than local storage. Since session storage data is cleared when the browser or tab is closed, the window for an attacker to steal tokens is smaller.
    - However, session storage is still not the most secure option for storing tokens, as it remains accessible to JavaScript. For stronger protection, consider using **HTTP-only cookies**.

  - **Implementation of Session Storage**:
    - **sessionStorage** works similarly to **localStorage**, but the data is stored only for the duration of the page session. Once the browser or tab is closed, the data is automatically cleared.
    - Example: Storing a token in session storage:

      ```javascript
      sessionStorage.setItem('auth_token', 'your-authentication-token');
      ```

- **JWT and Session Cookie Storage**:
  - Both **JWTs** and **session cookies** can be stored in **local storage**, **session storage**, or **HTTP-only cookies**.
  - The security risks associated with storing tokens are similar for both JWTs and session cookies:
    - If stored in **local storage** or **session storage**, both **JWTs** and **session cookies** are vulnerable to **XSS** because they can be accessed by JavaScript.
    - If stored in **HTTP-only cookies**, both **JWTs** and **session cookies** are protected from XSS attacks, but they must still be managed carefully to avoid cross-site request attacks.

- **JWT in HTTP-only Cookies**:
  - Storing **JWTs** in **HTTP-only cookies** provides a balance between convenience and security. Since cookies are automatically sent with each HTTP request, JWTs are included seamlessly without the need to manually add them to headers.
  - This also protects JWTs from JavaScript-based theft via XSS attacks. However, it is crucial to secure cookies with the **Secure** and **SameSite** flags to prevent them from being exposed to cross-site request attacks or being sent over insecure channels.

- **JWT in Headers**:
  - **JWTs** are often used in authentication and are commonly sent in **Authorization** headers when making HTTP requests. This is an alternative to storing them in client-side storage like local storage or session storage.
  - The typical way to send a JWT in the HTTP header is by using the **Authorization** header with the `Bearer` scheme:

    ```javascript
    fetch('https://api.example.com/endpoint', {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('auth_token')}`
      }
    });
    ```
  - **JWTs in headers** can provide additional flexibility as they can be sent with every HTTP request automatically without needing to manually retrieve them from storage. However, storing the JWT in client-side storage (e.g., **local storage** or **session storage**) creates potential security risks, especially if the website is vulnerable to XSS attacks.
  - It is also important to consider the **CORS (Cross-Origin Resource Sharing)** policy and **SameSite** cookie settings, as they can impact how JWTs are transmitted in headers or cookies.

- **Session Cookies in HTTP-only Cookies**:
  - **Session Cookies** stored in **HTTP-only cookies** are also protected from XSS attacks, as JavaScript cannot access these cookies.
  - As with JWTs, it is important to use the **Secure** and **SameSite** flags with session cookies to ensure that they are only sent over HTTPS and are protected from cross-site request attacks.
  - Since **session cookies** typically store a reference to session data on the server (rather than containing user data directly, like JWTs), their protection from XSS attacks is important to prevent attackers from hijacking user sessions.

#### Java client

When developing a custom Java client, securely storing authentication tokens (including session cookies and JWTs) is critical. Unlike web browsers, Java clients do not inherently provide built-in storage mechanisms like **local storage** or **session storage**, but they can use alternative storage strategies depending on the type of client (e.g., desktop applications, mobile clients, or server-side clients). The storage options need to be secure, as improper handling can lead to vulnerabilities.

##### **Local File System Storage**
- **Persistent**: Tokens can be stored securely in files on the local file system, allowing them to persist across client sessions.
- **Security Considerations**: If not properly encrypted, tokens stored on the file system could be vulnerable to unauthorized access. To mitigate risks, ensure that tokens are encrypted before being stored, and apply strong access controls to the file system.

##### **In-Memory Storage**
- **Temporary**: Tokens can be stored in memory for the duration of the session, using Java’s internal data structures (e.g., variables, `HashMap`, or `ConcurrentHashMap`).
- **Security Considerations**: While in-memory storage is generally faster and easier to manage, it is still vulnerable if an attacker gains access to the application’s runtime environment. It's essential to clear sensitive data from memory once it is no longer needed, to minimize the exposure of tokens.
