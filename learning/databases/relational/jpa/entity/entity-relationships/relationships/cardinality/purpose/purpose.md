# Purpose
1. **Definition of cardinality (or multiplicity)**
   - **multiplicity**, also known as **cardinality**, specifies how many instances can participate in a relationship between entities.
   - common cardinalities include:
      - **one-to-one (1:1)**
      - **one-to-many (1:N)**
      - **many-to-one (N:1)**
      - **many-to-many (N:M)**
   - **example**: a single `Customer` can have many `Orders`, but each `Order` is associated with only one `Customer` (**one-to-many relationship**).

2. **In a Database-First model**
   - cardinality is **enforced at the database level**, using constraints such as:
      - **foreign keys** (`FOREIGN KEY`)
      - **uniqueness constraints** (`UNIQUE`)
      - **not null** and other structural rules.
   - this approach allows the database to **enforce referential integrity**:
      - for example, it prevents creating an `Order` without an associated `Customer`.
   - **example**: the presence of a foreign key ensures that each `Employee` must reference a valid `Department`.

3. **At the application level (e.g., with ORM)**
   - once relationships are defined in the database, they are **mapped to object models** using
   tools like ORMs (Object-Relational Mapping).
   - in a **one-to-many** relationship, the ORM:
      - automatically manages the join between related tables;
      - enables **transparent retrieval** of related data.
   - **example**: when querying a `Department` entity, the ORM can automatically load the list of associated `Employees`. This is done behind the scenes via a `JOIN`, but in the application it’s accessed simply and directly (e.g., `department.employees` in code).
