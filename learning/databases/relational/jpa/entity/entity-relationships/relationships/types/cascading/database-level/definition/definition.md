# Definition
Cascading at the **database level** refers to the use of **foreign key constraints** to
automatically propagate changes (such as updates or deletions) between related tables.

In PostgreSQL, this cascading behavior is defined using the `ON DELETE`, `ON UPDATE`, and
other related clauses in the **foreign key constraints**.

These clauses allow the database to enforce referential integrity rules between tables
and ensure that related data is handled appropriately when changes occur.

**Important Note:** cascading at the database level is **not** directly mapped to cascading at the application level.

**The two are separate mechanisms:**
- **Database-level cascading** is enforced by the database itself through foreign key constraints, ensuring
that changes (e.g., deletions or updates) to parent records automatically propagate to the child records in
the database. The database enforces this behavior regardless of the application layer.
- **Application-level cascading** (as handled by JPA/Hibernate or other ORM frameworks) works through
annotations or configurations that instruct the ORM to manage related entity states (e.g., saving,
deleting, or updating related entities in memory). This behavior is implemented at the application 
level and is independent of the database cascading behavior.
