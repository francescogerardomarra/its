# Transaction Boundaries
Transaction boundaries define where a transaction starts and ends.

**In Database (Spring Data JPA):**
- **Starts**: at the beginning of a `@Transactional` method.
- **Ends**: at the end of the method — either commits or rolls back.

🚨 **Important:**
- nested `@Transactional` methods won't start a new transaction unless explicitly told using `Propagation.REQUIRES_NEW`.

