# Example 2: with HikariCP
**Here is an example of using connection pooling with the HikariCP library in a JDBC-based Java application:**
```java
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
  public static void main(String[] args) {
    // Configure HikariCP
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl("jdbc:mysql://localhost:3306/your_database"); // Database URL
    config.setUsername("your_username"); // Database username
    config.setPassword("your_password"); // Database password
    config.setMaximumPoolSize(10); // Maximum number of connections in the pool

    // Initialize the HikariCP DataSource
    try (HikariDataSource dataSource = new HikariDataSource(config)) {
      // Acquire a connection from the pool
      try (Connection connection = dataSource.getConnection();
           Statement statement = connection.createStatement();
           ResultSet resultSet = statement.executeQuery("SELECT * FROM your_table")) {

        // Process the result set
        while (resultSet.next()) {
          System.out.println("Column 1: " + resultSet.getString("column1"));
          System.out.println("Column 2: " + resultSet.getString("column2"));
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
```
### Explanation:
- **HikariConfig setup:**
    - The `HikariConfig` object is used to configure the connection pool settings such as database URL, credentials, and maximum pool size.
- **HikariDataSource initialization:**
    - A `HikariDataSource` object is created using the configuration. This object manages the connection pool.
- **Connection acquisition and release:**
    - The `getConnection` method is used to acquire a connection from the pool.
    - When the `Connection` object is closed (via `try-with-resources`), it is returned to the pool instead of being physically closed.
- **Query execution:**
    - A query is executed using the `Statement` object, and the results are processed using the `ResultSet`.

This approach ensures efficient use of database connections and reduces the overhead of connection management.
