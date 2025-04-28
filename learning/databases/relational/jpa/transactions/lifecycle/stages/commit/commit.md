# What happens when a transaction is committed?
**If the method finishes without throwing an exception:**
- all changes made during the transaction are flushed to the database (`EntityManager.flush()`);
- the database commits the changes;
- connection and resources are released.