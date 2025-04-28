# Usage
In a many-to-many relationship, multiple instances of one entity are associated
with multiple instances of another entity.

In a **bi-directional** relationship, both sides are aware of each other, 
allowing navigation from either side of the relationship.

This type of relationship is useful when both entities need to access
and manage the relationship, enabling complete bidirectional navigation.

1. **when to use:**
    - a bi-directional many-to-many relationship is preferred when both entities need to manage and access the relationship;
    - this is useful in scenarios where both sides often interact, and it is important to navigate and retrieve data from either entity.

2. **example:**
    - a `Student` entity is associated with multiple `Course` entities, and a `Course` entity is associated with multiple `Student` entities;
    - each student can enroll in multiple courses, and each course knows which students are enrolled;
    - both the `Student` and `Course` entities maintain collections of each other, allowing for navigation from both sides.

