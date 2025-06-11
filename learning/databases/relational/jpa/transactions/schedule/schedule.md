# Schedule
A schedule of a single transaction corresponds to the sequence of operations that define the transaction.

In contrast, a schedule involving multiple transactions represents the interleaving of operations from two or more transactions as they execute at the same time.

The interleaving of operations from different transactions is not determined by the database system or the developer. Rather, it is influenced by external factors such as when transactions are initiated by the client and how the database engine schedules threads at the CPU level. This interleaving is essentially non-deterministic and depends on various runtime conditions.

Consider the following transaction code for transferring funds between two bank accounts:
```java
CREATE OR REPLACE FUNCTION transfer_funds(sender_id INT, receiver_id INT, amount NUMERIC)
RETURNS VOID AS $$
BEGIN
-- Subtract amount from sender
UPDATE accounts
SET balance = balance - amount
WHERE id = sender_id;

    -- Add amount to receiver
    UPDATE accounts
    SET balance = balance + amount
    WHERE id = receiver_id;

    -- Check for invalid transfer amount
    IF amount <= 0 THEN
        RAISE EXCEPTION 'Transfer amount must be positive';
    END IF;
END;
```

Consider two instances of this transactions. When these two transactions are executed simultaneously, their operations can be interleaved in different ways. Below are different valid schedules showing the interleaving of operations.
Possible Schedule 1:

| Time --> | 0      | 1      | 2      | 3      | 4 | 5 |
|----------|--------|--------|--------|--------|---|---|
| Op(T1)   | R1*(A) | W1*(A) |        |        |   |   |
| Op(T2)   |        |        | R2*(B) | W2*(B) |   |   |

Possible Schedule 2:

| Time --> | 0      | 1      | 2      | 3 | 4      | 5 |
|----------|--------|--------|--------|---|--------|---|
| Op(T1)   | R1*(A) | W1*(A) |        |   |        |   |
| Op(T2)   |        |        | R2*(B) |   | W2*(B) |   |

Possible Schedule 3:

| Time --> | 0      | 1      | 2      | 3      | 4 | 5 |
|----------|--------|--------|--------|--------|---|---|
| Op(T1)   |        |        | R1*(A) | W1*(A) |   |   |
| Op(T2)   | R2*(B) | W2*(B) |        |        |   |   |

Possible Schedule 4:


| Time --> | 0      | 1      | 2      | 3      | 4 | 5 |
|----------|--------|--------|--------|--------|---|---|
| Op(T1)   | R1*(A) |        | W1*(A) |        |   |   |
| Op(T2)   |        | R2*(B) |        | W2*(B) |   |   |


    Schedules represent the interleaving of operations from multiple transactions.

    Non-deterministic behavior: The interleaving of operations depends on external runtime factors, such as when the transactions are initiated and how the database engine schedules threads at the CPU level.

    Ri(ELEMENT)* notation: Represents the operations on different data elements (e.g., R1*(A) for the first transaction accessing element A, and R2*(B) for the second transaction accessing element B).


