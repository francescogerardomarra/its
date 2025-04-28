# Usage
In a many-to-many relationship, multiple instances of one entity
are associated with multiple instances of another entity.

In a **uni-directional** relationship, only one side is aware of the relationship, 
while the other side does not have any reference back to the first entity.

This type of relationship is useful when only one entity needs to manage and access 
the relationship, simplifying navigation and structure.

1. **when to use:**
    - a uni-directional many-to-many relationship is preferred when only one side needs to manage or access the relationship;
    - this is useful in scenarios where only one entity cares about the association with the other entity, reducing complexity.
2. **example:**
    - a `Student` entity is associated with multiple `Course` entities, but the `Course` entity does not reference the `Student`;
    - each student can enroll in multiple courses, but the courses do not need to know which students are enrolled in them;
    - the `Student` entity holds a collection of `Course` entities, but the `Course` entity does not maintain any reference to the `Student`.
