# **Definition**
**Multiplicity** has the following characteristics:
- it defines the number of instances of one entity that can be associated with an instance of another entity;
- at the **application level**, it refers to how many objects (instances) of a class can be linked to objects of another class;
- at the **database level**, it refers to how many rows in one table can be associated with rows in another table via foreign keys and constraints.

### **The primary types of relationships are:**

#### **One-to-One (1:1)**
- **At the application level**: a single instance of an entity is associated with a single instance of another entity;
- **At the database level**:
  - one row in a table corresponds to one row in another table;
  - the foreign key in one table must have a `UNIQUE` constraint to enforce this;
- **Example**: each `Person` has one `Passport`, and each `Passport` belongs to one `Person`.

#### **One-to-Many (1:N)**
- **At the application level**: one instance of an entity can be linked to multiple instances of another entity;
- **At the database level**: one row in the parent table is referenced by multiple rows in the child table through a foreign key;
- **Example**: one `Department` has many `Employees`.

#### **Many-to-One (N:1)**

- **At the application level**: many instances of an entity are linked to a single instance of another entity;
- **At the database level**: many rows in the referring table (child) contain the same foreign key value pointing to one row in the referenced table (parent);
- **Example**: many `Employees` belong to one `Department`.

#### **Many-to-Many (N:N)**

- **At the application level**: many instances of an entity are associated with many instances of another entity;
- **At the database level**: a junction (bridge) table is required, containing foreign keys that reference the primary keys of both related tables;
- **Example**: many `Students` enroll in many `Courses`, and each `Course` can have many `Students`.
