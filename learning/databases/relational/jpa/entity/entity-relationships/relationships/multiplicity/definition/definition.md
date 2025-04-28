# Definition
**Multiplicity has the following characteristics:**
- it defines the number of instances of one entity that can be associated with an instance of another entity;
- it determines how many objects are involved in a relationship.

**The primary types of relationships are:**
- **One-to-One (1:1)**:
    - **Definition**: a single instance of an entity is associated with a single instance of another entity.
    - **Example**: each `Person` has one `Passport` and each `Passport` belongs to one `Person`.
- **One-to-Many (1:N)**:
    - **Definition**: a single instance of an entity is associated with multiple instances of another entity.
    - **Example**: one `Department` has many `Employees`.
- **Many-to-One (N:1)**:
    - **Definition**: multiple instances of an entity are associated with a single instance of another entity.
    - **Example**: many `Employees` belong to one `Department`.
- **Many-to-Many (N:N)**:
    - **Definition**: multiple instances of an entity are associated with multiple instances of another entity.
    - **Example**: many `Students` can enroll in many `Courses` and each `Course` can have many `Students`.