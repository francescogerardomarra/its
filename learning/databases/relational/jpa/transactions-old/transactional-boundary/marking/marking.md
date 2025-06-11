# Marking a method with a transactional context
In the context of making a method transactional, you are essentially defining
the boundaries of the transaction, ensuring that the method adheres to ACID principles and
guarantees proper transaction management.

**Here’s a clear explanation of how a method can be transactional:**

1. **Marking the Method as a Transaction (Transactional Boundary):**
   - before considering specific propagation or isolation behaviors,
   you must first establish that the method is transactional;
   - this is the transactional boundary;
   - the method should:
     - **Start a Transaction:**
       - a method becomes transactional when it involves operations that require atomicity, 
       consistency, isolation, and durability (ACID properties), such as database writes (inserts, updates, deletes).
       - the method is typically marked as transactional by using an annotation or
       equivalent mechanism;
       - for example, in Spring, you would use `@Transactional`;
       - this signals that the method operates within a transactional scope.
       - **Example (Spring Framework):**
       ```java
        @Transactional
        public void updateUser(User user) {
            userRepository.save(user);
            // other database operations
        }
        ```
        - in this case, the `@Transactional` annotation marks the method as transactional,
       meaning that any changes to the database (such as saving the user) should adhere to the transactional rules.
        - **Example (JPA/Hibernate):**
        - using `@Transactional` in Spring (or similar frameworks), the method should execute operations
       that require transactional control, such as database operations:
       ```java
        @Transactional
        public void createOrder(Order order) {
            orderRepository.save(order); // involves a database operation
            // additional transactional operations
        }
        ```
     - **Rollback and Commit Logic:**
       - the method should ensure that changes are committed if everything succeeds and rolled back if there’s an error.
       This is a key part of the transactional behavior, ensuring atomicity.
       - if an exception occurs during the method execution, the transaction should be automatically rolled back to maintain data consistency.
       - in frameworks like Spring, if an exception occurs during the execution of a method marked with `@Transactional`, the framework
       will automatically roll back the transaction unless otherwise specified (for example, you can customize rollback
       behavior to include certain exceptions).
