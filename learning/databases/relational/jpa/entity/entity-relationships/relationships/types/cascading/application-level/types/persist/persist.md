# `CascadeType.PERSIST`
Even though we don’t directly call `EntityManager.persist()`
when working with Spring Data JPA, the framework still honors the
cascade configuration defined in the entity mappings.

Specifically, when you invoke `save()` on a new entity, it triggers `persist()`, 
which can cascade to associated entities if `CascadeType.PERSIST` is set.

## What is `CascadeType.PERSIST`?
`CascadeType.PERSIST` propagates the `persist()` operation from the parent entity to its child entities.

**In other words:**
- when you save a new parent entity (e.g., `User`), **PERSIST** will also insert the associated
child entities (e.g., `Order`), assuming they are still in a transient (unsaved) state;
- **PERSIST** is typically used when you want to create and persist an entire object graph 
(parent + children) in one operation.

## Using `CascadeType.PERSIST` with `JpaRepository`
Let’s see how `CascadeType.PERSIST` works in the context of `JpaRepository` using the same 
`User` and `Order` entities.

1. Entities Setup with `CascadeType.PERSIST`

   in your entities, you define `CascadeType.PERSIST` to propagate the persist
   operation from the `User` to its associated `Orders`.
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
    
       @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
       private List<Order> orders;
   
       // Other fields, constructors, getters and setters
   }
   ```
   **Here:**
   - `@OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)`
     - when the `User` entity is persisted, any transient (new) `Order` entities in the list
     will also be persisted;
   - `mappedBy = "user"` indicates that the `Order` entity owns the relationship.
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
    
       // Other fields, constructors, getters and setters
   }
   ```
   - `@ManyToOne` indicates that the `Order` is the owning side and holds the foreign key 
   reference to the `User`.
2. **Scenario**: saving a new `User` and `Orders`
   
   Let’s create and save a new `User` along with two associated `Order` entities.
   ```
   // Persist new User with Orders
   
   User user = new User();
   user.setUsername("jane_doe");
    
   Order order1 = new Order();
   order1.setStatus(OrderStatus.PENDING);
   order1.setUser(user);
   
   Order order2 = new Order();
   order2.setStatus(OrderStatus.PENDING);
   order2.setUser(user);
    
   user.setOrders(Arrays.asList(order1, order2));
    
   // Save the parent entity (User)
   userRepository.save(user);  // This cascades persist() to the Order entities
   ```
   **What happens here:**
   - `userRepository.save(user)` triggers a `persist()` operation because the `User` is new;
   - `CascadeType.PERSIST` ensures that both `Order` entities (also new) are automatically
   persisted along with the `User`.
3. What Happens at the Database Level?
   
   **When `save()` is executed, Hibernate generates SQL similar to the following:**
   1. Insert `USER`:
   
     ```
     INSERT INTO shop_schema.User (username) VALUES ('jane_doe');
     -- Assume auto-generated user_id = 1
     ```
   
   2. Insert Associated Orders:
   
     ```
     INSERT INTO shop_schema.Order (user_id_fk, order_date, status)
     VALUES (1, CURRENT_TIMESTAMP, 'PENDING');
     
     INSERT INTO shop_schema.Order (user_id_fk, order_date, status)
     VALUES (1, CURRENT_TIMESTAMP, 'PENDING');
     ```
     - the `user_id_fk` in each `Order` references the newly created `User`.
4. How `JpaRepository` Works with `CascadeType.PERSIST`
   - if the `User` is a new entity (no ID set), calling `save(user)` causes JPA to persist
   it and cascade the `persist()` to its child entities.
   - however, if the `User` already exists and you add new Orders later, `CascadeType.PERSIST`
   won’t apply on its own.
     - in that case, you’ll need to either:
        - explicitly save the new `Order` entities using `orderRepository.save(order)`, or
        - use `CascadeType.ALL` or `CascadeType.MERGE` in addition to **PERSIST**.
