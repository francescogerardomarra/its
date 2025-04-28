# Usage
1. **when to use:**
   - a bi-directional one-to-one relationship is 
   preferred when both entities need to be aware 
   of each other, allowing navigation from either
   side of the relationship;
   - this is useful in scenarios where both
   entities often need to access each other's
   data, enhancing flexibility in entity navigation
   and data retrieval.
2. **example:**
    - a `Person` entity has a unique `Passport` entity;
    - each person has exactly one passport, and each
   passport is associated with exactly one person;
    - the `Person` entity holds a reference to the 
   `Passport`, and the `Passport` entity also holds
   a reference to the `Person`, allowing navigation
   from both sides of the relationship.
