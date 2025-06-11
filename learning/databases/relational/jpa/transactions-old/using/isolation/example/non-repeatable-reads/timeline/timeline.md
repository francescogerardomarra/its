# Timeline

| Transaction   | Event                                                                                     | Non-Repeatable Read        |
|---------------|-------------------------------------------------------------------------------------------|----------------------------|
| T1            | Transaction 1 starts                                                                      |                            |
| T1            | `SELECT balance FROM Accounts WHERE name = 'John';` → returns 100                         |                            |
| T2            | Transaction 2 starts                                                                      |                            |
| T2            | `UPDATE Accounts SET balance = 50 WHERE name = 'John';`                                   | ----> Data modified        |
| T2            | Transaction 2 commits                                                                     |                            |
| T1            | Re-reads John's balance: `SELECT balance FROM Accounts WHERE name = 'John';` → returns 50 | ❌ Non-repeatable read      |


### 💡 Explanation:

- **T1** reads John’s balance assuming it remains valid throughout the transaction;
- **T2** modifies John’s balance during **T1**'s lifetime;
- When **T1** checks the same data again, it has **changed**, despite being in the same transaction;
- This is a **non-repeatable read**, and it can lead to business logic errors like approving an overdraft.

> ✅ Use `REPEATABLE READ` or `SERIALIZABLE` to prevent this behavior.
