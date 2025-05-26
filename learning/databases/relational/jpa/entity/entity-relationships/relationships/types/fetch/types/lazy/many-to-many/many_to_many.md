# `@ManyToMany`
## Example: Lazy Loading in Many-to-Many (Order ↔ Item)
We’ll demonstrate how lazy loading works in a **many-to-many** relationship 
between `Order` and `Item` via the `OrderItem` join entity.

### Entity Definitions
**`Order` Entity (Parent Side)**
```java
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @Column(nullable = false)
    private LocalDateTime orderDate = LocalDateTime.now();

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    // Getters and setters
}
```
**`Item` Entity (Other Side of Many-to-Many)**
```java
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemId;

    private String name;

    private BigDecimal price;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    // Getters and setters
}
```
**`OrderItem` Join Entity**
```java
import jakarta.persistence.*;

@Entity
public class OrderItem {

    @EmbeddedId
    private OrderItemKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("itemId")
    @JoinColumn(name = "item_id")
    private Item item;

    private Integer quantity;

    // Getters and setters
}
```
## Demonstration of Lazy Loading in Action
**Assume the database has the following data:**
**`Order` Table**

| orderId | orderDate           |
|---------|---------------------|
| 1       | 2024-05-20 12:00:00 |

**`Item` Table**

| itemId | name     | price  |
|--------|----------|--------|
| 10     | Laptop   | 999.99 |
| 11     | Keyboard | 49.99  |

**`OrderItem` Table**

| order_id | item_id | quantity |
|----------|---------|----------|
| 1        | 10      | 1        |
| 1        | 11      | 2        |

**Code Demonstration:**
```java
// OrderRepository.java
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Optional<Order> findById(Integer id);
}
```
```java
// Main.java
public class Main {

    @Autowired
    private OrderRepository orderRepository;

    public void demoLazyLoading() {
        // Step 1: Fetch an Order by ID
        Order order = orderRepository.findById(1).orElse(null);

        System.out.println("Order fetched: " + order.getOrderId());

        // Step 2: Access OrderItems (triggers lazy loading)
        List<OrderItem> orderItems = order.getOrderItems();
        System.out.println("OrderItems fetched: " + orderItems.size());

        // Step 3: Access Item inside each OrderItem (triggers more lazy loads)
        for (OrderItem orderItem : orderItems) {
            System.out.println("Item: " + orderItem.getItem().getName());
        }
    }
}
```

## What Happens in the Database?
1. Fetch `Order` Entity
   
    ```
    SELECT * FROM Order WHERE order_id = 1;
    ```
    Only the `Order` entity is loaded — no `OrderItem` or `Item` yet.
2. Access O`rder.getOrderItems()` → Lazy Loading

   ```
   SELECT * FROM OrderItem WHERE order_id = 1;
   ```
   The associated join table rows (`OrderItem`) are now fetched.
3. Access `orderItem.getItem()` → Lazy Load Again (for each item)
   
   ```
   SELECT * FROM Item WHERE item_id = 10;
   SELECT * FROM Item WHERE item_id = 11;
   ```
   Triggers separate lazy queries for each `Item` (could cause **N+1** issue).

## Key Points
- Lazy Loading is default for `@OneToMany` and `@ManyToOne` in JPA;
- accessing `order.getOrderItems()` triggers a separate query;
- accessing each `Item` in `OrderItem` triggers another query;
- could lead to **N+1 problem** — optimize with fetch joins or batch loading if needed.