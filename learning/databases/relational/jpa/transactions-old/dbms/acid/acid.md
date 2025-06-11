# ACID principles
**Transactions are essential for ensuring data integrity and
consistency and are defined by four key properties,
commonly referred to as `ACID` properties:**
- **Atomicity:**
    - ensures that all operations within a transaction
      are completed successfully;
    - if any operation fails, the transaction is aborted,
      and all changes made during the transaction are rolled back,
      leaving the database in its previous state.
- **Consistency:**
    - guarantees that a transaction will bring the database
      from one valid state to another, adhering to all predefined
      rules and constraints of the database;
    - this ensures that the database remains in a consistent
      state before and after the transaction.
- **Isolation:**
    - ensures that transactions are executed independently
      of one another;
    - intermediate results of a transaction are not visible
      to other transactions until the transaction is committed,
      preventing concurrent transactions from interfering with
      each other.
- **Durability:**
    - ensures that once a transaction has been committed,
      its effects are permanently stored in the database, even
      in the event of a system failure.