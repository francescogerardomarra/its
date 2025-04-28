# `CascadeType.DETACH`
**In Java, `CascadeType.DETACH` has the following characteristics:**
- it propagates the `detach` operation from the parent entity to associated entities;
- it detaches both the parent and child entities from the persistence context, meaning they will no longer be tracked for changes;
- it is useful for breaking the connection between the entity and the persistence context, especially in detached entity scenarios.
