# Data Integrity
By encapsulating related operations into a transaction, DBMS ensures that partial updates do not corrupt data.

For example, transferring money between two accounts must debit and credit—not just one.

## Example
**As you can see we are:**
- **beginning a transaction**;
- **charging** the account with an **id of 1**;
- **crediting** the account with an **id of 2**;
- **commiting** the transactions updates with persistence.

```sql
BEGIN TRANSACTION;
UPDATE accounts SET balance = balance - 100 WHERE id = 1;
UPDATE accounts SET balance = balance + 100 WHERE id = 2;
COMMIT;
```
If any of these updates fail, the DBMS rolls back the transaction, keeping both accounts unchanged.