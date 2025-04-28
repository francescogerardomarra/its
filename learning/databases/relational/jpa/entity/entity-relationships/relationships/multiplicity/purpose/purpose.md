# Purpose
1. **Defining cardinality**:
   - multiplicity specifies the number of instances that can participate
   in a relationship between entities;
   - it defines the cardinality, such as one-to-one, one-to-many, many-to-one, and many-to-many.
   - **Example**: a single `Customer` can have multiple `Orders`, but each `Order`
   is associated with a single `Customer` (**one-to-many relationship**).
2. **Ensuring data integrity**:
   - by clearly defining the relationships and their multiplicities, the 
   database can enforce rules that maintain data integrity;
   - this prevents invalid data states, such as an `Order` without an associated `Customer`.
   - **Example**: multiplicity constraints ensure that an `Employee` is always
   assigned to exactly one `Department`.