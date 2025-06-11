# Code vs instance
In database theory and application development, it's important to distinguish between two related concepts: **transaction code** and **transaction instance**. While both refer to operations performed on data, they are fundamentally different in terms of their context and execution.

The **transaction code** represents the **sequence of operations** that define a transaction. This can be thought of as the **template** or **blueprint** for the transaction. It defines what operations need to be executed on the database (or any other system), often involving SQL relational operations. A transaction code can be implemented in:

- **Database-specific languages** (e.g., PL/SQL, PL/pgSQL),
- **High-level programming languages** (e.g., Java, Python) using object-oriented approaches with libraries like Hibernate or JPA.

The transaction code specifies the steps that must be followed, but it does **not** specify when or with which specific data the transaction should be executed.

A **transaction instance**, on the other hand, represents a **single execution** of a transaction code. When the transaction code is invoked, it is executed with specific parameters (such as data values or conditions), creating an instance of that transaction. Each execution of the transaction code with a specific set of input parameters is considered an instance of the transaction.

For example, if a transaction code is defined to transfer funds between two bank accounts, a transaction instance would occur every time the transfer is actually executed with real data (specific sender and receiver account IDs, and the transfer amount).

A **transaction instance** is often referred to simply as a **transaction** for brevity and is commonly denoted as **TI**.
This shorthand helps simplify discussions about individual executions of a transaction.

Consider the following **transaction code** for transferring funds between two bank accounts, implemented in **PL/pgSQL**:

```
CREATE OR REPLACE FUNCTION transfer_funds(sender_id INT, receiver_id INT, amount NUMERIC)
RETURNS VOID AS $$
BEGIN
    -- Start transaction block (implicitly managed within the function)
    
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

    -- Exception handling block
EXCEPTION
    WHEN OTHERS THEN
        RAISE NOTICE 'Transaction failed: %', SQLERRM;
        RAISE;
END;
```

This is the general sequence of operations that transfers funds from one account (sender_id) to another (receiver_id). The code checks for invalid transfer amounts and handles any errors that may arise.

This is the definition of the function, and it resides in the database.

Let’s look at two possible transaction instances of the above transaction code, where the function is called with different parameters. We will represent these instances using the operation-predicate-element notation.

Instance 1: Transfer Funds from Account 1 to Account 2, Amount = 100
For the first instance, where we transfer 100 from Account 1 to Account 2, the operations can be represented as follows:


R1(A)  -- T1 reads the balance of Account 1
W1(A)  -- T1 writes the new balance to Account 1 (subtracting 100)
R2(B)  -- T2 reads the balance of Account 2 (before T1 writes to it)
W2(B)  -- T2 writes the new balance to Account 2 (adding 100)

This represents the flow of the transaction with the respective read (R) and write (W) operations executed by T1 (transaction 1) and T2 (transaction 2).


Sender: Account 1
Receiver: Account 2
Amount: 100

Instance 2: Transfer Funds from Account 3 to Account 4, Amount = 50
For the second instance, where we transfer 50 from Account 3 to Account 4, the operations would be represented as:


R1(A)  -- T1 reads the balance of Account 3
W1(A)  -- T1 writes the new balance to Account 3 (subtracting 50)
R2(B)  -- T2 reads the balance of Account 4 (before T1 writes to it)
W2(B)  -- T2 writes the new balance to Account 4 (adding 50)

This shows how the transaction executes with different parameters, resulting in a different sender and receiver account, as well as a different transfer amount.

Sender: Account 3
Receiver: Account 4
Amount: 50