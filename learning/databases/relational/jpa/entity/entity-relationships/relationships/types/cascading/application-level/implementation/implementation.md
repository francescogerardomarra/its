# Implementation
The cascade policy is configured using the cascade attribute in relationship annotations.

**The following are some of the most commonly used relationship annotations with cascading behavior:**
- `@OneToMany`: defines a one-to-many relationship (e.g., one `User` can have many Orders);
- `@OneToOne`: defines a one-to-one relationship (e.g., one `User` has one Profile);
- `@ManyToOne`: defines a many-to-one relationship (e.g., many Orders belong to one `User`);
- `@ManyToMany`: defines a many-to-many relationship (e.g., many Users can have many Roles).

**Consider a one-to-many relationship between `User` and `Order` entities:**
```java
@Entity
public class User {

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders;

    // other fields and methods
}
```
```java
@Entity
public class Order {

    @ManyToOne
    @JoinColumn(name = "user_id_fk", nullable = false)
    private User user;

    // other fields and methods
}
```

**In this example:**
- the `@OneToMany` annotation in the `User` entity defines a one-to-many relationship where one `User` can be
associated with many `Order` entities;
- the `cascade = CascadeType.ALL` on the `@OneToMany` relationship means that when the `User` entity is persisted,
merged, or removed, all the related `Order` entities will also be persisted, merged, or removed automatically;
- the `@ManyToOne` annotation in the `Order` entity defines a many-to-one relationship, indicating that
each `Order` is related to a single `User` entity.

This cascading behavior ensures that when a parent entity (`User`) undergoes an operation,
such as being removed from the database, its related child entities (`Order`) will also be
automatically removed, preserving the integrity of the object graph.

## Defining Cascade on the Parent Side
In JPA, the parent side of the relationship typically defines the cascade attribute.
This means that the parent entity is responsible for defining how changes should propagate to the child entities.

### Example of Cascade on Parent Side

**Here is a simple example of how the cascade attribute is defined on the parent side of a relationship:**
```java
@Entity
public class User {

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders;

    // other fields and methods
}
```

**In this example:**
- the `User` entity is the parent entity, and it defines the cascading behavior on the orders collection;
- the `cascade = CascadeType.ALL` means that all operations on the `User` entity 
(such as persist, merge, remove, etc.) will be cascaded to the related `Order` entities;
- the `Order` entity, in turn, is the child entity. It does not define the cascade behavior directly
because the `User` entity owns the relationship and handles cascading operations.

This ensures that when a `User` entity is saved (persisted), deleted, or updated,
all its associated `Order` entities are also saved, deleted, or updated accordingly.