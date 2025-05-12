# `CascadeType.REMOVE`
**In Java, `CascadeType.REMOVE` has the following characteristics:**
- it propagates the `remove` operation from the parent entity to associated entities;
- it deletes the related entities when the parent entity is deleted, ensuring consistency in entity relationships;
- it is useful for automatically cleaning up child entities when a parent entity is removed from the database.
