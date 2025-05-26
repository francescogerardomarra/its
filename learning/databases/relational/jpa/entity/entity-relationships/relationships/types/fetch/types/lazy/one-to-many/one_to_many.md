# `@OneToMany`
Here's an example of how lazy loading works in a **one-to-many** relationship between
a `User` and `Order` in JPA, using `FetchType.LAZY` to control when the related `Order`
entities are loaded.

I'll break it down with a code example, explain the behavior, and demonstrate the
behavior at the database level.

## Example: `User` and `Order` with Lazy Loading

In this example, we have a **one-to-many** relationship between `User` and `Order`.
A `User` can have many `Order` entities associated with it, and each `Order` belongs to one `User`.

### Entity Definitions
**`User` Entity (Parent)**

**The `User` entity has a **one-to-many** relationship with `Order`:**

```java
import javax.persistence.*;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Order> orders; // Lazy loading is applied here

    // Getters and setters
}
```
**`Order` Entity (Child)**

**The `Order` entity has a **many-to-one** relationship with `User`:**

```java
import javax.persistence.*;

@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String product;

    @ManyToOne(fetch = FetchType.LAZY)  // Lazy loading is applied here as well
    @JoinColumn(name = "user_id")
    private User user;

    // Getters and setters
}
```
**Explanation of the Code:**
- the `User` entity defines a **one-to-many** relationship with `Order`
using `@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)`;
  - this means that the orders field will not be loaded immediately when the `User` entity
  is retrieved from the database;
  - instead, the related `Order` entities will be fetched only when accessed explicitly
  in the application (for example, when calling `getOrders()`).
- the `Order` entity defines a **many-to-one** relationship with `User` using
`@ManyToOne(fetch = FetchType.LAZY)`;
- this means that the `User` for each `Order` will also be lazily loaded.

### Demonstration of Lazy Loading in Action

Let's assume we have the following data in our database:

**Database Tables:**

**`User` Table**

| id | username   |
|----|------------|
| 1  | john_doe   |
| 2  | jane_smith |

**`Order` Table**

| id  | product    | user_id |
|-----|------------|---------|
| 101 | Laptop     | 1       |
| 102 | Smartphone | 1       |
| 103 | Tablet     | 2       |

Now, let's demonstrate how lazy loading works in practice when we load a `User` from the database.

**Code Demonstration:**

Here is the complete demonstration code to showcase how lazy loading
works for a `User` and `Order` relationship:

```java
// UserRepository.java
public interface UserRepository extends JpaRepository<User, Long> {
Optional<User> findById(Long id);
}

// Main.java
public class Main {

    @Autowired
    private UserRepository userRepository;

    public void demoLazyLoading() {
        // Fetch a User entity by ID (user_id = 1, john_doe)
        User user = userRepository.findById(1L).orElse(null);
        
        System.out.println("User fetched: " + user.getUsername()); // Prints: john_doe
        
        // Now accessing the orders triggers lazy loading
        System.out.println("Orders: " + user.getOrders()); // This triggers a second query
    }
}
```
1. Fetch `User` Entity

    When we fetch a `User` entity (for example, `john_doe`), we are not fetching
    the associated `Order` entities immediately because of the lazy loading configuration.
    
    ```
    User user = userRepository.findById(1L).orElse(null);  // Fetch User with id 1 (john_doe)
    System.out.println("User fetched: " + user.getUsername());
    ```
    
    At this point, only the `User` entity is loaded from the database.
    The orders collection has not been populated yet.
    There has been no query to fetch the `Order` entities at this stage.
    
    **SQL Query for `User`:**
    
    ```
    SELECT * FROM user WHERE id = 1;
    ```
    The query above is executed to retrieve the `User` entity, 
    and there is no query for the `Order` entities at this point.

2. Accessing orders Triggers Lazy Loading

    The orders collection is still empty until it is accessed in the application.
    Once you access the orders collection, JPA will fetch the related Order entities from the
    database lazily:
    
    ```
    System.out.println("Orders: " + user.getOrders());  // Accessing orders triggers lazy loading
    ```
    
    At this point, JPA will send a second query to the database to fetch the Order
    entities associated with the User (with id = 1).
    SQL Query for Orders:
    
    ```java
    SELECT * FROM order WHERE user_id = 1;
    ```
    This query is executed to retrieve the `Order` entities related to `john_doe` (with user_id = 1),
    only when the orders collection is accessed.

**What Happens in the Database?**
- **Step 1**:
  - when the `User` entity is loaded, only the `User` data is retrieved from the database;
  - no `Order` entities are retrieved at this point because they are lazily loaded.
- **Step 2**:
  - when you access the orders collection (which is part of the `User` entity), a second 
  query is issued to fetch the `Order` entities that are associated with the `User`
  - this is when the lazy loading mechanism takes effect and retrieves the related data.

**Key Points:**
- **Lazy Loading:**
  - the related `Order` entities are not loaded immediately when the `User` is fetched;
  - they are only loaded when you explicitly access the orders collection.
- **Reduced Initial Query Load:**
  - with lazy loading, the initial query only retrieves the User entity,
  so if you don't need the related `Order` data immediately, it avoids unnecessary queries to the database.
- **Potential N+1 Problem:**
  - one potential downside of lazy loading in a one-to-many relationship is the N+1 query problem;
  - if you fetch multiple User entities, and each User has many `Order` entities, you may end 
  up with a lot of individual queries to retrieve the `Order` entities, leading to performance issues;
  - in this example, if you fetch multiple users, each access to orders would trigger a separate
  query for each user's orders.

