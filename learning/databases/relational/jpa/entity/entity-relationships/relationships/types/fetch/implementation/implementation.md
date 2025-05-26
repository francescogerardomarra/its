# Implementation
In JPA (Java Persistence API), the **fetch policy** that defines how related entities are
loaded from the database when a parent entity is retrieved is implemented using the fetch
attribute in various relationship annotations, such as `@OneToMany`, `@ManyToOne`, and `@OneToOne`.
This fetch policy determines whether related entities should be **eagerly** loaded (immediately)
or **lazily** loaded (on-demand) when the parent entity is fetched.

The fetch attribute in JPA annotations allows developers to control how related entities are
retrieved in terms of database queries. The two main fetch strategies available are `FetchType.LAZY` 
and `FetchType.EAGER`.

## The fetch Attribute in JPA Annotations

**The fetch attribute is part of the following annotations in JPA:**
- `@OneToMany`
- `@ManyToOne`
- `@OneToOne`
- `@ManyToMany`

The fetch attribute controls the fetching behavior of related entities when the parent entity is loaded.

## Fetch Strategies

1. **`FetchType.LAZY` (Lazy Loading)**
   - with lazy loading, the related entities are not loaded immediately when the parent entity
   is loaded;
   - instead, they are only retrieved from the database when they are explicitly accessed
   in the application (for example, when a getter method is called for the related entities);
   - this can help improve performance by reducing the number of database queries, especially
   when not all related entities are needed at once;
   - **Example:**
   ```java
   @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
   private List<Order> orders;
   ```
   - in this example, when the `User` entity is retrieved, the related `Order` entities
are not loaded initially;
   - instead, they are loaded only when the orders field is accessed.
This may trigger a separate query to fetch the orders.
2. **`FetchType.EAGER` (Eager Loading)**
   - with eager loading, the related entities are loaded immediately when the parent entity is loaded;
   - this means that a single query will be issued to the database to retrieve both the parent
   entity and its related entities, often using a join in SQL;
   - while this approach ensures that related entities are always available, it may lead to performance
   issues if a large number of related entities are fetched unnecessarily;
   - **Example:**
   ```java
   @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
   private List<Order> orders;
   ```
   - in this example, when the `User` entity is loaded, its related `Order` entities will
   be fetched immediately, typically by issuing a join query to the database;
   - this could retrieve both the `User` and `Order` entities in a single query,
   potentially increasing the response time.


