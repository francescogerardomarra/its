# Rollback: when and how it happens?
**By default, Spring rolls back on:**
- Unchecked exceptions (i.e., RuntimeException, Error)
- you can configure it to roll back on checked exceptions:
```java
@Transactional(rollbackFor = Exception.class)
```
**What happens during a rollback:**
- any pending changes in the current transaction are undone;
- the database is returned to the state before the transaction began.