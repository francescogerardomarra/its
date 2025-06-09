# size
## Definition
In a relational context, the `CHARACTER VARYING(n)` (or `VARCHAR(n)`) type limits the maximum number of characters allowed in a column.
When paired with `CHECK` constraints, it can also enforce a minimum length.

At the application level, the `@Size(min = x, max = y)` annotation from the **Jakarta Bean Validation API** ensures that the length of a `String`,
`Collection`, or `array` falls within a specified range before persistence or processing.

**Database-level (Postgres):**
```
CREATE TABLE person (
    id SERIAL PRIMARY KEY,

    name VARCHAR(255) NOT NULL CHECK (char_length(name) >= 3),

    age INTEGER CHECK (age >= 18 AND age <= 100),

    email VARCHAR(255) NOT NULL UNIQUE CHECK (
        email ~ '^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$'
    )
);
```
- `VARCHAR(100)` limits the **maximum** number of characters for name to `100`;
- `CHECK (char_length(name) >= 3)` enforces the **minimum** length at the database level;
- these constraints are strictly enforced by the database engine during insert/update operations.

**Application-level (Hibernate/Java 21):**
```java
package com.example;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 3, message = "Name must be at least 3 characters long")
    private String name;

    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 100, message = "Age must be less than or equal to 100")
    private int age;

    @NotNull(message = "Email cannot be null")
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
            message = "Invalid email format"
    )
    @Column(unique = true)  // <-- Enforces UNIQUE at the database schema level
    private String email;

    public Person() {}

    // Getters and setters ...
}
```
### Notes:
- `@Size(min = 3, max = 100)` performs **validation before persistence** — if it fails, a `ConstraintViolationException` is typically thrown;
- unlike the database, this validation is not automatically enforced unless triggered (e.g., by a validator or a framework like Spring Boot).