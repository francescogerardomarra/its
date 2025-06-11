### Default isolation level
When using `@Transactional` without explicitly setting an isolation level,
the default isolation level of your database is being used.

### **What Is the Default Isolation Level?**
**The default isolation level depends on the database:**
- **PostgreSQL** → **Read Committed**
- **MySQL (InnoDB)** → **Repeatable Read**
- **SQL Server** → **Read Committed**

Since we are using PostgreSQL in our examples and projects our methods are running with **Read Committed** isolation by default.
