# Definition
## Parent and Child Entities in JPA
In **JPA (Java Persistence API)** relationships, the **parent entity** is the entity
that holds a reference to one or more related entities (the child entities),
while the **child entity** is the entity that is referenced by the parent.
These entities are typically defined by JPA annotations like `@OneToMany`,
`@ManyToOne`, `@OneToOne`, etc. The parent-child relationship is fundamental
to understanding how cascading operations are propagated across the object graph.

- **Parent Entity:**
  - the parent entity is the entity that typically "owns" the relationship;
  - it is the entity that holds a reference to the child entity or entities;
  - for example, in a @OneToMany relationship, the parent entity holds a collection or list of child entities;
  - the parent entity is generally responsible for managing the relationship and propagating changes to
  the child entity or entities;
  - in most cases, the parent entity is the one that defines the cascading behavior,
  which ensures that certain operations (like `persist`, `merge`, `remove`, etc.) performed on the parent
  entity are also executed on the related child entities.
- **Child Entity:**
  - the child entity is the entity that is referenced by the parent entity;
  - it can either contain a direct reference back to the parent entity or not,
  depending on the type of relationship;
  - for example, in a `@ManyToOne` relationship, the child entity will typically contain a reference
  to the parent entity;
  - in a `@OneToMany` relationship, the child entity is the "many" side and does not usually
  hold a direct reference to the parent entity.

These definitions are crucial because cascading policies are typically defined on the parent entity,
and the cascading operations (like `persist`, `merge`, `remove`, etc.) propagate from the parent entity to the child entities.

## Cascading Policies at the Application Level in JPA
In JPA, cascade refers to a mechanism that allows certain operations
(such as `persist`, `merge`, `remove`, `refresh`, etc.) performed on the parent entity to be automatically
propagated to its child entities. These operations are managed at the application level through JPA annotations
such as `@OneToMany`, `@ManyToOne`, `@OneToOne`, etc.

When you apply a cascade type to a relationship in JPA, it specifies which operations should be automatically
executed on the related child entities whenever the parent entity undergoes a certain operation. For example, 
if a parent entity is persisted, all of its child entities can be persisted automatically if the cascading policy 
is set to `CascadeType.PERSIST`.

It's important to note that this **cascading behavior** is **specific to the application level** and **does not directly 
relate** to cascading policies defined at the **database level** (e.g., `ON DELETE CASCADE`). While both database-level
and application-level cascading serve similar purposes—propagating changes between related entities—application-level
cascading is managed by the JPA provider (like **Hibernate**) and operates within the Java application context, whereas 
database-level cascading works at the **SQL level** to maintain referential integrity in the database itself.

###  Cascading as a Policy in Application-Level Relationships
**Cascading** in JPA can be considered a **policy** because it dictates how changes made to the **parent entity**
should be applied to the child entities. For example, in a **complex object graph** where a **parent entity**
(such as a `User`) is related to **child entities** (such as `Order`), the cascading policy ensures that operations
like saving, updating, or deleting entities are consistently applied to the child entities. This consistency
helps maintain the integrity of the entire object graph during transactional operations.


