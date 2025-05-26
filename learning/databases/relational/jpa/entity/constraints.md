# Constraints
- definition
  - types
    - notNull
    - unique
    - size
    - range
    - pattern
    - enumerated
    - defaultValue
    - notBlank
  - considerations


```
## Constraints: Entity Level vs Database Level

When designing an application, it is essential to enforce data integrity and business rules. Constraints play a crucial role in this, and they can be defined both at the **entity level** (in the code, using an ORM like Hibernate) and at the **database level** (in the database schema). Both levels serve distinct purposes, but together, they form a comprehensive validation and security layer. Understanding why we should repeat constraints at both levels is vital for building reliable and maintainable systems.

---

### Entity Level Constraints

Entity-level constraints are defined within the Java code using annotations that are understood by the ORM (e.g. Hibernate). These constraints are typically used to ensure that the data adheres to the business rules before it is persisted in the database. Common entity-level annotations include:

- `@NotNull`: Ensures that the field cannot be null.
- `@Size`: Restricts the length of a string field.
- `@Unique`: Though not a standard JPA annotation, it is often implemented via custom logic or a database-level constraint to ensure uniqueness.
- `@Min`, `@Max`: Defines a range for numerical values.
- `@Pattern`: Ensures that a string field matches a specific regular expression pattern.
- `@Enumerated(EnumType.STRING)`: Defines how an enum should be persisted in the database.

**Advantages of Entity-Level Constraints:**

1. **Immediate Validation:** Entity-level constraints allow for **real-time validation** when the application processes data. For example, `@NotNull` ensures that a field is non-null before the data is even sent to the database, avoiding potential database errors caused by invalid data.

2. **Separation of Business Logic:** By using annotations like `@Size` or `@Pattern`, you ensure that business rules are handled within the application layer, separate from the underlying database. This separation makes your application more maintainable and flexible.

3. **Portability Across Databases:** Entity-level constraints are database-agnostic. Whether you're using PostgreSQL, MySQL, or any other database, these constraints ensure consistent validation in your application without being tied to a specific database engine or its constraints.

4. **User-Friendliness:** By validating at the entity level, you can catch errors earlier in the application's flow and provide meaningful error messages to users, improving the user experience. This is much easier than dealing with cryptic database errors after the data has been committed.

---

### Database Level Constraints

Database-level constraints are enforced directly in the database schema and are used to ensure that data adheres to specific rules, even when data is inserted or modified outside of the application (e.g. via SQL scripts or other services interacting with the database). Common database constraints include:

- `NOT NULL`: Ensures a column cannot contain null values.
- `UNIQUE`: Ensures all values in a column are unique across the table.
- `CHECK`: Enforces a custom condition for data in a column (e.g. a price cannot be less than 0).
- `FOREIGN KEY`: Ensures that the data in a column matches data in another table, enforcing referential integrity.
- `DEFAULT`: Automatically sets a value for a column if no value is provided.

**Advantages of Database-Level Constraints:**

1. **Data Integrity:** Database constraints are a last line of defense to ensure that only valid data is stored. These constraints are enforced by the database engine itself, ensuring that no invalid data can be written to the database, even if the application fails to validate the data correctly.

2. **Security and Consistency:** By enforcing constraints like `UNIQUE` or `FOREIGN KEY` at the database level, the database ensures that data integrity is maintained, even if other applications or services interact with the database. This is critical when multiple systems need to share the same database.

3. **Efficient Performance:** Constraints such as `UNIQUE` or `CHECK` are often optimized by the database engine, making them more efficient than implementing the same checks at the application level, particularly when dealing with large datasets.

4. **Enforcing Complex Rules:** Some database constraints, such as cascading delete operations (`ON DELETE CASCADE` for foreign keys), are better handled at the database level. These ensure referential integrity is automatically managed by the database, reducing the need for complex logic in the application.

---

### Why Reinforce Constraints at Both Levels?

Although both entity-level and database-level constraints are crucial for maintaining data integrity, they serve different, complementary purposes. Here’s why it makes sense to define constraints at both levels:

1. **Redundancy for Reliability:** While database constraints are the final safeguard, entity-level constraints prevent invalid data from ever reaching the database. This early validation helps prevent database errors and allows your application to catch problems before they cause issues at the storage level. For example, without entity-level validation, an application could attempt to insert a null value into a field marked `NOT NULL` in the database, causing a database error at runtime.

2. **Decoupling Business Logic from Data Storage:** Entity-level constraints define **application-specific rules** (e.g. a user’s email must be in a specific format) that are independent of the database. These constraints ensure that the business rules are enforced regardless of the database system, making the application more portable and flexible. Database constraints, on the other hand, ensure the **integrity of the stored data**, maintaining consistency and relationships between tables even if the data comes from other sources.

3. **Better User Experience with Immediate Feedback:** Entity-level constraints allow for early detection of issues like invalid input or missing fields, which can be handled more gracefully in the application (e.g. showing a user-friendly error message). Database-level errors, by contrast, typically occur later in the process and may require more complex debugging.

4. **Ensuring Data Integrity Across Multiple Sources:** In systems where multiple applications or services interact with the same database, database constraints ensure **consistent rules** across the entire system. For example, if one service bypasses the application logic and directly manipulates the database, the database constraints will ensure that data integrity is not compromised. This redundancy is especially useful in environments with distributed systems or microservices.

5. **Database-Specific Features and Optimization:** Some constraints or features are more naturally enforced at the database level, such as `FOREIGN KEY` relationships, cascading operations, and performance optimizations on indexed columns. These are specific to the capabilities of the database engine and are often necessary to maintain optimal performance and consistency.

---

### Conclusion

Both **entity-level constraints** and **database-level constraints** play critical roles in maintaining data integrity and ensuring the reliability of an application.

- **Entity-level constraints** ensure that business rules are respected before data is persisted, improving application portability and providing early feedback.
- **Database-level constraints** ensure the integrity of the data at rest, protecting against corruption and ensuring consistency, even when data is manipulated outside of the application.

Reinforcing constraints at both levels provides a **robust, fault-tolerant system** that can handle data correctly, regardless of where it is being processed or accessed. By using both, you create an application that is **secure, maintainable, and reliable**.

```