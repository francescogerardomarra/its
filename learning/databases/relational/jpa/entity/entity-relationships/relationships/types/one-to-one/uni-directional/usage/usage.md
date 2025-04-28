# Usage
1. **when to use:**
   - a uni-directional one-to-one relationship
   is preferred when only one entity needs to be
   aware of the relationship, which simplifies 
   the structure and reduces unnecessary overhead.
2. **example:**
   - a `Person` entity has a unique `Passport` entity;
   - each person has exactly one passport,
   and each passport is associated with exactly 
   one person;
   - the `Person` entity holds a reference to the 
   `Passport`, but the `Passport` does not hold any 
   reference to the `Person`.

