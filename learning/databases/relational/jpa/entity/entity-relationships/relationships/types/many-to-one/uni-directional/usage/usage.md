# Usage
In a many-to-one relationship, multiple instances of one
entity are related to a single instance of another entity.

In a **uni-directional** relationship, only the **"many"**
side is aware of the relationship, while the **"one"** side
does not have any reference back to the **"many"** side.

This type of relationship is useful when the "one" side 
does not need to know about the entities related to it, allowing for
simpler entity navigation and a lighter structure.

1. **when to use:**
    - a uni-directional many-to-one relationship is preferred
   when only the "many" side needs to manage and access the relationship;
    - this is useful when the "one" side does not need to 
   access or manage the entities on the "many" side,
   such as in cases where the relationship is only 
   important from the perspective of the "many" side.

2. **example:**
    - an `Employee` entity belongs to a single `Department` entity;
    - each employee holds a reference to the department they belong to, but 
   the department does not maintain a collection of employees;
    - the `Employee` entity holds the foreign key to the `Department`, making 
   the relationship uni-directional.

