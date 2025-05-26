# `CascadeType.REMOVE`
Although we don’t call `EntityManager.remove()` directly when using Spring Data JPA, the
framework still honors the cascade configuration when we invoke methods like `delete()` or `deleteById()`.

If `CascadeType.REMOVE` is configured, deleting the parent entity will also delete its associated child 
entities within the same transaction.

## What is `CascadeType.REMOVE`?
`CascadeType.REMOVE` propagates the remove() operation from the parent entity to its child entities.

**In other words:**
- when you delete the parent entity (e.g., `User`), **REMOVE** will cascade to the child entities
(e.g., `Order`) and delete them as well;
- it’s typically used when child entities should not exist without the parent and should be
cleaned up automatically.

## Using `CascadeType.REMOVE` with `JpaRepository`
Let's walk through how `CascadeType.REMOVE` works with `JpaRepository` using the same 
`User` and `Order` entities from earlier.

1. Entities Setup with `CascadeType.REMOVE`
    
    in your entities, we define `CascadeType.REMOVE` to propagate delete operations from
the User to the `Order` entities.

    ```
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
    
        @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
        private List<Order> orders;
    
        // Other fields, constructors, getters, and setters
    }
    ```
    **Here:**
      - `@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)`:
      - when a `User` is deleted, all associated `Order` entities will also be deleted.
    ```
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
   - the `Order` entity does not need to define any cascade behavior on the `@ManyToOne` side.

2. Scenario: Deleting a User and Its Orders
   
   Let’s consider a scenario where you want to delete an existing `User` along with their
associated `Order` entities.
   ```
   // Delete User and cascade delete Orders
   
   // Fetch an existing User
   User user = userRepository.findById(1)
       .orElseThrow(() -> new RuntimeException("User not found"));
   
   // Delete the User
   userRepository.delete(user);  // Cascades remove to associated Order entities
   ```
   **What happens:**
     - the `User` is deleted;
     - all associated `Order` entities are also deleted due to `CascadeType.REMOVE`.

3. What Happens at the Database Level?
   
    **When `delete()` is invoked, Hibernate executes SQL similar to:**

    1. Delete Order Entities:
    ```
    DELETE FROM shop_schema.Order WHERE user_id_fk = 1;
    ```
    2. Delete User Entity:
    ```
    DELETE FROM shop_schema.User WHERE user_id = 1;
    ```
    - Hibernate ensures that child records are removed before the parent to avoid foreign key violations.

4. How `JpaRepository` Works with `CascadeType.REMOVE`
   - **When you call `userRepository.delete(user)`:**
     - JPA internally calls `EntityManager.remove(user)`;
     - `CascadeType.REMOVE` ensures the associated `Order` entities are deleted automatically.
   - without `CascadeType.REMOVE`:
     - if you try to delete a `User` who still has associated `Order` entities, 
     the operation will fail due to referential integrity constraints unless 
     you manually delete the `Order` entities first.
