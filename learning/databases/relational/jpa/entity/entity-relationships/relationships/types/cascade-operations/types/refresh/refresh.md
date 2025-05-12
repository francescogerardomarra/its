# `CascadeType.REFRESH`
**In Java, `CascadeType.REFRESH` has the following characteristics:**
- it propagates the `refresh` operation from the parent entity to associated entities;
- it reloads the state of both the parent and child entities from the database, discarding any unsaved changes in memory;
- it is useful when you need to reset both the parent and its associated entities to their current state in the database.
