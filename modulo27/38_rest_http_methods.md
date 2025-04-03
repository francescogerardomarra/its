# **HTTP Protocol**

The HTTP protocol (Hypertext Transfer Protocol) is a communication protocol used for transferring information over the Internet. Below are details on the definition, operation, and usage of the HTTP protocol:

### Definition
The HTTP protocol is an application-level protocol that defines rules and formats for communication between clients and servers on a computer network. It is commonly used to retrieve resources such as web pages, images, and multimedia files from web servers.

### Operation
HTTP operates based on a simple client-server architecture. A web client, such as a browser, sends an HTTP request to a specified web server, indicating the type of action requested (e.g. requesting a web page or submitting form data). The web server processes the request and sends an HTTP response to the client, containing the requested resources or information about the status of the requested operation.

### Usage
The HTTP protocol is widely used for transferring web pages and other content over the Internet. It is the foundation of the World Wide Web and allows users to access and view content on websites through web browsers. HTTP is also used in many other applications and web services for transferring structured and unstructured data over the Internet.

### Example
Here is a simple example of an HTTP request sent by a browser to a web server:
```
GET /index.html HTTP/1.1
Host: www.example.com
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.99 Safari/537.36
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/s-exchange;v=b3;q=0.9
```
In this example, the browser sends an HTTP GET request to the server www.example.com to retrieve the file `index.html`. The request includes information such as the destination host and the type of browser used.

---

## HTTP Methods

In HTTP (Hypertext Transfer Protocol), there are several standard methods used to define actions to be performed on a resource. The six main HTTP methods are as follows:

1. **GET**: The GET method is used to request a representation of a specified resource. It is a "safe" method, meaning it should not modify the state of the server.
2. **POST**: The POST method is used to send data to a server to create a new sub-resource. This method often implies a change of state or side effects on the server.
3. **PUT**: The PUT method is used to completely replace an existing resource or create a new resource if it does not exist. It is "idempotent," meaning that applying the same request multiple times produces the same result.
4. **DELETE**: The DELETE method is used to remove a specified resource from the server. Like PUT, it is also idempotent.
5. **PATCH**: The PATCH method is used to apply partial modifications to a resource. This means that instead of replacing the entire resource like the PUT method, the PATCH method is used to apply specific changes only to parts of the resource.

These are the six main HTTP methods used to define actions to be performed on a resource during client-server communication through the HTTP protocol.

---

### GET
The GET method is one of the primary methods used in the HTTP protocol (Hypertext Transfer Protocol). Below are details on the GET method and its usage:

The GET method is used to request data from a resource specified by a URI (Uniform Resource Identifier) on the server. This method is primarily used to retrieve data, such as web pages, images, or files, without modifying the state of the server or the data.

An HTTP GET request consists of a request line followed by optional headers. It does not have a message body, as request parameters are transmitted directly in the URI.

```
GET /path/to/resource HTTP/1.1
Host: example.com
```

In the following example:

```
GET /index.html HTTP/1.1
Host: www.example.com
```

the client requests the `index.html` resource from the server `www.example.com` using the GET method.

The GET method is considered "idempotent," meaning that repeating the same GET request multiple times produces the same results. Additionally, request parameters are visible in the URI, making GET requests easier to store and share.

The GET method is commonly used to request web pages, style files, images, and other static content on the Internet. It is the default method used by web browsers when a user types a URL in the address bar.

The GET method is a fundamental element of the HTTP protocol and plays a crucial role in retrieving content from web servers on the Internet.

---

### POST
The POST method in HTTP is used to send data to a web server. A common example of using the POST method is when filling out a form on a web page and submitting the entered data to the server for processing.

For example, suppose we have a registration form on a website. When a user enters their username, password, and email into the form and clicks the submit button, the browser sends a POST request to the server with the form data.

```
POST /registration HTTP/1.1
Host: www.example.com
Content-Type: application/x-www-form-urlencoded
Content-Length: 45

username=johndoe&password=secretpassword&email=johndoe@example.com
```
In this example:

- The request path is `/registration`.
- The content type is `application/x-www-form-urlencoded`, which is common for data submitted via HTML forms.
- The content length is specified as 45 bytes.
- The request body contains the form data encoded as a query string, where field names are associated with user-entered values.

The server then receives this request, extracts the data from the request body, and processes it accordingly, such as storing it in a database for user registration.

This is just one example of using the POST method in HTTP, which is widely used in web applications to send data from a client to a server securely and efficiently.

