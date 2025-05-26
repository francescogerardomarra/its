# Definition
## The N+1 Query Problem and Lazy Loading

The N+1 query problem occurs when fetching a list of parent entities, 
and for each parent entity, a separate query is issued to fetch the related child entities.
This leads to one query to fetch the parent entities (in this case, the `User` entities)
plus N additional queries to fetch the related Order entities (one for each `User`). 
This is inefficient because multiple queries are made to retrieve the same related data.

### Why Lazy Loading Leads to the N+1 Problem

**Lazy loading** is a mechanism in JPA where related entities are not loaded immediately when
the parent entity is loaded. Instead, the related entities (e.g., `Order` entities) are fetched
only when they are accessed for the first time in the application code (such as when accessing
the orders collection of a `User`).

While lazy loading is useful for reducing the amount of data fetched from the database, 
it leads to the N+1 query problem when you need to access the related entities for multiple
parent entities.

**Here's why:**
- lazy loading defers the fetching of related child entities (like `Order` in a **User-Order relationship**)
until they are actually accessed in the code.
- for each parent entity, when you access its related child entities (like calling `user.getOrders()`),
a new query is executed to fetch the related child entities;
- this results in multiple database queries— one for each parent entity.

## Example Scenario with Lazy Loading
Consider a scenario where you have multiple `User` entities, and each `User` has a collection
of Order entities.

**Here’s how lazy loading can cause the N+1 query problem:**
- **Step 1:**
  - a query is executed to load all the `User` entities:
    ```
    SELECT * FROM user;  -- This retrieves all users (e.g., 5 users)
    ```
- **Step 2:**
  - for each `User` entity, a separate query is executed to load the related `Order` entities;
  - each `User` entity has its own set of orders, so a new query is issued for every user:

    ```
    SELECT * FROM order WHERE user_id = 1;  -- 1st user, fetch orders (e.g., 3 orders)
    SELECT * FROM order WHERE user_id = 2;  -- 2nd user, fetch orders (e.g., 2 orders)
    SELECT * FROM order WHERE user_id = 3;  -- 3rd user, fetch orders (e.g., 5 orders)
    SELECT * FROM order WHERE user_id = 4;  -- 4th user, fetch orders (e.g., 1 order)
    SELECT * FROM order WHERE user_id = 5;  -- 5th user, fetch orders (e.g., 4 orders)
    ```
**Result:**
- 1 query to retrieve all the `User` entities.
- 5 additional queries (one for each user) to retrieve their related `Order` entities.

So, in total, 6 queries are executed — 1 query for the users and 5 queries for the orders.
This is inefficient because it results in a high number of database round trips, especially
when the number of parent entities (e.g., `User` entities) grows.
The more `User` entities you have, the more queries will be executed for the related `Order` entities.

**The Scale of the Problem:**

If you have hundreds or thousands of users, and each `User` has several orders,
the number of queries grows exponentially.

**For example:**
- 1000 users with 5 orders each would result in 1 query for users and 1000 queries for orders
— a total of 1001 queries;
- 5000 users with 5 orders each would result in 5001 queries — the problem can quickly become unmanageable.
