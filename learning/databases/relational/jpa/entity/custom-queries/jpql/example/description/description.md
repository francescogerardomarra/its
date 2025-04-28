# Description
### **Defining Custom Queries with JPQL**
JPQL is similar to SQL but operates on entity objects instead of database tables.

We can define JPQL queries using the `@Query` annotation in our repository interface.

If it is a simple enough query we can simply make use of method name conventions to have Spring Data JPA create the query automatically.

#### **Example: fetching orders by status using JPQL**
Suppose we need to retrieve a list of orders with a specific status.

**We can define the query as follows:**
```java
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Order> findByStatus(OrderStatus status);
}
```