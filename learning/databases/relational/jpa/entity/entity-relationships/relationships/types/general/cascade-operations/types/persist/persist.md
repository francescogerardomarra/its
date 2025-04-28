# `CascadeType.PERSIST`
**In Java, `CascadeType.PERSIST` has the following characteristics:**
- it propagates the `persist` operation from the parent entity to associated entities;
- it ensures that when the parent entity is saved, all related entities are also automatically saved;
- it is useful for inserting new records into both the parent and child tables without needing separate save operations.