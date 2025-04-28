# JDBC vs JPA

| Feature              | JDBC                                                                   | JPA                                                                                       |
|----------------------|------------------------------------------------------------------------|-------------------------------------------------------------------------------------------|
| **Definition**       | Low-level API for interacting with databases using SQL queries.        | High-level API that simplifies database operations using object-relational mapping (ORM). |
| **Query Type**       | Requires manual SQL queries.                                           | Uses JPQL (Java Persistence Query Language) or Criteria API.                              |
| **Boilerplate Code** | Requires handling result sets, connections, and transactions manually. | Reduces boilerplate by managing these aspects automatically.                              |
| **Mapping**          | No built-in object mapping; data must be manually converted.           | Maps Java objects to database tables automatically.                                       |
| **Performance**      | Can be faster for simple queries since it directly executes SQL.       | Uses caching and lazy loading to optimize performance.                                    |
| **Flexibility**      | Full control over SQL execution.                                       | Abstracts database interactions but allows native queries when needed.                    |
| **Use Case**         | When direct SQL control and fine-tuned performance are required.       | When working with complex object relationships and reducing boilerplate is a priority.    |

**Conclusion:**
- **Use JDBC** when you need **fine-grained control** over SQL execution and performance tuning.
- **Use JPA** when you want **easier database interaction** with less boilerplate and built-in ORM features.
