# States
**Essentially the states work in approximately this way:**
- `new`, `managed`, `detached` and `removed` are all only memory states, in the sense that they are not
truly persisted to the database until a transaction commits or uses `flush()`;
- `new`: essentially it's the state of an entity when it has just been created and it's still not within the persistence context memory area;
- `managed`: 
  - this is the state of an entity when it is inside the persistence context and is
  being actively tracked and managed by the persistence context created by entity manager;
  - any changes to it will be automatically synchronized with the database when a transaction commits or `flush()` is called;
- `detached`:
  - essentially it's the state of an entity that was previously `managed`,
  but is now no longer inside the persistence context;
  - it still exists in memory, but JPA is no longer tracking it;
  - to persist changes, it must be reattached using `merge()`.
- `removed`:
  - essentially it's the state of an entity that is still inside the persistence
  context but has been marked for deletion;
  - the entity is scheduled to be deleted from the database when the transaction
  commits or the context is flushed;
  - until then, it still exists in memory and can be accessed like any other
  managed entity, but it won't survive beyond the commit.

| State                | Description                                                        | How to Enter This State                       |
|----------------------|--------------------------------------------------------------------|-----------------------------------------------|
| New (Transient)      | Just created with `new`, not associated with DB or context         | `User user = new User();`                     |
| Managed (Persistent) | Inside the persistence context, tracked by JPA                     | `entityManager.persist(user);` or `find()`    |
| Detached             | Was managed, now outside the context, changes not tracked          | `entityManager.detach(user);` or `em.close()` |
| Removed              | Marked for deletion, will be deleted when transaction is committed | `entityManager.remove(user);`                 |