# `CascadeType.REFRESH`
Even though we don’t manually invoke `EntityManager.refresh()` in typical Spring Data JPA use,
the JPA specification allows cascading this operation if it's explicitly triggered (for example,
when you want to sync the in-memory state of an entity with the latest state from the database).

If `CascadeType.REFRESH` is configured, refreshing the parent entity will also refresh its
associated child entities.

## What is `CascadeType.REFRESH`?
`CascadeType.REFRESH` propagates the `refresh()` operation from the parent entity to its child entities.

**In other words:**
- when you call `refresh()` on the parent entity (e.g., `User`), **REFRESH** will also reload 
the associated child entities (e.g., `Order`) from the database;
- this is useful when you suspect that your in-memory entities are out of sync with the
actual database state (e.g., after direct SQL changes, concurrent modifications, etc.).

## Using `CascadeType.REFRESH` with `JpaRepository`
Even though `JpaRepository` doesn’t provide a direct method to refresh an entity, you can use
the `EntityManager` (if injected manually) to trigger the cascade.

Let’s still define the relationship using the same `User` and `Order` entities.

1. Entities Setup with `CascadeType.REFRESH`
```java
// User Entity
@Entity
@Table(name = "User", schema = "shop_schema")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REFRESH)
    private List<Order> orders;

    // Other fields, constructors, getters, and setters
}
```

**Here:**
   - `@OneToMany(mappedBy = "user", cascade = CascadeType.REFRESH)`:
     - when a `User` is refreshed, the associated `Order` entities will also be refreshed.

```java
// Order Entity
@Entity
@Table(name = "Order", schema = "shop_schema")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    @ManyToOne
    @JoinColumn(name = "user_id_fk", nullable = false)
    private User user;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate = LocalDateTime.now();

    @Column(name = "status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    // Other fields, constructors, getters, and setters
}
```
2. Scenario: Refreshing a `User` and Its Orders
   
    Let’s consider a scenario where the database state has changed 
(e.g., by another application or raw SQL), and you want to re-sync a `User` and
their associated Orders.
    ```
    // Refresh User and cascade refresh Orders
    
    @PersistenceContext
    private EntityManager entityManager;
    
    User user = userRepository.findById(1)
        .orElseThrow(() -> new RuntimeException("User not found"));
    
    // Refresh the entity graph from the database
    entityManager.refresh(user);  // Cascades refresh to associated Order entities
    ```

    **What happens:**
    - the `User` entity is reloaded from the database;
    - all associated `Order` entities are also reloaded, thanks to `CascadeType.REFRESH`.

3. What Happens at the Database Level?

   **When `refresh()` is called, Hibernate issues fresh `SELECT` queries for:**
   - the `User` entity;
   - each associated `Order` entity (joined or separately fetched, depending on the fetch type).

    Example SQL might look like:
    ```
    SELECT * FROM shop_schema.User WHERE user_id = 1;
    
    SELECT * FROM shop_schema.Order WHERE user_id_fk = 1;
    ```
4. How JpaRepository Works with `CascadeType.REFRESH`
   - JpaRepository does not expose a `refresh()` method by default;
   - you must use the `EntityManager.refresh()` manually if you need this behavior;
   - if `CascadeType.REFRESH` is enabled, it will ensure the full object graph is 
   kept in sync when refreshed.

