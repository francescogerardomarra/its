# Solution

To avoid problems like phantom reads, where the result set of a query changes during a transaction due to concurrent inserts or deletes, you need:

### 1. **Stronger Isolation Levels**
Use `REPEATABLE READ` or `SERIALIZABLE` to prevent phantom reads.

> These isolation levels ensure that once a transaction reads a set of data, that set is **locked** and cannot change, even if other transactions are inserting or deleting rows that match the same query condition.

---

### 2. **Locks (explicit or implicit)**
You can use explicit locks to prevent other transactions from inserting or deleting rows that might match your query condition.

> **SQL Example:**  
> `SELECT ... FROM table WHERE condition FOR UPDATE`

This will lock the rows that match the condition, preventing other transactions from inserting new rows that would match the condition.

---

### 3. **Predicate Locking**
Some databases, like PostgreSQL, can use **predicate locking** at the `REPEATABLE READ` level, which locks not just the rows you’ve already read but also prevents new rows from matching the condition of your query.

> PostgreSQL's **`REPEATABLE READ`** isolation level ensures that the result set for a query won’t change during the duration of the transaction, preventing phantom rows from appearing.

---

## 💡 TL;DR

- ✅ **Stronger isolation levels (REPEATABLE READ / SERIALIZABLE)** prevent phantom reads.
- ❌ **Lower isolation levels** like `READ COMMITTED` do not protect against phantom reads.
- 🧱 **Predicate locking** or **explicit row locks** can ensure consistent query results even in concurrent environments.
