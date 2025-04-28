# Connectivity
Connectivity in JDBC refers to how a Java application establishes and manages a connection with a database.

**It involves:**
- **[opening a connection](../management/definition/definition)** – using `DriverManager.getConnection(url, username, password)` to connect to the database;
- **[managing the connection](../management/definition/definition)** – using the `Connection` object to create SQL statements, execute queries, and process results;
- **[closing the connection](../management/definition/definition)** – calling `connection.close()` to free up resources and prevent memory leaks.  
