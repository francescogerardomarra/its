# Database-level
Transactions can be implemented either at the **database level** or at the **application level**.

- **Database Level:**
  - at this level, transactions are typically written using SQL-like procedural
  languages that run directly inside the database engine.
  - **these include:**
    - **PL/SQL** (used in Oracle)
    - **PL/pgSQL** (used in PostgreSQL)

- **Application Level:**
  - transactions can also be managed by the application logic,
  which interacts with the database using client libraries or ORM frameworks.

- **PL/pgSQL** is the procedural language used in **PostgreSQL** to write:
  - Stored procedures
  - Functions
  - Triggers

It allows for the use of SQL statements along with control structures such as conditionals,
loops, and exception handling.

**Here is an example of a transaction written using PL/pgSQL, encapsulated in a function:**
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
This function transfers funds between two accounts and ensures transactional integrity
with basic error handling.
