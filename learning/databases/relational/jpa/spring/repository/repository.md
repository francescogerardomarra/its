# `JpaRepository`
In Spring-based applications, `JpaRepository` is a central interface provided by
Spring Data JPA that significantly simplifies the implementation of data access layers.
By extending `JpaRepository`, developers gain access to a wide range of ready-to-use CRUD (Create,
Read, Update, Delete) operations and advanced features like pagination, sorting, and batch
operations—all without writing a single line of SQL or boilerplate code.

`JpaRepository` builds on top of the `CrudRepository` and `PagingAndSortingRepository` interfaces,
offering more sophisticated data access capabilities.
When a repository interface extends
`JpaRepository<T, ID>`, where `T` is the entity type and ID is the type of its primary key, Spring
Data JPA automatically provides the implementation at runtime.
This allows developers to focus
on domain logic rather than low-level persistence details.

The use of `JpaRepository` promotes the **Repository Pattern**, enabling a clean separation between
business logic and data access logic.
It integrates seamlessly with JPA and Hibernate, ensuring efficient ORM (Object-Relational Mapping)
and transaction management.
This approach enhances code maintainability, reduces boilerplate, and aligns with modern best
practices in enterprise Java application development.

## Example
```java
package com.example.repository;

import com.example.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    // By extending JpaRepository, the following methods are provided by default:

    // 1. save(S entity) - Saves a given entity, either inserting or updating it.
    // 2. saveAll(Iterable<S> entities) - Saves all given entities.
    // 3. findById(ID id) - Retrieves an entity by its id. Returns an Optional.
    // 4. existsById(ID id) - Checks whether an entity with the given id exists.
    // 5. findAll() - Retrieves all entities of type T.
    // 6. findAllById(Iterable<ID> ids) - Retrieves all entities with the given ids.
    // 7. count() - Returns the number of entities of type T.
    // 8. deleteById(ID id) - Deletes the entity with the given id.
    // 9. delete(T entity) - Deletes a given entity.
    // 10. deleteAllById(Iterable<? extends ID> ids) - Deletes all entities with the given ids.
    // 11. deleteAll(Iterable<? extends T> entities) - Deletes all given entities.
    // 12. deleteAll() - Deletes all entities of type T.

    // These methods are automatically implemented by Spring Data JPA, so no need to manually define them.
}
```

**PersonRepository Interface in Spring Data JPA**

The `PersonRepository` interface in Spring Data JPA extends `JpaRepository`, providing seamless interaction with the database for the `Person` entity without requiring custom SQL or JPQL queries.

1. **Spring Data JPA and `JpaRepository`**:  
   By extending `JpaRepository`, the `PersonRepository` inherits several powerful methods for performing common database operations. These built-in methods are automatically available without needing any manual implementation, such as:

    - **`save()`**: Saves a new `Person` entity or updates an existing one.
    - **`findAll()`**: Retrieves all `Person` records from the database.
    - **`findById()`**: Retrieves a specific `Person` by its unique identifier (primary key).
    - **`delete()`**: Deletes a `Person` entity from the database.
    - **`count()`**: Returns the total number of `Person` records in the table.

2. **No Custom Code in Repository**:  
   `PersonRepository` does not need any custom query code. The methods are automatically implemented by Spring Data JPA based on the repository’s method signatures. For example:

    - Calling `personRepository.save(person)` will automatically generate the necessary SQL to insert or update a `Person` entity.
    - Similarly, calling `personRepository.findById(id)` will generate the SQL to retrieve a `Person` by its ID, without additional configuration.

   This functionality is powered by the **Repository Pattern**, which Spring Data JPA leverages to automatically implement data access methods. This reduces boilerplate code and increases maintainability and productivity.

3. **`@Repository` Annotation**:  
   The `@Repository` annotation marks the `PersonRepository` interface as a Spring-managed component. Spring automatically handles its lifecycle and makes it available for dependency injection wherever needed in the application.

4. **`JpaRepository<Person, Long>`**:  
   By extending `JpaRepository<Person, Long>`, the `PersonRepository` inherits a comprehensive set of CRUD operations tailored to the `Person` entity, which has a primary key of type `Long`. This means `PersonRepository` can access methods like `save()`, `findAll()`, `findById()`, and `delete()`, all without any extra code.

5. **Summary**:
    - The `PersonRepository` interface extends `JpaRepository`, automatically providing built-in CRUD operations for the `Person` entity without requiring custom database queries.
    - Spring Data JPA generates the SQL or JPQL queries at runtime based on the method names, making the codebase cleaner and reducing the need for low-level SQL management.
    - No custom implementation is needed within the `PersonRepository` itself because Spring Data JPA handles query generation and execution automatically.

**Understanding `JpaRepository<Person, Long>`**

By extending `JpaRepository<Person, Long>`, the `PersonRepository` interface inherits a comprehensive set of CRUD (Create, Read, Update, Delete) operations specifically designed for the `Person` entity. The generic parameters `<Person, Long>` indicate that the repository is managing `Person` entities and that the primary key (ID) is of type `Long`.

