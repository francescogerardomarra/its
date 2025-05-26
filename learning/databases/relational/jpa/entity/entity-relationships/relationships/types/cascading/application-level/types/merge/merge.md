# `CascadeType.MERGE`
Even though we don't directly work with the `EntityManager` (which typically provides methods like `merge()`
and `persist()`), Spring Data JPA still handles the cascade behavior internally when we perform
operations like `save()`.
The key point is that the `save()` method provided by `JpaRepository` acts as a combination of `persist()`
and `merge()`, depending on whether the entity already exists or not.

## What is `CascadeType.MERGE`?
`CascadeType.MERGE` propagates the `merge()` operation from the parent entity to its child entities.

**In other words:**
- if you update the parent entity (e.g., `User`), **MERGE** will propagate to the child entities (e.g., `Order`)
and update them as well;
- **MERGE** is typically used when you want to **update** an entity in the persistence context, especially
when you have detached entities that should be synchronized with the database.

## Using `CascadeType.MERGE` with JpaRepository
Let's walk through an example of how `CascadeType.MERGE` would work with `JpaRepository` using
the `User` and `Order` entities from your previous example.

1. Entities Setup with `CascadeType.MERGE`

    In your entities, we specify `CascadeType.MERGE` to propagate the `merge()` operation from the parent
    (`User`) to the child (`Order`).
    
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
    
        @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
        private List<Order> orders;
    
        // Other fields, constructors, getters and setters
    }
    ```
    **Here:**
    - **`@OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)`:**
      - when the `User` entity is merged, the `Order` entities will also be merged.
    
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
    - the `Order` entity remains the same as in the previous example.

2. Scenario: updating an existing `User` and `Orders`

    Let's consider a scenario where you want to **update a `User`** along with their 
    associated `Order` entities.
    In this case, `CascadeType.MERGE` will propagate the update to the child entities
    (i.e., the associated `Order` entities).
    
    ```
    // Update User and Orders Example
    
    // Fetch an existing User from the database
    User user = userRepository.findById(1).orElseThrow(() -> new RuntimeException("User not found"));
    
    // Update some properties of the User
    user.setUsername("new_username");
    
    // Now, let's modify the orders associated with this user
    for (Order order : user.getOrders()) {
    order.setStatus(OrderStatus.SHIPPED);  // Change order status
    }
    
    // Save the updated User (this will also propagate the merge to Orders)
    userRepository.save(user);  // Cascades merge to associated Order entities
    ```
    
    **Explanation of what happens:**
      - `userRepository.save(user)`:
        - since we are using `CascadeType.MERGE`, `save()` will update both the `User`
        entity and the associated `Order` entities;
        - `CascadeType.MERGE` ensures that changes to the `User` entity (e.g., the `username`)
        and the `Order` entities (e.g., the `status`) are persisted to the database.

3. What Happens at the Database Level?

    **When you call `save()` (which internally calls `merge()`), it performs the following steps in the database:**
    - `User` Entity Update:
      - the `User` entity is updated in the `shop_schema.User` table, with the new value of the `username` field;
    - `Order` Entity Update:
      - each `Order` entity associated with the `User` is also updated in the `shop_schema.Order` table;
      - specifically, the `status` field is updated from **PENDING** to **SHIPPED**.
  
    **Example SQL Generated:**
    1. Updating `User` Entity:
      ```
      UPDATE shop_schema.User
      SET username = 'new_username'
      WHERE user_id = 1;
      ```
    2. Updating Associated `Order` Entities:
      ```
      UPDATE shop_schema.Order
      SET status = 'SHIPPED'
      WHERE user_id_fk = 1;
      ```

4. How `JPARepository` Works with `CascadeType.MERGE`:

   - **With `JpaRepository.save()`, the following happens:**
     - **if the `User` entity already exists in the database (based on the primary key)**, JPA internally
            calls `merge()`, updating the entity;
     - **if the** `User` **entity is new** (not in the database), it will be persisted using `persist()`.
    
   - **So, `CascadeType.MERGE` in the context of `JpaRepository`:**
     - ensures that when the `User` entity is merged, any changes to associated `Order` entities (like `status`)
            are also persisted;
     - even though `JpaRepository.save()` abstracts away `merge()` and `persist()`, the cascading update 
              still applies as expected.
