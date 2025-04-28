# Designing Resources in REST: URLs, Paths, and Parameters
A well-designed REST API should be intuitive, predictable, and easy to navigate.

## 1. Resources in REST

In REST, everything is considered a **resource**. A resource is any entity that can be identified, manipulated, and exchanged over HTTP. Examples of resources include users, orders, products, or even more abstract concepts like sessions or reports.

Each resource is uniquely identified by a **Uniform Resource Locator (URL)**, which serves as the entry point for interacting with that resource. For example, a user resource might be represented by:

```
/users/123
```

where `123` is the unique identifier for a specific user.

### 1.1 Protocol
The protocol (or scheme) is the first part of the URL and specifies the method used to communicate with the web server. It determines how the data is transferred between your browser and the server. Examples include:
- `http://`
- `https://` (secure version of HTTP)
- `ftp://` (File Transfer Protocol)
- `mailto://` (used for email links)

Example: `https://`

### 1.2 Subdomain (Optional)
A subdomain is a prefix to the domain name, typically used to organize different sections or services of a website. In many cases, `www` is used as a subdomain, but it can be anything (e.g. `blog`, `shop`, etc.). Not all URLs have a subdomain.

Example: `www` in `www.example.com` is a subdomain.

### 1.3 Domain Name
The domain name is the main address of the website and identifies the specific entity hosting the content. It is composed of the second-level domain and the top-level domain (TLD).

Example: `example` in `www.example.com`

### 1.4 Top-Level Domain (TLD)
The TLD is the last part of the domain name, following the dot (`.`). It helps identify the type or geographical location of the website. Some common TLDs include:
- `.com` (commercial websites)
- `.org` (non-profit organizations)
- `.gov` (government websites)
- `.edu` (educational institutions)

Example: `.com` in `www.example.com`

### 1.5 Path
The path specifies the specific resource or location within the website. It often corresponds to files or directories on the server. It can include several segments, each separated by a forward slash (`/`).

Example: `/products/shoes` in `https://www.example.com/products/shoes`

#### Path Variable
A path variable is a dynamic value within the path that is typically used in routing or identifying resources. It might look like this:

Example: `/users/{userId}` (where `{userId}` is replaced with an actual user ID, such as `/users/123`)

### 1.6 Query Parameters
Query parameters are used to pass data to the server, typically in the form of key-value pairs. They follow the question mark (`?`) and are separated by the ampersand (`&`) symbol if there are multiple parameters. Query parameters are commonly used for searches or filtering.

Example: `?search=shoes&color=blue` in `https://www.example.com/search?search=shoes&color=blue`

- `search` and `color` are parameters, and their values are `shoes` and `blue`, respectively.

### 1.7 Fragment (Optional)
The fragment identifier, also known as the hash fragment, refers to a specific section within a page. It’s preceded by a hash (`#`) symbol. It doesn't affect the server request but tells the browser to scroll to that specific part of the webpage.

Example: `#top` in `https://www.example.com#top`

### Full Example URL Breakdown

Consider the following URL:

`https://www.example.com/products/shoes?search=blue&size=10#top`

- **Protocol:** `https://`
- **Subdomain:** `www`
- **Domain:** `example`
- **TLD:** `.com`
- **Path:** `/products/shoes`
- **Query Parameters:** `search=blue&size=10`
- **Fragment:** `#top`

### Other Components
- **Port (Optional):** A port number may be included after the domain name and colon (`:`), but it is usually implied by the protocol. For example, `http://example.com:8080` uses port `8080`.
- **Authority (Optional):** In some cases, a URL might include an authority section (often a username or password). For example: `ftp://username:password@ftp.example.com`

With all these parts, you can see how URLs are structured and what role each component plays in locating and accessing web resources.

## 2. Designing RESTful URLs and Paths

A well-structured REST API should have clear, consistent, and predictable URLs. Here are the core principles:

### 2.1. Use Nouns, Not Verbs

URLs should represent **resources**, not actions. The action is determined by the HTTP method (GET, POST, PUT, DELETE, etc.).

✅ Correct:
```
/orders/456
/users/123
```
❌ Incorrect:
```
/getUser?id=123
/deleteOrder?id=456
```

### 2.2. Use Plural Nouns for Collections

Collections of resources should be named using **plural nouns**.

✅ Correct:
```
/users  → Collection of users
/orders → Collection of orders
```
❌ Incorrect:
```
/user  → Unclear whether this is a single user or a collection
/order → Not consistent with other resource names
```

### 2.3. Hierarchical Relationships

Use **nested resources** when there is a clear parent-child relationship.

✅ Correct:
```
/users/123/orders  → Orders belonging to user 123
/orders/456/items  → Items in order 456
```
❌ Incorrect:
```
/users/123?orders  → Query parameters are not ideal for hierarchical relationships
/orders/items/456  → Implies that order items are top-level resources
```

### 2.4. Avoid Deep Nesting

Over-nesting can make APIs difficult to use. Instead, favor **resource identifiers**.

❌ Too deeply nested:
```
/users/123/orders/456/items/789/reviews/001
```
✅ Better approach:
```
/orders/456/items/789/reviews/001
```

