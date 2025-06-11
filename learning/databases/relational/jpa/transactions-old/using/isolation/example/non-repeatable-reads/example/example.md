# Example
A **non-repeatable read** just means the **same query inside a transaction gives different results**
because another transaction modified the data in a row.

The issue is that the data unexpectedly changes while a transaction is still running.

In other words while a transaction is running another transaction could modify a row
of data making it so the operation always acts different, unpredictable, **non-repeatable**.

**At it's core a non-repeatable read is a problem because it's a concurrency issue.**

**Example:**
- you read that John's balance is $100;
- you check it's enough for a $70 purchase;
- you approve the transaction;
- but between the check and the debit, another transaction drops the balance to $50;
- ❌ You’ve now allowed an overdraft. That’s a bug.

## Pseudocode
```
// Transaction A: Processes a purchase request
BEGIN TRANSACTION A

  // Step 1: Read balance
  balance = SELECT balance FROM accounts WHERE user = 'John'
  // balance = 100

  // Step 2: Check if enough funds for $70
  IF balance >= 70 THEN
      // Meanwhile...

      // Transaction B starts and reduces John's balance
      BEGIN TRANSACTION B
        UPDATE accounts SET balance = 50 WHERE user = 'John'
        COMMIT TRANSACTION B
      END

      // Back to Transaction A:
      // Step 3: Proceed with debit
      UPDATE accounts SET balance = balance - 70 WHERE user = 'John'
      // John's balance now becomes -20 🧨

  END IF

COMMIT TRANSACTION A
```