Key Benefits:

- **Built-in CRUD operations:** The repository automatically provides methods like `save()`, `findAll()`, `findById()`, and `delete()` without requiring explicit implementation.
- **Less boilerplate code:** Eliminates the need for writing SQL queries or manually implementing repository logic.
- **Integration with JPA and Hibernate:** Works seamlessly with JPA’s ORM capabilities, handling entity persistence and retrieval efficiently.
- **Optional Pagination and Sorting:** By extending `JpaRepository`, we also inherit methods that allow sorting and paginating results easily, such as `findAll(Sort sort)` and `findAll(Pageable pageable).`
- **Type Safety:** Since we specify `Person` as the entity type and `Long` as the ID type, Spring Data JPA enforces correct usage at compile-time.

Example usage of the `PersonRepository`:

```java
@Autowired
private PersonRepository personRepository;

public void exampleUsage() {
    // Saving a new person
    Person person = new Person();
    person.setName("John");
    person.setSurname("Doe");
    person.setCity("New York");
    person.setAge(30);
    personRepository.save(person);

    // Finding all persons
    List<Person> people = personRepository.findAll();
    
    // Finding a person by ID
    Optional<Person> foundPerson = personRepository.findById(1L);
    foundPerson.ifPresent(System.out::println);
}
```

By leveraging `JpaRepository`, we significantly reduce the amount of code needed to interact with the database while maintaining flexibility and efficiency in data management.

**While Spring Data JPA provides a powerful set of CRUD operations out of the box, there are cases
where more complex queries, such as those involving specific filtering criteria, cannot be autogenerated.
In these scenarios, we need to define custom queries using JPQL (Java Persistence Query Language)
or native SQL.**

**By extending JpaRepository, the following methods are provided by default:**
```
save(S entity)
saveAll(Iterable<S> entities)
findById(ID id)
existsById(ID id)
findAll()
findAllById(Iterable<ID> ids)
count()
deleteById(ID id)
delete(T entity)
deleteAllById(Iterable<? extends ID> ids)
deleteAll(Iterable<? extends T> entities)
deleteAll()
```

This repository also provides basic CRUD operations without needing explicit method declarations.
Additionally, we define a method using method name conventions to auto-generate
a query for retrieving orders by `userId`.

In particular the following method:
```java
List<Order> findByUser_UserId(Integer userId);
```

```java
package com.example.repository;

import com.example.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The `OrderRepository` interface provides basic CRUD operations for the `Order` entity.
 * By extending `JpaRepository`, it inherits several methods for working with the `Order` entity, including:
 * <ul>
 *     <li>`save(S entity)` - Saves or updates an `Order` entity in the database.</li>
 *     <li>`findById(ID id)` - Finds an `Order` entity by its primary key (`orderId`).</li>
 *     <li>`findAll()` - Retrieves a list of all `Order` entities.</li>
 *     <li>`deleteById(ID id)` - Deletes an `Order` entity by its primary key (`orderId`).</li>
 *     <li>`delete(S entity)` - Deletes a specific `Order` entity.</li>
 * </ul>
 *
 * <p>
 * The method `findByUser_UserId` demonstrates how Spring Data JPA generates queries automatically
 * by interpreting method names based on naming conventions.
 * Spring Data JPA translates method names into SQL or JPQL queries at runtime.
 * </p>
 *
 * <h3>Method Name Conventions:</h3>
 * Spring Data JPA uses method name conventions to generate queries dynamically. The naming conventions follow these rules:
 * <ul>
 *     <li>`findBy` - This prefix indicates that the method will retrieve data.</li>
 *     <li>The next part of the method name specifies the field or property that the query will filter by.</li>
 *     <li>If you have a relationship between entities (e.g. `Order` and `User`), you can traverse the relationships by including the related entity's property in the method name.</li>
 *     <li>For example, `User` refers to the `User` entity, and `UserId` refers to the `userId` property in the `User` entity, which is mapped as a foreign key in the `Order` entity via the `user` field.</li>
 * </ul>
 *
 * <h3>Query Generation:</h3>
 * Spring Data JPA will automatically generate a query like:
 * <pre>
 *     SELECT o FROM Order o WHERE o.user.userId = ?1
 * </pre>
 * This query selects `Order` entities where the `userId` of the associated `User` matches the provided `userId`.
 *
 * <h3>Return Type:</h3>
 * Since the method returns a `List<Order>`, it will return an empty list if no matching orders are found for the given `userId`.
 * <br>
 * There's no need to wrap the result in `Optional` for a `List`, as `List` can always be returned, even if it's empty.
 * </p>
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

  /**
   * Retrieves a list of `Order` entities associated with a specific `userId`.
   * Spring Data JPA automatically implements this method based on the name, generating a query
   * like: `SELECT o FROM Order o WHERE o.user.userId = ?1`
   *
   * @param userId The `userId` of the `User` whose orders are being queried.
   * @return A list of `Order` entities related to the given `userId`.
   *         Returns an empty list if no orders are found.
   */
  List<Order> findByUser_UserId(Integer userId);
}
```