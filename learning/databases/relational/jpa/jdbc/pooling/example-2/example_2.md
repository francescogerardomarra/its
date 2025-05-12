# Example 2: HikariCP
**Here is an example of using connection pooling with the HikariCP library in a JDBC-based Java application:**
```java
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
  public static void main(String[] args) {
    // Configurazione HikariCP
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl("jdbc:mysql://localhost:3306/your_database");
    config.setUsername("your_username");
    config.setPassword("your_password");
    config.setMaximumPoolSize(5); // max connessioni nella pool

    try (HikariDataSource dataSource = new HikariDataSource(config)) {
      // Executor con più thread rispetto al massimo della pool
      ExecutorService executor = Executors.newFixedThreadPool(10);

      Runnable queryTask = () -> {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM your_table")) {

          // Elaborazione del risultato
          if (rs.next()) {
            System.out.println(Thread.currentThread().getName() + " ha letto count = " + rs.getInt(1));
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      };

      // Sottometto 10 task concorrenti
      for (int i = 0; i < 10; i++) {
        executor.submit(queryTask);
      }

      executor.shutdown();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
```
**What this HikariCP example shows:**

- We use a `HikariDataSource` with pool size = 5.
- We create a pool of 10 threads; each thread obtains a connection via `dataSource.getConnection()`.
- When all 5 slots are in use, the other threads wait until a connection is freed.
- Thanks to the `try-with-resources`, at the end of the block the `Connection` is automatically returned to the pool instead of being closed permanently.
