# Example
**Here is an example of the use of `FetchType.EAGER` in an order management system:**
```java
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order")
    private List<OrderItem> items;

    // Getters and setters
}
```
**In this example:**
- it defines an `Order` entity that has a one-to-many relationship with `OrderItem` entities;
- it uses `FetchType.EAGER` to ensure that the associated `OrderItem` entities are loaded immediately when an `Order` is retrieved;
- it simplifies the code when all items need to be processed with the order, avoiding separate database queries for the items.