### PUT
The PUT method in HTTP is used to update or replace an existing resource on the server with the provided data in the request. Below is an example of how a PUT request might look:
```
PUT /api/products/123 HTTP/1.1
Host: example.com
Content-Type: application/json

{
  "name": "New Product Name",
  "price": 29.99,
  "description": "An updated brief description of the product."
}
```
In this example, we attempt to update the product with ID `123`. The request body contains the new product data. When the server receives this request, it should update the resource with the specified ID using the provided data.

It is important to note that the URL in the request should point to the specific resource to be updated, and the request body should contain the updated data for the resource. Additionally, the content type in the request header should reflect the type of data sent in the request body.

---

### PUT vs POST: idempotency
When developing RESTful APIs, both PUT and POST are HTTP methods used to send data to the server, but they serve slightly different purposes. The main distinction between them involves idempotence.

The PUT method is used to update or completely replace an existing resource on the server. This means that if you send the same PUT request multiple times, the resource on the server will be replaced with the new version each time. In other words, every PUT request has the same final effect, regardless of the number of times it is executed. Therefore, the PUT method is considered idempotent.

```
PUT /api/users/123 HTTP/1.1
Host: example.com
Content-Type: application/json

{
  "name": "New User Name",
  "surname": "New User User Surname",
  "age": 30
}
```
In this example, we send a PUT request to update the information of the user with ID `123`. If we execute this request multiple times, the user with ID `123` will always have the name "New User Name."

On the other hand, the POST method is used to send data to create a new resource on the server or to execute non-idempotent actions. Every time you send the same POST request, it may behave differently, for example, by creating a new resource each time.

```
POST /api/users HTTP/1.1
Host: example.com
Content-Type: application/json

{
  "name": "New User Name",
  "surname": "New User User Surname",
  "age": 30
}
```
In this example, we send a POST request to create a new user on the server. Each time we execute this request with the same data, a new user will be created with a different ID.

In summary, PUT is used to update or replace an existing resource and is idempotent, whereas POST is used to create new resources or execute non-idempotent actions.

---

### PATCH
The PATCH method in HTTP is used to apply partial modifications to a resource. This means that instead of replacing the entire resource as the PUT method does, the PATCH method is used to apply specific changes only to parts of the resource.

A PATCH request follows this syntax:

```
PATCH /resource-url HTTP/1.1
Host: example.com
Content-Type: application/json
Content-Length: [body-length]

{
  "field_to_modify": "new_value"
}
```

Assume we have a "user" resource with the following data:

```
{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "age": 30
}
```

If we want to update only the "name" field of the user, we can send the following PATCH request:

```
PATCH /users/1 HTTP/1.1
Host: example.com
Content-Type: application/json
Content-Length: 18

{
  "name": "Jane Doe"
}
```
The response to this request will depend on the server's implementation. However, typically, a `200 OK` response is expected with the modified resource:
```
{
  "id": 1,
  "name": "Jane Doe",
  "email": "john@example.com",
  "age": 30
}
```
The PATCH method is useful when you need to modify only certain parts of a resource without sending its entire representation. However, its implementation may vary between different servers and resources, so it is always advisable to check the specific API documentation being used.
In REST, **HTTP PATCH** is **not necessarily idempotent** because it **partially updates** a resource instead of replacing it entirely. This means that **repeated PATCH requests might lead to different results**, depending on how the update is applied.

---

### PATCH and idempotency
**Scenario 1**
If a PATCH request **modifies a value relatively**, calling it multiple times will **change the resource each time**.

Consider the following example for incrementing a counter:

```http
PATCH /user/123
Content-Type: application/json

{ "credits": "+10" }
```

- First request: `credits = 50 → 60`
- Second request: `credits = 60 → 70`
- **Not idempotent**, since repeated requests change the value.

**Scenario 2**
If PATCH **adds** new data instead of **replacing** it, multiple PATCH requests lead to **duplicate** entries.

```http
PATCH /user/123
Content-Type: application/json

{ "tags": ["VIP"] }
```

- If PATCH **appends** instead of replacing:
    - First request: `tags = ["VIP"]`
    - Second request: `tags = ["VIP", "VIP"]`
- **Not idempotent**, because repeated requests modify the list.

**Scenario 3**
If PATCH **triggers actions** like sending an email, recording a log, or updating a timestamp, calling it multiple times will result in **different side effects**.

```http
PATCH /orders/456
Content-Type: application/json

{ "status": "shipped" }
```
- First call: Marks order as **shipped** and **sends an email**.
- Second call: **Might trigger another email** (not idempotent).

