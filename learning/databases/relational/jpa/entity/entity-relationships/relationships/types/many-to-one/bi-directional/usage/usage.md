# Usage
In a many-to-one relationship, multiple instances of one 
entity are related to a single instance of another entity;
in a **bi-directional** relationship, both sides are aware of each other, allowing
navigation from either the **"many"** side or the **"one"** side.

This type of relationship is useful when both sides need to
interact with each other, allowing data navigation and retrieval
from either perspective.

1. **when to use:**
    - a bi-directional many-to-one relationship is preferred when both
   the "many" and "one" sides need to manage and access the relationship;
    - this is useful in scenarios where the "one" side needs to access
   the collection of related entities on the "many" side, and the "many" 
   side needs to access the "one" side, allowing for complete bidirectional navigation.

2. **example:**
    - an `Employee` entity belongs to a single `Department` entity,
   and a `Department` entity has multiple `Employee` entities;
    - each employee holds a reference to the department they belong to, and
   the department maintains a collection of employees;
    - the `Employee` entity holds the foreign key to the `Department`, and the
   `Department` entity holds a reference to the collection of employees, allowing
   for bi-directional navigation.

