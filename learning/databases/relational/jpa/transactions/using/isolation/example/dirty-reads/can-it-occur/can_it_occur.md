# Can they occur in PostgreSQL?
Dirty reads are **not testable** in an environment that uses **PostgreSQL** as a database like our exercises.

**This is because:**
- **non-repeatable** reads can occur in `READ COMMITTED` but not in `REPEATABLE READ`;
- **phantom reads** can occur in `REPEATABLE READ`, but `SERIALIZABLE` prevents them;
- **dirty reads** can only occur in `READ UNCOMMITTED`;
- `READ UNCOMMITTED` **is not supported in PostgreSQL databases**.

**NOTE**: if you tried to set a transaction with an isolation level of `READ UNCOMMITTED` in a **PostgreSQL** database, it would act like it had an isolation level of `READ COMMITTED`.