Since PATCH **does not enforce idempotency by definition**, implementations vary.
Some APIs might treat PATCH as **idempotent** if they fully replace fields, while others handle it as **non-idempotent**.

---

### PATCH vs PUT: idempotency

| Method  | Idempotent?  | Purpose  |
|---------|-------------|----------|
| **PUT** | ✅ Yes       | Replaces the entire resource. |
| **PATCH** | ❌ Not necessarily | Modifies part of the resource. |

---

### DELETE
The DELETE method is one of the methods defined by the HTTP protocol (Hypertext Transfer Protocol) for interacting with web resources. As the name suggests, the DELETE method is used to request the removal of a specific resource on the web server.

When an HTTP client sends a DELETE request to a server, it essentially asks the server to delete the specified resource in the request URL. This could be a file, a document, a database record, or any other resource managed by the server.

The DELETE method is considered an "idempotent" operation, meaning that making the same DELETE request multiple times produces the same result as deleting the resource once. However, it is important to note that a deleted resource may no longer be accessible or recoverable after deletion, so it should be used with caution.

In the following:

```
DELETE /resource HTTP/1.1
Host: www.example.com
```

the client requests the server to delete the resource named "resource" on the domain `www.example.com`.

In the following example:

```
DELETE /api/users/123 HTTP/1.1
Host: example.com
```

the client requests the server to delete the user resource with ID `123`. The server should process the request and remove the corresponding resource from the system.

The DELETE method is widely used in web applications to allow users to remove data or resources when they are no longer needed, such as deleting a blog post or an image from a gallery.

# HTTP methods in REST
REST (Representational State Transfer) is an architectural style that uses HTTP methods to perform CRUD (Create, Read, Update, Delete) operations on resources. This document explores HTTP methods in REST, their behavior, idempotency, and practical examples with Java 21 and Spring Boot.

REST APIs utilize standard HTTP methods to interact with resources:

1. **GET** - Retrieves a resource.
2. **POST** - Creates a new resource.
3. **PUT** - Updates or replaces an existing resource.
4. **PATCH** - Partially updates a resource.
5. **DELETE** - Removes a resource.

These methods define clear semantics for how clients interact with servers.

---

## GET
### Description
- Used to retrieve information from the server.
- Should not modify server state (safe operation).
- Can be cached.
- Idempotent: Multiple identical requests yield the same result.

### Example in Spring Boot
```java
@RestController
@RequestMapping("/users")
public class UserController {
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }
}
```

---

## POST
### Description
- Used to create new resources.
- Not idempotent: Multiple identical requests create multiple resources.
- The server determines the new resource's URI.

### Example in Spring Boot
```java
@PostMapping
public ResponseEntity<User> createUser(@RequestBody User user) {
    User createdUser = userService.save(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
}
```

---

## PUT
### Description
- Used to update or replace a resource.
- Idempotent: Multiple identical requests yield the same result.
- If the resource doesn’t exist, it may be created (depending on implementation).

### Example in Spring Boot
```java
@PutMapping("/{id}")
public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
    User updatedUser = userService.update(user);
    return ResponseEntity.ok(updatedUser);
}
```

---

## PATCH
### Description
- Used for partial updates.
- Not necessarily idempotent (depends on implementation).
- Only modifies specified fields.

### Example in Spring Boot
```java
@PatchMapping("/{id}")
public ResponseEntity<User> patchUser(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
    User updatedUser = userService.partialUpdate(id, updates);
    return ResponseEntity.ok(updatedUser);
}
```

---

## DELETE
### Description
- Used to delete a resource.
- Idempotent: Multiple identical requests yield the same result.
- The deleted resource cannot be accessed afterward.

### Example in Spring Boot
```java
@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    userService.delete(id);
    return ResponseEntity.noContent().build();
}
```

---

## Idempotency in REST
| HTTP Method | Idempotent? | Safe? |
|------------|------------|------|
| GET        | Yes        | Yes  |
| POST       | No         | No   |
| PUT        | Yes        | No   |
| PATCH      | No         | No   |
| DELETE     | Yes        | No   |

---

### HTTP Methods in SOAP
SOAP is a protocol based on XML that allows applications to exchange information over a network. While it can use a variety of transport protocols, HTTP is the most common. The primary distinction between SOAP and REST is that SOAP relies heavily on the POST method, even for operations like retrieval of resources.

In SOAP, HTTP methods are typically used in the following manner:

