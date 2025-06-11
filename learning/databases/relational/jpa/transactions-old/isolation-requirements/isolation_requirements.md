# Isolation Requirements
- If your application uses specific isolation levels (e.g., `REPEATABLE_READ`, `SERIALIZABLE`), you may need a transaction for read operations to ensure the level of isolation needed.

- **Example**:
  - reading from a table that is being concurrently updated, where you want to ensure
  you read the most up-to-date value, or you want to avoid seeing intermediate states (like uncommitted data).