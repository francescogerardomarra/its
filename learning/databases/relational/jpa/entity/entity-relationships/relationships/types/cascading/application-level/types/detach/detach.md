# `CascadeType.DETACH`
In typical Spring Data JPA workflows, we don’t usually call `EntityManager.detach()` directly.
However, JPA allows you to propagate the detach operation to child entities if `CascadeType.DETACH`
is configured.

This is useful in more advanced scenarios where you want to explicitly remove an entity (and its 
associated entities) from the persistence context—essentially telling JPA to stop tracking them.

## What is `CascadeType.DETACH`?
`CascadeType.DETACH` propagates the `detach()` operation from the parent entity to its child entities.

**In other words:**
- when you detach the parent entity (e.g., `User`) from the persistence context, **DETACH** will also
detach all associated child entities (e.g., `Order`);
- detached entities are no longer managed by the EntityManager—no changes to them will be synchronized
to the database unless explicitly reattached (e.g., via `merge()`).

## Using `CascadeType.DETACH` with JpaRepository
Since `JpaRepository` does not expose a method like `detach()`, you'd need to access the `EntityManager`
directly.
But if `CascadeType.DETACH` is set, detaching the parent will cascade the detachment to children.

Let’s walk through it using `User` and `Order`.

1. Entities Setup with `CascadeType.DETACH`
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.DETACH)
    private List<Order> orders;

    // Other fields, constructors, getters, and setters
}
```
**Here:**
- `@OneToMany(mappedBy = "user", cascade = CascadeType.DETACH)`:
  - when the `User` is detached, the associated `Order` entities will also be detached.

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
2. Scenario: Detaching a `User` and Orders
   
   This example shows how you might explicitly detach a managed `User` and cascade that to its Orders:
   ```
   // Detach User and cascade detach Orders

   @PersistenceContext
   private EntityManager entityManager;
   
   User user = userRepository.findById(1)
   .orElseThrow(() -> new RuntimeException("User not found"));
   
   // Detach the User entity (also detaches Orders due to `CascadeType.DETACH`)
   entityManager.detach(user);
   ```
   **What happens:**
   - the `User` entity is detached from the persistence context;
   - all associated `Order` entities are also detached;
   - any changes made to these detached entities will not be automatically persisted.

3. What Happens in the Persistence Context?
   
   Unlike `save()` or `refresh()`, no SQL is issued when `detach()` is called.
   
   **Instead:**
   - JPA simply stops tracking the entity and its state;
   - any unsaved changes are discarded (unless the entity is re-attached later using `merge()`).

4. How `JpaRepository` Works with `CascadeType.DETACH`
   - `JpaRepository` doesn't offer `detach()`—this must be done manually via `EntityManager`;
   - `CascadeType.DETACH` ensures that if the parent is detached, the children are too;
   - without this cascade type, detaching the parent will leave the children still managed.