# `@BatchSize`
To address the N+1 query problem when dealing with lazy loading, batch fetching is one
of the strategies available in JPA. Batch fetching helps reduce the number of database queries
by grouping multiple related child entities (e.g., `Order` entities) into a single query instead
of executing separate queries for each parent-child relationship.

The `@BatchSize` annotation is used to define how many related child entities should be fetched
in one batch. Instead of issuing a new query for each child entity, JPA groups multiple child
entities and retrieves them in a single query. This significantly reduces the number of queries
when dealing with multiple related entities.

## Example Scenario: Batch Fetching with `@BatchSize`
Let’s continue with the example where we have a `User` entity that has a
**one-to-many** relationship with an `Order` entity. We’ll use the `@BatchSize` 
annotation to fetch multiple `Order` entities at once instead of issuing one query for each `Order`.

**The Entities:**
```java
@Entity
@BatchSize(size = 10)  // Fetch 10 orders per batch
public class User {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer userId;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Order> orders;

    // other fields, getters, and setters
}
```
```java
@Entity
public class Order {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_fk")
    private User user;

    // other fields, getters, and setters
}
```

In this example, the `User` entity has a **one-to-many** relationship with the `Order` entity.
We've added the `@BatchSize(size = 10)` annotation on the `User` entity, which means that 
when lazy loading the related `Order` entities, JPA will fetch up to 10 `Order` entities in 
one batch rather than loading each `Order` individually.

## How Batch Fetching Works:
**Let’s say we have the following data in the database:**
- 5 `User` entities (with `userId` values from 1 to 5).
- **Each `User` entity has a varying number of `Order` entities, such as:**
  - `User` 1 **has** 15 `Order` entities;
  - `User` 2 **has** 8 `Order` entities;
  - `User` 3 **has** 20 `Order` entities;
  - `User` 4 **has** 6 `Order` entities;
  - `User` 5 **has** 12 `Order` entities.

### **Fetching the `Users` and Their `Orders` with Lazy Loading:**

If we were to load all User entities and their associated `Order` entities with 
lazy loading (using `FetchType.LAZY`), and if we **did not use batch fetching**, the following would happen:

**Without Batch Fetching (Using Lazy Loading):**
- **Step 1:**
  - a query is issued to fetch all `User` entities:
  ```
  SELECT * FROM user;  -- Retrieves 5 users
  ```
- **Step 2:**
  - a separate query is issued for each `User` to fetch their related `Order` entities;
  - since we're using lazy loading, these queries are only executed when the orders
  collection is accessed in the application:

  ```
  SELECT * FROM order WHERE user_id = 1;  -- Retrieves orders for User 1
  SELECT * FROM order WHERE user_id = 2;  -- Retrieves orders for User 2
  SELECT * FROM order WHERE user_id = 3;  -- Retrieves orders for User 3
  SELECT * FROM order WHERE user_id = 4;  -- Retrieves orders for User 4
  SELECT * FROM order WHERE user_id = 5;  -- Retrieves orders for User 5
  ```
    

In this case, **5 additional queries** are executed, one for each user.
So in total, **6 queries are executed** — 1 for the `User` entities and 5 for the `Order` entities.
This is an example of the **N+1 query problem**.

### With Batch Fetching (Using `@BatchSize`):

If we configure batch fetching using the `@BatchSize(size = 10)` annotation, this will
reduce the number of queries executed for fetching related Order entities.
When the `User` entities are loaded, JPA will group the related `Order` entities into batches of 10,
based on the batch size configuration.

**For example:**
- **Step 1:**
  - a query is executed to fetch all `User` entities:
  ```
  SELECT * FROM user;  -- Retrieves 5 users
  ```
- **Step 2:**
  - instead of issuing separate queries for each user’s orders, batch
  fetching groups the `Order` entities into batches of 10;
  - JPA will execute one query to fetch all the `Order` entities for all users,
  grouped by the batch size (10 orders per query):
  ```
  SELECT * FROM order WHERE user_id IN (1, 2, 3, 4, 5) LIMIT 10;  -- Retrieves first 10 orders
  SELECT * FROM order WHERE user_id IN (1, 2, 3, 4, 5) LIMIT 10 OFFSET 10;  -- Retrieves next 10 orders
  ```

In this case, **only 2 additional queries** are needed to fetch the related `Order` entities,
even though there are multiple orders for each user.
JPA fetches **up to 10 orders at once** in each query, reducing the total number of queries executed.

### Final Result:
- **1 query** to retrieve all `User` entities;
- **2 queries** to retrieve the `Order` entities in batches of 10.

So, instead of **6 queries** (1 for users and 5 for orders), **only 3 queries** are executed
— **1 for users** and **2 for orders**, significantly reducing the number of queries executed and
improving performance.
