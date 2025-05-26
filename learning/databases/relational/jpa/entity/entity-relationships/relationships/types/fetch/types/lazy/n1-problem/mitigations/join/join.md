# explicit JOIN
When you use lazy loading with FetchType.LAZY, you may face the N+1 query problem, especially when fetching a collection of related entities (such as Order entities for a User). One effective way to mitigate this issue in Spring Data JPA is by using the @Query annotation with a JOIN FETCH clause.

The JOIN FETCH in JPQL (Java Persistence Query Language) allows you to eagerly fetch associated entities in a single query, thus preventing additional queries for each related entity. This eliminates the N+1 query problem by retrieving both the User and its associated Order entities in one query.
Example Scenario: User and Order Entities

Let's use the previous User and Order entities as an example:
The Entities:

java

@Entity
public class User {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

    private String name;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Order> orders;

    // Getters and setters
}

@Entity
public class Order {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

    private String orderDetails;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_fk")
    private User user;

    // Getters and setters
}

Using @Query with JOIN FETCH to Avoid N+1 Problem

To avoid the N+1 query problem in the above entities, you can write a custom query using JOIN FETCH to eagerly load both the User and its associated Order entities in a single query.
UserRepository with Custom Query:

java

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.orders WHERE u.id = :userId")
    Optional<User> findUserWithOrders(@Param("userId") Long userId);
}

How the @Query with JOIN FETCH Works:

    LEFT JOIN FETCH: The LEFT JOIN FETCH ensures that when a User is retrieved, all of its related Order entities are fetched in the same query.

        LEFT JOIN ensures that all users are returned, even if they don’t have any orders.

        FETCH makes the join eager, meaning that the related entities (orders) are fetched immediately when the User is loaded.

    Where Clause: The WHERE clause restricts the query to fetch only the User with the specified userId.

Example Data:

Let's assume the following data in the database:

    3 User entities:

        User 1 (ID: 1, Name: John) has 3 orders.

        User 2 (ID: 2, Name: Jane) has 2 orders.

        User 3 (ID: 3, Name: Mark) has 1 order.

    Total of 6 Order entities, distributed among the users.

Step-by-Step Query Execution with JOIN FETCH:
1. Executing the Query:

Let’s fetch User 1 and its associated Order entities by calling the findUserWithOrders() method in the UserRepository.

java

User user = userRepository.findUserWithOrders(1L).orElseThrow();

The @Query annotation ensures that the User and Order entities are fetched in a single query.
2. Generated SQL Query:

This is the SQL query that will be executed by JPA when you call the findUserWithOrders() method:

sql

SELECT u.id, u.name, o.id, o.order_details, o.user_id_fk
FROM user u
LEFT JOIN order o ON u.id = o.user_id_fk
WHERE u.id = 1;

This query performs the following:

    LEFT JOIN the user table with the order table based on the user_id_fk foreign key.

    **Fetches the User (u) and its associated Order entities (o)` in a single query.

    The WHERE clause ensures only the User with ID 1 is fetched.

3. Query Result:

The result of the query will be:
User ID 	User Name 	Order ID 	Order Details 	User ID (FK)
1 	John 	101 	Order A 	1
1 	John 	102 	Order B 	1
1 	John 	103 	Order C 	1

The query fetches all of User 1's orders in a single query.
Key Benefits of JOIN FETCH:

    Single Query: Only 1 SQL query is executed, retrieving both the User and its associated Order entities. This prevents additional queries from being issued for each Order, thus avoiding the N+1 query problem.

    Eager Fetching: The use of JOIN FETCH ensures that related entities (in this case, Order entities) are loaded immediately with the parent entity (User), eliminating the lazy loading and its associated inefficiencies.

Comparison with Lazy Loading:
Without JOIN FETCH:

    1 query to fetch User 1 (e.g., SELECT * FROM user WHERE id = 1).

    3 additional queries to fetch the Order entities for User 1 (because of lazy loading):

        SELECT * FROM order WHERE user_id_fk = 1

        SELECT * FROM order WHERE user_id_fk = 1

        SELECT * FROM order WHERE user_id_fk = 1

Total queries: 4 queries — 1 for the User entity and 3 for the Order entities.
With JOIN FETCH:

    1 query to fetch both the User and the Order entities in a single query.

Total queries: 1 query — much more efficient.