# Definition
Connection pooling is a technique used to manage and reuse database connections efficiently.

Instead of creating a new database connection every time an application needs to access the database,
connection pooling maintains a pool of pre-established connections that can be reused as needed.

**Here's how connection pooling works:**

1. **Initializing the Connection Pool:**
    - When the application starts, a pool of database connections is created and initialized. The size of the connection pool (i.e., the number of connections it contains) is configurable based on application requirements.
2. **Acquiring a Connection:**
    - When the application needs to access the database, it requests a connection from the connection pool. If a connection is available in the pool, it is retrieved and provided to the application. If no connections are available, the application may wait (blocking) or receive an error, depending on the pool configuration.
3. **Using the Connection:**
    - The application uses the acquired connection to execute database operations, just like in regular connection management.
4. **Returning the Connection to the Pool:**
    - After completing the database operations, the application returns the connection to the pool instead of closing it. This allows the connection to be reused by other parts of the application.
5. **Closing the Connection Pool:**
    - When the application shuts down, the connection pool is closed, and all connections are released.
