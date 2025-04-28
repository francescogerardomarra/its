# Example
In JDBC, connections are represented by the `java.sql.Connection` interface.

**Here's how connection management typically works:**
1. **establishing a connection:**
    - to connect to a database, you typically use `DriverManager.getConnection(url, username, password)` method,
      where `url` specifies the database URL, and `username` and `password` are credentials for authentication.
    ```
    Connection connection = DriverManager.getConnection(url, username, password);
    ```
2. **executing database operations:**
    - Once a connection is established, you can create statements
      (e.g., `Statement`, `PreparedStatement`, `CallableStatement`) to execute
      SQL queries, updates, and other database operations.
    ```
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery("SELECT * FROM table_name");
    ```
3. **closing the connection:**
    - after executing database operations, it's essential to close the connection to release resources
      and prevent resource leaks.
    - you can do this using the `close()` method.
    ```
    connection.close();
    ```