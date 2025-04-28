# Example 1: only JDBC
Here’s a simple example of connection pooling using Java's built-in JDBC without any external libraries like HikariCP.

**This implementation will create a basic connection pool manually:**
```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
  // List to hold available connections
  private List<Connection> connectionPool;
  private String jdbcUrl = "jdbc:mysql://localhost:3306/your_database";
  private String username = "your_username";
  private String password = "your_password";
  private int poolSize = 5; // Pool size

  // Constructor to initialize the pool
  public Main() throws SQLException {
    connectionPool = new ArrayList<>();

    // Initialize the pool with connections
    for (int i = 0; i < poolSize; i++) {
      connectionPool.add(createNewConnection());
    }
  }

  // Method to create a new database connection
  private Connection createNewConnection() throws SQLException {
    return DriverManager.getConnection(jdbcUrl, username, password);
  }

  // Method to get a connection from the pool
  public synchronized Connection getConnection() throws SQLException {
    if (connectionPool.size() > 0) {
      return connectionPool.remove(connectionPool.size() - 1);
    } else {
      // If no connections are available, create a new one
      return createNewConnection();
    }
  }

  // Method to return a connection to the pool
  public synchronized void releaseConnection(Connection connection) {
    if (connection != null) {
      connectionPool.add(connection);
    }
  }

  // Main method to test the pool
  public static void main(String[] args) {
    try {
      Main pool = new Main();

      // Get a connection from the pool
      Connection connection = pool.getConnection();
      System.out.println("Connection acquired: " + connection);

      // Do some work with the connection (e.g., querying the database)

      // Return the connection to the pool
      pool.releaseConnection(connection);
      System.out.println("Connection returned to the pool.");

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
```
### Explanation:
- **Connection Pool Initialization:**
    - The pool is initialized with a fixed number of connections (e.g., 5 in this case).
- **Getting a Connection:**
    - When `getConnection()` is called, the pool either returns an available connection or creates a new one if the pool is empty.
- **Releasing a Connection:**
    - Once a database operation is complete, the connection is returned to the pool using `releaseConnection()`, making it available for reuse.

### Notes:
- This is a very basic connection pool and lacks advanced features like connection validation or automatic cleanup, which are present in production-grade libraries like HikariCP or Apache DBCP.
- This implementation ensures that connections are reused, reducing the overhead of establishing new database connections repeatedly.
