# When read operations don’t need a transaction
- **Single Read without Dependency on Other Operations**:
  - if your read operation is simple and there are no concerns about concurrent 
  writes or isolation, you typically don’t need a transactional boundary.
- **Example**:
  - a simple `SELECT` statement where no other transactions are modifying the data at the same time.
- **Key Takeaway**:
  - you don’t need a transaction boundary for reads unless the operation
  needs to meet specific consistency and isolation requirements.