- **POST**: SOAP requests are always sent using the HTTP POST method, even when retrieving data. This is a key difference from REST, where the HTTP method (e.g. GET) indicates the type of action to be performed.
- **GET**: SOAP doesn't use the HTTP GET method directly for retrieving data. Instead, the POST method is used for all SOAP requests, including those that only need to retrieve information. This means the HTTP request body, which typically would be empty for a GET request, still carries an XML payload with the SOAP envelope structure.
- **PUT and DELETE**: Like GET, these HTTP methods are not commonly used in the same way in SOAP. SOAP often uses the POST method to perform updates or deletions, though custom methods can be defined for specific operations.

#### Example: Retrieve All Persons
In this example, we'll use SOAP to retrieve all persons, but as per SOAP's convention, we are forced to use the HTTP POST method even though no content is being sent to modify any data.

**SOAP Request Message:**
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:tns="http://example.com/soap">
    <soapenv:Header/>
    <soapenv:Body>
        <tns:GetAllPersonsRequest/>
    </soapenv:Body>
</soapenv:Envelope>
```

#### Example: Update a Person
Since SOAP does not directly use PUT, updating a person's details is still done using POST with an XML payload indicating the update operation.

**SOAP Request Message (Update Person):**
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:tns="http://example.com/soap">
    <soapenv:Header/>
    <soapenv:Body>
        <tns:UpdatePersonRequest>
            <tns:Person>
                <tns:ID>123</tns:ID>
                <tns:Name>John Doe</tns:Name>
                <tns:Email>john.doe@example.com</tns:Email>
            </tns:Person>
        </tns:UpdatePersonRequest>
    </soapenv:Body>
</soapenv:Envelope>
```

#### Example: Delete a Person
Similarly, deleting a person is done via POST instead of DELETE.

**SOAP Request Message (Delete Person):**
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:tns="http://example.com/soap">
    <soapenv:Header/>
    <soapenv:Body>
        <tns:DeletePersonRequest>
            <tns:ID>123</tns:ID>
        </tns:DeletePersonRequest>
    </soapenv:Body>
</soapenv:Envelope>
```

### HTTP Methods in REST
REST is an architectural style that leverages the simplicity of HTTP methods to define the behavior of requests. Unlike SOAP, which forces the use of POST for all operations, REST uses the HTTP methods according to their semantics:

In REST, the HTTP method reflects the action that the client wants to perform. Here's a breakdown of the common methods:

- **GET**: For retrieving resources (e.g. fetching a list of all persons).
- **POST**: For creating new resources (e.g. creating a new person).
- **PUT**: For updating existing resources (e.g. updating an existing person's details).
- **DELETE**: For deleting resources (e.g. deleting a person).

#### Example: Retrieve All Persons
In REST, retrieving all persons is straightforward, and it uses the HTTP GET method to fetch the information without any content body.

**REST API Request:**
```http
GET /api/persons HTTP/1.1
Host: example.com
```

#### Example: Update a Person
**REST API Request (Update Person):**
```http
PUT /api/persons/123 HTTP/1.1
Host: example.com
Content-Type: application/json

{
    "name": "John Doe",
    "email": "john.doe@example.com"
}
```

#### Example: Delete a Person
**REST API Request (Delete Person):**
```http
DELETE /api/persons/123 HTTP/1.1
Host: example.com
```

#### Take-away
| Aspect                  | SOAP (Post Only)               | REST (GET, POST, PUT, DELETE) |
|-------------------------|--------------------------------|--------------------------------|
| HTTP Method Usage       | Always uses POST for all operations | Uses GET, POST, PUT, DELETE as appropriate |
| Request Body            | Always contains a body (XML)  | GET usually has no body; POST, PUT, DELETE contain data |
| Complexity              | More complex (XML, SOAP envelope) | Simple and lightweight (JSON or XML) |
| Statelessness           | Generally stateful (requires session management) | Stateless (each request is independent) |
| Flexibility             | Less flexible (always POST)  | Highly flexible (uses all HTTP methods) |

The way HTTP methods are used in SOAP and REST reflects the underlying design principles of both protocols. SOAP's rigid use of the POST method for all operations, including data retrieval, contrasts sharply with REST's intuitive and method-specific usage of HTTP verbs like GET, POST, PUT, and DELETE. While SOAP enforces a uniform approach to requests, REST provides more flexibility and clarity by allowing each HTTP method to map directly to an action on the server.

Understanding these differences can help developers choose the right architecture for their needs, whether they are building complex enterprise-level systems (where SOAP might be more appropriate) or lightweight web services (where REST is often a better choice).
