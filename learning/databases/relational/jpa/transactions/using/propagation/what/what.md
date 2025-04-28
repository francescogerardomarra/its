# What does it affect?
Propagation affects how transactions behave when interacting with each-other.

### **Examples of Propagation Types:**
- **`REQUIRED`** → If an operation should always be part of a transaction (starts one if none exists).
    - *example*: placing an order should always happen inside a transaction, ensuring stock is updated only if the order is completed.
- **`MANDATORY`** → If an operation must always run inside an active transaction (fails if none exists).
    - *example*: processing a payment should only happen within an order transaction—charging a customer outside an order process should not be allowed.
- **`REQUIRES_NEW`** → If an operation should always create a **new** transaction, independent of any existing one.
    - *example*: logging system events should not roll back even if the main transaction fails.
- **`SUPPORTS`** → If an operation should run inside a transaction if one exists, but also work fine without one.
    - *example*: fetching a report should use a transaction if available but still work when called outside one.
- **`NOT_SUPPORTED`** → If an operation should always run **without** a transaction (temporarily suspending any active transaction).
    - *example*: running a complex analytics query that should not be part of an order transaction.
- **`NEVER`** → If an operation must never run inside a transaction (fails if called within one).
    - *example*: a background job that cleans up old logs should not interfere with any active transactions.
- **`NESTED`** → If an operation should run inside an existing transaction but allow for **partial rollbacks**.
    - *example*: placing an order with multiple items—if one item fails to process, only that part is rolled back while the rest of the order proceeds.  
