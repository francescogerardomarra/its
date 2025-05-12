# Example 1: manual
Here’s a simple example of connection pooling using Java's built-in JDBC without any external libraries like HikariCP.

**This implementation will create a basic connection pool manually:**
```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
  // Lista condivisa di connessioni disponibili
  private final List<Connection> connectionPool;
  private final String jdbcUrl = "jdbc:mysql://localhost:3306/your_database";
  private final String username = "your_username";
  private final String password = "your_password";
  private final int poolSize = 5; // dimensione massima della pool

  public Main() throws SQLException {
    connectionPool = new ArrayList<>();
    // Inizializzo la pool con un numero fisso di connessioni
    for (int i = 0; i < poolSize; i++) {
      connectionPool.add(createNewConnection());
    }
  }

  // Crea una nuova connessione al database
  private Connection createNewConnection() throws SQLException {
    return DriverManager.getConnection(jdbcUrl, username, password);
  }

  // Metodo thread-safe per ottenere una connessione
  public synchronized Connection getConnection() throws SQLException {
    if (!connectionPool.isEmpty()) {
      Connection conn = connectionPool.remove(connectionPool.size() - 1);
      System.out.println(Thread.currentThread().getName() + " ha acquisito una connessione dalla pool.");
      return conn;
    } else {
      // Se la pool è vuota, ne creo una nuova (opzionale)
      Connection conn = createNewConnection();
      System.out.println(Thread.currentThread().getName() + " ha creato una nuova connessione (pool esaurita).");
      return conn;
    }
  }

  // Metodo thread-safe per restituire la connessione
  public synchronized void releaseConnection(Connection connection) {
    if (connection != null) {
      connectionPool.add(connection);
      System.out.println(Thread.currentThread().getName() + " ha rilasciato una connessione nella pool.");
    }
  }

  // Runnable che simula un lavoro sul DB
  private class Worker implements Runnable {
    @Override
    public void run() {
      Connection conn = null;
      try {
        conn = getConnection();
        // Simulo un'operazione sul DB
        Thread.sleep(500);
        System.out.println(Thread.currentThread().getName() + " sta eseguendo query sul database...");
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        // Rilascio sempre la connessione
        releaseConnection(conn);
      }
    }
  }

  public static void main(String[] args) {
    try {
      Main pool = new Main();

      // Creo un Executor con più thread rispetto alla dimensione della pool
      ExecutorService executor = Executors.newFixedThreadPool(10);
      for (int i = 0; i < 10; i++) {
        executor.submit(pool.new Worker());
      }

      executor.shutdown();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
```
**What this multithreaded example does:**
- in `main` an `ExecutorService` with 10 threads is created, each running a `Worker`.
- each `Worker` calls `getConnection()` concurrently: if the pool has free connections it takes one, otherwise it creates a new one.
- after “working” on the DB (simulated with `Thread.sleep`), each thread returns the connection using `releaseConnection()`.
- on the console you can see which and how many threads acquire/create/release connections until the pool is exhausted.
 making it available for reuse.

### Notes:
- This is a very basic connection pool and lacks advanced features like connection validation or
automatic cleanup, which are present in production-grade libraries like HikariCP or Apache DBCP.
- This implementation ensures that connections are reused, reducing the overhead of establishing
new database connections repeatedly.
