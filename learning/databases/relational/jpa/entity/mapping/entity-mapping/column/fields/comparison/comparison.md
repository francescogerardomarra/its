# Comparison
Essentially, if you want to use fields that shouldn’t be mapped to any database column.

**In Summary:**
- use `@Transient` to mark fields that should not be persisted by JPA during persistence operations
(like `save()`, `update()`, etc.);
- this is important when you need temporary, calculated, or session-specific data in your entity,
but you don't want it to be part of your database schema;
- it can also be useful if you're using an external service or API to bring in data temporarily,
you may not want that data to be saved to the database;
- JPA will completely ignore these transient fields when it generates SQL for database interactions.