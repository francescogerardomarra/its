# What is the true difference between a `@OneToMany` and a `@ManyToOne` relationship?
The true difference between `@OneToMany` and `@ManyToOne` in Java's JPA (Java Persistence API)
lies in the direction and ownership of the relationship between two entities,
and how they are mapped in the database.

1. **`@ManyToOne` (the owning side)**:
   - **definition**: many instances of the source entity are associated with one instance of the target entity.
   - **database implication**: the table of the source entity will contain a foreign key to the target entity.
   ```java
    @Entity
    public class Order {
        @ManyToOne
        private Customer customer;
    }
    ```
   - **owns the relationship**: this side controls the foreign key column in the database.

2. **`@OneToMany` (usually the inverse side)**:
   - **definition**: one instance of the source entity is related to many instances of the target entity.
   - **database implication**: no foreign key in the source table unless it's unidirectional with a join table.
   ```java
    @Entity
    public class Customer {
        @OneToMany(mappedBy = "customer")
        private List<Order> orders;
    }
    ```
   - **inverse side**: the `mappedBy` attribute tells JPA that the customer field in the Order entity owns the relationship.