## 3. Query Parameters vs. URL Paths

### 3.1. When to Use URL Paths
Use **URL paths** for:
- Identifying specific resources
- Defining relationships between resources

✅ Examples:
```
/products/789  → Retrieves product 789
/users/123/orders  → Retrieves orders for user 123
```

### 3.2. When to Use Query Parameters
Use **query parameters** for:
- Filtering
- Sorting
- Pagination

✅ Examples:
```
/orders?status=pending  → Get pending orders
/products?category=electronics&sort=price_desc  → Get electronics sorted by price (descending)
/users?page=2&limit=20  → Paginate user results
```

### 3.3. Avoid Using Query Parameters for Actions
Query parameters should not be used for actions that modify resources.

❌ Incorrect:
```
/users/123?delete=true  → Actions should use HTTP verbs instead
```
✅ Correct:
```
DELETE /users/123  → Use DELETE method instead
```

## 4. Best Practices for Path and Parameter Design

### 4.1. Use Consistent Naming Conventions
Maintain a consistent naming pattern throughout the API.

✅ Preferred:
```
/users
/orders
/products
```
❌ Avoid:
```
/getUsers
/all_orders
/product-list
```

### 4.2. Support Pagination
When returning large collections, use pagination.

✅ Example:
```
GET /users?page=2&limit=50
```

### 4.3. Handle Filtering and Sorting Gracefully
Allow filtering and sorting via query parameters.

✅ Example:
```
GET /orders?status=shipped&sort=date_desc
```

### 4.4. Use Versioning in the URL
APIs evolve over time. Use versioning to avoid breaking changes.

✅ Example:
```
/v1/users/123
```

### 4.5. Use Hyphens Instead of Underscores
Use hyphens (`-`) instead of underscores (`_`) for better readability in URLs.

✅ Example:
```
GET /user-profiles
```
❌ Avoid:
```
GET /user_profiles
```

### 4.6. Avoid File Extensions
REST APIs should avoid specifying file extensions like `.json`, `.xml`, or `.html` in the URL paths. File extensions are considered to be implementation details that don't align with the purpose of REST APIs, which focus on resources rather than formats.

Instead, use **HTTP headers** to indicate the response format. This allows the API to remain flexible and avoid unnecessary coupling to a specific format in the URL path.

❌ Avoid:
```
GET /users/123.json
```

✅ Example:
```
GET /users/123
Accept: application/json
```


By omitting the file extension and using the `Accept` header to specify the desired format, the API remains flexible. The client can request different formats (e.g. `application/xml`, `text/html`) without modifying the URL, allowing the server to respond based on the requested media type.

This approach ensures that the URL is clean and focused on the resource itself, rather than the representation format.

## 5. Path Variables and Query Parameters in REST

When designing RESTful APIs, two common ways to pass data to an endpoint are **path variables** and **query parameters**. Understanding their differences and when to use each is crucial for creating intuitive and effective APIs.

### Path Variables

Path variables (also known as route parameters) are part of the **URL path** and typically represent **resources** or **entities**.

#### Syntax Example:
```plaintext
GET /users/{userId}/orders/{orderId}
```
Here, `{userId}` and `{orderId}` are path variables that specify which user and order to retrieve.

#### Characteristics:
- Used for identifying specific resources.
- Required for the request to make sense.
- Typically used in **hierarchical** relationships.
- Cannot be omitted; each request needs values for these variables.

#### Example in a REST API:
```plaintext
GET /products/{productId}
```
- **Good Use Case:** Fetching details of a specific product.
- **Bad Use Case:** Filtering a list of products by category (better suited for query parameters).

### Query Parameters

Query parameters are key-value pairs added to the **URL after a `?`**. They are often used to provide **filters, search criteria, or optional data**.

#### Syntax Example:
```plaintext
GET /products?category=electronics&sort=price_desc
```
Here, `category` and `sort` are query parameters used to filter and sort the results.

#### Characteristics:
- Used for filtering, sorting, or optional configurations.
- Not required to access the endpoint.
- Can be omitted without making the URL invalid.
- Support multiple parameters at once.

#### Example in a REST API:
```plaintext
GET /users?role=admin&status=active
```
- **Good Use Case:** Searching for all admin users who are active.
- **Bad Use Case:** Identifying a specific user (should be a path variable instead).

### How to Decide Which to Use

| Feature       | Path Variables         | Query Parameters                        |
|---------------|------------------------|-----------------------------------------|
| Purpose       | Identify a resource    | Filter, search, modify request behavior |
| Required?     | Yes                    | No                                      |
| URL Structure | `/users/{userId}`      | `/users?status=active`                  |
| Use Case      | Retrieve specific item | Retrieve filtered list                  |

#### General Guidelines:
- **Use path variables for required, unique identifiers** (e.g. `/users/{userId}`).
- **Use query parameters for optional filtering and sorting** (e.g. `/users?status=active`).
- **Avoid mixing them unnecessarily** to maintain clarity and usability.

By following these guidelines, you can design RESTful APIs that are intuitive, maintainable, and user-friendly.
