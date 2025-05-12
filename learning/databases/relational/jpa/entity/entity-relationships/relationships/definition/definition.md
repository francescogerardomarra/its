# Definition
**An entity relationship in the context of JPA (Java Persistence API) has the following characteristics:**
- it refers to **associations between different entity classes** in the application’s object model;
- these relationships define how entities are **connected to each other in a relational database**,
typically as foreign key constraints or join tables;
- **types of relationships** include:
    - [One-to-One (`@OneToOne`)](../../relationships/types/one-to-one/definition/definition.md)
    - [One-to-Many (`@OneToMany`)](../../relationships/types/one-to-many/definition/definition.md)
    - [Many-to-One (`@ManyToOne`)](../../relationships/types/many-to-one/definition/definition.md)
    - [Many-to-Many (`@ManyToMany`)](../../relationships/types/many-to-many/definition/definition.md)
- **at the application level**, entity relationships are annotated in the code using JPA
annotations, allowing the ORM framework (e.g., Hibernate) to manage the mapping to the database schema;
- **at the relational level**, these relationships are translated into foreign keys, join tables,
and constraints that enforce data integrity and enable efficient joins in SQL queries;
- under a **database-first approach**, relationships are **predefined in the database** schema 
(through foreign keys, join tables, etc.) and then **mapped into the application** model by
reverse-engineering the schema into entity classes.
