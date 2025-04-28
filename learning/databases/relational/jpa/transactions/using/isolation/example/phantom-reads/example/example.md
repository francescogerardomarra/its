# Application
A **phantom read** occurs when the **same query inside a transaction returns different results** because another transaction inserts or deletes rows that match the query condition.

The issue is that the result set itself changes, meaning new rows "magically" appear or disappear while a transaction is still running.

In other words, while a transaction is running, another transaction could insert or delete rows, making the operation behave differently each time, introducing phantom rows into the results.

**At it's core a phantom read is a problem because it's a concurrency issue.**

**Example:**
- you run a query to count how many high-value customers there are, where balance > $100,000;
- you find there are 5 such customers;
- meanwhile, another transaction inserts a new high-value customer with a balance of $150,000;
- you rerun the same query to count the high-value customers again;
- this time, you see 6 customers;
- ❌ you’ve counted a "phantom" row that wasn’t there initially, leading to incorrect business logic. That’s a bug.

**Here is an example that showcases this phenomenon:**

```
// Transaction A
BEGIN TRANSACTION A (ISOLATION LEVEL READ COMMITTED)

  result1 = SELECT COUNT(*) FROM Customers WHERE balance > 100000;
  // result1 = 2

  // Meanwhile...
  BEGIN TRANSACTION B
    INSERT INTO Customers(name, balance) VALUES ('Alice', 150000);
    COMMIT TRANSACTION B
  END

  result2 = SELECT COUNT(*) FROM Customers WHERE balance > 100000;
  // result2 = 3 ❗ Phantom row appeared

COMMIT TRANSACTION A
```