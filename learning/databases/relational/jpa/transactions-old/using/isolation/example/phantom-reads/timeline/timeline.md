# Timeline

| Transaction   | Event                                                                   | Phantom Read           |
|---------------|-------------------------------------------------------------------------|------------------------|
| T1            | Transaction 1 starts                                                    |                        |
| T1            | `SELECT COUNT(*) FROM Customers WHERE balance > 100000;` → returns 5    |                        |
| T2            | Transaction 2 starts                                                    |                        |
| T2            | `INSERT INTO Customers(name, balance) VALUES ('Alice', 150000);`        | ----> Phantom row      |
| T2            | Transaction 2 commits                                                   |                        |
| T1            | Re-executes same `SELECT` query → returns 6                             | ❌ Phantom read seen    |


### Explanation:

- **T1** expects a consistent set of rows where `balance > 100000`, but it sees a different count on the second read.
- **T2** introduced a row that **didn’t exist when T1 first queried**, causing the result of the same query to change mid-transaction.
- This is exactly what defines a **phantom read** — new rows (phantoms) matching the same query condition appear unexpectedly.
