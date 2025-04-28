# Usage
In a **uni-directional** relationship, only 
the **"one"** side is aware of the relationship (owning side),
while the **"many"** side has no reference back to
the **"one"** side.

1. **when to use:**
    - a uni-directional one-to-many relationship is preferred when only the "one" side needs to manage and access the relationship;
    - this is useful when the "many" side does not need to know about the "one" side, such as when you're only interested in maintaining control over the relationship from one entity.
2. **example:**
    - a `Department` entity has multiple `Employee` entities;
    - each department can reference many employees, but the employees do not hold any reference to the department;
    - the `Department` entity holds a reference to the collection of `Employee` entities, but the `Employee` entity does not reference the `Department`, making the relationship uni-directional.

